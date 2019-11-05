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
 * POJO for the {@code view} asset type in IGC, displayed as '{@literal View}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("view")
public class View extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("analysis")
    protected ItemList<TableAnalysis> analysis;

    @JsonProperty("based_upon_database_tables")
    protected ItemList<Datagroup> basedUponDatabaseTables;

    @JsonProperty("bi_model_collections")
    protected ItemList<BiCollection> biModelCollections;

    @JsonProperty("bi_report_queries")
    protected ItemList<BiReportQuery> biReportQueries;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("data_policies")
    protected ItemList<DataPolicy> dataPolicies;

    @JsonProperty("database_aliases")
    protected ItemList<Datagroup> databaseAliases;

    @JsonProperty("database_columns")
    protected ItemList<DatabaseColumn> databaseColumns;

    @JsonProperty("database_schema")
    protected DatabaseSchema databaseSchema;

    @JsonProperty("defined_foreign_key")
    protected ItemList<DatabaseColumn> definedForeignKey;

    @JsonProperty("defined_primary_key")
    protected ItemList<CandidateKey> definedPrimaryKey;

    @JsonProperty("expression")
    protected String expression;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("implements_design_tables_or_views")
    protected ItemList<Datagroup> implementsDesignTablesOrViews;

    @JsonProperty("implements_logical_entities")
    protected ItemList<LogicalEntity> implementsLogicalEntities;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("indexes")
    protected ItemList<DatabaseIndex> indexes;

    @JsonProperty("lineage_service_information")
    protected List<String> lineageServiceInformation;

    @JsonProperty("lineage_service_last_run_date")
    protected List<Date> lineageServiceLastRunDate;

    @JsonProperty("lineage_service_status")
    protected List<String> lineageServiceStatus;

    @JsonProperty("mapped_to_physical_objects")
    protected ItemList<PhysicalObject> mappedToPhysicalObjects;

    @JsonProperty("multi_column_analysis")
    protected List<String> multiColumnAnalysis;

    @JsonProperty("read_by_(design)")
    protected ItemList<InformationAsset> readByDesign;

    @JsonProperty("read_by_(operational)")
    protected ItemList<InformationAsset> readByOperational;

    @JsonProperty("read_by_(static)")
    protected ItemList<InformationAsset> readByStatic;

    @JsonProperty("read_by_(user_defined)")
    protected ItemList<InformationAsset> readByUserDefined;

    @JsonProperty("referenced_by_views")
    protected ItemList<View> referencedByViews;

    @JsonProperty("same_as_database_views")
    protected ItemList<View> sameAsDatabaseViews;

    @JsonProperty("selected_foreign_key")
    protected ItemList<DatabaseColumn> selectedForeignKey;

    @JsonProperty("selected_natural_key")
    protected ItemList<DataItem> selectedNaturalKey;

    @JsonProperty("selected_primary_key")
    protected ItemList<DataItem> selectedPrimaryKey;

    @JsonProperty("source_mapping_specifications")
    protected ItemList<MappingSpecification> sourceMappingSpecifications;

    @JsonProperty("table_definitions")
    protected ItemList<TableDefinition> tableDefinitions;

    @JsonProperty("target_mapping_specifications")
    protected ItemList<MappingSpecification> targetMappingSpecifications;

    @JsonProperty("written_by_(design)")
    protected ItemList<InformationAsset> writtenByDesign;

    @JsonProperty("written_by_(operational)")
    protected ItemList<InformationAsset> writtenByOperational;

    @JsonProperty("written_by_(static)")
    protected ItemList<InformationAsset> writtenByStatic;

    @JsonProperty("written_by_(user_defined)")
    protected ItemList<InformationAsset> writtenByUserDefined;

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
     * Retrieve the {@code analysis} property (displayed as '{@literal Analysis}') of the object.
     * @return {@code ItemList<TableAnalysis>}
     */
    @JsonProperty("analysis")
    public ItemList<TableAnalysis> getAnalysis() { return this.analysis; }

    /**
     * Set the {@code analysis} property (displayed as {@code Analysis}) of the object.
     * @param analysis the value to set
     */
    @JsonProperty("analysis")
    public void setAnalysis(ItemList<TableAnalysis> analysis) { this.analysis = analysis; }

    /**
     * Retrieve the {@code based_upon_database_tables} property (displayed as '{@literal Based upon Database Tables}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("based_upon_database_tables")
    public ItemList<Datagroup> getBasedUponDatabaseTables() { return this.basedUponDatabaseTables; }

    /**
     * Set the {@code based_upon_database_tables} property (displayed as {@code Based upon Database Tables}) of the object.
     * @param basedUponDatabaseTables the value to set
     */
    @JsonProperty("based_upon_database_tables")
    public void setBasedUponDatabaseTables(ItemList<Datagroup> basedUponDatabaseTables) { this.basedUponDatabaseTables = basedUponDatabaseTables; }

    /**
     * Retrieve the {@code bi_model_collections} property (displayed as '{@literal BI Model Collections}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("bi_model_collections")
    public ItemList<BiCollection> getBiModelCollections() { return this.biModelCollections; }

    /**
     * Set the {@code bi_model_collections} property (displayed as {@code BI Model Collections}) of the object.
     * @param biModelCollections the value to set
     */
    @JsonProperty("bi_model_collections")
    public void setBiModelCollections(ItemList<BiCollection> biModelCollections) { this.biModelCollections = biModelCollections; }

    /**
     * Retrieve the {@code bi_report_queries} property (displayed as '{@literal BI Report Queries}') of the object.
     * @return {@code ItemList<BiReportQuery>}
     */
    @JsonProperty("bi_report_queries")
    public ItemList<BiReportQuery> getBiReportQueries() { return this.biReportQueries; }

    /**
     * Set the {@code bi_report_queries} property (displayed as {@code BI Report Queries}) of the object.
     * @param biReportQueries the value to set
     */
    @JsonProperty("bi_report_queries")
    public void setBiReportQueries(ItemList<BiReportQuery> biReportQueries) { this.biReportQueries = biReportQueries; }

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
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @return {@code ItemList<DataPolicy>}
     */
    @JsonProperty("data_policies")
    public ItemList<DataPolicy> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<DataPolicy> dataPolicies) { this.dataPolicies = dataPolicies; }

    /**
     * Retrieve the {@code database_aliases} property (displayed as '{@literal Database Aliases}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("database_aliases")
    public ItemList<Datagroup> getDatabaseAliases() { return this.databaseAliases; }

    /**
     * Set the {@code database_aliases} property (displayed as {@code Database Aliases}) of the object.
     * @param databaseAliases the value to set
     */
    @JsonProperty("database_aliases")
    public void setDatabaseAliases(ItemList<Datagroup> databaseAliases) { this.databaseAliases = databaseAliases; }

    /**
     * Retrieve the {@code database_columns} property (displayed as '{@literal Database Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("database_columns")
    public ItemList<DatabaseColumn> getDatabaseColumns() { return this.databaseColumns; }

    /**
     * Set the {@code database_columns} property (displayed as {@code Database Columns}) of the object.
     * @param databaseColumns the value to set
     */
    @JsonProperty("database_columns")
    public void setDatabaseColumns(ItemList<DatabaseColumn> databaseColumns) { this.databaseColumns = databaseColumns; }

    /**
     * Retrieve the {@code database_schema} property (displayed as '{@literal Database Schema}') of the object.
     * @return {@code DatabaseSchema}
     */
    @JsonProperty("database_schema")
    public DatabaseSchema getDatabaseSchema() { return this.databaseSchema; }

    /**
     * Set the {@code database_schema} property (displayed as {@code Database Schema}) of the object.
     * @param databaseSchema the value to set
     */
    @JsonProperty("database_schema")
    public void setDatabaseSchema(DatabaseSchema databaseSchema) { this.databaseSchema = databaseSchema; }

    /**
     * Retrieve the {@code defined_foreign_key} property (displayed as '{@literal Defined Foreign Key}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("defined_foreign_key")
    public ItemList<DatabaseColumn> getDefinedForeignKey() { return this.definedForeignKey; }

    /**
     * Set the {@code defined_foreign_key} property (displayed as {@code Defined Foreign Key}) of the object.
     * @param definedForeignKey the value to set
     */
    @JsonProperty("defined_foreign_key")
    public void setDefinedForeignKey(ItemList<DatabaseColumn> definedForeignKey) { this.definedForeignKey = definedForeignKey; }

    /**
     * Retrieve the {@code defined_primary_key} property (displayed as '{@literal Defined Primary Key}') of the object.
     * @return {@code ItemList<CandidateKey>}
     */
    @JsonProperty("defined_primary_key")
    public ItemList<CandidateKey> getDefinedPrimaryKey() { return this.definedPrimaryKey; }

    /**
     * Set the {@code defined_primary_key} property (displayed as {@code Defined Primary Key}) of the object.
     * @param definedPrimaryKey the value to set
     */
    @JsonProperty("defined_primary_key")
    public void setDefinedPrimaryKey(ItemList<CandidateKey> definedPrimaryKey) { this.definedPrimaryKey = definedPrimaryKey; }

    /**
     * Retrieve the {@code expression} property (displayed as '{@literal Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("expression")
    public String getExpression() { return this.expression; }

    /**
     * Set the {@code expression} property (displayed as {@code Expression}) of the object.
     * @param expression the value to set
     */
    @JsonProperty("expression")
    public void setExpression(String expression) { this.expression = expression; }

    /**
     * Retrieve the {@code impacted_by} property (displayed as '{@literal Impacted by}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacted_by")
    public ItemList<InformationAsset> getImpactedBy() { return this.impactedBy; }

    /**
     * Set the {@code impacted_by} property (displayed as {@code Impacted by}) of the object.
     * @param impactedBy the value to set
     */
    @JsonProperty("impacted_by")
    public void setImpactedBy(ItemList<InformationAsset> impactedBy) { this.impactedBy = impactedBy; }

    /**
     * Retrieve the {@code impacts_on} property (displayed as '{@literal Impacts on}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacts_on")
    public ItemList<InformationAsset> getImpactsOn() { return this.impactsOn; }

    /**
     * Set the {@code impacts_on} property (displayed as {@code Impacts on}) of the object.
     * @param impactsOn the value to set
     */
    @JsonProperty("impacts_on")
    public void setImpactsOn(ItemList<InformationAsset> impactsOn) { this.impactsOn = impactsOn; }

    /**
     * Retrieve the {@code implements_design_tables_or_views} property (displayed as '{@literal Implements Design Tables or Design Views}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("implements_design_tables_or_views")
    public ItemList<Datagroup> getImplementsDesignTablesOrViews() { return this.implementsDesignTablesOrViews; }

    /**
     * Set the {@code implements_design_tables_or_views} property (displayed as {@code Implements Design Tables or Design Views}) of the object.
     * @param implementsDesignTablesOrViews the value to set
     */
    @JsonProperty("implements_design_tables_or_views")
    public void setImplementsDesignTablesOrViews(ItemList<Datagroup> implementsDesignTablesOrViews) { this.implementsDesignTablesOrViews = implementsDesignTablesOrViews; }

    /**
     * Retrieve the {@code implements_logical_entities} property (displayed as '{@literal Implements Logical Entities}') of the object.
     * @return {@code ItemList<LogicalEntity>}
     */
    @JsonProperty("implements_logical_entities")
    public ItemList<LogicalEntity> getImplementsLogicalEntities() { return this.implementsLogicalEntities; }

    /**
     * Set the {@code implements_logical_entities} property (displayed as {@code Implements Logical Entities}) of the object.
     * @param implementsLogicalEntities the value to set
     */
    @JsonProperty("implements_logical_entities")
    public void setImplementsLogicalEntities(ItemList<LogicalEntity> implementsLogicalEntities) { this.implementsLogicalEntities = implementsLogicalEntities; }

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
     * Retrieve the {@code indexes} property (displayed as '{@literal Indexes}') of the object.
     * @return {@code ItemList<DatabaseIndex>}
     */
    @JsonProperty("indexes")
    public ItemList<DatabaseIndex> getIndexes() { return this.indexes; }

    /**
     * Set the {@code indexes} property (displayed as {@code Indexes}) of the object.
     * @param indexes the value to set
     */
    @JsonProperty("indexes")
    public void setIndexes(ItemList<DatabaseIndex> indexes) { this.indexes = indexes; }

    /**
     * Retrieve the {@code lineage_service_information} property (displayed as '{@literal Lineage Service Information}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("lineage_service_information")
    public List<String> getLineageServiceInformation() { return this.lineageServiceInformation; }

    /**
     * Set the {@code lineage_service_information} property (displayed as {@code Lineage Service Information}) of the object.
     * @param lineageServiceInformation the value to set
     */
    @JsonProperty("lineage_service_information")
    public void setLineageServiceInformation(List<String> lineageServiceInformation) { this.lineageServiceInformation = lineageServiceInformation; }

    /**
     * Retrieve the {@code lineage_service_last_run_date} property (displayed as '{@literal Lineage Service Last Run Date}') of the object.
     * @return {@code List<Date>}
     */
    @JsonProperty("lineage_service_last_run_date")
    public List<Date> getLineageServiceLastRunDate() { return this.lineageServiceLastRunDate; }

    /**
     * Set the {@code lineage_service_last_run_date} property (displayed as {@code Lineage Service Last Run Date}) of the object.
     * @param lineageServiceLastRunDate the value to set
     */
    @JsonProperty("lineage_service_last_run_date")
    public void setLineageServiceLastRunDate(List<Date> lineageServiceLastRunDate) { this.lineageServiceLastRunDate = lineageServiceLastRunDate; }

    /**
     * Retrieve the {@code lineage_service_status} property (displayed as '{@literal Lineage Service Status}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("lineage_service_status")
    public List<String> getLineageServiceStatus() { return this.lineageServiceStatus; }

    /**
     * Set the {@code lineage_service_status} property (displayed as {@code Lineage Service Status}) of the object.
     * @param lineageServiceStatus the value to set
     */
    @JsonProperty("lineage_service_status")
    public void setLineageServiceStatus(List<String> lineageServiceStatus) { this.lineageServiceStatus = lineageServiceStatus; }

    /**
     * Retrieve the {@code mapped_to_physical_objects} property (displayed as '{@literal Mapped to Physical Objects}') of the object.
     * @return {@code ItemList<PhysicalObject>}
     */
    @JsonProperty("mapped_to_physical_objects")
    public ItemList<PhysicalObject> getMappedToPhysicalObjects() { return this.mappedToPhysicalObjects; }

    /**
     * Set the {@code mapped_to_physical_objects} property (displayed as {@code Mapped to Physical Objects}) of the object.
     * @param mappedToPhysicalObjects the value to set
     */
    @JsonProperty("mapped_to_physical_objects")
    public void setMappedToPhysicalObjects(ItemList<PhysicalObject> mappedToPhysicalObjects) { this.mappedToPhysicalObjects = mappedToPhysicalObjects; }

    /**
     * Retrieve the {@code multi_column_analysis} property (displayed as '{@literal Multi Column Analysis}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("multi_column_analysis")
    public List<String> getMultiColumnAnalysis() { return this.multiColumnAnalysis; }

    /**
     * Set the {@code multi_column_analysis} property (displayed as {@code Multi Column Analysis}) of the object.
     * @param multiColumnAnalysis the value to set
     */
    @JsonProperty("multi_column_analysis")
    public void setMultiColumnAnalysis(List<String> multiColumnAnalysis) { this.multiColumnAnalysis = multiColumnAnalysis; }

    /**
     * Retrieve the {@code read_by_(design)} property (displayed as '{@literal Read by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(design)")
    public ItemList<InformationAsset> getReadByDesign() { return this.readByDesign; }

    /**
     * Set the {@code read_by_(design)} property (displayed as {@code Read by (Design)}) of the object.
     * @param readByDesign the value to set
     */
    @JsonProperty("read_by_(design)")
    public void setReadByDesign(ItemList<InformationAsset> readByDesign) { this.readByDesign = readByDesign; }

    /**
     * Retrieve the {@code read_by_(operational)} property (displayed as '{@literal Read by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(operational)")
    public ItemList<InformationAsset> getReadByOperational() { return this.readByOperational; }

    /**
     * Set the {@code read_by_(operational)} property (displayed as {@code Read by (Operational)}) of the object.
     * @param readByOperational the value to set
     */
    @JsonProperty("read_by_(operational)")
    public void setReadByOperational(ItemList<InformationAsset> readByOperational) { this.readByOperational = readByOperational; }

    /**
     * Retrieve the {@code read_by_(static)} property (displayed as '{@literal Read by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(static)")
    public ItemList<InformationAsset> getReadByStatic() { return this.readByStatic; }

    /**
     * Set the {@code read_by_(static)} property (displayed as {@code Read by (Static)}) of the object.
     * @param readByStatic the value to set
     */
    @JsonProperty("read_by_(static)")
    public void setReadByStatic(ItemList<InformationAsset> readByStatic) { this.readByStatic = readByStatic; }

    /**
     * Retrieve the {@code read_by_(user_defined)} property (displayed as '{@literal Read by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(user_defined)")
    public ItemList<InformationAsset> getReadByUserDefined() { return this.readByUserDefined; }

    /**
     * Set the {@code read_by_(user_defined)} property (displayed as {@code Read by (User-Defined)}) of the object.
     * @param readByUserDefined the value to set
     */
    @JsonProperty("read_by_(user_defined)")
    public void setReadByUserDefined(ItemList<InformationAsset> readByUserDefined) { this.readByUserDefined = readByUserDefined; }

    /**
     * Retrieve the {@code referenced_by_views} property (displayed as '{@literal Referenced by Views}') of the object.
     * @return {@code ItemList<View>}
     */
    @JsonProperty("referenced_by_views")
    public ItemList<View> getReferencedByViews() { return this.referencedByViews; }

    /**
     * Set the {@code referenced_by_views} property (displayed as {@code Referenced by Views}) of the object.
     * @param referencedByViews the value to set
     */
    @JsonProperty("referenced_by_views")
    public void setReferencedByViews(ItemList<View> referencedByViews) { this.referencedByViews = referencedByViews; }

    /**
     * Retrieve the {@code same_as_database_views} property (displayed as '{@literal Same as Database Views}') of the object.
     * @return {@code ItemList<View>}
     */
    @JsonProperty("same_as_database_views")
    public ItemList<View> getSameAsDatabaseViews() { return this.sameAsDatabaseViews; }

    /**
     * Set the {@code same_as_database_views} property (displayed as {@code Same as Database Views}) of the object.
     * @param sameAsDatabaseViews the value to set
     */
    @JsonProperty("same_as_database_views")
    public void setSameAsDatabaseViews(ItemList<View> sameAsDatabaseViews) { this.sameAsDatabaseViews = sameAsDatabaseViews; }

    /**
     * Retrieve the {@code selected_foreign_key} property (displayed as '{@literal User Selected Foreign Key}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("selected_foreign_key")
    public ItemList<DatabaseColumn> getSelectedForeignKey() { return this.selectedForeignKey; }

    /**
     * Set the {@code selected_foreign_key} property (displayed as {@code User Selected Foreign Key}) of the object.
     * @param selectedForeignKey the value to set
     */
    @JsonProperty("selected_foreign_key")
    public void setSelectedForeignKey(ItemList<DatabaseColumn> selectedForeignKey) { this.selectedForeignKey = selectedForeignKey; }

    /**
     * Retrieve the {@code selected_natural_key} property (displayed as '{@literal User Selected Natural Key}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("selected_natural_key")
    public ItemList<DataItem> getSelectedNaturalKey() { return this.selectedNaturalKey; }

    /**
     * Set the {@code selected_natural_key} property (displayed as {@code User Selected Natural Key}) of the object.
     * @param selectedNaturalKey the value to set
     */
    @JsonProperty("selected_natural_key")
    public void setSelectedNaturalKey(ItemList<DataItem> selectedNaturalKey) { this.selectedNaturalKey = selectedNaturalKey; }

    /**
     * Retrieve the {@code selected_primary_key} property (displayed as '{@literal User Selected Primary Key}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("selected_primary_key")
    public ItemList<DataItem> getSelectedPrimaryKey() { return this.selectedPrimaryKey; }

    /**
     * Set the {@code selected_primary_key} property (displayed as {@code User Selected Primary Key}) of the object.
     * @param selectedPrimaryKey the value to set
     */
    @JsonProperty("selected_primary_key")
    public void setSelectedPrimaryKey(ItemList<DataItem> selectedPrimaryKey) { this.selectedPrimaryKey = selectedPrimaryKey; }

    /**
     * Retrieve the {@code source_mapping_specifications} property (displayed as '{@literal Source Mapping Specifications}') of the object.
     * @return {@code ItemList<MappingSpecification>}
     */
    @JsonProperty("source_mapping_specifications")
    public ItemList<MappingSpecification> getSourceMappingSpecifications() { return this.sourceMappingSpecifications; }

    /**
     * Set the {@code source_mapping_specifications} property (displayed as {@code Source Mapping Specifications}) of the object.
     * @param sourceMappingSpecifications the value to set
     */
    @JsonProperty("source_mapping_specifications")
    public void setSourceMappingSpecifications(ItemList<MappingSpecification> sourceMappingSpecifications) { this.sourceMappingSpecifications = sourceMappingSpecifications; }

    /**
     * Retrieve the {@code table_definitions} property (displayed as '{@literal Table Definitions}') of the object.
     * @return {@code ItemList<TableDefinition>}
     */
    @JsonProperty("table_definitions")
    public ItemList<TableDefinition> getTableDefinitions() { return this.tableDefinitions; }

    /**
     * Set the {@code table_definitions} property (displayed as {@code Table Definitions}) of the object.
     * @param tableDefinitions the value to set
     */
    @JsonProperty("table_definitions")
    public void setTableDefinitions(ItemList<TableDefinition> tableDefinitions) { this.tableDefinitions = tableDefinitions; }

    /**
     * Retrieve the {@code target_mapping_specifications} property (displayed as '{@literal Target Mapping Specifications}') of the object.
     * @return {@code ItemList<MappingSpecification>}
     */
    @JsonProperty("target_mapping_specifications")
    public ItemList<MappingSpecification> getTargetMappingSpecifications() { return this.targetMappingSpecifications; }

    /**
     * Set the {@code target_mapping_specifications} property (displayed as {@code Target Mapping Specifications}) of the object.
     * @param targetMappingSpecifications the value to set
     */
    @JsonProperty("target_mapping_specifications")
    public void setTargetMappingSpecifications(ItemList<MappingSpecification> targetMappingSpecifications) { this.targetMappingSpecifications = targetMappingSpecifications; }

    /**
     * Retrieve the {@code written_by_(design)} property (displayed as '{@literal Written by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(design)")
    public ItemList<InformationAsset> getWrittenByDesign() { return this.writtenByDesign; }

    /**
     * Set the {@code written_by_(design)} property (displayed as {@code Written by (Design)}) of the object.
     * @param writtenByDesign the value to set
     */
    @JsonProperty("written_by_(design)")
    public void setWrittenByDesign(ItemList<InformationAsset> writtenByDesign) { this.writtenByDesign = writtenByDesign; }

    /**
     * Retrieve the {@code written_by_(operational)} property (displayed as '{@literal Written by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(operational)")
    public ItemList<InformationAsset> getWrittenByOperational() { return this.writtenByOperational; }

    /**
     * Set the {@code written_by_(operational)} property (displayed as {@code Written by (Operational)}) of the object.
     * @param writtenByOperational the value to set
     */
    @JsonProperty("written_by_(operational)")
    public void setWrittenByOperational(ItemList<InformationAsset> writtenByOperational) { this.writtenByOperational = writtenByOperational; }

    /**
     * Retrieve the {@code written_by_(static)} property (displayed as '{@literal Written by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(static)")
    public ItemList<InformationAsset> getWrittenByStatic() { return this.writtenByStatic; }

    /**
     * Set the {@code written_by_(static)} property (displayed as {@code Written by (Static)}) of the object.
     * @param writtenByStatic the value to set
     */
    @JsonProperty("written_by_(static)")
    public void setWrittenByStatic(ItemList<InformationAsset> writtenByStatic) { this.writtenByStatic = writtenByStatic; }

    /**
     * Retrieve the {@code written_by_(user_defined)} property (displayed as '{@literal Written by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(user_defined)")
    public ItemList<InformationAsset> getWrittenByUserDefined() { return this.writtenByUserDefined; }

    /**
     * Set the {@code written_by_(user_defined)} property (displayed as {@code Written by (User-Defined)}) of the object.
     * @param writtenByUserDefined the value to set
     */
    @JsonProperty("written_by_(user_defined)")
    public void setWrittenByUserDefined(ItemList<InformationAsset> writtenByUserDefined) { this.writtenByUserDefined = writtenByUserDefined; }

}
