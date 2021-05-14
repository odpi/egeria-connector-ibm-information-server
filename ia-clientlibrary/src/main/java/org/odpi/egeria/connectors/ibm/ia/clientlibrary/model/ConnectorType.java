/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConnectorType implements IAEnum {

    DB2_CONNECTOR("DB2Connector"),
    ODBC_CONNECTOR("ODBCConnector"),
    ORACLE_CONNECTOR_V10("OracleConnectorV10"),
    ORACLE_CONNECTOR_V11("OracleConnectorV11"),
    TERADATA_CONNECTOR("TeraDataConnector");

    @JsonValue
    private String value;
    ConnectorType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
