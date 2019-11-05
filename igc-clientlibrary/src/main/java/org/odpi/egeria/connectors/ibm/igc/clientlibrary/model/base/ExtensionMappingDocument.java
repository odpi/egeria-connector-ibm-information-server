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
 * POJO for the {@code extension_mapping_document} asset type in IGC, displayed as '{@literal Extension Mapping Document}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("extension_mapping_document")
public class ExtensionMappingDocument extends InformationAsset {

    @JsonProperty("extension_mappings")
    protected ItemList<ExtensionMapping> extensionMappings;

    @JsonProperty("file_name")
    protected String fileName;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("parent_folder")
    protected ItemList<Folder> parentFolder;

    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code extension_mappings} property (displayed as '{@literal Extension Mappings}') of the object.
     * @return {@code ItemList<ExtensionMapping>}
     */
    @JsonProperty("extension_mappings")
    public ItemList<ExtensionMapping> getExtensionMappings() { return this.extensionMappings; }

    /**
     * Set the {@code extension_mappings} property (displayed as {@code Extension Mappings}) of the object.
     * @param extensionMappings the value to set
     */
    @JsonProperty("extension_mappings")
    public void setExtensionMappings(ItemList<ExtensionMapping> extensionMappings) { this.extensionMappings = extensionMappings; }

    /**
     * Retrieve the {@code file_name} property (displayed as '{@literal File Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("file_name")
    public String getFileName() { return this.fileName; }

    /**
     * Set the {@code file_name} property (displayed as {@code File Name}) of the object.
     * @param fileName the value to set
     */
    @JsonProperty("file_name")
    public void setFileName(String fileName) { this.fileName = fileName; }

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
     * Retrieve the {@code parent_folder} property (displayed as '{@literal Parent Folder}') of the object.
     * @return {@code ItemList<Folder>}
     */
    @JsonProperty("parent_folder")
    public ItemList<Folder> getParentFolder() { return this.parentFolder; }

    /**
     * Set the {@code parent_folder} property (displayed as {@code Parent Folder}) of the object.
     * @param parentFolder the value to set
     */
    @JsonProperty("parent_folder")
    public void setParentFolder(ItemList<Folder> parentFolder) { this.parentFolder = parentFolder; }

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
