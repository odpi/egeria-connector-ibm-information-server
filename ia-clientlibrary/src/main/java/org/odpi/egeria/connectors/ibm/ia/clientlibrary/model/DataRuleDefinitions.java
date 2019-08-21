/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataRuleDefinitions {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DataRuleDefinition")
    private List<DataRuleDefinition> dataRuleDefinitions;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RuleSetDefinition")
    private List<RuleSetDefinition> ruleSetDefinitions;

    public List<DataRuleDefinition> getDataRuleDefinitions() { return dataRuleDefinitions; }
    public void setDataRuleDefinitions(List<DataRuleDefinition> dataRuleDefinitions) { this.dataRuleDefinitions = dataRuleDefinitions; }

    public List<RuleSetDefinition> getRuleSetDefinitions() { return ruleSetDefinitions; }
    public void setRuleSetDefinitions(List<RuleSetDefinition> ruleSetDefinitions) { this.ruleSetDefinitions = ruleSetDefinitions; }

    @Override
    public String toString() {
        return "{ \"DataRuleDefinitions\": " + (dataRuleDefinitions == null ? "[]" : dataRuleDefinitions.toString())
                + ", \"RuleSetDefinitions\": " + (ruleSetDefinitions == null ? "[]" : ruleSetDefinitions.toString())
                + " }";
    }

}
