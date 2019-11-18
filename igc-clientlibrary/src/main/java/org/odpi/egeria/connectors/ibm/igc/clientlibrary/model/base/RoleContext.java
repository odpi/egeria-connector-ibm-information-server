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
 * POJO for the {@code role_context} asset type in IGC, displayed as '{@literal Role Context}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=RoleContext.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("role_context")
public class RoleContext extends Reference {

    @JsonProperty("context_id")
    protected String contextId;

    @JsonProperty("defines_role_assignment")
    protected ItemList<RoleAssignment> definesRoleAssignment;

    /**
     * Retrieve the {@code context_id} property (displayed as '{@literal Context Id}') of the object.
     * @return {@code String}
     */
    @JsonProperty("context_id")
    public String getContextId() { return this.contextId; }

    /**
     * Set the {@code context_id} property (displayed as {@code Context Id}) of the object.
     * @param contextId the value to set
     */
    @JsonProperty("context_id")
    public void setContextId(String contextId) { this.contextId = contextId; }

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

}
