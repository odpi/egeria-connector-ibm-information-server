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
 * POJO for the {@code r_shiny_app_object} asset type in IGC, displayed as '{@literal RShiny App Object}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=RShinyAppObject.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("r_shiny_app_object")
public class RShinyAppObject extends InformationAsset {

    @JsonProperty("from_r_shiny_app")
    protected RShinyApp fromRShinyApp;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("path")
    protected String path;

    @JsonProperty("studio_asset_type")
    protected String studioAssetType;

    @JsonProperty("system_equiv_id")
    protected String systemEquivId;

    /**
     * Retrieve the {@code from_r_shiny_app} property (displayed as '{@literal RShiny App}') of the object.
     * @return {@code RShinyApp}
     */
    @JsonProperty("from_r_shiny_app")
    public RShinyApp getFromRShinyApp() { return this.fromRShinyApp; }

    /**
     * Set the {@code from_r_shiny_app} property (displayed as {@code RShiny App}) of the object.
     * @param fromRShinyApp the value to set
     */
    @JsonProperty("from_r_shiny_app")
    public void setFromRShinyApp(RShinyApp fromRShinyApp) { this.fromRShinyApp = fromRShinyApp; }

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
     * Retrieve the {@code path} property (displayed as '{@literal Path}') of the object.
     * @return {@code String}
     */
    @JsonProperty("path")
    public String getPath() { return this.path; }

    /**
     * Set the {@code path} property (displayed as {@code Path}) of the object.
     * @param path the value to set
     */
    @JsonProperty("path")
    public void setPath(String path) { this.path = path; }

    /**
     * Retrieve the {@code studio_asset_type} property (displayed as '{@literal Studio Asset Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("studio_asset_type")
    public String getStudioAssetType() { return this.studioAssetType; }

    /**
     * Set the {@code studio_asset_type} property (displayed as {@code Studio Asset Type}) of the object.
     * @param studioAssetType the value to set
     */
    @JsonProperty("studio_asset_type")
    public void setStudioAssetType(String studioAssetType) { this.studioAssetType = studioAssetType; }

    /**
     * Retrieve the {@code system_equiv_id} property (displayed as '{@literal System Equiv Id}') of the object.
     * @return {@code String}
     */
    @JsonProperty("system_equiv_id")
    public String getSystemEquivId() { return this.systemEquivId; }

    /**
     * Set the {@code system_equiv_id} property (displayed as {@code System Equiv Id}) of the object.
     * @param systemEquivId the value to set
     */
    @JsonProperty("system_equiv_id")
    public void setSystemEquivId(String systemEquivId) { this.systemEquivId = systemEquivId; }

}
