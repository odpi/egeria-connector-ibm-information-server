/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Variable {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private LogicalDataType dataType;
    private String description;
    @JacksonXmlProperty(localName = "Binding")
    private Binding binding;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LogicalDataType getDataType() { return dataType; }
    public void setDataType(LogicalDataType dataType) { this.dataType = dataType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Binding getBinding() { return binding; }
    public void setBinding(Binding binding) { this.binding = binding; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"dataType\": \"" + dataType
                + "\", \"description\": \"" + description
                + "\", \"Binding\": " + (binding == null ? "{}" : binding.toString())
                + " }";
    }

}
