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
 * POJO for the {@code bi_collection_member} asset type in IGC, displayed as '{@literal BI Collection Member}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiCollectionMember.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_collection_member")
public class BiCollectionMember extends InformationAsset {

    @JsonProperty("bi_collection")
    protected BiCollection biCollection;

    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("details")
    protected BiCollectionMember details;

    @JsonProperty("expression")
    protected String expression;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

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

    @JsonProperty("referenced_by_bi_members")
    protected ItemList<BiCollectionMember> referencedByBiMembers;

    @JsonProperty("references_bi_members")
    protected ItemList<BiCollectionMember> referencesBiMembers;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("type_definition")
    protected String typeDefinition;

    @JsonProperty("used_by_bi_cubes")
    protected ItemList<BiCube> usedByBiCubes;

    @JsonProperty("used_by_bi_report_query_items")
    protected ItemList<Reportobject> usedByBiReportQueryItems;

    @JsonProperty("uses_database_columns")
    protected ItemList<DatabaseColumn> usesDatabaseColumns;

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
     * Retrieve the {@code data_type} property (displayed as '{@literal Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_type")
    public String getDataType() { return this.dataType; }

    /**
     * Set the {@code data_type} property (displayed as {@code Data Type}) of the object.
     * @param dataType the value to set
     */
    @JsonProperty("data_type")
    public void setDataType(String dataType) { this.dataType = dataType; }

    /**
     * Retrieve the {@code details} property (displayed as '{@literal Details}') of the object.
     * @return {@code BiCollectionMember}
     */
    @JsonProperty("details")
    public BiCollectionMember getDetails() { return this.details; }

    /**
     * Set the {@code details} property (displayed as {@code Details}) of the object.
     * @param details the value to set
     */
    @JsonProperty("details")
    public void setDetails(BiCollectionMember details) { this.details = details; }

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
     * Retrieve the {@code referenced_by_bi_members} property (displayed as '{@literal Referenced by BI Members}') of the object.
     * @return {@code ItemList<BiCollectionMember>}
     */
    @JsonProperty("referenced_by_bi_members")
    public ItemList<BiCollectionMember> getReferencedByBiMembers() { return this.referencedByBiMembers; }

    /**
     * Set the {@code referenced_by_bi_members} property (displayed as {@code Referenced by BI Members}) of the object.
     * @param referencedByBiMembers the value to set
     */
    @JsonProperty("referenced_by_bi_members")
    public void setReferencedByBiMembers(ItemList<BiCollectionMember> referencedByBiMembers) { this.referencedByBiMembers = referencedByBiMembers; }

    /**
     * Retrieve the {@code references_bi_members} property (displayed as '{@literal References BI Members}') of the object.
     * @return {@code ItemList<BiCollectionMember>}
     */
    @JsonProperty("references_bi_members")
    public ItemList<BiCollectionMember> getReferencesBiMembers() { return this.referencesBiMembers; }

    /**
     * Set the {@code references_bi_members} property (displayed as {@code References BI Members}) of the object.
     * @param referencesBiMembers the value to set
     */
    @JsonProperty("references_bi_members")
    public void setReferencesBiMembers(ItemList<BiCollectionMember> referencesBiMembers) { this.referencesBiMembers = referencesBiMembers; }

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
     * Retrieve the {@code used_by_bi_report_query_items} property (displayed as '{@literal Used by BI Report Query Items}') of the object.
     * @return {@code ItemList<Reportobject>}
     */
    @JsonProperty("used_by_bi_report_query_items")
    public ItemList<Reportobject> getUsedByBiReportQueryItems() { return this.usedByBiReportQueryItems; }

    /**
     * Set the {@code used_by_bi_report_query_items} property (displayed as {@code Used by BI Report Query Items}) of the object.
     * @param usedByBiReportQueryItems the value to set
     */
    @JsonProperty("used_by_bi_report_query_items")
    public void setUsedByBiReportQueryItems(ItemList<Reportobject> usedByBiReportQueryItems) { this.usedByBiReportQueryItems = usedByBiReportQueryItems; }

    /**
     * Retrieve the {@code uses_database_columns} property (displayed as '{@literal Uses Database Columns}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("uses_database_columns")
    public ItemList<DatabaseColumn> getUsesDatabaseColumns() { return this.usesDatabaseColumns; }

    /**
     * Set the {@code uses_database_columns} property (displayed as {@code Uses Database Columns}) of the object.
     * @param usesDatabaseColumns the value to set
     */
    @JsonProperty("uses_database_columns")
    public void setUsesDatabaseColumns(ItemList<DatabaseColumn> usesDatabaseColumns) { this.usesDatabaseColumns = usesDatabaseColumns; }

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
