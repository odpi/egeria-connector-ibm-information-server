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

    @JsonProperty("max_sessions")
    protected Number maxSessions;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("time_to_live")
    protected Number timeToLive;

    @JsonProperty("wake_up_interval")
    protected Number wakeUpInterval;

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
