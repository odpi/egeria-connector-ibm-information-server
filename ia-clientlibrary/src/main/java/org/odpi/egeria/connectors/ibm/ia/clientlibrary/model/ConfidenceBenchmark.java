/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfidenceBenchmark extends AbstractBenchmark {

    @JacksonXmlProperty(isAttribute = true) private ConfidenceBenchmarkMetricType metric;
    @JacksonXmlProperty(isAttribute = true) private BenchmarkComparisonOperatorType operator;
    @JacksonXmlProperty(isAttribute = true) private Float percentRulesNotMetLimit;
    @JacksonXmlProperty(isAttribute = true) private Float percentRecordsAllowedOverLimit;

    public ConfidenceBenchmarkMetricType getMetric() { return metric; }
    public void setMetric(ConfidenceBenchmarkMetricType metric) { this.metric = metric; }

    public BenchmarkComparisonOperatorType getOperator() { return operator; }
    public void setOperator(BenchmarkComparisonOperatorType operator) { this.operator = operator; }

    public Float getPercentRulesNotMetLimit() { return percentRulesNotMetLimit; }
    public void setPercentRulesNotMetLimit(Float percentRulesNotMetLimit) { this.percentRulesNotMetLimit = percentRulesNotMetLimit; }

    public Float getPercentRecordsAllowedOverLimit() { return percentRecordsAllowedOverLimit; }
    public void setPercentRecordsAllowedOverLimit(Float percentRecordsAllowedOverLimit) { this.percentRecordsAllowedOverLimit = percentRecordsAllowedOverLimit; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"metric\": \"" + metric
                + "\", \"operator\": \"" + operator
                + "\", \"percentRulesNotMetLimit\": " + percentRulesNotMetLimit
                + ", \"percentRecordsAllowedOverLimit\": " + percentRecordsAllowedOverLimit
                + " }";
    }

}
