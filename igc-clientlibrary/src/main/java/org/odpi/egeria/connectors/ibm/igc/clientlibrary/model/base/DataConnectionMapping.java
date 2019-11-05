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

/**
 * POJO for the {@code data_connection_mapping} asset type in IGC, displayed as '{@literal Data Connection Mapping}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_connection_mapping")
public class DataConnectionMapping extends Reference {

    @JsonProperty("bound_to_database")
    protected Database boundToDatabase;

    @JsonProperty("host_(engine)_name")
    protected String hostEngineName;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("preferred_data_connection")
    protected DataConnectionMapping preferredDataConnection;

    @JsonProperty("same_as_data_connections")
    protected ItemList<DataConnectionMapping> sameAsDataConnections;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("used_by")
    protected ItemList<MainObject> usedBy;

    /**
     * Retrieve the {@code bound_to_database} property (displayed as '{@literal Bound to Database}') of the object.
     * @return {@code Database}
     */
    @JsonProperty("bound_to_database")
    public Database getBoundToDatabase() { return this.boundToDatabase; }

    /**
     * Set the {@code bound_to_database} property (displayed as {@code Bound to Database}) of the object.
     * @param boundToDatabase the value to set
     */
    @JsonProperty("bound_to_database")
    public void setBoundToDatabase(Database boundToDatabase) { this.boundToDatabase = boundToDatabase; }

    /**
     * Retrieve the {@code host_(engine)_name} property (displayed as '{@literal Host (Engine) Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("host_(engine)_name")
    public String getHostEngineName() { return this.hostEngineName; }

    /**
     * Set the {@code host_(engine)_name} property (displayed as {@code Host (Engine) Name}) of the object.
     * @param hostEngineName the value to set
     */
    @JsonProperty("host_(engine)_name")
    public void setHostEngineName(String hostEngineName) { this.hostEngineName = hostEngineName; }

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
     * Retrieve the {@code preferred_data_connection} property (displayed as '{@literal Preferred Data Connection}') of the object.
     * @return {@code DataConnectionMapping}
     */
    @JsonProperty("preferred_data_connection")
    public DataConnectionMapping getPreferredDataConnection() { return this.preferredDataConnection; }

    /**
     * Set the {@code preferred_data_connection} property (displayed as {@code Preferred Data Connection}) of the object.
     * @param preferredDataConnection the value to set
     */
    @JsonProperty("preferred_data_connection")
    public void setPreferredDataConnection(DataConnectionMapping preferredDataConnection) { this.preferredDataConnection = preferredDataConnection; }

    /**
     * Retrieve the {@code same_as_data_connections} property (displayed as '{@literal Same as Data Connections}') of the object.
     * @return {@code ItemList<DataConnectionMapping>}
     */
    @JsonProperty("same_as_data_connections")
    public ItemList<DataConnectionMapping> getSameAsDataConnections() { return this.sameAsDataConnections; }

    /**
     * Set the {@code same_as_data_connections} property (displayed as {@code Same as Data Connections}) of the object.
     * @param sameAsDataConnections the value to set
     */
    @JsonProperty("same_as_data_connections")
    public void setSameAsDataConnections(ItemList<DataConnectionMapping> sameAsDataConnections) { this.sameAsDataConnections = sameAsDataConnections; }

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
     * Retrieve the {@code used_by} property (displayed as '{@literal Used by}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("used_by")
    public ItemList<MainObject> getUsedBy() { return this.usedBy; }

    /**
     * Set the {@code used_by} property (displayed as {@code Used by}) of the object.
     * @param usedBy the value to set
     */
    @JsonProperty("used_by")
    public void setUsedBy(ItemList<MainObject> usedBy) { this.usedBy = usedBy; }

}
