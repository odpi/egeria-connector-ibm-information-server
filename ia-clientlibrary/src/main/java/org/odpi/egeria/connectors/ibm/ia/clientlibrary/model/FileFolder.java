/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileFolder {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "FileName")
    private List<Table> fileNames;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Table> getFileNames() { return fileNames; }
    public void setFileNames(List<Table> fileNames) { this.fileNames = fileNames; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"FileNames\": " + (fileNames == null ? "[]" : fileNames.toString())
                + " }";
    }

}
