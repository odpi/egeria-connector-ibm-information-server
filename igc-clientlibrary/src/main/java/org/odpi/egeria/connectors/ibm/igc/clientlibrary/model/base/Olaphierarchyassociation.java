/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code olaphierarchyassociation} asset type in IGC, displayed as '{@literal OLAPHierarchyAssociation}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("olaphierarchyassociation")
public class Olaphierarchyassociation extends MainObject {

    @JsonProperty("business_name")
    protected String businessName;

    @JsonProperty("has_child_olap_level")
    protected BiLevel hasChildOlapLevel;

    @JsonProperty("has_parent_olap_level")
    protected BiLevel hasParentOlapLevel;

    @JsonProperty("of_olap_hierarchy_member")
    protected BiHierarchyMember ofOlapHierarchyMember;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("x_of_olap_level")
    protected ItemList<BiLevel> xOfOlapLevel;

    /**
     * Retrieve the {@code business_name} property (displayed as '{@literal Business Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("business_name")
    public String getBusinessName() { return this.businessName; }

    /**
     * Set the {@code business_name} property (displayed as {@code Business Name}) of the object.
     * @param businessName the value to set
     */
    @JsonProperty("business_name")
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    /**
     * Retrieve the {@code has_child_olap_level} property (displayed as '{@literal Has Child OLAP Level}') of the object.
     * @return {@code BiLevel}
     */
    @JsonProperty("has_child_olap_level")
    public BiLevel getHasChildOlapLevel() { return this.hasChildOlapLevel; }

    /**
     * Set the {@code has_child_olap_level} property (displayed as {@code Has Child OLAP Level}) of the object.
     * @param hasChildOlapLevel the value to set
     */
    @JsonProperty("has_child_olap_level")
    public void setHasChildOlapLevel(BiLevel hasChildOlapLevel) { this.hasChildOlapLevel = hasChildOlapLevel; }

    /**
     * Retrieve the {@code has_parent_olap_level} property (displayed as '{@literal Has Parent OLAP Level}') of the object.
     * @return {@code BiLevel}
     */
    @JsonProperty("has_parent_olap_level")
    public BiLevel getHasParentOlapLevel() { return this.hasParentOlapLevel; }

    /**
     * Set the {@code has_parent_olap_level} property (displayed as {@code Has Parent OLAP Level}) of the object.
     * @param hasParentOlapLevel the value to set
     */
    @JsonProperty("has_parent_olap_level")
    public void setHasParentOlapLevel(BiLevel hasParentOlapLevel) { this.hasParentOlapLevel = hasParentOlapLevel; }

    /**
     * Retrieve the {@code of_olap_hierarchy_member} property (displayed as '{@literal Of OLAP Hierarchy Member}') of the object.
     * @return {@code BiHierarchyMember}
     */
    @JsonProperty("of_olap_hierarchy_member")
    public BiHierarchyMember getOfOlapHierarchyMember() { return this.ofOlapHierarchyMember; }

    /**
     * Set the {@code of_olap_hierarchy_member} property (displayed as {@code Of OLAP Hierarchy Member}) of the object.
     * @param ofOlapHierarchyMember the value to set
     */
    @JsonProperty("of_olap_hierarchy_member")
    public void setOfOlapHierarchyMember(BiHierarchyMember ofOlapHierarchyMember) { this.ofOlapHierarchyMember = ofOlapHierarchyMember; }

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

    /**
     * Retrieve the {@code x_of_olap_level} property (displayed as '{@literal X Of OLAP Level}') of the object.
     * @return {@code ItemList<BiLevel>}
     */
    @JsonProperty("x_of_olap_level")
    public ItemList<BiLevel> getXOfOlapLevel() { return this.xOfOlapLevel; }

    /**
     * Set the {@code x_of_olap_level} property (displayed as {@code X Of OLAP Level}) of the object.
     * @param xOfOlapLevel the value to set
     */
    @JsonProperty("x_of_olap_level")
    public void setXOfOlapLevel(ItemList<BiLevel> xOfOlapLevel) { this.xOfOlapLevel = xOfOlapLevel; }

}
