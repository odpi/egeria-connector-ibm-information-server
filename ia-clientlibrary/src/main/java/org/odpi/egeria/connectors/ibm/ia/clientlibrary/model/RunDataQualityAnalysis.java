/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunDataQualityAnalysis extends AbstractTask {

    @JacksonXmlProperty(isAttribute = true) private Boolean forceColumnAnalysis;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Table")
    private List<Table> tables;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "File")
    private List<Table> files;

    public Boolean isForceColumnAnalysis() { return forceColumnAnalysis; }
    public void setForceColumnAnalysis(Boolean forceColumnAnalysis) { this.forceColumnAnalysis = forceColumnAnalysis; }

    public List<Table> getTables() { return tables; }
    public void setTables(List<Table> tables) { this.tables = tables; }

    public List<Table> getFiles() { return files; }
    public void setFiles(List<Table> files) { this.files = files; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"forceColumnAnalysis\": " + forceColumnAnalysis
                + ", \"Tables\": " + (tables == null ? "[]" : tables.toString())
                + ", \"Files\": " + (files == null ? "[]" : files.toString())
                + " }";
    }

}
