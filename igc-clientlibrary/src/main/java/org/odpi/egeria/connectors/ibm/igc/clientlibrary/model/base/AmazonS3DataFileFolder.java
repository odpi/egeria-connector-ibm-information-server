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
 * POJO for the {@code amazon_s3_data_file_folder} asset type in IGC, displayed as '{@literal Amazon S3 Data File Folder}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AmazonS3DataFileFolder.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("amazon_s3_data_file_folder")
public class AmazonS3DataFileFolder extends InformationAsset {

    @JsonProperty("amazon_s3_bucket")
    protected AmazonS3Bucket amazonS3Bucket;

    @JsonProperty("amazon_s3_data_file_folder")
    protected AmazonS3DataFileFolder amazonS3DataFileFolder;

    @JsonProperty("contains_amazon_s3_data_file_folders")
    protected ItemList<MainObject> containsAmazonS3DataFileFolders;

    @JsonProperty("contains_amazon_s3_data_files")
    protected ItemList<AmazonS3DataFile> containsAmazonS3DataFiles;

    @JsonProperty("data_connection")
    protected ItemList<DataConnection> dataConnection;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("include_for_business_lineage")
    protected Boolean includeForBusinessLineage;

    @JsonProperty("location")
    protected String location;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("parent_folder")
    protected MainObject parentFolder;

    @JsonProperty("same_as_data_sources")
    protected ItemList<MainObject> sameAsDataSources;

    @JsonProperty("source_creation_date")
    protected Date sourceCreationDate;

    @JsonProperty("source_modification_date")
    protected Date sourceModificationDate;

    @JsonProperty("synchronized_from")
    protected String synchronizedFrom;

    @JsonProperty("uses_data_file_definitions")
    protected ItemList<DataFileDefinition> usesDataFileDefinitions;

    /**
     * Retrieve the {@code amazon_s3_bucket} property (displayed as '{@literal Amazon S3 Bucket}') of the object.
     * @return {@code AmazonS3Bucket}
     */
    @JsonProperty("amazon_s3_bucket")
    public AmazonS3Bucket getAmazonS3Bucket() { return this.amazonS3Bucket; }

    /**
     * Set the {@code amazon_s3_bucket} property (displayed as {@code Amazon S3 Bucket}) of the object.
     * @param amazonS3Bucket the value to set
     */
    @JsonProperty("amazon_s3_bucket")
    public void setAmazonS3Bucket(AmazonS3Bucket amazonS3Bucket) { this.amazonS3Bucket = amazonS3Bucket; }

    /**
     * Retrieve the {@code amazon_s3_data_file_folder} property (displayed as '{@literal Amazon S3 Data File Folder}') of the object.
     * @return {@code AmazonS3DataFileFolder}
     */
    @JsonProperty("amazon_s3_data_file_folder")
    public AmazonS3DataFileFolder getAmazonS3DataFileFolder() { return this.amazonS3DataFileFolder; }

    /**
     * Set the {@code amazon_s3_data_file_folder} property (displayed as {@code Amazon S3 Data File Folder}) of the object.
     * @param amazonS3DataFileFolder the value to set
     */
    @JsonProperty("amazon_s3_data_file_folder")
    public void setAmazonS3DataFileFolder(AmazonS3DataFileFolder amazonS3DataFileFolder) { this.amazonS3DataFileFolder = amazonS3DataFileFolder; }

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
     * Retrieve the {@code impacted_by} property (displayed as '{@literal Impacted by}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacted_by")
    public ItemList<InformationAsset> getImpactedBy() { return this.impactedBy; }

    /**
     * Set the {@code impacted_by} property (displayed as {@code Impacted by}) of the object.
     * @param impactedBy the value to set
     */
    @JsonProperty("impacted_by")
    public void setImpactedBy(ItemList<InformationAsset> impactedBy) { this.impactedBy = impactedBy; }

    /**
     * Retrieve the {@code impacts_on} property (displayed as '{@literal Impacts on}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacts_on")
    public ItemList<InformationAsset> getImpactsOn() { return this.impactsOn; }

    /**
     * Set the {@code impacts_on} property (displayed as {@code Impacts on}) of the object.
     * @param impactsOn the value to set
     */
    @JsonProperty("impacts_on")
    public void setImpactsOn(ItemList<InformationAsset> impactsOn) { this.impactsOn = impactsOn; }

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
     * Retrieve the {@code include_for_business_lineage} property (displayed as '{@literal Include for Business Lineage}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("include_for_business_lineage")
    public Boolean getIncludeForBusinessLineage() { return this.includeForBusinessLineage; }

    /**
     * Set the {@code include_for_business_lineage} property (displayed as {@code Include for Business Lineage}) of the object.
     * @param includeForBusinessLineage the value to set
     */
    @JsonProperty("include_for_business_lineage")
    public void setIncludeForBusinessLineage(Boolean includeForBusinessLineage) { this.includeForBusinessLineage = includeForBusinessLineage; }

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
     * Retrieve the {@code parent_folder} property (displayed as '{@literal Data File Folder}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("parent_folder")
    public MainObject getParentFolder() { return this.parentFolder; }

    /**
     * Set the {@code parent_folder} property (displayed as {@code Data File Folder}) of the object.
     * @param parentFolder the value to set
     */
    @JsonProperty("parent_folder")
    public void setParentFolder(MainObject parentFolder) { this.parentFolder = parentFolder; }

    /**
     * Retrieve the {@code same_as_data_sources} property (displayed as '{@literal Same as Data Sources}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("same_as_data_sources")
    public ItemList<MainObject> getSameAsDataSources() { return this.sameAsDataSources; }

    /**
     * Set the {@code same_as_data_sources} property (displayed as {@code Same as Data Sources}) of the object.
     * @param sameAsDataSources the value to set
     */
    @JsonProperty("same_as_data_sources")
    public void setSameAsDataSources(ItemList<MainObject> sameAsDataSources) { this.sameAsDataSources = sameAsDataSources; }

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
     * Retrieve the {@code synchronized_from} property (displayed as '{@literal Synchronized From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("synchronized_from")
    public String getSynchronizedFrom() { return this.synchronizedFrom; }

    /**
     * Set the {@code synchronized_from} property (displayed as {@code Synchronized From}) of the object.
     * @param synchronizedFrom the value to set
     */
    @JsonProperty("synchronized_from")
    public void setSynchronizedFrom(String synchronizedFrom) { this.synchronizedFrom = synchronizedFrom; }

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
