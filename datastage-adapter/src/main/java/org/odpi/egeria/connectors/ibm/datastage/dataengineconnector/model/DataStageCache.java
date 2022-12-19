/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.apache.commons.collections4.CollectionUtils;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConstants;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping.ProcessMapping;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.services.ReportService;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classificationenabledgroup;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DataFileRecord;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DatabaseTable;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Dsjob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.View;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Utility class to cache DataStage information for use by multiple steps in the Data Engine processing.
 */
public class DataStageCache {

    private static final Logger log = LoggerFactory.getLogger(DataStageCache.class);
    private final boolean includeVirtualAssets;

    private Map<String, DataStageJob> ridToJob;
    private Map<String, Process> ridToProcess;
    private Map<String, Identity> storeToIdentity;
    private Map<String, List<Classificationenabledgroup>> storeToColumns;

    private IGCRestClient igcRestClient;
    private ObjectCache igcCache;
    private Date from;
    private Date to;
    private LineageMode mode;
    private List<String> limitToProjects;
    private List<String> limitToLabels;
    private boolean limitToLineageEnabled;

    private ReportService reportService;

    /**
     * Create a new cache for changes between the times provided.
     *
     * @param from                      the date and time from which to cache changes
     * @param to                        the date and time until which to cache changes
     * @param mode                      the mode of operation for the connector, indicating the level of detail to include for lineage
     * @param limitToProjects           limit the cached jobs to only those in the provided list of projects
     * @param limitToLabels             limit to labels
     * @param limitToLineageEnabledJobs limit the processing to those jobs for which lineage is enabled
     * @param includeVirtualAssets      limit the processing to real assets
     */
    public DataStageCache(Date from, Date to, LineageMode mode, List<String> limitToProjects, List<String> limitToLabels, boolean limitToLineageEnabledJobs,
                          boolean includeVirtualAssets) {
        this.igcCache = new ObjectCache();
        this.ridToJob = new HashMap<>();
        this.ridToProcess = new HashMap<>();
        this.storeToIdentity = new HashMap<>();
        this.storeToColumns = new HashMap<>();
        this.from = from;
        this.to = to;
        this.mode = mode;
        this.limitToProjects = (limitToProjects == null ? Collections.emptyList() : limitToProjects);
        this.limitToLabels = limitToLabels;
        this.limitToLineageEnabled = limitToLineageEnabledJobs;
        this.includeVirtualAssets = includeVirtualAssets;
    }

    /**
     * Populate the cache.
     *
     * @param igcRestClient connectivity to the IGC environment
     */
    public void initialize(IGCRestClient igcRestClient) throws IGCException {
        this.igcRestClient = igcRestClient;
        getChangedJobs();
    }

    public boolean initializeWithReportJobs(IGCRestClient igcRestClient, List<String> assetRIDs, boolean detectLineage) throws IGCException {
        this.igcRestClient = igcRestClient;
        this.reportService = new ReportService(igcRestClient);
        if(CollectionUtils.isNotEmpty(assetRIDs)) {
            List<String> jobsFromReports = reportService.getJobsFromReports(assetRIDs);
            cacheReportJobs(jobsFromReports, detectLineage);
            return true;
        }
        return false;
    }

    private void cacheReportJobs(List<String> jobRids, boolean detectLineage) throws IGCConnectivityException, IGCParsingException {
        for(String jobRid : jobRids) {
            Dsjob dsjob = (Dsjob) igcRestClient.getAssetById(jobRid);
            cacheReportJob(dsjob, detectLineage);
        }
    }

    private void cacheReportJob(Dsjob dsjob, boolean detectLineage) {
        String jobRid = dsjob.getId();
        if (!ridToJob.containsKey(jobRid)) {
            try {
                log.debug("Detecting lineage on dsjob: {}", jobRid);
                // Detect lineage on each dsjob to ensure its details are fully populated before proceeding
                boolean lineageDetected = true;
                if(detectLineage) {
                    lineageDetected = igcRestClient.detectLineage(jobRid);
                }
                if(lineageDetected) {
                    // We then need to re-retrieve the dsjob's details, as they may have changed since lineage
                    // detection (following call will be a no-op if the dsjob is already in the cache)
                    DataStageJob job = new DataStageJob(this, dsjob);
                    this.ridToJob.put(jobRid, job);
                } else {
                    log.warn("Unable to detect lineage for dsjob -- not including: {}", jobRid);
                }
            } catch (IGCException e) {
                log.error("Failed to detect lineage for dsjob -- not including: {}, check system log for exception details.", jobRid, e);
            }
        }
    }

    /**
     * Retrieve the mode of operation of the cache (level of detail to inclue for lineage).
     * @return LineageMode
     */
    public LineageMode getMode() { return mode; }

    /**
     * Retrieve the date and time from which this cache contains change information.
     * @return Date
     */
    public Date getFrom() { return from; }

    /**
     * Retrieve the date and time to which this cache contains change information.
     * @return Date
     */
    public Date getTo() { return to; }

    /**
     * Retrieve the embedded cache of IGC objects.
     * @return ObjectCache
     */
    public ObjectCache getIgcCache() { return igcCache; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DataStageCache)) return false;
        DataStageCache that = (DataStageCache) obj;
        return Objects.equals(getFrom(), that.getFrom()) &&
                Objects.equals(getTo(), that.getTo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo());
    }

    /**
     * Retrieve the IGC connectivity used to populate the cache.
     *
     * @return IGCRestClient
     */
    public IGCRestClient getIgcRestClient() {
        return igcRestClient;
    }

    /**
     * Retrieve all cached jobs.
     *
     * @return {@code Collection<DataStageJob>}
     */
    public Collection<DataStageJob> getAllJobs() {
        return ridToJob.values();
    }

    /**
     * Retrieve the Process representation of the provided DataStage job object's (sequence or job) RID.
     *
     * @param rid of the DataStage job object (sequence or job)
     * @return Process representation of the job (or sequence)
     */
    public Process getProcessByRid(String rid) throws IGCException {
        // First try to retrieve it from the cache directly
        Process process = ridToProcess.getOrDefault(rid, null);
        if (process == null) {
            log.debug("(cache miss) -- building Process for job: {}", rid);
            DataStageJob job = getJobByRid(rid);
            if (job != null) {
                ProcessMapping processMapping = new ProcessMapping(this);
                process = processMapping.getForJob(job);
                if (process != null) {
                    ridToProcess.put(rid, process);
                }
            }
        }
        return process;
    }

    /**
     * Retrieve the set of process RIDs that are currently cached.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getCachedProcessRids() {
        return new HashSet<>(ridToProcess.keySet());
    }

    /**
     * Retrieve a DataStage job based on the provided Repository ID (RID) of the job.
     *
     * @param rid the RID of the job to lookup
     * @return DataStageJob if found, otherwise null
     */
    public DataStageJob getJobByRid(String rid) throws IGCException {
        final String methodName = "getJobByRid";
        // First try to retrieve it from the cache directly
        DataStageJob job = ridToJob.getOrDefault(rid, null);
        if (job == null) {
            // If not there, run a search to retrieve it
            IGCSearch igcSearch = new IGCSearch("dsjob");
            igcSearch.addProperties(DataStageConstants.getJobSearchProperties());
            IGCSearchCondition byRid = new IGCSearchCondition("_id", "=", rid);
            IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(byRid);
            log.info("(cache miss) -- searching for job by RID: {}", rid);
            igcSearch.addConditions(conditionSet);

            ItemList<Dsjob> results = igcRestClient.search(igcSearch);
            if (results != null) {
                List<Dsjob> resultsList = results.getItems();
                if (resultsList != null && !resultsList.isEmpty()) {
                    // Assuming one is found, build its full details and then add it to the cache
                    job = new DataStageJob(this, resultsList.get(0));
                    ridToJob.put(rid, job);
                } else {
                    log.warn("No job found with RID: {}", rid);
                }
            } else {
                log.warn("No job found with RID: {}", rid);
            }
        }
        return job;
    }

    /**
     * Retrieve the list of fields for the provided data store ('database_table', 'view', or 'data_file_record')
     * Repository ID (RID).
     *
     * @param store the InformationAsset representing the data store for which to retrieve the fields
     * @return {@code List<Classificationenabledgroup>} of fields in that data store
     */
    public List<Classificationenabledgroup> getFieldsForStore(InformationAsset store) throws IGCException {

        final String methodName = "getFieldsForStore";

        // First try to retrieve it from the cache directly
        String rid = store.getId();
        String storeType = store.getType();

        List<Classificationenabledgroup> fields = null;

        // Regardless of mode, we at least need the store identity
        Identity storeIdentity = storeToIdentity.getOrDefault(rid, null);
        if (storeIdentity == null && mode == LineageMode.JOB_LEVEL) {

            // If not there, retrieve it and cache it
            log.debug("(cache miss) -- retrieving data store details for {}: {}", storeType, rid);
            if (!store.isVirtualAsset()) {
                // For non-virtual assets the most efficient way of retrieving this information is via a search (by RID)
                storeIdentity = store.getIdentity(igcRestClient, igcCache);
                storeToIdentity.put(rid, storeIdentity);
            } else if (includeVirtualAssets) {
                // For virtual assets, we must retrieve the full object (search by RID is not possible)
                Reference virtualStore = igcRestClient.getAssetById(rid, igcCache);
                storeToIdentity.put(rid, virtualStore.getIdentity(igcRestClient, igcCache));
            }

        } else if (mode == LineageMode.GRANULAR) {

            fields = storeToColumns.getOrDefault(rid, null);
            if (fields == null) {
                // If not there, run a search to retrieve it
                log.debug("(cache miss) -- retrieving data field details for {}: {}", storeType, rid);
                if (!store.isVirtualAsset()) {
                    // For non-virtual assets the most efficient way of retrieving this information is via a search (by RID)
                    IGCSearch igcSearch = new IGCSearch();
                    IGCSearchCondition byParentId = null;
                    if (storeType.equals("database_table") || storeType.equals("view")) {
                        igcSearch.addType("database_column");
                        igcSearch.addProperties(DataStageConstants.getDataFieldSearchProperties());
                        byParentId = new IGCSearchCondition("database_table_or_view", "=", rid);
                    } else if (storeType.equals("data_file_record")) {
                        igcSearch.addType("data_file_field");
                        igcSearch.addProperties(DataStageConstants.getDataFieldSearchProperties());
                        byParentId = new IGCSearchCondition("data_file_record", "=", rid);
                    } else {
                        log.warn("Unknown source / target type -- skipping: {}", store);
                    }
                    if (byParentId != null) {
                        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(byParentId);
                        igcSearch.addConditions(conditionSet);
                        ItemList<Classificationenabledgroup> ilFields = igcRestClient.search(igcSearch);
                        fields = igcRestClient.getAllPages(null, ilFields);
                    }
                } else if (includeVirtualAssets) {
                    // For virtual assets, we must retrieve the full object and page through its fields (search by RID is not possible)
                    fields = new ArrayList<>();
                    Reference virtualStore = igcRestClient.getAssetById(rid, igcCache);
                    if (virtualStore instanceof DatabaseTable) {
                        DatabaseTable virtualTable = (DatabaseTable) virtualStore;
                        fields.addAll(getDataFieldsFromVirtualList("database_columns", virtualTable.getDatabaseColumns()));
                    } else if (virtualStore instanceof View) {
                        View virtualView = (View) virtualStore;
                        fields.addAll(getDataFieldsFromVirtualList("database_columns", virtualView.getDatabaseColumns()));
                    } else if (virtualStore instanceof DataFileRecord) {
                        DataFileRecord virtualRecord = (DataFileRecord) virtualStore;
                        fields.addAll(getDataFieldsFromVirtualList("data_file_fields", virtualRecord.getDataFileFields()));
                    } else {
                        log.warn("Unhandled case for type: {}", virtualStore.getType());
                    }
                }
                // Add them to the cache once they've been retrieved
                if (fields != null) {
                    storeToColumns.put(rid, fields);
                    if (!fields.isEmpty()) {
                        storeIdentity = fields.get(0).getIdentity(igcRestClient, igcCache).getParentIdentity();
                        String storeId = storeIdentity.getRid();
                        storeToIdentity.put(storeId, storeIdentity);
                    }
                }

            }

        }
        return fields;
    }

    /**
     * Retrieve the full list of virtual fields contained within the provided list of virtual fields.  Note that this
     * should ONLY be used for virtual assets, as it is a very expensive operation.
     *
     * @param propertyName name of the property from which the virtual fields are retrieved
     * @param virtualFields the paged list of virtual fields that has been retrieved
     * @param <T> the type of field
     * @return {@code List<Classificationenabledgroup>} containing the full list of fully-detailed virtual fields
     */
    private <T extends Classificationenabledgroup> List<Classificationenabledgroup> getDataFieldsFromVirtualList(String propertyName, ItemList<T> virtualFields) throws IGCException {
        final String methodName = "getDataFieldsFromVirtualList";
        List<Classificationenabledgroup> fullFields = null;
        if (virtualFields != null) {
            fullFields = new ArrayList<>();
                List<T> allVirtualFields = igcRestClient.getAllPages(propertyName, virtualFields);
                for (Classificationenabledgroup virtualField : allVirtualFields) {
                    Classificationenabledgroup fullField = (Classificationenabledgroup) igcRestClient.getAssetById(virtualField.getId(), igcCache);
                    fullFields.add(fullField);
                }
        }
        return fullFields == null ? Collections.emptyList() : fullFields;
    }

    /**
     * Retrieve the store identity for the provided data store RID, or null if it cannot be found.
     *
     * @param rid the data store RID for which to retrieve an identity
     * @return Identity
     */
    public Identity getStoreIdentityFromRid(String rid) {
        return storeToIdentity.getOrDefault(rid, null);
    }

    /**
     * Build up the cache with changed job information.
     */
    private void getChangedJobs() throws IGCException {

        final String methodName = "getChangedJobs";
        // First retrieve the changed jobs
        long fromTime = 0;
        long toTime = to.getTime();
        // This will not pick up jobs used in changed sequences, those are handled by the sequence processing itself
        IGCSearch igcSearch = new IGCSearch("dsjob");
        // We will only retrieve the bare minimum set of properties for the job at this point, as we will need to
        // re-retrieve the full set of details after lineage is detected
        igcSearch.addProperty("modified_on");
        IGCSearchCondition cTo = new IGCSearchCondition("modified_on", "<=", "" + toTime);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(cTo);
        if (from != null) {
            fromTime = from.getTime();
            IGCSearchCondition cFrom = new IGCSearchCondition("modified_on", ">", "" + fromTime);
            conditionSet.addCondition(cFrom);
            conditionSet.setMatchAnyCondition(false);
        }
        if (!limitToProjects.isEmpty()) {
            IGCSearchCondition cProject = new IGCSearchCondition("transformation_project.name", limitToProjects);
            conditionSet.addCondition(cProject);
            conditionSet.setMatchAnyCondition(false);
        }
        if(!limitToLabels.isEmpty()){
            IGCSearchCondition cLabels = new IGCSearchCondition("labels.name", limitToLabels);
            conditionSet.addCondition(cLabels);
            conditionSet.setMatchAnyCondition(false);
        }
        if (limitToLineageEnabled) {
            IGCSearchCondition cIncludeForLineage = new IGCSearchCondition("include_for_lineage","=","true");
            conditionSet.addCondition(cIncludeForLineage);
            conditionSet.setMatchAnyCondition(false);
        }
        log.info(" ... searching for changed jobs > {} and <= {}, limited to projects: {}, limited to lineage enabled: {}", fromTime, toTime, limitToProjects, limitToLineageEnabled);
        igcSearch.addConditions(conditionSet);
        log.debug(" ... search query object: {}", igcSearch);
        cacheChangedJobs(igcRestClient.search(igcSearch));

    }

    /**
     * Build up the cache of changed job details for use by the other methods (minimizing re-retrieval of details)
     *
     * @param jobs the changed job details to cache
     */
    private void cacheChangedJobs(ItemList<Dsjob> jobs) throws IGCException {

        final String methodName = "cacheChangedJobs";
        // This could consume significant resources (memory), which should be controlled through the proxy's
        // "batchWindowInSeconds" parameter (reducing it to a smaller number), if needed.

        for (Dsjob job : jobs.getItems()) {
                String jobRid = job.getId();
                if (!ridToJob.containsKey(jobRid)) {
                    try {
                        log.debug("Detecting lineage on job: {}", jobRid);
                        // Detect lineage on each job to ensure its details are fully populated before proceeding
                        boolean lineageDetected = igcRestClient.detectLineage(jobRid);
                        if (lineageDetected) {
                            // We then need to re-retrieve the job's details, as they may have changed since lineage
                            // detection (following call will be a no-op if the job is already in the cache)
                            getJobByRid(jobRid);
                        } else {
                            log.warn("Unable to detect lineage for job -- not including: {}", jobRid);
                        }
                    } catch (IGCException e) {
                        log.error("Failed to detect lineage for job -- not including: {}, check system log for exception details.", jobRid, e);
                    }
                }
        }
        if (jobs.hasMorePages()) {
            ItemList<Dsjob> nextPage = igcRestClient.getNextPage(null, jobs);
            cacheChangedJobs(nextPage);
        }

    }

}
