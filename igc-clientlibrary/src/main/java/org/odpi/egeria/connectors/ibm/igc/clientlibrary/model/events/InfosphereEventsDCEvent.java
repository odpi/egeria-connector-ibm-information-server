/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events;

import com.fasterxml.jackson.annotation.*;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The payload contents of all data connection-specific events on the InfosphereEvents topic.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfosphereEventsDCEvent extends InfosphereEvents {

    @JsonIgnore
    public static final String ACTION_CREATE = "DC_CREATE_EVENT";

    @JsonIgnore
    public static final String ACTION_MODIFY = "DC_MERGED_EVENT";

    protected String createdRID;
    protected String mergedRID;

    /**
     * Retrieve the Repository ID (RID) of the data connection object that was created.
     * @return String
     */
    @JsonProperty("createdRID")
    public String getCreatedRID() { return this.createdRID; }

    /**
     * Set the Repository ID (RID) of the data connection object that was created.
     * @param createdRID of the data connection object
     */
    @JsonProperty("createdRID")
    public void setCreatedRID(String createdRID) { this.createdRID = createdRID; }

    /**
     * Retrieve the comma-separated list of Repository IDs (RIDs) of any objects that were merged.
     * Each comma-separated entry takes the form {@code <type>:RID}.
     * @return String
     */
    @JsonProperty("mergedRID")
    public String getMergedRID() { return this.mergedRID; }

    /**
     * Set the comma-separated list of Repository IDs (RIDs) of any objects that were merged.
     * Each comma-separated entry takes the form {@code <type>:RID}.
     * @param mergedRID comma-separated list of merged RIDs
     */
    @JsonProperty("mergedRID")
    public void setMergedRID(String mergedRID) { this.mergedRID = mergedRID; }

}
