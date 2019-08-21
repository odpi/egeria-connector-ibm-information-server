/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaselineComparisonBenchmark extends AbstractBenchmark {

    @JacksonXmlProperty(isAttribute = true) private BenchmarkBaselineType benchmarkType;
    @JacksonXmlProperty(isAttribute = true) private BenchmarkComparisonOperatorType operator;
    @JacksonXmlProperty(isAttribute = true) private Float value;

    public BenchmarkBaselineType getBenchmarkType() { return benchmarkType; }
    public void setBenchmarkType(BenchmarkBaselineType benchmarkType) { this.benchmarkType = benchmarkType; }

    public BenchmarkComparisonOperatorType getOperator() { return operator; }
    public void setOperator(BenchmarkComparisonOperatorType operator) { this.operator = operator; }

    public Float getValue() { return value; }
    public void setValue(Float value) { this.value = value; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"benchmarkType\": \"" + benchmarkType
                + "\", \"operator\": \"" + operator
                + "\", \"value\": " + value
                + " }";
    }

}
