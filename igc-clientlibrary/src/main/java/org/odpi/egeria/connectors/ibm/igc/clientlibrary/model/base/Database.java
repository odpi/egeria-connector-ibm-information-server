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

/**
 * POJO for the {@code database} asset type in IGC, displayed as '{@literal Database}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Database.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("database")
public class Database extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("bi_models")
    protected ItemList<BiModel> biModels;

    @JsonProperty("bi_reports")
    protected ItemList<BiReport> biReports;

    @JsonProperty("data_connection_mappings")
    protected ItemList<DataConnectionMapping> dataConnectionMappings;

    @JsonProperty("data_connections")
    protected ItemList<DataConnection> dataConnections;

    /**
     * @deprecated No longer applicable from 11.7.0.1 onwards.
     */
    @Deprecated
    @JsonProperty("data_policies")
    protected ItemList<DataPolicy> dataPolicies;

    @JsonProperty("database_schemas")
    protected ItemList<DatabaseSchema> databaseSchemas;

    @JsonProperty("database_type")
    protected String databaseType;

    @JsonProperty("dbms")
    protected String dbms;

    @JsonProperty("dbms_server_instance")
    protected String dbmsServerInstance;

    @JsonProperty("dbms_vendor")
    protected String dbmsVendor;

    @JsonProperty("dbms_version")
    protected String dbmsVersion;

    @JsonProperty("host")
    protected Host host;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("location")
    protected String location;

    @JsonProperty("mapped_to_mdm_models")
    protected ItemList<MdmModel> mappedToMdmModels;

    /**
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    protected ItemList<TermAssignment> suggestedTermAssignments;

    @JsonProperty("synchronized_from")
    protected String synchronizedFrom;

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
     * Retrieve the {@code bi_models} property (displayed as '{@literal BI Models}') of the object.
     * @return {@code ItemList<BiModel>}
     */
    @JsonProperty("bi_models")
    public ItemList<BiModel> getBiModels() { return this.biModels; }

    /**
     * Set the {@code bi_models} property (displayed as {@code BI Models}) of the object.
     * @param biModels the value to set
     */
    @JsonProperty("bi_models")
    public void setBiModels(ItemList<BiModel> biModels) { this.biModels = biModels; }

    /**
     * Retrieve the {@code bi_reports} property (displayed as '{@literal BI Reports}') of the object.
     * @return {@code ItemList<BiReport>}
     */
    @JsonProperty("bi_reports")
    public ItemList<BiReport> getBiReports() { return this.biReports; }

    /**
     * Set the {@code bi_reports} property (displayed as {@code BI Reports}) of the object.
     * @param biReports the value to set
     */
    @JsonProperty("bi_reports")
    public void setBiReports(ItemList<BiReport> biReports) { this.biReports = biReports; }

    /**
     * Retrieve the {@code data_connection_mappings} property (displayed as '{@literal Data Connection Mappings}') of the object.
     * @return {@code ItemList<DataConnectionMapping>}
     */
    @JsonProperty("data_connection_mappings")
    public ItemList<DataConnectionMapping> getDataConnectionMappings() { return this.dataConnectionMappings; }

    /**
     * Set the {@code data_connection_mappings} property (displayed as {@code Data Connection Mappings}) of the object.
     * @param dataConnectionMappings the value to set
     */
    @JsonProperty("data_connection_mappings")
    public void setDataConnectionMappings(ItemList<DataConnectionMapping> dataConnectionMappings) { this.dataConnectionMappings = dataConnectionMappings; }

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
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @deprecated No longer applicable from 11.7.0.1 onwards.
     * @return {@code ItemList<DataPolicy>}
     */
    @Deprecated
    @JsonProperty("data_policies")
    public ItemList<DataPolicy> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @param dataPolicies the value to set
     */
    @Deprecated
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<DataPolicy> dataPolicies) { this.dataPolicies = dataPolicies; }

    /**
     * Retrieve the {@code database_schemas} property (displayed as '{@literal Database Schemas}') of the object.
     * @return {@code ItemList<DatabaseSchema>}
     */
    @JsonProperty("database_schemas")
    public ItemList<DatabaseSchema> getDatabaseSchemas() { return this.databaseSchemas; }

    /**
     * Set the {@code database_schemas} property (displayed as {@code Database Schemas}) of the object.
     * @param databaseSchemas the value to set
     */
    @JsonProperty("database_schemas")
    public void setDatabaseSchemas(ItemList<DatabaseSchema> databaseSchemas) { this.databaseSchemas = databaseSchemas; }

    /**
     * Retrieve the {@code database_type} property (displayed as '{@literal Database Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("database_type")
    public String getDatabaseType() { return this.databaseType; }

    /**
     * Set the {@code database_type} property (displayed as {@code Database Type}) of the object.
     * @param databaseType the value to set
     */
    @JsonProperty("database_type")
    public void setDatabaseType(String databaseType) { this.databaseType = databaseType; }

    /**
     * Retrieve the {@code dbms} property (displayed as '{@literal DBMS}') of the object.
     * @return {@code String}
     */
    @JsonProperty("dbms")
    public String getDbms() { return this.dbms; }

    /**
     * Set the {@code dbms} property (displayed as {@code DBMS}) of the object.
     * @param dbms the value to set
     */
    @JsonProperty("dbms")
    public void setDbms(String dbms) { this.dbms = dbms; }

    /**
     * Retrieve the {@code dbms_server_instance} property (displayed as '{@literal DBMS Server Instance}') of the object.
     * @return {@code String}
     */
    @JsonProperty("dbms_server_instance")
    public String getDbmsServerInstance() { return this.dbmsServerInstance; }

    /**
     * Set the {@code dbms_server_instance} property (displayed as {@code DBMS Server Instance}) of the object.
     * @param dbmsServerInstance the value to set
     */
    @JsonProperty("dbms_server_instance")
    public void setDbmsServerInstance(String dbmsServerInstance) { this.dbmsServerInstance = dbmsServerInstance; }

    /**
     * Retrieve the {@code dbms_vendor} property (displayed as '{@literal DBMS Vendor}') of the object.
     * @return {@code String}
     */
    @JsonProperty("dbms_vendor")
    public String getDbmsVendor() { return this.dbmsVendor; }

    /**
     * Set the {@code dbms_vendor} property (displayed as {@code DBMS Vendor}) of the object.
     * @param dbmsVendor the value to set
     */
    @JsonProperty("dbms_vendor")
    public void setDbmsVendor(String dbmsVendor) { this.dbmsVendor = dbmsVendor; }

    /**
     * Retrieve the {@code dbms_version} property (displayed as '{@literal DBMS Version}') of the object.
     * @return {@code String}
     */
    @JsonProperty("dbms_version")
    public String getDbmsVersion() { return this.dbmsVersion; }

    /**
     * Set the {@code dbms_version} property (displayed as {@code DBMS Version}) of the object.
     * @param dbmsVersion the value to set
     */
    @JsonProperty("dbms_version")
    public void setDbmsVersion(String dbmsVersion) { this.dbmsVersion = dbmsVersion; }

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
     * Retrieve the {@code imported_from} property (displayed as '{@literal Imported From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("imported_from")
    public String getImportedFrom() { return this.importedFrom; }

    /**
     * Set the {@code imported_from} property (displayed as {@code Imported From}) of the object.
     * @param importedFrom the value to set
     */
    @JsonProperty("imported_from")
    public void setImportedFrom(String importedFrom) { this.importedFrom = importedFrom; }

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
     * Retrieve the {@code mapped_to_mdm_models} property (displayed as '{@literal Mapped to MDM Models}') of the object.
     * @return {@code ItemList<MdmModel>}
     */
    @JsonProperty("mapped_to_mdm_models")
    public ItemList<MdmModel> getMappedToMdmModels() { return this.mappedToMdmModels; }

    /**
     * Set the {@code mapped_to_mdm_models} property (displayed as {@code Mapped to MDM Models}) of the object.
     * @param mappedToMdmModels the value to set
     */
    @JsonProperty("mapped_to_mdm_models")
    public void setMappedToMdmModels(ItemList<MdmModel> mappedToMdmModels) { this.mappedToMdmModels = mappedToMdmModels; }

    /**
     * Retrieve the {@code suggested_term_assignments} property (displayed as '{@literal Suggested Term Assignments}') of the object.
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     * @return {@code ItemList<TermAssignment>}
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    public ItemList<TermAssignment> getSuggestedTermAssignments() { return this.suggestedTermAssignments; }

    /**
     * Set the {@code suggested_term_assignments} property (displayed as {@code Suggested Term Assignments}) of the object.
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     * @param suggestedTermAssignments the value to set
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    public void setSuggestedTermAssignments(ItemList<TermAssignment> suggestedTermAssignments) { this.suggestedTermAssignments = suggestedTermAssignments; }

    /**
     * Retrieve the {@code synchronized_from} property (displayed as '{@literal Synchronized From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("synchronized_from")
    public String getSynchronizedFrom() { return this.synchronizedFrom; }

    /**
     * Set the {@code synchronized_from} property (displayed as {@code Synchronized From}) of the object.
     * @param synchronizedFrom the value to set
     */
    @JsonProperty("synchronized_from")
    public void setSynchronizedFrom(String synchronizedFrom) { this.synchronizedFrom = synchronizedFrom; }

}
