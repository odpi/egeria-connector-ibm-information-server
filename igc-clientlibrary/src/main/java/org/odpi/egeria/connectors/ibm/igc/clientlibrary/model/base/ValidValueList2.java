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
 * POJO for the {@code validvaluelist} asset type in IGC, displayed as '{@literal ValidValueList}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ValidValueList2.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("validvaluelist")
public class ValidValueList2 extends Reference {

    @JsonProperty("contains_valid_values")
    protected ItemList<Validvalues> containsValidValues;

    @JsonProperty("custom_attribute_def_of_has_valid_values_inverse")
    protected ItemList<InformationAsset> customAttributeDefOfHasValidValuesInverse;

    @JsonProperty("is_case_sensitive")
    protected Boolean isCaseSensitive;

    @JsonProperty("is_not")
    protected Boolean isNot;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("valid_value_list_of_contains_valid_values_inverse")
    protected ItemList<ValidValueList2> validValueListOfContainsValidValuesInverse;

    /**
     * Retrieve the {@code contains_valid_values} property (displayed as '{@literal Contains Valid Values}') of the object.
     * @return {@code ItemList<Validvalues>}
     */
    @JsonProperty("contains_valid_values")
    public ItemList<Validvalues> getContainsValidValues() { return this.containsValidValues; }

    /**
     * Set the {@code contains_valid_values} property (displayed as {@code Contains Valid Values}) of the object.
     * @param containsValidValues the value to set
     */
    @JsonProperty("contains_valid_values")
    public void setContainsValidValues(ItemList<Validvalues> containsValidValues) { this.containsValidValues = containsValidValues; }

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
