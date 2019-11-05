/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code information_governance_policy} asset type in IGC, displayed as '{@literal Information Governance Policy}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("information_governance_policy")
public class InformationGovernancePolicy extends InformationAsset {

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("information_governance_rules")
    protected ItemList<InformationGovernanceRule> informationGovernanceRules;

    @JsonProperty("language")
    protected String language;

    @JsonProperty("parent_policy")
    protected InformationGovernancePolicy parentPolicy;

    @JsonProperty("subpolicies")
    protected ItemList<InformationGovernancePolicy> subpolicies;

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
     * Retrieve the {@code information_governance_rules} property (displayed as '{@literal Information Governance Rules}') of the object.
     * @return {@code ItemList<InformationGovernanceRule>}
     */
    @JsonProperty("information_governance_rules")
    public ItemList<InformationGovernanceRule> getInformationGovernanceRules() { return this.informationGovernanceRules; }

    /**
     * Set the {@code information_governance_rules} property (displayed as {@code Information Governance Rules}) of the object.
     * @param informationGovernanceRules the value to set
     */
    @JsonProperty("information_governance_rules")
    public void setInformationGovernanceRules(ItemList<InformationGovernanceRule> informationGovernanceRules) { this.informationGovernanceRules = informationGovernanceRules; }

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
     * Retrieve the {@code parent_policy} property (displayed as '{@literal Parent Policy}') of the object.
     * @return {@code InformationGovernancePolicy}
     */
    @JsonProperty("parent_policy")
    public InformationGovernancePolicy getParentPolicy() { return this.parentPolicy; }

    /**
     * Set the {@code parent_policy} property (displayed as {@code Parent Policy}) of the object.
     * @param parentPolicy the value to set
     */
    @JsonProperty("parent_policy")
    public void setParentPolicy(InformationGovernancePolicy parentPolicy) { this.parentPolicy = parentPolicy; }

    /**
     * Retrieve the {@code subpolicies} property (displayed as '{@literal Subpolicies}') of the object.
     * @return {@code ItemList<InformationGovernancePolicy>}
     */
    @JsonProperty("subpolicies")
    public ItemList<InformationGovernancePolicy> getSubpolicies() { return this.subpolicies; }

    /**
     * Set the {@code subpolicies} property (displayed as {@code Subpolicies}) of the object.
     * @param subpolicies the value to set
     */
    @JsonProperty("subpolicies")
    public void setSubpolicies(ItemList<InformationGovernancePolicy> subpolicies) { this.subpolicies = subpolicies; }

}
