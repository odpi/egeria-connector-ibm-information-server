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
import java.util.Date;

/**
 * POJO for the {@code analytics_project} asset type in IGC, displayed as '{@literal Data Science Project}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AnalyticsProject.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("analytics_project")
public class AnalyticsProject extends InformationAsset {

    @JsonProperty("analytics_models")
    protected ItemList<AnalyticsModel> analyticsModels;

    @JsonProperty("analytics_scripts")
    protected ItemList<AnalyticsScript> analyticsScripts;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("notebooks")
    protected ItemList<Notebook> notebooks;

    @JsonProperty("r_shiny_apps")
    protected ItemList<RShinyApp> rShinyApps;

    /**
     * Retrieve the {@code analytics_models} property (displayed as '{@literal Data Science Models}') of the object.
     * @return {@code ItemList<AnalyticsModel>}
     */
    @JsonProperty("analytics_models")
    public ItemList<AnalyticsModel> getAnalyticsModels() { return this.analyticsModels; }

    /**
     * Set the {@code analytics_models} property (displayed as {@code Data Science Models}) of the object.
     * @param analyticsModels the value to set
     */
    @JsonProperty("analytics_models")
    public void setAnalyticsModels(ItemList<AnalyticsModel> analyticsModels) { this.analyticsModels = analyticsModels; }

    /**
     * Retrieve the {@code analytics_scripts} property (displayed as '{@literal Data Science Scripts}') of the object.
     * @return {@code ItemList<AnalyticsScript>}
     */
    @JsonProperty("analytics_scripts")
    public ItemList<AnalyticsScript> getAnalyticsScripts() { return this.analyticsScripts; }

    /**
     * Set the {@code analytics_scripts} property (displayed as {@code Data Science Scripts}) of the object.
     * @param analyticsScripts the value to set
     */
    @JsonProperty("analytics_scripts")
    public void setAnalyticsScripts(ItemList<AnalyticsScript> analyticsScripts) { this.analyticsScripts = analyticsScripts; }

    /**
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

    /**
     * Retrieve the {@code notebooks} property (displayed as '{@literal Notebooks}') of the object.
     * @return {@code ItemList<Notebook>}
     */
    @JsonProperty("notebooks")
    public ItemList<Notebook> getNotebooks() { return this.notebooks; }

    /**
     * Set the {@code notebooks} property (displayed as {@code Notebooks}) of the object.
     * @param notebooks the value to set
     */
    @JsonProperty("notebooks")
    public void setNotebooks(ItemList<Notebook> notebooks) { this.notebooks = notebooks; }

    /**
     * Retrieve the {@code r_shiny_apps} property (displayed as '{@literal RShiny Apps}') of the object.
     * @return {@code ItemList<RShinyApp>}
     */
    @JsonProperty("r_shiny_apps")
    public ItemList<RShinyApp> getRShinyApps() { return this.rShinyApps; }

    /**
     * Set the {@code r_shiny_apps} property (displayed as {@code RShiny Apps}) of the object.
     * @param rShinyApps the value to set
     */
    @JsonProperty("r_shiny_apps")
    public void setRShinyApps(ItemList<RShinyApp> rShinyApps) { this.rShinyApps = rShinyApps; }

}
