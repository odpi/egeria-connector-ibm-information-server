/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskExecution {

    @JacksonXmlProperty(isAttribute = true) private String executionId;
    @JacksonXmlProperty(isAttribute = true) private Date executionDate;
    @JacksonXmlProperty(isAttribute = true) private Long executionTime;
    @JacksonXmlProperty(isAttribute = true) private ExecutionStatus status;
    @JacksonXmlProperty(isAttribute = true) private Integer progress;
    @JacksonXmlProperty(localName = "Message")
    private String message;
    @JacksonXmlProperty(localName = "StackTrace")
    private String stackTrace;
    @JacksonXmlElementWrapper(localName = "Jobs")
    @JacksonXmlProperty(localName = "Job")
    private List<Job> jobList;

    public String getExecutionId() { return executionId; }
    public void setExecutionId(String executionId) { this.executionId = executionId; }

    public Date getExecutionDate() { return executionDate; }
    public void setExecutionDate(Date executionDate) { this.executionDate = executionDate; }

    public Long getExecutionTime() { return executionTime; }
    public void setExecutionTime(Long executionTime) { this.executionTime = executionTime; }

    public ExecutionStatus getStatus() { return status; }
    public void setStatus(ExecutionStatus status) { this.status = status; }

    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }

    public List<Job> getJobList() { return jobList; }
    public void setJobList(List<Job> jobList) { this.jobList = jobList; }

    @Override
    public String toString() {
        return "{ \"executionId\": \"" + executionId
                + "\", \"executionDate\": \"" + executionDate
                + "\", \"executionTime\": " + executionTime
                + ", \"status\": \"" + status
                + "\", \"progress\": " + progress
                + ", \"Message\": \"" + message
                + "\", \"StackTrace\": \"" + stackTrace
                + "\", \"Jobs\": " + (jobList == null ? "[]" : jobList.toString())
                + " }";
    }

}
