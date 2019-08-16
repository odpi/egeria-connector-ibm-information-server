/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EngineOptions {

    @JacksonXmlProperty(isAttribute = true) private Boolean retainOsh = false;
    @JacksonXmlProperty(isAttribute = true) private Boolean retainDataSets = false;
    @JacksonXmlProperty(isAttribute = true) private String PXConfigurationFile;
    @JacksonXmlProperty(isAttribute = true) private Boolean gridEnabled = false;
    @JacksonXmlProperty(isAttribute = true) private Integer requestedNodes = 1;
    @JacksonXmlProperty(isAttribute = true) private Integer minimumNodes = 1;
    @JacksonXmlProperty(isAttribute = true) private Integer partitionsPerNode = 1;

    public Boolean isRetainOsh() { return retainOsh; }
    public void setRetainOsh(Boolean retainOsh) { this.retainOsh = retainOsh; }

    public Boolean isRetainDataSets() { return retainDataSets; }
    public void setRetainDataSets(Boolean retainDataSets) { this.retainDataSets = retainDataSets; }

    public String getPXConfigurationFile() { return PXConfigurationFile; }
    public void setPXConfigurationFile(String PXConfigurationFile) { this.PXConfigurationFile = PXConfigurationFile; }

    public Boolean isGridEnabled() { return gridEnabled; }
    public void setGridEnabled(Boolean gridEnabled) { this.gridEnabled = gridEnabled; }

    public Integer getRequestedNodes() { return requestedNodes; }
    public void setRequestedNodes(Integer requestedNodes) { this.requestedNodes = requestedNodes; }

    public Integer getMinimumNodes() { return minimumNodes; }
    public void setMinimumNodes(Integer minimumNodes) { this.minimumNodes = minimumNodes; }

    public Integer getPartitionsPerNode() { return partitionsPerNode; }
    public void setPartitionsPerNode(Integer partitionsPerNode) { this.partitionsPerNode = partitionsPerNode; }

    @Override
    public String toString() {
        return "{ \"retainOsh\": " + retainOsh
                + ", \"retainDataSets\": " + retainDataSets
                + ", \"PXConfigurationFile\": \"" + PXConfigurationFile
                + "\", \"gridEnabled\": " + gridEnabled
                + ", \"requestedNodes\": " + requestedNodes
                + ", \"minimumNodes\": " + minimumNodes
                + ", \"partitionsPerNode\": " + partitionsPerNode
                + " }";
    }

}
