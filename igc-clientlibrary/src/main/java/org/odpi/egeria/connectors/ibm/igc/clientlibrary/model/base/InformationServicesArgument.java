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
import java.util.List;

/**
 * POJO for the {@code information_services_argument} asset type in IGC, displayed as '{@literal Information Services Argument}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=InformationServicesArgument.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("information_services_argument")
public class InformationServicesArgument extends Reference {

    @JsonProperty("argument_name")
    protected List<String> argumentName;

    @JsonProperty("argument_type")
    protected List<String> argumentType;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("default_value")
    protected String defaultValue;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("information_services_operation")
    protected ItemList<InformationServicesOperation> informationServicesOperation;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    /**
     * Valid values are:
     * <ul>
     *   <li>BIGDECIMAL (displayed in the UI as 'BIGDECIMAL')</li>
     *   <li>BIGINTEGER (displayed in the UI as 'BIGINTEGER')</li>
     *   <li>BOOLEAN (displayed in the UI as 'BOOLEAN')</li>
     *   <li>BYTE (displayed in the UI as 'BYTE')</li>
     *   <li>BYTEARRAY (displayed in the UI as 'BYTEARRAY')</li>
     *   <li>DATE (displayed in the UI as 'DATE')</li>
     *   <li>DOUBLE (displayed in the UI as 'DOUBLE')</li>
     *   <li>FLOAT (displayed in the UI as 'FLOAT')</li>
     *   <li>INT (displayed in the UI as 'INT')</li>
     *   <li>STRING (displayed in the UI as 'STRING')</li>
     *   <li>TIME (displayed in the UI as 'TIME')</li>
     *   <li>TIMESTAMP (displayed in the UI as 'TIMESTAMP')</li>
     *   <li>ENCRYPTED (displayed in the UI as 'ENCRYPTED')</li>
     *   <li>XML (displayed in the UI as 'XML')</li>
     * </ul>
     */
    @JsonProperty("reference_type")
    protected String referenceType;

    /**
     * Retrieve the {@code argument_name} property (displayed as '{@literal Argument Name}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("argument_name")
    public List<String> getArgumentName() { return this.argumentName; }

    /**
     * Set the {@code argument_name} property (displayed as {@code Argument Name}) of the object.
     * @param argumentName the value to set
     */
    @JsonProperty("argument_name")
    public void setArgumentName(List<String> argumentName) { this.argumentName = argumentName; }

    /**
     * Retrieve the {@code argument_type} property (displayed as '{@literal Argument Type}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("argument_type")
    public List<String> getArgumentType() { return this.argumentType; }

    /**
     * Set the {@code argument_type} property (displayed as {@code Argument Type}) of the object.
     * @param argumentType the value to set
     */
    @JsonProperty("argument_type")
    public void setArgumentType(List<String> argumentType) { this.argumentType = argumentType; }

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
     * Retrieve the {@code default_value} property (displayed as '{@literal Default Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("default_value")
    public String getDefaultValue() { return this.defaultValue; }

    /**
     * Set the {@code default_value} property (displayed as {@code Default Value}) of the object.
     * @param defaultValue the value to set
     */
    @JsonProperty("default_value")
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }

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
     * Retrieve the {@code information_services_operation} property (displayed as '{@literal Information Services Operation}') of the object.
     * @return {@code ItemList<InformationServicesOperation>}
     */
    @JsonProperty("information_services_operation")
    public ItemList<InformationServicesOperation> getInformationServicesOperation() { return this.informationServicesOperation; }

    /**
     * Set the {@code information_services_operation} property (displayed as {@code Information Services Operation}) of the object.
     * @param informationServicesOperation the value to set
     */
    @JsonProperty("information_services_operation")
    public void setInformationServicesOperation(ItemList<InformationServicesOperation> informationServicesOperation) { this.informationServicesOperation = informationServicesOperation; }

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
     * Retrieve the {@code reference_type} property (displayed as '{@literal Reference Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("reference_type")
    public String getReferenceType() { return this.referenceType; }

    /**
     * Set the {@code reference_type} property (displayed as {@code Reference Type}) of the object.
     * @param referenceType the value to set
     */
    @JsonProperty("reference_type")
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

}
