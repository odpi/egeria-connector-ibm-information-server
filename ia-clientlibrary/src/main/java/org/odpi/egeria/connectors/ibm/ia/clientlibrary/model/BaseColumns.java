/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseColumns {

    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedPrimaryKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedPrimaryKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedNaturalKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedNaturalKey;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Column")
    private List<Column> columns;

    public Boolean isSelectedPrimaryKey() { return isSelectedPrimaryKey; }
    public void setSelectedPrimaryKey(Boolean selectedPrimaryKey) { isSelectedPrimaryKey = selectedPrimaryKey; }

    public Boolean isDefinedPrimaryKey() { return isDefinedPrimaryKey; }
    public void setDefinedPrimaryKey(Boolean definedPrimaryKey) { isDefinedPrimaryKey = definedPrimaryKey; }

    public Boolean isSelectedNaturalKey() { return isSelectedNaturalKey; }
    public void setSelectedNaturalKey(Boolean selectedNaturalKey) { isSelectedNaturalKey = selectedNaturalKey; }

    public Boolean isDefinedNaturalKey() { return isDefinedNaturalKey; }
    public void setDefinedNaturalKey(Boolean definedNaturalKey) { isDefinedNaturalKey = definedNaturalKey; }

    public List<Column> getColumns() { return columns; }
    public void setColumns(List<Column> columns) { this.columns = columns; }

    @Override
    public String toString() {
        return "{ \"isSelectedPrimaryKey\": " + isSelectedPrimaryKey
                + ", \"isDefinedPrimaryKey\": " + isDefinedPrimaryKey
                + ", \"isSelectedNaturalKey\": " + isSelectedNaturalKey
                + ", \"isDefinedNaturalKey\": " + isDefinedNaturalKey
                + ", \"Columns\": " + (columns == null ? "[]" : columns.toString())
                + " }";
    }

}
