/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricBenchmarkResult extends BenchmarkResult {

    @JacksonXmlProperty(localName = "MetricBenchmark")
    private MetricBenchmark metricBenchmark;

    public MetricBenchmark getMetricBenchmark() { return metricBenchmark; }
    public void setMetricBenchmark(MetricBenchmark metricBenchmark) { this.metricBenchmark = metricBenchmark; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"MetricBenchmark\": " + (metricBenchmark == null ? "{}" : metricBenchmark.toString())
                + " }";
    }

}
