/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.http.HttpHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.util.UriUtils;
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
        try {
            igcRestClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @BeforeSuite
    public void startClient() {
        try {
            assertTrue(igcRestClient.start());
            assertFalse(igcRestClient.isWorkflowEnabled());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
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
        IGCRestClient invalid = null;
        try {
            invalid = new IGCRestClient("127.0.0.1", "80", "admin", "admin");
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
        assertThrows(IGCConnectivityException.class, invalid::start);
    }

    @Test
    public void testInvalidCookies() {
        IGCRestClient invalidClient = null;
        try {
            invalidClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
        IGCSearch igcSearch = new IGCSearch("security_test");
        igcSearch.setPageSize(1);
        IGCRestClient finalInvalidClient = invalidClient;
        assertThrows(IGCConnectivityException.class, () -> finalInvalidClient.search(igcSearch));
    }

    @Test
    public void testUnknownCookies() {
        IGCRestClient invalidClient = null;
        try {
            invalidClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
        IGCSearch igcSearch = new IGCSearch("security_test");
        igcSearch.setPageSize(2);
        IGCRestClient finalInvalidClient = invalidClient;
        assertThrows(IGCConnectivityException.class, () -> finalInvalidClient.search(igcSearch));
    }

    @Test
    public void testInsecureCookies() {
        IGCRestClient invalidClient = null;
        try {
            invalidClient = new IGCRestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
        IGCSearch igcSearch = new IGCSearch("security_test");
        igcSearch.setPageSize(3);
        IGCRestClient finalInvalidClient = invalidClient;
        assertThrows(IGCConnectivityException.class, () -> finalInvalidClient.search(igcSearch));
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
        try {
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
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testNotes() {

        try {
            Reference testNote = igcRestClient.getAssetById(MockConstants.NOTE_RID);
            assertNotNull(testNote);
            assertTrue(testNote instanceof Note);
            Note note = (Note) testNote;
            assertEquals(note.getTheType(), "Informational");
            assertEquals(note.getType(), "note");
            assertTrue(note.getNote().startsWith("Just an"));
            assertEquals(note.getSubject(), "This is the subject");
            assertEquals(note.getStatus(), "Open");
            Reference related = note.getBelongingTo();
            assertNotNull(related);
            assertEquals(related.getType(), "term");
            assertEquals(related.getName(), "TestTerm");

            Reference testTerm = igcRestClient.getAssetById(MockConstants.TERM_WITH_NOTES_RID);
            assertNotNull(testTerm);
            assertTrue(testTerm instanceof Term);
            Term term = (Term) testTerm;
            ItemList<Note> notes = term.getNotes();
            assertNotNull(notes);
            List<Note> list = notes.getItems();
            assertEquals(list.size(), 2);
            assertEquals(list.get(1).getSubject(), "This is the subject");
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }

    }

    @Test
    public void testEmptyTypeDetails() {
        try {
            assertTrue(igcRestClient.getAllPropertiesForType(null).isEmpty());
            assertTrue(igcRestClient.getNonRelationshipPropertiesForType(null).isEmpty());
            assertTrue(igcRestClient.getAllStringPropertiesForType(null).isEmpty());
            assertTrue(igcRestClient.getPagedRelationshipPropertiesForType(null).isEmpty());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testFullAssetRetrievalAndSerDe() {

        try {
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

            Identity identity = category.getIdentity(igcRestClient, new ObjectCache());
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
            Category withModDetails = igcRestClient.getModificationDetails(category, new ObjectCache());
            assertEquals(withModDetails.getCreatedBy(), "Administrator IIS");
            assertNotNull(withModDetails.toString());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }

    }

    @Test
    public void testPartialAssetRetrievalAndSerDe() {
        try {
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
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testAssetRefRetrievalAndSerDe() {
        try {
            Reference testPart = igcRestClient.getAssetRefById(MockConstants.GLOSSARY_RID);
            assertNotNull(testPart);
            assertTrue(testPart instanceof Category);
            assertEquals(testPart.getName(), MockConstants.GLOSSARY_NAME);
            assertNotNull(testPart.toString());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testContextAndIdentity() {

        // Test that we can identify the difference between an unpopulated context (null) and an empty context ([])
        String plainRef = "{\"_name\":\"TestTerm\",\"_type\":\"term\",\"_id\":\"6662c0f2.e1b1ec6c.00270n9bc.9a0o5ur.hsbem7.o7tuf0mn7hgv85dv4s707\",\"_url\":\"https://infosvr:9446/ibm/iis/igc-rest/v1/assets/6662c0f2.e1b1ec6c.00270n9bc.9a0o5ur.hsbem7.o7tuf0mn7hgv85dv4s707\"}";
        String refWithEmptyCtx = "{\"_name\":\"TestTerm\",\"_type\":\"term\",\"_id\":\"6662c0f2.e1b1ec6c.00270n9bc.9a0o5ur.hsbem7.o7tuf0mn7hgv85dv4s707\",\"_url\":\"https://infosvr:9446/ibm/iis/igc-rest/v1/assets/6662c0f2.e1b1ec6c.00270n9bc.9a0o5ur.hsbem7.o7tuf0mn7hgv85dv4s707\",\"_context\":[]}";

        try {
            Reference testPlain = igcRestClient.readJSONIntoPOJO(plainRef);
            assertTrue(testPlain instanceof Term);
            assertNull(testPlain.getContext());
            Term plain = (Term) testPlain;
            assertNull(plain.getContext());
            Reference testWithEmptyCtx = igcRestClient.readJSONIntoPOJO(refWithEmptyCtx);
            assertTrue(testWithEmptyCtx instanceof Term);
            assertNotNull(testWithEmptyCtx.getContext());
            Term empty = (Term) testWithEmptyCtx;
            assertNotNull(empty.getContext());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }

    }

    @Test
    public void testSearchAndPaging() {

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition("name", "like %{0}%", "address");
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        IGCSearch igcSearch = new IGCSearch("term", igcSearchConditionSet);
        igcSearch.addProperties(IGCRestConstants.getModificationProperties());
        igcSearch.setPageSize(2);
        igcSearch.addSortingCriteria(new IGCSearchSorting("_id", true));

        try {
            // First search and get all pages in one go
            ItemList<Term> results = igcRestClient.search(igcSearch);
            assertNotNull(results);
            assertEquals(results.getPaging().getNumTotal().intValue(), 6);
            assertTrue(results.hasMorePages());
            assertNotNull(results.getItems());
            assertEquals(results.getItems().size(), 2);
            List<Term> allPages = igcRestClient.getAllPages(null, results);
            results.setAllPages(allPages);
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
            assertEquals(results.getItems().get(0).getId(), "6662c0f2.e1b1ec6c.00263shl8.8c6cjg1.thoiqd.g2jiimda7gvarsup8a3bb");
            ItemList<Term> nextPage = igcRestClient.getNextPage(null, results);
            assertNotNull(nextPage);
            assertNotNull(nextPage.getItems());
            assertEquals(nextPage.getItems().size(), 2);
            // TODO: ensure that this next page of results contains different terms than the first page
            //assertNotEquals(results.getItems().get(0).getId(), "6662c0f2.e1b1ec6c.00263shl8.8c6cjg1.thoiqd.g2jiimda7gvarsup8a3bb");
            paging = nextPage.getPaging();
            assertNotNull(paging.getPreviousPageURL());
            assertEquals(paging.getPageSize().intValue(), 2);
            assertEquals(paging.getBeginIndex().intValue(), 2);
            assertEquals(paging.getEndIndex().intValue(), 3);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }

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

        try {
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
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }

    }

    @Test
    public void testExistingBundles() {
        try {
            List<String> bundles = igcRestClient.getOpenIgcBundles();
            assertNotNull(bundles);
            assertTrue(bundles.contains("OMRS"));
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testCreate() {
        try {
            IGCCreate igcCreate = new IGCCreate("term");
            igcCreate.addProperty("name", "Test Term");
            igcCreate.addProperty("status", "CANDIDATE");
            igcCreate.addProperty("parent_category", "6662c0f2.ee6a64fe.001ms73o0.ft1a1dd.er0dsi.i5q6hj16mo65b060fndnp");
            assertTrue(igcCreate.hasProperty("name"));
            String rid = igcRestClient.create(igcCreate);
            assertNotNull(rid);
            assertEquals(rid, MockConstants.RID_FOR_CREATE_AND_UPDATE);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testUpdate() {
        try {
            IGCUpdate igcUpdate = new IGCUpdate(MockConstants.RID_FOR_CREATE_AND_UPDATE);
            igcUpdate.addProperty("short_description", "Just a test short description.");
            igcUpdate.addRelationship("assigned_to_terms", MockConstants.TERM_RID);
            igcUpdate.setRelationshipUpdateMode(IGCUpdate.UpdateMode.APPEND);
            igcUpdate.addExclusiveRelationship("parent_category", MockConstants.CATEGORY_RID);
            boolean result = igcRestClient.update(igcUpdate);
            assertTrue(result);
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }
    }

    @Test
    public void testOpenIGCBundle() {

        ClassPathResource directory = new ClassPathResource("pretendBundle");
        try {
            File bundle = igcRestClient.createOpenIgcBundleFile(directory.getFile());
            igcRestClient.upsertOpenIgcBundle("TestBundle", new FileSystemResource(bundle));
        } catch (IOException e) {
            assertNull(e, e.getMessage());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
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

        try {
            Reference reference = igcRestClient.getAssetById(MockConstants.VIRTUAL_ASSET_TABLE_RID);
            assertTrue(reference instanceof DatabaseTable);
            DatabaseTable table = (DatabaseTable) reference;
            ItemList<DatabaseColumn> columns = table.getDatabaseColumns();
            assertEquals(columns.getItems().size(), 5);
            assertTrue(columns.hasMorePages());
            ItemList<DatabaseColumn> nextPage = igcRestClient.getNextPage("database_columns", columns);
            assertEquals(nextPage.getItems().size(), 3);
            assertFalse(nextPage.hasMorePages());
        } catch (IGCException e) {
            assertNull(e, "Fatal error interacting with IGC.");
        }

    }

    @Test
    public void testRIDEncoding() {

        String rid = "deflated:c2e76d84.78bf4d29.66k77fg69.pm57036.72jftc.1a947bnehtr3q8ajjieqj;column_id;-2855024043478117416";
        String encoded = igcRestClient.getEncodedPathVariable(rid);
        assertEquals(encoded, "deflated%3Ac2e76d84.78bf4d29.66k77fg69.pm57036.72jftc.1a947bnehtr3q8ajjieqj%3Bcolumn_id%3B-2855024043478117416");

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
