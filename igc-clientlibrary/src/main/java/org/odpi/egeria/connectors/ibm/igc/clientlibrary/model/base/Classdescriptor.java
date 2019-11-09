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
 * POJO for the {@code classdescriptor} asset type in IGC, displayed as '{@literal ClassDescriptor}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Classdescriptor.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("classdescriptor")
public class Classdescriptor extends Reference {

    @JsonProperty("class_name")
    protected String className;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("has_action_descriptor")
    protected ItemList<Actiondescriptor> hasActionDescriptor;

    @JsonProperty("has_custom_attribute_def")
    protected ItemList<InformationAsset> hasCustomAttributeDef;

    @JsonProperty("has_navigation_descriptor")
    protected ItemList<Navigationdescriptor> hasNavigationDescriptor;

    @JsonProperty("has_prop_descriptor")
    protected ItemList<Propdescriptor> hasPropDescriptor;

    @JsonProperty("identifier_attribute")
    protected String identifierAttribute;

    @JsonProperty("long_description_attribute")
    protected String longDescriptionAttribute;

    @JsonProperty("model_name")
    protected String modelName;

    @JsonProperty("model_uri")
    protected String modelUri;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("short_description_attribute")
    protected String shortDescriptionAttribute;

    @JsonProperty("used_in_application_function")
    protected ItemList<Applicationfunction> usedInApplicationFunction;

    /**
     * Retrieve the {@code class_name} property (displayed as '{@literal Class Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("class_name")
    public String getClassName() { return this.className; }

    /**
     * Set the {@code class_name} property (displayed as {@code Class Name}) of the object.
     * @param className the value to set
     */
    @JsonProperty("class_name")
    public void setClassName(String className) { this.className = className; }

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
     * Retrieve the {@code has_action_descriptor} property (displayed as '{@literal Has Action Descriptor}') of the object.
     * @return {@code ItemList<Actiondescriptor>}
     */
    @JsonProperty("has_action_descriptor")
    public ItemList<Actiondescriptor> getHasActionDescriptor() { return this.hasActionDescriptor; }

    /**
     * Set the {@code has_action_descriptor} property (displayed as {@code Has Action Descriptor}) of the object.
     * @param hasActionDescriptor the value to set
     */
    @JsonProperty("has_action_descriptor")
    public void setHasActionDescriptor(ItemList<Actiondescriptor> hasActionDescriptor) { this.hasActionDescriptor = hasActionDescriptor; }

    /**
     * Retrieve the {@code has_custom_attribute_def} property (displayed as '{@literal Has Custom Attribute Def}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("has_custom_attribute_def")
    public ItemList<InformationAsset> getHasCustomAttributeDef() { return this.hasCustomAttributeDef; }

    /**
     * Set the {@code has_custom_attribute_def} property (displayed as {@code Has Custom Attribute Def}) of the object.
     * @param hasCustomAttributeDef the value to set
     */
    @JsonProperty("has_custom_attribute_def")
    public void setHasCustomAttributeDef(ItemList<InformationAsset> hasCustomAttributeDef) { this.hasCustomAttributeDef = hasCustomAttributeDef; }

    /**
     * Retrieve the {@code has_navigation_descriptor} property (displayed as '{@literal Has Navigation Descriptor}') of the object.
     * @return {@code ItemList<Navigationdescriptor>}
     */
    @JsonProperty("has_navigation_descriptor")
    public ItemList<Navigationdescriptor> getHasNavigationDescriptor() { return this.hasNavigationDescriptor; }

    /**
     * Set the {@code has_navigation_descriptor} property (displayed as {@code Has Navigation Descriptor}) of the object.
     * @param hasNavigationDescriptor the value to set
     */
    @JsonProperty("has_navigation_descriptor")
    public void setHasNavigationDescriptor(ItemList<Navigationdescriptor> hasNavigationDescriptor) { this.hasNavigationDescriptor = hasNavigationDescriptor; }

    /**
     * Retrieve the {@code has_prop_descriptor} property (displayed as '{@literal Has Prop Descriptor}') of the object.
     * @return {@code ItemList<Propdescriptor>}
     */
    @JsonProperty("has_prop_descriptor")
    public ItemList<Propdescriptor> getHasPropDescriptor() { return this.hasPropDescriptor; }

    /**
     * Set the {@code has_prop_descriptor} property (displayed as {@code Has Prop Descriptor}) of the object.
     * @param hasPropDescriptor the value to set
     */
    @JsonProperty("has_prop_descriptor")
    public void setHasPropDescriptor(ItemList<Propdescriptor> hasPropDescriptor) { this.hasPropDescriptor = hasPropDescriptor; }

    /**
     * Retrieve the {@code identifier_attribute} property (displayed as '{@literal Identifier Attribute}') of the object.
     * @return {@code String}
     */
    @JsonProperty("identifier_attribute")
    public String getIdentifierAttribute() { return this.identifierAttribute; }

    /**
     * Set the {@code identifier_attribute} property (displayed as {@code Identifier Attribute}) of the object.
     * @param identifierAttribute the value to set
     */
    @JsonProperty("identifier_attribute")
    public void setIdentifierAttribute(String identifierAttribute) { this.identifierAttribute = identifierAttribute; }

    /**
     * Retrieve the {@code long_description_attribute} property (displayed as '{@literal Long Description Attribute}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description_attribute")
    public String getLongDescriptionAttribute() { return this.longDescriptionAttribute; }

    /**
     * Set the {@code long_description_attribute} property (displayed as {@code Long Description Attribute}) of the object.
     * @param longDescriptionAttribute the value to set
     */
    @JsonProperty("long_description_attribute")
    public void setLongDescriptionAttribute(String longDescriptionAttribute) { this.longDescriptionAttribute = longDescriptionAttribute; }

    /**
     * Retrieve the {@code model_name} property (displayed as '{@literal Model Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("model_name")
    public String getModelName() { return this.modelName; }

    /**
     * Set the {@code model_name} property (displayed as {@code Model Name}) of the object.
     * @param modelName the value to set
     */
    @JsonProperty("model_name")
    public void setModelName(String modelName) { this.modelName = modelName; }

    /**
     * Retrieve the {@code model_uri} property (displayed as '{@literal Model URI}') of the object.
     * @return {@code String}
     */
    @JsonProperty("model_uri")
    public String getModelUri() { return this.modelUri; }

    /**
     * Set the {@code model_uri} property (displayed as {@code Model URI}) of the object.
     * @param modelUri the value to set
     */
    @JsonProperty("model_uri")
    public void setModelUri(String modelUri) { this.modelUri = modelUri; }

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
     * Retrieve the {@code short_description_attribute} property (displayed as '{@literal Short Description Attribute}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description_attribute")
    public String getShortDescriptionAttribute() { return this.shortDescriptionAttribute; }

    /**
     * Set the {@code short_description_attribute} property (displayed as {@code Short Description Attribute}) of the object.
     * @param shortDescriptionAttribute the value to set
     */
    @JsonProperty("short_description_attribute")
    public void setShortDescriptionAttribute(String shortDescriptionAttribute) { this.shortDescriptionAttribute = shortDescriptionAttribute; }

    /**
     * Retrieve the {@code used_in_application_function} property (displayed as '{@literal Used In Application Function}') of the object.
     * @return {@code ItemList<Applicationfunction>}
     */
    @JsonProperty("used_in_application_function")
    public ItemList<Applicationfunction> getUsedInApplicationFunction() { return this.usedInApplicationFunction; }

    /**
     * Set the {@code used_in_application_function} property (displayed as {@code Used In Application Function}) of the object.
     * @param usedInApplicationFunction the value to set
     */
    @JsonProperty("used_in_application_function")
    public void setUsedInApplicationFunction(ItemList<Applicationfunction> usedInApplicationFunction) { this.usedInApplicationFunction = usedInApplicationFunction; }

}
