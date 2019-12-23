/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OutputType implements IAEnum {

    PASSING_RECORDS("PassingRecords"),
    FAILING_RECORDS("FailingRecords"),
    ALL_RECORDS("AllRecords"),
    STATS_ONLY("StatsOnly"),
    UNDEFINED("undefined");

    @JsonValue
    private String value;
    OutputType(String value) { this.value = value; }
    public String getValue() { return value; }

}
