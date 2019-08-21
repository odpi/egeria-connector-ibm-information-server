/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOptions {

    @JacksonXmlProperty(isAttribute = true) private Boolean debugEnabled = false;
    @JacksonXmlProperty(isAttribute = true) private Long nbOfDebuggedRecords = 20L;
    @JacksonXmlProperty(isAttribute = true) private Integer arraySize = 2000;
    @JacksonXmlProperty(isAttribute = true) private Boolean autoCommit = false;
    @JacksonXmlProperty(isAttribute = true) private Integer isolationLevel = 1;
    @JacksonXmlProperty(isAttribute = true) private Boolean updateExistingTables = false;

    public Boolean isDebugEnabled() { return debugEnabled; }
    public void setDebugEnabled(Boolean debugEnabled) { this.debugEnabled = debugEnabled; }

    public Long getNbOfDebuggedRecords() { return nbOfDebuggedRecords; }
    public void setNbOfDebuggedRecords(Long nbOfDebuggedRecords) { this.nbOfDebuggedRecords = nbOfDebuggedRecords; }

    public Integer getArraySize() { return arraySize; }
    public void setArraySize(Integer arraySize) { this.arraySize = arraySize; }

    public Boolean isAutoCommit() { return autoCommit; }
    public void setAutoCommit(Boolean autoCommit) { this.autoCommit = autoCommit; }

    public Integer getIsolationLevel() { return isolationLevel; }
    public void setIsolationLevel(Integer isolationLevel) { this.isolationLevel = isolationLevel; }

    public Boolean isUpdateExistingTables() { return updateExistingTables; }
    public void setUpdateExistingTables(Boolean updateExistingTables) { this.updateExistingTables = updateExistingTables; }

    @Override
    public String toString() {
        return "{ \"debugEnabled\": " + debugEnabled
                + ", \"nbOfDebuggedRecords\": " + nbOfDebuggedRecords
                + ", \"arraySize\": " + arraySize
                + ", \"autoCommit\": " + autoCommit
                + ", \"isolationLevel\": " + isolationLevel
                + ", \"updateExistingTables\": " + updateExistingTables
                + " }";
    }

}
