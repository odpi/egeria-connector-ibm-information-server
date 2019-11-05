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
 * POJO for the {@code bi_model} asset type in IGC, displayed as '{@literal BI Model}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_model")
public class BiModel extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("bi_collections")
    protected ItemList<BiCollection> biCollections;

    @JsonProperty("bi_cubes")
    protected ItemList<BiCube> biCubes;

    @JsonProperty("bi_filters")
    protected ItemList<BiFilter> biFilters;

    @JsonProperty("bi_folder_or_bi_model")
    protected ItemList<MainObject> biFolderOrBiModel;

    @JsonProperty("bi_hierarchies")
    protected ItemList<BiHierarchy> biHierarchies;

    @JsonProperty("bi_model_creation_date")
    protected Date biModelCreationDate;

    @JsonProperty("bi_model_modification_date")
    protected Date biModelModificationDate;

    @JsonProperty("bi_report_queries")
    protected ItemList<BiReportQuery> biReportQueries;

    @JsonProperty("bi_reports")
    protected ItemList<BiReport> biReports;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("child_bi_models")
    protected ItemList<BiModel> childBiModels;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("include_for_business_lineage")
    protected Boolean includeForBusinessLineage;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("read_by_(design)")
    protected ItemList<InformationAsset> readByDesign;

    @JsonProperty("read_by_(operational)")
    protected ItemList<InformationAsset> readByOperational;

    @JsonProperty("read_by_(static)")
    protected ItemList<InformationAsset> readByStatic;

    @JsonProperty("read_by_(user_defined)")
    protected ItemList<InformationAsset> readByUserDefined;

    @JsonProperty("used_by_bi_reports")
    protected ItemList<BiReport> usedByBiReports;

    @JsonProperty("uses_databases")
    protected ItemList<Database> usesDatabases;

    @JsonProperty("written_by_(design)")
    protected ItemList<InformationAsset> writtenByDesign;

    @JsonProperty("written_by_(operational)")
    protected ItemList<InformationAsset> writtenByOperational;

    @JsonProperty("written_by_(static)")
    protected ItemList<InformationAsset> writtenByStatic;

    @JsonProperty("written_by_(user_defined)")
    protected ItemList<InformationAsset> writtenByUserDefined;

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
     * Retrieve the {@code bi_collections} property (displayed as '{@literal BI Collections}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("bi_collections")
    public ItemList<BiCollection> getBiCollections() { return this.biCollections; }

    /**
     * Set the {@code bi_collections} property (displayed as {@code BI Collections}) of the object.
     * @param biCollections the value to set
     */
    @JsonProperty("bi_collections")
    public void setBiCollections(ItemList<BiCollection> biCollections) { this.biCollections = biCollections; }

    /**
     * Retrieve the {@code bi_cubes} property (displayed as '{@literal BI Cubes}') of the object.
     * @return {@code ItemList<BiCube>}
     */
    @JsonProperty("bi_cubes")
    public ItemList<BiCube> getBiCubes() { return this.biCubes; }

    /**
     * Set the {@code bi_cubes} property (displayed as {@code BI Cubes}) of the object.
     * @param biCubes the value to set
     */
    @JsonProperty("bi_cubes")
    public void setBiCubes(ItemList<BiCube> biCubes) { this.biCubes = biCubes; }

    /**
     * Retrieve the {@code bi_filters} property (displayed as '{@literal BI Filters}') of the object.
     * @return {@code ItemList<BiFilter>}
     */
    @JsonProperty("bi_filters")
    public ItemList<BiFilter> getBiFilters() { return this.biFilters; }

    /**
     * Set the {@code bi_filters} property (displayed as {@code BI Filters}) of the object.
     * @param biFilters the value to set
     */
    @JsonProperty("bi_filters")
    public void setBiFilters(ItemList<BiFilter> biFilters) { this.biFilters = biFilters; }

    /**
     * Retrieve the {@code bi_folder_or_bi_model} property (displayed as '{@literal BI Folder or BI Model}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("bi_folder_or_bi_model")
    public ItemList<MainObject> getBiFolderOrBiModel() { return this.biFolderOrBiModel; }

    /**
     * Set the {@code bi_folder_or_bi_model} property (displayed as {@code BI Folder or BI Model}) of the object.
     * @param biFolderOrBiModel the value to set
     */
    @JsonProperty("bi_folder_or_bi_model")
    public void setBiFolderOrBiModel(ItemList<MainObject> biFolderOrBiModel) { this.biFolderOrBiModel = biFolderOrBiModel; }

    /**
     * Retrieve the {@code bi_hierarchies} property (displayed as '{@literal BI Hierarchies}') of the object.
     * @return {@code ItemList<BiHierarchy>}
     */
    @JsonProperty("bi_hierarchies")
    public ItemList<BiHierarchy> getBiHierarchies() { return this.biHierarchies; }

    /**
     * Set the {@code bi_hierarchies} property (displayed as {@code BI Hierarchies}) of the object.
     * @param biHierarchies the value to set
     */
    @JsonProperty("bi_hierarchies")
    public void setBiHierarchies(ItemList<BiHierarchy> biHierarchies) { this.biHierarchies = biHierarchies; }

    /**
     * Retrieve the {@code bi_model_creation_date} property (displayed as '{@literal BI Model Creation Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("bi_model_creation_date")
    public Date getBiModelCreationDate() { return this.biModelCreationDate; }

    /**
     * Set the {@code bi_model_creation_date} property (displayed as {@code BI Model Creation Date}) of the object.
     * @param biModelCreationDate the value to set
     */
    @JsonProperty("bi_model_creation_date")
    public void setBiModelCreationDate(Date biModelCreationDate) { this.biModelCreationDate = biModelCreationDate; }

    /**
     * Retrieve the {@code bi_model_modification_date} property (displayed as '{@literal BI Model Modification Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("bi_model_modification_date")
    public Date getBiModelModificationDate() { return this.biModelModificationDate; }

    /**
     * Set the {@code bi_model_modification_date} property (displayed as {@code BI Model Modification Date}) of the object.
     * @param biModelModificationDate the value to set
     */
    @JsonProperty("bi_model_modification_date")
    public void setBiModelModificationDate(Date biModelModificationDate) { this.biModelModificationDate = biModelModificationDate; }

    /**
     * Retrieve the {@code bi_report_queries} property (displayed as '{@literal BI Report Queries}') of the object.
     * @return {@code ItemList<BiReportQuery>}
     */
    @JsonProperty("bi_report_queries")
    public ItemList<BiReportQuery> getBiReportQueries() { return this.biReportQueries; }

    /**
     * Set the {@code bi_report_queries} property (displayed as {@code BI Report Queries}) of the object.
     * @param biReportQueries the value to set
     */
    @JsonProperty("bi_report_queries")
    public void setBiReportQueries(ItemList<BiReportQuery> biReportQueries) { this.biReportQueries = biReportQueries; }

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
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @param blueprintElements the value to set
     */
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code child_bi_models} property (displayed as '{@literal Child BI Models}') of the object.
     * @return {@code ItemList<BiModel>}
     */
    @JsonProperty("child_bi_models")
    public ItemList<BiModel> getChildBiModels() { return this.childBiModels; }

    /**
     * Set the {@code child_bi_models} property (displayed as {@code Child BI Models}) of the object.
     * @param childBiModels the value to set
     */
    @JsonProperty("child_bi_models")
    public void setChildBiModels(ItemList<BiModel> childBiModels) { this.childBiModels = childBiModels; }

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
     * Retrieve the {@code read_by_(design)} property (displayed as '{@literal Read by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(design)")
    public ItemList<InformationAsset> getReadByDesign() { return this.readByDesign; }

    /**
     * Set the {@code read_by_(design)} property (displayed as {@code Read by (Design)}) of the object.
     * @param readByDesign the value to set
     */
    @JsonProperty("read_by_(design)")
    public void setReadByDesign(ItemList<InformationAsset> readByDesign) { this.readByDesign = readByDesign; }

    /**
     * Retrieve the {@code read_by_(operational)} property (displayed as '{@literal Read by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(operational)")
    public ItemList<InformationAsset> getReadByOperational() { return this.readByOperational; }

    /**
     * Set the {@code read_by_(operational)} property (displayed as {@code Read by (Operational)}) of the object.
     * @param readByOperational the value to set
     */
    @JsonProperty("read_by_(operational)")
    public void setReadByOperational(ItemList<InformationAsset> readByOperational) { this.readByOperational = readByOperational; }

    /**
     * Retrieve the {@code read_by_(static)} property (displayed as '{@literal Read by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(static)")
    public ItemList<InformationAsset> getReadByStatic() { return this.readByStatic; }

    /**
     * Set the {@code read_by_(static)} property (displayed as {@code Read by (Static)}) of the object.
     * @param readByStatic the value to set
     */
    @JsonProperty("read_by_(static)")
    public void setReadByStatic(ItemList<InformationAsset> readByStatic) { this.readByStatic = readByStatic; }

    /**
     * Retrieve the {@code read_by_(user_defined)} property (displayed as '{@literal Read by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(user_defined)")
    public ItemList<InformationAsset> getReadByUserDefined() { return this.readByUserDefined; }

    /**
     * Set the {@code read_by_(user_defined)} property (displayed as {@code Read by (User-Defined)}) of the object.
     * @param readByUserDefined the value to set
     */
    @JsonProperty("read_by_(user_defined)")
    public void setReadByUserDefined(ItemList<InformationAsset> readByUserDefined) { this.readByUserDefined = readByUserDefined; }

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

    /**
     * Retrieve the {@code uses_databases} property (displayed as '{@literal Uses Databases}') of the object.
     * @return {@code ItemList<Database>}
     */
    @JsonProperty("uses_databases")
    public ItemList<Database> getUsesDatabases() { return this.usesDatabases; }

    /**
     * Set the {@code uses_databases} property (displayed as {@code Uses Databases}) of the object.
     * @param usesDatabases the value to set
     */
    @JsonProperty("uses_databases")
    public void setUsesDatabases(ItemList<Database> usesDatabases) { this.usesDatabases = usesDatabases; }

    /**
     * Retrieve the {@code written_by_(design)} property (displayed as '{@literal Written by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(design)")
    public ItemList<InformationAsset> getWrittenByDesign() { return this.writtenByDesign; }

    /**
     * Set the {@code written_by_(design)} property (displayed as {@code Written by (Design)}) of the object.
     * @param writtenByDesign the value to set
     */
    @JsonProperty("written_by_(design)")
    public void setWrittenByDesign(ItemList<InformationAsset> writtenByDesign) { this.writtenByDesign = writtenByDesign; }

    /**
     * Retrieve the {@code written_by_(operational)} property (displayed as '{@literal Written by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(operational)")
    public ItemList<InformationAsset> getWrittenByOperational() { return this.writtenByOperational; }

    /**
     * Set the {@code written_by_(operational)} property (displayed as {@code Written by (Operational)}) of the object.
     * @param writtenByOperational the value to set
     */
    @JsonProperty("written_by_(operational)")
    public void setWrittenByOperational(ItemList<InformationAsset> writtenByOperational) { this.writtenByOperational = writtenByOperational; }

    /**
     * Retrieve the {@code written_by_(static)} property (displayed as '{@literal Written by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(static)")
    public ItemList<InformationAsset> getWrittenByStatic() { return this.writtenByStatic; }

    /**
     * Set the {@code written_by_(static)} property (displayed as {@code Written by (Static)}) of the object.
     * @param writtenByStatic the value to set
     */
    @JsonProperty("written_by_(static)")
    public void setWrittenByStatic(ItemList<InformationAsset> writtenByStatic) { this.writtenByStatic = writtenByStatic; }

    /**
     * Retrieve the {@code written_by_(user_defined)} property (displayed as '{@literal Written by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(user_defined)")
    public ItemList<InformationAsset> getWrittenByUserDefined() { return this.writtenByUserDefined; }

    /**
     * Set the {@code written_by_(user_defined)} property (displayed as {@code Written by (User-Defined)}) of the object.
     * @param writtenByUserDefined the value to set
     */
    @JsonProperty("written_by_(user_defined)")
    public void setWrittenByUserDefined(ItemList<InformationAsset> writtenByUserDefined) { this.writtenByUserDefined = writtenByUserDefined; }

}
