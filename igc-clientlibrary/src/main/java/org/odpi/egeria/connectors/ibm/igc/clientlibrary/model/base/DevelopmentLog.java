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

    @JsonProperty("new_state")
    protected String newState;

    @JsonProperty("person")
    protected String person;

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
