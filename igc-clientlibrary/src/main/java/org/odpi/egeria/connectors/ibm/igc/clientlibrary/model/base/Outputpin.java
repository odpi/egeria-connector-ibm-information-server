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
 * POJO for the {@code outputpin} asset type in IGC, displayed as '{@literal OutputPin}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Outputpin.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("outputpin")
public class Outputpin extends Reference {

    @JsonProperty("is_source_of_link")
    protected MainObject isSourceOfLink;

    @JsonProperty("of_job_component")
    protected MainObject ofJobComponent;

    @JsonProperty("sequence")
    protected Number sequence;

    /**
     * Retrieve the {@code is_source_of_link} property (displayed as '{@literal Is Source Of Link}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("is_source_of_link")
    public MainObject getIsSourceOfLink() { return this.isSourceOfLink; }

    /**
     * Set the {@code is_source_of_link} property (displayed as {@code Is Source Of Link}) of the object.
     * @param isSourceOfLink the value to set
     */
    @JsonProperty("is_source_of_link")
    public void setIsSourceOfLink(MainObject isSourceOfLink) { this.isSourceOfLink = isSourceOfLink; }

    /**
     * Retrieve the {@code of_job_component} property (displayed as '{@literal Of Job Component}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("of_job_component")
    public MainObject getOfJobComponent() { return this.ofJobComponent; }

    /**
     * Set the {@code of_job_component} property (displayed as {@code Of Job Component}) of the object.
     * @param ofJobComponent the value to set
     */
    @JsonProperty("of_job_component")
    public void setOfJobComponent(MainObject ofJobComponent) { this.ofJobComponent = ofJobComponent; }

    /**
     * Retrieve the {@code sequence} property (displayed as '{@literal Sequence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sequence")
    public Number getSequence() { return this.sequence; }

    /**
     * Set the {@code sequence} property (displayed as {@code Sequence}) of the object.
     * @param sequence the value to set
     */
    @JsonProperty("sequence")
    public void setSequence(Number sequence) { this.sequence = sequence; }

}
