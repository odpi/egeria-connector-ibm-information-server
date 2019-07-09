/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.LineageMappingMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.ProcessMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.openmetadata.accessservices.dataengine.model.SoftwareServerCapability;
import org.odpi.openmetadata.frameworks.connectors.ffdc.*;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.odpi.openmetadata.openconnectors.governancedaemonconnectors.dataengineproxy.DataEngineConnectorBase;
import org.odpi.openmetadata.openconnectors.governancedaemonconnectors.dataengineproxy.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataStageConnector extends DataEngineConnectorBase {

    private static final Logger log = LoggerFactory.getLogger(DataStageConnector.class);

    public static final String SYNC_RULE_NAME = "Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS";
    public static final String SYNC_RULE_DESC = "GENERATED -- DO NOT UPDATE: last synced at ";

    private static final SimpleDateFormat SYNC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final List<String> LINEAGE_ASSET_TYPES = createLineageAssetTypes();

    private static List<String> createLineageAssetTypes() {
        ArrayList<String> lineageTypes = new ArrayList<>();
        lineageTypes.add("information_governance_rule");
        lineageTypes.add("data_file_field");
        lineageTypes.add("data_file_record");
        lineageTypes.add("data_file");
        lineageTypes.add("database_column");
        lineageTypes.add("database_table");
        lineageTypes.add("view");
        lineageTypes.add("ds_stage_column");
        lineageTypes.add("link");
        lineageTypes.add("stage");
        lineageTypes.add("dsjob");
        lineageTypes.add("sequence_job");
        return Collections.unmodifiableList(lineageTypes);
    }

    /**
     * Retrieve a list of the asset types we need to process for lineage.
     *
     * @return {@code List<String>}
     */
    public static final List<String> getLineageAssetTypes() { return LINEAGE_ASSET_TYPES; }

    private IGCRestClient igcRestClient;
    private IGCVersionEnum igcVersion;
    private ObjectMapper objectMapper;
    private DataEngineSoftwareServerCapability dataEngine;

    private String defaultUserId;

    private Set<DSJob> changedJobsCache;

    /**
     * Default constructor used by the OCF Connector Provider.
     */
    public DataStageConnector() {
        // Nothing to do...
    }

    /**
     * Call made by the ConnectorProvider to initialize the Connector with the base services.
     *
     * @param connectorInstanceId   unique id for the connector instance useful for messages etc
     * @param connectionProperties   POJO for the configuration used to create the connector.
     */
    @Override
    public void initialize(String               connectorInstanceId,
                           ConnectionProperties connectionProperties) {
        super.initialize(connectorInstanceId, connectionProperties);

        final String methodName = "initialize";

        if (log.isInfoEnabled()) { log.info("Initializing DataStageDataEngineConnector..."); }

        // Note: it is not currently possible to try to pull these from a separate IGC proxy instance, so we need to
        // ask for these same details on this connector as well
        Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
        String igcHost = (String) proxyProperties.get("ibm.igc.services.host");
        String igcPort = (String) proxyProperties.get("ibm.igc.services.port");
        String igcUser = (String) proxyProperties.get("ibm.igc.username");
        String igcPass = (String) proxyProperties.get("ibm.igc.password");
        Integer igcPage = (Integer) proxyProperties.get("ibm.igc.pagesize");

        this.defaultUserId = igcUser;

        // Create new REST API client (opens a new session)
        this.igcRestClient = new IGCRestClient(igcHost, igcPort, igcUser, igcPass);
        if (this.igcRestClient.isSuccessfullyInitialised()) {
            // Set the version based on the IGC client's auto-determination of the IGC environment's version
            this.igcVersion = this.igcRestClient.getIgcVersion();
            // Set the default page size to whatever is provided as part of config parameters (default to 100)
            if (igcPage != null) {
                this.igcRestClient.setDefaultPageSize(igcPage);
            } else {
                this.igcRestClient.setDefaultPageSize(100);
            }
            // Register the types we'll use as part of job processing
            for (String lineageAssetType : getLineageAssetTypes()) {
                Class pojo = igcRestClient.findPOJOForType(lineageAssetType);
                igcRestClient.registerPOJO(pojo);
            }
        }

        // Create a new SoftwareServerCapability representing this Data Engine
        SoftwareServerCapability sscDataEngine = new SoftwareServerCapability();
        sscDataEngine.setEngineType("IBM InfoSphere DataStage");
        sscDataEngine.setEngineVersion(igcRestClient.getIgcVersion().getVersionString());
        sscDataEngine.setQualifiedName("ibm-datastage@" + igcHost + ":" + igcPort);
        sscDataEngine.setDisplayName(igcHost + ":" + igcPort);
        dataEngine = new DataEngineSoftwareServerCapability(sscDataEngine, defaultUserId);
        this.objectMapper = new ObjectMapper();

    }

    /**
     * Free up any resources held since the connector is no longer needed.
     */
    @Override
    public void disconnect() {
        // Close the session on the IGC REST client
        this.igcRestClient.disconnect();
    }

    /**
     * Retrieve the details about the data engine to which we are connected.
     *
     * @return DataEngineSoftwareServerCapability
     */
    @Override
    public DataEngineSoftwareServerCapability getDataEngineDetails() { return dataEngine; }

    /**
     * Retrieve the date and time at which changes were last synchronized.
     *
     * @return Date
     */
    @Override
    public Date getChangesLastSynced() {
        Reference jobSyncRule = getJobSyncRule();
        Date lastSync = null;
        if (jobSyncRule != null) {
            String description = (String) igcRestClient.getPropertyByName(jobSyncRule, "short_description");
            String dateString = description.substring(SYNC_RULE_DESC.length());
            try {
                lastSync = SYNC_DATE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                log.error("Unable to parse date and time of last sync from rule: {} ({})", description, dateString);
            }
        }
        return lastSync;
    }

    /**
     * Persist the date and time at which changes were last successfully synchronized.
     *
     * @param time
     * @throws OCFRuntimeException if there is any problem persisting the date and time
     */
    @Override
    public void setChangesLastSynced(Date time) throws OCFRuntimeException {
        final String methodName = "setChangesLastSynced";
        Reference exists = getJobSyncRule();
        String newDescription = SYNC_RULE_DESC + SYNC_DATE_FORMAT.format(time);
        boolean success;
        if (exists == null) {
            // Create the entry
            IGCCreate igcCreate = new IGCCreate("information_governance_rule");
            igcCreate.addProperty("name", SYNC_RULE_NAME);
            igcCreate.addProperty("short_description", newDescription);
            success = igcRestClient.create(igcCreate);
        } else {
            // Update the entry
            IGCUpdate igcUpdate = new IGCUpdate(exists.getId());
            igcUpdate.addProperty("short_description", newDescription);
            success = igcRestClient.update(igcUpdate);
        }
        if (!success) {
            DataStageErrorCode errorCode = DataStageErrorCode.SYNC_TIME_UPDATE_FAILURE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage();
            throw new OCFRuntimeException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }
    }

    /**
     * Retrieve a list of the changed schema types between the dates and times provided.
     *
     * @param from the date and time from which to look for changes (exclusive)
     * @param to the date and time up to which to look for changes (inclusive)
     * @return {@code List<DataEngineSchemaType>}
     */
    @Override
    public List<DataEngineSchemaType> getChangedSchemaTypes(Date from, Date to) {
        // do nothing -- schemas will always be handled by other methods
        return null;
    }

    /**
     * Retrieve a list of the changed port implementations between the dates and times provided.
     *
     * @param from the date and time from which to look for changes (exclusive)
     * @param to the date and time up to which to look for changes (inclusive)
     * @return {@code List<DataEnginePortImplementation>}
     */
    @Override
    public List<DataEnginePortImplementation> getChangedPortImplementations(Date from, Date to) {
        // do nothing -- port implementations will always be handled by other methods
        return null;
    }

    /**
     * Retrieve a list of the changed port aliases between the dates and times provided.
     *
     * @param from the date and time from which to look for changes (exclusive)
     * @param to the date and time up to which to look for changes (inclusive)
     * @return {@code List<DataEnginePortAlias>}
     */
    @Override
    public List<DataEnginePortAlias> getChangedPortAliases(Date from, Date to) {
        // do nothing -- port aliases will always be handled by other methods
        return null;
    }

    /**
     * Retrieve a list of the changed processes between the dates and times provided.
     *
     * @param from the date and time from which to look for changes (exclusive)
     * @param to the date and time up to which to look for changes (inclusive)
     * @return {@code List<DataEngineProcess>}
     */
    @Override
    public List<DataEngineProcess> getChangedProcesses(Date from, Date to) {

        List<DataEngineProcess> changedProcesses = new ArrayList<>();

        // Reset the cache
        changedJobsCache = new HashSet<>();

        // Start by getting a list of the changed jobs
        ReferenceList changedJobs = getChangedJobs(from, to);
        changedProcesses.addAll(getProcessesForJobs(changedJobs));
        while (changedJobs.hasMorePages()) {
            changedJobs.getNextPage(igcRestClient);
            changedProcesses.addAll(getProcessesForJobs(changedJobs));
        }

        return changedProcesses;

    }

    /**
     * Retrieve a list of the changed lineage mappings between the dates and times provided.
     *
     * @param from the date and time from which to look for changes (exclusive)
     * @param to the date and time up to which to look for changes (inclusive)
     * @return {@code List<DataEngineLineageMappings>}
     */
    @Override
    public List<DataEngineLineageMappings> getChangedLineageMappings(Date from, Date to) {
        log.info("Retrieving all changed lineage mappings (from cache)...");
        Set<DataEngineLineageMappings> lineageMappings = new HashSet<>();
        // Re-use the jobs we've already retrieved and cached to avoid needing to re-query all of their details again
        log.info(" ... cache size = {}", changedJobsCache.size());
        for (DSJob job : changedJobsCache) {
            lineageMappings.addAll(getCrossStageLineageMappings(job));
        }
        return new ArrayList<>(lineageMappings);
    }

    /**
     * Retrieve a listing of jobs that have been modified since the provided date and time.
     *
     * @param from the date and time from which to look for changed jobs
     * @param to the date and time up to which to look for changed jobs
     * @return ReferenceList
     */
    private ReferenceList getChangedJobs(Date from, Date to) {
        long fromTime = 0;
        long toTime = to.getTime();
        // TODO: may need to modify search criteria for job retrieval to pick up jobs used in changed sequences
        IGCSearch igcSearch = new IGCSearch("dsjob");
        igcSearch.addProperties(DSJob.getSearchProperties());
        IGCSearchCondition cTo   = new IGCSearchCondition("modified_on", "<=", "" + toTime);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(cTo);
        if (from != null) {
            fromTime = from.getTime();
            IGCSearchCondition cFrom = new IGCSearchCondition("modified_on", ">", "" + fromTime);
            conditionSet.addCondition(cFrom);
            conditionSet.setMatchAnyCondition(false);
        }
        if (log.isInfoEnabled()) { log.info(" ... searching for changed jobs > {} and <= {}", fromTime, toTime); }
        igcSearch.addConditions(conditionSet);
        ReferenceList changedJobs = igcRestClient.search(igcSearch);
        return changedJobs;
    }

    /**
     * Retrieve all of the details about the provided DataStage job.
     *
     * @param job the DataStage job for which to retrieve details
     * @return DSJob
     */
    public DSJob getJobDetails(Reference job) {

        String jobRid = job.getId();
        ReferenceList stages = getStageDetailsForJob(jobRid);
        ReferenceList links = getLinkDetailsForJob(jobRid);
        ReferenceList stageCols = getStageColumnDetailsForLinks(jobRid);

        Map<String, ReferenceList> dataStoreDetailsMap = new HashMap<>();
        if (!job.getType().equals("sequence_job")) {
            mapDataStoreDetailsForJob(job, "reads_from_(design)", dataStoreDetailsMap);
            mapDataStoreDetailsForJob(job, "writes_to_(design)", dataStoreDetailsMap);
        }

        // Flatten the list of data store details
        List<Reference> dataStoreDetails = new ArrayList<>();
        for (ReferenceList referenceList : dataStoreDetailsMap.values()) {
            for (Reference item : referenceList.getItems()) {
                dataStoreDetails.add(item);
            }
        }

        return new DSJob(igcRestClient, job, stages, links, stageCols, dataStoreDetails);

    }

    /**
     * Translate the list of DataStage jobs into a list of Processes.
     *
     * @param jobs
     * @return {@code List<DataEngineProcess>}
     */
    private List<DataEngineProcess> getProcessesForJobs(ReferenceList jobs) {

        List<DataEngineProcess> processes = new ArrayList<>();

        List<Reference> jobList = jobs.getItems();
        List<Reference> seqList = new ArrayList<>();
        Map<String, DataEngineProcess> jobProcessByRid = new HashMap<>();
        // Translate changed jobs first, to build up appropriate PortAliases list
        for (Reference job : jobList) {
            if (job.getType().equals("sequence_job")) {
                seqList.add(job);
            } else {
                DSJob detailedJob = getJobDetails(job);
                changedJobsCache.add(detailedJob); // no need to cache sequences, only normal jobs
                processes.addAll(getProcessesForEachStage(detailedJob));
                DataEngineProcess jobProcess = getProcessForJob(detailedJob);
                if (jobProcess != null) {
                    jobProcessByRid.put(job.getId(), jobProcess);
                    processes.add(jobProcess);
                }
            }
        }
        // Then load sequences, re-using the PortAliases constructed for the jobs
        // TODO: this probably will NOT work for nested sequences?
        for (Reference sequence : seqList) {
            DSJob detailedSeq = getJobDetails(sequence);
            processes.add(getProcessForSequence(detailedSeq, jobProcessByRid));
        }

        return processes;

    }

    /**
     * Translate the detailed stages of the provided DataStage job into Processes.
     *
     * @param job
     * @return {@code List<DataEngineProcess>}
     */
    private List<DataEngineProcess> getProcessesForEachStage(DSJob job) {

        List<DataEngineProcess> processes = new ArrayList<>();

        log.info("Translating processes for each stage...");
        for (Reference stage : job.getAllStages()) {
            ProcessMapping processMapping = new ProcessMapping(job, stage);
            DataEngineProcess process = processMapping.getProcess();
            if (process != null) {
                try {
                    log.info(" ... process: {}", objectMapper.writeValueAsString(process));
                } catch (JsonProcessingException e) {
                    log.error("Unable to serialise to JSON: {}", process, e);
                }
                processes.add(process);
            }
        }

        return processes;

    }

    /**
     * Translate the detailed stages of the provided DataStage job into LineageMappings.
     *
     * @param job
     * @return {@code Set<DataEngineLineageMappings>}
     */
    private Set<DataEngineLineageMappings> getCrossStageLineageMappings(DSJob job) {

        Set<DataEngineLineageMappings> lineageMappings = new HashSet<>();

        log.info("Load cross-stage lineage mappings...");
        for (Reference link : job.getAllLinks()) {
            LineageMappingMapping lineageMappingMapping = new LineageMappingMapping(job, link);
            DataEngineLineageMappings crossStageLineageMappings = lineageMappingMapping.getLineageMappings();
            if (crossStageLineageMappings != null
                    && crossStageLineageMappings.getLineageMappings() != null
                    && !crossStageLineageMappings.getLineageMappings().isEmpty()) {
                try {
                    log.info(" ... mappings: {}", objectMapper.writeValueAsString(crossStageLineageMappings));
                } catch (JsonProcessingException e) {
                    log.error("Unable to serialise to JSON: {}", crossStageLineageMappings, e);
                }
                lineageMappings.add(crossStageLineageMappings);
            }
        }

        return lineageMappings;

    }

    /**
     * Translate a single Process to represent the DataStage job itself.
     *
     * @param job the job object for which to load a process
     * @return DataEngineProcess
     */
    private DataEngineProcess getProcessForJob(DSJob job) {
        DataEngineProcess process = null;
        if (job.getType() == DSJob.JobType.JOB) {
            log.info("Load process for job...");
            ProcessMapping processMapping = new ProcessMapping(job);
            process = processMapping.getProcess();
            if (process != null) {
                try {
                    log.info(" ... process: {}", objectMapper.writeValueAsString(process));
                } catch (JsonProcessingException e) {
                    log.error("Unable to serialise to JSON: {}", process, e);
                }
            }
        }
        return process;
    }

    /**
     * Translate a single Process to represent the DataStage sequence itself.
     *
     * @param job the job object for which to load a process
     * @param jobProcessByRid a map from job RID to its detailed process definition
     * @return DataEngineProcess
     */
    private DataEngineProcess getProcessForSequence(DSJob job, Map<String, DataEngineProcess> jobProcessByRid) {
        DataEngineProcess process = null;
        if (job.getType() == DSJob.JobType.SEQUENCE) {
            log.info("Load process for sequence...");
            ProcessMapping processMapping = new ProcessMapping(job, jobProcessByRid);
            process = processMapping.getProcess();
            if (process != null) {
                try {
                    log.info(" ... process: {}", objectMapper.writeValueAsString(process));
                } catch (JsonProcessingException e) {
                    log.error("Unable to serialise to JSON: {}", process, e);
                }
            }
        }
        return process;
    }

    /**
     * Retrieve a listing of the stages within a particular DataStage job.
     *
     * @param jobRid the RID of the job for which to retrieve stage details
     * @return ReferenceList
     */
    private ReferenceList getStageDetailsForJob(String jobRid) {
        IGCSearch igcSearch = new IGCSearch("stage");
        igcSearch.addProperties(DSStage.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList stages = igcRestClient.search(igcSearch);
        stages.getAllPages(igcRestClient);
        return stages;
    }

    /**
     * Retrieve a listing of the links within a particular DataStage job.
     *
     * @param jobRid the RID of the job for which to retrieve link details
     * @return ReferenceList
     */
    private ReferenceList getLinkDetailsForJob(String jobRid) {
        IGCSearch igcSearch = new IGCSearch("link");
        igcSearch.addProperties(DSLink.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList links = igcRestClient.search(igcSearch);
        links.getAllPages(igcRestClient);
        return links;
    }

    /**
     * Retrieve a listing of the stage columns within a particular DataStage job.
     *
     * @param jobRid the RID of the job for which to retrieve stage column details
     * @return ReferenceList
     */
    private ReferenceList getStageColumnDetailsForLinks(String jobRid) {
        IGCSearch igcSearch = new IGCSearch("ds_stage_column");
        igcSearch.addProperties(DSStageColumn.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("link.job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList stageCols = igcRestClient.search(igcSearch);
        stageCols.getAllPages(igcRestClient);
        return stageCols;
    }

    /**
     * Retrieve a listing of database columns within a particular database table.
     *
     * @param tableRid the RID of the database table for which to retrieve column details
     * @return ReferenceList
     */
    private ReferenceList getColumnsForTable(String tableRid) {
        IGCSearch igcSearch = new IGCSearch("database_column");
        igcSearch.addProperties(DatabaseColumn.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("database_table_or_view", "=", tableRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList columns = igcRestClient.search(igcSearch);
        columns.getAllPages(igcRestClient);
        return columns;
    }

    /**
     * Retrieve a listing of file fields within a particular file record.
     *
     * @param recordRid the RID of the data file record for which to retrieve field details
     * @return ReferenceList
     */
    private ReferenceList getFieldsForRecord(String recordRid) {
        IGCSearch igcSearch = new IGCSearch("data_file_field");
        igcSearch.addProperties(DataFileField.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("data_file_record", "=", recordRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList fields = igcRestClient.search(igcSearch);
        fields.getAllPages(igcRestClient);
        return fields;
    }

    /**
     * Map the data store details used by the provided job into the provided map.
     *
     * @param job the job for which to map data store details
     * @param relationshipProperty the relationship property from which to retrieve data store information
     * @param dataStoreDetailsMap the map into which to place the results
     */
    private void mapDataStoreDetailsForJob(Reference job, String relationshipProperty, Map<String, ReferenceList> dataStoreDetailsMap) {
        ReferenceList candidates = (ReferenceList) igcRestClient.getPropertyByName(job, relationshipProperty);
        if (candidates != null) {
            for (Reference candidate : candidates.getItems()) {
                String candidateId = candidate.getId();
                if (!dataStoreDetailsMap.containsKey(candidateId)) {
                    dataStoreDetailsMap.put(candidateId, getDataFieldDetails(candidate));
                }
            }
        }
    }

    /**
     * Retrieve a listing of details of the fields contained within the provided data store.
     *
     * @param datastore the data store for which to retrieve detailed field-level information
     * @return ReferenceList
     */
    private ReferenceList getDataFieldDetails(Reference datastore) {

        ReferenceList results = null;
        switch(datastore.getType()) {
            case "database_table":
            case "view":
                results = getColumnsForTable(datastore.getId());
                break;
            case "data_file_record":
                results = getFieldsForRecord(datastore.getId());
                break;
            default:
                if (log.isWarnEnabled()) { log.warn("Unknown source / target type -- skipping: {}", datastore); }
                break;
        }
        return results;

    }

    /**
     * Retrieve the rule that represents the job sync with Data Engine OMAS.
     *
     * @return Reference
     */
    private Reference getJobSyncRule() {
        IGCSearch igcSearch = new IGCSearch("information_governance_rule");
        igcSearch.addProperty("short_description");
        IGCSearchCondition condition = new IGCSearchCondition("name", "=", SYNC_RULE_NAME);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList results = igcRestClient.search(igcSearch);
        return (results == null || results.getPaging().getNumTotal() == 0) ? null : results.getItems().get(0);
    }

}
