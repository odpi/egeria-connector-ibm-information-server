/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricExecutionResult extends AbstractRuleExecutionResult {

    @JacksonXmlProperty(isAttribute = true) private Date timeStamp;
    @JacksonXmlProperty(isAttribute = true) private Double value;
    @JacksonXmlProperty(localName = "MetricBenchmarkResult")
    private MetricBenchmarkResult metricBenchmarkResult;

    public Date getTimeStamp() { return timeStamp; }
    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public MetricBenchmarkResult getMetricBenchmarkResult() { return metricBenchmarkResult; }
    public void setMetricBenchmarkResult(MetricBenchmarkResult metricBenchmarkResult) { this.metricBenchmarkResult = metricBenchmarkResult; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"timeStamp\": \"" + timeStamp
                + "\", \"value\": " + value
                + ", \"MetricBenchmarkResult\": " + (metricBenchmarkResult == null ? "{}" : metricBenchmarkResult.toString())
                + " }";
    }

}
