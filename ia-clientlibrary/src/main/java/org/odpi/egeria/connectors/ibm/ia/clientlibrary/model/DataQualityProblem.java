/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQualityProblem {

    @JacksonXmlProperty(isAttribute = true) private String problemCode;
    @JacksonXmlProperty(isAttribute = true) private String problemName;
    @JacksonXmlProperty(isAttribute = true) private String description;
    @JacksonXmlProperty(isAttribute = true) private Double confidence;
    @JacksonXmlProperty(isAttribute = true) private Double prevalence;
    @JacksonXmlProperty(isAttribute = true) private Long frequency;
    @JacksonXmlProperty(isAttribute = true) private Boolean ignored;

    public String getProblemCode() { return problemCode; }
    public void setProblemCode(String problemCode) { this.problemCode = problemCode; }

    public String getProblemName() { return problemName; }
    public void setProblemName(String problemName) { this.problemName = problemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }

    public Double getPrevalence() { return prevalence; }
    public void setPrevalence(Double prevalence) { this.prevalence = prevalence; }

    public Long getFrequency() { return frequency; }
    public void setFrequency(Long frequency) { this.frequency = frequency; }

    public Boolean isIgnored() { return ignored; }
    public void setIgnored(Boolean ignored) { this.ignored = ignored; }

    @Override
    public String toString() {
        return "{ \"problemCode\": \"" + problemCode
                + "\", \"problemName\": \"" + problemName
                + "\", \"description\": \"" + description
                + "\", \"confidence\": " + confidence
                + ", \"prevalence\": " + prevalence
                + ", \"frequency\": " + frequency
                + ", \"ignored\": " + ignored
                + " }";
    }

}
