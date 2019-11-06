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
 * POJO for the {@code database_schema} asset type in IGC, displayed as '{@literal Database Schema}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DatabaseSchema.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("database_schema")
public class DatabaseSchema extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("data_policies")
    protected ItemList<DataPolicy> dataPolicies;

    @JsonProperty("database")
    protected Database database;

    @JsonProperty("database_aliases")
    protected ItemList<DatabaseAlias> databaseAliases;

    @JsonProperty("database_domains")
    protected ItemList<DataItemDefinition> databaseDomains;

    @JsonProperty("database_tables")
    protected ItemList<DatabaseTable> databaseTables;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("implements_logical_data_models")
    protected ItemList<LogicalDataModel> implementsLogicalDataModels;

    @JsonProperty("implements_physical_data_models")
    protected ItemList<PhysicalDataModel> implementsPhysicalDataModels;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("include_for_business_lineage")
    protected Boolean includeForBusinessLineage;

    @JsonProperty("mapped_to_mdm_models")
    protected ItemList<MdmModel> mappedToMdmModels;

    @JsonProperty("owner")
    protected String owner;

    @JsonProperty("preferred_database_schema")
    protected DatabaseSchema preferredDatabaseSchema;

    @JsonProperty("read_by_(design)")
    protected ItemList<InformationAsset> readByDesign;

    @JsonProperty("read_by_(operational)")
    protected ItemList<InformationAsset> readByOperational;

    @JsonProperty("read_by_(static)")
    protected ItemList<InformationAsset> readByStatic;

    @JsonProperty("read_by_(user_defined)")
    protected ItemList<InformationAsset> readByUserDefined;

    @JsonProperty("same_as_data_sources")
    protected ItemList<MainObject> sameAsDataSources;

    @JsonProperty("stored_procedures")
    protected ItemList<StoredProcedure> storedProcedures;

    @JsonProperty("views")
    protected ItemList<View> views;

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
     * Retrieve the {@code database} property (displayed as '{@literal Database}') of the object.
     * @return {@code Database}
     */
    @JsonProperty("database")
    public Database getDatabase() { return this.database; }

    /**
     * Set the {@code database} property (displayed as {@code Database}) of the object.
     * @param database the value to set
     */
    @JsonProperty("database")
    public void setDatabase(Database database) { this.database = database; }

    /**
     * Retrieve the {@code database_aliases} property (displayed as '{@literal Database Aliases}') of the object.
     * @return {@code ItemList<DatabaseAlias>}
     */
    @JsonProperty("database_aliases")
    public ItemList<DatabaseAlias> getDatabaseAliases() { return this.databaseAliases; }

    /**
     * Set the {@code database_aliases} property (displayed as {@code Database Aliases}) of the object.
     * @param databaseAliases the value to set
     */
    @JsonProperty("database_aliases")
    public void setDatabaseAliases(ItemList<DatabaseAlias> databaseAliases) { this.databaseAliases = databaseAliases; }

    /**
     * Retrieve the {@code database_domains} property (displayed as '{@literal Database Domains}') of the object.
     * @return {@code ItemList<DataItemDefinition>}
     */
    @JsonProperty("database_domains")
    public ItemList<DataItemDefinition> getDatabaseDomains() { return this.databaseDomains; }

    /**
     * Set the {@code database_domains} property (displayed as {@code Database Domains}) of the object.
     * @param databaseDomains the value to set
     */
    @JsonProperty("database_domains")
    public void setDatabaseDomains(ItemList<DataItemDefinition> databaseDomains) { this.databaseDomains = databaseDomains; }

    /**
     * Retrieve the {@code database_tables} property (displayed as '{@literal Database Tables}') of the object.
     * @return {@code ItemList<DatabaseTable>}
     */
    @JsonProperty("database_tables")
    public ItemList<DatabaseTable> getDatabaseTables() { return this.databaseTables; }

    /**
     * Set the {@code database_tables} property (displayed as {@code Database Tables}) of the object.
     * @param databaseTables the value to set
     */
    @JsonProperty("database_tables")
    public void setDatabaseTables(ItemList<DatabaseTable> databaseTables) { this.databaseTables = databaseTables; }

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
     * Retrieve the {@code implements_logical_data_models} property (displayed as '{@literal Implements Logical Data Models}') of the object.
     * @return {@code ItemList<LogicalDataModel>}
     */
    @JsonProperty("implements_logical_data_models")
    public ItemList<LogicalDataModel> getImplementsLogicalDataModels() { return this.implementsLogicalDataModels; }

    /**
     * Set the {@code implements_logical_data_models} property (displayed as {@code Implements Logical Data Models}) of the object.
     * @param implementsLogicalDataModels the value to set
     */
    @JsonProperty("implements_logical_data_models")
    public void setImplementsLogicalDataModels(ItemList<LogicalDataModel> implementsLogicalDataModels) { this.implementsLogicalDataModels = implementsLogicalDataModels; }

    /**
     * Retrieve the {@code implements_physical_data_models} property (displayed as '{@literal Implements Physical Data Models}') of the object.
     * @return {@code ItemList<PhysicalDataModel>}
     */
    @JsonProperty("implements_physical_data_models")
    public ItemList<PhysicalDataModel> getImplementsPhysicalDataModels() { return this.implementsPhysicalDataModels; }

    /**
     * Set the {@code implements_physical_data_models} property (displayed as {@code Implements Physical Data Models}) of the object.
     * @param implementsPhysicalDataModels the value to set
     */
    @JsonProperty("implements_physical_data_models")
    public void setImplementsPhysicalDataModels(ItemList<PhysicalDataModel> implementsPhysicalDataModels) { this.implementsPhysicalDataModels = implementsPhysicalDataModels; }

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
     * Retrieve the {@code include_for_business_lineage} property (displayed as '{@literal Include for Business Lineage}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("include_for_business_lineage")
    public Boolean getIncludeForBusinessLineage() { return this.includeForBusinessLineage; }

    /**
     * Set the {@code include_for_business_lineage} property (displayed as {@code Include for Business Lineage}) of the object.
     * @param includeForBusinessLineage the value to set
     */
    @JsonProperty("include_for_business_lineage")
    public void setIncludeForBusinessLineage(Boolean includeForBusinessLineage) { this.includeForBusinessLineage = includeForBusinessLineage; }

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
     * Retrieve the {@code owner} property (displayed as '{@literal Owner}') of the object.
     * @return {@code String}
     */
    @JsonProperty("owner")
    public String getOwner() { return this.owner; }

    /**
     * Set the {@code owner} property (displayed as {@code Owner}) of the object.
     * @param owner the value to set
     */
    @JsonProperty("owner")
    public void setOwner(String owner) { this.owner = owner; }

    /**
     * Retrieve the {@code preferred_database_schema} property (displayed as '{@literal Preferred Database Schema}') of the object.
     * @return {@code DatabaseSchema}
     */
    @JsonProperty("preferred_database_schema")
    public DatabaseSchema getPreferredDatabaseSchema() { return this.preferredDatabaseSchema; }

    /**
     * Set the {@code preferred_database_schema} property (displayed as {@code Preferred Database Schema}) of the object.
     * @param preferredDatabaseSchema the value to set
     */
    @JsonProperty("preferred_database_schema")
    public void setPreferredDatabaseSchema(DatabaseSchema preferredDatabaseSchema) { this.preferredDatabaseSchema = preferredDatabaseSchema; }

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
     * Retrieve the {@code same_as_data_sources} property (displayed as '{@literal Same as Data Sources}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("same_as_data_sources")
    public ItemList<MainObject> getSameAsDataSources() { return this.sameAsDataSources; }

    /**
     * Set the {@code same_as_data_sources} property (displayed as {@code Same as Data Sources}) of the object.
     * @param sameAsDataSources the value to set
     */
    @JsonProperty("same_as_data_sources")
    public void setSameAsDataSources(ItemList<MainObject> sameAsDataSources) { this.sameAsDataSources = sameAsDataSources; }

    /**
     * Retrieve the {@code stored_procedures} property (displayed as '{@literal Stored Procedures}') of the object.
     * @return {@code ItemList<StoredProcedure>}
     */
    @JsonProperty("stored_procedures")
    public ItemList<StoredProcedure> getStoredProcedures() { return this.storedProcedures; }

    /**
     * Set the {@code stored_procedures} property (displayed as {@code Stored Procedures}) of the object.
     * @param storedProcedures the value to set
     */
    @JsonProperty("stored_procedures")
    public void setStoredProcedures(ItemList<StoredProcedure> storedProcedures) { this.storedProcedures = storedProcedures; }

    /**
     * Retrieve the {@code views} property (displayed as '{@literal Views}') of the object.
     * @return {@code ItemList<View>}
     */
    @JsonProperty("views")
    public ItemList<View> getViews() { return this.views; }

    /**
     * Set the {@code views} property (displayed as {@code Views}) of the object.
     * @param views the value to set
     */
    @JsonProperty("views")
    public void setViews(ItemList<View> views) { this.views = views; }

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
