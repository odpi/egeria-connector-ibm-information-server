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
 */
public class IGCOMRSRepositoryConnectorProvider extends OMRSRepositoryConnectorProviderBase {

    static final String  connectorTypeGUID = "62cd0d10-4e72-4d75-9fdc-e9b783f98d80";
    static final String  connectorTypeName = "OMRS IGC Repository Connector";
    static final String  connectorTypeDescription = "OMRS IGC Repository Connector that processes events from the IBM InfoSphere Information Governance Catalog repository store.";

    public static final String DEFAULT_ZONES = "defaultZones";
    public static final String DEFAULT_GLOSSARY_NAME = "defaultGlossaryName";
    public static final String DEFAULT_TERM_STATUS = "defaultTermStatus";

    /**
     * Constructor used to initialize the ConnectorProviderBase with the Java class name of the specific
     * OMRS Connector implementation.
     */
    public IGCOMRSRepositoryConnectorProvider() {

        Class connectorClass = IGCOMRSRepositoryConnector.class;
        super.setConnectorClassName(connectorClass.getName());

        ConnectorType connectorType = new ConnectorType();
        connectorType.setType(ConnectorType.getConnectorTypeType());
        connectorType.setGUID(connectorTypeGUID);
        connectorType.setQualifiedName(connectorTypeName);
        connectorType.setDisplayName(connectorTypeName);
        connectorType.setDescription(connectorTypeDescription);
        connectorType.setConnectorProviderClassName(this.getClass().getName());

        List<String> recognizedConfigurationProperties = new ArrayList<>();
        recognizedConfigurationProperties.add(DEFAULT_ZONES);
        recognizedConfigurationProperties.add(DEFAULT_GLOSSARY_NAME);
        recognizedConfigurationProperties.add(DEFAULT_TERM_STATUS);
        connectorType.setRecognizedConfigurationProperties(recognizedConfigurationProperties);

        super.connectorTypeBean = connectorType;

    }
}
