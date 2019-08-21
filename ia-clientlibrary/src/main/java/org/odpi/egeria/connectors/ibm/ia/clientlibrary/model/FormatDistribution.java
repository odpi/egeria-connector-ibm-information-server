/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormatDistribution {

    @JacksonXmlProperty(isAttribute = true) private Long nbOfFormats;
    @JacksonXmlProperty(isAttribute = true) private Long nbOfConformFormats;
    @JacksonXmlProperty(isAttribute = true) private Long nbOfViolatingFormats;
    @JacksonXmlProperty(isAttribute = true) private Long nbOfConformRecords;
    @JacksonXmlProperty(isAttribute = true) private Long nbOfViolatingRecords;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Format")
    private List<Format> formats;

    public Long getNbOfFormats() { return nbOfFormats; }
    public void setNbOfFormats(Long nbOfFormats) { this.nbOfFormats = nbOfFormats; }

    public Long getNbOfConformFormats() { return nbOfConformFormats; }
    public void setNbOfConformFormats(Long nbOfConformFormats) { this.nbOfConformFormats = nbOfConformFormats; }

    public Long getNbOfViolatingFormats() { return nbOfViolatingFormats; }
    public void setNbOfViolatingFormats(Long nbOfViolatingFormats) { this.nbOfViolatingFormats = nbOfViolatingFormats; }

    public Long getNbOfConformRecords() { return nbOfConformRecords; }
    public void setNbOfConformRecords(Long nbOfConformRecords) { this.nbOfConformRecords = nbOfConformRecords; }

    public Long getNbOfViolatingRecords() { return nbOfViolatingRecords; }
    public void setNbOfViolatingRecords(Long nbOfViolatingRecords) { this.nbOfViolatingRecords = nbOfViolatingRecords; }

    public List<Format> getFormats() { return formats; }
    public void setFormats(List<Format> formats) { this.formats = formats; }

    @Override
    public String toString() {
        return "{ \"nbOfFormats\": " + nbOfFormats
                + ", \"nbOfConformFormats\": " + nbOfConformFormats
                + ", \"nbOfViolatingFormats\": " + nbOfViolatingFormats
                + ", \"nbOfConformRecords\": " + nbOfConformRecords
                + ", \"nbOfViolatingRecords\": " + nbOfViolatingRecords
                + ", \"Formats\": " + (formats == null ? "[]" : formats.toString())
                + " }";
    }

}
