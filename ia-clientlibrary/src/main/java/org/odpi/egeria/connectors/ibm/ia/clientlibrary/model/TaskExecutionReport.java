/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(namespace = "iaapi", localName = "TaskExecutionReport")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskExecutionReport {

    @JacksonXmlElementWrapper(localName = "ScheduledTasks")
    @JacksonXmlProperty(localName = "ScheduledTask")
    private List<ScheduledTask> scheduledTaskList;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Warnings")
    private ErrorList errorList;

    public List<ScheduledTask> getScheduledTaskList() { return scheduledTaskList; }
    public void setScheduledTaskList(List<ScheduledTask> scheduledTaskList) { this.scheduledTaskList = scheduledTaskList; }

    public ErrorList getErrorList() { return errorList; }
    public void setErrorList(ErrorList errorList) { this.errorList = errorList; }

    @Override
    public String toString() {
        return "{ \"ScheduledTasks\": " + (scheduledTaskList == null ? "[]" : scheduledTaskList.toString())
                + ", \"Warnings\": " + (errorList == null ? "{}" : errorList.toString())
                + " }";
    }

}
