/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mocks;

import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.ConnectorType;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mocked connection for the IGCOMRSRepositoryConnector.
 */
public class MockConnection extends Connection {

    public MockConnection() {

        super();

        setDisplayName("Mock IGC Connection");
        setDescription("A pretend IGC connection.");

        ConnectorType connectorType = new ConnectorType();
        connectorType.setConnectorProviderClassName("org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider");
        setConnectorType(connectorType);

        Endpoint endpoint = new Endpoint();
        endpoint.setAddress(MockConstants.IGC_ENDPOINT);
        endpoint.setProtocol("https");
        setEndpoint(endpoint);

        setUserId(MockConstants.IGC_USER);
        setClearPassword(MockConstants.IGC_PASS);

        Map<String, Object> configProperties = new HashMap<>();
        List<String> defaultZones = new ArrayList<>();
        defaultZones.add("default");
        configProperties.put("defaultZones", defaultZones);
        setConfigurationProperties(configProperties);

    }

}
