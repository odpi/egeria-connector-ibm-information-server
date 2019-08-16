/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskSequence {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private String rid;
    @JacksonXmlProperty(isAttribute = true) private Boolean stopOnError;
    private String description;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunRules")
    private List<RunRules> runRules;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunMetrics")
    private List<RunMetrics> runMetrics;
    @JacksonXmlProperty(localName = "ExecutionHistory")
    private ExecutionHistory executionHistory;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public Boolean isStopOnError() { return stopOnError; }
    public void setStopOnError(Boolean stopOnError) { this.stopOnError = stopOnError; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<RunRules> getRunRules() { return runRules; }
    public void setRunRules(List<RunRules> runRules) { this.runRules = runRules; }

    public List<RunMetrics> getRunMetrics() { return runMetrics; }
    public void setRunMetrics(List<RunMetrics> runMetrics) { this.runMetrics = runMetrics; }

    public ExecutionHistory getExecutionHistory() { return executionHistory; }
    public void setExecutionHistory(ExecutionHistory executionHistory) { this.executionHistory = executionHistory; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"rid\": \"" + rid
                + "\", \"stopOnError\": " + stopOnError
                + ", \"description\": \"" + description
                + "\", \"RunRules\": " + (runRules == null ? "[]" : runRules.toString())
                + ", \"RunMetrics\": " + (runMetrics == null ? "[]" : runMetrics.toString())
                + ", \"ExecutionHistory\": " + (executionHistory == null ? "{}" : executionHistory.toString())
                + " }";
    }

}
