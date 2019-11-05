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

/**
 * POJO for the {@code column_definition} asset type in IGC, displayed as '{@literal Column Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("column_definition")
public class ColumnDefinition extends InformationAsset {

    @JsonProperty("allow_null_values")
    protected String allowNullValues;

    @JsonProperty("data_item_definition")
    protected DataItem dataItemDefinition;

    @JsonProperty("fraction")
    protected Number fraction;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("key")
    protected Boolean key;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("table_definition")
    protected ItemList<TableDefinition> tableDefinition;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("used_by_stage_columns")
    protected ItemList<DataItem> usedByStageColumns;

    /**
     * Retrieve the {@code allow_null_values} property (displayed as '{@literal Allow Null Values}') of the object.
     * @return {@code String}
     */
    @JsonProperty("allow_null_values")
    public String getAllowNullValues() { return this.allowNullValues; }

    /**
     * Set the {@code allow_null_values} property (displayed as {@code Allow Null Values}) of the object.
     * @param allowNullValues the value to set
     */
    @JsonProperty("allow_null_values")
    public void setAllowNullValues(String allowNullValues) { this.allowNullValues = allowNullValues; }

    /**
     * Retrieve the {@code data_item_definition} property (displayed as '{@literal Data Item Definition}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("data_item_definition")
    public DataItem getDataItemDefinition() { return this.dataItemDefinition; }

    /**
     * Set the {@code data_item_definition} property (displayed as {@code Data Item Definition}) of the object.
     * @param dataItemDefinition the value to set
     */
    @JsonProperty("data_item_definition")
    public void setDataItemDefinition(DataItem dataItemDefinition) { this.dataItemDefinition = dataItemDefinition; }

    /**
     * Retrieve the {@code fraction} property (displayed as '{@literal Fraction}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("fraction")
    public Number getFraction() { return this.fraction; }

    /**
     * Set the {@code fraction} property (displayed as {@code Fraction}) of the object.
     * @param fraction the value to set
     */
    @JsonProperty("fraction")
    public void setFraction(Number fraction) { this.fraction = fraction; }

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
     * Retrieve the {@code table_definition} property (displayed as '{@literal Table Definition}') of the object.
     * @return {@code ItemList<TableDefinition>}
     */
    @JsonProperty("table_definition")
    public ItemList<TableDefinition> getTableDefinition() { return this.tableDefinition; }

    /**
     * Set the {@code table_definition} property (displayed as {@code Table Definition}) of the object.
     * @param tableDefinition the value to set
     */
    @JsonProperty("table_definition")
    public void setTableDefinition(ItemList<TableDefinition> tableDefinition) { this.tableDefinition = tableDefinition; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

    /**
     * Retrieve the {@code used_by_stage_columns} property (displayed as '{@literal Used by Stage Columns}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("used_by_stage_columns")
    public ItemList<DataItem> getUsedByStageColumns() { return this.usedByStageColumns; }

    /**
     * Set the {@code used_by_stage_columns} property (displayed as {@code Used by Stage Columns}) of the object.
     * @param usedByStageColumns the value to set
     */
    @JsonProperty("used_by_stage_columns")
    public void setUsedByStageColumns(ItemList<DataItem> usedByStageColumns) { this.usedByStageColumns = usedByStageColumns; }

}
