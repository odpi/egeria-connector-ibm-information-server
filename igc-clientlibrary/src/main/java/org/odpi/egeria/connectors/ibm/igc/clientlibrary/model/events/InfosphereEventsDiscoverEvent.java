/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The payload contents of all Discovery-specific events on the InfosphereEvents topic.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfosphereEventsDiscoverEvent extends InfosphereEvents {

    protected String discoverOperationId;
    protected String timestampAsLong;

    /**
     * Retrieve the ID of the discovery operation that triggered this event (if any).
     * @return String
     */
    @JsonProperty("discoverOperationId")
    public String getDiscoverOperationId() { return this.discoverOperationId; }

    /**
     * Set the ID of the discovery operation that triggered this event.
     * @param discoverOperationId that triggered this event
     */
    @JsonProperty("discoverOperationId")
    public void setDiscoverOperationId(String discoverOperationId) { this.discoverOperationId = discoverOperationId; }

    /**
     * Retrieve the timestamp of the discovery operation as an epoch.
     * @return String
     */
    @JsonProperty("timestampAsLong")
    public String getTimestampAsLong() { return this.timestampAsLong; }

    /**
     * Se the timestamp of the discovery operation as an epoch.
     * @param timestampAsLong of the discovery operation
     */
    @JsonProperty("timestampAsLong")
    public void setTimestampAsLong(String timestampAsLong) { this.timestampAsLong = timestampAsLong; }

}
