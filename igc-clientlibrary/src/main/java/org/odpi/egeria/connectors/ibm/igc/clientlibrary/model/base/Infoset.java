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
 * POJO for the {@code infoset} asset type in IGC, displayed as '{@literal InfoSet}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Infoset.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("infoset")
public class Infoset extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("applied_data_classes")
    protected ItemList<ClassificationContribution> appliedDataClasses;

    /**
     * Valid values are:
     * <ul>
     *   <li>MixedLevel (displayed in the UI as 'MixedLevel')</li>
     *   <li>TopLevel (displayed in the UI as 'TopLevel')</li>
     * </ul>
     */
    @JsonProperty("composition")
    protected String composition;

    @JsonProperty("contributing_operations")
    protected ItemList<InfosetOperation> contributingOperations;

    @JsonProperty("contributing_volumes")
    protected ItemList<VolumeContribution> contributingVolumes;

    @JsonProperty("created")
    protected Date created;

    @JsonProperty("created_with")
    protected InfosetOperation createdWith;

    @JsonProperty("creator")
    protected String creator;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("instance")
    protected Instance instance;

    @JsonProperty("object_count")
    protected Number objectCount;

    @JsonProperty("size")
    protected Number size;

    @JsonProperty("sync_state")
    protected String syncState;

    /**
     * Valid values are:
     * <ul>
     *   <li>Regular (displayed in the UI as 'Regular')</li>
     *   <li>InferredPartial (displayed in the UI as 'Inferred Partial')</li>
     *   <li>InferredFull (displayed in the UI as 'Inferred Full')</li>
     * </ul>
     */
    @JsonProperty("sync_type")
    protected String syncType;

    @JsonProperty("tool_id")
    protected String toolId;

    /**
     * Valid values are:
     * <ul>
     *   <li>User (displayed in the UI as 'User')</li>
     *   <li>System (displayed in the UI as 'System')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    @JsonProperty("url")
    protected String url;

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
     * Retrieve the {@code applied_data_classes} property (displayed as '{@literal Data Classifications}') of the object.
     * @return {@code ItemList<ClassificationContribution>}
     */
    @JsonProperty("applied_data_classes")
    public ItemList<ClassificationContribution> getAppliedDataClasses() { return this.appliedDataClasses; }

    /**
     * Set the {@code applied_data_classes} property (displayed as {@code Data Classifications}) of the object.
     * @param appliedDataClasses the value to set
     */
    @JsonProperty("applied_data_classes")
    public void setAppliedDataClasses(ItemList<ClassificationContribution> appliedDataClasses) { this.appliedDataClasses = appliedDataClasses; }

    /**
     * Retrieve the {@code composition} property (displayed as '{@literal Composition}') of the object.
     * @return {@code String}
     */
    @JsonProperty("composition")
    public String getComposition() { return this.composition; }

    /**
     * Set the {@code composition} property (displayed as {@code Composition}) of the object.
     * @param composition the value to set
     */
    @JsonProperty("composition")
    public void setComposition(String composition) { this.composition = composition; }

    /**
     * Retrieve the {@code contributing_operations} property (displayed as '{@literal Applied Operations}') of the object.
     * @return {@code ItemList<InfosetOperation>}
     */
    @JsonProperty("contributing_operations")
    public ItemList<InfosetOperation> getContributingOperations() { return this.contributingOperations; }

    /**
     * Set the {@code contributing_operations} property (displayed as {@code Applied Operations}) of the object.
     * @param contributingOperations the value to set
     */
    @JsonProperty("contributing_operations")
    public void setContributingOperations(ItemList<InfosetOperation> contributingOperations) { this.contributingOperations = contributingOperations; }

    /**
     * Retrieve the {@code contributing_volumes} property (displayed as '{@literal Volumes}') of the object.
     * @return {@code ItemList<VolumeContribution>}
     */
    @JsonProperty("contributing_volumes")
    public ItemList<VolumeContribution> getContributingVolumes() { return this.contributingVolumes; }

    /**
     * Set the {@code contributing_volumes} property (displayed as {@code Volumes}) of the object.
     * @param contributingVolumes the value to set
     */
    @JsonProperty("contributing_volumes")
    public void setContributingVolumes(ItemList<VolumeContribution> contributingVolumes) { this.contributingVolumes = contributingVolumes; }

    /**
     * Retrieve the {@code created} property (displayed as '{@literal Created}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created")
    public Date getCreated() { return this.created; }

    /**
     * Set the {@code created} property (displayed as {@code Created}) of the object.
     * @param created the value to set
     */
    @JsonProperty("created")
    public void setCreated(Date created) { this.created = created; }

    /**
     * Retrieve the {@code created_with} property (displayed as '{@literal Originating Operation}') of the object.
     * @return {@code InfosetOperation}
     */
    @JsonProperty("created_with")
    public InfosetOperation getCreatedWith() { return this.createdWith; }

    /**
     * Set the {@code created_with} property (displayed as {@code Originating Operation}) of the object.
     * @param createdWith the value to set
     */
    @JsonProperty("created_with")
    public void setCreatedWith(InfosetOperation createdWith) { this.createdWith = createdWith; }

    /**
     * Retrieve the {@code creator} property (displayed as '{@literal Creator}') of the object.
     * @return {@code String}
     */
    @JsonProperty("creator")
    public String getCreator() { return this.creator; }

    /**
     * Set the {@code creator} property (displayed as {@code Creator}) of the object.
     * @param creator the value to set
     */
    @JsonProperty("creator")
    public void setCreator(String creator) { this.creator = creator; }

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
     * Retrieve the {@code instance} property (displayed as '{@literal Instance}') of the object.
     * @return {@code Instance}
     */
    @JsonProperty("instance")
    public Instance getInstance() { return this.instance; }

    /**
     * Set the {@code instance} property (displayed as {@code Instance}) of the object.
     * @param instance the value to set
     */
    @JsonProperty("instance")
    public void setInstance(Instance instance) { this.instance = instance; }

    /**
     * Retrieve the {@code object_count} property (displayed as '{@literal Number of Objects}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("object_count")
    public Number getObjectCount() { return this.objectCount; }

    /**
     * Set the {@code object_count} property (displayed as {@code Number of Objects}) of the object.
     * @param objectCount the value to set
     */
    @JsonProperty("object_count")
    public void setObjectCount(Number objectCount) { this.objectCount = objectCount; }

    /**
     * Retrieve the {@code size} property (displayed as '{@literal Size (Bytes)}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("size")
    public Number getSize() { return this.size; }

    /**
     * Set the {@code size} property (displayed as {@code Size (Bytes)}) of the object.
     * @param size the value to set
     */
    @JsonProperty("size")
    public void setSize(Number size) { this.size = size; }

    /**
     * Retrieve the {@code sync_state} property (displayed as '{@literal State}') of the object.
     * @return {@code String}
     */
    @JsonProperty("sync_state")
    public String getSyncState() { return this.syncState; }

    /**
     * Set the {@code sync_state} property (displayed as {@code State}) of the object.
     * @param syncState the value to set
     */
    @JsonProperty("sync_state")
    public void setSyncState(String syncState) { this.syncState = syncState; }

    /**
     * Retrieve the {@code sync_type} property (displayed as '{@literal Include For Governance}') of the object.
     * @return {@code String}
     */
    @JsonProperty("sync_type")
    public String getSyncType() { return this.syncType; }

    /**
     * Set the {@code sync_type} property (displayed as {@code Include For Governance}) of the object.
     * @param syncType the value to set
     */
    @JsonProperty("sync_type")
    public void setSyncType(String syncType) { this.syncType = syncType; }

    /**
     * Retrieve the {@code tool_id} property (displayed as '{@literal Tool ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("tool_id")
    public String getToolId() { return this.toolId; }

    /**
     * Set the {@code tool_id} property (displayed as {@code Tool ID}) of the object.
     * @param toolId the value to set
     */
    @JsonProperty("tool_id")
    public void setToolId(String toolId) { this.toolId = toolId; }

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
     * Retrieve the {@code url} property (displayed as '{@literal URL}') of the object.
     * @return {@code String}
     */
    @JsonProperty("url")
    public String getTheUrl() { return this.url; }

    /**
     * Set the {@code url} property (displayed as {@code URL}) of the object.
     * @param url the value to set
     */
    @JsonProperty("url")
    public void setTheUrl(String url) { this.url = url; }

}
