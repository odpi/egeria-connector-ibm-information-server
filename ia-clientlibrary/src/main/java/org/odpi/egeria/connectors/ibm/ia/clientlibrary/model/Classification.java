/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Classification {

    @JacksonXmlProperty(isAttribute = true) private String classCode;
    @JacksonXmlProperty(isAttribute = true) private String dataClass;
    @JacksonXmlProperty(isAttribute = true) private Double confidence;
    @JacksonXmlProperty(isAttribute = true) private Long frequency;
    @JacksonXmlProperty(isAttribute = true) private Double relativeFrequency;
    @JacksonXmlProperty(isAttribute = true) private Boolean valid;
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Classification> classifications;

    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }

    public String getDataClass() { return dataClass; }
    public void setDataClass(String dataClass) { this.dataClass = dataClass; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }

    public Long getFrequency() { return frequency; }
    public void setFrequency(Long frequency) { this.frequency = frequency; }

    public Double getRelativeFrequency() { return relativeFrequency; }
    public void setRelativeFrequency(Double relativeFrequency) { this.relativeFrequency = relativeFrequency; }

    public Boolean isValid() { return valid; }
    public void setValid(Boolean valid) { this.valid = valid; }

    public List<Classification> getClassifications() { return classifications; }
    public void setClassifications(List<Classification> classifications) { this.classifications = classifications; }

    @Override
    public String toString() {
        return "{ \"classCode\": \"" + classCode
                + "\", \"dataClass\": \"" + dataClass
                + "\", \"confidence\": " + confidence
                + ", \"frequency\": " + frequency
                + ", \"relativeFrequency\": " + relativeFrequency
                + ", \"valid\": " + valid
                + ", \"Classifications\": " + (classifications == null ? "[]" : classifications.toString())
                + " }";
    }

}
