/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metric extends AbstractQualityControl {

    private String expression;
    @JacksonXmlProperty(localName = "MetricBenchmark")
    private MetricBenchmark metricBenchmark;
    @JacksonXmlProperty(localName = "ExecutionHistory")
    private ExecutionHistory executionHistory;

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    public MetricBenchmark getMetricBenchmark() { return metricBenchmark; }
    public void setMetricBenchmark(MetricBenchmark metricBenchmark) { this.metricBenchmark = metricBenchmark; }

    public ExecutionHistory getExecutionHistory() { return executionHistory; }
    public void setExecutionHistory(ExecutionHistory executionHistory) { this.executionHistory = executionHistory; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"expression\": \"" + expression
                + "\", \"MetricBenchmark\": " + (metricBenchmark == null ? "{}" : metricBenchmark.toString())
                + ", \"ExecutionHistory\": " + (executionHistory == null ? "{}" : executionHistory.toString())
                + " }";
    }

}
