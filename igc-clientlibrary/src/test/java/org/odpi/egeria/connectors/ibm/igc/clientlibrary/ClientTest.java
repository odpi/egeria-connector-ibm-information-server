/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.openmetadata.http.HttpHelper;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Test the connector(s) using the mocked server resources.
 */
public class ClientTest {

    private IGCRestClient igcRestClient;
    private static final String GLOSSARY_RID = "6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3";
    private static final String GLOSSARY_NAME = "Coco Pharmaceuticals";
    private static final String GLOSSARY_DESC = "This glossary contains Glossary Terms and Categories that are related to the Coco Pharmaceuticals data";

    public ClientTest() {
        HttpHelper.noStrictSSL();
        igcRestClient = new IGCRestClient("localhost", "1080", "isadmin", "isadmin");
    }

    @BeforeSuite
    void startClient() {
        assertTrue(igcRestClient.start());
    }

    @Test
    void testVersionRetrieval() {
        assertEquals(igcRestClient.getIgcVersion(), IGCVersionEnum.V11710);
    }

    @Test
    void testBaseURL() {
        assertEquals(igcRestClient.getBaseURL(), "https://localhost:1080");
    }

    @Test
    void testDefaultPageSizeRetrieval() {
        assertEquals(igcRestClient.getDefaultPageSize(), 100);
    }

    @Test
    void testInvalidClient() {
        assertThrows(NullPointerException.class, () -> new IGCRestClient("http://localhost:1080", "isadmin", "isadmin"));
    }

    @Test
    void testTypeDetails() {
        List<String> termProperties = igcRestClient.getAllPropertiesForType("term");
        assertNotNull(termProperties);
        assertTrue(termProperties.contains("long_description"));
        List<String> stringProperties = igcRestClient.getAllStringPropertiesForType("term");
        assertNotNull(stringProperties);
        assertTrue(stringProperties.contains("short_description"));
        List<String> relationshipProperties = igcRestClient.getPagedRelationshipPropertiesForType("term");
        assertNotNull(relationshipProperties);
        assertTrue(relationshipProperties.contains("related_terms"));
        List<String> nonRelationshipProperties = igcRestClient.getNonRelationshipPropertiesForType("term");
        assertNotNull(nonRelationshipProperties);
        assertTrue(nonRelationshipProperties.contains("abbreviation"));
        assertTrue(igcRestClient.hasModificationDetails("term"));
        assertTrue(igcRestClient.isCreatable("term"));
        assertEquals(igcRestClient.getDisplayNameForType("term"), "Term");
    }

    @Test
    void testFullAssetRetrievalAndSerDe() {
        Reference testFull = igcRestClient.getAssetById(GLOSSARY_RID);
        assertNotNull(testFull);
        assertTrue(testFull instanceof Category);
        Category category = (Category) testFull;
        assertEquals(category.getShortDescription(), GLOSSARY_DESC);
        // Note that in this case they will not be equal because there is no context on this root-level category,
        // so a search will always be run and the results with only mod details (and context) returned
        Category withModDetails = igcRestClient.getModificationDetails(category);
        assertEquals(withModDetails.getCreatedBy(), "Administrator IIS");
    }

    @Test
    void testPartialAssetRetrievalAndSerDe() {
        List<String> properties = new ArrayList<>();
        properties.add("short_description");
        Reference testPart = igcRestClient.getAssetWithSubsetOfProperties(GLOSSARY_RID, "category", properties);
        assertNotNull(testPart);
        assertTrue(testPart instanceof Category);
        Category category = (Category) testPart;
        assertEquals(category.getShortDescription(), GLOSSARY_DESC);
        assertEquals(igcRestClient.getPropertyByName(category, "short_description"), GLOSSARY_DESC);
    }

    @Test
    void testAssetRefRetrievalAndSerDe() {
        Reference testPart = igcRestClient.getAssetRefById(GLOSSARY_RID);
        assertNotNull(testPart);
        assertTrue(testPart instanceof Category);
        assertEquals(testPart.getName(), GLOSSARY_NAME);
    }

    @Test
    void testSearchAndPaging() {
        IGCSearchCondition igcSearchCondition = new IGCSearchCondition("_id", "=", GLOSSARY_RID);
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        IGCSearch igcSearch = new IGCSearch("category", igcSearchConditionSet);
        igcSearch.addProperties(IGCRestConstants.getModificationProperties());
        igcSearch.setPageSize(2);
        ItemList<Category> results = igcRestClient.search(igcSearch);
        assertNotNull(results);
        assertTrue(results.getPaging().getNumTotal() == 1);
        List<Category> justResults = igcRestClient.getAllPages(results.getItems(), results.getPaging());
        assertNotNull(justResults);
        assertEquals(justResults.size(), 1);
    }

    @Test
    void testExistingBundles() {
        List<String> bundles = igcRestClient.getOpenIgcBundles();
        assertNotNull(bundles);
        assertTrue(bundles.contains("OMRS"));
    }

    @AfterSuite
    void stopClient() {
        igcRestClient.disconnect();
    }

}
