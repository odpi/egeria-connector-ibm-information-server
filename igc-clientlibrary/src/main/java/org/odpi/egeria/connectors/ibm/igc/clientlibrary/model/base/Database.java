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

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("data_connection_mappings")
    protected ItemList<DataConnectionMapping> dataConnectionMappings;

    @JsonProperty("data_connections")
    protected ItemList<DataConnection> dataConnections;

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

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("location")
    protected String location;

    @JsonProperty("mapped_to_mdm_models")
    protected ItemList<MdmModel> mappedToMdmModels;

    @JsonProperty("read_by_(design)")
    protected ItemList<InformationAsset> readByDesign;

    @JsonProperty("read_by_(operational)")
    protected ItemList<InformationAsset> readByOperational;

    @JsonProperty("read_by_(static)")
    protected ItemList<InformationAsset> readByStatic;

    @JsonProperty("read_by_(user_defined)")
    protected ItemList<InformationAsset> readByUserDefined;

    @JsonProperty("suggested_term_assignments")
    protected ItemList<TermAssignment> suggestedTermAssignments;

    @JsonProperty("written_by_(design)")
    protected ItemList<InformationAsset> writtenByDesign;

    @JsonProperty("written_by_(operational)")
    protected ItemList<InformationAsset> writtenByOperational;

    @JsonProperty("written_by_(static)")
    protected ItemList<InformationAsset> writtenByStatic;

    @JsonProperty("written_by_(user_defined)")
    protected ItemList<InformationAsset> writtenByUserDefined;

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
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @param blueprintElements the value to set
     */
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

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
     * @return {@code ItemList<DataPolicy>}
     */
    @JsonProperty("data_policies")
    public ItemList<DataPolicy> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
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
     * Retrieve the {@code impacted_by} property (displayed as '{@literal Impacted by}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacted_by")
    public ItemList<InformationAsset> getImpactedBy() { return this.impactedBy; }

    /**
     * Set the {@code impacted_by} property (displayed as {@code Impacted by}) of the object.
     * @param impactedBy the value to set
     */
    @JsonProperty("impacted_by")
    public void setImpactedBy(ItemList<InformationAsset> impactedBy) { this.impactedBy = impactedBy; }

    /**
     * Retrieve the {@code impacts_on} property (displayed as '{@literal Impacts on}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacts_on")
    public ItemList<InformationAsset> getImpactsOn() { return this.impactsOn; }

    /**
     * Set the {@code impacts_on} property (displayed as {@code Impacts on}) of the object.
     * @param impactsOn the value to set
     */
    @JsonProperty("impacts_on")
    public void setImpactsOn(ItemList<InformationAsset> impactsOn) { this.impactsOn = impactsOn; }

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
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

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
     * Retrieve the {@code read_by_(design)} property (displayed as '{@literal Read by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(design)")
    public ItemList<InformationAsset> getReadByDesign() { return this.readByDesign; }

    /**
     * Set the {@code read_by_(design)} property (displayed as {@code Read by (Design)}) of the object.
     * @param readByDesign the value to set
     */
    @JsonProperty("read_by_(design)")
    public void setReadByDesign(ItemList<InformationAsset> readByDesign) { this.readByDesign = readByDesign; }

    /**
     * Retrieve the {@code read_by_(operational)} property (displayed as '{@literal Read by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(operational)")
    public ItemList<InformationAsset> getReadByOperational() { return this.readByOperational; }

    /**
     * Set the {@code read_by_(operational)} property (displayed as {@code Read by (Operational)}) of the object.
     * @param readByOperational the value to set
     */
    @JsonProperty("read_by_(operational)")
    public void setReadByOperational(ItemList<InformationAsset> readByOperational) { this.readByOperational = readByOperational; }

    /**
     * Retrieve the {@code read_by_(static)} property (displayed as '{@literal Read by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(static)")
    public ItemList<InformationAsset> getReadByStatic() { return this.readByStatic; }

    /**
     * Set the {@code read_by_(static)} property (displayed as {@code Read by (Static)}) of the object.
     * @param readByStatic the value to set
     */
    @JsonProperty("read_by_(static)")
    public void setReadByStatic(ItemList<InformationAsset> readByStatic) { this.readByStatic = readByStatic; }

    /**
     * Retrieve the {@code read_by_(user_defined)} property (displayed as '{@literal Read by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(user_defined)")
    public ItemList<InformationAsset> getReadByUserDefined() { return this.readByUserDefined; }

    /**
     * Set the {@code read_by_(user_defined)} property (displayed as {@code Read by (User-Defined)}) of the object.
     * @param readByUserDefined the value to set
     */
    @JsonProperty("read_by_(user_defined)")
    public void setReadByUserDefined(ItemList<InformationAsset> readByUserDefined) { this.readByUserDefined = readByUserDefined; }

    /**
     * Retrieve the {@code suggested_term_assignments} property (displayed as '{@literal Suggested Term Assignments}') of the object.
     * @return {@code ItemList<TermAssignment>}
     */
    @JsonProperty("suggested_term_assignments")
    public ItemList<TermAssignment> getSuggestedTermAssignments() { return this.suggestedTermAssignments; }

    /**
     * Set the {@code suggested_term_assignments} property (displayed as {@code Suggested Term Assignments}) of the object.
     * @param suggestedTermAssignments the value to set
     */
    @JsonProperty("suggested_term_assignments")
    public void setSuggestedTermAssignments(ItemList<TermAssignment> suggestedTermAssignments) { this.suggestedTermAssignments = suggestedTermAssignments; }

    /**
     * Retrieve the {@code written_by_(design)} property (displayed as '{@literal Written by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(design)")
    public ItemList<InformationAsset> getWrittenByDesign() { return this.writtenByDesign; }

    /**
     * Set the {@code written_by_(design)} property (displayed as {@code Written by (Design)}) of the object.
     * @param writtenByDesign the value to set
     */
    @JsonProperty("written_by_(design)")
    public void setWrittenByDesign(ItemList<InformationAsset> writtenByDesign) { this.writtenByDesign = writtenByDesign; }

    /**
     * Retrieve the {@code written_by_(operational)} property (displayed as '{@literal Written by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(operational)")
    public ItemList<InformationAsset> getWrittenByOperational() { return this.writtenByOperational; }

    /**
     * Set the {@code written_by_(operational)} property (displayed as {@code Written by (Operational)}) of the object.
     * @param writtenByOperational the value to set
     */
    @JsonProperty("written_by_(operational)")
    public void setWrittenByOperational(ItemList<InformationAsset> writtenByOperational) { this.writtenByOperational = writtenByOperational; }

    /**
     * Retrieve the {@code written_by_(static)} property (displayed as '{@literal Written by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(static)")
    public ItemList<InformationAsset> getWrittenByStatic() { return this.writtenByStatic; }

    /**
     * Set the {@code written_by_(static)} property (displayed as {@code Written by (Static)}) of the object.
     * @param writtenByStatic the value to set
     */
    @JsonProperty("written_by_(static)")
    public void setWrittenByStatic(ItemList<InformationAsset> writtenByStatic) { this.writtenByStatic = writtenByStatic; }

    /**
     * Retrieve the {@code written_by_(user_defined)} property (displayed as '{@literal Written by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(user_defined)")
    public ItemList<InformationAsset> getWrittenByUserDefined() { return this.writtenByUserDefined; }

    /**
     * Set the {@code written_by_(user_defined)} property (displayed as {@code Written by (User-Defined)}) of the object.
     * @param writtenByUserDefined the value to set
     */
    @JsonProperty("written_by_(user_defined)")
    public void setWrittenByUserDefined(ItemList<InformationAsset> writtenByUserDefined) { this.writtenByUserDefined = writtenByUserDefined; }

}
