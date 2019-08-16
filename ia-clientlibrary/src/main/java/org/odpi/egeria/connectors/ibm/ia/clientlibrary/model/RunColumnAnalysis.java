/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunColumnAnalysis extends AbstractTask {

    @JacksonXmlProperty(isAttribute = true) private Boolean analyzeColumnProperties;
    @JacksonXmlProperty(isAttribute = true) private CaptureFDResultsType captureFDResultsType;
    @JacksonXmlProperty(isAttribute = true) private Long minFDCaptureSize;
    @JacksonXmlProperty(isAttribute = true) private Long maxFDCaptureSize;
    @JacksonXmlProperty(isAttribute = true) private Boolean analyzeDataClasses;
    @JacksonXmlProperty(isAttribute = true) private Boolean runTermSuggestion;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Column")
    private List<Column> columns;
    @JacksonXmlElementWrapper(localName = "SelectedDataClasses")
    @JacksonXmlProperty(localName = "SelectedDataClass")
    private List<SelectedDataClass> selectedDataClasses;
    @JacksonXmlElementWrapper(localName = "SelectedTermSuggestions")
    @JacksonXmlProperty(localName = "SelectedTermSuggestion")
    private List<SelectedTermSuggestion> selectedTermSuggestions;

    public Boolean isAnalyzeColumnProperties() { return analyzeColumnProperties; }
    public void setAnalyzeColumnProperties(Boolean analyzeColumnProperties) { this.analyzeColumnProperties = analyzeColumnProperties; }

    public CaptureFDResultsType getCaptureFDResultsType() { return captureFDResultsType; }
    public void setCaptureFDResultsType(CaptureFDResultsType captureFDResultsType) { this.captureFDResultsType = captureFDResultsType; }

    public Long getMinFDCaptureSize() { return minFDCaptureSize; }
    public void setMinFDCaptureSize(Long minFDCaptureSize) { this.minFDCaptureSize = minFDCaptureSize; }

    public Long getMaxFDCaptureSize() { return maxFDCaptureSize; }
    public void setMaxFDCaptureSize(Long maxFDCaptureSize) { this.maxFDCaptureSize = maxFDCaptureSize; }

    public Boolean isAnalyzeDataClasses() { return analyzeDataClasses; }
    public void setAnalyzeDataClasses(Boolean analyzeDataClasses) { this.analyzeDataClasses = analyzeDataClasses; }

    public Boolean isRunTermSuggestion() { return runTermSuggestion; }
    public void setRunTermSuggestion(Boolean runTermSuggestion) { this.runTermSuggestion = runTermSuggestion; }

    public List<Column> getColumns() { return columns; }
    public void setColumns(List<Column> columns) { this.columns = columns; }

    public List<SelectedDataClass> getSelectedDataClasses() { return selectedDataClasses; }
    public void setSelectedDataClasses(List<SelectedDataClass> selectedDataClasses) { this.selectedDataClasses = selectedDataClasses; }

    public List<SelectedTermSuggestion> getSelectedTermSuggestions() { return selectedTermSuggestions; }
    public void setSelectedTermSuggestions(List<SelectedTermSuggestion> selectedTermSuggestions) { this.selectedTermSuggestions = selectedTermSuggestions; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"analyzeColumnProperties\": " + analyzeColumnProperties
                + ", \"captureFDResultsType\": \"" + captureFDResultsType
                + "\", \"minFDCaptureSize\": " + minFDCaptureSize
                + ", \"maxFDCaptureSize\": " + maxFDCaptureSize
                + ", \"analyzeDataClasses\": " + analyzeDataClasses
                + ", \"runTermSuggestion\": " + runTermSuggestion
                + ", \"Column\": " + (columns == null ? "[]" : columns.toString())
                + ", \"SelectedDataClasses\": " + (selectedDataClasses == null ? "[]" : selectedDataClasses.toString())
                + ", \"SelectedTermSuggestions\": " + (selectedTermSuggestions == null ? "[]" : selectedTermSuggestions.toString())
                + " }";
    }

}
