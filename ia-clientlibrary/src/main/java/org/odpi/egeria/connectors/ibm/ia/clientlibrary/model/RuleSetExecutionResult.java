/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleSetExecutionResult extends AbstractRuleExecutionResult {

    @JacksonXmlProperty(isAttribute = true) private Integer nbRules;
    @JacksonXmlProperty(isAttribute = true) private Double meanNbFailedRulesPerRecord;
    @JacksonXmlProperty(isAttribute = true) private Double stddevNbFailedRulesPerRecord;
    @JacksonXmlProperty(isAttribute = true) private Double meanPercentFailedRulesPerRecord;
    @JacksonXmlProperty(isAttribute = true) private Double stddevPercentFailedRulesPerRecord;
    @JacksonXmlElementWrapper(localName = "RelativeFailureDistribution")
    private List<ContinuousDistributionItem> relativeFailureDistribution;
    @JacksonXmlElementWrapper(localName = "AbsoluteFailureDistribution")
    private List<DiscreteDistributionItem> absoluteFailureDistribution;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RuleExecutionResult")
    private List<RuleExecutionResult> ruleExecutionResults;

    public Integer getNbRules() { return nbRules; }
    public void setNbRules(Integer nbRules) { this.nbRules = nbRules; }

    public Double getMeanNbFailedRulesPerRecord() { return meanNbFailedRulesPerRecord; }
    public void setMeanNbFailedRulesPerRecord(Double meanNbFailedRulesPerRecord) { this.meanNbFailedRulesPerRecord = meanNbFailedRulesPerRecord; }

    public Double getStddevNbFailedRulesPerRecord() { return stddevNbFailedRulesPerRecord; }
    public void setStddevNbFailedRulesPerRecord(Double stddevNbFailedRulesPerRecord) { this.stddevNbFailedRulesPerRecord = stddevNbFailedRulesPerRecord; }

    public Double getMeanPercentFailedRulesPerRecord() { return meanPercentFailedRulesPerRecord; }
    public void setMeanPercentFailedRulesPerRecord(Double meanPercentFailedRulesPerRecord) { this.meanPercentFailedRulesPerRecord = meanPercentFailedRulesPerRecord; }

    public Double getStddevPercentFailedRulesPerRecord() { return stddevPercentFailedRulesPerRecord; }
    public void setStddevPercentFailedRulesPerRecord(Double stddevPercentFailedRulesPerRecord) { this.stddevPercentFailedRulesPerRecord = stddevPercentFailedRulesPerRecord; }

    public List<ContinuousDistributionItem> getRelativeFailureDistribution() { return relativeFailureDistribution; }
    public void setRelativeFailureDistribution(List<ContinuousDistributionItem> relativeFailureDistribution) { this.relativeFailureDistribution = relativeFailureDistribution; }

    public List<DiscreteDistributionItem> getAbsoluteFailureDistribution() { return absoluteFailureDistribution; }
    public void setAbsoluteFailureDistribution(List<DiscreteDistributionItem> absoluteFailureDistribution) { this.absoluteFailureDistribution = absoluteFailureDistribution; }

    public List<RuleExecutionResult> getRuleExecutionResults() { return ruleExecutionResults; }
    public void setRuleExecutionResults(List<RuleExecutionResult> ruleExecutionResults) { this.ruleExecutionResults = ruleExecutionResults; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"nbRules\": " + nbRules
                + ", \"meanNbFailedRulesPerRecord\": " + meanNbFailedRulesPerRecord
                + ", \"stddevNbFailedRulesPerRecord\": " + stddevNbFailedRulesPerRecord
                + ", \"meanPercentFailedRulesPerRecord\": " + meanPercentFailedRulesPerRecord
                + ", \"stddevPercentFailedRulesPerRecord\": " + stddevPercentFailedRulesPerRecord
                + ", \"RelativeFailureDistribution\": " + (relativeFailureDistribution == null ? "{}" : relativeFailureDistribution.toString())
                + ", \"AbsoluteFailureDistribution\": " + (absoluteFailureDistribution == null ? "{}" : absoluteFailureDistribution.toString())
                + ", \"RuleExecutionResults\": " + (ruleExecutionResults == null ? "[]" : ruleExecutionResults.toString())
                + " }";
    }

}
