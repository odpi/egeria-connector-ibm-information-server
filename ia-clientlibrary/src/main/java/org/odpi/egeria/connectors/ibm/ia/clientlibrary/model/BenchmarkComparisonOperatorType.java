/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BenchmarkComparisonOperatorType implements IAEnum {

    GREATER_THAN("greater_than"),
    GREATER_OR_EQUAL("greater_or_equal"),
    LESS_THAN("less_than"),
    LESS_OR_EQUAL("less_or_equal");

    @JsonValue
    private String value;
    BenchmarkComparisonOperatorType(String value) { this.value = value; }
    public String getValue() { return value; }

}
