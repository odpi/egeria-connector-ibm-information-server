/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.openmetadata.frameworks.connectors.properties.beans.ConnectorType;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryConnectorProviderBase;

import java.util.ArrayList;
import java.util.List;

/**
 * In the Open Connector Framework (OCF), a ConnectorProvider is a factory for a specific type of connector.
 * The IGCOMRSRepositoryConnectorProvider is the connector provider for the IGCOMRSRepositoryConnector.
 * It extends OMRSRepositoryConnectorProviderBase which in turn extends the OCF ConnectorProviderBase.
 * ConnectorProviderBase supports the creation of connector instances.
 * <p>
 * The IGCOMRSRepositoryConnectorProvider must initialize ConnectorProviderBase with the Java class
 * name of the OMRS Connector implementation (by calling super.setConnectorClassName(className)).
 * Then the connector provider will work.
 * <br><br>
 * The permitted configuration options include:
 * <ul>
 *     <li>defaultZones - a list of strings defining the default zones that should be applied to all assets homed
 *          in this repository proxy's metadata collection.</li>
 *     <li>ignoreUnmappedInstances - a boolean indicating whether to ignore instances that are not mapped (will log
 *          a warning, but not throw any exception when set to 'true') or to throw an exception if an unmapped instance
 *          is retrieved (when set to 'false').</li>
 * </ul>
 */
public class IGCOMRSRepositoryConnectorProvider extends OMRSRepositoryConnectorProviderBase {

    static final String CONNECTOR_TYPE_GUID = "62cd0d10-4e72-4d75-9fdc-e9b783f98d80";
    static final String CONNECTOR_TYPE_NAME = "OMRS IGC Repository Connector";
    static final String CONNECTOR_TYPE_DESC = "OMRS IGC Repository Connector that processes events from the IBM InfoSphere Information Governance Catalog repository store.";

    public static final String DEFAULT_ZONES = "defaultZones";
    public static final String IGNORE_UNMAPPED_INSTANCES = "ignoreUnmappedInstances";

    /**
     * Constructor used to initialize the ConnectorProviderBase with the Java class name of the specific
     * OMRS Connector implementation.
     */
    public IGCOMRSRepositoryConnectorProvider() {

        Class<?> connectorClass = IGCOMRSRepositoryConnector.class;
        super.setConnectorClassName(connectorClass.getName());

        ConnectorType connectorType = new ConnectorType();
        connectorType.setType(ConnectorType.getConnectorTypeType());
        connectorType.setGUID(CONNECTOR_TYPE_GUID);
        connectorType.setQualifiedName(CONNECTOR_TYPE_NAME);
        connectorType.setDisplayName(CONNECTOR_TYPE_NAME);
        connectorType.setDescription(CONNECTOR_TYPE_DESC);
        connectorType.setConnectorProviderClassName(this.getClass().getName());

        List<String> recognizedConfigurationProperties = new ArrayList<>();
        recognizedConfigurationProperties.add(DEFAULT_ZONES);
        recognizedConfigurationProperties.add(IGNORE_UNMAPPED_INSTANCES);
        connectorType.setRecognizedConfigurationProperties(recognizedConfigurationProperties);

        super.connectorTypeBean = connectorType;

    }
}
