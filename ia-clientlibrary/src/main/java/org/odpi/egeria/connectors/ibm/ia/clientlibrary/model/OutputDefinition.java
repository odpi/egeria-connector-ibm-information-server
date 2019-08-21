/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputDefinition {

    @JacksonXmlProperty(isAttribute = true) private OutputType type = OutputType.undefined;
    @JacksonXmlProperty(isAttribute = true) private Boolean distinctRecords;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "OutputColumn")
    private List<OutputColumn> outputColumns;

    public OutputType getType() { return type; }
    public void setType(OutputType type) { this.type = type; }

    public Boolean isDistinctRecords() { return distinctRecords; }
    public void setDistinctRecords(Boolean distinctRecords) { this.distinctRecords = distinctRecords; }

    public List<OutputColumn> getOutputColumns() { return outputColumns; }
    public void setOutputColumns(List<OutputColumn> outputColumns) { this.outputColumns = outputColumns; }

    @Override
    public String toString() {
        return "{ \"type\": \"" + type
                + "\", \"distinctRecords\": " + distinctRecords
                + ", \"OutputColumns\": " + (outputColumns == null ? "[]" : outputColumns.toString())
                + " }";
    }

}
