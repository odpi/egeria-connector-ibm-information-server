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
 * POJO for the {@code system_role} asset type in IGC, displayed as '{@literal System Role}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=SystemRole.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("system_role")
public class SystemRole extends MainObject {

    @JsonProperty("defines_role_assignment")
    protected ItemList<RoleAssignment> definesRoleAssignment;

    @JsonProperty("of_acl_entry")
    protected ItemList<Aclentry> ofAclEntry;

    @JsonProperty("of_principal")
    protected ItemList<Steward> ofPrincipal;

    /**
     * Valid values are:
     * <ul>
     *   <li>ASB (displayed in the UI as 'ASB')</li>
     *   <li>DATASTAGE (displayed in the UI as 'DATASTAGE')</li>
     *   <li>SORCERER (displayed in the UI as 'SORCERER')</li>
     *   <li>SOA (displayed in the UI as 'SOA')</li>
     *   <li>ENTERPRISE_MANAGER (displayed in the UI as 'ENTERPRISE_MANAGER')</li>
     *   <li>OTHER (displayed in the UI as 'OTHER')</li>
     *   <li>GLOSSARY (displayed in the UI as 'GLOSSARY')</li>
     *   <li>OMD (displayed in the UI as 'OMD')</li>
     *   <li>WISD (displayed in the UI as 'WISD')</li>
     * </ul>
     */
    @JsonProperty("product")
    protected String product;

    @JsonProperty("role_id")
    protected String roleId;

    /**
     * Valid values are:
     * <ul>
     *   <li>SUITE (displayed in the UI as 'SUITE')</li>
     *   <li>PRODUCT (displayed in the UI as 'PRODUCT')</li>
     *   <li>PROJECT (displayed in the UI as 'PROJECT')</li>
     * </ul>
     */
    @JsonProperty("role_type")
    protected String roleType;

    /**
     * Retrieve the {@code defines_role_assignment} property (displayed as '{@literal Defines Role Assignment}') of the object.
     * @return {@code ItemList<RoleAssignment>}
     */
    @JsonProperty("defines_role_assignment")
    public ItemList<RoleAssignment> getDefinesRoleAssignment() { return this.definesRoleAssignment; }

    /**
     * Set the {@code defines_role_assignment} property (displayed as {@code Defines Role Assignment}) of the object.
     * @param definesRoleAssignment the value to set
     */
    @JsonProperty("defines_role_assignment")
    public void setDefinesRoleAssignment(ItemList<RoleAssignment> definesRoleAssignment) { this.definesRoleAssignment = definesRoleAssignment; }

    /**
     * Retrieve the {@code of_acl_entry} property (displayed as '{@literal Of Acl Entry}') of the object.
     * @return {@code ItemList<Aclentry>}
     */
    @JsonProperty("of_acl_entry")
    public ItemList<Aclentry> getOfAclEntry() { return this.ofAclEntry; }

    /**
     * Set the {@code of_acl_entry} property (displayed as {@code Of Acl Entry}) of the object.
     * @param ofAclEntry the value to set
     */
    @JsonProperty("of_acl_entry")
    public void setOfAclEntry(ItemList<Aclentry> ofAclEntry) { this.ofAclEntry = ofAclEntry; }

    /**
     * Retrieve the {@code of_principal} property (displayed as '{@literal Of Principal}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("of_principal")
    public ItemList<Steward> getOfPrincipal() { return this.ofPrincipal; }

    /**
     * Set the {@code of_principal} property (displayed as {@code Of Principal}) of the object.
     * @param ofPrincipal the value to set
     */
    @JsonProperty("of_principal")
    public void setOfPrincipal(ItemList<Steward> ofPrincipal) { this.ofPrincipal = ofPrincipal; }

    /**
     * Retrieve the {@code product} property (displayed as '{@literal Product}') of the object.
     * @return {@code String}
     */
    @JsonProperty("product")
    public String getProduct() { return this.product; }

    /**
     * Set the {@code product} property (displayed as {@code Product}) of the object.
     * @param product the value to set
     */
    @JsonProperty("product")
    public void setProduct(String product) { this.product = product; }

    /**
     * Retrieve the {@code role_id} property (displayed as '{@literal Role Id}') of the object.
     * @return {@code String}
     */
    @JsonProperty("role_id")
    public String getRoleId() { return this.roleId; }

    /**
     * Set the {@code role_id} property (displayed as {@code Role Id}) of the object.
     * @param roleId the value to set
     */
    @JsonProperty("role_id")
    public void setRoleId(String roleId) { this.roleId = roleId; }

    /**
     * Retrieve the {@code role_type} property (displayed as '{@literal Role Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("role_type")
    public String getRoleType() { return this.roleType; }

    /**
     * Set the {@code role_type} property (displayed as {@code Role Type}) of the object.
     * @param roleType the value to set
     */
    @JsonProperty("role_type")
    public void setRoleType(String roleType) { this.roleType = roleType; }

}
