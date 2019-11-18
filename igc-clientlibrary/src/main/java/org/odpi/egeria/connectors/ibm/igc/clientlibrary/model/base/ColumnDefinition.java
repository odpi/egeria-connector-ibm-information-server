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
 * POJO for the {@code column_definition} asset type in IGC, displayed as '{@literal Column Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ColumnDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("column_definition")
public class ColumnDefinition extends DataField {

    @JsonProperty("apt_field_properties")
    protected String aptFieldProperties;

    @JsonProperty("key")
    protected Boolean key;

    @JsonProperty("table_definition")
    protected ItemList<TableDefinition> tableDefinition;

    @JsonProperty("used_by_stage_columns")
    protected ItemList<DataItem> usedByStageColumns;

    /**
     * Retrieve the {@code apt_field_properties} property (displayed as '{@literal APT Field Properties}') of the object.
     * @return {@code String}
     */
    @JsonProperty("apt_field_properties")
    public String getAptFieldProperties() { return this.aptFieldProperties; }

    /**
     * Set the {@code apt_field_properties} property (displayed as {@code APT Field Properties}) of the object.
     * @param aptFieldProperties the value to set
     */
    @JsonProperty("apt_field_properties")
    public void setAptFieldProperties(String aptFieldProperties) { this.aptFieldProperties = aptFieldProperties; }

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
