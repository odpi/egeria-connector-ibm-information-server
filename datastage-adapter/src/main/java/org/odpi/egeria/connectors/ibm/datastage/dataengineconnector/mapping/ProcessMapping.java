/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.LineageMode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.openmetadata.accessservices.dataengine.model.*;
import org.odpi.openmetadata.accessservices.dataengine.model.Collection;
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

    /**
     * Default constructor to pass in the cache for re-use.
     *
     * @param cache used by this mapping
     */
    public ProcessMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Create a new process from a DataStageJob that represents either a job or a sequence.
     *
     * @param job the job or sequence from which to create a process
     * @return Process
     */
    public Process getForJob(DataStageJob job) throws IGCException {
        final String methodName = "getForJob";
        Process process;
        if (job.getType().equals(DataStageJob.JobType.SEQUENCE)) {
            Dsjob jobObj = job.getJobObject();
            process = getSkeletonProcess(jobObj);
            if (process != null) {
                PortAliasMapping portAliasMapping = new PortAliasMapping(cache);
                process.setPortAliases(portAliasMapping.getForSequence(job));
                addTransformationProjectDetails(jobObj, process);
            }
        } else {
            Dsjob jobObj = job.getJobObject();
            process = getSkeletonProcess(jobObj);
            if (process != null) {

                addTransformationProjectDetails(jobObj, process);
                ItemList<SequenceJob> sequencedBy = job.getJobObject().getSequencedByJobs();

                if (cache.getMode() == LineageMode.JOB_LEVEL) {

                    // Setup job-level data flows
                    DataFlowMapping dataFlowMapping = new DataFlowMapping(cache);
                    Set<DataFlow> jobLevelLineage = dataFlowMapping.getForJob(job);
                    if (jobLevelLineage != null && !jobLevelLineage.isEmpty()) {
                        process.setDataFlows(new ArrayList<>(jobLevelLineage));
                    }

                } else if (cache.getMode() == LineageMode.GRANULAR) {

                    if (sequencedBy != null) {
                        // Setup a parent process relationship to any sequences that happen to call this job
                        // as only APPEND parents (not OWNED), since removal of the sequence does not result in removal
                        // of the job itself

                        List<ParentProcess> parents = new ArrayList<>();
                        List<SequenceJob> allSequences = igcRestClient.getAllPages("sequenced_by_jobs", sequencedBy);
                        for (SequenceJob sequenceJob : allSequences) {
                            ParentProcess parent = new ParentProcess();
                            String sequenceJobQN = getFullyQualifiedName(sequenceJob);
                            if (sequenceJobQN != null) {
                                parent.setQualifiedName(sequenceJobQN);
                                parent.setProcessContainmentType(ProcessContainmentType.APPEND);
                                parents.add(parent);
                            } else {
                                log.error("Unable to determine identity for sequence -- not including: {}", sequenceJob);
                            }
                        }
                        if (!parents.isEmpty()) {
                            process.setParentProcesses(parents);
                        }

                    }

                    PortAliasMapping portAliasMapping = new PortAliasMapping(cache);
                    process.setPortAliases(Stream.concat(
                            portAliasMapping.getForStages(job.getInputStages(), PortType.INPUT_PORT).stream(),
                            portAliasMapping.getForStages(job.getOutputStages(), PortType.OUTPUT_PORT).stream())
                            .collect(Collectors.toList()));
                    Set<DataFlow> dataFlows = new HashSet<>();
                    for (Link link : job.getAllLinks()) {
                        DataFlowMapping dataFlowMapping = new DataFlowMapping(cache);
                        Set<DataFlow> crossStageDataFlows = dataFlowMapping.getForLink(link, job);
                        if (crossStageDataFlows != null && !crossStageDataFlows.isEmpty()) {
                            dataFlows.addAll(crossStageDataFlows);
                        }
                    }
                    if (!dataFlows.isEmpty()) {
                        process.setDataFlows(new ArrayList<>(dataFlows));
                    }

                }
            }
        }
        return process;
    }

    /**
     * Create a new process from the provided DataStage stage.
     *
     * @param stage the stage from which to create a process
     * @param job the job within which the stage exists
     * @return Process
     */
    public Process getForStage(Stage stage, DataStageJob job) throws IGCException {
        final String methodName = "getForStage";
        Process process = getSkeletonProcess(stage);
        if (process != null) {
            Set<PortImplementation> portImplementations = new HashSet<>();
            Set<DataFlow> dataFlows = new HashSet<>();

            List<Link> allInputLinks = igcRestClient.getAllPages("input_links", stage.getInputLinks());
            List<Link> allOutputLinks = igcRestClient.getAllPages("output_links", stage.getOutputLinks());
            Set<String> allLinkRids = Stream.concat(allInputLinks.stream(), allOutputLinks.stream()).map(Link::getId).collect(Collectors.toSet());
            log.debug("Adding input links: {}", allInputLinks);
            addImplementationDetails(job, stage, allInputLinks, allLinkRids, PortType.INPUT_PORT, portImplementations, dataFlows);

            log.debug("Adding input stores: {}", stage.getReadsFromDesign());
            addDataStoreDetails(job, stage, "reads_from_(design)", stage.getReadsFromDesign(), allLinkRids, PortType.INPUT_PORT, portImplementations, dataFlows);
            log.debug("Adding output links: {}", allOutputLinks);
            addImplementationDetails(job, stage, allOutputLinks, allLinkRids, PortType.OUTPUT_PORT, portImplementations, dataFlows);
            log.debug("Adding output stores: {}", stage.getWritesToDesign());
            addDataStoreDetails(job, stage, "writes_to_(design)", stage.getWritesToDesign(), allLinkRids, PortType.OUTPUT_PORT, portImplementations, dataFlows);
            log.debug("Adding stage variables");
            addStageVariableDetails(job, stage, portImplementations, dataFlows);

            log.debug("Adding transformation project info ");
            addTransformationProjectDetails(stage, process);

            process.setPortImplementations(new ArrayList<>(portImplementations));
            process.setDataFlows(new ArrayList<>(dataFlows));
            // Stages are owned by the job that contains them, so setup an owned parent process relationship to the
            // job-level
            List<ParentProcess> parents = new ArrayList<>();
            ParentProcess parent = new ParentProcess();
            String jobQN = getFullyQualifiedName(job.getJobObject());
            if (jobQN != null) {
                parent.setQualifiedName(jobQN);
                parent.setProcessContainmentType(ProcessContainmentType.OWNED);
                parents.add(parent);
                process.setParentProcesses(parents);
            } else {
                log.error("Unable to determine identity for job -- not including: {}", job.getJobObject());
            }

        }
        return process;
    }

    /**
     * Construct a minimal Process object from the provided IGC object (stage, job, sequence, etc).
     *
     * @param igcObj the IGC object from which to construct the skeletal Process
     * @return Process
     */
    private Process getSkeletonProcess(InformationAsset igcObj) throws IGCException {
        final String methodName = "getSkeletonProcess";
        Process process = null;
        if (igcObj != null) {
            process = new Process();
            process.setName(igcObj.getName());
            process.setDisplayName(igcObj.getName());

            String objQN = getFullyQualifiedName(igcObj);
            if (objQN != null) {
                log.debug("Constructing process for: {}", objQN);
                process.setQualifiedName(objQN);
                process.setDescription(getDescription(igcObj));
                process.setOwner(igcObj.getCreatedBy());
                // TODO: setAdditionalProperties or setExtendedProperties with other information on the IGC object?
            } else {
                log.error("Unable to determine identity for asset -- not including: {}", igcObj);
                process = null;
            }

        }
        return process;
    }

    /**
     * Add implementation details of the job (ports and data flows) to the provided lists, for the provided stage.
     *
     * @param job the job within which the stage exists
     * @param stage the stage for which to add implementation details
     * @param links the links of the stage from which to draw implementation details
     * @param linkRids the RIDs of all links known by this stage (both input and output)
     * @param portType the type of port
     * @param portImplementations the list of PortImplementations to append to with implementation details
     * @param dataFlows the list of DataFlows to append to with implementation details
     */
    private void addImplementationDetails(DataStageJob job,
                                          Stage stage,
                                          List<Link> links,
                                          Set<String> linkRids,
                                          PortType portType,
                                          Set<PortImplementation> portImplementations,
                                          Set<DataFlow> dataFlows) throws IGCException {
        final String methodName = "addImplementationDetails";

        String stageQN = getFullyQualifiedName(stage);
        // Setup an x_PORT for each x_link into / out of the stage
        for (Link linkRef : links) {
            Link linkObjFull = job.getLinkByRid(linkRef.getId());
            log.debug("Adding implementation details for link: {}", linkObjFull);
            PortImplementationMapping portImplementationMapping = new PortImplementationMapping(cache);
            if(portImplementationMapping != null) {
                portImplementations.add(portImplementationMapping.getForLink(linkObjFull, job, portType, stageQN));
            }

            log.debug("Adding data flows for link as {}: {}", portType.getName(), linkObjFull);
            DataFlowMapping dataFlowMapping = new DataFlowMapping(cache);
            dataFlows.addAll(dataFlowMapping.getForLinkInStage(linkObjFull, job, stage.getId(), linkRids, stageQN, portType == PortType.INPUT_PORT));
        }

    }


    /**
     * Add transformation project to process, as collection, if it exists in the asset
     *
     * @param asset the asset for which to add transformation project
     * @param process the process on which to add the transformation project
     */
    private void addTransformationProjectDetails(InformationAsset asset, Process process) throws IGCException {
        TransformationProjectMapping transformationProjectMapping = new TransformationProjectMapping(cache);
        Collection transformationProject = transformationProjectMapping.getTransformationProject(asset);
        process.setCollection(transformationProject);
    }


    /**
     * Add stage variable details to the provided lists, for the provided stage.
     *
     * @param job the job within which the stage exists
     * @param stage the stage for which to add stage variable details
     * @param portImplementations the set of PortImplementations to append to with implementation details
     * @param dataFlows the set of DataFlows to append to with details
     */
    private void addStageVariableDetails(DataStageJob job,
                                         Stage stage,
                                         Set<PortImplementation> portImplementations,
                                         Set<DataFlow> dataFlows) throws IGCException {
        final String methodName = "addStageVariableDetails";

        String stageQN = getFullyQualifiedName(stage);
        List<StageVariable> stageVarsForStage = job.getStageVarsForStage(stage.getId());
        if (stageVarsForStage != null && !stageVarsForStage.isEmpty()) {
            log.debug("Adding implementation details for stage variables of stage: {}", stageQN);
            PortImplementationMapping portImplementationMapping = new PortImplementationMapping(cache);
            portImplementations.add(portImplementationMapping.getForStageVariables(stageVarsForStage, job, stage, stageQN));
            log.debug("Adding data flows for stage variables of stage: {}", stageQN);
            DataFlowMapping dataFlowMapping = new DataFlowMapping(cache);
            dataFlows.addAll(dataFlowMapping.getForStageVariables(stageVarsForStage, job, stageQN));
        } else {
            log.debug("No stage variables present in stage -- skipping: {}", stageQN);
        }

    }

    /**
     * Add implementation details of the job (ports and data flows) to the provided lists, for any data stores
     * used by the specified stage.
     *
     * @param job the job within which the stage exists
     * @param stage the stage for which to add implementation details
     * @param propertyName the name of the property from which stores were retrieved
     * @param stores the stores for which to create the implementation details
     * @param linkRids the RIDs of all links known by this stage (both input and output)
     * @param portType the type of port
     * @param portImplementations the list of PortImplementations to append to with implementation details
     * @param dataFlows the list of DataFlows to append to with implementation details
     */
    private void addDataStoreDetails(DataStageJob job,
                                     Stage stage,
                                     String propertyName,
                                     ItemList<InformationAsset> stores,
                                     Set<String> linkRids,
                                     PortType portType,
                                     Set<PortImplementation> portImplementations,
                                     Set<DataFlow> dataFlows) throws  IGCException {
        final String methodName = "addDataStoreDetails";
        // Setup an x_PORT for any data stores that are used by design as sources / targets

        String fullyQualifiedStageName = getFullyQualifiedName(stage);
        if (fullyQualifiedStageName != null) {
            List<InformationAsset> allStores = igcRestClient.getAllPages(propertyName, stores);
            for (InformationAsset storeRef : allStores) {
                List<Classificationenabledgroup> fieldsForStore = cache.getFieldsForStore(storeRef);
                log.debug("Adding implementation details for fields: {}", fieldsForStore);
                PortImplementationMapping portImplementationMapping = new PortImplementationMapping(cache);
                if(portImplementationMapping != null) {
                    portImplementations.add(portImplementationMapping.getForDataStoreFields(fieldsForStore, stage, portType, fullyQualifiedStageName));
                }

                log.debug("Adding data flows for fields as {}: {}", portType.getName(), fieldsForStore);
                DataFlowMapping dataFlowMapping = new DataFlowMapping(cache);
                dataFlows.addAll(dataFlowMapping.getForDataStoreFieldsInStage(fieldsForStore, job, stage.getId(), linkRids, portType.equals(PortType.INPUT_PORT), fullyQualifiedStageName));
            }
        } else {
            log.error("Unable to determine identity for stage -- not including: {}", stage);
        }

    }

}
