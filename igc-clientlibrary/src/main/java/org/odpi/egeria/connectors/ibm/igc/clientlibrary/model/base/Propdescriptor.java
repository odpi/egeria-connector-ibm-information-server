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
 * POJO for the {@code propdescriptor} asset type in IGC, displayed as '{@literal PropDescriptor}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Propdescriptor.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("propdescriptor")
public class Propdescriptor extends Reference {

    @JsonProperty("attribute_name")
    protected String attributeName;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("display_name")
    protected String displayName;

    @JsonProperty("is_attribute")
    protected Boolean isAttribute;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("of_class_descriptor")
    protected Classdescriptor ofClassDescriptor;

    @JsonProperty("value_expression")
    protected String valueExpression;

    @JsonProperty("value_type")
    protected String valueType;

    /**
     * Retrieve the {@code attribute_name} property (displayed as '{@literal Attribute Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("attribute_name")
    public String getAttributeName() { return this.attributeName; }

    /**
     * Set the {@code attribute_name} property (displayed as {@code Attribute Name}) of the object.
     * @param attributeName the value to set
     */
    @JsonProperty("attribute_name")
    public void setAttributeName(String attributeName) { this.attributeName = attributeName; }

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
     * Retrieve the {@code is_attribute} property (displayed as '{@literal Is Attribute}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_attribute")
    public Boolean getIsAttribute() { return this.isAttribute; }

    /**
     * Set the {@code is_attribute} property (displayed as {@code Is Attribute}) of the object.
     * @param isAttribute the value to set
     */
    @JsonProperty("is_attribute")
    public void setIsAttribute(Boolean isAttribute) { this.isAttribute = isAttribute; }

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
     * Retrieve the {@code of_class_descriptor} property (displayed as '{@literal Of Class Descriptor}') of the object.
     * @return {@code Classdescriptor}
     */
    @JsonProperty("of_class_descriptor")
    public Classdescriptor getOfClassDescriptor() { return this.ofClassDescriptor; }

    /**
     * Set the {@code of_class_descriptor} property (displayed as {@code Of Class Descriptor}) of the object.
     * @param ofClassDescriptor the value to set
     */
    @JsonProperty("of_class_descriptor")
    public void setOfClassDescriptor(Classdescriptor ofClassDescriptor) { this.ofClassDescriptor = ofClassDescriptor; }

    /**
     * Retrieve the {@code value_expression} property (displayed as '{@literal Value Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("value_expression")
    public String getValueExpression() { return this.valueExpression; }

    /**
     * Set the {@code value_expression} property (displayed as {@code Value Expression}) of the object.
     * @param valueExpression the value to set
     */
    @JsonProperty("value_expression")
    public void setValueExpression(String valueExpression) { this.valueExpression = valueExpression; }

    /**
     * Retrieve the {@code value_type} property (displayed as '{@literal Value Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("value_type")
    public String getValueType() { return this.valueType; }

    /**
     * Set the {@code value_type} property (displayed as {@code Value Type}) of the object.
     * @param valueType the value to set
     */
    @JsonProperty("value_type")
    public void setValueType(String valueType) { this.valueType = valueType; }

}
