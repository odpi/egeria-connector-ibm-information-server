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
import java.util.Date;

/**
 * POJO for the {@code collection} asset type in IGC, displayed as '{@literal Collection}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Collection.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("collection")
public class Collection extends Reference {

    @JsonProperty("assets")
    protected ItemList<InformationAsset> assets;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("labels")
    protected ItemList<Label> labels;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("owners")
    protected ItemList<Steward> owners;

    @JsonProperty("references_assets")
    protected ItemList<MainObject> referencesAssets;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("user_id")
    protected String userId;

    @JsonProperty("viewers")
    protected ItemList<Steward> viewers;

    @JsonProperty("visibility")
    protected Boolean visibility;

    /**
     * Retrieve the {@code assets} property (displayed as '{@literal Assets}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("assets")
    public ItemList<InformationAsset> getAssets() { return this.assets; }

    /**
     * Set the {@code assets} property (displayed as {@code Assets}) of the object.
     * @param assets the value to set
     */
    @JsonProperty("assets")
    public void setAssets(ItemList<InformationAsset> assets) { this.assets = assets; }

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
     * Retrieve the {@code labels} property (displayed as '{@literal Labels}') of the object.
     * @return {@code ItemList<Label>}
     */
    @JsonProperty("labels")
    public ItemList<Label> getLabels() { return this.labels; }

    /**
     * Set the {@code labels} property (displayed as {@code Labels}) of the object.
     * @param labels the value to set
     */
    @JsonProperty("labels")
    public void setLabels(ItemList<Label> labels) { this.labels = labels; }

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
     * Retrieve the {@code owners} property (displayed as '{@literal Owners}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("owners")
    public ItemList<Steward> getOwners() { return this.owners; }

    /**
     * Set the {@code owners} property (displayed as {@code Owners}) of the object.
     * @param owners the value to set
     */
    @JsonProperty("owners")
    public void setOwners(ItemList<Steward> owners) { this.owners = owners; }

    /**
     * Retrieve the {@code references_assets} property (displayed as '{@literal Assets}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("references_assets")
    public ItemList<MainObject> getReferencesAssets() { return this.referencesAssets; }

    /**
     * Set the {@code references_assets} property (displayed as {@code Assets}) of the object.
     * @param referencesAssets the value to set
     */
    @JsonProperty("references_assets")
    public void setReferencesAssets(ItemList<MainObject> referencesAssets) { this.referencesAssets = referencesAssets; }

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

    /**
     * Retrieve the {@code user_id} property (displayed as '{@literal User Id}') of the object.
     * @return {@code String}
     */
    @JsonProperty("user_id")
    public String getUserId() { return this.userId; }

    /**
     * Set the {@code user_id} property (displayed as {@code User Id}) of the object.
     * @param userId the value to set
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * Retrieve the {@code viewers} property (displayed as '{@literal Viewers}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("viewers")
    public ItemList<Steward> getViewers() { return this.viewers; }

    /**
     * Set the {@code viewers} property (displayed as {@code Viewers}) of the object.
     * @param viewers the value to set
     */
    @JsonProperty("viewers")
    public void setViewers(ItemList<Steward> viewers) { this.viewers = viewers; }

    /**
     * Retrieve the {@code visibility} property (displayed as '{@literal Visibility}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("visibility")
    public Boolean getVisibility() { return this.visibility; }

    /**
     * Set the {@code visibility} property (displayed as {@code Visibility}) of the object.
     * @param visibility the value to set
     */
    @JsonProperty("visibility")
    public void setVisibility(Boolean visibility) { this.visibility = visibility; }

}
