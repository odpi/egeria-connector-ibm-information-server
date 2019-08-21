/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractBenchmark {

    @JacksonXmlProperty(localName = "BenchmarkResult")
    private BenchmarkResult benchmarkResult;

    public BenchmarkResult getBenchmarkResult() { return benchmarkResult; }
    public void setBenchmarkResult(BenchmarkResult benchmarkResult) { this.benchmarkResult = benchmarkResult; }

    @Override
    public String toString() {
        return "{ \"BenchmarkResult\": " + (benchmarkResult == null ? "{}" : benchmarkResult.toString())
                + " }";
    }

}
