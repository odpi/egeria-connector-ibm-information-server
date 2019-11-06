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
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code field_analysis} asset type in IGC, displayed as '{@literal Field Analysis}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=FieldAnalysis.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("field_analysis")
public class FieldAnalysis extends Reference {

    @JsonProperty("average_value")
    protected String averageValue;

    @JsonProperty("classification")
    protected ItemList<Classification> classification;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("data_file_field")
    protected DataFileField dataFileField;

    @JsonProperty("domain")
    protected String domain;

    @JsonProperty("include_constant_values")
    protected Boolean includeConstantValues;

    @JsonProperty("include_null_values")
    protected Boolean includeNullValues;

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
    @JsonProperty("inferred_data_type")
    protected String inferredDataType;

    @JsonProperty("inferred_foreign_key")
    protected Boolean inferredForeignKey;

    @JsonProperty("inferred_format")
    protected String inferredFormat;

    @JsonProperty("inferred_length")
    protected Number inferredLength;

    @JsonProperty("inferred_precision")
    protected Number inferredPrecision;

    @JsonProperty("inferred_primary_key")
    protected Boolean inferredPrimaryKey;

    @JsonProperty("inferred_scale")
    protected Number inferredScale;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("nb_records_tested")
    protected Number nbRecordsTested;

    @JsonProperty("number_of_complete_values")
    protected Number numberOfCompleteValues;

    @JsonProperty("number_of_distinct_formats")
    protected Number numberOfDistinctFormats;

    @JsonProperty("number_of_distinct_values")
    protected Number numberOfDistinctValues;

    @JsonProperty("number_of_empty_values")
    protected Number numberOfEmptyValues;

    @JsonProperty("number_of_null_values")
    protected Number numberOfNullValues;

    @JsonProperty("number_of_valid_values")
    protected Number numberOfValidValues;

    @JsonProperty("number_of_zero_values")
    protected Number numberOfZeroValues;

    @JsonProperty("project")
    protected String project;

    /**
     * No longer applicable from 11.5.0.1ru5 onwards.
     * @see #qualityScorePercent
     */
    @Deprecated
    @JsonProperty("quality_score")
    protected Number qualityScore;

    @JsonProperty("quality_score_percent")
    protected String qualityScorePercent;

    @JsonProperty("quality_score_problems")
    protected ItemList<QualityProblem> qualityScoreProblems;

    @JsonProperty("require_unique_values")
    protected Boolean requireUniqueValues;

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

    @JsonProperty("table_analysis")
    protected MainObject tableAnalysis;

    @JsonProperty("validation_properties")
    protected List<String> validationProperties;

    @JsonProperty("validation_type")
    protected List<String> validationType;

    /**
     * Retrieve the {@code average_value} property (displayed as '{@literal Average Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("average_value")
    public String getAverageValue() { return this.averageValue; }

    /**
     * Set the {@code average_value} property (displayed as {@code Average Value}) of the object.
     * @param averageValue the value to set
     */
    @JsonProperty("average_value")
    public void setAverageValue(String averageValue) { this.averageValue = averageValue; }

    /**
     * Retrieve the {@code classification} property (displayed as '{@literal Detected Data Classifications}') of the object.
     * @return {@code ItemList<Classification>}
     */
    @JsonProperty("classification")
    public ItemList<Classification> getClassification() { return this.classification; }

    /**
     * Set the {@code classification} property (displayed as {@code Detected Data Classifications}) of the object.
     * @param classification the value to set
     */
    @JsonProperty("classification")
    public void setClassification(ItemList<Classification> classification) { this.classification = classification; }

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code data_file_field} property (displayed as '{@literal Data File Field}') of the object.
     * @return {@code DataFileField}
     */
    @JsonProperty("data_file_field")
    public DataFileField getDataFileField() { return this.dataFileField; }

    /**
     * Set the {@code data_file_field} property (displayed as {@code Data File Field}) of the object.
     * @param dataFileField the value to set
     */
    @JsonProperty("data_file_field")
    public void setDataFileField(DataFileField dataFileField) { this.dataFileField = dataFileField; }

    /**
     * Retrieve the {@code domain} property (displayed as '{@literal Domain}') of the object.
     * @return {@code String}
     */
    @JsonProperty("domain")
    public String getDomain() { return this.domain; }

    /**
     * Set the {@code domain} property (displayed as {@code Domain}) of the object.
     * @param domain the value to set
     */
    @JsonProperty("domain")
    public void setDomain(String domain) { this.domain = domain; }

    /**
     * Retrieve the {@code include_constant_values} property (displayed as '{@literal Include Constant Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("include_constant_values")
    public Boolean getIncludeConstantValues() { return this.includeConstantValues; }

    /**
     * Set the {@code include_constant_values} property (displayed as {@code Include Constant Values}) of the object.
     * @param includeConstantValues the value to set
     */
    @JsonProperty("include_constant_values")
    public void setIncludeConstantValues(Boolean includeConstantValues) { this.includeConstantValues = includeConstantValues; }

    /**
     * Retrieve the {@code include_null_values} property (displayed as '{@literal Include Null Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("include_null_values")
    public Boolean getIncludeNullValues() { return this.includeNullValues; }

    /**
     * Set the {@code include_null_values} property (displayed as {@code Include Null Values}) of the object.
     * @param includeNullValues the value to set
     */
    @JsonProperty("include_null_values")
    public void setIncludeNullValues(Boolean includeNullValues) { this.includeNullValues = includeNullValues; }

    /**
     * Retrieve the {@code inferred_data_type} property (displayed as '{@literal Inferred Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("inferred_data_type")
    public String getInferredDataType() { return this.inferredDataType; }

    /**
     * Set the {@code inferred_data_type} property (displayed as {@code Inferred Data Type}) of the object.
     * @param inferredDataType the value to set
     */
    @JsonProperty("inferred_data_type")
    public void setInferredDataType(String inferredDataType) { this.inferredDataType = inferredDataType; }

    /**
     * Retrieve the {@code inferred_foreign_key} property (displayed as '{@literal Inferred Foreign Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("inferred_foreign_key")
    public Boolean getInferredForeignKey() { return this.inferredForeignKey; }

    /**
     * Set the {@code inferred_foreign_key} property (displayed as {@code Inferred Foreign Key}) of the object.
     * @param inferredForeignKey the value to set
     */
    @JsonProperty("inferred_foreign_key")
    public void setInferredForeignKey(Boolean inferredForeignKey) { this.inferredForeignKey = inferredForeignKey; }

    /**
     * Retrieve the {@code inferred_format} property (displayed as '{@literal Inferred Format}') of the object.
     * @return {@code String}
     */
    @JsonProperty("inferred_format")
    public String getInferredFormat() { return this.inferredFormat; }

    /**
     * Set the {@code inferred_format} property (displayed as {@code Inferred Format}) of the object.
     * @param inferredFormat the value to set
     */
    @JsonProperty("inferred_format")
    public void setInferredFormat(String inferredFormat) { this.inferredFormat = inferredFormat; }

    /**
     * Retrieve the {@code inferred_length} property (displayed as '{@literal Inferred Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("inferred_length")
    public Number getInferredLength() { return this.inferredLength; }

    /**
     * Set the {@code inferred_length} property (displayed as {@code Inferred Length}) of the object.
     * @param inferredLength the value to set
     */
    @JsonProperty("inferred_length")
    public void setInferredLength(Number inferredLength) { this.inferredLength = inferredLength; }

    /**
     * Retrieve the {@code inferred_precision} property (displayed as '{@literal Inferred Precision}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("inferred_precision")
    public Number getInferredPrecision() { return this.inferredPrecision; }

    /**
     * Set the {@code inferred_precision} property (displayed as {@code Inferred Precision}) of the object.
     * @param inferredPrecision the value to set
     */
    @JsonProperty("inferred_precision")
    public void setInferredPrecision(Number inferredPrecision) { this.inferredPrecision = inferredPrecision; }

    /**
     * Retrieve the {@code inferred_primary_key} property (displayed as '{@literal Inferred Primary Key}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("inferred_primary_key")
    public Boolean getInferredPrimaryKey() { return this.inferredPrimaryKey; }

    /**
     * Set the {@code inferred_primary_key} property (displayed as {@code Inferred Primary Key}) of the object.
     * @param inferredPrimaryKey the value to set
     */
    @JsonProperty("inferred_primary_key")
    public void setInferredPrimaryKey(Boolean inferredPrimaryKey) { this.inferredPrimaryKey = inferredPrimaryKey; }

    /**
     * Retrieve the {@code inferred_scale} property (displayed as '{@literal Inferred Scale}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("inferred_scale")
    public Number getInferredScale() { return this.inferredScale; }

    /**
     * Set the {@code inferred_scale} property (displayed as {@code Inferred Scale}) of the object.
     * @param inferredScale the value to set
     */
    @JsonProperty("inferred_scale")
    public void setInferredScale(Number inferredScale) { this.inferredScale = inferredScale; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

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
     * Retrieve the {@code nb_records_tested} property (displayed as '{@literal Number of Records Tested}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("nb_records_tested")
    public Number getNbRecordsTested() { return this.nbRecordsTested; }

    /**
     * Set the {@code nb_records_tested} property (displayed as {@code Number of Records Tested}) of the object.
     * @param nbRecordsTested the value to set
     */
    @JsonProperty("nb_records_tested")
    public void setNbRecordsTested(Number nbRecordsTested) { this.nbRecordsTested = nbRecordsTested; }

    /**
     * Retrieve the {@code number_of_complete_values} property (displayed as '{@literal Number of Complete Values}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_complete_values")
    public Number getNumberOfCompleteValues() { return this.numberOfCompleteValues; }

    /**
     * Set the {@code number_of_complete_values} property (displayed as {@code Number of Complete Values}) of the object.
     * @param numberOfCompleteValues the value to set
     */
    @JsonProperty("number_of_complete_values")
    public void setNumberOfCompleteValues(Number numberOfCompleteValues) { this.numberOfCompleteValues = numberOfCompleteValues; }

    /**
     * Retrieve the {@code number_of_distinct_formats} property (displayed as '{@literal Number of Distinct Formats}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_distinct_formats")
    public Number getNumberOfDistinctFormats() { return this.numberOfDistinctFormats; }

    /**
     * Set the {@code number_of_distinct_formats} property (displayed as {@code Number of Distinct Formats}) of the object.
     * @param numberOfDistinctFormats the value to set
     */
    @JsonProperty("number_of_distinct_formats")
    public void setNumberOfDistinctFormats(Number numberOfDistinctFormats) { this.numberOfDistinctFormats = numberOfDistinctFormats; }

    /**
     * Retrieve the {@code number_of_distinct_values} property (displayed as '{@literal Number of Distinct Values}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_distinct_values")
    public Number getNumberOfDistinctValues() { return this.numberOfDistinctValues; }

    /**
     * Set the {@code number_of_distinct_values} property (displayed as {@code Number of Distinct Values}) of the object.
     * @param numberOfDistinctValues the value to set
     */
    @JsonProperty("number_of_distinct_values")
    public void setNumberOfDistinctValues(Number numberOfDistinctValues) { this.numberOfDistinctValues = numberOfDistinctValues; }

    /**
     * Retrieve the {@code number_of_empty_values} property (displayed as '{@literal Number of Empty Values}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_empty_values")
    public Number getNumberOfEmptyValues() { return this.numberOfEmptyValues; }

    /**
     * Set the {@code number_of_empty_values} property (displayed as {@code Number of Empty Values}) of the object.
     * @param numberOfEmptyValues the value to set
     */
    @JsonProperty("number_of_empty_values")
    public void setNumberOfEmptyValues(Number numberOfEmptyValues) { this.numberOfEmptyValues = numberOfEmptyValues; }

    /**
     * Retrieve the {@code number_of_null_values} property (displayed as '{@literal Number of Null Values}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_null_values")
    public Number getNumberOfNullValues() { return this.numberOfNullValues; }

    /**
     * Set the {@code number_of_null_values} property (displayed as {@code Number of Null Values}) of the object.
     * @param numberOfNullValues the value to set
     */
    @JsonProperty("number_of_null_values")
    public void setNumberOfNullValues(Number numberOfNullValues) { this.numberOfNullValues = numberOfNullValues; }

    /**
     * Retrieve the {@code number_of_valid_values} property (displayed as '{@literal Number of Valid Values}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_valid_values")
    public Number getNumberOfValidValues() { return this.numberOfValidValues; }

    /**
     * Set the {@code number_of_valid_values} property (displayed as {@code Number of Valid Values}) of the object.
     * @param numberOfValidValues the value to set
     */
    @JsonProperty("number_of_valid_values")
    public void setNumberOfValidValues(Number numberOfValidValues) { this.numberOfValidValues = numberOfValidValues; }

    /**
     * Retrieve the {@code number_of_zero_values} property (displayed as '{@literal Number of Zero Values}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_zero_values")
    public Number getNumberOfZeroValues() { return this.numberOfZeroValues; }

    /**
     * Set the {@code number_of_zero_values} property (displayed as {@code Number of Zero Values}) of the object.
     * @param numberOfZeroValues the value to set
     */
    @JsonProperty("number_of_zero_values")
    public void setNumberOfZeroValues(Number numberOfZeroValues) { this.numberOfZeroValues = numberOfZeroValues; }

    /**
     * Retrieve the {@code project} property (displayed as '{@literal Project}') of the object.
     * @return {@code String}
     */
    @JsonProperty("project")
    public String getProject() { return this.project; }

    /**
     * Set the {@code project} property (displayed as {@code Project}) of the object.
     * @param project the value to set
     */
    @JsonProperty("project")
    public void setProject(String project) { this.project = project; }

    /**
     * Retrieve the {@code quality_score} property (displayed as '{@literal Quality Score}') of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @return {@code Number}
     * @see #getQualityScorePercent()
     */
    @Deprecated
    @JsonProperty("quality_score")
    public Number getQualityScore() { return this.qualityScore; }

    /**
     * Set the {@code quality_score} property (displayed as {@code Quality Score}) of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @param qualityScore the value to set
     * @see #setQualityScorePercent(String)
     */
    @Deprecated
    @JsonProperty("quality_score")
    public void setQualityScore(Number qualityScore) { this.qualityScore = qualityScore; }

    /**
     * Retrieve the {@code quality_score_percent} property (displayed as '{@literal Quality Score}') of the object.
     * @return {@code String}
     */
    @JsonProperty("quality_score_percent")
    public String getQualityScorePercent() { return this.qualityScorePercent; }

    /**
     * Set the {@code quality_score_percent} property (displayed as {@code Quality Score}) of the object.
     * @param qualityScorePercent the value to set
     */
    @JsonProperty("quality_score_percent")
    public void setQualityScorePercent(String qualityScorePercent) { this.qualityScorePercent = qualityScorePercent; }

    /**
     * Retrieve the {@code quality_score_problems} property (displayed as '{@literal Quality Score Problems}') of the object.
     * @return {@code ItemList<QualityProblem>}
     */
    @JsonProperty("quality_score_problems")
    public ItemList<QualityProblem> getQualityScoreProblems() { return this.qualityScoreProblems; }

    /**
     * Set the {@code quality_score_problems} property (displayed as {@code Quality Score Problems}) of the object.
     * @param qualityScoreProblems the value to set
     */
    @JsonProperty("quality_score_problems")
    public void setQualityScoreProblems(ItemList<QualityProblem> qualityScoreProblems) { this.qualityScoreProblems = qualityScoreProblems; }

    /**
     * Retrieve the {@code require_unique_values} property (displayed as '{@literal Require Unique Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("require_unique_values")
    public Boolean getRequireUniqueValues() { return this.requireUniqueValues; }

    /**
     * Set the {@code require_unique_values} property (displayed as {@code Require Unique Values}) of the object.
     * @param requireUniqueValues the value to set
     */
    @JsonProperty("require_unique_values")
    public void setRequireUniqueValues(Boolean requireUniqueValues) { this.requireUniqueValues = requireUniqueValues; }

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
     * Retrieve the {@code table_analysis} property (displayed as '{@literal Table Analysis}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("table_analysis")
    public MainObject getTableAnalysis() { return this.tableAnalysis; }

    /**
     * Set the {@code table_analysis} property (displayed as {@code Table Analysis}) of the object.
     * @param tableAnalysis the value to set
     */
    @JsonProperty("table_analysis")
    public void setTableAnalysis(MainObject tableAnalysis) { this.tableAnalysis = tableAnalysis; }

    /**
     * Retrieve the {@code validation_properties} property (displayed as '{@literal Validation Properties}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("validation_properties")
    public List<String> getValidationProperties() { return this.validationProperties; }

    /**
     * Set the {@code validation_properties} property (displayed as {@code Validation Properties}) of the object.
     * @param validationProperties the value to set
     */
    @JsonProperty("validation_properties")
    public void setValidationProperties(List<String> validationProperties) { this.validationProperties = validationProperties; }

    /**
     * Retrieve the {@code validation_type} property (displayed as '{@literal Validation Type}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("validation_type")
    public List<String> getValidationType() { return this.validationType; }

    /**
     * Set the {@code validation_type} property (displayed as {@code Validation Type}) of the object.
     * @param validationType the value to set
     */
    @JsonProperty("validation_type")
    public void setValidationType(List<String> validationType) { this.validationType = validationType; }

}
