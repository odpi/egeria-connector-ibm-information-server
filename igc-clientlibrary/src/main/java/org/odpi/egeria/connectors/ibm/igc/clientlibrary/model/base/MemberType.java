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
 * POJO for the {@code member_type} asset type in IGC, displayed as '{@literal Member Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MemberType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("member_type")
public class MemberType extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    /**
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("contains_attributes")
    protected ItemList<Attribute> containsAttributes;

    @JsonProperty("contains_composite_views")
    protected ItemList<CompositeView> containsCompositeViews;

    @JsonProperty("contains_entity_types")
    protected ItemList<EntityType> containsEntityTypes;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("mdm_model")
    protected MdmModel mdmModel;

    @JsonProperty("mdm_model_for_query")
    protected MdmModel mdmModelForQuery;

    @JsonProperty("member_type_label")
    protected String memberTypeLabel;

    @JsonProperty("native_id")
    protected String nativeId;

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
     * Retrieve the {@code contains_attributes} property (displayed as '{@literal Contains Attributes}') of the object.
     * @return {@code ItemList<Attribute>}
     */
    @JsonProperty("contains_attributes")
    public ItemList<Attribute> getContainsAttributes() { return this.containsAttributes; }

    /**
     * Set the {@code contains_attributes} property (displayed as {@code Contains Attributes}) of the object.
     * @param containsAttributes the value to set
     */
    @JsonProperty("contains_attributes")
    public void setContainsAttributes(ItemList<Attribute> containsAttributes) { this.containsAttributes = containsAttributes; }

    /**
     * Retrieve the {@code contains_composite_views} property (displayed as '{@literal Contains Composite Views}') of the object.
     * @return {@code ItemList<CompositeView>}
     */
    @JsonProperty("contains_composite_views")
    public ItemList<CompositeView> getContainsCompositeViews() { return this.containsCompositeViews; }

    /**
     * Set the {@code contains_composite_views} property (displayed as {@code Contains Composite Views}) of the object.
     * @param containsCompositeViews the value to set
     */
    @JsonProperty("contains_composite_views")
    public void setContainsCompositeViews(ItemList<CompositeView> containsCompositeViews) { this.containsCompositeViews = containsCompositeViews; }

    /**
     * Retrieve the {@code contains_entity_types} property (displayed as '{@literal Contains Entity Types}') of the object.
     * @return {@code ItemList<EntityType>}
     */
    @JsonProperty("contains_entity_types")
    public ItemList<EntityType> getContainsEntityTypes() { return this.containsEntityTypes; }

    /**
     * Set the {@code contains_entity_types} property (displayed as {@code Contains Entity Types}) of the object.
     * @param containsEntityTypes the value to set
     */
    @JsonProperty("contains_entity_types")
    public void setContainsEntityTypes(ItemList<EntityType> containsEntityTypes) { this.containsEntityTypes = containsEntityTypes; }

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
     * Retrieve the {@code member_type_label} property (displayed as '{@literal Member Type Label}') of the object.
     * @return {@code String}
     */
    @JsonProperty("member_type_label")
    public String getMemberTypeLabel() { return this.memberTypeLabel; }

    /**
     * Set the {@code member_type_label} property (displayed as {@code Member Type Label}) of the object.
     * @param memberTypeLabel the value to set
     */
    @JsonProperty("member_type_label")
    public void setMemberTypeLabel(String memberTypeLabel) { this.memberTypeLabel = memberTypeLabel; }

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

}
