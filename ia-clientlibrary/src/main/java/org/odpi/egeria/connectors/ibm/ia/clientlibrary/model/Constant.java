/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Constant {

    @JacksonXmlProperty(isAttribute = true) private Double numericValue;
    @JacksonXmlProperty(isAttribute = true) private String stringValue;
    @JacksonXmlProperty(isAttribute = true) private Date dateValue;
    @JacksonXmlProperty(isAttribute = true) private Date timeValue;
    @JacksonXmlProperty(isAttribute = true) private Date dateTimeValue;
    @JacksonXmlProperty(isAttribute = true) private LogicalDataType dataType;

    public Double getNumericValue() { return numericValue; }
    public void setNumericValue(Double numericValue) { this.numericValue = numericValue; }

    public String getStringValue() { return stringValue; }
    public void setStringValue(String stringValue) { this.stringValue = stringValue; }

    public Date getDateValue() { return dateValue; }
    public void setDateValue(Date dateValue) { this.dateValue = dateValue; }

    public Date getTimeValue() { return timeValue; }
    public void setTimeValue(Date timeValue) { this.timeValue = timeValue; }

    public Date getDateTimeValue() { return dateTimeValue; }
    public void setDateTimeValue(Date dateTimeValue) { this.dateTimeValue = dateTimeValue; }

    public LogicalDataType getDataType() { return dataType; }
    public void setDataType(LogicalDataType dataType) { this.dataType = dataType; }

    @Override
    public String toString() {
        return "{ \"numericValue\": " + numericValue
                + ", \"stringValue\": \"" + stringValue
                + "\", \"dateValue\": \"" + dateValue
                + "\", \"timeValue\": \"" + timeValue
                + "\", \"dateTimeValue\": \"" + dateTimeValue
                + "\", \"dataType\": \"" + dataType
                + "\" }";
    }

}
