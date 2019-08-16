/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunReferentialIntegrityAnalysis extends AbstractTask {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ForeignKeyCandidate")
    private List<ForeignKeyCandidate> foreignKeyCandidateList;

    public List<ForeignKeyCandidate> getForeignKeyCandidateList() { return foreignKeyCandidateList; }
    public void setForeignKeyCandidateList(List<ForeignKeyCandidate> foreignKeyCandidateList) { this.foreignKeyCandidateList = foreignKeyCandidateList; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"ForeignKeyCandidates\": " + (foreignKeyCandidateList == null ? "[]" : foreignKeyCandidateList.toString())
                + " }";
    }

}
