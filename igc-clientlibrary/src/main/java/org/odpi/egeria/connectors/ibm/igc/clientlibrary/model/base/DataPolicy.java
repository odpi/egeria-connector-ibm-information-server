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
 * POJO for the {@code data_policy} asset type in IGC, displayed as '{@literal Data Policy}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataPolicy.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_policy")
public class DataPolicy extends MainObject {

    @JsonProperty("applied_to_assets")
    protected ItemList<MainObject> appliedToAssets;

    @JsonProperty("contacts")
    protected ItemList<Steward> contacts;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("effective_date")
    protected Date effectiveDate;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("policy_number")
    protected String policyNumber;

    @JsonProperty("termination_date")
    protected Date terminationDate;

    /**
     * Retrieve the {@code applied_to_assets} property (displayed as '{@literal Applied to Assets}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("applied_to_assets")
    public ItemList<MainObject> getAppliedToAssets() { return this.appliedToAssets; }

    /**
     * Set the {@code applied_to_assets} property (displayed as {@code Applied to Assets}) of the object.
     * @param appliedToAssets the value to set
     */
    @JsonProperty("applied_to_assets")
    public void setAppliedToAssets(ItemList<MainObject> appliedToAssets) { this.appliedToAssets = appliedToAssets; }

    /**
     * Retrieve the {@code contacts} property (displayed as '{@literal Contacts}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("contacts")
    public ItemList<Steward> getContacts() { return this.contacts; }

    /**
     * Set the {@code contacts} property (displayed as {@code Contacts}) of the object.
     * @param contacts the value to set
     */
    @JsonProperty("contacts")
    public void setContacts(ItemList<Steward> contacts) { this.contacts = contacts; }

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
     * Retrieve the {@code effective_date} property (displayed as '{@literal Effective Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("effective_date")
    public Date getEffectiveDate() { return this.effectiveDate; }

    /**
     * Set the {@code effective_date} property (displayed as {@code Effective Date}) of the object.
     * @param effectiveDate the value to set
     */
    @JsonProperty("effective_date")
    public void setEffectiveDate(Date effectiveDate) { this.effectiveDate = effectiveDate; }

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
     * Retrieve the {@code policy_number} property (displayed as '{@literal Policy Number}') of the object.
     * @return {@code String}
     */
    @JsonProperty("policy_number")
    public String getPolicyNumber() { return this.policyNumber; }

    /**
     * Set the {@code policy_number} property (displayed as {@code Policy Number}) of the object.
     * @param policyNumber the value to set
     */
    @JsonProperty("policy_number")
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    /**
     * Retrieve the {@code termination_date} property (displayed as '{@literal Termination Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("termination_date")
    public Date getTerminationDate() { return this.terminationDate; }

    /**
     * Set the {@code termination_date} property (displayed as {@code Termination Date}) of the object.
     * @param terminationDate the value to set
     */
    @JsonProperty("termination_date")
    public void setTerminationDate(Date terminationDate) { this.terminationDate = terminationDate; }

}
