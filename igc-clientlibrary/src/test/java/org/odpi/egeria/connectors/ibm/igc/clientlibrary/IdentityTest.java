/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.http.HttpHelper;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdentityTest {

    private IGCRestClient igcRestClient;

    public IdentityTest() {
        HttpHelper.noStrictSSL();
        igcRestClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
    }

    @BeforeSuite
    void startClient() {
        assertTrue(igcRestClient.start());
    }

    @Test
    public void testFromStringFull() {

        String fullIdentityString = "(host_(engine))=INFOSVR::(database)=SOMETHING::(database_schema)=ELSE::(database_table)=TABLE::(database_column)=COLUMN";
        Identity full = Identity.getFromString(fullIdentityString, igcRestClient, Identity.StringType.EXACT, false);
        assertNotNull(full);
        assertFalse(full.isPartial());
        assertEquals(full.getAssetType(), "database_column");
        assertEquals(full.getName(), "COLUMN");

    }

    @Test
    public void testFromStringPart() {

        String partIdentityString = "gine))=INFOSVR::(database)=SOME";
        Identity part = Identity.getFromString(partIdentityString, igcRestClient, Identity.StringType.CONTAINS, false);
        assertNotNull(part);
        assertTrue(part.isPartial());
        assertEquals(part.getAssetType(), "database");
        assertEquals(part.getName(), "SOME");

    }

    @Test
    public void testFromStringEdge() {

        String hostEnginePartString = "(engine))=I";
        Identity edge = Identity.getFromString(hostEnginePartString, igcRestClient, Identity.StringType.CONTAINS, false);
        assertNull(edge);

    }

    @AfterSuite
    void stopClient() {
        igcRestClient.disconnect();
    }

}
