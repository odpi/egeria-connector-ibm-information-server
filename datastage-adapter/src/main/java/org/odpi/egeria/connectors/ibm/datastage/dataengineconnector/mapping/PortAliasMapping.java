/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnector;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Dsjob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Stage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
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

    /**
     * Default constructor to pass in the cache for re-use.
     *
     * @param cache used by this mapping
     */
    PortAliasMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Create a list of PortAliases from the provided job and stage information.
     *
     * @param stages the stages from which to create PortAliases
     * @param portType the type of port to map (input or output)
     * @return {@code List<PortAlias>}
     */
    List<PortAlias> getForStages(List<Stage> stages, PortType portType) {
        Set<PortAlias> portAliases = new HashSet<>();
        for (Stage stage : stages) {
            if (portType.equals(PortType.INPUT_PORT)) {
                addInputPortAliases(portAliases, stage);
            } else if (portType.equals(PortType.OUTPUT_PORT)) {
                addOutputPortAliases(portAliases, stage);
            }
        }
        return new ArrayList<>(portAliases);
    }

    /**
     * Create a list of PortAliases from the provided sequence job.
     *
     * @param sequence the sequence for which to create PortAliases
     * @return {@code List<PortAlias>}
     */
    List<PortAlias> getForSequence(DataStageJob sequence) {
        Set<PortAlias> portAliases = new HashSet<>();
        final String methodName = "PortAliasMapping";
        if (sequence.getType().equals(DataStageJob.JobType.SEQUENCE)) {
            for (Stage stage : sequence.getAllStages()) {
                Dsjob runsJob = stage.getRunsSequencesJobs();
                if (runsJob != null) {
                    String jobId = runsJob.getId();
                    if (jobId != null) {
                        Process jobProcess = cache.getProcessByRid(jobId);

                        if (jobProcess != null) {
                            List<PortAlias> jobPortAliases = jobProcess.getPortAliases();
                            // Create a new PortAlias at the sequence level, for each underlying PortAlias of jobs
                            // that are executed, that delegateTo the underlying job's PortAlias
                            try {
                                for (PortAlias delegateTo : jobPortAliases) {
                                    String stageQN = getFullyQualifiedName(stage, delegateTo.getQualifiedName());
                                    if (stageQN != null) {
                                        PortAlias sequencePortAlias = getSkeletonPortAlias(stageQN, stage.getName() + "_" + delegateTo.getDisplayName());
                                        sequencePortAlias.setPortType(delegateTo.getPortType());
                                        sequencePortAlias.setDelegatesTo(delegateTo.getQualifiedName());
                                        portAliases.add(sequencePortAlias);
                                    } else {
                                        log.error("Unable to determine identity for stage -- not including: {}", stage);
                                    }
                                }
                            } catch (IGCException e) {
                                DataStageConnector.propagateIgcRestClientException(this.getClass().getName(), methodName, e);
                            }
                        } else {
                            log.warn("Unable to find existing process to use for alias: {}", jobId);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(portAliases);
    }

    private void addInputPortAliases(Set<PortAlias> portAliases, Stage stage) {
        addPortAliases(portAliases, stage, "reads_from_(design)", stage.getReadsFromDesign(), PortType.INPUT_PORT);
    }

    private void addOutputPortAliases(Set<PortAlias> portAliases, Stage stage) {
        addPortAliases(portAliases, stage, "writes_to_(design)", stage.getWritesToDesign(), PortType.OUTPUT_PORT);
    }

    private void addPortAliases(Set<PortAlias> portAliases, Stage stage, String propertyName, ItemList<InformationAsset> relations, PortType portType) {
        final String methodName = "addPortAliases";
        try {
            List<InformationAsset> allRelations = igcRestClient.getAllPages(propertyName, relations);
            int index = 0;
            for (InformationAsset relation : allRelations) {
                index++;
                Identity storeIdentity = cache.getStoreIdentityFromRid(relation.getId());
                String fullyQualifiedStageName = getFullyQualifiedName(stage);
                // Use the index to at least make each PortAlias qualifiedName for this stage unique (without risking
                // overlapping with the PortImplementation qualifiedNames that we need to delegateTo)
                PortAlias portAlias = getSkeletonPortAlias(fullyQualifiedStageName + "_" + index, stage.getName());
                portAlias.setPortType(portType);
                portAlias.setDelegatesTo(getFullyQualifiedName(storeIdentity, fullyQualifiedStageName));
                portAliases.add(portAlias);
            }
        } catch (IGCException e) {
            DataStageConnector.propagateIgcRestClientException(this.getClass().getName(), methodName, e);
        }
    }

    private PortAlias getSkeletonPortAlias(String qualifiedName, String displayName) {
        PortAlias portAlias = new PortAlias();
        portAlias.setQualifiedName(qualifiedName);
        portAlias.setDisplayName(displayName);
        return portAlias;
    }

}
