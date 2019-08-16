/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunCrossDomainAnalysis extends AbstractTask {

    @JacksonXmlProperty(isAttribute = true) private Boolean excludeColContainingNulls;
    @JacksonXmlProperty(isAttribute = true) private Float excludeByMinimumUniquenessPercent;
    @JacksonXmlProperty(isAttribute = true) private Boolean excludeDateDataClass;
    @JacksonXmlProperty(isAttribute = true) private Boolean excludeIndicatorsDataClass;
    @JacksonXmlProperty(isAttribute = true) private Boolean excludeTestDataClass;
    @JacksonXmlProperty(isAttribute = true) private Integer excludeByMaximalLength;
    @JacksonXmlProperty(isAttribute = true) private Boolean excludeColumnsMissingColumnAnalysisResults;
    @JacksonXmlProperty(isAttribute = true) private Double minJaccardCoefPercent;
    @JacksonXmlProperty(isAttribute = true) private Double minPKUniquenessPercent;
    @JacksonXmlProperty(isAttribute = true) private Double maxOrphanFKPercent;
    @JacksonXmlProperty(isAttribute = true) private Double minRelationshipConfidencePercent;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ColumnPair")
    private List<ColumnPair> columnPairs;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Table")
    private List<Table> tables;

    public Boolean isExcludeColContainingNulls() { return excludeColContainingNulls; }
    public void setExcludeColContainingNulls(Boolean excludeColContainingNulls) { this.excludeColContainingNulls = excludeColContainingNulls; }

    public Float getExcludeByMinimumUniquenessPercent() { return excludeByMinimumUniquenessPercent; }
    public void setExcludeByMinimumUniquenessPercent(Float excludeByMinimumUniquenessPercent) { this.excludeByMinimumUniquenessPercent = excludeByMinimumUniquenessPercent; }

    public Boolean isExcludeDateDataClass() { return excludeDateDataClass; }
    public void setExcludeDateDataClass(Boolean excludeDateDataClass) { this.excludeDateDataClass = excludeDateDataClass; }

    public Boolean isExcludeIndicatorsDataClass() { return excludeIndicatorsDataClass; }
    public void setExcludeIndicatorsDataClass(Boolean excludeIndicatorsDataClass) { this.excludeIndicatorsDataClass = excludeIndicatorsDataClass; }

    public Boolean isExcludeTestDataClass() { return excludeTestDataClass; }
    public void setExcludeTestDataClass(Boolean excludeTestDataClass) { this.excludeTestDataClass = excludeTestDataClass; }

    public Integer getExcludeByMaximalLength() { return excludeByMaximalLength; }
    public void setExcludeByMaximalLength(Integer excludeByMaximalLength) { this.excludeByMaximalLength = excludeByMaximalLength; }

    public Boolean isExcludeColumnsMissingColumnAnalysisResults() { return excludeColumnsMissingColumnAnalysisResults; }
    public void setExcludeColumnsMissingColumnAnalysisResults(Boolean excludeColumnsMissingColumnAnalysisResults) { this.excludeColumnsMissingColumnAnalysisResults = excludeColumnsMissingColumnAnalysisResults; }

    public Double getMinJaccardCoefPercent() { return minJaccardCoefPercent; }
    public void setMinJaccardCoefPercent(Double minJaccardCoefPercent) { this.minJaccardCoefPercent = minJaccardCoefPercent; }

    public Double getMinPKUniquenessPercent() { return minPKUniquenessPercent; }
    public void setMinPKUniquenessPercent(Double minPKUniquenessPercent) { this.minPKUniquenessPercent = minPKUniquenessPercent; }

    public Double getMaxOrphanFKPercent() { return maxOrphanFKPercent; }
    public void setMaxOrphanFKPercent(Double maxOrphanFKPercent) { this.maxOrphanFKPercent = maxOrphanFKPercent; }

    public Double getMinRelationshipConfidencePercent() { return minRelationshipConfidencePercent; }
    public void setMinRelationshipConfidencePercent(Double minRelationshipConfidencePercent) { this.minRelationshipConfidencePercent = minRelationshipConfidencePercent; }

    public List<ColumnPair> getColumnPairs() { return columnPairs; }
    public void setColumnPairs(List<ColumnPair> columnPairs) { this.columnPairs = columnPairs; }

    public List<Table> getTables() { return tables; }
    public void setTables(List<Table> tables) { this.tables = tables; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"excludeColContainingNulls\": " + excludeColContainingNulls
                + ", \"excludeByMinimumUniquenessPercent\": " + excludeByMinimumUniquenessPercent
                + ", \"excludeDateDataClass\": " + excludeDateDataClass
                + ", \"excludeIndicatorsDataClass\": " + excludeIndicatorsDataClass
                + ", \"excludeTestDataClass\": " + excludeTestDataClass
                + ", \"excludeByMaximalLength\": " + excludeByMaximalLength
                + ", \"excludeColumnsMissingColumnAnalysisResults\": " + excludeColumnsMissingColumnAnalysisResults
                + ", \"minJaccardCoefPercent\": " + minJaccardCoefPercent
                + ", \"minPKUniquenessPercent\": " + minPKUniquenessPercent
                + ", \"maxOrphanFKPercent\": " + maxOrphanFKPercent
                + ", \"minRelationshipConfidencePercent\": " + minRelationshipConfidencePercent
                + ", \"ColumnPairs\": " + (columnPairs == null ? "[]" : columnPairs.toString())
                + ", \"Tables\": " + (tables == null ? "[]" : tables.toString())
                + " }";
    }

}
