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
 * POJO for the {@code inv_data_rule_set_definition} asset type in IGC, displayed as '{@literal Data Rule Set Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=InvDataRuleSetDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("inv_data_rule_set_definition")
public class InvDataRuleSetDefinition extends Reference {

    @JsonProperty("assigned_to_terms")
    protected ItemList<Term> assignedToTerms;

    @JsonProperty("contact")
    protected ItemList<Steward> contact;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("data_rule_definitions")
    protected ItemList<MainObject> dataRuleDefinitions;

    @JsonProperty("data_rule_sets")
    protected ItemList<MainObject> dataRuleSets;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("project")
    protected String project;

    @JsonProperty("published")
    protected Boolean published;

    @JsonProperty("short_&_long_description")
    protected String shortLongDescription;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Valid values are:
     * <ul>
     *   <li>CANDIDATE (displayed in the UI as 'CANDIDATE')</li>
     *   <li>ACCEPTED (displayed in the UI as 'ACCEPTED')</li>
     *   <li>STANDARD (displayed in the UI as 'STANDARD')</li>
     *   <li>DEPRECATED (displayed in the UI as 'DEPRECATED')</li>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     *   <li>IN_PROCESS (displayed in the UI as 'IN_PROCESS')</li>
     *   <li>REJECTED (displayed in the UI as 'REJECTED')</li>
     *   <li>ERROR (displayed in the UI as 'ERROR')</li>
     * </ul>
     */
    @JsonProperty("status")
    protected String status;

    /**
     * Retrieve the {@code assigned_to_terms} property (displayed as '{@literal Assigned to Terms}') of the object.
     * @return {@code ItemList<Term>}
     */
    @JsonProperty("assigned_to_terms")
    public ItemList<Term> getAssignedToTerms() { return this.assignedToTerms; }

    /**
     * Set the {@code assigned_to_terms} property (displayed as {@code Assigned to Terms}) of the object.
     * @param assignedToTerms the value to set
     */
    @JsonProperty("assigned_to_terms")
    public void setAssignedToTerms(ItemList<Term> assignedToTerms) { this.assignedToTerms = assignedToTerms; }

    /**
     * Retrieve the {@code contact} property (displayed as '{@literal Contact}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("contact")
    public ItemList<Steward> getContact() { return this.contact; }

    /**
     * Set the {@code contact} property (displayed as {@code Contact}) of the object.
     * @param contact the value to set
     */
    @JsonProperty("contact")
    public void setContact(ItemList<Steward> contact) { this.contact = contact; }

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
     * Retrieve the {@code data_rule_definitions} property (displayed as '{@literal Data Rule Definitions}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("data_rule_definitions")
    public ItemList<MainObject> getDataRuleDefinitions() { return this.dataRuleDefinitions; }

    /**
     * Set the {@code data_rule_definitions} property (displayed as {@code Data Rule Definitions}) of the object.
     * @param dataRuleDefinitions the value to set
     */
    @JsonProperty("data_rule_definitions")
    public void setDataRuleDefinitions(ItemList<MainObject> dataRuleDefinitions) { this.dataRuleDefinitions = dataRuleDefinitions; }

    /**
     * Retrieve the {@code data_rule_sets} property (displayed as '{@literal Data Rule Sets}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("data_rule_sets")
    public ItemList<MainObject> getDataRuleSets() { return this.dataRuleSets; }

    /**
     * Set the {@code data_rule_sets} property (displayed as {@code Data Rule Sets}) of the object.
     * @param dataRuleSets the value to set
     */
    @JsonProperty("data_rule_sets")
    public void setDataRuleSets(ItemList<MainObject> dataRuleSets) { this.dataRuleSets = dataRuleSets; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

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
     * Retrieve the {@code project} property (displayed as '{@literal Project}') of the object.
     * @return {@code String}
     */
    @JsonProperty("project")
    public String getProject() { return this.project; }

    /**
     * Set the {@code project} property (displayed as {@code Project}) of the object.
     * @param project the value to set
     */
    @JsonProperty("project")
    public void setProject(String project) { this.project = project; }

    /**
     * Retrieve the {@code published} property (displayed as '{@literal Published}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("published")
    public Boolean getPublished() { return this.published; }

    /**
     * Set the {@code published} property (displayed as {@code Published}) of the object.
     * @param published the value to set
     */
    @JsonProperty("published")
    public void setPublished(Boolean published) { this.published = published; }

    /**
     * Retrieve the {@code short_&_long_description} property (displayed as '{@literal Short & Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_&_long_description")
    public String getShortLongDescription() { return this.shortLongDescription; }

    /**
     * Set the {@code short_&_long_description} property (displayed as {@code Short & Long Description}) of the object.
     * @param shortLongDescription the value to set
     */
    @JsonProperty("short_&_long_description")
    public void setShortLongDescription(String shortLongDescription) { this.shortLongDescription = shortLongDescription; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    /**
     * Retrieve the {@code status} property (displayed as '{@literal Status}') of the object.
     * @return {@code String}
     */
    @JsonProperty("status")
    public String getStatus() { return this.status; }

    /**
     * Set the {@code status} property (displayed as {@code Status}) of the object.
     * @param status the value to set
     */
    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

}
