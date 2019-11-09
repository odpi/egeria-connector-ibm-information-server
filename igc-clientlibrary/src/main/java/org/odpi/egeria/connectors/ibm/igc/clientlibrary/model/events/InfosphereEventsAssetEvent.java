/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events;

import com.fasterxml.jackson.annotation.*;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The payload contents of all asset-specific events on the InfosphereEvents topic.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfosphereEventsAssetEvent extends InfosphereEvents {

    @JsonIgnore
    public static final String ACTION_CREATE = "CREATE";

    @JsonIgnore
    public static final String ACTION_MODIFY = "MODIFY";

    @JsonIgnore
    public static final String ACTION_DELETE = "DELETE";

    @JsonIgnore
    public static final String ACTION_ASSIGNED_RELATIONSHIP = "ASSIGNED_RELATIONSHIP";

    protected String assetType;
    protected String assetRid;
    protected String assetContext;
    protected String action;
    protected String assetName;

    /**
     * Retrieve the display name of the asset type for which this event was triggered.
     * @return String
     */
    @JsonProperty("ASSET_TYPE")
    public String getAssetType() { return this.assetType; }

    /**
     * Set the display name of the asset type for which this event was triggered.
     * @param assetType for which this event was triggered
     */
    @JsonProperty("ASSET_TYPE")
    public void setAssetType(String assetType) { this.assetType = assetType; }

    /**
     * Retrieve the Repository ID (RID) of the asset for which this event was triggered.
     * @return String
     */
    @JsonProperty("ASSET_RID")
    public String getAssetRid() { return this.assetRid; }

    /**
     * Set the Repository ID (RID) of the asset for which this event was triggered.
     * @param assetRid of the asset for which this event was triggered
     */
    @JsonProperty("ASSET_RID")
    public void setAssetRid(String assetRid) { this.assetRid = assetRid; }

    /**
     * Retrieve contextual information about the asset for which this event was triggered.
     * @return String
     */
    @JsonProperty("ASSET_CONTEXT")
    public String getAssetContext() { return this.assetContext; }

    /**
     * Set the contextual information about the asset for which this event was triggered.
     * @param assetContext about the asset for which this event was triggered
     */
    @JsonProperty("ASSET_CONTEXT")
    public void setAssetContext(String assetContext) { this.assetContext = assetContext; }

    /**
     * Retrieve the action that was taken for this event to be triggered. For example 'CREATE',
     * 'ASSIGNED_RELATIONSHIP', etc.
     * @return String
     */
    @JsonProperty("ACTION")
    public String getAction() { return this.action; }

    /**
     * Set the action that was taken for this event to be triggered.
     * @param action taken for this event to be triggered
     */
    @JsonProperty("ACTION")
    public void setAction(String action) { this.action = action; }

    /**
     * Retrieve the name of the asset for which this event was triggered.
     * @return String
     */
    @JsonProperty("ASSET_NAME")
    public String getAssetName() { return this.assetName; }

    /**
     * Set the name of the asset for which this event was triggered.
     * @param assetName for which this event was triggered
     */
    @JsonProperty("ASSET_NAME") public void setAssetName(String assetName) { this.assetName = assetName; }

}
