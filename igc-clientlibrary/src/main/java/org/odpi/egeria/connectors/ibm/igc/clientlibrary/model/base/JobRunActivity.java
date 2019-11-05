/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

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
 * POJO for the {@code job_run_activity} asset type in IGC, displayed as '{@literal Job Run Activity}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_run_activity")
public class JobRunActivity extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("job_run")
    protected JobRun jobRun;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("x_emits_fail_events_display_name")
    protected List<String> xEmitsFailEventsDisplayName;

    @JsonProperty("x_emits_read_event_display_name")
    protected List<String> xEmitsReadEventDisplayName;

    @JsonProperty("x_emits_write_event_display_name")
    protected List<String> xEmitsWriteEventDisplayName;

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code job_run} property (displayed as '{@literal Job Run}') of the object.
     * @return {@code JobRun}
     */
    @JsonProperty("job_run")
    public JobRun getJobRun() { return this.jobRun; }

    /**
     * Set the {@code job_run} property (displayed as {@code Job Run}) of the object.
     * @param jobRun the value to set
     */
    @JsonProperty("job_run")
    public void setJobRun(JobRun jobRun) { this.jobRun = jobRun; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code x_emits_fail_events_display_name} property (displayed as '{@literal X Emits Fail Events Display Name}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("x_emits_fail_events_display_name")
    public List<String> getXEmitsFailEventsDisplayName() { return this.xEmitsFailEventsDisplayName; }

    /**
     * Set the {@code x_emits_fail_events_display_name} property (displayed as {@code X Emits Fail Events Display Name}) of the object.
     * @param xEmitsFailEventsDisplayName the value to set
     */
    @JsonProperty("x_emits_fail_events_display_name")
    public void setXEmitsFailEventsDisplayName(List<String> xEmitsFailEventsDisplayName) { this.xEmitsFailEventsDisplayName = xEmitsFailEventsDisplayName; }

    /**
     * Retrieve the {@code x_emits_read_event_display_name} property (displayed as '{@literal X Emits Read Event Display Name}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("x_emits_read_event_display_name")
    public List<String> getXEmitsReadEventDisplayName() { return this.xEmitsReadEventDisplayName; }

    /**
     * Set the {@code x_emits_read_event_display_name} property (displayed as {@code X Emits Read Event Display Name}) of the object.
     * @param xEmitsReadEventDisplayName the value to set
     */
    @JsonProperty("x_emits_read_event_display_name")
    public void setXEmitsReadEventDisplayName(List<String> xEmitsReadEventDisplayName) { this.xEmitsReadEventDisplayName = xEmitsReadEventDisplayName; }

    /**
     * Retrieve the {@code x_emits_write_event_display_name} property (displayed as '{@literal X Emits Write Event Display Name}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("x_emits_write_event_display_name")
    public List<String> getXEmitsWriteEventDisplayName() { return this.xEmitsWriteEventDisplayName; }

    /**
     * Set the {@code x_emits_write_event_display_name} property (displayed as {@code X Emits Write Event Display Name}) of the object.
     * @param xEmitsWriteEventDisplayName the value to set
     */
    @JsonProperty("x_emits_write_event_display_name")
    public void setXEmitsWriteEventDisplayName(List<String> xEmitsWriteEventDisplayName) { this.xEmitsWriteEventDisplayName = xEmitsWriteEventDisplayName; }

}
