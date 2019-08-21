/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Benchmarks {

    @JacksonXmlProperty(localName = "ValidityBenchmark")
    private ValidityBenchmark validityBenchmark;
    @JacksonXmlProperty(localName = "ConfidenceBenchmark")
    private ConfidenceBenchmark confidenceBenchmark;
    @JacksonXmlProperty(localName = "BaselineComparisonBenchmark")
    private BaselineComparisonBenchmark baselineComparisonBenchmark;

    public ValidityBenchmark getValidityBenchmark() { return validityBenchmark; }
    public void setValidityBenchmark(ValidityBenchmark validityBenchmark) { this.validityBenchmark = validityBenchmark; }

    public ConfidenceBenchmark getConfidenceBenchmark() { return confidenceBenchmark; }
    public void setConfidenceBenchmark(ConfidenceBenchmark confidenceBenchmark) { this.confidenceBenchmark = confidenceBenchmark; }

    public BaselineComparisonBenchmark getBaselineComparisonBenchmark() { return baselineComparisonBenchmark; }
    public void setBaselineComparisonBenchmark(BaselineComparisonBenchmark baselineComparisonBenchmark) { this.baselineComparisonBenchmark = baselineComparisonBenchmark; }

    @Override
    public String toString() {
        return "{ \"ValidityBenchmark\": " + (validityBenchmark == null ? "{}" : validityBenchmark.toString())
                + ", \"ConfidenceBenchmark\": " + (confidenceBenchmark == null ? "{}" : confidenceBenchmark.toString())
                + ", \"BaselineComparisonBenchmark\": " + (baselineComparisonBenchmark == null ? "{}" : baselineComparisonBenchmark.toString())
                + " }";
    }

}
