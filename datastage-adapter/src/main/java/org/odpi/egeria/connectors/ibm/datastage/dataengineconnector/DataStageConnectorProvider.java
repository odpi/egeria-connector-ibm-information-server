/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.openmetadata.frameworks.connectors.properties.beans.ConnectorType;
import org.odpi.openmetadata.governanceservers.dataengineproxy.connectors.DataEngineConnectorProviderBase;

import java.util.ArrayList;
import java.util.List;

/**
 * In the Open Connector Framework (OCF), a ConnectorProvider is a factory for a specific type of connector.
 * The DataStageConnectorProvider is the connector provider for the DataStageConnector.
 * It extends DataEngineConnectorProvider which in turn extends the OCF ConnectorProviderBase.
 * ConnectorProviderBase supports the creation of connector instances.
 * <br><br>
 * The DataStageConnectorProvider must initialize ConnectorProviderBase with the Java class
 * name of the OMRS Connector implementation (by calling super.setConnectorClassName(className)).
 * Then the connector provider will work.
 * <br><br>
 * The permitted configuration options include:
 * <ul>
 *     <li>pageSize - an integer giving the number of results to include in each page of processing</li>
 *     <li>includeVirtualAssets - a boolean that indicates whether to include the creation of schemas for virtual
 *          assets (when true) or not (when false). By default this will be enabled (true) to ensure that virtual
 *          assets that might exist in lineage are still included (as IGC alone does not communicate any information
 *          about virtual assets).</li>
 *     <li>createDataStoreSchemas - a boolean that indicates whether to include the creation of data store-level schemas
 *          (when true) or not (when false). When the DataStage connector is used alone in a cohort, without an IGC
 *          proxy also running in the cohort, this should be set to true to ensure that the data stores used as sources
 *          or targets by DataStage exist in lineage. If an IGC proxy is also being used in the cohort, this should
 *          be left at the default value (false) to ensure that the IGC proxy remains the home metadata collection of
 *          data store entities and is responsible for notifications of their changes, etc.</li>
 * </ul>
 */
public class DataStageConnectorProvider extends DataEngineConnectorProviderBase {

    private static final String CONNECTOR_TYPE_GUID = "f71e6c48-fa06-4016-8437-7f0e8efcfb39";
    private static final String CONNECTOR_TYPE_NAME = "DataStage Data Engine Connector";
    private static final String CONNECTOR_TYPE_DESC = "DataStage Data Engine Connector that processes job information from the IBM DataStage ETL engine.";

    static final String PAGE_SIZE = "pageSize";
    static final String INCLUDE_VIRTUAL_ASSETS = "includeVirtualAssets";
    static final String CREATE_DATA_STORE_SCHEMAS = "createDataStoreSchemas";
    static final String LIMIT_TO_PROJECTS = "limitToProjects";

    /**
     * Constructor used to initialize the ConnectorProviderBase with the Java class name of the specific
     * OMRS Connector implementation.
     */
    public DataStageConnectorProvider() {

        Class<?> connectorClass = DataStageConnector.class;
        super.setConnectorClassName(connectorClass.getName());

        ConnectorType connectorType = new ConnectorType();
        connectorType.setType(ConnectorType.getConnectorTypeType());
        connectorType.setGUID(CONNECTOR_TYPE_GUID);
        connectorType.setQualifiedName(CONNECTOR_TYPE_NAME);
        connectorType.setDisplayName(CONNECTOR_TYPE_NAME);
        connectorType.setDescription(CONNECTOR_TYPE_DESC);
        connectorType.setConnectorProviderClassName(this.getClass().getName());

        List<String> recognizedConfigurationProperties = new ArrayList<>();
        recognizedConfigurationProperties.add(PAGE_SIZE);
        recognizedConfigurationProperties.add(INCLUDE_VIRTUAL_ASSETS);
        recognizedConfigurationProperties.add(CREATE_DATA_STORE_SCHEMAS);
        recognizedConfigurationProperties.add(LIMIT_TO_PROJECTS);
        connectorType.setRecognizedConfigurationProperties(recognizedConfigurationProperties);

        super.connectorTypeBean = connectorType;

    }

}
