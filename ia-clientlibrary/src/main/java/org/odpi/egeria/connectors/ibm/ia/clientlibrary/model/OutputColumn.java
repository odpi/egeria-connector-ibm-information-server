/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputColumn {

    @JacksonXmlProperty(isAttribute = true) private OutputColumnType type;
    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private String value;
    @JacksonXmlProperty(isAttribute = true) private LogicalDataType dataType;

    public OutputColumnType getType() { return type; }
    public void setType(OutputColumnType type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public LogicalDataType getDataType() { return dataType; }
    public void setDataType(LogicalDataType dataType) { this.dataType = dataType; }

    @Override
    public String toString() {
        return "{ \"type\": \"" + type
                + "\", \"name\": \"" + name
                + "\", \"value\": \"" + value
                + "\", \"dataType\": \"" + dataType
                + "\" }";
    }

}
