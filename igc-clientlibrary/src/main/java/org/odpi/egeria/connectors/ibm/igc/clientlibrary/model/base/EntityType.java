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
 * POJO for the {@code entity_type} asset type in IGC, displayed as '{@literal Entity Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=EntityType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("entity_type")
public class EntityType extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("entity_type_label")
    protected String entityTypeLabel;

    @JsonProperty("member_type")
    protected MemberType memberType;

    @JsonProperty("member_type_for_query")
    protected MemberType memberTypeForQuery;

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
     * Retrieve the {@code entity_type_label} property (displayed as '{@literal Entity Type Label}') of the object.
     * @return {@code String}
     */
    @JsonProperty("entity_type_label")
    public String getEntityTypeLabel() { return this.entityTypeLabel; }

    /**
     * Set the {@code entity_type_label} property (displayed as {@code Entity Type Label}) of the object.
     * @param entityTypeLabel the value to set
     */
    @JsonProperty("entity_type_label")
    public void setEntityTypeLabel(String entityTypeLabel) { this.entityTypeLabel = entityTypeLabel; }

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

}
