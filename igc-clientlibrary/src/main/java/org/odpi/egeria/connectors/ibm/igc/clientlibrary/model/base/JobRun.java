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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code job_run} asset type in IGC, displayed as '{@literal Job Run}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=JobRun.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_run")
public class JobRun extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("ending_date")
    protected Date endingDate;

    @JsonProperty("invocation_id")
    protected String invocationId;

    @JsonProperty("job")
    protected Dsjob job;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("omd_file_name")
    protected String omdFileName;

    @JsonProperty("parameters")
    protected ItemList<JobParameter> parameters;

    @JsonProperty("starting_date")
    protected Date startingDate;

    @JsonProperty("translated_status_code")
    protected String translatedStatusCode;

    @JsonProperty("translated_summary")
    protected String translatedSummary;

    @JsonProperty("wave_number")
    protected String waveNumber;

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
     * Retrieve the {@code ending_date} property (displayed as '{@literal Ending Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("ending_date")
    public Date getEndingDate() { return this.endingDate; }

    /**
     * Set the {@code ending_date} property (displayed as {@code Ending Date}) of the object.
     * @param endingDate the value to set
     */
    @JsonProperty("ending_date")
    public void setEndingDate(Date endingDate) { this.endingDate = endingDate; }

    /**
     * Retrieve the {@code invocation_id} property (displayed as '{@literal Invocation ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("invocation_id")
    public String getInvocationId() { return this.invocationId; }

    /**
     * Set the {@code invocation_id} property (displayed as {@code Invocation ID}) of the object.
     * @param invocationId the value to set
     */
    @JsonProperty("invocation_id")
    public void setInvocationId(String invocationId) { this.invocationId = invocationId; }

    /**
     * Retrieve the {@code job} property (displayed as '{@literal Job}') of the object.
     * @return {@code Dsjob}
     */
    @JsonProperty("job")
    public Dsjob getJob() { return this.job; }

    /**
     * Set the {@code job} property (displayed as {@code Job}) of the object.
     * @param job the value to set
     */
    @JsonProperty("job")
    public void setJob(Dsjob job) { this.job = job; }

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
     * Retrieve the {@code omd_file_name} property (displayed as '{@literal OMD File Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("omd_file_name")
    public String getOmdFileName() { return this.omdFileName; }

    /**
     * Set the {@code omd_file_name} property (displayed as {@code OMD File Name}) of the object.
     * @param omdFileName the value to set
     */
    @JsonProperty("omd_file_name")
    public void setOmdFileName(String omdFileName) { this.omdFileName = omdFileName; }

    /**
     * Retrieve the {@code parameters} property (displayed as '{@literal Parameters}') of the object.
     * @return {@code ItemList<JobParameter>}
     */
    @JsonProperty("parameters")
    public ItemList<JobParameter> getParameters() { return this.parameters; }

    /**
     * Set the {@code parameters} property (displayed as {@code Parameters}) of the object.
     * @param parameters the value to set
     */
    @JsonProperty("parameters")
    public void setParameters(ItemList<JobParameter> parameters) { this.parameters = parameters; }

    /**
     * Retrieve the {@code starting_date} property (displayed as '{@literal Starting Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("starting_date")
    public Date getStartingDate() { return this.startingDate; }

    /**
     * Set the {@code starting_date} property (displayed as {@code Starting Date}) of the object.
     * @param startingDate the value to set
     */
    @JsonProperty("starting_date")
    public void setStartingDate(Date startingDate) { this.startingDate = startingDate; }

    /**
     * Retrieve the {@code translated_status_code} property (displayed as '{@literal Status}') of the object.
     * @return {@code String}
     */
    @JsonProperty("translated_status_code")
    public String getTranslatedStatusCode() { return this.translatedStatusCode; }

    /**
     * Set the {@code translated_status_code} property (displayed as {@code Status}) of the object.
     * @param translatedStatusCode the value to set
     */
    @JsonProperty("translated_status_code")
    public void setTranslatedStatusCode(String translatedStatusCode) { this.translatedStatusCode = translatedStatusCode; }

    /**
     * Retrieve the {@code translated_summary} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("translated_summary")
    public String getTranslatedSummary() { return this.translatedSummary; }

    /**
     * Set the {@code translated_summary} property (displayed as {@code Name}) of the object.
     * @param translatedSummary the value to set
     */
    @JsonProperty("translated_summary")
    public void setTranslatedSummary(String translatedSummary) { this.translatedSummary = translatedSummary; }

    /**
     * Retrieve the {@code wave_number} property (displayed as '{@literal Wave Number}') of the object.
     * @return {@code String}
     */
    @JsonProperty("wave_number")
    public String getWaveNumber() { return this.waveNumber; }

    /**
     * Set the {@code wave_number} property (displayed as {@code Wave Number}) of the object.
     * @param waveNumber the value to set
     */
    @JsonProperty("wave_number")
    public void setWaveNumber(String waveNumber) { this.waveNumber = waveNumber; }

}
