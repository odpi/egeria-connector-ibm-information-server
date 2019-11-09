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
 * POJO for the {@code valid_value} asset type in IGC, displayed as '{@literal Valid Value}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ValidValue.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("valid_value")
public class ValidValue extends Reference {

    @JsonProperty("design_column")
    protected ItemList<DataItem> designColumn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("rule_component")
    protected String ruleComponent;

    @JsonProperty("rule_type")
    protected String ruleType;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("valid_value_list")
    protected ValidValueList validValueList;

    /**
     * Retrieve the {@code design_column} property (displayed as '{@literal Design Column}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("design_column")
    public ItemList<DataItem> getDesignColumn() { return this.designColumn; }

    /**
     * Set the {@code design_column} property (displayed as {@code Design Column}) of the object.
     * @param designColumn the value to set
     */
    @JsonProperty("design_column")
    public void setDesignColumn(ItemList<DataItem> designColumn) { this.designColumn = designColumn; }

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
     * Retrieve the {@code rule_component} property (displayed as '{@literal Rule Component}') of the object.
     * @return {@code String}
     */
    @JsonProperty("rule_component")
    public String getRuleComponent() { return this.ruleComponent; }

    /**
     * Set the {@code rule_component} property (displayed as {@code Rule Component}) of the object.
     * @param ruleComponent the value to set
     */
    @JsonProperty("rule_component")
    public void setRuleComponent(String ruleComponent) { this.ruleComponent = ruleComponent; }

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
     * Retrieve the {@code valid_value_list} property (displayed as '{@literal Valid Value List}') of the object.
     * @return {@code ValidValueList}
     */
    @JsonProperty("valid_value_list")
    public ValidValueList getValidValueList() { return this.validValueList; }

    /**
     * Set the {@code valid_value_list} property (displayed as {@code Valid Value List}) of the object.
     * @param validValueList the value to set
     */
    @JsonProperty("valid_value_list")
    public void setValidValueList(ValidValueList validValueList) { this.validValueList = validValueList; }

}
