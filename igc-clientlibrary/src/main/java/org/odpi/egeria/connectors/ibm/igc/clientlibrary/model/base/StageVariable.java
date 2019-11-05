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
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code stage_variable} asset type in IGC, displayed as '{@literal Stage Variable}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("stage_variable")
public class StageVariable extends InformationAsset {

    @JsonProperty("expression")
    protected List<String> expression;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("minimum_length")
    protected Number minimumLength;

    @JsonProperty("next_stage_columns")
    protected ItemList<DataItem> nextStageColumns;

    /**
     * Valid values are:
     * <ul>
     *   <li>CHAR (displayed in the UI as 'CHAR')</li>
     *   <li>VARCHAR (displayed in the UI as 'VARCHAR')</li>
     *   <li>LONGVARCHAR (displayed in the UI as 'LONGVARCHAR')</li>
     *   <li>WCHAR (displayed in the UI as 'WCHAR')</li>
     *   <li>WVARCHAR (displayed in the UI as 'WVARCHAR')</li>
     *   <li>WLONGVARCHAR (displayed in the UI as 'WLONGVARCHAR')</li>
     *   <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *   <li>NUMERIC (displayed in the UI as 'NUMERIC')</li>
     *   <li>SMALLINT (displayed in the UI as 'SMALLINT')</li>
     *   <li>INTEGER (displayed in the UI as 'INTEGER')</li>
     *   <li>REAL (displayed in the UI as 'REAL')</li>
     *   <li>FLOAT (displayed in the UI as 'FLOAT')</li>
     *   <li>DOUBLE (displayed in the UI as 'DOUBLE')</li>
     *   <li>BIT (displayed in the UI as 'BIT')</li>
     *   <li>TINYINT (displayed in the UI as 'TINYINT')</li>
     *   <li>BIGINT (displayed in the UI as 'BIGINT')</li>
     *   <li>BINARY (displayed in the UI as 'BINARY')</li>
     *   <li>VARBINARY (displayed in the UI as 'VARBINARY')</li>
     *   <li>LONGVARBINARY (displayed in the UI as 'LONGVARBINARY')</li>
     *   <li>DATE (displayed in the UI as 'DATE')</li>
     *   <li>TIME (displayed in the UI as 'TIME')</li>
     *   <li>TIMESTAMP (displayed in the UI as 'TIMESTAMP')</li>
     *   <li>GUID (displayed in the UI as 'GUID')</li>
     *   <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     * </ul>
     */
    @JsonProperty("odbc_type")
    protected String odbcType;

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
     * Retrieve the {@code length} property (displayed as '{@literal Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("length")
    public Number getLength() { return this.length; }

    /**
     * Set the {@code length} property (displayed as {@code Length}) of the object.
     * @param length the value to set
     */
    @JsonProperty("length")
    public void setLength(Number length) { this.length = length; }

    /**
     * Retrieve the {@code minimum_length} property (displayed as '{@literal Minimum Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("minimum_length")
    public Number getMinimumLength() { return this.minimumLength; }

    /**
     * Set the {@code minimum_length} property (displayed as {@code Minimum Length}) of the object.
     * @param minimumLength the value to set
     */
    @JsonProperty("minimum_length")
    public void setMinimumLength(Number minimumLength) { this.minimumLength = minimumLength; }

    /**
     * Retrieve the {@code next_stage_columns} property (displayed as '{@literal Next Stage Columns or Variables}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("next_stage_columns")
    public ItemList<DataItem> getNextStageColumns() { return this.nextStageColumns; }

    /**
     * Set the {@code next_stage_columns} property (displayed as {@code Next Stage Columns or Variables}) of the object.
     * @param nextStageColumns the value to set
     */
    @JsonProperty("next_stage_columns")
    public void setNextStageColumns(ItemList<DataItem> nextStageColumns) { this.nextStageColumns = nextStageColumns; }

    /**
     * Retrieve the {@code odbc_type} property (displayed as '{@literal SQL Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("odbc_type")
    public String getOdbcType() { return this.odbcType; }

    /**
     * Set the {@code odbc_type} property (displayed as {@code SQL Type}) of the object.
     * @param odbcType the value to set
     */
    @JsonProperty("odbc_type")
    public void setOdbcType(String odbcType) { this.odbcType = odbcType; }

    /**
     * Retrieve the {@code previous_stage_columns} property (displayed as '{@literal Previous Stage Columns or Variables}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("previous_stage_columns")
    public ItemList<DataItem> getPreviousStageColumns() { return this.previousStageColumns; }

    /**
     * Set the {@code previous_stage_columns} property (displayed as {@code Previous Stage Columns or Variables}) of the object.
     * @param previousStageColumns the value to set
     */
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
