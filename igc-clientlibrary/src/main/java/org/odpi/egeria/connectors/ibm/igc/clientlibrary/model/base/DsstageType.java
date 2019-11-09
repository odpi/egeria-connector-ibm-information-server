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
 * POJO for the {@code dsstage_type} asset type in IGC, displayed as '{@literal Stage Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DsstageType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsstage_type")
public class DsstageType extends StageType {

    @JsonProperty("dll_name")
    protected String dllName;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("ole_type")
    protected String oleType;

    @JsonProperty("properties")
    protected ItemList<StageTypeDetail> properties;

    @JsonProperty("stages")
    protected ItemList<Stage> stages;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

    /**
     * Retrieve the {@code dll_name} property (displayed as '{@literal DLL Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("dll_name")
    public String getDllName() { return this.dllName; }

    /**
     * Set the {@code dll_name} property (displayed as {@code DLL Name}) of the object.
     * @param dllName the value to set
     */
    @JsonProperty("dll_name")
    public void setDllName(String dllName) { this.dllName = dllName; }

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
     * Retrieve the {@code ole_type} property (displayed as '{@literal OLE Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("ole_type")
    public String getOleType() { return this.oleType; }

    /**
     * Set the {@code ole_type} property (displayed as {@code OLE Type}) of the object.
     * @param oleType the value to set
     */
    @JsonProperty("ole_type")
    public void setOleType(String oleType) { this.oleType = oleType; }

    /**
     * Retrieve the {@code properties} property (displayed as '{@literal Properties}') of the object.
     * @return {@code ItemList<StageTypeDetail>}
     */
    @JsonProperty("properties")
    public ItemList<StageTypeDetail> getProperties() { return this.properties; }

    /**
     * Set the {@code properties} property (displayed as {@code Properties}) of the object.
     * @param properties the value to set
     */
    @JsonProperty("properties")
    public void setProperties(ItemList<StageTypeDetail> properties) { this.properties = properties; }

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
