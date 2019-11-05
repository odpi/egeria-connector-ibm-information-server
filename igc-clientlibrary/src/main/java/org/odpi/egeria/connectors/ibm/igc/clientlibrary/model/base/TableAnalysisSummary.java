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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code table_analysis_summary} asset type in IGC, displayed as '{@literal Table Analysis Summary}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("table_analysis_summary")
public class TableAnalysisSummary extends Reference {

    @JsonProperty("analyzed_table")
    protected Datagroup analyzedTable;

    @JsonProperty("assigned_to_terms")
    protected ItemList<Term> assignedToTerms;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("foreign_key_violations")
    protected Number foreignKeyViolations;

    @JsonProperty("inferred_foreign_keys")
    protected String inferredForeignKeys;

    @JsonProperty("inferred_primary_keys")
    protected String inferredPrimaryKeys;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("number_of_fields")
    protected Number numberOfFields;

    @JsonProperty("number_of_rows")
    protected Number numberOfRows;

    @JsonProperty("primary_key_duplicates")
    protected Number primaryKeyDuplicates;

    @JsonProperty("project_name")
    protected String projectName;

    @JsonProperty("promoted_by_principal")
    protected Steward promotedByPrincipal;

    @JsonProperty("review_date")
    protected Date reviewDate;

    @JsonProperty("reviewed_by_principal")
    protected Steward reviewedByPrincipal;

    @JsonProperty("short_&_long_description")
    protected String shortLongDescription;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("steward")
    protected ItemList<Steward> steward;

    /**
     * Retrieve the {@code analyzed_table} property (displayed as '{@literal Analyzed Table}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("analyzed_table")
    public Datagroup getAnalyzedTable() { return this.analyzedTable; }

    /**
     * Set the {@code analyzed_table} property (displayed as {@code Analyzed Table}) of the object.
     * @param analyzedTable the value to set
     */
    @JsonProperty("analyzed_table")
    public void setAnalyzedTable(Datagroup analyzedTable) { this.analyzedTable = analyzedTable; }

    /**
     * Retrieve the {@code assigned_to_terms} property (displayed as '{@literal Assigned to Terms}') of the object.
     * @return {@code ItemList<Term>}
     */
    @JsonProperty("assigned_to_terms")
    public ItemList<Term> getAssignedToTerms() { return this.assignedToTerms; }

    /**
     * Set the {@code assigned_to_terms} property (displayed as {@code Assigned to Terms}) of the object.
     * @param assignedToTerms the value to set
     */
    @JsonProperty("assigned_to_terms")
    public void setAssignedToTerms(ItemList<Term> assignedToTerms) { this.assignedToTerms = assignedToTerms; }

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
     * Retrieve the {@code inferred_foreign_keys} property (displayed as '{@literal Inferred Foreign Keys}') of the object.
     * @return {@code String}
     */
    @JsonProperty("inferred_foreign_keys")
    public String getInferredForeignKeys() { return this.inferredForeignKeys; }

    /**
     * Set the {@code inferred_foreign_keys} property (displayed as {@code Inferred Foreign Keys}) of the object.
     * @param inferredForeignKeys the value to set
     */
    @JsonProperty("inferred_foreign_keys")
    public void setInferredForeignKeys(String inferredForeignKeys) { this.inferredForeignKeys = inferredForeignKeys; }

    /**
     * Retrieve the {@code inferred_primary_keys} property (displayed as '{@literal Inferred Primary Keys}') of the object.
     * @return {@code String}
     */
    @JsonProperty("inferred_primary_keys")
    public String getInferredPrimaryKeys() { return this.inferredPrimaryKeys; }

    /**
     * Set the {@code inferred_primary_keys} property (displayed as {@code Inferred Primary Keys}) of the object.
     * @param inferredPrimaryKeys the value to set
     */
    @JsonProperty("inferred_primary_keys")
    public void setInferredPrimaryKeys(String inferredPrimaryKeys) { this.inferredPrimaryKeys = inferredPrimaryKeys; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

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
     * Retrieve the {@code project_name} property (displayed as '{@literal Analysis Project}') of the object.
     * @return {@code String}
     */
    @JsonProperty("project_name")
    public String getProjectName() { return this.projectName; }

    /**
     * Set the {@code project_name} property (displayed as {@code Analysis Project}) of the object.
     * @param projectName the value to set
     */
    @JsonProperty("project_name")
    public void setProjectName(String projectName) { this.projectName = projectName; }

    /**
     * Retrieve the {@code promoted_by_principal} property (displayed as '{@literal Promoted By Principal}') of the object.
     * @return {@code Steward}
     */
    @JsonProperty("promoted_by_principal")
    public Steward getPromotedByPrincipal() { return this.promotedByPrincipal; }

    /**
     * Set the {@code promoted_by_principal} property (displayed as {@code Promoted By Principal}) of the object.
     * @param promotedByPrincipal the value to set
     */
    @JsonProperty("promoted_by_principal")
    public void setPromotedByPrincipal(Steward promotedByPrincipal) { this.promotedByPrincipal = promotedByPrincipal; }

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
     * Retrieve the {@code reviewed_by_principal} property (displayed as '{@literal Reviewed By Principal}') of the object.
     * @return {@code Steward}
     */
    @JsonProperty("reviewed_by_principal")
    public Steward getReviewedByPrincipal() { return this.reviewedByPrincipal; }

    /**
     * Set the {@code reviewed_by_principal} property (displayed as {@code Reviewed By Principal}) of the object.
     * @param reviewedByPrincipal the value to set
     */
    @JsonProperty("reviewed_by_principal")
    public void setReviewedByPrincipal(Steward reviewedByPrincipal) { this.reviewedByPrincipal = reviewedByPrincipal; }

    /**
     * Retrieve the {@code short_&_long_description} property (displayed as '{@literal Short & Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_&_long_description")
    public String getShortLongDescription() { return this.shortLongDescription; }

    /**
     * Set the {@code short_&_long_description} property (displayed as {@code Short & Long Description}) of the object.
     * @param shortLongDescription the value to set
     */
    @JsonProperty("short_&_long_description")
    public void setShortLongDescription(String shortLongDescription) { this.shortLongDescription = shortLongDescription; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    /**
     * Retrieve the {@code steward} property (displayed as '{@literal Steward}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("steward")
    public ItemList<Steward> getSteward() { return this.steward; }

    /**
     * Set the {@code steward} property (displayed as {@code Steward}) of the object.
     * @param steward the value to set
     */
    @JsonProperty("steward")
    public void setSteward(ItemList<Steward> steward) { this.steward = steward; }

}
