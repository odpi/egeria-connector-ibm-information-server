/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidityBenchmark extends AbstractBenchmark {

    @JacksonXmlProperty(isAttribute = true) private ValidityBenchmarkMetricType metric;
    @JacksonXmlProperty(isAttribute = true) private BenchmarkComparisonOperatorType operator;
    @JacksonXmlProperty(isAttribute = true) private Float value;

    public ValidityBenchmarkMetricType getMetric() { return metric; }
    public void setMetric(ValidityBenchmarkMetricType metric) { this.metric = metric; }

    public BenchmarkComparisonOperatorType getOperator() { return operator; }
    public void setOperator(BenchmarkComparisonOperatorType operator) { this.operator = operator; }

    public Float getValue() { return value; }
    public void setValue(Float value) { this.value = value; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"metric\": \"" + metric
                + "\", \"operator\": \"" + operator
                + "\", \"value\": " + value
                + " }";
    }

}
