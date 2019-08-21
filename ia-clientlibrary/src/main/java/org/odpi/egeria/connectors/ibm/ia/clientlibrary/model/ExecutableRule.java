/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutableRule extends AbstractQualityControl {

    @JacksonXmlProperty(isAttribute = true) private Boolean excludeDuplicates;
    @JacksonXmlProperty(localName = "BoundExpression")
    private String boundExpression;
    @JacksonXmlProperty(localName = "OutputDefinition")
    private OutputDefinition outputDefinition;
    @JacksonXmlElementWrapper(localName = "Bindings")
    private List<Binding> bindings;
    @JacksonXmlElementWrapper(localName = "JoinConditions")
    private List<JoinCondition> joinConditions;
    @JacksonXmlProperty(localName = "ExecutionHistory")
    private ExecutionHistory executionHistory;
    @JacksonXmlProperty(localName = "Benchmarks")
    private Benchmarks benchmarks;
    @JacksonXmlElementWrapper(localName = "OutputLinks")
    private List<OutputLink> outputLinks;
    @JacksonXmlElementWrapper(localName = "InputLinks")
    private List<InputLink> inputLinks;
    @JacksonXmlProperty(localName = "LandingTable")
    private LandingTable landingTable;

    public Boolean isExcludeDuplicates() { return excludeDuplicates; }
    public void setExcludeDuplicates(Boolean excludeDuplicates) { this.excludeDuplicates = excludeDuplicates; }

    public String getBoundExpression() { return boundExpression; }
    public void setBoundExpression(String boundExpression) { this.boundExpression = boundExpression; }

    public OutputDefinition getOutputDefinition() { return outputDefinition; }
    public void setOutputDefinition(OutputDefinition outputDefinition) { this.outputDefinition = outputDefinition; }

    public List<Binding> getBindings() { return bindings; }
    public void setBindings(List<Binding> bindings) { this.bindings = bindings; }

    public List<JoinCondition> getJoinConditions() { return joinConditions; }
    public void setJoinConditions(List<JoinCondition> joinConditions) { this.joinConditions = joinConditions; }

    public ExecutionHistory getExecutionHistory() { return executionHistory; }
    public void setExecutionHistory(ExecutionHistory executionHistory) { this.executionHistory = executionHistory; }

    public Benchmarks getBenchmarks() { return benchmarks; }
    public void setBenchmarks(Benchmarks benchmarks) { this.benchmarks = benchmarks; }

    public List<OutputLink> getOutputLinks() { return outputLinks; }
    public void setOutputLinks(List<OutputLink> outputLinks) { this.outputLinks = outputLinks; }

    public List<InputLink> getInputLinks() { return inputLinks; }
    public void setInputLinks(List<InputLink> inputLinks) { this.inputLinks = inputLinks; }

    public LandingTable getLandingTable() { return landingTable; }
    public void setLandingTable(LandingTable landingTable) { this.landingTable = landingTable; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"excludeDuplicates\": " + excludeDuplicates
                + ", \"boundExpression\": \"" + boundExpression.replaceAll("\"", "'").replaceAll("\n", "")
                + "\", \"OutputDefinition\": " + (outputDefinition == null ? "{}" : outputDefinition.toString())
                + ", \"Bindings\": " + (bindings == null ? "[]" : bindings.toString())
                + ", \"JoinConditions\": " + (joinConditions == null ? "[]" : joinConditions.toString())
                + ", \"ExecutionHistory\": " + (executionHistory == null ? "{}" : executionHistory.toString())
                + ", \"Benchmarks\": " + (benchmarks == null ? "{}" : benchmarks.toString())
                + ", \"OutputLinks\": " + (outputLinks == null ? "[]" : outputLinks.toString())
                + ", \"InputLinks\": " + (inputLinks == null ? "[]" : inputLinks.toString())
                + ", \"LandingTable\": " + (landingTable == null ? "{}" : landingTable.toString())
                + " }";
    }

}
