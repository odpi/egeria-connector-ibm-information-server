/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQualitySettings {

    @JacksonXmlElementWrapper(localName = "IgnoredDataQualityProblems")
    private List<DataQualityProblemType> ignoredDataQualityProblems;

    public List<DataQualityProblemType> getIgnoredDataQualityProblems() { return ignoredDataQualityProblems; }
    public void setIgnoredDataQualityProblems(List<DataQualityProblemType> ignoredDataQualityProblems) { this.ignoredDataQualityProblems = ignoredDataQualityProblems; }

    @Override
    public String toString() {
        return "{ \"IgnoredDataQualityProblems\": " + (ignoredDataQualityProblems == null ? "[]" : ignoredDataQualityProblems.toString()) + " }";
    }

}
