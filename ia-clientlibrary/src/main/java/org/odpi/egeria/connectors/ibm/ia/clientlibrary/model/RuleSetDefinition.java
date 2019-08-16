/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleSetDefinition extends AbstractRuleDefinition {

    @JacksonXmlProperty(isAttribute = true) private Boolean separateVariablesByRule;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RuleDefinitionReference")
    private List<RuleDefinitionReference> ruleDefinitionReferences;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DataRuleDefinition")
    private List<DataRuleDefinition> dataRuleDefinitions;

    public Boolean isSeparateVariablesByRule() { return separateVariablesByRule; }
    public void setSeparateVariablesByRule(Boolean separateVariablesByRule) { this.separateVariablesByRule = separateVariablesByRule; }

    public List<RuleDefinitionReference> getRuleDefinitionReferences() { return ruleDefinitionReferences; }
    public void setRuleDefinitionReferences(List<RuleDefinitionReference> ruleDefinitionReferences) { this.ruleDefinitionReferences = ruleDefinitionReferences; }

    public List<DataRuleDefinition> getDataRuleDefinitions() { return dataRuleDefinitions; }
    public void setDataRuleDefinitions(List<DataRuleDefinition> dataRuleDefinitions) { this.dataRuleDefinitions = dataRuleDefinitions; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"separateVariablesByRule\": " + separateVariablesByRule
                + ", \"RuleDefinitionReferences\": " + (ruleDefinitionReferences == null ? "[]" : ruleDefinitionReferences.toString())
                + ", \"DataRuleDefinitions\": " + (dataRuleDefinitions == null ? "[]" : dataRuleDefinitions.toString())
                + " }";
    }

}
