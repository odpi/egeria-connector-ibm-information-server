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
 * POJO for the {@code bi_level} asset type in IGC, displayed as '{@literal BI Level}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiLevel.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_level")
public class BiLevel extends MainObject {

    @JsonProperty("bi_collection")
    protected BiCollection biCollection;

    @JsonProperty("bi_hierarchy")
    protected ItemList<BiHierarchy> biHierarchy;

    @JsonProperty("children_levels")
    protected ItemList<BiLevel> childrenLevels;

    @JsonProperty("depth")
    protected Number depth;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("parent_bi_level")
    protected ItemList<BiLevel> parentBiLevel;

    /**
     * Retrieve the {@code bi_collection} property (displayed as '{@literal BI Collection}') of the object.
     * @return {@code BiCollection}
     */
    @JsonProperty("bi_collection")
    public BiCollection getBiCollection() { return this.biCollection; }

    /**
     * Set the {@code bi_collection} property (displayed as {@code BI Collection}) of the object.
     * @param biCollection the value to set
     */
    @JsonProperty("bi_collection")
    public void setBiCollection(BiCollection biCollection) { this.biCollection = biCollection; }

    /**
     * Retrieve the {@code bi_hierarchy} property (displayed as '{@literal BI Hierarchy}') of the object.
     * @return {@code ItemList<BiHierarchy>}
     */
    @JsonProperty("bi_hierarchy")
    public ItemList<BiHierarchy> getBiHierarchy() { return this.biHierarchy; }

    /**
     * Set the {@code bi_hierarchy} property (displayed as {@code BI Hierarchy}) of the object.
     * @param biHierarchy the value to set
     */
    @JsonProperty("bi_hierarchy")
    public void setBiHierarchy(ItemList<BiHierarchy> biHierarchy) { this.biHierarchy = biHierarchy; }

    /**
     * Retrieve the {@code children_levels} property (displayed as '{@literal Children Levels}') of the object.
     * @return {@code ItemList<BiLevel>}
     */
    @JsonProperty("children_levels")
    public ItemList<BiLevel> getChildrenLevels() { return this.childrenLevels; }

    /**
     * Set the {@code children_levels} property (displayed as {@code Children Levels}) of the object.
     * @param childrenLevels the value to set
     */
    @JsonProperty("children_levels")
    public void setChildrenLevels(ItemList<BiLevel> childrenLevels) { this.childrenLevels = childrenLevels; }

    /**
     * Retrieve the {@code depth} property (displayed as '{@literal Depth}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("depth")
    public Number getDepth() { return this.depth; }

    /**
     * Set the {@code depth} property (displayed as {@code Depth}) of the object.
     * @param depth the value to set
     */
    @JsonProperty("depth")
    public void setDepth(Number depth) { this.depth = depth; }

    /**
     * Retrieve the {@code native_id} property (displayed as '{@literal Native ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_id")
    public String getNativeId() { return this.nativeId; }

    /**
     * Set the {@code native_id} property (displayed as {@code Native ID}) of the object.
     * @param nativeId the value to set
     */
    @JsonProperty("native_id")
    public void setNativeId(String nativeId) { this.nativeId = nativeId; }

    /**
     * Retrieve the {@code parent_bi_level} property (displayed as '{@literal Parent BI Level}') of the object.
     * @return {@code ItemList<BiLevel>}
     */
    @JsonProperty("parent_bi_level")
    public ItemList<BiLevel> getParentBiLevel() { return this.parentBiLevel; }

    /**
     * Set the {@code parent_bi_level} property (displayed as {@code Parent BI Level}) of the object.
     * @param parentBiLevel the value to set
     */
    @JsonProperty("parent_bi_level")
    public void setParentBiLevel(ItemList<BiLevel> parentBiLevel) { this.parentBiLevel = parentBiLevel; }

}
