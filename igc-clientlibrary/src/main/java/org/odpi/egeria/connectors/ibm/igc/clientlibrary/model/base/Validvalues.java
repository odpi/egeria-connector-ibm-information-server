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
 * POJO for the {@code validvalues} asset type in IGC, displayed as '{@literal ValidValues}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Validvalues.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("validvalues")
public class Validvalues extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("custom_attribute_def_of_has_valid_values_inverse")
    protected ItemList<Customattributedef> customAttributeDefOfHasValidValuesInverse;

    @JsonProperty("is_case_sensitive")
    protected Boolean isCaseSensitive;

    @JsonProperty("is_not")
    protected Boolean isNot;

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
     * @return {@code ItemList<Customattributedef>}
     */
    @JsonProperty("custom_attribute_def_of_has_valid_values_inverse")
    public ItemList<Customattributedef> getCustomAttributeDefOfHasValidValuesInverse() { return this.customAttributeDefOfHasValidValuesInverse; }

    /**
     * Set the {@code custom_attribute_def_of_has_valid_values_inverse} property (displayed as {@code Custom Attribute Def Of Has Valid Values Inverse}) of the object.
     * @param customAttributeDefOfHasValidValuesInverse the value to set
     */
    @JsonProperty("custom_attribute_def_of_has_valid_values_inverse")
    public void setCustomAttributeDefOfHasValidValuesInverse(ItemList<Customattributedef> customAttributeDefOfHasValidValuesInverse) { this.customAttributeDefOfHasValidValuesInverse = customAttributeDefOfHasValidValuesInverse; }

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
