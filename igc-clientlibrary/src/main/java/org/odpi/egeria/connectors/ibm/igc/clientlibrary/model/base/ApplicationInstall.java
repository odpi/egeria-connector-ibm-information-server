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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code application_install} asset type in IGC, displayed as '{@literal Application Install}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("application_install")
public class ApplicationInstall extends MainObject {

    @JsonProperty("default_credential")
    protected Credential defaultCredential;

    @JsonProperty("has_credential")
    protected ItemList<Credential> hasCredential;

    @JsonProperty("installation_date")
    protected String installationDate;

    @JsonProperty("installation_path")
    protected String installationPath;

    @JsonProperty("installed_on_host")
    protected Host installedOnHost;

    @JsonProperty("instance_name")
    protected String instanceName;

    @JsonProperty("location_name")
    protected String locationName;

    @JsonProperty("platform_identifier")
    protected String platformIdentifier;

    @JsonProperty("release_number")
    protected String releaseNumber;

    @JsonProperty("vendor_name")
    protected String vendorName;

    /**
     * Retrieve the {@code default_credential} property (displayed as '{@literal Default Credential}') of the object.
     * @return {@code Credential}
     */
    @JsonProperty("default_credential")
    public Credential getDefaultCredential() { return this.defaultCredential; }

    /**
     * Set the {@code default_credential} property (displayed as {@code Default Credential}) of the object.
     * @param defaultCredential the value to set
     */
    @JsonProperty("default_credential")
    public void setDefaultCredential(Credential defaultCredential) { this.defaultCredential = defaultCredential; }

    /**
     * Retrieve the {@code has_credential} property (displayed as '{@literal Has Credential}') of the object.
     * @return {@code ItemList<Credential>}
     */
    @JsonProperty("has_credential")
    public ItemList<Credential> getHasCredential() { return this.hasCredential; }

    /**
     * Set the {@code has_credential} property (displayed as {@code Has Credential}) of the object.
     * @param hasCredential the value to set
     */
    @JsonProperty("has_credential")
    public void setHasCredential(ItemList<Credential> hasCredential) { this.hasCredential = hasCredential; }

    /**
     * Retrieve the {@code installation_date} property (displayed as '{@literal Installation Date}') of the object.
     * @return {@code String}
     */
    @JsonProperty("installation_date")
    public String getInstallationDate() { return this.installationDate; }

    /**
     * Set the {@code installation_date} property (displayed as {@code Installation Date}) of the object.
     * @param installationDate the value to set
     */
    @JsonProperty("installation_date")
    public void setInstallationDate(String installationDate) { this.installationDate = installationDate; }

    /**
     * Retrieve the {@code installation_path} property (displayed as '{@literal Installation Path}') of the object.
     * @return {@code String}
     */
    @JsonProperty("installation_path")
    public String getInstallationPath() { return this.installationPath; }

    /**
     * Set the {@code installation_path} property (displayed as {@code Installation Path}) of the object.
     * @param installationPath the value to set
     */
    @JsonProperty("installation_path")
    public void setInstallationPath(String installationPath) { this.installationPath = installationPath; }

    /**
     * Retrieve the {@code installed_on_host} property (displayed as '{@literal Installed On Host}') of the object.
     * @return {@code Host}
     */
    @JsonProperty("installed_on_host")
    public Host getInstalledOnHost() { return this.installedOnHost; }

    /**
     * Set the {@code installed_on_host} property (displayed as {@code Installed On Host}) of the object.
     * @param installedOnHost the value to set
     */
    @JsonProperty("installed_on_host")
    public void setInstalledOnHost(Host installedOnHost) { this.installedOnHost = installedOnHost; }

    /**
     * Retrieve the {@code instance_name} property (displayed as '{@literal Instance Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("instance_name")
    public String getInstanceName() { return this.instanceName; }

    /**
     * Set the {@code instance_name} property (displayed as {@code Instance Name}) of the object.
     * @param instanceName the value to set
     */
    @JsonProperty("instance_name")
    public void setInstanceName(String instanceName) { this.instanceName = instanceName; }

    /**
     * Retrieve the {@code location_name} property (displayed as '{@literal Location Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("location_name")
    public String getLocationName() { return this.locationName; }

    /**
     * Set the {@code location_name} property (displayed as {@code Location Name}) of the object.
     * @param locationName the value to set
     */
    @JsonProperty("location_name")
    public void setLocationName(String locationName) { this.locationName = locationName; }

    /**
     * Retrieve the {@code platform_identifier} property (displayed as '{@literal Platform Identifier}') of the object.
     * @return {@code String}
     */
    @JsonProperty("platform_identifier")
    public String getPlatformIdentifier() { return this.platformIdentifier; }

    /**
     * Set the {@code platform_identifier} property (displayed as {@code Platform Identifier}) of the object.
     * @param platformIdentifier the value to set
     */
    @JsonProperty("platform_identifier")
    public void setPlatformIdentifier(String platformIdentifier) { this.platformIdentifier = platformIdentifier; }

    /**
     * Retrieve the {@code release_number} property (displayed as '{@literal Release Number}') of the object.
     * @return {@code String}
     */
    @JsonProperty("release_number")
    public String getReleaseNumber() { return this.releaseNumber; }

    /**
     * Set the {@code release_number} property (displayed as {@code Release Number}) of the object.
     * @param releaseNumber the value to set
     */
    @JsonProperty("release_number")
    public void setReleaseNumber(String releaseNumber) { this.releaseNumber = releaseNumber; }

    /**
     * Retrieve the {@code vendor_name} property (displayed as '{@literal Vendor Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("vendor_name")
    public String getVendorName() { return this.vendorName; }

    /**
     * Set the {@code vendor_name} property (displayed as {@code Vendor Name}) of the object.
     * @param vendorName the value to set
     */
    @JsonProperty("vendor_name")
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

}
