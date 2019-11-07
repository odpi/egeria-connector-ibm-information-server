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

/**
 * POJO for the {@code volume_contribution} asset type in IGC, displayed as '{@literal Volume Contribution}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=VolumeContribution.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("volume_contribution")
public class VolumeContribution extends Reference {

    @JsonProperty("infoset")
    protected Infoset infoset;

    @JsonProperty("object_count")
    protected Number objectCount;

    @JsonProperty("size")
    protected Number size;

    @JsonProperty("volume")
    protected Volume volume;

    /**
     * Retrieve the {@code infoset} property (displayed as '{@literal Infoset}') of the object.
     * @return {@code Infoset}
     */
    @JsonProperty("infoset")
    public Infoset getInfoset() { return this.infoset; }

    /**
     * Set the {@code infoset} property (displayed as {@code Infoset}) of the object.
     * @param infoset the value to set
     */
    @JsonProperty("infoset")
    public void setInfoset(Infoset infoset) { this.infoset = infoset; }

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
     * Retrieve the {@code volume} property (displayed as '{@literal Volume}') of the object.
     * @return {@code Volume}
     */
    @JsonProperty("volume")
    public Volume getVolume() { return this.volume; }

    /**
     * Set the {@code volume} property (displayed as {@code Volume}) of the object.
     * @param volume the value to set
     */
    @JsonProperty("volume")
    public void setVolume(Volume volume) { this.volume = volume; }

}
