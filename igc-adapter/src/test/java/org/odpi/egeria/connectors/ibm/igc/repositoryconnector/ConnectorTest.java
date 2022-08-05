/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Note;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapper;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.model.ChangeSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.*;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.*;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mocks.MockConnection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCRelationshipGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.adapters.eventbus.topic.inmemory.InMemoryOpenMetadataTopicConnector;
import org.odpi.openmetadata.adapters.repositoryservices.ConnectorConfigurationFactory;
import org.odpi.openmetadata.adminservices.configuration.properties.OpenMetadataExchangeRule;
import org.odpi.openmetadata.frameworks.auditlog.ComponentDevelopmentStatus;
import org.odpi.openmetadata.frameworks.connectors.Connector;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBroker;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectionCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.http.HttpHelper;
import org.odpi.openmetadata.opentypes.OpenMetadataTypesArchive;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLog;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLogDestination;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditingComponent;
import org.odpi.openmetadata.repositoryservices.connectors.omrstopic.OMRSTopicConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.archivestore.properties.OpenMetadataArchive;
import org.odpi.openmetadata.repositoryservices.connectors.stores.archivestore.properties.OpenMetadataArchiveTypeStore;
import org.odpi.openmetadata.repositoryservices.connectors.stores.auditlogstore.OMRSAuditLogStore;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.eventmanagement.OMRSRepositoryEventExchangeRule;
import org.odpi.openmetadata.repositoryservices.eventmanagement.OMRSRepositoryEventManager;
import org.odpi.openmetadata.repositoryservices.eventmanagement.OMRSRepositoryEventPublisher;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.*;
import org.odpi.openmetadata.repositoryservices.localrepository.repositorycontentmanager.OMRSRepositoryContentHelper;
import org.odpi.openmetadata.repositoryservices.localrepository.repositorycontentmanager.OMRSRepositoryContentManager;
import org.odpi.openmetadata.repositoryservices.localrepository.repositorycontentmanager.OMRSRepositoryContentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

/**
 * Test the connector(s) using the mocked server resources.
 */
public class ConnectorTest {

    private static final Logger log = LoggerFactory.getLogger(ConnectorTest.class);

    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;
    private IGCOMRSMetadataCollection igcomrsMetadataCollection;
    private IGCOMRSRepositoryEventMapper igcomrsRepositoryEventMapper;
    private IGCRepositoryHelper igcRepositoryHelper;
    private OMRSRepositoryContentManager contentManager;
    private OMRSRepositoryEventManager eventManager;
    private InMemoryOpenMetadataTopicConnector inMemoryEventConnector;
    private OMRSRepositoryHelper repositoryHelper;
    private String sourceName;

    private String metadataCollectionId;
    private String otherMetadataCollectionId;

    private List<AttributeTypeDef> supportedAttributeTypeDefs;
    private Map<String, TypeDef> supportedTypeDefs;
    private List<String> supportedTypeDefNames;

    /**
     * Construct base objects.
     */
    public ConnectorTest() {

        HttpHelper.noStrictSSL();
        metadataCollectionId = UUID.randomUUID().toString();
        otherMetadataCollectionId = UUID.randomUUID().toString();
        supportedAttributeTypeDefs = new ArrayList<>();
        supportedTypeDefs = new HashMap<>();
        supportedTypeDefNames = new ArrayList<>();
        inMemoryEventConnector = new InMemoryOpenMetadataTopicConnector();

    }

    /**
     * Initialize the connector with some basic values.
     */
    @BeforeSuite
    public void startConnector() {

        Connection mockConnection = new MockConnection();
        ConnectorConfigurationFactory connectorConfigurationFactory = new ConnectorConfigurationFactory();
        ConnectorBroker connectorBroker = new ConnectorBroker();

        Connector auditLogConnector = null;
        try {
            auditLogConnector = connectorBroker.getConnector(connectorConfigurationFactory.getDefaultAuditLogConnection());
            auditLogConnector.start();
        } catch (ConnectionCheckedException | ConnectorCheckedException e) {
            log.error("Unable to get or start audit log via the broker.", e);
            assertNull(e);
        }
        List<OMRSAuditLogStore> auditLogDestinations = new ArrayList<>();
        auditLogDestinations.add((OMRSAuditLogStore)auditLogConnector);
        OMRSAuditLogDestination destination = new OMRSAuditLogDestination("TestServer", "Test", "ODPi", auditLogDestinations);
        OMRSAuditLog auditLog = new OMRSAuditLog(destination, -1, ComponentDevelopmentStatus.IN_DEVELOPMENT, "ConnectorTest", "Testing of the connector", null);
        contentManager = new OMRSRepositoryContentManager(MockConstants.EGERIA_USER, auditLog);
        eventManager = new OMRSRepositoryEventManager("Mock Outbound EventManager",
                new OMRSRepositoryEventExchangeRule(OpenMetadataExchangeRule.SELECTED_TYPES, Collections.emptyList()),
                new OMRSRepositoryContentValidator(contentManager),
                new OMRSAuditLog(destination, OMRSAuditingComponent.REPOSITORY_EVENT_MANAGER));

        // TODO: setup eventManager with the InMemoryTopicConnector, so that it writes to memory rather than Kafka
        List<Connector> inMemoryConnector = new ArrayList<>();
        inMemoryConnector.add(inMemoryEventConnector);
        OMRSTopicConnector omrsTopicConnector = new OMRSTopicConnector();
        omrsTopicConnector.initializeEmbeddedConnectors(inMemoryConnector);
        OMRSRepositoryEventPublisher publisher = new OMRSRepositoryEventPublisher("Mock EventPublisher",
                omrsTopicConnector,
                auditLog.createNewAuditLog(OMRSAuditingComponent.EVENT_PUBLISHER));
        eventManager.registerRepositoryEventProcessor(publisher);

        try {
            Object connector = connectorBroker.getConnector(mockConnection);
            assertTrue(connector instanceof IGCOMRSRepositoryConnector);
            igcomrsRepositoryConnector = (IGCOMRSRepositoryConnector) connector;
            igcomrsRepositoryConnector.setAuditLog(auditLog);
            igcomrsRepositoryConnector.setRepositoryHelper(new OMRSRepositoryContentHelper(contentManager));
            igcomrsRepositoryConnector.setRepositoryValidator(new OMRSRepositoryContentValidator(contentManager));
            igcomrsRepositoryConnector.setMetadataCollectionId(metadataCollectionId);
            igcomrsRepositoryConnector.start();
        } catch (ConnectionCheckedException | ConnectorCheckedException e) {
            log.error("Unable to get connector via the broker.", e);
            assertNull(e);
        }

        try {
            OMRSMetadataCollection collection = igcomrsRepositoryConnector.getMetadataCollection();
            assertTrue(collection instanceof IGCOMRSMetadataCollection);
            igcomrsMetadataCollection = (IGCOMRSMetadataCollection) collection;
            assertEquals(igcomrsMetadataCollection.getMetadataCollectionId(MockConstants.EGERIA_USER), metadataCollectionId);
        } catch (RepositoryErrorException e) {
            log.error("Unable to match metadata collection IDs.", e);
            assertNotNull(e);
        }

        try {
            Connection eventMapperConnection = connectorConfigurationFactory.getRepositoryEventMapperConnection(
                    "org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapperProvider",
                    null,
                    "localhost:1080"
            );
            Object connector = connectorBroker.getConnector(eventMapperConnection);
            assertTrue(connector instanceof IGCOMRSRepositoryEventMapper);
            igcomrsRepositoryEventMapper = (IGCOMRSRepositoryEventMapper) connector;
            igcomrsRepositoryEventMapper.setAuditLog(auditLog);
            igcomrsRepositoryEventMapper.setRepositoryEventProcessor(eventManager);
            igcomrsRepositoryEventMapper.initialize("Mock IGC Event Mapper", igcomrsRepositoryConnector);
            igcomrsRepositoryEventMapper.start();
        } catch (ConnectorCheckedException e) {
            log.info("As expected, could not fully start due to lack of Kafka.", e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to start event mapper!", e);
            assertNull(e);
        }

        repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        sourceName = igcomrsRepositoryConnector.getRepositoryName();
        igcRepositoryHelper = igcomrsMetadataCollection.getIgcRepositoryHelper();

    }

    /**
     * Initialize all of the open metadata types that the connector could support, before running any of the actual
     * tests.
     */
    @BeforeTest
    public void initAllOpenTypes() {

        OpenMetadataArchive archive = new OpenMetadataTypesArchive().getOpenMetadataArchive();
        OpenMetadataArchiveTypeStore typeStore = archive.getArchiveTypeStore();
        List<AttributeTypeDef> attributeTypeDefList = typeStore.getAttributeTypeDefs();
        List<TypeDef> typeDefList = typeStore.getNewTypeDefs();
        for (AttributeTypeDef attributeTypeDef : attributeTypeDefList) {
            boolean supported = false;
            try {
                igcomrsMetadataCollection.addAttributeTypeDef(MockConstants.EGERIA_USER, attributeTypeDef);
                supported = true;
            } catch (TypeDefNotSupportedException e) {
                log.debug("AttributeTypeDef is not supported -- skipping: {}", attributeTypeDef.getName());
            } catch (RepositoryErrorException e) {
                if (e.getReportedErrorMessage().startsWith("OMRS-IGC-REPOSITORY-400-006")) {
                    log.debug("AttributeTypeDef is supported: {}", attributeTypeDef.getName());
                    supported = true;
                }
            } catch (InvalidParameterException | TypeDefKnownException | TypeDefConflictException | InvalidTypeDefException e) {
                log.error("Unable to process the AttributeTypeDef: {}", attributeTypeDef.getName(), e);
                assertNull(e);
            } catch (Exception e) {
                log.error("Unexpected exception trying to setup attribute type definitions.", e);
                assertNull(e);
            }
            if (supported) {
                supportedAttributeTypeDefs.add(attributeTypeDef);
            }
            contentManager.addAttributeTypeDef(igcomrsRepositoryConnector.getRepositoryName(), attributeTypeDef);
        }
        for (TypeDef typeDef : typeDefList) {
            boolean supported = false;
            try {
                igcomrsMetadataCollection.addTypeDef(MockConstants.EGERIA_USER, typeDef);
                supported = true;
            } catch (TypeDefNotSupportedException e) {
                log.debug("TypeDef is not supported -- skipping: {}", typeDef.getName());
            } catch (RepositoryErrorException e) {
                if (e.getReportedErrorMessage().startsWith("OMRS-IGC-REPOSITORY-400-006")) {
                    log.debug("TypeDef is supported: {}", typeDef.getName());
                    supported = true;
                }
            } catch (InvalidParameterException | TypeDefKnownException | TypeDefConflictException | InvalidTypeDefException e) {
                log.error("Unable to process the TypeDef: {}", typeDef.getName(), e);
                assertNull(e);
            } catch (Exception e) {
                log.error("Unexpected exception trying to setup type definitions.", e);
                assertNull(e);
            }
            if (supported) {
                supportedTypeDefNames.add(typeDef.getName());
                supportedTypeDefs.put(typeDef.getName(), typeDef);
            }
            contentManager.addTypeDef(igcomrsRepositoryConnector.getRepositoryName(), typeDef);
        }
        List<TypeDefPatch> typeDefPatches = typeStore.getTypeDefPatches();
        if (typeDefPatches != null) {
            for (TypeDefPatch patch : typeDefPatches) {
                String typeDefName = patch.getTypeDefName();
                TypeDef typeDef = null;
                if (supportedTypeDefs.containsKey(typeDefName)) {
                    try {
                        typeDef = igcomrsMetadataCollection.updateTypeDef(MockConstants.EGERIA_USER, patch);
                    } catch (TypeDefNotKnownException e) {
                        log.debug("TypeDef is not supported -- skipping: {}", patch.getTypeDefName());
                    } catch (InvalidParameterException | PatchErrorException e) {
                        log.error("Unable to process the TypeDefPatch: {}", patch.getTypeDefName(), e);
                        assertNull(e);
                    } catch (Exception e) {
                        log.error("Unexpected exception trying to patch type definitions.", e);
                        assertNull(e);
                    }
                }
                if (typeDef != null) {
                    supportedTypeDefs.put(typeDef.getName(), typeDef);
                }
            }
        }
    }

    @Test
    public void verifySupportedTypes() {

        for (AttributeTypeDef attributeTypeDef : supportedAttributeTypeDefs) {
            try {
                assertTrue(igcomrsMetadataCollection.verifyAttributeTypeDef(MockConstants.EGERIA_USER, attributeTypeDef));
            } catch (InvalidParameterException | RepositoryErrorException | InvalidTypeDefException e) {
                log.error("Unable to verify attribute type definition: {}", attributeTypeDef.getName(), e);
                assertNull(e);
            } catch (Exception e) {
                log.error("Unexpected exception trying to verify attribute type definitions.", e);
                assertNull(e);
            }
        }

        for (String typeDefName : supportedTypeDefNames) {
            TypeDef typeDef = supportedTypeDefs.get(typeDefName);
            try {
                assertTrue(igcomrsMetadataCollection.verifyTypeDef(MockConstants.EGERIA_USER, typeDef));
            } catch (InvalidParameterException | RepositoryErrorException | InvalidTypeDefException | TypeDefNotSupportedException e) {
                log.error("Unable to verify type definition: {}", typeDefName, e);
                assertNull(e);
            } catch (Exception e) {
                log.error("Unexpected exception trying to verify type definitions.", e);
                assertNull(e);
            }
        }

        try {
            TypeDefGallery typeDefGallery = igcomrsMetadataCollection.getAllTypes(MockConstants.EGERIA_USER);
            assertNotNull(typeDefGallery);
            List<AttributeTypeDef> fromGalleryATD = typeDefGallery.getAttributeTypeDefs();
            List<TypeDef> fromGalleryTD = typeDefGallery.getTypeDefs();
            assertTrue(fromGalleryATD.containsAll(supportedAttributeTypeDefs));
            assertTrue(supportedAttributeTypeDefs.containsAll(fromGalleryATD));
            assertTrue(fromGalleryTD.containsAll(supportedTypeDefs.values()));
            assertTrue(supportedTypeDefs.values().containsAll(fromGalleryTD));
        } catch (RepositoryErrorException | InvalidParameterException e) {
            log.error("Unable to retrieve all types.", e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to retrieve all types.", e);
            assertNull(e);
        }

    }

    @Test
    public void testFindTypes() {

        List<TypeDef> entityTypeDefs = findTypeDefsByCategory(TypeDefCategory.ENTITY_DEF);
        applyAssertionsToTypeDefs(entityTypeDefs, TypeDefCategory.ENTITY_DEF);

        List<TypeDef> classificationTypeDefs = findTypeDefsByCategory(TypeDefCategory.CLASSIFICATION_DEF);
        applyAssertionsToTypeDefs(classificationTypeDefs, TypeDefCategory.CLASSIFICATION_DEF);

        List<TypeDef> relationshipTypeDefs = findTypeDefsByCategory(TypeDefCategory.RELATIONSHIP_DEF);
        applyAssertionsToTypeDefs(relationshipTypeDefs, TypeDefCategory.RELATIONSHIP_DEF);

        Map<String, Object> typeDefProperties = new HashMap<>();
        typeDefProperties.put("qualifiedName", null);
        TypeDefProperties matchProperties = new TypeDefProperties();
        matchProperties.setTypeDefProperties(typeDefProperties);

        try {
            List<TypeDef> searchResults = igcomrsMetadataCollection.searchForTypeDefs(MockConstants.EGERIA_USER, ".*a.*");
            assertNotNull(searchResults);
            assertFalse(searchResults.isEmpty());
            List<String> names = searchResults.stream().map(TypeDef::getName).collect(Collectors.toList());
            assertTrue(names.contains("RelationalTable"));
            assertTrue(names.contains("CategoryHierarchyLink"));
            assertTrue(names.contains("Confidentiality"));
            List<TypeDef> typeDefsByProperty = igcomrsMetadataCollection.findTypeDefsByProperty(MockConstants.EGERIA_USER, matchProperties);
            assertNotNull(typeDefsByProperty);
            names = typeDefsByProperty.stream().map(TypeDef::getName).collect(Collectors.toList());
            assertTrue(names.contains("Referenceable"));
            typeDefsByProperty = igcomrsMetadataCollection.findTypeDefsByProperty(MockConstants.EGERIA_USER, new TypeDefProperties());
            assertNotNull(typeDefsByProperty);
            assertTrue(names.contains("Referenceable"));
        } catch (InvalidParameterException | RepositoryErrorException e) {
            log.error("Unable to search for TypeDefs with contains string.", e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception searching for TypeDefs.", e);
            assertNull(e);
        }

    }

    private List<TypeDef> findTypeDefsByCategory(TypeDefCategory typeDefCategory) {
        List<TypeDef> typeDefs = null;
        try {
            typeDefs = igcomrsMetadataCollection.findTypeDefsByCategory(MockConstants.EGERIA_USER, typeDefCategory);
        } catch (InvalidParameterException | RepositoryErrorException e) {
            log.error("Unable to search for TypeDefs from category: {}", typeDefCategory.getName(), e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for TypeDefs by category.", e);
            assertNull(e);
        }
        return typeDefs;
    }

    private void applyAssertionsToTypeDefs(List<TypeDef> typeDefs, TypeDefCategory typeDefCategory) {
        assertNotNull(typeDefs);
        assertFalse(typeDefs.isEmpty());
        for (TypeDef typeDef : typeDefs) {
            assertEquals(typeDef.getCategory(), typeDefCategory);
        }
    }

    @Test
    public void testTypeDefRetrievals() {

        final String relationalTableGUID = "ce7e72b8-396a-4013-8688-f9d973067425";
        final String relationalTableName = "RelationalTable";

        final String unknownTypeGUID = "6b60a73e-47bc-4096-9073-f94cab975958";
        final String unknownTypeName = "DesignPattern";

        try {
            TypeDef byGUID = igcomrsMetadataCollection.getTypeDefByGUID(MockConstants.EGERIA_USER, relationalTableGUID);
            TypeDef byName = igcomrsMetadataCollection.getTypeDefByName(MockConstants.EGERIA_USER, relationalTableName);
            assertNotNull(byGUID);
            assertNotNull(byName);
            assertEquals(byGUID.getName(), relationalTableName);
            assertEquals(byName.getGUID(), relationalTableGUID);
            assertEquals(byGUID, byName);
            assertThrows(TypeDefNotKnownException.class, () -> igcomrsMetadataCollection.getTypeDefByGUID(MockConstants.EGERIA_USER, unknownTypeGUID));
            assertThrows(TypeDefNotKnownException.class, () -> igcomrsMetadataCollection.getTypeDefByName(MockConstants.EGERIA_USER, unknownTypeName));
        } catch (InvalidParameterException | RepositoryErrorException | TypeDefNotKnownException e) {
            log.error("Unable to retrieve type definition: {} / {}", relationalTableGUID, relationalTableName, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying retrieve type definition.", e);
            assertNull(e);
        }

    }

    @Test
    public void testAttributeTypeDefSearches() {

        try {
            List<AttributeTypeDef> primitives = igcomrsMetadataCollection.findAttributeTypeDefsByCategory(MockConstants.EGERIA_USER, AttributeTypeDefCategory.PRIMITIVE);
            assertNotNull(primitives);
            assertEquals(primitives.size(), 13);
            List<AttributeTypeDef> collections = igcomrsMetadataCollection.findAttributeTypeDefsByCategory(MockConstants.EGERIA_USER, AttributeTypeDefCategory.COLLECTION);
            assertNotNull(collections);
            assertEquals(collections.size(), 7);
            List<AttributeTypeDef> enums = igcomrsMetadataCollection.findAttributeTypeDefsByCategory(MockConstants.EGERIA_USER, AttributeTypeDefCategory.ENUM_DEF);
            assertNotNull(enums);
            assertEquals(enums.size(), 6);
        } catch (InvalidParameterException | RepositoryErrorException e) {
            log.error("Unable to search for attribute type defs by ENUM category.", e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for attribute type definitions.", e);
            assertNull(e);
        }

    }

    @Test
    public void testFindTypeDefsByExternalID() {

        try {
            assertThrows(InvalidParameterException.class, () -> igcomrsMetadataCollection.findTypesByExternalID(MockConstants.EGERIA_USER, null, null, null));
            List<TypeDef> noTypes = igcomrsMetadataCollection.findTypesByExternalID(MockConstants.EGERIA_USER, "some", "org", "id");
            assertNull(noTypes);
        } catch (InvalidParameterException | RepositoryErrorException e) {
            log.error("Unable to search for type defs by external ID.", e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for type defs by external ID.", e);
            assertNull(e);
        }

    }

    @Test
    public void testAttributeTypeDefRetrievals() {

        final String dataClassAssignmentGUID = "2611892f-0527-478f-8843-a3aa2b9abb47";
        final String dataClassAssignmentName = "DataClassAssignmentStatus";

        final String unknownTypeGUID = "ecb48ca2-4d29-4de9-99a1-bc4db9816d68";
        final String unknownTypeName = "DiscoveryRequestStatus";

        try {
            AttributeTypeDef byGUID = igcomrsMetadataCollection.getAttributeTypeDefByGUID(MockConstants.EGERIA_USER, dataClassAssignmentGUID);
            AttributeTypeDef byName = igcomrsMetadataCollection.getAttributeTypeDefByName(MockConstants.EGERIA_USER, dataClassAssignmentName);
            assertNotNull(byGUID);
            assertNotNull(byName);
            assertEquals(byGUID.getName(), dataClassAssignmentName);
            assertEquals(byName.getGUID(), dataClassAssignmentGUID);
            assertEquals(byGUID, byName);
            assertThrows(TypeDefNotKnownException.class, () -> igcomrsMetadataCollection.getAttributeTypeDefByGUID(MockConstants.EGERIA_USER, unknownTypeGUID));
            assertThrows(TypeDefNotKnownException.class, () -> igcomrsMetadataCollection.getAttributeTypeDefByName(MockConstants.EGERIA_USER, unknownTypeName));
        } catch (InvalidParameterException | RepositoryErrorException | TypeDefNotKnownException e) {
            log.error("Unable to retrieve attribute type definition: {} / {}", dataClassAssignmentGUID, dataClassAssignmentName, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying retrieve attribute type definition.", e);
            assertNull(e);
        }

    }

    @Test
    public void testAsOfTimeMethods() {

        String ignoredEntityTypeGUID = MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID;
        String ignoredRelationshipTypeGUID = "4df37335-7f0c-4ced-82df-3b2fd07be1bd";
        Date now = new Date();

        assertThrows(FunctionNotSupportedException.class, () -> igcomrsMetadataCollection.getRelationshipsForEntity(MockConstants.EGERIA_USER,
                ignoredEntityTypeGUID,
                ignoredRelationshipTypeGUID,
                0,
                null,
                now,
                null,
                null,
                MockConstants.EGERIA_PAGESIZE));

        assertThrows(FunctionNotSupportedException.class, () -> igcomrsMetadataCollection.findEntitiesByProperty(MockConstants.EGERIA_USER,
                ignoredEntityTypeGUID,
                null,
                null,
                0,
                null,
                null,
                now,
                null,
                null,
                MockConstants.EGERIA_PAGESIZE));

        assertThrows(FunctionNotSupportedException.class, () -> igcomrsMetadataCollection.findEntitiesByClassification(MockConstants.EGERIA_USER,
                ignoredEntityTypeGUID,
                "Confidentiality",
                null,
                null,
                0,
                null,
                now,
                null,
                null,
                MockConstants.EGERIA_PAGESIZE));

        assertThrows(FunctionNotSupportedException.class, () -> igcomrsMetadataCollection.findEntitiesByPropertyValue(MockConstants.EGERIA_USER,
                ignoredEntityTypeGUID,
                "ignore",
                0,
                null,
                null,
                now,
                null,
                null,
                MockConstants.EGERIA_PAGESIZE));

        assertThrows(FunctionNotSupportedException.class, () -> igcomrsMetadataCollection.findRelationshipsByProperty(MockConstants.EGERIA_USER,
                ignoredRelationshipTypeGUID,
                null,
                null,
                0,
                null,
                now,
                null,
                null,
                MockConstants.EGERIA_PAGESIZE));

        assertThrows(FunctionNotSupportedException.class, () -> igcomrsMetadataCollection.findRelationshipsByPropertyValue(MockConstants.EGERIA_USER,
                ignoredRelationshipTypeGUID,
                "ignore",
                0,
                null,
                now,
                null,
                null,
                MockConstants.EGERIA_PAGESIZE));

    }

    @Test
    public void testNegativeRetrievals() {

        assertThrows(EntityNotKnownException.class, () -> igcomrsMetadataCollection.getEntityDetail(MockConstants.EGERIA_USER, "123"));
        assertThrows(RelationshipNotKnownException.class, () -> igcomrsMetadataCollection.getRelationship(MockConstants.EGERIA_USER, "123"));

    }

    @Test
    public void testRelationshipGuids() {

        Reference endOne = new Reference("test", "term", "abc");
        Reference endTwo = new Reference("test", "term", "def");

        // Ensure that for mappings where there is no order, the guid is calculated consistently (eg. alphabetically)
        IGCRelationshipGuid guid = RelationshipMapping.getRelationshipGUID(igcRepositoryHelper,
                SynonymMapper.getInstance(null),
                endOne,
                endTwo,
                "synonyms",
                null);

        assertNotNull(guid);
        assertTrue(guid.toString().endsWith(":term@def<Synonym>term@abc"));

        guid = RelationshipMapping.getRelationshipGUID(igcRepositoryHelper,
                SynonymMapper.getInstance(null),
                endTwo,
                endOne,
                "synonyms",
                null);

        assertNotNull(guid);
        assertTrue(guid.toString().endsWith(":term@def<Synonym>term@abc"));

        // TODO: test other scenarios for calculating relationship GUIDs

        assertNull(RelationshipMapping.getProxyOneGuidFromRelationship(null, null));
        assertNull(RelationshipMapping.getProxyOneGuidFromRelationship(igcRepositoryHelper, null));
        assertNull(RelationshipMapping.getProxyTwoGuidFromRelationship(null, null));
        assertNull(RelationshipMapping.getProxyTwoGuidFromRelationship(igcRepositoryHelper, null));

    }

    @Test
    public void testIgcRepositoryHelper() {

        List<RelationshipMapping> relationshipMappings = igcRepositoryHelper.getAllRelationshipMappings();
        assertNotNull(relationshipMappings);
        assertFalse(relationshipMappings.isEmpty());
        Set<EntityMapping> entityMappings = igcRepositoryHelper.getEntityMappingsByPrefix(RelationalDBSchemaTypeMapper.IGC_RID_PREFIX);
        assertNotNull(entityMappings);
        assertFalse(entityMappings.isEmpty());
        ClassificationMapping classificationMapping = igcRepositoryHelper.getClassificationMappingByTypes("PrimaryKey", "database_column");
        assertNotNull(classificationMapping);
        assertEquals(classificationMapping.getOmrsClassificationType(), "PrimaryKey");
        assertEquals(classificationMapping.getIgcAssetType(), "database_column");
        List<EntityMapping> defaultMappings = igcRepositoryHelper.getMappers("unmapped_type");
        assertNotNull(defaultMappings);
        assertEquals(defaultMappings.size(), 1);
        assertEquals(defaultMappings.get(0).getOmrsTypeDefName(), "Referenceable");
        assertEquals(defaultMappings.get(0).getIgcAssetType(), "main_object");

    }

    @Test
    public void testAttributeValues() {

        final String methodName = "testAttributeValues";
        InstanceProperties ip = new InstanceProperties();

        PrimitiveDef primitiveDef = new PrimitiveDef();
        primitiveDef.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_LONG);
        TypeDefAttribute property = new TypeDefAttribute();
        property.setAttributeName("testAttribute");
        property.setAttributeType(primitiveDef);
        ip = AttributeMapping.addPrimitivePropertyToInstance(repositoryHelper,
                sourceName,
                ip,
                property,
                10L,
                methodName);
        assertEquals(ip.getPropertyCount(), 1);
        assertEquals(ip.getPropertyValue("testAttribute").valueAsObject(), 10L);

        primitiveDef = new PrimitiveDef();
        primitiveDef.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_FLOAT);
        property.setAttributeType(primitiveDef);
        ip = AttributeMapping.addPrimitivePropertyToInstance(repositoryHelper,
                sourceName,
                ip,
                property,
                10.1,
                methodName);
        assertEquals(ip.getPropertyCount(), 1);
        assertEquals(ip.getPropertyValue("testAttribute").valueAsString(), "10.1");

        try {
            assertNull(AttributeMapping.getIgcValueFromPropertyValue(null));
        } catch (PropertyErrorException e) {
            assertNull(e);
        }

        PrimitivePropertyValue ppv = new PrimitivePropertyValue();
        ppv.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_DATE);
        ppv.setPrimitiveValue(10L);
        try {
            String igcValue = AttributeMapping.getIgcValueFromPropertyValue(ppv);
            assertEquals(igcValue, "10");
        } catch (PropertyErrorException e) {
            assertNull(e);
        }

        EnumPropertyValue epv = new EnumPropertyValue();
        epv.setSymbolicName("TestName");
        try {
            String igcValue = AttributeMapping.getIgcValueFromPropertyValue(epv);
            assertEquals(igcValue, "TestName");
        } catch (PropertyErrorException e) {
            assertNull(e);
        }

        ArrayPropertyValue apv = new ArrayPropertyValue();
        apv.setArrayCount(2);
        apv.setArrayValue(0, ppv);
        apv.setArrayValue(1, epv);
        try {
            String igcValue = AttributeMapping.getIgcValueFromPropertyValue(apv);
            assertEquals(igcValue, "[ \"10\",\"TestName\" ]");
        } catch (PropertyErrorException e) {
            assertNull(e);
        }

        MapPropertyValue mpv = new MapPropertyValue();
        mpv.setMapValue("testPropertyName", ppv);
        assertThrows(PropertyErrorException.class, () -> AttributeMapping.getIgcValueFromPropertyValue(mpv));

    }

    @Test
    public void testSortingOfValues() {

        testComparator(false, true, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BOOLEAN);
        testComparator((byte)1, (byte)2, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BYTE);
        testComparator('a', 'b', PrimitiveDefCategory.OM_PRIMITIVE_TYPE_CHAR);
        testComparator((short)1, (short)2, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_SHORT);
        testComparator(3, 4, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT);
        testComparator(5L, 6L, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_LONG);
        testComparator((float)7.0, (float)7.1, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_FLOAT);
        testComparator(8.0, 8.1, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_DOUBLE);
        testComparator(BigInteger.valueOf(9), BigInteger.valueOf(10), PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BIGINTEGER);
        testComparator(BigDecimal.valueOf(11.0), BigDecimal.valueOf(11.1), PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BIGDECIMAL);
        long now = new Date().getTime();
        testComparator(now, now + 1, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_DATE);
        testComparator("String1", "String2", PrimitiveDefCategory.OM_PRIMITIVE_TYPE_STRING);

        EnumPropertyValue ev1 = new EnumPropertyValue();
        ev1.setOrdinal(0);
        ev1.setSymbolicName("Test0");
        ev1.setDescription("Test0");
        EnumPropertyValue ev2 = new EnumPropertyValue();
        ev2.setOrdinal(1);
        ev2.setSymbolicName("Test1");
        ev2.setDescription("Test1");
        assertTrue(AttributeMapping.compareInstanceProperty(ev1, ev2) < 0);
        assertTrue(AttributeMapping.compareInstanceProperty(ev2, ev1) > 0);
        assertEquals(AttributeMapping.compareInstanceProperty(ev1, ev1), 0);

    }

    private void testComparator(Object first, Object second, PrimitiveDefCategory category) {
        PrimitivePropertyValue one = new PrimitivePropertyValue();
        one.setPrimitiveDefCategory(category);
        one.setPrimitiveValue(first);
        PrimitivePropertyValue two = new PrimitivePropertyValue();
        two.setPrimitiveDefCategory(category);
        two.setPrimitiveValue(second);
        assertTrue(AttributeMapping.compareInstanceProperty(one, two) < 0);
        assertTrue(AttributeMapping.compareInstanceProperty(two, one) > 0);
        assertEquals(AttributeMapping.compareInstanceProperty(one, one), 0);
    }

    @Test
    public void testGlossaryFindByPropertyValue() {

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                "36f66863-9726-4b41-97ee-714fd0dc6fe4",
                "Glossary",
                repositoryHelper.getContainsRegex("a"),
                MockConstants.EGERIA_PAGESIZE,
                2
        );

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertTrue(qualifiedName.startsWith("gen!GL@(category)="));
        }

        results = testFindEntitiesByPropertyValue(
                "36f66863-9726-4b41-97ee-714fd0dc6fe4",
                "Glossary",
                repositoryHelper.getContainsRegex("a", true),
                MockConstants.EGERIA_PAGESIZE,
                2
        );

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertTrue(qualifiedName.startsWith("gen!GL@(category)="));
        }

    }

    @Test
    public void testGetGlossary() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Coco Pharmaceuticals");
        expectedValues.put("qualifiedName", MockConstants.GLOSSARY_QN);

        testEntityDetail(
                "category",
                "Glossary",
                GlossaryMapper.IGC_RID_PREFIX,
                MockConstants.GLOSSARY_RID,
                expectedValues
        );

    }

    @Test
    public void testGlossaryRelationships() {

        String expectedProxyOneQN = "gen!GL@(category)=Coco Pharmaceuticals";

        ObjectCache cache = new ObjectCache();

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 22,
                        "CategoryAnchor", "Glossary", "GlossaryCategory",
                        expectedProxyOneQN, null)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(22, 65,
                        "TermAnchor", "Glossary", "GlossaryTerm",
                        expectedProxyOneQN, null)
        );

        testRelationshipsForEntity(
                "category",
                "Glossary",
                GlossaryMapper.IGC_RID_PREFIX,
                MockConstants.GLOSSARY_RID,
                MockConstants.EGERIA_PAGESIZE,
                65,
                relationshipExpectations
        );

        relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 10,
                        "CategoryAnchor", "Glossary", "GlossaryCategory",
                        expectedProxyOneQN, null)
        );
        testRelationshipsForEntity(
                "category",
                "Glossary",
                GlossaryMapper.IGC_RID_PREFIX,
                MockConstants.GLOSSARY_RID,
                10,
                10,
                relationshipExpectations,
                SequencingOrder.GUID,
                null
        );

        Reference category = new Reference("TestCategory", "category", "123");
        Reference glossary = new Reference("TestGlossary", "category", "abc");
        Reference term = new Reference("TestTerm", "term", "def");
        List<Reference> ctx = new ArrayList<>();
        ctx.add(glossary);
        category.setContext(ctx);
        glossary.setContext(Collections.emptyList());
        try {
            CategoryAnchorMapper mapper = CategoryAnchorMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion());
            List<Reference> proxyOnes = mapper.getProxyOneAssetFromAsset(category, igcomrsRepositoryConnector.getIGCRestClient(), cache);
            assertEquals(proxyOnes.size(), 1);
            assertEquals(proxyOnes.get(0).getName(), "TestGlossary");
            assertTrue(mapper.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, cache, category, proxyOnes.get(0)));

            proxyOnes = mapper.getProxyOneAssetFromAsset(glossary, igcomrsRepositoryConnector.getIGCRestClient(), cache);
            assertEquals(proxyOnes.size(), 1);
            assertEquals(proxyOnes.get(0).getName(), "TestGlossary");
            assertTrue(mapper.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, cache, proxyOnes.get(0), category));

            proxyOnes = mapper.getProxyOneAssetFromAsset(term, igcomrsRepositoryConnector.getIGCRestClient(), cache);
            assertEquals(proxyOnes.size(), 1);
            assertEquals(proxyOnes.get(0).getName(), "TestTerm");
            assertFalse(mapper.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, cache, proxyOnes.get(0), glossary));
        } catch (Exception e) {
            log.error("Hit unexpected exception.", e);
            assertNull(e);
        }

    }

    @Test
    public void testCategoryFindByPropertyValue() {

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                "e507485b-9b5a-44c9-8a28-6967f7ff3672",
                "GlossaryCategory",
                repositoryHelper.getContainsRegex("e"),
                MockConstants.EGERIA_PAGESIZE,
                12
        );

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertTrue(qualifiedName.startsWith("(category)="));
        }

        results = testFindEntitiesByPropertyValue(
                "e507485b-9b5a-44c9-8a28-6967f7ff3672",
                "GlossaryCategory",
                repositoryHelper.getContainsRegex("e", true),
                MockConstants.EGERIA_PAGESIZE,
                12
        );

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertTrue(qualifiedName.startsWith("(category)="));
        }

    }

    @Test
    public void testGetGlossaryCategory() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Employee");
        expectedValues.put("qualifiedName", "(category)=Coco Pharmaceuticals::(category)=Person::(category)=Employee");

        testEntityDetail(
                "category",
                "GlossaryCategory",
                null,
                MockConstants.CATEGORY_RID,
                expectedValues
        );

    }

    @Test
    public void testGlossaryCategoryRelationships() {

        String expectedProxyTwoQN = "(category)=Coco Pharmaceuticals::(category)=Person::(category)=Employee";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "GlossaryCategory", "NoteLog",
                        expectedProxyTwoQN, "gen!NL@" + expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "CategoryAnchor", "Glossary", "GlossaryCategory",
                        "gen!GL@(category)=Coco Pharmaceuticals",
                        expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "CategoryHierarchyLink", "GlossaryCategory", "GlossaryCategory",
                        "(category)=Coco Pharmaceuticals::(category)=Person",
                        expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(3, 13,
                        "TermCategorization", "GlossaryCategory", "GlossaryTerm",
                        expectedProxyTwoQN,
                        null)
        );

        testRelationshipsForEntity(
                "category",
                "GlossaryCategory",
                null,
                MockConstants.CATEGORY_RID,
                MockConstants.EGERIA_PAGESIZE,
                13,
                relationshipExpectations
        );

    }

    @Test
    public void testGlossaryTermFindByPropertyValue() {

        testFindEntitiesByPropertyValue(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                repositoryHelper.getContainsRegex("Address"),
                MockConstants.EGERIA_PAGESIZE,
                6
        );

        testFindEntitiesByPropertyValue(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                repositoryHelper.getContainsRegex("address", true),
                MockConstants.EGERIA_PAGESIZE,
                6
        );

    }

    @Test
    public void testGlossaryTermFindByProperty_displayName() {

        final String methodName = "testGlossaryTermFindByProperty_displayName";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "displayName", repositoryHelper.getContainsRegex("Address"), methodName);
        testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                6
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "displayName", repositoryHelper.getContainsRegex("address", true), methodName);
        testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                6
        );

    }

    @Test
    public void testGlossaryTermFindByProperties_ANY() {

        final String methodName = "testGlossaryTermFindByProperties_ANY";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "displayName", repositoryHelper.getContainsRegex("Address"), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "summary", repositoryHelper.getContainsRegex("Number"), methodName);
        testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                ip,
                MatchCriteria.ANY,
                MockConstants.EGERIA_PAGESIZE,
                7
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "displayName", repositoryHelper.getContainsRegex("address", true), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "summary", repositoryHelper.getContainsRegex("number", true), methodName);
        testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                ip,
                MatchCriteria.ANY,
                MockConstants.EGERIA_PAGESIZE,
                7
        );

    }

    @Test
    public void testGlossaryTermFindByProperties_ALL() {

        final String methodName = "testGlossaryTermFindByProperties_ALL";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "displayName", repositoryHelper.getContainsRegex("Address"), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "summary", repositoryHelper.getContainsRegex("number"), methodName);
        List<EntityDetail> results = testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        EntityDetail single = results.get(0);
        testQualifiedNameEquality("(category)=Coco Pharmaceuticals::(term)=Address Line 1", single.getProperties().getPropertyValue("qualifiedName"));

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "displayName", repositoryHelper.getContainsRegex("address", true), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "summary", repositoryHelper.getContainsRegex("number", true), methodName);
        results = testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        single = results.get(0);
        testQualifiedNameEquality("(category)=Coco Pharmaceuticals::(term)=Address Line 1", single.getProperties().getPropertyValue("qualifiedName"));

    }

    @Test
    public void testAssetFindByPropertyValue() {

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add("Asset");
        possibleTypes.add("Database");
        possibleTypes.add("DataFile");

        testFindEntitiesByPropertyValue(
                "896d14c2-7522-4f6c-8519-757711943fe6",
                possibleTypes,
                null,
                repositoryHelper.getContainsRegex("COMPDIR"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        // Case-insensitively we should have 4 results (3 files with CompDir in their name)
        testFindEntitiesByPropertyValue(
                "896d14c2-7522-4f6c-8519-757711943fe6",
                possibleTypes,
                null,
                repositoryHelper.getContainsRegex("compdir", true),
                MockConstants.EGERIA_PAGESIZE,
                4
        );

    }

    @Test
    public void testAllTypesFindByPropertyValue() {

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add("GlossaryTerm");
        possibleTypes.add("DataClass");

        testFindEntitiesByPropertyValue(
                possibleTypes,
                repositoryHelper.getContainsRegex("Address"),
                MockConstants.EGERIA_PAGESIZE,
                13
        );

        // There should be one additional result for case-insensitive search, the "Uniform Resource Locator"
        // data class, which has "address" (all lower-case) in its description
        testFindEntitiesByPropertyValue(
                possibleTypes,
                repositoryHelper.getContainsRegex("address", true),
                MockConstants.EGERIA_PAGESIZE,
                14
        );

        testFindEntitiesByPropertyValue(
                possibleTypes,
                repositoryHelper.getContainsRegex("Address"),
                4,
                4
        );

        testFindEntitiesByPropertyValue(
                possibleTypes,
                repositoryHelper.getContainsRegex("address", true),
                4,
                4
        );

    }

    @Test
    public void testAllTypesFindByPropertyValue_limitToConfidentiality() {

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add("GlossaryTerm");

        Set<String> classifications = new HashSet<>();
        classifications.add("Confidentiality");

        testFindEntitiesByPropertyValue(
                possibleTypes,
                classifications,
                repositoryHelper.getContainsRegex("Address"),
                MockConstants.EGERIA_PAGESIZE,
                6
        );

        testFindEntitiesByPropertyValue(
                possibleTypes,
                classifications,
                repositoryHelper.getContainsRegex("address", true),
                MockConstants.EGERIA_PAGESIZE,
                6
        );

    }

    @Test
    public void testGlossaryTermFindByClassification() {

        final String methodName = "testGlossaryTermFindByClassification";

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "levelIdentifier", 3, methodName);

        testFindEntitiesByClassification(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                "Confidentiality",
                ip,
                MatchCriteria.ALL,
                12);

    }

    @Test
    public void testGetGlossaryTermWithNotes() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "TestTerm");
        expectedValues.put("qualifiedName", "(category)=Default Glossary::(term)=TestTerm");

        testEntityDetail(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.TERM_WITH_NOTES_RID,
                expectedValues
        );

    }

    @Test
    public void testGetGlossaryTermWithNotesRelationships() {

        String expectedProxyOneQN = "gen!GL@(category)=Default Glossary";
        String expectedProxyTwoQN = "(category)=Default Glossary::(term)=TestTerm";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME, "NoteLog",
                        expectedProxyTwoQN, "gen!NL@" + expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "TermAnchor", "Glossary", MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                        expectedProxyOneQN, expectedProxyTwoQN)
        );

        testRelationshipsForEntity(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.TERM_WITH_NOTES_RID,
                MockConstants.EGERIA_PAGESIZE,
                2,
                relationshipExpectations
        );

    }

    @Test
    public void testFindNoteLogs() {

        String methodName = "testFindNoteLogs";
        String exact = repositoryHelper.getExactMatchRegex("gen!NL@(category)=Default Glossary::(term)=TestTerm");
        String contains = repositoryHelper.getContainsRegex("e");
        String starts = repositoryHelper.getStartsWithRegex("gen!NL@(category)=Default");
        String ends = repositoryHelper.getEndsWithRegex("(term)=TestTerm");
        String typeGUID = "646727c7-9ad4-46fa-b660-265489ad96c6";
        String typeName = "NoteLog";

        // Note that we explicitly disable search for NoteLog, so these should all come back with no results

        testFindEntitiesByPropertyValue(typeGUID, typeName, exact, MockConstants.EGERIA_PAGESIZE, 0);
        testFindEntitiesByPropertyValue(typeGUID, typeName, contains, MockConstants.EGERIA_PAGESIZE, 0);
        testFindEntitiesByPropertyValue(typeGUID, typeName, starts, MockConstants.EGERIA_PAGESIZE, 0);
        testFindEntitiesByPropertyValue(typeGUID, typeName, ends, MockConstants.EGERIA_PAGESIZE, 0);

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", exact, methodName);
        testFindEntitiesByProperty(typeGUID, typeName, ip, MatchCriteria.ALL, MockConstants.EGERIA_PAGESIZE, 0);

        testFindEntitiesByProperty(
                "a32316b8-dc8c-48c5-b12b-71c1b2a080bf",
                "Referenceable",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                0
        );

        ip = new InstanceProperties();
        testFindEntitiesByProperty(typeGUID, typeName, ip, MatchCriteria.ALL, MockConstants.EGERIA_PAGESIZE, 0);

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", contains, methodName);
        testFindEntitiesByProperty(typeGUID, typeName, ip, MatchCriteria.ALL, MockConstants.EGERIA_PAGESIZE, 0);

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", starts, methodName);
        testFindEntitiesByProperty(typeGUID, typeName, ip, MatchCriteria.ALL, MockConstants.EGERIA_PAGESIZE, 0);

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", ends, methodName);
        testFindEntitiesByProperty(typeGUID, typeName, ip, MatchCriteria.ALL, MockConstants.EGERIA_PAGESIZE, 0);

        typeGUID = "4f798c0c-6769-4a2d-b489-d2714d89e0a4";
        typeName = "AttachedNoteLog";

        ip = new InstanceProperties();
        testFindRelationshipsByProperty(typeGUID, typeName, ip, MatchCriteria.ALL, MockConstants.EGERIA_PAGESIZE, 0);

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "name", repositoryHelper.getExactMatchRegex(""), methodName);
        testFindRelationshipsByProperty(typeGUID, typeName, ip, MatchCriteria.ALL, MockConstants.EGERIA_PAGESIZE, 0);

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "name", repositoryHelper.getExactMatchRegex(""), methodName);
        testFindRelationshipsByProperty(typeGUID, typeName, ip, MatchCriteria.NONE, MockConstants.EGERIA_PAGESIZE, 0);

        testFindRelationshipsByPropertyValue(typeGUID, typeName, exact, 0);
        testFindRelationshipsByPropertyValue(typeGUID, typeName, contains, 0);

    }

    @Test
    public void testGetGlossaryTermNoteLog() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", null);
        expectedValues.put("description", null);

        testEntityDetail(
                "term",
                "NoteLog",
                NoteLogMapper.IGC_RID_PREFIX,
                MockConstants.TERM_WITH_NOTES_RID,
                expectedValues
        );

    }

    @Test
    public void testGetGlossaryTermNoteLogRelationships() {

        String expectedProxyForNoteLog = "gen!NL@(category)=Default Glossary::(term)=TestTerm";
        String expectedProxyForAsset = "(category)=Default Glossary::(term)=TestTerm";

        ObjectCache cache = new ObjectCache();

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "GlossaryTerm", "NoteLog",
                        expectedProxyForAsset, expectedProxyForNoteLog)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 3,
                        "AttachedNoteLogEntry", "NoteLog", "NoteEntry",
                        expectedProxyForNoteLog, null)
        );

        testRelationshipsForEntity(
                "term",
                "NoteLog",
                NoteLogMapper.IGC_RID_PREFIX,
                MockConstants.TERM_WITH_NOTES_RID,
                MockConstants.EGERIA_PAGESIZE,
                3,
                relationshipExpectations
        );

        try {
            IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
            Term term = igcRestClient.getAssetWithSubsetOfProperties("6662c0f2.e1b1ec6c.00270n9bc.9a0o5ur.hsbem7.o7tuf0mn7hgv85dv4s707", "term", new String[]{"name"});
            Note note = igcRestClient.getAssetWithSubsetOfProperties("b1c497ce.1a21c74f.00270nb2s.3iaovvr.rbf470.568fcokgdnlg70p0q4cu7", "note", new String[]{"subject"});

            List<Relationship> relationships = new ArrayList<>();
            AttachedNoteLogEntryMapper mapper = AttachedNoteLogEntryMapper.getInstance(null);
            mapper.addMappedOMRSRelationships(igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    term,
                    null,
                    0,
                    null,
                    MockConstants.EGERIA_PAGESIZE,
                    MockConstants.EGERIA_USER);
            assertEquals(relationships.size(), 2);

            relationships = new ArrayList<>();
            mapper.addMappedOMRSRelationships(igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    term,
                    note,
                    0,
                    null,
                    MockConstants.EGERIA_PAGESIZE,
                    MockConstants.EGERIA_USER);
            assertEquals(relationships.size(), 1);

            relationships = new ArrayList<>();
            mapper.addMappedOMRSRelationships(igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    note,
                    null,
                    0,
                    null,
                    MockConstants.EGERIA_PAGESIZE,
                    MockConstants.EGERIA_USER);
            assertEquals(relationships.size(), 1);

            relationships = new ArrayList<>();
            mapper.addMappedOMRSRelationships(igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    note,
                    term,
                    0,
                    null,
                    MockConstants.EGERIA_PAGESIZE,
                    MockConstants.EGERIA_USER);
            assertEquals(relationships.size(), 1);
        } catch (IGCException | RepositoryErrorException e) {
            log.error("Hit unexpected exception testing term and note log relationships.", e);
            assertNull(e);
        }

    }

    @Test
    public void testGetNoteEntry() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("title", "This is the subject");
        expectedValues.put("text", "Just an information note here.");

        testEntityDetail(
                "note",
                "NoteEntry",
                null,
                MockConstants.NOTE_RID,
                expectedValues
        );

    }

    @Test
    public void testGetNoteEntryRelationships() {

        String expectedProxyForNoteLog = "gen!NL@(category)=Default Glossary::(term)=TestTerm";
        String expectedProxyForEntry = "(note)=" + MockConstants.NOTE_RID;

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLogEntry", "NoteLog", "NoteEntry",
                        expectedProxyForNoteLog, expectedProxyForEntry)
        );

        testRelationshipsForEntity(
                "note",
                "NoteEntry",
                null,
                MockConstants.NOTE_RID,
                MockConstants.EGERIA_PAGESIZE,
                1,
                relationshipExpectations
        );

    }

    @Test
    public void testGetGlossaryTermDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("summary", "Street and street number");
        expectedValues.put("displayName", "Address Line 1");
        expectedValues.put("qualifiedName", "(category)=Coco Pharmaceuticals::(term)=Address Line 1");

        EntityDetail detail = testEntityDetail(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.TERM_RID,
                expectedValues
        );

        testConfidentialityLevel3(detail.getClassifications());

    }

    @Test
    public void testGetGlossaryTermSummary() {

        EntitySummary summary = testEntitySummary(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.TERM_RID
        );

        testConfidentialityLevel3(summary.getClassifications());

    }

    private void testConfidentialityLevel3(List<Classification> classifications) {
        assertNotNull(classifications);
        assertFalse(classifications.isEmpty());
        Classification first = classifications.get(0);
        assertEquals(first.getType().getTypeDefName(), "Confidentiality");
        assertTrue(first.getVersion() > 1);
        int level = getIntegerValue(first.getProperties(), "levelIdentifier");
        assertEquals(level, 3);
    }

    @Test
    public void testGlossaryTermRelationships() {

        String expectedProxyOneQN = "gen!GL@(category)=Coco Pharmaceuticals";
        String expectedProxyTwoQN = "(category)=Coco Pharmaceuticals::(term)=Address Line 1";

        Set<String> proxyOneTypes = new HashSet<>();
        proxyOneTypes.add("RelationalColumn");
        proxyOneTypes.add("TabularFileColumn");

        Set<String> proxyTwoTypes = new HashSet<>();
        proxyTwoTypes.add(MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME);

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "GlossaryTerm", "NoteLog",
                        expectedProxyTwoQN, "gen!NL@" + expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 3,
                        "SemanticAssignment", proxyOneTypes, proxyTwoTypes,
                        null, expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(3, 4,
                        "TermAnchor", "Glossary", MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                        expectedProxyOneQN, expectedProxyTwoQN)
        );

        testRelationshipsForEntity(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.TERM_RID,
                MockConstants.EGERIA_PAGESIZE,
                4,
                relationshipExpectations
        );

    }

    @Test
    public void testGetDatabaseDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "COMPDIR");
        expectedValues.put("deployedImplementationType", "DB2");
        expectedValues.put("instance", "db2inst1");
        expectedValues.put("databaseVersion", "11.01.0202");

        testEntityDetail(
                "database",
                "Database",
                null,
                MockConstants.DATABASE_RID,
                expectedValues
        );

    }

    @Test
    public void testDatabaseRelationships() {

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "Database", "NoteLog",
                        MockConstants.DATABASE_QN, "gen!NL@" + MockConstants.DATABASE_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "DataContentForDataSet", "Database", "DeployedDatabaseSchema",
                        MockConstants.DATABASE_QN, MockConstants.DATABASE_SCHEMA_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "ConnectionToAsset", "Connection", "Database",
                        MockConstants.DATA_CONNECTION_QN, MockConstants.DATABASE_QN)
        );

        testRelationshipsForEntity(
                "database",
                "Database",
                null,
                MockConstants.DATABASE_RID,
                MockConstants.EGERIA_PAGESIZE,
                3,
                relationshipExpectations
        );

    }

    @Test
    public void testGetConnectionDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "CocoPharma_COMPDIR_ibm-db2");
        expectedValues.put("qualifiedName", MockConstants.DATA_CONNECTION_QN);

        testEntityDetail(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID,
                expectedValues
        );

    }

    @Test
    public void testConnectionRelationships() {

        String expectedConnectorTypeQN = MockConstants.HOST_QN + "::(connector)=DB2Connector";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "ConnectionToAsset", "Connection", "Database",
                        MockConstants.DATA_CONNECTION_QN, MockConstants.DATABASE_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "ConnectionConnectorType", "Connection", "ConnectorType",
                        MockConstants.DATA_CONNECTION_QN, expectedConnectorTypeQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "ConnectionEndpoint", "Endpoint", "Connection",
                        MockConstants.HOST_QN, MockConstants.DATA_CONNECTION_QN)
        );

        testRelationshipsForEntity(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID,
                MockConstants.EGERIA_PAGESIZE,
                3,
                relationshipExpectations
        );

    }

    @Test
    public void testGetEndpointDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("qualifiedName", MockConstants.HOST_QN);

        testEntityDetail(
                "host",
                "Endpoint",
                null,
                MockConstants.HOST_RID,
                expectedValues
        );

    }

    @Test
    public void testEndpointRelationships() {

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "Endpoint", "NoteLog",
                        MockConstants.HOST_QN, "gen!NL@" + MockConstants.HOST_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 8,
                        "ConnectionEndpoint", "Endpoint", "Connection",
                        MockConstants.HOST_QN, null)
        );

        ObjectCache cache = new ObjectCache();

        testRelationshipsForEntity(
                "host",
                "Endpoint",
                null,
                MockConstants.HOST_RID,
                MockConstants.EGERIA_PAGESIZE,
                8,
                relationshipExpectations
        );

        relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "Endpoint", "NoteLog",
                        MockConstants.HOST_QN, "gen!NL@" + MockConstants.HOST_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 5,
                        "ConnectionEndpoint", "Endpoint", "Connection",
                        MockConstants.HOST_QN, null)
        );
        testRelationshipsForEntity(
                "host",
                "Endpoint",
                null,
                MockConstants.HOST_RID,
                5,
                5,
                relationshipExpectations,
                SequencingOrder.GUID,
                null
        );

        try {
            Reference connector = new Reference("DB2Connector", "connector", "b1c497ce.54ec142d.001mtr38f.q8hjqk4.spumq8.k1bt587cologck6u9tf8q");
            Reference dataConnection = new Reference("IADB", "data_connection", "b1c497ce.8e4c0a48.001mtr3so.6k54588.marlmn.hvquoercv3ji5gdi2rslf");
            ConnectionEndpointMapper mapper = ConnectionEndpointMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion());
            List<Reference> proxyOnes = mapper.getProxyOneAssetFromAsset(connector, igcomrsRepositoryConnector.getIGCRestClient(), cache);
            assertEquals(proxyOnes.size(), 1);
            assertEquals(proxyOnes.get(0).getName(), "INFOSVR");

            proxyOnes = mapper.getProxyOneAssetFromAsset(dataConnection, igcomrsRepositoryConnector.getIGCRestClient(), cache);
            assertEquals(proxyOnes.size(), 1);
            assertEquals(proxyOnes.get(0).getName(), "IADB");

            List<Reference> proxyTwos = mapper.getProxyTwoAssetFromAsset(connector, igcomrsRepositoryConnector.getIGCRestClient(), cache);
            assertEquals(proxyTwos.size(), 6);

            proxyTwos = mapper.getProxyTwoAssetFromAsset(dataConnection, igcomrsRepositoryConnector.getIGCRestClient(), cache);
            assertEquals(proxyTwos.size(), 1);
            assertEquals(proxyTwos.get(0).getName(), "IADB");
        } catch (RepositoryErrorException e) {
            assertNull(e);
        }

    }

    @Test
    public void testGetDeployedDatabaseSchemaDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "DB2INST1");

        testEntityDetail(
                "database_schema",
                "DeployedDatabaseSchema",
                null,
                MockConstants.DATABASE_SCHEMA_RID,
                expectedValues
        );

    }

    @Test
    public void testDeployedDatabaseSchemaRelationships() {

        String expectedSchemaTypeQN = "gen!RDBST@" + MockConstants.DATABASE_SCHEMA_QN;

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "DeployedDatabaseSchema", "NoteLog",
                        MockConstants.DATABASE_SCHEMA_QN, "gen!NL@" + MockConstants.DATABASE_SCHEMA_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "AssetSchemaType", "DeployedDatabaseSchema", "RelationalDBSchemaType",
                        MockConstants.DATABASE_SCHEMA_QN, expectedSchemaTypeQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "DataContentForDataSet", "Database", "DeployedDatabaseSchema",
                        MockConstants.DATABASE_QN, MockConstants.DATABASE_SCHEMA_QN)
        );

        List<Relationship> results = testRelationshipsForEntity(
                "database_schema",
                "DeployedDatabaseSchema",
                null,
                MockConstants.DATABASE_SCHEMA_RID,
                MockConstants.EGERIA_PAGESIZE,
                3,
                relationshipExpectations
        );

        testRelationshipsAreRetrievable(results.subList(1, 2), "AssetSchemaType");

    }

    @Test
    public void testGetRelationalDBSchemaTypeDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "DB2INST1");

        testEntityDetail(
                "database_schema",
                "RelationalDBSchemaType",
                RelationalDBSchemaTypeMapper.IGC_RID_PREFIX,
                MockConstants.DATABASE_SCHEMA_RID,
                expectedValues
        );

    }

    @Test
    public void testRelationalDBSchemaTypeRelationships() {

        String expectedSchemaTypeQN = "gen!RDBST@" + MockConstants.DATABASE_SCHEMA_QN;

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AssetSchemaType", "DeployedDatabaseSchema", "RelationalDBSchemaType",
                        MockConstants.DATABASE_SCHEMA_QN, expectedSchemaTypeQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 4,
                        "AttributeForSchema", "RelationalDBSchemaType", "RelationalTable",
                        expectedSchemaTypeQN, null)
        );

        testRelationshipsForEntity(
                "database_schema",
                "DeployedDatabaseSchema",
                RelationalDBSchemaTypeMapper.IGC_RID_PREFIX,
                MockConstants.DATABASE_SCHEMA_RID,
                MockConstants.EGERIA_PAGESIZE,
                4,
                relationshipExpectations
        );

    }

    @Test
    public void testGetRelationalTableDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "CONTACTEMAIL");

        testEntityDetail(
                "database_table",
                "RelationalTable",
                null,
                MockConstants.DATABASE_TABLE_RID,
                expectedValues
        );

    }

    @Test
    public void testRelationalTableRelationships() {

        String expectedSchemaTypeQN = "gen!RDBST@" + MockConstants.DATABASE_SCHEMA_QN;

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "RelationalTable", "NoteLog",
                        MockConstants.DATABASE_TABLE_QN, "gen!NL@" + MockConstants.DATABASE_TABLE_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "AttributeForSchema", "RelationalDBSchemaType", "RelationalTable",
                        expectedSchemaTypeQN, MockConstants.DATABASE_TABLE_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 5,
                        "NestedSchemaAttribute", "RelationalTable", "RelationalColumn",
                        MockConstants.DATABASE_TABLE_QN, null)
        );

        testRelationshipsForEntity(
                "database_table",
                "RelationalTable",
                null,
                MockConstants.DATABASE_TABLE_RID,
                MockConstants.EGERIA_PAGESIZE,
                5,
                relationshipExpectations
        );

    }

    @Test
    public void testGetRelationalColumnDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "EMAIL");

        EntityDetail detail = testEntityDetail(
                "database_column",
                "RelationalColumn",
                null,
                MockConstants.DATABASE_COLUMN_RID,
                expectedValues
        );

        int length = getIntegerValue(detail.getProperties(), "length");
        assertEquals(length, 120);

        testColumnClassifications(detail.getClassifications());

    }

    private void testColumnClassifications(List<Classification> classifications) {
        assertNotNull(classifications);
        assertFalse(classifications.isEmpty());
        assertEquals(classifications.size(), 2);
        for (Classification classification : classifications) {
            String classificationType = classification.getType().getTypeDefName();
            if (classificationType.equalsIgnoreCase("TypeEmbeddedAttribute")) {
                String dataType = getStringValue(classification.getProperties(), "dataType");
                assertEquals(dataType, "STRING");
            } else if (classificationType.equalsIgnoreCase("Anchors")) {
                String anchorGUID = getStringValue(classification.getProperties(), "anchorGUID");
                assertNotNull(anchorGUID);
            } else {
                fail("Only TypeEmbeddedAttribute and Anchors classifications should be present");
            }
        }
    }

    @Test
    public void testRelationalColumnRelationships() {

        String expectedTermQN = "(category)=Coco Pharmaceuticals::(term)=Email Address";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "RelationalColumn", "NoteLog",
                        MockConstants.DATABASE_COLUMN_QN, "gen!NL@" + MockConstants.DATABASE_COLUMN_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "SemanticAssignment", "RelationalColumn", "GlossaryTerm",
                        MockConstants.DATABASE_COLUMN_QN, expectedTermQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 6,
                        "DataClassAssignment", "RelationalColumn", "DataClass",
                        MockConstants.DATABASE_COLUMN_QN, null)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(6, 7,
                        "NestedSchemaAttribute", "RelationalTable", "RelationalColumn",
                        MockConstants.DATABASE_TABLE_QN, MockConstants.DATABASE_COLUMN_QN)
        );

        List<Relationship> relationships = testRelationshipsForEntity(
                "database_column",
                "RelationalColumn",
                null,
                MockConstants.DATABASE_COLUMN_RID,
                MockConstants.EGERIA_PAGESIZE,
                7,
                relationshipExpectations
        );

        testDiscoveredDataClasses(relationships, 2, 5);
        testProposedDataClasses(relationships, 5, 6);

    }

    private void testDiscoveredDataClasses(List<Relationship> relationships, int startIndex, int finishIndex) {
        for (int i = startIndex; i < finishIndex; i++) {
            Relationship assignment = relationships.get(i);
            InstanceProperties properties = assignment.getProperties();
            EnumPropertyValue status = getEnumValue(properties, "status");
            assertEquals(status.getOrdinal(), 0);
            assertEquals(status.getSymbolicName(), "Discovered");
            int confidence = getIntegerValue(properties, "confidence");
            assertEquals(confidence, 100);
        }
    }

    private void testProposedDataClasses(List<Relationship> relationships, int startIndex, int finishIndex) {
        for (int i = startIndex; i < finishIndex; i++) {
            Relationship assignment = relationships.get(i);
            EnumPropertyValue status = getEnumValue(assignment.getProperties(), "status");
            assertEquals(status.getOrdinal(), 1);
            assertEquals(status.getSymbolicName(), "Proposed");
        }
    }

    @Test
    public void testGetRelationalColumnSummary() {

        testEntitySummary(
                "database_column",
                "RelationalColumn",
                null,
                MockConstants.DATABASE_COLUMN_RID
        );

    }

    @Test
    public void testGetDataClassDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "Email Address");
        expectedValues.put("example", "paul_fowler@aol.com");
        expectedValues.put("qualifiedName", MockConstants.DATA_CLASS_QN);

        testEntityDetail(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                expectedValues
        );

    }

    @Test
    public void testDataClassRelationships() {

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "DataClass", "NoteLog",
                        MockConstants.DATA_CLASS_QN, "gen!NL@" + MockConstants.DATA_CLASS_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 4,
                        "DataClassAssignment", "RelationalColumn", "DataClass",
                        MockConstants.DATABASE_COLUMN_QN, MockConstants.DATA_CLASS_QN)
        );

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                MockConstants.EGERIA_PAGESIZE,
                4,
                relationshipExpectations
        );
        testDiscoveredDataClasses(relationships, 1, 3);
        testProposedDataClasses(relationships, 3, 4);

        relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 2,
                        "DataClassAssignment", "RelationalColumn", "DataClass",
                        MockConstants.DATABASE_COLUMN_QN, MockConstants.DATA_CLASS_QN)
        );
        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.GUID,
                null
        );
        testDiscoveredDataClasses(relationships, 0, 2);

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.LAST_UPDATE_RECENT,
                null
        );
        testDiscoveredDataClasses(relationships, 0, 2);

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.CREATION_DATE_RECENT,
                null
        );
        testDiscoveredDataClasses(relationships, 0, 2);

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.PROPERTY_DESCENDING,
                "confidence"
        );
        testDiscoveredDataClasses(relationships, 0, 2);

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.PROPERTY_DESCENDING,
                "status"
        );
        testProposedDataClasses(relationships, 0, 1);
        testDiscoveredDataClasses(relationships, 1, 2);

        relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "DataClass", "NoteLog",
                        MockConstants.DATA_CLASS_QN, "gen!NL@" + MockConstants.DATA_CLASS_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "DataClassAssignment", "RelationalColumn", "DataClass",
                        MockConstants.DATABASE_COLUMN_QN, MockConstants.DATA_CLASS_QN)
        );
        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.LAST_UPDATE_OLDEST,
                null
        );
        testDiscoveredDataClasses(relationships, 1, 2);

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.CREATION_DATE_OLDEST,
                null
        );
        testDiscoveredDataClasses(relationships, 1, 2);

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.PROPERTY_ASCENDING,
                "status"
        );
        testDiscoveredDataClasses(relationships, 1, 2);

        relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "DataClass", "NoteLog",
                        MockConstants.DATA_CLASS_QN, "gen!NL@" + MockConstants.DATA_CLASS_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "DataClassAssignment", "RelationalColumn", "DataClass",
                        MockConstants.DATABASE_COLUMN_QN, MockConstants.DATA_CLASS_QN)
        );

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.PROPERTY_ASCENDING,
                "confidence"
        );
        testProposedDataClasses(relationships, 1, 2);

        relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                2,
                2,
                relationshipExpectations,
                SequencingOrder.PROPERTY_ASCENDING,
                "partialMatch"
        );
        testProposedDataClasses(relationships, 1, 2);

    }

    @Test
    public void testGetConnectionFSDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "LOCALFS");
        expectedValues.put("qualifiedName", MockConstants.DATA_CONNECTION_QN_FS);

        testEntityDetail(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID_FS,
                expectedValues
        );

    }

    @Test
    public void testConnectionFSRelationships() {

        String expectedFolderQN = MockConstants.HOST_QN + "::(data_file_folder)=/";
        String expectedConnectorTypeQN = MockConstants.HOST_QN + "::(connector)=LocalFileConnector";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "ConnectionToAsset", "Connection", "FileFolder",
                        MockConstants.DATA_CONNECTION_QN_FS, expectedFolderQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "ConnectionConnectorType", "Connection", "ConnectorType",
                        MockConstants.DATA_CONNECTION_QN_FS, expectedConnectorTypeQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "ConnectionEndpoint", "Endpoint", "Connection",
                        MockConstants.HOST_QN, MockConstants.DATA_CONNECTION_QN_FS)
        );

        testRelationshipsForEntity(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID_FS,
                MockConstants.EGERIA_PAGESIZE,
                3,
                relationshipExpectations
        );

    }

    @Test
    public void testDataFileFolderRelationships() {

        String expectedParentFolderQN = MockConstants.HOST_QN + "::(data_file_folder)=/::(data_file_folder)=data::(data_file_folder)=files";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "FileFolder", "NoteLog",
                        MockConstants.DATA_FILE_FOLDER_QN, "gen!NL@" + MockConstants.DATA_FILE_FOLDER_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "FolderHierarchy", "FileFolder", "FileFolder",
                        expectedParentFolderQN, MockConstants.DATA_FILE_FOLDER_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 10,
                        "NestedFile", "FileFolder", "DataFile",
                        MockConstants.DATA_FILE_FOLDER_QN, null)
        );

        testRelationshipsForEntity(
                "data_file_folder",
                "FileFolder",
                null,
                MockConstants.DATA_FILE_FOLDER_RID,
                MockConstants.EGERIA_PAGESIZE,
                10,
                relationshipExpectations
        );

    }

    @Test
    public void testGetDataFileDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("qualifiedName", MockConstants.DATA_FILE_QN);

        testEntityDetail(
                "data_file",
                "DataFile",
                null,
                MockConstants.DATA_FILE_RID,
                expectedValues
        );

    }

    @Test
    public void testDataFileRelationships() {

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "DataFile", "NoteLog",
                        MockConstants.DATA_FILE_QN, "gen!NL@" + MockConstants.DATA_FILE_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "AssetSchemaType", "DataFile", "TabularSchemaType",
                        MockConstants.DATA_FILE_QN, MockConstants.DATA_FILE_RECORD_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "NestedFile", "FileFolder", "DataFile",
                        MockConstants.DATA_FILE_FOLDER_QN, MockConstants.DATA_FILE_QN)
        );

        testRelationshipsForEntity(
                "data_file",
                "DataFile",
                null,
                MockConstants.DATA_FILE_RID,
                MockConstants.EGERIA_PAGESIZE,
                3,
                relationshipExpectations
        );

    }

    @Test
    public void testGetTabularSchemaTypeDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "CompDir-ContactEmail");
        expectedValues.put("qualifiedName", MockConstants.DATA_FILE_RECORD_QN);

        testEntityDetail(
                "data_file_record",
                "TabularSchemaType",
                null,
                MockConstants.DATA_FILE_RECORD_RID,
                expectedValues
        );

    }

    @Test
    public void testTabularSchemaTypeRelationships() {

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "TabularSchemaType", "NoteLog",
                        MockConstants.DATA_FILE_RECORD_QN, "gen!NL@" + MockConstants.DATA_FILE_RECORD_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "AssetSchemaType", "DataFile", "TabularSchemaType",
                        MockConstants.DATA_FILE_QN, MockConstants.DATA_FILE_RECORD_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 5,
                        "AttributeForSchema", "TabularSchemaType", "TabularFileColumn",
                        MockConstants.DATA_FILE_RECORD_QN, null)
        );

        testRelationshipsForEntity(
                "data_file_record",
                "TabularSchemaType",
                null,
                MockConstants.DATA_FILE_RECORD_RID,
                MockConstants.EGERIA_PAGESIZE,
                5,
                relationshipExpectations
        );

    }

    @Test
    public void testGetTabularColumnDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Email");

        EntityDetail detail = testEntityDetail(
                "data_file_field",
                "TabularFileColumn",
                null,
                MockConstants.DATA_FILE_FIELD_RID,
                expectedValues
        );

        testColumnClassifications(detail.getClassifications());

    }

    @Test
    public void testTabularColumnRelationships() {

        String expectedTermQN = "(category)=Coco Pharmaceuticals::(term)=Email Address";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "TabularFileColumn", "NoteLog",
                        MockConstants.DATA_FILE_FIELD_QN, "gen!NL@" + MockConstants.DATA_FILE_FIELD_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "SemanticAssignment", "TabularFileColumn", "GlossaryTerm",
                        MockConstants.DATA_FILE_FIELD_QN, expectedTermQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "AttributeForSchema", "TabularSchemaType", "TabularFileColumn",
                        MockConstants.DATA_FILE_RECORD_QN, MockConstants.DATA_FILE_FIELD_QN)
        );

        testRelationshipsForEntity(
                "data_file_field",
                "TabularFileColumn",
                null,
                MockConstants.DATA_FILE_FIELD_RID,
                MockConstants.EGERIA_PAGESIZE,
                3,
                relationshipExpectations
        );

    }

    @Test
    public void testGetSpineObjectDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Employee");
        expectedValues.put("qualifiedName", MockConstants.SPINE_OBJECT_QN);

        EntityDetail detail = testEntityDetail(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.SPINE_OBJECT_RID,
                expectedValues
        );

        List<Classification> classifications = detail.getClassifications();
        assertNotNull(classifications);
        assertFalse(classifications.isEmpty());
        assertEquals(classifications.size(), 3);
        Classification first = classifications.get(0);
        assertEquals(first.getType().getTypeDefName(), "SpineObject");
        assertTrue(first.getVersion() > 1);

    }

    @Test
    public void testGetSubjectAreaDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Organization");
        expectedValues.put("qualifiedName", MockConstants.SUBJECT_AREA_QN);

        EntityDetail detail = testEntityDetail(
                "category",
                "GlossaryCategory",
                null,
                MockConstants.SUBJECT_AREA_RID,
                expectedValues
        );

        List<Classification> classifications = detail.getClassifications();
        assertNotNull(classifications);
        assertFalse(classifications.isEmpty());
        assertEquals(classifications.size(), 2);
        Classification first = classifications.get(0);
        assertEquals(first.getType().getTypeDefName(), "SubjectArea");
        assertTrue(first.getVersion() > 1);

    }

    @Test
    public void testGetGovernancePolicyDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("title", "Confidential information should be masked when user does not have specific access to its Subject Area");
        expectedValues.put("qualifiedName", MockConstants.INFORMATION_GOVERNANCE_POLICY_QN);

        testEntityDetail(
                "information_governance_policy",
                "GovernancePolicy",
                null,
                MockConstants.INFORMATION_GOVERNANCE_POLICY_RID,
                expectedValues
        );

    }

    @Test
    public void testGetPersonDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "ggeeke");
        expectedValues.put("fullName", "Gary Geeke");
        expectedValues.put("jobTitle", "IT Infrastructure Administrator");
        expectedValues.put("qualifiedName", MockConstants.USER_QN);

        testEntityDetail(
                "user",
                "Person",
                null,
                MockConstants.USER_RID,
                expectedValues
        );

    }

    @Test
    public void testPersonRelationships() {

        String expectedContactDetailsQN = "gen!CD@" + MockConstants.USER_QN;

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "AttachedNoteLog", "Person", "NoteLog",
                        MockConstants.USER_QN, "gen!NL@" + MockConstants.USER_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "ContactThrough", "Person", "ContactDetails",
                        MockConstants.USER_QN, expectedContactDetailsQN)
        );

        testRelationshipsForEntity(
                "user",
                "Person",
                null,
                MockConstants.USER_RID,
                MockConstants.EGERIA_PAGESIZE,
                2,
                relationshipExpectations
        );

    }

    @Test
    public void testFindActorProfile() {

        final String methodName = "testFindActorProfile";

        InstanceProperties ip = repositoryHelper.addStringPropertyToInstance(sourceName, null, "name", repositoryHelper.getExactMatchRegex("cocopharma_external"), methodName);

        testFindEntitiesByProperty(
                "5a2f38dc-d69d-4a6f-ad26-ac86f118fa35",
                "Team",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByProperty(
                "36db26d5-aba2-439b-bc15-d62d373c5db6",
                "Team",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "name", repositoryHelper.getExactMatchRegex("cquartile"), methodName);
        testFindEntitiesByProperty(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

    }

    @Test
    public void testGetContactDetailsDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("contactMethodValue", "gary.geeke@cocopharmaceutical.com");

        EntityDetail detail = testEntityDetail(
                "user",
                "ContactDetails",
                ContactDetailsMapper.IGC_RID_PREFIX,
                MockConstants.USER_RID,
                expectedValues
        );

        EnumPropertyValue contactMethodEnum = getEnumValue(detail.getProperties(), "contactMethodType");
        assertEquals(contactMethodEnum.getOrdinal(), 0);
        assertEquals(contactMethodEnum.getSymbolicName(), "Email");

    }

    @Test
    public void testGetTeamDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "cocopharma_it_ops");
        expectedValues.put("description", "IT Operations");

        testEntityDetail(
                "group",
                "Team",
                null,
                MockConstants.GROUP_RID,
                expectedValues
        );

    }

    @Test
    public void testForeignKeyFindByPropertyValue() {

        final String methodName = "testForeignKeyFindByPropertyValue";

        String relationshipType = "3cd4e0e7-fdbf-47a6-ae88-d4b3205e0c07";
        String typeName = "ForeignKey";

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "confidence", 100, methodName);

        testFindRelationshipsByProperty(
                relationshipType,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                3);

        testFindRelationshipsByProperty(
                relationshipType,
                typeName,
                ip,
                MatchCriteria.ALL,
                2,
                2);

    }

    @Test
    public void testSemanticAssignmentFindByProperty() {

        final String methodName = "testSemanticAssignmentFindByProperty";

        String relationshipType = "e6670973-645f-441a-bec7-6f5570345b92";
        String typeName = "SemanticAssignment";

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "confidence", 100, methodName);

        testFindRelationshipsByProperty(
                relationshipType,
                typeName,
                ip,
                MatchCriteria.ALL,
                2,
                2);

        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "confidence", 10, methodName);
        testFindRelationshipsByProperty(
                relationshipType,
                typeName,
                ip,
                MatchCriteria.ALL,
                2,
                0);

    }

    @Test
    public void testFindEntitiesByQualifiedName() {

        final String methodName = "testFindEntitiesByQualifiedName";

        String typeName = "RelationalColumn";
        String typeGUID = "aa8d5470-6dbc-4648-9e2f-045e5df9d2f9";

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add(typeName);

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                possibleTypes,
                repositoryHelper.getExactMatchRegex(MockConstants.DATABASE_COLUMN_QN),
                MockConstants.EGERIA_PAGESIZE,
                1);

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertEquals(qualifiedName, MockConstants.DATABASE_COLUMN_QN);
        }

        results = testFindEntitiesByPropertyValue(
                possibleTypes,
                repositoryHelper.getExactMatchRegex(MockConstants.DATABASE_COLUMN_QN.toLowerCase(), true),
                MockConstants.EGERIA_PAGESIZE,
                1);

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertEquals(qualifiedName, MockConstants.DATABASE_COLUMN_QN);
        }

        InstanceProperties ip = new InstanceProperties();
        InstanceProperties ci = new InstanceProperties();
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", repositoryHelper.getContainsRegex("ADDR"), methodName);
        ci = repositoryHelper.addStringPropertyToInstance(sourceName, ci, "qualifiedName", repositoryHelper.getContainsRegex("addr", true), methodName);

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                12
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                12
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.NONE,
                MockConstants.EGERIA_PAGESIZE,
                66
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.NONE,
                MockConstants.EGERIA_PAGESIZE,
                66
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.NONE,
                10,
                10
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.NONE,
                10,
                10
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", repositoryHelper.getStartsWithRegex(MockConstants.DATABASE_COLUMN_QN.substring(0, 20)), methodName);
        ci = repositoryHelper.addStringPropertyToInstance(sourceName, ci, "qualifiedName", repositoryHelper.getStartsWithRegex(MockConstants.DATABASE_COLUMN_QN.substring(0, 20).toLowerCase(), true), methodName);

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ANY,
                MockConstants.EGERIA_PAGESIZE,
                78
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.ANY,
                MockConstants.EGERIA_PAGESIZE,
                78
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ANY,
                10,
                10
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.ANY,
                10,
                10
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", repositoryHelper.getEndsWithRegex(MockConstants.DATABASE_COLUMN_QN.substring(20)), methodName);
        ci = repositoryHelper.addStringPropertyToInstance(sourceName, ci, "qualifiedName", repositoryHelper.getEndsWithRegex(MockConstants.DATABASE_COLUMN_QN.substring(20).toLowerCase(), true), methodName);

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getExactMatchRegex("(user)=Mr. Gary Geeke"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getExactMatchRegex("(user)=mr. gary geeke", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getExactMatchRegex("Gary Geeke"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getExactMatchRegex("gary geeke", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("user)=Mr. Gary Geeke"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("user)=mr. gary geeke", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "79296df8-645a-4ef7-a011-912d1cdcf75a",
                "ContactDetails",
                repositoryHelper.getEndsWithRegex("r. Gary Geeke"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("Gary Geeke"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("gary geeke", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("Geeke"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("geeke", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "79296df8-645a-4ef7-a011-912d1cdcf75a",
                "ContactDetails",
                repositoryHelper.getEndsWithRegex("dministrator IIS"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "229ed5cc-de31-45fc-beb4-9919fd247398",
                "FileFolder",
                repositoryHelper.getExactMatchRegex("(host)=INFOSVR::(data_file_folder)=/::(data_file_folder)=data::(data_file_folder)=files"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "229ed5cc-de31-45fc-beb4-9919fd247398",
                "FileFolder",
                repositoryHelper.getExactMatchRegex("(host)=infosvr::(data_file_folder)=/::(data_file_folder)=data::(data_file_folder)=files", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "248975ec-8019-4b8a-9caf-084c8b724233",
                "TabularSchemaType",
                repositoryHelper.getEndsWithRegex("::(data_file_folder)=CocoPharma::(data_file)=Employee-Dept.csv::(data_file_record)=Employee-Dept"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                "248975ec-8019-4b8a-9caf-084c8b724233",
                "TabularSchemaType",
                repositoryHelper.getEndsWithRegex("::(data_file_folder)=cocopharma::(data_file)=employee-dept.csv::(data_file_record)=employee-dept", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        Set<String> hostedTypes = new HashSet<>();
        hostedTypes.add("TabularColumn");
        hostedTypes.add("TabularSchemaType");
        hostedTypes.add("DataFile");
        hostedTypes.add("FileFolder");
        hostedTypes.add("Endpoint");
        hostedTypes.add("RelationalColumn");
        hostedTypes.add("RelationalTable");
        hostedTypes.add("DeployedDatabaseSchema");
        hostedTypes.add("Database");

        testFindEntitiesByPropertyValue(
                null,
                hostedTypes,
                null,
                repositoryHelper.getStartsWithRegex("(host)=INFO"),
                5,
                5
        );

        typeName = "Glossary";
        typeGUID = "36f66863-9726-4b41-97ee-714fd0dc6fe4";

        possibleTypes = new HashSet<>();
        possibleTypes.add(typeName);

        InstanceProperties qn = repositoryHelper.addStringPropertyToInstance(sourceName, null, "qualifiedName", "gen!GL@(category)=Default Glossary", methodName);
        SearchProperties searchProperties = new SearchProperties();
        searchProperties.setMatchCriteria(MatchCriteria.ALL);

        List<PropertyCondition> outer = new ArrayList<>();
        PropertyCondition one = new PropertyCondition();
        one.setProperty("qualifiedName");
        one.setOperator(PropertyComparisonOperator.EQ);
        one.setValue(qn.getPropertyValue("qualifiedName"));
        outer.add(one);

        InstanceProperties invalid = repositoryHelper.addStringPropertyToInstance(sourceName, null, "qualifiedName", "InvalidValue", methodName);
        PropertyCondition two = new PropertyCondition();
        two.setProperty("qualifiedName");
        two.setOperator(PropertyComparisonOperator.NEQ);
        two.setValue(invalid.getPropertyValue("qualifiedName"));
        outer.add(two);

        SearchProperties nestedProperties = new SearchProperties();
        nestedProperties.setMatchCriteria(MatchCriteria.ANY);

        List<PropertyCondition> inner = new ArrayList<>();
        PropertyCondition three = new PropertyCondition();
        three.setProperty("qualifiedName");
        three.setOperator(PropertyComparisonOperator.EQ);
        three.setValue(qn.getPropertyValue("qualifiedName"));
        inner.add(three);

        PropertyCondition four = new PropertyCondition();
        four.setProperty("qualifiedName");
        four.setOperator(PropertyComparisonOperator.NEQ);
        four.setValue(qn.getPropertyValue("qualifiedName"));
        inner.add(four);

        nestedProperties.setConditions(inner);

        PropertyCondition five = new PropertyCondition();
        five.setNestedConditions(nestedProperties);
        outer.add(five);

        searchProperties.setConditions(outer);

        testFindEntities(
                typeGUID,
                typeName,
                null,
                searchProperties,
                5,
                1,
                null,
                null
        );

    }

    @Test
    public void testFindDataClassByProperty() {

        final String methodName = "testFindDataClassByProperty";

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "dataType", repositoryHelper.getExactMatchRegex("string"), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "specificationDetails", repositoryHelper.getExactMatchRegex("com.ibm.infosphere.classification.impl.EmailClassifier"), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "specification", repositoryHelper.getExactMatchRegex("Java"), methodName);
        ip = repositoryHelper.addBooleanPropertyToInstance(sourceName, ip, "userDefined", false, methodName);

        testFindEntitiesByProperty(
                "6bc727dc-e855-4979-8736-78ac3cfcd32f",
                "DataClass",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "dataType", repositoryHelper.getExactMatchRegex("String", true), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "specificationDetails", repositoryHelper.getExactMatchRegex("com.ibm.infosphere.classification.impl.emailclassifier", true), methodName);
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "specification", repositoryHelper.getExactMatchRegex("java", true), methodName);
        ip = repositoryHelper.addBooleanPropertyToInstance(sourceName, ip, "userDefined", false, methodName);

        testFindEntitiesByProperty(
                "6bc727dc-e855-4979-8736-78ac3cfcd32f",
                "DataClass",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

    }

    @Test
    public void testFindDataClassAssignmentByProperty() {

        final String methodName = "testFindDataClassAssignmentByProperty";

        String typeGUID = "4df37335-7f0c-4ced-82df-3b2fd07be1bd";
        String typeName = "DataClassAssignment";

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addEnumPropertyToInstance(sourceName, ip, "status", 0, "Discovered", "The data class assignment was discovered by an automated process.", methodName);
        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "confidence", 100, methodName);
        ip = repositoryHelper.addBooleanPropertyToInstance(sourceName, ip, "partialMatch", false, methodName);
        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "valueFrequency", 34, methodName);

        testFindRelationshipsByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                3);

        testFindRelationshipsByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                1,
                1);

        // Try searching by the symbolic name of an enumeration, should not return any results because it is not a
        // string property
        testFindRelationshipsByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getExactMatchRegex("Proposed"),
                0
        );

        testFindRelationshipsByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getExactMatchRegex("proposed", true),
                0
        );

        // Try searching based on a partialMatch of true
        ip = new InstanceProperties();
        ip = repositoryHelper.addEnumPropertyToInstance(sourceName, ip, "status", 0, "Discovered", "The data class assignment was discovered by an automated process.", methodName);
        ip = repositoryHelper.addBooleanPropertyToInstance(sourceName, ip, "partialMatch", true, methodName);
        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "valueFrequency", 28, methodName);
        testFindRelationshipsByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

    }

    @Test
    public void testFindAllAttributeForSchemas() {

        String typeGUID = "86b176a2-015c-44a6-8106-54d5d69ba661";
        String typeName = "AttributeForSchema";

        testFindRelationshipsByProperty(
                typeGUID,
                typeName,
                null,
                null,
                2,
                2);

    }

    @Test
    public void testFindGovernanceDefinitionByDomain() {

        final String methodName = "testFindGovernanceDefinitionByDomain";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "domain", repositoryHelper.getContainsRegex("Data Access"), methodName);
        testFindEntitiesByProperty(
                "578a3500-9ad3-45fe-8ada-e4e9572c37c8",
                "GovernancePolicy",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                2
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "domain", repositoryHelper.getContainsRegex("data access", true), methodName);
        testFindEntitiesByProperty(
                "578a3500-9ad3-45fe-8ada-e4e9572c37c8",
                "GovernancePolicy",
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                2
        );

    }

    @Test
    public void testFindColumnsByPrimaryKeyName() {

        final String methodName = "testFindColumnsByPrimaryKeyName";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "name", repositoryHelper.getStartsWithRegex("SQL"), methodName);
        testFindEntitiesByClassification(
                "aa8d5470-6dbc-4648-9e2f-045e5df9d2f9",
                "RelationalColumn",
                "PrimaryKey",
                ip,
                MatchCriteria.ANY,
                4);

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "name", repositoryHelper.getStartsWithRegex("sql", true), methodName);
        testFindEntitiesByClassification(
                "aa8d5470-6dbc-4648-9e2f-045e5df9d2f9",
                "RelationalColumn",
                "PrimaryKey",
                ip,
                MatchCriteria.ANY,
                4);

    }

    @Test
    public void testFindFileByType() {

        final String methodName = "testFindFileByType";

        String typeName = "DataFile";
        String typeGUID = "10752b4a-4b5d-4519-9eae-fdd6d162122f";

        InstanceProperties ip = new InstanceProperties();
        InstanceProperties ci = new InstanceProperties();
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "fileType", repositoryHelper.getExactMatchRegex("CSV"), methodName);
        ci = repositoryHelper.addStringPropertyToInstance(sourceName, ci, "fileType", repositoryHelper.getExactMatchRegex("csv", true), methodName);

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                8
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                8
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                5,
                5
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.ALL,
                5,
                5
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "fileType", repositoryHelper.getStartsWithRegex("CS"), methodName);
        ci = repositoryHelper.addStringPropertyToInstance(sourceName, ci, "fileType", repositoryHelper.getStartsWithRegex("cs", true), methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                8
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ci,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                8
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getEndsWithRegex("CSV"),
                MockConstants.EGERIA_PAGESIZE,
                8
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getEndsWithRegex("csv", true),
                MockConstants.EGERIA_PAGESIZE,
                8
        );

    }

    @Test
    public void testFindCategoryBySubjectAreaName() {

        final String methodName = "testFindCategoryBySubjectAreaName";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "name", repositoryHelper.getStartsWithRegex("Org"), methodName);
        testFindEntitiesByClassification(
                "e507485b-9b5a-44c9-8a28-6967f7ff3672",
                "GlossaryCategory",
                "SubjectArea",
                ip,
                MatchCriteria.ALL,
                1);

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "name", repositoryHelper.getStartsWithRegex("org", true), methodName);
        testFindEntitiesByClassification(
                "e507485b-9b5a-44c9-8a28-6967f7ff3672",
                "GlossaryCategory",
                "SubjectArea",
                ip,
                MatchCriteria.ALL,
                1);

    }

    @Test
    public void testFindContactDetailsByProperty() {

        final String methodName = "testFindContactDetailsByProperty";

        String typeName = "ContactDetails";
        String typeGUID = "79296df8-645a-4ef7-a011-912d1cdcf75a";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "contactMethodValue", repositoryHelper.getEndsWithRegex("w@cocopharmaceutical.com"), methodName);
        ip = repositoryHelper.addEnumPropertyToInstance(sourceName, ip, "contactMethodType", 0, "Email", "Contact through email.", methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "contactMethodValue", repositoryHelper.getEndsWithRegex("w@COCOPHARMACEUTICAL.COM", true), methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getEndsWithRegex("w@cocopharmaceutical.com"),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getEndsWithRegex("w@COCOPHARMACEUTICAL.COM", true),
                MockConstants.EGERIA_PAGESIZE,
                1
        );

    }

    @Test
    public void testFindSchemaTypeByNamespace() {

        final String methodName = "testFindSchemaTypeByNamespace";

        String typeName = "RelationalDBSchemaType";
        String typeGUID = "f20f5f45-1afb-41c1-9a09-34d8812626a4";

        InstanceProperties ip = new InstanceProperties();

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "namespace", repositoryHelper.getStartsWithRegex("EMPL"), methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                2
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "namespace", repositoryHelper.getStartsWithRegex("empl", true), methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                MockConstants.EGERIA_PAGESIZE,
                2
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getStartsWithRegex("EMPL"),
                MockConstants.EGERIA_PAGESIZE,
                2
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getStartsWithRegex("empl", true),
                MockConstants.EGERIA_PAGESIZE,
                2
        );

    }

    @Test
    public void testChangeSet() {

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        try {
            List<String> referenceListProperties = igcRestClient.getPagedRelationshipPropertiesForType("term");

            Reference testTerm = igcRestClient.getAssetById(MockConstants.TERM_RID);
            OMRSStub testStub = igcRepositoryHelper.getOMRSStubForAsset(testTerm);

            // Initial test will be against a non-existent stub
            ChangeSet test = new ChangeSet(igcRestClient, testTerm, testStub);
            Set<String> changedProperties = test.getChangedProperties();
            assertNotNull(changedProperties);
            assertEquals(changedProperties.size(), 19);

            ChangeSet.Change newAssignedAssetInstance = getSingleChange(test, "assigned_assets");
            assertEquals(newAssignedAssetInstance.getIgcPropertyName(), "assigned_assets");
            assertEquals(newAssignedAssetInstance.getIgcPropertyPath(), "/assigned_assets");
            assertEquals(newAssignedAssetInstance.getOp(), "add");
            assertNull(newAssignedAssetInstance.getOldValue(referenceListProperties));
            Object assignedAssets = newAssignedAssetInstance.getNewValue(referenceListProperties);
            assertTrue(assignedAssets instanceof ItemList);
            ItemList<?> actualAssets = (ItemList<?>) assignedAssets;
            List<?> listOfAssets = actualAssets.getItems();
            assertNotNull(listOfAssets);
            assertEquals(listOfAssets.size(), 2);

            // Next test will have a stub populated so changes will not all be adds
            testStub = igcRepositoryHelper.getOMRSStubForAsset(testTerm);
            test = new ChangeSet(igcRestClient, testTerm, testStub);
            changedProperties = test.getChangedProperties();
            assertNotNull(changedProperties);
            assertEquals(changedProperties.size(), 27);

            newAssignedAssetInstance = getSingleChange(test, "assigned_assets");
            assertEquals(newAssignedAssetInstance.getIgcPropertyName(), "assigned_assets");
            assertEquals(newAssignedAssetInstance.getIgcPropertyPath(), "/assigned_assets/items/0");
            assertEquals(newAssignedAssetInstance.getOp(), "add");
            assertNull(newAssignedAssetInstance.getOldValue(referenceListProperties)); // because it is an 'add'
            assignedAssets = newAssignedAssetInstance.getNewValue(referenceListProperties);
            assertTrue(assignedAssets instanceof Reference); // because we are only adding 1 new relationship
            Reference actualAsset = (Reference) assignedAssets;
            assertNotNull(actualAsset);

            ChangeSet.Change newReplacedBy = getSingleChange(test, "replaced_by");
            assertEquals(newReplacedBy.getIgcPropertyName(), "replaced_by");
            assertEquals(newReplacedBy.getIgcPropertyPath(), "/replaced_by");
            assertEquals(newReplacedBy.getOp(), "remove");

            ChangeSet.Change newModifiedOn = getSingleChange(test, "modified_on");
            assertEquals(newModifiedOn.getIgcPropertyName(), "modified_on");
            assertEquals(newModifiedOn.getIgcPropertyPath(), "/modified_on");
            assertEquals(newModifiedOn.getOp(), "replace");
            assertNotNull(newModifiedOn.getOldValue(referenceListProperties));
        } catch (IGCException e) {
            log.error("Hit unexpected exception testing change sets.", e);
            assertNull(e);
        }

    }

    private ChangeSet.Change getSingleChange(ChangeSet changes, String propertyName) {
        List<ChangeSet.Change> change = changes.getChangesForProperty(propertyName);
        assertNotNull(change);
        assertEquals(change.size(), 1);
        return change.get(0);
    }

    @Test
    public void testUnsupportedEvents() {

        try {

            igcomrsRepositoryEventMapper.processEvent("{\"INVALID\":\"\"");
            igcomrsRepositoryEventMapper.processEvent("{\"eventType\":\"IA_PROJECT_CREATED_EVENT\"}");
            igcomrsRepositoryEventMapper.processEvent("{\"eventType\":\"IGC_ETLGROUP_EVENT\"}");
            igcomrsRepositoryEventMapper.processEvent("{\"eventType\":\"IGC_BUSINESSTERM_EVENT\",\"ACTION\":\"ASSIGNED_RELATIONSHIP\"}");
            igcomrsRepositoryEventMapper.processEvent("{\"eventType\":\"IGC_BUSINESSTERM_EVENT\",\"ACTION\":\"INVALID\"}");
            igcomrsRepositoryEventMapper.processEvent("{\"eventType\":\"IA_COLUMN_CLASSIFIED_EVENT\"}");

        } catch (Exception e) {
            log.error("Hit unexpected exception during unsupported event processing.", e);
            assertNull(e);
        }

    }

    @Test
    public void testDataConnectionEvent() {

        try {
            igcomrsRepositoryEventMapper.processEvent("{\"createdRID\":\"" + MockConstants.DATA_CONNECTION_RID + "\",\"eventType\":\"DC_CREATE_EVENT\"}");
        } catch (Exception e) {
            log.error("Hit unexpected exception during data connection event processing.", e);
            assertNull(e);
        }

    }

    @Test
    public void testTermAddEvent() {

        // TODO: determine how to check the output / outcome of events themselves...
        // TODO: confirm that this generates all of the following:
        //  - ???
        try {
            igcomrsRepositoryEventMapper.processEvent("{\"ASSET_NAME\":\"Email Address\",\"ACTION\":\"CREATE\",\"ASSET_CONTEXT\":\"Coco Pharmaceuticals\",\"TIMESTAMP\":\"1578339969042\",\"ASSET_TYPE\":\"Term\",\"eventType\":\"IGC_BUSINESSTERM_EVENT\",\"USER\":\"isadmin\",\"CHANGEWORKFLOWSTATE\":\"false\",\"ASSET_RID\":\"" + MockConstants.TERM_RID_FOR_EVENT + "\"}");
        } catch (Exception e) {
            log.error("Hit unexpected exception during add event processing.", e);
            assertNull(e);
        }

    }

    @Test
    public void testDataFileDeleteEvent() {

        // TODO: confirm that this generates all of the following:
        //  - purge entities for: 1x DataFile, 1x TabularSchemaType, 3x TabularColumn
        //  - purge relationships for: 1x NestedDataFile, ???, 3x SemanticAssignment (to each TabularColumn)
        //  Note that the following will not be generated (as they would come based on other IGC update events)
        //  - update entities for: 1x DataFileFolder, 3x GlossaryTerm
        try {
            igcomrsRepositoryEventMapper.processEvent("{\"ASSET_NAME\":\"CompDir-ContactPhone.csv\",\"ACTION\":\"DELETE\",\"ASSET_CONTEXT\":\"INFOSVR >> / >> data >> files >> CocoPharma\",\"TIMESTAMP\":\"1578340621093\",\"ASSET_TYPE\":\"Data File\",\"eventType\":\"IGC_ASSET_EVENT\",\"USER\":\"isadmin\",\"ASSET_RID\":\"" + MockConstants.DATA_FILE_RID_FOR_DELETE_EVENT + "\"}");
        } catch (Exception e) {
            log.error("Hit unexpected exception during delete event processing.", e);
            assertNull(e);
        }

    }

    @Test
    public void testIMAMShareEvent() {
        // TODO: confirm that this generates all of the following:
        //  - update entities for: 1x RelationalDBSchemaType / DeployedDatabaseSchema, 1x RelationalTable
        //  - purge relationship for: 1x ??? (schema-to-table)
        //  - add relationship for: 1x ??? (schema-to-table)
        try {
            igcomrsRepositoryEventMapper.processEvent("{\"discoverOperationId\":\"1578394441915\",\"createdRIDs\":\"\",\"deletedRIDs\":\"\",\"configFile\":\"\",\"eventType\":\"IMAM_SHARE_EVENT\",\"user\":\"isadmin\",\"importEventRid\":\"1a6c9.b98b35d5.001muvt3r.amdvtco.3657j2.1r4eivkibet0nhobp4sg1j00\",\"mergedRIDs\":\"Hidden_DataCollection_in_Database:b1c497ce.54bd3a08.001mts4qn.7n9a341.3l2hic.d867phul07pgt3478ctim, Hidden_DataSchema:b1c497ce.c1fb060b.001mts4qn.7n9ghn6.59e1lg.oeu3169u6dtpesgou6cqh, DataConnection:b1c497ce.8e4c0a48.001mts4qn.7mouq34.s1cncq.51abs5f71epl37jke7irc, HostSystem:b1c497ce.354f5217.001mtr387.0nbvgbo.uh4485.rd8qffabbjgrsfjh2sheh\"}");
        } catch (Exception e) {
            log.error("Hit unexpected exception during IMAM share event processing.", e);
            assertNull(e);
        }
    }

    @AfterSuite
    public void stopConnector() {
        try {
            igcomrsRepositoryEventMapper.disconnect();
            igcomrsRepositoryConnector.disconnect();
        } catch (ConnectorCheckedException e) {
            log.error("Unable to property disconnect connector.", e);
        }
    }

    /**
     * Translate the provided OMRS property name into a simple string value.
     * @param properties the OMRS InstanceProperties from which to retrieve the OMRS value
     * @param propertyName the name of the property for which to retrieve the value
     * @return String
     */
    private String getStringValue(InstanceProperties properties, String propertyName) {
        String value = null;
        try {
            value = AttributeMapping.getIgcValueFromPropertyValue(properties.getPropertyValue(propertyName));
        } catch (PropertyErrorException e) {
            log.error("Unable to translate {}.", propertyName, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception translating {}.", propertyName, e);
            assertNull(e);
        }
        return value;
    }

    /**
     * Translate the provided OMRS property name into a simple integer value.
     * @param properties the OMRS InstanceProperties from which to retrieve the OMRS value
     * @param propertyName the name of the property for which to retrieve the value
     * @return int
     */
    private int getIntegerValue(InstanceProperties properties, String propertyName) {
        String value = null;
        try {
            value = AttributeMapping.getIgcValueFromPropertyValue(properties.getPropertyValue(propertyName));
        } catch (PropertyErrorException e) {
            log.error("Unable to translate {}.", propertyName, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception translating {}.", propertyName, e);
            assertNull(e);
        }
        return Integer.parseInt(value);
    }

    /**
     * Translate the provided OMRS property name into an enumeration value.
     * @param properties the OMRS InstanceProperties from which to retrieve the OMRS value
     * @param propertyName the name of the property for which to retrieve the value
     * @return EnumPropertyValue
     */
    private EnumPropertyValue getEnumValue(InstanceProperties properties, String propertyName) {
        InstancePropertyValue value = properties.getPropertyValue(propertyName);
        assertEquals(value.getInstancePropertyCategory(), InstancePropertyCategory.ENUM);
        return (EnumPropertyValue) value;
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param typeGUID the entity type GUID to search
     * @param typeName the name of the type to search
     * @param queryString the string criteria by which to search
     * @param pageSize limit the number of results
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(String typeGUID,
                                                               String typeName,
                                                               String queryString,
                                                               int pageSize,
                                                               int totalNumberExpected) {
        Set<String> types = new HashSet<>();
        if (typeName != null) {
            types.add(typeName);
        }
        return testFindEntitiesByPropertyValue(typeGUID, types, null, queryString, pageSize, totalNumberExpected);
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param possibleTypes the names of the types that could be returned by the search
     * @param queryString the string criteria by which to search
     * @param pageSize limit the number of results
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(Set<String> possibleTypes,
                                                               String queryString,
                                                               int pageSize,
                                                               int totalNumberExpected) {
        return testFindEntitiesByPropertyValue(null, possibleTypes, null, queryString, pageSize, totalNumberExpected);
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param possibleTypes the names of the types that could be returned by the search
     * @param classificationLimiters the names of classifications by which to limit the results (or null if not to limit)
     * @param queryString the string criteria by which to search
     * @param pageSize limit the number of results
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(Set<String> possibleTypes,
                                                               Set<String> classificationLimiters,
                                                               String queryString,
                                                               int pageSize,
                                                               int totalNumberExpected) {
        return testFindEntitiesByPropertyValue(null, possibleTypes, classificationLimiters, queryString, pageSize, totalNumberExpected);
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param typeGUID the entity type GUID to search
     * @param possibleTypes the names of the types that could be returned by the search
     * @param classificationLimiters the names of classifications by which to limit the results (or null if not to limit)
     * @param queryString the string criteria by which to search
     * @param pageSize limit the number of results
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(String typeGUID,
                                                               Set<String> possibleTypes,
                                                               Set<String> classificationLimiters,
                                                               String queryString,
                                                               int pageSize,
                                                               int totalNumberExpected) {
        return testFindEntitiesByPropertyValue(
                typeGUID,
                possibleTypes,
                classificationLimiters,
                queryString,
                pageSize,
                totalNumberExpected,
                null,
                null
        );
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param typeGUID the entity type GUID to search
     * @param possibleTypes the names of the types that could be returned by the search
     * @param classificationLimiters the names of classifications by which to limit the results (or null if not to limit)
     * @param queryString the string criteria by which to search
     * @param pageSize limit the number of results
     * @param totalNumberExpected the total number of expected results
     * @param sequencingOrder the ordering to use for the results
     * @param sequencingProperty the property by which to sort (if sequencing order is based on a property)
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(String typeGUID,
                                                               Set<String> possibleTypes,
                                                               Set<String> classificationLimiters,
                                                               String queryString,
                                                               int pageSize,
                                                               int totalNumberExpected,
                                                               SequencingOrder sequencingOrder,
                                                               String sequencingProperty) {

        List<EntityDetail> results = null;
        List<String> classifications = null;
        if (classificationLimiters != null) {
            classifications = new ArrayList<>(classificationLimiters);
        }

        try {
            results = igcomrsMetadataCollection.findEntitiesByPropertyValue(
                    MockConstants.EGERIA_USER,
                    typeGUID,
                    queryString,
                    0,
                    null,
                    classifications,
                    null,
                    sequencingProperty,
                    sequencingOrder,
                    pageSize
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for entities of type '{}' by property value.", typeGUID, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for entities of type '{}' by property value.", typeGUID, e);
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(results == null || results.isEmpty());
        } else {
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertEquals(results.size(), totalNumberExpected);
            for (EntityDetail result : results) {
                assertTrue(possibleTypes.contains(result.getType().getTypeDefName()));
                assertTrue(result.getVersion() > 1);
            }
        }

        return results;

    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property.
     *
     * @param typeGUID the entity type GUID to search
     * @param typeName the name of the type to search
     * @param matchProperties the properties to match against
     * @param matchCriteria the criteria by which to match
     * @param pageSize the number of results to limit
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByProperty(String typeGUID,
                                                          String typeName,
                                                          InstanceProperties matchProperties,
                                                          MatchCriteria matchCriteria,
                                                          int pageSize,
                                                          int totalNumberExpected) {
        return testFindEntitiesByProperty(
                typeGUID,
                typeName,
                matchProperties,
                matchCriteria,
                pageSize,
                totalNumberExpected,
                null,
                null
        );
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property.
     *
     * @param typeGUID the entity type GUID to search
     * @param typeName the name of the type to search
     * @param matchProperties the properties to match against
     * @param matchCriteria the criteria by which to match
     * @param pageSize the number of results to limit
     * @param totalNumberExpected the total number of expected results
     * @param sequencingOrder the ordering to use for the results
     * @param sequencingProperty the property by which to sort (if sequencing order is based on a property)
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByProperty(String typeGUID,
                                                          String typeName,
                                                          InstanceProperties matchProperties,
                                                          MatchCriteria matchCriteria,
                                                          int pageSize,
                                                          int totalNumberExpected,
                                                          SequencingOrder sequencingOrder,
                                                          String sequencingProperty) {

        List<EntityDetail> results = null;

        try {
            results = igcomrsMetadataCollection.findEntitiesByProperty(
                    MockConstants.EGERIA_USER,
                    typeGUID,
                    matchProperties,
                    matchCriteria,
                    0,
                    null,
                    null,
                    null,
                    sequencingProperty,
                    sequencingOrder,
                    pageSize
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for {} entities by property: {}", typeName, matchProperties, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for {} entities by property: {}", typeName, matchProperties, e);
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(results == null || results.isEmpty());
        } else {
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertEquals(results.size(), totalNumberExpected);
            for (EntityDetail result : results) {
                assertEquals(result.getType().getTypeDefName(), typeName);
                assertTrue(result.getVersion() > 1);

                // TODO: need to understand how the refresh request methods are actually meant to work...
                /*
                try {
                    igcomrsMetadataCollection.refreshEntityReferenceCopy(MockConstants.EGERIA_USER,
                            result.getGUID(),
                            result.getType().getTypeDefGUID(),
                            result.getType().getTypeDefName(),
                            result.getMetadataCollectionId());
                } catch (InvalidParameterException | RepositoryErrorException | HomeEntityException | UserNotAuthorizedException e) {
                    log.error("Unable to send a refresh event for entity GUID: {}", result.getGUID());
                    assertNull(e);
                } catch (Exception e) {
                    log.error("Unexpected exception trying to send a refresh event for entity GUID: {}", result.getGUID(), e);
                    assertNull(e);
                }*/

            }
        }

        return results;

    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by
     * classification property.
     *
     * @param typeGUID the entity type GUID to search
     * @param typeName the name of the type to search
     * @param classificationName the name of the classification by which to limit the results
     * @param matchClassificationProperties the properties of the classification to match against
     * @param matchCriteria the criteria by which to match
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByClassification(String typeGUID,
                                                                String typeName,
                                                                String classificationName,
                                                                InstanceProperties matchClassificationProperties,
                                                                MatchCriteria matchCriteria,
                                                                int totalNumberExpected) {

        List<EntityDetail> results = null;

        try {
            results = igcomrsMetadataCollection.findEntitiesByClassification(
                    MockConstants.EGERIA_USER,
                    typeGUID,
                    classificationName,
                    matchClassificationProperties,
                    matchCriteria,
                    0,
                    null,
                    null,
                    null,
                    null,
                    MockConstants.EGERIA_PAGESIZE
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for {} entities by classification {} with properties: {}", typeName, classificationName, matchClassificationProperties, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for {} entities by classification {} with properties: {}", typeName, classificationName, matchClassificationProperties, e);
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(results == null || results.isEmpty());
        } else {
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertEquals(results.size(), totalNumberExpected);
            for (EntityDetail result : results) {
                assertEquals(result.getType().getTypeDefName(), typeName);
                assertTrue(result.getVersion() > 1);
            }
        }

        return results;

    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property.
     *
     * @param typeGUID the entity type GUID to search
     * @param typeName the name of the type to search
     * @param subTypes GUIDs of any subtypes to match against
     * @param searchProperties the criteria against which to search
     * @param pageSize the number of results to limit
     * @param totalNumberExpected the total number of expected results
     * @param sequencingOrder the ordering to use for the results
     * @param sequencingProperty the property by which to sort (if sequencing order is based on a property)
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntities(String typeGUID,
                                                String typeName,
                                                List<String> subTypes,
                                                SearchProperties searchProperties,
                                                int pageSize,
                                                int totalNumberExpected,
                                                SequencingOrder sequencingOrder,
                                                String sequencingProperty) {

        List<EntityDetail> results = null;

        try {
            results = igcomrsMetadataCollection.findEntities(
                    MockConstants.EGERIA_USER,
                    typeGUID,
                    subTypes,
                    searchProperties,
                    0,
                    null,
                    null,
                    null,
                    sequencingProperty,
                    sequencingOrder,
                    pageSize
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for {} entities by: {}", typeName, searchProperties, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for {} entities by: {}", typeName, searchProperties, e);
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(results == null || results.isEmpty());
        } else {
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertEquals(results.size(), totalNumberExpected);
            for (EntityDetail result : results) {
                assertEquals(result.getType().getTypeDefName(), typeName);
                assertTrue(result.getVersion() > 1);

                // TODO: need to understand how the refresh request methods are actually meant to work...
                /*
                try {
                    igcomrsMetadataCollection.refreshEntityReferenceCopy(MockConstants.EGERIA_USER,
                            result.getGUID(),
                            result.getType().getTypeDefGUID(),
                            result.getType().getTypeDefName(),
                            result.getMetadataCollectionId());
                } catch (InvalidParameterException | RepositoryErrorException | HomeEntityException | UserNotAuthorizedException e) {
                    log.error("Unable to send a refresh event for entity GUID: {}", result.getGUID());
                    assertNull(e);
                } catch (Exception e) {
                    log.error("Unexpected exception trying to send a refresh event for entity GUID: {}", result.getGUID(), e);
                    assertNull(e);
                }*/

            }
        }

        return results;

    }

    /**
     * Executes a common set of tests against a list of Relationship objects after first searching for them by property.
     *
     * @param typeGUID the relationship type GUID to search
     * @param typeName the name of the type to search
     * @param matchProperties the properties to match against
     * @param matchCriteria the criteria by which to match
     * @param pageSize the number of results to limit
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<Relationship>} the results of the query
     */
    private List<Relationship> testFindRelationshipsByProperty(String typeGUID,
                                                               String typeName,
                                                               InstanceProperties matchProperties,
                                                               MatchCriteria matchCriteria,
                                                               int pageSize,
                                                               int totalNumberExpected) {
        return testFindRelationshipsByProperty(
                typeGUID,
                typeName,
                matchProperties,
                matchCriteria,
                pageSize,
                totalNumberExpected,
                null,
                null
        );
    }

    /**
     * Executes a common set of tests against a list of Relationship objects after first searching for them by property.
     *
     * @param typeGUID the relationship type GUID to search
     * @param typeName the name of the type to search
     * @param matchProperties the properties to match against
     * @param matchCriteria the criteria by which to match
     * @param pageSize the number of results to limit
     * @param totalNumberExpected the total number of expected results
     * @param sequencingOrder the ordering to use for the results
     * @param sequencingProperty the property by which to sort (if sequencing order is based on a property)
     * @return {@code List<Relationship>} the results of the query
     */
    private List<Relationship> testFindRelationshipsByProperty(String typeGUID,
                                                               String typeName,
                                                               InstanceProperties matchProperties,
                                                               MatchCriteria matchCriteria,
                                                               int pageSize,
                                                               int totalNumberExpected,
                                                               SequencingOrder sequencingOrder,
                                                               String sequencingProperty) {

        List<Relationship> results = null;

        try {
            results = igcomrsMetadataCollection.findRelationshipsByProperty(
                    MockConstants.EGERIA_USER,
                    typeGUID,
                    matchProperties,
                    matchCriteria,
                    0,
                    null,
                    null,
                    sequencingProperty,
                    sequencingOrder,
                    pageSize
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for {} relationships by property: {}", typeName, matchProperties, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for {} relationships by property: {}", typeName, matchProperties, e);
            e.printStackTrace();
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(results == null || results.isEmpty());
        } else {
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertEquals(results.size(), totalNumberExpected);
            testRelationshipsAreRetrievable(results, typeName);
        }

        return results;

    }

    /**
     * Executes a common set of tests against a list of Relationship objects after first searching for them by property
     * value.
     *
     * @param typeGUID the relationship type GUID to search
     * @param typeName the name of the type to search
     * @param searchCriteria the string to use for the search
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<Relationship>} the results of the query
     */
    private List<Relationship> testFindRelationshipsByPropertyValue(String typeGUID,
                                                               String typeName,
                                                               String searchCriteria,
                                                               int totalNumberExpected) {

        List<Relationship> results = null;

        try {
            results = igcomrsMetadataCollection.findRelationshipsByPropertyValue(
                    MockConstants.EGERIA_USER,
                    typeGUID,
                    searchCriteria,
                    0,
                    null,
                    null,
                    null,
                    null,
                    MockConstants.EGERIA_PAGESIZE
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for {} relationships by criteria: {}", typeName, searchCriteria, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for {} relationships by criteria: {}", typeName, searchCriteria, e);
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(results == null || results.isEmpty());
        } else {
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertEquals(results.size(), totalNumberExpected);
            testRelationshipsAreRetrievable(results, typeName);
        }

        return results;

    }

    /**
     * Executes a common set of tests against an EntityDetail object after first directly retrieving it.
     *
     * @param igcType the type of IGC object
     * @param omrsType the type of OMRS object
     * @param prefix the prefix for a generated entity (or null if none)
     * @param rid the RID of the IGC object
     * @param expectedValues a map of any expected values that will be asserted for equality (property name to value)
     * @return EntityDetail that is retrieved
     */
    private EntityDetail testEntityDetail(String igcType, String omrsType, String prefix, String rid, Map<String, String> expectedValues) {

        EntityDetail detail = null;

        try {
            IGCEntityGuid guid = new IGCEntityGuid(metadataCollectionId, igcType, prefix, rid);
            detail = igcomrsMetadataCollection.isEntityKnown(MockConstants.EGERIA_USER, guid.toString());
        } catch (RepositoryErrorException | InvalidParameterException e) {
            log.error("Unable to retrieve entity detail for {}.", omrsType, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception retrieving {} detail.", omrsType, e);
            assertNull(e);
        }

        assertNotNull(detail);
        assertEquals(detail.getType().getTypeDefName(), omrsType);
        assertTrue(detail.getVersion() > 1);
        assertNotNull(detail.getMetadataCollectionId());
        testExpectedValuesForEquality(detail.getProperties(), expectedValues);

        return detail;

    }

    /**
     * Executes a common set of tests against an EntitySummary object after first directly retrieving it.
     *
     * @param igcType the type of IGC object
     * @param omrsType the type of OMRS object
     * @param prefix the prefix for a generated entity (or null if none)
     * @param rid the RID of the IGC object
     * @return EntitySummary that is retrieved
     */
    private EntitySummary testEntitySummary(String igcType, String omrsType, String prefix, String rid) {

        EntitySummary summary = null;

        try {
            IGCEntityGuid guid = new IGCEntityGuid(metadataCollectionId, igcType, prefix, rid);
            summary = igcomrsMetadataCollection.getEntitySummary(MockConstants.EGERIA_USER, guid.toString());
        } catch (RepositoryErrorException | InvalidParameterException | EntityNotKnownException e) {
            log.error("Unable to retrieve entity detail for {}.", omrsType, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception retrieving {} detail.", omrsType, e);
            assertNull(e);
        }

        assertNotNull(summary);
        assertEquals(summary.getType().getTypeDefName(), omrsType);
        assertTrue(summary.getVersion() > 1);
        assertNotNull(summary.getMetadataCollectionId());

        return summary;

    }

    /**
     * Tests whether the provided properties have values equal to those that are expected.
     *
     * @param properties the properties to check
     * @param expectedValues the expected values of the properties (property name to value)
     */
    private void testExpectedValuesForEquality(InstanceProperties properties, Map<String, String> expectedValues) {
        if (expectedValues != null) {
            for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
                String propertyName = expected.getKey();
                String expectedValue = expected.getValue();
                String foundValue = getStringValue(properties, propertyName);
                assertEquals(foundValue, expectedValue);
            }
        }
    }

    /**
     * Executes a common set of tests against a list of Relationship objects after first directly retrieving them.
     *
     * @param igcType the type of IGC object
     * @param omrsType the type of OMRS object
     * @param prefix the prefix for a generated entity (or null if none)
     * @param rid the RID of the IGC object
     * @param pageSize limit the results
     * @param totalNumberExpected the total number of relationships expected
     * @param relationshipExpectations a list of relationship expectations
     * @return {@code List<Relationship>} the list of relationships retrieved
     */
    private List<Relationship> testRelationshipsForEntity(String igcType,
                                                          String omrsType,
                                                          String prefix,
                                                          String rid,
                                                          int pageSize,
                                                          int totalNumberExpected,
                                                          List<RelationshipExpectation> relationshipExpectations) {
        return testRelationshipsForEntity(
                igcType,
                omrsType,
                prefix,
                rid,
                pageSize,
                totalNumberExpected,
                relationshipExpectations,
                null,
                null
        );
    }

    /**
     * Executes a common set of tests against a list of Relationship objects after first directly retrieving them.
     *
     * @param igcType the type of IGC object
     * @param omrsType the type of OMRS object
     * @param prefix the prefix for a generated entity (or null if none)
     * @param rid the RID of the IGC object
     * @param pageSize limit the results
     * @param totalNumberExpected the total number of relationships expected
     * @param relationshipExpectations a list of relationship expectations
     * @param sequencingOrder the ordering to use for the results
     * @param sequencingProperty the property by which to sort (if sequencing order is based on a property)
     * @return {@code List<Relationship>} the list of relationships retrieved
     */
    private List<Relationship> testRelationshipsForEntity(String igcType,
                                                          String omrsType,
                                                          String prefix,
                                                          String rid,
                                                          int pageSize,
                                                          int totalNumberExpected,
                                                          List<RelationshipExpectation> relationshipExpectations,
                                                          SequencingOrder sequencingOrder,
                                                          String sequencingProperty) {

        List<Relationship> relationships = null;

        try {
            IGCEntityGuid guid = new IGCEntityGuid(metadataCollectionId, igcType, prefix, rid);
            relationships = igcomrsMetadataCollection.getRelationshipsForEntity(
                    MockConstants.EGERIA_USER,
                    guid.toString(),
                    null,
                    0,
                    null,
                    null,
                    sequencingProperty,
                    sequencingOrder,
                    pageSize
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | EntityNotKnownException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to retrieve relationships for {}.", omrsType, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception retrieving {} relationships.", omrsType, e);
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(relationships == null || relationships.isEmpty());
        } else {
            assertNotNull(relationships);
            assertFalse(relationships.isEmpty());
            assertEquals(relationships.size(), totalNumberExpected);
            for (RelationshipExpectation relationshipExpectation : relationshipExpectations) {
                for (int i = relationshipExpectation.getStartIndex(); i < relationshipExpectation.getFinishIndex(); i++) {

                    Relationship candidate = relationships.get(i);
                    assertEquals(candidate.getType().getTypeDefName(), relationshipExpectation.getOmrsType());
                    EntityProxy one = candidate.getEntityOneProxy();
                    EntityProxy two = candidate.getEntityTwoProxy();
                    assertTrue(relationshipExpectation.getProxyOneTypes().contains(one.getType().getTypeDefName()));
                    assertTrue(one.getVersion() > 1);
                    testQualifiedNameEquality(relationshipExpectation.getExpectedProxyOneQN(), one.getUniqueProperties().getPropertyValue("qualifiedName"));
                    assertTrue(relationshipExpectation.getProxyTwoTypes().contains(two.getType().getTypeDefName()));
                    assertTrue(two.getVersion() > 1);
                    testQualifiedNameEquality(relationshipExpectation.getExpectedProxyTwoQN(), two.getUniqueProperties().getPropertyValue("qualifiedName"));

                    // TODO: need to understand how the refresh request methods are actually meant to work...
                    /*
                    try {
                        igcomrsMetadataCollection.refreshRelationshipReferenceCopy(MockConstants.EGERIA_USER,
                                candidate.getGUID(),
                                candidate.getType().getTypeDefGUID(),
                                candidate.getType().getTypeDefName(),
                                otherMetadataCollectionId);
                    } catch (InvalidParameterException | RepositoryErrorException | HomeRelationshipException | UserNotAuthorizedException e) {
                        log.error("Unable to send a refresh event for relationship GUID: {}", candidate.getGUID());
                        assertNull(e);
                    } catch (Exception e) {
                        log.error("Unexpected exception trying to send a refresh event for relationship GUID: {}", candidate.getGUID(), e);
                        assertNull(e);
                    }*/

                }

            }
        }

        return relationships;

    }

    /**
     * Attempt to re-retrieve the provided relationships by their GUID.
     *
     * @param relationships list of relationships to test re-retrieval
     * @param typeName the expected type of the relationship
     */
    private void testRelationshipsAreRetrievable(List<Relationship> relationships, String typeName) {

        for (Relationship result : relationships) {
            assertEquals(result.getType().getTypeDefName(), typeName);
            assertTrue(result.getVersion() > 1);

            try {
                Relationship foundAgain = igcomrsMetadataCollection.isRelationshipKnown(MockConstants.EGERIA_USER, result.getGUID());
                assertNotNull(foundAgain);
                assertEquals(foundAgain, result);
                IGCRelationshipGuid igcRelationshipGuid = IGCRelationshipGuid.fromGuid(result.getGUID());
                assertNotNull(RelationshipMapping.getProxyOneGuidFromRelationship(igcRepositoryHelper, igcRelationshipGuid));
                assertNotNull(RelationshipMapping.getProxyTwoGuidFromRelationship(igcRepositoryHelper, igcRelationshipGuid));
            } catch (InvalidParameterException | RepositoryErrorException e) {
                log.error("Unable to find relationship again by GUID: {}", result.getGUID());
                assertNull(e);
            } catch (Exception e) {
                log.error("Unexpected exception trying to find relationship again by GUID: {}", result.getGUID(), e);
                assertNull(e);
            }

        }

    }

    /**
     * Test whether the provided qualifiedNames are equal or not.
     * @param expectedQN the expected qualifiedName
     * @param foundValue the found qualifiedName
     */
    private void testQualifiedNameEquality(String expectedQN, InstancePropertyValue foundValue) {
        if (expectedQN != null) {
            try {
                String foundQN = AttributeMapping.getIgcValueFromPropertyValue(foundValue);
                assertEquals(foundQN, expectedQN);
            } catch (PropertyErrorException e) {
                log.error("Unable to test equality of qualifiedName.", e);
                assertNull(e);
            }
        }
    }

    /**
     * Utility class for defining the expectations to check against a set of relationships.
     */
    protected class RelationshipExpectation {

        private int startIndex;
        private int finishIndex;
        private String omrsType;
        private Set<String> proxyOneTypes;
        private Set<String> proxyTwoTypes;
        private String expectedProxyOneQN;
        private String expectedProxyTwoQN;

        private RelationshipExpectation() {
            proxyOneTypes = new HashSet<>();
            proxyTwoTypes = new HashSet<>();
        }

        RelationshipExpectation(int startIndex, int finishIndex, String omrsType, String proxyOneType, String proxyTwoType) {
            this();
            this.startIndex = startIndex;
            this.finishIndex = finishIndex;
            this.omrsType = omrsType;
            this.proxyOneTypes.add(proxyOneType);
            this.proxyTwoTypes.add(proxyTwoType);
        }

        RelationshipExpectation(int startIndex,
                                int finishIndex,
                                String omrsType,
                                String proxyOneType,
                                String proxyTwoType,
                                String expectedProxyOneQN,
                                String expectedProxyTwoQN) {
            this(startIndex, finishIndex, omrsType, proxyOneType, proxyTwoType);
            this.expectedProxyOneQN = expectedProxyOneQN;
            this.expectedProxyTwoQN = expectedProxyTwoQN;
        }

        RelationshipExpectation(int startIndex,
                                int finishIndex,
                                String omrsType,
                                Set<String> proxyOneTypes,
                                Set<String> proxyTwoTypes,
                                String expectedProxyOneQN,
                                String expectedProxyTwoQN) {
            this.startIndex = startIndex;
            this.finishIndex = finishIndex;
            this.omrsType = omrsType;
            this.proxyOneTypes = proxyOneTypes;
            this.proxyTwoTypes = proxyTwoTypes;
            this.expectedProxyOneQN = expectedProxyOneQN;
            this.expectedProxyTwoQN = expectedProxyTwoQN;
        }

        int getStartIndex() { return startIndex; }
        int getFinishIndex() { return finishIndex; }
        String getOmrsType() { return omrsType; }
        Set<String> getProxyOneTypes() { return proxyOneTypes; }
        Set<String> getProxyTwoTypes() { return proxyTwoTypes; }
        String getExpectedProxyOneQN() { return expectedProxyOneQN; }
        String getExpectedProxyTwoQN() { return expectedProxyTwoQN; }

    }

}
