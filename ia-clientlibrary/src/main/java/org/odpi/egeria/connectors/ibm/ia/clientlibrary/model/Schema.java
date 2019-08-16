/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schema {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Table")
    private List<Table> tables;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "VirtualTable")
    private List<VirtualTable> virtualTables;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "File")
    private List<Table> files;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Table> getTables() { return tables; }
    public void setTables(List<Table> tables) { this.tables = tables; }

    public List<VirtualTable> getVirtualTables() { return virtualTables; }
    public void setVirtualTables(List<VirtualTable> virtualTables) { this.virtualTables = virtualTables; }

    public List<Table> getFiles() { return files; }
    public void setFiles(List<Table> files) { this.files = files; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"Tables\": " + (tables == null ? "[]" : tables.toString())
                + ", \"VirtualTables\": " + (virtualTables == null ? "[]" : virtualTables.toString())
                + ", \"Files\": " + (files == null ? "[]" : files.toString())
                + " }";
    }

}
