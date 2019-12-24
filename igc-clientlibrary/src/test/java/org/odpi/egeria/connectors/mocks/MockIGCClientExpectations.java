/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.mocks;

import org.mockserver.client.MockServerClient;
import org.mockserver.client.initialize.ExpectationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

/**
 * Setup a mock server to act as an IGC REST API endpoint against which we can do some thorough testing.
 */
public class MockIGCClientExpectations implements ExpectationInitializer {

    private static final Logger log = LoggerFactory.getLogger(MockIGCClientExpectations.class);

    private static final String GLOSSARY_RID = "6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3";

    /**
     * Setup the expectations we will need to respond to various tests.
     * @param mockServerClient the client against which to set the expectations
     */
    public void initializeExpectations(MockServerClient mockServerClient) {

        setStartupQuery(mockServerClient);
        setTypesQuery(mockServerClient);
        setBundlesQuery(mockServerClient);

        setTypeDetails(mockServerClient, "term");
        setTypeDetails(mockServerClient, "category");

        setExampleFullAsset(mockServerClient, GLOSSARY_RID);
        setExamplePartAsset(mockServerClient, "category", GLOSSARY_RID);
        setExampleAssetWithModDetails(mockServerClient, "category", GLOSSARY_RID);
        setExampleRefAsset(mockServerClient, GLOSSARY_RID);

        setLogout(mockServerClient);

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
                        .withBody("{\"paging\":{\"numTotal\":0,\"pageSize\":1,\"end\":-1,\"begin\":0},\"items\":[]}")
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

    private void setTypeDetails(MockServerClient mockServerClient, String typeName) {
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
                        .withBody(getResourceFileContents("types_" + typeName + ".json"))
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

}
