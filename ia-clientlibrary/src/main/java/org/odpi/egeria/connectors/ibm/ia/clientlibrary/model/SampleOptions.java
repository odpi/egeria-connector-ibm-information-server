/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleOptions {

    private SampleType type;
    @JacksonXmlProperty(isAttribute = true) private Double percent;
    @JacksonXmlProperty(isAttribute = true) private Long seed;
    @JacksonXmlProperty(isAttribute = true) private Long size;
    @JacksonXmlProperty(isAttribute = true) private Integer nthValue;

    public SampleType getType() { return type; }
    public void setType(SampleType type) { this.type = type; }

    public Double getPercent() { return percent; }
    public void setPercent(Double percent) { this.percent = percent; }

    public Long getSeed() { return seed; }
    public void setSeed(Long seed) { this.seed = seed; }

    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }

    public Integer getNthValue() { return nthValue; }
    public void setNthValue(Integer nthValue) { this.nthValue = nthValue; }

    @Override
    public String toString() {
        return "{ \"type\": \"" + type
                + "\", \"percent\": " + percent
                + ", \"seed\": " + seed
                + ", \"size\": " + size
                + ", \"nthValue\": " + nthValue
                + " }";
    }

}
