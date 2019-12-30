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

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.model.Parameter.param;

/**
 * Setup a mock server to act as an IGC REST API endpoint against which we can do some thorough testing.
 */
public class MockServerExpectations implements ExpectationInitializer {

    private static final Logger log = LoggerFactory.getLogger(MockServerExpectations.class);

    private static final String GLOSSARY_RID = "6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3";

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

        setExampleFullAsset(mockServerClient, GLOSSARY_RID);
        setExamplePartAsset(mockServerClient, "category", GLOSSARY_RID);
        setExampleAssetWithModDetails(mockServerClient, "category", GLOSSARY_RID);
        setExampleRefAsset(mockServerClient, GLOSSARY_RID);

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

    }

    private void initializeIGCConnectorExpectations(MockServerClient mockServerClient) {

        setUploadBundle(mockServerClient);

    }

    private void initializeDataStageConnectorExpectations(MockServerClient mockServerClient) {

        setJobSyncRuleQueryEmpty(mockServerClient);
        setJobSyncRuleQueryFull(mockServerClient);

        setJobChangeQuery(mockServerClient);

    }

    private void setStartupQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                                .withBody("{\"types\":[\"category\",\"term\",\"information_governance_policy\",\"information_governance_rule\"],\"pageSize\":1,\"workflowMode\":\"draft\"}")
                ).respond(
                response()
                        .withBody(getResourceFileContents("no_results.json"))
        );
    }

    private void setTypesQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/ibm/iis/igc-rest/v1/types")
                ).respond(
                response()
                        .withBody(getResourceFileContents("types.json"))
        );
    }

    private void setBundlesQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/ibm/iis/igc-rest/v1/bundles")
                ).respond(
                response()
                        .withBody("[\"OMRS\"]")
        );
    }

    private void setTypeDetails(MockServerClient mockServerClient, String typeFilename) {
        String typeName = typeFilename.substring(0, typeFilename.indexOf(".json"));
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/ibm/iis/igc-rest/v1/types/" + typeName)
                                .withQueryStringParameters(
                                        param("showViewProperties", "true"),
                                        param("showCreateProperties", "true"),
                                        param("showEditProperties", "true")
                                )
                ).respond(
                response()
                        .withBody(getResourceFileContents("types" + File.separator + typeName + ".json"))
        );
    }

    private void setExampleFullAsset(MockServerClient mockServerClient, String rid) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/ibm/iis/igc-rest/v1/assets/" + rid)
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("rid_full_" + rid + ".json"))
                );
    }

    private void setExamplePartAsset(MockServerClient mockServerClient, String type, String rid) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                                .withBody("{\"types\":[\"" + type + "\"],\"properties\":[\"short_description\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}],\"operator\":\"and\"}}")
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("rid_part_" + rid + ".json"))
                );
    }

    private void setExampleAssetWithModDetails(MockServerClient mockServerClient, String type, String rid) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                                .withBody("{\"types\":[\"" + type + "\"],\"properties\":[\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":2,\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}],\"operator\":\"and\"}}")
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("rid_mod_" + rid + ".json"))
                );
    }

    private void setExampleRefAsset(MockServerClient mockServerClient, String rid) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                                .withBody("{\"types\":[\"main_object\",\"classification\",\"label\",\"user\",\"group\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}],\"operator\":\"and\"}}")
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("rid_ref_" + rid + ".json"))
                );
    }

    private void setLogout(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/ibm/iis/igc-rest/v1/logout")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                );
    }

    private void setUploadBundle(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath("/ibm/iis/igc-rest/v1/bundles")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                );
    }

    private void setJobSyncRuleQueryEmpty(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                                .withBody("{\"types\":[\"information_governance_rule\"],\"properties\":[\"short_description\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"name\",\"operator\":\"=\",\"value\":\"Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS\"}],\"operator\":\"and\"}}"),
                        Times.exactly(1)
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("no_results.json"))
        );
    }

    private void setJobSyncRuleQueryFull(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                                .withBody("{\"types\":[\"information_governance_rule\"],\"properties\":[\"short_description\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"name\",\"operator\":\"=\",\"value\":\"Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS\"}],\"operator\":\"and\"}}")
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("job_sync_rule.json"))
                );
    }

    private void setJobChangeQuery(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                                .withBody(
                                        json(
                                                "{\"types\":[\"dsjob\"],\"properties\":[\"short_description\",\"long_description\",\"references_local_or_shared_containers\",\"type\",\"reads_from_(design)\",\"writes_to_(design)\",\"created_by\",\"created_on\",\"modified_by\",\"modified_on\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"modified_on\",\"operator\":\"<=\"}],\"operator\":\"and\"}}",
                                                MatchType.ONLY_MATCHING_FIELDS
                                        )
                                )
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("changed_jobs.json"))
                );
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
            String rid = filename.substring(filename.lastIndexOf("/") + 1, filename.indexOf(".json"));
            String path = filename.substring(filename.indexOf("/") + 1, filename.lastIndexOf("/"));
            String type = path.substring(path.lastIndexOf("/") + 1);
            mockServerClient
                    .withSecure(true)
                    .when(
                            request()
                                    .withMethod("POST")
                                    .withPath("/ibm/iis/igc-rest/v1/search")
                                    .withBody(
                                            json(
                                                    "{\"types\":[\"" + type + "\"],\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                                    MatchType.ONLY_MATCHING_FIELDS
                                            )
                                    )
                    )
                    .respond(
                            response()
                                    .withBody(getResourceFileContents("by_rid" + File.separator + type + File.separator + rid + ".json"))
                    );
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
            String rid = filename.substring(filename.lastIndexOf("/") + 1, filename.indexOf(".json"));
            String path = filename.substring(filename.indexOf("/") + 1, filename.lastIndexOf("/"));
            String type = path.substring(path.lastIndexOf("/") + 1);
            String property = null;
            switch (type) {
                case "database_schema":
                    property = "database_tables";
                    break;
            }
            if (property != null) {
                mockServerClient
                        .withSecure(true)
                        .when(
                                request()
                                        .withMethod("POST")
                                        .withPath("/ibm/iis/igc-rest/v1/search")
                                        .withBody(
                                                json(
                                                        "{\"types\":[\"" + type + "\"],\"where\":{\"conditions\":[{\"property\":\"" + property + "\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                                        MatchType.ONLY_MATCHING_FIELDS
                                                )
                                        )
                        )
                        .respond(
                                response()
                                        .withBody(getResourceFileContents("by_child_rid" + File.separator + type + File.separator + rid + ".json"))
                        );
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
            String rid = filename.substring(filename.lastIndexOf("/") + 1, filename.indexOf(".json"));
            String path = filename.substring(filename.indexOf("/") + 1, filename.lastIndexOf("/"));
            String type = path.substring(path.lastIndexOf("/") + 1);
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
                        .when(
                                request()
                                        .withMethod("POST")
                                        .withPath("/ibm/iis/igc-rest/v1/search")
                                        .withBody(
                                                json(
                                                        "{\"types\":[\"" + type + "\"],\"where\":{\"conditions\":[{\"property\":\"" + property + "\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                                        MatchType.ONLY_MATCHING_FIELDS
                                                )
                                        )
                        )
                        .respond(
                                response()
                                        .withBody(getResourceFileContents("by_parent_rid" + File.separator + type + File.separator + rid + ".json"))
                        );
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
            String rid = filename.substring(filename.lastIndexOf("/") + 1, filename.indexOf(".json"));
            mockServerClient
                    .withSecure(true)
                    .when(
                            request()
                                    .withMethod("POST")
                                    .withPath("/ibm/iis/igc-rest/v1/search")
                                    .withBody(
                                            json(
                                                    "{\"types\":[\"main_object\",\"classification\",\"label\",\"user\",\"group\"],\"where\":{\"conditions\":[{\"property\":\"_id\",\"operator\":\"=\",\"value\":\"" + rid + "\"}]}}",
                                                    MatchType.ONLY_MATCHING_FIELDS
                                            )
                                    )
                    )
                    .respond(
                            response()
                                    .withBody(getResourceFileContents("ref_by_rid" + File.separator + rid + ".json"))
                    );
        }
    }

    private void setDefaultSearchResponseToNoResults(MockServerClient mockServerClient) {
        mockServerClient
                .withSecure(true)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/ibm/iis/igc-rest/v1/search")
                )
                .respond(
                        response()
                                .withBody(getResourceFileContents("no_results.json"))
                );
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