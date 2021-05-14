/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DataTypeCode implements IAEnum {

    BOOLEAN("boolean"),
    DATE("date"),
    DATETIME("datetime"),
    DECIMAL("decimal"),
    DFLOAT("dfloat"),
    INT8("int8"),
    INT16("int16"),
    INT32("int32"),
    INT64("int64"),
    SFLOAT("sfloat"),
    QFLOAT("qfloat"),
    TIME("time"),
    STRING("string");

    @JsonValue
    private String value;
    DataTypeCode(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
