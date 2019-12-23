/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BenchmarkResultStatus implements IAEnum {

    PASSED("passed"),
    FAILED("failed");

    @JsonValue
    private String value;
    BenchmarkResultStatus(String value) { this.value = value; }
    public String getValue() { return value; }

}
