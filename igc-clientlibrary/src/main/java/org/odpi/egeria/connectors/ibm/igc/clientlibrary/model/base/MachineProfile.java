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
 * POJO for the {@code machine_profile} asset type in IGC, displayed as '{@literal Machine Profile}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MachineProfile.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("machine_profile")
public class MachineProfile extends InformationAsset {

    @JsonProperty("host")
    protected String host;

    @JsonProperty("platform_type")
    protected String platformType;

    @JsonProperty("port_number")
    protected Number portNumber;

    @JsonProperty("transfer_type")
    protected String transferType;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

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
     * Retrieve the {@code platform_type} property (displayed as '{@literal Platform Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("platform_type")
    public String getPlatformType() { return this.platformType; }

    /**
     * Set the {@code platform_type} property (displayed as {@code Platform Type}) of the object.
     * @param platformType the value to set
     */
    @JsonProperty("platform_type")
    public void setPlatformType(String platformType) { this.platformType = platformType; }

    /**
     * Retrieve the {@code port_number} property (displayed as '{@literal Port Number}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("port_number")
    public Number getPortNumber() { return this.portNumber; }

    /**
     * Set the {@code port_number} property (displayed as {@code Port Number}) of the object.
     * @param portNumber the value to set
     */
    @JsonProperty("port_number")
    public void setPortNumber(Number portNumber) { this.portNumber = portNumber; }

    /**
     * Retrieve the {@code transfer_type} property (displayed as '{@literal Transfer Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("transfer_type")
    public String getTransferType() { return this.transferType; }

    /**
     * Set the {@code transfer_type} property (displayed as {@code Transfer Type}) of the object.
     * @param transferType the value to set
     */
    @JsonProperty("transfer_type")
    public void setTransferType(String transferType) { this.transferType = transferType; }

    /**
     * Retrieve the {@code transformation_project} property (displayed as '{@literal Transformation Project}') of the object.
     * @return {@code TransformationProject}
     */
    @JsonProperty("transformation_project")
    public TransformationProject getTransformationProject() { return this.transformationProject; }

    /**
     * Set the {@code transformation_project} property (displayed as {@code Transformation Project}) of the object.
     * @param transformationProject the value to set
     */
    @JsonProperty("transformation_project")
    public void setTransformationProject(TransformationProject transformationProject) { this.transformationProject = transformationProject; }

}
