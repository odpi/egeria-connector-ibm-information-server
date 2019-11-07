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
 * POJO for the {@code volume} asset type in IGC, displayed as '{@literal Volume}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Volume.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("volume")
public class Volume extends InformationAsset {

    @JsonProperty("data_server")
    protected String dataServer;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("infosets")
    protected ItemList<Infoset> infosets;

    @JsonProperty("instance")
    protected Instance instance;

    @JsonProperty("last_harvested")
    protected Date lastHarvested;

    @JsonProperty("object_count")
    protected Number objectCount;

    @JsonProperty("server_name")
    protected String serverName;

    @JsonProperty("size")
    protected Number size;

    @JsonProperty("source_type")
    protected String sourceType;

    @JsonProperty("sync_state")
    protected String syncState;

    @JsonProperty("tool_id")
    protected String toolId;

    /**
     * Valid values are:
     * <ul>
     *   <li>Primary (displayed in the UI as 'Primary')</li>
     *   <li>Retention (displayed in the UI as 'Retention')</li>
     *   <li>Discovery (displayed in the UI as 'Discovery')</li>
     *   <li>Export (displayed in the UI as 'Export')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    @JsonProperty("url")
    protected String url;

    /**
     * Retrieve the {@code data_server} property (displayed as '{@literal Data Server}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_server")
    public String getDataServer() { return this.dataServer; }

    /**
     * Set the {@code data_server} property (displayed as {@code Data Server}) of the object.
     * @param dataServer the value to set
     */
    @JsonProperty("data_server")
    public void setDataServer(String dataServer) { this.dataServer = dataServer; }

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
     * Retrieve the {@code infosets} property (displayed as '{@literal InfoSets}') of the object.
     * @return {@code ItemList<Infoset>}
     */
    @JsonProperty("infosets")
    public ItemList<Infoset> getInfosets() { return this.infosets; }

    /**
     * Set the {@code infosets} property (displayed as {@code InfoSets}) of the object.
     * @param infosets the value to set
     */
    @JsonProperty("infosets")
    public void setInfosets(ItemList<Infoset> infosets) { this.infosets = infosets; }

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
     * Retrieve the {@code last_harvested} property (displayed as '{@literal Last Harvest Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("last_harvested")
    public Date getLastHarvested() { return this.lastHarvested; }

    /**
     * Set the {@code last_harvested} property (displayed as {@code Last Harvest Date}) of the object.
     * @param lastHarvested the value to set
     */
    @JsonProperty("last_harvested")
    public void setLastHarvested(Date lastHarvested) { this.lastHarvested = lastHarvested; }

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
     * Retrieve the {@code server_name} property (displayed as '{@literal Server}') of the object.
     * @return {@code String}
     */
    @JsonProperty("server_name")
    public String getServerName() { return this.serverName; }

    /**
     * Set the {@code server_name} property (displayed as {@code Server}) of the object.
     * @param serverName the value to set
     */
    @JsonProperty("server_name")
    public void setServerName(String serverName) { this.serverName = serverName; }

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
     * Retrieve the {@code source_type} property (displayed as '{@literal Source Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("source_type")
    public String getSourceType() { return this.sourceType; }

    /**
     * Set the {@code source_type} property (displayed as {@code Source Type}) of the object.
     * @param sourceType the value to set
     */
    @JsonProperty("source_type")
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }

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
