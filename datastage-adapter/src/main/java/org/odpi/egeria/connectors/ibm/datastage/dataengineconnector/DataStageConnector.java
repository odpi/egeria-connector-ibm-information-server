/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.DataFileMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.DatabaseSchemaMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.ProcessMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.RelationalTableMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.SchemaTypeMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.openmetadata.accessservices.dataengine.model.*;
import org.odpi.openmetadata.accessservices.dataengine.model.DataFile;
import org.odpi.openmetadata.accessservices.dataengine.model.DatabaseSchema;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.odpi.openmetadata.frameworks.connectors.ffdc.*;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.odpi.openmetadata.frameworks.connectors.properties.EndpointProperties;
import org.odpi.openmetadata.governanceservers.dataengineproxy.connectors.DataEngineConnectorBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataStageConnector extends DataEngineConnectorBase {

    private static final Logger log = LoggerFactory.getLogger(DataStageConnector.class);

    private static final String SYNC_RULE_PREFIX = "Job metadata will be synced through Egeria";
    private static final String SYNC_RULE_DESC = "GENERATED -- DO NOT UPDATE: last synced at ";
    public static final String DATABASE_TABLE = "database_table";
    public static final String DATA_FILE_RECORD = "data_file_record";

    private final SimpleDateFormat syncDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private IGCRestClient igcRestClient;
    private ObjectMapper objectMapper;
    private SoftwareServerCapability dataEngine;

    private DataStageCache dataStageCache;
    private List<ProcessHierarchy> processHierarchies;

    private boolean includeVirtualAssets = true;
    private boolean createDataStoreSchemas = false;
    private List<String> limitToProjects;
    private boolean limitToLineageEnabledJobs = false;

    private LineageMode mode = LineageMode.GRANULAR;

    /**
     * Default constructor used by the OCF Connector Provider.
     */
    public DataStageConnector() {
        limitToProjects = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(String               connectorInstanceId,
                           ConnectionProperties connectionProperties) {
        super.initialize(connectorInstanceId, connectionProperties);
        this.objectMapper = new ObjectMapper();
        this.dataStageCache = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized void start() throws ConnectorCheckedException {

        super.start();
        final String methodName = "start";

        log.info("Initializing DataStageDataEngineConnector...");

        EndpointProperties endpointProperties = connectionProperties.getEndpoint();
        if (endpointProperties == null) {
            raiseConnectorCheckedException(methodName, "<null>");
        } else {
            String address = endpointProperties.getAddress();
            if (address == null || address.length() == 0) {
                raiseConnectorCheckedException(methodName, address);
            } else {

                String igcUser = connectionProperties.getUserId();
                String igcPass = connectionProperties.getClearPassword();

                Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
                Integer igcPage = null;
                if (proxyProperties != null) {
                    igcPage = (Integer) proxyProperties.get(DataStageConnectorProvider.PAGE_SIZE);
                    includeVirtualAssets = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.INCLUDE_VIRTUAL_ASSETS, true);
                    createDataStoreSchemas = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.CREATE_DATA_STORE_SCHEMAS, false);
                    Object projects = proxyProperties.getOrDefault(DataStageConnectorProvider.LIMIT_TO_PROJECTS, null);
                    if (projects instanceof String) {
                        limitToProjects.add((String)projects);
                    } else if (projects != null) {
                        limitToProjects.addAll((List<String>)projects);
                    }
                    limitToLineageEnabledJobs = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.LIMIT_TO_LINEAGE_ENABLED_JOBS, limitToLineageEnabledJobs);
                    Object lineageMode = proxyProperties.getOrDefault(DataStageConnectorProvider.MODE, null);
                    if (lineageMode != null) {
                        try {
                            mode = LineageMode.valueOf((String) lineageMode);
                        } catch (IllegalArgumentException e) {
                            log.warn("Lineage mode specified in the configuration is not a known value.", e);
                        }
                    }
                }

                IGCVersionEnum igcVersion;
                try {
                    // Create new REST API client (opens a new session)
                    this.igcRestClient = new IGCRestClient("https://" + address, igcUser, igcPass);
                    if (this.igcRestClient.start()) {

                        // Set the version based on the IGC client's auto-determination of the IGC environment's version
                        igcVersion = this.igcRestClient.getIgcVersion();
                        // Set the default page size to whatever is provided as part of config parameters (default to 100)
                        if (igcPage != null) {
                            this.igcRestClient.setDefaultPageSize(igcPage);
                        } else {
                            this.igcRestClient.setDefaultPageSize(100);
                        }

                        // Create a new SoftwareServerCapability representing this Data Engine
                        dataEngine = new SoftwareServerCapability();
                        dataEngine.setEngineType("IBM InfoSphere DataStage");
                        dataEngine.setEngineVersion(igcVersion.getVersionString());
                        dataEngine.setQualifiedName("ibm-datastage@" + address);
                        dataEngine.setName(address);

                    } else {
                        raiseConnectorCheckedException(methodName, address);
                    }
                } catch (IGCException e) {
                    raiseConnectorCheckedException(methodName, address);
                }

            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void disconnect() throws ConnectorCheckedException {
        throwsOCFException_(ConnectorCheckedException.class);
        final String methodName = "disconnect";
        try {
            // Close the session on the IGC REST client
            this.igcRestClient.disconnect();
        } catch (IGCException e) {
            throwOCFException(new ConnectorCheckedException(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR.getMessageDefinition(),this.getClass().getName(), methodName, e));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoftwareServerCapability getDataEngineDetails() { return dataEngine; }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Date getChangesLastSynced()  throws ConnectorCheckedException, PropertyServerException {

        throwsOCFException_(ConnectorCheckedException.class);
        throwsOCFException_(PropertyServerException.class);

        InformationGovernanceRule jobSyncRule = getJobSyncRule();
        Date lastSync = null;
        if (jobSyncRule != null) {
            String description = jobSyncRule.getShortDescription();
            String dateString = description.substring(SYNC_RULE_DESC.length());
            try {
                lastSync = syncDateFormat.parse(dateString);
            } catch (ParseException e) {
                log.error("Unable to parse date and time of last sync from rule '{}' ({}) using format: {}", description, dateString, syncDateFormat.toPattern(), e);
            }
        }
        return lastSync;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setChangesLastSynced(Date time) throws ConnectorCheckedException, PropertyServerException {

        throwsOCFException_(ConnectorCheckedException.class);
        throwsOCFException_(PropertyServerException.class);

        final String methodName = "setChangesLastSynced";
        InformationGovernanceRule exists = getJobSyncRule();
        String newDescription = SYNC_RULE_DESC + syncDateFormat.format(time);
        boolean success = false;
        try {
            if (exists == null) {
                // Create the entry
                IGCCreate igcCreate = new IGCCreate("information_governance_rule");
                igcCreate.addProperty(DataStageConstants.NAME, getJobSyncRuleName());
                igcCreate.addProperty(DataStageConstants.SHORT_DESCRIPTION, newDescription);
                success = igcRestClient.create(igcCreate) != null;
            } else {
                // Update the entry
                IGCUpdate igcUpdate = new IGCUpdate(exists.getId());
                igcUpdate.addProperty(DataStageConstants.SHORT_DESCRIPTION, newDescription);
                success = igcRestClient.update(igcUpdate);
            }
        } catch (IGCException e) {
            propagateIgcRestClientException(this.getClass().getName(), methodName, e);
        }
        if (!success) {
            throwOCFException(new ConnectorCheckedException(DataStageErrorCode.SYNC_TIME_UPDATE_FAILURE.getMessageDefinition(), this.getClass().getName(), methodName));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Date getOldestChangeSince(Date time) throws ConnectorCheckedException, PropertyServerException {

        throwsOCFException_(PropertyServerException.class);
        throwsOCFException_(ConnectorCheckedException.class);

        Dsjob oldest = getOldestJobSince(time);
        if (oldest != null) {
            return oldest.getModifiedOn();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SchemaType> getChangedSchemaTypes(Date from, Date to) throws ConnectorCheckedException, PropertyServerException {

        throwsOCFException_(PropertyServerException.class);
        throwsOCFException_(ConnectorCheckedException.class);

        log.debug("Looking for changed SchemaTypes...");
        Map<String, SchemaType> schemaTypeMap = new HashMap<>();

        if (mode == LineageMode.JOB_LEVEL) {
            return Collections.emptyList();
        }
        initializeCache(from, to);

        // Iterate through each job looking for any virtual assets -- these must be created first
        for (DataStageJob job : dataStageCache.getAllJobs()) {
            for (String storeRid : job.getStoreRids()) {
                log.debug(" ... considering store: {}", storeRid);
                if (!schemaTypeMap.containsKey(storeRid)) {
                    if ( (IGCRestClient.isVirtualAssetRid(storeRid) && includeVirtualAssets)
                            || (!IGCRestClient.isVirtualAssetRid(storeRid) && createDataStoreSchemas) ) {
                        log.debug(" ... Creating a SchemaType ...");
                        SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(dataStageCache);
                        SchemaType deSchemaType = schemaTypeMapping.getForDataStore(dataStageCache.getStoreIdentityFromRid(storeRid));
                        logEntityCreated(deSchemaType);
                        schemaTypeMap.put(storeRid, deSchemaType);
                    }
                }
            }
        }
        return new ArrayList<>(schemaTypeMap.values());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<? super Referenceable> getChangedDataStores(Date from, Date to) throws ConnectorCheckedException, PropertyServerException  {

        throwsOCFException_(PropertyServerException.class);
        throwsOCFException_(ConnectorCheckedException.class);

        log.debug("Looking for changed DataStores...");
        Map<String, ? super Referenceable> dataStoreMap = new HashMap<>();

        initializeCache(from, to);

        // Iterate through each job looking for any virtual assets -- these must be created first
        for (DataStageJob job : dataStageCache.getAllJobs()) {
            for (String storeRid : job.getStoreRids()) {
                log.debug(" ... considering store: {}", storeRid);
                if (!dataStoreMap.containsKey(storeRid)) {
                    if ((IGCRestClient.isVirtualAssetRid(storeRid) && includeVirtualAssets)) {
                        Identity storeIdentity = dataStageCache.getStoreIdentityFromRid(storeRid);
                        Identity parentIdentity = storeIdentity.getParentIdentity();
                        String type = storeIdentity.getAssetType();
                        switch (type) {
                            case DATABASE_TABLE:
                                log.debug(" ... Creating a DatabaseSchema ...");
                                DatabaseSchemaMapping databaseSchemaMapping = new DatabaseSchemaMapping(dataStageCache);
                                DatabaseSchema dbSchema = databaseSchemaMapping.getForDataStore(parentIdentity);
                                logEntityCreated(dbSchema);

                                log.debug(" ... Creating a RelationalTable ...");
                                RelationalTableMapping relationalTableMapping = new RelationalTableMapping(dataStageCache);
                                VirtualTable table = relationalTableMapping.getForDataStore(storeIdentity);
                                table.setDatabaseSchema(dbSchema);
                                logEntityCreated(table);

                                dataStoreMap.put(storeRid, table);
                                break;
                            case DATA_FILE_RECORD:
                                log.debug(" ... Creating a SchemaType ...");
                                SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(dataStageCache);
                                SchemaType schemaType = schemaTypeMapping.getForDataStore(storeIdentity);
                                logEntityCreated(schemaType);

                                log.debug(" ... Creating a File ...");
                                DataFileMapping dataFileMapping = new DataFileMapping(dataStageCache);
                                DataFile dataFile = dataFileMapping.getForDataStore(parentIdentity);
                                dataFile.setSchema(schemaType);
                                logEntityCreated(dataFile);

                                dataStoreMap.put(storeRid, dataFile);
                                break;
                        }
                    }
                }
            }
        }
        return new ArrayList<>(dataStoreMap.values());
    }

    private void logEntityCreated(Referenceable table) {
        if (log.isDebugEnabled()) {
            try {
                log.debug(" ... created: {}", objectMapper.writeValueAsString(table));
            } catch (JsonProcessingException e) {
                log.error("Unable to serialise to JSON: {}", table, e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Process> getChangedProcesses(Date from, Date to) throws ConnectorCheckedException, PropertyServerException {

        throwsOCFException_(ConnectorCheckedException.class);
        throwsOCFException_(PropertyServerException.class);

        initializeCache(from, to);

        List<Process> processes = new ArrayList<>();

        List<DataStageJob> seqList = new ArrayList<>();
        // Translate changed jobs first, to build up appropriate PortAliases list
        for (DataStageJob detailedJob : dataStageCache.getAllJobs()) {
            if (detailedJob.getType().equals(DataStageJob.JobType.SEQUENCE)) {
                seqList.add(detailedJob);
            } else {
                if (mode == LineageMode.GRANULAR) {
                    // Only translate stage-level details for granular mode
                    List<Process> stageLevelProcesses = getProcessesForEachStage(detailedJob);
                    for (Process stageLevel : stageLevelProcesses) {
                        cacheHierarchyRelationshipsFromProcessDetails(stageLevel);
                        processes.add(stageLevel);
                    }

                    // Then load sequences, re-using the PortAliases constructed for the jobs
                    // TODO: this probably will NOT work for nested sequences?
                    for (DataStageJob detailedSeq : seqList) {
                        List<Process> sequencedJobs = getProcessesForSequence(detailedSeq);
                        for (Process sequenced : sequencedJobs) {
                            cacheHierarchyRelationshipsFromProcessDetails(sequenced);
                            processes.add(sequenced);
                        }
                    }
                }
                Process jobProcess = getProcessForJob(detailedJob);
                if (jobProcess != null) {
                    cacheHierarchyRelationshipsFromProcessDetails(jobProcess);
                    processes.add(jobProcess);
                }
            }
        }

        return processes;

    }

    private void cacheHierarchyRelationshipsFromProcessDetails(Process process) {
        List<ParentProcess> parents = process.getParentProcesses();
        if (parents != null) {
            // Store these in-memory to pass along to the getChangedProcessHierarchies() method
            for (ParentProcess parent : parents) {
                ProcessHierarchy hierarchy = new ProcessHierarchy();
                hierarchy.setChildProcess(process.getQualifiedName());
                hierarchy.setParentProcess(parent.getQualifiedName());
                hierarchy.setProcessContainmentType(parent.getProcessContainmentType());
                processHierarchies.add(hierarchy);
            }
            process.setParentProcesses(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProcessHierarchy> getChangedProcessHierarchies(Date from, Date to)  {
        // Output list of changed process hierarchies from the cached information
        return processHierarchies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LineageMapping> getChangedLineageMappings(Date from, Date to) {
        // do nothing -- lineage mappings will always be handled by other methods
        return Collections.emptyList();
    }

    /**
     * Initialize the cache of changed job details based on the provided dates and times.
     *
     * @param from the date and time from which to cache changes (exclusive)
     * @param to the date and time up to which to cache changes (inclusive)
     */
    private void initializeCache(Date from, Date to) {
        DataStageCache forComparison = new DataStageCache(from, to, mode, limitToProjects, limitToLineageEnabledJobs);
        if (dataStageCache == null || !dataStageCache.equals(forComparison)) {
            // Initialize the cache, if it is empty, or reset it if it differs from the dates and times we've been given
            dataStageCache = forComparison;
            dataStageCache.initialize(igcRestClient);
            processHierarchies = new ArrayList<>();
        }
    }

    /**
     * Translate the detailed stages of the provided DataStage job into Processes.
     *
     * @param job the job for which to translate detailed stages
     * @return {@code List<Process>}
     */
    private List<Process> getProcessesForEachStage(DataStageJob job) {
        List<Process> processes = new ArrayList<>();
        log.debug("Translating processes for each stage...");
        for (Stage stage : job.getAllStages()) {
            ProcessMapping processMapping = new ProcessMapping(dataStageCache);
            Process process = processMapping.getForStage(stage, job);
            if (process != null) {
                try {
                    log.debug(" ... process: {}", objectMapper.writeValueAsString(process));
                } catch (JsonProcessingException e) {
                    log.error("Unable to serialise to JSON: {}", process, e);
                }
                processes.add(process);
            }
        }
        return processes;
    }

    /**
     * Translate a single Process to represent the DataStage job itself.
     *
     * @param job the job object for which to load a process
     * @return Process
     */
    private Process getProcessForJob(DataStageJob job) {
        log.debug("Load process for job...");
        Process process = dataStageCache.getProcessByRid(job.getJobObject().getId());
        if (process != null) {
            if (mode == LineageMode.JOB_LEVEL) {
                // TODO: fill in the LineageMapping at this process level
            }
            try {
                if (log.isDebugEnabled()) { log.debug(" ... process: {}", objectMapper.writeValueAsString(process)); }
            } catch (JsonProcessingException e) {
                log.error("Unable to serialise to JSON: {}", process, e);
            }
        }
        return process;
    }

    /**
     * Translate a DataStage sequence into a Process, as well as any other jobs that the sequence calls which are not
     * already included as changes (executing a job from a sequence does not cause the job to be updated, so will not
     * appear at job-level as a separate change, but needs to be included to update the process hierarchy
     * relationships).
     *
     * @param job the job object for which to load a process
     * @return {@code List<Process>}
     */
    private List<Process> getProcessesForSequence(DataStageJob job) {
        log.debug("Load process for sequence...");
        List<Process> processes = new ArrayList<>();
        // Create a copy of the map, as the next step could update it by caching additional job details
        // necessary for the port aliases
        Set<String> alreadyOutputProcesses = dataStageCache.getCachedProcessRids();
        Process process = dataStageCache.getProcessByRid(job.getJobObject().getId());
        if (process != null) {
            log.debug(" ... examining {} jobs run by the sequence", job.getAllStages().size());
            for (Stage stage : job.getAllStages()) {
                Dsjob runsJob = stage.getRunsSequencesJobs();
                String rid = runsJob.getId();
                if (rid != null && !alreadyOutputProcesses.contains(rid)) {
                    log.debug(" ...... found a job not already included in our changes: {}", rid);
                    // For any remaining, add them to the list of processes
                    Process sequencedProcess = dataStageCache.getProcessByRid(rid);
                    if (sequencedProcess != null) {
                        try {
                            if (log.isDebugEnabled()) { log.debug(" ...... adding process: {}", objectMapper.writeValueAsString(sequencedProcess)); }
                        } catch (JsonProcessingException e) {
                            log.error("Unable to serialise to JSON: {}", sequencedProcess, e);
                        }
                        processes.add(sequencedProcess);
                    } else {
                        log.error(" ... job could not be found or cached, something went wrong: {}", rid);
                    }
                }
            }
            // And then finally add the sequence itself
            processes.add(process);
            try {
                if (log.isDebugEnabled()) { log.debug(" ... process: {}", objectMapper.writeValueAsString(process)); }
            } catch (JsonProcessingException e) {
                log.error("Unable to serialise to JSON: {}", process, e);
            }
        }
        return processes;
    }

    /**
     * Retrieve the rule that represents the job sync with Data Engine OMAS.
     *
     * @return InformationGovernanceRule
     */
    private InformationGovernanceRule getJobSyncRule() {
        final String methodName = "getJobSyncRule";
        IGCSearch igcSearch = new IGCSearch("information_governance_rule");
        igcSearch.addProperty(DataStageConstants.SHORT_DESCRIPTION);
        IGCSearchCondition condition = new IGCSearchCondition(DataStageConstants.NAME, "=", getJobSyncRuleName());
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        try {
            ItemList<InformationGovernanceRule> results = igcRestClient.search(igcSearch);
            return (results == null || results.getPaging().getNumTotal() == 0) ? null : results.getItems().get(0);
        } catch (IGCException e) {
            propagateIgcRestClientException(this.getClass().getName(), methodName, e);
        }
        return null;
    }

    /**
     * Construct the name of a unique rule to capture the last time jobs were sync'd for a combination of filtered
     * projects (and mode) to the Data Engine OMAS.
     *
     * @return String
     */
    private String getJobSyncRuleName() {
        String ruleName = SYNC_RULE_PREFIX + " (" + mode.getName() + ")";
        if (limitToProjects.size() > 0) {
            ruleName += " for projects: [" + String.join(",", limitToProjects) + "]";
        }
        return ruleName;
    }

    /**
     * Retrieve the oldest job (modified_on date furthest in the past) since the provided time.
     * @param time from which to consider changes
     * @return Dsjob
     */
    private Dsjob getOldestJobSince(Date time) {
        final String methodName = "getOldestJobSince";
        long startFrom = 0;
        if (time != null) {
            startFrom = time.getTime();
        }
        IGCSearch igcSearch = new IGCSearch("dsjob");
        igcSearch.addProperty("modified_on");
        IGCSearchCondition condition = new IGCSearchCondition("modified_on", ">", "" + startFrom);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        if (limitToProjects.size() > 0) {
            IGCSearchCondition cProject = new IGCSearchCondition("transformation_project.name", limitToProjects);
            conditionSet.addCondition(cProject);
            conditionSet.setMatchAnyCondition(false);
        }
        if(limitToLineageEnabledJobs) {
            IGCSearchCondition cIncludeForLineage = new IGCSearchCondition("include_for_lineage","=","true");
            conditionSet.addCondition(cIncludeForLineage);
            conditionSet.setMatchAnyCondition(false);
        }
        igcSearch.addConditions(conditionSet);
        IGCSearchSorting sorting = new IGCSearchSorting("modified_on", true);
        igcSearch.addSortingCriteria(sorting);
        igcSearch.setPageSize(2); // No need to get any more than 2 results, as we will only take the top result anyway
        try {
            ItemList<Dsjob> results = igcRestClient.search(igcSearch);
            return (results == null || results.getPaging().getNumTotal() == 0) ? null : results.getItems().get(0);
        } catch (IGCException e) {
            propagateIgcRestClientException(this.getClass().getName(), methodName, e);
        }
        return null;
    }

    /**
     * Throws a ConnectorCheckedException using the provided parameters.
     * @param methodName the name of the method throwing the exception
     * @param params any parameters for formatting the error message
     * @throws ConnectorCheckedException always
     */
    private void raiseConnectorCheckedException(String methodName, String... params) throws ConnectorCheckedException {
        throw new ConnectorCheckedException(DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition(params),
                this.getClass().getName(),
                methodName);
    }

    /**
     * Generic helper method that allows checked exceptions to be passed as unchecked by misleading the compiler. This helps with more flexible coding style.
     * This does not mean that the responsibility to properly define what errors are thrown is removed instead it is moved to single place that is the top caller method.
     *
     * @param e the actual OCF exception to be thrown.
     * @param <T> type variable
     * @return no return, only throws back e
     * @throws T always one of the known OCF Exceptions
     */
    @SuppressWarnings("unchecked")
    public static <T extends Exception> OCFRuntimeException throwOCFException(OCFCheckedExceptionBase e) throws T {
        throw (T) e;
    }

    /**
     *  Generic helper method that "enforces" specific exception type defined by parameter clazz to be thrown.
     *  Using this method we can compensate for the fact that error previously thrown line unchecked (@See throwOCFException) now should be explicitly declared in the method signature.
     *  This way the consumer of the connector API will know and implement error handling.
     *
     * @param clazz the class instance to be thrown
     * @param <T> type variable
     * @throws T exception instance defined by the type variable
     */
    @SuppressWarnings({"unused", "RedundantThrows"})
    private static <T extends Exception> void throwsOCFException_(Class<T> clazz) throws T {
    }

    /**
     * Utility method that centrally controls how runtime exceptions coming form IGC REST API will be propagated as OCF known exception.
     * Ideally, known exceptions will be mapped to one of the OCF checked exceptions: ConnectorCheckedException or PropertyServerException, worst case they are propagated as OCFRuntimeException.
     * With this, exceptions are categorized and aligned with the connector API interfaces (contracts).
     *
     * @param className the name of the class where IGCException originally occurred
     * @param methodName the name of the method where IGCException originally occurred
     * @param cause the original IGCException (or its subclasses)
     *
     * @throws OCFRuntimeException (or one of PropertyServerException, ConnectorCheckedException)
     */
    public static void propagateIgcRestClientException(String className, String methodName, IGCException cause) {
        if (cause == null) {
            // this should never be the case - we should always pass the original IGC exception; still this adds some extra resilience
            throw new OCFRuntimeException(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR.getMessageDefinition(),
                    className,
                    methodName);
        } else if (cause instanceof IGCConnectivityException) {
            // IGCConnectivityException  get mapped to the OCF PropertyServerException and can be retried if the usage context allows it
            // In future this part can be extended with specific cases for the rest of the IGCException sub-classes.
            throw throwOCFException(new PropertyServerException(DataStageErrorCode.CONNECTION_ERROR.getMessageDefinition(),
                    className,
                    methodName,
                    cause));
        } else {
            // we map all the generic IGCExceptions to ConnectorCheckedException with unknown runtime error code for the consumer.
            throw throwOCFException(new ConnectorCheckedException(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR.getMessageDefinition(),
                    className,
                    methodName,
                    cause));
        }
    }
}
