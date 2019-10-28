/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary;

import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.odpi.egeria.connectors.ibm.ia.clientlibrary.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Library of methods to connect to and interact with an IBM Information Analyzer environment
 * using appropriate session management.
 * <br><br>
 * Methods are provided to interact with REST API endpoints and process results as through the use of registered POJOs
 * to automatically (de-)serialise between the XML form and a native Java object.
 */
public class IARestClient {

    private static final Logger log = LoggerFactory.getLogger(IARestClient.class);

    private String authorization;
    private String baseURL;
    private List<String> cookies = null;
    private RestTemplate restTemplate;

    private XmlMapper mapper;
    private DocumentBuilder xmlParser;
    private Transformer xmlTransformer;

    private static final String EP_BASE_API = "/ibm/iis/ia/api/";
    private static final String EP_PROJECT = EP_BASE_API + "project";
    private static final String EP_PROJECTS = EP_PROJECT + "s";
    private static final String EP_COL_ANALYSIS_BASE = EP_BASE_API + "columnAnalysis/";
    private static final String EP_COL_ANALYSIS_RESULTS = EP_COL_ANALYSIS_BASE + "results";
    private static final String EP_FORMAT_DISTRIBUTION = EP_COL_ANALYSIS_BASE + "formatDistribution";
    private static final String EP_FREQ_DISTRIBUTION = EP_COL_ANALYSIS_BASE + "frequencyDistribution";
    private static final String EP_DQ_ANALYSIS_BASE = EP_BASE_API + "dataQualityAnalysis/";
    private static final String EP_DQ_ANALYSIS_RESULTS = EP_DQ_ANALYSIS_BASE + "results";
    private static final String EP_PUBLISHED_RESULTS = EP_BASE_API + "publishedResults";
    private static final String EP_EXECUTE_TASK = EP_BASE_API + "executeTasks";
    private static final String EP_TASK_STATUS = EP_BASE_API + "analysisStatus";
    private static final String EP_PUBLISH = EP_BASE_API + "publishResults";
    private static final String EP_LOGOUT = EP_BASE_API + "logout";

    /**
     * Default constructor used by the IARestClient.
     * <br><br>
     * Creates a new session on the server and retains the cookies to re-use the same session for the life
     * of the client (or until the session times out); whichever occurs first.
     *
     * @param host the services (domain) tier host
     * @param port the services (domain) tier port number
     * @param user the username with which to open and retain the session
     * @param password the password for the user
     */
    public IARestClient(String host, String port, String user, String password) {
        this("https://" + host + ":" + port, encodeBasicAuth(user, password));
    }

    /**
     * Creates a new session on the server and retains the cookies to re-use the same session for the life
     * of the client (or until the session times out); whichever occurs first.
     *
     * @param baseURL the base URL of the domain tier of Information Server
     * @param authorization the Basic-encoded authorization string to use to login to Information Server
     */
    private IARestClient(String baseURL, String authorization) {

        if (baseURL == null || !baseURL.startsWith("https://")) {
            if (log.isErrorEnabled()) { log.error("Cannot instantiate IARestClient -- baseURL must be https: {}", baseURL); }
            throw new NullPointerException();
        }

        this.baseURL = baseURL;
        this.authorization = authorization;
        XMLInputFactory inputFactory = new WstxInputFactory();
        XMLOutputFactory outputFactory = new WstxOutputFactory();
        XmlFactory xf = new XmlFactory(inputFactory, outputFactory);
        this.mapper = new XmlMapper(xf);
        this.mapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, JsonInclude.Include.NON_EMPTY));
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            this.xmlParser = documentBuilderFactory.newDocumentBuilder();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            this.xmlTransformer = transformerFactory.newTransformer();
        } catch (ParserConfigurationException e) {
            log.error("Unable to instantiate an XML parser.", e);
        } catch (TransformerConfigurationException e) {
            log.error("Unable to instantiate an XML transformer.", e);
        }
        this.restTemplate = new RestTemplate();

        // Ensure that the REST template always uses UTF-8
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        converters.removeIf(httpMessageConverter -> httpMessageConverter instanceof StringHttpMessageConverter);
        converters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        if (log.isDebugEnabled()) { log.debug("Constructing IARestClient..."); }

        if (this.authorization == null) {
            log.error("Unable to construct IARestClient: no authorization provided.");
        }

    }

    /**
     * Utility function to easily encode a username and password to send through as authorization info.
     *
     * @param username username to encode
     * @param password password to encode
     * @return String of appropriately-encoded credentials for authorization
     */
    private static String encodeBasicAuth(String username, String password) {
        return Base64Utils.encodeToString((username + ":" + password).getBytes(UTF_8));
    }

    /**
     * Setup the HTTP headers of a request based on either session reuse (forceLogin = false) or forcing a new
     * session (forceLogin = true).
     *
     * @param forceLogin indicates whether to create a new session by forcing login (true), or reuse existing session (false)
     * @return HttpHeaders
     */
    private HttpHeaders getHttpHeaders(boolean forceLogin) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");

        // If we have cookies already, and haven't been asked to force the login,
        // re-use these (to maintain the same session)
        if (cookies != null && !forceLogin) {
            headers.addAll(HttpHeaders.COOKIE, cookies);
        } else { // otherwise re-authenticate by Basic authentication
            String auth = "Basic " + this.authorization;
            headers.add(HttpHeaders.AUTHORIZATION, auth);
        }

        return headers;

    }

    /**
     * Attempts to open a new session while sending the provided request. If the alreadyTriedNewSession is true,
     * and we are unable to open a new session with this attempt, will give up. If the alreadyTriedNewSession is false,
     * will attempt to re-send this request to open a new session precisely once before giving up.
     *
     * @param url the URL to which to send the request
     * @param method the HTTP method to use in sending the request
     * @param payload the payload (if any) for the request
     * @param alreadyTriedNewSession indicates whether a new session was already attempted (true) or not (false)
     * @return {@code ResponseEntity<String>}
     */
    private ResponseEntity<String> openNewSessionWithRequest(String url,
                                                             HttpMethod method,
                                                             String payload,
                                                             boolean alreadyTriedNewSession) {
        if (alreadyTriedNewSession) {
            if (log.isErrorEnabled()) { log.error("Opening a new session already attempted without success -- giving up on {} to {} with {}", method, url, payload); }
            return null;
        } else {
            // By removing cookies, we'll force a login
            this.cookies = null;
            return makeRequest(url, method, payload, true);
        }
    }

    /**
     * Adds the cookies from a response into subsequent headers, so that we re-use the session indicated by those
     * cookies.
     *
     * @param response the response from which to obtain the cookies
     */
    private void setCookiesFromResponse(ResponseEntity<String> response) {

        // If we had a successful response, setup the cookies
        if (response.getStatusCode() == HttpStatus.OK
                || response.getStatusCode() == HttpStatus.CREATED
                || response.getStatusCode() == HttpStatus.NO_CONTENT) {
            HttpHeaders headers = response.getHeaders();
            if (headers.get(HttpHeaders.SET_COOKIE) != null) {
                this.cookies = headers.get(HttpHeaders.SET_COOKIE);
            }
        } else {
            if (log.isErrorEnabled()) { log.error("Unable to make request or unexpected status: {}", response.getStatusCode()); }
        }

    }

    /**
     * Internal utility for making potentially repeat requests (if session expires and needs to be re-opened).
     *
     * @param url the URL against which to make the request
     * @param method HttpMethod (GET, POST, etc)
     * @param payload if POSTing some content, the JSON structure providing what should be POSTed
     * @param forceLogin a boolean indicating whether login should be forced (true) or session reused (false)
     * @return {@code ResponseEntity<String>}
     */
    private ResponseEntity<String> makeRequest(String url,
                                               HttpMethod method,
                                               String payload,
                                               boolean forceLogin) {
        HttpHeaders headers = getHttpHeaders(forceLogin);
        HttpEntity<String> toSend;
        if (payload != null) {
            toSend = new HttpEntity<>(payload, headers);
        } else {
            toSend = new HttpEntity<>(headers);
        }
        ResponseEntity<String> response = null;
        try {
            if (log.isDebugEnabled()) { log.debug("{}ing to {} with: {}", method, url, payload); }
            response = restTemplate.exchange(
                    url,
                    method,
                    toSend,
                    String.class);
            setCookiesFromResponse(response);
        } catch (HttpClientErrorException e) {
            log.warn("Request failed -- session may have expired, retrying...", e);
            // If the response was forbidden (fails with exception), the session may have expired -- create a new one
            response = openNewSessionWithRequest(
                    url,
                    method,
                    payload,
                    forceLogin
            );
        } catch (RestClientException e) {
            log.error("Request failed -- check IA environment connectivity and authentication details.", e);
        }
        return response;
    }

    /**
     * General utility for making requests.
     *
     * @param endpoint the REST resource against which to make the request
     * @param method HttpMethod (GET, POST, etc)
     * @param payload if POSTing some content, the JSON structure providing what should be POSTed
     * @return String - containing the body of the response
     */
    private String makeRequest(String endpoint, HttpMethod method, String payload) {
        ResponseEntity<String> response = makeRequest(
                baseURL + endpoint,
                method,
                payload,
                false
        );
        String body = null;
        if (response == null) {
            log.error("Unable to complete request -- check IA environment connectivity and authentication details.");
            throw new NullPointerException("Unable to complete request -- check IA environment connectivity and authentication details.");
        } else if (response.hasBody()) {
            // We MUST minimize the XML response in order for it to be properly de-serialized by Jackson
            // (otherwise an <A></A> with a newline in-between is interpreted has having a long '      ' value rather
            // than as identical to just <A/>, and as a result things like arrays are not properly deserialized)
            body = response.getBody();
            if (body != null) {
                try {
                    Document xmlDoc = xmlParser.parse(new InputSource(new StringReader(body)));
                    minimizeXML(xmlDoc);
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);
                    xmlTransformer.transform(new DOMSource(xmlDoc), result);
                    body = writer.toString();
                } catch (TransformerException e) {
                    log.error("Unable to transform parsed response body back to String: {}", body, e);
                } catch (SAXException | IOException e) {
                    log.error("Unable to parse the response body as XML: {}", body, e);
                }
            }
        }
        return body;
    }

    /**
     * Disconnect from IGC REST API and invalidate the session.
     */
    public void disconnect() {
        makeRequest(EP_LOGOUT, HttpMethod.GET, null);
    }

    /**
     * Retrieve the list of projects defined in the IA environment.
     *
     * @return {@code List<Project>}
     */
    public List<Project> getProjectList() {
        String response = makeRequest(EP_PROJECTS, HttpMethod.GET, null);
        List<Project> lProjects = new ArrayList<>();
        try {
            lProjects = mapper.readValue(response, new TypeReference<List<Project>>(){});
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse projects response: {}", response, e); }
        }
        return lProjects;
    }

    /**
     * Retrieve the project details for the specified IA project.
     *
     * @param projectName the name of the IA project for which to retrieve details
     * @return Project - containing only the details of the project definition
     */
    public Project getProjectDetails(String projectName) {
        String response = makeRequest(EP_PROJECT + "?projectName=" + encodeParameterForURL(projectName), HttpMethod.GET, null);
        Project project = null;
        try {
            project = mapper.readValue(response, Project.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse project details for project '{}': {}", projectName, response, e); }
        }
        return project;
    }

    /**
     * Retrieve a list of all the (qualified) names of tables within the specified project.
     *
     * @param details the project details as retrieved from getProjectDetails
     * @return {@code List<String>}
     * @see #getProjectDetails(String)
     */
    public List<String> getTablesInProject(Project details) {
        List<String> tableNames = new ArrayList<>();
        for (DataSource dataSource : details.getDataSources()) {
            for (Schema schema : dataSource.getSchemas()) {
                for (Table table : schema.getTables()) {
                    String qualifiedName = dataSource.getName() + "." + schema.getName() + "." + table.getName();
                    tableNames.add(qualifiedName);
                }
            }
        }
        return tableNames;
    }

    /**
     * Retrieve a list of all the (qualified) names of columns within the specified project.
     *
     * @param details the project details as retrieved from getProjectDetails
     * @return {@code List<String>}
     * @see #getProjectDetails(String)
     */
    public List<String> getColumnsInProject(Project details) {
        List<String> columnNames = new ArrayList<>();
        for (DataSource dataSource : details.getDataSources()) {
            for (Schema schema : dataSource.getSchemas()) {
                for (Table table : schema.getTables()) {
                    for (Column column : table.getColumns()) {
                        String qualifiedName = dataSource.getName() + "." + schema.getName() + "." + table.getName() + "." + column.getName();
                        columnNames.add(qualifiedName);
                    }
                }
            }
        }
        return columnNames;
    }

    /**
     * Retrieve the column analysis results for a table as a Map, keyed by the name of the column.
     *
     * @param projectName the name of the project from which to retrieve results
     * @param tableName the name of the table for which to retrieve results
     * @return {@code Map<String, ColumnAnalysisResults>} - a map from column name to analysis results
     */
    public Map<String, ColumnAnalysisResults> getColumnAnalysisResultsForTable(String projectName,
                                                                               String tableName) {
        Project results = getColumnAnalysisResults(projectName, tableName + ".*");
        Map<String, ColumnAnalysisResults> map = new HashMap<>();
        for (DataSource dataSource : results.getDataSources()) {
            for (Schema schema : dataSource.getSchemas()) {
                for (Table table : schema.getTables()) {
                    for (Column column : table.getColumns()) {
                        ColumnAnalysisResults columnAnalysisResults = column.getColumnAnalysisResults();
                        if (columnAnalysisResults != null) {
                            map.put(column.getName(), columnAnalysisResults);
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * Retrieve the data quality analysis results for a table as a Map, keyed by the name of the column.
     *
     * @param projectName the name of the project from which to retrieve results
     * @param tableName the name of the table for which to retrieve results
     * @return {@code Map<String, List<DataQualityProblem>>} - a map from column name to analysis results
     */
    public Map<String, List<DataQualityProblem>> getDataQualityAnalysisResultsForTable(String projectName,
                                                                                    String tableName) {
        Project results = getDataQualityAnalysisResults(projectName, tableName);
        Map<String, List<DataQualityProblem>> map = new HashMap<>();
        for (DataSource dataSource : results.getDataSources()) {
            for (Schema schema : dataSource.getSchemas()) {
                for (Table table : schema.getTables()) {
                    for (Column column : table.getColumns()) {
                        List<DataQualityProblem> dataQualityProblems = column.getDataQualityProblems();
                        if (dataQualityProblems != null) {
                            map.put(column.getName(), dataQualityProblems);
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * Retrieve the column analysis results for the specified IA project.
     *
     * @param projectName the name of the IA project for which to retrieve column analysis results
     * @param columnName (optional) qualified name of a column for which to retrieve analysis results
     * @return Project - containing only the column analysis results details
     */
    private Project getColumnAnalysisResults(String projectName,
                                            String columnName) {
        return makeColumnBasedRequest(projectName, columnName, EP_COL_ANALYSIS_RESULTS, "getColumnAnalysisResults");
    }

    /**
     * Retrieve the column analysis results for the specified IA project and column.
     *
     * @param projectName the name of the IA project for which to retrieve format distribution results
     * @param columnName the qualified name of the column for which to retrieve analysis results
     * @return Project - containing only the format distribution results details
     */
    public Project getFormatDistribution(String projectName,
                                         String columnName) {
        if (columnName == null) {
            throw new NullPointerException("The 'columnName' parameter is required for 'getFormatDistribution'.");
        }
        return makeColumnBasedRequest(projectName, columnName, EP_FORMAT_DISTRIBUTION, "getFormatDistribution");
    }

    /**
     * Retrieve the frequency distribution of values for the specified IA project and column.
     *
     * @param projectName the name of the IA project for which to retrieve format distribution results
     * @param columnName the qualified name of the column for which to retrieve analysis results
     * @return Project - containing only the format distribution results details
     */
    public Project getFrequencyDistribution(String projectName,
                                            String columnName) {
        if (columnName == null) {
            throw new NullPointerException("The 'columnName' parameter is required for 'getFrequencyDistribution'.");
        }
        return makeColumnBasedRequest(projectName, columnName, EP_FREQ_DISTRIBUTION, "getFrequencyDistribution");
    }

    /**
     * Retrieve the data quality analysis results for the specified IA project.
     *
     * @param projectName the name of the IA project for which to retrieve column analysis results
     * @param tableName (optional) qualified name of a table for which to retrieve analysis results
     * @return Project - containing only the data quality analysis results details
     */
    private Project getDataQualityAnalysisResults(String projectName,
                                                 String tableName) {
        return makeTableBasedRequest(projectName, tableName, EP_DQ_ANALYSIS_RESULTS, "getDataQualityAnalysisResults");
    }

    /**
     * Retrieve information on results that have been published for the specified IA project.
     *
     * @param projectName the name of the IA project for which to retrieve the published results
     * @return {@code Map<String, Date>} - a map from table name to last publication date, empty if nothing published
     */
    public Map<String, Date> getPublishedResults(String projectName) {
        Project publishedResults = makeColumnBasedRequest(projectName, null, EP_PUBLISHED_RESULTS, "getPublishedResults");
        Map<String, Date> map = new HashMap<>();
        if (publishedResults != null) {
            List<Table> results = publishedResults.getPublishedResults();
            if (results != null) {
                for (Table table : results) {
                    map.put(table.getName(), table.getPublicationDateOfAnalysisResults());
                }
            }
        }
        return map;
    }

    /**
     * Run a column analysis within the specified project, against the specified column(s).
     * The name of the column should be fully-qualified (database.schema.table.column), and multiple columns can be
     * specified by using a '*' for example: database.schema.table.* for all columns in a table.
     *
     * @param projectName the name of the project in which to run the column analysis
     * @param columnName the fully-qualified name of the column(s) to run analysis against
     * @return TaskExecutionReport containing the scheduled task ID or any errors
     */
    public TaskExecutionReport runColumnAnalysis(String projectName,
                                                 String columnName) {
        Column column = new Column();
        column.setName(columnName);
        List<Column> columns = new ArrayList<>();
        columns.add(column);
        RunColumnAnalysis runColumnAnalysis = new RunColumnAnalysis();
        runColumnAnalysis.setColumns(columns);
        return makeTaskRequest(projectName, runColumnAnalysis, "runColumnAnalysis");
    }

    /**
     * Run a data quality analysis within the specified project, against the specified table.
     * The name of the table should be fully-qualified (database.schema.table).
     *
     * @param projectName the name of the project in which to run the data quality analysis
     * @param tableName the fully-qualified name of the table to run analysis against
     * @return TaskExecutionReport containing the scheduled task ID or any errors
     */
    public TaskExecutionReport runDataQualityAnalysis(String projectName,
                                                      String tableName) {
        Table table = new Table();
        table.setName(tableName);
        List<Table> tables = new ArrayList<>();
        tables.add(table);
        RunDataQualityAnalysis runDataQualityAnalysis = new RunDataQualityAnalysis();
        runDataQualityAnalysis.setTables(tables);
        return makeTaskRequest(projectName, runDataQualityAnalysis, "runDataQualityAnalysis");
    }

    /**
     * Publish analysis results from the specified project's specified table.
     *
     * @param projectName the name of the project from which to publish analysis results
     * @param tableName the fully-qualified name of the table for which to publish results
     */
    public void publishResults(String projectName,
                               String tableName) {
        Table table = new Table();
        table.setName(tableName);
        List<Table> tables = new ArrayList<>();
        tables.add(table);
        PublishResults toPublish = new PublishResults();
        toPublish.setTableList(tables);
        publishResults(projectName, toPublish);
    }

    /**
     * Retrieve the status of each task within a running request.
     *
     * @param taskExecutionReport the response of a running request containing task information
     * @return {@code List<TaskExecutionSchedule>}
     */
    public List<TaskExecutionSchedule> getTaskStatus(TaskExecutionReport taskExecutionReport) {
        List<TaskExecutionSchedule> taskExecutionSchedules = new ArrayList<>();
        for (ScheduledTask scheduledTask : taskExecutionReport.getScheduledTaskList()) {
            TaskExecutionSchedule taskExecutionSchedule = getTaskStatus(scheduledTask.getScheduleId());
            if (taskExecutionSchedule != null) {
                taskExecutionSchedules.add(taskExecutionSchedule);
            }
        }
        return taskExecutionSchedules;
    }

    /**
     * Retrieve the status of a task from its scheduled ID.
     *
     * @param scheduleId the scheduleId of the task for which to retrieve the status
     * @return TaskExecutionSchedule
     */
    public TaskExecutionSchedule getTaskStatus(String scheduleId) {
        String response = makeRequest(EP_TASK_STATUS + "?scheduleID=" + scheduleId, HttpMethod.GET, null);
        TaskExecutionSchedule taskExecutionSchedule = null;
        try {
            taskExecutionSchedule = mapper.readValue(response, TaskExecutionSchedule.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse execution status for '{}': {}", scheduleId, response, e); }
        }
        return taskExecutionSchedule;
    }

    /**
     * Publish the results from the specified project, using the details provided.
     *
     * @param projectName the name of the project from which to publish results
     * @param details the details of which column(s) and options to use for publishing
     */
    private void publishResults(String projectName,
                                PublishResults details) {
        String xmlPayload = getTaskPayload(projectName, details);
        if (log.isInfoEnabled()) { log.info("Task request payload: " + xmlPayload); }
        String response = makeRequest(EP_PUBLISH, HttpMethod.POST, xmlPayload);
        if (response != null) {
            throw new NullPointerException("Error publishing: " + response);
        }
    }

    /**
     * Translate the provided POJO into an XML document for executing a task.
     *
     * @param projectName the name of the project in which to run the task
     * @param value the POJO to translate
     * @return String - the XML-translated content of the POJO
     */
    private String getTaskPayload(String projectName,
                                  Object value) {
        StringWriter out = new StringWriter();
        try {
            XMLStreamWriter xmlWriter = mapper.getFactory().getXMLOutputFactory().createXMLStreamWriter(out);
            xmlWriter.writeStartDocument();
            xmlWriter.writeStartElement("iaapi", "Project", "http://www.ibm.com/investigate/api/iaapi");
            xmlWriter.writeAttribute("name", projectName);
            xmlWriter.writeStartElement("Tasks");
            mapper.writeValue(xmlWriter, value);
            xmlWriter.writeEndElement(); // /Tasks
            xmlWriter.writeEndElement(); // /Project
            xmlWriter.writeEndDocument();
            xmlWriter.flush();
            xmlWriter.close();
        } catch (JsonProcessingException e) {
            log.error("Unable to translate provided {} object to XML: {}", value.getClass().getName(), value, e);
        } catch (XMLStreamException e) {
            log.error("Unable to write to XML stream: {}", value, e);
        } catch (IOException e) {
            log.error("Unable to translate provided {} object to XML stream: {}", value.getClass().getName(), value, e);
        }
        return out.toString();
    }

    /**
     * Request the execution of a task based on the provided details.
     *
     * @param projectName the name of the IA project in which to execute the task
     * @param details the details of the task to execute
     * @param methodName a description of the operation that is being requested
     * @return TaskExecutionReport
     */
    private TaskExecutionReport makeTaskRequest(String projectName,
                                                Object details,
                                                String methodName) {
        String xmlPayload = getTaskPayload(projectName, details);
        if (log.isInfoEnabled()) { log.info("Task request payload: " + xmlPayload); }
        String response = makeRequest(EP_EXECUTE_TASK, HttpMethod.POST, xmlPayload);
        TaskExecutionReport taskExecutionReport = null;
        try {
            taskExecutionReport = mapper.readValue(response, TaskExecutionReport.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse {} execution report for project '{}': {}", methodName, projectName, response, e); }
        }
        return taskExecutionReport;
    }

    /**
     * Make a request that is based on a combination of a project and an (optional) column name.
     *
     * @param projectName the name of the IA project for which to retrieve results
     * @param columnName (optional) qualified name of a column for which to retrieve results
     * @param urlBase the base API URL against which to invoke the operation
     * @param methodName a description of the operation that is being invoked
     * @return Project
     */
    private Project makeColumnBasedRequest(String projectName,
                                           String columnName,
                                           String urlBase,
                                           String methodName) {
        String url = urlBase + "?projectName=" + encodeParameterForURL(projectName);
        if (columnName != null) {
            url += "&columnName=" + encodeParameterForURL(columnName);
        }
        String response = makeRequest(url, HttpMethod.GET, null);
        Project project = null;
        try {
            project = mapper.readValue(response, Project.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                if (columnName == null) {
                    log.error("Unable to parse {} results for project '{}': {}", methodName, projectName, response, e);
                } else {
                    log.error("Unable to parse {} results for project '{}' and column '{}': {}", methodName, projectName, columnName, response, e);
                }
            }
        }
        return project;
    }

    /**
     * Make a request that is based on a combination of a project and an (optional) table name.
     *
     * @param projectName the name of the IA project for which to retrieve results
     * @param tableName (optional) qualified name of a table for which to retrieve results
     * @param urlBase the base API URL against which to invoke the operation
     * @param methodName a description of the operation that is being invoked
     * @return Project
     */
    private Project makeTableBasedRequest(String projectName,
                                          String tableName,
                                          String urlBase,
                                          String methodName) {
        String url = urlBase + "?projectName=" + encodeParameterForURL(projectName);
        if (tableName != null) {
            url += "&tableName=" + encodeParameterForURL(tableName);
        }
        String response = makeRequest(url, HttpMethod.GET, null);
        Project project = null;
        try {
            project = mapper.readValue(response, Project.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                if (tableName == null) {
                    log.error("Unable to parse {} results for project '{}': {}", methodName, projectName, response, e);
                } else {
                    log.error("Unable to parse {} results for project '{}' and table '{}': {}", methodName, projectName, tableName, response, e);
                }
            }
        }
        return project;
    }

    /**
     * Encode the provided value as one that can be passed via GET.
     *
     * @param value the value to encode
     * @return String - the encoded value
     */
    private String encodeParameterForURL(String value) {
        String encoded = value;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Unable to encode parameter value for a URL: {}", value, e);
        }
        return encoded;
    }

    /**
     * Recursively remove all whitespace (including newlines) between XML elements, in-place in the provided XML.
     *
     * @param node the XML node from which to start the minimization
     */
    private void minimizeXML(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                child.setTextContent(child.getTextContent().trim());
            }
            minimizeXML(child);
        }
    }

}
