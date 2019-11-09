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
 * POJO for the {@code job_write_event} asset type in IGC, displayed as '{@literal Job Write Event}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=JobWriteEvent.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_write_event")
public class JobWriteEvent extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("job_run_activity")
    protected InformationAsset jobRunActivity;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("row_count")
    protected Number rowCount;

    @JsonProperty("time")
    protected Date time;

    @JsonProperty("writes_to")
    protected String writesTo;

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
     * Retrieve the {@code job_run_activity} property (displayed as '{@literal Emitted By Activity}') of the object.
     * @return {@code InformationAsset}
     */
    @JsonProperty("job_run_activity")
    public InformationAsset getJobRunActivity() { return this.jobRunActivity; }

    /**
     * Set the {@code job_run_activity} property (displayed as {@code Emitted By Activity}) of the object.
     * @param jobRunActivity the value to set
     */
    @JsonProperty("job_run_activity")
    public void setJobRunActivity(InformationAsset jobRunActivity) { this.jobRunActivity = jobRunActivity; }

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
     * Retrieve the {@code row_count} property (displayed as '{@literal Rows Processed}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("row_count")
    public Number getRowCount() { return this.rowCount; }

    /**
     * Set the {@code row_count} property (displayed as {@code Rows Processed}) of the object.
     * @param rowCount the value to set
     */
    @JsonProperty("row_count")
    public void setRowCount(Number rowCount) { this.rowCount = rowCount; }

    /**
     * Retrieve the {@code time} property (displayed as '{@literal Time}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("time")
    public Date getTime() { return this.time; }

    /**
     * Set the {@code time} property (displayed as {@code Time}) of the object.
     * @param time the value to set
     */
    @JsonProperty("time")
    public void setTime(Date time) { this.time = time; }

    /**
     * Retrieve the {@code writes_to} property (displayed as '{@literal Writes To}') of the object.
     * @return {@code String}
     */
    @JsonProperty("writes_to")
    public String getWritesTo() { return this.writesTo; }

    /**
     * Set the {@code writes_to} property (displayed as {@code Writes To}) of the object.
     * @param writesTo the value to set
     */
    @JsonProperty("writes_to")
    public void setWritesTo(String writesTo) { this.writesTo = writesTo; }

}
