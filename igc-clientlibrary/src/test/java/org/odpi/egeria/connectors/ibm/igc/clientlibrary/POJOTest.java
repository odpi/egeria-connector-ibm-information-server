/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.mocks.MockIGCClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.mocks.MockTerm;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class POJOTest {

    private IGCRestClient igcRestClient;
    private Reference mockObject;

    public POJOTest() {
        igcRestClient = new MockIGCClient();
        mockObject = new MockTerm();
    }

    @Test
    public void testSerDe() {
        String sampleObjectAsString = igcRestClient.getValueAsJSON(mockObject);
        assertNotNull(sampleObjectAsString);
        Reference readBack = igcRestClient.readJSONIntoPOJO(sampleObjectAsString);
        assertNotNull(readBack);
        assertEquals(sampleObjectAsString, igcRestClient.getValueAsJSON(readBack));
    }

    @Test
    public void testIdentity() {
        Identity identity = mockObject.getIdentity(igcRestClient);
        assertNotNull(identity);
        assertEquals(identity.getAssetType(), "term");
        assertEquals(identity.getName(), "Sample Term");
        Identity parent = identity.getParentIdentity();
        assertNotNull(parent);
        assertEquals(parent.getAssetType(), "category");
        assertEquals(parent.getName(), "Organization");
        Identity ultimateParent = identity.getUltimateParentIdentity();
        assertNotNull(ultimateParent);
        assertEquals(ultimateParent.getAssetType(), "category");
        assertEquals(ultimateParent.getName(), "Coco Pharmaceuticals");
    }

    @Test
    public void testDynamicAccessor() {
        Object name = igcRestClient.getPropertyByName(mockObject, "_name");
        assertTrue(name instanceof String);
        assertEquals((String) name, "Sample Term");
        Object parentCategory = igcRestClient.getPropertyByName(mockObject, "parent_category");
        assertTrue(parentCategory instanceof Category);
        assertEquals( ((Category) parentCategory).getName(), "Organization");
    }

    @Test
    public void testGetContext() {
        Reference asIs = igcRestClient.getModificationDetails(mockObject);
        assertEquals(asIs, mockObject);
    }

}
