/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Utility class for interacting with 'ds_job' information via IGC's REST API.
 */
public class DSJob {

    private static final Logger log = LoggerFactory.getLogger(DSJob.class);

    private static final List<String> SEARCH_PROPERTIES = createSearchProperties();

    private static List<String> createSearchProperties() {
        ArrayList<String> searchProperties = new ArrayList<>();
        searchProperties.add("short_description");
        searchProperties.add("long_description");
        searchProperties.add("references_local_or_shared_containers");
        searchProperties.add("type");
        searchProperties.add("reads_from_(design)");
        searchProperties.add("writes_to_(design)");
        searchProperties.add("created_by");
        searchProperties.add("created_on");
        searchProperties.add("modified_by");
        searchProperties.add("modified_on");
        return Collections.unmodifiableList(searchProperties);
    }

    /**
     * Retrieve a list of the search properties to use when querying via the IGC REST API.
     *
     * @return {@code List<String>}
     */
    public static List<String> getSearchProperties() { return SEARCH_PROPERTIES; }

    private IGCRestClient igcRestClient;
    private Reference job;
    private JobType type;
    private Map<String, Reference> stageMap;
    private Map<String, Reference> linkMap;
    private Map<String, Reference> columnMap;
    private Map<String, Reference> fieldMap;
    private Map<String, List<Reference>> storeToFieldsMap;
    private Map<String, Identity> storeToIdentityMap;
    private List<String> inputStageRIDs;
    private List<String> outputStageRIDs;

    public enum JobType {
        JOB, SEQUENCE
    }

    /**
     * Create a new detailed DataStage job object.
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param job the dsjob object from IGC
     * @param stages a listing of stage objects from IGC
     * @param links a listing of link objects from IGC
     * @param columns a listing of stage columns from IGC
     * @param fields a listing of data store fields (including database columns) from IGC
     */
    public DSJob(IGCRestClient igcRestClient, Reference job, ItemList<Reference> stages, ItemList<Reference> links, ItemList<Reference> columns, List<Reference> fields) {
        if (log.isDebugEnabled()) { log.debug("Creating new job..."); }
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
        classifyStages(stages);
        if (log.isDebugEnabled()) { log.debug("... building link map"); }
        buildMap(linkMap, links);
        if (log.isDebugEnabled()) { log.debug("... building column map"); }
        buildMap(columnMap, columns);
        classifyFields(fields);
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
     * @return {@code List<Reference>}
     */
    public List<Reference> getInputStages() {
        List<Reference> list = new ArrayList<>();
        for (String inputRid : inputStageRIDs) {
            list.add(stageMap.get(inputRid));
        }
        return list;
    }

    /**
     * Retrieve a list of the output stages for this job.
     *
     * @return {@code List<Reference>}
     */
    public List<Reference> getOutputStages() {
        List<Reference> list = new ArrayList<>();
        for (String outputRid : outputStageRIDs) {
            list.add(stageMap.get(outputRid));
        }
        return list;
    }

    /**
     * Retrieve all of the stages used within the job.
     *
     * @return {@code Collection<Reference>}
     */
    public Collection<Reference> getAllStages() {
        return stageMap.values();
    }

    /**
     * Retrieve all of the links used within the job.
     *
     * @return {@code Collection<Reference>}
     */
    public Collection<Reference> getAllLinks() {
        return linkMap.values();
    }

    /**
     * Retrieve the job object itself.
     *
     * @return Reference
     */
    public Reference getJobObject() { return job; }

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
     * @return Reference
     */
    public Reference getLinkByRid(String rid) {
        if (log.isDebugEnabled()) { log.debug("Looking up cached link: {}", rid); }
        return linkMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve the complete 'ds_stage_column' object based on its RID.
     *
     * @param rid the RID of the ds_stage_column object
     * @return Reference
     */
    public Reference getStageColumnByRid(String rid) {
        if (log.isDebugEnabled()) { log.debug("Looking up cached stage column: {}", rid); }
        return columnMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve the complete data store field ('data_file_field' or 'database_column') based on its RID.
     *
     * @param rid the RID of the data store field object
     * @return Reference
     */
    public Reference getDataStoreFieldByRid(String rid) {
        if (log.isDebugEnabled()) { log.debug("Looking up cached data store field: {}", rid); }
        return fieldMap.getOrDefault(rid, null);
    }

    /**
     * Retrieve the list of data store fields for the provided data store ('database_table', 'view', or 'data_file_record') RID.
     *
     * @param rid the RID of the data store
     * @return {@code List<Reference>}
     */
    public List<Reference> getFieldsForStore(String rid) {
        return storeToFieldsMap.getOrDefault(rid, null);
    }

    private void buildMap(Map<String, Reference> map, ItemList<Reference> objects) {
        List<Reference> listOfObjects = objects.getItems();
        for (Reference candidateObject : listOfObjects) {
            String rid = candidateObject.getId();
            if (log.isDebugEnabled()) { log.debug("...... caching RID: {}", rid); }
            map.put(rid, candidateObject);
        }
    }

    private void classifyStages(ItemList<Reference> stages) {
        List<Reference> listOfStages = stages.getItems();
        for (Reference candidateStage : listOfStages) {
            String rid = candidateStage.getId();
            stageMap.put(rid, candidateStage);
            if (DSStage.isInputStage(igcRestClient, candidateStage)) {
                inputStageRIDs.add(rid);
            }
            if (DSStage.isOutputStage(igcRestClient, candidateStage)) {
                outputStageRIDs.add(rid);
            }
        }
    }

    private void classifyFields(List<Reference> listOfFields) {
        for (Reference candidateField : listOfFields) {
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

}
