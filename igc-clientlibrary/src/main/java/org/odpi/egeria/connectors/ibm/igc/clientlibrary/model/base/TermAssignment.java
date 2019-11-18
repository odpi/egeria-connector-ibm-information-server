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
 * POJO for the {@code term_assignment} asset type in IGC, displayed as '{@literal Suggested Term Assignment}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=TermAssignment.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("term_assignment")
public class TermAssignment extends Reference {

    @JsonProperty("assign_to_object")
    protected InformationAsset assignToObject;

    @JsonProperty("assign_to_term")
    protected MainObject assignToTerm;

    @JsonProperty("confidence_percent")
    protected Number confidencePercent;

    /**
     * Retrieve the {@code assign_to_object} property (displayed as '{@literal Asset}') of the object.
     * @return {@code InformationAsset}
     */
    @JsonProperty("assign_to_object")
    public InformationAsset getAssignToObject() { return this.assignToObject; }

    /**
     * Set the {@code assign_to_object} property (displayed as {@code Asset}) of the object.
     * @param assignToObject the value to set
     */
    @JsonProperty("assign_to_object")
    public void setAssignToObject(InformationAsset assignToObject) { this.assignToObject = assignToObject; }

    /**
     * Retrieve the {@code assign_to_term} property (displayed as '{@literal Term}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("assign_to_term")
    public MainObject getAssignToTerm() { return this.assignToTerm; }

    /**
     * Set the {@code assign_to_term} property (displayed as {@code Term}) of the object.
     * @param assignToTerm the value to set
     */
    @JsonProperty("assign_to_term")
    public void setAssignToTerm(MainObject assignToTerm) { this.assignToTerm = assignToTerm; }

    /**
     * Retrieve the {@code confidence_percent} property (displayed as '{@literal Confidence (Percent)}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("confidence_percent")
    public Number getConfidencePercent() { return this.confidencePercent; }

    /**
     * Set the {@code confidence_percent} property (displayed as {@code Confidence (Percent)}) of the object.
     * @param confidencePercent the value to set
     */
    @JsonProperty("confidence_percent")
    public void setConfidencePercent(Number confidencePercent) { this.confidencePercent = confidencePercent; }

}
