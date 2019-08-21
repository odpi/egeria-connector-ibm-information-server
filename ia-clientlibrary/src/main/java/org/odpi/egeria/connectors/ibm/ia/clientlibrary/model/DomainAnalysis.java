/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainAnalysis {

    @JacksonXmlProperty(isAttribute = true) private Date analysisDate;
    @JacksonXmlProperty(isAttribute = true) private AnalysisStatus analysisStatus;

    public Date getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(Date analysisDate) { this.analysisDate = analysisDate; }

    public AnalysisStatus getAnalysisStatus() { return analysisStatus; }
    public void setAnalysisStatus(AnalysisStatus analysisStatus) { this.analysisStatus = analysisStatus; }

    @Override
    public String toString() {
        return "{ \"analysisDate\": \"" + analysisDate
                + "\", \"analysisStatus\": \"" + analysisStatus
                + "\" }";
    }
}
