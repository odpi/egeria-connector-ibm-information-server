/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSource {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private String host;
    private String description;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Schema")
    private List<Schema> schemas;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "FileFolder")
    private List<FileFolder> fileFolders;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Schema> getSchemas() { return schemas; }
    public void setSchemas(List<Schema> schemas) { this.schemas = schemas; }

    public List<FileFolder> getFileFolders() { return fileFolders; }
    public void setFileFolders(List<FileFolder> fileFolders) { this.fileFolders = fileFolders; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"host\": \"" + host
                + "\", \"description\": \"" + description
                + "\", \"Schemas\": " + (schemas == null ? "[]" : schemas.toString())
                + ", \"FileFolders\": " + (fileFolders == null ? "[]" : fileFolders.toString())
                + " }";
    }

}
