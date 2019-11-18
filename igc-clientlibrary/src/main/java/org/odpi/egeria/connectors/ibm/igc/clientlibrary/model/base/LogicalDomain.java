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
 * POJO for the {@code logical_domain} asset type in IGC, displayed as '{@literal Logical Domain}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=LogicalDomain.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("logical_domain")
public class LogicalDomain extends Reference {

    @JsonProperty("child_domains")
    protected ItemList<LogicalDomain> childDomains;

    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("default_value")
    protected String defaultValue;

    @JsonProperty("entity_attributes")
    protected ItemList<EntityAttribute> entityAttributes;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("logical_data_model")
    protected LogicalDataModel logicalDataModel;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("native_type")
    protected String nativeType;

    @JsonProperty("null_value")
    protected String nullValue;

    @JsonProperty("parent_domain")
    protected LogicalDomain parentDomain;

    @JsonProperty("scale")
    protected Number scale;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Retrieve the {@code child_domains} property (displayed as '{@literal Child Domains}') of the object.
     * @return {@code ItemList<LogicalDomain>}
     */
    @JsonProperty("child_domains")
    public ItemList<LogicalDomain> getChildDomains() { return this.childDomains; }

    /**
     * Set the {@code child_domains} property (displayed as {@code Child Domains}) of the object.
     * @param childDomains the value to set
     */
    @JsonProperty("child_domains")
    public void setChildDomains(ItemList<LogicalDomain> childDomains) { this.childDomains = childDomains; }

    /**
     * Retrieve the {@code data_type} property (displayed as '{@literal Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_type")
    public String getDataType() { return this.dataType; }

    /**
     * Set the {@code data_type} property (displayed as {@code Data Type}) of the object.
     * @param dataType the value to set
     */
    @JsonProperty("data_type")
    public void setDataType(String dataType) { this.dataType = dataType; }

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
     * Retrieve the {@code entity_attributes} property (displayed as '{@literal Entity Attributes}') of the object.
     * @return {@code ItemList<EntityAttribute>}
     */
    @JsonProperty("entity_attributes")
    public ItemList<EntityAttribute> getEntityAttributes() { return this.entityAttributes; }

    /**
     * Set the {@code entity_attributes} property (displayed as {@code Entity Attributes}) of the object.
     * @param entityAttributes the value to set
     */
    @JsonProperty("entity_attributes")
    public void setEntityAttributes(ItemList<EntityAttribute> entityAttributes) { this.entityAttributes = entityAttributes; }

    /**
     * Retrieve the {@code length} property (displayed as '{@literal Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("length")
    public Number getLength() { return this.length; }

    /**
     * Set the {@code length} property (displayed as {@code Length}) of the object.
     * @param length the value to set
     */
    @JsonProperty("length")
    public void setLength(Number length) { this.length = length; }

    /**
     * Retrieve the {@code logical_data_model} property (displayed as '{@literal Logical Data Model}') of the object.
     * @return {@code LogicalDataModel}
     */
    @JsonProperty("logical_data_model")
    public LogicalDataModel getLogicalDataModel() { return this.logicalDataModel; }

    /**
     * Set the {@code logical_data_model} property (displayed as {@code Logical Data Model}) of the object.
     * @param logicalDataModel the value to set
     */
    @JsonProperty("logical_data_model")
    public void setLogicalDataModel(LogicalDataModel logicalDataModel) { this.logicalDataModel = logicalDataModel; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

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
     * Retrieve the {@code native_id} property (displayed as '{@literal Native ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_id")
    public String getNativeId() { return this.nativeId; }

    /**
     * Set the {@code native_id} property (displayed as {@code Native ID}) of the object.
     * @param nativeId the value to set
     */
    @JsonProperty("native_id")
    public void setNativeId(String nativeId) { this.nativeId = nativeId; }

    /**
     * Retrieve the {@code native_type} property (displayed as '{@literal Native Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_type")
    public String getNativeType() { return this.nativeType; }

    /**
     * Set the {@code native_type} property (displayed as {@code Native Type}) of the object.
     * @param nativeType the value to set
     */
    @JsonProperty("native_type")
    public void setNativeType(String nativeType) { this.nativeType = nativeType; }

    /**
     * Retrieve the {@code null_value} property (displayed as '{@literal Null Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("null_value")
    public String getNullValue() { return this.nullValue; }

    /**
     * Set the {@code null_value} property (displayed as {@code Null Value}) of the object.
     * @param nullValue the value to set
     */
    @JsonProperty("null_value")
    public void setNullValue(String nullValue) { this.nullValue = nullValue; }

    /**
     * Retrieve the {@code parent_domain} property (displayed as '{@literal Parent Domain}') of the object.
     * @return {@code LogicalDomain}
     */
    @JsonProperty("parent_domain")
    public LogicalDomain getParentDomain() { return this.parentDomain; }

    /**
     * Set the {@code parent_domain} property (displayed as {@code Parent Domain}) of the object.
     * @param parentDomain the value to set
     */
    @JsonProperty("parent_domain")
    public void setParentDomain(LogicalDomain parentDomain) { this.parentDomain = parentDomain; }

    /**
     * Retrieve the {@code scale} property (displayed as '{@literal Scale}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("scale")
    public Number getScale() { return this.scale; }

    /**
     * Set the {@code scale} property (displayed as {@code Scale}) of the object.
     * @param scale the value to set
     */
    @JsonProperty("scale")
    public void setScale(Number scale) { this.scale = scale; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

}
