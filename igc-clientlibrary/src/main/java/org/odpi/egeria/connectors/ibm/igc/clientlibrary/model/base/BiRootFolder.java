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
 * POJO for the {@code bi_root_folder} asset type in IGC, displayed as '{@literal BI Folder}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiRootFolder.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_root_folder")
public class BiRootFolder extends Reference {

    @JsonProperty("bi_models")
    protected ItemList<BiModel> biModels;

    @JsonProperty("bi_reports")
    protected ItemList<BiReport> biReports;

    @JsonProperty("bi_server")
    protected BiServer biServer;

    @JsonProperty("contains_bi_folders")
    protected ItemList<BiFolder> containsBiFolders;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Retrieve the {@code bi_models} property (displayed as '{@literal BI Models}') of the object.
     * @return {@code ItemList<BiModel>}
     */
    @JsonProperty("bi_models")
    public ItemList<BiModel> getBiModels() { return this.biModels; }

    /**
     * Set the {@code bi_models} property (displayed as {@code BI Models}) of the object.
     * @param biModels the value to set
     */
    @JsonProperty("bi_models")
    public void setBiModels(ItemList<BiModel> biModels) { this.biModels = biModels; }

    /**
     * Retrieve the {@code bi_reports} property (displayed as '{@literal BI Reports}') of the object.
     * @return {@code ItemList<BiReport>}
     */
    @JsonProperty("bi_reports")
    public ItemList<BiReport> getBiReports() { return this.biReports; }

    /**
     * Set the {@code bi_reports} property (displayed as {@code BI Reports}) of the object.
     * @param biReports the value to set
     */
    @JsonProperty("bi_reports")
    public void setBiReports(ItemList<BiReport> biReports) { this.biReports = biReports; }

    /**
     * Retrieve the {@code bi_server} property (displayed as '{@literal BI Server}') of the object.
     * @return {@code BiServer}
     */
    @JsonProperty("bi_server")
    public BiServer getBiServer() { return this.biServer; }

    /**
     * Set the {@code bi_server} property (displayed as {@code BI Server}) of the object.
     * @param biServer the value to set
     */
    @JsonProperty("bi_server")
    public void setBiServer(BiServer biServer) { this.biServer = biServer; }

    /**
     * Retrieve the {@code contains_bi_folders} property (displayed as '{@literal Contains BI Folders}') of the object.
     * @return {@code ItemList<BiFolder>}
     */
    @JsonProperty("contains_bi_folders")
    public ItemList<BiFolder> getContainsBiFolders() { return this.containsBiFolders; }

    /**
     * Set the {@code contains_bi_folders} property (displayed as {@code Contains BI Folders}) of the object.
     * @param containsBiFolders the value to set
     */
    @JsonProperty("contains_bi_folders")
    public void setContainsBiFolders(ItemList<BiFolder> containsBiFolders) { this.containsBiFolders = containsBiFolders; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

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
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

}
