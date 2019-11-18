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

/**
 * POJO for the {@code transform_argument} asset type in IGC, displayed as '{@literal Transform Argument}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=TransformArgument.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("transform_argument")
public class TransformArgument extends DataItem {

    @JsonProperty("data_elements")
    protected DataElement dataElements;

    @JsonProperty("transform")
    protected TransformsFunction transform;

    /**
     * Retrieve the {@code data_elements} property (displayed as '{@literal Data Elements}') of the object.
     * @return {@code DataElement}
     */
    @JsonProperty("data_elements")
    public DataElement getDataElements() { return this.dataElements; }

    /**
     * Set the {@code data_elements} property (displayed as {@code Data Elements}) of the object.
     * @param dataElements the value to set
     */
    @JsonProperty("data_elements")
    public void setDataElements(DataElement dataElements) { this.dataElements = dataElements; }

    /**
     * Retrieve the {@code transform} property (displayed as '{@literal Transform}') of the object.
     * @return {@code TransformsFunction}
     */
    @JsonProperty("transform")
    public TransformsFunction getTransform() { return this.transform; }

    /**
     * Set the {@code transform} property (displayed as {@code Transform}) of the object.
     * @param transform the value to set
     */
    @JsonProperty("transform")
    public void setTransform(TransformsFunction transform) { this.transform = transform; }

}
