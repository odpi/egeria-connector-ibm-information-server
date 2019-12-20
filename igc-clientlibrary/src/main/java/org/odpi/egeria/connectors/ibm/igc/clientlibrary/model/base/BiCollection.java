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
import java.util.List;

/**
 * POJO for the {@code bi_collection} asset type in IGC, displayed as '{@literal BI Collection}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiCollection.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_collection")
public class BiCollection extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("bi_collection")
    protected BiCollection biCollection;

    @JsonProperty("bi_collection_members")
    protected ItemList<BiCollectionMember> biCollectionMembers;

    @JsonProperty("bi_filters")
    protected ItemList<BiFilter> biFilters;

    @JsonProperty("bi_hierarchies")
    protected ItemList<BiHierarchy> biHierarchies;

    @JsonProperty("bi_levels")
    protected ItemList<BiLevel> biLevels;

    @JsonProperty("bi_model")
    protected BiModel biModel;

    @JsonProperty("bi_model_or_bi_collection")
    protected ItemList<Olapobject> biModelOrBiCollection;

    @JsonProperty("filter_expression")
    protected List<String> filterExpression;

    @JsonProperty("has_olap_collection")
    protected ItemList<BiCollection> hasOlapCollection;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("join_condition")
    protected List<String> joinCondition;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("referenced_by_bi_collection")
    protected ItemList<BiCollection> referencedByBiCollection;

    @JsonProperty("referenced_by_bi_hierarchies")
    protected ItemList<BiHierarchy> referencedByBiHierarchies;

    @JsonProperty("references_bi_collections")
    protected ItemList<BiCollection> referencesBiCollections;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("type_definition")
    protected String typeDefinition;

    @JsonProperty("used_by_bi_cubes")
    protected ItemList<BiCube> usedByBiCubes;

    @JsonProperty("used_by_bi_report_queries")
    protected ItemList<BiReportQuery> usedByBiReportQueries;

    @JsonProperty("uses_database_tables_or_views")
    protected ItemList<Datagroup> usesDatabaseTablesOrViews;

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
     * Retrieve the {@code bi_collection_members} property (displayed as '{@literal BI Collection Members}') of the object.
     * @return {@code ItemList<BiCollectionMember>}
     */
    @JsonProperty("bi_collection_members")
    public ItemList<BiCollectionMember> getBiCollectionMembers() { return this.biCollectionMembers; }

    /**
     * Set the {@code bi_collection_members} property (displayed as {@code BI Collection Members}) of the object.
     * @param biCollectionMembers the value to set
     */
    @JsonProperty("bi_collection_members")
    public void setBiCollectionMembers(ItemList<BiCollectionMember> biCollectionMembers) { this.biCollectionMembers = biCollectionMembers; }

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
     * Retrieve the {@code bi_levels} property (displayed as '{@literal BI Levels}') of the object.
     * @return {@code ItemList<BiLevel>}
     */
    @JsonProperty("bi_levels")
    public ItemList<BiLevel> getBiLevels() { return this.biLevels; }

    /**
     * Set the {@code bi_levels} property (displayed as {@code BI Levels}) of the object.
     * @param biLevels the value to set
     */
    @JsonProperty("bi_levels")
    public void setBiLevels(ItemList<BiLevel> biLevels) { this.biLevels = biLevels; }

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
     * Retrieve the {@code bi_model_or_bi_collection} property (displayed as '{@literal BI Model or BI Collection}') of the object.
     * @return {@code ItemList<Olapobject>}
     */
    @JsonProperty("bi_model_or_bi_collection")
    public ItemList<Olapobject> getBiModelOrBiCollection() { return this.biModelOrBiCollection; }

    /**
     * Set the {@code bi_model_or_bi_collection} property (displayed as {@code BI Model or BI Collection}) of the object.
     * @param biModelOrBiCollection the value to set
     */
    @JsonProperty("bi_model_or_bi_collection")
    public void setBiModelOrBiCollection(ItemList<Olapobject> biModelOrBiCollection) { this.biModelOrBiCollection = biModelOrBiCollection; }

    /**
     * Retrieve the {@code filter_expression} property (displayed as '{@literal Filter Expression}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("filter_expression")
    public List<String> getFilterExpression() { return this.filterExpression; }

    /**
     * Set the {@code filter_expression} property (displayed as {@code Filter Expression}) of the object.
     * @param filterExpression the value to set
     */
    @JsonProperty("filter_expression")
    public void setFilterExpression(List<String> filterExpression) { this.filterExpression = filterExpression; }

    /**
     * Retrieve the {@code has_olap_collection} property (displayed as '{@literal Has OLAP Collection}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("has_olap_collection")
    public ItemList<BiCollection> getHasOlapCollection() { return this.hasOlapCollection; }

    /**
     * Set the {@code has_olap_collection} property (displayed as {@code Has OLAP Collection}) of the object.
     * @param hasOlapCollection the value to set
     */
    @JsonProperty("has_olap_collection")
    public void setHasOlapCollection(ItemList<BiCollection> hasOlapCollection) { this.hasOlapCollection = hasOlapCollection; }

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
     * Retrieve the {@code join_condition} property (displayed as '{@literal Join Condition}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("join_condition")
    public List<String> getJoinCondition() { return this.joinCondition; }

    /**
     * Set the {@code join_condition} property (displayed as {@code Join Condition}) of the object.
     * @param joinCondition the value to set
     */
    @JsonProperty("join_condition")
    public void setJoinCondition(List<String> joinCondition) { this.joinCondition = joinCondition; }

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
     * Retrieve the {@code referenced_by_bi_collection} property (displayed as '{@literal Referenced by BI Collection}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("referenced_by_bi_collection")
    public ItemList<BiCollection> getReferencedByBiCollection() { return this.referencedByBiCollection; }

    /**
     * Set the {@code referenced_by_bi_collection} property (displayed as {@code Referenced by BI Collection}) of the object.
     * @param referencedByBiCollection the value to set
     */
    @JsonProperty("referenced_by_bi_collection")
    public void setReferencedByBiCollection(ItemList<BiCollection> referencedByBiCollection) { this.referencedByBiCollection = referencedByBiCollection; }

    /**
     * Retrieve the {@code referenced_by_bi_hierarchies} property (displayed as '{@literal Referenced by BI Hierarchies}') of the object.
     * @return {@code ItemList<BiHierarchy>}
     */
    @JsonProperty("referenced_by_bi_hierarchies")
    public ItemList<BiHierarchy> getReferencedByBiHierarchies() { return this.referencedByBiHierarchies; }

    /**
     * Set the {@code referenced_by_bi_hierarchies} property (displayed as {@code Referenced by BI Hierarchies}) of the object.
     * @param referencedByBiHierarchies the value to set
     */
    @JsonProperty("referenced_by_bi_hierarchies")
    public void setReferencedByBiHierarchies(ItemList<BiHierarchy> referencedByBiHierarchies) { this.referencedByBiHierarchies = referencedByBiHierarchies; }

    /**
     * Retrieve the {@code references_bi_collections} property (displayed as '{@literal References BI Collections}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("references_bi_collections")
    public ItemList<BiCollection> getReferencesBiCollections() { return this.referencesBiCollections; }

    /**
     * Set the {@code references_bi_collections} property (displayed as {@code References BI Collections}) of the object.
     * @param referencesBiCollections the value to set
     */
    @JsonProperty("references_bi_collections")
    public void setReferencesBiCollections(ItemList<BiCollection> referencesBiCollections) { this.referencesBiCollections = referencesBiCollections; }

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
     * Retrieve the {@code type_definition} property (displayed as '{@literal Type Definition}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type_definition")
    public String getTypeDefinition() { return this.typeDefinition; }

    /**
     * Set the {@code type_definition} property (displayed as {@code Type Definition}) of the object.
     * @param typeDefinition the value to set
     */
    @JsonProperty("type_definition")
    public void setTypeDefinition(String typeDefinition) { this.typeDefinition = typeDefinition; }

    /**
     * Retrieve the {@code used_by_bi_cubes} property (displayed as '{@literal Used by BI Cubes}') of the object.
     * @return {@code ItemList<BiCube>}
     */
    @JsonProperty("used_by_bi_cubes")
    public ItemList<BiCube> getUsedByBiCubes() { return this.usedByBiCubes; }

    /**
     * Set the {@code used_by_bi_cubes} property (displayed as {@code Used by BI Cubes}) of the object.
     * @param usedByBiCubes the value to set
     */
    @JsonProperty("used_by_bi_cubes")
    public void setUsedByBiCubes(ItemList<BiCube> usedByBiCubes) { this.usedByBiCubes = usedByBiCubes; }

    /**
     * Retrieve the {@code used_by_bi_report_queries} property (displayed as '{@literal Used by BI Report Queries}') of the object.
     * @return {@code ItemList<BiReportQuery>}
     */
    @JsonProperty("used_by_bi_report_queries")
    public ItemList<BiReportQuery> getUsedByBiReportQueries() { return this.usedByBiReportQueries; }

    /**
     * Set the {@code used_by_bi_report_queries} property (displayed as {@code Used by BI Report Queries}) of the object.
     * @param usedByBiReportQueries the value to set
     */
    @JsonProperty("used_by_bi_report_queries")
    public void setUsedByBiReportQueries(ItemList<BiReportQuery> usedByBiReportQueries) { this.usedByBiReportQueries = usedByBiReportQueries; }

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
