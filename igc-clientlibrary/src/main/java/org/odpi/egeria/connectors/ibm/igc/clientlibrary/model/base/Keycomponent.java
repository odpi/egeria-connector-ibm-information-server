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
import java.util.Date;

/**
 * POJO for the {@code keycomponent} asset type in IGC, displayed as '{@literal KeyComponent}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Keycomponent.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("keycomponent")
public class Keycomponent extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("of_key")
    protected MainObject ofKey;

    @JsonProperty("references_data_field")
    protected DataItem referencesDataField;

    @JsonProperty("sequence")
    protected Number sequence;

    /**
     * Valid values are:
     * <ul>
     *   <li>ASCENDING (displayed in the UI as 'ASCENDING')</li>
     *   <li>DESCENDING (displayed in the UI as 'DESCENDING')</li>
     *   <li>NONE (displayed in the UI as 'NONE')</li>
     * </ul>
     */
    @JsonProperty("sorting_order")
    protected String sortingOrder;

    @JsonProperty("uses_data_field")
    protected DataItem usesDataField;

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

    /**
     * Retrieve the {@code of_key} property (displayed as '{@literal Of Key}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("of_key")
    public MainObject getOfKey() { return this.ofKey; }

    /**
     * Set the {@code of_key} property (displayed as {@code Of Key}) of the object.
     * @param ofKey the value to set
     */
    @JsonProperty("of_key")
    public void setOfKey(MainObject ofKey) { this.ofKey = ofKey; }

    /**
     * Retrieve the {@code references_data_field} property (displayed as '{@literal References Data Field}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("references_data_field")
    public DataItem getReferencesDataField() { return this.referencesDataField; }

    /**
     * Set the {@code references_data_field} property (displayed as {@code References Data Field}) of the object.
     * @param referencesDataField the value to set
     */
    @JsonProperty("references_data_field")
    public void setReferencesDataField(DataItem referencesDataField) { this.referencesDataField = referencesDataField; }

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

    /**
     * Retrieve the {@code sorting_order} property (displayed as '{@literal Sorting Order}') of the object.
     * @return {@code String}
     */
    @JsonProperty("sorting_order")
    public String getSortingOrder() { return this.sortingOrder; }

    /**
     * Set the {@code sorting_order} property (displayed as {@code Sorting Order}) of the object.
     * @param sortingOrder the value to set
     */
    @JsonProperty("sorting_order")
    public void setSortingOrder(String sortingOrder) { this.sortingOrder = sortingOrder; }

    /**
     * Retrieve the {@code uses_data_field} property (displayed as '{@literal Uses Data Field}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("uses_data_field")
    public DataItem getUsesDataField() { return this.usesDataField; }

    /**
     * Set the {@code uses_data_field} property (displayed as {@code Uses Data Field}) of the object.
     * @param usesDataField the value to set
     */
    @JsonProperty("uses_data_field")
    public void setUsesDataField(DataItem usesDataField) { this.usesDataField = usesDataField; }

}
