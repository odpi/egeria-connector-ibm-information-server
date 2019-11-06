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
 * POJO for the {@code data_rule_results} asset type in IGC, displayed as '{@literal Data Rule Results}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataRuleResults.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_rule_results")
public class DataRuleResults extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("end_time")
    protected Date endTime;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("number_of_records_met")
    protected Number numberOfRecordsMet;

    @JsonProperty("number_of_records_not_met")
    protected Number numberOfRecordsNotMet;

    @JsonProperty("number_of_records_tested")
    protected Number numberOfRecordsTested;

    @JsonProperty("rule_results")
    protected ItemList<InformationAsset> ruleResults;

    @JsonProperty("start_time")
    protected Date startTime;

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
     * Retrieve the {@code end_time} property (displayed as '{@literal End Time}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("end_time")
    public Date getEndTime() { return this.endTime; }

    /**
     * Set the {@code end_time} property (displayed as {@code End Time}) of the object.
     * @param endTime the value to set
     */
    @JsonProperty("end_time")
    public void setEndTime(Date endTime) { this.endTime = endTime; }

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
     * Retrieve the {@code number_of_records_met} property (displayed as '{@literal Number of Records Met}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_records_met")
    public Number getNumberOfRecordsMet() { return this.numberOfRecordsMet; }

    /**
     * Set the {@code number_of_records_met} property (displayed as {@code Number of Records Met}) of the object.
     * @param numberOfRecordsMet the value to set
     */
    @JsonProperty("number_of_records_met")
    public void setNumberOfRecordsMet(Number numberOfRecordsMet) { this.numberOfRecordsMet = numberOfRecordsMet; }

    /**
     * Retrieve the {@code number_of_records_not_met} property (displayed as '{@literal Number of Records Not Met}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_records_not_met")
    public Number getNumberOfRecordsNotMet() { return this.numberOfRecordsNotMet; }

    /**
     * Set the {@code number_of_records_not_met} property (displayed as {@code Number of Records Not Met}) of the object.
     * @param numberOfRecordsNotMet the value to set
     */
    @JsonProperty("number_of_records_not_met")
    public void setNumberOfRecordsNotMet(Number numberOfRecordsNotMet) { this.numberOfRecordsNotMet = numberOfRecordsNotMet; }

    /**
     * Retrieve the {@code number_of_records_tested} property (displayed as '{@literal Number of Records Tested}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_records_tested")
    public Number getNumberOfRecordsTested() { return this.numberOfRecordsTested; }

    /**
     * Set the {@code number_of_records_tested} property (displayed as {@code Number of Records Tested}) of the object.
     * @param numberOfRecordsTested the value to set
     */
    @JsonProperty("number_of_records_tested")
    public void setNumberOfRecordsTested(Number numberOfRecordsTested) { this.numberOfRecordsTested = numberOfRecordsTested; }

    /**
     * Retrieve the {@code rule_results} property (displayed as '{@literal Rule Results}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("rule_results")
    public ItemList<InformationAsset> getRuleResults() { return this.ruleResults; }

    /**
     * Set the {@code rule_results} property (displayed as {@code Rule Results}) of the object.
     * @param ruleResults the value to set
     */
    @JsonProperty("rule_results")
    public void setRuleResults(ItemList<InformationAsset> ruleResults) { this.ruleResults = ruleResults; }

    /**
     * Retrieve the {@code start_time} property (displayed as '{@literal Start Time}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("start_time")
    public Date getStartTime() { return this.startTime; }

    /**
     * Set the {@code start_time} property (displayed as {@code Start Time}) of the object.
     * @param startTime the value to set
     */
    @JsonProperty("start_time")
    public void setStartTime(Date startTime) { this.startTime = startTime; }

}
