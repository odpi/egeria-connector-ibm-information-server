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
 * POJO for the {@code bi_report_query_detail_filter} asset type in IGC, displayed as '{@literal Detail Filter}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiReportQueryDetailFilter.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_report_query_detail_filter")
public class BiReportQueryDetailFilter extends InformationAsset {

    @JsonProperty("bi_report_query")
    protected BiReportQuery biReportQuery;

    @JsonProperty("contains_bi_report_query_items")
    protected ItemList<Reportobject> containsBiReportQueryItems;

    @JsonProperty("expression")
    protected String expression;

    @JsonProperty("expression_in_title")
    protected String expressionInTitle;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("src_contains_bi_report_query_items")
    protected ItemList<Reportobject> srcContainsBiReportQueryItems;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("uses_bi_collection_members")
    protected ItemList<BiCollectionMember> usesBiCollectionMembers;

    @JsonProperty("uses_database_columns")
    protected ItemList<DataItem> usesDatabaseColumns;

    /**
     * Retrieve the {@code bi_report_query} property (displayed as '{@literal BI Report Query}') of the object.
     * @return {@code BiReportQuery}
     */
    @JsonProperty("bi_report_query")
    public BiReportQuery getBiReportQuery() { return this.biReportQuery; }

    /**
     * Set the {@code bi_report_query} property (displayed as {@code BI Report Query}) of the object.
     * @param biReportQuery the value to set
     */
    @JsonProperty("bi_report_query")
    public void setBiReportQuery(BiReportQuery biReportQuery) { this.biReportQuery = biReportQuery; }

    /**
     * Retrieve the {@code contains_bi_report_query_items} property (displayed as '{@literal References BI Report Query Items}') of the object.
     * @return {@code ItemList<Reportobject>}
     */
    @JsonProperty("contains_bi_report_query_items")
    public ItemList<Reportobject> getContainsBiReportQueryItems() { return this.containsBiReportQueryItems; }

    /**
     * Set the {@code contains_bi_report_query_items} property (displayed as {@code References BI Report Query Items}) of the object.
     * @param containsBiReportQueryItems the value to set
     */
    @JsonProperty("contains_bi_report_query_items")
    public void setContainsBiReportQueryItems(ItemList<Reportobject> containsBiReportQueryItems) { this.containsBiReportQueryItems = containsBiReportQueryItems; }

    /**
     * Retrieve the {@code expression} property (displayed as '{@literal Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("expression")
    public String getExpression() { return this.expression; }

    /**
     * Set the {@code expression} property (displayed as {@code Expression}) of the object.
     * @param expression the value to set
     */
    @JsonProperty("expression")
    public void setExpression(String expression) { this.expression = expression; }

    /**
     * Retrieve the {@code expression_in_title} property (displayed as '{@literal Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("expression_in_title")
    public String getExpressionInTitle() { return this.expressionInTitle; }

    /**
     * Set the {@code expression_in_title} property (displayed as {@code Expression}) of the object.
     * @param expressionInTitle the value to set
     */
    @JsonProperty("expression_in_title")
    public void setExpressionInTitle(String expressionInTitle) { this.expressionInTitle = expressionInTitle; }

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
     * Retrieve the {@code src_contains_bi_report_query_items} property (displayed as '{@literal Referenced by BI Report Query Item}') of the object.
     * @return {@code ItemList<Reportobject>}
     */
    @JsonProperty("src_contains_bi_report_query_items")
    public ItemList<Reportobject> getSrcContainsBiReportQueryItems() { return this.srcContainsBiReportQueryItems; }

    /**
     * Set the {@code src_contains_bi_report_query_items} property (displayed as {@code Referenced by BI Report Query Item}) of the object.
     * @param srcContainsBiReportQueryItems the value to set
     */
    @JsonProperty("src_contains_bi_report_query_items")
    public void setSrcContainsBiReportQueryItems(ItemList<Reportobject> srcContainsBiReportQueryItems) { this.srcContainsBiReportQueryItems = srcContainsBiReportQueryItems; }

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
     * Retrieve the {@code uses_bi_collection_members} property (displayed as '{@literal Uses BI Collection Members}') of the object.
     * @return {@code ItemList<BiCollectionMember>}
     */
    @JsonProperty("uses_bi_collection_members")
    public ItemList<BiCollectionMember> getUsesBiCollectionMembers() { return this.usesBiCollectionMembers; }

    /**
     * Set the {@code uses_bi_collection_members} property (displayed as {@code Uses BI Collection Members}) of the object.
     * @param usesBiCollectionMembers the value to set
     */
    @JsonProperty("uses_bi_collection_members")
    public void setUsesBiCollectionMembers(ItemList<BiCollectionMember> usesBiCollectionMembers) { this.usesBiCollectionMembers = usesBiCollectionMembers; }

    /**
     * Retrieve the {@code uses_database_columns} property (displayed as '{@literal Uses Database Columns}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("uses_database_columns")
    public ItemList<DataItem> getUsesDatabaseColumns() { return this.usesDatabaseColumns; }

    /**
     * Set the {@code uses_database_columns} property (displayed as {@code Uses Database Columns}) of the object.
     * @param usesDatabaseColumns the value to set
     */
    @JsonProperty("uses_database_columns")
    public void setUsesDatabaseColumns(ItemList<DataItem> usesDatabaseColumns) { this.usesDatabaseColumns = usesDatabaseColumns; }

}
