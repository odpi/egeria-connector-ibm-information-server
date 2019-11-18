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
 * POJO for the {@code dsexternaldependency} asset type in IGC, displayed as '{@literal DSExternalDependency}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Dsexternaldependency.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsexternaldependency")
public class Dsexternaldependency extends Reference {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("calls_ds_routine")
    protected Routine callsDsRoutine;

    @JsonProperty("location")
    protected String location;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("of_ds_job_def")
    protected Dsjob ofDsJobDef;

    @JsonProperty("of_ds_routine")
    protected Routine ofDsRoutine;

    @JsonProperty("runs_ds_job")
    protected Dsjob runsDsJob;

    /**
     * Valid values are:
     * <ul>
     *   <li>JOB (displayed in the UI as 'JOB')</li>
     *   <li>ROUTINE (displayed in the UI as 'ROUTINE')</li>
     *   <li>UVROUTINE (displayed in the UI as 'UVROUTINE')</li>
     *   <li>FILE (displayed in the UI as 'FILE')</li>
     *   <li>ACTIVEX (displayed in the UI as 'ACTIVEX')</li>
     *   <li>WEBSERVICE (displayed in the UI as 'WEBSERVICE')</li>
     *   <li>ROOTJOB (displayed in the UI as 'ROOTJOB')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code a_xmeta_locking_root} property (displayed as '{@literal A XMeta Locking Root}') of the object.
     * @return {@code String}
     */
    @JsonProperty("a_xmeta_locking_root")
    public String getAXmetaLockingRoot() { return this.aXmetaLockingRoot; }

    /**
     * Set the {@code a_xmeta_locking_root} property (displayed as {@code A XMeta Locking Root}) of the object.
     * @param aXmetaLockingRoot the value to set
     */
    @JsonProperty("a_xmeta_locking_root")
    public void setAXmetaLockingRoot(String aXmetaLockingRoot) { this.aXmetaLockingRoot = aXmetaLockingRoot; }

    /**
     * Retrieve the {@code calls_ds_routine} property (displayed as '{@literal Calls DS Routine}') of the object.
     * @return {@code Routine}
     */
    @JsonProperty("calls_ds_routine")
    public Routine getCallsDsRoutine() { return this.callsDsRoutine; }

    /**
     * Set the {@code calls_ds_routine} property (displayed as {@code Calls DS Routine}) of the object.
     * @param callsDsRoutine the value to set
     */
    @JsonProperty("calls_ds_routine")
    public void setCallsDsRoutine(Routine callsDsRoutine) { this.callsDsRoutine = callsDsRoutine; }

    /**
     * Retrieve the {@code location} property (displayed as '{@literal Location}') of the object.
     * @return {@code String}
     */
    @JsonProperty("location")
    public String getLocation() { return this.location; }

    /**
     * Set the {@code location} property (displayed as {@code Location}) of the object.
     * @param location the value to set
     */
    @JsonProperty("location")
    public void setLocation(String location) { this.location = location; }

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
     * Retrieve the {@code of_ds_job_def} property (displayed as '{@literal Of DS Job Def}') of the object.
     * @return {@code Dsjob}
     */
    @JsonProperty("of_ds_job_def")
    public Dsjob getOfDsJobDef() { return this.ofDsJobDef; }

    /**
     * Set the {@code of_ds_job_def} property (displayed as {@code Of DS Job Def}) of the object.
     * @param ofDsJobDef the value to set
     */
    @JsonProperty("of_ds_job_def")
    public void setOfDsJobDef(Dsjob ofDsJobDef) { this.ofDsJobDef = ofDsJobDef; }

    /**
     * Retrieve the {@code of_ds_routine} property (displayed as '{@literal Of DS Routine}') of the object.
     * @return {@code Routine}
     */
    @JsonProperty("of_ds_routine")
    public Routine getOfDsRoutine() { return this.ofDsRoutine; }

    /**
     * Set the {@code of_ds_routine} property (displayed as {@code Of DS Routine}) of the object.
     * @param ofDsRoutine the value to set
     */
    @JsonProperty("of_ds_routine")
    public void setOfDsRoutine(Routine ofDsRoutine) { this.ofDsRoutine = ofDsRoutine; }

    /**
     * Retrieve the {@code runs_ds_job} property (displayed as '{@literal Runs DS Job}') of the object.
     * @return {@code Dsjob}
     */
    @JsonProperty("runs_ds_job")
    public Dsjob getRunsDsJob() { return this.runsDsJob; }

    /**
     * Set the {@code runs_ds_job} property (displayed as {@code Runs DS Job}) of the object.
     * @param runsDsJob the value to set
     */
    @JsonProperty("runs_ds_job")
    public void setRunsDsJob(Dsjob runsDsJob) { this.runsDsJob = runsDsJob; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

}
