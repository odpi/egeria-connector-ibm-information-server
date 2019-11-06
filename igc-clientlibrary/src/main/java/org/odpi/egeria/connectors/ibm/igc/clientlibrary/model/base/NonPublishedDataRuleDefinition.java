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
 * POJO for the {@code non_published_data_rule_definition} asset type in IGC, displayed as '{@literal Data Rule Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=NonPublishedDataRuleDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("non_published_data_rule_definition")
public class NonPublishedDataRuleDefinition extends InformationAsset {

    @JsonProperty("contact")
    protected ItemList<Steward> contact;

    @JsonProperty("data_policies")
    protected ItemList<DataPolicy> dataPolicies;

    @JsonProperty("data_rule_set_definitions")
    protected ItemList<NonPublishedDataRuleSet> dataRuleSetDefinitions;

    @JsonProperty("data_rules")
    protected ItemList<DataRule> dataRules;

    @JsonProperty("design_bindings")
    protected ItemList<DatabaseColumn> designBindings;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("project")
    protected String project;

    @JsonProperty("published")
    protected Boolean published;

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
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @return {@code ItemList<DataPolicy>}
     */
    @JsonProperty("data_policies")
    public ItemList<DataPolicy> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<DataPolicy> dataPolicies) { this.dataPolicies = dataPolicies; }

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
     * Retrieve the {@code data_rules} property (displayed as '{@literal Data Rules}') of the object.
     * @return {@code ItemList<DataRule>}
     */
    @JsonProperty("data_rules")
    public ItemList<DataRule> getDataRules() { return this.dataRules; }

    /**
     * Set the {@code data_rules} property (displayed as {@code Data Rules}) of the object.
     * @param dataRules the value to set
     */
    @JsonProperty("data_rules")
    public void setDataRules(ItemList<DataRule> dataRules) { this.dataRules = dataRules; }

    /**
     * Retrieve the {@code design_bindings} property (displayed as '{@literal Design Bindings}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("design_bindings")
    public ItemList<DatabaseColumn> getDesignBindings() { return this.designBindings; }

    /**
     * Set the {@code design_bindings} property (displayed as {@code Design Bindings}) of the object.
     * @param designBindings the value to set
     */
    @JsonProperty("design_bindings")
    public void setDesignBindings(ItemList<DatabaseColumn> designBindings) { this.designBindings = designBindings; }

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
     * Retrieve the {@code rule_logic} property (displayed as '{@literal Expression}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("rule_logic")
    public List<String> getRuleLogic() { return this.ruleLogic; }

    /**
     * Set the {@code rule_logic} property (displayed as {@code Expression}) of the object.
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
