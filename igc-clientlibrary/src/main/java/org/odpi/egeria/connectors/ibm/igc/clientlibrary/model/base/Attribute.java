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

/**
 * POJO for the {@code attribute} asset type in IGC, displayed as '{@literal Attribute}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Attribute.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("attribute")
public class Attribute extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("attribute_label")
    protected String attributeLabel;

    /**
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("code")
    protected String code;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("member_type")
    protected MemberType memberType;

    @JsonProperty("member_type_for_query")
    protected MemberType memberTypeForQuery;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("references_attribute_types")
    protected AttributeType referencesAttributeTypes;

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
     * Retrieve the {@code attribute_label} property (displayed as '{@literal Attribute Label}') of the object.
     * @return {@code String}
     */
    @JsonProperty("attribute_label")
    public String getAttributeLabel() { return this.attributeLabel; }

    /**
     * Set the {@code attribute_label} property (displayed as {@code Attribute Label}) of the object.
     * @param attributeLabel the value to set
     */
    @JsonProperty("attribute_label")
    public void setAttributeLabel(String attributeLabel) { this.attributeLabel = attributeLabel; }

    /**
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @param blueprintElements the value to set
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code code} property (displayed as '{@literal Code}') of the object.
     * @return {@code String}
     */
    @JsonProperty("code")
    public String getCode() { return this.code; }

    /**
     * Set the {@code code} property (displayed as {@code Code}) of the object.
     * @param code the value to set
     */
    @JsonProperty("code")
    public void setCode(String code) { this.code = code; }

    /**
     * Retrieve the {@code impacted_by} property (displayed as '{@literal Impacted by}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacted_by")
    public ItemList<InformationAsset> getImpactedBy() { return this.impactedBy; }

    /**
     * Set the {@code impacted_by} property (displayed as {@code Impacted by}) of the object.
     * @param impactedBy the value to set
     */
    @JsonProperty("impacted_by")
    public void setImpactedBy(ItemList<InformationAsset> impactedBy) { this.impactedBy = impactedBy; }

    /**
     * Retrieve the {@code impacts_on} property (displayed as '{@literal Impacts on}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacts_on")
    public ItemList<InformationAsset> getImpactsOn() { return this.impactsOn; }

    /**
     * Set the {@code impacts_on} property (displayed as {@code Impacts on}) of the object.
     * @param impactsOn the value to set
     */
    @JsonProperty("impacts_on")
    public void setImpactsOn(ItemList<InformationAsset> impactsOn) { this.impactsOn = impactsOn; }

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
     * Retrieve the {@code member_type} property (displayed as '{@literal Member Type}') of the object.
     * @return {@code MemberType}
     */
    @JsonProperty("member_type")
    public MemberType getMemberType() { return this.memberType; }

    /**
     * Set the {@code member_type} property (displayed as {@code Member Type}) of the object.
     * @param memberType the value to set
     */
    @JsonProperty("member_type")
    public void setMemberType(MemberType memberType) { this.memberType = memberType; }

    /**
     * Retrieve the {@code member_type_for_query} property (displayed as '{@literal Member Type}') of the object.
     * @return {@code MemberType}
     */
    @JsonProperty("member_type_for_query")
    public MemberType getMemberTypeForQuery() { return this.memberTypeForQuery; }

    /**
     * Set the {@code member_type_for_query} property (displayed as {@code Member Type}) of the object.
     * @param memberTypeForQuery the value to set
     */
    @JsonProperty("member_type_for_query")
    public void setMemberTypeForQuery(MemberType memberTypeForQuery) { this.memberTypeForQuery = memberTypeForQuery; }

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
     * Retrieve the {@code references_attribute_types} property (displayed as '{@literal References Attribute Types}') of the object.
     * @return {@code AttributeType}
     */
    @JsonProperty("references_attribute_types")
    public AttributeType getReferencesAttributeTypes() { return this.referencesAttributeTypes; }

    /**
     * Set the {@code references_attribute_types} property (displayed as {@code References Attribute Types}) of the object.
     * @param referencesAttributeTypes the value to set
     */
    @JsonProperty("references_attribute_types")
    public void setReferencesAttributeTypes(AttributeType referencesAttributeTypes) { this.referencesAttributeTypes = referencesAttributeTypes; }

}
