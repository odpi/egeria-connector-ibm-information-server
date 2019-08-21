/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(namespace = "iaapi", localName = "TaskExecutionSchedule")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskExecutionSchedule {

    @JacksonXmlProperty(isAttribute = true) private String scheduleId;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TaskExecution")
    private List<TaskExecution> taskExecutionList;

    public String getScheduleId() { return scheduleId; }
    public void setScheduleId(String scheduleId) { this.scheduleId = scheduleId; }

    public List<TaskExecution> getTaskExecutionList() { return taskExecutionList; }
    public void setTaskExecutionList(List<TaskExecution> taskExecutionList) { this.taskExecutionList = taskExecutionList; }

    @Override
    public String toString() {
        return "{ \"scheduleId\": \"" + scheduleId
                + "\", \"TaskExecutions\": " + (taskExecutionList == null ? "[]" : taskExecutionList.toString())
                + " }";
    }

}
