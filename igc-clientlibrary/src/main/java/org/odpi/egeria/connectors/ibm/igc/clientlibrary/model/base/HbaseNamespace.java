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
 * POJO for the {@code hbase_namespace} asset type in IGC, displayed as '{@literal HBase Namespace}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=HbaseNamespace.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("hbase_namespace")
public class HbaseNamespace extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("data_policies")
    protected ItemList<MainObject> dataPolicies;

    @JsonProperty("database")
    protected Database database;

    @JsonProperty("database_aliases")
    protected ItemList<DatabaseAlias> databaseAliases;

    @JsonProperty("database_domains")
    protected ItemList<DataItemDefinition> databaseDomains;

    @JsonProperty("database_tables")
    protected ItemList<DatabaseTable> databaseTables;

    @JsonProperty("help_text")
    protected String helpText;

    @JsonProperty("implements_logical_data_models")
    protected ItemList<LogicalDataModel> implementsLogicalDataModels;

    @JsonProperty("implements_physical_data_models")
    protected ItemList<PhysicalDataModel> implementsPhysicalDataModels;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("include_for_business_lineage")
    protected Boolean includeForBusinessLineage;

    @JsonProperty("mapped_to_mdm_models")
    protected ItemList<MdmModel> mappedToMdmModels;

    @JsonProperty("owner")
    protected String owner;

    @JsonProperty("same_as_data_sources")
    protected ItemList<MainObject> sameAsDataSources;

    /**
     * @deprecated No longer applicable from 11.7.1.1 onwards.
     */
    @Deprecated
    @JsonProperty("suggested_term_assignments")
    protected ItemList<TermAssignment> suggestedTermAssignments;

    @JsonProperty("views")
    protected ItemList<View> views;

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
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("data_policies")
    public ItemList<MainObject> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<MainObject> dataPolicies) { this.dataPolicies = dataPolicies; }

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
     * Retrieve the {@code help_text} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("help_text")
    public String getHelpText() { return this.helpText; }

    /**
     * Set the {@code help_text} property (displayed as {@code Long Description}) of the object.
     * @param helpText the value to set
     */
    @JsonProperty("help_text")
    public void setHelpText(String helpText) { this.helpText = helpText; }

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

}
