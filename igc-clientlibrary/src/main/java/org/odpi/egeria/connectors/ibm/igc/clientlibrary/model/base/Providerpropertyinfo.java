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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code providerpropertyinfo} asset type in IGC, displayed as '{@literal ProviderPropertyInfo}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("providerpropertyinfo")
public class Providerpropertyinfo extends Reference {

    @JsonProperty("complex_attribute_source")
    protected Boolean complexAttributeSource;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("default_value")
    protected String defaultValue;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("display_name")
    protected String displayName;

    @JsonProperty("has_directory_provider_property")
    protected ItemList<Directoryproviderproperty> hasDirectoryProviderProperty;

    @JsonProperty("has_provider_property_info_extended")
    protected ItemList<Providerpropertyinfoextended> hasProviderPropertyInfoExtended;

    @JsonProperty("is_complex_attribute")
    protected Boolean isComplexAttribute;

    @JsonProperty("is_editable")
    protected Boolean isEditable;

    @JsonProperty("is_required")
    protected Boolean isRequired;

    @JsonProperty("is_searchable")
    protected Boolean isSearchable;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("property_data_type")
    protected String propertyDataType;

    /**
     * Retrieve the {@code complex_attribute_source} property (displayed as '{@literal Complex Attribute Source}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("complex_attribute_source")
    public Boolean getComplexAttributeSource() { return this.complexAttributeSource; }

    /**
     * Set the {@code complex_attribute_source} property (displayed as {@code Complex Attribute Source}) of the object.
     * @param complexAttributeSource the value to set
     */
    @JsonProperty("complex_attribute_source")
    public void setComplexAttributeSource(Boolean complexAttributeSource) { this.complexAttributeSource = complexAttributeSource; }

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
     * Retrieve the {@code default_value} property (displayed as '{@literal Default Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("default_value")
    public String getDefaultValue() { return this.defaultValue; }

    /**
     * Set the {@code default_value} property (displayed as {@code Default Value}) of the object.
     * @param defaultValue the value to set
     */
    @JsonProperty("default_value")
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("description")
    public String getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(String description) { this.description = description; }

    /**
     * Retrieve the {@code display_name} property (displayed as '{@literal Display Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("display_name")
    public String getDisplayName() { return this.displayName; }

    /**
     * Set the {@code display_name} property (displayed as {@code Display Name}) of the object.
     * @param displayName the value to set
     */
    @JsonProperty("display_name")
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    /**
     * Retrieve the {@code has_directory_provider_property} property (displayed as '{@literal Has Directory Provider Property}') of the object.
     * @return {@code ItemList<Directoryproviderproperty>}
     */
    @JsonProperty("has_directory_provider_property")
    public ItemList<Directoryproviderproperty> getHasDirectoryProviderProperty() { return this.hasDirectoryProviderProperty; }

    /**
     * Set the {@code has_directory_provider_property} property (displayed as {@code Has Directory Provider Property}) of the object.
     * @param hasDirectoryProviderProperty the value to set
     */
    @JsonProperty("has_directory_provider_property")
    public void setHasDirectoryProviderProperty(ItemList<Directoryproviderproperty> hasDirectoryProviderProperty) { this.hasDirectoryProviderProperty = hasDirectoryProviderProperty; }

    /**
     * Retrieve the {@code has_provider_property_info_extended} property (displayed as '{@literal Has Provider Property Info Extended}') of the object.
     * @return {@code ItemList<Providerpropertyinfoextended>}
     */
    @JsonProperty("has_provider_property_info_extended")
    public ItemList<Providerpropertyinfoextended> getHasProviderPropertyInfoExtended() { return this.hasProviderPropertyInfoExtended; }

    /**
     * Set the {@code has_provider_property_info_extended} property (displayed as {@code Has Provider Property Info Extended}) of the object.
     * @param hasProviderPropertyInfoExtended the value to set
     */
    @JsonProperty("has_provider_property_info_extended")
    public void setHasProviderPropertyInfoExtended(ItemList<Providerpropertyinfoextended> hasProviderPropertyInfoExtended) { this.hasProviderPropertyInfoExtended = hasProviderPropertyInfoExtended; }

    /**
     * Retrieve the {@code is_complex_attribute} property (displayed as '{@literal Is Complex Attribute}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_complex_attribute")
    public Boolean getIsComplexAttribute() { return this.isComplexAttribute; }

    /**
     * Set the {@code is_complex_attribute} property (displayed as {@code Is Complex Attribute}) of the object.
     * @param isComplexAttribute the value to set
     */
    @JsonProperty("is_complex_attribute")
    public void setIsComplexAttribute(Boolean isComplexAttribute) { this.isComplexAttribute = isComplexAttribute; }

    /**
     * Retrieve the {@code is_editable} property (displayed as '{@literal Is Editable}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_editable")
    public Boolean getIsEditable() { return this.isEditable; }

    /**
     * Set the {@code is_editable} property (displayed as {@code Is Editable}) of the object.
     * @param isEditable the value to set
     */
    @JsonProperty("is_editable")
    public void setIsEditable(Boolean isEditable) { this.isEditable = isEditable; }

    /**
     * Retrieve the {@code is_required} property (displayed as '{@literal Is Required}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_required")
    public Boolean getIsRequired() { return this.isRequired; }

    /**
     * Set the {@code is_required} property (displayed as {@code Is Required}) of the object.
     * @param isRequired the value to set
     */
    @JsonProperty("is_required")
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }

    /**
     * Retrieve the {@code is_searchable} property (displayed as '{@literal Is Searchable}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_searchable")
    public Boolean getIsSearchable() { return this.isSearchable; }

    /**
     * Set the {@code is_searchable} property (displayed as {@code Is Searchable}) of the object.
     * @param isSearchable the value to set
     */
    @JsonProperty("is_searchable")
    public void setIsSearchable(Boolean isSearchable) { this.isSearchable = isSearchable; }

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
     * Retrieve the {@code property_data_type} property (displayed as '{@literal Property Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("property_data_type")
    public String getPropertyDataType() { return this.propertyDataType; }

    /**
     * Set the {@code property_data_type} property (displayed as {@code Property Data Type}) of the object.
     * @param propertyDataType the value to set
     */
    @JsonProperty("property_data_type")
    public void setPropertyDataType(String propertyDataType) { this.propertyDataType = propertyDataType; }

}
