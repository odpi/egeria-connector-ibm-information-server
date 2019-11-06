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
 * POJO for the {@code sequence_job} asset type in IGC, displayed as '{@literal Sequence Job}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=SequenceJob.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("sequence_job")
public class SequenceJob extends InformationAsset {

    @JsonProperty("annotations")
    protected List<String> annotations;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("design_parameters")
    protected ItemList<DsparameterJob> designParameters;

    @JsonProperty("folder")
    protected Dsfolder folder;

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

    @JsonProperty("inherits_lineage_setting_from_transformation_project")
    protected Boolean inheritsLineageSettingFromTransformationProject;

    @JsonProperty("job_runs")
    protected ItemList<JobRun> jobRuns;

    @JsonProperty("parameter_sets")
    protected ItemList<ParameterSetDefinition> parameterSets;

    @JsonProperty("references_local_or_shared_containers")
    protected ItemList<ReferencedContainer> referencesLocalOrSharedContainers;

    @JsonProperty("sequences_jobs")
    protected ItemList<Dsjob> sequencesJobs;

    @JsonProperty("stages")
    protected ItemList<Stage> stages;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

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
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @param blueprintElements the value to set
     */
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code design_parameters} property (displayed as '{@literal Parameters}') of the object.
     * @return {@code ItemList<DsparameterJob>}
     */
    @JsonProperty("design_parameters")
    public ItemList<DsparameterJob> getDesignParameters() { return this.designParameters; }

    /**
     * Set the {@code design_parameters} property (displayed as {@code Parameters}) of the object.
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
     * Retrieve the {@code sequences_jobs} property (displayed as '{@literal Sequences Jobs}') of the object.
     * @return {@code ItemList<Dsjob>}
     */
    @JsonProperty("sequences_jobs")
    public ItemList<Dsjob> getSequencesJobs() { return this.sequencesJobs; }

    /**
     * Set the {@code sequences_jobs} property (displayed as {@code Sequences Jobs}) of the object.
     * @param sequencesJobs the value to set
     */
    @JsonProperty("sequences_jobs")
    public void setSequencesJobs(ItemList<Dsjob> sequencesJobs) { this.sequencesJobs = sequencesJobs; }

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

}
