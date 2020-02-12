/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.http.HttpHelper;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
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
    public void startClient() {
        assertTrue(igcRestClient.start());
        assertFalse(igcRestClient.isWorkflowEnabled());
    }

    @Test
    public void testVersionRetrieval() {
        assertEquals(igcRestClient.getIgcVersion(), IGCVersionEnum.V11710);
    }

    @Test
    public void testBaseURL() {
        assertEquals(igcRestClient.getBaseURL(), "https://localhost:1080");
    }

    @Test
    public void testInvalidURL() {
        IGCRestClient invalid = new IGCRestClient("127.0.0.1", "80", "admin", "admin");
        assertThrows(IGCConnectivityException.class, invalid::start);
    }

    @Test
    public void testInvalidCookies() {
        IGCRestClient invalidClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        IGCSearch igcSearch = new IGCSearch("security_test");
        igcSearch.setPageSize(1);
        assertThrows(IGCConnectivityException.class, () -> invalidClient.search(igcSearch));
    }

    @Test
    public void testUnknownCookies() {
        IGCRestClient invalidClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        IGCSearch igcSearch = new IGCSearch("security_test");
        igcSearch.setPageSize(2);
        assertThrows(IGCConnectivityException.class, () -> invalidClient.search(igcSearch));
    }

    @Test
    public void testInsecureCookies() {
        IGCRestClient invalidClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        IGCSearch igcSearch = new IGCSearch("security_test");
        igcSearch.setPageSize(3);
        assertThrows(IGCConnectivityException.class, () -> invalidClient.search(igcSearch));
    }

    @Test
    public void testDefaultPageSizeRetrieval() {
        assertEquals(igcRestClient.getDefaultPageSize(), 100);
    }

    @Test
    public void testInvalidClient() {
        assertThrows(IGCConnectivityException.class, () -> new IGCRestClient("http://localhost:1080", MockConstants.IGC_USER, MockConstants.IGC_PASS));
        assertThrows(IGCConnectivityException.class, () -> new IGCRestClient(null, MockConstants.IGC_USER, MockConstants.IGC_PASS));
    }

    @Test
    public void testTypeDetails() {
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
    public void testEmptyTypeDetails() {
        assertTrue(igcRestClient.getAllPropertiesForType(null).isEmpty());
        assertTrue(igcRestClient.getNonRelationshipPropertiesForType(null).isEmpty());
        assertTrue(igcRestClient.getAllStringPropertiesForType(null).isEmpty());
        assertTrue(igcRestClient.getPagedRelationshipPropertiesForType(null).isEmpty());
    }

    @Test
    public void testFullAssetRetrievalAndSerDe() {

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

        String fullAsJSON = igcRestClient.getValueAsJSON(category);
        assertNotNull(fullAsJSON);
        Reference backAgain = igcRestClient.readJSONIntoPOJO(fullAsJSON);
        assertNotNull(backAgain);
        String againAsJSON = igcRestClient.getValueAsJSON(backAgain);
        assertEquals(againAsJSON, fullAsJSON);

        ItemList<Term> terms = category.getTerms();
        assertNotNull(terms);
        assertEquals(terms.getPaging().getNumTotal().intValue(), 42);
        String itemListAsJSON = terms.toString();
        ItemList<Term> listAgain = igcRestClient.readJSONIntoItemList(itemListAsJSON);
        assertNotNull(listAgain);
        assertEquals(listAgain.getPaging().getNumTotal().intValue(), 42);
        String listAgainAsJSON = listAgain.toString();
        assertEquals(listAgainAsJSON, itemListAsJSON);

        // Note that in this case they will not be equal because there is no context on this root-level category,
        // so a search will always be run and the results with only mod details (and context) returned
        Category withModDetails = igcRestClient.getModificationDetails(category);
        assertEquals(withModDetails.getCreatedBy(), "Administrator IIS");
        assertNotNull(withModDetails.toString());

    }

    @Test
    public void testPartialAssetRetrievalAndSerDe() {
        List<String> properties = new ArrayList<>();
        properties.add("short_description");
        Reference testPart = igcRestClient.getAssetWithSubsetOfProperties(MockConstants.GLOSSARY_RID, "category", properties);
        assertNotNull(testPart);
        assertTrue(testPart instanceof Category);
        Category category = (Category) testPart;
        assertEquals(category.getShortDescription(), MockConstants.GLOSSARY_DESC);
        assertEquals(igcRestClient.getPropertyByName(category, "short_description"), MockConstants.GLOSSARY_DESC);
        assertNotNull(category.toString());
        Reference testAgain = igcRestClient.getAssetWithSubsetOfProperties(MockConstants.GLOSSARY_RID, "category", properties.stream().toArray(String[]::new));
        assertEquals(testPart.toString(), testAgain.toString());
        testAgain = igcRestClient.getAssetWithSubsetOfProperties(MockConstants.GLOSSARY_RID, "category", properties, 100);
        assertEquals(testPart.toString(), testAgain.toString());
        testAgain = igcRestClient.getAssetWithSubsetOfProperties(MockConstants.GLOSSARY_RID, "category", properties.stream().toArray(String[]::new), 100);
        assertEquals(testPart.toString(), testAgain.toString());
    }

    @Test
    public void testAssetRefRetrievalAndSerDe() {
        Reference testPart = igcRestClient.getAssetRefById(MockConstants.GLOSSARY_RID);
        assertNotNull(testPart);
        assertTrue(testPart instanceof Category);
        assertEquals(testPart.getName(), MockConstants.GLOSSARY_NAME);
        assertNotNull(testPart.toString());
    }

    @Test
    public void testSearchAndPaging() {

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
    public void testSearchNegationAndSorting() {

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
    public void testExistingBundles() {
        List<String> bundles = igcRestClient.getOpenIgcBundles();
        assertNotNull(bundles);
        assertTrue(bundles.contains("OMRS"));
    }

    @Test
    public void testCreate() {
        IGCCreate igcCreate = new IGCCreate("term");
        igcCreate.addProperty("name", "Test Term");
        igcCreate.addProperty("status", "CANDIDATE");
        igcCreate.addProperty("parent_category", "6662c0f2.ee6a64fe.001ms73o0.ft1a1dd.er0dsi.i5q6hj16mo65b060fndnp");
        assertTrue(igcCreate.hasProperty("name"));
        String rid = igcRestClient.create(igcCreate);
        assertNotNull(rid);
        assertEquals(rid, MockConstants.RID_FOR_CREATE_AND_UPDATE);
    }

    @Test
    public void testUpdate() {
        IGCUpdate igcUpdate = new IGCUpdate(MockConstants.RID_FOR_CREATE_AND_UPDATE);
        igcUpdate.addProperty("short_description", "Just a test short description.");
        igcUpdate.addRelationship("assigned_to_terms", MockConstants.TERM_RID);
        igcUpdate.setRelationshipUpdateMode(IGCUpdate.UpdateMode.APPEND);
        igcUpdate.addExclusiveRelationship("parent_category", MockConstants.CATEGORY_RID);
        boolean result = igcRestClient.update(igcUpdate);
        assertTrue(result);
    }

    @Test
    public void testOpenIGCBundle() {

        ClassPathResource directory = new ClassPathResource("pretendBundle");
        try {
            File bundle = igcRestClient.createOpenIgcBundleFile(directory.getFile());
            igcRestClient.upsertOpenIgcBundle("TestBundle", new FileSystemResource(bundle));
        } catch (IOException e) {
            assertNull(e, e.getMessage());
        }

    }

    @Test
    public void testInvalidJSON() {

        String brokenAssetJSON = "{\"_type\":\"term\",\"_id\":\"123\",\"_name\":\"BrokenTestTerm\"";
        assertThrows(IGCParsingException.class, () -> igcRestClient.readJSONIntoPOJO(brokenAssetJSON));
        assertThrows(IGCParsingException.class, () -> igcRestClient.readJSONIntoItemList(brokenAssetJSON));

    }

    @Test
    public void testVirtualAssets() {

        String virtualAssetRid = "extern:fr:123";
        String realAssetRid = "abc123.def456";
        assertTrue(IGCRestClient.isVirtualAssetRid(virtualAssetRid));
        assertTrue(IGCRestClient.isVirtualAssetRid(null));
        assertFalse(IGCRestClient.isVirtualAssetRid(realAssetRid));

    }

    @AfterSuite
    void stopClient() {
        igcRestClient.disconnect();
    }

}
