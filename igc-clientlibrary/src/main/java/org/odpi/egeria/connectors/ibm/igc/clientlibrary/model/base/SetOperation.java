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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code set_operation} asset type in IGC, displayed as '{@literal Set Operation}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=SetOperation.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("set_operation")
public class SetOperation extends Reference {

    @JsonProperty("additional_inputs")
    protected ItemList<Infoset> additionalInputs;

    @JsonProperty("infoset")
    protected Infoset infoset;

    @JsonProperty("primary_input")
    protected Infoset primaryInput;

    @JsonProperty("secondary_input")
    protected Infoset secondaryInput;

    /**
     * Valid values are:
     * <ul>
     *   <li>Union (displayed in the UI as 'Union')</li>
     *   <li>Intersection (displayed in the UI as 'Intersection')</li>
     *   <li>Difference (displayed in the UI as 'Difference')</li>
     *   <li>Subtraction (displayed in the UI as 'Subtraction')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code additional_inputs} property (displayed as '{@literal Additional Inputs}') of the object.
     * @return {@code ItemList<Infoset>}
     */
    @JsonProperty("additional_inputs")
    public ItemList<Infoset> getAdditionalInputs() { return this.additionalInputs; }

    /**
     * Set the {@code additional_inputs} property (displayed as {@code Additional Inputs}) of the object.
     * @param additionalInputs the value to set
     */
    @JsonProperty("additional_inputs")
    public void setAdditionalInputs(ItemList<Infoset> additionalInputs) { this.additionalInputs = additionalInputs; }

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
     * Retrieve the {@code secondary_input} property (displayed as '{@literal Secondary Input}') of the object.
     * @return {@code Infoset}
     */
    @JsonProperty("secondary_input")
    public Infoset getSecondaryInput() { return this.secondaryInput; }

    /**
     * Set the {@code secondary_input} property (displayed as {@code Secondary Input}) of the object.
     * @param secondaryInput the value to set
     */
    @JsonProperty("secondary_input")
    public void setSecondaryInput(Infoset secondaryInput) { this.secondaryInput = secondaryInput; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Set Operation}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Set Operation}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

}
