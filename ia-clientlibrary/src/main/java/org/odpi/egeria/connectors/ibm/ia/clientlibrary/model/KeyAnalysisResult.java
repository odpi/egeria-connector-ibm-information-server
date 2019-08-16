/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyAnalysisResult {

    @JacksonXmlProperty(isAttribute = true) private Integer nbPrimaryKeyCandidates;
    @JacksonXmlProperty(isAttribute = true) private Boolean primaryKeyDefined;
    @JacksonXmlProperty(isAttribute = true) private Boolean primaryKeySelected;
    @JacksonXmlProperty(isAttribute = true) private Boolean naturalKeyDefined;
    @JacksonXmlProperty(isAttribute = true) private Boolean naturalKeySelected;
    @JacksonXmlProperty(isAttribute = true) private Boolean foreignKeyDefined;
    @JacksonXmlProperty(isAttribute = true) private Boolean foreignKeySelected;
    @JacksonXmlElementWrapper(localName = "SingleColumnKeys")
    private List<SingleColumnKey> singleColumnKeys;
    @JacksonXmlElementWrapper(localName = "MultiColumnKeys")
    private List<MultiColumnKey> multiColumnKeys;

    public Integer getNbPrimaryKeyCandidates() { return nbPrimaryKeyCandidates; }
    public void setNbPrimaryKeyCandidates(Integer nbPrimaryKeyCandidates) { this.nbPrimaryKeyCandidates = nbPrimaryKeyCandidates; }

    public Boolean isPrimaryKeyDefined() { return primaryKeyDefined; }
    public void setPrimaryKeyDefined(Boolean primaryKeyDefined) { this.primaryKeyDefined = primaryKeyDefined; }

    public Boolean isPrimaryKeySelected() { return primaryKeySelected; }
    public void setPrimaryKeySelected(Boolean primaryKeySelected) { this.primaryKeySelected = primaryKeySelected; }

    public Boolean isNaturalKeyDefined() { return naturalKeyDefined; }
    public void setNaturalKeyDefined(Boolean naturalKeyDefined) { this.naturalKeyDefined = naturalKeyDefined; }

    public Boolean isNaturalKeySelected() { return naturalKeySelected; }
    public void setNaturalKeySelected(Boolean naturalKeySelected) { this.naturalKeySelected = naturalKeySelected; }

    public Boolean isForeignKeyDefined() { return foreignKeyDefined; }
    public void setForeignKeyDefined(Boolean foreignKeyDefined) { this.foreignKeyDefined = foreignKeyDefined; }

    public Boolean isForeignKeySelected() { return foreignKeySelected; }
    public void setForeignKeySelected(Boolean foreignKeySelected) { this.foreignKeySelected = foreignKeySelected; }

    public List<SingleColumnKey> getSingleColumnKeys() { return singleColumnKeys; }
    public void setSingleColumnKeys(List<SingleColumnKey> singleColumnKeys) { this.singleColumnKeys = singleColumnKeys; }

    public List<MultiColumnKey> getMultiColumnKeys() { return multiColumnKeys; }
    public void setMultiColumnKeys(List<MultiColumnKey> multiColumnKeys) { this.multiColumnKeys = multiColumnKeys; }

    @Override
    public String toString() {
        return "{ \"nbPrimaryKeyCandidates\": " + nbPrimaryKeyCandidates
                + ", \"primaryKeyDefined\": " + primaryKeyDefined
                + ", \"primaryKeySelected\": " + primaryKeySelected
                + ", \"naturalKeyDefined\": " + naturalKeyDefined
                + ", \"naturalKeySelected\": " + naturalKeySelected
                + ", \"foreignKeyDefined\": " + foreignKeyDefined
                + ", \"foreignKeySelected\": " + foreignKeySelected
                + ", \"SingleColumnKeys\": " + (singleColumnKeys == null ? "[]" : singleColumnKeys.toString())
                + ", \"MultiColumnKeys\": " + (multiColumnKeys == null ? "[]" : multiColumnKeys.toString())
                + " }";
    }

}
