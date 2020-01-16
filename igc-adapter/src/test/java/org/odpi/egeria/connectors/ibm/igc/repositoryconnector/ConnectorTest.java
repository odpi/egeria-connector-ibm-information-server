/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapper;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.model.ChangeSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.ContactDetailsMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.RelationalDBSchemaTypeMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mocks.MockConnection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.adapters.eventbus.topic.inmemory.InMemoryOpenMetadataTopicConnector;
import org.odpi.openmetadata.adapters.repositoryservices.ConnectorConfigurationFactory;
import org.odpi.openmetadata.adminservices.configuration.properties.OpenMetadataExchangeRule;
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
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
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
    private OMRSRepositoryContentManager contentManager;
    private OMRSRepositoryEventManager eventManager;
    private InMemoryOpenMetadataTopicConnector inMemoryEventConnector;
    private OMRSRepositoryHelper repositoryHelper;
    private String sourceName;

    private String metadataCollectionId;
    private String otherMetadataCollectionId;

    private List<AttributeTypeDef> supportedAttributeTypeDefs;
    private List<TypeDef> supportedTypeDefs;

    /**
     * Construct base objects.
     */
    public ConnectorTest() {

        HttpHelper.noStrictSSL();
        metadataCollectionId = UUID.randomUUID().toString();
        otherMetadataCollectionId = UUID.randomUUID().toString();
        supportedAttributeTypeDefs = new ArrayList<>();
        supportedTypeDefs = new ArrayList<>();
        inMemoryEventConnector = new InMemoryOpenMetadataTopicConnector();

    }

    /**
     * Initialize the connector with some basic values.
     */
    @BeforeSuite
    public void startConnector() {

        Connection mockConnection = new MockConnection();
        OMRSAuditLogDestination destination = new OMRSAuditLogDestination(null);
        OMRSAuditLog auditLog = new OMRSAuditLog(destination, -1, "ConnectorTest", "Testing of the connector", null);
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

        ConnectorBroker connectorBroker = new ConnectorBroker();

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

        ConnectorConfigurationFactory connectorConfigurationFactory = new ConnectorConfigurationFactory();
        try {
            Connection eventMapperConnection = connectorConfigurationFactory.getRepositoryEventMapperConnection(
                    "MockIGCServer",
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
                if (e.getErrorMessage().startsWith("OMRS-IGC-REPOSITORY-400-006")) {
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
                if (e.getErrorMessage().startsWith("OMRS-IGC-REPOSITORY-400-006")) {
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
                supportedTypeDefs.add(typeDef);
            }
            contentManager.addTypeDef(igcomrsRepositoryConnector.getRepositoryName(), typeDef);
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

        for (TypeDef typeDef : supportedTypeDefs) {
            try {
                assertTrue(igcomrsMetadataCollection.verifyTypeDef(MockConstants.EGERIA_USER, typeDef));
            } catch (InvalidParameterException | RepositoryErrorException | InvalidTypeDefException | TypeDefNotSupportedException e) {
                log.error("Unable to verify type definition: {}", typeDef.getName(), e);
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
            assertTrue(fromGalleryTD.containsAll(supportedTypeDefs));
            assertTrue(supportedTypeDefs.containsAll(fromGalleryTD));
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
    public void testGlossaryFindByPropertyValue() {

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                "36f66863-9726-4b41-97ee-714fd0dc6fe4",
                "Glossary",
                repositoryHelper.getContainsRegex("a"),
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
                65,
                relationshipExpectations
        );

    }

    @Test
    public void testCategoryFindByPropertyValue() {

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                "e507485b-9b5a-44c9-8a28-6967f7ff3672",
                "GlossaryCategory",
                repositoryHelper.getContainsRegex("e"),
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
                        "CategoryAnchor", "Glossary", "GlossaryCategory",
                        "gen!GL@(category)=Coco Pharmaceuticals",
                        expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "CategoryHierarchyLink", "GlossaryCategory", "GlossaryCategory",
                        "(category)=Coco Pharmaceuticals::(category)=Person",
                        expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 12,
                        "TermCategorization", "GlossaryCategory", "GlossaryTerm",
                        expectedProxyTwoQN,
                        null)
        );

        testRelationshipsForEntity(
                "category",
                "GlossaryCategory",
                null,
                MockConstants.CATEGORY_RID,
                12,
                relationshipExpectations
        );

    }

    @Test
    public void testGlossaryTermFindByPropertyValue() {

        testFindEntitiesByPropertyValue(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                repositoryHelper.getContainsRegex("Address"),
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
                1
        );

        EntityDetail single = results.get(0);
        testQualifiedNameEquality("(category)=Coco Pharmaceuticals::(term)=Address Line 1", single.getProperties().getPropertyValue("qualifiedName"));

    }

    @Test
    public void testAssetFindByPropertyValue() {

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add("Asset");
        possibleTypes.add("Database");

        testFindEntitiesByPropertyValue(
                "896d14c2-7522-4f6c-8519-757711943fe6",
                possibleTypes,
                null,
                repositoryHelper.getContainsRegex("COMPDIR"),
                1
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
                13
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
                6
        );

    }

    @Test
    public void testGlossaryTermFindByClassification() {

        final String methodName = "testGlossaryTermFindByClassification";

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addIntPropertyToInstance(sourceName, ip, "level", 3, methodName);

        testFindEntitiesByClassification(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                "Confidentiality",
                ip,
                MatchCriteria.ALL,
                12);

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
        int level = getIntegerValue(first.getProperties(), "level");
        assertEquals(level, 3);
    }

    @Test
    public void testGlossaryTermRelationships() {

        String expectedProxyOneQN = "gen!GL@(category)=Coco Pharmaceuticals";
        String expectedProxyTwoQN = "(category)=Coco Pharmaceuticals::(term)=Address Line 1";

        Set<String> proxyOneTypes = new HashSet<>();
        proxyOneTypes.add("RelationalColumn");
        proxyOneTypes.add("TabularColumn");

        Set<String> proxyTwoTypes = new HashSet<>();
        proxyTwoTypes.add(MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME);

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 2,
                        "SemanticAssignment", proxyOneTypes, proxyTwoTypes,
                        null, expectedProxyTwoQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(2, 3,
                        "TermAnchor", "Glossary", MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                        expectedProxyOneQN, expectedProxyTwoQN)
        );

        testRelationshipsForEntity(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.TERM_RID,
                3,
                relationshipExpectations
        );

    }

    @Test
    public void testGetDatabaseDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "COMPDIR");
        expectedValues.put("type", "DB2");
        expectedValues.put("instance", "db2inst1");

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
                        "DataContentForDataSet", "Database", "DeployedDatabaseSchema",
                        MockConstants.DATABASE_QN, MockConstants.DATABASE_SCHEMA_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "ConnectionToAsset", "Connection", "Database",
                        MockConstants.DATA_CONNECTION_QN, MockConstants.DATABASE_QN)
        );

        testRelationshipsForEntity(
                "database",
                "Database",
                null,
                MockConstants.DATABASE_RID,
                2,
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
                new RelationshipExpectation(0, 7,
                        "ConnectionEndpoint", "Endpoint", "Connection",
                        MockConstants.HOST_QN, null)
        );

        testRelationshipsForEntity(
                "host",
                "Endpoint",
                null,
                MockConstants.HOST_RID,
                7,
                relationshipExpectations
        );

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
                        "AssetSchemaType", "DeployedDatabaseSchema", "RelationalDBSchemaType",
                        MockConstants.DATABASE_SCHEMA_QN, expectedSchemaTypeQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "DataContentForDataSet", "Database", "DeployedDatabaseSchema",
                        MockConstants.DATABASE_QN, MockConstants.DATABASE_SCHEMA_QN)
        );

        testRelationshipsForEntity(
                "database_schema",
                "DeployedDatabaseSchema",
                null,
                MockConstants.DATABASE_SCHEMA_RID,
                2,
                relationshipExpectations
        );

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
                        "AttributeForSchema", "RelationalDBSchemaType", "RelationalTable",
                        expectedSchemaTypeQN, MockConstants.DATABASE_TABLE_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 4,
                        "NestedSchemaAttribute", "RelationalTable", "RelationalColumn",
                        MockConstants.DATABASE_TABLE_QN, null)
        );

        testRelationshipsForEntity(
                "database_table",
                "RelationalTable",
                null,
                MockConstants.DATABASE_TABLE_RID,
                4,
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

        testTypeEmbeddedAttributeWithStringDataType(detail.getClassifications());

    }

    private void testTypeEmbeddedAttributeWithStringDataType(List<Classification> classifications) {
        assertNotNull(classifications);
        assertFalse(classifications.isEmpty());
        assertEquals(classifications.size(), 1);
        Classification first = classifications.get(0);
        assertEquals(first.getType().getTypeDefName(), "TypeEmbeddedAttribute");
        String dataType = getStringValue(first.getProperties(), "dataType");
        assertEquals(dataType, "STRING");
    }

    @Test
    public void testRelationalColumnRelationships() {

        String expectedTermQN = "(category)=Coco Pharmaceuticals::(term)=Email Address";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "SemanticAssignment", "RelationalColumn", "GlossaryTerm",
                        MockConstants.DATABASE_COLUMN_QN, expectedTermQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 5,
                        "DataClassAssignment", "RelationalColumn", "DataClass",
                        MockConstants.DATABASE_COLUMN_QN, null)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(5, 6,
                        "NestedSchemaAttribute", "RelationalTable", "RelationalColumn",
                        MockConstants.DATABASE_TABLE_QN, MockConstants.DATABASE_COLUMN_QN)
        );

        List<Relationship> relationships = testRelationshipsForEntity(
                "database_column",
                "RelationalColumn",
                null,
                MockConstants.DATABASE_COLUMN_RID,
                6,
                relationshipExpectations
        );

        testDiscoveredDataClasses(relationships, 1, 4);
        testProposedDataClasses(relationships, 4, 5);

    }

    private void testDiscoveredDataClasses(List<Relationship> relationships, int startIndex, int finishIndex) {
        for (int i = startIndex; i < finishIndex; i++) {
            Relationship assignment = relationships.get(i);
            InstanceProperties properties = assignment.getProperties();
            int confidence = getIntegerValue(properties, "confidence");
            assertEquals(confidence, 100);
            EnumPropertyValue status = getEnumValue(properties, "status");
            assertEquals(status.getOrdinal(), 0);
            assertEquals(status.getSymbolicName(), "Discovered");
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
                new RelationshipExpectation(0, 3,
                        "DataClassAssignment", "RelationalColumn", "DataClass",
                        MockConstants.DATABASE_COLUMN_QN, MockConstants.DATA_CLASS_QN)
        );

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                3,
                relationshipExpectations
        );

        testDiscoveredDataClasses(relationships, 0, 2);
        testProposedDataClasses(relationships, 2, 3);

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
                        "FolderHierarchy", "FileFolder", "FileFolder",
                        expectedParentFolderQN, MockConstants.DATA_FILE_FOLDER_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 9,
                        "NestedFile", "FileFolder", "DataFile",
                        MockConstants.DATA_FILE_FOLDER_QN, null)
        );

        testRelationshipsForEntity(
                "data_file_folder",
                "FileFolder",
                null,
                MockConstants.DATA_FILE_FOLDER_RID,
                9,
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
                        "AssetSchemaType", "DataFile", "TabularSchemaType",
                        MockConstants.DATA_FILE_QN, MockConstants.DATA_FILE_RECORD_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "NestedFile", "FileFolder", "DataFile",
                        MockConstants.DATA_FILE_FOLDER_QN, MockConstants.DATA_FILE_QN)
        );

        testRelationshipsForEntity(
                "data_file",
                "DataFile",
                null,
                MockConstants.DATA_FILE_RID,
                2,
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
                        "AssetSchemaType", "DataFile", "TabularSchemaType",
                        MockConstants.DATA_FILE_QN, MockConstants.DATA_FILE_RECORD_QN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 4,
                        "AttributeForSchema", "TabularSchemaType", "TabularColumn",
                        MockConstants.DATA_FILE_RECORD_QN, null)
        );

        testRelationshipsForEntity(
                "data_file_record",
                "TabularSchemaType",
                null,
                MockConstants.DATA_FILE_RECORD_RID,
                4,
                relationshipExpectations
        );

    }

    @Test
    public void testGetTabularColumnDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Email");

        EntityDetail detail = testEntityDetail(
                "data_file_field",
                "TabularColumn",
                null,
                MockConstants.DATA_FILE_FIELD_RID,
                expectedValues
        );

        testTypeEmbeddedAttributeWithStringDataType(detail.getClassifications());

    }

    @Test
    public void testTabularColumnRelationships() {

        String expectedTermQN = "(category)=Coco Pharmaceuticals::(term)=Email Address";

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "SemanticAssignment", "TabularColumn", "GlossaryTerm",
                        MockConstants.DATA_FILE_FIELD_QN, expectedTermQN)
        );
        relationshipExpectations.add(
                new RelationshipExpectation(1, 2,
                        "AttributeForSchema", "TabularSchemaType", "TabularColumn",
                        MockConstants.DATA_FILE_RECORD_QN, MockConstants.DATA_FILE_FIELD_QN)
        );

        testRelationshipsForEntity(
                "data_file_field",
                "TabularColumn",
                null,
                MockConstants.DATA_FILE_FIELD_RID,
                2,
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
        assertEquals(classifications.size(), 1);
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
        assertEquals(classifications.size(), 1);
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
                        "ContactThrough", "Person", "ContactDetails",
                        MockConstants.USER_QN, expectedContactDetailsQN)
        );

        testRelationshipsForEntity(
                "user",
                "Person",
                null,
                MockConstants.USER_RID,
                1,
                relationshipExpectations
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
                3);

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
                1);

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertEquals(qualifiedName, MockConstants.DATABASE_COLUMN_QN);
        }

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", repositoryHelper.getContainsRegex("ADDR"), methodName);

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                12
        );

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.NONE,
                66
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", repositoryHelper.getStartsWithRegex(MockConstants.DATABASE_COLUMN_QN.substring(0, 20)), methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ANY,
                78
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "qualifiedName", repositoryHelper.getEndsWithRegex(MockConstants.DATABASE_COLUMN_QN.substring(20)), methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getExactMatchRegex("(steward_user)=Mr. Gary Geeke"),
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getExactMatchRegex("Gary Geeke"),
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("_user)=Mr. Gary Geeke"),
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("Gary Geeke"),
                1
        );

        testFindEntitiesByPropertyValue(
                "ac406bf8-e53e-49f1-9088-2af28bbbd285",
                "Person",
                repositoryHelper.getEndsWithRegex("Geeke"),
                1
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
                3);

        // Try searching by the symbolic name of an enumeration, should not return any results because it is not a
        // string property
        List<Relationship> results = testFindRelationshipsByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getExactMatchRegex("Proposed"),
                0
        );

    }

    @Test
    public void testFindSchemaElementByAnchorGUID() {

        final String methodName = "testFindSchemaElementByAnchorGUID";

        String ridForTable = "b1c497ce.54bd3a08.001mts4qn.7mp1ug9.4m6ktd.rpguhg74d1vci4g1fnf52";
        IGCEntityGuid guid = new IGCEntityGuid(metadataCollectionId, "database_table", ridForTable);

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "anchorGUID", repositoryHelper.getExactMatchRegex(guid.toString()), methodName);

        testFindEntitiesByProperty(
                "aa8d5470-6dbc-4648-9e2f-045e5df9d2f9",
                "RelationalColumn",
                ip,
                MatchCriteria.ALL,
                7
        );

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

    }

    @Test
    public void testFindFileByType() {

        final String methodName = "testFindFileByType";

        String typeName = "DataFile";
        String typeGUID = "10752b4a-4b5d-4519-9eae-fdd6d162122f";

        InstanceProperties ip = new InstanceProperties();
        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "fileType", repositoryHelper.getExactMatchRegex("CSV"), methodName);

        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                8
        );

        ip = repositoryHelper.addStringPropertyToInstance(sourceName, ip, "fileType", repositoryHelper.getStartsWithRegex("CS"), methodName);
        testFindEntitiesByProperty(
                typeGUID,
                typeName,
                ip,
                MatchCriteria.ALL,
                8
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getEndsWithRegex("CSV"),
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
                1
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getEndsWithRegex("w@cocopharmaceutical.com"),
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
                2
        );

        testFindEntitiesByPropertyValue(
                typeGUID,
                typeName,
                repositoryHelper.getStartsWithRegex("EMPL"),
                2
        );

    }

    @Test
    public void testChangeSet() {

        IGCRepositoryHelper helper = igcomrsMetadataCollection.getIgcRepositoryHelper();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        List<String> referenceListProperties = igcRestClient.getPagedRelationshipPropertiesForType("term");

        Reference testTerm = igcRestClient.getAssetById(MockConstants.TERM_RID);
        OMRSStub testStub = helper.getOMRSStubForAsset(testTerm);

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
        testStub = helper.getOMRSStubForAsset(testTerm);
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

    }

    private ChangeSet.Change getSingleChange(ChangeSet changes, String propertyName) {
        List<ChangeSet.Change> change = changes.getChangesForProperty(propertyName);
        assertNotNull(change);
        assertEquals(change.size(), 1);
        return change.get(0);
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

    // TODO: additional tests for coverage (in approximate order of priority)
    //  - self-referencing entity update event (eg. database schema) (40ish?)
    //  - IMAM share event (30ish?)

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
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(String typeGUID,
                                                               String typeName,
                                                               String queryString,
                                                               int totalNumberExpected) {
        Set<String> types = new HashSet<>();
        if (typeName != null) {
            types.add(typeName);
        }
        return testFindEntitiesByPropertyValue(typeGUID, types, null, queryString, totalNumberExpected);
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param possibleTypes the names of the types that could be returned by the search
     * @param queryString the string criteria by which to search
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(Set<String> possibleTypes,
                                                               String queryString,
                                                               int totalNumberExpected) {
        return testFindEntitiesByPropertyValue(null, possibleTypes, null, queryString, totalNumberExpected);
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param possibleTypes the names of the types that could be returned by the search
     * @param classificationLimiters the names of classifications by which to limit the results (or null if not to limit)
     * @param queryString the string criteria by which to search
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(Set<String> possibleTypes,
                                                               Set<String> classificationLimiters,
                                                               String queryString,
                                                               int totalNumberExpected) {
        return testFindEntitiesByPropertyValue(null, possibleTypes, classificationLimiters, queryString, totalNumberExpected);
    }

    /**
     * Executes a common set of tests against a list of EntityDetail objects after first searching for them by property
     * value.
     *
     * @param typeGUID the entity type GUID to search
     * @param possibleTypes the names of the types that could be returned by the search
     * @param classificationLimiters the names of classifications by which to limit the results (or null if not to limit)
     * @param queryString the string criteria by which to search
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByPropertyValue(String typeGUID,
                                                               Set<String> possibleTypes,
                                                               Set<String> classificationLimiters,
                                                               String queryString,
                                                               int totalNumberExpected) {

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
                    null,
                    null,
                    MockConstants.EGERIA_PAGESIZE
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
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByProperty(String typeGUID,
                                                          String typeName,
                                                          InstanceProperties matchProperties,
                                                          MatchCriteria matchCriteria,
                                                          int totalNumberExpected) {

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
                    null,
                    null,
                    MockConstants.EGERIA_PAGESIZE
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
     * Executes a common set of tests against a list of Relationship objects after first searching for them by property.
     *
     * @param typeGUID the relationship type GUID to search
     * @param typeName the name of the type to search
     * @param matchProperties the properties to match against
     * @param matchCriteria the criteria by which to match
     * @param totalNumberExpected the total number of expected results
     * @return {@code List<Relationship>} the results of the query
     */
    private List<Relationship> testFindRelationshipsByProperty(String typeGUID,
                                                               String typeName,
                                                               InstanceProperties matchProperties,
                                                               MatchCriteria matchCriteria,
                                                               int totalNumberExpected) {

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
                    null,
                    null,
                    MockConstants.EGERIA_PAGESIZE
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for {} relationships by property: {}", typeName, matchProperties, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for {} relationships by property: {}", typeName, matchProperties, e);
            assertNull(e);
        }

        if (totalNumberExpected <= 0) {
            assertTrue(results == null || results.isEmpty());
        } else {
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertEquals(results.size(), totalNumberExpected);
            for (Relationship result : results) {
                assertEquals(result.getType().getTypeDefName(), typeName);
                assertTrue(result.getVersion() > 1);

                try {
                    Relationship foundAgain = igcomrsMetadataCollection.isRelationshipKnown(MockConstants.EGERIA_USER, result.getGUID());
                    assertNotNull(foundAgain);
                    assertEquals(foundAgain, result);
                } catch (InvalidParameterException | RepositoryErrorException e) {
                    log.error("Unable to find relationship again by GUID: {}", result.getGUID());
                    assertNull(e);
                } catch (Exception e) {
                    log.error("Unexpected exception trying to find relationship again by GUID: {}", result.getGUID(), e);
                    assertNull(e);
                }

            }
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
            for (Relationship result : results) {
                assertEquals(result.getType().getTypeDefName(), typeName);
                assertTrue(result.getVersion() > 1);

                try {
                    Relationship foundAgain = igcomrsMetadataCollection.isRelationshipKnown(MockConstants.EGERIA_USER, result.getGUID());
                    assertNotNull(foundAgain);
                    assertEquals(foundAgain, result);
                } catch (InvalidParameterException | RepositoryErrorException e) {
                    log.error("Unable to find relationship again by GUID: {}", result.getGUID());
                    assertNull(e);
                } catch (Exception e) {
                    log.error("Unexpected exception trying to find relationship again by GUID: {}", result.getGUID(), e);
                    assertNull(e);
                }

            }
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
     * @param totalNumberExpected the total number of relationships expected
     * @param relationshipExpectations a list of relationship expectations
     * @return {@code List<Relationship>} the list of relationships retrieved
     */
    private List<Relationship> testRelationshipsForEntity(String igcType,
                                                          String omrsType,
                                                          String prefix,
                                                          String rid,
                                                          int totalNumberExpected,
                                                          List<RelationshipExpectation> relationshipExpectations) {

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
                    null,
                    null,
                    MockConstants.EGERIA_PAGESIZE
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | EntityNotKnownException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
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
