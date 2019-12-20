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

/**
 * POJO for the {@code non_published_data_rule_set} asset type in IGC, displayed as '{@literal Data Rule Set Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=NonPublishedDataRuleSet.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("non_published_data_rule_set")
public class NonPublishedDataRuleSet extends DataRuleSetDefinition {

    @JsonProperty("data_rule_definitions")
    protected ItemList<MainObject> dataRuleDefinitions;

    @JsonProperty("data_rule_sets")
    protected ItemList<DataRuleSet> dataRuleSets;

    @JsonProperty("design_bindings")
    protected ItemList<DatabaseColumn> designBindings;

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
     * @return {@code ItemList<DataRuleSet>}
     */
    @JsonProperty("data_rule_sets")
    public ItemList<DataRuleSet> getDataRuleSets() { return this.dataRuleSets; }

    /**
     * Set the {@code data_rule_sets} property (displayed as {@code Data Rule Sets}) of the object.
     * @param dataRuleSets the value to set
     */
    @JsonProperty("data_rule_sets")
    public void setDataRuleSets(ItemList<DataRuleSet> dataRuleSets) { this.dataRuleSets = dataRuleSets; }

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

}
