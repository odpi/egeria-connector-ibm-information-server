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
import java.util.Date;

/**
 * POJO for the {@code quality_problem} asset type in IGC, displayed as '{@literal Quality Score Problem}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=QualityProblem.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("quality_problem")
public class QualityProblem extends Reference {

    @JsonProperty("Column")
    protected String column;

    @JsonProperty("QualityProblemTypeDescription")
    protected String qualityproblemtypedescription;

    @JsonProperty("QualityProblemTypeName")
    protected String qualityproblemtypename;

    @JsonProperty("confidence")
    protected Number confidence;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    /**
     * No longer applicable from 11.7.0.1sp1 onwards.
     */
    @Deprecated
    @JsonProperty("data_quality_score")
    protected InformationAsset dataQualityScore;

    @JsonProperty("details")
    protected String details;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("occurrences")
    protected Number occurrences;

    @JsonProperty("percent_occurrences")
    protected Number percentOccurrences;

    /**
     * No longer applicable from 11.7.0.1sp1 onwards.
     */
    @Deprecated
    @JsonProperty("problem_type")
    protected InformationAsset problemType;

    @JsonProperty("qualityScore")
    protected Number qualityscore;

    /**
     * Retrieve the {@code Column} property (displayed as '{@literal Column}') of the object.
     * @return {@code String}
     */
    @JsonProperty("Column")
    public String getColumn() { return this.column; }

    /**
     * Set the {@code Column} property (displayed as {@code Column}) of the object.
     * @param column the value to set
     */
    @JsonProperty("Column")
    public void setColumn(String column) { this.column = column; }

    /**
     * Retrieve the {@code QualityProblemTypeDescription} property (displayed as '{@literal Quality Problem Type Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("QualityProblemTypeDescription")
    public String getQualityproblemtypedescription() { return this.qualityproblemtypedescription; }

    /**
     * Set the {@code QualityProblemTypeDescription} property (displayed as {@code Quality Problem Type Description}) of the object.
     * @param qualityproblemtypedescription the value to set
     */
    @JsonProperty("QualityProblemTypeDescription")
    public void setQualityproblemtypedescription(String qualityproblemtypedescription) { this.qualityproblemtypedescription = qualityproblemtypedescription; }

    /**
     * Retrieve the {@code QualityProblemTypeName} property (displayed as '{@literal Quality Problem Type Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("QualityProblemTypeName")
    public String getQualityproblemtypename() { return this.qualityproblemtypename; }

    /**
     * Set the {@code QualityProblemTypeName} property (displayed as {@code Quality Problem Type Name}) of the object.
     * @param qualityproblemtypename the value to set
     */
    @JsonProperty("QualityProblemTypeName")
    public void setQualityproblemtypename(String qualityproblemtypename) { this.qualityproblemtypename = qualityproblemtypename; }

    /**
     * Retrieve the {@code confidence} property (displayed as '{@literal Confidence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("confidence")
    public Number getConfidence() { return this.confidence; }

    /**
     * Set the {@code confidence} property (displayed as {@code Confidence}) of the object.
     * @param confidence the value to set
     */
    @JsonProperty("confidence")
    public void setConfidence(Number confidence) { this.confidence = confidence; }

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
     * Retrieve the {@code data_quality_score} property (displayed as '{@literal Data Quality Score}') of the object.
     * No longer applicable from 11.7.0.1sp1 onwards.
     *
     * @return {@code InformationAsset}
     */
    @Deprecated
    @JsonProperty("data_quality_score")
    public InformationAsset getDataQualityScore() { return this.dataQualityScore; }

    /**
     * Set the {@code data_quality_score} property (displayed as {@code Data Quality Score}) of the object.
     * No longer applicable from 11.7.0.1sp1 onwards.
     *
     * @param dataQualityScore the value to set
     */
    @Deprecated
    @JsonProperty("data_quality_score")
    public void setDataQualityScore(InformationAsset dataQualityScore) { this.dataQualityScore = dataQualityScore; }

    /**
     * Retrieve the {@code details} property (displayed as '{@literal Details}') of the object.
     * @return {@code String}
     */
    @JsonProperty("details")
    public String getDetails() { return this.details; }

    /**
     * Set the {@code details} property (displayed as {@code Details}) of the object.
     * @param details the value to set
     */
    @JsonProperty("details")
    public void setDetails(String details) { this.details = details; }

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
     * Retrieve the {@code occurrences} property (displayed as '{@literal Occurrences}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("occurrences")
    public Number getOccurrences() { return this.occurrences; }

    /**
     * Set the {@code occurrences} property (displayed as {@code Occurrences}) of the object.
     * @param occurrences the value to set
     */
    @JsonProperty("occurrences")
    public void setOccurrences(Number occurrences) { this.occurrences = occurrences; }

    /**
     * Retrieve the {@code percent_occurrences} property (displayed as '{@literal Percent Occurrences}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("percent_occurrences")
    public Number getPercentOccurrences() { return this.percentOccurrences; }

    /**
     * Set the {@code percent_occurrences} property (displayed as {@code Percent Occurrences}) of the object.
     * @param percentOccurrences the value to set
     */
    @JsonProperty("percent_occurrences")
    public void setPercentOccurrences(Number percentOccurrences) { this.percentOccurrences = percentOccurrences; }

    /**
     * Retrieve the {@code problem_type} property (displayed as '{@literal Problem Type}') of the object.
     * No longer applicable from 11.7.0.1sp1 onwards.
     *
     * @return {@code InformationAsset}
     */
    @Deprecated
    @JsonProperty("problem_type")
    public InformationAsset getProblemType() { return this.problemType; }

    /**
     * Set the {@code problem_type} property (displayed as {@code Problem Type}) of the object.
     * No longer applicable from 11.7.0.1sp1 onwards.
     *
     * @param problemType the value to set
     */
    @Deprecated
    @JsonProperty("problem_type")
    public void setProblemType(InformationAsset problemType) { this.problemType = problemType; }

    /**
     * Retrieve the {@code qualityScore} property (displayed as '{@literal Quality Score}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("qualityScore")
    public Number getQualityscore() { return this.qualityscore; }

    /**
     * Set the {@code qualityScore} property (displayed as {@code Quality Score}) of the object.
     * @param qualityscore the value to set
     */
    @JsonProperty("qualityScore")
    public void setQualityscore(Number qualityscore) { this.qualityscore = qualityscore; }

}
