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
 * POJO for the {@code steward_user} asset type in IGC, displayed as '{@literal Steward User}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=StewardUser.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("steward_user")
public class StewardUser extends User {

    @JsonProperty("managed_assets")
    protected ItemList<InformationAsset> managedAssets;

    @JsonProperty("managed_assets_basic")
    protected ItemList<MainObject> managedAssetsBasic;

    /**
     * Retrieve the {@code managed_assets} property (displayed as '{@literal Managed Assets}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("managed_assets")
    public ItemList<InformationAsset> getManagedAssets() { return this.managedAssets; }

    /**
     * Set the {@code managed_assets} property (displayed as {@code Managed Assets}) of the object.
     * @param managedAssets the value to set
     */
    @JsonProperty("managed_assets")
    public void setManagedAssets(ItemList<InformationAsset> managedAssets) { this.managedAssets = managedAssets; }

    /**
     * Retrieve the {@code managed_assets_basic} property (displayed as '{@literal Managed Assets}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("managed_assets_basic")
    public ItemList<MainObject> getManagedAssetsBasic() { return this.managedAssetsBasic; }

    /**
     * Set the {@code managed_assets_basic} property (displayed as {@code Managed Assets}) of the object.
     * @param managedAssetsBasic the value to set
     */
    @JsonProperty("managed_assets_basic")
    public void setManagedAssetsBasic(ItemList<MainObject> managedAssetsBasic) { this.managedAssetsBasic = managedAssetsBasic; }

}
