/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MultiColumnKey {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Column")
    private List<Column> columns;
    @JacksonXmlProperty(isAttribute = true) private Integer nbOfColumns;
    @JacksonXmlProperty(isAttribute = true) private Double percentUnique;
    @JacksonXmlProperty(isAttribute = true) private Double percentDuplicate;
    @JacksonXmlProperty(isAttribute = true) private Integer nbForeignKeyCandidates;
    @JacksonXmlProperty(isAttribute = true) private Boolean isPrimaryKeyCandidate;
    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedPrimaryKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedPrimaryKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedForeignKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedForeignKey;

    public List<Column> getColumns() { return columns; }
    public void setColumns(List<Column> columns) { this.columns = columns; }

    public Integer getNbOfColumns() { return nbOfColumns; }
    public void setNbOfColumns(Integer nbOfColumns) { this.nbOfColumns = nbOfColumns; }

    public Double getPercentUnique() { return percentUnique; }
    public void setPercentUnique(Double percentUnique) { this.percentUnique = percentUnique; }

    public Double getPercentDuplicate() { return percentDuplicate; }
    public void setPercentDuplicate(Double percentDuplicate) { this.percentDuplicate = percentDuplicate; }

    public Integer getNbForeignKeyCandidates() { return nbForeignKeyCandidates; }
    public void setNbForeignKeyCandidates(Integer nbForeignKeyCandidates) { this.nbForeignKeyCandidates = nbForeignKeyCandidates; }

    public Boolean isPrimaryKeyCandidate() { return isPrimaryKeyCandidate; }
    public void setPrimaryKeyCandidate(Boolean primaryKeyCandidate) { isPrimaryKeyCandidate = primaryKeyCandidate; }

    public Boolean isSelectedPrimaryKey() { return isSelectedPrimaryKey; }
    public void setSelectedPrimaryKey(Boolean selectedPrimaryKey) { isSelectedPrimaryKey = selectedPrimaryKey; }

    public Boolean isDefinedPrimaryKey() { return isDefinedPrimaryKey; }
    public void setDefinedPrimaryKey(Boolean definedPrimaryKey) { isDefinedPrimaryKey = definedPrimaryKey; }

    public Boolean isSelectedForeignKey() { return isSelectedForeignKey; }
    public void setSelectedForeignKey(Boolean selectedForeignKey) { isSelectedForeignKey = selectedForeignKey; }

    public Boolean isDefinedForeignKey() { return isDefinedForeignKey; }
    public void setDefinedForeignKey(Boolean definedForeignKey) { isDefinedForeignKey = definedForeignKey; }

    @Override
    public String toString() {
        return "{ \"Columns\": " + (columns == null ? "[]" : columns.toString())
                + ", \"nbOfColumns\": " + nbOfColumns
                + ", \"percentUnique\": " + percentUnique
                + ", \"percentDuplicate\": " + percentDuplicate
                + ", \"nbForeignKeyCandidates\": " + nbForeignKeyCandidates
                + ", \"isPrimaryKeyCandidate\": " + isPrimaryKeyCandidate
                + ", \"isSelectedPrimaryKey\": " + isSelectedPrimaryKey
                + ", \"isDefinedPrimaryKey\": " + isDefinedPrimaryKey
                + ", \"isSelectedForeignKey\": " + isSelectedForeignKey
                + ", \"isDefinedForeignKey\": " + isDefinedForeignKey
                + " }";
    }

}
