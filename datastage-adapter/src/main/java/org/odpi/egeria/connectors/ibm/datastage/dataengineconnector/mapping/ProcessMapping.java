/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DSJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
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
     * Create a new process from a DSJob.
     *
     * @param job the DSJob from which to create a process
     */
    public ProcessMapping(DSJob job) {
        super(job.getIgcRestClient());
        process = null;
        if (job.getType() == DSJob.JobType.JOB) {
            Reference jobObj = job.getJobObject();
            process = getSkeletonProcess(jobObj);
            if (process != null) {
                PortAliasMapping inputAliasMapping = new PortAliasMapping(job, job.getInputStages(), "reads_from_(design)");
                PortAliasMapping outputAliasMapping = new PortAliasMapping(job, job.getOutputStages(), "writes_to_(design)");
                process.setPortAliases(Stream.concat(inputAliasMapping.getPortAliases().stream(), outputAliasMapping.getPortAliases().stream()).collect(Collectors.toList()));
            }
        } else {
            log.error("Unable to create a job mapping for a sequence: {}", job);
        }
    }

    /**
     * Create a new process from a DSJob that represents a sequence.
     *
     * @param sequence the sequence from which to create a process
     * @param jobProcessByRid a map from job RID to the full Process for that job
     */
    public ProcessMapping(DSJob sequence, Map<String, Process> jobProcessByRid) {
        super(sequence.getIgcRestClient());
        process = null;
        if (sequence.getType() == DSJob.JobType.SEQUENCE) {
            Reference jobObj = sequence.getJobObject();
            process = getSkeletonProcess(jobObj);
            if (process != null) {
                Set<PortAlias> portAliases = new HashSet<>();
                for (Reference stage : sequence.getAllStages()) {
                    Reference runsJob = (Reference) igcRestClient.getPropertyByName(stage, "runs_sequences_jobs");
                    if (runsJob != null) {
                        String jobId = runsJob.getId();
                        Process jobProcess = jobProcessByRid.getOrDefault(jobId, null);
                        if (jobProcess != null) {
                            portAliases.addAll(jobProcess.getPortAliases());
                        }
                    }
                }
                process.setPortAliases(portAliases.stream().collect(Collectors.toList()));
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
    public ProcessMapping(DSJob job, Reference stage) {
        super(job.getIgcRestClient());
        process = getSkeletonProcess(stage);
        if (process != null) {
            List<PortImplementation> portImplementations = new ArrayList<>();
            List<LineageMapping> lineageMappings = new ArrayList<>();
            addImplementationDetails(job, stage, "input_links", PortType.INPUT_PORT, portImplementations, lineageMappings);
            addDataStoreDetails(job, stage, "reads_from_(design)", "read_by_(design)", PortType.INPUT_PORT, portImplementations, lineageMappings);
            addImplementationDetails(job, stage, "output_links", PortType.OUTPUT_PORT, portImplementations, lineageMappings);
            addDataStoreDetails(job, stage, "writes_to_(design)", "written_by_(design)", PortType.OUTPUT_PORT, portImplementations, lineageMappings);
            process.setPortImplementations(portImplementations);
            process.setLineageMappings(lineageMappings);
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
    private Process getSkeletonProcess(Reference igcObj) {
        Process process = null;
        if (igcObj != null) {
            process = new Process();
            // TODO: process.setGuid(jobObj.getId());
            process.setName(igcObj.getName());
            process.setDisplayName(igcObj.getName());
            process.setQualifiedName(getFullyQualifiedName(igcObj));
            process.setDescription(getDescription(igcObj));
            process.setOwner((String)igcRestClient.getPropertyByName(igcObj, "created_by"));
        }
        return process;
    }

    /**
     * Add implementation details of the job (ports and lineage mappings) to the provided lists, for the provided stage.
     *
     * @param job the job within which the stage exists
     * @param stage the stage for which to add implementation details
     * @param linkProperty the property of the stage for which to create the implementation details
     * @param portType the type of port
     * @param portImplementations the list of PortImplementations to append to with implementation details
     * @param lineageMappings the list of LineageMappings to append to with implementation details
     */
    private void addImplementationDetails(DSJob job,
                                          Reference stage,
                                          String linkProperty,
                                          PortType portType,
                                          List<PortImplementation> portImplementations,
                                          List<LineageMapping> lineageMappings) {
        String stageNameSuffix = "_" + stage.getName();
        // Setup an x_PORT for each x_link into / out of the stage
        ReferenceList inputLinks = (ReferenceList) igcRestClient.getPropertyByName(stage, linkProperty);
        for (Reference inputLinkRef : inputLinks.getItems()) {
            Reference linkObjFull = job.getLinkByRid(inputLinkRef.getId());
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
     * @param stageProperty the property of the stage for which to create the implementation details
     * @param dataStoreProperty the property of the stage for which to create the data store-relevant details
     * @param portType the type of port
     * @param portImplementations the list of PortImplementations to append to with implementation details
     * @param lineageMappings the list of LineageMappings to append to with implementation details
     */
    private void addDataStoreDetails(DSJob job,
                                     Reference stage,
                                     String stageProperty,
                                     String dataStoreProperty,
                                     PortType portType,
                                     List<PortImplementation> portImplementations,
                                     List<LineageMapping> lineageMappings) {
        String stageNameSuffix = "_" + stage.getName();
        // Setup an x_PORT for any data stores that are used by design as sources / targets
        String fullyQualifiedStageName = getFullyQualifiedName(stage);
        ReferenceList sources = (ReferenceList) igcRestClient.getPropertyByName(stage, stageProperty);
        for (Reference sourceRef : sources.getItems()) {
            List<Reference> fieldsForSource = job.getFieldsForStore(sourceRef.getId());
            PortImplementationMapping portImplementationMapping = new PortImplementationMapping(job, stage, portType, fieldsForSource, fullyQualifiedStageName);
            portImplementations.add(portImplementationMapping.getPortImplementation());
            LineageMappingMapping lineageMappingMapping = new LineageMappingMapping(job, fieldsForSource, dataStoreProperty, portType == PortType.INPUT_PORT, fullyQualifiedStageName, stageNameSuffix);
            lineageMappings.addAll(lineageMappingMapping.getLineageMappings());
        }
    }

}
