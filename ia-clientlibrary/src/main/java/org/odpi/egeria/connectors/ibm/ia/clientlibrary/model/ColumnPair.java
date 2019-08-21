/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnPair {

    @JacksonXmlProperty(localName = "BaseColumns")
    private BaseColumns baseColumns;
    @JacksonXmlProperty(localName = "PairedColumns")
    private PairedColumns pairedColumns;

    public BaseColumns getBaseColumns() { return baseColumns; }
    public void setBaseColumns(BaseColumns baseColumns) { this.baseColumns = baseColumns; }

    public PairedColumns getPairedColumns() { return pairedColumns; }
    public void setPairedColumns(PairedColumns pairedColumns) { this.pairedColumns = pairedColumns; }

    @Override
    public String toString() {
        return "{ \"BaseColumns\": " + (baseColumns == null ? "{}" : baseColumns.toString())
                + ", \"PairedColumns\": " + (pairedColumns == null ? "{}" : pairedColumns.toString())
                + " }";
    }

}
