/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Dsjob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Stage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.openmetadata.accessservices.dataengine.model.PortAlias;
import org.odpi.openmetadata.accessservices.dataengine.model.PortType;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Mappings for creating a set of PortAliases.
 */
class PortAliasMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(PortAliasMapping.class);

    private Set<PortAlias> portAliases;

    private PortAliasMapping(IGCRestClient igcRestClient) {
        super(igcRestClient);
        portAliases = new HashSet<>();
    }

    /**
     * Create a list of PortAliases from the provided job and stage information.
     *
     * @param job the job for which to create PortAliases
     * @param stages the stages from which to create PortAliases
     * @param portType the type of port to map (input or output)
     */
    PortAliasMapping(DataStageJob job, List<Stage> stages, PortType portType) {

        this(job.getIgcRestClient());

        for (Stage stage : stages) {
            if (portType.equals(PortType.INPUT_PORT)) {
                addInputPortAliases(job, stage);
            } else if (portType.equals(PortType.OUTPUT_PORT)) {
                addOutputPortAliases(job, stage);
            }
        }

    }

    /**
     * Create a list of PortAliases from the provided sequence job.
     *
     * @param sequence the sequence for which to create PortAliases
     * @param jobProcessByRid a map from job RID to the full Process for that job
     */
    PortAliasMapping(DataStageJob sequence, Map<String, Process> jobProcessByRid) {

        this(sequence.getIgcRestClient());

        if (sequence.getType().equals(DataStageJob.JobType.SEQUENCE)) {
            for (Stage stage : sequence.getAllStages()) {
                Dsjob runsJob = stage.getRunsSequencesJobs();
                if (runsJob != null) {
                    String jobId = runsJob.getId();
                    if (jobId != null) {
                        Process jobProcess = jobProcessByRid.getOrDefault(jobId, null);
                        if (jobProcess == null) {
                            // If it was not in our cache (eg. because it has not changed), we need to retrieve it
                            // and add it to the cache
                            DataStageJob lookup = DataStageJob.lookupJobByRid(igcRestClient, jobId);
                            if (lookup != null) {
                                DataStageJob.JobType type = lookup.getType();
                                ProcessMapping processMapping;
                                if (type.equals(DataStageJob.JobType.JOB)) {
                                    processMapping = new ProcessMapping(lookup);
                                } else {
                                    processMapping = new ProcessMapping(lookup, jobProcessByRid);
                                }
                                jobProcess = processMapping.getProcess();
                                if (jobProcess != null) {
                                    jobProcessByRid.put(jobId, jobProcess);
                                }
                            }
                        }
                        if (jobProcess != null) {
                            List<PortAlias> jobPortAliases = jobProcess.getPortAliases();
                            // Create a new PortAlias at the sequence level, for each underlying PortAlias of jobs
                            // that are executed, that delegateTo the underlying job's PortAlias
                            for (PortAlias delegateTo : jobPortAliases) {
                                PortAlias sequencePortAlias = getSkeletonPortAlias(getFullyQualifiedName(stage) + "::" + delegateTo.getQualifiedName(), stage.getName() + "_" + delegateTo.getDisplayName());
                                sequencePortAlias.setPortType(delegateTo.getPortType());
                                sequencePortAlias.setDelegatesTo(delegateTo.getQualifiedName());
                                portAliases.add(sequencePortAlias);
                            }
                        } else {
                            log.warn("Unable to find existing process to use for alias: {}", jobId);
                        }
                    }
                }
            }
        }

    }

    /**
     * Retrieve the PortAliases that were setup.
     *
     * @return {@code List<PortAlias>}
     */
    List<PortAlias> getPortAliases() { return new ArrayList<>(portAliases); }

    private void addInputPortAliases(DataStageJob job, Stage stage) {
        addPortAliases(job, stage, "reads_from_(design)", stage.getReadsFromDesign(), PortType.INPUT_PORT);
    }

    private void addOutputPortAliases(DataStageJob job, Stage stage) {
        addPortAliases(job, stage, "writes_to_(design)", stage.getWritesToDesign(), PortType.OUTPUT_PORT);
    }

    private void addPortAliases(DataStageJob job, Stage stage, String propertyName, ItemList<InformationAsset> relations, PortType portType) {
        List<InformationAsset> allRelations = igcRestClient.getAllPages(propertyName, relations);
        for (InformationAsset relation : allRelations) {
            String fullyQualifiedStoreName = job.getQualifiedNameFromStoreRid(relation.getId());
            String fullyQualifiedStageName = getFullyQualifiedName(stage);
            PortAlias portAlias = getSkeletonPortAlias(fullyQualifiedStageName, stage.getName());
            portAlias.setPortType(portType);
            portAlias.setDelegatesTo(fullyQualifiedStoreName + fullyQualifiedStageName);
            portAliases.add(portAlias);
        }
    }

    private PortAlias getSkeletonPortAlias(String qualifiedName, String displayName) {
        PortAlias portAlias = new PortAlias();
        portAlias.setQualifiedName(qualifiedName);
        portAlias.setDisplayName(displayName);
        return portAlias;
    }

}
