/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualTable extends Table {

    @JacksonXmlProperty(localName = "SelectStatement")
    private String selectStatement;
    @JacksonXmlProperty(isAttribute = true) private String baseTable;

    public String getSelectStatement() { return selectStatement; }
    public void setSelectStatement(String selectStatement) { this.selectStatement = selectStatement; }

    public String getBaseTable() { return baseTable; }
    public void setBaseTable(String baseTable) { this.baseTable = baseTable; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"SelectStatement\": \"" + selectStatement
                + "\", \"baseTable\": \"" + baseTable
                + "\" }";
    }

}
