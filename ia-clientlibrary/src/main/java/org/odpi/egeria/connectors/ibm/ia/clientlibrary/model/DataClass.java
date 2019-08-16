/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataClass {

    @JacksonXmlProperty(isAttribute = true) private String inferredClass;
    @JacksonXmlProperty(isAttribute = true) private String selectedClass;
    @JacksonXmlProperty(isAttribute = true) private Date classificationDate;
    @JacksonXmlProperty(isAttribute = true) private AnalysisStatus classificationStatus;

    public String getInferredClass() { return inferredClass; }
    public void setInferredClass(String inferredClass) { this.inferredClass = inferredClass; }

    public String getSelectedClass() { return selectedClass; }
    public void setSelectedClass(String selectedClass) { this.selectedClass = selectedClass; }

    public Date getClassificationDate() { return classificationDate; }
    public void setClassificationDate(Date classificationDate) { this.classificationDate = classificationDate; }

    public AnalysisStatus getClassificationStatus() { return classificationStatus; }
    public void setClassificationStatus(AnalysisStatus classificationStatus) { this.classificationStatus = classificationStatus; }

    @Override
    public String toString() {
        return "{ \"inferredClass\": \"" + inferredClass
                + "\", \"selectedClass\": \"" + selectedClass
                + "\", \"classificationDate\": \"" + classificationDate
                + "\", \"classificationStatus\": \"" + classificationStatus
                + "\" }";
    }
}
