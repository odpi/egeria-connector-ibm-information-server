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
 * POJO for the {@code logical_entity} asset type in IGC, displayed as '{@literal Logical Entity}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=LogicalEntity.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("logical_entity")
public class LogicalEntity extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    /**
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("entity_attributes")
    protected ItemList<EntityAttribute> entityAttributes;

    @JsonProperty("implemented_by_database_tables_or_views")
    protected ItemList<Datagroup> implementedByDatabaseTablesOrViews;

    @JsonProperty("implemented_by_design_tables_or_views")
    protected ItemList<Datagroup> implementedByDesignTablesOrViews;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("logical_data_model")
    protected LogicalDataModel logicalDataModel;

    @JsonProperty("logical_foreign_keys")
    protected ItemList<LogicalForeignKey> logicalForeignKeys;

    @JsonProperty("logical_inversion_keys")
    protected ItemList<LogicalInversionKey> logicalInversionKeys;

    @JsonProperty("logical_keys")
    protected ItemList<LogicalKey> logicalKeys;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("physical_name")
    protected String physicalName;

    @JsonProperty("subject_areas")
    protected ItemList<SubjectArea> subjectAreas;

    @JsonProperty("subtypes")
    protected ItemList<LogicalEntity> subtypes;

    @JsonProperty("supertypes")
    protected ItemList<LogicalEntity> supertypes;

    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @param aliasBusinessName the value to set
     */
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @param blueprintElements the value to set
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code entity_attributes} property (displayed as '{@literal Entity Attributes}') of the object.
     * @return {@code ItemList<EntityAttribute>}
     */
    @JsonProperty("entity_attributes")
    public ItemList<EntityAttribute> getEntityAttributes() { return this.entityAttributes; }

    /**
     * Set the {@code entity_attributes} property (displayed as {@code Entity Attributes}) of the object.
     * @param entityAttributes the value to set
     */
    @JsonProperty("entity_attributes")
    public void setEntityAttributes(ItemList<EntityAttribute> entityAttributes) { this.entityAttributes = entityAttributes; }

    /**
     * Retrieve the {@code implemented_by_database_tables_or_views} property (displayed as '{@literal Implemented by Database Tables or Views}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("implemented_by_database_tables_or_views")
    public ItemList<Datagroup> getImplementedByDatabaseTablesOrViews() { return this.implementedByDatabaseTablesOrViews; }

    /**
     * Set the {@code implemented_by_database_tables_or_views} property (displayed as {@code Implemented by Database Tables or Views}) of the object.
     * @param implementedByDatabaseTablesOrViews the value to set
     */
    @JsonProperty("implemented_by_database_tables_or_views")
    public void setImplementedByDatabaseTablesOrViews(ItemList<Datagroup> implementedByDatabaseTablesOrViews) { this.implementedByDatabaseTablesOrViews = implementedByDatabaseTablesOrViews; }

    /**
     * Retrieve the {@code implemented_by_design_tables_or_views} property (displayed as '{@literal Implemented by Design Tables or Design Views}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("implemented_by_design_tables_or_views")
    public ItemList<Datagroup> getImplementedByDesignTablesOrViews() { return this.implementedByDesignTablesOrViews; }

    /**
     * Set the {@code implemented_by_design_tables_or_views} property (displayed as {@code Implemented by Design Tables or Design Views}) of the object.
     * @param implementedByDesignTablesOrViews the value to set
     */
    @JsonProperty("implemented_by_design_tables_or_views")
    public void setImplementedByDesignTablesOrViews(ItemList<Datagroup> implementedByDesignTablesOrViews) { this.implementedByDesignTablesOrViews = implementedByDesignTablesOrViews; }

    /**
     * Retrieve the {@code imported_from} property (displayed as '{@literal Imported From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("imported_from")
    public String getImportedFrom() { return this.importedFrom; }

    /**
     * Set the {@code imported_from} property (displayed as {@code Imported From}) of the object.
     * @param importedFrom the value to set
     */
    @JsonProperty("imported_from")
    public void setImportedFrom(String importedFrom) { this.importedFrom = importedFrom; }

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
     * Retrieve the {@code logical_data_model} property (displayed as '{@literal Logical Data Model}') of the object.
     * @return {@code LogicalDataModel}
     */
    @JsonProperty("logical_data_model")
    public LogicalDataModel getLogicalDataModel() { return this.logicalDataModel; }

    /**
     * Set the {@code logical_data_model} property (displayed as {@code Logical Data Model}) of the object.
     * @param logicalDataModel the value to set
     */
    @JsonProperty("logical_data_model")
    public void setLogicalDataModel(LogicalDataModel logicalDataModel) { this.logicalDataModel = logicalDataModel; }

    /**
     * Retrieve the {@code logical_foreign_keys} property (displayed as '{@literal Logical Foreign Keys}') of the object.
     * @return {@code ItemList<LogicalForeignKey>}
     */
    @JsonProperty("logical_foreign_keys")
    public ItemList<LogicalForeignKey> getLogicalForeignKeys() { return this.logicalForeignKeys; }

    /**
     * Set the {@code logical_foreign_keys} property (displayed as {@code Logical Foreign Keys}) of the object.
     * @param logicalForeignKeys the value to set
     */
    @JsonProperty("logical_foreign_keys")
    public void setLogicalForeignKeys(ItemList<LogicalForeignKey> logicalForeignKeys) { this.logicalForeignKeys = logicalForeignKeys; }

    /**
     * Retrieve the {@code logical_inversion_keys} property (displayed as '{@literal Logical Inversion Keys}') of the object.
     * @return {@code ItemList<LogicalInversionKey>}
     */
    @JsonProperty("logical_inversion_keys")
    public ItemList<LogicalInversionKey> getLogicalInversionKeys() { return this.logicalInversionKeys; }

    /**
     * Set the {@code logical_inversion_keys} property (displayed as {@code Logical Inversion Keys}) of the object.
     * @param logicalInversionKeys the value to set
     */
    @JsonProperty("logical_inversion_keys")
    public void setLogicalInversionKeys(ItemList<LogicalInversionKey> logicalInversionKeys) { this.logicalInversionKeys = logicalInversionKeys; }

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
     * Retrieve the {@code subject_areas} property (displayed as '{@literal Subject Areas}') of the object.
     * @return {@code ItemList<SubjectArea>}
     */
    @JsonProperty("subject_areas")
    public ItemList<SubjectArea> getSubjectAreas() { return this.subjectAreas; }

    /**
     * Set the {@code subject_areas} property (displayed as {@code Subject Areas}) of the object.
     * @param subjectAreas the value to set
     */
    @JsonProperty("subject_areas")
    public void setSubjectAreas(ItemList<SubjectArea> subjectAreas) { this.subjectAreas = subjectAreas; }

    /**
     * Retrieve the {@code subtypes} property (displayed as '{@literal Subtypes}') of the object.
     * @return {@code ItemList<LogicalEntity>}
     */
    @JsonProperty("subtypes")
    public ItemList<LogicalEntity> getSubtypes() { return this.subtypes; }

    /**
     * Set the {@code subtypes} property (displayed as {@code Subtypes}) of the object.
     * @param subtypes the value to set
     */
    @JsonProperty("subtypes")
    public void setSubtypes(ItemList<LogicalEntity> subtypes) { this.subtypes = subtypes; }

    /**
     * Retrieve the {@code supertypes} property (displayed as '{@literal Supertypes}') of the object.
     * @return {@code ItemList<LogicalEntity>}
     */
    @JsonProperty("supertypes")
    public ItemList<LogicalEntity> getSupertypes() { return this.supertypes; }

    /**
     * Set the {@code supertypes} property (displayed as {@code Supertypes}) of the object.
     * @param supertypes the value to set
     */
    @JsonProperty("supertypes")
    public void setSupertypes(ItemList<LogicalEntity> supertypes) { this.supertypes = supertypes; }

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

}
