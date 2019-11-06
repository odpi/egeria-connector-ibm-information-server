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
import java.util.List;

/**
 * POJO for the {@code foreign_key} asset type in IGC, displayed as '{@literal Foreign Key}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ForeignKey.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("foreign_key")
public class ForeignKey extends Reference {

    @JsonProperty("database_table")
    protected ItemList<Datagroup> databaseTable;

    @JsonProperty("included_database_columns")
    protected ItemList<DataItem> includedDatabaseColumns;

    /**
     * Valid values are:
     * <ul>
     *   <li>ASCENDING (displayed in the UI as 'ASCENDING')</li>
     *   <li>DESCENDING (displayed in the UI as 'DESCENDING')</li>
     *   <li>NONE (displayed in the UI as 'NONE')</li>
     * </ul>
     */
    @JsonProperty("sorting")
    protected List<String> sorting;

    /**
     * Retrieve the {@code database_table} property (displayed as '{@literal Database Table}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("database_table")
    public ItemList<Datagroup> getDatabaseTable() { return this.databaseTable; }

    /**
     * Set the {@code database_table} property (displayed as {@code Database Table}) of the object.
     * @param databaseTable the value to set
     */
    @JsonProperty("database_table")
    public void setDatabaseTable(ItemList<Datagroup> databaseTable) { this.databaseTable = databaseTable; }

    /**
     * Retrieve the {@code included_database_columns} property (displayed as '{@literal Included Database Columns}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("included_database_columns")
    public ItemList<DataItem> getIncludedDatabaseColumns() { return this.includedDatabaseColumns; }

    /**
     * Set the {@code included_database_columns} property (displayed as {@code Included Database Columns}) of the object.
     * @param includedDatabaseColumns the value to set
     */
    @JsonProperty("included_database_columns")
    public void setIncludedDatabaseColumns(ItemList<DataItem> includedDatabaseColumns) { this.includedDatabaseColumns = includedDatabaseColumns; }

    /**
     * Retrieve the {@code sorting} property (displayed as '{@literal Sorting}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("sorting")
    public List<String> getSorting() { return this.sorting; }

    /**
     * Set the {@code sorting} property (displayed as {@code Sorting}) of the object.
     * @param sorting the value to set
     */
    @JsonProperty("sorting")
    public void setSorting(List<String> sorting) { this.sorting = sorting; }

}
