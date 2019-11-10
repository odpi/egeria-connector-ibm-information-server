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
 * POJO for the {@code mapping_specification} asset type in IGC, displayed as '{@literal Mapping Specification}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MappingSpecification.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("mapping_specification")
public class MappingSpecification extends InformationAsset {

    @JsonProperty("description")
    protected List<String> description;

    @JsonProperty("filters")
    protected ItemList<MappingFilter> filters;

    @JsonProperty("generated_jobs")
    protected ItemList<Dsjob> generatedJobs;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    /**
     * Valid values are:
     * <ul>
     *   <li>setToTrue (displayed in the UI as 'True')</li>
     *   <li>setToFalse (displayed in the UI as 'False')</li>
     * </ul>
     */
    @JsonProperty("include_for_lineage")
    protected String includeForLineage;

    /**
     * @deprecated No longer applicable from 11.7.0.1 onwards.
     */
    @Deprecated
    @JsonProperty("information_server_reports")
    protected ItemList<InformationServerReportMappingSpecification> informationServerReports;

    @JsonProperty("joins")
    protected ItemList<MappingJoin> joins;

    @JsonProperty("mapping_project")
    protected ItemList<MappingProject> mappingProject;

    @JsonProperty("mappings")
    protected ItemList<Mapping> mappings;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("owner")
    protected Steward owner;

    @JsonProperty("source_database_tables_or_views")
    protected ItemList<Datagroup> sourceDatabaseTablesOrViews;

    /**
     * Valid values are:
     * <ul>
     *   <li>PROCESSING_STARTED (displayed in the UI as 'PROCESSING_STARTED')</li>
     *   <li>REVIEW_COMPLETED (displayed in the UI as 'REVIEW_COMPLETED')</li>
     *   <li>DEPLOYMENT_COMPLETED (displayed in the UI as 'DEPLOYMENT_COMPLETED')</li>
     * </ul>
     */
    @JsonProperty("status")
    protected String status;

    @JsonProperty("target_database_tables_or_views")
    protected ItemList<Datagroup> targetDatabaseTablesOrViews;

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
     * Retrieve the {@code filters} property (displayed as '{@literal Filters}') of the object.
     * @return {@code ItemList<MappingFilter>}
     */
    @JsonProperty("filters")
    public ItemList<MappingFilter> getFilters() { return this.filters; }

    /**
     * Set the {@code filters} property (displayed as {@code Filters}) of the object.
     * @param filters the value to set
     */
    @JsonProperty("filters")
    public void setFilters(ItemList<MappingFilter> filters) { this.filters = filters; }

    /**
     * Retrieve the {@code generated_jobs} property (displayed as '{@literal Generated Jobs}') of the object.
     * @return {@code ItemList<Dsjob>}
     */
    @JsonProperty("generated_jobs")
    public ItemList<Dsjob> getGeneratedJobs() { return this.generatedJobs; }

    /**
     * Set the {@code generated_jobs} property (displayed as {@code Generated Jobs}) of the object.
     * @param generatedJobs the value to set
     */
    @JsonProperty("generated_jobs")
    public void setGeneratedJobs(ItemList<Dsjob> generatedJobs) { this.generatedJobs = generatedJobs; }

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
     * Retrieve the {@code include_for_lineage} property (displayed as '{@literal Include for Lineage}') of the object.
     * @return {@code String}
     */
    @JsonProperty("include_for_lineage")
    public String getIncludeForLineage() { return this.includeForLineage; }

    /**
     * Set the {@code include_for_lineage} property (displayed as {@code Include for Lineage}) of the object.
     * @param includeForLineage the value to set
     */
    @JsonProperty("include_for_lineage")
    public void setIncludeForLineage(String includeForLineage) { this.includeForLineage = includeForLineage; }

    /**
     * Retrieve the {@code information_server_reports} property (displayed as '{@literal Information Server Reports}') of the object.
     * @deprecated No longer applicable from 11.7.0.1 onwards.
     * @return {@code ItemList<InformationServerReportMappingSpecification>}
     */
    @Deprecated
    @JsonProperty("information_server_reports")
    public ItemList<InformationServerReportMappingSpecification> getInformationServerReports() { return this.informationServerReports; }

    /**
     * Set the {@code information_server_reports} property (displayed as {@code Information Server Reports}) of the object.
     * @deprecated No longer applicable from 11.7.0.1 onwards.
     * @param informationServerReports the value to set
     */
    @Deprecated
    @JsonProperty("information_server_reports")
    public void setInformationServerReports(ItemList<InformationServerReportMappingSpecification> informationServerReports) { this.informationServerReports = informationServerReports; }

    /**
     * Retrieve the {@code joins} property (displayed as '{@literal Joins}') of the object.
     * @return {@code ItemList<MappingJoin>}
     */
    @JsonProperty("joins")
    public ItemList<MappingJoin> getJoins() { return this.joins; }

    /**
     * Set the {@code joins} property (displayed as {@code Joins}) of the object.
     * @param joins the value to set
     */
    @JsonProperty("joins")
    public void setJoins(ItemList<MappingJoin> joins) { this.joins = joins; }

    /**
     * Retrieve the {@code mapping_project} property (displayed as '{@literal Mapping Project}') of the object.
     * @return {@code ItemList<MappingProject>}
     */
    @JsonProperty("mapping_project")
    public ItemList<MappingProject> getMappingProject() { return this.mappingProject; }

    /**
     * Set the {@code mapping_project} property (displayed as {@code Mapping Project}) of the object.
     * @param mappingProject the value to set
     */
    @JsonProperty("mapping_project")
    public void setMappingProject(ItemList<MappingProject> mappingProject) { this.mappingProject = mappingProject; }

    /**
     * Retrieve the {@code mappings} property (displayed as '{@literal Mappings}') of the object.
     * @return {@code ItemList<Mapping>}
     */
    @JsonProperty("mappings")
    public ItemList<Mapping> getMappings() { return this.mappings; }

    /**
     * Set the {@code mappings} property (displayed as {@code Mappings}) of the object.
     * @param mappings the value to set
     */
    @JsonProperty("mappings")
    public void setMappings(ItemList<Mapping> mappings) { this.mappings = mappings; }

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
     * Retrieve the {@code owner} property (displayed as '{@literal Owner}') of the object.
     * @return {@code Steward}
     */
    @JsonProperty("owner")
    public Steward getOwner() { return this.owner; }

    /**
     * Set the {@code owner} property (displayed as {@code Owner}) of the object.
     * @param owner the value to set
     */
    @JsonProperty("owner")
    public void setOwner(Steward owner) { this.owner = owner; }

    /**
     * Retrieve the {@code source_database_tables_or_views} property (displayed as '{@literal Source Database Tables or Views}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("source_database_tables_or_views")
    public ItemList<Datagroup> getSourceDatabaseTablesOrViews() { return this.sourceDatabaseTablesOrViews; }

    /**
     * Set the {@code source_database_tables_or_views} property (displayed as {@code Source Database Tables or Views}) of the object.
     * @param sourceDatabaseTablesOrViews the value to set
     */
    @JsonProperty("source_database_tables_or_views")
    public void setSourceDatabaseTablesOrViews(ItemList<Datagroup> sourceDatabaseTablesOrViews) { this.sourceDatabaseTablesOrViews = sourceDatabaseTablesOrViews; }

    /**
     * Retrieve the {@code status} property (displayed as '{@literal Status}') of the object.
     * @return {@code String}
     */
    @JsonProperty("status")
    public String getStatus() { return this.status; }

    /**
     * Set the {@code status} property (displayed as {@code Status}) of the object.
     * @param status the value to set
     */
    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

    /**
     * Retrieve the {@code target_database_tables_or_views} property (displayed as '{@literal Target Database Tables or Views}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("target_database_tables_or_views")
    public ItemList<Datagroup> getTargetDatabaseTablesOrViews() { return this.targetDatabaseTablesOrViews; }

    /**
     * Set the {@code target_database_tables_or_views} property (displayed as {@code Target Database Tables or Views}) of the object.
     * @param targetDatabaseTablesOrViews the value to set
     */
    @JsonProperty("target_database_tables_or_views")
    public void setTargetDatabaseTablesOrViews(ItemList<Datagroup> targetDatabaseTablesOrViews) { this.targetDatabaseTablesOrViews = targetDatabaseTablesOrViews; }

}
