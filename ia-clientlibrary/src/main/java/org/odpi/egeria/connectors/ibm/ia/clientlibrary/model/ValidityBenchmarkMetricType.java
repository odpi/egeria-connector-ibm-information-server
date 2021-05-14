/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ValidityBenchmarkMetricType implements IAEnum {

    PERCENT_MET("percent_met"),
    PERCENT_NOT_MET("percent_not_met"),
    NB_MET("nb_met"),
    NB_NOT_MET("nb_not_met");

    @JsonValue
    private String value;
    ValidityBenchmarkMetricType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
