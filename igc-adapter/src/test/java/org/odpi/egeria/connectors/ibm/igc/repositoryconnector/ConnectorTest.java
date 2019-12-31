/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
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
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.*;
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

    private String exampleTableGuid;

    /**
     * Construct base objects.
     */
    public ConnectorTest() {

        HttpHelper.noStrictSSL();
        metadataCollectionId = UUID.randomUUID().toString();
        supportedAttributeTypeDefs = new ArrayList<>();
        supportedTypeDefs = new ArrayList<>();

        String exampleTableRid = MockConstants.TABLE_RID;
        IGCEntityGuid igcEntityGuid = new IGCEntityGuid(metadataCollectionId, "database_table", exampleTableRid);
        assertNotNull(igcEntityGuid);
        exampleTableGuid = igcEntityGuid.asGuid();

    }

    /**
     * Initialize the connector with some basic values.
     */
    @BeforeSuite
    public void startConnector() {

        Connection mockConnection = new MockConnection();
        OMRSAuditLogDestination destination = new OMRSAuditLogDestination(null);
        OMRSAuditLog auditLog = new OMRSAuditLog(destination, -1, "ConnectorTest", "Testing of the connector", null);
        contentManager = new OMRSRepositoryContentManager(auditLog);
        eventManager = new OMRSRepositoryEventManager("Mock Outbound EventManager",
                new OMRSRepositoryEventExchangeRule(OpenMetadataExchangeRule.SELECTED_TYPES, Collections.emptyList()),
                new OMRSRepositoryContentValidator(contentManager),
                new OMRSAuditLog(destination, OMRSAuditingComponent.REPOSITORY_EVENT_MANAGER));

        ConnectorBroker connectorBroker = new ConnectorBroker();

        try {
            Object connector = connectorBroker.getConnector(mockConnection);
            assertTrue(connector instanceof IGCOMRSRepositoryConnector);
            igcomrsRepositoryConnector = (IGCOMRSRepositoryConnector) connector;
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
        expectedValues.put("qualifiedName", "gen!GL@(category)=Coco Pharmaceuticals");

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

    // TODO: implement a set of tests below that mirror what was implemented in Postman -- test all the same types,
    //  relationships, REST endpoints (directly as MetadataCollection method calls), and scripted test assertions

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

        List<EntityDetail> results = null;

        try {
            results = igcomrsMetadataCollection.findEntitiesByPropertyValue(
                    MockConstants.EGERIA_USER,
                    typeGUID,
                    queryString,
                    0,
                    null,
                    null,
                    null,
                    null,
                    null,
                    MockConstants.EGERIA_PAGESIZE
            );
        } catch (InvalidParameterException | TypeErrorException | RepositoryErrorException | PropertyErrorException | PagingErrorException | FunctionNotSupportedException | UserNotAuthorizedException e) {
            log.error("Unable to search for {} entities by property value.", typeName, e);
            assertNull(e);
        } catch (Exception e) {
            log.error("Unexpected exception trying to search for {} entities by property value.", typeName, e);
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

        testExpectedValuesForEquality(detail.getProperties(), expectedValues);

        return detail;

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
                assertEquals(expectedValue, foundValue);
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
        }

        for (RelationshipExpectation relationshipExpectation : relationshipExpectations) {
            for (int i = relationshipExpectation.getStartIndex(); i < relationshipExpectation.getFinishIndex(); i++) {
                Relationship candidate = relationships.get(i);
                assertEquals(candidate.getType().getTypeDefName(), relationshipExpectation.getOmrsType());
                EntityProxy one = candidate.getEntityOneProxy();
                EntityProxy two = candidate.getEntityTwoProxy();
                assertEquals(one.getType().getTypeDefName(), relationshipExpectation.getProxyOneType());
                assertTrue(one.getVersion() > 1);
                testQualifiedNameEquality(relationshipExpectation.getExpectedProxyOneQN(), one.getUniqueProperties().getPropertyValue("qualifiedName"));
                assertEquals(two.getType().getTypeDefName(), relationshipExpectation.getProxyTwoType());
                assertTrue(two.getVersion() > 1);
                testQualifiedNameEquality(relationshipExpectation.getExpectedProxyTwoQN(), two.getUniqueProperties().getPropertyValue("qualifiedName"));
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
                assertEquals(expectedQN, foundQN);
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
        private String proxyOneType;
        private String proxyTwoType;
        private String expectedProxyOneQN;
        private String expectedProxyTwoQN;

        RelationshipExpectation(int startIndex, int finishIndex, String omrsType, String proxyOneType, String proxyTwoType) {
            this.startIndex = startIndex;
            this.finishIndex = finishIndex;
            this.omrsType = omrsType;
            this.proxyOneType = proxyOneType;
            this.proxyTwoType = proxyTwoType;
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

        int getStartIndex() { return startIndex; }
        int getFinishIndex() { return finishIndex; }
        String getOmrsType() { return omrsType; }
        String getProxyOneType() { return proxyOneType; }
        String getProxyTwoType() { return proxyTwoType; }
        String getExpectedProxyOneQN() { return expectedProxyOneQN; }
        String getExpectedProxyTwoQN() { return expectedProxyTwoQN; }

    }

}
