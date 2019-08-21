/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishResults {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Table")
    private List<Table> tableList;

    public List<Table> getTableList() { return tableList; }
    public void setTableList(List<Table> tableList) { this.tableList = tableList; }

    @Override
    public String toString() {
        return "{ \"Tables\": " + (tableList == null ? "[]" : tableList.toString())
                + " }";
    }

}
