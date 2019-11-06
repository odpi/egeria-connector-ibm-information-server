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
 * POJO for the {@code database_column} asset type in IGC, displayed as '{@literal Database Column}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DatabaseColumn.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("database_column")
public class DatabaseColumn extends Classificationenabledgroup {

    /**
     * No longer applicable from 11.5.0.2sp3 onwards.
     */
    @Deprecated
    @JsonProperty("analysis")
    protected ItemList<ColumnAnalysis> analysis;

    @JsonProperty("averageValue")
    protected List<String> averagevalue;

    @JsonProperty("bi_collection_members")
    protected ItemList<BiCollectionMember> biCollectionMembers;

    @JsonProperty("bi_report_query_items")
    protected ItemList<Reportobject> biReportQueryItems;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("column_definitions")
    protected ItemList<ColumnDefinition> columnDefinitions;

    @JsonProperty("constantFlag")
    protected Boolean constantflag;

    @JsonProperty("data_policies")
    protected ItemList<DataPolicy> dataPolicies;

    @JsonProperty("data_rule_definitions")
    protected ItemList<NonPublishedDataRuleDefinition> dataRuleDefinitions;

    @JsonProperty("data_rule_set_definitions")
    protected ItemList<NonPublishedDataRuleSet> dataRuleSetDefinitions;

    @JsonProperty("data_rule_sets")
    protected ItemList<DataRuleSet> dataRuleSets;

    @JsonProperty("data_rules")
    protected ItemList<DataRule> dataRules;

    @JsonProperty("database_alias")
    protected DatabaseAlias databaseAlias;

    @JsonProperty("database_domains")
    protected DatabaseDomain databaseDomains;

    @JsonProperty("database_indexes")
    protected ItemList<DatabaseIndex> databaseIndexes;

    @JsonProperty("database_table")
    protected DatabaseTable databaseTable;

    @JsonProperty("database_table_or_view")
    protected Datagroup databaseTableOrView;

    @JsonProperty("defined_foreign_key")
    protected Boolean definedForeignKey;

    @JsonProperty("defined_foreign_key_referenced")
    protected ItemList<DataItem> definedForeignKeyReferenced;

    @JsonProperty("defined_foreign_key_references")
    protected ItemList<DatabaseColumn> definedForeignKeyReferences;

    @JsonProperty("defined_primary_key")
    protected ItemList<CandidateKey> definedPrimaryKey;

    @JsonProperty("detected_classifications")
    protected ItemList<Classification> detectedClassifications;

    @JsonProperty("domainType")
    protected List<String> domaintype;

    @JsonProperty("has_DataClassifications")
    protected ItemList<Classification> hasDataclassifications;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("implements_design_columns")
    protected ItemList<DesignColumn> implementsDesignColumns;

    @JsonProperty("implements_entity_attributes")
    protected ItemList<EntityAttribute> implementsEntityAttributes;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("index")
    protected ItemList<DatabaseIndex> index;

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
    @JsonProperty("inferredDataType")
    protected List<String> inferreddatatype;

    @JsonProperty("inferredFormat")
    protected List<String> inferredformat;

    @JsonProperty("inferredLength")
    protected List<Number> inferredlength;

    @JsonProperty("inferredPrecision")
    protected List<Number> inferredprecision;

    @JsonProperty("inferredScale")
    protected List<Number> inferredscale;

    @JsonProperty("isInferredForeignKey")
    protected Boolean isinferredforeignkey;

    @JsonProperty("isInferredPrimaryKey")
    protected Boolean isinferredprimarykey;

    @JsonProperty("mapped_to_physical_object_attributes")
    protected ItemList<PhysicalObjectAttribute> mappedToPhysicalObjectAttributes;

    @JsonProperty("nbRecordsTested")
    protected List<Number> nbrecordstested;

    @JsonProperty("nullabilityFlag")
    protected Boolean nullabilityflag;

    @JsonProperty("numberCompleteValues")
    protected List<Number> numbercompletevalues;

    @JsonProperty("numberDistinctValues")
    protected List<Number> numberdistinctvalues;

    @JsonProperty("numberEmptyValues")
    protected List<Number> numberemptyvalues;

    @JsonProperty("numberFormats")
    protected List<Number> numberformats;

    @JsonProperty("numberNullValues")
    protected List<Number> numbernullvalues;

    @JsonProperty("numberValidValues")
    protected List<Number> numbervalidvalues;

    @JsonProperty("numberZeroValues")
    protected List<Number> numberzerovalues;

    @JsonProperty("occurs")
    protected List<String> occurs;

    @JsonProperty("qualityScore")
    protected String qualityscore;

    @JsonProperty("read_by_(design)")
    protected ItemList<InformationAsset> readByDesign;

    @JsonProperty("read_by_(operational)")
    protected ItemList<InformationAsset> readByOperational;

    @JsonProperty("read_by_(static)")
    protected ItemList<InformationAsset> readByStatic;

    @JsonProperty("read_by_(user_defined)")
    protected ItemList<InformationAsset> readByUserDefined;

    @JsonProperty("referenced_by_database_columns")
    protected ItemList<DatabaseColumn> referencedByDatabaseColumns;

    @JsonProperty("references_database_columns")
    protected ItemList<DatabaseColumn> referencesDatabaseColumns;

    /**
     * No longer applicable from 11.5.0.1ru5 onwards.
     * @see #sameAsDataSources
     */
    @Deprecated
    @JsonProperty("same_as_database_columns")
    protected ItemList<DatabaseColumn> sameAsDatabaseColumns;

    @JsonProperty("same_as_data_sources")
    protected ItemList<DataItem> sameAsDataSources;

    @JsonProperty("selected_classification")
    protected DataClass selectedClassification;

    @JsonProperty("selected_foreign_key")
    protected Boolean selectedForeignKey;

    @JsonProperty("selected_foreign_key_referenced")
    protected ItemList<DatabaseColumn> selectedForeignKeyReferenced;

    @JsonProperty("selected_foreign_key_references")
    protected ItemList<DatabaseColumn> selectedForeignKeyReferences;

    @JsonProperty("selected_natural_key")
    protected Boolean selectedNaturalKey;

    @JsonProperty("selected_primary_key")
    protected Boolean selectedPrimaryKey;

    @JsonProperty("start_end_columns")
    protected String startEndColumns;

    @JsonProperty("suggested_term_assignments")
    protected ItemList<TermAssignment> suggestedTermAssignments;

    @JsonProperty("uniqueFlag")
    protected Boolean uniqueflag;

    @JsonProperty("validity_tables")
    protected ItemList<ValidityTable> validityTables;

    @JsonProperty("view")
    protected ItemList<View> view;

    @JsonProperty("written_by_(design)")
    protected ItemList<InformationAsset> writtenByDesign;

    @JsonProperty("written_by_(operational)")
    protected ItemList<InformationAsset> writtenByOperational;

    @JsonProperty("written_by_(static)")
    protected ItemList<InformationAsset> writtenByStatic;

    @JsonProperty("written_by_(user_defined)")
    protected ItemList<InformationAsset> writtenByUserDefined;

    /**
     * Retrieve the {@code analysis} property (displayed as '{@literal Analysis}') of the object.
     * No longer applicable from 11.5.0.2sp3 onwards.
     *
     * @return {@code ItemList<ColumnAnalysis>}
     */
    @Deprecated
    @JsonProperty("analysis")
    public ItemList<ColumnAnalysis> getAnalysis() { return this.analysis; }

    /**
     * Set the {@code analysis} property (displayed as {@code Analysis}) of the object.
     * No longer applicable from 11.5.0.2sp3 onwards.
     *
     * @param analysis the value to set
     */
    @Deprecated
    @JsonProperty("analysis")
    public void setAnalysis(ItemList<ColumnAnalysis> analysis) { this.analysis = analysis; }

    /**
     * Retrieve the {@code averageValue} property (displayed as '{@literal Average Value}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("averageValue")
    public List<String> getAveragevalue() { return this.averagevalue; }

    /**
     * Set the {@code averageValue} property (displayed as {@code Average Value}) of the object.
     * @param averagevalue the value to set
     */
    @JsonProperty("averageValue")
    public void setAveragevalue(List<String> averagevalue) { this.averagevalue = averagevalue; }

    /**
     * Retrieve the {@code bi_collection_members} property (displayed as '{@literal BI Collection Members}') of the object.
     * @return {@code ItemList<BiCollectionMember>}
     */
    @JsonProperty("bi_collection_members")
    public ItemList<BiCollectionMember> getBiCollectionMembers() { return this.biCollectionMembers; }

    /**
     * Set the {@code bi_collection_members} property (displayed as {@code BI Collection Members}) of the object.
     * @param biCollectionMembers the value to set
     */
    @JsonProperty("bi_collection_members")
    public void setBiCollectionMembers(ItemList<BiCollectionMember> biCollectionMembers) { this.biCollectionMembers = biCollectionMembers; }

    /**
     * Retrieve the {@code bi_report_query_items} property (displayed as '{@literal BI Report Query Items}') of the object.
     * @return {@code ItemList<Reportobject>}
     */
    @JsonProperty("bi_report_query_items")
    public ItemList<Reportobject> getBiReportQueryItems() { return this.biReportQueryItems; }

    /**
     * Set the {@code bi_report_query_items} property (displayed as {@code BI Report Query Items}) of the object.
     * @param biReportQueryItems the value to set
     */
    @JsonProperty("bi_report_query_items")
    public void setBiReportQueryItems(ItemList<Reportobject> biReportQueryItems) { this.biReportQueryItems = biReportQueryItems; }

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
     * Retrieve the {@code column_definitions} property (displayed as '{@literal Column Definitions}') of the object.
     * @return {@code ItemList<ColumnDefinition>}
     */
    @JsonProperty("column_definitions")
    public ItemList<ColumnDefinition> getColumnDefinitions() { return this.columnDefinitions; }

    /**
     * Set the {@code column_definitions} property (displayed as {@code Column Definitions}) of the object.
     * @param columnDefinitions the value to set
     */
    @JsonProperty("column_definitions")
    public void setColumnDefinitions(ItemList<ColumnDefinition> columnDefinitions) { this.columnDefinitions = columnDefinitions; }

    /**
     * Retrieve the {@code constantFlag} property (displayed as '{@literal Include Constant Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("constantFlag")
    public Boolean getConstantflag() { return this.constantflag; }

    /**
     * Set the {@code constantFlag} property (displayed as {@code Include Constant Values}) of the object.
     * @param constantflag the value to set
     */
    @JsonProperty("constantFlag")
    public void setConstantflag(Boolean constantflag) { this.constantflag = constantflag; }

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
     * Retrieve the {@code data_rule_definitions} property (displayed as '{@literal Data Rule Definitions}') of the object.
     * @return {@code ItemList<NonPublishedDataRuleDefinition>}
     */
    @JsonProperty("data_rule_definitions")
    public ItemList<NonPublishedDataRuleDefinition> getDataRuleDefinitions() { return this.dataRuleDefinitions; }

    /**
     * Set the {@code data_rule_definitions} property (displayed as {@code Data Rule Definitions}) of the object.
     * @param dataRuleDefinitions the value to set
     */
    @JsonProperty("data_rule_definitions")
    public void setDataRuleDefinitions(ItemList<NonPublishedDataRuleDefinition> dataRuleDefinitions) { this.dataRuleDefinitions = dataRuleDefinitions; }

    /**
     * Retrieve the {@code data_rule_set_definitions} property (displayed as '{@literal Data Rule Set Definitions}') of the object.
     * @return {@code ItemList<NonPublishedDataRuleSet>}
     */
    @JsonProperty("data_rule_set_definitions")
    public ItemList<NonPublishedDataRuleSet> getDataRuleSetDefinitions() { return this.dataRuleSetDefinitions; }

    /**
     * Set the {@code data_rule_set_definitions} property (displayed as {@code Data Rule Set Definitions}) of the object.
     * @param dataRuleSetDefinitions the value to set
     */
    @JsonProperty("data_rule_set_definitions")
    public void setDataRuleSetDefinitions(ItemList<NonPublishedDataRuleSet> dataRuleSetDefinitions) { this.dataRuleSetDefinitions = dataRuleSetDefinitions; }

    /**
     * Retrieve the {@code data_rule_sets} property (displayed as '{@literal Data Rule Sets}') of the object.
     * @return {@code ItemList<DataRuleSet>}
     */
    @JsonProperty("data_rule_sets")
    public ItemList<DataRuleSet> getDataRuleSets() { return this.dataRuleSets; }

    /**
     * Set the {@code data_rule_sets} property (displayed as {@code Data Rule Sets}) of the object.
     * @param dataRuleSets the value to set
     */
    @JsonProperty("data_rule_sets")
    public void setDataRuleSets(ItemList<DataRuleSet> dataRuleSets) { this.dataRuleSets = dataRuleSets; }

    /**
     * Retrieve the {@code data_rules} property (displayed as '{@literal Data Rules}') of the object.
     * @return {@code ItemList<DataRule>}
     */
    @JsonProperty("data_rules")
    public ItemList<DataRule> getDataRules() { return this.dataRules; }

    /**
     * Set the {@code data_rules} property (displayed as {@code Data Rules}) of the object.
     * @param dataRules the value to set
     */
    @JsonProperty("data_rules")
    public void setDataRules(ItemList<DataRule> dataRules) { this.dataRules = dataRules; }

    /**
     * Retrieve the {@code database_alias} property (displayed as '{@literal Database Alias}') of the object.
     * @return {@code DatabaseAlias}
     */
    @JsonProperty("database_alias")
    public DatabaseAlias getDatabaseAlias() { return this.databaseAlias; }

    /**
     * Set the {@code database_alias} property (displayed as {@code Database Alias}) of the object.
     * @param databaseAlias the value to set
     */
    @JsonProperty("database_alias")
    public void setDatabaseAlias(DatabaseAlias databaseAlias) { this.databaseAlias = databaseAlias; }

    /**
     * Retrieve the {@code database_domains} property (displayed as '{@literal Database Domains}') of the object.
     * @return {@code DatabaseDomain}
     */
    @JsonProperty("database_domains")
    public DatabaseDomain getDatabaseDomains() { return this.databaseDomains; }

    /**
     * Set the {@code database_domains} property (displayed as {@code Database Domains}) of the object.
     * @param databaseDomains the value to set
     */
    @JsonProperty("database_domains")
    public void setDatabaseDomains(DatabaseDomain databaseDomains) { this.databaseDomains = databaseDomains; }

    /**
     * Retrieve the {@code database_indexes} property (displayed as '{@literal Index}') of the object.
     * @return {@code ItemList<DatabaseIndex>}
     */
    @JsonProperty("database_indexes")
    public ItemList<DatabaseIndex> getDatabaseIndexes() { return this.databaseIndexes; }

    /**
     * Set the {@code database_indexes} property (displayed as {@code Index}) of the object.
     * @param databaseIndexes the value to set
     */
    @JsonProperty("database_indexes")
    public void setDatabaseIndexes(ItemList<DatabaseIndex> databaseIndexes) { this.databaseIndexes = databaseIndexes; }

    /**
     * Retrieve the {@code database_table} property (displayed as '{@literal Database Table}') of the object.
     * @return {@code DatabaseTable}
     */
    @JsonProperty("database_table")
    public DatabaseTable getDatabaseTable() { return this.databaseTable; }

    /**
     * Set the {@code database_table} property (displayed as {@code Database Table}) of the object.
     * @param databaseTable the value to set
     */
    @JsonProperty("database_table")
    public void setDatabaseTable(DatabaseTable databaseTable) { this.databaseTable = databaseTable; }

    /**
     * Retrieve the {@code database_table_or_view} property (displayed as '{@literal Database Table or View}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("database_table_or_view")
    public Datagroup getDatabaseTableOrView() { return this.databaseTableOrView; }

    /**
     * Set the {@code database_table_or_view} property (displayed as {@code Database Table or View}) of the object.
     * @param databaseTableOrView the value to set
     */
    @JsonProperty("database_table_or_view")
    public void setDatabaseTableOrView(Datagroup databaseTableOrView) { this.databaseTableOrView = databaseTableOrView; }

    /**
     * Retrieve the {@code defined_foreign_key} property (displayed as '{@literal Defined Foreign Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("defined_foreign_key")
    public Boolean getDefinedForeignKey() { return this.definedForeignKey; }

    /**
     * Set the {@code defined_foreign_key} property (displayed as {@code Defined Foreign Key}) of the object.
     * @param definedForeignKey the value to set
     */
    @JsonProperty("defined_foreign_key")
    public void setDefinedForeignKey(Boolean definedForeignKey) { this.definedForeignKey = definedForeignKey; }

    /**
     * Retrieve the {@code defined_foreign_key_referenced} property (displayed as '{@literal Defined Foreign Key Referenced}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("defined_foreign_key_referenced")
    public ItemList<DataItem> getDefinedForeignKeyReferenced() { return this.definedForeignKeyReferenced; }

    /**
     * Set the {@code defined_foreign_key_referenced} property (displayed as {@code Defined Foreign Key Referenced}) of the object.
     * @param definedForeignKeyReferenced the value to set
     */
    @JsonProperty("defined_foreign_key_referenced")
    public void setDefinedForeignKeyReferenced(ItemList<DataItem> definedForeignKeyReferenced) { this.definedForeignKeyReferenced = definedForeignKeyReferenced; }

    /**
     * Retrieve the {@code defined_foreign_key_references} property (displayed as '{@literal Defined Foreign Key References}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("defined_foreign_key_references")
    public ItemList<DatabaseColumn> getDefinedForeignKeyReferences() { return this.definedForeignKeyReferences; }

    /**
     * Set the {@code defined_foreign_key_references} property (displayed as {@code Defined Foreign Key References}) of the object.
     * @param definedForeignKeyReferences the value to set
     */
    @JsonProperty("defined_foreign_key_references")
    public void setDefinedForeignKeyReferences(ItemList<DatabaseColumn> definedForeignKeyReferences) { this.definedForeignKeyReferences = definedForeignKeyReferences; }

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
     * Retrieve the {@code detected_classifications} property (displayed as '{@literal Detected Data Classifications}') of the object.
     * @return {@code ItemList<Classification>}
     */
    @JsonProperty("detected_classifications")
    public ItemList<Classification> getDetectedClassifications() { return this.detectedClassifications; }

    /**
     * Set the {@code detected_classifications} property (displayed as {@code Detected Data Classifications}) of the object.
     * @param detectedClassifications the value to set
     */
    @JsonProperty("detected_classifications")
    public void setDetectedClassifications(ItemList<Classification> detectedClassifications) { this.detectedClassifications = detectedClassifications; }

    /**
     * Retrieve the {@code domainType} property (displayed as '{@literal Domain}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("domainType")
    public List<String> getDomaintype() { return this.domaintype; }

    /**
     * Set the {@code domainType} property (displayed as {@code Domain}) of the object.
     * @param domaintype the value to set
     */
    @JsonProperty("domainType")
    public void setDomaintype(List<String> domaintype) { this.domaintype = domaintype; }

    /**
     * Retrieve the {@code has_DataClassifications} property (displayed as '{@literal Detected data Classifications}') of the object.
     * @return {@code ItemList<Classification>}
     */
    @JsonProperty("has_DataClassifications")
    public ItemList<Classification> getHasDataclassifications() { return this.hasDataclassifications; }

    /**
     * Set the {@code has_DataClassifications} property (displayed as {@code Detected data Classifications}) of the object.
     * @param hasDataclassifications the value to set
     */
    @JsonProperty("has_DataClassifications")
    public void setHasDataclassifications(ItemList<Classification> hasDataclassifications) { this.hasDataclassifications = hasDataclassifications; }

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
     * Retrieve the {@code implements_design_columns} property (displayed as '{@literal Implements Design Columns}') of the object.
     * @return {@code ItemList<DesignColumn>}
     */
    @JsonProperty("implements_design_columns")
    public ItemList<DesignColumn> getImplementsDesignColumns() { return this.implementsDesignColumns; }

    /**
     * Set the {@code implements_design_columns} property (displayed as {@code Implements Design Columns}) of the object.
     * @param implementsDesignColumns the value to set
     */
    @JsonProperty("implements_design_columns")
    public void setImplementsDesignColumns(ItemList<DesignColumn> implementsDesignColumns) { this.implementsDesignColumns = implementsDesignColumns; }

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
     * Retrieve the {@code index} property (displayed as '{@literal Index}') of the object.
     * @return {@code ItemList<DatabaseIndex>}
     */
    @JsonProperty("index")
    public ItemList<DatabaseIndex> getIndex() { return this.index; }

    /**
     * Set the {@code index} property (displayed as {@code Index}) of the object.
     * @param index the value to set
     */
    @JsonProperty("index")
    public void setIndex(ItemList<DatabaseIndex> index) { this.index = index; }

    /**
     * Retrieve the {@code inferredDataType} property (displayed as '{@literal Inferred Data Type}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("inferredDataType")
    public List<String> getInferreddatatype() { return this.inferreddatatype; }

    /**
     * Set the {@code inferredDataType} property (displayed as {@code Inferred Data Type}) of the object.
     * @param inferreddatatype the value to set
     */
    @JsonProperty("inferredDataType")
    public void setInferreddatatype(List<String> inferreddatatype) { this.inferreddatatype = inferreddatatype; }

    /**
     * Retrieve the {@code inferredFormat} property (displayed as '{@literal Inferred Format}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("inferredFormat")
    public List<String> getInferredformat() { return this.inferredformat; }

    /**
     * Set the {@code inferredFormat} property (displayed as {@code Inferred Format}) of the object.
     * @param inferredformat the value to set
     */
    @JsonProperty("inferredFormat")
    public void setInferredformat(List<String> inferredformat) { this.inferredformat = inferredformat; }

    /**
     * Retrieve the {@code inferredLength} property (displayed as '{@literal Inferred Length}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("inferredLength")
    public List<Number> getInferredlength() { return this.inferredlength; }

    /**
     * Set the {@code inferredLength} property (displayed as {@code Inferred Length}) of the object.
     * @param inferredlength the value to set
     */
    @JsonProperty("inferredLength")
    public void setInferredlength(List<Number> inferredlength) { this.inferredlength = inferredlength; }

    /**
     * Retrieve the {@code inferredPrecision} property (displayed as '{@literal Inferred Precision}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("inferredPrecision")
    public List<Number> getInferredprecision() { return this.inferredprecision; }

    /**
     * Set the {@code inferredPrecision} property (displayed as {@code Inferred Precision}) of the object.
     * @param inferredprecision the value to set
     */
    @JsonProperty("inferredPrecision")
    public void setInferredprecision(List<Number> inferredprecision) { this.inferredprecision = inferredprecision; }

    /**
     * Retrieve the {@code inferredScale} property (displayed as '{@literal Inferred Scale}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("inferredScale")
    public List<Number> getInferredscale() { return this.inferredscale; }

    /**
     * Set the {@code inferredScale} property (displayed as {@code Inferred Scale}) of the object.
     * @param inferredscale the value to set
     */
    @JsonProperty("inferredScale")
    public void setInferredscale(List<Number> inferredscale) { this.inferredscale = inferredscale; }

    /**
     * Retrieve the {@code isInferredForeignKey} property (displayed as '{@literal Inferred Foreign Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("isInferredForeignKey")
    public Boolean getIsinferredforeignkey() { return this.isinferredforeignkey; }

    /**
     * Set the {@code isInferredForeignKey} property (displayed as {@code Inferred Foreign Key}) of the object.
     * @param isinferredforeignkey the value to set
     */
    @JsonProperty("isInferredForeignKey")
    public void setIsinferredforeignkey(Boolean isinferredforeignkey) { this.isinferredforeignkey = isinferredforeignkey; }

    /**
     * Retrieve the {@code isInferredPrimaryKey} property (displayed as '{@literal Inferred Primary Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("isInferredPrimaryKey")
    public Boolean getIsinferredprimarykey() { return this.isinferredprimarykey; }

    /**
     * Set the {@code isInferredPrimaryKey} property (displayed as {@code Inferred Primary Key}) of the object.
     * @param isinferredprimarykey the value to set
     */
    @JsonProperty("isInferredPrimaryKey")
    public void setIsinferredprimarykey(Boolean isinferredprimarykey) { this.isinferredprimarykey = isinferredprimarykey; }

    /**
     * Retrieve the {@code mapped_to_physical_object_attributes} property (displayed as '{@literal Mapped to Physical Object Attributes}') of the object.
     * @return {@code ItemList<PhysicalObjectAttribute>}
     */
    @JsonProperty("mapped_to_physical_object_attributes")
    public ItemList<PhysicalObjectAttribute> getMappedToPhysicalObjectAttributes() { return this.mappedToPhysicalObjectAttributes; }

    /**
     * Set the {@code mapped_to_physical_object_attributes} property (displayed as {@code Mapped to Physical Object Attributes}) of the object.
     * @param mappedToPhysicalObjectAttributes the value to set
     */
    @JsonProperty("mapped_to_physical_object_attributes")
    public void setMappedToPhysicalObjectAttributes(ItemList<PhysicalObjectAttribute> mappedToPhysicalObjectAttributes) { this.mappedToPhysicalObjectAttributes = mappedToPhysicalObjectAttributes; }

    /**
     * Retrieve the {@code nbRecordsTested} property (displayed as '{@literal Number of Records Tested}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("nbRecordsTested")
    public List<Number> getNbrecordstested() { return this.nbrecordstested; }

    /**
     * Set the {@code nbRecordsTested} property (displayed as {@code Number of Records Tested}) of the object.
     * @param nbrecordstested the value to set
     */
    @JsonProperty("nbRecordsTested")
    public void setNbrecordstested(List<Number> nbrecordstested) { this.nbrecordstested = nbrecordstested; }

    /**
     * Retrieve the {@code nullabilityFlag} property (displayed as '{@literal Include Null Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("nullabilityFlag")
    public Boolean getNullabilityflag() { return this.nullabilityflag; }

    /**
     * Set the {@code nullabilityFlag} property (displayed as {@code Include Null Values}) of the object.
     * @param nullabilityflag the value to set
     */
    @JsonProperty("nullabilityFlag")
    public void setNullabilityflag(Boolean nullabilityflag) { this.nullabilityflag = nullabilityflag; }

    /**
     * Retrieve the {@code numberCompleteValues} property (displayed as '{@literal Number of Complete Values}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("numberCompleteValues")
    public List<Number> getNumbercompletevalues() { return this.numbercompletevalues; }

    /**
     * Set the {@code numberCompleteValues} property (displayed as {@code Number of Complete Values}) of the object.
     * @param numbercompletevalues the value to set
     */
    @JsonProperty("numberCompleteValues")
    public void setNumbercompletevalues(List<Number> numbercompletevalues) { this.numbercompletevalues = numbercompletevalues; }

    /**
     * Retrieve the {@code numberDistinctValues} property (displayed as '{@literal Number of Distinct Values}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("numberDistinctValues")
    public List<Number> getNumberdistinctvalues() { return this.numberdistinctvalues; }

    /**
     * Set the {@code numberDistinctValues} property (displayed as {@code Number of Distinct Values}) of the object.
     * @param numberdistinctvalues the value to set
     */
    @JsonProperty("numberDistinctValues")
    public void setNumberdistinctvalues(List<Number> numberdistinctvalues) { this.numberdistinctvalues = numberdistinctvalues; }

    /**
     * Retrieve the {@code numberEmptyValues} property (displayed as '{@literal Number of Empty Values}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("numberEmptyValues")
    public List<Number> getNumberemptyvalues() { return this.numberemptyvalues; }

    /**
     * Set the {@code numberEmptyValues} property (displayed as {@code Number of Empty Values}) of the object.
     * @param numberemptyvalues the value to set
     */
    @JsonProperty("numberEmptyValues")
    public void setNumberemptyvalues(List<Number> numberemptyvalues) { this.numberemptyvalues = numberemptyvalues; }

    /**
     * Retrieve the {@code numberFormats} property (displayed as '{@literal Number of Distinct Formats}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("numberFormats")
    public List<Number> getNumberformats() { return this.numberformats; }

    /**
     * Set the {@code numberFormats} property (displayed as {@code Number of Distinct Formats}) of the object.
     * @param numberformats the value to set
     */
    @JsonProperty("numberFormats")
    public void setNumberformats(List<Number> numberformats) { this.numberformats = numberformats; }

    /**
     * Retrieve the {@code numberNullValues} property (displayed as '{@literal Number of Null Values}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("numberNullValues")
    public List<Number> getNumbernullvalues() { return this.numbernullvalues; }

    /**
     * Set the {@code numberNullValues} property (displayed as {@code Number of Null Values}) of the object.
     * @param numbernullvalues the value to set
     */
    @JsonProperty("numberNullValues")
    public void setNumbernullvalues(List<Number> numbernullvalues) { this.numbernullvalues = numbernullvalues; }

    /**
     * Retrieve the {@code numberValidValues} property (displayed as '{@literal Number of Valid Values}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("numberValidValues")
    public List<Number> getNumbervalidvalues() { return this.numbervalidvalues; }

    /**
     * Set the {@code numberValidValues} property (displayed as {@code Number of Valid Values}) of the object.
     * @param numbervalidvalues the value to set
     */
    @JsonProperty("numberValidValues")
    public void setNumbervalidvalues(List<Number> numbervalidvalues) { this.numbervalidvalues = numbervalidvalues; }

    /**
     * Retrieve the {@code numberZeroValues} property (displayed as '{@literal Number of Zero Values}') of the object.
     * @return {@code List<Number>}
     */
    @JsonProperty("numberZeroValues")
    public List<Number> getNumberzerovalues() { return this.numberzerovalues; }

    /**
     * Set the {@code numberZeroValues} property (displayed as {@code Number of Zero Values}) of the object.
     * @param numberzerovalues the value to set
     */
    @JsonProperty("numberZeroValues")
    public void setNumberzerovalues(List<Number> numberzerovalues) { this.numberzerovalues = numberzerovalues; }

    /**
     * Retrieve the {@code occurs} property (displayed as '{@literal Occurs}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("occurs")
    public List<String> getOccurs() { return this.occurs; }

    /**
     * Set the {@code occurs} property (displayed as {@code Occurs}) of the object.
     * @param occurs the value to set
     */
    @JsonProperty("occurs")
    public void setOccurs(List<String> occurs) { this.occurs = occurs; }

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
     * Retrieve the {@code referenced_by_database_columns} property (displayed as '{@literal Referenced by Database Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("referenced_by_database_columns")
    public ItemList<DatabaseColumn> getReferencedByDatabaseColumns() { return this.referencedByDatabaseColumns; }

    /**
     * Set the {@code referenced_by_database_columns} property (displayed as {@code Referenced by Database Columns}) of the object.
     * @param referencedByDatabaseColumns the value to set
     */
    @JsonProperty("referenced_by_database_columns")
    public void setReferencedByDatabaseColumns(ItemList<DatabaseColumn> referencedByDatabaseColumns) { this.referencedByDatabaseColumns = referencedByDatabaseColumns; }

    /**
     * Retrieve the {@code references_database_columns} property (displayed as '{@literal References Database Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("references_database_columns")
    public ItemList<DatabaseColumn> getReferencesDatabaseColumns() { return this.referencesDatabaseColumns; }

    /**
     * Set the {@code references_database_columns} property (displayed as {@code References Database Columns}) of the object.
     * @param referencesDatabaseColumns the value to set
     */
    @JsonProperty("references_database_columns")
    public void setReferencesDatabaseColumns(ItemList<DatabaseColumn> referencesDatabaseColumns) { this.referencesDatabaseColumns = referencesDatabaseColumns; }

    /**
     * Retrieve the {@code same_as_database_columns} property (displayed as '{@literal Same as Database Columns}') of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @return {@code ItemList<DatabaseColumn>}
     * @see #getSameAsDataSources()
     */
    @Deprecated
    @JsonProperty("same_as_database_columns")
    public ItemList<DatabaseColumn> getSameAsDatabaseColumns() { return this.sameAsDatabaseColumns; }

    /**
     * Set the {@code same_as_database_columns} property (displayed as {@code Same as Database Columns}) of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @param sameAsDatabaseColumns the value to set
     * @see #setSameAsDataSources(ItemList)
     */
    @Deprecated
    @JsonProperty("same_as_database_columns")
    public void setSameAsDatabaseColumns(ItemList<DatabaseColumn> sameAsDatabaseColumns) { this.sameAsDatabaseColumns = sameAsDatabaseColumns; }

    /**
     * Retrieve the {@code same_as_data_sources} property (displayed as '{@literal Same as Data Sources}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("same_as_data_sources")
    public ItemList<DataItem> getSameAsDataSources() { return this.sameAsDataSources; }

    /**
     * Set the {@code same_as_data_sources} property (displayed as {@code Same as Data Sources}) of the object.
     * @param sameAsDataSources the value to set
     */
    @JsonProperty("same_as_data_sources")
    public void setSameAsDataSources(ItemList<DataItem> sameAsDataSources) { this.sameAsDataSources = sameAsDataSources; }

    /**
     * Retrieve the {@code selected_classification} property (displayed as '{@literal Selected Data Classification}') of the object.
     * @return {@code DataClass}
     */
    @JsonProperty("selected_classification")
    public DataClass getSelectedClassification() { return this.selectedClassification; }

    /**
     * Set the {@code selected_classification} property (displayed as {@code Selected Data Classification}) of the object.
     * @param selectedClassification the value to set
     */
    @JsonProperty("selected_classification")
    public void setSelectedClassification(DataClass selectedClassification) { this.selectedClassification = selectedClassification; }

    /**
     * Retrieve the {@code selected_foreign_key} property (displayed as '{@literal User Selected Foreign Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("selected_foreign_key")
    public Boolean getSelectedForeignKey() { return this.selectedForeignKey; }

    /**
     * Set the {@code selected_foreign_key} property (displayed as {@code User Selected Foreign Key}) of the object.
     * @param selectedForeignKey the value to set
     */
    @JsonProperty("selected_foreign_key")
    public void setSelectedForeignKey(Boolean selectedForeignKey) { this.selectedForeignKey = selectedForeignKey; }

    /**
     * Retrieve the {@code selected_foreign_key_referenced} property (displayed as '{@literal User Selected Foreign Key Referenced}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("selected_foreign_key_referenced")
    public ItemList<DatabaseColumn> getSelectedForeignKeyReferenced() { return this.selectedForeignKeyReferenced; }

    /**
     * Set the {@code selected_foreign_key_referenced} property (displayed as {@code User Selected Foreign Key Referenced}) of the object.
     * @param selectedForeignKeyReferenced the value to set
     */
    @JsonProperty("selected_foreign_key_referenced")
    public void setSelectedForeignKeyReferenced(ItemList<DatabaseColumn> selectedForeignKeyReferenced) { this.selectedForeignKeyReferenced = selectedForeignKeyReferenced; }

    /**
     * Retrieve the {@code selected_foreign_key_references} property (displayed as '{@literal User Selected Foreign Key References}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("selected_foreign_key_references")
    public ItemList<DatabaseColumn> getSelectedForeignKeyReferences() { return this.selectedForeignKeyReferences; }

    /**
     * Set the {@code selected_foreign_key_references} property (displayed as {@code User Selected Foreign Key References}) of the object.
     * @param selectedForeignKeyReferences the value to set
     */
    @JsonProperty("selected_foreign_key_references")
    public void setSelectedForeignKeyReferences(ItemList<DatabaseColumn> selectedForeignKeyReferences) { this.selectedForeignKeyReferences = selectedForeignKeyReferences; }

    /**
     * Retrieve the {@code selected_natural_key} property (displayed as '{@literal User Selected Natural Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("selected_natural_key")
    public Boolean getSelectedNaturalKey() { return this.selectedNaturalKey; }

    /**
     * Set the {@code selected_natural_key} property (displayed as {@code User Selected Natural Key}) of the object.
     * @param selectedNaturalKey the value to set
     */
    @JsonProperty("selected_natural_key")
    public void setSelectedNaturalKey(Boolean selectedNaturalKey) { this.selectedNaturalKey = selectedNaturalKey; }

    /**
     * Retrieve the {@code selected_primary_key} property (displayed as '{@literal User Selected Primary Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("selected_primary_key")
    public Boolean getSelectedPrimaryKey() { return this.selectedPrimaryKey; }

    /**
     * Set the {@code selected_primary_key} property (displayed as {@code User Selected Primary Key}) of the object.
     * @param selectedPrimaryKey the value to set
     */
    @JsonProperty("selected_primary_key")
    public void setSelectedPrimaryKey(Boolean selectedPrimaryKey) { this.selectedPrimaryKey = selectedPrimaryKey; }

    /**
     * Retrieve the {@code start_end_columns} property (displayed as '{@literal Starting .. Ending Columns}') of the object.
     * @return {@code String}
     */
    @JsonProperty("start_end_columns")
    public String getStartEndColumns() { return this.startEndColumns; }

    /**
     * Set the {@code start_end_columns} property (displayed as {@code Starting .. Ending Columns}) of the object.
     * @param startEndColumns the value to set
     */
    @JsonProperty("start_end_columns")
    public void setStartEndColumns(String startEndColumns) { this.startEndColumns = startEndColumns; }

    /**
     * Retrieve the {@code suggested_term_assignments} property (displayed as '{@literal Suggested Term Assignments}') of the object.
     * @return {@code ItemList<TermAssignment>}
     */
    @JsonProperty("suggested_term_assignments")
    public ItemList<TermAssignment> getSuggestedTermAssignments() { return this.suggestedTermAssignments; }

    /**
     * Set the {@code suggested_term_assignments} property (displayed as {@code Suggested Term Assignments}) of the object.
     * @param suggestedTermAssignments the value to set
     */
    @JsonProperty("suggested_term_assignments")
    public void setSuggestedTermAssignments(ItemList<TermAssignment> suggestedTermAssignments) { this.suggestedTermAssignments = suggestedTermAssignments; }

    /**
     * Retrieve the {@code uniqueFlag} property (displayed as '{@literal Require Unique Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("uniqueFlag")
    public Boolean getUniqueflag() { return this.uniqueflag; }

    /**
     * Set the {@code uniqueFlag} property (displayed as {@code Require Unique Values}) of the object.
     * @param uniqueflag the value to set
     */
    @JsonProperty("uniqueFlag")
    public void setUniqueflag(Boolean uniqueflag) { this.uniqueflag = uniqueflag; }

    /**
     * Retrieve the {@code validity_tables} property (displayed as '{@literal Validity Tables}') of the object.
     * @return {@code ItemList<ValidityTable>}
     */
    @JsonProperty("validity_tables")
    public ItemList<ValidityTable> getValidityTables() { return this.validityTables; }

    /**
     * Set the {@code validity_tables} property (displayed as {@code Validity Tables}) of the object.
     * @param validityTables the value to set
     */
    @JsonProperty("validity_tables")
    public void setValidityTables(ItemList<ValidityTable> validityTables) { this.validityTables = validityTables; }

    /**
     * Retrieve the {@code view} property (displayed as '{@literal Database View}') of the object.
     * @return {@code ItemList<View>}
     */
    @JsonProperty("view")
    public ItemList<View> getView() { return this.view; }

    /**
     * Set the {@code view} property (displayed as {@code Database View}) of the object.
     * @param view the value to set
     */
    @JsonProperty("view")
    public void setView(ItemList<View> view) { this.view = view; }

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
