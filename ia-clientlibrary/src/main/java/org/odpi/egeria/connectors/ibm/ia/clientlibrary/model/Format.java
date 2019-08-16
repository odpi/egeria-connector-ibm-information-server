/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Format {

    @JacksonXmlProperty(isAttribute = true) private String generalFormat;
    @JacksonXmlProperty(isAttribute = true) private Date analysisDate;
    @JacksonXmlProperty(isAttribute = true) private AnalysisStatus analysisStatus;
    @JacksonXmlProperty(isAttribute = true) private Long distValueCount;
    @JacksonXmlProperty(isAttribute = true) private Double distValuePercent;
    @JacksonXmlProperty(isAttribute = true) private Long recordCount;
    @JacksonXmlProperty(isAttribute = true) private Double generalFormatPercent;

    public String getGeneralFormat() { return generalFormat; }
    public void setGeneralFormat(String generalFormat) { this.generalFormat = generalFormat; }

    public Date getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(Date analysisDate) { this.analysisDate = analysisDate; }

    public AnalysisStatus getAnalysisStatus() { return analysisStatus; }
    public void setAnalysisStatus(AnalysisStatus analysisStatus) { this.analysisStatus = analysisStatus; }

    public Long getDistValueCount() { return distValueCount; }
    public void setDistValueCount(Long distValueCount) { this.distValueCount = distValueCount; }

    public Double getDistValuePercent() { return distValuePercent; }
    public void setDistValuePercent(Double distValuePercent) { this.distValuePercent = distValuePercent; }

    public Long getRecordCount() { return recordCount; }
    public void setRecordCount(Long recordCount) { this.recordCount = recordCount; }

    public Double getGeneralFormatPercent() { return generalFormatPercent; }
    public void setGeneralFormatPercent(Double generalFormatPercent) { this.generalFormatPercent = generalFormatPercent; }

    @Override
    public String toString() {
        return "{ \"generalFormat\": \"" + generalFormat
                + "\", \"analysisDate\": \"" + analysisDate
                + "\", \"analysisStatus\": \"" + analysisStatus
                + "\", \"distValueCount\": " + distValueCount
                + ", \"distValuePercent\": " + distValuePercent
                + ", \"recordCount\": " + recordCount
                + ", \"generalFormatPercent\": " + generalFormatPercent
                + " }";
    }
}
