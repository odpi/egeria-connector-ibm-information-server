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
 * POJO for the {@code node_operation} asset type in IGC, displayed as '{@literal Node Operation}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=NodeOperation.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("node_operation")
public class NodeOperation extends Reference {

    @JsonProperty("infoset")
    protected Infoset infoset;

    @JsonProperty("primary_input")
    protected Infoset primaryInput;

    /**
     * Valid values are:
     * <ul>
     *   <li>Expand (displayed in the UI as 'Expand')</li>
     *   <li>Collapse (displayed in the UI as 'Collapse')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code infoset} property (displayed as '{@literal InfoSet}') of the object.
     * @return {@code Infoset}
     */
    @JsonProperty("infoset")
    public Infoset getInfoset() { return this.infoset; }

    /**
     * Set the {@code infoset} property (displayed as {@code InfoSet}) of the object.
     * @param infoset the value to set
     */
    @JsonProperty("infoset")
    public void setInfoset(Infoset infoset) { this.infoset = infoset; }

    /**
     * Retrieve the {@code primary_input} property (displayed as '{@literal Primary Input}') of the object.
     * @return {@code Infoset}
     */
    @JsonProperty("primary_input")
    public Infoset getPrimaryInput() { return this.primaryInput; }

    /**
     * Set the {@code primary_input} property (displayed as {@code Primary Input}) of the object.
     * @param primaryInput the value to set
     */
    @JsonProperty("primary_input")
    public void setPrimaryInput(Infoset primaryInput) { this.primaryInput = primaryInput; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Node Operation}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Node Operation}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

}
