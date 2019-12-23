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

/**
 * POJO for the {@code physical_object_attribute} asset type in IGC, displayed as '{@literal Physical Object Attribute}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=PhysicalObjectAttribute.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("physical_object_attribute")
public class PhysicalObjectAttribute extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("maps_database_fields")
    protected DatabaseColumn mapsDatabaseFields;

    @JsonProperty("physical_object")
    protected PhysicalObject physicalObject;

    @JsonProperty("physical_object_for_query")
    protected PhysicalObject physicalObjectForQuery;

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @param aliasBusinessName the value to set
     */
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code maps_database_fields} property (displayed as '{@literal Maps Database Fields}') of the object.
     * @return {@code DatabaseColumn}
     */
    @JsonProperty("maps_database_fields")
    public DatabaseColumn getMapsDatabaseFields() { return this.mapsDatabaseFields; }

    /**
     * Set the {@code maps_database_fields} property (displayed as {@code Maps Database Fields}) of the object.
     * @param mapsDatabaseFields the value to set
     */
    @JsonProperty("maps_database_fields")
    public void setMapsDatabaseFields(DatabaseColumn mapsDatabaseFields) { this.mapsDatabaseFields = mapsDatabaseFields; }

    /**
     * Retrieve the {@code physical_object} property (displayed as '{@literal Physical Object}') of the object.
     * @return {@code PhysicalObject}
     */
    @JsonProperty("physical_object")
    public PhysicalObject getPhysicalObject() { return this.physicalObject; }

    /**
     * Set the {@code physical_object} property (displayed as {@code Physical Object}) of the object.
     * @param physicalObject the value to set
     */
    @JsonProperty("physical_object")
    public void setPhysicalObject(PhysicalObject physicalObject) { this.physicalObject = physicalObject; }

    /**
     * Retrieve the {@code physical_object_for_query} property (displayed as '{@literal Physical Object}') of the object.
     * @return {@code PhysicalObject}
     */
    @JsonProperty("physical_object_for_query")
    public PhysicalObject getPhysicalObjectForQuery() { return this.physicalObjectForQuery; }

    /**
     * Set the {@code physical_object_for_query} property (displayed as {@code Physical Object}) of the object.
     * @param physicalObjectForQuery the value to set
     */
    @JsonProperty("physical_object_for_query")
    public void setPhysicalObjectForQuery(PhysicalObject physicalObjectForQuery) { this.physicalObjectForQuery = physicalObjectForQuery; }

}
