/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    @JacksonXmlProperty(isAttribute = true) private Long frequency;
    @JacksonXmlProperty(isAttribute = true) private Double percent;
    @JacksonXmlText private String value;

    public Long getFrequency() { return frequency; }
    public void setFrequency(Long frequency) { this.frequency = frequency; }

    public Double getPercent() { return percent; }
    public void setPercent(Double percent) { this.percent = percent; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    @Override
    public String toString() {
        return "{ \"frequency\": " + frequency
                + ", \"percent\": " + percent
                + ", \"value\": \"" + value
                + "\" }";
    }

}
