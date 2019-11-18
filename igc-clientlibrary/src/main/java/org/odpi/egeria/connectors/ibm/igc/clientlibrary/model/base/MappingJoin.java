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
 * POJO for the {@code mapping_join} asset type in IGC, displayed as '{@literal Mapping Join}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MappingJoin.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("mapping_join")
public class MappingJoin extends Reference {

    @JsonProperty("description")
    protected List<String> description;

    @JsonProperty("foreign_key_columns")
    protected ItemList<DatabaseColumn> foreignKeyColumns;

    @JsonProperty("name")
    protected List<String> name;

    @JsonProperty("primary_key_columns")
    protected ItemList<DatabaseColumn> primaryKeyColumns;

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("description")
    public List<String> getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(List<String> description) { this.description = description; }

    /**
     * Retrieve the {@code foreign_key_columns} property (displayed as '{@literal Foreign Key Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("foreign_key_columns")
    public ItemList<DatabaseColumn> getForeignKeyColumns() { return this.foreignKeyColumns; }

    /**
     * Set the {@code foreign_key_columns} property (displayed as {@code Foreign Key Columns}) of the object.
     * @param foreignKeyColumns the value to set
     */
    @JsonProperty("foreign_key_columns")
    public void setForeignKeyColumns(ItemList<DatabaseColumn> foreignKeyColumns) { this.foreignKeyColumns = foreignKeyColumns; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("name")
    public List<String> getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(List<String> name) { this.name = name; }

    /**
     * Retrieve the {@code primary_key_columns} property (displayed as '{@literal Primary Key Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("primary_key_columns")
    public ItemList<DatabaseColumn> getPrimaryKeyColumns() { return this.primaryKeyColumns; }

    /**
     * Set the {@code primary_key_columns} property (displayed as {@code Primary Key Columns}) of the object.
     * @param primaryKeyColumns the value to set
     */
    @JsonProperty("primary_key_columns")
    public void setPrimaryKeyColumns(ItemList<DatabaseColumn> primaryKeyColumns) { this.primaryKeyColumns = primaryKeyColumns; }

}
