/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Column {

    @JacksonXmlProperty(isAttribute = true) private String rid;
    @JacksonXmlProperty(isAttribute = true) private DataTypeCode type;
    @JacksonXmlProperty(isAttribute = true) private Integer length;
    @JacksonXmlProperty(isAttribute = true) private Integer scale;
    @JacksonXmlProperty(isAttribute = true) private Integer precision;
    @JacksonXmlProperty(isAttribute = true) private Boolean virtual;
    @JacksonXmlProperty(isAttribute = true) private Double qualityScore;
    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private Date publishedOn;
    private String description;
    @JacksonXmlProperty(localName = "ColumnAnalysisResults")
    private ColumnAnalysisResults columnAnalysisResults;
    @JacksonXmlProperty(localName = "DataClassificationResults")
    private DataClassificationResults dataClassificationResults;
    @JacksonXmlProperty(localName = "ColumnConstraints")
    private ColumnConstraints columnConstraints;
    @JacksonXmlProperty(localName = "DataQualitySettings")
    private DataQualitySettings dataQualitySettings;
    @JacksonXmlElementWrapper(localName = "DataQualityProblems")
    private List<DataQualityProblem> dataQualityProblems;

    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public DataTypeCode getType() { return type; }
    public void setType(DataTypeCode type) { this.type = type; }

    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }

    public Integer getScale() { return scale; }
    public void setScale(Integer scale) { this.scale = scale; }

    public Integer getPrecision() { return precision; }
    public void setPrecision(Integer precision) { this.precision = precision; }

    public Boolean isVirtual() { return virtual; }
    public void setVirtual(Boolean virtual) { this.virtual = virtual; }

    public Double getQualityScore() { return qualityScore; }
    public void setQualityScore(Double qualityScore) { this.qualityScore = qualityScore; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getPublishedOn() { return publishedOn; }
    public void setPublishedOn(Date publishedOn) { this.publishedOn = publishedOn; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ColumnAnalysisResults getColumnAnalysisResults() { return columnAnalysisResults; }
    public void setColumnAnalysisResults(ColumnAnalysisResults columnAnalysisResults) { this.columnAnalysisResults = columnAnalysisResults; }

    public List<DataQualityProblem> getDataQualityProblems() { return dataQualityProblems; }
    public void setDataQualityProblems(List<DataQualityProblem> dataQualityProblems) { this.dataQualityProblems = dataQualityProblems; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"rid\": \"" + rid
                + "\", \"type\": \"" + type
                + "\", \"length\": " + length
                + ", \"scale\": " + scale
                + ", \"precision\": " + precision
                + ", \"virtual\": " + virtual
                + ", \"qualityScore\": " + qualityScore
                + ", \"description\": \"" + description
                + "\", \"publishedOn\": \"" + publishedOn
                + "\", \"ColumnAnalysisResults\": " + (columnAnalysisResults == null ? "{}" : columnAnalysisResults.toString())
                + ", \"DataClassificationResults\": " + (dataClassificationResults == null ? "{}" : dataClassificationResults.toString())
                + ", \"ColumnConstraints\": " + (columnConstraints == null ? "{}" : columnConstraints.toString())
                + ", \"DataQualitySettings\": " + (dataQualitySettings == null ? "{}" : dataQualitySettings.toString())
                + ", \"DataQualityProblems\": " + (dataQualityProblems == null ? "{}" : dataQualityProblems.toString())
                + " }";
    }

}
