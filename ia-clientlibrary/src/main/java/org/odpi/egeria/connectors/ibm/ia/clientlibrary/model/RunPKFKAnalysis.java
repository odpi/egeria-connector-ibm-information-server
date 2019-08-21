/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunPKFKAnalysis extends AbstractTask {

    @JacksonXmlProperty(isAttribute = true) private Integer compositeMax;
    @JacksonXmlProperty(isAttribute = true) private Double minRelationshipConfidencePercent;
    @JacksonXmlProperty(isAttribute = true) private Double minPKUniquenessPercent;
    @JacksonXmlProperty(isAttribute = true) private Double maxOrphanFKPercent;
    @JacksonXmlProperty(isAttribute = true) private Double minJaccardCoefPercent;
    @JacksonXmlProperty(isAttribute = true) private Boolean optimizeForTime;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Table")
    private List<Table> tables;

    public Integer getCompositeMax() { return compositeMax; }
    public void setCompositeMax(Integer compositeMax) { this.compositeMax = compositeMax; }

    public Double getMinRelationshipConfidencePercent() { return minRelationshipConfidencePercent; }
    public void setMinRelationshipConfidencePercent(Double minRelationshipConfidencePercent) { this.minRelationshipConfidencePercent = minRelationshipConfidencePercent; }

    public Double getMinPKUniquenessPercent() { return minPKUniquenessPercent; }
    public void setMinPKUniquenessPercent(Double minPKUniquenessPercent) { this.minPKUniquenessPercent = minPKUniquenessPercent; }

    public Double getMaxOrphanFKPercent() { return maxOrphanFKPercent; }
    public void setMaxOrphanFKPercent(Double maxOrphanFKPercent) { this.maxOrphanFKPercent = maxOrphanFKPercent; }

    public Double getMinJaccardCoefPercent() { return minJaccardCoefPercent; }
    public void setMinJaccardCoefPercent(Double minJaccardCoefPercent) { this.minJaccardCoefPercent = minJaccardCoefPercent; }

    public Boolean isOptimizeForTime() { return optimizeForTime; }
    public void setOptimizeForTime(Boolean optimizeForTime) { this.optimizeForTime = optimizeForTime; }

    public List<Table> getTables() { return tables; }
    public void setTables(List<Table> tables) { this.tables = tables; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"compositeMax\": " + compositeMax
                + ", \"minRelationshipConfidencePercent\": " + minRelationshipConfidencePercent
                + ", \"minPKUniquenessPercent\": " + minPKUniquenessPercent
                + ", \"maxOrphanFKPercent\": " + maxOrphanFKPercent
                + ", \"minJaccardCoefPercent\": " + minJaccardCoefPercent
                + ", \"optimizeForTime\": " + optimizeForTime
                + ", \"Tables\": " + (tables == null ? "[]" : tables.toString())
                + " }";
    }

}
