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

/**
 * POJO for the {@code directoryproviderproperty} asset type in IGC, displayed as '{@literal DirectoryProviderProperty}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Directoryproviderproperty.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("directoryproviderproperty")
public class Directoryproviderproperty extends Reference {

    @JsonProperty("of_directory_provider_configuration")
    protected Directoryproviderconfiguration ofDirectoryProviderConfiguration;

    @JsonProperty("of_provider_property_info")
    protected Providerpropertyinfo ofProviderPropertyInfo;

    @JsonProperty("of_provider_property_type")
    protected Providerpropertytype ofProviderPropertyType;

    @JsonProperty("value")
    protected String value;

    /**
     * Retrieve the {@code of_directory_provider_configuration} property (displayed as '{@literal Of Directory Provider Configuration}') of the object.
     * @return {@code Directoryproviderconfiguration}
     */
    @JsonProperty("of_directory_provider_configuration")
    public Directoryproviderconfiguration getOfDirectoryProviderConfiguration() { return this.ofDirectoryProviderConfiguration; }

    /**
     * Set the {@code of_directory_provider_configuration} property (displayed as {@code Of Directory Provider Configuration}) of the object.
     * @param ofDirectoryProviderConfiguration the value to set
     */
    @JsonProperty("of_directory_provider_configuration")
    public void setOfDirectoryProviderConfiguration(Directoryproviderconfiguration ofDirectoryProviderConfiguration) { this.ofDirectoryProviderConfiguration = ofDirectoryProviderConfiguration; }

    /**
     * Retrieve the {@code of_provider_property_info} property (displayed as '{@literal Of Provider Property Info}') of the object.
     * @return {@code Providerpropertyinfo}
     */
    @JsonProperty("of_provider_property_info")
    public Providerpropertyinfo getOfProviderPropertyInfo() { return this.ofProviderPropertyInfo; }

    /**
     * Set the {@code of_provider_property_info} property (displayed as {@code Of Provider Property Info}) of the object.
     * @param ofProviderPropertyInfo the value to set
     */
    @JsonProperty("of_provider_property_info")
    public void setOfProviderPropertyInfo(Providerpropertyinfo ofProviderPropertyInfo) { this.ofProviderPropertyInfo = ofProviderPropertyInfo; }

    /**
     * Retrieve the {@code of_provider_property_type} property (displayed as '{@literal Of Provider Property Type}') of the object.
     * @return {@code Providerpropertytype}
     */
    @JsonProperty("of_provider_property_type")
    public Providerpropertytype getOfProviderPropertyType() { return this.ofProviderPropertyType; }

    /**
     * Set the {@code of_provider_property_type} property (displayed as {@code Of Provider Property Type}) of the object.
     * @param ofProviderPropertyType the value to set
     */
    @JsonProperty("of_provider_property_type")
    public void setOfProviderPropertyType(Providerpropertytype ofProviderPropertyType) { this.ofProviderPropertyType = ofProviderPropertyType; }

    /**
     * Retrieve the {@code value} property (displayed as '{@literal Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("value")
    public String getValue() { return this.value; }

    /**
     * Set the {@code value} property (displayed as {@code Value}) of the object.
     * @param value the value to set
     */
    @JsonProperty("value")
    public void setValue(String value) { this.value = value; }

}
