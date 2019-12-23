/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConfidenceBenchmarkMetricType implements IAEnum {

    PERCENT_ABOVE_STANDARD("percent_above_standard"),
    PERCENT_BELOW_STANDARD("percent_below_standard"),
    NB_ABOVE_STANDARD("nb_above_standard"),
    NB_BELOW_STANDARD("nb_below_standard");

    @JsonValue
    private String value;
    ConfidenceBenchmarkMetricType(String value) { this.value = value; }
    public String getValue() { return value; }

}
