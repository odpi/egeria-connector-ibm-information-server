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
 * POJO for the {@code role_assignment} asset type in IGC, displayed as '{@literal Role Assignment}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=RoleAssignment.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("role_assignment")
public class RoleAssignment extends Reference {

    @JsonProperty("assigns_principal")
    protected ItemList<Steward> assignsPrincipal;

    @JsonProperty("defined_by_system_role")
    protected SystemRole definedBySystemRole;

    @JsonProperty("has_role_context")
    protected RoleContext hasRoleContext;

    @JsonProperty("sequence")
    protected Number sequence;

    /**
     * Retrieve the {@code assigns_principal} property (displayed as '{@literal Assigns Principal}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("assigns_principal")
    public ItemList<Steward> getAssignsPrincipal() { return this.assignsPrincipal; }

    /**
     * Set the {@code assigns_principal} property (displayed as {@code Assigns Principal}) of the object.
     * @param assignsPrincipal the value to set
     */
    @JsonProperty("assigns_principal")
    public void setAssignsPrincipal(ItemList<Steward> assignsPrincipal) { this.assignsPrincipal = assignsPrincipal; }

    /**
     * Retrieve the {@code defined_by_system_role} property (displayed as '{@literal Defined By System Role}') of the object.
     * @return {@code SystemRole}
     */
    @JsonProperty("defined_by_system_role")
    public SystemRole getDefinedBySystemRole() { return this.definedBySystemRole; }

    /**
     * Set the {@code defined_by_system_role} property (displayed as {@code Defined By System Role}) of the object.
     * @param definedBySystemRole the value to set
     */
    @JsonProperty("defined_by_system_role")
    public void setDefinedBySystemRole(SystemRole definedBySystemRole) { this.definedBySystemRole = definedBySystemRole; }

    /**
     * Retrieve the {@code has_role_context} property (displayed as '{@literal Has Role Context}') of the object.
     * @return {@code RoleContext}
     */
    @JsonProperty("has_role_context")
    public RoleContext getHasRoleContext() { return this.hasRoleContext; }

    /**
     * Set the {@code has_role_context} property (displayed as {@code Has Role Context}) of the object.
     * @param hasRoleContext the value to set
     */
    @JsonProperty("has_role_context")
    public void setHasRoleContext(RoleContext hasRoleContext) { this.hasRoleContext = hasRoleContext; }

    /**
     * Retrieve the {@code sequence} property (displayed as '{@literal Sequence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sequence")
    public Number getSequence() { return this.sequence; }

    /**
     * Set the {@code sequence} property (displayed as {@code Sequence}) of the object.
     * @param sequence the value to set
     */
    @JsonProperty("sequence")
    public void setSequence(Number sequence) { this.sequence = sequence; }

}
