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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code attribute_type_field} asset type in IGC, displayed as '{@literal Attribute Type Field}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AttributeTypeField.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("attribute_type_field")
public class AttributeTypeField extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("attribute_type")
    protected AttributeType attributeType;

    @JsonProperty("attribute_type_for_query")
    protected AttributeType attributeTypeForQuery;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("required")
    protected Boolean required;

    /**
     * Valid values are:
     * <ul>
     *   <li>CHAR (displayed in the UI as 'CHAR')</li>
     *   <li>DATETIME (displayed in the UI as 'DATETIME')</li>
     *   <li>SQWORD (displayed in the UI as 'SQWORD')</li>
     *   <li>UDWORD (displayed in the UI as 'UDWORD')</li>
     *   <li>UWORD (displayed in the UI as 'UWORD')</li>
     *   <li>VARCHAR (displayed in the UI as 'VARCHAR')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

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
     * Retrieve the {@code attribute_type} property (displayed as '{@literal Attribute Type}') of the object.
     * @return {@code AttributeType}
     */
    @JsonProperty("attribute_type")
    public AttributeType getAttributeType() { return this.attributeType; }

    /**
     * Set the {@code attribute_type} property (displayed as {@code Attribute Type}) of the object.
     * @param attributeType the value to set
     */
    @JsonProperty("attribute_type")
    public void setAttributeType(AttributeType attributeType) { this.attributeType = attributeType; }

    /**
     * Retrieve the {@code attribute_type_for_query} property (displayed as '{@literal Attribute Type}') of the object.
     * @return {@code AttributeType}
     */
    @JsonProperty("attribute_type_for_query")
    public AttributeType getAttributeTypeForQuery() { return this.attributeTypeForQuery; }

    /**
     * Set the {@code attribute_type_for_query} property (displayed as {@code Attribute Type}) of the object.
     * @param attributeTypeForQuery the value to set
     */
    @JsonProperty("attribute_type_for_query")
    public void setAttributeTypeForQuery(AttributeType attributeTypeForQuery) { this.attributeTypeForQuery = attributeTypeForQuery; }

    /**
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @param blueprintElements the value to set
     */
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

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
     * Retrieve the {@code required} property (displayed as '{@literal Required}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("required")
    public Boolean getRequired() { return this.required; }

    /**
     * Set the {@code required} property (displayed as {@code Required}) of the object.
     * @param required the value to set
     */
    @JsonProperty("required")
    public void setRequired(Boolean required) { this.required = required; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

}
