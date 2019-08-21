/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private Long executionTime;
    @JacksonXmlProperty(isAttribute = true) private ExecutionStatus status;
    @JacksonXmlProperty(localName = "Message")
    private String message;
    @JacksonXmlElementWrapper(localName = "JobLog")
    @JacksonXmlProperty(localName = "JobLogEntry")
    private List<JobLogEntry> jobLogEntryList;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getExecutionTime() { return executionTime; }
    public void setExecutionTime(Long executionTime) { this.executionTime = executionTime; }

    public ExecutionStatus getStatus() { return status; }
    public void setStatus(ExecutionStatus status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<JobLogEntry> getJobLogEntryList() { return jobLogEntryList; }
    public void setJobLogEntryList(List<JobLogEntry> jobLogEntryList) { this.jobLogEntryList = jobLogEntryList; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"executionTime\": " + executionTime
                + ", \"status\": \"" + status
                + "\", \"Message\": \"" + message
                + "\", \"JobLog\": " + (jobLogEntryList == null ? "[]" : jobLogEntryList.toString())
                + " }";
    }

}
