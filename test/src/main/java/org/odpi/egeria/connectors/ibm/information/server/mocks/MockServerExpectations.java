/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.information.server.mocks;

import org.mockserver.client.MockServerClient;
import org.mockserver.client.initialize.ExpectationInitializer;
import org.mockserver.matchers.MatchType;
import org.mockserver.matchers.Times;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;
import static org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants.*;

/**
 * Setup a mock server to act as an IGC REST API endpoint against which we can do some thorough testing.
 */
public class MockServerExpectations implements ExpectationInitializer {

    private static final Logger log = LoggerFactory.getLogger(MockServerExpectations.class);

    /**
     * Setup the expectations we will need to respond to various tests.
     * @param mockServerClient the client against which to set the expectations
     */
    public void initializeExpectations(MockServerClient mockServerClient) {

        initializeTypeDetails(mockServerClient);
        initializeIGCClientExpectations(mockServerClient);
        initializeExampleInstances(mockServerClient);
        initializeIGCConnectorExpectations(mockServerClient);
        initializeDataStageConnectorExpectations(mockServerClient);

        setDefaultSearchResponseToNoResults(mockServerClient);

    }

    private void initializeTypeDetails(MockServerClient mockServerClient) {

        setTypesQuery(mockServerClient);
        Resource[] typeFiles = getFilesMatchingPattern("types/*.json");
        if (typeFiles != null) {
            for (Resource typeFile : typeFiles) {
                setTypeDetails(mockServerClient, typeFile.getFilename());
            }
        }

    }

    private void initializeIGCClientExpectations(MockServerClient mockServerClient) {

        setStartupQuery(mockServerClient);
        setTypesQuery(mockServerClient);
        setBundlesQuery(mockServerClient);

        String glossaryIgcType = "category";
        setExamplePartAsset(mockServerClient, glossaryIgcType, MockConstants.GLOSSARY_RID);
        setExampleAssetWithModDetails(mockServerClient, glossaryIgcType, MockConstants.GLOSSARY_RID);

        setLogout(mockServerClient);

    }

    private void initializeExampleInstances(MockServerClient mockServerClient) {

        Resource[] instanceExamples = getFilesMatchingPattern("by_rid/**/*.json");
        if (instanceExamples != null) {
            for (Resource instanceExample : instanceExamples) {
                setDetailsByRidQuery(mockServerClient, instanceExample);
            }
        }

        Resource[] childExamples = getFilesMatchingPattern("by_child_rid/**/*.json");
        if (childExamples != null) {
            for (Resource relationshipExample : childExamples) {
                setDetailsByChildRidQuery(mockServerClient, relationshipExample);
            }
        }

        Resource[] parentExamples = getFilesMatchingPattern("by_parent_rid/**/*.json");
        if (parentExamples != null) {
            for (Resource relationshipExample : parentExamples) {
                setDetailsByParentRidQuery(mockServerClient, relationshipExample);
            }
        }

        Resource[] referenceExamples = getFilesMatchingPattern("ref_by_rid/*.json");
        if (referenceExamples != null) {
            for (Resource referenceExample : referenceExamples) {
                setReferenceByRidQuery(mockServerClient, referenceExample);
            }
        }

        Resource[] fullExamples = getFilesMatchingPattern("full_by_rid/*.json");
        if (fullExamples != null) {
            for (Resource fullExample : fullExamples) {
                setFullByRidQuery(mockServerClient, fullExample);
            }
        }

    }

    private void initializeIGCConnectorExpectations(MockServerClient mockServerClient) {

        setUploadBundle(mockServerClient);

        // Setup responses for specific test cases

        // Glossary tests
        setGlossaryQueryByPropertyValue(mockServerClient);
        setCategoriesInGlossary(mockServerClient);
        setTermsInGlossary(mockServerClient);

        // GlossaryCategory tests
        setCategoryQueryByPropertyValue(mockServerClient);
        setParentCategoryQuery(mockServerClient);
        setTermsInCategory(mockServerClient);

    }

    private void initializeDataStageConnectorExpectations(MockServerClient mockServerClient) {

        setJobSyncRuleQueryEmpty(mockServerClient);
        setJobSyncRuleQueryFull(mockServerClient);

        setJobChangeQuery(mockServerClient);

    }

    private void setStartupQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(searchRequest("{\"types\":[\"category\",\"term\",\"information_governance_policy\",\"information_governance_rule\"],\"pageSize\":1,\"workflowMode\":\"draft\"}"))
                .respond(withResponse(getResourceFileContents("no_results.json")));
    }

    private void setTypesQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(typesRequest())
                .respond(withResponse(getResourceFileContents("types.json")));
    }

    private void setBundlesQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(bundlesRequest())
                .respond(withResponse("[\"OMRS\"]"));
    }

    private void setTypeDetails(MockServerClient mockServerClient, String typeFilename) {
        String typeName = typeFilename.substring(0, typeFilename.indexOf(".json"));
        mockServerClient
                .withSecure(true)
                .when(typesRequest(typeName)
                                .withQueryStringParameters(
                                        param("showViewProperties", "true"),
                                        param("showCreateProperties", "true"),
                                        param("showEditProperties", "true")
                                )
                )
                .respond(withResponse(getResourceFileContents("types" + File.separator + typeName + ".json")));
    }

    private void setExamplePartAsset(MockServerClient mockServerClient, String type, String rid) {
        mockServerClient
                .withSecure(true)
                .when(searchRequest("{\"types\":[\"" + type + "\"],\"properties\":[\"short_description\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}],\"operator\":\"and\"}}"))
                .respond(withResponse(getResourceFileContents("rid_part_" + rid + ".json")));
    }

    private void setExampleAssetWithModDetails(MockServerClient mockServerClient, String type, String rid) {
        mockServerClient
                .withSecure(true)
                .when(searchRequest("{\"types\":[\"" + type + "\"],\"properties\":[\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":2,\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}],\"operator\":\"and\"}}"))
                .respond(withResponse(getResourceFileContents("rid_mod_" + rid + ".json")));
    }

    private void setLogout(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(logoutRequest())
                .respond(response().withStatusCode(200));
    }

    private void setUploadBundle(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(upsertBundleRequest())
                .respond(response().withStatusCode(200));
    }

    private void setJobSyncRuleQueryEmpty(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(jobSyncRuleRequest(), Times.exactly(1))
                .respond(withResponse(getResourceFileContents("no_results.json")));
    }

    private void setJobSyncRuleQueryFull(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(jobSyncRuleRequest())
                .respond(withResponse(getResourceFileContents("job_sync_rule.json")));
    }

    private void setJobChangeQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(searchRequest(
                        json(
                                "{\"types\":[\"dsjob\"],\"properties\":[\"short_description\",\"long_description\",\"references_local_or_shared_containers\",\"type\",\"reads_from_(design)\",\"writes_to_(design)\",\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"modified_on\",\"operator\":\"<=\"}],\"operator\":\"and\"}}",
                                MatchType.ONLY_MATCHING_FIELDS
                        )))
                .respond(withResponse(getResourceFileContents("changed_jobs.json")));
    }

    private void setDetailsByRidQuery(MockServerClient mockServerClient, Resource resource) {
        URL url = null;
        try {
            url = resource.getURL();
        } catch (IOException e) {
            log.error("Unable to retrieve detailed file from: {}", resource, e);
        }
        if (url != null) {
            String filename = url.getFile();
            String rid = getRidFromFilename(filename);
            String type = getTypeFromFilename(filename);
            mockServerClient
                    .withSecure(true)
                    .when(searchRequest(
                            json(
                                    "{\"types\":[\"" + type + "\"],\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                    MatchType.ONLY_MATCHING_FIELDS
                            )))
                    .respond(withResponse(getResourceFileContents("by_rid" + File.separator + type + File.separator + rid + ".json")));
        }
    }

    private void setDetailsByChildRidQuery(MockServerClient mockServerClient, Resource resource) {
        URL url = null;
        try {
            url = resource.getURL();
        } catch (IOException e) {
            log.error("Unable to retrieve detailed file from: {}", resource, e);
        }
        if (url != null) {
            String filename = url.getFile();
            String rid = getRidFromFilename(filename);
            String type = getTypeFromFilename(filename);
            String property = null;
            switch (type) {
                case "database_schema":
                    property = "database_tables";
                    break;
            }
            if (property != null) {
                mockServerClient
                        .withSecure(true)
                        .when(searchRequest(
                                json(
                                        "{\"types\":[\"" + type + "\"],\"where\":{\"conditions\":[{\"property\":\"" + property + "\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                        MatchType.ONLY_MATCHING_FIELDS
                                )))
                        .respond(withResponse(getResourceFileContents("by_child_rid" + File.separator + type + File.separator + rid + ".json")));
            }
        }
    }

    private void setDetailsByParentRidQuery(MockServerClient mockServerClient, Resource resource) {
        URL url = null;
        try {
            url = resource.getURL();
        } catch (IOException e) {
            log.error("Unable to retrieve detailed file from: {}", resource, e);
        }
        if (url != null) {
            String filename = url.getFile();
            String rid = getRidFromFilename(filename);
            String type = getTypeFromFilename(filename);
            String property = null;
            switch (type) {
                case "database_column":
                    property = "database_table_or_view";
                    break;
                case "data_file_field":
                    property = "data_file_record";
                    break;
                case "stage_column":
                case "ds_stage_column":
                    property = "link.job_or_container";
                    break;
                case "link":
                case "stage":
                    property = "job_or_container";
                    break;
            }
            if (property != null) {
                mockServerClient
                        .withSecure(true)
                        .when(searchRequest(
                                json(
                                        "{\"types\":[\"" + type + "\"],\"where\":{\"conditions\":[{\"property\":\"" + property + "\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                        MatchType.ONLY_MATCHING_FIELDS
                                )))
                        .respond(withResponse(getResourceFileContents("by_parent_rid" + File.separator + type + File.separator + rid + ".json")));
            }
        }
    }

    private void setReferenceByRidQuery(MockServerClient mockServerClient, Resource resource) {
        URL url = null;
        try {
            url = resource.getURL();
        } catch (IOException e) {
            log.error("Unable to retrieve reference file from: {}", resource, e);
        }
        if (url != null) {
            String filename = url.getFile();
            String rid = getRidFromFilename(filename);
            mockServerClient
                    .withSecure(true)
                    .when(searchRequest(
                            json(
                                    "{\"types\":[\"main_object\",\"classification\",\"label\",\"user\",\"group\"],\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                    MatchType.ONLY_MATCHING_FIELDS
                            )))
                    .respond(withResponse(getResourceFileContents("ref_by_rid" + File.separator + rid + ".json")));
        }
    }

    private void setFullByRidQuery(MockServerClient mockServerClient, Resource resource) {
        URL url = null;
        try {
            url = resource.getURL();
        } catch (IOException e) {
            log.error("Unable to retrieve full details file from: {}", resource, e);
        }
        if (url != null) {
            String filename = url.getFile();
            String rid = getRidFromFilename(filename);
            mockServerClient
                    .withSecure(true)
                    .when(assetByRidRequest(rid))
                    .respond(withResponse(getResourceFileContents("full_by_rid" + File.separator + rid + ".json")));
        }
    }

    private String getRidFromFilename(String filename) {
        return filename.substring(filename.lastIndexOf("/") + 1, filename.indexOf(".json"));
    }

    private String getTypeFromFilename(String filename) {
        String path = filename.substring(filename.indexOf("/") + 1, filename.lastIndexOf("/"));
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private void setDefaultSearchResponseToNoResults(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(MockConstants.searchRequest())
                .respond(withResponse(getResourceFileContents("no_results.json")));
    }

    private void setGlossaryQueryByPropertyValue(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(MockConstants.searchRequest(
                        json(
                                "{\"types\":[\"category\"],\"where\":{\"conditions\":[{\"conditions\":[{\"property\":\"parent_category\",\"operator\":\"isNull\",\"negated\":false},{\"property\":\"name\",\"operator\":\"<>\",\"value\":\"Classifications\"}],\"operator\":\"and\"},{\"conditions\":[{\"property\":\"name\",\"operator\":\"like %{0}%\",\"value\":\"a\"},{\"property\":\"short_description\",\"operator\":\"like %{0}%\",\"value\":\"a\"},{\"property\":\"long_description\",\"operator\":\"like %{0}%\",\"value\":\"a\"},{\"property\":\"language\",\"operator\":\"like %{0}%\",\"value\":\"a\"}],\"operator\":\"or\"}],\"operator\":\"and\"}}",
                                MatchType.ONLY_MATCHING_FIELDS
                        ))
                )
                .respond(withResponse(getResourceFileContents("by_case" + File.separator + "glossary_by_property_value.json")));
    }

    private void setCategoriesInGlossary(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(MockConstants.searchRequest(
                        "{\"types\":[\"category\"],\"properties\":[\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"category_path\",\"operator\":\"=\",\"value\":\"" + GLOSSARY_RID + "\"}],\"operator\":\"and\"}}"
                ))
                .respond(withResponse(getResourceFileContents("by_case" + File.separator + "categories_in_glossary.json")));
    }

    private void setTermsInGlossary(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(MockConstants.searchRequest(
                        "{\"types\":[\"term\"],\"properties\":[\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"parent_category.category_path\",\"operator\":\"=\",\"value\":\"" + GLOSSARY_RID + "\"},{\"property\":\"parent_category\",\"operator\":\"=\",\"value\":\"" + GLOSSARY_RID + "\"}],\"operator\":\"or\"}}"
                ))
                .respond(withResponse(getResourceFileContents("by_case" + File.separator + "terms_in_glossary.json")));
    }

    private void setCategoryQueryByPropertyValue(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(MockConstants.searchRequest(
                        json(
                                "{\"types\":[\"category\"],\"where\":{\"conditions\":[{\"conditions\":[{\"property\":\"parent_category\",\"operator\":\"isNull\",\"negated\":true},{\"property\":\"category_path.name\",\"operator\":\"<>\",\"value\":\"Classifications\"}],\"operator\":\"and\"},{\"conditions\":[{\"property\":\"name\",\"operator\":\"like %{0}%\",\"value\":\"e\"},{\"property\":\"short_description\",\"operator\":\"like %{0}%\",\"value\":\"e\"}],\"operator\":\"or\"}],\"operator\":\"and\"}}",
                                MatchType.ONLY_MATCHING_FIELDS
                        ))
                )
                .respond(withResponse(getResourceFileContents("by_case" + File.separator + "category_by_property_value.json")));
    }

    private void setParentCategoryQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(MockConstants.searchRequest(
                        "{\"types\":[\"category\"],\"properties\":[\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"subcategories\",\"operator\":\"=\",\"value\":\"" + CATEGORY_RID + "\"}],\"operator\":\"and\"}}"
                ))
                .respond(withResponse(getResourceFileContents("by_case" + File.separator + "parent_category.json")));
    }

    private void setTermsInCategory(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(MockConstants.searchRequest(
                        "{\"types\":[\"term\"],\"properties\":[\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"parent_category\",\"operator\":\"=\",\"value\":\"" + CATEGORY_RID + "\"},{\"property\":\"referencing_categories\",\"operator\":\"=\",\"value\":\"" + CATEGORY_RID + "\"}],\"operator\":\"or\"}}"
                ))
                .respond(withResponse(getResourceFileContents("by_case" + File.separator + "terms_in_category.json")));
    }

    /**
     * Retrieve the contents of a test resource file.
     * @return String
     */
    private String getResourceFileContents(String filename) {

        ClassPathResource resource = new ClassPathResource(filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Unable to read resource file: {}", filename, e);
        }
        if (reader != null) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        return null;

    }

    /**
     * Retrieve the set of resources that match the specified pattern.
     * @param pattern to match for retrieving resources
     * @return {@code Resource[]}
     */
    private Resource[] getFilesMatchingPattern(String pattern) {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources(pattern);
        } catch(IOException e) {
            log.error("Unable to find any matches to pattern: {}", pattern, e);
        }
        return null;

    }

}
