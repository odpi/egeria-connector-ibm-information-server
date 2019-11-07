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
 * POJO for the {@code classification_contribution} asset type in IGC, displayed as '{@literal Classification Contribution}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ClassificationContribution.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("classification_contribution")
public class ClassificationContribution extends Reference {

    @JsonProperty("data_class")
    protected DataClass dataClass;

    @JsonProperty("infoset")
    protected Infoset infoset;

    /**
     * Valid values are:
     * <ul>
     *   <li>Exact (displayed in the UI as 'Exact')</li>
     *   <li>GreaterThan (displayed in the UI as 'GreaterThan')</li>
     * </ul>
     */
    @JsonProperty("match_type")
    protected String matchType;

    @JsonProperty("object_count")
    protected Number objectCount;

    @JsonProperty("size")
    protected Number size;

    /**
     * Retrieve the {@code data_class} property (displayed as '{@literal Data Class}') of the object.
     * @return {@code DataClass}
     */
    @JsonProperty("data_class")
    public DataClass getDataClass() { return this.dataClass; }

    /**
     * Set the {@code data_class} property (displayed as {@code Data Class}) of the object.
     * @param dataClass the value to set
     */
    @JsonProperty("data_class")
    public void setDataClass(DataClass dataClass) { this.dataClass = dataClass; }

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
     * Retrieve the {@code match_type} property (displayed as '{@literal Match Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("match_type")
    public String getMatchType() { return this.matchType; }

    /**
     * Set the {@code match_type} property (displayed as {@code Match Type}) of the object.
     * @param matchType the value to set
     */
    @JsonProperty("match_type")
    public void setMatchType(String matchType) { this.matchType = matchType; }

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

}
