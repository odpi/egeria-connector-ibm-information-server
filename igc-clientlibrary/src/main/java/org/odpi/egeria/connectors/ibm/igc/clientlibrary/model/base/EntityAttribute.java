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

/**
 * POJO for the {@code entity_attribute} asset type in IGC, displayed as '{@literal Entity Attribute}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=EntityAttribute.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("entity_attribute")
public class EntityAttribute extends InformationAsset {

    @JsonProperty("child_logical_foreign_keys")
    protected ItemList<EntityAttribute> childLogicalForeignKeys;

    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("implemented_by_database_columns")
    protected ItemList<DatabaseColumn> implementedByDatabaseColumns;

    @JsonProperty("implemented_by_design_columns")
    protected ItemList<DesignColumn> implementedByDesignColumns;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("logical_entity")
    protected LogicalEntity logicalEntity;

    @JsonProperty("logical_keys")
    protected ItemList<LogicalKey> logicalKeys;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("native_type")
    protected String nativeType;

    @JsonProperty("parent_logical_foreignKey")
    protected ItemList<LogicalForeignKey> parentLogicalForeignkey;

    @JsonProperty("physical_name")
    protected String physicalName;

    @JsonProperty("primary_key")
    protected Boolean primaryKey;

    @JsonProperty("required")
    protected Boolean required;

    @JsonProperty("scale")
    protected Number scale;

    @JsonProperty("sequence")
    protected Number sequence;

    /**
     * Up to 11.7.1.0sp2 this appears to be a singular LogicalValidationList, but from 11.7.1.0sp2 onwards it is an
     * ItemList of those objects. If used as-is in versions prior to 11.7.1.0sp2 this could cause deserialization
     * errors. If so, simply remove the 'ItemList' wrapping and correct the corresponding getters and setters for
     * your release.
     */
    @JsonProperty("validation_list")
    protected ItemList<LogicalValidationList> validationList;

    @JsonProperty("validation_range")
    protected LogicalValidationRange validationRange;

    @JsonProperty("validation_rule")
    protected LogicalValidationRule validationRule;

    /**
     * Retrieve the {@code child_logical_foreign_keys} property (displayed as '{@literal Child Logical Foreign Keys}') of the object.
     * @return {@code ItemList<EntityAttribute>}
     */
    @JsonProperty("child_logical_foreign_keys")
    public ItemList<EntityAttribute> getChildLogicalForeignKeys() { return this.childLogicalForeignKeys; }

    /**
     * Set the {@code child_logical_foreign_keys} property (displayed as {@code Child Logical Foreign Keys}) of the object.
     * @param childLogicalForeignKeys the value to set
     */
    @JsonProperty("child_logical_foreign_keys")
    public void setChildLogicalForeignKeys(ItemList<EntityAttribute> childLogicalForeignKeys) { this.childLogicalForeignKeys = childLogicalForeignKeys; }

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
     * Retrieve the {@code implemented_by_design_columns} property (displayed as '{@literal Implemented by Design Columns}') of the object.
     * @return {@code ItemList<DesignColumn>}
     */
    @JsonProperty("implemented_by_design_columns")
    public ItemList<DesignColumn> getImplementedByDesignColumns() { return this.implementedByDesignColumns; }

    /**
     * Set the {@code implemented_by_design_columns} property (displayed as {@code Implemented by Design Columns}) of the object.
     * @param implementedByDesignColumns the value to set
     */
    @JsonProperty("implemented_by_design_columns")
    public void setImplementedByDesignColumns(ItemList<DesignColumn> implementedByDesignColumns) { this.implementedByDesignColumns = implementedByDesignColumns; }

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
     * Retrieve the {@code logical_entity} property (displayed as '{@literal Logical Entity}') of the object.
     * @return {@code LogicalEntity}
     */
    @JsonProperty("logical_entity")
    public LogicalEntity getLogicalEntity() { return this.logicalEntity; }

    /**
     * Set the {@code logical_entity} property (displayed as {@code Logical Entity}) of the object.
     * @param logicalEntity the value to set
     */
    @JsonProperty("logical_entity")
    public void setLogicalEntity(LogicalEntity logicalEntity) { this.logicalEntity = logicalEntity; }

    /**
     * Retrieve the {@code logical_keys} property (displayed as '{@literal Logical Keys}') of the object.
     * @return {@code ItemList<LogicalKey>}
     */
    @JsonProperty("logical_keys")
    public ItemList<LogicalKey> getLogicalKeys() { return this.logicalKeys; }

    /**
     * Set the {@code logical_keys} property (displayed as {@code Logical Keys}) of the object.
     * @param logicalKeys the value to set
     */
    @JsonProperty("logical_keys")
    public void setLogicalKeys(ItemList<LogicalKey> logicalKeys) { this.logicalKeys = logicalKeys; }

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
     * Retrieve the {@code native_type} property (displayed as '{@literal Native Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_type")
    public String getNativeType() { return this.nativeType; }

    /**
     * Set the {@code native_type} property (displayed as {@code Native Type}) of the object.
     * @param nativeType the value to set
     */
    @JsonProperty("native_type")
    public void setNativeType(String nativeType) { this.nativeType = nativeType; }

    /**
     * Retrieve the {@code parent_logical_foreignKey} property (displayed as '{@literal Parent Logical Foreign Key}') of the object.
     * @return {@code ItemList<LogicalForeignKey>}
     */
    @JsonProperty("parent_logical_foreignKey")
    public ItemList<LogicalForeignKey> getParentLogicalForeignkey() { return this.parentLogicalForeignkey; }

    /**
     * Set the {@code parent_logical_foreignKey} property (displayed as {@code Parent Logical Foreign Key}) of the object.
     * @param parentLogicalForeignkey the value to set
     */
    @JsonProperty("parent_logical_foreignKey")
    public void setParentLogicalForeignkey(ItemList<LogicalForeignKey> parentLogicalForeignkey) { this.parentLogicalForeignkey = parentLogicalForeignkey; }

    /**
     * Retrieve the {@code physical_name} property (displayed as '{@literal Physical Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("physical_name")
    public String getPhysicalName() { return this.physicalName; }

    /**
     * Set the {@code physical_name} property (displayed as {@code Physical Name}) of the object.
     * @param physicalName the value to set
     */
    @JsonProperty("physical_name")
    public void setPhysicalName(String physicalName) { this.physicalName = physicalName; }

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
     * Retrieve the {@code required} property (displayed as '{@literal Required}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("required")
    public Boolean getRequired() { return this.required; }

    /**
     * Set the {@code required} property (displayed as {@code Required}) of the object.
     * @param required the value to set
     */
    @JsonProperty("required")
    public void setRequired(Boolean required) { this.required = required; }

    /**
     * Retrieve the {@code scale} property (displayed as '{@literal Scale}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("scale")
    public Number getScale() { return this.scale; }

    /**
     * Set the {@code scale} property (displayed as {@code Scale}) of the object.
     * @param scale the value to set
     */
    @JsonProperty("scale")
    public void setScale(Number scale) { this.scale = scale; }

    /**
     * Retrieve the {@code sequence} property (displayed as '{@literal Sequence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sequence")
    public Number getSequence() { return this.sequence; }

    /**
     * Set the {@code sequence} property (displayed as {@code Sequence}) of the object.
     * @param sequence the value to set
     */
    @JsonProperty("sequence")
    public void setSequence(Number sequence) { this.sequence = sequence; }

    /**
     * Retrieve the {@code validation_list} property (displayed as '{@literal Validation List}') of the object.
     * @return {@code ItemList<LogicalValidationList>}
     */
    @JsonProperty("validation_list")
    public ItemList<LogicalValidationList> getValidationList() { return this.validationList; }

    /**
     * Set the {@code validation_list} property (displayed as {@code Validation List}) of the object.
     * @param validationList the value to set
     */
    @JsonProperty("validation_list")
    public void setValidationList(ItemList<LogicalValidationList> validationList) { this.validationList = validationList; }

    /**
     * Retrieve the {@code validation_range} property (displayed as '{@literal Validation Range}') of the object.
     * @return {@code LogicalValidationRange}
     */
    @JsonProperty("validation_range")
    public LogicalValidationRange getValidationRange() { return this.validationRange; }

    /**
     * Set the {@code validation_range} property (displayed as {@code Validation Range}) of the object.
     * @param validationRange the value to set
     */
    @JsonProperty("validation_range")
    public void setValidationRange(LogicalValidationRange validationRange) { this.validationRange = validationRange; }

    /**
     * Retrieve the {@code validation_rule} property (displayed as '{@literal Validation Rule}') of the object.
     * @return {@code LogicalValidationRule}
     */
    @JsonProperty("validation_rule")
    public LogicalValidationRule getValidationRule() { return this.validationRule; }

    /**
     * Set the {@code validation_rule} property (displayed as {@code Validation Rule}) of the object.
     * @param validationRule the value to set
     */
    @JsonProperty("validation_rule")
    public void setValidationRule(LogicalValidationRule validationRule) { this.validationRule = validationRule; }

}
