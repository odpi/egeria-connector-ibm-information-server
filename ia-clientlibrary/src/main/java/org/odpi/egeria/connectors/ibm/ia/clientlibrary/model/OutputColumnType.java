/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OutputColumnType implements IAEnum {

    COLUMN("column"),
    VARIABLE("variable"),
    EXPRESSION("expression"),
    METRIC("metric");

    @JsonValue
    private String value;
    OutputColumnType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
