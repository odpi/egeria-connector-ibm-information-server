/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Dsjob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Utility class to cache DataStage information for use by multiple steps in the Data Engine processing.
 */
public class DataStageCache {

    private static final Logger log = LoggerFactory.getLogger(DataStageCache.class);

    private Map<String, DataStageJob> ridToJob;

    private IGCRestClient igcRestClient;
    private Date from;
    private Date to;

    /**
     * Create a new cache for changes between the times provided.
     *
     * @param from the date and time from which to cache changes
     * @param to the date and time until which to cache changes
     */
    public DataStageCache(Date from, Date to) {
        this.ridToJob = new HashMap<>();
        this.from = from;
        this.to = to;
    }

    /**
     * Populate the cache.
     *
     * @param igcRestClient connectivity to the IGC environment
     */
    public void initialize(IGCRestClient igcRestClient) {
        this.igcRestClient = igcRestClient;
        getChangedJobs();
    }

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
     * Retrieve a single cached job based on its Repository ID (RID).
     *
     * @param rid the Repository ID (RID) of the job to retrieve
     * @return DataStageJob
     */
    public DataStageJob getCachedJobById(String rid) {
        return ridToJob.getOrDefault(rid, null);
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
     * Build up the cache with changed job information.
     */
    private void getChangedJobs() {

        // First retrieve the changed jobs
        long fromTime = 0;
        long toTime = to.getTime();
        // TODO: may need to modify search criteria for job retrieval to pick up jobs used in changed sequences
        IGCSearch igcSearch = new IGCSearch("dsjob");
        igcSearch.addProperties(DataStageConstants.getJobSearchProperties());
        IGCSearchCondition cTo = new IGCSearchCondition("modified_on", "<=", "" + toTime);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(cTo);
        if (from != null) {
            fromTime = from.getTime();
            IGCSearchCondition cFrom = new IGCSearchCondition("modified_on", ">", "" + fromTime);
            conditionSet.addCondition(cFrom);
            conditionSet.setMatchAnyCondition(false);
        }
        log.info(" ... searching for changed jobs > {} and <= {}", fromTime, toTime);
        igcSearch.addConditions(conditionSet);
        cacheChangedJobs(igcRestClient.search(igcSearch));

    }

    /**
     * Build up the cache of changed job details for use by the other methods (minimizing re-retrieval of details)
     *
     * @param jobs the changed job details to cache
     */
    private void cacheChangedJobs(ItemList<Dsjob> jobs) {

        // TODO: need to keep an eye on how fast this may consume memory, and may need to determine some other way
        //  to batch up
        for (Dsjob job : jobs.getItems()) {
            String jobRid = job.getId();
            if (!ridToJob.containsKey(jobRid)) {
                ridToJob.put(jobRid, new DataStageJob(igcRestClient, job));
            }
        }
        if (jobs.hasMorePages()) {
            jobs.getNextPage(igcRestClient);
            cacheChangedJobs(jobs);
        }

    }

}
