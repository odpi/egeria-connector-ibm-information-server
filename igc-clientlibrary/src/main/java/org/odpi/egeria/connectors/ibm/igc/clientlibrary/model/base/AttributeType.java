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
 * POJO for the {@code attribute_type} asset type in IGC, displayed as '{@literal Attribute Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AttributeType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("attribute_type")
public class AttributeType extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    /**
     * No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("contains_attribute_type_fields")
    protected ItemList<AttributeTypeField> containsAttributeTypeFields;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("mdm_model")
    protected MdmModel mdmModel;

    @JsonProperty("mdm_model_for_query")
    protected MdmModel mdmModelForQuery;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("referenced_by_attributes")
    protected ItemList<Attribute> referencedByAttributes;

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
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * No longer applicable from 11.7.0.0 onwards.
     *
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * No longer applicable from 11.7.0.0 onwards.
     *
     * @param blueprintElements the value to set
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code contains_attribute_type_fields} property (displayed as '{@literal Contains Attribute Type Fields}') of the object.
     * @return {@code ItemList<AttributeTypeField>}
     */
    @JsonProperty("contains_attribute_type_fields")
    public ItemList<AttributeTypeField> getContainsAttributeTypeFields() { return this.containsAttributeTypeFields; }

    /**
     * Set the {@code contains_attribute_type_fields} property (displayed as {@code Contains Attribute Type Fields}) of the object.
     * @param containsAttributeTypeFields the value to set
     */
    @JsonProperty("contains_attribute_type_fields")
    public void setContainsAttributeTypeFields(ItemList<AttributeTypeField> containsAttributeTypeFields) { this.containsAttributeTypeFields = containsAttributeTypeFields; }

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
     * Retrieve the {@code mdm_model} property (displayed as '{@literal MDM Model}') of the object.
     * @return {@code MdmModel}
     */
    @JsonProperty("mdm_model")
    public MdmModel getMdmModel() { return this.mdmModel; }

    /**
     * Set the {@code mdm_model} property (displayed as {@code MDM Model}) of the object.
     * @param mdmModel the value to set
     */
    @JsonProperty("mdm_model")
    public void setMdmModel(MdmModel mdmModel) { this.mdmModel = mdmModel; }

    /**
     * Retrieve the {@code mdm_model_for_query} property (displayed as '{@literal MDM Model}') of the object.
     * @return {@code MdmModel}
     */
    @JsonProperty("mdm_model_for_query")
    public MdmModel getMdmModelForQuery() { return this.mdmModelForQuery; }

    /**
     * Set the {@code mdm_model_for_query} property (displayed as {@code MDM Model}) of the object.
     * @param mdmModelForQuery the value to set
     */
    @JsonProperty("mdm_model_for_query")
    public void setMdmModelForQuery(MdmModel mdmModelForQuery) { this.mdmModelForQuery = mdmModelForQuery; }

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
     * Retrieve the {@code referenced_by_attributes} property (displayed as '{@literal Referenced by Attributes}') of the object.
     * @return {@code ItemList<Attribute>}
     */
    @JsonProperty("referenced_by_attributes")
    public ItemList<Attribute> getReferencedByAttributes() { return this.referencedByAttributes; }

    /**
     * Set the {@code referenced_by_attributes} property (displayed as {@code Referenced by Attributes}) of the object.
     * @param referencedByAttributes the value to set
     */
    @JsonProperty("referenced_by_attributes")
    public void setReferencedByAttributes(ItemList<Attribute> referencedByAttributes) { this.referencedByAttributes = referencedByAttributes; }

}
