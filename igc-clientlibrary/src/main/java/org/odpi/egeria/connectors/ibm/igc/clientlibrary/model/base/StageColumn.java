/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.List;

/**
 * POJO for the {@code stage_column} asset type in IGC, displayed as '{@literal Stage Column}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=StageColumn.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DsStageColumn.class, name = "ds_stage_column"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("stage_column")
public class StageColumn extends DataItem {

    @JsonProperty("constraints")
    protected ItemList<JobConstraint> constraints;

    @JsonProperty("expression")
    protected List<String> expression;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("key")
    protected Boolean key;

    @JsonProperty("link")
    protected Link link;

    @JsonProperty("next_stage_columns")
    protected ItemList<DataItem> nextStageColumns;

    @JsonProperty("previous_stage_columns")
    protected ItemList<DataItem> previousStageColumns;

    @JsonProperty("reads_from_(design)")
    protected ItemList<InformationAsset> readsFromDesign;

    @JsonProperty("reads_from_(operational)")
    protected ItemList<InformationAsset> readsFromOperational;

    @JsonProperty("reads_from_(static)")
    protected ItemList<InformationAsset> readsFromStatic;

    @JsonProperty("reads_from_(user_defined)")
    protected ItemList<InformationAsset> readsFromUserDefined;

    @JsonProperty("writes_to_(design)")
    protected ItemList<InformationAsset> writesToDesign;

    @JsonProperty("writes_to_(operational)")
    protected ItemList<InformationAsset> writesToOperational;

    @JsonProperty("writes_to_(static)")
    protected ItemList<InformationAsset> writesToStatic;

    @JsonProperty("writes_to_(user_defined)")
    protected ItemList<InformationAsset> writesToUserDefined;

    /**
     * Retrieve the {@code constraints} property (displayed as '{@literal Constraints}') of the object.
     * @return {@code ItemList<JobConstraint>}
     */
    @JsonProperty("constraints")
    public ItemList<JobConstraint> getConstraints() { return this.constraints; }

    /**
     * Set the {@code constraints} property (displayed as {@code Constraints}) of the object.
     * @param constraints the value to set
     */
    @JsonProperty("constraints")
    public void setConstraints(ItemList<JobConstraint> constraints) { this.constraints = constraints; }

    /**
     * Retrieve the {@code expression} property (displayed as '{@literal Expression}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("expression")
    public List<String> getExpression() { return this.expression; }

    /**
     * Set the {@code expression} property (displayed as {@code Expression}) of the object.
     * @param expression the value to set
     */
    @JsonProperty("expression")
    public void setExpression(List<String> expression) { this.expression = expression; }

    /**
     * Retrieve the {@code impacted_by} property (displayed as '{@literal Impacted by}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacted_by")
    public ItemList<InformationAsset> getImpactedBy() { return this.impactedBy; }

    /**
     * Set the {@code impacted_by} property (displayed as {@code Impacted by}) of the object.
     * @param impactedBy the value to set
     */
    @JsonProperty("impacted_by")
    public void setImpactedBy(ItemList<InformationAsset> impactedBy) { this.impactedBy = impactedBy; }

    /**
     * Retrieve the {@code impacts_on} property (displayed as '{@literal Impacts on}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacts_on")
    public ItemList<InformationAsset> getImpactsOn() { return this.impactsOn; }

    /**
     * Set the {@code impacts_on} property (displayed as {@code Impacts on}) of the object.
     * @param impactsOn the value to set
     */
    @JsonProperty("impacts_on")
    public void setImpactsOn(ItemList<InformationAsset> impactsOn) { this.impactsOn = impactsOn; }

    /**
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

    /**
     * Retrieve the {@code key} property (displayed as '{@literal Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("key")
    public Boolean getKey() { return this.key; }

    /**
     * Set the {@code key} property (displayed as {@code Key}) of the object.
     * @param key the value to set
     */
    @JsonProperty("key")
    public void setKey(Boolean key) { this.key = key; }

    /**
     * Retrieve the {@code link} property (displayed as '{@literal Link}') of the object.
     * @return {@code Link}
     */
    @JsonProperty("link")
    public Link getLink() { return this.link; }

    /**
     * Set the {@code link} property (displayed as {@code Link}) of the object.
     * @param link the value to set
     */
    @JsonProperty("link")
    public void setLink(Link link) { this.link = link; }

    /**
     * Retrieve the {@code next_stage_columns} property (displayed as '{@literal Next Stage Columns}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("next_stage_columns")
    public ItemList<DataItem> getNextStageColumns() { return this.nextStageColumns; }

    /**
     * Set the {@code next_stage_columns} property (displayed as {@code Next Stage Columns}) of the object.
     * @param nextStageColumns the value to set
     */
    @JsonProperty("next_stage_columns")
    public void setNextStageColumns(ItemList<DataItem> nextStageColumns) { this.nextStageColumns = nextStageColumns; }

    /**
     * Retrieve the {@code previous_stage_columns} property (displayed as '{@literal Previous Stage Columns}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("previous_stage_columns")
    public ItemList<DataItem> getPreviousStageColumns() { return this.previousStageColumns; }

    /**
     * Set the {@code previous_stage_columns} property (displayed as {@code Previous Stage Columns}) of the object.
     * @param previousStageColumns the value to set
     */
    @JsonProperty("previous_stage_columns")
    public void setPreviousStageColumns(ItemList<DataItem> previousStageColumns) { this.previousStageColumns = previousStageColumns; }

    /**
     * Retrieve the {@code reads_from_(design)} property (displayed as '{@literal Reads from (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(design)")
    public ItemList<InformationAsset> getReadsFromDesign() { return this.readsFromDesign; }

    /**
     * Set the {@code reads_from_(design)} property (displayed as {@code Reads from (Design)}) of the object.
     * @param readsFromDesign the value to set
     */
    @JsonProperty("reads_from_(design)")
    public void setReadsFromDesign(ItemList<InformationAsset> readsFromDesign) { this.readsFromDesign = readsFromDesign; }

    /**
     * Retrieve the {@code reads_from_(operational)} property (displayed as '{@literal Reads from (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(operational)")
    public ItemList<InformationAsset> getReadsFromOperational() { return this.readsFromOperational; }

    /**
     * Set the {@code reads_from_(operational)} property (displayed as {@code Reads from (Operational)}) of the object.
     * @param readsFromOperational the value to set
     */
    @JsonProperty("reads_from_(operational)")
    public void setReadsFromOperational(ItemList<InformationAsset> readsFromOperational) { this.readsFromOperational = readsFromOperational; }

    /**
     * Retrieve the {@code reads_from_(static)} property (displayed as '{@literal Reads from (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(static)")
    public ItemList<InformationAsset> getReadsFromStatic() { return this.readsFromStatic; }

    /**
     * Set the {@code reads_from_(static)} property (displayed as {@code Reads from (Static)}) of the object.
     * @param readsFromStatic the value to set
     */
    @JsonProperty("reads_from_(static)")
    public void setReadsFromStatic(ItemList<InformationAsset> readsFromStatic) { this.readsFromStatic = readsFromStatic; }

    /**
     * Retrieve the {@code reads_from_(user_defined)} property (displayed as '{@literal Reads from (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(user_defined)")
    public ItemList<InformationAsset> getReadsFromUserDefined() { return this.readsFromUserDefined; }

    /**
     * Set the {@code reads_from_(user_defined)} property (displayed as {@code Reads from (User-Defined)}) of the object.
     * @param readsFromUserDefined the value to set
     */
    @JsonProperty("reads_from_(user_defined)")
    public void setReadsFromUserDefined(ItemList<InformationAsset> readsFromUserDefined) { this.readsFromUserDefined = readsFromUserDefined; }

    /**
     * Retrieve the {@code writes_to_(design)} property (displayed as '{@literal Writes to (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(design)")
    public ItemList<InformationAsset> getWritesToDesign() { return this.writesToDesign; }

    /**
     * Set the {@code writes_to_(design)} property (displayed as {@code Writes to (Design)}) of the object.
     * @param writesToDesign the value to set
     */
    @JsonProperty("writes_to_(design)")
    public void setWritesToDesign(ItemList<InformationAsset> writesToDesign) { this.writesToDesign = writesToDesign; }

    /**
     * Retrieve the {@code writes_to_(operational)} property (displayed as '{@literal Writes to (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(operational)")
    public ItemList<InformationAsset> getWritesToOperational() { return this.writesToOperational; }

    /**
     * Set the {@code writes_to_(operational)} property (displayed as {@code Writes to (Operational)}) of the object.
     * @param writesToOperational the value to set
     */
    @JsonProperty("writes_to_(operational)")
    public void setWritesToOperational(ItemList<InformationAsset> writesToOperational) { this.writesToOperational = writesToOperational; }

    /**
     * Retrieve the {@code writes_to_(static)} property (displayed as '{@literal Writes to (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(static)")
    public ItemList<InformationAsset> getWritesToStatic() { return this.writesToStatic; }

    /**
     * Set the {@code writes_to_(static)} property (displayed as {@code Writes to (Static)}) of the object.
     * @param writesToStatic the value to set
     */
    @JsonProperty("writes_to_(static)")
    public void setWritesToStatic(ItemList<InformationAsset> writesToStatic) { this.writesToStatic = writesToStatic; }

    /**
     * Retrieve the {@code writes_to_(user_defined)} property (displayed as '{@literal Writes to (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(user_defined)")
    public ItemList<InformationAsset> getWritesToUserDefined() { return this.writesToUserDefined; }

    /**
     * Set the {@code writes_to_(user_defined)} property (displayed as {@code Writes to (User-Defined)}) of the object.
     * @param writesToUserDefined the value to set
     */
    @JsonProperty("writes_to_(user_defined)")
    public void setWritesToUserDefined(ItemList<InformationAsset> writesToUserDefined) { this.writesToUserDefined = writesToUserDefined; }

}
