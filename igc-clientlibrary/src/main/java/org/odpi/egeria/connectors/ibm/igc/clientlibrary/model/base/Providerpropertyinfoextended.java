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
 * POJO for the {@code providerpropertyinfoextended} asset type in IGC, displayed as '{@literal ProviderPropertyInfoExtended}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Providerpropertyinfoextended.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("providerpropertyinfoextended")
public class Providerpropertyinfoextended extends Reference {

    @JsonProperty("name")
    protected String name;

    @JsonProperty("of_provider_property_info")
    protected Providerpropertyinfo ofProviderPropertyInfo;

    @JsonProperty("str_value")
    protected String strValue;

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
     * Retrieve the {@code str_value} property (displayed as '{@literal Str Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("str_value")
    public String getStrValue() { return this.strValue; }

    /**
     * Set the {@code str_value} property (displayed as {@code Str Value}) of the object.
     * @param strValue the value to set
     */
    @JsonProperty("str_value")
    public void setStrValue(String strValue) { this.strValue = strValue; }

}
