/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code development_log} asset type in IGC, displayed as '{@literal DevelopmentLog}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DevelopmentLog.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("development_log")
public class DevelopmentLog extends Reference {

    @JsonProperty("activity")
    protected String activity;

    @JsonProperty("comment")
    protected String comment;

    @JsonProperty("date")
    protected Date date;

    @JsonProperty("development_log")
    protected List<String> developmentLog;

    @JsonProperty("new_state")
    protected String newState;

    @JsonProperty("person")
    protected String person;

    @JsonProperty("user_task_key")
    protected String userTaskKey;

    @JsonProperty("user_task_name")
    protected String userTaskName;

    @JsonProperty("workflow_event")
    protected String workflowEvent;

    @JsonProperty("workflow_instance_id")
    protected String workflowInstanceId;

    @JsonProperty("workflow_new_state")
    protected String workflowNewState;

    @JsonProperty("workflow_task")
    protected String workflowTask;

    /**
     * Retrieve the {@code activity} property (displayed as '{@literal Activity}') of the object.
     * @return {@code String}
     */
    @JsonProperty("activity")
    public String getActivity() { return this.activity; }

    /**
     * Set the {@code activity} property (displayed as {@code Activity}) of the object.
     * @param activity the value to set
     */
    @JsonProperty("activity")
    public void setActivity(String activity) { this.activity = activity; }

    /**
     * Retrieve the {@code comment} property (displayed as '{@literal Comment}') of the object.
     * @return {@code String}
     */
    @JsonProperty("comment")
    public String getComment() { return this.comment; }

    /**
     * Set the {@code comment} property (displayed as {@code Comment}) of the object.
     * @param comment the value to set
     */
    @JsonProperty("comment")
    public void setComment(String comment) { this.comment = comment; }

    /**
     * Retrieve the {@code date} property (displayed as '{@literal Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("date")
    public Date getDate() { return this.date; }

    /**
     * Set the {@code date} property (displayed as {@code Date}) of the object.
     * @param date the value to set
     */
    @JsonProperty("date")
    public void setDate(Date date) { this.date = date; }

    /**
     * Retrieve the {@code development_log} property (displayed as '{@literal Header}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("development_log")
    public List<String> getDevelopmentLog() { return this.developmentLog; }

    /**
     * Set the {@code development_log} property (displayed as {@code Header}) of the object.
     * @param developmentLog the value to set
     */
    @JsonProperty("development_log")
    public void setDevelopmentLog(List<String> developmentLog) { this.developmentLog = developmentLog; }

    /**
     * Retrieve the {@code new_state} property (displayed as '{@literal New State}') of the object.
     * @return {@code String}
     */
    @JsonProperty("new_state")
    public String getNewState() { return this.newState; }

    /**
     * Set the {@code new_state} property (displayed as {@code New State}) of the object.
     * @param newState the value to set
     */
    @JsonProperty("new_state")
    public void setNewState(String newState) { this.newState = newState; }

    /**
     * Retrieve the {@code person} property (displayed as '{@literal Person}') of the object.
     * @return {@code String}
     */
    @JsonProperty("person")
    public String getPerson() { return this.person; }

    /**
     * Set the {@code person} property (displayed as {@code Person}) of the object.
     * @param person the value to set
     */
    @JsonProperty("person")
    public void setPerson(String person) { this.person = person; }

    /**
     * Retrieve the {@code user_task_key} property (displayed as '{@literal User Task Key}') of the object.
     * @return {@code String}
     */
    @JsonProperty("user_task_key")
    public String getUserTaskKey() { return this.userTaskKey; }

    /**
     * Set the {@code user_task_key} property (displayed as {@code User Task Key}) of the object.
     * @param userTaskKey the value to set
     */
    @JsonProperty("user_task_key")
    public void setUserTaskKey(String userTaskKey) { this.userTaskKey = userTaskKey; }

    /**
     * Retrieve the {@code user_task_name} property (displayed as '{@literal User Task Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("user_task_name")
    public String getUserTaskName() { return this.userTaskName; }

    /**
     * Set the {@code user_task_name} property (displayed as {@code User Task Name}) of the object.
     * @param userTaskName the value to set
     */
    @JsonProperty("user_task_name")
    public void setUserTaskName(String userTaskName) { this.userTaskName = userTaskName; }

    /**
     * Retrieve the {@code workflow_event} property (displayed as '{@literal Workflow Event}') of the object.
     * @return {@code String}
     */
    @JsonProperty("workflow_event")
    public String getWorkflowEvent() { return this.workflowEvent; }

    /**
     * Set the {@code workflow_event} property (displayed as {@code Workflow Event}) of the object.
     * @param workflowEvent the value to set
     */
    @JsonProperty("workflow_event")
    public void setWorkflowEvent(String workflowEvent) { this.workflowEvent = workflowEvent; }

    /**
     * Retrieve the {@code workflow_instance_id} property (displayed as '{@literal Workflow Instance Id}') of the object.
     * @return {@code String}
     */
    @JsonProperty("workflow_instance_id")
    public String getWorkflowInstanceId() { return this.workflowInstanceId; }

    /**
     * Set the {@code workflow_instance_id} property (displayed as {@code Workflow Instance Id}) of the object.
     * @param workflowInstanceId the value to set
     */
    @JsonProperty("workflow_instance_id")
    public void setWorkflowInstanceId(String workflowInstanceId) { this.workflowInstanceId = workflowInstanceId; }

    /**
     * Retrieve the {@code workflow_new_state} property (displayed as '{@literal Workflow New State}') of the object.
     * @return {@code String}
     */
    @JsonProperty("workflow_new_state")
    public String getWorkflowNewState() { return this.workflowNewState; }

    /**
     * Set the {@code workflow_new_state} property (displayed as {@code Workflow New State}) of the object.
     * @param workflowNewState the value to set
     */
    @JsonProperty("workflow_new_state")
    public void setWorkflowNewState(String workflowNewState) { this.workflowNewState = workflowNewState; }

    /**
     * Retrieve the {@code workflow_task} property (displayed as '{@literal Workflow Task}') of the object.
     * @return {@code String}
     */
    @JsonProperty("workflow_task")
    public String getWorkflowTask() { return this.workflowTask; }

    /**
     * Set the {@code workflow_task} property (displayed as {@code Workflow Task}) of the object.
     * @param workflowTask the value to set
     */
    @JsonProperty("workflow_task")
    public void setWorkflowTask(String workflowTask) { this.workflowTask = workflowTask; }

}
