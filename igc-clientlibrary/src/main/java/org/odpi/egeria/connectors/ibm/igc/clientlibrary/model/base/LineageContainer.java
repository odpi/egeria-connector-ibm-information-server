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
 * POJO for the {@code lineage_container} asset type in IGC, displayed as '{@literal Business Lineage Container}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=LineageContainer.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("lineage_container")
public class LineageContainer extends InformationAsset {

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("information_assets")
    protected ItemList<InformationAsset> informationAssets;

    @JsonProperty("parent_container")
    protected LineageContainer parentContainer;

    @JsonProperty("sub_business_container")
    protected ItemList<Category> subBusinessContainer;

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
     * Retrieve the {@code information_assets} property (displayed as '{@literal Contains Information Assets}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("information_assets")
    public ItemList<InformationAsset> getInformationAssets() { return this.informationAssets; }

    /**
     * Set the {@code information_assets} property (displayed as {@code Contains Information Assets}) of the object.
     * @param informationAssets the value to set
     */
    @JsonProperty("information_assets")
    public void setInformationAssets(ItemList<InformationAsset> informationAssets) { this.informationAssets = informationAssets; }

    /**
     * Retrieve the {@code parent_container} property (displayed as '{@literal Parent Container}') of the object.
     * @return {@code LineageContainer}
     */
    @JsonProperty("parent_container")
    public LineageContainer getParentContainer() { return this.parentContainer; }

    /**
     * Set the {@code parent_container} property (displayed as {@code Parent Container}) of the object.
     * @param parentContainer the value to set
     */
    @JsonProperty("parent_container")
    public void setParentContainer(LineageContainer parentContainer) { this.parentContainer = parentContainer; }

    /**
     * Retrieve the {@code sub_business_container} property (displayed as '{@literal Sub Business Container}') of the object.
     * @return {@code ItemList<Category>}
     */
    @JsonProperty("sub_business_container")
    public ItemList<Category> getSubBusinessContainer() { return this.subBusinessContainer; }

    /**
     * Set the {@code sub_business_container} property (displayed as {@code Sub Business Container}) of the object.
     * @param subBusinessContainer the value to set
     */
    @JsonProperty("sub_business_container")
    public void setSubBusinessContainer(ItemList<Category> subBusinessContainer) { this.subBusinessContainer = subBusinessContainer; }

}
