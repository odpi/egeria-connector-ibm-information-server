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
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code published_data_rule_definition} asset type in IGC, displayed as '{@literal Data Rule Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=PublishedDataRuleDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("published_data_rule_definition")
public class PublishedDataRuleDefinition extends InformationAsset {

    @JsonProperty("contact")
    protected ItemList<Steward> contact;

    @JsonProperty("copied_into_data_rule_stages")
    protected ItemList<Stage> copiedIntoDataRuleStages;

    @JsonProperty("data_policies")
    protected ItemList<MainObject> dataPolicies;

    @JsonProperty("data_rule_set_definitions")
    protected ItemList<NonPublishedDataRuleSet> dataRuleSetDefinitions;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("is_valid")
    protected Boolean isValid;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("publication_date")
    protected Date publicationDate;

    @JsonProperty("published")
    protected Boolean published;

    @JsonProperty("root_folder")
    protected String rootFolder;

    @JsonProperty("rule_logic")
    protected List<String> ruleLogic;

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
     *   <li>SUGGESTED (displayed in the UI as 'SUGGESTED')</li>
     *   <li>ERROR (displayed in the UI as 'ERROR')</li>
     * </ul>
     */
    @JsonProperty("status")
    protected String status;

    /**
     * Retrieve the {@code contact} property (displayed as '{@literal Contacts}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("contact")
    public ItemList<Steward> getContact() { return this.contact; }

    /**
     * Set the {@code contact} property (displayed as {@code Contacts}) of the object.
     * @param contact the value to set
     */
    @JsonProperty("contact")
    public void setContact(ItemList<Steward> contact) { this.contact = contact; }

    /**
     * Retrieve the {@code copied_into_data_rule_stages} property (displayed as '{@literal Copied Into Data Rule Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("copied_into_data_rule_stages")
    public ItemList<Stage> getCopiedIntoDataRuleStages() { return this.copiedIntoDataRuleStages; }

    /**
     * Set the {@code copied_into_data_rule_stages} property (displayed as {@code Copied Into Data Rule Stages}) of the object.
     * @param copiedIntoDataRuleStages the value to set
     */
    @JsonProperty("copied_into_data_rule_stages")
    public void setCopiedIntoDataRuleStages(ItemList<Stage> copiedIntoDataRuleStages) { this.copiedIntoDataRuleStages = copiedIntoDataRuleStages; }

    /**
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("data_policies")
    public ItemList<MainObject> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<MainObject> dataPolicies) { this.dataPolicies = dataPolicies; }

    /**
     * Retrieve the {@code data_rule_set_definitions} property (displayed as '{@literal Data Rule Set Definitions}') of the object.
     * @return {@code ItemList<NonPublishedDataRuleSet>}
     */
    @JsonProperty("data_rule_set_definitions")
    public ItemList<NonPublishedDataRuleSet> getDataRuleSetDefinitions() { return this.dataRuleSetDefinitions; }

    /**
     * Set the {@code data_rule_set_definitions} property (displayed as {@code Data Rule Set Definitions}) of the object.
     * @param dataRuleSetDefinitions the value to set
     */
    @JsonProperty("data_rule_set_definitions")
    public void setDataRuleSetDefinitions(ItemList<NonPublishedDataRuleSet> dataRuleSetDefinitions) { this.dataRuleSetDefinitions = dataRuleSetDefinitions; }

    /**
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

    /**
     * Retrieve the {@code is_valid} property (displayed as '{@literal Is Valid}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_valid")
    public Boolean getIsValid() { return this.isValid; }

    /**
     * Set the {@code is_valid} property (displayed as {@code Is Valid}) of the object.
     * @param isValid the value to set
     */
    @JsonProperty("is_valid")
    public void setIsValid(Boolean isValid) { this.isValid = isValid; }

    /**
     * Retrieve the {@code native_id} property (displayed as '{@literal Native ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_id")
    public String getNativeId() { return this.nativeId; }

    /**
     * Set the {@code native_id} property (displayed as {@code Native ID}) of the object.
     * @param nativeId the value to set
     */
    @JsonProperty("native_id")
    public void setNativeId(String nativeId) { this.nativeId = nativeId; }

    /**
     * Retrieve the {@code publication_date} property (displayed as '{@literal Publication Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("publication_date")
    public Date getPublicationDate() { return this.publicationDate; }

    /**
     * Set the {@code publication_date} property (displayed as {@code Publication Date}) of the object.
     * @param publicationDate the value to set
     */
    @JsonProperty("publication_date")
    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }

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
     * Retrieve the {@code root_folder} property (displayed as '{@literal Root Folder}') of the object.
     * @return {@code String}
     */
    @JsonProperty("root_folder")
    public String getRootFolder() { return this.rootFolder; }

    /**
     * Set the {@code root_folder} property (displayed as {@code Root Folder}) of the object.
     * @param rootFolder the value to set
     */
    @JsonProperty("root_folder")
    public void setRootFolder(String rootFolder) { this.rootFolder = rootFolder; }

    /**
     * Retrieve the {@code rule_logic} property (displayed as '{@literal Rule Logic}' or '{@literal Expression}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("rule_logic")
    public List<String> getRuleLogic() { return this.ruleLogic; }

    /**
     * Set the {@code rule_logic} property (displayed as {@code Rule Logic} or {@code Expression}) of the object.
     * @param ruleLogic the value to set
     */
    @JsonProperty("rule_logic")
    public void setRuleLogic(List<String> ruleLogic) { this.ruleLogic = ruleLogic; }

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
