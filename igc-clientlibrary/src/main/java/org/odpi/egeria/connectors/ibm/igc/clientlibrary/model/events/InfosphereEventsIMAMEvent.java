/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The payload contents of all Metadata Asset Manager-specific events on the InfosphereEvents topic.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfosphereEventsIMAMEvent extends InfosphereEvents {

    protected String createdRIDs;
    protected String mergedRIDs;
    protected String deletedRIDs;
    protected String discoverOperationId;
    protected String configFile;
    protected String importEventRid;

    /**
     * Retrieve the comma-separated list of Repository IDs (RIDs) of any objects that were created.
     * Each comma-separated entry takes the form {@code <type>:RID}.
     * @return String
     * @see IGCRestConstants#getImamTypeToIgcType()
     */
    @JsonProperty("createdRIDs")
    public String getCreatedRIDs() { return this.createdRIDs; }

    /**
     * Set the comma-separated list of Repository IDs (RIDs) of any objects that were created.
     * Each comma-separated entry takes the form {@code <type>:RID}.
     * @param createdRIDs of any objects that were created
     * @see IGCRestConstants#getImamTypeToIgcType()
     */
    @JsonProperty("createdRIDs")
    public void setCreatedRIDs(String createdRIDs) { this.createdRIDs = createdRIDs; }

    /**
     * Retrieve the comma-separated list of Repository IDs (RIDs) of any objects that were merged.
     * Each comma-separated entry takes the form {@code <type>:RID}.
     * @return String
     * @see IGCRestConstants#getImamTypeToIgcType()
     */
    @JsonProperty("mergedRIDs")
    public String getMergedRIDs() { return this.mergedRIDs; }

    /**
     * Set the comma-separated list of Repository IDs (RIDs) of any objects that were merged.
     * Each comma-separated entry takes the form {@code <type>:RID}.
     * @param mergedRIDs of any objects that were merged
     * @see IGCRestConstants#getImamTypeToIgcType()
     */
    @JsonProperty("mergedRIDs")
    public void setMergedRIDs(String mergedRIDs) { this.mergedRIDs = mergedRIDs; }

    /**
     * Retrieve the comma-separated list of Repository IDs (RIDs) of any objects that were deleted.
     * Each comma-separated entry takes the form {@code <type>RID}
     * @return String
     * @see IGCRestConstants#getImamTypeToIgcType()
     */
    @JsonProperty("deletedRIDs")
    public String getDeletedRIDs() { return this.deletedRIDs; }

    /**
     * Set the comma-separated list of Repository IDs (RIDs) of any objects that were deleted.
     * Each comma-separated entry takes the form {@code <type>:RID}.
     * @param deletedRIDs of any objects that were deleted
     * @see IGCRestConstants#getImamTypeToIgcType()
     */
    @JsonProperty("deletedRIDs")
    public void setDeletedRIDs(String deletedRIDs) { this.deletedRIDs = deletedRIDs; }

    /**
     * Retrieve the ID of the discovery operation that triggered this event (if any).
     * @return String
     */
    @JsonProperty("discoverOperationId")
    public String getDiscoverOperationId() { return this.discoverOperationId; }

    /**
     * Set the ID of the discovery operation that triggered this event (if any).
     * @param discoverOperationId of the discovery operation that triggered this event
     */
    @JsonProperty("discoverOperationId")
    public void setDiscoverOperationId(String discoverOperationId) { this.discoverOperationId = discoverOperationId; }

    /**
     * Retrieve a JSON representation of the configuration used.
     * @return String
     */
    @JsonProperty("configFile")
    public String getConfigFile() { return this.configFile; }

    /**
     * Set the JSON representation of the configuration used.
     * @param configFile of the configuration used
     */
    @JsonProperty("configFile")
    public void setConfigFile(String configFile) { this.configFile = configFile; }

    /**
     * Retrieve the Repository ID (RID) of the event that triggered the import (if any).
     * @return String
     */
    @JsonProperty("importEventRid")
    public String getImportEventRid() { return this.importEventRid; }

    /**
     * Set the Repository ID (RID) of the event that triggered the import (if any).
     * @param importEventRid of the event that triggered the import
     */
    @JsonProperty("importEventRid")
    public void setImportEventRid(String importEventRid) { this.importEventRid = importEventRid; }

}
