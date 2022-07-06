/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.DataFileMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.DatabaseMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.DatabaseSchemaMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.ProcessMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.RelationalTableMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.SchemaTypeMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.LineageMode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Dsjob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Stage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.DataFile;
import org.odpi.openmetadata.accessservices.dataengine.model.Database;
import org.odpi.openmetadata.accessservices.dataengine.model.DatabaseSchema;
import org.odpi.openmetadata.accessservices.dataengine.model.LineageMapping;
import org.odpi.openmetadata.accessservices.dataengine.model.ParentProcess;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.odpi.openmetadata.accessservices.dataengine.model.ProcessHierarchy;
import org.odpi.openmetadata.accessservices.dataengine.model.Referenceable;
import org.odpi.openmetadata.accessservices.dataengine.model.RelationalTable;
import org.odpi.openmetadata.accessservices.dataengine.model.SchemaType;
import org.odpi.openmetadata.accessservices.dataengine.model.SoftwareServerCapability;
import org.odpi.openmetadata.frameworks.auditlog.messagesets.ExceptionMessageDefinition;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.odpi.openmetadata.frameworks.connectors.properties.EndpointProperties;
import org.odpi.openmetadata.governanceservers.dataengineproxy.connectors.DataEngineConnectorBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataStageReportsConnector extends DataEngineConnectorBase {

    private static final Logger log = LoggerFactory.getLogger(DataStageReportsConnector.class);

    public static final String DATABASE_TABLE = "database_table";
    public static final String DATA_FILE_RECORD = "data_file_record";

    private IGCRestClient igcRestClient;
    private ObjectMapper objectMapper;
    private SoftwareServerCapability dataEngine;

    private DataStageCache dataStageCache;
    private List<ProcessHierarchy> processHierarchies;

    private boolean includeVirtualAssets = true;
    private boolean createDataStoreSchemas = false;
    private boolean detectLineage = false;
    private final List<String> limitToReportRids = new ArrayList<>();
    private boolean cacheInitialized;

    private LineageMode mode = LineageMode.GRANULAR;

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
            raiseConnectorCheckedException(this.getClass(), methodName, null, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition());
        } else {
            String address = endpointProperties.getAddress();
            if (address == null || address.length() == 0) {
                raiseConnectorCheckedException(this.getClass(), methodName, null, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition(address));
            } else {

                String igcUser = connectionProperties.getUserId();
                String igcPass = connectionProperties.getClearPassword();

                Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
                Integer igcPage = null;
                if (proxyProperties != null) {
                    igcPage = (Integer) proxyProperties.get(DataStageConnectorProvider.PAGE_SIZE);
                    includeVirtualAssets = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.INCLUDE_VIRTUAL_ASSETS, true);
                    createDataStoreSchemas = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.CREATE_DATA_STORE_SCHEMAS, false);
                    detectLineage = (Boolean) proxyProperties.getOrDefault(DataStageReportsConnectorProvider.DETECT_LINEAGE, false);

                    Object reportRids = proxyProperties.getOrDefault(DataStageReportsConnectorProvider.LIMIT_TO_REPORT_RIDS, null);
                    if (reportRids instanceof String) {
                        limitToReportRids.add((String)reportRids);
                    } else if (reportRids != null) {
                        limitToReportRids.addAll((List<String>)reportRids);
                    }
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
                        raiseConnectorCheckedException(this.getClass(), methodName, null, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition(address));
                    }
                } catch (IGCException e) {
                    raiseConnectorCheckedException(this.getClass(), methodName, e, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition(address));
                }

            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void disconnect() throws ConnectorCheckedException {
        final String methodName = "disconnect";
        try {
            // Close the session on the IGC REST client
            this.igcRestClient.disconnect();
        } catch (IGCException e) {
            raiseConnectorCheckedException(this.getClass(), methodName, e, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition());
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
    public List<SchemaType> getChangedSchemaTypes(Date from, Date to) throws ConnectorCheckedException, PropertyServerException {

        final String methodName = "getChangedSchemaTypes";
        log.debug("Looking for changed SchemaTypes...");
        Map<String, SchemaType> schemaTypeMap = new HashMap<>();

        if (mode == LineageMode.JOB_LEVEL) {
            return Collections.emptyList();
        }

        try {
            initializeCache(from, to);
            // Iterate through each job looking for any virtual assets -- these must be created first
            for (DataStageJob job : dataStageCache.getAllJobs()) {
                for (String storeRid : job.getStoreRids()) {
                    log.debug(" ... considering store: {}", storeRid);
                    if (!schemaTypeMap.containsKey(storeRid)) {
                        if ((IGCRestClient.isVirtualAssetRid(storeRid) && includeVirtualAssets)
                                || (!IGCRestClient.isVirtualAssetRid(storeRid) && createDataStoreSchemas)) {
                            log.debug(" ... Creating a SchemaType ...");
                            SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(dataStageCache);
                            SchemaType deSchemaType = schemaTypeMapping.getForDataStore(dataStageCache.getStoreIdentityFromRid(storeRid));
                            logEntityCreated(deSchemaType);
                            schemaTypeMap.put(storeRid, deSchemaType);
                        }
                    }
                }
            }
        } catch (IGCException e) {
            handleIGCException(this.getClass().getName(), methodName, e);
        }
        return new ArrayList<>(schemaTypeMap.values());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<? super Referenceable> getChangedDataStores(Date from, Date to) throws ConnectorCheckedException, PropertyServerException {

        final String methodName = "getChangedDataStores";
        log.debug("Looking for changed DataStores...");
        Map<String, ? super Referenceable> dataStoreMap = new HashMap<>();

        try {

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
                                    Identity databaseLevelIdentity = parentIdentity.getParentIdentity();
                                    log.debug(" ... Creating a Database ...");
                                    DatabaseMapping databaseMapping = new DatabaseMapping(dataStageCache);
                                    Database database = databaseMapping.getForDataStore(databaseLevelIdentity);
                                    logEntityCreated(database);

                                    log.debug(" ... Creating a DatabaseSchema ...");
                                    DatabaseSchemaMapping databaseSchemaMapping = new DatabaseSchemaMapping(dataStageCache);
                                    DatabaseSchema dbSchema = databaseSchemaMapping.getForDataStore(parentIdentity);
                                    logEntityCreated(dbSchema);

                                    log.debug(" ... Creating a RelationalTable ...");
                                    RelationalTableMapping relationalTableMapping = new RelationalTableMapping(dataStageCache);
                                    RelationalTable table = relationalTableMapping.getForDataStore(storeIdentity);
                                    logEntityCreated(table);

                                    database.setDatabaseSchema(dbSchema);
                                    database.setTables(Collections.singletonList(table));
                                    dataStoreMap.put(storeRid, database);
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

        } catch (IGCException e) {
            handleIGCException(this.getClass().getName(), methodName, e);
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

        final String methodName = "getChangedProcesses";
        List<Process> processes = new ArrayList<>();
        List<DataStageJob> seqList = new ArrayList<>();

        try {
            initializeCache(from, to);
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
        } catch (IGCException e) {
            handleIGCException(this.getClass().getName(), methodName, e);
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
    private void initializeCache(Date from, Date to) throws IGCException {
        if(!cacheInitialized) {
            this.dataStageCache = new DataStageCache(from, to, mode, new ArrayList<>(), new ArrayList<>(), false);
            dataStageCache.initializeWithReportJobs(igcRestClient, limitToReportRids, detectLineage);
            processHierarchies = new ArrayList<>();
            cacheInitialized = true;
        }
    }

    /**
     * Translate the detailed stages of the provided DataStage job into Processes.
     *
     * @param job the job for which to translate detailed stages
     * @return {@code List<Process>}
     */
    private List<Process> getProcessesForEachStage(DataStageJob job) throws IGCException {
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
    private Process getProcessForJob(DataStageJob job) throws IGCException {
        log.debug("Load process for job...");
        Process process = dataStageCache.getProcessByRid(job.getJobObject().getId());
        if (process != null) {
            if (mode == LineageMode.JOB_LEVEL) {
                // TODO: fill in the LineageMapping at this process level
            }
            try {
                log.debug(" ... process: {}", objectMapper.writeValueAsString(process));
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
    private List<Process> getProcessesForSequence(DataStageJob job) throws IGCException {
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
     * Throws a ConnectorCheckedException using the provided parameters.
     * @param clazz
     * @param cause
     * @param exceptionMessageDefinition
     * @throws ConnectorCheckedException always
     */
    private void raiseConnectorCheckedException(Class clazz, String methodName, Exception cause, ExceptionMessageDefinition exceptionMessageDefinition) throws ConnectorCheckedException {
        if(cause == null) {
            throw new ConnectorCheckedException(exceptionMessageDefinition,
                    clazz.getName(),
                    methodName);
        } else {
            throw new ConnectorCheckedException(exceptionMessageDefinition,
                    clazz.getName(),
                    methodName,
                    cause);
        }
    }

    /**
     * Helper method that takes details of where the original IGCException is caught, maps and rethrows it further as one of the OCF checked exceptions.
     *
     * @param className The name of the class where original IGCException is caught
     * @param methodName The name of the method where original IGCException is caught
     * @param e Instance of IGCException (or subclasses)
     * @throws ConnectorCheckedException
     * @throws PropertyServerException
     */
    public static void handleIGCException(String className, String methodName, IGCException e) throws ConnectorCheckedException, PropertyServerException {
        if(e instanceof IGCConnectivityException) {
            throw new PropertyServerException(DataStageErrorCode.CONNECTION_ERROR.getMessageDefinition(),
                    className,
                    methodName,
                    e);
        } else {
            throw new ConnectorCheckedException(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR.getMessageDefinition(),
                    className,
                    methodName,
                    e);
        }
    }
}
