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

/**
 * POJO for the {@code table_analysis} asset type in IGC, displayed as '{@literal Table Analysis}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=TableAnalysis.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("table_analysis")
public class TableAnalysis extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("database_table_or_view")
    protected Datagroup databaseTableOrView;

    @JsonProperty("foreign_key_violations")
    protected Number foreignKeyViolations;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("nb_record_tested")
    protected Number nbRecordTested;

    @JsonProperty("number_of_fields")
    protected Number numberOfFields;

    @JsonProperty("number_of_rows")
    protected Number numberOfRows;

    @JsonProperty("primary_key_duplicates")
    protected Number primaryKeyDuplicates;

    @JsonProperty("project")
    protected String project;

    @JsonProperty("quality_score")
    protected Number qualityScore;

    @JsonProperty("quality_score_problems")
    protected ItemList<QualityProblem> qualityScoreProblems;

    @JsonProperty("review_date")
    protected Date reviewDate;

    @JsonProperty("selected_foreign_key")
    protected ItemList<DatabaseColumn> selectedForeignKey;

    @JsonProperty("selected_natural_key")
    protected ItemList<DatabaseColumn> selectedNaturalKey;

    @JsonProperty("selected_primary_key")
    protected ItemList<DatabaseColumn> selectedPrimaryKey;

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
     * Retrieve the {@code foreign_key_violations} property (displayed as '{@literal Foreign Key Violations}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("foreign_key_violations")
    public Number getForeignKeyViolations() { return this.foreignKeyViolations; }

    /**
     * Set the {@code foreign_key_violations} property (displayed as {@code Foreign Key Violations}) of the object.
     * @param foreignKeyViolations the value to set
     */
    @JsonProperty("foreign_key_violations")
    public void setForeignKeyViolations(Number foreignKeyViolations) { this.foreignKeyViolations = foreignKeyViolations; }

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
     * Retrieve the {@code nb_record_tested} property (displayed as '{@literal Number of Record Tested}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("nb_record_tested")
    public Number getNbRecordTested() { return this.nbRecordTested; }

    /**
     * Set the {@code nb_record_tested} property (displayed as {@code Number of Record Tested}) of the object.
     * @param nbRecordTested the value to set
     */
    @JsonProperty("nb_record_tested")
    public void setNbRecordTested(Number nbRecordTested) { this.nbRecordTested = nbRecordTested; }

    /**
     * Retrieve the {@code number_of_fields} property (displayed as '{@literal Number of Fields}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_fields")
    public Number getNumberOfFields() { return this.numberOfFields; }

    /**
     * Set the {@code number_of_fields} property (displayed as {@code Number of Fields}) of the object.
     * @param numberOfFields the value to set
     */
    @JsonProperty("number_of_fields")
    public void setNumberOfFields(Number numberOfFields) { this.numberOfFields = numberOfFields; }

    /**
     * Retrieve the {@code number_of_rows} property (displayed as '{@literal Number of Rows}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_rows")
    public Number getNumberOfRows() { return this.numberOfRows; }

    /**
     * Set the {@code number_of_rows} property (displayed as {@code Number of Rows}) of the object.
     * @param numberOfRows the value to set
     */
    @JsonProperty("number_of_rows")
    public void setNumberOfRows(Number numberOfRows) { this.numberOfRows = numberOfRows; }

    /**
     * Retrieve the {@code primary_key_duplicates} property (displayed as '{@literal Primary Key Duplicates}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("primary_key_duplicates")
    public Number getPrimaryKeyDuplicates() { return this.primaryKeyDuplicates; }

    /**
     * Set the {@code primary_key_duplicates} property (displayed as {@code Primary Key Duplicates}) of the object.
     * @param primaryKeyDuplicates the value to set
     */
    @JsonProperty("primary_key_duplicates")
    public void setPrimaryKeyDuplicates(Number primaryKeyDuplicates) { this.primaryKeyDuplicates = primaryKeyDuplicates; }

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
     * @return {@code Number}
     */
    @JsonProperty("quality_score")
    public Number getQualityScore() { return this.qualityScore; }

    /**
     * Set the {@code quality_score} property (displayed as {@code Quality Score}) of the object.
     * @param qualityScore the value to set
     */
    @JsonProperty("quality_score")
    public void setQualityScore(Number qualityScore) { this.qualityScore = qualityScore; }

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
     * Retrieve the {@code review_date} property (displayed as '{@literal Review Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("review_date")
    public Date getReviewDate() { return this.reviewDate; }

    /**
     * Set the {@code review_date} property (displayed as {@code Review Date}) of the object.
     * @param reviewDate the value to set
     */
    @JsonProperty("review_date")
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }

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
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("selected_natural_key")
    public ItemList<DatabaseColumn> getSelectedNaturalKey() { return this.selectedNaturalKey; }

    /**
     * Set the {@code selected_natural_key} property (displayed as {@code User Selected Natural Key}) of the object.
     * @param selectedNaturalKey the value to set
     */
    @JsonProperty("selected_natural_key")
    public void setSelectedNaturalKey(ItemList<DatabaseColumn> selectedNaturalKey) { this.selectedNaturalKey = selectedNaturalKey; }

    /**
     * Retrieve the {@code selected_primary_key} property (displayed as '{@literal User Selected Primary Key}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("selected_primary_key")
    public ItemList<DatabaseColumn> getSelectedPrimaryKey() { return this.selectedPrimaryKey; }

    /**
     * Set the {@code selected_primary_key} property (displayed as {@code User Selected Primary Key}) of the object.
     * @param selectedPrimaryKey the value to set
     */
    @JsonProperty("selected_primary_key")
    public void setSelectedPrimaryKey(ItemList<DatabaseColumn> selectedPrimaryKey) { this.selectedPrimaryKey = selectedPrimaryKey; }

}
