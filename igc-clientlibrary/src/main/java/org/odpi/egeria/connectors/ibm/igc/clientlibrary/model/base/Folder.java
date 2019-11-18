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
 * POJO for the {@code folder} asset type in IGC, displayed as '{@literal Folder}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Folder.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("folder")
public class Folder extends Reference {

    @JsonProperty("contains_folders")
    protected ItemList<Folder> containsFolders;

    @JsonProperty("contains_mapping_documents")
    protected ItemList<MainObject> containsMappingDocuments;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("parent_folder")
    protected Folder parentFolder;

    /**
     * Retrieve the {@code contains_folders} property (displayed as '{@literal Contains Folders}') of the object.
     * @return {@code ItemList<Folder>}
     */
    @JsonProperty("contains_folders")
    public ItemList<Folder> getContainsFolders() { return this.containsFolders; }

    /**
     * Set the {@code contains_folders} property (displayed as {@code Contains Folders}) of the object.
     * @param containsFolders the value to set
     */
    @JsonProperty("contains_folders")
    public void setContainsFolders(ItemList<Folder> containsFolders) { this.containsFolders = containsFolders; }

    /**
     * Retrieve the {@code contains_mapping_documents} property (displayed as '{@literal Contains Mapping Documents}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("contains_mapping_documents")
    public ItemList<MainObject> getContainsMappingDocuments() { return this.containsMappingDocuments; }

    /**
     * Set the {@code contains_mapping_documents} property (displayed as {@code Contains Mapping Documents}) of the object.
     * @param containsMappingDocuments the value to set
     */
    @JsonProperty("contains_mapping_documents")
    public void setContainsMappingDocuments(ItemList<MainObject> containsMappingDocuments) { this.containsMappingDocuments = containsMappingDocuments; }

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("description")
    public String getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(String description) { this.description = description; }

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
     * @return {@code Folder}
     */
    @JsonProperty("parent_folder")
    public Folder getParentFolder() { return this.parentFolder; }

    /**
     * Set the {@code parent_folder} property (displayed as {@code Parent Folder}) of the object.
     * @param parentFolder the value to set
     */
    @JsonProperty("parent_folder")
    public void setParentFolder(Folder parentFolder) { this.parentFolder = parentFolder; }

}
