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
import java.util.List;

/**
 * POJO for the {@code data_file_field} asset type in IGC, displayed as '{@literal Data File Field}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataFileField.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_file_field")
public class DataFileField extends Classificationenabledgroup {

    /**
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     */
    @Deprecated
    @JsonProperty("analysis")
    protected ItemList<FieldAnalysis> analysis;

    @JsonProperty("averageValue")
    protected List<String> averagevalue;

    @JsonProperty("constantFlag")
    protected Boolean constantflag;

    @JsonProperty("data_file_record")
    protected DataFileRecord dataFileRecord;

    @JsonProperty("database_data_rule_sets")
    protected ItemList<DataRuleSet> databaseDataRuleSets;

    @JsonProperty("datafile_data_rules")
    protected ItemList<DataRule> datafileDataRules;

    @JsonProperty("detected_classifications")
    protected ItemList<Classification> detectedClassifications;

    @JsonProperty("domainType")
    protected List<String> domaintype;

    @JsonProperty("hasDataClassification")
    protected ItemList<Classification> hasdataclassification;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("implements_design_columns")
    protected ItemList<DesignColumn> implementsDesignColumns;

    @JsonProperty("implements_entity_attributes")
    protected ItemList<EntityAttribute> implementsEntityAttributes;

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

    @JsonProperty("qualityScore")
    protected String qualityscore;

    @JsonProperty("qualityScore_bubble")
    protected String qualityscoreBubble;

    @JsonProperty("quality_benchmark")
    protected List<Number> qualityBenchmark;

    @JsonProperty("quality_dimension")
    protected ItemList<QualityProblem> qualityDimension;

    @JsonProperty("same_as_data_sources")
    protected ItemList<DataItem> sameAsDataSources;

    @JsonProperty("selected_classification")
    protected DataClass selectedClassification;

    @JsonProperty("suggested_term_assignments")
    protected ItemList<TermAssignment> suggestedTermAssignments;

    @JsonProperty("synchronized_from")
    protected String synchronizedFrom;

    @JsonProperty("uniqueFlag")
    protected Boolean uniqueflag;

    /**
     * Retrieve the {@code analysis} property (displayed as '{@literal Analysis}') of the object.
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     * @return {@code ItemList<FieldAnalysis>}
     */
    @Deprecated
    @JsonProperty("analysis")
    public ItemList<FieldAnalysis> getAnalysis() { return this.analysis; }

    /**
     * Set the {@code analysis} property (displayed as {@code Analysis}) of the object.
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     * @param analysis the value to set
     */
    @Deprecated
    @JsonProperty("analysis")
    public void setAnalysis(ItemList<FieldAnalysis> analysis) { this.analysis = analysis; }

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
     * Retrieve the {@code data_file_record} property (displayed as '{@literal Data File Record}') of the object.
     * @return {@code DataFileRecord}
     */
    @JsonProperty("data_file_record")
    public DataFileRecord getDataFileRecord() { return this.dataFileRecord; }

    /**
     * Set the {@code data_file_record} property (displayed as {@code Data File Record}) of the object.
     * @param dataFileRecord the value to set
     */
    @JsonProperty("data_file_record")
    public void setDataFileRecord(DataFileRecord dataFileRecord) { this.dataFileRecord = dataFileRecord; }

    /**
     * Retrieve the {@code database_data_rule_sets} property (displayed as '{@literal Data Rule Sets}') of the object.
     * @return {@code ItemList<DataRuleSet>}
     */
    @JsonProperty("database_data_rule_sets")
    public ItemList<DataRuleSet> getDatabaseDataRuleSets() { return this.databaseDataRuleSets; }

    /**
     * Set the {@code database_data_rule_sets} property (displayed as {@code Data Rule Sets}) of the object.
     * @param databaseDataRuleSets the value to set
     */
    @JsonProperty("database_data_rule_sets")
    public void setDatabaseDataRuleSets(ItemList<DataRuleSet> databaseDataRuleSets) { this.databaseDataRuleSets = databaseDataRuleSets; }

    /**
     * Retrieve the {@code datafile_data_rules} property (displayed as '{@literal Data Rules}') of the object.
     * @return {@code ItemList<DataRule>}
     */
    @JsonProperty("datafile_data_rules")
    public ItemList<DataRule> getDatafileDataRules() { return this.datafileDataRules; }

    /**
     * Set the {@code datafile_data_rules} property (displayed as {@code Data Rules}) of the object.
     * @param datafileDataRules the value to set
     */
    @JsonProperty("datafile_data_rules")
    public void setDatafileDataRules(ItemList<DataRule> datafileDataRules) { this.datafileDataRules = datafileDataRules; }

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
     * Retrieve the {@code hasDataClassification} property (displayed as '{@literal Detected Data Classifications}') of the object.
     * @return {@code ItemList<Classification>}
     */
    @JsonProperty("hasDataClassification")
    public ItemList<Classification> getHasdataclassification() { return this.hasdataclassification; }

    /**
     * Set the {@code hasDataClassification} property (displayed as {@code Detected Data Classifications}) of the object.
     * @param hasdataclassification the value to set
     */
    @JsonProperty("hasDataClassification")
    public void setHasdataclassification(ItemList<Classification> hasdataclassification) { this.hasdataclassification = hasdataclassification; }

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

}
