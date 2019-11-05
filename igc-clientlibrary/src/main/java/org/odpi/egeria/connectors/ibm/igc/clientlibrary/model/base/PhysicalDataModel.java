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
 * POJO for the {@code physical_data_model} asset type in IGC, displayed as '{@literal Physical Data Model}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("physical_data_model")
public class PhysicalDataModel extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("author")
    protected String author;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("contains_design_stored_procedures")
    protected ItemList<DesignStoredProcedure> containsDesignStoredProcedures;

    @JsonProperty("contains_design_tables")
    protected ItemList<DesignTable> containsDesignTables;

    @JsonProperty("contains_design_views")
    protected ItemList<DesignView> containsDesignViews;

    @JsonProperty("contains_physical_models")
    protected ItemList<PhysicalDataModel> containsPhysicalModels;

    @JsonProperty("implemented_by_data_files")
    protected ItemList<MainObject> implementedByDataFiles;

    @JsonProperty("implemented_by_database_schemas")
    protected ItemList<DatabaseSchema> implementedByDatabaseSchemas;

    @JsonProperty("implements_logical_data_models")
    protected ItemList<LogicalDataModel> implementsLogicalDataModels;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("package")
    protected String zpackage;

    @JsonProperty("physical_domains")
    protected ItemList<PhysicalDomain> physicalDomains;

    @JsonProperty("physical_model")
    protected PhysicalDataModel physicalModel;

    @JsonProperty("target_database")
    protected String targetDatabase;

    @JsonProperty("target_dbms")
    protected String targetDbms;

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
     * Retrieve the {@code author} property (displayed as '{@literal Author}') of the object.
     * @return {@code String}
     */
    @JsonProperty("author")
    public String getAuthor() { return this.author; }

    /**
     * Set the {@code author} property (displayed as {@code Author}) of the object.
     * @param author the value to set
     */
    @JsonProperty("author")
    public void setAuthor(String author) { this.author = author; }

    /**
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @param blueprintElements the value to set
     */
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code contains_design_stored_procedures} property (displayed as '{@literal Contains Design Stored Procedures}') of the object.
     * @return {@code ItemList<DesignStoredProcedure>}
     */
    @JsonProperty("contains_design_stored_procedures")
    public ItemList<DesignStoredProcedure> getContainsDesignStoredProcedures() { return this.containsDesignStoredProcedures; }

    /**
     * Set the {@code contains_design_stored_procedures} property (displayed as {@code Contains Design Stored Procedures}) of the object.
     * @param containsDesignStoredProcedures the value to set
     */
    @JsonProperty("contains_design_stored_procedures")
    public void setContainsDesignStoredProcedures(ItemList<DesignStoredProcedure> containsDesignStoredProcedures) { this.containsDesignStoredProcedures = containsDesignStoredProcedures; }

    /**
     * Retrieve the {@code contains_design_tables} property (displayed as '{@literal Contains Design Tables}') of the object.
     * @return {@code ItemList<DesignTable>}
     */
    @JsonProperty("contains_design_tables")
    public ItemList<DesignTable> getContainsDesignTables() { return this.containsDesignTables; }

    /**
     * Set the {@code contains_design_tables} property (displayed as {@code Contains Design Tables}) of the object.
     * @param containsDesignTables the value to set
     */
    @JsonProperty("contains_design_tables")
    public void setContainsDesignTables(ItemList<DesignTable> containsDesignTables) { this.containsDesignTables = containsDesignTables; }

    /**
     * Retrieve the {@code contains_design_views} property (displayed as '{@literal Contains Design Views}') of the object.
     * @return {@code ItemList<DesignView>}
     */
    @JsonProperty("contains_design_views")
    public ItemList<DesignView> getContainsDesignViews() { return this.containsDesignViews; }

    /**
     * Set the {@code contains_design_views} property (displayed as {@code Contains Design Views}) of the object.
     * @param containsDesignViews the value to set
     */
    @JsonProperty("contains_design_views")
    public void setContainsDesignViews(ItemList<DesignView> containsDesignViews) { this.containsDesignViews = containsDesignViews; }

    /**
     * Retrieve the {@code contains_physical_models} property (displayed as '{@literal Contains Physical Models}') of the object.
     * @return {@code ItemList<PhysicalDataModel>}
     */
    @JsonProperty("contains_physical_models")
    public ItemList<PhysicalDataModel> getContainsPhysicalModels() { return this.containsPhysicalModels; }

    /**
     * Set the {@code contains_physical_models} property (displayed as {@code Contains Physical Models}) of the object.
     * @param containsPhysicalModels the value to set
     */
    @JsonProperty("contains_physical_models")
    public void setContainsPhysicalModels(ItemList<PhysicalDataModel> containsPhysicalModels) { this.containsPhysicalModels = containsPhysicalModels; }

    /**
     * Retrieve the {@code implemented_by_data_files} property (displayed as '{@literal Implemented By Data Files}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("implemented_by_data_files")
    public ItemList<MainObject> getImplementedByDataFiles() { return this.implementedByDataFiles; }

    /**
     * Set the {@code implemented_by_data_files} property (displayed as {@code Implemented By Data Files}) of the object.
     * @param implementedByDataFiles the value to set
     */
    @JsonProperty("implemented_by_data_files")
    public void setImplementedByDataFiles(ItemList<MainObject> implementedByDataFiles) { this.implementedByDataFiles = implementedByDataFiles; }

    /**
     * Retrieve the {@code implemented_by_database_schemas} property (displayed as '{@literal Implemented by Database Schemas}') of the object.
     * @return {@code ItemList<DatabaseSchema>}
     */
    @JsonProperty("implemented_by_database_schemas")
    public ItemList<DatabaseSchema> getImplementedByDatabaseSchemas() { return this.implementedByDatabaseSchemas; }

    /**
     * Set the {@code implemented_by_database_schemas} property (displayed as {@code Implemented by Database Schemas}) of the object.
     * @param implementedByDatabaseSchemas the value to set
     */
    @JsonProperty("implemented_by_database_schemas")
    public void setImplementedByDatabaseSchemas(ItemList<DatabaseSchema> implementedByDatabaseSchemas) { this.implementedByDatabaseSchemas = implementedByDatabaseSchemas; }

    /**
     * Retrieve the {@code implements_logical_data_models} property (displayed as '{@literal Implements Logical Data Models}') of the object.
     * @return {@code ItemList<LogicalDataModel>}
     */
    @JsonProperty("implements_logical_data_models")
    public ItemList<LogicalDataModel> getImplementsLogicalDataModels() { return this.implementsLogicalDataModels; }

    /**
     * Set the {@code implements_logical_data_models} property (displayed as {@code Implements Logical Data Models}) of the object.
     * @param implementsLogicalDataModels the value to set
     */
    @JsonProperty("implements_logical_data_models")
    public void setImplementsLogicalDataModels(ItemList<LogicalDataModel> implementsLogicalDataModels) { this.implementsLogicalDataModels = implementsLogicalDataModels; }

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
     * Retrieve the {@code namespace} property (displayed as '{@literal Namespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("namespace")
    public String getNamespace() { return this.namespace; }

    /**
     * Set the {@code namespace} property (displayed as {@code Namespace}) of the object.
     * @param namespace the value to set
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) { this.namespace = namespace; }

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
     * Retrieve the {@code package} property (displayed as '{@literal Package}') of the object.
     * @return {@code String}
     */
    @JsonProperty("package")
    public String getPackage() { return this.zpackage; }

    /**
     * Set the {@code package} property (displayed as {@code Package}) of the object.
     * @param zpackage the value to set
     */
    @JsonProperty("package")
    public void setPackage(String zpackage) { this.zpackage = zpackage; }

    /**
     * Retrieve the {@code physical_domains} property (displayed as '{@literal Physical Domains}') of the object.
     * @return {@code ItemList<PhysicalDomain>}
     */
    @JsonProperty("physical_domains")
    public ItemList<PhysicalDomain> getPhysicalDomains() { return this.physicalDomains; }

    /**
     * Set the {@code physical_domains} property (displayed as {@code Physical Domains}) of the object.
     * @param physicalDomains the value to set
     */
    @JsonProperty("physical_domains")
    public void setPhysicalDomains(ItemList<PhysicalDomain> physicalDomains) { this.physicalDomains = physicalDomains; }

    /**
     * Retrieve the {@code physical_model} property (displayed as '{@literal Physical Model}') of the object.
     * @return {@code PhysicalDataModel}
     */
    @JsonProperty("physical_model")
    public PhysicalDataModel getPhysicalModel() { return this.physicalModel; }

    /**
     * Set the {@code physical_model} property (displayed as {@code Physical Model}) of the object.
     * @param physicalModel the value to set
     */
    @JsonProperty("physical_model")
    public void setPhysicalModel(PhysicalDataModel physicalModel) { this.physicalModel = physicalModel; }

    /**
     * Retrieve the {@code target_database} property (displayed as '{@literal Target Database}') of the object.
     * @return {@code String}
     */
    @JsonProperty("target_database")
    public String getTargetDatabase() { return this.targetDatabase; }

    /**
     * Set the {@code target_database} property (displayed as {@code Target Database}) of the object.
     * @param targetDatabase the value to set
     */
    @JsonProperty("target_database")
    public void setTargetDatabase(String targetDatabase) { this.targetDatabase = targetDatabase; }

    /**
     * Retrieve the {@code target_dbms} property (displayed as '{@literal Target DBMS}') of the object.
     * @return {@code String}
     */
    @JsonProperty("target_dbms")
    public String getTargetDbms() { return this.targetDbms; }

    /**
     * Set the {@code target_dbms} property (displayed as {@code Target DBMS}) of the object.
     * @param targetDbms the value to set
     */
    @JsonProperty("target_dbms")
    public void setTargetDbms(String targetDbms) { this.targetDbms = targetDbms; }

}
