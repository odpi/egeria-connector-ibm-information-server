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
 * POJO for the {@code blueprint_element_link} asset type in IGC, displayed as '{@literal Blueprint Element Link}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BlueprintElementLink.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("blueprint_element_link")
public class BlueprintElementLink extends Reference {

    @JsonProperty("element_name")
    protected String elementName;

    /**
     * Retrieve the {@code element_name} property (displayed as '{@literal Element Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("element_name")
    public String getElementName() { return this.elementName; }

    /**
     * Set the {@code element_name} property (displayed as {@code Element Name}) of the object.
     * @param elementName the value to set
     */
    @JsonProperty("element_name")
    public void setElementName(String elementName) { this.elementName = elementName; }

}
