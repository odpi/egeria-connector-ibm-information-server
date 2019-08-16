/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscreteDistributionItem {

    @JacksonXmlProperty(isAttribute = true) private Integer value;
    @JacksonXmlProperty(isAttribute = true) private Double percentFrequency;
    @JacksonXmlProperty(isAttribute = true) private Long absoluteFrequency;

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public Double getPercentFrequency() { return percentFrequency; }
    public void setPercentFrequency(Double percentFrequency) { this.percentFrequency = percentFrequency; }

    public Long getAbsoluteFrequency() { return absoluteFrequency; }
    public void setAbsoluteFrequency(Long absoluteFrequency) { this.absoluteFrequency = absoluteFrequency; }

    @Override
    public String toString() {
        return "{ \"value\": " + value
                + ", \"percentFrequency\": " + percentFrequency
                + ", \"absoluteFrequency\": " + absoluteFrequency
                + " }";
    }

}
