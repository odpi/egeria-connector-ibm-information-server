/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder {

    @JacksonXmlProperty(isAttribute = true) private String name;
    private String description;
    @JacksonXmlProperty(localName = "LongDescription")
    private String longDescription;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Folder")
    private List<Folder> folders;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLongDescription() { return longDescription; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    public List<Folder> getFolders() { return folders; }
    public void setFolders(List<Folder> folders) { this.folders = folders; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"description\": \"" + description
                + "\", \"longDescription\": \"" + longDescription
                + "\", \"Folders\": " + (folders == null ? "[]" : folders.toString())
                + " }";
    }

}
