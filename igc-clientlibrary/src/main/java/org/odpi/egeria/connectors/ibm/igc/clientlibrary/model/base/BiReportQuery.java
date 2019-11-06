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
 * POJO for the {@code bi_report_query} asset type in IGC, displayed as '{@literal BI Report Query}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiReportQuery.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_report_query")
public class BiReportQuery extends InformationAsset {

    @JsonProperty("bi_collection")
    protected BiCollection biCollection;

    @JsonProperty("bi_model")
    protected BiModel biModel;

    @JsonProperty("bi_report")
    protected BiReport biReport;

    @JsonProperty("bi_report_query_detail_filters")
    protected ItemList<BiReportQueryDetailFilter> biReportQueryDetailFilters;

    @JsonProperty("bi_report_query_items")
    protected ItemList<BiReportQueryItem> biReportQueryItems;

    @JsonProperty("bi_report_query_summary_filters")
    protected ItemList<BiReportQuerySummaryFilter> biReportQuerySummaryFilters;

    @JsonProperty("condition")
    protected String condition;

    @JsonProperty("contained_by_bi_report_queries")
    protected ItemList<BiReportQuery> containedByBiReportQueries;

    @JsonProperty("contains_bi_queries")
    protected ItemList<BiReportQuery> containsBiQueries;

    @JsonProperty("context")
    protected ItemList<MainObject> context;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

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

    @JsonProperty("type")
    protected String type;

    @JsonProperty("uses_bi_collections")
    protected ItemList<BiCollection> usesBiCollections;

    @JsonProperty("uses_database_tables_or_views")
    protected ItemList<Datagroup> usesDatabaseTablesOrViews;

    @JsonProperty("written_by_(design)")
    protected ItemList<InformationAsset> writtenByDesign;

    @JsonProperty("written_by_(operational)")
    protected ItemList<InformationAsset> writtenByOperational;

    @JsonProperty("written_by_(static)")
    protected ItemList<InformationAsset> writtenByStatic;

    @JsonProperty("written_by_(user_defined)")
    protected ItemList<InformationAsset> writtenByUserDefined;

    /**
     * Retrieve the {@code bi_collection} property (displayed as '{@literal BI Collection}') of the object.
     * @return {@code BiCollection}
     */
    @JsonProperty("bi_collection")
    public BiCollection getBiCollection() { return this.biCollection; }

    /**
     * Set the {@code bi_collection} property (displayed as {@code BI Collection}) of the object.
     * @param biCollection the value to set
     */
    @JsonProperty("bi_collection")
    public void setBiCollection(BiCollection biCollection) { this.biCollection = biCollection; }

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
     * Retrieve the {@code bi_report} property (displayed as '{@literal BI Report}') of the object.
     * @return {@code BiReport}
     */
    @JsonProperty("bi_report")
    public BiReport getBiReport() { return this.biReport; }

    /**
     * Set the {@code bi_report} property (displayed as {@code BI Report}) of the object.
     * @param biReport the value to set
     */
    @JsonProperty("bi_report")
    public void setBiReport(BiReport biReport) { this.biReport = biReport; }

    /**
     * Retrieve the {@code bi_report_query_detail_filters} property (displayed as '{@literal BI Query Detail Filters}') of the object.
     * @return {@code ItemList<BiReportQueryDetailFilter>}
     */
    @JsonProperty("bi_report_query_detail_filters")
    public ItemList<BiReportQueryDetailFilter> getBiReportQueryDetailFilters() { return this.biReportQueryDetailFilters; }

    /**
     * Set the {@code bi_report_query_detail_filters} property (displayed as {@code BI Query Detail Filters}) of the object.
     * @param biReportQueryDetailFilters the value to set
     */
    @JsonProperty("bi_report_query_detail_filters")
    public void setBiReportQueryDetailFilters(ItemList<BiReportQueryDetailFilter> biReportQueryDetailFilters) { this.biReportQueryDetailFilters = biReportQueryDetailFilters; }

    /**
     * Retrieve the {@code bi_report_query_items} property (displayed as '{@literal BI Report Query Items}') of the object.
     * @return {@code ItemList<BiReportQueryItem>}
     */
    @JsonProperty("bi_report_query_items")
    public ItemList<BiReportQueryItem> getBiReportQueryItems() { return this.biReportQueryItems; }

    /**
     * Set the {@code bi_report_query_items} property (displayed as {@code BI Report Query Items}) of the object.
     * @param biReportQueryItems the value to set
     */
    @JsonProperty("bi_report_query_items")
    public void setBiReportQueryItems(ItemList<BiReportQueryItem> biReportQueryItems) { this.biReportQueryItems = biReportQueryItems; }

    /**
     * Retrieve the {@code bi_report_query_summary_filters} property (displayed as '{@literal BI Query Summary Filters}') of the object.
     * @return {@code ItemList<BiReportQuerySummaryFilter>}
     */
    @JsonProperty("bi_report_query_summary_filters")
    public ItemList<BiReportQuerySummaryFilter> getBiReportQuerySummaryFilters() { return this.biReportQuerySummaryFilters; }

    /**
     * Set the {@code bi_report_query_summary_filters} property (displayed as {@code BI Query Summary Filters}) of the object.
     * @param biReportQuerySummaryFilters the value to set
     */
    @JsonProperty("bi_report_query_summary_filters")
    public void setBiReportQuerySummaryFilters(ItemList<BiReportQuerySummaryFilter> biReportQuerySummaryFilters) { this.biReportQuerySummaryFilters = biReportQuerySummaryFilters; }

    /**
     * Retrieve the {@code condition} property (displayed as '{@literal Condition}') of the object.
     * @return {@code String}
     */
    @JsonProperty("condition")
    public String getCondition() { return this.condition; }

    /**
     * Set the {@code condition} property (displayed as {@code Condition}) of the object.
     * @param condition the value to set
     */
    @JsonProperty("condition")
    public void setCondition(String condition) { this.condition = condition; }

    /**
     * Retrieve the {@code contained_by_bi_report_queries} property (displayed as '{@literal Contained by BI Report Queries}') of the object.
     * @return {@code ItemList<BiReportQuery>}
     */
    @JsonProperty("contained_by_bi_report_queries")
    public ItemList<BiReportQuery> getContainedByBiReportQueries() { return this.containedByBiReportQueries; }

    /**
     * Set the {@code contained_by_bi_report_queries} property (displayed as {@code Contained by BI Report Queries}) of the object.
     * @param containedByBiReportQueries the value to set
     */
    @JsonProperty("contained_by_bi_report_queries")
    public void setContainedByBiReportQueries(ItemList<BiReportQuery> containedByBiReportQueries) { this.containedByBiReportQueries = containedByBiReportQueries; }

    /**
     * Retrieve the {@code contains_bi_queries} property (displayed as '{@literal Contains BI Queries}') of the object.
     * @return {@code ItemList<BiReportQuery>}
     */
    @JsonProperty("contains_bi_queries")
    public ItemList<BiReportQuery> getContainsBiQueries() { return this.containsBiQueries; }

    /**
     * Set the {@code contains_bi_queries} property (displayed as {@code Contains BI Queries}) of the object.
     * @param containsBiQueries the value to set
     */
    @JsonProperty("contains_bi_queries")
    public void setContainsBiQueries(ItemList<BiReportQuery> containsBiQueries) { this.containsBiQueries = containsBiQueries; }

    /**
     * Retrieve the {@code context} property (displayed as '{@literal Context}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("context")
    public ItemList<MainObject> getTheContext() { return this.context; }

    /**
     * Set the {@code context} property (displayed as {@code Context}) of the object.
     * @param context the value to set
     */
    @JsonProperty("context")
    public void setTheContext(ItemList<MainObject> context) { this.context = context; }

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

    /**
     * Retrieve the {@code uses_bi_collections} property (displayed as '{@literal Uses BI Collections}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("uses_bi_collections")
    public ItemList<BiCollection> getUsesBiCollections() { return this.usesBiCollections; }

    /**
     * Set the {@code uses_bi_collections} property (displayed as {@code Uses BI Collections}) of the object.
     * @param usesBiCollections the value to set
     */
    @JsonProperty("uses_bi_collections")
    public void setUsesBiCollections(ItemList<BiCollection> usesBiCollections) { this.usesBiCollections = usesBiCollections; }

    /**
     * Retrieve the {@code uses_database_tables_or_views} property (displayed as '{@literal Uses Database Tables or Views}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("uses_database_tables_or_views")
    public ItemList<Datagroup> getUsesDatabaseTablesOrViews() { return this.usesDatabaseTablesOrViews; }

    /**
     * Set the {@code uses_database_tables_or_views} property (displayed as {@code Uses Database Tables or Views}) of the object.
     * @param usesDatabaseTablesOrViews the value to set
     */
    @JsonProperty("uses_database_tables_or_views")
    public void setUsesDatabaseTablesOrViews(ItemList<Datagroup> usesDatabaseTablesOrViews) { this.usesDatabaseTablesOrViews = usesDatabaseTablesOrViews; }

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
