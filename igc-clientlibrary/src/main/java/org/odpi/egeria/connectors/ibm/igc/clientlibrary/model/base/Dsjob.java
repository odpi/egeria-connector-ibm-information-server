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
 * POJO for the {@code dsjob} asset type in IGC, displayed as '{@literal Job}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Dsjob.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsjob")
public class Dsjob extends InformationAsset {

    @JsonProperty("annotations")
    protected List<String> annotations;

    /**
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("design_parameters")
    protected ItemList<DsparameterJob> designParameters;

    @JsonProperty("folder")
    protected Dsfolder folder;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("include_for_lineage")
    protected Boolean includeForLineage;

    /**
     * Valid values are:
     * <ul>
     *   <li>setToTrue (displayed in the UI as 'True')</li>
     *   <li>setToFalse (displayed in the UI as 'False')</li>
     *   <li>defaultToProject (displayed in the UI as 'Default (Transformation Project)')</li>
     * </ul>
     */
    @JsonProperty("include_for_lineage__edit")
    protected String includeForLineageEdit;

    @JsonProperty("include_for_lineage_description")
    protected String includeForLineageDescription;

    @JsonProperty("information_services_operations")
    protected ItemList<InformationServicesOperation> informationServicesOperations;

    @JsonProperty("inherits_lineage_setting_from_transformation_project")
    protected Boolean inheritsLineageSettingFromTransformationProject;

    @JsonProperty("job_runs")
    protected ItemList<JobRun> jobRuns;

    @JsonProperty("lineage_service_information")
    protected List<String> lineageServiceInformation;

    @JsonProperty("lineage_service_last_run_date")
    protected List<Date> lineageServiceLastRunDate;

    @JsonProperty("lineage_service_status")
    protected List<String> lineageServiceStatus;

    @JsonProperty("mapping_specifications")
    protected ItemList<MainObject> mappingSpecifications;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("optimized_by_jobs")
    protected ItemList<Dsjob> optimizedByJobs;

    @JsonProperty("optimizes_job")
    protected ItemList<Dsjob> optimizesJob;

    @JsonProperty("parameter_sets")
    protected ItemList<ParameterSetDefinition> parameterSets;

    @JsonProperty("reads_from_(design)")
    protected ItemList<InformationAsset> readsFromDesign;

    @JsonProperty("reads_from_(operational)")
    protected ItemList<InformationAsset> readsFromOperational;

    @JsonProperty("reads_from_(static)")
    protected ItemList<InformationAsset> readsFromStatic;

    @JsonProperty("reads_from_(user_defined)")
    protected ItemList<InformationAsset> readsFromUserDefined;

    @JsonProperty("references_local_or_shared_containers")
    protected ItemList<ReferencedContainer> referencesLocalOrSharedContainers;

    @JsonProperty("references_table_definitions")
    protected ItemList<TableDefinition> referencesTableDefinitions;

    @JsonProperty("runtime_column_propagation")
    protected Boolean runtimeColumnPropagation;

    @JsonProperty("sequenced_by_jobs")
    protected ItemList<SequenceJob> sequencedByJobs;

    @JsonProperty("stages")
    protected ItemList<Stage> stages;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("web_service_enabled")
    protected Boolean webServiceEnabled;

    @JsonProperty("writes_to_(design)")
    protected ItemList<InformationAsset> writesToDesign;

    @JsonProperty("writes_to_(operational)")
    protected ItemList<InformationAsset> writesToOperational;

    @JsonProperty("writes_to_(static)")
    protected ItemList<InformationAsset> writesToStatic;

    @JsonProperty("writes_to_(user_defined)")
    protected ItemList<InformationAsset> writesToUserDefined;

    /**
     * Retrieve the {@code annotations} property (displayed as '{@literal Annotations}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("annotations")
    public List<String> getAnnotations() { return this.annotations; }

    /**
     * Set the {@code annotations} property (displayed as {@code Annotations}) of the object.
     * @param annotations the value to set
     */
    @JsonProperty("annotations")
    public void setAnnotations(List<String> annotations) { this.annotations = annotations; }

    /**
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @param blueprintElements the value to set
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code design_parameters} property (displayed as '{@literal Design Parameters}') of the object.
     * @return {@code ItemList<DsparameterJob>}
     */
    @JsonProperty("design_parameters")
    public ItemList<DsparameterJob> getDesignParameters() { return this.designParameters; }

    /**
     * Set the {@code design_parameters} property (displayed as {@code Design Parameters}) of the object.
     * @param designParameters the value to set
     */
    @JsonProperty("design_parameters")
    public void setDesignParameters(ItemList<DsparameterJob> designParameters) { this.designParameters = designParameters; }

    /**
     * Retrieve the {@code folder} property (displayed as '{@literal Folder}') of the object.
     * @return {@code Dsfolder}
     */
    @JsonProperty("folder")
    public Dsfolder getFolder() { return this.folder; }

    /**
     * Set the {@code folder} property (displayed as {@code Folder}) of the object.
     * @param folder the value to set
     */
    @JsonProperty("folder")
    public void setFolder(Dsfolder folder) { this.folder = folder; }

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
     * @return {@code Boolean}
     */
    @JsonProperty("include_for_lineage")
    public Boolean getIncludeForLineage() { return this.includeForLineage; }

    /**
     * Set the {@code include_for_lineage} property (displayed as {@code Include for Lineage}) of the object.
     * @param includeForLineage the value to set
     */
    @JsonProperty("include_for_lineage")
    public void setIncludeForLineage(Boolean includeForLineage) { this.includeForLineage = includeForLineage; }

    /**
     * Retrieve the {@code include_for_lineage__edit} property (displayed as '{@literal Include for Lineage}') of the object.
     * @return {@code String}
     */
    @JsonProperty("include_for_lineage__edit")
    public String getIncludeForLineageEdit() { return this.includeForLineageEdit; }

    /**
     * Set the {@code include_for_lineage__edit} property (displayed as {@code Include for Lineage}) of the object.
     * @param includeForLineageEdit the value to set
     */
    @JsonProperty("include_for_lineage__edit")
    public void setIncludeForLineageEdit(String includeForLineageEdit) { this.includeForLineageEdit = includeForLineageEdit; }

    /**
     * Retrieve the {@code include_for_lineage_description} property (displayed as '{@literal Include for Lineage}') of the object.
     * @return {@code String}
     */
    @JsonProperty("include_for_lineage_description")
    public String getIncludeForLineageDescription() { return this.includeForLineageDescription; }

    /**
     * Set the {@code include_for_lineage_description} property (displayed as {@code Include for Lineage}) of the object.
     * @param includeForLineageDescription the value to set
     */
    @JsonProperty("include_for_lineage_description")
    public void setIncludeForLineageDescription(String includeForLineageDescription) { this.includeForLineageDescription = includeForLineageDescription; }

    /**
     * Retrieve the {@code information_services_operations} property (displayed as '{@literal Information Services Operations}') of the object.
     * @return {@code ItemList<InformationServicesOperation>}
     */
    @JsonProperty("information_services_operations")
    public ItemList<InformationServicesOperation> getInformationServicesOperations() { return this.informationServicesOperations; }

    /**
     * Set the {@code information_services_operations} property (displayed as {@code Information Services Operations}) of the object.
     * @param informationServicesOperations the value to set
     */
    @JsonProperty("information_services_operations")
    public void setInformationServicesOperations(ItemList<InformationServicesOperation> informationServicesOperations) { this.informationServicesOperations = informationServicesOperations; }

    /**
     * Retrieve the {@code inherits_lineage_setting_from_transformation_project} property (displayed as '{@literal Inherits Lineage Setting from Transformation Project}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("inherits_lineage_setting_from_transformation_project")
    public Boolean getInheritsLineageSettingFromTransformationProject() { return this.inheritsLineageSettingFromTransformationProject; }

    /**
     * Set the {@code inherits_lineage_setting_from_transformation_project} property (displayed as {@code Inherits Lineage Setting from Transformation Project}) of the object.
     * @param inheritsLineageSettingFromTransformationProject the value to set
     */
    @JsonProperty("inherits_lineage_setting_from_transformation_project")
    public void setInheritsLineageSettingFromTransformationProject(Boolean inheritsLineageSettingFromTransformationProject) { this.inheritsLineageSettingFromTransformationProject = inheritsLineageSettingFromTransformationProject; }

    /**
     * Retrieve the {@code job_runs} property (displayed as '{@literal Job Runs}') of the object.
     * @return {@code ItemList<JobRun>}
     */
    @JsonProperty("job_runs")
    public ItemList<JobRun> getJobRuns() { return this.jobRuns; }

    /**
     * Set the {@code job_runs} property (displayed as {@code Job Runs}) of the object.
     * @param jobRuns the value to set
     */
    @JsonProperty("job_runs")
    public void setJobRuns(ItemList<JobRun> jobRuns) { this.jobRuns = jobRuns; }

    /**
     * Retrieve the {@code lineage_service_information} property (displayed as '{@literal Lineage Service Information}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("lineage_service_information")
    public List<String> getLineageServiceInformation() { return this.lineageServiceInformation; }

    /**
     * Set the {@code lineage_service_information} property (displayed as {@code Lineage Service Information}) of the object.
     * @param lineageServiceInformation the value to set
     */
    @JsonProperty("lineage_service_information")
    public void setLineageServiceInformation(List<String> lineageServiceInformation) { this.lineageServiceInformation = lineageServiceInformation; }

    /**
     * Retrieve the {@code lineage_service_last_run_date} property (displayed as '{@literal Lineage Service Last Run Date}') of the object.
     * @return {@code List<Date>}
     */
    @JsonProperty("lineage_service_last_run_date")
    public List<Date> getLineageServiceLastRunDate() { return this.lineageServiceLastRunDate; }

    /**
     * Set the {@code lineage_service_last_run_date} property (displayed as {@code Lineage Service Last Run Date}) of the object.
     * @param lineageServiceLastRunDate the value to set
     */
    @JsonProperty("lineage_service_last_run_date")
    public void setLineageServiceLastRunDate(List<Date> lineageServiceLastRunDate) { this.lineageServiceLastRunDate = lineageServiceLastRunDate; }

    /**
     * Retrieve the {@code lineage_service_status} property (displayed as '{@literal Lineage Service Status}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("lineage_service_status")
    public List<String> getLineageServiceStatus() { return this.lineageServiceStatus; }

    /**
     * Set the {@code lineage_service_status} property (displayed as {@code Lineage Service Status}) of the object.
     * @param lineageServiceStatus the value to set
     */
    @JsonProperty("lineage_service_status")
    public void setLineageServiceStatus(List<String> lineageServiceStatus) { this.lineageServiceStatus = lineageServiceStatus; }

    /**
     * Retrieve the {@code mapping_specifications} property (displayed as '{@literal Mapping Specifications}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("mapping_specifications")
    public ItemList<MainObject> getMappingSpecifications() { return this.mappingSpecifications; }

    /**
     * Set the {@code mapping_specifications} property (displayed as {@code Mapping Specifications}) of the object.
     * @param mappingSpecifications the value to set
     */
    @JsonProperty("mapping_specifications")
    public void setMappingSpecifications(ItemList<MainObject> mappingSpecifications) { this.mappingSpecifications = mappingSpecifications; }

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
     * Retrieve the {@code optimized_by_jobs} property (displayed as '{@literal Optimized by Jobs}') of the object.
     * @return {@code ItemList<Dsjob>}
     */
    @JsonProperty("optimized_by_jobs")
    public ItemList<Dsjob> getOptimizedByJobs() { return this.optimizedByJobs; }

    /**
     * Set the {@code optimized_by_jobs} property (displayed as {@code Optimized by Jobs}) of the object.
     * @param optimizedByJobs the value to set
     */
    @JsonProperty("optimized_by_jobs")
    public void setOptimizedByJobs(ItemList<Dsjob> optimizedByJobs) { this.optimizedByJobs = optimizedByJobs; }

    /**
     * Retrieve the {@code optimizes_job} property (displayed as '{@literal Optimizes Job}') of the object.
     * @return {@code ItemList<Dsjob>}
     */
    @JsonProperty("optimizes_job")
    public ItemList<Dsjob> getOptimizesJob() { return this.optimizesJob; }

    /**
     * Set the {@code optimizes_job} property (displayed as {@code Optimizes Job}) of the object.
     * @param optimizesJob the value to set
     */
    @JsonProperty("optimizes_job")
    public void setOptimizesJob(ItemList<Dsjob> optimizesJob) { this.optimizesJob = optimizesJob; }

    /**
     * Retrieve the {@code parameter_sets} property (displayed as '{@literal Parameter Sets}') of the object.
     * @return {@code ItemList<ParameterSetDefinition>}
     */
    @JsonProperty("parameter_sets")
    public ItemList<ParameterSetDefinition> getParameterSets() { return this.parameterSets; }

    /**
     * Set the {@code parameter_sets} property (displayed as {@code Parameter Sets}) of the object.
     * @param parameterSets the value to set
     */
    @JsonProperty("parameter_sets")
    public void setParameterSets(ItemList<ParameterSetDefinition> parameterSets) { this.parameterSets = parameterSets; }

    /**
     * Retrieve the {@code reads_from_(design)} property (displayed as '{@literal Reads from (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(design)")
    public ItemList<InformationAsset> getReadsFromDesign() { return this.readsFromDesign; }

    /**
     * Set the {@code reads_from_(design)} property (displayed as {@code Reads from (Design)}) of the object.
     * @param readsFromDesign the value to set
     */
    @JsonProperty("reads_from_(design)")
    public void setReadsFromDesign(ItemList<InformationAsset> readsFromDesign) { this.readsFromDesign = readsFromDesign; }

    /**
     * Retrieve the {@code reads_from_(operational)} property (displayed as '{@literal Reads from (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(operational)")
    public ItemList<InformationAsset> getReadsFromOperational() { return this.readsFromOperational; }

    /**
     * Set the {@code reads_from_(operational)} property (displayed as {@code Reads from (Operational)}) of the object.
     * @param readsFromOperational the value to set
     */
    @JsonProperty("reads_from_(operational)")
    public void setReadsFromOperational(ItemList<InformationAsset> readsFromOperational) { this.readsFromOperational = readsFromOperational; }

    /**
     * Retrieve the {@code reads_from_(static)} property (displayed as '{@literal Reads from (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(static)")
    public ItemList<InformationAsset> getReadsFromStatic() { return this.readsFromStatic; }

    /**
     * Set the {@code reads_from_(static)} property (displayed as {@code Reads from (Static)}) of the object.
     * @param readsFromStatic the value to set
     */
    @JsonProperty("reads_from_(static)")
    public void setReadsFromStatic(ItemList<InformationAsset> readsFromStatic) { this.readsFromStatic = readsFromStatic; }

    /**
     * Retrieve the {@code reads_from_(user_defined)} property (displayed as '{@literal Reads from (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(user_defined)")
    public ItemList<InformationAsset> getReadsFromUserDefined() { return this.readsFromUserDefined; }

    /**
     * Set the {@code reads_from_(user_defined)} property (displayed as {@code Reads from (User-Defined)}) of the object.
     * @param readsFromUserDefined the value to set
     */
    @JsonProperty("reads_from_(user_defined)")
    public void setReadsFromUserDefined(ItemList<InformationAsset> readsFromUserDefined) { this.readsFromUserDefined = readsFromUserDefined; }

    /**
     * Retrieve the {@code references_local_or_shared_containers} property (displayed as '{@literal References Local or Shared Containers}') of the object.
     * @return {@code ItemList<ReferencedContainer>}
     */
    @JsonProperty("references_local_or_shared_containers")
    public ItemList<ReferencedContainer> getReferencesLocalOrSharedContainers() { return this.referencesLocalOrSharedContainers; }

    /**
     * Set the {@code references_local_or_shared_containers} property (displayed as {@code References Local or Shared Containers}) of the object.
     * @param referencesLocalOrSharedContainers the value to set
     */
    @JsonProperty("references_local_or_shared_containers")
    public void setReferencesLocalOrSharedContainers(ItemList<ReferencedContainer> referencesLocalOrSharedContainers) { this.referencesLocalOrSharedContainers = referencesLocalOrSharedContainers; }

    /**
     * Retrieve the {@code references_table_definitions} property (displayed as '{@literal References Table Definitions}') of the object.
     * @return {@code ItemList<TableDefinition>}
     */
    @JsonProperty("references_table_definitions")
    public ItemList<TableDefinition> getReferencesTableDefinitions() { return this.referencesTableDefinitions; }

    /**
     * Set the {@code references_table_definitions} property (displayed as {@code References Table Definitions}) of the object.
     * @param referencesTableDefinitions the value to set
     */
    @JsonProperty("references_table_definitions")
    public void setReferencesTableDefinitions(ItemList<TableDefinition> referencesTableDefinitions) { this.referencesTableDefinitions = referencesTableDefinitions; }

    /**
     * Retrieve the {@code runtime_column_propagation} property (displayed as '{@literal Runtime Column Propagation}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("runtime_column_propagation")
    public Boolean getRuntimeColumnPropagation() { return this.runtimeColumnPropagation; }

    /**
     * Set the {@code runtime_column_propagation} property (displayed as {@code Runtime Column Propagation}) of the object.
     * @param runtimeColumnPropagation the value to set
     */
    @JsonProperty("runtime_column_propagation")
    public void setRuntimeColumnPropagation(Boolean runtimeColumnPropagation) { this.runtimeColumnPropagation = runtimeColumnPropagation; }

    /**
     * Retrieve the {@code sequenced_by_jobs} property (displayed as '{@literal Sequenced by Jobs}') of the object.
     * @return {@code ItemList<SequenceJob>}
     */
    @JsonProperty("sequenced_by_jobs")
    public ItemList<SequenceJob> getSequencedByJobs() { return this.sequencedByJobs; }

    /**
     * Set the {@code sequenced_by_jobs} property (displayed as {@code Sequenced by Jobs}) of the object.
     * @param sequencedByJobs the value to set
     */
    @JsonProperty("sequenced_by_jobs")
    public void setSequencedByJobs(ItemList<SequenceJob> sequencedByJobs) { this.sequencedByJobs = sequencedByJobs; }

    /**
     * Retrieve the {@code stages} property (displayed as '{@literal Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("stages")
    public ItemList<Stage> getStages() { return this.stages; }

    /**
     * Set the {@code stages} property (displayed as {@code Stages}) of the object.
     * @param stages the value to set
     */
    @JsonProperty("stages")
    public void setStages(ItemList<Stage> stages) { this.stages = stages; }

    /**
     * Retrieve the {@code transformation_project} property (displayed as '{@literal Transformation Project}') of the object.
     * @return {@code TransformationProject}
     */
    @JsonProperty("transformation_project")
    public TransformationProject getTransformationProject() { return this.transformationProject; }

    /**
     * Set the {@code transformation_project} property (displayed as {@code Transformation Project}) of the object.
     * @param transformationProject the value to set
     */
    @JsonProperty("transformation_project")
    public void setTransformationProject(TransformationProject transformationProject) { this.transformationProject = transformationProject; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

    /**
     * Retrieve the {@code web_service_enabled} property (displayed as '{@literal Web Service Enabled}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("web_service_enabled")
    public Boolean getWebServiceEnabled() { return this.webServiceEnabled; }

    /**
     * Set the {@code web_service_enabled} property (displayed as {@code Web Service Enabled}) of the object.
     * @param webServiceEnabled the value to set
     */
    @JsonProperty("web_service_enabled")
    public void setWebServiceEnabled(Boolean webServiceEnabled) { this.webServiceEnabled = webServiceEnabled; }

    /**
     * Retrieve the {@code writes_to_(design)} property (displayed as '{@literal Writes to (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(design)")
    public ItemList<InformationAsset> getWritesToDesign() { return this.writesToDesign; }

    /**
     * Set the {@code writes_to_(design)} property (displayed as {@code Writes to (Design)}) of the object.
     * @param writesToDesign the value to set
     */
    @JsonProperty("writes_to_(design)")
    public void setWritesToDesign(ItemList<InformationAsset> writesToDesign) { this.writesToDesign = writesToDesign; }

    /**
     * Retrieve the {@code writes_to_(operational)} property (displayed as '{@literal Writes to (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(operational)")
    public ItemList<InformationAsset> getWritesToOperational() { return this.writesToOperational; }

    /**
     * Set the {@code writes_to_(operational)} property (displayed as {@code Writes to (Operational)}) of the object.
     * @param writesToOperational the value to set
     */
    @JsonProperty("writes_to_(operational)")
    public void setWritesToOperational(ItemList<InformationAsset> writesToOperational) { this.writesToOperational = writesToOperational; }

    /**
     * Retrieve the {@code writes_to_(static)} property (displayed as '{@literal Writes to (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(static)")
    public ItemList<InformationAsset> getWritesToStatic() { return this.writesToStatic; }

    /**
     * Set the {@code writes_to_(static)} property (displayed as {@code Writes to (Static)}) of the object.
     * @param writesToStatic the value to set
     */
    @JsonProperty("writes_to_(static)")
    public void setWritesToStatic(ItemList<InformationAsset> writesToStatic) { this.writesToStatic = writesToStatic; }

    /**
     * Retrieve the {@code writes_to_(user_defined)} property (displayed as '{@literal Writes to (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(user_defined)")
    public ItemList<InformationAsset> getWritesToUserDefined() { return this.writesToUserDefined; }

    /**
     * Set the {@code writes_to_(user_defined)} property (displayed as {@code Writes to (User-Defined)}) of the object.
     * @param writesToUserDefined the value to set
     */
    @JsonProperty("writes_to_(user_defined)")
    public void setWritesToUserDefined(ItemList<InformationAsset> writesToUserDefined) { this.writesToUserDefined = writesToUserDefined; }

}
