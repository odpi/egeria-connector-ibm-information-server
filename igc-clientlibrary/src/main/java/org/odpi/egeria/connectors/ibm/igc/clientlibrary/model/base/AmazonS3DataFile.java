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
 * POJO for the {@code amazon_s3_data_file} asset type in IGC, displayed as '{@literal Amazon S3 Data File}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AmazonS3DataFile.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("amazon_s3_data_file")
public class AmazonS3DataFile extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("compression")
    protected String compression;

    @JsonProperty("content_type")
    protected String contentType;

    @JsonProperty("data_file_records")
    protected ItemList<DataFileRecord> dataFileRecords;

    @JsonProperty("implements_data_file_definition")
    protected DataFileDefinition implementsDataFileDefinition;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("include_for_business_lineage")
    protected Boolean includeForBusinessLineage;

    @JsonProperty("parent_folder")
    protected AmazonS3DataFileFolder parentFolder;

    @JsonProperty("path")
    protected String path;

    @JsonProperty("size")
    protected Number size;

    @JsonProperty("source_creation_date")
    protected Date sourceCreationDate;

    @JsonProperty("source_modification_date")
    protected Date sourceModificationDate;

    @JsonProperty("synchronized_from")
    protected String synchronizedFrom;

    @JsonProperty("url")
    protected String url;

    @JsonProperty("version")
    protected String version;

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
     * Retrieve the {@code compression} property (displayed as '{@literal Compression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("compression")
    public String getCompression() { return this.compression; }

    /**
     * Set the {@code compression} property (displayed as {@code Compression}) of the object.
     * @param compression the value to set
     */
    @JsonProperty("compression")
    public void setCompression(String compression) { this.compression = compression; }

    /**
     * Retrieve the {@code content_type} property (displayed as '{@literal Content Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("content_type")
    public String getContentType() { return this.contentType; }

    /**
     * Set the {@code content_type} property (displayed as {@code Content Type}) of the object.
     * @param contentType the value to set
     */
    @JsonProperty("content_type")
    public void setContentType(String contentType) { this.contentType = contentType; }

    /**
     * Retrieve the {@code data_file_records} property (displayed as '{@literal Data File Records}') of the object.
     * @return {@code ItemList<DataFileRecord>}
     */
    @JsonProperty("data_file_records")
    public ItemList<DataFileRecord> getDataFileRecords() { return this.dataFileRecords; }

    /**
     * Set the {@code data_file_records} property (displayed as {@code Data File Records}) of the object.
     * @param dataFileRecords the value to set
     */
    @JsonProperty("data_file_records")
    public void setDataFileRecords(ItemList<DataFileRecord> dataFileRecords) { this.dataFileRecords = dataFileRecords; }

    /**
     * Retrieve the {@code implements_data_file_definition} property (displayed as '{@literal Implements Data File Definition}') of the object.
     * @return {@code DataFileDefinition}
     */
    @JsonProperty("implements_data_file_definition")
    public DataFileDefinition getImplementsDataFileDefinition() { return this.implementsDataFileDefinition; }

    /**
     * Set the {@code implements_data_file_definition} property (displayed as {@code Implements Data File Definition}) of the object.
     * @param implementsDataFileDefinition the value to set
     */
    @JsonProperty("implements_data_file_definition")
    public void setImplementsDataFileDefinition(DataFileDefinition implementsDataFileDefinition) { this.implementsDataFileDefinition = implementsDataFileDefinition; }

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
     * Retrieve the {@code parent_folder} property (displayed as '{@literal Parent Folder}') of the object.
     * @return {@code AmazonS3DataFileFolder}
     */
    @JsonProperty("parent_folder")
    public AmazonS3DataFileFolder getParentFolder() { return this.parentFolder; }

    /**
     * Set the {@code parent_folder} property (displayed as {@code Parent Folder}) of the object.
     * @param parentFolder the value to set
     */
    @JsonProperty("parent_folder")
    public void setParentFolder(AmazonS3DataFileFolder parentFolder) { this.parentFolder = parentFolder; }

    /**
     * Retrieve the {@code path} property (displayed as '{@literal Path}') of the object.
     * @return {@code String}
     */
    @JsonProperty("path")
    public String getPath() { return this.path; }

    /**
     * Set the {@code path} property (displayed as {@code Path}) of the object.
     * @param path the value to set
     */
    @JsonProperty("path")
    public void setPath(String path) { this.path = path; }

    /**
     * Retrieve the {@code size} property (displayed as '{@literal Size}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("size")
    public Number getSize() { return this.size; }

    /**
     * Set the {@code size} property (displayed as {@code Size}) of the object.
     * @param size the value to set
     */
    @JsonProperty("size")
    public void setSize(Number size) { this.size = size; }

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
     * Retrieve the {@code url} property (displayed as '{@literal URL}') of the object.
     * @return {@code String}
     */
    @JsonProperty("url")
    public String getTheUrl() { return this.url; }

    /**
     * Set the {@code url} property (displayed as {@code URL}) of the object.
     * @param url the value to set
     */
    @JsonProperty("url")
    public void setTheUrl(String url) { this.url = url; }

    /**
     * Retrieve the {@code version} property (displayed as '{@literal Version}') of the object.
     * @return {@code String}
     */
    @JsonProperty("version")
    public String getVersion() { return this.version; }

    /**
     * Set the {@code version} property (displayed as {@code Version}) of the object.
     * @param version the value to set
     */
    @JsonProperty("version")
    public void setVersion(String version) { this.version = version; }

}
