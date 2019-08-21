/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleColumnKey {

    @JacksonXmlProperty(localName = "Column")
    private Column column;
    @JacksonXmlProperty(isAttribute = true) private String alias;
    @JacksonXmlProperty(isAttribute = true) private Double percentUnique;
    @JacksonXmlProperty(isAttribute = true) private Double percentDuplicate;
    @JacksonXmlProperty(isAttribute = true) private Double percentNull;
    @JacksonXmlProperty(isAttribute = true) private String dataClass;
    @JacksonXmlProperty(isAttribute = true) private DataTypeCode dataType;
    @JacksonXmlProperty(isAttribute = true) private Integer nbForeignKeyCandidates;
    @JacksonXmlProperty(isAttribute = true) private Boolean isPrimaryKeyCandidate;
    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedPrimaryKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedPrimaryKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedNaturalKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedNatualKey; // TODO: mis-spelled in XSD, is XSD correct?
    @JacksonXmlProperty(isAttribute = true) private Boolean isSelectedForeignKey;
    @JacksonXmlProperty(isAttribute = true) private Boolean isDefinedForeignKey;
    @JacksonXmlProperty(isAttribute = true) private Integer length;

    public Column getColumn() { return column; }
    public void setColumn(Column column) { this.column = column; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public Double getPercentUnique() { return percentUnique; }
    public void setPercentUnique(Double percentUnique) { this.percentUnique = percentUnique; }

    public Double getPercentDuplicate() { return percentDuplicate; }
    public void setPercentDuplicate(Double percentDuplicate) { this.percentDuplicate = percentDuplicate; }

    public Double getPercentNull() { return percentNull; }
    public void setPercentNull(Double percentNull) { this.percentNull = percentNull; }

    public String getDataClass() { return dataClass; }
    public void setDataClass(String dataClass) { this.dataClass = dataClass; }

    public DataTypeCode getDataType() { return dataType; }
    public void setDataType(DataTypeCode dataType) { this.dataType = dataType; }

    public Integer getNbForeignKeyCandidates() { return nbForeignKeyCandidates; }
    public void setNbForeignKeyCandidates(Integer nbForeignKeyCandidates) { this.nbForeignKeyCandidates = nbForeignKeyCandidates; }

    public Boolean isPrimaryKeyCandidate() { return isPrimaryKeyCandidate; }
    public void setPrimaryKeyCandidate(Boolean primaryKeyCandidate) { isPrimaryKeyCandidate = primaryKeyCandidate; }

    public Boolean isSelectedPrimaryKey() { return isSelectedPrimaryKey; }
    public void setSelectedPrimaryKey(Boolean selectedPrimaryKey) { isSelectedPrimaryKey = selectedPrimaryKey; }

    public Boolean isDefinedPrimaryKey() { return isDefinedPrimaryKey; }
    public void setDefinedPrimaryKey(Boolean definedPrimaryKey) { isDefinedPrimaryKey = definedPrimaryKey; }

    public Boolean isSelectedNaturalKey() { return isSelectedNaturalKey; }
    public void setSelectedNaturalKey(Boolean selectedNaturalKey) { isSelectedNaturalKey = selectedNaturalKey; }

    public Boolean isDefinedNatualKey() { return isDefinedNatualKey; }
    public void setDefinedNatualKey(Boolean definedNatualKey) { isDefinedNatualKey = definedNatualKey; }

    public Boolean isSelectedForeignKey() { return isSelectedForeignKey; }
    public void setSelectedForeignKey(Boolean selectedForeignKey) { isSelectedForeignKey = selectedForeignKey; }

    public Boolean isDefinedForeignKey() { return isDefinedForeignKey; }
    public void setDefinedForeignKey(Boolean definedForeignKey) { isDefinedForeignKey = definedForeignKey; }

    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }

    @Override
    public String toString() {
        return "{ \"Column\": " + (column == null ? "{}" : column.toString())
                + ", \"alias\": \"" + alias
                + "\", \"percentUnique\": " + percentUnique
                + ", \"percentDuplicate\": " + percentDuplicate
                + ", \"percentNull\": " + percentNull
                + ", \"dataClass\": \"" + dataClass
                + "\", \"dataType\": \"" + dataType
                + "\", \"nbForeignKeyCandidates\": " + nbForeignKeyCandidates
                + ", \"isPrimaryKeyCandidate\": " + isPrimaryKeyCandidate
                + ", \"isSelectedPrimaryKey\": " + isSelectedPrimaryKey
                + ", \"isDefinedPrimaryKey\": " + isDefinedPrimaryKey
                + ", \"isSelectedNaturalKey\": " + isSelectedNaturalKey
                + ", \"isDefinedNatualKey\": " + isDefinedNatualKey
                + ", \"isSelectedForeignKey\": " + isSelectedForeignKey
                + ", \"isDefinedForeignKey\": " + isDefinedForeignKey
                + ", \"length\": " + length
                + " }";
    }

}
