/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.RelationalDBSchemaTypeMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCRelationshipGuid;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IGCGuidTest {

    private String METADATA_COL_ID = "5e74232d-92df-4b81-a401-b100dbfea73a";
    private String SAMPLE_RID = "6662c0f2.ee6a64fe.o1h6eveh1.gbvjvq0.ols3j6.0oadmdn8gknhjvmojr3pt";
    private String SAMPLE_ASSET_TYPE = "database_schema";
    private String SAMPLE_PREFIX = RelationalDBSchemaTypeMapper.IGC_RID_PREFIX;
    private String SAMPLE_RELATIONSHIP_TYPE = "AssetSchemaType";

    public IGCGuidTest() {
        // Do nothing...
    }

    @Test
    public void testNullGuids() {
        assertNull(IGCEntityGuid.fromGuid(null));
        assertNull(IGCRelationshipGuid.fromGuid(null));
    }

    @Test
    public void testValidEntityGuidFormats() {

        IGCEntityGuid igcGuid = new IGCEntityGuid(METADATA_COL_ID, SAMPLE_ASSET_TYPE, SAMPLE_RID);
        IGCEntityGuid igcGuidWithPrefix = new IGCEntityGuid(METADATA_COL_ID, SAMPLE_ASSET_TYPE, SAMPLE_PREFIX, SAMPLE_RID);

        // Test metadata collection ID is retained and equal
        assertEquals(igcGuid.getMetadataCollectionId(), METADATA_COL_ID);
        assertEquals(igcGuidWithPrefix.getMetadataCollectionId(), METADATA_COL_ID);

        // Test asset type is retained and equal
        assertEquals(igcGuid.getAssetType(), SAMPLE_ASSET_TYPE);
        assertEquals(igcGuidWithPrefix.getAssetType(), SAMPLE_ASSET_TYPE);

        // Test generated prefix
        assertFalse(igcGuid.isGeneratedEntityGuid());
        assertTrue(igcGuidWithPrefix.isGeneratedEntityGuid());
        assertNull(igcGuid.getGeneratedPrefix());
        assertEquals(igcGuidWithPrefix.getGeneratedPrefix(), SAMPLE_PREFIX);

        // Test RID is retained and equal
        assertEquals(igcGuid.getRid(), SAMPLE_RID);
        assertEquals(igcGuidWithPrefix.getRid(), SAMPLE_RID);

        // Test reversal of the GUID
        String guid = igcGuid.toString();
        assertEquals(guid, igcGuid.toString());
        String guidWithPrefix = igcGuidWithPrefix.toString();
        assertEquals(guidWithPrefix, igcGuidWithPrefix.toString());
        IGCEntityGuid guidReversed = IGCEntityGuid.fromGuid(guid);
        assertTrue(guidReversed.equals(igcGuid));
        assertEquals(guid, guidReversed.toString());
        IGCEntityGuid guidWithPrefixReversed = IGCEntityGuid.fromGuid(guidWithPrefix);
        assertEquals(guidWithPrefix, guidWithPrefixReversed.toString());
        assertTrue(guidWithPrefixReversed.equals(igcGuidWithPrefix));

    }

    @Test
    public void testValidRelationshipGuidFormats() {

        IGCRelationshipGuid igcGuid = new IGCRelationshipGuid(METADATA_COL_ID, SAMPLE_ASSET_TYPE, SAMPLE_ASSET_TYPE, SAMPLE_RID, SAMPLE_RID, SAMPLE_RELATIONSHIP_TYPE);
        IGCRelationshipGuid igcGuidWithPrefix = new IGCRelationshipGuid(METADATA_COL_ID, SAMPLE_ASSET_TYPE, SAMPLE_ASSET_TYPE, null, SAMPLE_PREFIX, SAMPLE_RID, SAMPLE_RID, SAMPLE_RELATIONSHIP_TYPE);

        // Test metadata collection ID is retained and equal
        assertEquals(igcGuid.getMetadataCollectionId(), METADATA_COL_ID);
        assertEquals(igcGuidWithPrefix.getMetadataCollectionId(), METADATA_COL_ID);

        // Test asset type is retained and equal
        assertEquals(igcGuid.getAssetType1(), SAMPLE_ASSET_TYPE);
        assertEquals(igcGuid.getAssetType2(), SAMPLE_ASSET_TYPE);
        assertEquals(igcGuidWithPrefix.getAssetType1(), SAMPLE_ASSET_TYPE);
        assertEquals(igcGuidWithPrefix.getAssetType2(), SAMPLE_ASSET_TYPE);

        // Test generated prefix
        assertNull(igcGuid.getGeneratedPrefix1());
        assertNull(igcGuid.getGeneratedPrefix2());
        assertNull(igcGuidWithPrefix.getGeneratedPrefix1());
        assertEquals(igcGuidWithPrefix.getGeneratedPrefix2(), SAMPLE_PREFIX);

        // Test RID is retained and equal
        assertEquals(igcGuid.getRid1(), SAMPLE_RID);
        assertEquals(igcGuid.getRid2(), SAMPLE_RID);
        assertEquals(igcGuidWithPrefix.getRid1(), SAMPLE_RID);
        assertEquals(igcGuidWithPrefix.getRid2(), SAMPLE_RID);

        // Test reversal of the GUID
        String guid = igcGuid.toString();
        assertEquals(guid, igcGuid.toString());
        String guidWithPrefix = igcGuidWithPrefix.toString();
        assertEquals(guidWithPrefix, igcGuidWithPrefix.toString());
        IGCRelationshipGuid guidReversed = IGCRelationshipGuid.fromGuid(guid);
        assertEquals(guid, guidReversed.toString());
        IGCRelationshipGuid guidWithPrefixReversed = IGCRelationshipGuid.fromGuid(guidWithPrefix);
        assertEquals(guidWithPrefix, guidWithPrefixReversed.toString());

        // Negative tests
        assertNull(RelationshipMapping.getProxyOneGuidFromRelationship(null, null));
        assertNull(RelationshipMapping.getProxyTwoGuidFromRelationship(null, null));

    }

}
