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
 * POJO for the {@code user_role} asset type in IGC, displayed as '{@literal User Role}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=UserRole.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("user_role")
public class UserRole extends Reference {

    @JsonProperty("name")
    protected String name;

    @JsonProperty("system_role")
    protected Boolean systemRole;

    @JsonProperty("users")
    protected ItemList<Steward> users;

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Role}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Role}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code system_role} property (displayed as '{@literal System Role}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("system_role")
    public Boolean getSystemRole() { return this.systemRole; }

    /**
     * Set the {@code system_role} property (displayed as {@code System Role}) of the object.
     * @param systemRole the value to set
     */
    @JsonProperty("system_role")
    public void setSystemRole(Boolean systemRole) { this.systemRole = systemRole; }

    /**
     * Retrieve the {@code users} property (displayed as '{@literal Users}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("users")
    public ItemList<Steward> getUsers() { return this.users; }

    /**
     * Set the {@code users} property (displayed as {@code Users}) of the object.
     * @param users the value to set
     */
    @JsonProperty("users")
    public void setUsers(ItemList<Steward> users) { this.users = users; }

}
