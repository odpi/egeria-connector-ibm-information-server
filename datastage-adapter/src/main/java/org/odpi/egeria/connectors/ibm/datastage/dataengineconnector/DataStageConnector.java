/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DSJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBase;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class DataStageConnector extends ConnectorBase {

    private static final Logger log = LoggerFactory.getLogger(DataStageConnector.class);

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

    ReferenceList getChangedJobs(Date since) {
        IGCSearch igcSearch = new IGCSearch("dsjob");
        igcSearch.addProperties(DSJob.getSearchProperties());
        IGCSearchCondition condition = new IGCSearchCondition("modified_on", ">=", "" + since.getTime());
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        igcSearch.addConditions(conditionSet);
        ReferenceList changedJobs = igcRestClient.search(igcSearch);
        changedJobs.getAllPages(igcRestClient);
        return changedJobs;
    }

    /*Date getJobChangesLastSynced() {
        IGCSearch igcSearch = new IGCSearch("collection");
        igcSearch.addProperty("short_description");
        IGCSearchCondition condition = new IGCSearchCondition("name", "=", "???");
    }*/

}
