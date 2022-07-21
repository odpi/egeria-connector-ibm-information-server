/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCIOException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.DynamicPropertyReader;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Paging;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.TypeDetails;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.TypeHeader;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.TypeProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Library of methods to connect to and interact with an IBM Information Governance Catalog environment
 * using appropriate session management.
 * <br><br>
 * Methods are provided to interact with REST API endpoints and process results as JsonNode objects
 * (ie. allowing direct traversal of the JSON objects) and through the use of registered POJOs to
 * automatically (de-)serialise between the JSON form and a native Java object.
 * <br><br>
 * The native Java objects for out-of-the-box Information Governance Catalog asset types have been
 * generated under org.odpi.openmetadata.adapters.repositoryservices.igc.model.* -- including different
 * versions depending on the environment to which you are connecting.
 * <br><br>
 * For additional examples of using the REST API (eg. potential criteria and operators for searching, etc), see:
 * <ul>
 *     <li><a href="http://www-01.ibm.com/support/docview.wss?uid=swg27047054">IGC REST API: Tips, tricks, and time-savers</a></li>
 *     <li><a href="http://www-01.ibm.com/support/docview.wss?uid=swg27047059">IGC REST API: Sample REST API calls and use case descriptions</a></li>
 * </ul>
 *
 * @see #registerPOJO(Class)
 */
public class IGCRestClient {

    private static final Logger log = LoggerFactory.getLogger(IGCRestClient.class);

    private String authorization;
    private String baseURL;
    private Boolean workflowEnabled = false;
    private List<String> cookies = null;
    private RestTemplate restTemplate;

    private IGCVersionEnum igcVersion;
    private HashMap<String, DynamicPropertyReader> typeAndPropertyToAccessor;

    private Set<String> typesThatCanBeCreated;
    private Set<String> typesThatIncludeModificationDetails;
    private Map<String, String> typeToDisplayName;
    private Map<String, List<String>> typeToNonRelationshipProperties;
    private Map<String, List<String>> typeToStringProperties;
    private Map<String, List<String>> typeToAllProperties;
    private Map<String, List<String>> typeToPagedRelationshipProperties;

    private Map<String, Class<?>> registeredTypes;

    private int defaultPageSize = 100;

    private ObjectMapper mapper;
    private ObjectMapper typeMapper;

    private static final String EP_BASE_API = "/ibm/iis/igc-rest/v1";
    private static final String EP_TYPES = EP_BASE_API + "/types";
    private static final String EP_ASSET = EP_BASE_API + "/assets";
    private static final String EP_SEARCH = EP_BASE_API + "/search";
    private static final String EP_LOGOUT  = EP_BASE_API + "/logout";
    private static final String EP_BUNDLES = EP_BASE_API + "/bundles";
    private static final String EP_BUNDLE_ASSETS = EP_BUNDLES + "/assets";
    private static final String REPORT_ENDPOINT = EP_BASE_API + "/flows/report";

    /**
     * Default constructor used by the IGCRestClient.
     * <br><br>
     * Creates a new session on the server and retains the cookies to re-use the same session for the life
     * of the client (or until the session times out); whichever occurs first.
     *
     * @param host the services (domain) tier host
     * @param port the services (domain) tier port number
     * @param user the username with which to open and retain the session
     * @param password the password for the user
     * @throws IGCConnectivityException if there is any issue connecting to IGC
     */
    public IGCRestClient(String host, String port, String user, String password) throws IGCConnectivityException {
        this("https://" + host + ":" + port, user, password);
    }

    /**
     * Creates a new session on the server and retains the cookies to re-use the same session for the life
     * of the client (or until the session times out); whichever occurs first.
     *
     * @param baseURL the base URL of the domain tier of Information Server
     * @param user the username with which to open and retain the session
     * @param password the password of the user
     * @throws IGCConnectivityException if there is any issue connecting to IGC
     */
    public IGCRestClient(String baseURL, String user, String password) throws IGCConnectivityException {
        this(baseURL, encodeBasicAuth(user, password));
    }

    /**
     * Creates a new session on the server and retains the cookies to re-use the same session for the life
     * of the client (or until the session times out); whichever occurs first.
     *
     * @param baseURL the base URL of the domain tier of Information Server
     * @param authorization the Basic-encoded authorization string to use to login to Information Server
     * @throws IGCConnectivityException if there is any issue connecting to IGC
     */
    protected IGCRestClient(String baseURL, String authorization) throws IGCConnectivityException {

        if (baseURL == null || !baseURL.startsWith("https://")) {
            throw new IGCConnectivityException("Cannot instantiate IGCRestClient -- baseURL must be https.", baseURL);
        }

        this.baseURL = baseURL;
        this.authorization = authorization;
        // We need to allow a single value as an array for a couple cases where one version of IGC uses
        // an array of values (Strings) and another only has a single String (eg. 'rule_logic' in
        // 'published_data_rule_definition'), and also allow empty Strings to be equivalent to nulls, for
        // areas where a Number is expected but the payload may contain "" (eg. 'length' of 'database_column' as
        // derived from 'data_item')
        this.mapper = new ObjectMapper()
                            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this.typeMapper = new ObjectMapper();
        this.typeAndPropertyToAccessor = new HashMap<>();
        this.restTemplate = new RestTemplate();

        // Ensure that the REST template always uses UTF-8
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        converters.removeIf(httpMessageConverter -> httpMessageConverter instanceof StringHttpMessageConverter);
        converters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        this.typesThatCanBeCreated = new HashSet<>();
        this.typesThatIncludeModificationDetails = new HashSet<>();
        this.typeToDisplayName = new HashMap<>();
        this.typeToNonRelationshipProperties = new HashMap<>();
        this.typeToStringProperties = new HashMap<>();
        this.typeToAllProperties = new HashMap<>();
        this.typeToPagedRelationshipProperties = new HashMap<>();

        this.registeredTypes = new HashMap<>();

        // Setup these values up-front for the 'note' type, which is not formally a type otherwise
        this.typesThatIncludeModificationDetails.add(IGCRestConstants.NOTE);
        this.typeToDisplayName.put(IGCRestConstants.NOTE, "Note");
        this.typeToNonRelationshipProperties.put(IGCRestConstants.NOTE, Arrays.asList("note", "status", "subject", "type"));
        this.typeToStringProperties.put(IGCRestConstants.NOTE, Arrays.asList("note", "status", "subject", "type"));
        this.typeToAllProperties.put(IGCRestConstants.NOTE, Arrays.asList("belonging_to", "note", "status", "subject", "type"));
        this.typeToPagedRelationshipProperties.put(IGCRestConstants.NOTE, Arrays.asList("note", "status", "subject", "type"));

    }

    /**
     * Start the client by trying to connect based on the configured parameters.
     * @return boolean indicating true if the client was successfully started, or false if not.
     * @throws IGCConnectivityException if there is any issue connecting to IGC
     * @throws IGCParsingException if there is any issue parsing a response from IGC
     */
    public boolean start() throws IGCConnectivityException, IGCParsingException {

        // Run a simple initial query to obtain a session and setup the cookies
        IGCSearch igcSearch = new IGCSearch("category");
        igcSearch.addType("term");
        igcSearch.addType("information_governance_policy");
        igcSearch.addType("information_governance_rule");
        igcSearch.setPageSize(1);
        igcSearch.setDevGlossary(true);
        String response = searchJson(igcSearch);

        log.debug("Checking for workflow and registering version...");
        ObjectMapper tmpMapper = new ObjectMapper();
        try {
            this.workflowEnabled = tmpMapper.readValue(response, new TypeReference<ItemList<Reference>>(){}).getPaging().getNumTotal() > 0;
        } catch (IOException e) {
            throw new IGCConnectivityException("Unable to determine if workflow is enabled.", e);
        }

        // Start with lowest version supported
        this.igcVersion = IGCVersionEnum.values()[0];
        List<TypeHeader> igcTypes = getTypes(tmpMapper);
        Set<String> typeNames = igcTypes.stream().map(TypeHeader::getId).collect(Collectors.toSet());
        for (IGCVersionEnum aVersion : IGCVersionEnum.values()) {
            if (aVersion.isHigherThan(this.igcVersion)
                    && typeNames.contains(aVersion.getTypeNameFirstAvailableInThisVersion())
                    && !typeNames.contains(aVersion.getTypeNameNotAvailableInThisVersion())) {
                this.igcVersion = aVersion;
            }
        }
        log.info("Detected IGC version: {}", this.igcVersion.getVersionString());

        // So long as no runtime exception was thrown by the steps above, we have successfully started
        return true;

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
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        // If we have cookies already, and haven't been asked to force the login,
        // re-use these (to maintain the same session)
        if (cookies != null && !forceLogin) {
            // Note that validation of the cookies is done when we store them, and all access is private to this class
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
     * @param contentType the type of content to expect in the payload (if any)
     * @param payload the payload (if any) for the request
     * @param alreadyTriedNewSession indicates whether a new session was already attempted (true) or not (false)
     * @param cause the underlying exception stack, if we have already tried to open a new session without success
     * @return {@code ResponseEntity<String>}
     * @throws IGCConnectivityException if the attempt to open a new session fails
     */
    private ResponseEntity<String> openNewSessionWithRequest(String url,
                                                             HttpMethod method,
                                                             MediaType contentType,
                                                             String payload,
                                                             boolean alreadyTriedNewSession,
                                                             Exception cause) throws IGCConnectivityException {
        if (alreadyTriedNewSession) {
            String formattedMessage = "Opening a new session already attempted without success -- giving up: " + method + " to " + url + " with: " + payload;
            throw new IGCConnectivityException(formattedMessage, cause);
        } else {
            // By removing cookies, we'll force a login
            this.cookies = null;
            return makeRequest(url, method, contentType, payload, true);
        }
    }

    /**
     * Attempts to open a new session while uploading the provided file. If the alreadyTriedNewSession is true,
     * and we are unable to open a new session with this attempt, will give up. If the alreadyTriedNewSession is false,
     * will attempt to re-upload the file to open a new session precisely once before giving up.
     *
     * @param endpoint the endpoint to which to upload the file
     * @param method the HTTP method to use in sending the request
     * @param file the Spring FileSystemResource or ClassPathResource containing the file to be uploaded
     * @param alreadyTriedNewSession indicates whether a new session was already attempted (true) or not (false)
     * @param cause the underlying exception stack, if we have already tried to open a new session without success
     * @return {@code ResponseEntity<String>}
     * @throws IGCConnectivityException if the attempt to open a new session fails
     */
    private ResponseEntity<String> openNewSessionWithUpload(String endpoint,
                                                            HttpMethod method,
                                                            AbstractResource file,
                                                            boolean alreadyTriedNewSession,
                                                            Exception cause) throws IGCConnectivityException {
        if (alreadyTriedNewSession) {
            String formattedMessage = "Opening a new session already attempted without success -- giving up: " + method + " to " + endpoint + " with: " + file.toString();
            throw new IGCConnectivityException(formattedMessage, cause);
        } else {
            log.info("Session appears to have timed out -- starting a new session and re-trying the upload.");
            // By removing cookies, we'll force a login
            this.cookies = null;
            return uploadFile(endpoint, method, file, true);
        }
    }

    /**
     * Adds the cookies from a response into subsequent headers, so that we re-use the session indicated by those
     * cookies.
     *
     * @param response the response from which to obtain the cookies
     * @throws IGCConnectivityException if an invalid cookie is found, suggesting some attempt at hacking
     */
    private void setCookiesFromResponse(ResponseEntity<String> response) throws IGCConnectivityException {

        // If we had a successful response, setup the cookies
        if (response.getStatusCode() == HttpStatus.OK
                || response.getStatusCode() == HttpStatus.CREATED
                || response.getStatusCode() == HttpStatus.ACCEPTED) {
            HttpHeaders headers = response.getHeaders();
            if (headers.get(HttpHeaders.SET_COOKIE) != null) {
                // Validate each cookie against our whitelist of valid cookies, to avoid any potential security exposure
                List<String> candidateCookies = headers.get(HttpHeaders.SET_COOKIE);
                if (candidateCookies != null) {
                    cookies = new ArrayList<>();
                    for (String candidate : candidateCookies) {
                        String[] tokens = candidate.split("=");
                        if (tokens.length < 2) {
                            throw new IGCConnectivityException("An invalid cookie was found, which could present a security problem.", candidate);
                        }
                        String cookieName = tokens[0];
                        if (!IGCRestConstants.getValidCookieNames().contains(cookieName)) {
                            throw new IGCConnectivityException("An invalid cookie was found, which could present a security problem.", candidate);
                        }
                        Matcher m = IGCRestConstants.COOKIE_WHITELIST.matcher(candidate);
                        if (m.matches()) {
                            cookies.add(candidate);
                        } else {
                            throw new IGCConnectivityException("A cookie was found that has invalid characters and could therefore present a security problem.", candidate);
                        }
                    }
                }
            }
        } else {
            throw new IGCConnectivityException("Unable to make request or unexpected status.", response.getStatusCode().toString());
        }

    }

    /**
     * Attempt to convert the JSON string into a Java object.
     *
     * @param json the JSON string to convert
     * @param <T> the type of POJO into which to read
     * @return T - an IGC object that is at least a Reference, but can be more specific
     * @throws IGCParsingException if the attempt to read the JSON fails
     */
    public <T extends Reference> T readJSONIntoPOJO(String json) throws IGCParsingException {
        T reference;
        try {
            reference = this.mapper.readValue(json, new TypeReference<T>(){});
        } catch (IOException e) {
            throw new IGCParsingException("Unable to translate JSON into POJO.", json, e);
        }
        return reference;
    }

    /**
     * Attempt to convert the JSON string into an ItemList.
     *
     * @param json the JSON string to convert
     * @param <T> the type of items that should be in the ItemList
     * @return {@code ItemList<T>}
     * @throws IGCParsingException if the attempt to read the JSON fails
     */
    public <T extends Reference> ItemList<T> readJSONIntoItemList(String json) throws IGCParsingException {
        ItemList<T> itemList;
        try {
            itemList = this.mapper.readValue(json, new TypeReference<ItemList<T>>(){});
        } catch (IOException e) {
            throw new IGCParsingException("Unable to translate JSON into ItemList.", json, e);
        }
        return itemList;
    }

    /**
     * Attempt to convert the provided IGC object into JSON, based on the registered POJOs.
     *
     * @param asset the IGC asset to convert
     * @return String of JSON representing the asset
     * @throws IGCParsingException if the attempt to read the JSON fails
     */
    public String getValueAsJSON(Reference asset) throws IGCParsingException {
        String payload;
        try {
            payload = this.mapper.writeValueAsString(asset);
        } catch (JsonProcessingException e) {
            throw new IGCParsingException("Unable to translate asset into JSON.", asset.toString(), e);
        }
        return payload;
    }

    /**
     * Retrieve the version of the IGC environment (static member VERSION_115 or VERSION_117).
     *
     * @return IGCVersionEnum
     */
    public IGCVersionEnum getIgcVersion() { return igcVersion; }

    /**
     * Retrieve the base URL of this IGC REST API connection.
     *
     * @return String
     */
    public String getBaseURL() { return baseURL; }

    /**
     * Retrieve the default page size for this IGC REST API connection.
     *
     * @return int
     */
    public int getDefaultPageSize() { return defaultPageSize; }

    /**
     * Set the default page size for this IGC REST API connection.
     *
     * @param pageSize the new default page size to use
     */
    public void setDefaultPageSize(int pageSize) { this.defaultPageSize = pageSize; }

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
     * Indicates whether the provided Repository ID (RID) represents a virtual asset (true) or not (false).
     *
     * @param rid the Repository ID (RID) to check
     * @return boolean
     */
    public static boolean isVirtualAssetRid(String rid) {
        return rid == null || rid.startsWith("extern:");
    }

    /**
     * Indicates whether the provided Repository ID (RID) represents an embedded asset (true) or not (false).
     *
     * @param rid the Repository ID (RID) to check
     * @return boolean
     */
    public static boolean isEmbeddedAssetRid(String rid) {
        return rid == null || rid.startsWith("deflated:");
    }

    /**
     * Internal utility for making potentially repeat requests (if session expires and needs to be re-opened),
     * to upload a file to a given endpoint.
     *
     * @param endpoint the REST resource against which to POST the upload
     * @param file the Spring FileSystemResource or ClassPathResource of the file to be uploaded
     * @param forceLogin a boolean indicating whether login should be forced (true) or session reused (false)
     * @return {@code ResponseEntity<String>}
     * @throws IGCConnectivityException if there is any connectivity issue during the upload
     */
    private ResponseEntity<String> uploadFile(String endpoint, HttpMethod method, AbstractResource file, boolean forceLogin) throws IGCConnectivityException {

        HttpHeaders headers = getHttpHeaders(forceLogin);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<String> response;
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);

        HttpEntity<MultiValueMap<String, Object>> toSend = new HttpEntity<>(body, headers);

        String url = baseURL + (endpoint.startsWith("/") ? endpoint : "/" + endpoint);

        try {
            response = restTemplate.exchange(
                    url,
                    method,
                    toSend,
                    String.class
            );
        } catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized e) {
            log.warn("Request failed -- session may have expired, retrying...", e);
            // If the response was forbidden or unauthorized (fails with exception), the session may have expired so
            // attempt to create a new one
            response = openNewSessionWithUpload(
                    endpoint,
                    method,
                    file,
                    forceLogin,
                    e
            );
        } catch (RestClientException e) {
            throw new IGCConnectivityException("Request failed -- check IGC environment connectivity and authentication details.", e);
        }

        return response;

    }

    /**
     * General utility for uploading binary files.
     *
     * @param endpoint the REST resource against which to upload the file
     * @param method HttpMethod (POST, PUT, etc)
     * @param file the Spring FileSystemResource or ClassPathResource containing the file to be uploaded
     * @return boolean - indicates success (true) or failure (false)
     * @throws IGCConnectivityException if there is any connectivity issue during the upload
     */
    public boolean uploadFile(String endpoint, HttpMethod method, AbstractResource file) throws IGCConnectivityException {
        ResponseEntity<String> response = uploadFile(endpoint, method, file, false);
        return (response != null && response.getStatusCode() == HttpStatus.OK);
    }

    /**
     * Internal utility for making potentially repeat requests (if session expires and needs to be re-opened).
     *
     * @param url the URL against which to make the request
     * @param method HttpMethod (GET, POST, etc)
     * @param contentType the type of content to expect in the payload (if any)
     * @param payload if POSTing some content, the JSON structure providing what should be POSTed
     * @param forceLogin a boolean indicating whether login should be forced (true) or session reused (false)
     * @return {@code ResponseEntity<String>}
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    private ResponseEntity<String> makeRequest(String url,
                                               HttpMethod method,
                                               MediaType contentType,
                                               String payload,
                                               boolean forceLogin) throws IGCConnectivityException {
        HttpHeaders headers = getHttpHeaders(forceLogin);
        HttpEntity<String> toSend;
        if (payload != null) {
            headers.setContentType(contentType);
            toSend = new HttpEntity<>(payload, headers);
        } else {
            toSend = new HttpEntity<>(headers);
        }
        ResponseEntity<String> response;
        try {
            log.debug("{}ing to {} with: {}", method, url, payload);
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build(true);
            response = restTemplate.exchange(
                    uriComponents.toUri(),
                    method,
                    toSend,
                    String.class);
            setCookiesFromResponse(response);
        } catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized e) {
            log.warn("Request failed -- session may have expired, retrying...", e);
            // If the response was forbidden or unauthorized (fails with exception), the session may have expired so
            // attempt to create a new one
            response = openNewSessionWithRequest(
                    url,
                    method,
                    contentType,
                    payload,
                    forceLogin,
                    e
            );
        } catch (RestClientException e) {
            throw new IGCConnectivityException("Request failed -- check IGC environment connectivity and authentication details.", e);
        }
        return response;
    }

    /**
     * General utility for making requests.
     *
     * @param endpoint the REST resource against which to make the request
     * @param method HttpMethod (GET, POST, etc)
     * @param contentType the type of content to expect in the payload (if any)
     * @param payload if POSTing some content, the JSON structure providing what should be POSTed
     * @return String - containing the body of the response
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public String makeRequest(String endpoint, HttpMethod method, MediaType contentType, String payload) throws IGCConnectivityException {
        ResponseEntity<String> response = makeRequest(
                baseURL + (endpoint.startsWith("/") ? endpoint : "/" + endpoint),
                method,
                contentType,
                payload,
                false
        );
        String body = null;
        if (response == null) {
            String formattedMessage = method + " to " + endpoint + " with: " + payload;
            throw new IGCConnectivityException("Unable to complete request -- check IGC environment connectivity and authentication details.", formattedMessage);
        } else if (response.hasBody()) {
            body = response.getBody();
        }
        return body;
    }

    /**
     * General utility for making creation requests.
     *
     * @param endpoint the REST resource against which to make the request
     * @param method HttpMethod (POST, PUT, etc)
     * @param contentType the type of content to expect in the payload
     * @param payload the data that should be created
     * @return String - containing the RID of the created object instance
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public String makeCreateRequest(String endpoint, HttpMethod method, MediaType contentType, String payload) throws IGCConnectivityException {
        ResponseEntity<String> response = makeRequest(
                baseURL + (endpoint.startsWith("/") ? endpoint : "/" + endpoint),
                method,
                contentType,
                payload,
                false
        );
        String rid = null;
        if (response == null) {
            String formattedMessage = method + " to " + endpoint + " with: " + payload;
            throw new IGCConnectivityException("Unable to create instance -- check IGC environment connectivity and authentication details.", formattedMessage);
        } else if (!response.getStatusCode().equals(HttpStatus.CREATED)) {
            String formattedMessage = method + " to " + endpoint + " with: " + payload;
            throw new IGCConnectivityException("Instance creation failed -- check IGC environment connectivity and authentication details.", formattedMessage);
        } else {
            HttpHeaders headers = response.getHeaders();
            List<String> instanceURLs = headers.get("Location");
            if (instanceURLs != null && instanceURLs.size() == 1) {
                String instanceURL = instanceURLs.get(0);
                rid = instanceURL.substring(instanceURL.lastIndexOf("/") + 1);
            }
        }
        return rid;
    }

    /**
     * Retrieves the list of metadata types supported by IGC.
     *
     * @param objectMapper an ObjectMapper to use for translating the types list
     * @return {@code List<TypeHeader>} the list of types supported by IGC
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the types payload
     */
    public List<TypeHeader> getTypes(ObjectMapper objectMapper) throws IGCConnectivityException, IGCParsingException {
        String response = makeRequest(EP_TYPES, HttpMethod.GET, null,null);
        List<TypeHeader> alTypes;
        try {
            alTypes = objectMapper.readValue(response, new TypeReference<List<TypeHeader>>(){});
        } catch (IOException e) {
            throw new IGCParsingException("Unable to parse types response.", response, e);
        }
        return alTypes;
    }

    /**
     * Retrieves the type details (all properties and their details) for the provided type name in IGC.
     *
     * @param typeName the IGC type name for which to retrieve details
     * @return TypeDetails
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the types payload
     */
    public TypeDetails getTypeDetails(String typeName) throws IGCConnectivityException, IGCParsingException {
        return getTypeDetails(typeName, true, true, true);
    }

    /**
     * Retrieves the type details (requested properties and their details) for the provided type name in IGC.
     *
     * @param typeName the IGC type name for which to retrieve details
     * @param view whether to include the viewable properties
     * @param create whether to include the properties that can be included during creation
     * @param edit whether to include editable properties
     * @return TypeDetails
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the types payload
     */
    public TypeDetails getTypeDetails(String typeName, boolean view, boolean create, boolean edit) throws IGCConnectivityException, IGCParsingException {
        String response = makeRequest(EP_TYPES + "/" + typeName + "?showViewProperties=" + view + "&showCreateProperties=" + create + "&showEditProperties=" + edit, HttpMethod.GET, null, null);
        TypeDetails typeDetails;
        try {
            typeDetails = typeMapper.readValue(response, TypeDetails.class);
        } catch (IOException e) {
            throw new IGCParsingException("Unable to parse type details response.", response, e);
        }
        return typeDetails;
    }

    /**
     * Retrieve all information about an asset from IGC.
     * This can be an expensive operation that may retrieve far more information than you actually need.
     *
     * @see #getAssetRefById(String)
     *
     * @param rid the Repository ID of the asset
     * @return Reference - the IGC object representing the asset
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public Reference getAssetById(String rid) throws IGCConnectivityException, IGCParsingException {
        return getAssetById(rid, null);
    }

    /**
     * Retrieve all information about an asset from IGC.
     * This can be an expensive operation that may retrieve far more information than you actually need.
     *
     * @see #getAssetRefById(String)
     *
     * @param rid the Repository ID of the asset
     * @param cache a cache of previously-retrieved assets
     * @return Reference - the IGC object representing the asset
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public Reference getAssetById(String rid, ObjectCache cache) throws IGCConnectivityException, IGCParsingException {
        Reference result = null;
        if (cache != null) {
            result = cache.get(rid);
        }
        if (result == null) {
            String url = EP_ASSET + "/" + getEncodedPathVariable(rid) + "?referencePageSize=" + defaultPageSize;
            String response = makeRequest(url, HttpMethod.GET, null, null);
            result = readJSONIntoPOJO(response);
        }
        return result;
    }

    /**
     * Calculate a path-encoded URL for the provided endpoint.
     *
     * @param pathVariable the value within the path to encode
     * @return String - the encoded endpoint
     */
    public static String getEncodedPathVariable(String pathVariable) {
        return UriUtils.encode(pathVariable, "UTF-8");
    }

    /**
     * Retrieve only the minimal unique properties of an asset from IGC.
     * This will generally be the most performant way to see that an asset exists and get its identifying characteristics.
     *
     * @param rid the Repository ID of the asset
     * @return Reference - the minimalistic IGC object representing the asset
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public Reference getAssetRefById(String rid) throws IGCConnectivityException, IGCParsingException {

        // We can search for any object by ID by using "main_object" as the type
        // (no properties needed)
        IGCSearchCondition condition = new IGCSearchCondition(
                "_id",
                "=",
                rid
        );
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        IGCSearch igcSearch = new IGCSearch("main_object", conditionSet);
        // Add non-main_object types that might also be looked-up by RID
        igcSearch.addType("classification");
        igcSearch.addType("label");
        igcSearch.addType("user");
        igcSearch.addType("group");
        ItemList<Reference> results = search(igcSearch);
        Reference reference = null;
        if (results.getPaging().getNumTotal() > 0) {
            if (results.getPaging().getNumTotal() > 1) {
                log.warn("Found multiple assets for RID {}, taking only the first.", rid);
            }
            reference = results.getItems().get(0);
        }

        return reference;

    }

    /**
     * This will generally be the most performant method by which to retrieve asset information, when only
     * some subset of properties is required
     *
     * @param rid the repository ID (RID) of the asset to retrieve
     * @param assetType the IGC asset type of the asset to retrieve
     * @param properties a list of the properties to retrieve
     * @param <T> the type of Reference to return
     * @return Reference - the object including only the subset of properties specified
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public <T extends Reference> T getAssetWithSubsetOfProperties(String rid,
                                                                  String assetType,
                                                                  List<String> properties) throws IGCConnectivityException, IGCParsingException {
        return getAssetWithSubsetOfProperties(rid, assetType, properties, defaultPageSize);
    }

    /**
     * This will generally be the most performant method by which to retrieve asset information, when only
     * some subset of properties is required
     *
     * @param rid the repository ID (RID) of the asset to retrieve
     * @param assetType the IGC asset type of the asset to retrieve
     * @param properties a list of the properties to retrieve
     * @param <T> the type of Reference to return
     * @return Reference - the object including only the subset of properties specified
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public <T extends Reference> T getAssetWithSubsetOfProperties(String rid,
                                                                  String assetType,
                                                                  String[] properties) throws IGCConnectivityException, IGCParsingException {
        return getAssetWithSubsetOfProperties(rid, assetType, properties, defaultPageSize);
    }

    /**
     * This will generally be the most performant method by which to retrieve asset information, when only
     * some subset of properties is required
     *
     * @param rid the repository ID (RID) of the asset to retrieve
     * @param assetType the IGC asset type of the asset to retrieve
     * @param properties a list of the properties to retrieve
     * @param pageSize the maximum number of each of the asset's relationships to return on this request
     * @param <T> the type of Reference to return
     * @return Reference - the object including only the subset of properties specified
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public <T extends Reference> T getAssetWithSubsetOfProperties(String rid,
                                                                  String assetType,
                                                                  String[] properties,
                                                                  int pageSize) throws IGCConnectivityException, IGCParsingException {
        return getAssetWithSubsetOfProperties(rid, assetType, Arrays.asList(properties), pageSize);
    }

    /**
     * This will generally be the most performant method by which to retrieve asset information, when only
     * some subset of properties is required
     *
     * @param rid the repository ID (RID) of the asset to retrieve
     * @param assetType the IGC asset type of the asset to retrieve
     * @param properties a list of the properties to retrieve
     * @param pageSize the maximum number of each of the asset's relationships to return on this request
     * @param <T> the type of Reference to return
     * @return Reference - the object including only the subset of properties specified
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    @SuppressWarnings("unchecked")
    public <T extends Reference> T getAssetWithSubsetOfProperties(String rid,
                                                                  String assetType,
                                                                  List<String> properties,
                                                                  int pageSize) throws IGCConnectivityException, IGCParsingException {
        if (IGCRestConstants.getTypesThatCannotBeSearched().contains(assetType)) {
            log.debug("Retrieving full asset {}, as it cannot be searched to retrieve only a subset of properties.", rid);
            Reference full = getAssetById(rid);
            return (T) full;
        } else {
            log.debug("Retrieving asset {} with subset of details: {}", rid, properties);
            T assetWithProperties = null;
            IGCSearchCondition idOnly = new IGCSearchCondition("_id", "=", rid);
            IGCSearchConditionSet idOnlySet = new IGCSearchConditionSet(idOnly);
            IGCSearch igcSearch = new IGCSearch(IGCRestConstants.getAssetTypeForSearch(assetType), properties, idOnlySet);
            if (pageSize > 0) {
                igcSearch.setPageSize(pageSize);
            }
            ItemList<T> assetsWithProperties = search(igcSearch);
            if (!assetsWithProperties.getItems().isEmpty()) {
                assetWithProperties = assetsWithProperties.getItems().get(0);
            }
            return assetWithProperties;
        }
    }

    /**
     * Retrieve all assets that match the provided search criteria from IGC.
     *
     * @param igcSearch the IGCSearch object defining criteria by which to search
     * @return JsonNode - the first JSON page of results from the search
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    private String searchJson(IGCSearch igcSearch) throws IGCConnectivityException {
        return makeRequest(EP_SEARCH, HttpMethod.POST, MediaType.APPLICATION_JSON, igcSearch.getQuery().toString());
    }

    /**
     * Retrieve all assets that match the provided search criteria from IGC.
     *
     * @param igcSearch search conditions and criteria to use
     * @param <T> the type of items that should be in the ItemList
     * @return {@code ItemList<T>} - the first page of results from the search
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public <T extends Reference> ItemList<T> search(IGCSearch igcSearch) throws IGCConnectivityException, IGCParsingException {
        ItemList<T> itemList;
        String results = searchJson(igcSearch);
        try {
            itemList = this.mapper.readValue(results, new TypeReference<ItemList<T>>(){});
        } catch (IOException e) {
            throw new IGCParsingException("Unable to translate JSON results.", results, e);
        }
        return itemList;
    }

    /**
     * Update the asset specified by the provided RID with the value(s) provided.
     *
     * @param rid the Repository ID of the asset to update
     * @param value the JSON structure defining what value(s) of the asset to update (and mode)
     * @return String - the JSON indicating the updated asset's RID and updates made
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    private String updateJson(String rid, JsonNode value) throws IGCConnectivityException {
        return makeRequest(EP_ASSET + "/" + rid, HttpMethod.PUT, MediaType.APPLICATION_JSON, value.toString());
    }

    /**
     * Apply the update described by the provided update object.
     *
     * @param igcUpdate update criteria to use
     * @return boolean - indicating success (true) or not (false) of the operation
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public boolean update(IGCUpdate igcUpdate) throws IGCConnectivityException {
        String result = updateJson(igcUpdate.getRidToUpdate(), igcUpdate.getUpdate());
        return (result != null);
    }

    /**
     * Create the asset specified by the provided value(s).
     *
     * @param value the JSON structure defining what should be created
     * @return String - the created asset's RID
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    private String createJson(JsonNode value) throws IGCConnectivityException {
        return makeCreateRequest(EP_ASSET, HttpMethod.POST, MediaType.APPLICATION_JSON, value.toString());
    }

    /**
     * Create the object described by the provided create object.
     *
     * @param igcCreate creation criteria to use
     * @return String - the created asset's RID (or null if nothing was created)
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public String create(IGCCreate igcCreate) throws IGCConnectivityException {
        return createJson(igcCreate.getCreate());
    }

    /**
     * Delete the asset specified by the provided RID.
     *
     * @param rid the RID of the asset to delete
     * @return String - null upon successful deletion, otherwise containing a message pertaining to the failure
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    private String deleteJson(String rid) throws IGCConnectivityException {
        return makeRequest(EP_ASSET + "/" + rid, HttpMethod.DELETE, MediaType.APPLICATION_JSON, null);
    }

    /**
     * Delete the object specified by the provided RID.
     *
     * @param rid the RID of the asset to delete
     * @return boolean
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public boolean delete(String rid) throws IGCConnectivityException {
        String result = deleteJson(rid);
        if (result != null) {
            throw new IGCConnectivityException("Unable to delete asset.", rid);
        } else {
            return true;
        }
    }

    /**
     * Run lineage detection against the provided job (by RID).
     *
     * @param jobRid the RID of the job for which to detect lineage
     * @return boolean giving indication of success (true) or not (false)
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public boolean detectLineage(String jobRid) throws IGCConnectivityException {
        ResponseEntity<String> response = makeRequest(
                baseURL + EP_BASE_API + "/flows/detectFlows/dsjob/" + jobRid,
                HttpMethod.GET,
                MediaType.APPLICATION_JSON,
                null,
                false
        );
        if (response != null) {
            // A successful lineage detection should return 202 (ACCEPTED), anything else will be
            // considered a failure
            return (response.getStatusCode() == HttpStatus.ACCEPTED);
        }
        return false;
    }

    /**
     * Upload the specified bundle, creating it if it does not already exist or updating it if it does.
     *
     * @param name the bundleId of the bundle
     * @param file the Spring FileSystemResource or ClassPathResource containing the file to be uploaded
     * @return boolean - indication of success (true) or failure (false)
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public boolean upsertOpenIgcBundle(String name, AbstractResource file) throws IGCConnectivityException, IGCParsingException {
        boolean success;
        List<String> existingBundles = getOpenIgcBundles();
        if (existingBundles.contains(name)) {
            success = uploadFile(EP_BUNDLES, HttpMethod.PUT, file);
        } else {
            success = uploadFile(EP_BUNDLES, HttpMethod.POST, file);
        }
        return success;
    }

    /**
     * Generates an OpenIGC bundle zip file from the provided directory path, and returns the temporary file it creates.
     *
     * @param directory the directory under which the OpenIGC bundle is defined (ie. including an
     *                  'asset_type_descriptor.xml', an 'i18n' subdirectory and an 'icons' subdirectory)
     * @return File - the temporary zip file containing the bundle
     * @throws IGCIOException if there is any issue loading the provided directory or creating a bundle from it
     */
    public File createOpenIgcBundleFile(File directory) throws IGCIOException {

        File bundle;
        try {
            bundle = File.createTempFile("openigc", "zip");
        } catch (IOException e) {
            throw new IGCIOException("Unable to create temporary file needed for OpenIGC bundle from directory.", directory.getName(), e);
        }
        try (
                FileOutputStream bundleOut = new FileOutputStream(bundle);
                ZipOutputStream zipOutput = new ZipOutputStream(bundleOut)
        ) {
            if (!directory.isDirectory()) {
                throw new IGCIOException("Provided bundle location is not a directory.", directory.getName(), null);
            } else {
                recursivelyZipFiles(directory, "", zipOutput);
            }
        } catch (IOException e) {
            throw new IGCIOException("Unable to create temporary file needed for OpenIGC bundle from directory.", directory.getName(), e);
        }
        return bundle;

    }

    /**
     * Recursively traverses the provided file to build up a zip file output in the provided ZipOutputStream.
     *
     * @param file the file from which to recursively process
     * @param name the name of the file from which to recursively process
     * @param zipOutput the zip output stream into which to write the entries
     * @throws IGCIOException if there is any issue loading the provided directory or creating a zip file from it
     */
    private void recursivelyZipFiles(File file, String name, ZipOutputStream zipOutput) throws IGCIOException {

        if (file.isDirectory()) {

            // Make sure the directory name ends with a separator
            String directoryName = name;
            if (directoryName.length() != 0) {
                directoryName = directoryName.endsWith(File.separator) ? directoryName : directoryName + File.separator;
            }

            // Create an entry in the zip file for the directory, then recurse on the files within it
            try {
                if (directoryName.length() != 0) {
                    zipOutput.putNextEntry(new ZipEntry(directoryName));
                }
                File[] files = file.listFiles();
                if (files != null) {
                    for (File subFile : files) {
                        recursivelyZipFiles(subFile, directoryName + subFile.getName(), zipOutput);
                    }
                } else {
                    throw new IGCIOException("No files found for the bundle, cannot create.", file.getCanonicalPath(), null);
                }
            } catch (IOException e) {
                throw new IGCIOException("Unable to create directory entry in zip file for directory.", directoryName, e);
            }

        } else {

            try (FileInputStream fileInput = new FileInputStream(file)) {
                // Add an entry for the file into the zip file, and write its bytes into the zipfile output
                zipOutput.putNextEntry(new ZipEntry(name));
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInput.read(buffer)) >= 0) {
                    zipOutput.write(buffer, 0, length);
                }
            } catch (FileNotFoundException e) {
                throw new IGCIOException("Unable to find file.", file.getName(), e);
            } catch (IOException e) {
                throw new IGCIOException("Unable to read/write file.", file.getName(), e);
            }

        }

    }

    /**
     * Retrieve the set of OpenIGC bundles already defined in the environment.
     *
     * @return {@code List<String>}
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public List<String> getOpenIgcBundles() throws IGCConnectivityException, IGCParsingException {
        String bundles = makeRequest(EP_BUNDLES, HttpMethod.GET, null,null);
        List<String> alBundles = new ArrayList<>();
        try {
            ArrayNode anBundles = mapper.readValue(bundles, ArrayNode.class);
            for (int i = 0; i < anBundles.size(); i++) {
                alBundles.add(anBundles.get(i).asText());
            }
        } catch (IOException e) {
            throw new IGCParsingException("Unable to parse bundle response.", bundles, e);
        }
        return alBundles;
    }

    /**
     * Upload the provided OpenIGC asset XML: creating the asset(s) if they do not exist, updating if they do.
     *
     * @param assetXML the XML string defining the OpenIGC asset
     * @return String - the JSON structure indicating the updated assets' RID(s)
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public String upsertOpenIgcAsset(String assetXML) throws IGCConnectivityException {
        return makeRequest(EP_BUNDLE_ASSETS, HttpMethod.POST, MediaType.APPLICATION_XML, assetXML);
    }

    /**
     * Delete using the provided OpenIGC asset XML: deleting the asset(s) specified within it.
     *
     * @param assetXML the XML string defining the OpenIGC asset deletion
     * @return boolean - true on success, false on failure
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public boolean deleteOpenIgcAsset(String assetXML) throws IGCConnectivityException {
        return (makeRequest(EP_BUNDLE_ASSETS, HttpMethod.DELETE, MediaType.APPLICATION_XML, assetXML) == null);
    }

    /**
     * Retrieve the next page of results for the provided parameters.
     *
     * @param propertyName the name of the property for which the list provides items (or null if the result of a search)
     * @param list the list of items from which to retrieve the next page
     * @param <T> the type of items to expect in the ItemList
     * @return {@code ItemList<T>} - the next page of results
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public <T extends Reference> ItemList<T> getNextPage(String propertyName, ItemList<T> list) throws IGCConnectivityException, IGCParsingException {
        return getNextPage(propertyName, list.getPaging());
    }

    /**
     * Retrieve all pages of results from a set of Paging details and items, or if there is no next page return the
     * items provided.
     *
     * @param propertyName the name of the property for which the list provides items (or null if the result of a search)
     * @param list the ItemList for which to retrieve all pages
     * @param <T> the type of items to expect in the ItemList
     * @return {@code List<T>} - a List containing all items from all pages of results
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    public <T extends Reference> List<T> getAllPages(String propertyName, ItemList<T> list) throws IGCConnectivityException, IGCParsingException {
        if (list != null) {
            return getAllPages(propertyName, list.getItems(), list.getPaging());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Retrieve the next page of results from a set of paging details, or if there is no next page return an empty
     * ItemList.
     *
     * @param propertyName the name of the property for which the list provides items (or null if the result of a search)
     * @param paging the "paging" portion of the JSON response from which to retrieve the next page
     * @param <T> the type of items to expect in the ItemList
     * @return {@code ItemList<T>} - the next page of results
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    private <T extends Reference> ItemList<T> getNextPage(String propertyName, Paging paging) throws IGCConnectivityException, IGCParsingException {
        ItemList<T> nextPage = null;
        try {
            nextPage = mapper.readValue("{}", new TypeReference<ItemList<T>>() {});
            String sNextURL = paging.getNextPageURL();
            if (sNextURL != null && !sNextURL.equals("null")) {
                if (this.workflowEnabled && !sNextURL.contains("workflowMode=draft")) {
                    sNextURL += "&workflowMode=draft";
                }
                String requestUrl;
                if (sNextURL.startsWith("extern:")) {
                    // On v11.5, for virtual assets, the paging URL only has the RID and nothing else -- we must
                    // reconstruct an appropriate paging URL from just this RID and the other parameters received by
                    // the method
                    requestUrl = EP_ASSET + "/" + getEncodedPathVariable(sNextURL)
                            + "/" + getEncodedPathVariable(propertyName)
                            + "?begin=" + (paging.getEndIndex() + 1)
                            + "&pageSize=" + paging.getPageSize();
                } else {
                    // Strip off the hostname and port number details from the IGC response, to replace with details used
                    // in configuration of the connector (allowing a proxy or other server in front)
                    UriComponents components = UriComponentsBuilder.fromHttpUrl(sNextURL).build(true);
                    String embeddedHost = "https://" + components.getHost() + ":" + components.getPort();
                    requestUrl = sNextURL.substring(embeddedHost.length() + 1);
                }
                String nextPageBody = makeRequest(requestUrl, HttpMethod.GET, null, null);
                // If the page is part of an ASSET retrieval, we need to strip off the attribute
                // name of the relationship for proper multi-page composition
                if (requestUrl.startsWith(EP_ASSET)) {
                    String remainder = requestUrl.substring(EP_ASSET.length() + 1);
                    String attributeName = remainder.substring(remainder.indexOf('/') + 1, remainder.indexOf('?'));
                    nextPageBody = nextPageBody.substring(attributeName.length() + 4, nextPageBody.length() - 1);
                }
                nextPage = mapper.readValue(nextPageBody, new TypeReference<ItemList<T>>() {});
            }
        } catch (IOException e) {
            throw new IGCParsingException("Unable to parse next page from JSON.", paging.toString(), e);
        }
        return nextPage;
    }

    /**
     * Retrieve all pages of results from the set of Paging details and items, or if there is no next page return the
     * items provided.
     *
     * @param propertyName the name of the property for which the list provides items (or null if the result of a search)
     * @param items the List of items for which to retrieve all pages
     * @param paging the Paging object for which to retrieve all pages
     * @param <T> the type of items to expect in the ItemList
     * @return {@code List<T>} - a List containing all items from all pages of results
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     */
    private <T extends Reference> List<T> getAllPages(String propertyName, List<T> items, Paging paging) throws IGCConnectivityException, IGCParsingException {
        List<T> allPages = items;
        ItemList<T> results = getNextPage(propertyName, paging);
        List<T> resultsItems = results.getItems();
        if (!resultsItems.isEmpty()) {
            // NOTE: this ordering of addAll is important, to avoid side-effecting the original set of items
            resultsItems.addAll(allPages);
            allPages = getAllPages(propertyName, resultsItems, results.getPaging());
        }
        return allPages;
    }

    /**
     * Disconnect from IGC REST API and invalidate the session.
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     */
    public void disconnect() throws IGCConnectivityException {
        makeRequest(EP_LOGOUT, HttpMethod.GET, null,null);
    }

    /**
     * Cache detailed information about the IGC object type.
     *
     * @param typeName name of the IGC object type to cache
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO that defines the type and its properties
     */
    public void cacheTypeDetails(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {

        if (typeName != null) {
            // Only continue if the information is not already cached
            if (!typeToDisplayName.containsKey(typeName)) {
                TypeDetails typeDetails = getTypeDetails(typeName);

                // Cache whether the type supports creation or not
                if (typeDetails.getCreateInfo() != null) {
                    List<TypeProperty> create = typeDetails.getCreateInfo().getProperties();
                    if (create != null && !create.isEmpty()) {
                        typesThatCanBeCreated.add(typeName);
                    }
                }

                // Cache property details
                List<TypeProperty> view = typeDetails.getViewInfo().getProperties();
                if (view != null) {
                    Set<String> allProperties = new TreeSet<>();
                    Set<String> nonRelationship = new TreeSet<>();
                    Set<String> stringProperties = new TreeSet<>();
                    Set<String> pagedRelationship = new TreeSet<>();
                    for (TypeProperty property : view) {

                        String propertyName = property.getName();

                        if (propertyName != null) {
                            // Attempt to instantiate and cache generic property retrieval mechanism
                            // Note that this will return null if the bean / POJO supporting that type does not actually
                            // contain the property, in which case we should not cache the property as one that we can
                            // handle
                            DynamicPropertyReader reader = getAccessor(typeName, propertyName);
                            if (reader != null) {

                                if (!IGCRestConstants.getPropertiesToIgnore().contains(propertyName)) {
                                    if (propertyName.equals("created_on")) {
                                        typesThatIncludeModificationDetails.add(typeName);
                                    }
                                    org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.TypeReference type = property.getType();
                                    String propertyType = type.getName();
                                    if (propertyType.equals("string") || propertyType.equals("enum")) {
                                        // TODO: confirm whether enums should be treated the same as all other string properties?
                                        stringProperties.add(propertyName);
                                        nonRelationship.add(propertyName);
                                    } else if (type.getUrl() != null || propertyType.equals("note")) {
                                        if (property.getMaxCardinality() < 0) {
                                            pagedRelationship.add(propertyName);
                                        }
                                    } else {
                                        nonRelationship.add(propertyName);
                                    }
                                    allProperties.add(propertyName);

                                }
                            }
                        }

                    }
                    typeToAllProperties.put(typeName, new ArrayList<>(allProperties));
                    typeToNonRelationshipProperties.put(typeName, new ArrayList<>(nonRelationship));
                    typeToStringProperties.put(typeName, new ArrayList<>(stringProperties));
                    typeToPagedRelationshipProperties.put(typeName, new ArrayList<>(pagedRelationship));

                }
                typeToDisplayName.put(typeName, typeDetails.getName());

            }
        }

    }

    /**
     * Retrieve the display name of this IGC object type.
     *
     * @param typeName the name of the IGC object type
     * @return String
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public String getDisplayNameForType(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        cacheTypeDetails(typeName);
        if (typeName != null) {
            return typeToDisplayName.getOrDefault(typeName, null);
        } else {
            return null;
        }
    }

    /**
     * Indicates whether the IGC object type can be created (true) or not (false).
     *
     * @param typeName the name of the IGC object type
     * @return boolean
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public boolean isCreatable(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        cacheTypeDetails(typeName);
        if (typeName != null) {
            return typesThatCanBeCreated.contains(typeName);
        } else {
            return false;
        }
    }

    /**
     * Indicates whether assets of this type include modification details (true) or not (false).
     *
     * @param typeName the name of the IGC asset type for which to check whether it tracks modification details
     * @return boolean
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public boolean hasModificationDetails(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        cacheTypeDetails(typeName);
        if (typeName != null) {
            return typesThatIncludeModificationDetails.contains(typeName);
        } else {
            return false;
        }
    }

    /**
     * Retrieve the names of all properties for the provided IGC object type, or null if the type is unknown.
     *
     * @param typeName the name of the IGC object type
     * @return {@code List<String>}
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public List<String> getAllPropertiesForType(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        cacheTypeDetails(typeName);
        if (typeName != null) {
            return typeToAllProperties.getOrDefault(typeName, Collections.emptyList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Retrieve the names of all non-relationship properties for the provided IGC object type, or null if the type is
     * unknown.
     *
     * @param typeName the name of the IGC object type
     * @return {@code List<String>}
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public List<String> getNonRelationshipPropertiesForType(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        cacheTypeDetails(typeName);
        if (typeName != null) {
            return typeToNonRelationshipProperties.getOrDefault(typeName, Collections.emptyList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Retrieve the names of all string properties for the provided IGC object type, or null if the type is unknown.
     *
     * @param typeName the name of the IGC object type
     * @return {@code List<String>}
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public List<String> getAllStringPropertiesForType(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        cacheTypeDetails(typeName);
        if (typeName != null) {
            return typeToStringProperties.getOrDefault(typeName, Collections.emptyList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Retrieve the names of all paged relationship properties for the provided IGC object type, or null if the type is
     * unknown.
     *
     * @param typeName the name of the IGC object type
     * @return {@code List<String>}
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public List<String> getPagedRelationshipPropertiesForType(String typeName) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        cacheTypeDetails(typeName);
        if (typeName != null) {
            return typeToPagedRelationshipProperties.getOrDefault(typeName, Collections.emptyList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Register a POJO as an object to handle serde of JSON objects.<br>
     * Note that this MUST be done BEFORE any object mapping (translation) is done!
     * <br><br>
     * In general, you'll want your POJO to extend at least the Reference
     * object, and more likely the InformationAsset (for your own OpenIGC object),
     * or if you are adding custom attributes to one of the native asset types, consider
     * directly changing that bean.
     * <br><br>
     * To allow this dynamic registration to work, also ensure you have a @JsonTypeName("...") annotation
     * in your class set to the type that the IGC REST API uses to refer to the asset (eg. for Term.class
     * it would be "term"). See the base POJOs for examples.
     *
     * @param clazz the Java Class (POJO) object to register
     * @throws IGCIOException if there is any issue introspecting the provided POJO class
     */
    public void registerPOJO(Class<?> clazz) throws IGCIOException {
        JsonTypeName typeName = clazz.getAnnotation(JsonTypeName.class);
        if (typeName != null) {
            String typeId = typeName.value();
            this.mapper.registerSubtypes(clazz);
            this.registeredTypes.put(typeId, clazz);
            log.info("Registered IGC type {} to be handled by POJO: {}", typeId, clazz.getCanonicalName());
        } else {
            throw new IGCIOException("Unable to find JsonTypeName annotation to identify type in POJO.", clazz.getCanonicalName(), null);
        }
    }

    /**
     * Returns the POJO that is registered to serde the provided IGC asset type, preferring any that have been
     * registered or defaulting to the out-of-the-box ones if there are no overrides.
     *
     * @param assetType name of the IGC asset
     * @return Class
     * @see #registerPOJO(Class)
     * @throws IGCIOException if there is any issue introspecting the provided POJO class
     */
    public Class<?> getPOJOForType(String assetType) throws IGCIOException {
        Class<?> igcPOJO = registeredTypes.getOrDefault(assetType, null);
        if (igcPOJO == null) {
            StringBuilder sbPojoName = new StringBuilder();
            sbPojoName.append(IGCRestConstants.IGC_REST_BASE_MODEL_PKG);
            sbPojoName.append(".");
            sbPojoName.append(IGCRestConstants.getClassNameForAssetType(assetType));
            try {
                igcPOJO = Class.forName(sbPojoName.toString());
            } catch (ClassNotFoundException e) {
                throw new IGCIOException("Unable to find POJO class.", sbPojoName.toString(), e);
            }
        }
        return igcPOJO;
    }

    /**
     * Returns true iff the workflow is enabled in the environment against which the REST connection is defined.
     *
     * @return Boolean
     */
    public Boolean isWorkflowEnabled() {
        return this.workflowEnabled;
    }

    /**
     * Construct a unique key for dynamic reading and writing of a given type's specific property.
     *
     * @param typeName the name of the IGC object type
     * @param propertyName the name of the property within the object
     * @return String
     */
    private String getDynamicPropertyKey(String typeName, String propertyName) {
        return typeName + "$" + propertyName;
    }

    /**
     * Retrieve a dynamic property reader to access properties of the provided asset type, and create one if it does
     * not already exist.
     *
     * @param type the IGC asset type from which to retrieve the property
     * @param property the name of the property to retrieve
     * @return DynamicPropertyReader
     * @throws IGCIOException if there is any issue introspecting the class that defines the type and its properties
     */
    private DynamicPropertyReader getAccessor(String type, String property) throws IGCIOException {
        String key = getDynamicPropertyKey(type, property);
        if (!typeAndPropertyToAccessor.containsKey(key)) {
            try {
                DynamicPropertyReader reader = new DynamicPropertyReader(getPOJOForType(type), property);
                typeAndPropertyToAccessor.put(key, reader);
            } catch (IllegalArgumentException e) {
                log.warn("Unable to setup an accessor for property '{}' on type '{}' - this property will be entirely ignored. If this is a custom property, see https://github.com/odpi/egeria-connector-ibm-information-server/tree/master/igc-clientlibrary#using-your-own-asset-types for how to add your own properties.", property, type, e);
                typeAndPropertyToAccessor.put(key, null); // add a null accessor to avoid trying to build one again
            }
        }
        return typeAndPropertyToAccessor.getOrDefault(key, null);
    }

    /**
     * Retrieve a property of an IGC object based on the property's name.
     *
     * @param object the IGC object from which to retrieve the property's value
     * @param property the name of the property for which to retrieve the value
     * @return Object
     * @throws IGCIOException if there is any issue introspecting the class that defines the type and its properties
     */
    public Object getPropertyByName(Reference object, String property) throws IGCIOException {
        if (object != null) {
            DynamicPropertyReader accessor = getAccessor(object.getType(), property);
            if (accessor != null) {
                return accessor.getProperty(object);
            } else {
                String formattedMessage = object.getType() + "::" + property;
                throw new IGCIOException("Unable to find accessor for object type and property.", formattedMessage, null);
            }
        } else {
            return null;
        }
    }

    /**
     * Ensures that the modification details of the asset are populated (takes no action if already populated or
     * the asset does not support them).
     *
     * @param <T> the type of IGC object (minimally a Reference)
     * @param object the IGC object for which to populate modification details
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return T - the IGC object with its _context and modification details populated
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    public <T extends Reference> T getModificationDetails(T object, ObjectCache cache) throws IGCConnectivityException, IGCParsingException, IGCIOException {
        return getAssetContext(object, true, cache);
    }

    /**
     * Ensures that the _context of the asset is populated (takes no action if already populated).
     * In addition, if the asset type supports them, will also retrieve and set modification details.
     * Will force retrieval of modification details even if _context is populated if 'bPopulateModDetails' is true
     * and no modification details appear to be populated. (Takes no action otherwise, just returns object as-is.)
     *
     * @param <T> the type of IGC object (minimally a Reference)
     * @param object the IGC object for which to populate the context (and modification details)
     * @param bPopulateModDetails true to force modification detail retrieval, false otherwise
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return T - the IGC object with its _context and modification details populated
     * @throws IGCConnectivityException if there is any connectivity issue during the request
     * @throws IGCParsingException if there is any issue parsing the response from IGC
     * @throws IGCIOException if there is any issue accessing the POJO defining the type and its properties
     */
    @SuppressWarnings("unchecked")
    private <T extends Reference> T getAssetContext(T object, boolean bPopulateModDetails, ObjectCache cache) throws IGCConnectivityException, IGCParsingException, IGCIOException {

        T populated = object;
        if (object != null) {

            String rid = object.getId();
            Reference fromCache = cache.get(rid);
            if (fromCache == null) {
                boolean bHasModificationDetails = hasModificationDetails(object.getType());

                // Only bother retrieving the context if the identity isn't already present
                if ((!object.isIdentityPopulated() && (object.getContext() == null || object.getContext().isEmpty()))
                        || (bHasModificationDetails && !object.areModificationDetailsPopulated() && bPopulateModDetails)) {

                    log.debug("Context and / or modification details are empty, and not found in cache; populating...");

                    String assetType = object.getType();
                    if (object.isVirtualAsset()
                            || object.isEmbeddedAsset()
                            || IGCRestConstants.getTypesThatCannotBeSearched().contains(assetType)) {
                        populated = (T) getAssetById(rid);
                    } else {
                        List<String> properties = new ArrayList<>();
                        if (bHasModificationDetails) {
                            properties.addAll(IGCRestConstants.getModificationProperties());
                        }
                        populated = getAssetWithSubsetOfProperties(rid, assetType, properties, 2);
                    }
                    cache.add(populated);

                }
            } else {
                populated = (T) fromCache;
            }
        }

        return populated;

    }

    public String getReport(String rid) {

        Map<String, Object> request = new HashMap<>();
        request.put("assetId", rid);
        request.put("markTruncation", false);
        request.put("flowTypes", Collections.singletonList("DESIGN"));
        int times = 5;
        HttpStatus statusCode;
        do {
            try {
                String body = new ObjectMapper().writeValueAsString(request);
                ResponseEntity<String> response = makeRequest(baseURL + REPORT_ENDPOINT, HttpMethod.POST,
                        MediaType.APPLICATION_JSON, body, true);

                statusCode = response.getStatusCode();

                if (statusCode.equals(HttpStatus.OK)) {
                    return response.getBody();
                }
                times--;
                log.error("Report with RID {} cannot be retrieved because the IGC response status is: {}", rid, statusCode);
            } catch (IGCConnectivityException | JsonProcessingException e) {
                log.error("Report with RID {} cannot be retrieved because of the following exception: '{}'", rid, e.getMessage());
                times--;
            }
        } while(times > 0);
        return "";
    }


}
