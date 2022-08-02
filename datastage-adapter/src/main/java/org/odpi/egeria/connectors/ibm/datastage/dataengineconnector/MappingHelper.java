/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Dsjob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Stage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.DataFile;
import org.odpi.openmetadata.accessservices.dataengine.model.Database;
import org.odpi.openmetadata.accessservices.dataengine.model.DatabaseSchema;
import org.odpi.openmetadata.accessservices.dataengine.model.ParentProcess;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.odpi.openmetadata.accessservices.dataengine.model.ProcessHierarchy;
import org.odpi.openmetadata.accessservices.dataengine.model.Referenceable;
import org.odpi.openmetadata.accessservices.dataengine.model.RelationalTable;
import org.odpi.openmetadata.accessservices.dataengine.model.SchemaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  Utility class that helps to extract Data Engine OMAS beans from DataStageCache.
 *  It centralises mapping logic promoting reuse between different connector implementations.
 */
public class MappingHelper {

    private static final Logger log = LoggerFactory.getLogger(MappingHelper.class);

    public static final String DATABASE_TABLE = "database_table";
    public static final String DATA_FILE_RECORD = "data_file_record";

    private static final ObjectWriter objectWriter = new ObjectMapper().writer();

    public static  Map<String, SchemaType> mapChangedSchemaTypes(DataStageCache cache, boolean includeVirtualAssets, boolean createDataStoreSchemas) throws IGCException {
        Map<String, SchemaType> schemaTypeMap = new HashMap<>();
        // Iterate through each job looking for any virtual assets -- these must be created first
        for (DataStageJob job : cache.getAllJobs()) {
            for (String storeRid : job.getStoreRids()) {
                log.debug(" ... considering store: {}", storeRid);
                if (!schemaTypeMap.containsKey(storeRid)) {
                    if ((IGCRestClient.isVirtualAssetRid(storeRid) && includeVirtualAssets)
                            || (!IGCRestClient.isVirtualAssetRid(storeRid) && createDataStoreSchemas)) {
                        log.debug(" ... Creating a SchemaType ...");
                        SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache);
                        SchemaType deSchemaType = schemaTypeMapping.getForDataStore(cache.getStoreIdentityFromRid(storeRid));
                        logEntityCreated(deSchemaType);
                        schemaTypeMap.put(storeRid, deSchemaType);
                    }
                }
            }
        }
        return schemaTypeMap;
    }

    public static Map<String, ? super Referenceable> mapChangedDataStores(DataStageCache cache, boolean includeVirtualAssets) throws  IGCException {
        Map<String, ? super Referenceable> dataStoreMap = new HashMap<>();
        // Iterate through each job looking for any virtual assets -- these must be created first
        for (DataStageJob job : cache.getAllJobs()) {
            for (String storeRid : job.getStoreRids()) {
                log.debug(" ... considering store: {}", storeRid);
                if (!dataStoreMap.containsKey(storeRid)) {
                    if ((IGCRestClient.isVirtualAssetRid(storeRid) && includeVirtualAssets)) {
                        Identity storeIdentity = cache.getStoreIdentityFromRid(storeRid);
                        Identity parentIdentity = storeIdentity.getParentIdentity();
                        String type = storeIdentity.getAssetType();
                        switch (type) {
                            case DATABASE_TABLE:
                                Identity databaseLevelIdentity = parentIdentity.getParentIdentity();
                                log.debug(" ... Creating a Database ...");
                                DatabaseMapping databaseMapping = new DatabaseMapping(cache);
                                Database database = databaseMapping.getForDataStore(databaseLevelIdentity);
                                database.setIncomplete(IGCRestClient.isVirtualAssetRid(databaseLevelIdentity.getRid()));
                                logEntityCreated(database);

                                log.debug(" ... Creating a DatabaseSchema ...");
                                DatabaseSchemaMapping databaseSchemaMapping = new DatabaseSchemaMapping(cache);
                                DatabaseSchema dbSchema = databaseSchemaMapping.getForDataStore(parentIdentity);
                                dbSchema.setIncomplete(IGCRestClient.isVirtualAssetRid(parentIdentity.getRid()));
                                logEntityCreated(dbSchema);

                                log.debug(" ... Creating a RelationalTable ...");
                                RelationalTableMapping relationalTableMapping = new RelationalTableMapping(cache);
                                RelationalTable table = relationalTableMapping.getForDataStore(storeIdentity);
                                table.setIncomplete(true);
                                logEntityCreated(table);

                                database.setDatabaseSchema(dbSchema);
                                database.setTables(Collections.singletonList(table));
                                dataStoreMap.put(storeRid, database);
                                break;
                            case DATA_FILE_RECORD:
                                log.debug(" ... Creating a SchemaType ...");
                                SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache);
                                SchemaType schemaType = schemaTypeMapping.getForDataStore(storeIdentity);
                                logEntityCreated(schemaType);

                                log.debug(" ... Creating a File ...");
                                DataFileMapping dataFileMapping = new DataFileMapping(cache);
                                DataFile dataFile = dataFileMapping.getForDataStore(parentIdentity);
                                dataFile.setSchema(schemaType);
                                dataFile.setIncomplete(true);
                                logEntityCreated(dataFile);

                                dataStoreMap.put(storeRid, dataFile);
                                break;
                        }
                    }
                }
            }
        }
        return dataStoreMap;
    }

    public static List<Process>  mapChangedProcesses(DataStageCache cache, List<ProcessHierarchy> processHierarchies, LineageMode mode) throws IGCException {
        List<Process> processes = new ArrayList<>();
        List<DataStageJob> seqList = new ArrayList<>();
        // Translate changed jobs first, to build up appropriate PortAliases list
        for (DataStageJob detailedJob : cache.getAllJobs()) {
            if (detailedJob.getType().equals(DataStageJob.JobType.SEQUENCE)) {
                seqList.add(detailedJob);
            } else {
                if (mode == LineageMode.GRANULAR) {
                    // Only translate stage-level details for granular mode
                    List<Process> stageLevelProcesses = getProcessesForEachStage(cache, detailedJob);
                    for (Process stageLevel : stageLevelProcesses) {
                        cacheHierarchyRelationshipsFromProcessDetails(processHierarchies, stageLevel);
                        processes.add(stageLevel);
                    }

                    // Then load sequences, re-using the PortAliases constructed for the jobs
                    // TODO: this probably will NOT work for nested sequences?
                    for (DataStageJob detailedSeq : seqList) {
                        List<Process> sequencedJobs = getProcessesForSequence(cache, detailedSeq);
                        for (Process sequenced : sequencedJobs) {
                            cacheHierarchyRelationshipsFromProcessDetails(processHierarchies, sequenced);
                            processes.add(sequenced);
                        }
                    }
                }
                Process jobProcess = getProcessForJob(cache, detailedJob);
                if (jobProcess != null) {
                    cacheHierarchyRelationshipsFromProcessDetails(processHierarchies, jobProcess);
                    processes.add(jobProcess);
                }
            }
        }
        return processes;
    }

    /**
     * Translate the detailed stages of the provided DataStage job into Processes.
     *
     * @param job the job for which to translate detailed stages
     * @return {@code List<Process>}
     */
    private static List<Process> getProcessesForEachStage(DataStageCache cache, DataStageJob job) throws IGCException {
        List<Process> processes = new ArrayList<>();
        log.debug("Translating processes for each stage...");
        for (Stage stage : job.getAllStages()) {
            ProcessMapping processMapping = new ProcessMapping(cache);
            Process process = processMapping.getForStage(stage, job);
            if (process != null) {
                try {
                    log.debug(" ... process: {}", objectWriter.writeValueAsString(process));
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
    private static Process getProcessForJob(DataStageCache cache, DataStageJob job) throws IGCException {
//        log.debug("Load process for job...");
        Process process = cache.getProcessByRid(job.getJobObject().getId());
        if (process != null) {
/*
            if (mode == LineageMode.JOB_LEVEL) {
                // TODO: fill in the LineageMapping at this process level
            }
*/
            try {
                log.debug(" ... process: {}", objectWriter.writeValueAsString(process));
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
    private static List<Process> getProcessesForSequence(DataStageCache cache, DataStageJob job) throws IGCException {
        log.debug("Load process for sequence...");
        List<Process> processes = new ArrayList<>();
        // Create a copy of the map, as the next step could update it by caching additional job details
        // necessary for the port aliases
        Set<String> alreadyOutputProcesses = cache.getCachedProcessRids();
        Process process = cache.getProcessByRid(job.getJobObject().getId());
        if (process != null) {
            log.debug(" ... examining {} jobs run by the sequence", job.getAllStages().size());
            for (Stage stage : job.getAllStages()) {
                Dsjob runsJob = stage.getRunsSequencesJobs();
                String rid = runsJob.getId();
                if (rid != null && !alreadyOutputProcesses.contains(rid)) {
                    log.debug(" ...... found a job not already included in our changes: {}", rid);
                    // For any remaining, add them to the list of processes
                    Process sequencedProcess = cache.getProcessByRid(rid);
                    if (sequencedProcess != null) {
                        try {
                            if (log.isDebugEnabled()) { log.debug(" ...... adding process: {}", objectWriter.writeValueAsString(sequencedProcess)); }
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
                if (log.isDebugEnabled()) { log.debug(" ... process: {}", objectWriter.writeValueAsString(process)); }
            } catch (JsonProcessingException e) {
                log.error("Unable to serialise to JSON: {}", process, e);
            }
        }
        return processes;
    }

    private static void cacheHierarchyRelationshipsFromProcessDetails(List<ProcessHierarchy> processHierarchies, Process process) {
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

    private static void logEntityCreated(Referenceable table) {
        if (log.isDebugEnabled()) {
            try {
                log.debug(" ... created: {}", objectWriter.writeValueAsString(table));
            } catch (JsonProcessingException e) {
                log.error("Unable to serialise to JSON: {}", table, e);
            }
        }
    }

}
