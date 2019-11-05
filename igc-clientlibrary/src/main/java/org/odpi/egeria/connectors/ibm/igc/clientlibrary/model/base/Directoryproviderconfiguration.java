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

/**
 * POJO for the {@code directoryproviderconfiguration} asset type in IGC, displayed as '{@literal DirectoryProviderConfiguration}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("directoryproviderconfiguration")
public class Directoryproviderconfiguration extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("has_directory_provider_property")
    protected ItemList<Directoryproviderproperty> hasDirectoryProviderProperty;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("of_directory")
    protected ItemList<Directory> ofDirectory;

    @JsonProperty("provider_name")
    protected String providerName;

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
     * Retrieve the {@code has_directory_provider_property} property (displayed as '{@literal Has Directory Provider Property}') of the object.
     * @return {@code ItemList<Directoryproviderproperty>}
     */
    @JsonProperty("has_directory_provider_property")
    public ItemList<Directoryproviderproperty> getHasDirectoryProviderProperty() { return this.hasDirectoryProviderProperty; }

    /**
     * Set the {@code has_directory_provider_property} property (displayed as {@code Has Directory Provider Property}) of the object.
     * @param hasDirectoryProviderProperty the value to set
     */
    @JsonProperty("has_directory_provider_property")
    public void setHasDirectoryProviderProperty(ItemList<Directoryproviderproperty> hasDirectoryProviderProperty) { this.hasDirectoryProviderProperty = hasDirectoryProviderProperty; }

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
     * Retrieve the {@code of_directory} property (displayed as '{@literal Of Directory}') of the object.
     * @return {@code ItemList<Directory>}
     */
    @JsonProperty("of_directory")
    public ItemList<Directory> getOfDirectory() { return this.ofDirectory; }

    /**
     * Set the {@code of_directory} property (displayed as {@code Of Directory}) of the object.
     * @param ofDirectory the value to set
     */
    @JsonProperty("of_directory")
    public void setOfDirectory(ItemList<Directory> ofDirectory) { this.ofDirectory = ofDirectory; }

    /**
     * Retrieve the {@code provider_name} property (displayed as '{@literal Provider Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("provider_name")
    public String getProviderName() { return this.providerName; }

    /**
     * Set the {@code provider_name} property (displayed as {@code Provider Name}) of the object.
     * @param providerName the value to set
     */
    @JsonProperty("provider_name")
    public void setProviderName(String providerName) { this.providerName = providerName; }

}
