/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchmarkResult {

    @JacksonXmlProperty(isAttribute = true) private BenchmarkResultStatus result;
    @JacksonXmlProperty(isAttribute = true) private Double variance;

    public BenchmarkResultStatus getResult() { return result; }
    public void setResult(BenchmarkResultStatus result) { this.result = result; }

    public Double getVariance() { return variance; }
    public void setVariance(Double variance) { this.variance = variance; }

    @Override
    public String toString() {
        return "{ \"result\": \"" + result
                + "\", \"variance\": " + variance
                + " }";
    }

}
