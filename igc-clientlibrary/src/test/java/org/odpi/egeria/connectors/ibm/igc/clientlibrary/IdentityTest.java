/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
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
    private String FULL_IDENTITY_STRING = "(host)=INFOSVR::(database)=SOMETHING::(database_schema)=ELSE::(database_table)=TABLE::(database_column)=COLUMN";
    private String PART_IDENTITY_STRING = "ost)=INFOSVR::(database)=SOME";
    private String PART_IDENTITY_STRING2 = "tabase)=COMPDIR::(database_schema)=DB2INST1::(database_table)=CONTACTEMAIL::(database_column)=EMAIL";
    private String HOST_PART_IDT_STRING = "(engine))=I";
    private String DATA_FILE_EXAMPLE = "(host)=INFOSVR::(data_file_folder)=/::(data_file_folder)=data::(data_file_folder)=somewhere::(data_file)=FileName.csv::(data_file_record)=FileName";
    private String USER_EXAMPLE = "(steward_user)=Ms. Firstname Surname";
    private String USER_EXAMPLE_PART1 = "(non_steward_user)=Firstname";
    private String USER_EXAMPLE_PART2 = "(steward_user)=Ms. Firstna";
    private String DATA_FILE_FOLDER_EXAMPLE = "(host)=INFOSVR::(data_file_folder)=/::(data_file_folder)=data";
    private String TERM_EXAMPLE = "(category)=Some Category::(term)=Some Term";
    private String NON_IDENTITY = "JustAString";
    private String TYPE_ONLY = "(category)";

    public IdentityTest() {
        HttpHelper.noStrictSSL();
        try {
            igcRestClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @BeforeSuite
    void startClient() {
        try {
            assertTrue(igcRestClient.start());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testConfidenceInStringAsIdentity() {

        assertEquals(Identity.isIdentityString(FULL_IDENTITY_STRING), 6);
        assertEquals(Identity.isIdentityString(PART_IDENTITY_STRING), 6);
        assertEquals(Identity.isIdentityString(HOST_PART_IDT_STRING), 4);
        assertEquals(Identity.isIdentityString(DATA_FILE_EXAMPLE), 6);
        assertEquals(Identity.isIdentityString(USER_EXAMPLE), 4);
        assertEquals(Identity.isIdentityString(USER_EXAMPLE_PART1), 4);
        assertEquals(Identity.isIdentityString(USER_EXAMPLE_PART2), 4);
        assertEquals(Identity.isIdentityString(DATA_FILE_FOLDER_EXAMPLE), 6);
        assertEquals(Identity.isIdentityString(TERM_EXAMPLE), 6);
        assertEquals(Identity.isIdentityString(NON_IDENTITY), 0);
        assertEquals(Identity.isIdentityString(TYPE_ONLY), 2);

    }

    @Test
    public void testFromStringFull() {

        Identity full = Identity.getFromString(FULL_IDENTITY_STRING, igcRestClient, Identity.StringType.EXACT);
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
        assertEquals(ultimateFromBottom.getAssetType(), "host");
        assertEquals(ultimateFromBottom.getName(), "INFOSVR");
        Identity ultimateFromParent = parent.getUltimateParentIdentity();
        assertEquals(ultimateFromBottom, ultimateFromParent);

        assertEquals(ultimateFromBottom.getUltimateParentIdentity(), ultimateFromBottom);
        assertNull(ultimateFromBottom.getParentIdentity());
        assertNull(ultimateFromBottom.getRid());

    }

    @Test
    public void testFromStringPart() {

        Identity part = Identity.getFromString(PART_IDENTITY_STRING, igcRestClient, Identity.StringType.CONTAINS);
        assertNotNull(part);
        assertTrue(part.isPartial());
        assertEquals(part.getAssetType(), "database");
        assertEquals(part.getName(), "SOME");

        part = Identity.getFromString(PART_IDENTITY_STRING2, igcRestClient, Identity.StringType.ENDS_WITH);
        assertNotNull(part);
        assertTrue(part.isPartial());
        assertEquals(part.getAssetType(), "database_column");
        assertEquals(part.getName(), "EMAIL");
        assertEquals(part.toString(), "(database_schema)=DB2INST1::(database_table)=CONTACTEMAIL::(database_column)=EMAIL");

    }

    @Test
    public void testFromStringEdge() {

        Identity edge = Identity.getFromString(HOST_PART_IDT_STRING, igcRestClient, Identity.StringType.CONTAINS);
        assertNull(edge);

        edge = Identity.getFromString(NON_IDENTITY, igcRestClient, Identity.StringType.ENDS_WITH);
        assertNull(edge);

        edge = Identity.getFromString(TYPE_ONLY, igcRestClient, Identity.StringType.STARTS_WITH);
        assertNotNull(edge);
        assertEquals(edge.getAssetType(), "category");

    }

    @Test
    public void testSearchCriteriaBuild() {

        Identity full = Identity.getFromString(FULL_IDENTITY_STRING, igcRestClient, Identity.StringType.EXACT);
        IGCSearchConditionSet conditions = full.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 5);

        Identity fileRecord = Identity.getFromString(DATA_FILE_EXAMPLE, igcRestClient, Identity.StringType.EXACT);
        conditions = fileRecord.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 4);

        Identity user = Identity.getFromString(USER_EXAMPLE, igcRestClient, Identity.StringType.EXACT);
        conditions = user.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 1);

    }

    @Test
    public void testSearchCriteriaEdges() {

        Identity part = Identity.getFromString(USER_EXAMPLE_PART1, igcRestClient, Identity.StringType.STARTS_WITH);
        assertNotNull(part);
        IGCSearchConditionSet conditions = part.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 1);
        assertTrue(conditions.getConditionSetObject().toString().contains("courtesy_title"));

        part = Identity.getFromString(USER_EXAMPLE_PART2, igcRestClient, Identity.StringType.STARTS_WITH);
        assertNotNull(part);
        conditions = part.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 1);
        assertTrue(conditions.getConditionSetObject().toString().contains("surname"));

        Identity full = Identity.getFromString(DATA_FILE_FOLDER_EXAMPLE, igcRestClient, Identity.StringType.EXACT);
        assertNotNull(full);
        conditions = full.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 2);

        full = Identity.getFromString(TERM_EXAMPLE, igcRestClient, Identity.StringType.EXACT);
        assertNotNull(full);
        conditions = full.getSearchCriteria();
        assertNotNull(conditions);
        assertEquals(conditions.size(), 2);

    }

    @AfterSuite
    void stopClient() {
        try {
            igcRestClient.disconnect();
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

}
