/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LogicalDataType implements IAEnum {

    NUMERIC("numeric"),
    STRING("string"),
    DATE("date"),
    TIME("time"),
    TIMESTAMP("timestamp"),
    ANY("any");

    @JsonValue
    private String value;
    LogicalDataType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
