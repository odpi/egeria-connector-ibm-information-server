/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.openmetadata.accessservices.dataengine.model.*;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mappings for creating a Process.
 */
public class ProcessMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(ProcessMapping.class);

    private Process process;

    /**
     * Create a new process from a DataStageJob.
     *
     * @param job the DataStageJob from which to create a process
     */
    public ProcessMapping(DataStageJob job) {
        super(job.getIgcRestClient());
        process = null;
        if (job.getType().equals(DataStageJob.JobType.JOB)) {
            Dsjob jobObj = job.getJobObject();
            process = getSkeletonProcess(jobObj);
            if (process != null) {
                PortAliasMapping inputAliasMapping = new PortAliasMapping(job, job.getInputStages(), PortType.INPUT_PORT);
                PortAliasMapping outputAliasMapping = new PortAliasMapping(job, job.getOutputStages(), PortType.OUTPUT_PORT);
                process.setPortAliases(Stream.concat(inputAliasMapping.getPortAliases().stream(), outputAliasMapping.getPortAliases().stream()).collect(Collectors.toList()));
                Set<LineageMapping> lineageMappings = new HashSet<>();
                for (Link link : job.getAllLinks()) {
                    LineageMappingMapping lineageMappingMapping = new LineageMappingMapping(job, link);
                    Set<LineageMapping> crossStageLineageMappings = lineageMappingMapping.getLineageMappings();
                    if (crossStageLineageMappings != null && !crossStageLineageMappings.isEmpty()) {
                        lineageMappings.addAll(crossStageLineageMappings);
                    }
                }
                if (!lineageMappings.isEmpty()) {
                    process.setLineageMappings(new ArrayList<>(lineageMappings));
                }
            }
        } else {
            log.error("Unable to create a job mapping for a sequence: {}", job);
        }
    }

    /**
     * Create a new process from a DataStageJob that represents a sequence.
     *
     * @param sequence the sequence from which to create a process
     * @param jobProcessByRid a map from job RID to the full Process for that job
     */
    public ProcessMapping(DataStageJob sequence, Map<String, Process> jobProcessByRid) {
        super(sequence.getIgcRestClient());
        process = null;
        if (sequence.getType().equals(DataStageJob.JobType.SEQUENCE)) {
            Dsjob jobObj = sequence.getJobObject();
            process = getSkeletonProcess(jobObj);
            if (process != null) {
                Set<PortAlias> portAliases = new HashSet<>();
                for (Stage stage : sequence.getAllStages()) {
                    Dsjob runsJob = stage.getRunsSequencesJobs();
                    if (runsJob != null) {
                        String jobId = runsJob.getId();
                        if (jobId != null) {
                            Process jobProcess = jobProcessByRid.getOrDefault(jobId, null);
                            if (jobProcess != null) {
                                portAliases.addAll(jobProcess.getPortAliases());
                            } else {
                                log.warn("Unable to find existing process to use for alias: {}", jobId);
                            }
                        }
                    }
                }
                process.setPortAliases(new ArrayList<>(portAliases));
            }
        } else {
            log.error("Unable to create a sequence mapping for a job: {}", sequence);
        }
    }

    /**
     * Create a new process from the provided DataStage stage.
     *
     * @param job the job within which the stage exists
     * @param stage the stage from which to create a process
     */
    public ProcessMapping(DataStageJob job, Stage stage) {
        super(job.getIgcRestClient());
        process = getSkeletonProcess(stage);
        if (process != null) {
            Set<PortImplementation> portImplementations = new HashSet<>();
            Set<LineageMapping> lineageMappings = new HashSet<>();
            addImplementationDetails(job, stage, "input_links", stage.getInputLinks(), PortType.INPUT_PORT, portImplementations, lineageMappings);
            addDataStoreDetails(job, stage, "reads_from_(design)", stage.getReadsFromDesign(), PortType.INPUT_PORT, portImplementations, lineageMappings);
            addImplementationDetails(job, stage, "output_links", stage.getOutputLinks(), PortType.OUTPUT_PORT, portImplementations, lineageMappings);
            addDataStoreDetails(job, stage, "writes_to_(design)", stage.getWritesToDesign(), PortType.OUTPUT_PORT, portImplementations, lineageMappings);
            process.setPortImplementations(new ArrayList<>(portImplementations));
            process.setLineageMappings(new ArrayList<>(lineageMappings));
        }
    }

    /**
     * Retrieve the mapped process.
     *
     * @return Process
     */
    public Process getProcess() { return this.process; }

    /**
     * Construct a minimal Process object from the provided IGC object (stage, job, sequence, etc).
     *
     * @param igcObj the IGC object from which to construct the skeletal Process
     * @return Process
     */
    private Process getSkeletonProcess(InformationAsset igcObj) {
        Process process = null;
        if (igcObj != null) {
            process = new Process();
            process.setName(igcObj.getName());
            process.setDisplayName(igcObj.getName());
            process.setQualifiedName(getFullyQualifiedName(igcObj));
            process.setDescription(getDescription(igcObj));
            process.setOwner(igcObj.getCreatedBy());
            // TODO: setAdditionalProperties or setExtendedProperties with other information on the IGC object?
        }
        return process;
    }

    /**
     * Add implementation details of the job (ports and lineage mappings) to the provided lists, for the provided stage.
     *
     * @param job the job within which the stage exists
     * @param stage the stage for which to add implementation details
     * @param propertyName the name of the property from which links were retrieved
     * @param links the links of the stage from which to draw implementation details
     * @param portType the type of port
     * @param portImplementations the list of PortImplementations to append to with implementation details
     * @param lineageMappings the list of LineageMappings to append to with implementation details
     */
    private void addImplementationDetails(DataStageJob job,
                                          Stage stage,
                                          String propertyName,
                                          ItemList<Link> links,
                                          PortType portType,
                                          Set<PortImplementation> portImplementations,
                                          Set<LineageMapping> lineageMappings) {
        String stageNameSuffix = "_" + stage.getName();
        // Setup an x_PORT for each x_link into / out of the stage
        List<Link> allLinks = igcRestClient.getAllPages(propertyName, links);
        for (Link linkRef : allLinks) {
            Link linkObjFull = job.getLinkByRid(linkRef.getId());
            PortImplementationMapping portImplementationMapping = new PortImplementationMapping(job, linkObjFull, portType, stageNameSuffix);
            portImplementations.add(portImplementationMapping.getPortImplementation());
            LineageMappingMapping lineageMappingMapping = new LineageMappingMapping(job, linkObjFull, stageNameSuffix, portType == PortType.INPUT_PORT);
            lineageMappings.addAll(lineageMappingMapping.getLineageMappings());
        }
    }

    /**
     * Add implementation details of the job (ports and lineage mappings) to the provided lists, for any data stores
     * used by the specified stage.
     *
     * @param job the job within which the stage exists
     * @param stage the stage for which to add implementation details
     * @param propertyName the name of the property from which stores were retrieved
     * @param stores the stores for which to create the implementation details
     * @param portType the type of port
     * @param portImplementations the list of PortImplementations to append to with implementation details
     * @param lineageMappings the list of LineageMappings to append to with implementation details
     */
    private void addDataStoreDetails(DataStageJob job,
                                     Stage stage,
                                     String propertyName,
                                     ItemList<InformationAsset> stores,
                                     PortType portType,
                                     Set<PortImplementation> portImplementations,
                                     Set<LineageMapping> lineageMappings) {
        String stageNameSuffix = "_" + stage.getName();
        // Setup an x_PORT for any data stores that are used by design as sources / targets
        String fullyQualifiedStageName = getFullyQualifiedName(stage);
        List<InformationAsset> allStores = igcRestClient.getAllPages(propertyName, stores);
        for (InformationAsset storeRef : allStores) {
            List<Classificationenabledgroup> fieldsForStore = job.getFieldsForStore(storeRef.getId());
            PortImplementationMapping portImplementationMapping = new PortImplementationMapping(job, stage, portType, fieldsForStore, fullyQualifiedStageName);
            portImplementations.add(portImplementationMapping.getPortImplementation());
            LineageMappingMapping lineageMappingMapping = new LineageMappingMapping(job, fieldsForStore, portType == PortType.INPUT_PORT, fullyQualifiedStageName, stageNameSuffix);
            lineageMappings.addAll(lineageMappingMapping.getLineageMappings());
        }
    }

}
