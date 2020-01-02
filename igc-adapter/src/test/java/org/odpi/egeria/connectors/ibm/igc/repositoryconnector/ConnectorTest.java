/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.ContactDetailsMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.RelationalDBSchemaTypeMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mocks.MockConnection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.adapters.repositoryservices.ConnectorConfigurationFactory;
import org.odpi.openmetadata.adminservices.configuration.properties.OpenMetadataExchangeRule;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBroker;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectionCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.http.HttpHelper;
import org.odpi.openmetadata.opentypes.OpenMetadataTypesArchive;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLog;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLogDestination;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditingComponent;
import org.odpi.openmetadata.repositoryservices.connectors.stores.archivestore.properties.OpenMetadataArchive;
import org.odpi.openmetadata.repositoryservices.connectors.stores.archivestore.properties.OpenMetadataArchiveTypeStore;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.eventmanagement.OMRSRepositoryEventExchangeRule;
import org.odpi.openmetadata.repositoryservices.eventmanagement.OMRSRepositoryEventManager;
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

    private String metadataCollectionId;

    private List<AttributeTypeDef> supportedAttributeTypeDefs;
    private List<TypeDef> supportedTypeDefs;

    /**
     * Construct base objects.
     */
    public ConnectorTest() {

        HttpHelper.noStrictSSL();
        metadataCollectionId = UUID.randomUUID().toString();
        supportedAttributeTypeDefs = new ArrayList<>();
        supportedTypeDefs = new ArrayList<>();

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

        OMRSMetadataCollection collection = igcomrsRepositoryConnector.getMetadataCollection();
        assertTrue(collection instanceof IGCOMRSMetadataCollection);
        igcomrsMetadataCollection = (IGCOMRSMetadataCollection) collection;
        try {
            assertEquals(igcomrsMetadataCollection.getMetadataCollectionId(MockConstants.EGERIA_USER), metadataCollectionId);
        } catch (RepositoryErrorException e) {
            log.error("Unable to match metadata collection IDs.", e);
            assertNotNull(e);
        }

        ConnectorConfigurationFactory connectorConfigurationFactory = new ConnectorConfigurationFactory();
        Connection eventMapperConnection = connectorConfigurationFactory.getRepositoryEventMapperConnection(
                "MockIGCServer",
                "org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapperProvider",
                null,
                "localhost:1080"
        );
        try {
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

    /**
     * Test the supported open metadata types.
     */
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

    /**
     * Test searching for open metadata types.
     */
    @Test
    public void testFindTypes() {

        List<TypeDef> entityTypeDefs = findTypeDefsByCategory(TypeDefCategory.ENTITY_DEF);
        applyAssertionsToTypeDefs(entityTypeDefs, TypeDefCategory.ENTITY_DEF);

        List<TypeDef> classificationTypeDefs = findTypeDefsByCategory(TypeDefCategory.CLASSIFICATION_DEF);
        applyAssertionsToTypeDefs(classificationTypeDefs, TypeDefCategory.CLASSIFICATION_DEF);

        List<TypeDef> relationshipTypeDefs = findTypeDefsByCategory(TypeDefCategory.RELATIONSHIP_DEF);
        applyAssertionsToTypeDefs(relationshipTypeDefs, TypeDefCategory.RELATIONSHIP_DEF);

        try {
            List<TypeDef> searchResults = igcomrsMetadataCollection.searchForTypeDefs(MockConstants.EGERIA_USER, ".*a.*");
            assertNotNull(searchResults);
            assertFalse(searchResults.isEmpty());
            List<String> names = searchResults.stream().map(TypeDef::getName).collect(Collectors.toList());
            assertTrue(names.contains("RelationalTable"));
            assertTrue(names.contains("CategoryHierarchyLink"));
            assertTrue(names.contains("Confidentiality"));
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

    /**
     * Test direct type def retrievals.
     */
    @Test
    public void testTypeDefRetrievals() {

        final String relationalTableGUID = "ce7e72b8-396a-4013-8688-f9d973067425";
        final String relationalTableName = "RelationalTable";

        try {
            TypeDef byGUID = igcomrsMetadataCollection.getTypeDefByGUID(MockConstants.EGERIA_USER, relationalTableGUID);
            TypeDef byName = igcomrsMetadataCollection.getTypeDefByName(MockConstants.EGERIA_USER, relationalTableName);
            assertNotNull(byGUID);
            assertNotNull(byName);
            assertEquals(byGUID.getName(), relationalTableName);
            assertEquals(byName.getGUID(), relationalTableGUID);
            assertEquals(byGUID, byName);
        } catch (InvalidParameterException | RepositoryErrorException | TypeDefNotKnownException e) {
            log.error("Unable to retrieve type definition: {} / {}", relationalTableGUID, relationalTableName, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying retrieve type definition.", e);
            assertNull(e);
        }

    }

    /**
     * Test Glossary search, by property value.
     */
    @Test
    public void testGlossaryFindByPropertyValue() {

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                "36f66863-9726-4b41-97ee-714fd0dc6fe4",
                "Glossary",
                ".*\\Qa\\E.*",
                2
        );

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertTrue(qualifiedName.startsWith("gen!GL@(category)="));
        }

    }

    /**
     * Test Glossary direct retrieval by GUID.
     */
    @Test
    public void testGetGlossary() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Coco Pharmaceuticals");
        expectedValues.put("qualifiedName", MockConstants.GLOSSARY_QN);

        EntityDetail detail = testEntityDetail(
                "category",
                "Glossary",
                GlossaryMapper.IGC_RID_PREFIX,
                MockConstants.GLOSSARY_RID,
                expectedValues
        );

    }

    /**
     * Test Glossary relationship retrievals.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "category",
                "Glossary",
                GlossaryMapper.IGC_RID_PREFIX,
                MockConstants.GLOSSARY_RID,
                65,
                relationshipExpectations
        );

    }

    /**
     * Test Category search, by property value.
     */
    @Test
    public void testCategoryFindByPropertyValue() {

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                "e507485b-9b5a-44c9-8a28-6967f7ff3672",
                "GlossaryCategory",
                ".*\\Qe\\E.*",
                12
        );

        for (EntityDetail result : results) {
            String qualifiedName = getStringValue(result.getProperties(), "qualifiedName");
            assertTrue(qualifiedName.startsWith("(category)="));
        }

    }

    /**
     * Test GlossaryCategory direct retrieval by GUID.
     */
    @Test
    public void testGetGlossaryCategory() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "Employee");
        expectedValues.put("qualifiedName", "(category)=Coco Pharmaceuticals::(category)=Person::(category)=Employee");

        EntityDetail detail = testEntityDetail(
                "category",
                "GlossaryCategory",
                null,
                MockConstants.CATEGORY_RID,
                expectedValues
        );

    }

    /**
     * Test GlossaryCategory relationship retrievals.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "category",
                "GlossaryCategory",
                null,
                MockConstants.CATEGORY_RID,
                12,
                relationshipExpectations
        );

    }

    /**
     * Test searching GlossaryTerm by property value.
     */
    @Test
    public void testGlossaryTermFindByPropertyValue() {

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                ".*\\QAddress\\E.*",
                6
        );

    }

    /**
     * Test searching GlossaryTerm by property.
     */
    @Test
    public void testGlossaryTermFindByProperty_displayName() {

        Map<String, String> matchProperties = new HashMap<>();
        matchProperties.put("displayName", ".*\\QAddress\\E.*");

        List<EntityDetail> results = testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                matchProperties,
                MatchCriteria.ALL,
                6
        );

    }

    /**
     * Test searching GlossaryTerm by any of multiple properties.
     */
    @Test
    public void testGlossaryTermFindByProperties_ANY() {

        Map<String, String> matchProperties = new HashMap<>();
        matchProperties.put("displayName", ".*\\QAddress\\E.*");
        matchProperties.put("summary", ".*\\QNumber\\E.*");

        List<EntityDetail> results = testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                matchProperties,
                MatchCriteria.ANY,
                7
        );

    }

    /**
     * Test searching GlossaryTerm by all of multiple properties.
     */
    @Test
    public void testGlossaryTermFindByProperties_ALL() {

        Map<String, String> matchProperties = new HashMap<>();
        matchProperties.put("displayName", ".*\\QAddress\\E.*");
        matchProperties.put("summary", ".*\\Qnumber\\E.*");

        List<EntityDetail> results = testFindEntitiesByProperty(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                matchProperties,
                MatchCriteria.ALL,
                1
        );

        EntityDetail single = results.get(0);
        testQualifiedNameEquality("(category)=Coco Pharmaceuticals::(term)=Address Line 1", single.getProperties().getPropertyValue("qualifiedName"));

    }

    /**
     * Test searching a supertype by property value.
     */
    @Test
    public void testAssetFindByPropertyValue() {

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add("Asset");
        possibleTypes.add("Database");

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                "896d14c2-7522-4f6c-8519-757711943fe6",
                possibleTypes,
                null,
                ".*\\QCOMPDIR\\E.*",
                1
        );

    }

    /**
     * Test searching everything by property value.
     */
    @Test
    public void testAllTypesFindByPropertyValue() {

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add("GlossaryTerm");
        possibleTypes.add("DataClass");

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                possibleTypes,
                ".*\\QAddress\\E.*",
                13
        );

    }

    /**
     * Test searching everything by property value and limiting by classification.
     */
    @Test
    public void testAllTypesFindByPropertyValue_limitToConfidentiality() {

        Set<String> possibleTypes = new HashSet<>();
        possibleTypes.add("GlossaryTerm");

        Set<String> classifications = new HashSet<>();
        classifications.add("Confidentiality");

        List<EntityDetail> results = testFindEntitiesByPropertyValue(
                possibleTypes,
                classifications,
                ".*\\QAddress\\E.*",
                6
        );

    }

    /**
     * Test searching by classification.
     */
    @Test
    public void testGlossaryTermFindByClassification() {

        Map<String, Integer> properties = new HashMap<>();
        properties.put("level", 3);

        List<EntityDetail> results = testFindEntitiesByClassification(
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_GUID,
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                "Confidentiality",
                properties,
                MatchCriteria.ALL,
                12);

    }

    /**
     * Test retrieving a GlossaryTerm as an EntityDetail directly.
     */
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

    /**
     * Test retrieving a GlossaryTerm as an EntitySummary directly.
     */
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

    /**
     * Test GlossaryTerm relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "term",
                MockConstants.EGERIA_GLOSSARY_TERM_TYPE_NAME,
                null,
                MockConstants.TERM_RID,
                3,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a Database as an EntityDetail directly.
     */
    @Test
    public void testGetDatabaseDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "COMPDIR");
        expectedValues.put("type", "DB2");
        expectedValues.put("instance", "db2inst1");

        EntityDetail detail = testEntityDetail(
                "database",
                "Database",
                null,
                MockConstants.DATABASE_RID,
                expectedValues
        );

    }

    /**
     * Test Database relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "database",
                "Database",
                null,
                MockConstants.DATABASE_RID,
                2,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a Connection as an EntityDetail directly.
     */
    @Test
    public void testGetConnectionDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "CocoPharma_COMPDIR_ibm-db2");
        expectedValues.put("qualifiedName", MockConstants.DATA_CONNECTION_QN);

        EntityDetail detail = testEntityDetail(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID,
                expectedValues
        );

    }

    /**
     * Test Connection relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID,
                3,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving an Endpoint as an EntityDetail directly.
     */
    @Test
    public void testGetEndpointDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("qualifiedName", MockConstants.HOST_QN);

        EntityDetail detail = testEntityDetail(
                "host",
                "Endpoint",
                null,
                MockConstants.HOST_RID,
                expectedValues
        );

    }

    /**
     * Test Endpoint relationship retrieval.
     */
    @Test
    public void testEndpointRelationships() {

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 7,
                        "ConnectionEndpoint", "Endpoint", "Connection",
                        MockConstants.HOST_QN, null)
        );

        List<Relationship> relationships = testRelationshipsForEntity(
                "host",
                "Endpoint",
                null,
                MockConstants.HOST_RID,
                7,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a DeployedDatabaseSchema as an EntityDetail directly.
     */
    @Test
    public void testGetDeployedDatabaseSchemaDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "DB2INST1");

        EntityDetail detail = testEntityDetail(
                "database_schema",
                "DeployedDatabaseSchema",
                null,
                MockConstants.DATABASE_SCHEMA_RID,
                expectedValues
        );

    }

    /**
     * Test DeployedDatabaseSchema relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "database_schema",
                "DeployedDatabaseSchema",
                null,
                MockConstants.DATABASE_SCHEMA_RID,
                2,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a RelationalDBSchemaType as an EntityDetail directly.
     */
    @Test
    public void testGetRelationalDBSchemaTypeDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "DB2INST1");

        EntityDetail detail = testEntityDetail(
                "database_schema",
                "RelationalDBSchemaType",
                RelationalDBSchemaTypeMapper.IGC_RID_PREFIX,
                MockConstants.DATABASE_SCHEMA_RID,
                expectedValues
        );

    }

    /**
     * Test RelationalDBSchemaType relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "database_schema",
                "DeployedDatabaseSchema",
                RelationalDBSchemaTypeMapper.IGC_RID_PREFIX,
                MockConstants.DATABASE_SCHEMA_RID,
                4,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a RelationalTable as an EntityDetail directly.
     */
    @Test
    public void testGetRelationalTableDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "CONTACTEMAIL");

        EntityDetail detail = testEntityDetail(
                "database_table",
                "RelationalTable",
                null,
                MockConstants.DATABASE_TABLE_RID,
                expectedValues
        );

    }

    /**
     * Test RelationalTable relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "database_table",
                "RelationalTable",
                null,
                MockConstants.DATABASE_TABLE_RID,
                4,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a RelationalColumn as an EntityDetail directly.
     */
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

        List<Classification> classifications = detail.getClassifications();
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

    /**
     * Test RelationalColumn relationship retrieval.
     */
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

    /**
     * Test retrieving a RelationalColumn as an EntitySummary directly.
     */
    @Test
    public void testGetRelationalColumnSummary() {

        EntitySummary summary = testEntitySummary(
                "database_column",
                "RelationalColumn",
                null,
                MockConstants.DATABASE_COLUMN_RID
        );

    }

    /**
     * Test retrieving a DataClass as an EntityDetail directly.
     */
    @Test
    public void testGetDataClassDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "Email Address");
        expectedValues.put("example", "paul_fowler@aol.com");
        expectedValues.put("qualifiedName", MockConstants.DATA_CLASS_QN);

        EntityDetail detail = testEntityDetail(
                "data_class",
                "DataClass",
                null,
                MockConstants.DATA_CLASS_RID,
                expectedValues
        );

    }

    /**
     * Test DataClass relationship retrieval.
     */
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

    /**
     * Test retrieving a Connection as an EntityDetail directly.
     */
    @Test
    public void testGetConnectionFSDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "LOCALFS");
        expectedValues.put("qualifiedName", MockConstants.DATA_CONNECTION_QN_FS);

        EntityDetail detail = testEntityDetail(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID_FS,
                expectedValues
        );

    }

    /**
     * Test Connection relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_connection",
                "Connection",
                null,
                MockConstants.DATA_CONNECTION_RID_FS,
                3,
                relationshipExpectations
        );

    }

    /**
     * Test DataFileFolder (root) relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_file_folder",
                "FileFolder",
                null,
                MockConstants.DATA_FILE_FOLDER_RID,
                9,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a DataFile as an EntityDetail directly.
     */
    @Test
    public void testGetDataFileDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("qualifiedName", MockConstants.DATA_FILE_QN);

        EntityDetail detail = testEntityDetail(
                "data_file",
                "DataFile",
                null,
                MockConstants.DATA_FILE_RID,
                expectedValues
        );

    }

    /**
     * Test DataFile relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_file",
                "DataFile",
                null,
                MockConstants.DATA_FILE_RID,
                2,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a TabularSchemaType as an EntityDetail directly.
     */
    @Test
    public void testGetTabularSchemaTypeDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("displayName", "CompDir-ContactEmail");
        expectedValues.put("qualifiedName", MockConstants.DATA_FILE_RECORD_QN);

        EntityDetail detail = testEntityDetail(
                "data_file_record",
                "TabularSchemaType",
                null,
                MockConstants.DATA_FILE_RECORD_RID,
                expectedValues
        );

    }

    /**
     * Test TabularSchemaType relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_file_record",
                "TabularSchemaType",
                null,
                MockConstants.DATA_FILE_RECORD_RID,
                4,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a TabularColumn as an EntityDetail directly.
     */
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

    /**
     * Test TabularColumn relationship retrieval.
     */
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

        List<Relationship> relationships = testRelationshipsForEntity(
                "data_file_field",
                "TabularColumn",
                null,
                MockConstants.DATA_FILE_FIELD_RID,
                2,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a GlossaryTerm classified as a SpineObject as an EntityDetail directly.
     */
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

    /**
     * Test retrieving a GlossaryCategory classified as a SubjectArea as an EntityDetail directly.
     */
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

    /**
     * Test retrieving a GovernancePolicy as an EntityDetail directly.
     */
    @Test
    public void testGetGovernancePolicyDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("title", "Confidential information should be masked when user does not have specific access to its Subject Area");
        expectedValues.put("qualifiedName", MockConstants.INFORMATION_GOVERNANCE_POLICY_QN);

        EntityDetail detail = testEntityDetail(
                "information_governance_policy",
                "GovernancePolicy",
                null,
                MockConstants.INFORMATION_GOVERNANCE_POLICY_RID,
                expectedValues
        );

    }

    /**
     * Test retrieving a Person as an EntityDetail directly.
     */
    @Test
    public void testGetPersonDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "ggeeke");
        expectedValues.put("fullName", "Gary Geeke");
        expectedValues.put("jobTitle", "IT Infrastructure Administrator");
        expectedValues.put("qualifiedName", MockConstants.USER_QN);

        EntityDetail detail = testEntityDetail(
                "user",
                "Person",
                null,
                MockConstants.USER_RID,
                expectedValues
        );

    }

    /**
     * Test Person relationship retrieval.
     */
    @Test
    public void testPersonRelationships() {

        String expectedContactDetailsQN = "gen!CD@" + MockConstants.USER_QN;

        List<RelationshipExpectation> relationshipExpectations = new ArrayList<>();
        relationshipExpectations.add(
                new RelationshipExpectation(0, 1,
                        "ContactThrough", "Person", "ContactDetails",
                        MockConstants.USER_QN, expectedContactDetailsQN)
        );

        List<Relationship> relationships = testRelationshipsForEntity(
                "user",
                "Person",
                null,
                MockConstants.USER_RID,
                1,
                relationshipExpectations
        );

    }

    /**
     * Test retrieving a ContactDetails as an EntityDetail directly.
     */
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

    /**
     * Test retrieving a Team as an EntityDetail directly.
     */
    @Test
    public void testGetTeamDetail() {

        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("name", "cocopharma_it_ops");
        expectedValues.put("description", "IT Operations");

        EntityDetail detail = testEntityDetail(
                "group",
                "Team",
                null,
                MockConstants.GROUP_RID,
                expectedValues
        );

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
     * @param totalNumberExpected the total number of expected results
     * @return {@List<EntityDetail>} the results of the query
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
     * @return {@List<EntityDetail>} the results of the query
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
     * @return {@List<EntityDetail>} the results of the query
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
     * @return {@List<EntityDetail>} the results of the query
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
     * @param properties the properties to match against
     * @param matchCriteria the criteria by which to match
     * @param totalNumberExpected the total number of expected results
     * @return {@List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByProperty(String typeGUID,
                                                          String typeName,
                                                          Map<String, String> properties,
                                                          MatchCriteria matchCriteria,
                                                          int totalNumberExpected) {

        final String methodName = "testFindEntitiesByProperty";
        List<EntityDetail> results = null;

        OMRSRepositoryHelper helper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repoName = igcomrsRepositoryConnector.getRepositoryName();

        InstanceProperties matchProperties = new InstanceProperties();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String propertyName = entry.getKey();
            String value = entry.getValue();
            matchProperties = helper.addStringPropertyToInstance(
                    repoName,
                    matchProperties,
                    propertyName,
                    value,
                    methodName
            );
        }

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
     * @param properties the properties of the classification to match against
     * @param matchCriteria the criteria by which to match
     * @param totalNumberExpected the total number of expected results
     * @return {@List<EntityDetail>} the results of the query
     */
    private List<EntityDetail> testFindEntitiesByClassification(String typeGUID,
                                                                String typeName,
                                                                String classificationName,
                                                                Map<String, Integer> properties,
                                                                MatchCriteria matchCriteria,
                                                                int totalNumberExpected) {

        final String methodName = "testFindEntitiesByClassification";
        List<EntityDetail> results = null;

        OMRSRepositoryHelper helper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repoName = igcomrsRepositoryConnector.getRepositoryName();

        InstanceProperties matchClassificationProperties = new InstanceProperties();
        for (Map.Entry<String, Integer> entry : properties.entrySet()) {
            String propertyName = entry.getKey();
            int value = entry.getValue();
            matchClassificationProperties = helper.addIntPropertyToInstance(
                    repoName,
                    matchClassificationProperties,
                    propertyName,
                    value,
                    methodName
            );
        }

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
            detail = igcomrsMetadataCollection.isEntityKnown(MockConstants.EGERIA_USER, guid.asGuid());
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
            summary = igcomrsMetadataCollection.getEntitySummary(MockConstants.EGERIA_USER, guid.asGuid());
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

        log.warn("Found this summary, or detail? {}", summary);

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
                    guid.asGuid(),
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
