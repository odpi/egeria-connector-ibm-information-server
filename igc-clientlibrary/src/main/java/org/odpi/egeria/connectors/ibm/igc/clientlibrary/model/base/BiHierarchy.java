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
 * POJO for the {@code bi_hierarchy} asset type in IGC, displayed as '{@literal BI Hierarchy}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiHierarchy.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_hierarchy")
public class BiHierarchy extends MainObject {

    @JsonProperty("bi_collection")
    protected BiCollection biCollection;

    @JsonProperty("bi_levels")
    protected ItemList<BiLevel> biLevels;

    @JsonProperty("bi_model")
    protected BiModel biModel;

    @JsonProperty("context")
    protected ItemList<Olapobject> context;

    /**
     * Valid values are:
     * <ul>
     *   <li>STANDARD (displayed in the UI as 'STANDARD')</li>
     *   <li>RECURSIVE (displayed in the UI as 'RECURSIVE')</li>
     * </ul>
     */
    @JsonProperty("deployment")
    protected String deployment;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("references_bi_collections")
    protected ItemList<BiCollection> referencesBiCollections;

    /**
     * Valid values are:
     * <ul>
     *   <li>BALANCED (displayed in the UI as 'BALANCED')</li>
     *   <li>UNBALANCED (displayed in the UI as 'UNBALANCED')</li>
     *   <li>RAGGED (displayed in the UI as 'RAGGED')</li>
     *   <li>NETWORK (displayed in the UI as 'NETWORK')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

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
     * Retrieve the {@code context} property (displayed as '{@literal Context}') of the object.
     * @return {@code ItemList<Olapobject>}
     */
    @JsonProperty("context")
    public ItemList<Olapobject> getTheContext() { return this.context; }

    /**
     * Set the {@code context} property (displayed as {@code Context}) of the object.
     * @param context the value to set
     */
    @JsonProperty("context")
    public void setTheContext(ItemList<Olapobject> context) { this.context = context; }

    /**
     * Retrieve the {@code deployment} property (displayed as '{@literal Deployment}') of the object.
     * @return {@code String}
     */
    @JsonProperty("deployment")
    public String getDeployment() { return this.deployment; }

    /**
     * Set the {@code deployment} property (displayed as {@code Deployment}) of the object.
     * @param deployment the value to set
     */
    @JsonProperty("deployment")
    public void setDeployment(String deployment) { this.deployment = deployment; }

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

}
