/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Violation {

    @JacksonXmlProperty(isAttribute = true) private String foreignKeyValue;
    @JacksonXmlProperty(isAttribute = true) private Long records;
    @JacksonXmlProperty(isAttribute = true) private Float percentage;

    public String getForeignKeyValue() { return foreignKeyValue; }
    public void setForeignKeyValue(String foreignKeyValue) { this.foreignKeyValue = foreignKeyValue; }

    public Long getRecords() { return records; }
    public void setRecords(Long records) { this.records = records; }

    public Float getPercentage() { return percentage; }
    public void setPercentage(Float percentage) { this.percentage = percentage; }

    @Override
    public String toString() {
        return "{ \"foreignKeyValue\": \"" + foreignKeyValue
                + "\", \"records\": " + records
                + ", \"percentage\": " + percentage
                + " }";
    }

}
