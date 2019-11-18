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

/**
 * POJO for the {@code logical_validation_rule} asset type in IGC, displayed as '{@literal Logical Validation Rule}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=LogicalValidationRule.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("logical_validation_rule")
public class LogicalValidationRule extends Reference {

    @JsonProperty("logical_data_model")
    protected LogicalDataModel logicalDataModel;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("rule_expression")
    protected String ruleExpression;

    @JsonProperty("rule_type")
    protected String ruleType;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("top_element")
    protected String topElement;

    @JsonProperty("used_by_entity_attributes")
    protected ItemList<EntityAttribute> usedByEntityAttributes;

    /**
     * Retrieve the {@code logical_data_model} property (displayed as '{@literal Logical Data Model}') of the object.
     * @return {@code LogicalDataModel}
     */
    @JsonProperty("logical_data_model")
    public LogicalDataModel getLogicalDataModel() { return this.logicalDataModel; }

    /**
     * Set the {@code logical_data_model} property (displayed as {@code Logical Data Model}) of the object.
     * @param logicalDataModel the value to set
     */
    @JsonProperty("logical_data_model")
    public void setLogicalDataModel(LogicalDataModel logicalDataModel) { this.logicalDataModel = logicalDataModel; }

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
     * Retrieve the {@code rule_expression} property (displayed as '{@literal Rule Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("rule_expression")
    public String getRuleExpression() { return this.ruleExpression; }

    /**
     * Set the {@code rule_expression} property (displayed as {@code Rule Expression}) of the object.
     * @param ruleExpression the value to set
     */
    @JsonProperty("rule_expression")
    public void setRuleExpression(String ruleExpression) { this.ruleExpression = ruleExpression; }

    /**
     * Retrieve the {@code rule_type} property (displayed as '{@literal Rule Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("rule_type")
    public String getRuleType() { return this.ruleType; }

    /**
     * Set the {@code rule_type} property (displayed as {@code Rule Type}) of the object.
     * @param ruleType the value to set
     */
    @JsonProperty("rule_type")
    public void setRuleType(String ruleType) { this.ruleType = ruleType; }

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
     * Retrieve the {@code top_element} property (displayed as '{@literal Top Element}') of the object.
     * @return {@code String}
     */
    @JsonProperty("top_element")
    public String getTopElement() { return this.topElement; }

    /**
     * Set the {@code top_element} property (displayed as {@code Top Element}) of the object.
     * @param topElement the value to set
     */
    @JsonProperty("top_element")
    public void setTopElement(String topElement) { this.topElement = topElement; }

    /**
     * Retrieve the {@code used_by_entity_attributes} property (displayed as '{@literal Used by Entity Attributes}') of the object.
     * @return {@code ItemList<EntityAttribute>}
     */
    @JsonProperty("used_by_entity_attributes")
    public ItemList<EntityAttribute> getUsedByEntityAttributes() { return this.usedByEntityAttributes; }

    /**
     * Set the {@code used_by_entity_attributes} property (displayed as {@code Used by Entity Attributes}) of the object.
     * @param usedByEntityAttributes the value to set
     */
    @JsonProperty("used_by_entity_attributes")
    public void setUsedByEntityAttributes(ItemList<EntityAttribute> usedByEntityAttributes) { this.usedByEntityAttributes = usedByEntityAttributes; }

}
