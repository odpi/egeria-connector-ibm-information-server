/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code datagroup} asset type in IGC, displayed as '{@literal DataGroup}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Datagroup.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataFileRecord.class, name = "data_file_record"),
        @JsonSubTypes.Type(value = DatabaseAlias.class, name = "database_alias"),
        @JsonSubTypes.Type(value = DatabaseTable.class, name = "database_table"),
        @JsonSubTypes.Type(value = DesignStoredProcedure.class, name = "design_stored_procedure"),
        @JsonSubTypes.Type(value = DesignTable.class, name = "design_table"),
        @JsonSubTypes.Type(value = DesignView.class, name = "design_view"),
        @JsonSubTypes.Type(value = StoredProcedure.class, name = "stored_procedure"),
        @JsonSubTypes.Type(value = View.class, name = "view"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("datagroup")
public class Datagroup extends InformationAsset {

    @JsonProperty("database_schema")
    protected DatabaseSchema databaseSchema;

    @JsonProperty("imported_via_data_connection")
    protected DataConnection importedViaDataConnection;

    @JsonProperty("name_qualifier")
    protected String nameQualifier;

    @JsonProperty("name_quoting_char")
    protected String nameQuotingChar;

    @JsonProperty("native_id")
    protected String nativeId;

    /**
     * Retrieve the {@code database_schema} property (displayed as '{@literal Database Schema}') of the object.
     * @return {@code DatabaseSchema}
     */
    @JsonProperty("database_schema")
    public DatabaseSchema getDatabaseSchema() { return this.databaseSchema; }

    /**
     * Set the {@code database_schema} property (displayed as {@code Database Schema}) of the object.
     * @param databaseSchema the value to set
     */
    @JsonProperty("database_schema")
    public void setDatabaseSchema(DatabaseSchema databaseSchema) { this.databaseSchema = databaseSchema; }

    /**
     * Retrieve the {@code imported_via_data_connection} property (displayed as '{@literal Data Connection}') of the object.
     * @return {@code DataConnection}
     */
    @JsonProperty("imported_via_data_connection")
    public DataConnection getImportedViaDataConnection() { return this.importedViaDataConnection; }

    /**
     * Set the {@code imported_via_data_connection} property (displayed as {@code Data Connection}) of the object.
     * @param importedViaDataConnection the value to set
     */
    @JsonProperty("imported_via_data_connection")
    public void setImportedViaDataConnection(DataConnection importedViaDataConnection) { this.importedViaDataConnection = importedViaDataConnection; }

    /**
     * Retrieve the {@code name_qualifier} property (displayed as '{@literal Name Qualifier}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name_qualifier")
    public String getNameQualifier() { return this.nameQualifier; }

    /**
     * Set the {@code name_qualifier} property (displayed as {@code Name Qualifier}) of the object.
     * @param nameQualifier the value to set
     */
    @JsonProperty("name_qualifier")
    public void setNameQualifier(String nameQualifier) { this.nameQualifier = nameQualifier; }

    /**
     * Retrieve the {@code name_quoting_char} property (displayed as '{@literal Name Quoting Char}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name_quoting_char")
    public String getNameQuotingChar() { return this.nameQuotingChar; }

    /**
     * Set the {@code name_quoting_char} property (displayed as {@code Name Quoting Char}) of the object.
     * @param nameQuotingChar the value to set
     */
    @JsonProperty("name_quoting_char")
    public void setNameQuotingChar(String nameQuotingChar) { this.nameQuotingChar = nameQuotingChar; }

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

}
