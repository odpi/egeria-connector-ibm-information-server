/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model;

import com.fasterxml.jackson.annotation.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * POJO for a simple object in IGC to track previous versions of each instance of an entity, primarily for use in
 * detecting changes to an object to determine which events to send.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("$OMRS-Stub")
public class OMRSStub extends InformationAsset {
    
    /**
     * The 'sourceType' property, displayed as 'IGC Type' in the IGC UI.
     * <br><br>
     * Provides the type of asset for which this stub represents a shadow copy.
     */
    protected String $sourceType;

    /**
     * The 'sourceRID' property, displayed as 'IGC RID' in the IGC UI.
     * <br><br>
     * Provides the Repository ID (RID) of the asset for which this stub represents a shadow copy.
     */
    protected String $sourceRID;

    /**
     * The 'payload' property, displayed as 'Last Version' in the IGC UI.
     * <br><br>
     * Provides the JSON payload of the last version of the asset for which this stub represents a shadow copy.
     */
    protected String $payload;

    // TODO: add notes object reference

    /**
     * Retrieve the type of asset for which this stub represents a shadow copy.
     *
     * @return String
     * @see #$sourceType
     */
    @JsonProperty("$sourceType")
    public String getSourceType() { return this.$sourceType; }

    /**
     * Set the type of asset for which this stub represents a shadow copy.
     *
     * @param sourceType of asset for which this stub represents a shadow copy
     * @see #$sourceType
     */
    @JsonProperty("$sourceType")
    public void setSourceType(String sourceType) { this.$sourceType = sourceType; }

    /**
     * Retrieve the Repository ID (RID) of the asset for which this stub represents a shadow copy.
     *
     * @return String
     * @see #$sourceRID
     */
    @JsonProperty("$sourceRID")
    public String getSourceRID() { return this.$sourceRID; }

    /**
     * Set the Repository ID (RID) of the asset for which this stub represents a shadow copy.
     *
     * @param sourceRID of the asset for which this stub represents a shadow copy
     * @see #$sourceRID
     */
    @JsonProperty("$sourceRID")
    public void setSourceRID(String sourceRID) { this.$sourceRID = sourceRID; }

    /**
     * Retrieve the JSON payload of the last version of the asset for which this stub represents a shadow copy.
     *
     * @return String
     * @see #$payload
     */
    @JsonProperty("$payload")
    public String getPayload() { return this.$payload; }

    /**
     * Set the JSON payload of the last version of the asset for which this stub represents a shadow copy.
     *
     * @param payload of the last version of the asset for which this stub represents a shadow copy
     * @see #$payload
     */
    @JsonProperty("$payload")
    public void setPayload(String payload) { this.$payload = payload; }

}
