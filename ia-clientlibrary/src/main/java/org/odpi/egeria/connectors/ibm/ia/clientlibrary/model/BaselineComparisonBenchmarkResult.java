/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaselineComparisonBenchmarkResult extends BenchmarkResult {

    @JacksonXmlProperty(isAttribute = true) private Double similarity;
    @JacksonXmlProperty(isAttribute = true) private Double degradation;
    @JacksonXmlProperty(isAttribute = true) private Double improvement;

    public Double getSimilarity() { return similarity; }
    public void setSimilarity(Double similarity) { this.similarity = similarity; }

    public Double getDegradation() { return degradation; }
    public void setDegradation(Double degradation) { this.degradation = degradation; }

    public Double getImprovement() { return improvement; }
    public void setImprovement(Double improvement) { this.improvement = improvement; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"similarity\": " + similarity
                + ", \"degradation\": " + degradation
                + ", \"improvement\": " + improvement
                + " }";
    }

}
