/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunMultiColumnKeyAnalysis extends AbstractTask {

    @JacksonXmlProperty(isAttribute = true) private Integer compositeMax;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Table")
    private List<Table> tables;

    public Integer getCompositeMax() { return compositeMax; }
    public void setCompositeMax(Integer compositeMax) { this.compositeMax = compositeMax; }

    public List<Table> getTables() { return tables; }
    public void setTables(List<Table> tables) { this.tables = tables; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"compositeMax\": " + compositeMax
                + ", \"Tables\": " + (tables == null ? "[]" : tables.toString())
                + " }";
    }

}
