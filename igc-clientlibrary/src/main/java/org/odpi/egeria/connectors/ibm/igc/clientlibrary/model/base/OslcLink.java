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
 * POJO for the {@code oslc_link} asset type in IGC, displayed as '{@literal Link}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=OslcLink.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("oslc_link")
public class OslcLink extends Reference {

    @JsonProperty("description")
    protected String description;

    @JsonProperty("link_type")
    protected Linktype linkType;

    @JsonProperty("of_main_object")
    protected MainObject ofMainObject;

    @JsonProperty("of_provider_connection")
    protected InformationAsset ofProviderConnection;

    @JsonProperty("url")
    protected String url;

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("description")
    public String getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(String description) { this.description = description; }

    /**
     * Retrieve the {@code link_type} property (displayed as '{@literal Link Type}') of the object.
     * @return {@code Linktype}
     */
    @JsonProperty("link_type")
    public Linktype getLinkType() { return this.linkType; }

    /**
     * Set the {@code link_type} property (displayed as {@code Link Type}) of the object.
     * @param linkType the value to set
     */
    @JsonProperty("link_type")
    public void setLinkType(Linktype linkType) { this.linkType = linkType; }

    /**
     * Retrieve the {@code of_main_object} property (displayed as '{@literal Of Main Object}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("of_main_object")
    public MainObject getOfMainObject() { return this.ofMainObject; }

    /**
     * Set the {@code of_main_object} property (displayed as {@code Of Main Object}) of the object.
     * @param ofMainObject the value to set
     */
    @JsonProperty("of_main_object")
    public void setOfMainObject(MainObject ofMainObject) { this.ofMainObject = ofMainObject; }

    /**
     * Retrieve the {@code of_provider_connection} property (displayed as '{@literal Of Provider Connection}') of the object.
     * @return {@code InformationAsset}
     */
    @JsonProperty("of_provider_connection")
    public InformationAsset getOfProviderConnection() { return this.ofProviderConnection; }

    /**
     * Set the {@code of_provider_connection} property (displayed as {@code Of Provider Connection}) of the object.
     * @param ofProviderConnection the value to set
     */
    @JsonProperty("of_provider_connection")
    public void setOfProviderConnection(InformationAsset ofProviderConnection) { this.ofProviderConnection = ofProviderConnection; }

    /**
     * Retrieve the {@code url} property (displayed as '{@literal Url}') of the object.
     * @return {@code String}
     */
    @JsonProperty("url")
    public String getTheUrl() { return this.url; }

    /**
     * Set the {@code url} property (displayed as {@code Url}) of the object.
     * @param url the value to set
     */
    @JsonProperty("url")
    public void setTheUrl(String url) { this.url = url; }

}
