/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Table {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private String rid;
    @JacksonXmlProperty(isAttribute = true) private Date publicationDateOfAnalysisResults;
    @JacksonXmlProperty(isAttribute = true) private Double qualityScore;
    private String description;
    @JacksonXmlProperty(localName = "WhereCondition")
    private String whereCondition;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Column")
    private List<Column> columns;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "VirtualColumn")
    private List<VirtualColumn> virtualColumns;
    @JacksonXmlProperty(localName = "KeyAnalysisResult")
    private KeyAnalysisResult keyAnalysisResult;
    @JacksonXmlProperty(localName = "DataQualitySettings")
    private DataQualitySettings dataQualitySettings;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public Date getPublicationDateOfAnalysisResults() { return publicationDateOfAnalysisResults; }
    public void setPublicationDateOfAnalysisResults(Date publicationDateOfAnalysisResults) { this.publicationDateOfAnalysisResults = publicationDateOfAnalysisResults; }

    public Double getQualityScore() { return qualityScore; }
    public void setQualityScore(Double qualityScore) { this.qualityScore = qualityScore; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWhereCondition() { return whereCondition; }
    public void setWhereCondition(String whereCondition) { this.whereCondition = whereCondition; }

    public List<Column> getColumns() { return columns; }
    public void setColumns(List<Column> columns) { this.columns = columns; }

    public List<VirtualColumn> getVirtualColumns() { return virtualColumns; }
    public void setVirtualColumns(List<VirtualColumn> virtualColumns) { this.virtualColumns = virtualColumns; }

    public KeyAnalysisResult getKeyAnalysisResult() { return keyAnalysisResult; }
    public void setKeyAnalysisResult(KeyAnalysisResult keyAnalysisResult) { this.keyAnalysisResult = keyAnalysisResult; }

    public DataQualitySettings getDataQualitySettings() { return dataQualitySettings; }
    public void setDataQualitySettings(DataQualitySettings dataQualitySettings) { this.dataQualitySettings = dataQualitySettings; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"rid\": \"" + rid
                + "\", \"publicationDateOfAnalysisResults\": \"" + publicationDateOfAnalysisResults
                + "\", \"qualityScore\": " + qualityScore
                + ", \"description\": \"" + description
                + "\", \"whereCondition\": \"" + whereCondition
                + "\", \"Columns\": " + (columns == null ? "[]" : columns.toString())
                + ", \"VirtualColumns\": " + (virtualColumns == null ? "[]" : virtualColumns.toString())
                + ", \"KeyAnalysisResult\": " + (keyAnalysisResult == null ? "{}" : keyAnalysisResult.toString())
                + ", \"DataQualitySettings\": " + (dataQualitySettings == null ? "{}" : dataQualitySettings.toString())
                + " }";
    }

}
