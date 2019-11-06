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
 * POJO for the {@code valid_value_range} asset type in IGC, displayed as '{@literal Valid Value Range}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ValidValueRange.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("valid_value_range")
public class ValidValueRange extends Reference {

    @JsonProperty("design_column")
    protected ItemList<DataItem> designColumn;

    @JsonProperty("is_max_inclusive")
    protected Boolean isMaxInclusive;

    @JsonProperty("is_min_inclusive")
    protected Boolean isMinInclusive;

    @JsonProperty("maximum_value")
    protected String maximumValue;

    @JsonProperty("minimum_value")
    protected String minimumValue;

    @JsonProperty("name")
    protected String name;

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
     * Retrieve the {@code is_max_inclusive} property (displayed as '{@literal Is Max Inclusive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_max_inclusive")
    public Boolean getIsMaxInclusive() { return this.isMaxInclusive; }

    /**
     * Set the {@code is_max_inclusive} property (displayed as {@code Is Max Inclusive}) of the object.
     * @param isMaxInclusive the value to set
     */
    @JsonProperty("is_max_inclusive")
    public void setIsMaxInclusive(Boolean isMaxInclusive) { this.isMaxInclusive = isMaxInclusive; }

    /**
     * Retrieve the {@code is_min_inclusive} property (displayed as '{@literal Is Min Inclusive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_min_inclusive")
    public Boolean getIsMinInclusive() { return this.isMinInclusive; }

    /**
     * Set the {@code is_min_inclusive} property (displayed as {@code Is Min Inclusive}) of the object.
     * @param isMinInclusive the value to set
     */
    @JsonProperty("is_min_inclusive")
    public void setIsMinInclusive(Boolean isMinInclusive) { this.isMinInclusive = isMinInclusive; }

    /**
     * Retrieve the {@code maximum_value} property (displayed as '{@literal Maximum Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("maximum_value")
    public String getMaximumValue() { return this.maximumValue; }

    /**
     * Set the {@code maximum_value} property (displayed as {@code Maximum Value}) of the object.
     * @param maximumValue the value to set
     */
    @JsonProperty("maximum_value")
    public void setMaximumValue(String maximumValue) { this.maximumValue = maximumValue; }

    /**
     * Retrieve the {@code minimum_value} property (displayed as '{@literal Minimum Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("minimum_value")
    public String getMinimumValue() { return this.minimumValue; }

    /**
     * Set the {@code minimum_value} property (displayed as {@code Minimum Value}) of the object.
     * @param minimumValue the value to set
     */
    @JsonProperty("minimum_value")
    public void setMinimumValue(String minimumValue) { this.minimumValue = minimumValue; }

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
