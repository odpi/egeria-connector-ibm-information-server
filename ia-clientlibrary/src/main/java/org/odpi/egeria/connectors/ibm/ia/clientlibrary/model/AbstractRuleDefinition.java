/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractRuleDefinition extends AbstractQualityControl {

    @JacksonXmlProperty(isAttribute = true) private String publishedAs;
    @JacksonXmlElementWrapper(localName = "Variables")
    private List<Variable> variables;
    @JacksonXmlElementWrapper(localName = "ExecutableRules")
    private List<ExecutableRule> executableRules;
    @JacksonXmlProperty(localName = "Benchmarks")
    private Benchmarks benchmarks;

    public String getPublishedAs() { return publishedAs; }
    public void setPublishedAs(String publishedAs) { this.publishedAs = publishedAs; }

    public List<Variable> getVariables() { return variables; }
    public void setVariables(List<Variable> variables) { this.variables = variables; }

    public List<ExecutableRule> getExecutableRules() { return executableRules; }
    public void setExecutableRules(List<ExecutableRule> executableRules) { this.executableRules = executableRules; }

    public Benchmarks getBenchmarks() { return benchmarks; }
    public void setBenchmarks(Benchmarks benchmarks) { this.benchmarks = benchmarks; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"publishedAs\": \"" + publishedAs
                + "\", \"Variables\": " + (variables == null ? "[]" : variables.toString())
                + ", \"ExecutableRules\": " + (executableRules == null ? "[]" : executableRules.toString())
                + ", \"Benchmarks\": " + (benchmarks == null ? "{}" : benchmarks.toString())
                + " }";
    }

}
