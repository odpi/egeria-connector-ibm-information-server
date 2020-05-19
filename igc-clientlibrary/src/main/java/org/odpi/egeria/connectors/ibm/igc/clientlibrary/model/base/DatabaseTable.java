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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code database_table} asset type in IGC, displayed as '{@literal Database Table}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DatabaseTable.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("database_table")
public class DatabaseTable extends Datagroup {

    @JsonProperty("FKViolationCount")
    protected List<Number> fkviolationcount;

    @JsonProperty("PKDuplicateCount")
    protected List<Number> pkduplicatecount;

    /**
     * @deprecated No longer applicable from 11.7.1.0 onwards.
     */
    @Deprecated
    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    /**
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     */
    @Deprecated
    @JsonProperty("analysis")
    protected ItemList<TableAnalysis> analysis;

    @JsonProperty("bi_model_collections")
    protected ItemList<BiCollection> biModelCollections;

    @JsonProperty("bi_report_queries")
    protected ItemList<BiReportQuery> biReportQueries;

    @JsonProperty("data_policies")
    protected ItemList<MainObject> dataPolicies;

    @JsonProperty("database_aliases")
    protected ItemList<Datagroup> databaseAliases;

    @JsonProperty("database_columns")
    protected ItemList<DatabaseColumn> databaseColumns;

    @JsonProperty("database_indexes")
    protected ItemList<DatabaseIndex> databaseIndexes;

    @JsonProperty("defined_foreign_key")
    protected ItemList<Reference> definedForeignKey;

    @JsonProperty("defined_non_primary_key")
    protected ItemList<CandidateKey> definedNonPrimaryKey;

    @JsonProperty("defined_primary_key")
    protected ItemList<CandidateKey> definedPrimaryKey;

    @JsonProperty("fieldCount")
    protected List<Number> fieldcount;

    @JsonProperty("implements_design_tables_or_views")
    protected ItemList<Datagroup> implementsDesignTablesOrViews;

    @JsonProperty("implements_logical_entities")
    protected ItemList<LogicalEntity> implementsLogicalEntities;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("indexes")
    protected ItemList<DatabaseIndex> indexes;

    @JsonProperty("mapped_to_physical_objects")
    protected ItemList<PhysicalObject> mappedToPhysicalObjects;

    @JsonProperty("multi_column_analysis")
    protected List<String> multiColumnAnalysis;

    @JsonProperty("nbRecordTested")
    protected List<Number> nbrecordtested;

    @JsonProperty("qualityScore")
    protected String qualityscore;

    @JsonProperty("qualityScore_bubble")
    protected String qualityscoreBubble;

    @JsonProperty("quality_benchmark")
    protected List<Number> qualityBenchmark;

    @JsonProperty("quality_dimension")
    protected ItemList<QualityProblem> qualityDimension;

    @JsonProperty("referenced_by_views")
    protected ItemList<View> referencedByViews;

    @JsonProperty("reviewDate")
    protected List<Date> reviewdate;

    @JsonProperty("rowCount")
    protected List<Number> rowcount;

    @JsonProperty("same_as_data_sources")
    protected ItemList<Datagroup> sameAsDataSources;

    @JsonProperty("selected_foreign_key")
    protected ItemList<DatabaseColumn> selectedForeignKey;

    @JsonProperty("selected_natural_key")
    protected ItemList<DataItem> selectedNaturalKey;

    @JsonProperty("selected_primary_key")
    protected ItemList<DataItem> selectedPrimaryKey;

    @JsonProperty("source_mapping_specifications")
    protected ItemList<MappingSpecification> sourceMappingSpecifications;

    /**
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    protected ItemList<TermAssignment> suggestedTermAssignments;

    @JsonProperty("synchronized_from")
    protected String synchronizedFrom;

    @JsonProperty("table_definitions")
    protected ItemList<TableDefinition> tableDefinitions;

    @JsonProperty("target_mapping_specifications")
    protected ItemList<MappingSpecification> targetMappingSpecifications;

    /**
     * Retrieve the {@code FKViolationCount} property (displayed as '{@literal Foreign Key Violations}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("FKViolationCount")
    public List<Number> getFkviolationcount() { return this.fkviolationcount; }

    /**
     * Set the {@code FKViolationCount} property (displayed as {@code Foreign Key Violations}) of the object.
     * @param fkviolationcount the value to set
     */
    @JsonProperty("FKViolationCount")
    public void setFkviolationcount(List<Number> fkviolationcount) { this.fkviolationcount = fkviolationcount; }

    /**
     * Retrieve the {@code PKDuplicateCount} property (displayed as '{@literal Primary Key Duplicates}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("PKDuplicateCount")
    public List<Number> getPkduplicatecount() { return this.pkduplicatecount; }

    /**
     * Set the {@code PKDuplicateCount} property (displayed as {@code Primary Key Duplicates}) of the object.
     * @param pkduplicatecount the value to set
     */
    @JsonProperty("PKDuplicateCount")
    public void setPkduplicatecount(List<Number> pkduplicatecount) { this.pkduplicatecount = pkduplicatecount; }

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @deprecated No longer applicable from 11.7.1.0 onwards.
     * @return {@code String}
     */
    @Deprecated
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @deprecated No longer applicable from 11.7.1.0 onwards.
     * @param aliasBusinessName the value to set
     */
    @Deprecated
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code analysis} property (displayed as '{@literal Analysis}') of the object.
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     * @return {@code ItemList<TableAnalysis>}
     */
    @Deprecated
    @JsonProperty("analysis")
    public ItemList<TableAnalysis> getAnalysis() { return this.analysis; }

    /**
     * Set the {@code analysis} property (displayed as {@code Analysis}) of the object.
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     * @param analysis the value to set
     */
    @Deprecated
    @JsonProperty("analysis")
    public void setAnalysis(ItemList<TableAnalysis> analysis) { this.analysis = analysis; }

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
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("data_policies")
    public ItemList<MainObject> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<MainObject> dataPolicies) { this.dataPolicies = dataPolicies; }

    /**
     * Retrieve the {@code database_aliases} property (displayed as '{@literal Alias}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("database_aliases")
    public ItemList<Datagroup> getDatabaseAliases() { return this.databaseAliases; }

    /**
     * Set the {@code database_aliases} property (displayed as {@code Alias}) of the object.
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
     * Retrieve the {@code database_indexes} property (displayed as '{@literal Indexes}') of the object.
     * @return {@code ItemList<DatabaseIndex>}
     */
    @JsonProperty("database_indexes")
    public ItemList<DatabaseIndex> getDatabaseIndexes() { return this.databaseIndexes; }

    /**
     * Set the {@code database_indexes} property (displayed as {@code Indexes}) of the object.
     * @param databaseIndexes the value to set
     */
    @JsonProperty("database_indexes")
    public void setDatabaseIndexes(ItemList<DatabaseIndex> databaseIndexes) { this.databaseIndexes = databaseIndexes; }

    /**
     * Retrieve the {@code defined_foreign_key} property (displayed as '{@literal Defined Foreign Key}') of the object.
     * For earlier releases this will return an {@code ItemList<DatabaseColumn>} while for newer releases (11.5.0.2sp5
     * onwards and 11.7.1.0 onwards) this will return an {@code ItemList<ForeignKey>}.
     *
     * @return {@code ItemList<Reference>}
     */
    @JsonProperty("defined_foreign_key")
    public ItemList<Reference> getDefinedForeignKey() { return this.definedForeignKey; }

    /**
     * Set the {@code defined_foreign_key} property (displayed as {@code Defined Foreign Key}) of the object.
     * For earlier releases this required an {@code ItemList<DatabaseColumn>} while for newer releases (11.5.0.2sp5
     * onwards and 11.7.1.0 onwards) requires an {@code ItemList<ForeignKey>}.
     *
     * @param definedForeignKey the value to set
     */
    @JsonProperty("defined_foreign_key")
    public void setDefinedForeignKey(ItemList<Reference> definedForeignKey) { this.definedForeignKey = definedForeignKey; }

    /**
     * Retrieve the {@code defined_non_primary_key} property (displayed as '{@literal Defined Non Primary Key}') of the object.
     * @return {@code ItemList<CandidateKey>}
     */
    @JsonProperty("defined_non_primary_key")
    public ItemList<CandidateKey> getDefinedNonPrimaryKey() { return this.definedNonPrimaryKey; }

    /**
     * Set the {@code defined_non_primary_key} property (displayed as {@code Defined Non Primary Key}) of the object.
     * @param definedNonPrimaryKey the value to set
     */
    @JsonProperty("defined_non_primary_key")
    public void setDefinedNonPrimaryKey(ItemList<CandidateKey> definedNonPrimaryKey) { this.definedNonPrimaryKey = definedNonPrimaryKey; }

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
     * Retrieve the {@code fieldCount} property (displayed as '{@literal Number of Fields}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("fieldCount")
    public List<Number> getFieldcount() { return this.fieldcount; }

    /**
     * Set the {@code fieldCount} property (displayed as {@code Number of Fields}) of the object.
     * @param fieldcount the value to set
     */
    @JsonProperty("fieldCount")
    public void setFieldcount(List<Number> fieldcount) { this.fieldcount = fieldcount; }

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
     * Retrieve the {@code nbRecordTested} property (displayed as '{@literal Number of Records Tested}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("nbRecordTested")
    public List<Number> getNbrecordtested() { return this.nbrecordtested; }

    /**
     * Set the {@code nbRecordTested} property (displayed as {@code Number of Records Tested}) of the object.
     * @param nbrecordtested the value to set
     */
    @JsonProperty("nbRecordTested")
    public void setNbrecordtested(List<Number> nbrecordtested) { this.nbrecordtested = nbrecordtested; }

    /**
     * Retrieve the {@code qualityScore} property (displayed as '{@literal Quality Score}') of the object.
     * @return {@code String}
     */
    @JsonProperty("qualityScore")
    public String getQualityscore() { return this.qualityscore; }

    /**
     * Set the {@code qualityScore} property (displayed as {@code Quality Score}) of the object.
     * @param qualityscore the value to set
     */
    @JsonProperty("qualityScore")
    public void setQualityscore(String qualityscore) { this.qualityscore = qualityscore; }

    /**
     * Retrieve the {@code qualityScore_bubble} property (displayed as '{@literal Quality Score}') of the object.
     * @return {@code String}
     */
    @JsonProperty("qualityScore_bubble")
    public String getQualityscoreBubble() { return this.qualityscoreBubble; }

    /**
     * Set the {@code qualityScore_bubble} property (displayed as {@code Quality Score}) of the object.
     * @param qualityscoreBubble the value to set
     */
    @JsonProperty("qualityScore_bubble")
    public void setQualityscoreBubble(String qualityscoreBubble) { this.qualityscoreBubble = qualityscoreBubble; }

    /**
     * Retrieve the {@code quality_benchmark} property (displayed as '{@literal Quality Benchmark}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("quality_benchmark")
    public List<Number> getQualityBenchmark() { return this.qualityBenchmark; }

    /**
     * Set the {@code quality_benchmark} property (displayed as {@code Quality Benchmark}) of the object.
     * @param qualityBenchmark the value to set
     */
    @JsonProperty("quality_benchmark")
    public void setQualityBenchmark(List<Number> qualityBenchmark) { this.qualityBenchmark = qualityBenchmark; }

    /**
     * Retrieve the {@code quality_dimension} property (displayed as '{@literal Quality Dimensions}') of the object.
     * @return {@code ItemList<QualityProblem>}
     */
    @JsonProperty("quality_dimension")
    public ItemList<QualityProblem> getQualityDimension() { return this.qualityDimension; }

    /**
     * Set the {@code quality_dimension} property (displayed as {@code Quality Dimensions}) of the object.
     * @param qualityDimension the value to set
     */
    @JsonProperty("quality_dimension")
    public void setQualityDimension(ItemList<QualityProblem> qualityDimension) { this.qualityDimension = qualityDimension; }

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
     * Retrieve the {@code reviewDate} property (displayed as '{@literal Review Date}') of the object.
     * @return {@code List<Date>}
     */
    @JsonProperty("reviewDate")
    public List<Date> getReviewdate() { return this.reviewdate; }

    /**
     * Set the {@code reviewDate} property (displayed as {@code Review Date}) of the object.
     * @param reviewdate the value to set
     */
    @JsonProperty("reviewDate")
    public void setReviewdate(List<Date> reviewdate) { this.reviewdate = reviewdate; }

    /**
     * Retrieve the {@code rowCount} property (displayed as '{@literal Number of Rows}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("rowCount")
    public List<Number> getRowcount() { return this.rowcount; }

    /**
     * Set the {@code rowCount} property (displayed as {@code Number of Rows}) of the object.
     * @param rowcount the value to set
     */
    @JsonProperty("rowCount")
    public void setRowcount(List<Number> rowcount) { this.rowcount = rowcount; }

    /**
     * Retrieve the {@code same_as_data_sources} property (displayed as '{@literal Same as Data Sources}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("same_as_data_sources")
    public ItemList<Datagroup> getSameAsDataSources() { return this.sameAsDataSources; }

    /**
     * Set the {@code same_as_data_sources} property (displayed as {@code Same as Data Sources}) of the object.
     * @param sameAsDataSources the value to set
     */
    @JsonProperty("same_as_data_sources")
    public void setSameAsDataSources(ItemList<Datagroup> sameAsDataSources) { this.sameAsDataSources = sameAsDataSources; }

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
     * Retrieve the {@code suggested_term_assignments} property (displayed as '{@literal Suggested Term Assignments}') of the object.
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     * @return {@code ItemList<TermAssignment>}
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    public ItemList<TermAssignment> getSuggestedTermAssignments() { return this.suggestedTermAssignments; }

    /**
     * Set the {@code suggested_term_assignments} property (displayed as {@code Suggested Term Assignments}) of the object.
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     * @param suggestedTermAssignments the value to set
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    public void setSuggestedTermAssignments(ItemList<TermAssignment> suggestedTermAssignments) { this.suggestedTermAssignments = suggestedTermAssignments; }

    /**
     * Retrieve the {@code synchronized_from} property (displayed as '{@literal Synchronized From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("synchronized_from")
    public String getSynchronizedFrom() { return this.synchronizedFrom; }

    /**
     * Set the {@code synchronized_from} property (displayed as {@code Synchronized From}) of the object.
     * @param synchronizedFrom the value to set
     */
    @JsonProperty("synchronized_from")
    public void setSynchronizedFrom(String synchronizedFrom) { this.synchronizedFrom = synchronizedFrom; }

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

}
