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

/**
 * POJO for the {@code candidate_key} asset type in IGC, displayed as '{@literal Candidate Key}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=CandidateKey.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("candidate_key")
public class CandidateKey extends Reference {

    @JsonProperty("database_table_view")
    protected Datagroup databaseTableView;

    @JsonProperty("defined_on_database_columns")
    protected ItemList<DatabaseColumn> definedOnDatabaseColumns;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("primary_key")
    protected Boolean primaryKey;

    @JsonProperty("referenced_by_foreign_keys")
    protected ItemList<ForeignKey> referencedByForeignKeys;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Retrieve the {@code database_table_view} property (displayed as '{@literal Database Table View}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("database_table_view")
    public Datagroup getDatabaseTableView() { return this.databaseTableView; }

    /**
     * Set the {@code database_table_view} property (displayed as {@code Database Table View}) of the object.
     * @param databaseTableView the value to set
     */
    @JsonProperty("database_table_view")
    public void setDatabaseTableView(Datagroup databaseTableView) { this.databaseTableView = databaseTableView; }

    /**
     * Retrieve the {@code defined_on_database_columns} property (displayed as '{@literal Defined on Database Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("defined_on_database_columns")
    public ItemList<DatabaseColumn> getDefinedOnDatabaseColumns() { return this.definedOnDatabaseColumns; }

    /**
     * Set the {@code defined_on_database_columns} property (displayed as {@code Defined on Database Columns}) of the object.
     * @param definedOnDatabaseColumns the value to set
     */
    @JsonProperty("defined_on_database_columns")
    public void setDefinedOnDatabaseColumns(ItemList<DatabaseColumn> definedOnDatabaseColumns) { this.definedOnDatabaseColumns = definedOnDatabaseColumns; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

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
     * Retrieve the {@code primary_key} property (displayed as '{@literal Primary Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("primary_key")
    public Boolean getPrimaryKey() { return this.primaryKey; }

    /**
     * Set the {@code primary_key} property (displayed as {@code Primary Key}) of the object.
     * @param primaryKey the value to set
     */
    @JsonProperty("primary_key")
    public void setPrimaryKey(Boolean primaryKey) { this.primaryKey = primaryKey; }

    /**
     * Retrieve the {@code referenced_by_foreign_keys} property (displayed as '{@literal Referenced by Foreign Keys}') of the object.
     * @return {@code ItemList<ForeignKey>}
     */
    @JsonProperty("referenced_by_foreign_keys")
    public ItemList<ForeignKey> getReferencedByForeignKeys() { return this.referencedByForeignKeys; }

    /**
     * Set the {@code referenced_by_foreign_keys} property (displayed as {@code Referenced by Foreign Keys}) of the object.
     * @param referencedByForeignKeys the value to set
     */
    @JsonProperty("referenced_by_foreign_keys")
    public void setReferencedByForeignKeys(ItemList<ForeignKey> referencedByForeignKeys) { this.referencedByForeignKeys = referencedByForeignKeys; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

}
