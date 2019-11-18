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
 * POJO for the {@code steward} asset type in IGC, displayed as '{@literal Steward}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Steward.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("steward")
public class Steward extends Reference {

    @JsonProperty("assigned_by_contact_assignment")
    protected ItemList<Associativeobject> assignedByContactAssignment;

    /**
     * Retrieve the {@code assigned_by_contact_assignment} property (displayed as '{@literal Assigned By Contact Assignment}') of the object.
     * @return {@code ItemList<Associativeobject>}
     */
    @JsonProperty("assigned_by_contact_assignment")
    public ItemList<Associativeobject> getAssignedByContactAssignment() { return this.assignedByContactAssignment; }

    /**
     * Set the {@code assigned_by_contact_assignment} property (displayed as {@code Assigned By Contact Assignment}) of the object.
     * @param assignedByContactAssignment the value to set
     */
    @JsonProperty("assigned_by_contact_assignment")
    public void setAssignedByContactAssignment(ItemList<Associativeobject> assignedByContactAssignment) { this.assignedByContactAssignment = assignedByContactAssignment; }

}
