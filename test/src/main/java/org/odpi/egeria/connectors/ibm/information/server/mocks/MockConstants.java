/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.information.server.mocks;

import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;

import java.util.List;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

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

    public static final String EGERIA_GLOSSARY_TERM_TYPE_GUID = "0db3e6ec-f5ef-4d75-ae38-b7ee6fd6ec0a";
    public static final String EGERIA_GLOSSARY_TERM_TYPE_NAME = "GlossaryTerm";

    public static final String GLOSSARY_RID = "6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3";
    public static final String GLOSSARY_NAME = "Coco Pharmaceuticals";
    public static final String GLOSSARY_DESC = "This glossary contains Glossary Terms and Categories that are related to the Coco Pharmaceuticals data";
    public static final String GLOSSARY_QN = "gen!GL@(category)=Coco Pharmaceuticals";

    // RIDs used for specific scenarios to test IGC mappers
    public static final String CATEGORY_RID = "6662c0f2.ee6a64fe.o1h6evehs.j0f25pn.ihsrb3.m7984f1jgfencf15nopk0";
    public static final String TERM_RID = "6662c0f2.e1b1ec6c.00263shl8.8c6cjg1.thoiqd.g2jiimda7gvarsup8a3bb";
    public static final String HOST_RID = "b1c497ce.354f5217.001mtr387.0nbvgbo.uh4485.rd8qffabbjgrsfjh2sheh";
    public static final String HOST_QN = "(host_(engine))=INFOSVR";
    public static final String DATABASE_RID = "b1c497ce.6e83759b.001mts4qn.7n9l0ef.4ov512.nfvlgdgsrggoc6hdlgic5";
    public static final String DATABASE_QN = HOST_QN + "::(database)=COMPDIR";
    public static final String DATA_CONNECTION_RID = "b1c497ce.8e4c0a48.001mts4qn.7mouq34.s1cncq.51abs5f71epl37jke7irc";
    public static final String DATA_CONNECTION_QN = "(data_connection)=CocoPharma_COMPDIR_ibm-db2";
    public static final String DATA_CONNECTION_RID_FS = "b1c497ce.8e4c0a48.001mts4ph.b7m0b1n.i2d31s.oaetfv0vaorabkeoccdc3";
    public static final String DATA_CONNECTION_QN_FS = "(data_connection)=LOCALFS";
    public static final String DATABASE_SCHEMA_RID = "b1c497ce.c1fb060b.001mts4qn.7n9ghn6.59e1lg.oeu3169u6dtpesgou6cqh";
    public static final String DATABASE_SCHEMA_QN = DATABASE_QN + "::(database_schema)=DB2INST1";
    public static final String DATABASE_TABLE_RID = "b1c497ce.54bd3a08.001mts4qn.7n9a341.3l2hic.d867phul07pgt3478ctim";
    public static final String DATABASE_TABLE_QN = DATABASE_SCHEMA_QN + "::(database_table)=CONTACTEMAIL";
    public static final String DATABASE_COLUMN_RID = "b1c497ce.60641b50.001mts4qn.7n94g9l.d6kb7r.l766qqrh375qc8dngkpni";
    public static final String DATABASE_COLUMN_QN = DATABASE_TABLE_QN + "::(database_column)=EMAIL";
    public static final String DATA_CLASS_RID = "f4951817.e469fa50.001mtr2gq.i03lpp2.ff6ti2.b6ol04ugdbtt6u6eojunp";
    public static final String DATA_CLASS_QN = "(data_class)=Email Address";
    public static final String DATA_FILE_FOLDER_RID = "b1c497ce.11727c74.001mts4ph.b86popc.fmed0i.m91ns5n533hivl7v9hop8";
    public static final String DATA_FILE_FOLDER_QN = HOST_QN + "::(data_file_folder)=/::(data_file_folder)=data::(data_file_folder)=files::(data_file_folder)=CocoPharma";
    public static final String DATA_FILE_RID = "b1c497ce.6e76d866.001mts4ph.b7m5306.9nrsit.7gtv16ok5bkl9ulo5rq8b";
    public static final String DATA_FILE_QN = DATA_FILE_FOLDER_QN + "::(data_file)=CompDir-ContactEmail.csv";
    public static final String DATA_FILE_RECORD_RID = "b1c497ce.54bd3a08.001mts4ph.b86utm6.a6meal.r7it9dr6jpapp50ad4ef4";
    public static final String DATA_FILE_RECORD_QN = DATA_FILE_QN + "::(data_file_record)=CompDir-ContactEmail";
    public static final String DATA_FILE_FIELD_RID = "b1c497ce.60641b50.001mts4ph.b86ofl8.toj5lt.0vrgjai9knc4h9k0hpr08";
    public static final String DATA_FILE_FIELD_QN = DATA_FILE_RECORD_QN + "::(data_file_field)=Email";
    public static final String SPINE_OBJECT_RID = "6662c0f2.e1b1ec6c.000mfkabd.cnqudif.58d1v3.mchnq8gmq5e3mo3kpev7l";
    public static final String SPINE_OBJECT_QN = "(category)=Coco Pharmaceuticals::(term)=Employee";
    public static final String SUBJECT_AREA_RID = "6662c0f2.ee6a64fe.o1h6evefs.3cd0db2.onm1g1.3auq0edm3j6k2gumuks96";
    public static final String SUBJECT_AREA_QN = "(category)=Coco Pharmaceuticals::(category)=Organization";
    public static final String INFORMATION_GOVERNANCE_POLICY_RID = "6662c0f2.8ed29152.000mfk806.3p93nta.95ttq9.gc3t9m9vfgg55hhm19vsp";
    public static final String INFORMATION_GOVERNANCE_POLICY_QN = "(information_governance_policy)=Data Access Policies::(information_governance_policy)=Confidential information should be masked when user does not have specific access to its Subject Area";
    public static final String USER_RID = "b1c497ce.285feb.001mts4th.dq2dkb2.ggbeni.vl04tnd0t0bjgmlg5a35vn0";
    public static final String USER_QN = "(steward_user)=Mr. Gary Geeke";
    public static final String GROUP_RID = "b1c497ce.8a5be154.001mts4th.nmdbb31.7flfke.0b7taja56pjhs73o553iq";
    public static final String RID_FOR_CREATE_AND_UPDATE = "6662c0f2.e1b1ec6c.001muv34k.s0rtkp1.c38mtf.60ltvrgr67q6l6pq5t3qi";

    // Examples used for specific scenarios to test IA client
    public static final String IA_PROJECT_NAME = "CocoPharma";
    public static final String IA_TABLE_NAME = "INFOSVR.COMPDIR.DB2INST1.CONTACTEMAIL";
    public static final String IA_COLUMN_NAME = IA_TABLE_NAME + ".EMAIL";
    public static final String IA_TABLE_NAME_WITH_DQ_PROBLEMS = "INFOSVR.EMPLSANL.DB2INST1.EMPSALARYANALYSIS";
    public static final String IA_CA_SCHEDULE_ID = "d70c6594.80cb2b5c.001muqrfs.a21f89o.a90986.6i2ig951fma0dtk23k0h7";
    public static final String IA_DQ_SCHEDULE_ID = "d70c6594.80cb2b5c.001muqr88.hpidg9f.il4mkc.rid1t6lqls0k16jlj3isu";

    private static final String IGC_REST_EP = "/ibm/iis/igc-rest/v1/";
    private static final String IA_REST_EP = "/ibm/iis/ia/api/";

    /**
     * Create a mock IGC search request.
     * @return HttpRequest
     */
    public static HttpRequest searchRequest() {
        return request().withMethod("POST").withPath(IGC_REST_EP + "search");
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
     * Create a mock IGC next page request based on the provided parameters.
     * (Note that this should really have a 'where' clause to better restrict the query, but getting such a clause
     *  working seems far too convoluted to be worth it, as neither trying to allow mock-server itself to URL-encode it
     *  nor up-front URL-encoding the string seem to work.)
     * @param types the types to search
     * @param properties the properties to include in the results
     * @param pageSize the number of results per page
     * @param begin the result index to start from
     * @return HttpRequest
     */
    public static HttpRequest nextPageRequest(String types, List<String> properties, String pageSize, String begin) {
        return request().withMethod("GET").withPath(IGC_REST_EP + "search")
                .withQueryStringParameters(
                        param("types", types),
                        param("properties", properties),
                        param("pageSize", pageSize),
                        param("begin", begin)
                );
    }

    /**
     * Create a mock IGC type query request.
     * @return HttpRequest
     */
    public static HttpRequest typesRequest() {
        return request().withMethod("GET").withPath(IGC_REST_EP + "types");
    }

    /**
     * Create a mock IGC type query request.
     * @param typeName type name for which to retrieve details
     * @return HttpRequest
     */
    public static HttpRequest typesRequest(String typeName) {
        return request().withMethod("GET").withPath(IGC_REST_EP + "types/" + typeName);
    }

    /**
     * Create a mock IGC asset retrieval by RID request.
     * @param rid the RID for the asset to retrieve
     * @return HttpRequest
     */
    public static HttpRequest assetByRidRequest(String rid) {
        return request().withMethod("GET").withPath(IGC_REST_EP + "assets/" + rid);
    }

    /**
     * Create a mock IGC bundle query request.
     * @return HttpRequest
     */
    public static HttpRequest bundlesRequest() {
        return request().withMethod("GET").withPath(IGC_REST_EP + "bundles");
    }

    /**
     * Create a mock IGC bundle upsert request.
     * @return HttpRequest
     */
    public static HttpRequest upsertBundleRequest() {
        return request().withMethod("PUT").withPath(IGC_REST_EP + "bundles");
    }

    /**
     * Create a mock IGC asset creation request.
     * @param body what should be created
     * @return HttpRequest
     */
    public static HttpRequest createAssetRequest(String body) {
        return request().withMethod("POST").withPath(IGC_REST_EP + "assets").withBody(body);
    }

    /**
     * Create a mock IGC asset update request.
     * @param rid the RID of the asset to update
     * @param body what should be updated
     * @return HttpRequest
     */
    public static HttpRequest updateAssetRequest(String rid, String body) {
        return request().withMethod("PUT").withPath(IGC_REST_EP + "assets/" + rid).withBody(body);
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
    public static HttpRequest igcLogoutRequest() {
        return request().withMethod("GET").withPath(IGC_REST_EP + "logout");
    }

    /**
     * Create a mock IA projects list request.
     * @return HttpRequest
     */
    public static HttpRequest getProjectsRequest() {
        return request().withMethod("GET").withPath(IA_REST_EP + "projects");
    }

    /**
     * Create a mock IA project details request.
     * @param projectName name of the project for which to obtain details
     * @return HttpRequest
     */
    public static HttpRequest getProjectDetailsRequest(String projectName) {
        return request().withMethod("GET").withPath(IA_REST_EP + "project").withQueryStringParameter("projectName", projectName);
    }

    /**
     * Create a mock IA published results request.
     * @param projectName name of the project for which to obtain published results
     * @return HttpRequest
     */
    public static HttpRequest getPublishedResultsRequest(String projectName) {
        return request().withMethod("GET").withPath(IA_REST_EP + "publishedResults").withQueryStringParameter("projectName", projectName);
    }

    /**
     * Create a mock IA column analysis results request.
     * @param projectName name of the project for which to obtain column analysis results
     * @param tableName name of the table for which to obtain column analysis results
     * @return HttpRequest
     */
    public static HttpRequest getColumnAnalysisResultsRequest(String projectName, String tableName) {
        return request().withMethod("GET").withPath(IA_REST_EP + "columnAnalysis/results")
                .withQueryStringParameters(
                        param("projectName", projectName),
                        param("columnName", tableName + ".*")
                );
    }

    /**
     * Create a mock IA format distribution request.
     * @param projectName name of the project for which to obtain the format distribution
     * @param columnName name of the column for which to obtain the format distribution
     * @return HttpRequest
     */
    public static HttpRequest getFormatDistributionRequest(String projectName, String columnName) {
        return request().withMethod("GET").withPath(IA_REST_EP + "columnAnalysis/formatDistribution")
                .withQueryStringParameters(
                        param("projectName", projectName),
                        param("columnName", columnName)
                );
    }

    /**
     * Create a mock IA frequency distribution request.
     * @param projectName name of the project for which to obtain the frequency distribution
     * @param columnName name of the column for which to obtain the frequency distribution
     * @return HttpRequest
     */
    public static HttpRequest getFrequencyDistributionRequest(String projectName, String columnName) {
        return request().withMethod("GET").withPath(IA_REST_EP + "columnAnalysis/frequencyDistribution")
                .withQueryStringParameters(
                        param("projectName", projectName),
                        param("columnName", columnName)
                );
    }

    /**
     * Create a mock IA data quality analysis results request.
     * @param projectName name of the project for which to obtain data quality results
     * @param tableName name of the table for which to obtain data quality results
     * @return HttpRequest
     */
    public static HttpRequest getDataQualityResultsRequest(String projectName, String tableName) {
        return request().withMethod("GET").withPath(IA_REST_EP + "dataQualityAnalysis/results")
                .withQueryStringParameters(
                        param("projectName", projectName),
                        param("tableName", tableName)
                );
    }

    /**
     * Create a mock IA task execution request.
     * @param body the details of the task to execute
     * @return HttpRequest
     */
    public static HttpRequest getExecuteTaskRequest(String body) {
        return request().withMethod("POST").withPath(IA_REST_EP + "executeTasks").withBody(body);
    }

    /**
     * Create a mock IA task status request.
     * @param scheduleId the scheduleId of the task for which to request the status
     * @return HttpRequest
     */
    public static HttpRequest getTaskStatusRequest(String scheduleId) {
        return request().withMethod("GET").withPath(IA_REST_EP + "analysisStatus").withQueryStringParameter("scheduleID", scheduleId);
    }

    /**
     * Create a mock IA publish results request.
     * @param body the details of the results to publish
     * @return HttpRequest
     */
    public static HttpRequest getPublishResultsRequest(String body) {
        return request().withMethod("POST").withPath(IA_REST_EP + "publishResults").withBody(body);
    }

    /**
     * Create a mock IA logout request.
     * @return HttpRequest
     */
    public static HttpRequest iaLogoutRequest() {
        return request().withMethod("GET").withPath(IA_REST_EP + "logout");
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
