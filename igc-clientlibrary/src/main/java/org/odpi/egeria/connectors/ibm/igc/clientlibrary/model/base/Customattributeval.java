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
 * POJO for the {@code customattributeval} asset type in IGC, displayed as '{@literal CustomAttributeVal}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Customattributeval.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("customattributeval")
public class Customattributeval extends Reference {

    @JsonProperty("value")
    protected String value;

    @JsonProperty("x_custom_attribute_name")
    protected String xCustomAttributeName;

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

    /**
     * Retrieve the {@code x_custom_attribute_name} property (displayed as '{@literal X Custom Attribute Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("x_custom_attribute_name")
    public String getXCustomAttributeName() { return this.xCustomAttributeName; }

    /**
     * Set the {@code x_custom_attribute_name} property (displayed as {@code X Custom Attribute Name}) of the object.
     * @param xCustomAttributeName the value to set
     */
    @JsonProperty("x_custom_attribute_name")
    public void setXCustomAttributeName(String xCustomAttributeName) { this.xCustomAttributeName = xCustomAttributeName; }

}
