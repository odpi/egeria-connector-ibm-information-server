/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.LineageMode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Dsjob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationGovernanceRule;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.openmetadata.accessservices.dataengine.model.Engine;
import org.odpi.openmetadata.accessservices.dataengine.model.DataFlow;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.odpi.openmetadata.accessservices.dataengine.model.ProcessHierarchy;
import org.odpi.openmetadata.accessservices.dataengine.model.Referenceable;
import org.odpi.openmetadata.accessservices.dataengine.model.SchemaType;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.odpi.openmetadata.frameworks.connectors.properties.EndpointProperties;
import org.odpi.openmetadata.governanceservers.dataengineproxy.connectors.DataEngineConnectorBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStageConnector extends DataEngineConnectorBase {

    private static final Logger log = LoggerFactory.getLogger(DataStageConnector.class);

    private static final String SYNC_RULE_PREFIX = "Job metadata will be synced through Egeria";
    private static final String SYNC_RULE_DESC = "GENERATED -- DO NOT UPDATE: last synced at ";

    private final SimpleDateFormat syncDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private IGCRestClient igcRestClient;
    private Engine dataEngine;

    private DataStageCache dataStageCache;
    private List<ProcessHierarchy> processHierarchies;

    private boolean includeVirtualAssets = true;
    private boolean createDataStoreSchemas = false;
    private List<String> limitToProjects;
    private List<String> limitToLabels;
    private boolean limitToLineageEnabledJobs = false;

    private LineageMode mode = LineageMode.GRANULAR;

    /**
     * Default constructor used by the OCF Connector Provider.
     */
    public DataStageConnector() {
        limitToProjects = new ArrayList<>();
        limitToLabels = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(String               connectorInstanceId,
                           ConnectionProperties connectionProperties) {
        super.initialize(connectorInstanceId, connectionProperties);
        this.dataStageCache = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized void start() throws ConnectorCheckedException {

        super.start();
        final String methodName = "start";

        log.info("Initializing " + this.getClass().getSimpleName() + "...");

        EndpointProperties endpointProperties = connectionProperties.getEndpoint();
        if (endpointProperties == null) {
            ConnectorHelper.raiseConnectorCheckedException(this.getClass(), methodName, null, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition());
        } else {
            String address = endpointProperties.getAddress();
            if (address == null || address.length() == 0) {
                ConnectorHelper.raiseConnectorCheckedException(this.getClass(), methodName, null, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition(address));
            } else {

                String igcUser = connectionProperties.getUserId();
                String igcPass = connectionProperties.getClearPassword();

                Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
                Integer igcPage = null;
                if (proxyProperties != null) {
                    igcPage = (Integer) proxyProperties.get(DataStageConnectorProvider.PAGE_SIZE);
                    includeVirtualAssets = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.INCLUDE_VIRTUAL_ASSETS, true);
                    createDataStoreSchemas = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.CREATE_DATA_STORE_SCHEMAS, false);
                    Object projects = proxyProperties.getOrDefault(DataStageConnectorProvider.LIMIT_TO_PROJECTS, null);
                    if (projects instanceof String) {
                        limitToProjects.add((String)projects);
                    } else if (projects != null) {
                        limitToProjects.addAll((List<String>)projects);
                    }
                    Object labels = proxyProperties.getOrDefault(DataStageConnectorProvider.LIMIT_TO_LABELS, null);
                    if (labels instanceof String) {
                        limitToLabels.add((String)labels);
                    } else if (labels != null) {
                        limitToLabels.addAll((List<String>)labels);
                    }
                    limitToLineageEnabledJobs = (Boolean) proxyProperties.getOrDefault(DataStageConnectorProvider.LIMIT_TO_LINEAGE_ENABLED_JOBS, limitToLineageEnabledJobs);
                    Object lineageMode = proxyProperties.getOrDefault(DataStageConnectorProvider.MODE, null);
                    if (lineageMode != null) {
                        try {
                            mode = LineageMode.valueOf((String) lineageMode);
                        } catch (IllegalArgumentException e) {
                            log.warn("Lineage mode specified in the configuration is not a known value.", e);
                        }
                    }
                }

                try {
                    igcRestClient = new IGCRestClient("https://" + address, igcUser, igcPass);
                    dataEngine = new Engine();
                    ConnectorHelper.connectIGC(this.getClass(), igcRestClient, dataEngine, address, igcPage);
                } catch (IGCException e) {
                    ConnectorHelper.raiseConnectorCheckedException(this.getClass(), methodName, e, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition(address));
                }

            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void disconnect() throws ConnectorCheckedException {
        final String methodName = "disconnect";
        try {
            // Close the session on the IGC REST client
            this.igcRestClient.disconnect();
        } catch (IGCException e) {
            ConnectorHelper.raiseConnectorCheckedException(this.getClass(), methodName, e, DataStageErrorCode.CONNECTION_FAILURE.getMessageDefinition());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Engine getDataEngineDetails() { return dataEngine; }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProcessingStateSyncKey() {
        String labels = String.join(", ", limitToLabels);
        String projects = String.join(", ", limitToProjects);
        return "by projects, mode = " + mode.getName() + ", limitToLabels = [" + labels + "], limitToProjects = [" + projects + "], " +
                "limitToLineageEnabledJobs = " + limitToLineageEnabledJobs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Date getChangesLastSynced()  throws ConnectorCheckedException, PropertyServerException {
        final String methodName = "getChangesLastSynced";
        try {
            InformationGovernanceRule jobSyncRule = getJobSyncRule();
            Date lastSync = null;
            if (jobSyncRule != null) {
                String description = jobSyncRule.getShortDescription();
                String dateString = description.substring(SYNC_RULE_DESC.length());
                try {
                    lastSync = syncDateFormat.parse(dateString);
                } catch (ParseException e) {
                    log.error("Unable to parse date and time of last sync from rule '{}' ({}) using format: {}", description, dateString, syncDateFormat.toPattern(), e);
                    ConnectorHelper.raiseConnectorCheckedException(this.getClass(), methodName, e, DataStageErrorCode.UNKNOWN_RUNTIME_ERROR.getMessageDefinition());
                }
            }
            return lastSync;
        } catch (IGCException e) {
            ConnectorHelper.handleIGCException(this.getClass().getName(), this.getClass().getEnclosingMethod().getName(), e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setChangesLastSynced(Date time) throws ConnectorCheckedException, PropertyServerException {

        final String methodName = "setChangesLastSynced";
        boolean success = false;
        try {
            InformationGovernanceRule exists = getJobSyncRule();
            String newDescription = SYNC_RULE_DESC + syncDateFormat.format(time);
            if (exists == null) {
                // Create the entry
                IGCCreate igcCreate = new IGCCreate("information_governance_rule");
                igcCreate.addProperty(DataStageConstants.NAME, getJobSyncRuleName());
                igcCreate.addProperty(DataStageConstants.SHORT_DESCRIPTION, newDescription);
                success = igcRestClient.create(igcCreate) != null;
            } else {
                // Update the entry
                IGCUpdate igcUpdate = new IGCUpdate(exists.getId());
                igcUpdate.addProperty(DataStageConstants.SHORT_DESCRIPTION, newDescription);
                success = igcRestClient.update(igcUpdate);
            }
        } catch (IGCException e) {
            ConnectorHelper.handleIGCException(this.getClass().getName(), methodName, e);
        }
        if (!success) {
            ConnectorHelper.raiseConnectorCheckedException(this.getClass(),methodName, null, DataStageErrorCode.SYNC_TIME_UPDATE_FAILURE.getMessageDefinition());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Date getOldestChangeSince(Date time) throws ConnectorCheckedException, PropertyServerException {
        final String methodName = "getOldestChangeSince";
        try {
        Dsjob oldest = getOldestJobSince(time);
            if (oldest != null) {
                return oldest.getModifiedOn();
            }
        } catch (IGCException e) {
            ConnectorHelper.handleIGCException(this.getClass().getName(), methodName, e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SchemaType> getChangedSchemaTypes(Date from, Date to) throws ConnectorCheckedException, PropertyServerException {

        final String methodName = "getChangedSchemaTypes";
        log.debug("Looking for changed SchemaTypes...");
        Map<String, SchemaType> schemaTypeMap = new HashMap<>();

        if (mode == LineageMode.JOB_LEVEL) {
            return Collections.emptyList();
        }

        try {

            initializeCache(from, to);
            schemaTypeMap = ConnectorHelper.mapChangedSchemaTypes(dataStageCache, includeVirtualAssets, createDataStoreSchemas);

        } catch (IGCException e) {
            ConnectorHelper.handleIGCException(this.getClass().getName(), methodName, e);
        }
        return new ArrayList<>(schemaTypeMap.values());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<? super Referenceable> getChangedDataStores(Date from, Date to) throws ConnectorCheckedException, PropertyServerException {

        final String methodName = "getChangedDataStores";
        log.debug("Looking for changed DataStores...");
        Map<String, ? super Referenceable> dataStoreMap = new HashMap<>();

        try {

            initializeCache(from, to);
            dataStoreMap = ConnectorHelper.mapChangedDataStores(dataStageCache, includeVirtualAssets);

        } catch (IGCException e) {
            ConnectorHelper.handleIGCException(this.getClass().getName(), methodName, e);
        }
        return new ArrayList<>(dataStoreMap.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Process> getChangedProcesses(Date from, Date to) throws ConnectorCheckedException, PropertyServerException {

        final String methodName = "getChangedProcesses";
        List<Process> processes = new ArrayList<>();

        try {
            initializeCache(from, to);
            processes = ConnectorHelper.mapChangedProcesses(dataStageCache, processHierarchies, mode);

        } catch (IGCException e) {
            ConnectorHelper.handleIGCException(this.getClass().getName(), methodName, e);
        }

        return processes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProcessHierarchy> getChangedProcessHierarchies(Date from, Date to)  {
        // Output list of changed process hierarchies from the cached information
        return processHierarchies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataFlow> getChangedDataFlows(Date from, Date to) {
        // do nothing -- data flows will always be handled by other methods
        return Collections.emptyList();
    }

    /**
     * Initialize the cache of changed job details based on the provided dates and times.
     *
     * @param from the date and time from which to cache changes (exclusive)
     * @param to the date and time up to which to cache changes (inclusive)
     */
    private void initializeCache(Date from, Date to) throws IGCException {
        DataStageCache forComparison = new DataStageCache(from, to, mode, limitToProjects, limitToLabels, limitToLineageEnabledJobs,
                includeVirtualAssets);
        if (dataStageCache == null || !dataStageCache.equals(forComparison)) {
            // Initialize the cache, if it is empty, or reset it if it differs from the dates and times we've been given
            dataStageCache = forComparison;
            dataStageCache.initialize(igcRestClient);
            processHierarchies = new ArrayList<>();
        }
    }

    /**
     * Retrieve the rule that represents the job sync with Data Engine OMAS.
     *
     * @return InformationGovernanceRule
     */
    private InformationGovernanceRule getJobSyncRule() throws IGCException {

        final String methodName = "getJobSyncRule";
        IGCSearch igcSearch = new IGCSearch("information_governance_rule");
        igcSearch.addProperty(DataStageConstants.SHORT_DESCRIPTION);
        IGCSearchCondition condition = new IGCSearchCondition(DataStageConstants.NAME, "=", getJobSyncRuleName());
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);

        ItemList<InformationGovernanceRule> results = igcRestClient.search(igcSearch);
        return (results == null || results.getPaging().getNumTotal() == 0) ? null : results.getItems().get(0);

    }

    /**
     * Construct the name of a unique rule to capture the last time jobs were sync'd for a combination of filtered
     * projects (and mode) to the Data Engine OMAS.
     *
     * @return String
     */
    private String getJobSyncRuleName() {
        String ruleName = SYNC_RULE_PREFIX + " (" + mode.getName() + ")";
        if (limitToProjects.size() > 0) {
            ruleName += " projects: [" + String.join(",", limitToProjects) + "]";
        }
        if (limitToLabels.size() > 0) {
            ruleName += " labels: [" + String.join(",", limitToLabels) + "]";
        }
        return ruleName;
    }

    /**
     * Retrieve the oldest job (modified_on date furthest in the past) since the provided time.
     * @param time from which to consider changes
     * @return Dsjob
     */
    private Dsjob getOldestJobSince(Date time) throws  IGCException {
        final String methodName = "getOldestJobSince";
        long startFrom = 0;
        if (time != null) {
            startFrom = time.getTime();
        }
        IGCSearch igcSearch = new IGCSearch("dsjob");
        igcSearch.addProperty("modified_on");
        IGCSearchCondition condition = new IGCSearchCondition("modified_on", ">", "" + startFrom);
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        if (limitToProjects.size() > 0) {
            IGCSearchCondition cProject = new IGCSearchCondition("transformation_project.name", limitToProjects);
            conditionSet.addCondition(cProject);
            conditionSet.setMatchAnyCondition(false);
        }
        if(limitToLineageEnabledJobs) {
            IGCSearchCondition cIncludeForLineage = new IGCSearchCondition("include_for_lineage","=","true");
            conditionSet.addCondition(cIncludeForLineage);
            conditionSet.setMatchAnyCondition(false);
        }
        if(limitToLabels.size() > 0){
            IGCSearchCondition cLabels = new IGCSearchCondition("labels.name", limitToLabels);
            conditionSet.addCondition(cLabels);
            conditionSet.setMatchAnyCondition(false);
        }
        igcSearch.addConditions(conditionSet);
        IGCSearchSorting sorting = new IGCSearchSorting("modified_on", true);
        igcSearch.addSortingCriteria(sorting);
        igcSearch.setPageSize(2); // No need to get any more than 2 results, as we will only take the top result anyway

        ItemList<Dsjob> results = igcRestClient.search(igcSearch);
        return (results == null || results.getPaging().getNumTotal() == 0) ? null : results.getItems().get(0);
    }

}
