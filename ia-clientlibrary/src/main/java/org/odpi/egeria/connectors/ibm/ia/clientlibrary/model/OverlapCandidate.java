/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OverlapCandidate {

    @JacksonXmlProperty(isAttribute = true) private String baseColumn;
    @JacksonXmlProperty(isAttribute = true) private String pairedColumn;
    @JacksonXmlProperty(isAttribute = true) private Long absolutePairedToBase;
    @JacksonXmlProperty(isAttribute = true) private Float percentPairedToBase;
    @JacksonXmlProperty(isAttribute = true) private Long absoluteBaseToPaired;
    @JacksonXmlProperty(isAttribute = true) private Float percentBaseToPaired;
    @JacksonXmlProperty(isAttribute = true) private Date date;

    public String getBaseColumn() { return baseColumn; }
    public void setBaseColumn(String baseColumn) { this.baseColumn = baseColumn; }

    public String getPairedColumn() { return pairedColumn; }
    public void setPairedColumn(String pairedColumn) { this.pairedColumn = pairedColumn; }

    public Long getAbsolutePairedToBase() { return absolutePairedToBase; }
    public void setAbsolutePairedToBase(Long absolutePairedToBase) { this.absolutePairedToBase = absolutePairedToBase; }

    public Float getPercentPairedToBase() { return percentPairedToBase; }
    public void setPercentPairedToBase(Float percentPairedToBase) { this.percentPairedToBase = percentPairedToBase; }

    public Long getAbsoluteBaseToPaired() { return absoluteBaseToPaired; }
    public void setAbsoluteBaseToPaired(Long absoluteBaseToPaired) { this.absoluteBaseToPaired = absoluteBaseToPaired; }

    public Float getPercentBaseToPaired() { return percentBaseToPaired; }
    public void setPercentBaseToPaired(Float percentBaseToPaired) { this.percentBaseToPaired = percentBaseToPaired; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Override
    public String toString() {
        return "{ \"baseColumn\": \"" + baseColumn
                + "\", \"pairedColumn\": \"" + pairedColumn
                + "\", \"absolutePairedToBase\": " + absolutePairedToBase
                + ", \"percentPairedToBase\": " + percentPairedToBase
                + ", \"absoluteBaseToPaired\": " + absoluteBaseToPaired
                + ", \"percentBaseToPaired\": " + percentBaseToPaired
                + ", \"date\": " + date
                + "\" }";
    }

}
