/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataClassificationResults {

    @JacksonXmlProperty(isAttribute = true) private Date classificationDate;
    @JacksonXmlElementWrapper(localName = "DataClassCandidates")
    private List<Classification> dataClassCandidates;
    @JacksonXmlElementWrapper(localName = "MatchedDataClasses")
    private List<Classification> matchedDataClasses;

    public Date getClassificationDate() { return classificationDate; }
    public void setClassificationDate(Date classificationDate) { this.classificationDate = classificationDate; }

    public List<Classification> getDataClassCandidates() { return dataClassCandidates; }
    public void setDataClassCandidates(List<Classification> dataClassCandidates) { this.dataClassCandidates = dataClassCandidates; }

    public List<Classification> getMatchedDataClasses() { return matchedDataClasses; }
    public void setMatchedDataClasses(List<Classification> matchedDataClasses) { this.matchedDataClasses = matchedDataClasses; }

    @Override
    public String toString() {
        return "{ \"classificationDate\": \"" + classificationDate
                + "\", \"DataClassCandidates\": " + (dataClassCandidates == null ? "[]" : dataClassCandidates.toString())
                + ", \"MatchedDataClasses\": " + (matchedDataClasses == null ? "[]" : matchedDataClasses.toString())
                + " }";
    }

}
