/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.openmetadata.frameworks.connectors.properties.beans.ConnectorType;
import org.odpi.openmetadata.openconnectors.governancedaemonconnectors.dataengineproxy.DataEngineConnectorProviderBase;

import java.util.ArrayList;
import java.util.List;

/**
 * In the Open Connector Framework (OCF), a ConnectorProvider is a factory for a specific type of connector.
 * The DataStageConnectorProvider is the connector provider for the DataStageConnector.
 * It extends DataEngineConnectorProvider which in turn extends the OCF ConnectorProviderBase.
 * ConnectorProviderBase supports the creation of connector instances.
 * <p>
 * The DataStageConnectorProvider must initialize ConnectorProviderBase with the Java class
 * name of the OMRS Connector implementation (by calling super.setConnectorClassName(className)).
 * Then the connector provider will work.
 */
public class DataStageConnectorProvider extends DataEngineConnectorProviderBase {

    private static final String CONNECTOR_TYPE_GUID = "f71e6c48-fa06-4016-8437-7f0e8efcfb39";
    private static final String CONNECTOR_TYPE_NAME = "DataStage Data Engine Connector";
    private static final String CONNECTOR_TYPE_DESC = "DataStage Data Engine Connector that processes job information from the IBM DataStage ETL engine.";

    private static final String PAGE_SIZE = "pageSize";

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
        connectorType.setRecognizedConfigurationProperties(recognizedConfigurationProperties);

        super.connectorTypeBean = connectorType;

    }

}
