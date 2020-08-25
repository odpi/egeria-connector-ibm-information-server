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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.interfaces.ColumnLevelLineage;

import java.util.List;

/**
 * POJO for the {@code stage_variable} asset type in IGC, displayed as '{@literal Stage Variable}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=StageVariable.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("stage_variable")
public class StageVariable extends DataItem implements ColumnLevelLineage {

    @JsonProperty("expression")
    protected List<String> expression;

    @JsonProperty("next_stage_columns")
    protected ItemList<DataItem> nextStageColumns;

    @JsonProperty("previous_stage_columns")
    protected ItemList<DataItem> previousStageColumns;

    @JsonProperty("stage")
    protected Stage stage;

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
     * Retrieve the {@code next_stage_columns} property (displayed as '{@literal Next Stage Columns or Variables}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @Override
    @JsonProperty("next_stage_columns")
    public ItemList<DataItem> getNextStageColumns() { return this.nextStageColumns; }

    /**
     * Set the {@code next_stage_columns} property (displayed as {@code Next Stage Columns or Variables}) of the object.
     * @param nextStageColumns the value to set
     */
    @Override
    @JsonProperty("next_stage_columns")
    public void setNextStageColumns(ItemList<DataItem> nextStageColumns) { this.nextStageColumns = nextStageColumns; }

    /**
     * Retrieve the {@code previous_stage_columns} property (displayed as '{@literal Previous Stage Columns or Variables}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @Override
    @JsonProperty("previous_stage_columns")
    public ItemList<DataItem> getPreviousStageColumns() { return this.previousStageColumns; }

    /**
     * Set the {@code previous_stage_columns} property (displayed as {@code Previous Stage Columns or Variables}) of the object.
     * @param previousStageColumns the value to set
     */
    @Override
    @JsonProperty("previous_stage_columns")
    public void setPreviousStageColumns(ItemList<DataItem> previousStageColumns) { this.previousStageColumns = previousStageColumns; }

    /**
     * Retrieve the {@code stage} property (displayed as '{@literal Stage}') of the object.
     * @return {@code Stage}
     */
    @JsonProperty("stage")
    public Stage getStage() { return this.stage; }

    /**
     * Set the {@code stage} property (displayed as {@code Stage}) of the object.
     * @param stage the value to set
     */
    @JsonProperty("stage")
    public void setStage(Stage stage) { this.stage = stage; }

}
