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
 * POJO for the {@code dsdesign_view} asset type in IGC, displayed as '{@literal Design View}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsdesign_view")
public class DsdesignView extends MainObject {

    @JsonProperty("container_view_sizing")
    protected String containerViewSizing;

    @JsonProperty("grid_lines")
    protected Number gridLines;

    @JsonProperty("has_canvas_annotation")
    protected ItemList<MainObject> hasCanvasAnnotation;

    @JsonProperty("input_pins")
    protected String inputPins;

    @JsonProperty("internal_id")
    protected String internalId;

    @JsonProperty("is_top_level")
    protected Boolean isTopLevel;

    @JsonProperty("next_id")
    protected Number nextId;

    @JsonProperty("next_stage_id")
    protected Number nextStageId;

    @JsonProperty("of_job")
    protected Dsjob ofJob;

    @JsonProperty("output_pins")
    protected String outputPins;

    @JsonProperty("snap_to_grid")
    protected Number snapToGrid;

    @JsonProperty("stage_list")
    protected String stageList;

    @JsonProperty("stage_types")
    protected String stageTypes;

    @JsonProperty("stage_x_pos")
    protected String stageXPos;

    @JsonProperty("stage_x_size")
    protected String stageXSize;

    @JsonProperty("stage_y_pos")
    protected String stageYPos;

    @JsonProperty("stage_y_size")
    protected String stageYSize;

    @JsonProperty("zoom_value")
    protected Number zoomValue;

    /**
     * Retrieve the {@code container_view_sizing} property (displayed as '{@literal Container View Sizing}') of the object.
     * @return {@code String}
     */
    @JsonProperty("container_view_sizing")
    public String getContainerViewSizing() { return this.containerViewSizing; }

    /**
     * Set the {@code container_view_sizing} property (displayed as {@code Container View Sizing}) of the object.
     * @param containerViewSizing the value to set
     */
    @JsonProperty("container_view_sizing")
    public void setContainerViewSizing(String containerViewSizing) { this.containerViewSizing = containerViewSizing; }

    /**
     * Retrieve the {@code grid_lines} property (displayed as '{@literal Grid Lines}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("grid_lines")
    public Number getGridLines() { return this.gridLines; }

    /**
     * Set the {@code grid_lines} property (displayed as {@code Grid Lines}) of the object.
     * @param gridLines the value to set
     */
    @JsonProperty("grid_lines")
    public void setGridLines(Number gridLines) { this.gridLines = gridLines; }

    /**
     * Retrieve the {@code has_canvas_annotation} property (displayed as '{@literal Has Canvas Annotation}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("has_canvas_annotation")
    public ItemList<MainObject> getHasCanvasAnnotation() { return this.hasCanvasAnnotation; }

    /**
     * Set the {@code has_canvas_annotation} property (displayed as {@code Has Canvas Annotation}) of the object.
     * @param hasCanvasAnnotation the value to set
     */
    @JsonProperty("has_canvas_annotation")
    public void setHasCanvasAnnotation(ItemList<MainObject> hasCanvasAnnotation) { this.hasCanvasAnnotation = hasCanvasAnnotation; }

    /**
     * Retrieve the {@code input_pins} property (displayed as '{@literal Input Pins}') of the object.
     * @return {@code String}
     */
    @JsonProperty("input_pins")
    public String getInputPins() { return this.inputPins; }

    /**
     * Set the {@code input_pins} property (displayed as {@code Input Pins}) of the object.
     * @param inputPins the value to set
     */
    @JsonProperty("input_pins")
    public void setInputPins(String inputPins) { this.inputPins = inputPins; }

    /**
     * Retrieve the {@code internal_id} property (displayed as '{@literal Internal ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("internal_id")
    public String getInternalId() { return this.internalId; }

    /**
     * Set the {@code internal_id} property (displayed as {@code Internal ID}) of the object.
     * @param internalId the value to set
     */
    @JsonProperty("internal_id")
    public void setInternalId(String internalId) { this.internalId = internalId; }

    /**
     * Retrieve the {@code is_top_level} property (displayed as '{@literal Is Top Level}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_top_level")
    public Boolean getIsTopLevel() { return this.isTopLevel; }

    /**
     * Set the {@code is_top_level} property (displayed as {@code Is Top Level}) of the object.
     * @param isTopLevel the value to set
     */
    @JsonProperty("is_top_level")
    public void setIsTopLevel(Boolean isTopLevel) { this.isTopLevel = isTopLevel; }

    /**
     * Retrieve the {@code next_id} property (displayed as '{@literal Next ID}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("next_id")
    public Number getNextId() { return this.nextId; }

    /**
     * Set the {@code next_id} property (displayed as {@code Next ID}) of the object.
     * @param nextId the value to set
     */
    @JsonProperty("next_id")
    public void setNextId(Number nextId) { this.nextId = nextId; }

    /**
     * Retrieve the {@code next_stage_id} property (displayed as '{@literal Next Stage ID}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("next_stage_id")
    public Number getNextStageId() { return this.nextStageId; }

    /**
     * Set the {@code next_stage_id} property (displayed as {@code Next Stage ID}) of the object.
     * @param nextStageId the value to set
     */
    @JsonProperty("next_stage_id")
    public void setNextStageId(Number nextStageId) { this.nextStageId = nextStageId; }

    /**
     * Retrieve the {@code of_job} property (displayed as '{@literal Of Job}') of the object.
     * @return {@code Dsjob}
     */
    @JsonProperty("of_job")
    public Dsjob getOfJob() { return this.ofJob; }

    /**
     * Set the {@code of_job} property (displayed as {@code Of Job}) of the object.
     * @param ofJob the value to set
     */
    @JsonProperty("of_job")
    public void setOfJob(Dsjob ofJob) { this.ofJob = ofJob; }

    /**
     * Retrieve the {@code output_pins} property (displayed as '{@literal Output Pins}') of the object.
     * @return {@code String}
     */
    @JsonProperty("output_pins")
    public String getOutputPins() { return this.outputPins; }

    /**
     * Set the {@code output_pins} property (displayed as {@code Output Pins}) of the object.
     * @param outputPins the value to set
     */
    @JsonProperty("output_pins")
    public void setOutputPins(String outputPins) { this.outputPins = outputPins; }

    /**
     * Retrieve the {@code snap_to_grid} property (displayed as '{@literal Snap To Grid}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("snap_to_grid")
    public Number getSnapToGrid() { return this.snapToGrid; }

    /**
     * Set the {@code snap_to_grid} property (displayed as {@code Snap To Grid}) of the object.
     * @param snapToGrid the value to set
     */
    @JsonProperty("snap_to_grid")
    public void setSnapToGrid(Number snapToGrid) { this.snapToGrid = snapToGrid; }

    /**
     * Retrieve the {@code stage_list} property (displayed as '{@literal Stage List}') of the object.
     * @return {@code String}
     */
    @JsonProperty("stage_list")
    public String getStageList() { return this.stageList; }

    /**
     * Set the {@code stage_list} property (displayed as {@code Stage List}) of the object.
     * @param stageList the value to set
     */
    @JsonProperty("stage_list")
    public void setStageList(String stageList) { this.stageList = stageList; }

    /**
     * Retrieve the {@code stage_types} property (displayed as '{@literal Stage Types}') of the object.
     * @return {@code String}
     */
    @JsonProperty("stage_types")
    public String getStageTypes() { return this.stageTypes; }

    /**
     * Set the {@code stage_types} property (displayed as {@code Stage Types}) of the object.
     * @param stageTypes the value to set
     */
    @JsonProperty("stage_types")
    public void setStageTypes(String stageTypes) { this.stageTypes = stageTypes; }

    /**
     * Retrieve the {@code stage_x_pos} property (displayed as '{@literal Stage X Pos}') of the object.
     * @return {@code String}
     */
    @JsonProperty("stage_x_pos")
    public String getStageXPos() { return this.stageXPos; }

    /**
     * Set the {@code stage_x_pos} property (displayed as {@code Stage X Pos}) of the object.
     * @param stageXPos the value to set
     */
    @JsonProperty("stage_x_pos")
    public void setStageXPos(String stageXPos) { this.stageXPos = stageXPos; }

    /**
     * Retrieve the {@code stage_x_size} property (displayed as '{@literal Stage X Size}') of the object.
     * @return {@code String}
     */
    @JsonProperty("stage_x_size")
    public String getStageXSize() { return this.stageXSize; }

    /**
     * Set the {@code stage_x_size} property (displayed as {@code Stage X Size}) of the object.
     * @param stageXSize the value to set
     */
    @JsonProperty("stage_x_size")
    public void setStageXSize(String stageXSize) { this.stageXSize = stageXSize; }

    /**
     * Retrieve the {@code stage_y_pos} property (displayed as '{@literal Stage Y Pos}') of the object.
     * @return {@code String}
     */
    @JsonProperty("stage_y_pos")
    public String getStageYPos() { return this.stageYPos; }

    /**
     * Set the {@code stage_y_pos} property (displayed as {@code Stage Y Pos}) of the object.
     * @param stageYPos the value to set
     */
    @JsonProperty("stage_y_pos")
    public void setStageYPos(String stageYPos) { this.stageYPos = stageYPos; }

    /**
     * Retrieve the {@code stage_y_size} property (displayed as '{@literal Stage Y Size}') of the object.
     * @return {@code String}
     */
    @JsonProperty("stage_y_size")
    public String getStageYSize() { return this.stageYSize; }

    /**
     * Set the {@code stage_y_size} property (displayed as {@code Stage Y Size}) of the object.
     * @param stageYSize the value to set
     */
    @JsonProperty("stage_y_size")
    public void setStageYSize(String stageYSize) { this.stageYSize = stageYSize; }

    /**
     * Retrieve the {@code zoom_value} property (displayed as '{@literal Zoom Value}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("zoom_value")
    public Number getZoomValue() { return this.zoomValue; }

    /**
     * Set the {@code zoom_value} property (displayed as {@code Zoom Value}) of the object.
     * @param zoomValue the value to set
     */
    @JsonProperty("zoom_value")
    public void setZoomValue(Number zoomValue) { this.zoomValue = zoomValue; }

}
