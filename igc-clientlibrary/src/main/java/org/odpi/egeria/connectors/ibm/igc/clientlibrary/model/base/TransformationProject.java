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

/**
 * POJO for the {@code transformation_project} asset type in IGC, displayed as '{@literal Transformation Project}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=TransformationProject.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("transformation_project")
public class TransformationProject extends InformationAsset {

    @JsonProperty("containers")
    protected ItemList<SharedContainer> containers;

    @JsonProperty("folders")
    protected ItemList<Dsfolder> folders;

    @JsonProperty("host_(engine)")
    protected HostEngine hostEngine;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("include_for_lineage_desc")
    protected Boolean includeForLineageDesc;

    @JsonProperty("jobs")
    protected ItemList<Dsjob> jobs;

    @JsonProperty("machine_profiles")
    protected ItemList<MachineProfile> machineProfiles;

    @JsonProperty("match_specifications")
    protected ItemList<MatchSpecification> matchSpecifications;

    @JsonProperty("parameter_sets")
    protected ItemList<ParameterSet> parameterSets;

    @JsonProperty("routines")
    protected ItemList<Routine> routines;

    @JsonProperty("stage_types")
    protected ItemList<DsstageType> stageTypes;

    @JsonProperty("standardization_rule_sets")
    protected ItemList<StandardizationRuleSet> standardizationRuleSets;

    @JsonProperty("table_definitions")
    protected ItemList<TableDefinition> tableDefinitions;

    @JsonProperty("transforms")
    protected ItemList<TransformsFunction> transforms;

    /**
     * Retrieve the {@code containers} property (displayed as '{@literal Containers}') of the object.
     * @return {@code ItemList<SharedContainer>}
     */
    @JsonProperty("containers")
    public ItemList<SharedContainer> getContainers() { return this.containers; }

    /**
     * Set the {@code containers} property (displayed as {@code Containers}) of the object.
     * @param containers the value to set
     */
    @JsonProperty("containers")
    public void setContainers(ItemList<SharedContainer> containers) { this.containers = containers; }

    /**
     * Retrieve the {@code folders} property (displayed as '{@literal Folders}') of the object.
     * @return {@code ItemList<Dsfolder>}
     */
    @JsonProperty("folders")
    public ItemList<Dsfolder> getFolders() { return this.folders; }

    /**
     * Set the {@code folders} property (displayed as {@code Folders}) of the object.
     * @param folders the value to set
     */
    @JsonProperty("folders")
    public void setFolders(ItemList<Dsfolder> folders) { this.folders = folders; }

    /**
     * Retrieve the {@code host_(engine)} property (displayed as '{@literal Host (Engine)}') of the object.
     * @return {@code HostEngine}
     */
    @JsonProperty("host_(engine)")
    public HostEngine getHostEngine() { return this.hostEngine; }

    /**
     * Set the {@code host_(engine)} property (displayed as {@code Host (Engine)}) of the object.
     * @param hostEngine the value to set
     */
    @JsonProperty("host_(engine)")
    public void setHostEngine(HostEngine hostEngine) { this.hostEngine = hostEngine; }

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
     * Retrieve the {@code include_for_lineage_desc} property (displayed as '{@literal Include for Lineage}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("include_for_lineage_desc")
    public Boolean getIncludeForLineageDesc() { return this.includeForLineageDesc; }

    /**
     * Set the {@code include_for_lineage_desc} property (displayed as {@code Include for Lineage}) of the object.
     * @param includeForLineageDesc the value to set
     */
    @JsonProperty("include_for_lineage_desc")
    public void setIncludeForLineageDesc(Boolean includeForLineageDesc) { this.includeForLineageDesc = includeForLineageDesc; }

    /**
     * Retrieve the {@code jobs} property (displayed as '{@literal Jobs}') of the object.
     * @return {@code ItemList<Dsjob>}
     */
    @JsonProperty("jobs")
    public ItemList<Dsjob> getJobs() { return this.jobs; }

    /**
     * Set the {@code jobs} property (displayed as {@code Jobs}) of the object.
     * @param jobs the value to set
     */
    @JsonProperty("jobs")
    public void setJobs(ItemList<Dsjob> jobs) { this.jobs = jobs; }

    /**
     * Retrieve the {@code machine_profiles} property (displayed as '{@literal Machine Profiles}') of the object.
     * @return {@code ItemList<MachineProfile>}
     */
    @JsonProperty("machine_profiles")
    public ItemList<MachineProfile> getMachineProfiles() { return this.machineProfiles; }

    /**
     * Set the {@code machine_profiles} property (displayed as {@code Machine Profiles}) of the object.
     * @param machineProfiles the value to set
     */
    @JsonProperty("machine_profiles")
    public void setMachineProfiles(ItemList<MachineProfile> machineProfiles) { this.machineProfiles = machineProfiles; }

    /**
     * Retrieve the {@code match_specifications} property (displayed as '{@literal Match Specifications}') of the object.
     * @return {@code ItemList<MatchSpecification>}
     */
    @JsonProperty("match_specifications")
    public ItemList<MatchSpecification> getMatchSpecifications() { return this.matchSpecifications; }

    /**
     * Set the {@code match_specifications} property (displayed as {@code Match Specifications}) of the object.
     * @param matchSpecifications the value to set
     */
    @JsonProperty("match_specifications")
    public void setMatchSpecifications(ItemList<MatchSpecification> matchSpecifications) { this.matchSpecifications = matchSpecifications; }

    /**
     * Retrieve the {@code parameter_sets} property (displayed as '{@literal Parameter Sets}') of the object.
     * @return {@code ItemList<ParameterSet>}
     */
    @JsonProperty("parameter_sets")
    public ItemList<ParameterSet> getParameterSets() { return this.parameterSets; }

    /**
     * Set the {@code parameter_sets} property (displayed as {@code Parameter Sets}) of the object.
     * @param parameterSets the value to set
     */
    @JsonProperty("parameter_sets")
    public void setParameterSets(ItemList<ParameterSet> parameterSets) { this.parameterSets = parameterSets; }

    /**
     * Retrieve the {@code routines} property (displayed as '{@literal Routines}') of the object.
     * @return {@code ItemList<Routine>}
     */
    @JsonProperty("routines")
    public ItemList<Routine> getRoutines() { return this.routines; }

    /**
     * Set the {@code routines} property (displayed as {@code Routines}) of the object.
     * @param routines the value to set
     */
    @JsonProperty("routines")
    public void setRoutines(ItemList<Routine> routines) { this.routines = routines; }

    /**
     * Retrieve the {@code stage_types} property (displayed as '{@literal Stage Types}') of the object.
     * @return {@code ItemList<DsstageType>}
     */
    @JsonProperty("stage_types")
    public ItemList<DsstageType> getStageTypes() { return this.stageTypes; }

    /**
     * Set the {@code stage_types} property (displayed as {@code Stage Types}) of the object.
     * @param stageTypes the value to set
     */
    @JsonProperty("stage_types")
    public void setStageTypes(ItemList<DsstageType> stageTypes) { this.stageTypes = stageTypes; }

    /**
     * Retrieve the {@code standardization_rule_sets} property (displayed as '{@literal Standardization Rule Sets}') of the object.
     * @return {@code ItemList<StandardizationRuleSet>}
     */
    @JsonProperty("standardization_rule_sets")
    public ItemList<StandardizationRuleSet> getStandardizationRuleSets() { return this.standardizationRuleSets; }

    /**
     * Set the {@code standardization_rule_sets} property (displayed as {@code Standardization Rule Sets}) of the object.
     * @param standardizationRuleSets the value to set
     */
    @JsonProperty("standardization_rule_sets")
    public void setStandardizationRuleSets(ItemList<StandardizationRuleSet> standardizationRuleSets) { this.standardizationRuleSets = standardizationRuleSets; }

    /**
     * Retrieve the {@code table_definitions} property (displayed as '{@literal Table Definitions}') of the object.
     * @return {@code ItemList<TableDefinition>}
     */
    @JsonProperty("table_definitions")
    public ItemList<TableDefinition> getTableDefinitions() { return this.tableDefinitions; }

    /**
     * Set the {@code table_definitions} property (displayed as {@code Table Definitions}) of the object.
     * @param tableDefinitions the value to set
     */
    @JsonProperty("table_definitions")
    public void setTableDefinitions(ItemList<TableDefinition> tableDefinitions) { this.tableDefinitions = tableDefinitions; }

    /**
     * Retrieve the {@code transforms} property (displayed as '{@literal Transforms}') of the object.
     * @return {@code ItemList<TransformsFunction>}
     */
    @JsonProperty("transforms")
    public ItemList<TransformsFunction> getTransforms() { return this.transforms; }

    /**
     * Set the {@code transforms} property (displayed as {@code Transforms}) of the object.
     * @param transforms the value to set
     */
    @JsonProperty("transforms")
    public void setTransforms(ItemList<TransformsFunction> transforms) { this.transforms = transforms; }

}
