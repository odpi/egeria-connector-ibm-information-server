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
public class NonPublishedDataRuleDefinition extends DataRuleDefinition {

    @JsonProperty("data_rules")
    protected ItemList<DataRule> dataRules;

    @JsonProperty("design_bindings")
    protected ItemList<DatabaseColumn> designBindings;

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

}
