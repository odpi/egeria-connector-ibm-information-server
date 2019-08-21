/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(namespace = "iaapi", localName = "Project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    @JacksonXmlProperty(isAttribute = true) private String rid;
    @JacksonXmlProperty(isAttribute = true) private String name;
    private String description;
    @JacksonXmlProperty(localName = "LongDescription")
    private String longDescription;

    @JacksonXmlProperty(localName = "Warnings")
    private ErrorList warnings;
    @JacksonXmlElementWrapper(localName = "GlobalVariables")
    @JacksonXmlProperty(localName = "Variable")
    private List<Variable> globalVariables;
    @JacksonXmlElementWrapper(localName = "DataSources")
    @JacksonXmlProperty(localName = "DataSource")
    private List<DataSource> dataSources;
    @JacksonXmlProperty(localName = "DataRuleDefinitions")
    private DataRuleDefinitions dataRuleDefinitions;
    @JacksonXmlElementWrapper(localName = "PublishedResults")
    @JacksonXmlProperty(localName = "Table")
    private List<Table> publishedResults;
    @JacksonXmlElementWrapper(localName = "Metrics")
    @JacksonXmlProperty(localName = "Metric")
    private List<Metric> metrics;
    @JacksonXmlElementWrapper(localName = "Folders")
    @JacksonXmlProperty(localName = "Folder")
    private List<Folder> folders;
    @JacksonXmlElementWrapper(localName = "TaskSequences")
    @JacksonXmlProperty(localName = "TaskSequence")
    private List<TaskSequence> taskSequences;
    @JacksonXmlProperty(localName = "Tasks")
    private Tasks tasks;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "OverlapCandidate")
    private List<OverlapCandidate> overlapCandidates;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ForeignKeyCandidate")
    private List<ForeignKeyCandidate> foreignKeyCandidates;

    public String getRid() { return rid; }
    public void setRid(String rid) { this.rid = rid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLongDescription() { return longDescription; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    public ErrorList getWarnings() { return warnings; }
    public void setWarnings(ErrorList warnings) { this.warnings = warnings; }

    public List<Variable> getGlobalVariables() { return globalVariables; }
    public void setGlobalVariables(List<Variable> globalVariables) { this.globalVariables = globalVariables; }

    public List<DataSource> getDataSources() { return dataSources; }
    public void setDataSources(List<DataSource> dataSources) { this.dataSources = dataSources; }

    public DataRuleDefinitions getDataRuleDefinitions() { return dataRuleDefinitions; }
    public void setDataRuleDefinitions(DataRuleDefinitions dataRuleDefinitions) { this.dataRuleDefinitions = dataRuleDefinitions; }

    public List<Table> getPublishedResults() { return publishedResults; }
    public void setPublishedResults(List<Table> publishedResults) { this.publishedResults = publishedResults; }

    public List<Metric> getMetrics() { return metrics; }
    public void setMetrics(List<Metric> metrics) { this.metrics = metrics; }

    public List<Folder> getFolders() { return folders; }
    public void setFolders(List<Folder> folders) { this.folders = folders; }

    public List<TaskSequence> getTaskSequences() { return taskSequences; }
    public void setTaskSequences(List<TaskSequence> taskSequences) { this.taskSequences = taskSequences; }

    public Tasks getTasks() { return tasks; }
    public void setTasks(Tasks tasks) { this.tasks = tasks; }

    public List<OverlapCandidate> getOverlapCandidates() { return overlapCandidates; }
    public void setOverlapCandidates(List<OverlapCandidate> overlapCandidates) { this.overlapCandidates = overlapCandidates; }

    public List<ForeignKeyCandidate> getForeignKeyCandidates() { return foreignKeyCandidates; }
    public void setForeignKeyCandidates(List<ForeignKeyCandidate> foreignKeyCandidates) { this.foreignKeyCandidates = foreignKeyCandidates; }

    @Override
    public String toString() {
        return "Project: { \"name\": \"" + name
                + "\", \"rid\": \"" + rid
                + "\", \"description\": \"" + description
                + "\", \"longDescription\": \"" + longDescription
                + "\", \"Warnings\": " + (warnings == null ? "[]" : warnings.toString())
                + ", \"GlobalVariables\": " + (globalVariables == null ? "[]" : globalVariables.toString())
                + ", \"DataSources\": " + (dataSources == null ? "[]" : dataSources.toString())
                + ", \"DataRuleDefinitions\": " + (dataRuleDefinitions == null ? "{}" : dataRuleDefinitions.toString())
                + ", \"PublishedResults\": " + (publishedResults == null ? "[]" : publishedResults.toString())
                + ", \"Metrics\": " + (metrics == null ? "[]" : metrics.toString())
                + ", \"Folders\": " + (folders == null ? "[]" : folders.toString())
                + ", \"TaskSequences\": " + (taskSequences == null ? "[]" : taskSequences.toString())
                + ", \"Tasks\": " + (tasks == null ? "{}" : tasks.toString())
                + ", \"OverlapCandidates\": " + (overlapCandidates == null ? "[]" : overlapCandidates.toString())
                + ", \"ForeignKeyCandidates\": " + (foreignKeyCandidates == null ? "[]" : foreignKeyCandidates.toString())
                + " }";
    }

}
