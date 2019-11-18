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
 * POJO for the {@code column_analysis_summary} asset type in IGC, displayed as '{@literal Column Analysis Summary}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ColumnAnalysisSummary.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("column_analysis_summary")
public class ColumnAnalysisSummary extends Reference {

    @JsonProperty("allow_null_values")
    protected Boolean allowNullValues;

    @JsonProperty("analyzed_column")
    protected DataItem analyzedColumn;

    @JsonProperty("assigned_to_terms")
    protected ItemList<Term> assignedToTerms;

    @JsonProperty("average_length")
    protected String averageLength;

    @JsonProperty("constant")
    protected Boolean constant;

    @JsonProperty("domain_type")
    protected String domainType;

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

    @JsonProperty("inferred_format")
    protected String inferredFormat;

    @JsonProperty("inferred_length")
    protected Number inferredLength;

    @JsonProperty("inferred_precision")
    protected Number inferredPrecision;

    @JsonProperty("inferred_scale")
    protected Number inferredScale;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("longest_length")
    protected String longestLength;

    @JsonProperty("mask")
    protected String mask;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("number_of_complete_values")
    protected Number numberOfCompleteValues;

    @JsonProperty("number_of_distinct_formats")
    protected Number numberOfDistinctFormats;

    @JsonProperty("number_of_distinct_patterns")
    protected Number numberOfDistinctPatterns;

    @JsonProperty("number_of_distinct_values")
    protected Number numberOfDistinctValues;

    @JsonProperty("number_of_empty_values")
    protected Number numberOfEmptyValues;

    @JsonProperty("number_of_null_values")
    protected Number numberOfNullValues;

    @JsonProperty("number_of_valid_values")
    protected Number numberOfValidValues;

    @JsonProperty("number_values")
    protected Number numberValues;

    @JsonProperty("project_name")
    protected String projectName;

    @JsonProperty("promoted_by_principal")
    protected Steward promotedByPrincipal;

    @JsonProperty("require_unique_values")
    protected Boolean requireUniqueValues;

    @JsonProperty("review_date")
    protected Date reviewDate;

    @JsonProperty("reviewed_by_principal")
    protected Steward reviewedByPrincipal;

    @JsonProperty("short_&_long_description")
    protected String shortLongDescription;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("shortest_length")
    protected String shortestLength;

    @JsonProperty("steward")
    protected ItemList<Steward> steward;

    /**
     * Retrieve the {@code allow_null_values} property (displayed as '{@literal Allow Null Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("allow_null_values")
    public Boolean getAllowNullValues() { return this.allowNullValues; }

    /**
     * Set the {@code allow_null_values} property (displayed as {@code Allow Null Values}) of the object.
     * @param allowNullValues the value to set
     */
    @JsonProperty("allow_null_values")
    public void setAllowNullValues(Boolean allowNullValues) { this.allowNullValues = allowNullValues; }

    /**
     * Retrieve the {@code analyzed_column} property (displayed as '{@literal Analyzed Column}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("analyzed_column")
    public DataItem getAnalyzedColumn() { return this.analyzedColumn; }

    /**
     * Set the {@code analyzed_column} property (displayed as {@code Analyzed Column}) of the object.
     * @param analyzedColumn the value to set
     */
    @JsonProperty("analyzed_column")
    public void setAnalyzedColumn(DataItem analyzedColumn) { this.analyzedColumn = analyzedColumn; }

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
     * Retrieve the {@code average_length} property (displayed as '{@literal Average Length}') of the object.
     * @return {@code String}
     */
    @JsonProperty("average_length")
    public String getAverageLength() { return this.averageLength; }

    /**
     * Set the {@code average_length} property (displayed as {@code Average Length}) of the object.
     * @param averageLength the value to set
     */
    @JsonProperty("average_length")
    public void setAverageLength(String averageLength) { this.averageLength = averageLength; }

    /**
     * Retrieve the {@code constant} property (displayed as '{@literal Constant}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("constant")
    public Boolean getConstant() { return this.constant; }

    /**
     * Set the {@code constant} property (displayed as {@code Constant}) of the object.
     * @param constant the value to set
     */
    @JsonProperty("constant")
    public void setConstant(Boolean constant) { this.constant = constant; }

    /**
     * Retrieve the {@code domain_type} property (displayed as '{@literal Domain Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("domain_type")
    public String getDomainType() { return this.domainType; }

    /**
     * Set the {@code domain_type} property (displayed as {@code Domain Type}) of the object.
     * @param domainType the value to set
     */
    @JsonProperty("domain_type")
    public void setDomainType(String domainType) { this.domainType = domainType; }

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
     * Retrieve the {@code longest_length} property (displayed as '{@literal Longest Length}') of the object.
     * @return {@code String}
     */
    @JsonProperty("longest_length")
    public String getLongestLength() { return this.longestLength; }

    /**
     * Set the {@code longest_length} property (displayed as {@code Longest Length}) of the object.
     * @param longestLength the value to set
     */
    @JsonProperty("longest_length")
    public void setLongestLength(String longestLength) { this.longestLength = longestLength; }

    /**
     * Retrieve the {@code mask} property (displayed as '{@literal Mask}') of the object.
     * @return {@code String}
     */
    @JsonProperty("mask")
    public String getMask() { return this.mask; }

    /**
     * Set the {@code mask} property (displayed as {@code Mask}) of the object.
     * @param mask the value to set
     */
    @JsonProperty("mask")
    public void setMask(String mask) { this.mask = mask; }

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
     * Retrieve the {@code number_of_distinct_patterns} property (displayed as '{@literal Number of Distinct Patterns}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_of_distinct_patterns")
    public Number getNumberOfDistinctPatterns() { return this.numberOfDistinctPatterns; }

    /**
     * Set the {@code number_of_distinct_patterns} property (displayed as {@code Number of Distinct Patterns}) of the object.
     * @param numberOfDistinctPatterns the value to set
     */
    @JsonProperty("number_of_distinct_patterns")
    public void setNumberOfDistinctPatterns(Number numberOfDistinctPatterns) { this.numberOfDistinctPatterns = numberOfDistinctPatterns; }

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
     * Retrieve the {@code number_values} property (displayed as '{@literal Number Values}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("number_values")
    public Number getNumberValues() { return this.numberValues; }

    /**
     * Set the {@code number_values} property (displayed as {@code Number Values}) of the object.
     * @param numberValues the value to set
     */
    @JsonProperty("number_values")
    public void setNumberValues(Number numberValues) { this.numberValues = numberValues; }

    /**
     * Retrieve the {@code project_name} property (displayed as '{@literal Project Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("project_name")
    public String getProjectName() { return this.projectName; }

    /**
     * Set the {@code project_name} property (displayed as {@code Project Name}) of the object.
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
     * Retrieve the {@code shortest_length} property (displayed as '{@literal Shortest Length}') of the object.
     * @return {@code String}
     */
    @JsonProperty("shortest_length")
    public String getShortestLength() { return this.shortestLength; }

    /**
     * Set the {@code shortest_length} property (displayed as {@code Shortest Length}) of the object.
     * @param shortestLength the value to set
     */
    @JsonProperty("shortest_length")
    public void setShortestLength(String shortestLength) { this.shortestLength = shortestLength; }

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
