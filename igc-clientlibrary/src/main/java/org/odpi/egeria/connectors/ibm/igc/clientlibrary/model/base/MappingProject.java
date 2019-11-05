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

/**
 * POJO for the {@code mapping_project} asset type in IGC, displayed as '{@literal Mapping Project}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("mapping_project")
public class MappingProject extends InformationAsset {

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("generated_jobs")
    protected ItemList<Dsjob> generatedJobs;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("mapping_components")
    protected ItemList<MappingComponent> mappingComponents;

    @JsonProperty("mapping_specifications")
    protected ItemList<MappingSpecification> mappingSpecifications;

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
     * Retrieve the {@code mapping_specifications} property (displayed as '{@literal Mapping Specifications}') of the object.
     * @return {@code ItemList<MappingSpecification>}
     */
    @JsonProperty("mapping_specifications")
    public ItemList<MappingSpecification> getMappingSpecifications() { return this.mappingSpecifications; }

    /**
     * Set the {@code mapping_specifications} property (displayed as {@code Mapping Specifications}) of the object.
     * @param mappingSpecifications the value to set
     */
    @JsonProperty("mapping_specifications")
    public void setMappingSpecifications(ItemList<MappingSpecification> mappingSpecifications) { this.mappingSpecifications = mappingSpecifications; }

}
