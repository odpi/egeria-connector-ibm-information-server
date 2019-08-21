/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledTask {

    @JacksonXmlProperty(isAttribute = true) private String scheduleId;
    @JacksonXmlProperty(isAttribute = true) private TaskType taskType;

    public String getScheduleId() { return scheduleId; }
    public void setScheduleId(String scheduleId) { this.scheduleId = scheduleId; }

    public TaskType getTaskType() { return taskType; }
    public void setTaskType(TaskType taskType) { this.taskType = taskType; }

    @Override
    public String toString() {
        return "{ \"scheduleId\": \"" + scheduleId
                + "\", \"taskType\": \"" + taskType
                + "\" }";
    }

}
