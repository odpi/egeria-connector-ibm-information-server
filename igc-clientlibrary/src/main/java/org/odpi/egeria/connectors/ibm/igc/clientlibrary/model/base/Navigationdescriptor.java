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
 * POJO for the {@code navigationdescriptor} asset type in IGC, displayed as '{@literal NavigationDescriptor}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Navigationdescriptor.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("navigationdescriptor")
public class Navigationdescriptor extends Reference {

    @JsonProperty("description")
    protected String description;

    @JsonProperty("display_name")
    protected String displayName;

    @JsonProperty("expression")
    protected String expression;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("of_class_descriptor")
    protected Classdescriptor ofClassDescriptor;

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
     * Retrieve the {@code expression} property (displayed as '{@literal Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("expression")
    public String getExpression() { return this.expression; }

    /**
     * Set the {@code expression} property (displayed as {@code Expression}) of the object.
     * @param expression the value to set
     */
    @JsonProperty("expression")
    public void setExpression(String expression) { this.expression = expression; }

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

}
