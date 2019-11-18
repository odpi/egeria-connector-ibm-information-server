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

/**
 * POJO for the {@code connector} asset type in IGC, displayed as '{@literal Connector}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Connector.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("connector")
public class Connector extends Reference {

    @JsonProperty("data_connections")
    protected ItemList<DataConnection> dataConnections;

    @JsonProperty("host")
    protected Host host;

    @JsonProperty("implements_stage_type")
    protected StageType implementsStageType;

    @JsonProperty("library")
    protected String library;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("variant")
    protected String variant;

    @JsonProperty("version")
    protected String version;

    /**
     * Retrieve the {@code data_connections} property (displayed as '{@literal Data Connections}') of the object.
     * @return {@code ItemList<DataConnection>}
     */
    @JsonProperty("data_connections")
    public ItemList<DataConnection> getDataConnections() { return this.dataConnections; }

    /**
     * Set the {@code data_connections} property (displayed as {@code Data Connections}) of the object.
     * @param dataConnections the value to set
     */
    @JsonProperty("data_connections")
    public void setDataConnections(ItemList<DataConnection> dataConnections) { this.dataConnections = dataConnections; }

    /**
     * Retrieve the {@code host} property (displayed as '{@literal Host}') of the object.
     * @return {@code Host}
     */
    @JsonProperty("host")
    public Host getHost() { return this.host; }

    /**
     * Set the {@code host} property (displayed as {@code Host}) of the object.
     * @param host the value to set
     */
    @JsonProperty("host")
    public void setHost(Host host) { this.host = host; }

    /**
     * Retrieve the {@code implements_stage_type} property (displayed as '{@literal Implements Stage Type}') of the object.
     * @return {@code StageType}
     */
    @JsonProperty("implements_stage_type")
    public StageType getImplementsStageType() { return this.implementsStageType; }

    /**
     * Set the {@code implements_stage_type} property (displayed as {@code Implements Stage Type}) of the object.
     * @param implementsStageType the value to set
     */
    @JsonProperty("implements_stage_type")
    public void setImplementsStageType(StageType implementsStageType) { this.implementsStageType = implementsStageType; }

    /**
     * Retrieve the {@code library} property (displayed as '{@literal Library}') of the object.
     * @return {@code String}
     */
    @JsonProperty("library")
    public String getLibrary() { return this.library; }

    /**
     * Set the {@code library} property (displayed as {@code Library}) of the object.
     * @param library the value to set
     */
    @JsonProperty("library")
    public void setLibrary(String library) { this.library = library; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code native_id} property (displayed as '{@literal Native ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_id")
    public String getNativeId() { return this.nativeId; }

    /**
     * Set the {@code native_id} property (displayed as {@code Native ID}) of the object.
     * @param nativeId the value to set
     */
    @JsonProperty("native_id")
    public void setNativeId(String nativeId) { this.nativeId = nativeId; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

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
     * Retrieve the {@code variant} property (displayed as '{@literal Variant}') of the object.
     * @return {@code String}
     */
    @JsonProperty("variant")
    public String getVariant() { return this.variant; }

    /**
     * Set the {@code variant} property (displayed as {@code Variant}) of the object.
     * @param variant the value to set
     */
    @JsonProperty("variant")
    public void setVariant(String variant) { this.variant = variant; }

    /**
     * Retrieve the {@code version} property (displayed as '{@literal Version}') of the object.
     * @return {@code String}
     */
    @JsonProperty("version")
    public String getVersion() { return this.version; }

    /**
     * Set the {@code version} property (displayed as {@code Version}) of the object.
     * @param version the value to set
     */
    @JsonProperty("version")
    public void setVersion(String version) { this.version = version; }

}
