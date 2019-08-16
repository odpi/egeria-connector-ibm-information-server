/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DataTypeCode {
    BOOLEAN,
    DATE,
    DATETIME,
    DECIMAL,
    DFLOAT,
    INT8,
    INT16,
    INT32,
    INT64,
    SFLOAT,
    QFLOAT,
    TIME,
    STRING;

    private static Map<String, DataTypeCode> names = new HashMap<>(3);

    static {
        names.put("boolean", BOOLEAN);
        names.put("date", DATE);
        names.put("datetime", DATETIME);
        names.put("decimal", DECIMAL);
        names.put("dfloat", DFLOAT);
        names.put("int8", INT8);
        names.put("int16", INT16);
        names.put("int32", INT32);
        names.put("int64", INT64);
        names.put("sfloat", SFLOAT);
        names.put("qfloat", QFLOAT);
        names.put("time", TIME);
        names.put("string", STRING);
    }

    @JsonCreator
    public static DataTypeCode forValue(String value) {
        return names.getOrDefault(value, null);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, DataTypeCode> entry : names.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }
        return null;
    }

}
