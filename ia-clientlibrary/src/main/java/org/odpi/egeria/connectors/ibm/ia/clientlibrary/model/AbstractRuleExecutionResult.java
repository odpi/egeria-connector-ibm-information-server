/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractRuleExecutionResult {

    @JacksonXmlProperty(isAttribute = true) private String id;
    @JacksonXmlProperty(isAttribute = true) private ExecutionStatus status = ExecutionStatus.unknown;
    @JacksonXmlProperty(isAttribute = true) private Date startTime;
    @JacksonXmlProperty(isAttribute = true) private Date endTime;
    @JacksonXmlProperty(isAttribute = true) private String runBy;
    @JacksonXmlProperty(isAttribute = true) private Long nbOfRecords;
    @JacksonXmlProperty(isAttribute = true) private String rid;
    @JacksonXmlProperty(isAttribute = true) private Long nbPassed;
    @JacksonXmlProperty(isAttribute = true) private Long nbFailed;
    @JacksonXmlProperty(isAttribute = true) private Double percentPassed;
    @JacksonXmlProperty(isAttribute = true) private Double percentFailed;
    @JacksonXmlProperty(localName = "RuntimeMetaData")
    private RuntimeMetaData runtimeMetaData;
    @JacksonXmlProperty(localName = "Benchmarks")
    private Benchmarks benchmarks;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ExecutionStatus getStatus() { return status; }
    public void setStatus(ExecutionStatus status) { this.status = status; }

    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public String getRunBy() { return runBy; }
    public void setRunBy(String runBy) { this.runBy = runBy; }

    public Long getNbOfRecords() { return nbOfRecords; }
    public void setNbOfRecords(Long nbOfRecords) { this.nbOfRecords = nbOfRecords; }

    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public Long getNbPassed() { return nbPassed; }
    public void setNbPassed(Long nbPassed) { this.nbPassed = nbPassed; }

    public Long getNbFailed() { return nbFailed; }
    public void setNbFailed(Long nbFailed) { this.nbFailed = nbFailed; }

    public Double getPercentPassed() { return percentPassed; }
    public void setPercentPassed(Double percentPassed) { this.percentPassed = percentPassed; }

    public Double getPercentFailed() { return percentFailed; }
    public void setPercentFailed(Double percentFailed) { this.percentFailed = percentFailed; }

    public RuntimeMetaData getRuntimeMetaData() { return runtimeMetaData; }
    public void setRuntimeMetaData(RuntimeMetaData runtimeMetaData) { this.runtimeMetaData = runtimeMetaData; }

    public Benchmarks getBenchmarks() { return benchmarks; }
    public void setBenchmarks(Benchmarks benchmarks) { this.benchmarks = benchmarks; }

    @Override
    public String toString() {
        return "{ \"id\": \"" + id
                + "\", \"status\": \"" + status
                + "\", \"startTime\": \"" + startTime
                + "\", \"endTime\": \"" + endTime
                + "\", \"runBy\": \"" + runBy
                + "\", \"nbOfRecords\": " + nbOfRecords
                + ", \"rid\": \"" + rid
                + "\", \"nbPassed\": " + nbPassed
                + ", \"nbFailed\": " + nbFailed
                + ", \"percentPassed\": " + percentPassed
                + ", \"percentFailed\": " + percentFailed
                + ", \"RuntimeMetaData\": " + (runtimeMetaData == null ? "{}" : runtimeMetaData.toString())
                + ", \"Benchmarks\": " + (benchmarks == null ? "{}" : benchmarks.toString())
                + " }";
    }

}
