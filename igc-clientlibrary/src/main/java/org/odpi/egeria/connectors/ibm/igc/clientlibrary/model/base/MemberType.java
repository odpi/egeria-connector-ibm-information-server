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

    @JsonProperty("contains_attributes")
    protected ItemList<Attribute> containsAttributes;

    @JsonProperty("contains_composite_views")
    protected ItemList<CompositeView> containsCompositeViews;

    @JsonProperty("contains_entity_types")
    protected ItemList<EntityType> containsEntityTypes;

    @JsonProperty("mdm_model")
    protected MdmModel mdmModel;

    @JsonProperty("mdm_model_for_query")
    protected MdmModel mdmModelForQuery;

    @JsonProperty("member_type_label")
    protected String memberTypeLabel;

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

}
