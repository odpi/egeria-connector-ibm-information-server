/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.information.server.mocks;

import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * A set of constants that can be re-used across various modules' tests.
 */
public class MockConstants {

    public static final String IGC_HOST = "localhost";
    public static final String IGC_PORT = "1080";
    public static final String IGC_ENDPOINT = IGC_HOST + ":" + IGC_PORT;
    public static final String IGC_USER = "isadmin";
    public static final String IGC_PASS = "isadmin";

    public static final String EGERIA_USER = "admin";
    public static final int EGERIA_PAGESIZE = 100;

    public static final String GLOSSARY_RID = "6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3";
    public static final String GLOSSARY_NAME = "Coco Pharmaceuticals";
    public static final String GLOSSARY_DESC = "This glossary contains Glossary Terms and Categories that are related to the Coco Pharmaceuticals data";

    public static final String CATEGORY_RID = "6662c0f2.ee6a64fe.o1h6evehs.j0f25pn.ihsrb3.m7984f1jgfencf15nopk0";

    public static final String TABLE_RID = "b1c497ce.54bd3a08.001mts4qn.7n9a341.3l2hic.d867phul07pgt3478ctim";
    public static final String TABLE_NAME = "CONTACTEMAIL";

    /**
     * Create a mock IGC search request.
     * @return HttpRequest
     */
    public static HttpRequest searchRequest() {
        return request().withMethod("POST").withPath("/ibm/iis/igc-rest/v1/search");
    }

    /**
     * Create a mock IGC search request for the provided body.
     * @param body to search
     * @return HttpRequest
     */
    public static HttpRequest searchRequest(String body) {
        return searchRequest().withBody(body);
    }

    /**
     * Create a mock IGC search request for the provided JSON body.
     * @param body to search
     * @return HttpRequest
     */
    public static HttpRequest searchRequest(JsonBody body) {
        return searchRequest().withBody(body);
    }

    /**
     * Create a mock IGC type query request.
     * @return HttpRequest
     */
    public static HttpRequest typesRequest() {
        return request().withMethod("GET").withPath("/ibm/iis/igc-rest/v1/types");
    }

    /**
     * Create a mock IGC type query request.
     * @param typeName type name for which to retrieve details
     * @return HttpRequest
     */
    public static HttpRequest typesRequest(String typeName) {
        return request().withMethod("GET").withPath("/ibm/iis/igc-rest/v1/types/" + typeName);
    }

    /**
     * Create a mock IGC asset retrieval by RID request.
     * @param rid the RID for the asset to retrieve
     * @return HttpRequest
     */
    public static HttpRequest assetByRidRequest(String rid) {
        return request().withMethod("GET").withPath("/ibm/iis/igc-rest/v1/assets/" + rid);
    }

    /**
     * Create a mock IGC bundle query request.
     * @return HttpRequest
     */
    public static HttpRequest bundlesRequest() {
        return request().withMethod("GET").withPath("/ibm/iis/igc-rest/v1/bundles");
    }

    /**
     * Create a mock IGC bundle upsert request.
     * @return HttpRequest
     */
    public static HttpRequest upsertBundleRequest() {
        return request().withMethod("PUT").withPath("/ibm/iis/igc-rest/v1/bundles");
    }

    /**
     * Create a mock IGC request for the job synchronization rule.
     * @return HttpRequest
     */
    public static HttpRequest jobSyncRuleRequest() {
        return searchRequest("{\"types\":[\"information_governance_rule\"],\"properties\":[\"short_description\"],\"pageSize\":100,\"where\":{\"conditions\":[{\"property\":\"name\",\"operator\":\"=\",\"value\":\"Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS\"}],\"operator\":\"and\"}}");
    }

    /**
     * Create a mock IGC logout request.
     * @return HttpRequest
     */
    public static HttpRequest logoutRequest() {
        return request().withMethod("GET").withPath("/ibm/iis/igc-rest/v1/logout");
    }

    /**
     * Create a mock IGC response using the provided body.
     * @param body to respond with
     * @return HttpResponse
     */
    public static HttpResponse withResponse(String body) {
        return response().withBody(body);
    }

}
