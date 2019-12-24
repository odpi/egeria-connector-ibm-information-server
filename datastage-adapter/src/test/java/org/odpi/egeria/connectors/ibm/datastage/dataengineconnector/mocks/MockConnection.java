/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mocks;

import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.ConnectorType;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Endpoint;

/**
 * Mocked connection for the DataStageConnector.
 */
public class MockConnection extends Connection {

    public MockConnection() {

        super();

        setDisplayName("Mock DataStage Connection");
        setDescription("A pretend DataStage connection.");

        ConnectorType connectorType = new ConnectorType();
        connectorType.setConnectorProviderClassName("org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnectorProvider");
        setConnectorType(connectorType);

        Endpoint endpoint = new Endpoint();
        endpoint.setAddress(MockConstants.IGC_ENDPOINT);
        endpoint.setProtocol("https");
        setEndpoint(endpoint);

        setUserId(MockConstants.IGC_USER);
        setClearPassword(MockConstants.IGC_PASS);

    }

}
