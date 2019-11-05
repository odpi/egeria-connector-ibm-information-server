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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code bi_hierarchy_member} asset type in IGC, displayed as '{@literal BI Hierarchy Member}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_hierarchy_member")
public class BiHierarchyMember extends Reference {

    @JsonProperty("bi_level")
    protected BiLevel biLevel;

    @JsonProperty("child_level")
    protected ItemList<BiLevel> childLevel;

    @JsonProperty("sequence")
    protected Number sequence;

    /**
     * Valid values are:
     * <ul>
     *   <li>ASCENDING (displayed in the UI as 'ASCENDING')</li>
     *   <li>DESCENDING (displayed in the UI as 'DESCENDING')</li>
     *   <li>NONE (displayed in the UI as 'NONE')</li>
     * </ul>
     */
    @JsonProperty("sorting_order")
    protected String sortingOrder;

    /**
     * Retrieve the {@code bi_level} property (displayed as '{@literal BI Level}') of the object.
     * @return {@code BiLevel}
     */
    @JsonProperty("bi_level")
    public BiLevel getBiLevel() { return this.biLevel; }

    /**
     * Set the {@code bi_level} property (displayed as {@code BI Level}) of the object.
     * @param biLevel the value to set
     */
    @JsonProperty("bi_level")
    public void setBiLevel(BiLevel biLevel) { this.biLevel = biLevel; }

    /**
     * Retrieve the {@code child_level} property (displayed as '{@literal Child Level}') of the object.
     * @return {@code ItemList<BiLevel>}
     */
    @JsonProperty("child_level")
    public ItemList<BiLevel> getChildLevel() { return this.childLevel; }

    /**
     * Set the {@code child_level} property (displayed as {@code Child Level}) of the object.
     * @param childLevel the value to set
     */
    @JsonProperty("child_level")
    public void setChildLevel(ItemList<BiLevel> childLevel) { this.childLevel = childLevel; }

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
     * Retrieve the {@code sorting_order} property (displayed as '{@literal Sorting Order}') of the object.
     * @return {@code String}
     */
    @JsonProperty("sorting_order")
    public String getSortingOrder() { return this.sortingOrder; }

    /**
     * Set the {@code sorting_order} property (displayed as {@code Sorting Order}) of the object.
     * @param sortingOrder the value to set
     */
    @JsonProperty("sorting_order")
    public void setSortingOrder(String sortingOrder) { this.sortingOrder = sortingOrder; }

}
