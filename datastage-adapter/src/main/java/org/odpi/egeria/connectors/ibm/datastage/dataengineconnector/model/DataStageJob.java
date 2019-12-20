/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
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
    private Map<String, Stage> stageMap;
    private Map<String, Link> linkMap;
    private Map<String, StageColumn> columnMap;
    private Map<String, Classificationenabledgroup> fieldMap;
    private Map<String, List<Classificationenabledgroup>> storeToFieldsMap;
    private Map<String, Identity> storeToIdentityMap;
    private List<String> inputStageRIDs;
    private List<String> outputStageRIDs;

    public enum JobType {
        JOB, SEQUENCE
    }

    /**
     * Create a new detailed DataStage job object.
     *
     * @param igcRestClient connectivity to the IGC environment
     * @param job the job for which to retrieve details
     */
    DataStageJob(IGCRestClient igcRestClient, Dsjob job) {

        this.igcRestClient = igcRestClient;
        this.job = job;
        this.type = job.getType().equals("sequence_job") ? JobType.SEQUENCE : JobType.JOB;
        this.stageMap = new HashMap<>();
        this.linkMap = new HashMap<>();
        this.columnMap = new HashMap<>();
        this.fieldMap = new HashMap<>();
        this.storeToFieldsMap = new HashMap<>();
        this.storeToIdentityMap = new HashMap<>();
        this.inputStageRIDs = new ArrayList<>();
        this.outputStageRIDs = new ArrayList<>();

        if (log.isDebugEnabled()) { log.debug("Retrieving job details for: {}", job.getId()); }

        getStageDetailsForJob();
        getLinkDetailsForJob();
        getStageColumnDetailsForLinks();
        classifyFields();

    }

    /**
     * Retrieve the IGC environment connectivity used to collect details for this job.
     *
     * @return IGCRestClient
     */
    public IGCRestClient getIgcRestClient() { return igcRestClient; }

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
     * Retrieve the qualifiedName for the provided data store RID, or null if it cannot be found.
     *
     * @param rid the data store RID for which to retrieve a qualifiedName
     * @return String
     */
    public String getQualifiedNameFromStoreRid(String rid) {
        Identity storeIdentity = getStoreIdentityFromRid(rid);
        String qualifiedName = null;
        if (storeIdentity != null) {
            qualifiedName = storeIdentity.toString();
        }
        return qualifiedName;
    }

    /**
     * Retrieve the store identity for the provided data store RID, or null if it cannot be found.
     *
     * @param rid the data store RID for which to retrieve an identity
     * @return Identity
     */
    public Identity getStoreIdentityFromRid(String rid) {
        return storeToIdentityMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve the set of data store RIDs that are used by this job.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getStoreRids() {
        return storeToIdentityMap.keySet();
    }

    /**
     * Retrieve the complete 'link' object based on its RID.
     *
     * @param rid the RID of the link object
     * @return Link
     */
    public Link getLinkByRid(String rid) {
        if (log.isDebugEnabled()) { log.debug("Looking up cached link: {}", rid); }
        return linkMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve the complete 'stage_column' object based on its RID.
     *
     * @param rid the RID of the stage_column object
     * @return StageColumn
     */
    public StageColumn getStageColumnByRid(String rid) {
        if (log.isDebugEnabled()) { log.debug("Looking up cached stage column: {}", rid); }
        return columnMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve the complete data store field ('data_file_field' or 'database_column') based on its RID.
     *
     * @param rid the RID of the data store field object
     * @return Classificationenabledgroup
     */
    public Classificationenabledgroup getDataStoreFieldByRid(String rid) {
        if (log.isDebugEnabled()) { log.debug("Looking up cached data store field: {}", rid); }
        return fieldMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve the list of data store fields for the provided data store ('database_table', 'view', or 'data_file_record') RID.
     *
     * @param rid the RID of the data store
     * @return {@code List<Classificationenabledgroup>}
     */
    public List<Classificationenabledgroup> getFieldsForStore(String rid) {
        return storeToFieldsMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve a listing of the stages within this particular DataStage job.
     */
    private void getStageDetailsForJob() {
        String jobRid = job.getId();
        if (log.isDebugEnabled()) { log.debug("Retrieving stage details for job: {}", jobRid); }
        IGCSearch igcSearch = new IGCSearch("stage");
        igcSearch.addProperties(DataStageConstants.getStageSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ItemList<Stage> stages = igcRestClient.search(igcSearch);
        stages.getAllPages(igcRestClient);
        classifyStages(stages);
    }

    /**
     * Retrieve a listing of the links within this particular DataStage job.
     */
    private void getLinkDetailsForJob() {
        String jobRid = job.getId();
        if (log.isDebugEnabled()) { log.debug("Retrieving link details for job: {}", jobRid); }
        IGCSearch igcSearch = new IGCSearch("link");
        igcSearch.addProperties(DataStageConstants.getLinkSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ItemList<Link> links = igcRestClient.search(igcSearch);
        links.getAllPages(igcRestClient);
        buildMap(linkMap, links);
    }

    /**
     * Retrieve a listing of the stage columns within this particular DataStage job.
     */
    private void getStageColumnDetailsForLinks() {
        String jobRid = job.getId();
        if (log.isDebugEnabled()) { log.debug("Retrieving stage column details for job: {}", jobRid); }
        ItemList<StageColumn> stageCols = getStageColumnDetailsForLinks("stage_column", jobRid);
        if (stageCols == null) {
            if (log.isInfoEnabled()) { log.info("Unable to identify stage columns for job by 'stage_column', reverting to 'ds_stage_column'."); }
            stageCols = getStageColumnDetailsForLinks("ds_stage_column", jobRid);
        }
        if (stageCols != null) {
            buildMap(columnMap, stageCols);
        } else {
            log.error("Unable to identify any stage columns for job: {}", jobRid);
        }
    }

    /**
     * Retrieve the details of stage columns within this particular DataStage job using the type provided: some IGC
     * versions need to retrieve 'stage_column' and others must retrieve 'ds_stage_column'.
     *
     * @param usingType the type by which to search for the stage columns
     * @param jobRid the RID of the job
     */
    private ItemList<StageColumn> getStageColumnDetailsForLinks(String usingType, String jobRid) {
        IGCSearch igcSearch = new IGCSearch(usingType);
        igcSearch.addProperties(DataStageConstants.getStageColumnSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("link.job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ItemList<StageColumn> stageCols = igcRestClient.search(igcSearch);
        if (stageCols.getPaging().getNumTotal() > 0) {
            stageCols.getAllPages(igcRestClient);
            return stageCols;
        }
        return null;
    }

    /**
     * Retrieve a listing of all of the data assets (to field-level granularity) this particular DataStage job reads
     * from or writes to.
     */
    private void classifyFields() {

        Map<String, List<Classificationenabledgroup>> dataStoreDetailsMap = new HashMap<>();
        if (!getType().equals(JobType.SEQUENCE)) {
            mapDataStoreDetailsForJob(job.getReadsFromDesign(), dataStoreDetailsMap);
            mapDataStoreDetailsForJob(job.getWritesToDesign(), dataStoreDetailsMap);
        }

        // Flatten the list of data store details
        List<Classificationenabledgroup> dataStoreDetails = new ArrayList<>();
        for (List<Classificationenabledgroup> referenceList : dataStoreDetailsMap.values()) {
            dataStoreDetails.addAll(referenceList);
        }

        for (Classificationenabledgroup candidateField : dataStoreDetails) {
            String rid = candidateField.getId();
            fieldMap.put(rid, candidateField);
            Identity storeIdentity = candidateField.getIdentity(igcRestClient).getParentIdentity();
            String storeId = storeIdentity.getRid();
            storeToIdentityMap.put(storeId, storeIdentity);
            if (!storeToFieldsMap.containsKey(storeId)) {
                storeToFieldsMap.put(storeId, new ArrayList<>());
            }
            storeToFieldsMap.get(storeId).add(candidateField);
        }
    }

    /**
     * Map the data store details used by the job into the provided map.
     *
     * @param candidates the list of candidate data store details to cache
     * @param dataStoreDetailsMap the map into which to place the results
     */
    private void mapDataStoreDetailsForJob(ItemList<InformationAsset> candidates,
                                           Map<String, List<Classificationenabledgroup>> dataStoreDetailsMap) {
        if (candidates != null) {
            candidates.getAllPages(igcRestClient);
            for (InformationAsset candidate : candidates.getItems()) {
                String candidateId = candidate.getId();
                if (!dataStoreDetailsMap.containsKey(candidateId)) {
                    List<Classificationenabledgroup> fields = DataStageDataAsset.getDataFieldsForStore(igcRestClient, candidate);
                    if (fields != null) {
                        dataStoreDetailsMap.put(candidateId, fields);
                    }
                }
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
    private <T extends Reference> void buildMap(Map<String, T> map, ItemList<T> objects) {
        List<T> listOfObjects = objects.getItems();
        for (T candidateObject : listOfObjects) {
            String rid = candidateObject.getId();
            if (log.isDebugEnabled()) { log.debug("...... caching RID: {}", rid); }
            map.put(rid, candidateObject);
        }
    }

    /**
     * Group the set of stages for this particular job according to whether they are input or output.
     *
     * @param stages the list of stages to cache and classify
     */
    private void classifyStages(ItemList<Stage> stages) {
        List<Stage> listOfStages = stages.getItems();
        for (Stage candidateStage : listOfStages) {
            String rid = candidateStage.getId();
            stageMap.put(rid, candidateStage);
            if (DataStageStage.isInputStage(igcRestClient, candidateStage)) {
                inputStageRIDs.add(rid);
            }
            if (DataStageStage.isOutputStage(igcRestClient, candidateStage)) {
                outputStageRIDs.add(rid);
            }
        }
    }

}
