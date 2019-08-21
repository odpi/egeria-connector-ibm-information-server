/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PairedColumns {

    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedForeignKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedForeignKey;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Column")
    private List<Column> columns;

    public Boolean isSelectedForeignKey() { return isSelectedForeignKey; }
    public void setSelectedForeignKey(Boolean selectedForeignKey) { isSelectedForeignKey = selectedForeignKey; }

    public Boolean isDefinedForeignKey() { return isDefinedForeignKey; }
    public void setDefinedForeignKey(Boolean definedForeignKey) { isDefinedForeignKey = definedForeignKey; }

    public List<Column> getColumns() { return columns; }
    public void setColumns(List<Column> columns) { this.columns = columns; }

    @Override
    public String toString() {
        return "{ \"isSelectedForeignKey\": " + isSelectedForeignKey
                + ", \"isDefinedForeignKey\": " + isDefinedForeignKey
                + ", \"Columns\": " + (columns == null ? "[]" : columns.toString())
                + " }";
    }

}
