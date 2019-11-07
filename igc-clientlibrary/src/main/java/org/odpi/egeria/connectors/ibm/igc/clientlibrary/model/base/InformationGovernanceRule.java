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
 * POJO for the {@code information_governance_rule} asset type in IGC, displayed as '{@literal Information Governance Rule}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=InformationGovernanceRule.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("information_governance_rule")
public class InformationGovernanceRule extends InformationAsset {

    /**
     * Valid values are:
     * <ul>
     *   <li>PUBLISHED (displayed in the UI as 'PUBLISHED')</li>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     * </ul>
     */
    @JsonProperty("glossary_type")
    protected String glossaryType;

    @JsonProperty("governs_assets")
    protected ItemList<InformationAsset> governsAssets;

    @JsonProperty("implemented_by_assets")
    protected ItemList<InformationAsset> implementedByAssets;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("language")
    protected String language;

    @JsonProperty("referencing_policies")
    protected ItemList<InformationGovernancePolicy> referencingPolicies;

    @JsonProperty("related_rules")
    protected ItemList<InformationGovernanceRule> relatedRules;

    /**
     * Valid values are:
     * <ul>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     *   <li>WAITING_APPROVAL (displayed in the UI as 'WAITING_APPROVAL')</li>
     *   <li>APPROVED (displayed in the UI as 'APPROVED')</li>
     * </ul>
     */
    @JsonProperty("workflow_current_state")
    protected List<String> workflowCurrentState;

    /**
     * Valid values are:
     * <ul>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     *   <li>WAITING_APPROVAL (displayed in the UI as 'WAITING_APPROVAL')</li>
     *   <li>APPROVED (displayed in the UI as 'APPROVED')</li>
     * </ul>
     */
    @JsonProperty("workflow_stored_state")
    protected List<String> workflowStoredState;

    /**
     * Retrieve the {@code glossary_type} property (displayed as '{@literal Glossary Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("glossary_type")
    public String getGlossaryType() { return this.glossaryType; }

    /**
     * Set the {@code glossary_type} property (displayed as {@code Glossary Type}) of the object.
     * @param glossaryType the value to set
     */
    @JsonProperty("glossary_type")
    public void setGlossaryType(String glossaryType) { this.glossaryType = glossaryType; }

    /**
     * Retrieve the {@code governs_assets} property (displayed as '{@literal Governs Assets}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("governs_assets")
    public ItemList<InformationAsset> getGovernsAssets() { return this.governsAssets; }

    /**
     * Set the {@code governs_assets} property (displayed as {@code Governs Assets}) of the object.
     * @param governsAssets the value to set
     */
    @JsonProperty("governs_assets")
    public void setGovernsAssets(ItemList<InformationAsset> governsAssets) { this.governsAssets = governsAssets; }

    /**
     * Retrieve the {@code implemented_by_assets} property (displayed as '{@literal Implemented by Assets}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("implemented_by_assets")
    public ItemList<InformationAsset> getImplementedByAssets() { return this.implementedByAssets; }

    /**
     * Set the {@code implemented_by_assets} property (displayed as {@code Implemented by Assets}) of the object.
     * @param implementedByAssets the value to set
     */
    @JsonProperty("implemented_by_assets")
    public void setImplementedByAssets(ItemList<InformationAsset> implementedByAssets) { this.implementedByAssets = implementedByAssets; }

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
     * Retrieve the {@code language} property (displayed as '{@literal Language}') of the object.
     * @return {@code String}
     */
    @JsonProperty("language")
    public String getLanguage() { return this.language; }

    /**
     * Set the {@code language} property (displayed as {@code Language}) of the object.
     * @param language the value to set
     */
    @JsonProperty("language")
    public void setLanguage(String language) { this.language = language; }

    /**
     * Retrieve the {@code referencing_policies} property (displayed as '{@literal Referencing Policies}') of the object.
     * @return {@code ItemList<InformationGovernancePolicy>}
     */
    @JsonProperty("referencing_policies")
    public ItemList<InformationGovernancePolicy> getReferencingPolicies() { return this.referencingPolicies; }

    /**
     * Set the {@code referencing_policies} property (displayed as {@code Referencing Policies}) of the object.
     * @param referencingPolicies the value to set
     */
    @JsonProperty("referencing_policies")
    public void setReferencingPolicies(ItemList<InformationGovernancePolicy> referencingPolicies) { this.referencingPolicies = referencingPolicies; }

    /**
     * Retrieve the {@code related_rules} property (displayed as '{@literal Related Rules}') of the object.
     * @return {@code ItemList<InformationGovernanceRule>}
     */
    @JsonProperty("related_rules")
    public ItemList<InformationGovernanceRule> getRelatedRules() { return this.relatedRules; }

    /**
     * Set the {@code related_rules} property (displayed as {@code Related Rules}) of the object.
     * @param relatedRules the value to set
     */
    @JsonProperty("related_rules")
    public void setRelatedRules(ItemList<InformationGovernanceRule> relatedRules) { this.relatedRules = relatedRules; }

    /**
     * Retrieve the {@code workflow_current_state} property (displayed as '{@literal Workflow Current State}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("workflow_current_state")
    public List<String> getWorkflowCurrentState() { return this.workflowCurrentState; }

    /**
     * Set the {@code workflow_current_state} property (displayed as {@code Workflow Current State}) of the object.
     * @param workflowCurrentState the value to set
     */
    @JsonProperty("workflow_current_state")
    public void setWorkflowCurrentState(List<String> workflowCurrentState) { this.workflowCurrentState = workflowCurrentState; }

    /**
     * Retrieve the {@code workflow_stored_state} property (displayed as '{@literal Workflow Stored State}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("workflow_stored_state")
    public List<String> getWorkflowStoredState() { return this.workflowStoredState; }

    /**
     * Set the {@code workflow_stored_state} property (displayed as {@code Workflow Stored State}) of the object.
     * @param workflowStoredState the value to set
     */
    @JsonProperty("workflow_stored_state")
    public void setWorkflowStoredState(List<String> workflowStoredState) { this.workflowStoredState = workflowStoredState; }

}
