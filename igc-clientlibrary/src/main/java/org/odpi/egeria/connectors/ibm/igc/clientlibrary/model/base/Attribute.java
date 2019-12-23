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

    @JsonProperty("code")
    protected String code;

    @JsonProperty("member_type")
    protected MemberType memberType;

    @JsonProperty("member_type_for_query")
    protected MemberType memberTypeForQuery;

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
