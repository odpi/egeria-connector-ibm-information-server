/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.ProcessMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.SchemaTypeMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.openmetadata.accessservices.dataengine.model.*;
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

    private static final String SYNC_RULE_NAME = "Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS";
    private static final String SYNC_RULE_DESC = "GENERATED -- DO NOT UPDATE: last synced at ";

    private final SimpleDateFormat syncDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private IGCRestClient igcRestClient;
    private ObjectMapper objectMapper;
    private SoftwareServerCapability dataEngine;

    private DataStageCache dataStageCache;

    private boolean includeVirtualAssets = true;
    private boolean createDataStoreSchemas = false;

    /**
     * Default constructor used by the OCF Connector Provider.
     */
    public DataStageConnector() {
        // Nothing to do...
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
    public void start() throws ConnectorCheckedException {

        super.start();
        final String methodName = "start";

        log.info("Initializing DataStageDataEngineConnector...");

        EndpointProperties endpointProperties = connectionProperties.getEndpoint();
        if (endpointProperties == null) {
            raiseConnectorCheckedException(DataStageErrorCode.CONNECTION_FAILURE, methodName, "<null>");
        } else {
            String address = endpointProperties.getAddress();
            if (address == null || address.equals("")) {
                raiseConnectorCheckedException(DataStageErrorCode.CONNECTION_FAILURE, methodName, address);
            } else {

                String igcUser = connectionProperties.getUserId();
                String igcPass = connectionProperties.getClearPassword();

                Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
                Integer igcPage = null;
                if (proxyProperties != null) {
                    igcPage = (Integer) proxyProperties.get(DataStageConnectorProvider.PAGE_SIZE);
                    includeVirtualAssets = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.INCLUDE_VIRTUAL_ASSETS, true);
                    createDataStoreSchemas = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.CREATE_DATA_STORE_SCHEMAS, false);
                }

                IGCVersionEnum igcVersion;
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
                    dataEngine.setDisplayName(address);

                } else {
                    raiseConnectorCheckedException(DataStageErrorCode.CONNECTION_FAILURE, methodName, address);
                }

            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect() {
        // Close the session on the IGC REST client
        this.igcRestClient.disconnect();
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
    public synchronized Date getChangesLastSynced() {
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
    public synchronized void setChangesLastSynced(Date time) {
        final String methodName = "setChangesLastSynced";
        InformationGovernanceRule exists = getJobSyncRule();
        String newDescription = SYNC_RULE_DESC + syncDateFormat.format(time);
        boolean success;
        if (exists == null) {
            // Create the entry
            IGCCreate igcCreate = new IGCCreate("information_governance_rule");
            igcCreate.addProperty(DataStageConstants.NAME, SYNC_RULE_NAME);
            igcCreate.addProperty(DataStageConstants.SHORT_DESCRIPTION, newDescription);
            success = igcRestClient.create(igcCreate) != null;
        } else {
            // Update the entry
            IGCUpdate igcUpdate = new IGCUpdate(exists.getId());
            igcUpdate.addProperty(DataStageConstants.SHORT_DESCRIPTION, newDescription);
            success = igcRestClient.update(igcUpdate);
        }
        if (!success) {
            throw new OCFRuntimeException(DataStageErrorCode.SYNC_TIME_UPDATE_FAILURE.getMessageDefinition(),
                    this.getClass().getName(),
                    methodName);
        }
    }

    /**
     * {@inheritDoc}
     */
//    @Override
    public synchronized Date getOldestChangeSince(Date time) {
        final String methodName = "getOldestChangeSince";
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
    public List<SchemaType> getChangedSchemaTypes(Date from, Date to) {

        log.debug("Looking for changed SchemaTypes...");
        Map<String, SchemaType> schemaTypeMap = new HashMap<>();

        initializeCache(from, to);

        // Iterate through each job looking for any virtual assets -- these must be created first
        for (DataStageJob job : dataStageCache.getAllJobs()) {
            for (String storeRid : job.getStoreRids()) {
                log.debug(" ... considering store: {}", storeRid);
                if (!schemaTypeMap.containsKey(storeRid)) {
                    if ( (IGCRestClient.isVirtualAssetRid(storeRid) && includeVirtualAssets)
                            || (!IGCRestClient.isVirtualAssetRid(storeRid) && createDataStoreSchemas) ) {
                        log.debug(" ... Creating a SchemaType ...");
                        SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(job, job.getStoreIdentityFromRid(storeRid), job.getFieldsForStore(storeRid));
                        SchemaType deSchemaType = schemaTypeMapping.getSchemaType();
                        if (log.isDebugEnabled()) {
                            try {
                                log.debug(" ... created: {}", objectMapper.writeValueAsString(deSchemaType));
                            } catch (JsonProcessingException e) {
                                log.error("Unable to serialise to JSON: {}", deSchemaType, e);
                            }
                        }
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
    public List<PortImplementation> getChangedPortImplementations(Date from, Date to) {
        // do nothing -- port implementations will always be handled by other methods
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PortAlias> getChangedPortAliases(Date from, Date to) {
        // do nothing -- port aliases will always be handled by other methods
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Process> getChangedProcesses(Date from, Date to) {

        initializeCache(from, to);

        List<Process> processes = new ArrayList<>();

        List<DataStageJob> seqList = new ArrayList<>();
        Map<String, Process> jobProcessByRid = new HashMap<>();
        // Translate changed jobs first, to build up appropriate PortAliases list
        for (DataStageJob detailedJob : dataStageCache.getAllJobs()) {
            if (detailedJob.getType().equals(DataStageJob.JobType.SEQUENCE)) {
                seqList.add(detailedJob);
            } else {
                processes.addAll(getProcessesForEachStage(detailedJob));
                Process jobProcess = getProcessForJob(detailedJob);
                if (jobProcess != null) {
                    jobProcessByRid.put(detailedJob.getJobObject().getId(), jobProcess);
                    processes.add(jobProcess);
                }
            }
        }
        // Then load sequences, re-using the PortAliases constructed for the jobs
        // TODO: this probably will NOT work for nested sequences?
        for (DataStageJob detailedSeq : seqList) {
            processes.addAll(getProcessesForSequence(detailedSeq, jobProcessByRid));
        }

        return processes;

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
        DataStageCache forComparison = new DataStageCache(from, to);
        if (dataStageCache == null || !dataStageCache.equals(forComparison)) {
            // Initialize the cache, if it is empty, or reset it if it differs from the dates and times we've been given
            dataStageCache = forComparison;
            dataStageCache.initialize(igcRestClient);
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
            ProcessMapping processMapping = new ProcessMapping(job, stage);
            Process process = processMapping.getProcess();
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
        Process process = null;
        if (!job.getType().equals(DataStageJob.JobType.SEQUENCE)) {
            log.debug("Load process for job...");
            ProcessMapping processMapping = new ProcessMapping(job);
            process = processMapping.getProcess();
            if (process != null) {
                try {
                    if (log.isDebugEnabled()) { log.debug(" ... process: {}", objectMapper.writeValueAsString(process)); }
                } catch (JsonProcessingException e) {
                    log.error("Unable to serialise to JSON: {}", process, e);
                }
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
     * @param jobProcessByRid a map from job RID to its detailed process definition
     * @return {@code List<Process>}
     */
    private List<Process> getProcessesForSequence(DataStageJob job, Map<String, Process> jobProcessByRid) {
        List<Process> processes = new ArrayList<>();
        if (job.getType().equals(DataStageJob.JobType.SEQUENCE)) {
            log.debug("Load process for sequence...");
            // Create a copy of the map, as the next step could update it by caching additional job details
            // necessary for the port aliases
            Map<String, Process> alreadyOutputProcesses = new HashMap<>(jobProcessByRid);
            ProcessMapping processMapping = new ProcessMapping(job, jobProcessByRid);
            Process process = processMapping.getProcess();
            if (process != null) {
                log.debug(" ... examining {} jobs run by the sequence", job.getAllStages().size());
                for (Stage stage : job.getAllStages()) {
                    Dsjob runsJob = stage.getRunsSequencesJobs();
                    String rid = runsJob.getId();
                    if (!alreadyOutputProcesses.containsKey(rid)) {
                        log.debug(" ...... found a job not already included in our changes: {}", rid);
                        // For any remaining, add them to the list of processes
                        Process sequencedProcess = jobProcessByRid.getOrDefault(rid, null);
                        if (sequencedProcess != null) {
                            try {
                                if (log.isDebugEnabled()) { log.debug(" ...... adding process: {}", objectMapper.writeValueAsString(sequencedProcess)); }
                            } catch (JsonProcessingException e) {
                                log.error("Unable to serialise to JSON: {}", sequencedProcess, e);
                            }
                            processes.add(sequencedProcess);
                            jobProcessByRid.put(rid, sequencedProcess);
                        } else {
                            log.error(" ... job was not already cached by port aliases, something went wrong: {}", rid);
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
        }
        return processes;
    }

    /**
     * Retrieve the rule that represents the job sync with Data Engine OMAS.
     *
     * @return InformationGovernanceRule
     */
    private InformationGovernanceRule getJobSyncRule() {
        IGCSearch igcSearch = new IGCSearch("information_governance_rule");
        igcSearch.addProperty(DataStageConstants.SHORT_DESCRIPTION);
        IGCSearchCondition condition = new IGCSearchCondition(DataStageConstants.NAME, "=", SYNC_RULE_NAME);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ItemList<InformationGovernanceRule> results = igcRestClient.search(igcSearch);
        return (results == null || results.getPaging().getNumTotal() == 0) ? null : results.getItems().get(0);
    }

    /**
     * Retrieve the oldest job (modified_on date furthest in the past) since the provided time.
     * @param time from which to consider changes
     * @return Dsjob
     */
    private Dsjob getOldestJobSince(Date time) {
        long startFrom = 0;
        if (time != null) {
            startFrom = time.getTime();
        }
        IGCSearch igcSearch = new IGCSearch("dsjob");
        igcSearch.addProperty("modified_on");
        IGCSearchCondition condition = new IGCSearchCondition("modified_on", ">", "" + startFrom);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        IGCSearchSorting sorting = new IGCSearchSorting("modified_on", true);
        igcSearch.addSortingCriteria(sorting);
        igcSearch.setPageSize(2); // No need to get any more than 2 results, as we will only take the top result anyway
        ItemList<Dsjob> results = igcRestClient.search(igcSearch);
        return (results == null || results.getPaging().getNumTotal() == 0) ? null : results.getItems().get(0);
    }

    /**
     * Throws a ConnectorCheckedException using the provided parameters.
     * @param errorCode the error code for the exception
     * @param methodName the name of the method throwing the exception
     * @param params any parameters for formatting the error message
     * @throws ConnectorCheckedException always
     */
    private void raiseConnectorCheckedException(DataStageErrorCode errorCode, String methodName, String ...params) throws ConnectorCheckedException {
        throw new ConnectorCheckedException(errorCode.getMessageDefinition(params),
                this.getClass().getName(),
                methodName);
    }

}
