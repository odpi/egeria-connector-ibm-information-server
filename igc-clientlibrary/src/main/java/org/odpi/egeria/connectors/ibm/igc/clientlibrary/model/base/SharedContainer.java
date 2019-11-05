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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code shared_container} asset type in IGC, displayed as '{@literal Shared Container}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("shared_container")
public class SharedContainer extends InformationAsset {

    @JsonProperty("annotations")
    protected List<String> annotations;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("mapping_components")
    protected ItemList<MappingComponent> mappingComponents;

    @JsonProperty("parameter_sets")
    protected ItemList<DsparameterSet> parameterSets;

    @JsonProperty("parameters")
    protected ItemList<Dsparameter> parameters;

    @JsonProperty("referenced_by_containers")
    protected ItemList<SharedContainer> referencedByContainers;

    @JsonProperty("referenced_by_stages")
    protected ItemList<Stage> referencedByStages;

    @JsonProperty("references_containers")
    protected ItemList<ReferencedContainer> referencesContainers;

    @JsonProperty("source_mappings")
    protected ItemList<MainObject> sourceMappings;

    @JsonProperty("stages")
    protected ItemList<Stage> stages;

    @JsonProperty("target_mappings")
    protected ItemList<MainObject> targetMappings;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

    /**
     * Valid values are:
     * <ul>
     *   <li>SERVER (displayed in the UI as 'SERVER')</li>
     *   <li>MAINFRAME (displayed in the UI as 'MAINFRAME')</li>
     *   <li>SEQUENCE (displayed in the UI as 'SEQUENCE')</li>
     *   <li>PARALLEL (displayed in the UI as 'PARALLEL')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

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
     * Retrieve the {@code mapping_components} property (displayed as '{@literal Mapping Components}') of the object.
     * @return {@code ItemList<MappingComponent>}
     */
    @JsonProperty("mapping_components")
    public ItemList<MappingComponent> getMappingComponents() { return this.mappingComponents; }

    /**
     * Set the {@code mapping_components} property (displayed as {@code Mapping Components}) of the object.
     * @param mappingComponents the value to set
     */
    @JsonProperty("mapping_components")
    public void setMappingComponents(ItemList<MappingComponent> mappingComponents) { this.mappingComponents = mappingComponents; }

    /**
     * Retrieve the {@code parameter_sets} property (displayed as '{@literal Parameter Sets}') of the object.
     * @return {@code ItemList<DsparameterSet>}
     */
    @JsonProperty("parameter_sets")
    public ItemList<DsparameterSet> getParameterSets() { return this.parameterSets; }

    /**
     * Set the {@code parameter_sets} property (displayed as {@code Parameter Sets}) of the object.
     * @param parameterSets the value to set
     */
    @JsonProperty("parameter_sets")
    public void setParameterSets(ItemList<DsparameterSet> parameterSets) { this.parameterSets = parameterSets; }

    /**
     * Retrieve the {@code parameters} property (displayed as '{@literal Parameters}') of the object.
     * @return {@code ItemList<Dsparameter>}
     */
    @JsonProperty("parameters")
    public ItemList<Dsparameter> getParameters() { return this.parameters; }

    /**
     * Set the {@code parameters} property (displayed as {@code Parameters}) of the object.
     * @param parameters the value to set
     */
    @JsonProperty("parameters")
    public void setParameters(ItemList<Dsparameter> parameters) { this.parameters = parameters; }

    /**
     * Retrieve the {@code referenced_by_containers} property (displayed as '{@literal Referenced by Containers}') of the object.
     * @return {@code ItemList<SharedContainer>}
     */
    @JsonProperty("referenced_by_containers")
    public ItemList<SharedContainer> getReferencedByContainers() { return this.referencedByContainers; }

    /**
     * Set the {@code referenced_by_containers} property (displayed as {@code Referenced by Containers}) of the object.
     * @param referencedByContainers the value to set
     */
    @JsonProperty("referenced_by_containers")
    public void setReferencedByContainers(ItemList<SharedContainer> referencedByContainers) { this.referencedByContainers = referencedByContainers; }

    /**
     * Retrieve the {@code referenced_by_stages} property (displayed as '{@literal Referenced by Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("referenced_by_stages")
    public ItemList<Stage> getReferencedByStages() { return this.referencedByStages; }

    /**
     * Set the {@code referenced_by_stages} property (displayed as {@code Referenced by Stages}) of the object.
     * @param referencedByStages the value to set
     */
    @JsonProperty("referenced_by_stages")
    public void setReferencedByStages(ItemList<Stage> referencedByStages) { this.referencedByStages = referencedByStages; }

    /**
     * Retrieve the {@code references_containers} property (displayed as '{@literal References Containers}') of the object.
     * @return {@code ItemList<ReferencedContainer>}
     */
    @JsonProperty("references_containers")
    public ItemList<ReferencedContainer> getReferencesContainers() { return this.referencesContainers; }

    /**
     * Set the {@code references_containers} property (displayed as {@code References Containers}) of the object.
     * @param referencesContainers the value to set
     */
    @JsonProperty("references_containers")
    public void setReferencesContainers(ItemList<ReferencedContainer> referencesContainers) { this.referencesContainers = referencesContainers; }

    /**
     * Retrieve the {@code source_mappings} property (displayed as '{@literal Source Mappings}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("source_mappings")
    public ItemList<MainObject> getSourceMappings() { return this.sourceMappings; }

    /**
     * Set the {@code source_mappings} property (displayed as {@code Source Mappings}) of the object.
     * @param sourceMappings the value to set
     */
    @JsonProperty("source_mappings")
    public void setSourceMappings(ItemList<MainObject> sourceMappings) { this.sourceMappings = sourceMappings; }

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
     * Retrieve the {@code target_mappings} property (displayed as '{@literal Target Mappings}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("target_mappings")
    public ItemList<MainObject> getTargetMappings() { return this.targetMappings; }

    /**
     * Set the {@code target_mappings} property (displayed as {@code Target Mappings}) of the object.
     * @param targetMappings the value to set
     */
    @JsonProperty("target_mappings")
    public void setTargetMappings(ItemList<MainObject> targetMappings) { this.targetMappings = targetMappings; }

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

}
