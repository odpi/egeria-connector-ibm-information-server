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
 * POJO for the {@code bi_cube} asset type in IGC, displayed as '{@literal BI Cube}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiCube.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_cube")
public class BiCube extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("author")
    protected String author;

    @JsonProperty("bi_collection_dimensions")
    protected ItemList<BiCollection> biCollectionDimensions;

    @JsonProperty("bi_collection_facts")
    protected ItemList<BiCollection> biCollectionFacts;

    @JsonProperty("bi_cube_creation_date")
    protected Date biCubeCreationDate;

    @JsonProperty("bi_cube_modification_date")
    protected Date biCubeModificationDate;

    @JsonProperty("bi_model")
    protected BiModel biModel;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("used_by_bi_reports")
    protected ItemList<BiReport> usedByBiReports;

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
     * Retrieve the {@code author} property (displayed as '{@literal Author}') of the object.
     * @return {@code String}
     */
    @JsonProperty("author")
    public String getAuthor() { return this.author; }

    /**
     * Set the {@code author} property (displayed as {@code Author}) of the object.
     * @param author the value to set
     */
    @JsonProperty("author")
    public void setAuthor(String author) { this.author = author; }

    /**
     * Retrieve the {@code bi_collection_dimensions} property (displayed as '{@literal BI Collection Dimensions}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("bi_collection_dimensions")
    public ItemList<BiCollection> getBiCollectionDimensions() { return this.biCollectionDimensions; }

    /**
     * Set the {@code bi_collection_dimensions} property (displayed as {@code BI Collection Dimensions}) of the object.
     * @param biCollectionDimensions the value to set
     */
    @JsonProperty("bi_collection_dimensions")
    public void setBiCollectionDimensions(ItemList<BiCollection> biCollectionDimensions) { this.biCollectionDimensions = biCollectionDimensions; }

    /**
     * Retrieve the {@code bi_collection_facts} property (displayed as '{@literal BI Collection Facts}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("bi_collection_facts")
    public ItemList<BiCollection> getBiCollectionFacts() { return this.biCollectionFacts; }

    /**
     * Set the {@code bi_collection_facts} property (displayed as {@code BI Collection Facts}) of the object.
     * @param biCollectionFacts the value to set
     */
    @JsonProperty("bi_collection_facts")
    public void setBiCollectionFacts(ItemList<BiCollection> biCollectionFacts) { this.biCollectionFacts = biCollectionFacts; }

    /**
     * Retrieve the {@code bi_cube_creation_date} property (displayed as '{@literal BI Cube Creation Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("bi_cube_creation_date")
    public Date getBiCubeCreationDate() { return this.biCubeCreationDate; }

    /**
     * Set the {@code bi_cube_creation_date} property (displayed as {@code BI Cube Creation Date}) of the object.
     * @param biCubeCreationDate the value to set
     */
    @JsonProperty("bi_cube_creation_date")
    public void setBiCubeCreationDate(Date biCubeCreationDate) { this.biCubeCreationDate = biCubeCreationDate; }

    /**
     * Retrieve the {@code bi_cube_modification_date} property (displayed as '{@literal BI Cube Modification Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("bi_cube_modification_date")
    public Date getBiCubeModificationDate() { return this.biCubeModificationDate; }

    /**
     * Set the {@code bi_cube_modification_date} property (displayed as {@code BI Cube Modification Date}) of the object.
     * @param biCubeModificationDate the value to set
     */
    @JsonProperty("bi_cube_modification_date")
    public void setBiCubeModificationDate(Date biCubeModificationDate) { this.biCubeModificationDate = biCubeModificationDate; }

    /**
     * Retrieve the {@code bi_model} property (displayed as '{@literal BI Model}') of the object.
     * @return {@code BiModel}
     */
    @JsonProperty("bi_model")
    public BiModel getBiModel() { return this.biModel; }

    /**
     * Set the {@code bi_model} property (displayed as {@code BI Model}) of the object.
     * @param biModel the value to set
     */
    @JsonProperty("bi_model")
    public void setBiModel(BiModel biModel) { this.biModel = biModel; }

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
     * Retrieve the {@code namespace} property (displayed as '{@literal Namespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("namespace")
    public String getNamespace() { return this.namespace; }

    /**
     * Set the {@code namespace} property (displayed as {@code Namespace}) of the object.
     * @param namespace the value to set
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) { this.namespace = namespace; }

    /**
     * Retrieve the {@code used_by_bi_reports} property (displayed as '{@literal Used by BI Reports}') of the object.
     * @return {@code ItemList<BiReport>}
     */
    @JsonProperty("used_by_bi_reports")
    public ItemList<BiReport> getUsedByBiReports() { return this.usedByBiReports; }

    /**
     * Set the {@code used_by_bi_reports} property (displayed as {@code Used by BI Reports}) of the object.
     * @param usedByBiReports the value to set
     */
    @JsonProperty("used_by_bi_reports")
    public void setUsedByBiReports(ItemList<BiReport> usedByBiReports) { this.usedByBiReports = usedByBiReports; }

}
