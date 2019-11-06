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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code array} asset type in IGC, displayed as '{@literal Array}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Array.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("array")
public class Array extends MainObject {

    @JsonProperty("belonging_to_data_item")
    protected DataItem belongingToDataItem;

    @JsonProperty("lower_bound")
    protected Number lowerBound;

    @JsonProperty("maximum_size")
    protected Number maximumSize;

    @JsonProperty("minimum_size")
    protected Number minimumSize;

    @JsonProperty("next_array")
    protected Array nextArray;

    @JsonProperty("previous_array")
    protected Array previousArray;

    /**
     * Retrieve the {@code belonging_to_data_item} property (displayed as '{@literal Belonging to Data Item}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("belonging_to_data_item")
    public DataItem getBelongingToDataItem() { return this.belongingToDataItem; }

    /**
     * Set the {@code belonging_to_data_item} property (displayed as {@code Belonging to Data Item}) of the object.
     * @param belongingToDataItem the value to set
     */
    @JsonProperty("belonging_to_data_item")
    public void setBelongingToDataItem(DataItem belongingToDataItem) { this.belongingToDataItem = belongingToDataItem; }

    /**
     * Retrieve the {@code lower_bound} property (displayed as '{@literal Lower Bound}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("lower_bound")
    public Number getLowerBound() { return this.lowerBound; }

    /**
     * Set the {@code lower_bound} property (displayed as {@code Lower Bound}) of the object.
     * @param lowerBound the value to set
     */
    @JsonProperty("lower_bound")
    public void setLowerBound(Number lowerBound) { this.lowerBound = lowerBound; }

    /**
     * Retrieve the {@code maximum_size} property (displayed as '{@literal Maximum Size}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("maximum_size")
    public Number getMaximumSize() { return this.maximumSize; }

    /**
     * Set the {@code maximum_size} property (displayed as {@code Maximum Size}) of the object.
     * @param maximumSize the value to set
     */
    @JsonProperty("maximum_size")
    public void setMaximumSize(Number maximumSize) { this.maximumSize = maximumSize; }

    /**
     * Retrieve the {@code minimum_size} property (displayed as '{@literal Minimum Size}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("minimum_size")
    public Number getMinimumSize() { return this.minimumSize; }

    /**
     * Set the {@code minimum_size} property (displayed as {@code Minimum Size}) of the object.
     * @param minimumSize the value to set
     */
    @JsonProperty("minimum_size")
    public void setMinimumSize(Number minimumSize) { this.minimumSize = minimumSize; }

    /**
     * Retrieve the {@code next_array} property (displayed as '{@literal Next Array}') of the object.
     * @return {@code Array}
     */
    @JsonProperty("next_array")
    public Array getNextArray() { return this.nextArray; }

    /**
     * Set the {@code next_array} property (displayed as {@code Next Array}) of the object.
     * @param nextArray the value to set
     */
    @JsonProperty("next_array")
    public void setNextArray(Array nextArray) { this.nextArray = nextArray; }

    /**
     * Retrieve the {@code previous_array} property (displayed as '{@literal Previous Array}') of the object.
     * @return {@code Array}
     */
    @JsonProperty("previous_array")
    public Array getPreviousArray() { return this.previousArray; }

    /**
     * Set the {@code previous_array} property (displayed as {@code Previous Array}) of the object.
     * @param previousArray the value to set
     */
    @JsonProperty("previous_array")
    public void setPreviousArray(Array previousArray) { this.previousArray = previousArray; }

}
