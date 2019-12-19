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

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("uses_bi_collections")
    protected ItemList<BiCollection> usesBiCollections;

    @JsonProperty("uses_database_tables_or_views")
    protected ItemList<Datagroup> usesDatabaseTablesOrViews;

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

}
