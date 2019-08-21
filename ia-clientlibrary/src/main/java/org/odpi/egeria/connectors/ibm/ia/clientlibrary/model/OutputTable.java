/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputTable {

    @JacksonXmlProperty(localName = "OutputDefinition")
    private OutputDefinition outputDefinition;
    @JacksonXmlElementWrapper(localName = "Rows")
    @JacksonXmlProperty(localName = "Row")
    private List<Row> rows;

    public OutputDefinition getOutputDefinition() { return outputDefinition; }
    public void setOutputDefinition(OutputDefinition outputDefinition) { this.outputDefinition = outputDefinition; }

    public List<Row> getRows() { return rows; }
    public void setRows(List<Row> rows) { this.rows = rows; }

    @Override
    public String toString() {
        return "{ \"OutputDefinition\": " + (outputDefinition == null ? "{}" : outputDefinition.toString())
                + ", \"Rows\": " + (rows == null ? "[]" : rows.toString())
                + " }";
    }
}
