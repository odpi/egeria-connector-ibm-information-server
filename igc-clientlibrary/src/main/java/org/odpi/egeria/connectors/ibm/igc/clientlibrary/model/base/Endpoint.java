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

/**
 * POJO for the {@code endpoint} asset type in IGC, displayed as '{@literal Endpoint}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Endpoint.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("endpoint")
public class Endpoint extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("application")
    protected String application;

    /**
     * Valid values are:
     * <ul>
     *   <li>in (displayed in the UI as 'in')</li>
     *   <li>out (displayed in the UI as 'out')</li>
     *   <li>inout (displayed in the UI as 'inout')</li>
     * </ul>
     */
    @JsonProperty("direction")
    protected String direction;

    @JsonProperty("host")
    protected String host;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("port")
    protected String port;

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @param aliasBusinessName the value to set
     */
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code application} property (displayed as '{@literal Application}') of the object.
     * @return {@code String}
     */
    @JsonProperty("application")
    public String getApplication() { return this.application; }

    /**
     * Set the {@code application} property (displayed as {@code Application}) of the object.
     * @param application the value to set
     */
    @JsonProperty("application")
    public void setApplication(String application) { this.application = application; }

    /**
     * Retrieve the {@code direction} property (displayed as '{@literal Direction}') of the object.
     * @return {@code String}
     */
    @JsonProperty("direction")
    public String getDirection() { return this.direction; }

    /**
     * Set the {@code direction} property (displayed as {@code Direction}) of the object.
     * @param direction the value to set
     */
    @JsonProperty("direction")
    public void setDirection(String direction) { this.direction = direction; }

    /**
     * Retrieve the {@code host} property (displayed as '{@literal Host}') of the object.
     * @return {@code String}
     */
    @JsonProperty("host")
    public String getHost() { return this.host; }

    /**
     * Set the {@code host} property (displayed as {@code Host}) of the object.
     * @param host the value to set
     */
    @JsonProperty("host")
    public void setHost(String host) { this.host = host; }

    /**
     * Retrieve the {@code namespace} property (displayed as '{@literal Namespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("namespace")
    public String getNamespace() { return this.namespace; }

    /**
     * Set the {@code namespace} property (displayed as {@code Namespace}) of the object.
     * @param namespace the value to set
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) { this.namespace = namespace; }

    /**
     * Retrieve the {@code port} property (displayed as '{@literal Port}') of the object.
     * @return {@code String}
     */
    @JsonProperty("port")
    public String getPort() { return this.port; }

    /**
     * Set the {@code port} property (displayed as {@code Port}) of the object.
     * @param port the value to set
     */
    @JsonProperty("port")
    public void setPort(String port) { this.port = port; }

}
