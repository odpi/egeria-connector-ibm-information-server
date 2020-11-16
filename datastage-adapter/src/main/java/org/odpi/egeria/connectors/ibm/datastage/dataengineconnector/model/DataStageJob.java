/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnector;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConstants;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.interfaces.ColumnLevelLineage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Collection;

/**
 * Class for interacting with DataStage Job objects.
 */
public class DataStageJob {

    private static final Logger log = LoggerFactory.getLogger(DataStageJob.class);

    private IGCRestClient igcRestClient;
    private Dsjob job;
    private JobType type;
    private final Map<String, Stage> stageMap;
    private final Map<String, Link> linkMap;
    private final Map<String, StageColumn> columnMap;
    private final Map<String, StageVariable> varMap;
    private final Map<String, Set<String>> stageToVarsMap;
    private final Set<String> storesForJob;
    private final List<String> inputStageRIDs;
    private final List<String> outputStageRIDs;

    public enum JobType {
        JOB, SEQUENCE
    }

    /**
     * Create a new detailed DataStage job object.
     *
     * @param cache cache and connectivity to the IGC environment
     * @param job the job for which to retrieve details
     */
    public DataStageJob(DataStageCache cache, Dsjob job) {

        this.igcRestClient = cache.getIgcRestClient();
        this.job = job;
        this.type = job.getType().equals("sequence_job") ? JobType.SEQUENCE : JobType.JOB;
        this.stageMap = new TreeMap<>();
        this.linkMap = new TreeMap<>();
        this.columnMap = new TreeMap<>();
        this.varMap = new TreeMap<>();
        this.stageToVarsMap = new TreeMap<>();
        this.storesForJob = new TreeSet<>();
        this.inputStageRIDs = new ArrayList<>();
        this.outputStageRIDs = new ArrayList<>();

        log.debug("Retrieving job details for: {}", job.getId());

        getStageDetailsForJob();
        getLinkDetailsForJob();
        getStageVariablesForJob();
        classifyStages(stageMap.values());
        getStageColumnDetailsForLinks();
        classifyFields(cache);

    }

    /**
     * Retrieve the type of job represented by this instance.
     *
     * @return JobType
     */
    public JobType getType() { return type; }

    /**
     * Retrieve a list of the input stages for this job.
     *
     * @return {@code List<Stage>}
     */
    public List<Stage> getInputStages() {
        List<Stage> list = new ArrayList<>();
        for (String inputRid : inputStageRIDs) {
            list.add(stageMap.get(inputRid));
        }
        return list;
    }

    /**
     * Retrieve a list of the output stages for this job.
     *
     * @return {@code List<Stage>}
     */
    public List<Stage> getOutputStages() {
        List<Stage> list = new ArrayList<>();
        for (String outputRid : outputStageRIDs) {
            list.add(stageMap.get(outputRid));
        }
        return list;
    }

    /**
     * Retrieve all of the stages used within the job.
     *
     * @return {@code Collection<Stage>}
     */
    public Collection<Stage> getAllStages() {
        return stageMap.values();
    }

    /**
     * Retrieve all of the links used within the job.
     *
     * @return {@code Collection<Reference>}
     */
    public Collection<Link> getAllLinks() {
        return linkMap.values();
    }

    /**
     * Retrieve the job object itself.
     *
     * @return Dsjob
     */
    public Dsjob getJobObject() { return job; }

    /**
     * Retrieve the set of data store RIDs that are used by this job.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getStoreRids() {
        return new TreeSet<>(storesForJob);
    }

    /**
     * Retrieve the complete 'link' object based on its RID.
     *
     * @param rid the RID of the link object
     * @return Link
     */
    public Link getLinkByRid(String rid) {
        final String methodName = "getLinkByRid";
        log.debug("Looking up cached link: {}", rid);
        Link link = linkMap.getOrDefault(rid, null);
        if (link == null) {
            log.debug("(cache miss) -- retrieving and caching link: {}", rid);
            try {
                link = (Link) igcRestClient.getAssetById(rid);
                linkMap.put(rid, link);
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
        return link;
    }

    /**
     * Retrieve the complete column-level lineage object ('stage_column' or 'stage_variable') based on its RID.
     *
     * @param rid the RID of the column-level lineage object
     * @return ColumnLevelLineage
     */
    public ColumnLevelLineage getColumnLevelLineageByRid(String rid) {
        final String methodName = "getColumnLevelLineageByRid";
        log.debug("Looking up cached stage column / variable: {}", rid);
        ColumnLevelLineage toReturn = null;
        StageColumn column = columnMap.getOrDefault(rid, null);
        if (column == null) {
            StageVariable variable = varMap.getOrDefault(rid, null);
            if (variable == null) {
                log.debug("(cache miss) -- retrieving and caching stage column / variable: {}", rid);
                try {
                    Reference thing = igcRestClient.getAssetById(rid);
                    if (thing instanceof StageVariable) {
                        varMap.put(rid, (StageVariable) thing);
                        toReturn = varMap.get(rid);
                    } else if (thing instanceof StageColumn) {
                        columnMap.put(rid, (StageColumn) thing);
                        toReturn = columnMap.get(rid);
                    } else if (thing != null) {
                        log.error("Unable to determine object type ({}) by RID: {}", thing.getType(), rid);
                    } else {
                        log.error("Unable to find object by RID: {}", rid);
                    }
                } catch (IGCException e) {
                    DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                            this.getClass().getName(),
                            methodName,
                            e);
                }
            } else {
                toReturn = variable;
            }
        } else {
            toReturn = column;
        }
        return toReturn;
    }

    /**
     * Retrieve the complete list of stage variables for the provided stage based on its RID.
     *
     * @param rid the RID of the stage for which to retrieve all stage variables
     * @return {@code List<StageVariable>}
     */
    public List<StageVariable> getStageVarsForStage(String rid) {
        final String methodName = "getStageVarsForStage";
        log.debug("Looking up cache stage variables for stage: {}", rid);
        Set<String> stageVarRids = stageToVarsMap.getOrDefault(rid, null);
        if (stageVarRids == null) {
            log.debug("(cache miss) -- retrieving and caching stage variables: {}", rid);
            try {
                Reference thing = igcRestClient.getAssetById(rid);
                if (thing instanceof Stage) {
                    Stage stage = (Stage) thing;
                    ItemList<StageVariable> vars = stage.getStageVariable();
                    stageToVarsMap.put(rid, new TreeSet<>());
                    buildStageVariableMaps(igcRestClient.getAllPages("stage_variable", vars));
                    stageVarRids = stageToVarsMap.getOrDefault(rid, null);
                } else {
                    log.error("Unable to find stage with RID: {} -- found asset of type {} instead.", rid, thing == null ? "<null>" : thing.getType());
                }
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
        if (stageVarRids != null && !stageVarRids.isEmpty()) {
            List<StageVariable> stageVariables = new ArrayList<>();
            for (String stageVarRid : stageVarRids) {
                StageVariable var = varMap.get(stageVarRid);
                if (var != null) {
                    stageVariables.add(var);
                }
            }
            return stageVariables;
        }
        return Collections.emptyList();
    }

    /**
     * Retrieve a listing of the stages within this particular DataStage job.
     */
    private void getStageDetailsForJob() {
        final String methodName = "getStageDetailsForJob";
        String jobRid = job.getId();
        log.debug("Retrieving stage details for job: {}", jobRid);
        IGCSearch igcSearch = new IGCSearch("stage");
        igcSearch.addProperties(DataStageConstants.getStageSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        try {
            ItemList<Stage> stages = igcRestClient.search(igcSearch);
            List<Stage> allStages = igcRestClient.getAllPages(null, stages);
            for (Stage stage : allStages) {
                stageToVarsMap.put(stage.getId(), new TreeSet<>());
            }
            buildMap(stageMap, allStages);
        } catch (IGCException e) {
            DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                    this.getClass().getName(),
                    methodName,
                    e);
        }
    }

    /**
     * Retrieve a listing of the links within this particular DataStage job.
     */
    private void getLinkDetailsForJob() {
        final String methodName = "getLinkDetailsForJob";
        String jobRid = job.getId();
        log.debug("Retrieving link details for job: {}", jobRid);
        IGCSearch igcSearch = new IGCSearch("link");
        igcSearch.addProperties(DataStageConstants.getLinkSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        try {
            ItemList<Link> links = igcRestClient.search(igcSearch);
            buildMap(linkMap, igcRestClient.getAllPages(null, links));
        } catch (IGCException e) {
            DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                    this.getClass().getName(),
                    methodName,
                    e);
        }
    }

    /**
     * Retrieve a listing of the stage variables within this particular DataStage job.
     */
    private void getStageVariablesForJob() {
        final String methodName = "getStageVariablesForJob";
        String jobRid = job.getId();
        log.debug("Retrieving stage variables for job: {}", jobRid);
        IGCSearch igcSearch = new IGCSearch("stage_variable");
        igcSearch.addProperties(DataStageConstants.getStageVariableSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("stage.job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        try {
            ItemList<StageVariable> vars = igcRestClient.search(igcSearch);
            buildStageVariableMaps(igcRestClient.getAllPages(null, vars));
        } catch (IGCException e) {
            DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                    this.getClass().getName(),
                    methodName,
                    e);
        }
    }

    /**
     * Cache relationships for all of the stage variables provided.
     * @param allStageVars the list of stage variables to cache
     */
    private void buildStageVariableMaps(List<StageVariable> allStageVars) throws IGCConnectivityException, IGCParsingException {
        for (StageVariable stageVar : allStageVars) {
            String rid = stageVar.getId();
            // If the modification details are empty, likely we did not get this from a search but from paging
            // within a stage itself (cache miss), and therefore must retrieve the details of each variable one-by-one
            // as well
            if (stageVar.getModifiedBy() == null) {
                log.debug("...... retrieving stage variable by RID: {}", rid);
                stageVar = igcRestClient.getAssetWithSubsetOfProperties(rid, "stage_variable", DataStageConstants.getStageVariableSearchProperties());
            }
            log.debug("...... caching RID: {}", rid);
            varMap.put(rid, stageVar);
            String stageRid = stageVar.getStage().getId();
            Set<String> stageVars = stageToVarsMap.getOrDefault(stageRid, null);
            if (stageVars != null) {
                stageVars.add(rid);
                stageToVarsMap.put(stageRid, stageVars);
            } else {
                log.error("Stage variables were null for stage RID: {}", stageRid);
            }
        }
    }

    /**
     * Retrieve a listing of the stage columns within this particular DataStage job.
     */
    private void getStageColumnDetailsForLinks() {
        String jobRid = job.getId();
        log.debug("Retrieving stage column details for job: {}", jobRid);
        List<StageColumn> stageCols = getStageColumnDetailsForLinks("stage_column", jobRid);
        if (stageCols == null) {
            log.info("Unable to identify stage columns for job by 'stage_column', reverting to 'ds_stage_column'.");
            stageCols = getStageColumnDetailsForLinks("ds_stage_column", jobRid);
        }
        if (stageCols != null) {
            buildMap(columnMap, stageCols);
        } else if (type.equals(JobType.JOB)) {
            // Only warn about finding no columns if this is a Job (Sequences in general will not have stage columns)
            log.warn("Unable to identify any stage columns for job: {}", jobRid);
        }
    }

    /**
     * Retrieve the details of stage columns within this particular DataStage job using the type provided: some IGC
     * versions need to retrieve 'stage_column' and others must retrieve 'ds_stage_column'.
     *
     * @param usingType the type by which to search for the stage columns
     * @param jobRid the RID of the job
     */
    private List<StageColumn> getStageColumnDetailsForLinks(String usingType, String jobRid) {
        final String methodName = "getStageColumnDetailsForLinks";
        IGCSearch igcSearch = new IGCSearch(usingType);
        igcSearch.addProperties(DataStageConstants.getStageColumnSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("link.job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        try {
            ItemList<StageColumn> stageCols = igcRestClient.search(igcSearch);
            if (stageCols.getPaging().getNumTotal() > 0) {
                return igcRestClient.getAllPages(null, stageCols);
            }
        } catch (IGCException e) {
            DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                    this.getClass().getName(),
                    methodName,
                    e);
        }
        return null;
    }

    /**
     * Retrieve a listing of all of the data assets (to field-level granularity) this particular DataStage job reads
     * from or writes to.
     *
     * @param cache cache and connectivity to IGC environment
     */
    private void classifyFields(DataStageCache cache) {
        if (!getType().equals(JobType.SEQUENCE)) {
            mapDataStoreDetailsForJob(cache, "reads_from_(design)", job.getReadsFromDesign());
            mapDataStoreDetailsForJob(cache, "writes_to_(design)", job.getWritesToDesign());
        }
    }

    /**
     * Map the data store details used by the job into the provided map.
     *
     * @param cache cache and connectivity to IGC environment
     * @param propertyName the name of the property from which the candidates were retrieved
     * @param candidates the list of candidate data store details to cache
     */
    private void mapDataStoreDetailsForJob(DataStageCache cache,
                                           String propertyName,
                                           ItemList<InformationAsset> candidates) {
        final String methodName = "mapDataStoreDetailsForJob";
        if (candidates != null) {
            try {
                List<InformationAsset> allCandidates = igcRestClient.getAllPages(propertyName, candidates);
                for (InformationAsset candidate : allCandidates) {
                    String storeId = candidate.getId();
                    storesForJob.add(storeId);
                    cache.getFieldsForStore(candidate);
                }
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
    }

    /**
     * Cache the provided ItemList of objects into the provided map, keyed by the RID of the object.
     *
     * @param map the map into which to store the cache
     * @param objects the list of objects to cache
     * @param <T> the type of object to cache
     */
    private <T extends Reference> void buildMap(Map<String, T> map, List<T> objects) {
        for (T candidateObject : objects) {
            String rid = candidateObject.getId();
            log.debug("...... caching RID: {}", rid);
            map.put(rid, candidateObject);
        }
    }

    /**
     * Group the set of stages for this particular job according to whether they are input or output.
     *
     * @param stages the collection of stages to classify
     */
    private void classifyStages(Collection<Stage> stages) {
        for (Stage candidateStage : stages) {
            String rid = candidateStage.getId();
            if (DataStageStage.isInputStage(candidateStage)) {
                inputStageRIDs.add(rid);
            }
            if (DataStageStage.isOutputStage(candidateStage)) {
                outputStageRIDs.add(rid);
            }
        }
    }

}
