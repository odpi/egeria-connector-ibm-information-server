/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractQualityControl {

    @JacksonXmlProperty(isAttribute = true) private String rid;
    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private String folder;
    private String description;
    @JacksonXmlProperty(localName = "LongDescription")
    private String longDescription;
    @JacksonXmlElementWrapper(localName = "Notes")
    private List<Note> notes;
    @JacksonXmlElementWrapper(localName = "Terms")
    private List<Term> terms;

    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFolder() { return folder; }
    public void setFolder(String folder) { this.folder = folder; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLongDescription() { return longDescription; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }

    public List<Term> getTerms() { return terms; }
    public void setTerms(List<Term> terms) { this.terms = terms; }

    @Override
    public String toString() {
        return "{ \"rid\": \"" + rid
                + "\", \"name\": \"" + name
                + "\", \"folder\": \"" + folder
                + "\", \"description\": \"" + description
                + "\", \"LongDescription\": \"" + longDescription
                + "\", \"Notes\": " + (notes == null ? "[]" : notes.toString())
                + ", \"Terms\": " + (terms == null ? "[]" : terms.toString())
                + " }";
    }

}
