/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RuntimeMetaData {

    @JacksonXmlProperty(isAttribute = true) private Date analysisDate;
    @JacksonXmlProperty(isAttribute = true) private Long runTime;
    @JacksonXmlProperty(isAttribute = true) private Boolean sampleUsed;
    @JacksonXmlProperty(isAttribute = true) private String whereClause;
    @JacksonXmlProperty(localName = "SampleOptions")
    private SampleOptions sampleOptions;

    public Date getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(Date analysisDate) { this.analysisDate = analysisDate; }

    public Long getRunTime() { return runTime; }
    public void setRunTime(Long runTime) { this.runTime = runTime; }

    public Boolean isSampleUsed() { return sampleUsed; }
    public void setSampleUsed(Boolean sampleUsed) { this.sampleUsed = sampleUsed; }

    public String getWhereClause() { return whereClause; }
    public void setWhereClause(String whereClause) { this.whereClause = whereClause; }

    public SampleOptions getSampleOptions() { return sampleOptions; }
    public void setSampleOptions(SampleOptions sampleOptions) { this.sampleOptions = sampleOptions; }

    @Override
    public String toString() {
        return "{ \"analysisDate\": \"" + analysisDate
                + "\", \"runTime\": " + runTime
                + ", \"sampleUsed\": " + sampleUsed
                + ", \"whereClause\": \"" + whereClause
                + "\", \"SampleOptions\": " + (sampleOptions == null ? "{}" : sampleOptions.toString())
                + " }";
    }

}
