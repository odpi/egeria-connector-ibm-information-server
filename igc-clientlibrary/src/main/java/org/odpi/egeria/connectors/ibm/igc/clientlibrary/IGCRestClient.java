/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    private boolean successfullyInitialised = false;
    private RestTemplate restTemplate;

    private IGCVersionEnum igcVersion;
    private HashMap<String, Class> registeredPojosByType;
    private HashMap<String, DynamicPropertyReader> typeAndPropertyToAccessor;
    private HashMap<String, DynamicPropertyWriter> typeAndPropertyToWriter;
    private HashMap<String, PojoIntrospector> typeToIntrospector;

    private int defaultPageSize = 100;

    private ObjectMapper mapper;

    public static final String EP_TYPES = "/ibm/iis/igc-rest/v1/types";
    public static final String EP_ASSET = "/ibm/iis/igc-rest/v1/assets";
    public static final String EP_SEARCH = "/ibm/iis/igc-rest/v1/search";
    public static final String EP_LOGOUT  = "/ibm/iis/igc-rest/v1/logout";
    public static final String EP_BUNDLES = "/ibm/iis/igc-rest/v1/bundles";
    public static final String EP_BUNDLE_ASSETS = EP_BUNDLES + "/assets";

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
     */
    public IGCRestClient(String host, String port, String user, String password) {
        this("https://" + host + ":" + port, encodeBasicAuth(user, password));
    }

    /**
     * Creates a new session on the server and retains the cookies to re-use the same session for the life
     * of the client (or until the session times out); whichever occurs first.
     *
     * @param baseURL the base URL of the domain tier of Information Server
     * @param authorization the Basic-encoded authorization string to use to login to Information Server
     */
    protected IGCRestClient(String baseURL, String authorization) {

        if (baseURL == null || !baseURL.startsWith("https://")) {
            if (log.isErrorEnabled()) { log.error("Cannot instantiate IGCRestClient -- baseURL must be https: {}", baseURL); }
            throw new NullPointerException();
        }

        this.baseURL = baseURL;
        this.authorization = authorization;
        this.mapper = new ObjectMapper();
        this.registeredPojosByType = new HashMap<>();
        this.typeAndPropertyToAccessor = new HashMap<>();
        this.typeAndPropertyToWriter = new HashMap<>();
        this.typeToIntrospector = new HashMap<>();
        this.restTemplate = new RestTemplate();

        if (log.isDebugEnabled()) { log.debug("Constructing IGCRestClient..."); }

        // Run a simple initial query to obtain a session and setup the cookies
        if (this.authorization != null) {

            IGCSearch igcSearch = new IGCSearch("category");
            igcSearch.addType("term");
            igcSearch.addType("information_governance_policy");
            igcSearch.addType("information_governance_rule");
            igcSearch.setPageSize(1);
            igcSearch.setDevGlossary(true);
            String response = searchJson(igcSearch);

            if (response != null) {

                if (log.isDebugEnabled()) { log.debug("Checking for workflow and registering version..."); }
                ObjectMapper tmpMapper = new ObjectMapper();
                try {
                    this.workflowEnabled = tmpMapper.readValue(response, ReferenceList.class).getPaging().getNumTotal() > 0;
                } catch (IOException e) {
                    if (log.isErrorEnabled()) { log.error("Unable to determine if workflow is enabled: {}", e); }
                }
                // Register the non-generated types
                this.registerPOJO(Paging.class);

                // Start with lowest version supported
                this.igcVersion = IGCVersionEnum.values()[0];
                List<Type> igcTypes = getTypes(tmpMapper);
                Set<String> typeNames = igcTypes.stream().map(Type::getId).collect(Collectors.toSet());
                for (IGCVersionEnum aVersion : IGCVersionEnum.values()) {
                    if (aVersion.isHigherThan(this.igcVersion)
                            && typeNames.contains(aVersion.getTypeNameFirstAvailableInThisVersion())
                            && !typeNames.contains(aVersion.getTypeNameNotAvailableInThisVersion())) {
                        this.igcVersion = aVersion;
                    }
                }
                if (log.isInfoEnabled()) { log.info("Detected IGC version: {}", this.igcVersion.getVersionString()); }
                successfullyInitialised = true;

            } else {
                log.error("Unable to construct IGCRestClient: no authorization provided.");
            }

        }

    }

    /**
     * Indicates whether the client was successfully initialised (true) or not (false).
     *
     * @return boolean
     */
    public boolean isSuccessfullyInitialised() { return successfullyInitialised; }

    /**
     * Setup the HTTP headers of a request based on either session reuse (forceLogin = false) or forcing a new
     * session (forceLogin = true).
     *
     * @param forceLogin indicates whether to create a new session by forcing login (true), or reuse existing session (false)
     * @return
     */
    private HttpHeaders getHttpHeaders(boolean forceLogin) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        // If we have cookies already, and haven't been asked to force the login,
        // re-use these (to maintain the same session)
        if (cookies != null && !forceLogin) {
            // TODO: identified as High issue on page 1122
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
     * @return {@code ResponseEntity<String>}
     */
    private ResponseEntity<String> openNewSessionWithRequest(String url,
                                                             HttpMethod method,
                                                             MediaType contentType,
                                                             String payload,
                                                             boolean alreadyTriedNewSession) {
        if (alreadyTriedNewSession) {
            if (log.isErrorEnabled()) { log.error("Opening a new session already attempted without success -- giving up on {} to {} with {}", method, url, payload); }
            return null;
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
     * @return {@code ResponseEntity<String>}
     */
    private ResponseEntity<String> openNewSessionWithUpload(String endpoint,
                                                            HttpMethod method,
                                                            AbstractResource file,
                                                            boolean alreadyTriedNewSession) {
        if (alreadyTriedNewSession) {
            if (log.isErrorEnabled()) { log.error("Opening a new session already attempted without success -- giving up on {} to {} with {}", method, endpoint, file); }
            return null;
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
     */
    private void setCookiesFromResponse(ResponseEntity<String> response) {

        // If we had a successful response, setup the cookies
        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
            HttpHeaders headers = response.getHeaders();
            if (headers.get(HttpHeaders.SET_COOKIE) != null) {
                this.cookies = headers.get(HttpHeaders.SET_COOKIE);
            }
        } else {
            if (log.isErrorEnabled()) { log.error("Unable to make request or unexpected status: {}", response.getStatusCode()); }
        }

    }

    /**
     * Attempt to convert the JSON string into a Java object, based on the registered POJOs.
     *
     * @param json the JSON string to convert
     * @return Reference - an IGC object
     */
    public Reference readJSONIntoPOJO(String json) {
        Reference reference = null;
        try {
            reference = this.mapper.readValue(json, Reference.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to translate JSON into POJO: {}", json, e); }
        }
        return reference;
    }

    /**
     * Attempt to convert the JSON string into a ReferenceList.
     *
     * @param json the JSON string to convert
     * @return ReferenceList
     */
    public ReferenceList readJSONIntoReferenceList(String json) {
        ReferenceList referenceList = null;
        try {
            referenceList = this.mapper.readValue(json, ReferenceList.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to translate JSON into ReferenceList: {}", json, e); }
        }
        return referenceList;
    }

    /**
     * Attempt to convert the provided IGC object into JSON, based on the registered POJOs.
     *
     * @param asset the IGC asset to convert
     * @return String of JSON representing the asset
     */
    public String getValueAsJSON(Reference asset) {
        String payload = null;
        try {
            payload = this.mapper.writeValueAsString(asset);
        } catch (JsonProcessingException e) {
            if (log.isErrorEnabled()) { log.error("Unable to translate asset into JSON: {}", asset, e); }
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
    public static String encodeBasicAuth(String username, String password) {
        return Base64Utils.encodeToString((username + ":" + password).getBytes(UTF_8));
    }

    /**
     * Internal utility for making potentially repeat requests (if session expires and needs to be re-opened),
     * to upload a file to a given endpoint.
     *
     * @param endpoint the REST resource against which to POST the upload
     * @param file the Spring FileSystemResource or ClassPathResource of the file to be uploaded
     * @param forceLogin a boolean indicating whether login should be forced (true) or session reused (false)
     * @return {@code ResponseEntity<String>}
     */
    private ResponseEntity<String> uploadFile(String endpoint, HttpMethod method, AbstractResource file, boolean forceLogin) {

        HttpHeaders headers = getHttpHeaders(forceLogin);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<String> response = null;
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);

        HttpEntity<MultiValueMap<String, Object>> toSend = new HttpEntity<>(body, headers);

        try {
            response = restTemplate.exchange(
                    baseURL + endpoint,
                    method,
                    toSend,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            log.warn("Request failed -- session may have expired, retrying...", e);
            // If the response was forbidden (fails with exception), the session may have expired -- create a new one
            response = openNewSessionWithUpload(
                    baseURL + endpoint,
                    method,
                    file,
                    forceLogin
            );
        } catch (RestClientException e) {
            log.error("Request failed -- check IGC environment connectivity and authentication details.", e);
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
     */
    public boolean uploadFile(String endpoint, HttpMethod method, AbstractResource file) {
        ResponseEntity<String> response = uploadFile(endpoint, method, file, false);
        return (response == null ? false : response.getStatusCode() == HttpStatus.OK);
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
     */
    private ResponseEntity<String> makeRequest(String url,
                                               HttpMethod method,
                                               MediaType contentType,
                                               String payload,
                                               boolean forceLogin) {
        HttpHeaders headers = getHttpHeaders(forceLogin);
        HttpEntity<String> toSend;
        if (payload != null) {
            headers.setContentType(contentType);
            toSend = new HttpEntity<>(payload, headers);
        } else {
            toSend = new HttpEntity<>(headers);
        }
        ResponseEntity<String> response = null;
        try {
            if (log.isDebugEnabled()) { log.debug("{}ing to {} with: {}", method, url, payload); }
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build(true);
            response = restTemplate.exchange(
                    uriComponents.toUri(),
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
                    contentType,
                    payload,
                    forceLogin
            );
        } catch (RestClientException e) {
            log.error("Request failed -- check IGC environment connectivity and authentication details.", e);
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
     */
    public String makeRequest(String endpoint, HttpMethod method, MediaType contentType, String payload) {
        ResponseEntity<String> response = makeRequest(
                baseURL + endpoint,
                method,
                contentType,
                payload,
                false
        );
        String body = null;
        if (response == null) {
            log.error("Unable to complete request -- check IGC environment connectivity and authentication details.");
            throw new NullPointerException("Unable to complete request -- check IGC environment connectivity and authentication details.");
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
     */
    public String makeCreateRequest(String endpoint, HttpMethod method, MediaType contentType, String payload) {
        ResponseEntity<String> response = makeRequest(
                baseURL + endpoint,
                method,
                contentType,
                payload,
                false
        );
        String rid = null;
        if (!response.getStatusCode().equals(HttpStatus.CREATED)) {
            log.error("Unable to create instance -- check IGC environment connectivity and authentication details.");
            throw new NullPointerException("Unable to create instance -- check IGC environment connectivity and authentication details.");
        } else {
            HttpHeaders headers = response.getHeaders();
            List<String> instanceURLs = headers.get("Location");
            if (instanceURLs != null && !instanceURLs.isEmpty() && instanceURLs.size() == 1) {
                String instanceURL = instanceURLs.get(0);
                rid = instanceURL.substring(instanceURL.lastIndexOf("/"));
            }
        }
        return rid;
    }

    /**
     * Retrieves the list of metadata types supported by IGC.
     *
     * @param objectMapper an ObjectMapper to use for translating the types list
     *
     * @return ArrayNode the list of types supported by IGC, as a JSON structure
     */
    public List<Type> getTypes(ObjectMapper objectMapper) {
        String response = makeRequest(EP_TYPES, HttpMethod.GET, null,null);
        List<Type> alTypes = new ArrayList<>();
        try {
            alTypes = objectMapper.readValue(response, new TypeReference<List<Type>>(){});
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse types response: {}", response, e); }
        }
        return alTypes;
    }

    /**
     * Retrieve all information about an asset from IGC.
     * This can be an expensive operation that may retrieve far more information than you actually need.
     *
     * @see #getAssetRefById(String)
     *
     * @param rid the Repository ID of the asset
     * @return Reference - the IGC object representing the asset
     */
    public Reference getAssetById(String rid) {
        return readJSONIntoPOJO(makeRequest(EP_ASSET + "/" + rid, HttpMethod.GET, null,null));
    }

    /**
     * Retrieve only the minimal unique properties of an asset from IGC.
     * This will generally be the most performant way to see that an asset exists and get its identifying characteristics.
     *
     * @param rid the Repository ID of the asset
     * @return Reference - the minimalistic IGC object representing the asset
     */
    public Reference getAssetRefById(String rid) {

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
        ReferenceList results = search(igcSearch);
        Reference reference = null;
        if (results.getPaging().getNumTotal() > 0) {
            if (results.getPaging().getNumTotal() > 1) {
                if (log.isWarnEnabled()) { log.warn("Found multiple assets for RID {}, taking only the first.", rid); }
            }
            reference = results.getItems().get(0);
        }

        return reference;

    }

    /**
     * Retrieve all assets that match the provided search criteria from IGC.
     *
     * @param igcSearch the IGCSearch object defining criteria by which to search
     * @return JsonNode - the first JSON page of results from the search
     */
    public String searchJson(IGCSearch igcSearch) {
        return makeRequest(EP_SEARCH, HttpMethod.POST, MediaType.APPLICATION_JSON, igcSearch.getQuery().toString());
    }

    /**
     * Retrieve all assets that match the provided search criteria from IGC.
     *
     * @param igcSearch search conditions and criteria to use
     * @return ReferenceList - the first page of results from the search
     */
    public ReferenceList search(IGCSearch igcSearch) {
        ReferenceList referenceList = null;
        String results = searchJson(igcSearch);
        try {
            referenceList = this.mapper.readValue(results, ReferenceList.class);
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to translate JSON results: {}", results, e); }
        }
        return referenceList;
    }

    /**
     * Update the asset specified by the provided RID with the value(s) provided.
     *
     * @param rid the Repository ID of the asset to update
     * @param value the JSON structure defining what value(s) of the asset to update (and mode)
     * @return String - the JSON indicating the updated asset's RID and updates made
     */
    private String updateJson(String rid, JsonNode value) {
        return makeRequest(EP_ASSET + "/" + rid, HttpMethod.PUT, MediaType.APPLICATION_JSON, value.toString());
    }

    /**
     * Apply the update described by the provided update object.
     *
     * @param igcUpdate update criteria to use
     * @return boolean - indicating success (true) or not (false) of the operation
     */
    public boolean update(IGCUpdate igcUpdate) {
        String result = updateJson(igcUpdate.getRidToUpdate(), igcUpdate.getUpdate());
        return (result != null);
    }

    /**
     * Create the asset specified by the provided value(s).
     *
     * @param value the JSON structure defining what should be created
     * @return String - the created asset's RID
     */
    private String createJson(JsonNode value) {
        return makeCreateRequest(EP_ASSET, HttpMethod.POST, MediaType.APPLICATION_JSON, value.toString());
    }

    /**
     * Create the object described by the provided create object.
     *
     * @param igcCreate creation criteria to use
     * @return String - the created asset's RID (or null if nothing was created)
     */
    public String create(IGCCreate igcCreate) {
        return createJson(igcCreate.getCreate());
    }

    /**
     * Delete the asset specified by the provided RID.
     *
     * @param rid the RID of the asset to delete
     * @return String - null upon successful deletion, otherwise containing a message pertaining to the failure
     */
    private String deleteJson(String rid) {
        return makeRequest(EP_ASSET + "/" + rid, HttpMethod.DELETE, MediaType.APPLICATION_JSON, null);
    }

    /**
     * Delete the object specified by the provided RID.
     *
     * @param rid the RID of the asset to delete
     * @return boolean
     */
    public boolean delete(String rid) {
        String result = deleteJson(rid);
        if (result != null) {
            log.error("Unable to delete asset {}: {}", rid, result);
        }
        return (result == null);
    }

    /**
     * Upload the specified bundle, creating it if it does not already exist or updating it if it does.
     *
     * @param name the bundleId of the bundle
     * @param file the Spring FileSystemResource or ClassPathResource containing the file to be uploaded
     * @return boolean - indication of success (true) or failure (false)
     */
    public boolean upsertOpenIgcBundle(String name, AbstractResource file) {
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
     */
    public File createOpenIgcBundleFile(File directory) {

        File bundle = null;
        try {
            bundle = File.createTempFile("openigc", "zip");
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to create temporary file needed for OpenIGC bundle from directory: {}", directory, e); }
        }
        if (bundle != null) {
            try (
                    FileOutputStream bundleOut = new FileOutputStream(bundle);
                    ZipOutputStream zipOutput = new ZipOutputStream(bundleOut)
            ) {
                if (!directory.isDirectory()) {
                    if (log.isErrorEnabled()) { log.error("Provided bundle location is not a directory: {}", directory); }
                } else {
                    recursivelyZipFiles(directory, "", zipOutput);
                }

            } catch (IOException e) {
                if (log.isErrorEnabled()) { log.error("Unable to create temporary file needed for OpenIGC bundle from directory: {}", directory, e); }
            }
        }
        return bundle;

    }

    /**
     * Recursively traverses the provided file to build up a zip file output in the provided ZipOutputStream.
     *
     * @param file the file from which to recursively process
     * @param name the name of the file from which to recursively process
     * @param zipOutput the zip output stream into which to write the entries
     */
    private void recursivelyZipFiles(File file, String name, ZipOutputStream zipOutput) {

        if (file.isDirectory()) {

            // Make sure the directory name ends with a separator
            String directoryName = name;
            if (!directoryName.equals("")) {
                directoryName = directoryName.endsWith(File.separator) ? directoryName : directoryName + File.separator;
            }

            // Create an entry in the zip file for the directory, then recurse on the files within it
            try {
                if (!directoryName.equals("")) {
                    zipOutput.putNextEntry(new ZipEntry(directoryName));
                }
                File[] files = file.listFiles();
                for (File subFile : files) {
                    recursivelyZipFiles(subFile, directoryName + subFile.getName(), zipOutput);
                }
            } catch (IOException e) {
                if (log.isErrorEnabled()) { log.error("Unable to create directory entry in zip file for {}.", directoryName, e); }
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
                if (log.isErrorEnabled()) { log.error("Unable to find file: {}", file, e); }
            } catch (IOException e) {
                if (log.isErrorEnabled()) { log.error("Unable to read/write file: {}", file, e); }
            }

        }

    }

    /**
     * Retrieve the set of OpenIGC bundles already defined in the environment.
     *
     * @return {@code List<String>}
     */
    public List<String> getOpenIgcBundles() {
        String bundles = makeRequest(EP_BUNDLES, HttpMethod.GET, null,null);
        List<String> alBundles = new ArrayList<>();
        try {
            ArrayNode anBundles = mapper.readValue(bundles, ArrayNode.class);
            for (int i = 0; i < anBundles.size(); i++) {
                alBundles.add(anBundles.get(i).asText());
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse bundle response: {}", bundles, e); }
        }
        return alBundles;
    }

    /**
     * Upload the provided OpenIGC asset XML: creating the asset(s) if they do not exist, updating if they do.
     *
     * @param assetXML the XML string defining the OpenIGC asset
     * @return String - the JSON structure indicating the updated assets' RID(s)
     */
    public String upsertOpenIgcAsset(String assetXML) {
        return makeRequest(EP_BUNDLE_ASSETS, HttpMethod.POST, MediaType.APPLICATION_XML, assetXML);
    }

    /**
     * Delete using the provided OpenIGC asset XML: deleting the asset(s) specified within it.
     *
     * @param assetXML the XML string defining the OpenIGC asset deletion
     * @return boolean - true on success, false on failure
     */
    public boolean deleteOpenIgcAsset(String assetXML) {
        return (makeRequest(EP_BUNDLE_ASSETS, HttpMethod.DELETE, MediaType.APPLICATION_XML, assetXML) == null);
    }

    /**
     * Retrieve the next page of results from a set of paging details<br>
     * ... or if there is no next page, return an empty ReferenceList.
     *
     * @param paging the "paging" portion of the JSON response from which to retrieve the next page
     * @return ReferenceList - the next page of results
     */
    public ReferenceList getNextPage(Paging paging) {
        ReferenceList nextPage = null;
        try {
            nextPage = mapper.readValue("{\"items\": []}", ReferenceList.class);
            String sNextURL = paging.getNextPageURL();
            if (sNextURL != null && !sNextURL.equals("null")) {
                if (this.workflowEnabled && !sNextURL.contains("workflowMode=draft")) {
                    sNextURL += "&workflowMode=draft";
                }
                // Strip off the hostname and port number details from the IGC response, to replace with details used
                // in configuration of the connector (allowing a proxy or other server in front)
                UriComponents components = UriComponentsBuilder.fromHttpUrl(sNextURL).build(true);
                String embeddedHost = "https://" + components.getHost() + ":" + components.getPort();
                String nextUrlNoHost = sNextURL.substring(embeddedHost.length() + 1);
                String nextPageBody = makeRequest(nextUrlNoHost, HttpMethod.GET, null, null);
                // If the page is part of an ASSET retrieval, we need to strip off the attribute
                // name of the relationship for proper multi-page composition
                if (sNextURL.contains(EP_ASSET)) {
                    String remainder = sNextURL.substring((baseURL + EP_ASSET).length() + 2);
                    String attributeName = remainder.substring(remainder.indexOf('/') + 1, remainder.indexOf('?'));
                    nextPageBody = nextPageBody.substring(attributeName.length() + 4, nextPageBody.length() - 1);
                }
                nextPage = mapper.readValue(nextPageBody, ReferenceList.class);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse next page from JSON: {}", paging, e); }
        }
        return nextPage;
    }

    /**
     * Retrieve all pages of results from a set of Paging details and items<br>
     * ... or if there is no next page, return the items provided.
     *
     * @param items the List of items for which to retrieve all pages
     * @param paging the Paging object for which to retrieve all pages
     * @return {@code List<Reference>} - an List containing all items from all pages of results
     */
    public List<Reference> getAllPages(List<Reference> items, Paging paging) {
        List<Reference> allPages = items;
        ReferenceList results = getNextPage(paging);
        List<Reference> resultsItems = results.getItems();
        if (!resultsItems.isEmpty()) {
            // NOTE: this ordering of addAll is important, to avoid side-effecting the original set of items
            resultsItems.addAll(allPages);
            allPages = getAllPages(resultsItems, results.getPaging());
        }
        return allPages;
    }

    /**
     * Disconnect from IGC REST API and invalidate the session.
     */
    public void disconnect() {
        makeRequest(EP_LOGOUT, HttpMethod.GET, null,null);
    }

    /**
     * Register a POJO as an object to handle serde of JSON objects.<br>
     * Note that this MUST be done BEFORE any object mappingRemoved (translation) is done!
     * <br><br>
     * In general, you'll want your POJO to extend at least the model.Reference
     * object in this package; more likely the model.MainObject (for your own OpenIGC object),
     * or if you are adding custom attributes to one of the native asset types, consider
     * directly extending that asset from model.generated.*
     * <br><br>
     * To allow this dynamic registration to work, also ensure you have a @JsonTypeName("...") annotation
     * in your class set to the type that the IGC REST API uses to refer to the asset (eg. for Term.class
     * it would be "term"). See the generated POJOs for examples.
     *
     * @param clazz the Java Class (POJO) object to register
     * @see #getPOJOForType(String)
     */
    public void registerPOJO(Class clazz) {
        JsonTypeName typeName = (JsonTypeName) clazz.getAnnotation(JsonTypeName.class);
        if (typeName != null) {
            String typeId = typeName.value();
            this.mapper.registerSubtypes(clazz);
            this.registeredPojosByType.put(typeId, clazz);
            if (log.isInfoEnabled()) { log.info("Registered IGC type {} to be handled by POJO: {}", typeId, clazz.getCanonicalName()); }
        } else {
            if (log.isErrorEnabled()) { log.error("Unable to find JsonTypeName annotation to identify type in POJO: {}", clazz.getCanonicalName()); }
        }
    }

    /**
     * Returns the POJO that is registered to serde the provided IGC asset type.
     * <br><br>
     * Note that the POJO must first be registered via registerPOJO!
     *
     * @param typeName name of the IGC asset
     * @return Class
     * @see #registerPOJO(Class)
     */
    public Class getPOJOForType(String typeName) {
        return this.registeredPojosByType.get(typeName);
    }

    /**
     * Retrieve the POJO for the provided IGC REST API's JSON representation into a Java object.
     *
     * @param assetType the IGC REST API's JSON representation
     * @return Class
     */
    public final Class findPOJOForType(String assetType) {
        Class igcPOJO = null;
        StringBuilder sbPojoName = new StringBuilder();
        sbPojoName.append(IGCRestConstants.IGC_REST_GENERATED_MODEL_PKG);
        sbPojoName.append(".");
        sbPojoName.append(getIgcVersion().getVersionString());
        sbPojoName.append(".");
        sbPojoName.append(IGCRestConstants.getClassNameForAssetType(assetType));
        try {
            igcPOJO = Class.forName(sbPojoName.toString());
        } catch (ClassNotFoundException e) {
            if (log.isErrorEnabled()) { log.error("Unable to find POJO class: {}", sbPojoName.toString(), e); }
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
     * Cache a dynamic property reader to access properties from the provided asset type.
     *
     * @param type the IGC asset type from which to retrieve the property
     * @param property the name of the property to retrieve
     * @param accessor the dynamic property reader that will retrieve it
     */
    private void addAccessor(String type, String property, DynamicPropertyReader accessor) {
        typeAndPropertyToAccessor.put(type + "$" + property, accessor);
    }

    /**
     * Retrieve a dynamic property reader to access properties of the provided asset type, and create one if it does
     * not already exist.
     *
     * @param type the IGC asset type from which to retrieve the property
     * @param property the name of the property to retrieve
     * @return DynamicPropertyReader
     */
    private DynamicPropertyReader getAccessor(String type, String property) {
        DynamicPropertyReader accessor = typeAndPropertyToAccessor.getOrDefault(type + "$" + property, null);
        if (accessor == null) {
            accessor = new DynamicPropertyReader(getPOJOForType(type), property);
            addAccessor(type, property, accessor);
        }
        return accessor;
    }

    /**
     * Cache a dynamic property writer to update properties for the provided asset type.
     *
     * @param type the IGC asset type for which to update a property
     * @param property the name of the property to update
     * @param writer the dynamic property writer that will update it
     */
    private void addWriter(String type, String property, DynamicPropertyWriter writer) {
        typeAndPropertyToWriter.put(type + "$" + property, writer);
    }

    /**
     * Retrieve a dynamic property writer to update properties of the provided asset type, and create one if it does
     * not already exist.
     *
     * @param type the IGC asset type for which to update a property
     * @param property the name of the property to update
     * @return DynamicPropertyWriter
     */
    private DynamicPropertyWriter getWriter(String type, String property) {
        DynamicPropertyWriter writer = typeAndPropertyToWriter.getOrDefault(type + "$" + property, null);
        if (writer == null) {
            writer = new DynamicPropertyWriter(getPOJOForType(type), property);
            addWriter(type, property, writer);
        }
        return writer;
    }

    /**
     * Cache a dynamic POJO introspector to retrieve static details about the POJO.
     *
     * @param type the IGC asset type for which to access static details
     * @param introspector the dynamic POJO introspector
     */
    private void addIntrospector(String type, PojoIntrospector introspector) {
        typeToIntrospector.put(type, introspector);
    }

    /**
     * Retrieve a dynamic POJO introspector to retrieve static details about the provided asset type, and create one if
     * it does not already exist.
     *
     * @param type the IGC asse type for which to access static details
     * @return PojoIntrospector
     */
    private PojoIntrospector getIntrospector(String type) {
        PojoIntrospector introspector = typeToIntrospector.getOrDefault(type, null);
        if (introspector == null) {
            introspector = new PojoIntrospector(getPOJOForType(type));
            addIntrospector(type, introspector);
        }
        return introspector;
    }

    /**
     * Retrieve a property of an IGC object based on the property's name.
     *
     * @param object the IGC object from which to retrieve the property's value
     * @param property the name of the property for which to retrieve the value
     * @return Object
     */
    public Object getPropertyByName(Reference object, String property) {
        if (object != null) {
            DynamicPropertyReader accessor = getAccessor(object.getType(), property);
            return accessor.getProperty(object);
        } else {
            return null;
        }
    }

    /**
     * Set a property of an IGC object based on the property's name.
     *
     * @param object the IGC object for which to set the property's value
     * @param property the name of the property to set / update
     * @param value the value to set on the property
     */
    public void setPropertyByName(Reference object, String property, Object value) {
        if (object != null) {
            DynamicPropertyWriter writer = getWriter(object.getType(), property);
            writer.setProperty(object, value);
        }
    }

    /**
     * Indicates whether IGC assets of the provided type are capable of being created (true) or not (false).
     *
     * @param typeName the name of the IGC asset type for which to check an asset's create-ability
     * @return boolean
     */
    public boolean isCreatableFromPOJO(String typeName) {
        return getIntrospector(typeName).canBeCreated();
    }

    /**
     * Retrieves the IGC asset display name from the provided POJO.
     *
     * @param typeName the name of the IGC asset type for which to retrieve the display name
     * @return String
     */
    public String getDisplayNameFromPOJO(String typeName) {
        return getIntrospector(typeName).getIgcTypeDisplayName();
    }

    /**
     * Retrieves the list of property names for the asset that are not relationships to other assets.
     *
     * @param typeName the name of the IGC asset type for which to retrieve the list of properties
     * @return {@code List<String>}
     */
    public List<String> getNonRelationshipPropertiesFromPOJO(String typeName) {
        return getIntrospector(typeName).getNonRelationshipProperties();
    }

    /**
     * Retrieves the list of property names for the asset that are string-valued.
     *
     * @param typeName the name of the IGC asset type for which to retrieve the list of properties
     * @return {@code List<String>}
     */
    public List<String> getStringPropertiesFromPOJO(String typeName) {
        return getIntrospector(typeName).getStringProperties();
    }

    /**
     * Retrieves the list of all property names for the asset.
     *
     * @param typeName the name of the IGC asset type for which to retrieve the list of properties
     * @return {@code List<String>}
     */
    public List<String> getAllPropertiesFromPOJO(String typeName) {
        return getIntrospector(typeName).getAllProperties();
    }

    /**
     * Retrieves the list of all paged relationship property names for the asset.
     *
     * @param typeName the name of the IGC asset type for which to retrieve the list of properties
     * @return {@code List<String>}
     */
    public List<String> getPagedRelationalPropertiesFromPOJO(String typeName) {
        return getIntrospector(typeName).getPagedRelationshipProperties();
    }

    /**
     * Indicates whether assets of this type include modification details (true) or not (false).
     *
     * @param typeName the name of the IGC asset type for which to check whether it tracks modification details
     * @return boolean
     */
    public boolean hasModificationDetails(String typeName) {
        return getIntrospector(typeName).includesModificationDetails();
    }

}
