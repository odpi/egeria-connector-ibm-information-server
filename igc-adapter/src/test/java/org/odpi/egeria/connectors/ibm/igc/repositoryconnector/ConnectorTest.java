/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mocks.MockConnection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBroker;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectionCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.http.HttpHelper;
import org.odpi.openmetadata.opentypes.OpenMetadataTypesArchive;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLog;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLogDestination;
import org.odpi.openmetadata.repositoryservices.connectors.stores.archivestore.properties.OpenMetadataArchive;
import org.odpi.openmetadata.repositoryservices.connectors.stores.archivestore.properties.OpenMetadataArchiveTypeStore;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.*;
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
    private OMRSRepositoryContentManager contentManager;

    private String metadataCollectionId;

    private List<AttributeTypeDef> supportedAttributeTypeDefs;
    private List<TypeDef> supportedTypeDefs;

    private String exampleTableGuid;

    /**
     * Construct base objects
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

    }

    /**
     * Initialize all of the open metadata types that the connector could support.
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
                assertNotNull(e);
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
                assertNotNull(e);
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
                assertNotNull(e);
            }
        }

        for (TypeDef typeDef : supportedTypeDefs) {
            try {
                assertTrue(igcomrsMetadataCollection.verifyTypeDef(MockConstants.EGERIA_USER, typeDef));
            } catch (InvalidParameterException | RepositoryErrorException | InvalidTypeDefException | TypeDefNotSupportedException e) {
                log.error("Unable to verify type definition: {}", typeDef.getName(), e);
                assertNotNull(e);
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
            assertNotNull(e);
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
            assertNotNull(e);
        }

    }

    private List<TypeDef> findTypeDefsByCategory(TypeDefCategory typeDefCategory) {
        List<TypeDef> typeDefs = null;
        try {
            typeDefs = igcomrsMetadataCollection.findTypeDefsByCategory(MockConstants.EGERIA_USER, typeDefCategory);
        } catch (InvalidParameterException | RepositoryErrorException e) {
            log.error("Unable to search for TypeDefs from category: {}", typeDefCategory.getName(), e);
            assertNotNull(e);
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
     * Test direct type def retrievals
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
            assertNotNull(e);
        }

    }

    /**
     * Test the findEntitiesBy... operations
     */
    @Test
    public void testFindEntityOps() {
        // TODO: write a test against a relatively limited set that can be put easily into mocks
    }

    /**
     * Test the findRelationshipsBy... operations
     */
    @Test
    public void testFindRelationshipOps() {
        // TODO: write a test against a relatively limited set that can be put easily into mocks
    }

    /**
     * Test the RelationalTable mapping
     */
    @Test
    public void testRelationalTableMapper() {

        try {

            EntitySummary entitySummary = igcomrsMetadataCollection.getEntitySummary(MockConstants.EGERIA_USER, exampleTableGuid);
            assertNotNull(entitySummary);

            EntityDetail entityDetail = igcomrsMetadataCollection.isEntityKnown(MockConstants.EGERIA_USER, exampleTableGuid);
            assertNotNull(entityDetail);
            assertEquals(entityDetail.getType().getTypeDefName(), "RelationalTable");
            assertNotNull(entityDetail.getCreateTime());
            assertNotNull(entityDetail.getUpdateTime());
            assertTrue(entityDetail.getVersion() > 0);

            List<Classification> classifications = entityDetail.getClassifications();
            assertFalse(classifications.isEmpty());
            assertEquals(classifications.size(), 1);
            Classification classification = classifications.get(0);
            assertEquals(classification.getType().getTypeDefName(), "TypeEmbeddedAttribute");
            assertNotNull(classification.getCreateTime());
            assertNotNull(classification.getUpdateTime());
            assertTrue(classification.getVersion() > 0);

        } catch (RepositoryErrorException | InvalidParameterException | EntityNotKnownException e) {
            log.error("Unable to retrieve entity detail for: {}", exampleTableGuid);
            assertNull(e);
        } catch (Exception e) {
            log.error("Failed with unexpected exception!", e);
            assertNull(e);
        }

    }

    /**
     * Test the relationships retrieval from an entity
     */
    @Test
    public void testRelationalTableRelationships() {

        List<Relationship> relationships = null;

        try {

            relationships = igcomrsMetadataCollection.getRelationshipsForEntity(
                    MockConstants.EGERIA_USER,
                    exampleTableGuid,
                    null,
                    0,
                    null,
                    null,
                    null,
                    null,
                    0
            );

        } catch (RepositoryErrorException | InvalidParameterException | EntityNotKnownException e) {
            log.error("Unable to retrieve relationships for: {}", exampleTableGuid);
            assertNull(e);
        } catch (Exception e) {
            log.error("Failed with unexpected exception!", e);
            assertNull(e);
        }

        assertNotNull(relationships);
        assertFalse(relationships.isEmpty());

        List<String> relationshipTypes = relationships.stream().map(Relationship::getType).map(InstanceType::getTypeDefName).collect(Collectors.toList());
        assertTrue(relationshipTypes.contains("AttributeForSchema"));
        assertTrue(relationshipTypes.contains("NestedSchemaAttribute"));

        String relationshipGuid = relationships.get(0).getGUID();
        try {
            Relationship retrievedByGuid = igcomrsMetadataCollection.getRelationship(MockConstants.EGERIA_USER, relationshipGuid);
            assertNotNull(retrievedByGuid);
        } catch (InvalidParameterException | RepositoryErrorException | RelationshipNotKnownException e) {
            log.error("Unable to retrieve relationship by GUID: {}", relationshipGuid);
            assertNull(e);
        } catch (Exception e) {
            log.error("Failed with unexpected exception!", e);
            assertNull(e);
        }

    }

    @AfterSuite
    void stopConnector() {
        igcomrsRepositoryConnector.disconnect();
    }

}
