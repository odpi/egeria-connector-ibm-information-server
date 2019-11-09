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
 * POJO for the {@code match_specification} asset type in IGC, displayed as '{@literal Match Specification}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MatchSpecification.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("match_specification")
public class MatchSpecification extends InformationAsset {

    @JsonProperty("dataFields")
    protected List<String> datafields;

    @JsonProperty("data_quality_specifications")
    protected ItemList<StandardizationObject> dataQualitySpecifications;

    @JsonProperty("folder")
    protected Dsfolder folder;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("used_by_stages")
    protected ItemList<Stage> usedByStages;

    /**
     * Retrieve the {@code dataFields} property (displayed as '{@literal Data Fields}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("dataFields")
    public List<String> getDatafields() { return this.datafields; }

    /**
     * Set the {@code dataFields} property (displayed as {@code Data Fields}) of the object.
     * @param datafields the value to set
     */
    @JsonProperty("dataFields")
    public void setDatafields(List<String> datafields) { this.datafields = datafields; }

    /**
     * Retrieve the {@code data_quality_specifications} property (displayed as '{@literal Data Quality Specifications}') of the object.
     * @return {@code ItemList<StandardizationObject>}
     */
    @JsonProperty("data_quality_specifications")
    public ItemList<StandardizationObject> getDataQualitySpecifications() { return this.dataQualitySpecifications; }

    /**
     * Set the {@code data_quality_specifications} property (displayed as {@code Data Quality Specifications}) of the object.
     * @param dataQualitySpecifications the value to set
     */
    @JsonProperty("data_quality_specifications")
    public void setDataQualitySpecifications(ItemList<StandardizationObject> dataQualitySpecifications) { this.dataQualitySpecifications = dataQualitySpecifications; }

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
     * Retrieve the {@code used_by_stages} property (displayed as '{@literal Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("used_by_stages")
    public ItemList<Stage> getUsedByStages() { return this.usedByStages; }

    /**
     * Set the {@code used_by_stages} property (displayed as {@code Stages}) of the object.
     * @param usedByStages the value to set
     */
    @JsonProperty("used_by_stages")
    public void setUsedByStages(ItemList<Stage> usedByStages) { this.usedByStages = usedByStages; }

}
