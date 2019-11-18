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
 * POJO for the {@code acl} asset type in IGC, displayed as '{@literal Acl}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Acl.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("acl")
public class Acl extends Reference {

    @JsonProperty("has_acl_entry")
    protected ItemList<Aclentry> hasAclEntry;

    @JsonProperty("of_common_object")
    protected InformationAsset ofCommonObject;

    /**
     * Retrieve the {@code has_acl_entry} property (displayed as '{@literal Has Acl Entry}') of the object.
     * @return {@code ItemList<Aclentry>}
     */
    @JsonProperty("has_acl_entry")
    public ItemList<Aclentry> getHasAclEntry() { return this.hasAclEntry; }

    /**
     * Set the {@code has_acl_entry} property (displayed as {@code Has Acl Entry}) of the object.
     * @param hasAclEntry the value to set
     */
    @JsonProperty("has_acl_entry")
    public void setHasAclEntry(ItemList<Aclentry> hasAclEntry) { this.hasAclEntry = hasAclEntry; }

    /**
     * Retrieve the {@code of_common_object} property (displayed as '{@literal Of Common Object}') of the object.
     * @return {@code InformationAsset}
     */
    @JsonProperty("of_common_object")
    public InformationAsset getOfCommonObject() { return this.ofCommonObject; }

    /**
     * Set the {@code of_common_object} property (displayed as {@code Of Common Object}) of the object.
     * @param ofCommonObject the value to set
     */
    @JsonProperty("of_common_object")
    public void setOfCommonObject(InformationAsset ofCommonObject) { this.ofCommonObject = ofCommonObject; }

}
