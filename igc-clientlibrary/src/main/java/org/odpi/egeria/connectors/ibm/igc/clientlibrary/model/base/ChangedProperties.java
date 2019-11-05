/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code changed_properties} asset type in IGC, displayed as '{@literal Changed Properties}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("changed_properties")
public class ChangedProperties extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("previous_value")
    protected List<String> previousValue;

    @JsonProperty("property_name")
    protected List<String> propertyName;

    @JsonProperty("term_history")
    protected ItemList<TermHistory> termHistory;

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
     * Retrieve the {@code previous_value} property (displayed as '{@literal Previous Value}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("previous_value")
    public List<String> getPreviousValue() { return this.previousValue; }

    /**
     * Set the {@code previous_value} property (displayed as {@code Previous Value}) of the object.
     * @param previousValue the value to set
     */
    @JsonProperty("previous_value")
    public void setPreviousValue(List<String> previousValue) { this.previousValue = previousValue; }

    /**
     * Retrieve the {@code property_name} property (displayed as '{@literal Property Name}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("property_name")
    public List<String> getPropertyName() { return this.propertyName; }

    /**
     * Set the {@code property_name} property (displayed as {@code Property Name}) of the object.
     * @param propertyName the value to set
     */
    @JsonProperty("property_name")
    public void setPropertyName(List<String> propertyName) { this.propertyName = propertyName; }

    /**
     * Retrieve the {@code term_history} property (displayed as '{@literal Term History}') of the object.
     * @return {@code ItemList<TermHistory>}
     */
    @JsonProperty("term_history")
    public ItemList<TermHistory> getTermHistory() { return this.termHistory; }

    /**
     * Set the {@code term_history} property (displayed as {@code Term History}) of the object.
     * @param termHistory the value to set
     */
    @JsonProperty("term_history")
    public void setTermHistory(ItemList<TermHistory> termHistory) { this.termHistory = termHistory; }

}
