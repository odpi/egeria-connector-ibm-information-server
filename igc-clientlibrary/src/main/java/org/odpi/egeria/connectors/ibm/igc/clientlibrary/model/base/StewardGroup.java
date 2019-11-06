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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code steward_group} asset type in IGC, displayed as '{@literal Steward Group}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=StewardGroup.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("steward_group")
public class StewardGroup extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("email_address")
    protected String emailAddress;

    @JsonProperty("group_name")
    protected String groupName;

    @JsonProperty("location")
    protected String location;

    @JsonProperty("managed_assets")
    protected ItemList<InformationAsset> managedAssets;

    @JsonProperty("managed_assets_basic")
    protected ItemList<MainObject> managedAssetsBasic;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("organization")
    protected String organization;

    @JsonProperty("principal_id")
    protected String principalId;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("web_address")
    protected String webAddress;

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
     * Retrieve the {@code email_address} property (displayed as '{@literal Email Address}') of the object.
     * @return {@code String}
     */
    @JsonProperty("email_address")
    public String getEmailAddress() { return this.emailAddress; }

    /**
     * Set the {@code email_address} property (displayed as {@code Email Address}) of the object.
     * @param emailAddress the value to set
     */
    @JsonProperty("email_address")
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    /**
     * Retrieve the {@code group_name} property (displayed as '{@literal Group Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("group_name")
    public String getGroupName() { return this.groupName; }

    /**
     * Set the {@code group_name} property (displayed as {@code Group Name}) of the object.
     * @param groupName the value to set
     */
    @JsonProperty("group_name")
    public void setGroupName(String groupName) { this.groupName = groupName; }

    /**
     * Retrieve the {@code location} property (displayed as '{@literal Location}') of the object.
     * @return {@code String}
     */
    @JsonProperty("location")
    public String getLocation() { return this.location; }

    /**
     * Set the {@code location} property (displayed as {@code Location}) of the object.
     * @param location the value to set
     */
    @JsonProperty("location")
    public void setLocation(String location) { this.location = location; }

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
     * Retrieve the {@code organization} property (displayed as '{@literal Organization}') of the object.
     * @return {@code String}
     */
    @JsonProperty("organization")
    public String getOrganization() { return this.organization; }

    /**
     * Set the {@code organization} property (displayed as {@code Organization}) of the object.
     * @param organization the value to set
     */
    @JsonProperty("organization")
    public void setOrganization(String organization) { this.organization = organization; }

    /**
     * Retrieve the {@code principal_id} property (displayed as '{@literal Principal ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("principal_id")
    public String getPrincipalId() { return this.principalId; }

    /**
     * Set the {@code principal_id} property (displayed as {@code Principal ID}) of the object.
     * @param principalId the value to set
     */
    @JsonProperty("principal_id")
    public void setPrincipalId(String principalId) { this.principalId = principalId; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

    /**
     * Retrieve the {@code web_address} property (displayed as '{@literal Web Address}') of the object.
     * @return {@code String}
     */
    @JsonProperty("web_address")
    public String getWebAddress() { return this.webAddress; }

    /**
     * Set the {@code web_address} property (displayed as {@code Web Address}) of the object.
     * @param webAddress the value to set
     */
    @JsonProperty("web_address")
    public void setWebAddress(String webAddress) { this.webAddress = webAddress; }

}
