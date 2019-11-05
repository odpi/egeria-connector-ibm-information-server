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
 * POJO for the {@code amazon_s3_bucket} asset type in IGC, displayed as '{@literal Amazon S3 Bucket}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("amazon_s3_bucket")
public class AmazonS3Bucket extends InformationAsset {

    @JsonProperty("contains_amazon_s3_data_file_folders")
    protected ItemList<MainObject> containsAmazonS3DataFileFolders;

    @JsonProperty("contains_amazon_s3_data_files")
    protected ItemList<AmazonS3DataFile> containsAmazonS3DataFiles;

    @JsonProperty("data_connection")
    protected ItemList<DataConnection> dataConnection;

    @JsonProperty("host")
    protected Host host;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("location")
    protected String location;

    @JsonProperty("source_creation_date")
    protected Date sourceCreationDate;

    @JsonProperty("source_modification_date")
    protected Date sourceModificationDate;

    @JsonProperty("uses_data_file_definitions")
    protected ItemList<DataFileDefinition> usesDataFileDefinitions;

    /**
     * Retrieve the {@code contains_amazon_s3_data_file_folders} property (displayed as '{@literal Contains Amazon S3 Data File Folders}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("contains_amazon_s3_data_file_folders")
    public ItemList<MainObject> getContainsAmazonS3DataFileFolders() { return this.containsAmazonS3DataFileFolders; }

    /**
     * Set the {@code contains_amazon_s3_data_file_folders} property (displayed as {@code Contains Amazon S3 Data File Folders}) of the object.
     * @param containsAmazonS3DataFileFolders the value to set
     */
    @JsonProperty("contains_amazon_s3_data_file_folders")
    public void setContainsAmazonS3DataFileFolders(ItemList<MainObject> containsAmazonS3DataFileFolders) { this.containsAmazonS3DataFileFolders = containsAmazonS3DataFileFolders; }

    /**
     * Retrieve the {@code contains_amazon_s3_data_files} property (displayed as '{@literal Contains Amazon S3 Data Files}') of the object.
     * @return {@code ItemList<AmazonS3DataFile>}
     */
    @JsonProperty("contains_amazon_s3_data_files")
    public ItemList<AmazonS3DataFile> getContainsAmazonS3DataFiles() { return this.containsAmazonS3DataFiles; }

    /**
     * Set the {@code contains_amazon_s3_data_files} property (displayed as {@code Contains Amazon S3 Data Files}) of the object.
     * @param containsAmazonS3DataFiles the value to set
     */
    @JsonProperty("contains_amazon_s3_data_files")
    public void setContainsAmazonS3DataFiles(ItemList<AmazonS3DataFile> containsAmazonS3DataFiles) { this.containsAmazonS3DataFiles = containsAmazonS3DataFiles; }

    /**
     * Retrieve the {@code data_connection} property (displayed as '{@literal Data Connection}') of the object.
     * @return {@code ItemList<DataConnection>}
     */
    @JsonProperty("data_connection")
    public ItemList<DataConnection> getDataConnection() { return this.dataConnection; }

    /**
     * Set the {@code data_connection} property (displayed as {@code Data Connection}) of the object.
     * @param dataConnection the value to set
     */
    @JsonProperty("data_connection")
    public void setDataConnection(ItemList<DataConnection> dataConnection) { this.dataConnection = dataConnection; }

    /**
     * Retrieve the {@code host} property (displayed as '{@literal Host}') of the object.
     * @return {@code Host}
     */
    @JsonProperty("host")
    public Host getHost() { return this.host; }

    /**
     * Set the {@code host} property (displayed as {@code Host}) of the object.
     * @param host the value to set
     */
    @JsonProperty("host")
    public void setHost(Host host) { this.host = host; }

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
     * Retrieve the {@code source_creation_date} property (displayed as '{@literal Source Creation Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("source_creation_date")
    public Date getSourceCreationDate() { return this.sourceCreationDate; }

    /**
     * Set the {@code source_creation_date} property (displayed as {@code Source Creation Date}) of the object.
     * @param sourceCreationDate the value to set
     */
    @JsonProperty("source_creation_date")
    public void setSourceCreationDate(Date sourceCreationDate) { this.sourceCreationDate = sourceCreationDate; }

    /**
     * Retrieve the {@code source_modification_date} property (displayed as '{@literal Source Modification Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("source_modification_date")
    public Date getSourceModificationDate() { return this.sourceModificationDate; }

    /**
     * Set the {@code source_modification_date} property (displayed as {@code Source Modification Date}) of the object.
     * @param sourceModificationDate the value to set
     */
    @JsonProperty("source_modification_date")
    public void setSourceModificationDate(Date sourceModificationDate) { this.sourceModificationDate = sourceModificationDate; }

    /**
     * Retrieve the {@code uses_data_file_definitions} property (displayed as '{@literal Uses Data File Definitions}') of the object.
     * @return {@code ItemList<DataFileDefinition>}
     */
    @JsonProperty("uses_data_file_definitions")
    public ItemList<DataFileDefinition> getUsesDataFileDefinitions() { return this.usesDataFileDefinitions; }

    /**
     * Set the {@code uses_data_file_definitions} property (displayed as {@code Uses Data File Definitions}) of the object.
     * @param usesDataFileDefinitions the value to set
     */
    @JsonProperty("uses_data_file_definitions")
    public void setUsesDataFileDefinitions(ItemList<DataFileDefinition> usesDataFileDefinitions) { this.usesDataFileDefinitions = usesDataFileDefinitions; }

}
