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
 * POJO for the {@code data_file_definition} asset type in IGC, displayed as '{@literal Data File Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_file_definition")
public class DataFileDefinition extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("data_file_definition_records")
    protected ItemList<DataFileDefinitionRecord> dataFileDefinitionRecords;

    @JsonProperty("implemented_by_data_files")
    protected ItemList<MainObject> implementedByDataFiles;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("references_data_file_folders")
    protected ItemList<MainObject> referencesDataFileFolders;

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @param aliasBusinessName the value to set
     */
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code data_file_definition_records} property (displayed as '{@literal Data File Definition Records}') of the object.
     * @return {@code ItemList<DataFileDefinitionRecord>}
     */
    @JsonProperty("data_file_definition_records")
    public ItemList<DataFileDefinitionRecord> getDataFileDefinitionRecords() { return this.dataFileDefinitionRecords; }

    /**
     * Set the {@code data_file_definition_records} property (displayed as {@code Data File Definition Records}) of the object.
     * @param dataFileDefinitionRecords the value to set
     */
    @JsonProperty("data_file_definition_records")
    public void setDataFileDefinitionRecords(ItemList<DataFileDefinitionRecord> dataFileDefinitionRecords) { this.dataFileDefinitionRecords = dataFileDefinitionRecords; }

    /**
     * Retrieve the {@code implemented_by_data_files} property (displayed as '{@literal Implemented by Data Files}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("implemented_by_data_files")
    public ItemList<MainObject> getImplementedByDataFiles() { return this.implementedByDataFiles; }

    /**
     * Set the {@code implemented_by_data_files} property (displayed as {@code Implemented by Data Files}) of the object.
     * @param implementedByDataFiles the value to set
     */
    @JsonProperty("implemented_by_data_files")
    public void setImplementedByDataFiles(ItemList<MainObject> implementedByDataFiles) { this.implementedByDataFiles = implementedByDataFiles; }

    /**
     * Retrieve the {@code imported_from} property (displayed as '{@literal Imported From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("imported_from")
    public String getImportedFrom() { return this.importedFrom; }

    /**
     * Set the {@code imported_from} property (displayed as {@code Imported From}) of the object.
     * @param importedFrom the value to set
     */
    @JsonProperty("imported_from")
    public void setImportedFrom(String importedFrom) { this.importedFrom = importedFrom; }

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
     * Retrieve the {@code references_data_file_folders} property (displayed as '{@literal References Data File Folders}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("references_data_file_folders")
    public ItemList<MainObject> getReferencesDataFileFolders() { return this.referencesDataFileFolders; }

    /**
     * Set the {@code references_data_file_folders} property (displayed as {@code References Data File Folders}) of the object.
     * @param referencesDataFileFolders the value to set
     */
    @JsonProperty("references_data_file_folders")
    public void setReferencesDataFileFolders(ItemList<MainObject> referencesDataFileFolders) { this.referencesDataFileFolders = referencesDataFileFolders; }

}
