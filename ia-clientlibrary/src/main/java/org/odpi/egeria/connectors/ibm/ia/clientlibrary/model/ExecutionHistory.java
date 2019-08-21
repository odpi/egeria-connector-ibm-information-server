/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutionHistory {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RuleExecutionResult")
    private List<RuleExecutionResult> ruleExecutionResults;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RuleSetExecutionResult")
    private List<RuleSetExecutionResult> ruleSetExecutionResults;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MetricExecutionResult")
    private List<MetricExecutionResult> metricExecutionResults;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TaskSequenceExecutionResult")
    private List<TaskSequenceExecutionResult> taskSequenceExecutionResults;

    public List<RuleExecutionResult> getRuleExecutionResults() { return ruleExecutionResults; }
    public void setRuleExecutionResults(List<RuleExecutionResult> ruleExecutionResults) { this.ruleExecutionResults = ruleExecutionResults; }

    public List<RuleSetExecutionResult> getRuleSetExecutionResults() { return ruleSetExecutionResults; }
    public void setRuleSetExecutionResults(List<RuleSetExecutionResult> ruleSetExecutionResults) { this.ruleSetExecutionResults = ruleSetExecutionResults; }

    public List<MetricExecutionResult> getMetricExecutionResults() { return metricExecutionResults; }
    public void setMetricExecutionResults(List<MetricExecutionResult> metricExecutionResults) { this.metricExecutionResults = metricExecutionResults; }

    public List<TaskSequenceExecutionResult> getTaskSequenceExecutionResults() { return taskSequenceExecutionResults; }
    public void setTaskSequenceExecutionResults(List<TaskSequenceExecutionResult> taskSequenceExecutionResults) { this.taskSequenceExecutionResults = taskSequenceExecutionResults; }

    @Override
    public String toString() {
        return "{ \"RuleExecutionResults\": " + (ruleExecutionResults == null ? "[]" : ruleExecutionResults.toString())
                + ", \"RuleSetExecutionResults\": " + (ruleSetExecutionResults == null ? "[]" : ruleSetExecutionResults.toString())
                + ", \"MetricExecutionResults\": " + (metricExecutionResults == null ? "[]" : metricExecutionResults.toString())
                + ", \"TaskSequenceExecutionResults\": " + (taskSequenceExecutionResults == null ? "[]" : taskSequenceExecutionResults.toString())
                +" }";
    }

}
