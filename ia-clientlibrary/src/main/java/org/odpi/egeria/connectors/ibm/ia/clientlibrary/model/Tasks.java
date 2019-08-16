/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tasks {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunColumnAnalysis")
    private List<RunColumnAnalysis> runColumnAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunRules")
    private List<RunRules> runRulesList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunMetrics")
    private List<RunMetrics> runMetricsList;
    @JacksonXmlElementWrapper(localName = "PublishResults")
    private PublishResults publishResults;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunMultiColumnKeyAnalysis")
    private List<RunMultiColumnKeyAnalysis> runMultiColumnKeyAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunPrimaryKeyAnalysis")
    private List<RunPrimaryKeyAnalysis> runPrimaryKeyAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunCrossDomainAnalysis")
    private List<RunCrossDomainAnalysis> runCrossDomainAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunReferentialIntegrityAnalysis")
    private List<RunReferentialIntegrityAnalysis> runReferentialIntegrityAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunDomainValidation")
    private List<RunDomainValidation> runDomainValidationList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunTaskSequences")
    private List<RunTaskSequences> runTaskSequencesList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Sequence")
    private List<SequencedTasks> sequenceList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunPKFKAnalysis")
    private List<RunPKFKAnalysis> runPKFKAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunDataQualityAnalysis")
    private List<RunDataQualityAnalysis> runDataQualityAnalysisList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RunAutoDiscovery")
    private List<RunAutoDiscovery> runAutoDiscoveryList;

    public List<RunColumnAnalysis> getRunColumnAnalysisList() { return runColumnAnalysisList; }
    public void setRunColumnAnalysisList(List<RunColumnAnalysis> runColumnAnalysisList) { this.runColumnAnalysisList = runColumnAnalysisList; }

    public List<RunRules> getRunRulesList() { return runRulesList; }
    public void setRunRulesList(List<RunRules> runRulesList) { this.runRulesList = runRulesList; }

    public List<RunMetrics> getRunMetricsList() { return runMetricsList; }
    public void setRunMetricsList(List<RunMetrics> runMetricsList) { this.runMetricsList = runMetricsList; }

    public PublishResults getPublishResultsList() { return publishResults; }
    public void setPublishResultsList(PublishResults publishResults) { this.publishResults = publishResults; }

    public List<RunMultiColumnKeyAnalysis> getRunMultiColumnKeyAnalysisList() { return runMultiColumnKeyAnalysisList; }
    public void setRunMultiColumnKeyAnalysisList(List<RunMultiColumnKeyAnalysis> runMultiColumnKeyAnalysisList) { this.runMultiColumnKeyAnalysisList = runMultiColumnKeyAnalysisList; }

    public List<RunPrimaryKeyAnalysis> getRunPrimaryKeyAnalysisList() { return runPrimaryKeyAnalysisList; }
    public void setRunPrimaryKeyAnalysisList(List<RunPrimaryKeyAnalysis> runPrimaryKeyAnalysisList) { this.runPrimaryKeyAnalysisList = runPrimaryKeyAnalysisList; }

    public List<RunCrossDomainAnalysis> getRunCrossDomainAnalysisList() { return runCrossDomainAnalysisList; }
    public void setRunCrossDomainAnalysisList(List<RunCrossDomainAnalysis> runCrossDomainAnalysisList) { this.runCrossDomainAnalysisList = runCrossDomainAnalysisList; }

    public List<RunReferentialIntegrityAnalysis> getRunReferentialIntegrityAnalysisList() { return runReferentialIntegrityAnalysisList; }
    public void setRunReferentialIntegrityAnalysisList(List<RunReferentialIntegrityAnalysis> runReferentialIntegrityAnalysisList) { this.runReferentialIntegrityAnalysisList = runReferentialIntegrityAnalysisList; }

    public List<RunDomainValidation> getRunDomainValidationList() { return runDomainValidationList; }
    public void setRunDomainValidationList(List<RunDomainValidation> runDomainValidationList) { this.runDomainValidationList = runDomainValidationList; }

    public List<RunTaskSequences> getRunTaskSequencesList() { return runTaskSequencesList; }
    public void setRunTaskSequencesList(List<RunTaskSequences> runTaskSequencesList) { this.runTaskSequencesList = runTaskSequencesList; }

    public List<SequencedTasks> getSequenceList() { return sequenceList; }
    public void setSequenceList(List<SequencedTasks> sequenceList) { this.sequenceList = sequenceList; }

    public List<RunPKFKAnalysis> getRunPKFKAnalysisList() { return runPKFKAnalysisList; }
    public void setRunPKFKAnalysisList(List<RunPKFKAnalysis> runPKFKAnalysisList) { this.runPKFKAnalysisList = runPKFKAnalysisList; }

    public List<RunDataQualityAnalysis> getRunDataQualityAnalysisList() { return runDataQualityAnalysisList; }
    public void setRunDataQualityAnalysisList(List<RunDataQualityAnalysis> runDataQualityAnalysisList) { this.runDataQualityAnalysisList = runDataQualityAnalysisList; }

    public List<RunAutoDiscovery> getRunAutoDiscoveryList() { return runAutoDiscoveryList; }
    public void setRunAutoDiscoveryList(List<RunAutoDiscovery> runAutoDiscoveryList) { this.runAutoDiscoveryList = runAutoDiscoveryList; }

    @Override
    public String toString() {;
        return "{ \"RunColumnAnalysis\": " + (runColumnAnalysisList == null ? "[]" : runColumnAnalysisList.toString())
                + ", \"RunRules\": " + (runRulesList == null ? "[]" : runRulesList.toString())
                + ", \"RunMetrics\": " + (runMetricsList == null ? "[]" : runMetricsList.toString())
                + ", \"PublishResults\": " + (publishResults == null ? "{}" : publishResults.toString())
                + ", \"RunMultiColumnKeyAnalysis\": " + (runMultiColumnKeyAnalysisList == null ? "[]" : runMultiColumnKeyAnalysisList.toString())
                + ", \"RunPrimaryKeyAnalysis\": " + (runPrimaryKeyAnalysisList == null ? "[]" : runPrimaryKeyAnalysisList.toString())
                + ", \"RunCrossDomainAnalysis\": " + (runCrossDomainAnalysisList == null ? "[]" : runCrossDomainAnalysisList.toString())
                + ", \"RunReferentialIntegrityAnalysis\": " + (runReferentialIntegrityAnalysisList == null ? "[]" : runReferentialIntegrityAnalysisList.toString())
                + ", \"RunDomainValidation\": " + (runDomainValidationList == null ? "[]" : runDomainValidationList.toString())
                + ", \"RunTaskSequences\": " + (runTaskSequencesList == null ? "[]" : runTaskSequencesList.toString())
                + ", \"Sequences\": " + (sequenceList == null ? "[]" : sequenceList.toString())
                + ", \"RunPKFKAnalysis\": " + (runPKFKAnalysisList == null ? "[]" : runPKFKAnalysisList.toString())
                + ", \"RunDataQualityAnalysis\": " + (runDataQualityAnalysisList == null ? "[]" : runDataQualityAnalysisList.toString())
                + ", \"RunAutoDiscovery\": " + (runAutoDiscoveryList == null ? "[]" : runAutoDiscoveryList.toString())
                + " }";
    }

}
