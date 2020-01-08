/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.http.HttpHelper;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdentityTest {

    private IGCRestClient igcRestClient;
    private String FULL_IDENTITY_STRING = "(host_(engine))=INFOSVR::(database)=SOMETHING::(database_schema)=ELSE::(database_table)=TABLE::(database_column)=COLUMN";

    public IdentityTest() {
        HttpHelper.noStrictSSL();
        igcRestClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
    }

    @BeforeSuite
    void startClient() {
        assertTrue(igcRestClient.start());
    }

    @Test
    public void testConfidenceInStringAsIdentity() {

        assertEquals(Identity.isIdentityString(FULL_IDENTITY_STRING), 6);

    }

    @Test
    public void testFromStringFull() {

        Identity full = Identity.getFromString(FULL_IDENTITY_STRING, igcRestClient, Identity.StringType.EXACT, false);
        assertNotNull(full);
        assertFalse(full.isPartial());
        assertEquals(full.getAssetType(), "database_column");
        assertEquals(full.getName(), "COLUMN");
        assertEquals(full.toString(), FULL_IDENTITY_STRING);

        Identity parent = full.getParentIdentity();
        assertNotNull(parent);
        assertFalse(parent.isPartial());
        assertEquals(parent.getAssetType(), "database_table");
        assertEquals(parent.getName(), "TABLE");

        Identity ultimateFromBottom = full.getUltimateParentIdentity();
        assertNotNull(ultimateFromBottom);
        assertFalse(ultimateFromBottom.isPartial());
        assertEquals(ultimateFromBottom.getAssetType(), "host_(engine)");
        assertEquals(ultimateFromBottom.getName(), "INFOSVR");
        Identity ultimateFromParent = parent.getUltimateParentIdentity();
        assertEquals(ultimateFromBottom, ultimateFromParent);

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

    @Test
    public void testSearchCriteriaBuild() {

        String dataFileExample = "(host_(engine))=INFOSVR::(data_file_folder)=/::(data_file_folder)=data::(data_file_folder)=somewhere::(data_file)=FileName.csv::(data_file_record)=FileName";
        String userExample = "(steward_user)=Ms. Firstname Surname";

        Identity full = Identity.getFromString(FULL_IDENTITY_STRING, igcRestClient, Identity.StringType.EXACT, false);
        IGCSearchConditionSet conditions = full.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 5);

        Identity fileRecord = Identity.getFromString(dataFileExample, igcRestClient, Identity.StringType.EXACT, false);
        conditions = fileRecord.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 4);

        Identity user = Identity.getFromString(userExample, igcRestClient, Identity.StringType.EXACT, false);
        conditions = user.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 1);

    }

    @Test
    public void testSearchCriteriaEdges() {

        String userExamplePart1 = "(non_steward_user)=Firstname";
        String userExamplePart2 = "(steward_user)=Ms. Firstna";
        String dataFileFolder = "(host_(engine))=INFOSVR::(data_file_folder)=/::(data_file_folder)=data";
        String term = "(category)=Some Category::(term)=Some Term";

        Identity part = Identity.getFromString(userExamplePart1, igcRestClient, Identity.StringType.STARTS_WITH, false);
        assertNotNull(part);
        IGCSearchConditionSet conditions = part.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 1);
        assertTrue(conditions.getConditionSetObject().toString().contains("courtesy_title"));

        part = Identity.getFromString(userExamplePart2, igcRestClient, Identity.StringType.STARTS_WITH, false);
        assertNotNull(part);
        conditions = part.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 1);
        assertTrue(conditions.getConditionSetObject().toString().contains("surname"));

        Identity full = Identity.getFromString(dataFileFolder, igcRestClient, Identity.StringType.EXACT, false);
        assertNotNull(full);
        conditions = full.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 3);

        full = Identity.getFromString(term, igcRestClient, Identity.StringType.EXACT, false);
        assertNotNull(full);
        conditions = full.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 2);

    }

    @AfterSuite
    void stopClient() {
        igcRestClient.disconnect();
    }

}
