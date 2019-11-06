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
 * POJO for the {@code session_configuration} asset type in IGC, displayed as '{@literal Session Configuration}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=SessionConfiguration.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("session_configuration")
public class SessionConfiguration extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("max_sessions")
    protected Number maxSessions;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("time_to_live")
    protected Number timeToLive;

    @JsonProperty("wake_up_interval")
    protected Number wakeUpInterval;

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
     * Retrieve the {@code max_sessions} property (displayed as '{@literal Max Sessions}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("max_sessions")
    public Number getMaxSessions() { return this.maxSessions; }

    /**
     * Set the {@code max_sessions} property (displayed as {@code Max Sessions}) of the object.
     * @param maxSessions the value to set
     */
    @JsonProperty("max_sessions")
    public void setMaxSessions(Number maxSessions) { this.maxSessions = maxSessions; }

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
     * Retrieve the {@code time_to_live} property (displayed as '{@literal Time To Live}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("time_to_live")
    public Number getTimeToLive() { return this.timeToLive; }

    /**
     * Set the {@code time_to_live} property (displayed as {@code Time To Live}) of the object.
     * @param timeToLive the value to set
     */
    @JsonProperty("time_to_live")
    public void setTimeToLive(Number timeToLive) { this.timeToLive = timeToLive; }

    /**
     * Retrieve the {@code wake_up_interval} property (displayed as '{@literal Wake Up Interval}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("wake_up_interval")
    public Number getWakeUpInterval() { return this.wakeUpInterval; }

    /**
     * Set the {@code wake_up_interval} property (displayed as {@code Wake Up Interval}) of the object.
     * @param wakeUpInterval the value to set
     */
    @JsonProperty("wake_up_interval")
    public void setWakeUpInterval(Number wakeUpInterval) { this.wakeUpInterval = wakeUpInterval; }

}
