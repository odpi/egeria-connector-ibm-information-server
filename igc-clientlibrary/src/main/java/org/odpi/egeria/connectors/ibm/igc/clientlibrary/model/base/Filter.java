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
 * POJO for the {@code filter} asset type in IGC, displayed as '{@literal Filter}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Filter.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("filter")
public class Filter extends InformationAsset {

    @JsonProperty("creator")
    protected String creator;

    @JsonProperty("detects_data_class")
    protected DataClass detectsDataClass;

    @JsonProperty("filter_expression")
    protected String filterExpression;

    @JsonProperty("infosets")
    protected ItemList<Infoset> infosets;

    @JsonProperty("instance")
    protected Instance instance;

    @JsonProperty("sync_state")
    protected String syncState;

    @JsonProperty("tool_id")
    protected String toolId;

    @JsonProperty("url")
    protected String url;

    /**
     * Retrieve the {@code creator} property (displayed as '{@literal Creator}') of the object.
     * @return {@code String}
     */
    @JsonProperty("creator")
    public String getCreator() { return this.creator; }

    /**
     * Set the {@code creator} property (displayed as {@code Creator}) of the object.
     * @param creator the value to set
     */
    @JsonProperty("creator")
    public void setCreator(String creator) { this.creator = creator; }

    /**
     * Retrieve the {@code detects_data_class} property (displayed as '{@literal Data Classification}') of the object.
     * @return {@code DataClass}
     */
    @JsonProperty("detects_data_class")
    public DataClass getDetectsDataClass() { return this.detectsDataClass; }

    /**
     * Set the {@code detects_data_class} property (displayed as {@code Data Classification}) of the object.
     * @param detectsDataClass the value to set
     */
    @JsonProperty("detects_data_class")
    public void setDetectsDataClass(DataClass detectsDataClass) { this.detectsDataClass = detectsDataClass; }

    /**
     * Retrieve the {@code filter_expression} property (displayed as '{@literal Filter Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("filter_expression")
    public String getFilterExpression() { return this.filterExpression; }

    /**
     * Set the {@code filter_expression} property (displayed as {@code Filter Expression}) of the object.
     * @param filterExpression the value to set
     */
    @JsonProperty("filter_expression")
    public void setFilterExpression(String filterExpression) { this.filterExpression = filterExpression; }

    /**
     * Retrieve the {@code infosets} property (displayed as '{@literal InfoSets}') of the object.
     * @return {@code ItemList<Infoset>}
     */
    @JsonProperty("infosets")
    public ItemList<Infoset> getInfosets() { return this.infosets; }

    /**
     * Set the {@code infosets} property (displayed as {@code InfoSets}) of the object.
     * @param infosets the value to set
     */
    @JsonProperty("infosets")
    public void setInfosets(ItemList<Infoset> infosets) { this.infosets = infosets; }

    /**
     * Retrieve the {@code instance} property (displayed as '{@literal Instance}') of the object.
     * @return {@code Instance}
     */
    @JsonProperty("instance")
    public Instance getInstance() { return this.instance; }

    /**
     * Set the {@code instance} property (displayed as {@code Instance}) of the object.
     * @param instance the value to set
     */
    @JsonProperty("instance")
    public void setInstance(Instance instance) { this.instance = instance; }

    /**
     * Retrieve the {@code sync_state} property (displayed as '{@literal State}') of the object.
     * @return {@code String}
     */
    @JsonProperty("sync_state")
    public String getSyncState() { return this.syncState; }

    /**
     * Set the {@code sync_state} property (displayed as {@code State}) of the object.
     * @param syncState the value to set
     */
    @JsonProperty("sync_state")
    public void setSyncState(String syncState) { this.syncState = syncState; }

    /**
     * Retrieve the {@code tool_id} property (displayed as '{@literal Tool ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("tool_id")
    public String getToolId() { return this.toolId; }

    /**
     * Set the {@code tool_id} property (displayed as {@code Tool ID}) of the object.
     * @param toolId the value to set
     */
    @JsonProperty("tool_id")
    public void setToolId(String toolId) { this.toolId = toolId; }

    /**
     * Retrieve the {@code url} property (displayed as '{@literal URL}') of the object.
     * @return {@code String}
     */
    @JsonProperty("url")
    public String getTheUrl() { return this.url; }

    /**
     * Set the {@code url} property (displayed as {@code URL}) of the object.
     * @param url the value to set
     */
    @JsonProperty("url")
    public void setTheUrl(String url) { this.url = url; }

}
