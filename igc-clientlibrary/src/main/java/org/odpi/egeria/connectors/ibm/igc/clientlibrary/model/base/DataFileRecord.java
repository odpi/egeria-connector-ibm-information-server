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
 * POJO for the {@code data_file_record} asset type in IGC, displayed as '{@literal Data File Record}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataFileRecord.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_file_record")
public class DataFileRecord extends Datagroup {

    @JsonProperty("FKViolationCount")
    protected List<Number> fkviolationcount;

    @JsonProperty("PKDuplicateCount")
    protected List<Number> pkduplicatecount;

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    /**
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     */
    @Deprecated
    @JsonProperty("analysis")
    protected ItemList<FileRecordAnalysis> analysis;

    @JsonProperty("data_file")
    protected DataFile dataFile;

    @JsonProperty("data_file_fields")
    protected ItemList<DataFileField> dataFileFields;

    @JsonProperty("fieldCount")
    protected List<Number> fieldcount;

    @JsonProperty("implements_design_tables_or_views")
    protected ItemList<Datagroup> implementsDesignTablesOrViews;

    @JsonProperty("implements_logical_entities")
    protected ItemList<LogicalEntity> implementsLogicalEntities;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("include_for_business_lineage")
    protected Boolean includeForBusinessLineage;

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

    @JsonProperty("reviewDate")
    protected List<Date> reviewdate;

    @JsonProperty("rowCount")
    protected List<Number> rowcount;

    @JsonProperty("same_as_data_sources")
    protected ItemList<Datagroup> sameAsDataSources;

    /**
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    protected ItemList<TermAssignment> suggestedTermAssignments;

    @JsonProperty("synchronized_from")
    protected String synchronizedFrom;

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
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     * @return {@code ItemList<FileRecordAnalysis>}
     */
    @Deprecated
    @JsonProperty("analysis")
    public ItemList<FileRecordAnalysis> getAnalysis() { return this.analysis; }

    /**
     * Set the {@code analysis} property (displayed as {@code Analysis}) of the object.
     * @deprecated No longer applicable from 11.5.0.2sp3 onwards.
     * @param analysis the value to set
     */
    @Deprecated
    @JsonProperty("analysis")
    public void setAnalysis(ItemList<FileRecordAnalysis> analysis) { this.analysis = analysis; }

    /**
     * Retrieve the {@code data_file} property (displayed as '{@literal Default Data File}') of the object.
     * @return {@code DataFile}
     */
    @JsonProperty("data_file")
    public DataFile getDataFile() { return this.dataFile; }

    /**
     * Set the {@code data_file} property (displayed as {@code Default Data File}) of the object.
     * @param dataFile the value to set
     */
    @JsonProperty("data_file")
    public void setDataFile(DataFile dataFile) { this.dataFile = dataFile; }

    /**
     * Retrieve the {@code data_file_fields} property (displayed as '{@literal Data File Fields}') of the object.
     * @return {@code ItemList<DataFileField>}
     */
    @JsonProperty("data_file_fields")
    public ItemList<DataFileField> getDataFileFields() { return this.dataFileFields; }

    /**
     * Set the {@code data_file_fields} property (displayed as {@code Data File Fields}) of the object.
     * @param dataFileFields the value to set
     */
    @JsonProperty("data_file_fields")
    public void setDataFileFields(ItemList<DataFileField> dataFileFields) { this.dataFileFields = dataFileFields; }

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
     * Retrieve the {@code include_for_business_lineage} property (displayed as '{@literal Include for Business Lineage}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("include_for_business_lineage")
    public Boolean getIncludeForBusinessLineage() { return this.includeForBusinessLineage; }

    /**
     * Set the {@code include_for_business_lineage} property (displayed as {@code Include for Business Lineage}) of the object.
     * @param includeForBusinessLineage the value to set
     */
    @JsonProperty("include_for_business_lineage")
    public void setIncludeForBusinessLineage(Boolean includeForBusinessLineage) { this.includeForBusinessLineage = includeForBusinessLineage; }

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

}
