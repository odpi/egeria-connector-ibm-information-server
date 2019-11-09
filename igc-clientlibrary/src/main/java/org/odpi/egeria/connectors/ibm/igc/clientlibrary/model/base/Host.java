/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code host} asset type in IGC, displayed as '{@literal Host}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Host.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HostEngine.class, name = "host_(engine)"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("host")
public class Host extends InformationAsset {

    @JsonProperty("amazon_s3_buckets")
    protected ItemList<AmazonS3Bucket> amazonS3Buckets;

    /**
     * No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("data_connections")
    protected ItemList<Connector> dataConnections;

    @JsonProperty("data_file_folders")
    protected ItemList<DataFileFolder> dataFileFolders;

    @JsonProperty("data_files")
    protected ItemList<DataFile> dataFiles;

    @JsonProperty("databases")
    protected ItemList<Database> databases;

    @JsonProperty("idoc_types")
    protected ItemList<IdocType> idocTypes;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("location")
    protected String location;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("network_node")
    protected String networkNode;

    @JsonProperty("transformation_projects")
    protected ItemList<TransformationProject> transformationProjects;

    /**
     * Retrieve the {@code amazon_s3_buckets} property (displayed as '{@literal Amazon S3 Buckets}') of the object.
     * @return {@code ItemList<AmazonS3Bucket>}
     */
    @JsonProperty("amazon_s3_buckets")
    public ItemList<AmazonS3Bucket> getAmazonS3Buckets() { return this.amazonS3Buckets; }

    /**
     * Set the {@code amazon_s3_buckets} property (displayed as {@code Amazon S3 Buckets}) of the object.
     * @param amazonS3Buckets the value to set
     */
    @JsonProperty("amazon_s3_buckets")
    public void setAmazonS3Buckets(ItemList<AmazonS3Bucket> amazonS3Buckets) { this.amazonS3Buckets = amazonS3Buckets; }

    /**
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * No longer applicable from 11.7.0.0 onwards.
     *
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * No longer applicable from 11.7.0.0 onwards.
     *
     * @param blueprintElements the value to set
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code data_connections} property (displayed as '{@literal Data Connections}') of the object.
     * @return {@code ItemList<Connector>}
     */
    @JsonProperty("data_connections")
    public ItemList<Connector> getDataConnections() { return this.dataConnections; }

    /**
     * Set the {@code data_connections} property (displayed as {@code Data Connections}) of the object.
     * @param dataConnections the value to set
     */
    @JsonProperty("data_connections")
    public void setDataConnections(ItemList<Connector> dataConnections) { this.dataConnections = dataConnections; }

    /**
     * Retrieve the {@code data_file_folders} property (displayed as '{@literal Data File Folders}') of the object.
     * @return {@code ItemList<DataFileFolder>}
     */
    @JsonProperty("data_file_folders")
    public ItemList<DataFileFolder> getDataFileFolders() { return this.dataFileFolders; }

    /**
     * Set the {@code data_file_folders} property (displayed as {@code Data File Folders}) of the object.
     * @param dataFileFolders the value to set
     */
    @JsonProperty("data_file_folders")
    public void setDataFileFolders(ItemList<DataFileFolder> dataFileFolders) { this.dataFileFolders = dataFileFolders; }

    /**
     * Retrieve the {@code data_files} property (displayed as '{@literal Data Files}') of the object.
     * @return {@code ItemList<DataFile>}
     */
    @JsonProperty("data_files")
    public ItemList<DataFile> getDataFiles() { return this.dataFiles; }

    /**
     * Set the {@code data_files} property (displayed as {@code Data Files}) of the object.
     * @param dataFiles the value to set
     */
    @JsonProperty("data_files")
    public void setDataFiles(ItemList<DataFile> dataFiles) { this.dataFiles = dataFiles; }

    /**
     * Retrieve the {@code databases} property (displayed as '{@literal Databases}') of the object.
     * @return {@code ItemList<Database>}
     */
    @JsonProperty("databases")
    public ItemList<Database> getDatabases() { return this.databases; }

    /**
     * Set the {@code databases} property (displayed as {@code Databases}) of the object.
     * @param databases the value to set
     */
    @JsonProperty("databases")
    public void setDatabases(ItemList<Database> databases) { this.databases = databases; }

    /**
     * Retrieve the {@code idoc_types} property (displayed as '{@literal IDoc Types}') of the object.
     * @return {@code ItemList<IdocType>}
     */
    @JsonProperty("idoc_types")
    public ItemList<IdocType> getIdocTypes() { return this.idocTypes; }

    /**
     * Set the {@code idoc_types} property (displayed as {@code IDoc Types}) of the object.
     * @param idocTypes the value to set
     */
    @JsonProperty("idoc_types")
    public void setIdocTypes(ItemList<IdocType> idocTypes) { this.idocTypes = idocTypes; }

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
     * Retrieve the {@code location} property (displayed as '{@literal Location}') of the object.
     * @return {@code String}
     */
    @JsonProperty("location")
    public String getLocation() { return this.location; }

    /**
     * Set the {@code location} property (displayed as {@code Location}) of the object.
     * @param location the value to set
     */
    @JsonProperty("location")
    public void setLocation(String location) { this.location = location; }

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
     * Retrieve the {@code network_node} property (displayed as '{@literal Network Node}') of the object.
     * @return {@code String}
     */
    @JsonProperty("network_node")
    public String getNetworkNode() { return this.networkNode; }

    /**
     * Set the {@code network_node} property (displayed as {@code Network Node}) of the object.
     * @param networkNode the value to set
     */
    @JsonProperty("network_node")
    public void setNetworkNode(String networkNode) { this.networkNode = networkNode; }

    /**
     * Retrieve the {@code transformation_projects} property (displayed as '{@literal Transformation Projects}') of the object.
     * @return {@code ItemList<TransformationProject>}
     */
    @JsonProperty("transformation_projects")
    public ItemList<TransformationProject> getTransformationProjects() { return this.transformationProjects; }

    /**
     * Set the {@code transformation_projects} property (displayed as {@code Transformation Projects}) of the object.
     * @param transformationProjects the value to set
     */
    @JsonProperty("transformation_projects")
    public void setTransformationProjects(ItemList<TransformationProject> transformationProjects) { this.transformationProjects = transformationProjects; }

}
