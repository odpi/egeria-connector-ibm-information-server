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
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code design_column} asset type in IGC, displayed as '{@literal Design Column}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DesignColumn.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("design_column")
public class DesignColumn extends InformationAsset {

    @JsonProperty("allows_null_values")
    protected Boolean allowsNullValues;

    /**
     * Valid values are:
     * <ul>
     *   <li>INT8 (displayed in the UI as 'INT8')</li>
     *   <li>INT16 (displayed in the UI as 'INT16')</li>
     *   <li>INT32 (displayed in the UI as 'INT32')</li>
     *   <li>INT64 (displayed in the UI as 'INT64')</li>
     *   <li>SFLOAT (displayed in the UI as 'SFLOAT')</li>
     *   <li>DFLOAT (displayed in the UI as 'DFLOAT')</li>
     *   <li>QFLOAT (displayed in the UI as 'QFLOAT')</li>
     *   <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *   <li>STRING (displayed in the UI as 'STRING')</li>
     *   <li>BINARY (displayed in the UI as 'BINARY')</li>
     *   <li>BOOLEAN (displayed in the UI as 'BOOLEAN')</li>
     *   <li>DATE (displayed in the UI as 'DATE')</li>
     *   <li>TIME (displayed in the UI as 'TIME')</li>
     *   <li>DATETIME (displayed in the UI as 'DATETIME')</li>
     *   <li>DURATION (displayed in the UI as 'DURATION')</li>
     *   <li>CHOICE (displayed in the UI as 'CHOICE')</li>
     *   <li>ORDERED_GROUP (displayed in the UI as 'ORDERED_GROUP')</li>
     *   <li>UNORDERED_GROUP (displayed in the UI as 'UNORDERED_GROUP')</li>
     *   <li>GUID (displayed in the UI as 'GUID')</li>
     *   <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     *   <li>JSON (displayed in the UI as 'JSON')</li>
     *   <li>XML (displayed in the UI as 'XML')</li>
     * </ul>
     */
    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("design_table_or_view")
    protected Datagroup designTableOrView;

    @JsonProperty("fraction")
    protected Number fraction;

    @JsonProperty("implemented_by_data_fields")
    protected ItemList<DataItem> implementedByDataFields;

    @JsonProperty("implemented_by_database_columns")
    protected ItemList<DatabaseColumn> implementedByDatabaseColumns;

    @JsonProperty("implements_entity_attributes")
    protected ItemList<EntityAttribute> implementsEntityAttributes;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("included_in_design_foreign_key")
    protected ItemList<DesignForeignKey> includedInDesignForeignKey;

    @JsonProperty("included_in_design_key")
    protected ItemList<DesignKey> includedInDesignKey;

    @JsonProperty("length")
    protected String length;

    @JsonProperty("level")
    protected Number level;

    @JsonProperty("minimum_length")
    protected Number minimumLength;

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

    @JsonProperty("parent_design_foreignKey")
    protected ItemList<DesignForeignKey> parentDesignForeignkey;

    @JsonProperty("physical_domains")
    protected PhysicalDomain physicalDomains;

    @JsonProperty("position")
    protected Number position;

    @JsonProperty("primary_key")
    protected List<String> primaryKey;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("unique")
    protected Boolean unique;

    /**
     * Retrieve the {@code allows_null_values} property (displayed as '{@literal Allow Null Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("allows_null_values")
    public Boolean getAllowsNullValues() { return this.allowsNullValues; }

    /**
     * Set the {@code allows_null_values} property (displayed as {@code Allow Null Values}) of the object.
     * @param allowsNullValues the value to set
     */
    @JsonProperty("allows_null_values")
    public void setAllowsNullValues(Boolean allowsNullValues) { this.allowsNullValues = allowsNullValues; }

    /**
     * Retrieve the {@code data_type} property (displayed as '{@literal Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_type")
    public String getDataType() { return this.dataType; }

    /**
     * Set the {@code data_type} property (displayed as {@code Data Type}) of the object.
     * @param dataType the value to set
     */
    @JsonProperty("data_type")
    public void setDataType(String dataType) { this.dataType = dataType; }

    /**
     * Retrieve the {@code design_table_or_view} property (displayed as '{@literal Design Table or View}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("design_table_or_view")
    public Datagroup getDesignTableOrView() { return this.designTableOrView; }

    /**
     * Set the {@code design_table_or_view} property (displayed as {@code Design Table or View}) of the object.
     * @param designTableOrView the value to set
     */
    @JsonProperty("design_table_or_view")
    public void setDesignTableOrView(Datagroup designTableOrView) { this.designTableOrView = designTableOrView; }

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
     * Retrieve the {@code implemented_by_data_fields} property (displayed as '{@literal Implemented By Data Fields}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("implemented_by_data_fields")
    public ItemList<DataItem> getImplementedByDataFields() { return this.implementedByDataFields; }

    /**
     * Set the {@code implemented_by_data_fields} property (displayed as {@code Implemented By Data Fields}) of the object.
     * @param implementedByDataFields the value to set
     */
    @JsonProperty("implemented_by_data_fields")
    public void setImplementedByDataFields(ItemList<DataItem> implementedByDataFields) { this.implementedByDataFields = implementedByDataFields; }

    /**
     * Retrieve the {@code implemented_by_database_columns} property (displayed as '{@literal Implemented by Database Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("implemented_by_database_columns")
    public ItemList<DatabaseColumn> getImplementedByDatabaseColumns() { return this.implementedByDatabaseColumns; }

    /**
     * Set the {@code implemented_by_database_columns} property (displayed as {@code Implemented by Database Columns}) of the object.
     * @param implementedByDatabaseColumns the value to set
     */
    @JsonProperty("implemented_by_database_columns")
    public void setImplementedByDatabaseColumns(ItemList<DatabaseColumn> implementedByDatabaseColumns) { this.implementedByDatabaseColumns = implementedByDatabaseColumns; }

    /**
     * Retrieve the {@code implements_entity_attributes} property (displayed as '{@literal Implements Entity Attributes}') of the object.
     * @return {@code ItemList<EntityAttribute>}
     */
    @JsonProperty("implements_entity_attributes")
    public ItemList<EntityAttribute> getImplementsEntityAttributes() { return this.implementsEntityAttributes; }

    /**
     * Set the {@code implements_entity_attributes} property (displayed as {@code Implements Entity Attributes}) of the object.
     * @param implementsEntityAttributes the value to set
     */
    @JsonProperty("implements_entity_attributes")
    public void setImplementsEntityAttributes(ItemList<EntityAttribute> implementsEntityAttributes) { this.implementsEntityAttributes = implementsEntityAttributes; }

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
     * Retrieve the {@code included_in_design_foreign_key} property (displayed as '{@literal Child Design Foreign Key}') of the object.
     * @return {@code ItemList<DesignForeignKey>}
     */
    @JsonProperty("included_in_design_foreign_key")
    public ItemList<DesignForeignKey> getIncludedInDesignForeignKey() { return this.includedInDesignForeignKey; }

    /**
     * Set the {@code included_in_design_foreign_key} property (displayed as {@code Child Design Foreign Key}) of the object.
     * @param includedInDesignForeignKey the value to set
     */
    @JsonProperty("included_in_design_foreign_key")
    public void setIncludedInDesignForeignKey(ItemList<DesignForeignKey> includedInDesignForeignKey) { this.includedInDesignForeignKey = includedInDesignForeignKey; }

    /**
     * Retrieve the {@code included_in_design_key} property (displayed as '{@literal Design Key}') of the object.
     * @return {@code ItemList<DesignKey>}
     */
    @JsonProperty("included_in_design_key")
    public ItemList<DesignKey> getIncludedInDesignKey() { return this.includedInDesignKey; }

    /**
     * Set the {@code included_in_design_key} property (displayed as {@code Design Key}) of the object.
     * @param includedInDesignKey the value to set
     */
    @JsonProperty("included_in_design_key")
    public void setIncludedInDesignKey(ItemList<DesignKey> includedInDesignKey) { this.includedInDesignKey = includedInDesignKey; }

    /**
     * Retrieve the {@code length} property (displayed as '{@literal Length}') of the object.
     * @return {@code String}
     */
    @JsonProperty("length")
    public String getLength() { return this.length; }

    /**
     * Set the {@code length} property (displayed as {@code Length}) of the object.
     * @param length the value to set
     */
    @JsonProperty("length")
    public void setLength(String length) { this.length = length; }

    /**
     * Retrieve the {@code level} property (displayed as '{@literal Level}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("level")
    public Number getLevel() { return this.level; }

    /**
     * Set the {@code level} property (displayed as {@code Level}) of the object.
     * @param level the value to set
     */
    @JsonProperty("level")
    public void setLevel(Number level) { this.level = level; }

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
     * Retrieve the {@code odbc_type} property (displayed as '{@literal ODBC Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("odbc_type")
    public String getOdbcType() { return this.odbcType; }

    /**
     * Set the {@code odbc_type} property (displayed as {@code ODBC Type}) of the object.
     * @param odbcType the value to set
     */
    @JsonProperty("odbc_type")
    public void setOdbcType(String odbcType) { this.odbcType = odbcType; }

    /**
     * Retrieve the {@code parent_design_foreignKey} property (displayed as '{@literal Parent Design Foreign Key}') of the object.
     * @return {@code ItemList<DesignForeignKey>}
     */
    @JsonProperty("parent_design_foreignKey")
    public ItemList<DesignForeignKey> getParentDesignForeignkey() { return this.parentDesignForeignkey; }

    /**
     * Set the {@code parent_design_foreignKey} property (displayed as {@code Parent Design Foreign Key}) of the object.
     * @param parentDesignForeignkey the value to set
     */
    @JsonProperty("parent_design_foreignKey")
    public void setParentDesignForeignkey(ItemList<DesignForeignKey> parentDesignForeignkey) { this.parentDesignForeignkey = parentDesignForeignkey; }

    /**
     * Retrieve the {@code physical_domains} property (displayed as '{@literal Physical Domains}') of the object.
     * @return {@code PhysicalDomain}
     */
    @JsonProperty("physical_domains")
    public PhysicalDomain getPhysicalDomains() { return this.physicalDomains; }

    /**
     * Set the {@code physical_domains} property (displayed as {@code Physical Domains}) of the object.
     * @param physicalDomains the value to set
     */
    @JsonProperty("physical_domains")
    public void setPhysicalDomains(PhysicalDomain physicalDomains) { this.physicalDomains = physicalDomains; }

    /**
     * Retrieve the {@code position} property (displayed as '{@literal Position}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("position")
    public Number getPosition() { return this.position; }

    /**
     * Set the {@code position} property (displayed as {@code Position}) of the object.
     * @param position the value to set
     */
    @JsonProperty("position")
    public void setPosition(Number position) { this.position = position; }

    /**
     * Retrieve the {@code primary_key} property (displayed as '{@literal Primary Key}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("primary_key")
    public List<String> getPrimaryKey() { return this.primaryKey; }

    /**
     * Set the {@code primary_key} property (displayed as {@code Primary Key}) of the object.
     * @param primaryKey the value to set
     */
    @JsonProperty("primary_key")
    public void setPrimaryKey(List<String> primaryKey) { this.primaryKey = primaryKey; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Native Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Native Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

    /**
     * Retrieve the {@code unique} property (displayed as '{@literal Unique Constraint}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("unique")
    public Boolean getUnique() { return this.unique; }

    /**
     * Set the {@code unique} property (displayed as {@code Unique Constraint}) of the object.
     * @param unique the value to set
     */
    @JsonProperty("unique")
    public void setUnique(Boolean unique) { this.unique = unique; }

}
