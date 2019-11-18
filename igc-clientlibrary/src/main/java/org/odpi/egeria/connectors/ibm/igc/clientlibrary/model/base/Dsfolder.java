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

/**
 * POJO for the {@code dsfolder} asset type in IGC, displayed as '{@literal Folder}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Dsfolder.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsfolder")
public class Dsfolder extends Reference {

    @JsonProperty("contains_assets")
    protected ItemList<MainObject> containsAssets;

    @JsonProperty("contains_folders")
    protected ItemList<Dsfolder> containsFolders;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("parent_folder")
    protected Dsfolder parentFolder;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

    /**
     * Retrieve the {@code contains_assets} property (displayed as '{@literal Contains Assets}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("contains_assets")
    public ItemList<MainObject> getContainsAssets() { return this.containsAssets; }

    /**
     * Set the {@code contains_assets} property (displayed as {@code Contains Assets}) of the object.
     * @param containsAssets the value to set
     */
    @JsonProperty("contains_assets")
    public void setContainsAssets(ItemList<MainObject> containsAssets) { this.containsAssets = containsAssets; }

    /**
     * Retrieve the {@code contains_folders} property (displayed as '{@literal Contains Folders}') of the object.
     * @return {@code ItemList<Dsfolder>}
     */
    @JsonProperty("contains_folders")
    public ItemList<Dsfolder> getContainsFolders() { return this.containsFolders; }

    /**
     * Set the {@code contains_folders} property (displayed as {@code Contains Folders}) of the object.
     * @param containsFolders the value to set
     */
    @JsonProperty("contains_folders")
    public void setContainsFolders(ItemList<Dsfolder> containsFolders) { this.containsFolders = containsFolders; }

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
     * Retrieve the {@code parent_folder} property (displayed as '{@literal Parent Folder}') of the object.
     * @return {@code Dsfolder}
     */
    @JsonProperty("parent_folder")
    public Dsfolder getParentFolder() { return this.parentFolder; }

    /**
     * Set the {@code parent_folder} property (displayed as {@code Parent Folder}) of the object.
     * @param parentFolder the value to set
     */
    @JsonProperty("parent_folder")
    public void setParentFolder(Dsfolder parentFolder) { this.parentFolder = parentFolder; }

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
