/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cardinality {

    @JacksonXmlProperty(isAttribute = true) private Long count;
    @JacksonXmlProperty(isAttribute = true) private CardinalityType definedCardinalityType;
    @JacksonXmlProperty(isAttribute = true) private CardinalityType inferredCardinalityType;
    @JacksonXmlProperty(isAttribute = true) private CardinalityType selectedCardinalityType;
    @JacksonXmlProperty(isAttribute = true) private String maxValue;
    @JacksonXmlProperty(isAttribute = true) private String minValue;
    @JacksonXmlProperty(isAttribute = true) private Double percent;
    @JacksonXmlProperty(isAttribute = true) private Long sequence;
    @JacksonXmlProperty(isAttribute = true) private Long totalRows;
    @JacksonXmlProperty(isAttribute = true) private Long totalNulls;

    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    public CardinalityType getDefinedCardinalityType() { return definedCardinalityType; }
    public void setDefinedCardinalityType(CardinalityType definedCardinalityType) { this.definedCardinalityType = definedCardinalityType; }

    public CardinalityType getInferredCardinalityType() { return inferredCardinalityType; }
    public void setInferredCardinalityType(CardinalityType inferredCardinalityType) { this.inferredCardinalityType = inferredCardinalityType; }

    public CardinalityType getSelectedCardinalityType() { return selectedCardinalityType; }
    public void setSelectedCardinalityType(CardinalityType selectedCardinalityType) { this.selectedCardinalityType = selectedCardinalityType; }

    public String getMaxValue() { return maxValue; }
    public void setMaxValue(String maxValue) { this.maxValue = maxValue; }

    public String getMinValue() { return minValue; }
    public void setMinValue(String minValue) { this.minValue = minValue; }

    public Double getPercent() { return percent; }
    public void setPercent(Double percent) { this.percent = percent; }

    public Long getSequence() { return sequence; }
    public void setSequence(Long sequence) { this.sequence = sequence; }

    public Long getTotalRows() { return totalRows; }
    public void setTotalRows(Long totalRows) { this.totalRows = totalRows; }

    public Long getTotalNulls() { return totalNulls; }
    public void setTotalNulls(Long totalNulls) { this.totalNulls = totalNulls; }

    @Override
    public String toString() {
        return "{ \"count\": " + count
                + ", \"definedCardinalityType\": \"" + definedCardinalityType
                + "\", \"inferredCardinalityType\": \"" + inferredCardinalityType
                + "\", \"selectedCardinalityType\": \"" + selectedCardinalityType
                + "\", \"maxValue\": \"" + maxValue
                + "\", \"minValue\": \"" + minValue
                + "\", \"percent\": " + percent
                + ", \"sequence\": " + sequence
                + ", \"totalRows\": " + totalRows
                + ", \"totalNulls\": " + totalNulls
                + " }";
    }

}
