/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContinuousDistributionItem {

    @JacksonXmlProperty(isAttribute = true) private Double lowerRange;
    @JacksonXmlProperty(isAttribute = true) private Double upperRange;
    @JacksonXmlProperty(isAttribute = true) private Boolean includeLowerRange;
    @JacksonXmlProperty(isAttribute = true) private Boolean includeUpperRange;
    @JacksonXmlProperty(isAttribute = true) private Double percentFrequency;
    @JacksonXmlProperty(isAttribute = true) private Long absoluteFrequency;

    public Double getLowerRange() { return lowerRange; }
    public void setLowerRange(Double lowerRange) { this.lowerRange = lowerRange; }

    public Double getUpperRange() { return upperRange; }
    public void setUpperRange(Double upperRange) { this.upperRange = upperRange; }

    public Boolean isIncludeLowerRange() { return includeLowerRange; }
    public void setIncludeLowerRange(Boolean includeLowerRange) { this.includeLowerRange = includeLowerRange; }

    public Boolean isIncludeUpperRange() { return includeUpperRange; }
    public void setIncludeUpperRange(Boolean includeUpperRange) { this.includeUpperRange = includeUpperRange; }

    public Double getPercentFrequency() { return percentFrequency; }
    public void setPercentFrequency(Double percentFrequency) { this.percentFrequency = percentFrequency; }

    public Long getAbsoluteFrequency() { return absoluteFrequency; }
    public void setAbsoluteFrequency(Long absoluteFrequency) { this.absoluteFrequency = absoluteFrequency; }

    @Override
    public String toString() {
        return "{ \"lowerRange\": " + lowerRange
                + ", \"upperRange\": " + upperRange
                + ", \"includeLowerRange\": " + includeLowerRange
                + ", \"includeUpperRange\": " + includeUpperRange
                + ", \"percentFrequency\": " + percentFrequency
                + ", \"absoluteFrequency\": " + absoluteFrequency
                + " }";
    }

}
