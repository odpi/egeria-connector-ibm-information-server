/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.tests;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.openmetadata.http.HttpHelper;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Test the connector(s) using the mocked server resources.
 */
public class ConnectorTest {

    private IGCRestClient igcRestClient;
    private static final String GLOSSARY_RID = "6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3";

    public ConnectorTest() {
        HttpHelper.noStrictSSL();
        igcRestClient = new IGCRestClient("localhost", "1080", "isadmin", "isadmin");
    }

    @BeforeSuite
    void startClient() {
        assertTrue(igcRestClient.start());
    }

    @Test
    void testFullAssetRetrievalAndSerde() {
        Reference testFull = igcRestClient.getAssetById(GLOSSARY_RID);
        assertNotNull(testFull);
        assertTrue(testFull instanceof Category);
        Category category = (Category) testFull;
        assertEquals(category.getShortDescription(), "This glossary contains Glossary Terms and Categories that are related to the Coco Pharmaceuticals data");
    }

    @Test
    void testPartialAssetRetrievalAndSerde() {
        Reference testPart = igcRestClient.getAssetRefById(GLOSSARY_RID);
        assertNotNull(testPart);
        assertTrue(testPart instanceof Category);
        assertEquals(testPart.getName(), "Coco Pharmaceuticals");
    }

    @AfterSuite
    void stopClient() {
        igcRestClient.disconnect();
    }

}
