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
 * POJO for the {@code mapping} asset type in IGC, displayed as '{@literal Mapping}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Mapping.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("mapping")
public class Mapping extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("description")
    protected List<String> description;

    @JsonProperty("last_update_description")
    protected List<String> lastUpdateDescription;

    @JsonProperty("mapping_specification")
    protected ItemList<MainObject> mappingSpecification;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("rule_description")
    protected List<String> ruleDescription;

    @JsonProperty("rule_expression")
    protected List<String> ruleExpression;

    @JsonProperty("source_column_names")
    protected List<String> sourceColumnNames;

    @JsonProperty("source_columns")
    protected ItemList<DatabaseColumn> sourceColumns;

    @JsonProperty("source_terms")
    protected ItemList<Term> sourceTerms;

    @JsonProperty("status")
    protected List<String> status;

    @JsonProperty("tags")
    protected String tags;

    @JsonProperty("target_column_names")
    protected List<String> targetColumnNames;

    @JsonProperty("target_columns")
    protected ItemList<DatabaseColumn> targetColumns;

    @JsonProperty("target_terms")
    protected ItemList<Term> targetTerms;

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
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("description")
    public List<String> getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(List<String> description) { this.description = description; }

    /**
     * Retrieve the {@code last_update_description} property (displayed as '{@literal Last Update Description}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("last_update_description")
    public List<String> getLastUpdateDescription() { return this.lastUpdateDescription; }

    /**
     * Set the {@code last_update_description} property (displayed as {@code Last Update Description}) of the object.
     * @param lastUpdateDescription the value to set
     */
    @JsonProperty("last_update_description")
    public void setLastUpdateDescription(List<String> lastUpdateDescription) { this.lastUpdateDescription = lastUpdateDescription; }

    /**
     * Retrieve the {@code mapping_specification} property (displayed as '{@literal Mapping Specification}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("mapping_specification")
    public ItemList<MainObject> getMappingSpecification() { return this.mappingSpecification; }

    /**
     * Set the {@code mapping_specification} property (displayed as {@code Mapping Specification}) of the object.
     * @param mappingSpecification the value to set
     */
    @JsonProperty("mapping_specification")
    public void setMappingSpecification(ItemList<MainObject> mappingSpecification) { this.mappingSpecification = mappingSpecification; }

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
     * Retrieve the {@code rule_description} property (displayed as '{@literal Rule Description}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("rule_description")
    public List<String> getRuleDescription() { return this.ruleDescription; }

    /**
     * Set the {@code rule_description} property (displayed as {@code Rule Description}) of the object.
     * @param ruleDescription the value to set
     */
    @JsonProperty("rule_description")
    public void setRuleDescription(List<String> ruleDescription) { this.ruleDescription = ruleDescription; }

    /**
     * Retrieve the {@code rule_expression} property (displayed as '{@literal Rule Expression}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("rule_expression")
    public List<String> getRuleExpression() { return this.ruleExpression; }

    /**
     * Set the {@code rule_expression} property (displayed as {@code Rule Expression}) of the object.
     * @param ruleExpression the value to set
     */
    @JsonProperty("rule_expression")
    public void setRuleExpression(List<String> ruleExpression) { this.ruleExpression = ruleExpression; }

    /**
     * Retrieve the {@code source_column_names} property (displayed as '{@literal Source Column Names}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("source_column_names")
    public List<String> getSourceColumnNames() { return this.sourceColumnNames; }

    /**
     * Set the {@code source_column_names} property (displayed as {@code Source Column Names}) of the object.
     * @param sourceColumnNames the value to set
     */
    @JsonProperty("source_column_names")
    public void setSourceColumnNames(List<String> sourceColumnNames) { this.sourceColumnNames = sourceColumnNames; }

    /**
     * Retrieve the {@code source_columns} property (displayed as '{@literal Source Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("source_columns")
    public ItemList<DatabaseColumn> getSourceColumns() { return this.sourceColumns; }

    /**
     * Set the {@code source_columns} property (displayed as {@code Source Columns}) of the object.
     * @param sourceColumns the value to set
     */
    @JsonProperty("source_columns")
    public void setSourceColumns(ItemList<DatabaseColumn> sourceColumns) { this.sourceColumns = sourceColumns; }

    /**
     * Retrieve the {@code source_terms} property (displayed as '{@literal Source Terms}') of the object.
     * @return {@code ItemList<Term>}
     */
    @JsonProperty("source_terms")
    public ItemList<Term> getSourceTerms() { return this.sourceTerms; }

    /**
     * Set the {@code source_terms} property (displayed as {@code Source Terms}) of the object.
     * @param sourceTerms the value to set
     */
    @JsonProperty("source_terms")
    public void setSourceTerms(ItemList<Term> sourceTerms) { this.sourceTerms = sourceTerms; }

    /**
     * Retrieve the {@code status} property (displayed as '{@literal Status}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("status")
    public List<String> getStatus() { return this.status; }

    /**
     * Set the {@code status} property (displayed as {@code Status}) of the object.
     * @param status the value to set
     */
    @JsonProperty("status")
    public void setStatus(List<String> status) { this.status = status; }

    /**
     * Retrieve the {@code tags} property (displayed as '{@literal Tags}') of the object.
     * @return {@code String}
     */
    @JsonProperty("tags")
    public String getTags() { return this.tags; }

    /**
     * Set the {@code tags} property (displayed as {@code Tags}) of the object.
     * @param tags the value to set
     */
    @JsonProperty("tags")
    public void setTags(String tags) { this.tags = tags; }

    /**
     * Retrieve the {@code target_column_names} property (displayed as '{@literal Target Column Names}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("target_column_names")
    public List<String> getTargetColumnNames() { return this.targetColumnNames; }

    /**
     * Set the {@code target_column_names} property (displayed as {@code Target Column Names}) of the object.
     * @param targetColumnNames the value to set
     */
    @JsonProperty("target_column_names")
    public void setTargetColumnNames(List<String> targetColumnNames) { this.targetColumnNames = targetColumnNames; }

    /**
     * Retrieve the {@code target_columns} property (displayed as '{@literal Target Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("target_columns")
    public ItemList<DatabaseColumn> getTargetColumns() { return this.targetColumns; }

    /**
     * Set the {@code target_columns} property (displayed as {@code Target Columns}) of the object.
     * @param targetColumns the value to set
     */
    @JsonProperty("target_columns")
    public void setTargetColumns(ItemList<DatabaseColumn> targetColumns) { this.targetColumns = targetColumns; }

    /**
     * Retrieve the {@code target_terms} property (displayed as '{@literal Target Terms}') of the object.
     * @return {@code ItemList<Term>}
     */
    @JsonProperty("target_terms")
    public ItemList<Term> getTargetTerms() { return this.targetTerms; }

    /**
     * Set the {@code target_terms} property (displayed as {@code Target Terms}) of the object.
     * @param targetTerms the value to set
     */
    @JsonProperty("target_terms")
    public void setTargetTerms(ItemList<Term> targetTerms) { this.targetTerms = targetTerms; }

}
