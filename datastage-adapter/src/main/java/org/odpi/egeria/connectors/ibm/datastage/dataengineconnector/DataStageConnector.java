/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBase;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// TODO: swap to extending DataEngineConnectorBase (once in Maven)
public class DataStageConnector extends ConnectorBase {

    private static final Logger log = LoggerFactory.getLogger(DataStageConnector.class);

    public static final String SYNC_RULE_NAME = "Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS";
    public static final String SYNC_RULE_DESC = "GENERATED -- DO NOT UPDATE: last synced at ";
    private static final SimpleDateFormat SYNC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final int POLL_INTERVAL_IN_SECONDS = 15;

    private IGCRestClient igcRestClient;
    private IGCVersionEnum igcVersion;

    private Date jobChangesLastSynced;

    /**
     * Default constructor used by the OCF Connector Provider.
     */
    public DataStageConnector() {
        // Nothing to do...
    }

    /**
     * Call made by the ConnectorProvider to initialize the Connector with the base services.
     *
     * @param connectorInstanceId   unique id for the connector instance useful for messages etc
     * @param connectionProperties   POJO for the configuration used to create the connector.
     */
    @Override
    public void initialize(String               connectorInstanceId,
                           ConnectionProperties connectionProperties) {
        super.initialize(connectorInstanceId, connectionProperties);

        final String methodName = "initialize";
        log.info("Initializing DataStageDataEngineConnector...");
        if (log.isDebugEnabled()) { log.debug("Initializing DataStageDataEngineConnector..."); }

        // Note: it is not currently possible to try to pull these from a separate IGC proxy instance, so we need to
        // ask for these same details on this connector as well
        Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
        String igcHost = (String) proxyProperties.get("ibm.igc.services.host");
        String igcPort = (String) proxyProperties.get("ibm.igc.services.port");
        String igcUser = (String) proxyProperties.get("ibm.igc.username");
        String igcPass = (String) proxyProperties.get("ibm.igc.password");
        Integer igcPage = (Integer) proxyProperties.get("ibm.igc.pagesize");

        // Create new REST API client (opens a new session)
        this.igcRestClient = new IGCRestClient(igcHost, igcPort, igcUser, igcPass);
        if (this.igcRestClient.isSuccessfullyInitialised()) {
            // Set the version based on the IGC client's auto-determination of the IGC environment's version
            this.igcVersion = this.igcRestClient.getIgcVersion();
            // Set the default page size to whatever is provided as part of config parameters (default to 100)
            if (igcPage != null) {
                this.igcRestClient.setDefaultPageSize(igcPage);
            } else {
                this.igcRestClient.setDefaultPageSize(100);
            }
            // Try to read the date of the last job sync
            jobChangesLastSynced = getJobChangesLastSynced();
            if (jobChangesLastSynced == null) {
                // If non-existent, try to sync everything since now...
                jobChangesLastSynced = new Date();
            }
        }

    }

    /**
     * Indicates that the connector is completely configured and can begin processing.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    @Override
    public void start() throws ConnectorCheckedException {
        super.start();
        new Thread(new DataStagePollThread()).start();
    }

    /**
     * Class to support separate-threaded processing of DataStage jobs.
     */
    private class DataStagePollThread implements Runnable {

        /**
         * Poll for DataStage job changes.
         */
        @Override
        public void run() {

            log.info("Starting DataStage polling thread.");

            while (true) {
                try {
                    log.debug("Polling for changed DataStage jobs...");
                    ReferenceList changedJobs = getChangedJobs(jobChangesLastSynced);
                    processChangedJobs(changedJobs);
                    while (changedJobs.hasMorePages()) {
                        changedJobs.getNextPage(igcRestClient);
                        processChangedJobs(changedJobs);
                    }
                    if (!saveJobChangesSyncTime(jobChangesLastSynced)) {
                        log.error("There was a problem updating the last sync time -- will revert to previous sync time at next synchronization.");
                    }
                    Thread.sleep(POLL_INTERVAL_IN_SECONDS * 1000);
                } catch (InterruptedException e) {
                    log.error("Thread was interrupted.", e);
                    break;
                }
            }
        }

    }

    private void processChangedJobs(ReferenceList changedJobs) {
        List<Reference> jobList = changedJobs.getItems();
        for (Reference job : jobList) {
            DSJob detailedJob = getJobDetails(job);
            loadProcessesForEachStage(detailedJob);
            loadProcessForJob(detailedJob);
            loadProcessForSequence(detailedJob);
        }
    }

    private void loadProcessesForEachStage(DSJob job) {
        log.info("Load processes for each stage...");
        // TODO: implement once we have a DE OMAS bean for processes to code against
    }

    private void loadProcessForJob(DSJob job) {
        if (job.getType() == DSJob.JobType.JOB) {
            log.info("Load process for job...");
            // TODO: implement once we have a DE OMAS bean for processes to code against
        }
    }

    private void loadProcessForSequence(DSJob job) {
        if (job.getType() == DSJob.JobType.SEQUENCE) {
            log.info("Load process for sequence...");
            // TODO: implement once we have a DE OMAS bean for processes to code against
        }
    }

    /**
     * Retrieve the version identifier of the IGC environment.
     *
     * @return IGCVersionEnum
     */
    public IGCVersionEnum getIGCVersion() {
        return this.igcVersion;
    }

    /**
     * Free up any resources held since the connector is no longer needed.
     */
    @Override
    public void disconnect() {
        // Close the session on the IGC REST client
        this.igcRestClient.disconnect();
    }


    /**
     * Access the IGC REST API client directly.
     *
     * @return IGCRestClient
     */
    public IGCRestClient getIGCRestClient() { return this.igcRestClient; }

    // TODO: Mark as an Override and provide additional arguments once available
    public void sendProcess() {

    }

    /**
     * Retrieve a listing of jobs that have been modified since the provided date and time.
     *
     * @param since the date and time from which to look for changed jobs
     * @return ReferenceList
     */
    public ReferenceList getChangedJobs(Date since) {
        IGCSearch igcSearch = new IGCSearch("dsjob");
        igcSearch.addProperties(DSJob.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("modified_on", ">=", "" + since.getTime());
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList changedJobs = igcRestClient.search(igcSearch);
        return changedJobs;
    }

    /**
     * Retrieve all of the details about the provided DataStage job.
     *
     * @param job the DataStage job for which to retrieve details
     * @return DSJob
     */
    public DSJob getJobDetails(Reference job) {

        String jobRid = job.getId();
        ReferenceList stages = getStageDetailsForJob(jobRid);
        ReferenceList links = getLinkDetailsForJob(jobRid);
        ReferenceList stageCols = getStageColumnDetailsForLinks(jobRid);

        Map<String, ReferenceList> dataStoreDetailsMap = new HashMap<>();
        mapDataStoreDetailsForJob(job, "reads_from_(design)", dataStoreDetailsMap);
        mapDataStoreDetailsForJob(job, "writes_to_(design)", dataStoreDetailsMap);

        // Flatten the list of data store details
        List<Reference> dataStoreDetails = new ArrayList<>();
        for (ReferenceList referenceList : dataStoreDetailsMap.values()) {
            for (Reference item : referenceList.getItems()) {
                dataStoreDetails.add(item);
            }
        }

        return new DSJob(igcRestClient, job, stages, links, stageCols, dataStoreDetails);

    }

    /**
     * Retrieve a listing of the stages within a particular DataStage job.
     *
     * @param jobRid the RID of the job for which to retrieve stage details
     * @return ReferenceList
     */
    public ReferenceList getStageDetailsForJob(String jobRid) {
        IGCSearch igcSearch = new IGCSearch("stage");
        igcSearch.addProperties(DSStage.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList stages = igcRestClient.search(igcSearch);
        stages.getAllPages(igcRestClient);
        return stages;
    }

    /**
     * Retrieve a listing of the links within a particular DataStage job.
     *
     * @param jobRid the RID of the job for which to retrieve link details
     * @return ReferenceList
     */
    public ReferenceList getLinkDetailsForJob(String jobRid) {
        IGCSearch igcSearch = new IGCSearch("link");
        igcSearch.addProperties(DSLink.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList links = igcRestClient.search(igcSearch);
        links.getAllPages(igcRestClient);
        return links;
    }

    /**
     * Retrieve a listing of the stage columns within a particular DataStage job.
     *
     * @param jobRid the RID of the job for which to retrieve stage column details
     * @return ReferenceList
     */
    public ReferenceList getStageColumnDetailsForLinks(String jobRid) {
        IGCSearch igcSearch = new IGCSearch("ds_stage_column");
        igcSearch.addProperties(DSStageColumn.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("link.job_or_container", "=", jobRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList stageCols = igcRestClient.search(igcSearch);
        stageCols.getAllPages(igcRestClient);
        return stageCols;
    }

    /**
     * Retrieve a listing of database columns within a particular database table.
     *
     * @param tableRid the RID of the database table for which to retrieve column details
     * @return ReferenceList
     */
    public ReferenceList getColumnsForTable(String tableRid) {
        IGCSearch igcSearch = new IGCSearch("database_column");
        igcSearch.addProperties(DatabaseColumn.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("database_table_or_view", "=", tableRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList columns = igcRestClient.search(igcSearch);
        columns.getAllPages(igcRestClient);
        return columns;
    }

    /**
     * Retrieve a listing of file fields within a particular file record.
     *
     * @param recordRid the RID of the data file record for which to retrieve field details
     * @return ReferenceList
     */
    public ReferenceList getFieldsForRecord(String recordRid) {
        IGCSearch igcSearch = new IGCSearch("data_file_field");
        igcSearch.addProperties(DataFileField.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("data_file_record", "=", recordRid);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList fields = igcRestClient.search(igcSearch);
        fields.getAllPages(igcRestClient);
        return fields;
    }

    /**
     * Map the data store details used by the provided job into the provided map.
     *
     * @param job the job for which to map data store details
     * @param relationshipProperty the relationship property from which to retrieve data store information
     * @param dataStoreDetailsMap the map into which to place the results
     */
    private void mapDataStoreDetailsForJob(Reference job, String relationshipProperty, Map<String, ReferenceList> dataStoreDetailsMap) {
        ReferenceList candidates = (ReferenceList) igcRestClient.getPropertyByName(job, relationshipProperty);
        List<Reference> candidateList = candidates.getItems();
        for (Reference candidate : candidateList) {
            String candidateId = candidate.getId();
            if (!dataStoreDetailsMap.containsKey(candidateId)) {
                dataStoreDetailsMap.put(candidateId, getDataFieldDetails(candidate));
            }
        }
    }

    /**
     * Retrieve a listing of details of the fields contained within the provided data store.
     *
     * @param datastore the data store for which to retrieve detailed field-level information
     * @return ReferenceList
     */
    private ReferenceList getDataFieldDetails(Reference datastore) {

        ReferenceList results = null;
        String type = datastore.getType();
        switch(datastore.getType()) {
            case "database_table":
            case "view":
                results = getColumnsForTable(datastore.getId());
                break;
            case "data_file_record":
                results = getFieldsForRecord(datastore.getId());
                break;
            default:
                if (log.isWarnEnabled()) { log.warn("Unknown source / target type -- skipping: {}", datastore); }
                break;
        }
        return results;

    }

    /**
     * Retrieve the rule that represents the job sync with Data Engine OMAS.
     *
     * @return Reference
     */
    private Reference getJobSyncRule() {
        IGCSearch igcSearch = new IGCSearch("information_governance_rule");
        igcSearch.addProperty("short_description");
        IGCSearchCondition condition = new IGCSearchCondition("name", "=", SYNC_RULE_NAME);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList results = igcRestClient.search(igcSearch);
        return results == null ? null : results.getItems().get(0);
    }

    /**
     * Retrieve the date and time that job information was last synchronized.
     *
     * @return Date
     */
    private Date getJobChangesLastSynced() {
        Reference jobSyncRule = getJobSyncRule();
        Date lastSync = null;
        if (jobSyncRule != null) {
            String description = (String) igcRestClient.getPropertyByName(jobSyncRule, "short_description");
            String dateString = description.substring(SYNC_RULE_DESC.length());
            try {
                lastSync = SYNC_DATE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                log.error("Unable to parse date and time of last sync from rule: {} ({})", description, dateString);
            }
        }
        return lastSync;
    }

    /**
     * Save the time the jobs were last synced.
     *
     * @param syncTime the date and time of the last synchronization
     * @return boolean - indicating success (true) or failure (false) of persisting the time
     */
    private boolean saveJobChangesSyncTime(Date syncTime) {
        Reference exists = getJobSyncRule();
        String newDescription = SYNC_RULE_DESC + SYNC_DATE_FORMAT.format(jobChangesLastSynced);
        if (exists == null) {
            // Create the entry
            IGCCreate igcCreate = new IGCCreate("information_governance_rule");
            igcCreate.addProperty("name", SYNC_RULE_NAME);
            igcCreate.addProperty("short_description", newDescription);
            return igcRestClient.create(igcCreate);
        } else {
            // Update the entry
            IGCUpdate igcUpdate = new IGCUpdate(exists.getId());
            igcUpdate.addProperty("short_description", newDescription);
            return igcRestClient.update(igcUpdate);
        }
    }

}
