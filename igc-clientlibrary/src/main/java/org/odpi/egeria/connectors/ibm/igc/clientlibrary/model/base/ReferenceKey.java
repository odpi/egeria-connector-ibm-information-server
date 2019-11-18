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
 * POJO for the {@code reference_key} asset type in IGC, displayed as '{@literal Reference Key}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ReferenceKey.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("reference_key")
public class ReferenceKey extends Reference {

    @JsonProperty("logical_entity")
    protected LogicalEntity logicalEntity;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("physical_name")
    protected String physicalName;

    @JsonProperty("referenced_by_attribute")
    protected ItemList<EntityAttribute> referencedByAttribute;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Retrieve the {@code logical_entity} property (displayed as '{@literal Logical Entity}') of the object.
     * @return {@code LogicalEntity}
     */
    @JsonProperty("logical_entity")
    public LogicalEntity getLogicalEntity() { return this.logicalEntity; }

    /**
     * Set the {@code logical_entity} property (displayed as {@code Logical Entity}) of the object.
     * @param logicalEntity the value to set
     */
    @JsonProperty("logical_entity")
    public void setLogicalEntity(LogicalEntity logicalEntity) { this.logicalEntity = logicalEntity; }

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
     * Retrieve the {@code physical_name} property (displayed as '{@literal Physical Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("physical_name")
    public String getPhysicalName() { return this.physicalName; }

    /**
     * Set the {@code physical_name} property (displayed as {@code Physical Name}) of the object.
     * @param physicalName the value to set
     */
    @JsonProperty("physical_name")
    public void setPhysicalName(String physicalName) { this.physicalName = physicalName; }

    /**
     * Retrieve the {@code referenced_by_attribute} property (displayed as '{@literal Parent Entity Attribute}') of the object.
     * @return {@code ItemList<EntityAttribute>}
     */
    @JsonProperty("referenced_by_attribute")
    public ItemList<EntityAttribute> getReferencedByAttribute() { return this.referencedByAttribute; }

    /**
     * Set the {@code referenced_by_attribute} property (displayed as {@code Parent Entity Attribute}) of the object.
     * @param referencedByAttribute the value to set
     */
    @JsonProperty("referenced_by_attribute")
    public void setReferencedByAttribute(ItemList<EntityAttribute> referencedByAttribute) { this.referencedByAttribute = referencedByAttribute; }

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
