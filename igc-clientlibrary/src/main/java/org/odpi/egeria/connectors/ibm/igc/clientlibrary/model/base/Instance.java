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
 * POJO for the {@code instance} asset type in IGC, displayed as '{@literal Instance}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Instance.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("instance")
public class Instance extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("filters")
    protected ItemList<Filter> filters;

    @JsonProperty("infosets")
    protected ItemList<Infoset> infosets;

    @JsonProperty("primary_contact")
    protected String primaryContact;

    @JsonProperty("secondary_contact")
    protected String secondaryContact;

    @JsonProperty("sync_state")
    protected String syncState;

    @JsonProperty("tool_id")
    protected String toolId;

    @JsonProperty("url")
    protected String url;

    @JsonProperty("volumes")
    protected ItemList<Volume> volumes;

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @param aliasBusinessName the value to set
     */
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code filters} property (displayed as '{@literal Filters}') of the object.
     * @return {@code ItemList<Filter>}
     */
    @JsonProperty("filters")
    public ItemList<Filter> getFilters() { return this.filters; }

    /**
     * Set the {@code filters} property (displayed as {@code Filters}) of the object.
     * @param filters the value to set
     */
    @JsonProperty("filters")
    public void setFilters(ItemList<Filter> filters) { this.filters = filters; }

    /**
     * Retrieve the {@code infosets} property (displayed as '{@literal Infosets}') of the object.
     * @return {@code ItemList<Infoset>}
     */
    @JsonProperty("infosets")
    public ItemList<Infoset> getInfosets() { return this.infosets; }

    /**
     * Set the {@code infosets} property (displayed as {@code Infosets}) of the object.
     * @param infosets the value to set
     */
    @JsonProperty("infosets")
    public void setInfosets(ItemList<Infoset> infosets) { this.infosets = infosets; }

    /**
     * Retrieve the {@code primary_contact} property (displayed as '{@literal Primary Contact}') of the object.
     * @return {@code String}
     */
    @JsonProperty("primary_contact")
    public String getPrimaryContact() { return this.primaryContact; }

    /**
     * Set the {@code primary_contact} property (displayed as {@code Primary Contact}) of the object.
     * @param primaryContact the value to set
     */
    @JsonProperty("primary_contact")
    public void setPrimaryContact(String primaryContact) { this.primaryContact = primaryContact; }

    /**
     * Retrieve the {@code secondary_contact} property (displayed as '{@literal Secondary Contact}') of the object.
     * @return {@code String}
     */
    @JsonProperty("secondary_contact")
    public String getSecondaryContact() { return this.secondaryContact; }

    /**
     * Set the {@code secondary_contact} property (displayed as {@code Secondary Contact}) of the object.
     * @param secondaryContact the value to set
     */
    @JsonProperty("secondary_contact")
    public void setSecondaryContact(String secondaryContact) { this.secondaryContact = secondaryContact; }

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

    /**
     * Retrieve the {@code volumes} property (displayed as '{@literal Volumes}') of the object.
     * @return {@code ItemList<Volume>}
     */
    @JsonProperty("volumes")
    public ItemList<Volume> getVolumes() { return this.volumes; }

    /**
     * Set the {@code volumes} property (displayed as {@code Volumes}) of the object.
     * @param volumes the value to set
     */
    @JsonProperty("volumes")
    public void setVolumes(ItemList<Volume> volumes) { this.volumes = volumes; }

}
