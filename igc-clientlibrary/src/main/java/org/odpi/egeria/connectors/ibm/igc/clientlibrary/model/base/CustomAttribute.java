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
 * POJO for the {@code custom_attribute} asset type in IGC, displayed as '{@literal Custom Attribute}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=CustomAttribute.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("custom_attribute")
public class CustomAttribute extends Reference {

    @JsonProperty("applies_to")
    protected String appliesTo;

    /**
     * Valid values are:
     * <ul>
     *   <li>BOOLEAN (displayed in the UI as 'Boolean')</li>
     *   <li>INTEGER (displayed in the UI as 'INTEGER')</li>
     *   <li>DOUBLE (displayed in the UI as 'Number')</li>
     *   <li>STRING (displayed in the UI as 'Predefined Values')</li>
     *   <li>DATE (displayed in the UI as 'Date')</li>
     *   <li>TEXT (displayed in the UI as 'Text')</li>
     *   <li>REFERENCE (displayed in the UI as 'REFERENCE')</li>
     * </ul>
     */
    @JsonProperty("custom_attribute_type")
    protected String customAttributeType;

    /**
     * Retrieve the {@code applies_to} property (displayed as '{@literal Applies To}') of the object.
     * @return {@code String}
     */
    @JsonProperty("applies_to")
    public String getAppliesTo() { return this.appliesTo; }

    /**
     * Set the {@code applies_to} property (displayed as {@code Applies To}) of the object.
     * @param appliesTo the value to set
     */
    @JsonProperty("applies_to")
    public void setAppliesTo(String appliesTo) { this.appliesTo = appliesTo; }

    /**
     * Retrieve the {@code custom_attribute_type} property (displayed as '{@literal Custom Attribute Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("custom_attribute_type")
    public String getCustomAttributeType() { return this.customAttributeType; }

    /**
     * Set the {@code custom_attribute_type} property (displayed as {@code Custom Attribute Type}) of the object.
     * @param customAttributeType the value to set
     */
    @JsonProperty("custom_attribute_type")
    public void setCustomAttributeType(String customAttributeType) { this.customAttributeType = customAttributeType; }

}
