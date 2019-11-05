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
 * POJO for the {@code directory} asset type in IGC, displayed as '{@literal Directory}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("directory")
public class Directory extends MainObject {

    @JsonProperty("has_directory_provider_configuration")
    protected Directoryproviderconfiguration hasDirectoryProviderConfiguration;

    @JsonProperty("has_principal")
    protected ItemList<Steward> hasPrincipal;

    /**
     * Retrieve the {@code has_directory_provider_configuration} property (displayed as '{@literal Has Directory Provider Configuration}') of the object.
     * @return {@code Directoryproviderconfiguration}
     */
    @JsonProperty("has_directory_provider_configuration")
    public Directoryproviderconfiguration getHasDirectoryProviderConfiguration() { return this.hasDirectoryProviderConfiguration; }

    /**
     * Set the {@code has_directory_provider_configuration} property (displayed as {@code Has Directory Provider Configuration}) of the object.
     * @param hasDirectoryProviderConfiguration the value to set
     */
    @JsonProperty("has_directory_provider_configuration")
    public void setHasDirectoryProviderConfiguration(Directoryproviderconfiguration hasDirectoryProviderConfiguration) { this.hasDirectoryProviderConfiguration = hasDirectoryProviderConfiguration; }

    /**
     * Retrieve the {@code has_principal} property (displayed as '{@literal Has Principal}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("has_principal")
    public ItemList<Steward> getHasPrincipal() { return this.hasPrincipal; }

    /**
     * Set the {@code has_principal} property (displayed as {@code Has Principal}) of the object.
     * @param hasPrincipal the value to set
     */
    @JsonProperty("has_principal")
    public void setHasPrincipal(ItemList<Steward> hasPrincipal) { this.hasPrincipal = hasPrincipal; }

}
