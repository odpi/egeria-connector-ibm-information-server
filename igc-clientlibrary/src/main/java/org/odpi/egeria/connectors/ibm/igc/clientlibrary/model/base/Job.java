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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code job} asset type in IGC, displayed as '{@literal Job}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Job.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job")
public class Job extends MainObject {

    @JsonProperty("has_job_def")
    protected Jobdef hasJobDef;

    @JsonProperty("host_system")
    protected Host hostSystem;

    @JsonProperty("release_status")
    protected String releaseStatus;

    /**
     * Retrieve the {@code has_job_def} property (displayed as '{@literal Has Job Def}') of the object.
     * @return {@code Jobdef}
     */
    @JsonProperty("has_job_def")
    public Jobdef getHasJobDef() { return this.hasJobDef; }

    /**
     * Set the {@code has_job_def} property (displayed as {@code Has Job Def}) of the object.
     * @param hasJobDef the value to set
     */
    @JsonProperty("has_job_def")
    public void setHasJobDef(Jobdef hasJobDef) { this.hasJobDef = hasJobDef; }

    /**
     * Retrieve the {@code host_system} property (displayed as '{@literal Host System}') of the object.
     * @return {@code Host}
     */
    @JsonProperty("host_system")
    public Host getHostSystem() { return this.hostSystem; }

    /**
     * Set the {@code host_system} property (displayed as {@code Host System}) of the object.
     * @param hostSystem the value to set
     */
    @JsonProperty("host_system")
    public void setHostSystem(Host hostSystem) { this.hostSystem = hostSystem; }

    /**
     * Retrieve the {@code release_status} property (displayed as '{@literal Release Status}') of the object.
     * @return {@code String}
     */
    @JsonProperty("release_status")
    public String getReleaseStatus() { return this.releaseStatus; }

    /**
     * Set the {@code release_status} property (displayed as {@code Release Status}) of the object.
     * @param releaseStatus the value to set
     */
    @JsonProperty("release_status")
    public void setReleaseStatus(String releaseStatus) { this.releaseStatus = releaseStatus; }

}
