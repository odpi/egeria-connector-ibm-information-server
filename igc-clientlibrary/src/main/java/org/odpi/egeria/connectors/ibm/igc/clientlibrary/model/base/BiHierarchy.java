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

    @JsonProperty("bi_levels")
    protected ItemList<BiLevel> biLevels;

    @JsonProperty("context")
    protected ItemList<Olapobject> context;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    /**
     * Valid values are:
     * <ul>
     *   <li>STANDARD (displayed in the UI as 'STANDARD')</li>
     *   <li>RECURSIVE (displayed in the UI as 'RECURSIVE')</li>
     * </ul>
     */
    @JsonProperty("deployment")
    protected String deployment;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

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
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

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
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

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
