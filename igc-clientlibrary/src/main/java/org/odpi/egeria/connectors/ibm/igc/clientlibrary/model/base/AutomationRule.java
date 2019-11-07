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

/**
 * POJO for the {@code automation_rule} asset type in IGC, displayed as '{@literal Automation Rule}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AutomationRule.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("automation_rule")
public class AutomationRule extends InformationAsset {

    @JsonProperty("associated_terms")
    protected ItemList<Term> associatedTerms;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("rule_logic")
    protected String ruleLogic;

    /**
     * Retrieve the {@code associated_terms} property (displayed as '{@literal Associated Terms}') of the object.
     * @return {@code ItemList<Term>}
     */
    @JsonProperty("associated_terms")
    public ItemList<Term> getAssociatedTerms() { return this.associatedTerms; }

    /**
     * Set the {@code associated_terms} property (displayed as {@code Associated Terms}) of the object.
     * @param associatedTerms the value to set
     */
    @JsonProperty("associated_terms")
    public void setAssociatedTerms(ItemList<Term> associatedTerms) { this.associatedTerms = associatedTerms; }

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
     * Retrieve the {@code rule_logic} property (displayed as '{@literal Rule Logic}') of the object.
     * @return {@code String}
     */
    @JsonProperty("rule_logic")
    public String getRuleLogic() { return this.ruleLogic; }

    /**
     * Set the {@code rule_logic} property (displayed as {@code Rule Logic}) of the object.
     * @param ruleLogic the value to set
     */
    @JsonProperty("rule_logic")
    public void setRuleLogic(String ruleLogic) { this.ruleLogic = ruleLogic; }

}
