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
 * POJO for the {@code actiondescriptor} asset type in IGC, displayed as '{@literal ActionDescriptor}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Actiondescriptor.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("actiondescriptor")
public class Actiondescriptor extends Reference {

    @JsonProperty("description")
    protected String description;

    @JsonProperty("display_name")
    protected String displayName;

    @JsonProperty("has_privilege_contact_role")
    protected ItemList<UserRole> hasPrivilegeContactRole;

    @JsonProperty("has_privilege_system_role")
    protected ItemList<SystemRole> hasPrivilegeSystemRole;

    @JsonProperty("instance_constraint_expression")
    protected String instanceConstraintExpression;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("of_class_descriptor")
    protected Classdescriptor ofClassDescriptor;

    @JsonProperty("operation")
    protected String operation;

    @JsonProperty("parameters")
    protected String parameters;

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("description")
    public String getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(String description) { this.description = description; }

    /**
     * Retrieve the {@code display_name} property (displayed as '{@literal Display Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("display_name")
    public String getDisplayName() { return this.displayName; }

    /**
     * Set the {@code display_name} property (displayed as {@code Display Name}) of the object.
     * @param displayName the value to set
     */
    @JsonProperty("display_name")
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    /**
     * Retrieve the {@code has_privilege_contact_role} property (displayed as '{@literal Has Privilege Contact Role}') of the object.
     * @return {@code ItemList<UserRole>}
     */
    @JsonProperty("has_privilege_contact_role")
    public ItemList<UserRole> getHasPrivilegeContactRole() { return this.hasPrivilegeContactRole; }

    /**
     * Set the {@code has_privilege_contact_role} property (displayed as {@code Has Privilege Contact Role}) of the object.
     * @param hasPrivilegeContactRole the value to set
     */
    @JsonProperty("has_privilege_contact_role")
    public void setHasPrivilegeContactRole(ItemList<UserRole> hasPrivilegeContactRole) { this.hasPrivilegeContactRole = hasPrivilegeContactRole; }

    /**
     * Retrieve the {@code has_privilege_system_role} property (displayed as '{@literal Has Privilege System Role}') of the object.
     * @return {@code ItemList<SystemRole>}
     */
    @JsonProperty("has_privilege_system_role")
    public ItemList<SystemRole> getHasPrivilegeSystemRole() { return this.hasPrivilegeSystemRole; }

    /**
     * Set the {@code has_privilege_system_role} property (displayed as {@code Has Privilege System Role}) of the object.
     * @param hasPrivilegeSystemRole the value to set
     */
    @JsonProperty("has_privilege_system_role")
    public void setHasPrivilegeSystemRole(ItemList<SystemRole> hasPrivilegeSystemRole) { this.hasPrivilegeSystemRole = hasPrivilegeSystemRole; }

    /**
     * Retrieve the {@code instance_constraint_expression} property (displayed as '{@literal Instance Constraint Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("instance_constraint_expression")
    public String getInstanceConstraintExpression() { return this.instanceConstraintExpression; }

    /**
     * Set the {@code instance_constraint_expression} property (displayed as {@code Instance Constraint Expression}) of the object.
     * @param instanceConstraintExpression the value to set
     */
    @JsonProperty("instance_constraint_expression")
    public void setInstanceConstraintExpression(String instanceConstraintExpression) { this.instanceConstraintExpression = instanceConstraintExpression; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code of_class_descriptor} property (displayed as '{@literal Of Class Descriptor}') of the object.
     * @return {@code Classdescriptor}
     */
    @JsonProperty("of_class_descriptor")
    public Classdescriptor getOfClassDescriptor() { return this.ofClassDescriptor; }

    /**
     * Set the {@code of_class_descriptor} property (displayed as {@code Of Class Descriptor}) of the object.
     * @param ofClassDescriptor the value to set
     */
    @JsonProperty("of_class_descriptor")
    public void setOfClassDescriptor(Classdescriptor ofClassDescriptor) { this.ofClassDescriptor = ofClassDescriptor; }

    /**
     * Retrieve the {@code operation} property (displayed as '{@literal Operation}') of the object.
     * @return {@code String}
     */
    @JsonProperty("operation")
    public String getOperation() { return this.operation; }

    /**
     * Set the {@code operation} property (displayed as {@code Operation}) of the object.
     * @param operation the value to set
     */
    @JsonProperty("operation")
    public void setOperation(String operation) { this.operation = operation; }

    /**
     * Retrieve the {@code parameters} property (displayed as '{@literal Parameters}') of the object.
     * @return {@code String}
     */
    @JsonProperty("parameters")
    public String getParameters() { return this.parameters; }

    /**
     * Set the {@code parameters} property (displayed as {@code Parameters}) of the object.
     * @param parameters the value to set
     */
    @JsonProperty("parameters")
    public void setParameters(String parameters) { this.parameters = parameters; }

}
