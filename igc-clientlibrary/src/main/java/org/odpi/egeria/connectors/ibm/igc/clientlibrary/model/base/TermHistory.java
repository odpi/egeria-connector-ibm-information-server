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
 * POJO for the {@code term_history} asset type in IGC, displayed as '{@literal Term History}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("term_history")
public class TermHistory extends Reference {

    @JsonProperty("changed_properties")
    protected ItemList<ChangedProperties> changedProperties;

    @JsonProperty("comment")
    protected List<String> comment;

    @JsonProperty("date")
    protected Date date;

    @JsonProperty("edited_by")
    protected String editedBy;

    @JsonProperty("term")
    protected InformationAsset term;

    /**
     * Retrieve the {@code changed_properties} property (displayed as '{@literal Changed Properties}') of the object.
     * @return {@code ItemList<ChangedProperties>}
     */
    @JsonProperty("changed_properties")
    public ItemList<ChangedProperties> getChangedProperties() { return this.changedProperties; }

    /**
     * Set the {@code changed_properties} property (displayed as {@code Changed Properties}) of the object.
     * @param changedProperties the value to set
     */
    @JsonProperty("changed_properties")
    public void setChangedProperties(ItemList<ChangedProperties> changedProperties) { this.changedProperties = changedProperties; }

    /**
     * Retrieve the {@code comment} property (displayed as '{@literal Comment}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("comment")
    public List<String> getComment() { return this.comment; }

    /**
     * Set the {@code comment} property (displayed as {@code Comment}) of the object.
     * @param comment the value to set
     */
    @JsonProperty("comment")
    public void setComment(List<String> comment) { this.comment = comment; }

    /**
     * Retrieve the {@code date} property (displayed as '{@literal Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("date")
    public Date getDate() { return this.date; }

    /**
     * Set the {@code date} property (displayed as {@code Date}) of the object.
     * @param date the value to set
     */
    @JsonProperty("date")
    public void setDate(Date date) { this.date = date; }

    /**
     * Retrieve the {@code edited_by} property (displayed as '{@literal Edited By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("edited_by")
    public String getEditedBy() { return this.editedBy; }

    /**
     * Set the {@code edited_by} property (displayed as {@code Edited By}) of the object.
     * @param editedBy the value to set
     */
    @JsonProperty("edited_by")
    public void setEditedBy(String editedBy) { this.editedBy = editedBy; }

    /**
     * Retrieve the {@code term} property (displayed as '{@literal Term}') of the object.
     * @return {@code InformationAsset}
     */
    @JsonProperty("term")
    public InformationAsset getTerm() { return this.term; }

    /**
     * Set the {@code term} property (displayed as {@code Term}) of the object.
     * @param term the value to set
     */
    @JsonProperty("term")
    public void setTerm(InformationAsset term) { this.term = term; }

}
