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
import java.util.Date;

/**
 * POJO for the {@code validvaluerange} asset type in IGC, displayed as '{@literal ValidValueRange}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ValidValueRange2.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("validvaluerange")
public class ValidValueRange2 extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("custom_attribute_def_of_has_valid_values_inverse")
    protected ItemList<InformationAsset> customAttributeDefOfHasValidValuesInverse;

    @JsonProperty("is_case_sensitive")
    protected Boolean isCaseSensitive;

    @JsonProperty("is_max_inclusive")
    protected Boolean isMaxInclusive;

    @JsonProperty("is_min_inclusive")
    protected Boolean isMinInclusive;

    @JsonProperty("is_not")
    protected Boolean isNot;

    @JsonProperty("maximum_value")
    protected String maximumValue;

    @JsonProperty("minimum_value")
    protected String minimumValue;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("valid_value_list_of_contains_valid_values_inverse")
    protected ItemList<ValidValueList2> validValueListOfContainsValidValuesInverse;

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code custom_attribute_def_of_has_valid_values_inverse} property (displayed as '{@literal Custom Attribute Def Of Has Valid Values Inverse}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("custom_attribute_def_of_has_valid_values_inverse")
    public ItemList<InformationAsset> getCustomAttributeDefOfHasValidValuesInverse() { return this.customAttributeDefOfHasValidValuesInverse; }

    /**
     * Set the {@code custom_attribute_def_of_has_valid_values_inverse} property (displayed as {@code Custom Attribute Def Of Has Valid Values Inverse}) of the object.
     * @param customAttributeDefOfHasValidValuesInverse the value to set
     */
    @JsonProperty("custom_attribute_def_of_has_valid_values_inverse")
    public void setCustomAttributeDefOfHasValidValuesInverse(ItemList<InformationAsset> customAttributeDefOfHasValidValuesInverse) { this.customAttributeDefOfHasValidValuesInverse = customAttributeDefOfHasValidValuesInverse; }

    /**
     * Retrieve the {@code is_case_sensitive} property (displayed as '{@literal Is Case Sensitive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_case_sensitive")
    public Boolean getIsCaseSensitive() { return this.isCaseSensitive; }

    /**
     * Set the {@code is_case_sensitive} property (displayed as {@code Is Case Sensitive}) of the object.
     * @param isCaseSensitive the value to set
     */
    @JsonProperty("is_case_sensitive")
    public void setIsCaseSensitive(Boolean isCaseSensitive) { this.isCaseSensitive = isCaseSensitive; }

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
     * Retrieve the {@code is_not} property (displayed as '{@literal Is Not}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_not")
    public Boolean getIsNot() { return this.isNot; }

    /**
     * Set the {@code is_not} property (displayed as {@code Is Not}) of the object.
     * @param isNot the value to set
     */
    @JsonProperty("is_not")
    public void setIsNot(Boolean isNot) { this.isNot = isNot; }

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
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

    /**
     * Retrieve the {@code sequence} property (displayed as '{@literal Sequence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sequence")
    public Number getSequence() { return this.sequence; }

    /**
     * Set the {@code sequence} property (displayed as {@code Sequence}) of the object.
     * @param sequence the value to set
     */
    @JsonProperty("sequence")
    public void setSequence(Number sequence) { this.sequence = sequence; }

    /**
     * Retrieve the {@code valid_value_list_of_contains_valid_values_inverse} property (displayed as '{@literal Valid Value List Of Contains Valid Values Inverse}') of the object.
     * @return {@code ItemList<ValidValueList2>}
     */
    @JsonProperty("valid_value_list_of_contains_valid_values_inverse")
    public ItemList<ValidValueList2> getValidValueListOfContainsValidValuesInverse() { return this.validValueListOfContainsValidValuesInverse; }

    /**
     * Set the {@code valid_value_list_of_contains_valid_values_inverse} property (displayed as {@code Valid Value List Of Contains Valid Values Inverse}) of the object.
     * @param validValueListOfContainsValidValuesInverse the value to set
     */
    @JsonProperty("valid_value_list_of_contains_valid_values_inverse")
    public void setValidValueListOfContainsValidValuesInverse(ItemList<ValidValueList2> validValueListOfContainsValidValuesInverse) { this.validValueListOfContainsValidValuesInverse = validValueListOfContainsValidValuesInverse; }

}
