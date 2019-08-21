/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Note {

    @JacksonXmlProperty(isAttribute = true) private String subject;
    @JacksonXmlProperty(isAttribute = true) private String type;
    @JacksonXmlProperty(isAttribute = true) private String textContent;
    @JacksonXmlProperty(isAttribute = true) private String status;
    @JacksonXmlText private String note;

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTextContent() { return textContent; }
    public void setTextContent(String textContent) { this.textContent = textContent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return "{ \"subject\": \"" + subject
                + "\", \"type\": \"" + type
                + "\", \"textContent\": \"" + textContent
                + "\", \"status\": \"" + status
                + "\", \"note\": \"" + note
                + "\" }";
    }

}
