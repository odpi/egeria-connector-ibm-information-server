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
 * POJO for the {@code providerpropertytype} asset type in IGC, displayed as '{@literal ProviderPropertyType}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Providerpropertytype.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("providerpropertytype")
public class Providerpropertytype extends Reference {

    @JsonProperty("description")
    protected String description;

    @JsonProperty("has_directory_provider_property")
    protected ItemList<Directoryproviderproperty> hasDirectoryProviderProperty;

    @JsonProperty("name")
    protected String name;

    /**
     * Valid values are:
     * <ul>
     *   <li>USER (displayed in the UI as 'USER')</li>
     *   <li>USER_GROUP (displayed in the UI as 'USER_GROUP')</li>
     *   <li>ROLE (displayed in the UI as 'ROLE')</li>
     *   <li>CONNECTION (displayed in the UI as 'CONNECTION')</li>
     * </ul>
     */
    @JsonProperty("property_type")
    protected String propertyType;

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
     * Retrieve the {@code property_type} property (displayed as '{@literal Property Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("property_type")
    public String getPropertyType() { return this.propertyType; }

    /**
     * Set the {@code property_type} property (displayed as {@code Property Type}) of the object.
     * @param propertyType the value to set
     */
    @JsonProperty("property_type")
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

}
