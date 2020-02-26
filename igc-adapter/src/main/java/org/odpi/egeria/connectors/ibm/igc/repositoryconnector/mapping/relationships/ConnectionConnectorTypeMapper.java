/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "ConnectionConnectorType" relationship between IGC "data_connection" and "connector" assets.
 */
public class ConnectionConnectorTypeMapper extends RelationshipMapping {

    private static class Singleton {
        private static final ConnectionConnectorTypeMapper INSTANCE = new ConnectionConnectorTypeMapper();
    }
    public static ConnectionConnectorTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected ConnectionConnectorTypeMapper() {
        super(
                "data_connection",
                "connector",
                "data_connectors",
                "data_connections",
                "ConnectionConnectorType",
                "connections",
                "connectorType"
        );
    }

}
