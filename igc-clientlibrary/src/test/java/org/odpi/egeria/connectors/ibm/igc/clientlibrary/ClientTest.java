/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Paging;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
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

    public ClientTest() {
        HttpHelper.noStrictSSL();
        igcRestClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
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
        assertThrows(RuntimeException.class, () -> new IGCRestClient("http://localhost:1080", "isadmin", "isadmin"));
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

        Reference testFull = igcRestClient.getAssetById(MockConstants.GLOSSARY_RID);
        assertNotNull(testFull);
        assertTrue(testFull instanceof Category);
        Category category = (Category) testFull;
        assertEquals(category.getShortDescription(), MockConstants.GLOSSARY_DESC);
        category.setFullyRetrieved();
        assertTrue(category.isFullyRetrieved());
        assertTrue(category.areModificationDetailsPopulated());
        assertFalse(Reference.isSimpleType(category));
        assertNotNull(category.getCreatedBy());
        assertNotNull(category.getCreatedOn());
        assertNotNull(category.getModifiedBy());
        assertNotNull(category.getModifiedOn());

        Identity identity = category.getIdentity(igcRestClient);
        assertNotNull(identity);

        // Note that in this case they will not be equal because there is no context on this root-level category,
        // so a search will always be run and the results with only mod details (and context) returned
        Category withModDetails = igcRestClient.getModificationDetails(category);
        assertEquals(withModDetails.getCreatedBy(), "Administrator IIS");
        assertNotNull(withModDetails.toString());

    }

    @Test
    void testPartialAssetRetrievalAndSerDe() {
        List<String> properties = new ArrayList<>();
        properties.add("short_description");
        Reference testPart = igcRestClient.getAssetWithSubsetOfProperties(MockConstants.GLOSSARY_RID, "category", properties);
        assertNotNull(testPart);
        assertTrue(testPart instanceof Category);
        Category category = (Category) testPart;
        assertEquals(category.getShortDescription(), MockConstants.GLOSSARY_DESC);
        assertEquals(igcRestClient.getPropertyByName(category, "short_description"), MockConstants.GLOSSARY_DESC);
        assertNotNull(category.toString());
    }

    @Test
    void testAssetRefRetrievalAndSerDe() {
        Reference testPart = igcRestClient.getAssetRefById(MockConstants.GLOSSARY_RID);
        assertNotNull(testPart);
        assertTrue(testPart instanceof Category);
        assertEquals(testPart.getName(), MockConstants.GLOSSARY_NAME);
        assertNotNull(testPart.toString());
    }

    @Test
    void testSearchAndPaging() {

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition("name", "like %{0}%", "address");
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        IGCSearch igcSearch = new IGCSearch("term", igcSearchConditionSet);
        igcSearch.addProperties(IGCRestConstants.getModificationProperties());
        igcSearch.setPageSize(2);

        // First search and get all pages in one go
        ItemList<Term> results = igcRestClient.search(igcSearch);
        assertNotNull(results);
        assertEquals(results.getPaging().getNumTotal().intValue(), 6);
        assertTrue(results.hasMorePages());
        assertNotNull(results.getItems());
        assertEquals(results.getItems().size(), 2);
        results.getAllPages(igcRestClient);
        assertNotNull(results);
        assertNotNull(results.getItems());
        assertEquals(results.getItems().size(), 6);

        // Then search and get just the second page of results
        results = igcRestClient.search(igcSearch);
        assertNotNull(results);
        Paging paging = results.getPaging();
        assertEquals(paging.getNumTotal().intValue(), 6);
        assertTrue(results.hasMorePages());
        assertNotNull(results.getItems());
        assertEquals(results.getItems().size(), 2);
        results.getNextPage(igcRestClient);
        assertNotNull(results);
        assertNotNull(results.getItems());
        assertEquals(results.getItems().size(), 2);
        paging = results.getPaging();
        assertNotNull(paging.getPreviousPageURL());
        assertEquals(paging.getPageSize().intValue(), 2);
        assertEquals(paging.getBeginIndex().intValue(), 2);
        assertEquals(paging.getEndIndex().intValue(), 3);

    }

    @Test
    void testSearchNegationAndSorting() {

        IGCSearchSorting igcSearchSorting = new IGCSearchSorting("name");

        List<String> listOfValues = new ArrayList<>();
        listOfValues.add("Address Line 2");
        IGCSearchCondition desc = new IGCSearchCondition("short_description", "=", "", true);
        IGCSearchCondition name = new IGCSearchCondition("name", listOfValues, false);
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(desc);
        igcSearchConditionSet.addCondition(name);

        IGCSearch igcSearch = new IGCSearch("term", igcSearchConditionSet);
        igcSearch.addProperties(IGCRestConstants.getModificationProperties());
        igcSearch.setPageSize(2);
        igcSearch.addSortingCriteria(igcSearchSorting);
        ItemList<Term> results = igcRestClient.search(igcSearch);
        assertNotNull(results);
        assertFalse(results.getItems().isEmpty());
        assertEquals(results.getItems().size(), 1);

        igcSearchConditionSet.setNegateAll(true);
        igcSearch = new IGCSearch("term", igcSearchConditionSet);
        igcSearch.addProperties(IGCRestConstants.getModificationProperties());
        igcSearch.setPageSize(2);
        igcSearch.addSortingCriteria(igcSearchSorting);
        results = igcRestClient.search(igcSearch);
        assertNotNull(results);
        assertTrue(results.getItems().isEmpty());

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
