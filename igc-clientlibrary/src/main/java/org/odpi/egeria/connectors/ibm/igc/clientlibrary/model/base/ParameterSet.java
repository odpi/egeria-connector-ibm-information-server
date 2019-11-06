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
 * POJO for the {@code parameter_set} asset type in IGC, displayed as '{@literal Parameter Set}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ParameterSet.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("parameter_set")
public class ParameterSet extends InformationAsset {

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("jobs")
    protected ItemList<Jobdef> jobs;

    @JsonProperty("parameters")
    protected ItemList<DsparameterSet> parameters;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

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
     * Retrieve the {@code jobs} property (displayed as '{@literal Jobs}') of the object.
     * @return {@code ItemList<Jobdef>}
     */
    @JsonProperty("jobs")
    public ItemList<Jobdef> getJobs() { return this.jobs; }

    /**
     * Set the {@code jobs} property (displayed as {@code Jobs}) of the object.
     * @param jobs the value to set
     */
    @JsonProperty("jobs")
    public void setJobs(ItemList<Jobdef> jobs) { this.jobs = jobs; }

    /**
     * Retrieve the {@code parameters} property (displayed as '{@literal Parameters}') of the object.
     * @return {@code ItemList<DsparameterSet>}
     */
    @JsonProperty("parameters")
    public ItemList<DsparameterSet> getParameters() { return this.parameters; }

    /**
     * Set the {@code parameters} property (displayed as {@code Parameters}) of the object.
     * @param parameters the value to set
     */
    @JsonProperty("parameters")
    public void setParameters(ItemList<DsparameterSet> parameters) { this.parameters = parameters; }

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
