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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code table_definition} asset type in IGC, displayed as '{@literal Table Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("table_definition")
public class TableDefinition extends InformationAsset {

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("column_definitions")
    protected ItemList<ColumnDefinition> columnDefinitions;

    @JsonProperty("computer")
    protected String computer;

    @JsonProperty("data_collection")
    protected String dataCollection;

    @JsonProperty("data_connection")
    protected DsdataConnection dataConnection;

    @JsonProperty("data_schema")
    protected String dataSchema;

    @JsonProperty("data_source_name")
    protected String dataSourceName;

    @JsonProperty("data_source_type")
    protected String dataSourceType;

    @JsonProperty("data_store")
    protected String dataStore;

    @JsonProperty("foreign_keys")
    protected ItemList<ForeignKeyDefinition> foreignKeys;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("included_by_stages")
    protected ItemList<Stage> includedByStages;

    @JsonProperty("machine_or_platform_type")
    protected String machineOrPlatformType;

    @JsonProperty("mainframe_access_type")
    protected String mainframeAccessType;

    @JsonProperty("owner")
    protected String owner;

    @JsonProperty("referenced_by_data_sources")
    protected Datagroup referencedByDataSources;

    @JsonProperty("referenced_by_foreign_keys")
    protected ItemList<ForeignKeyDefinition> referencedByForeignKeys;

    @JsonProperty("software_product")
    protected String softwareProduct;

    @JsonProperty("table_type")
    protected String tableType;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

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
     * Retrieve the {@code column_definitions} property (displayed as '{@literal Column Definitions}') of the object.
     * @return {@code ItemList<ColumnDefinition>}
     */
    @JsonProperty("column_definitions")
    public ItemList<ColumnDefinition> getColumnDefinitions() { return this.columnDefinitions; }

    /**
     * Set the {@code column_definitions} property (displayed as {@code Column Definitions}) of the object.
     * @param columnDefinitions the value to set
     */
    @JsonProperty("column_definitions")
    public void setColumnDefinitions(ItemList<ColumnDefinition> columnDefinitions) { this.columnDefinitions = columnDefinitions; }

    /**
     * Retrieve the {@code computer} property (displayed as '{@literal Computer}') of the object.
     * @return {@code String}
     */
    @JsonProperty("computer")
    public String getComputer() { return this.computer; }

    /**
     * Set the {@code computer} property (displayed as {@code Computer}) of the object.
     * @param computer the value to set
     */
    @JsonProperty("computer")
    public void setComputer(String computer) { this.computer = computer; }

    /**
     * Retrieve the {@code data_collection} property (displayed as '{@literal Data Collection}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_collection")
    public String getDataCollection() { return this.dataCollection; }

    /**
     * Set the {@code data_collection} property (displayed as {@code Data Collection}) of the object.
     * @param dataCollection the value to set
     */
    @JsonProperty("data_collection")
    public void setDataCollection(String dataCollection) { this.dataCollection = dataCollection; }

    /**
     * Retrieve the {@code data_connection} property (displayed as '{@literal Data Connection}') of the object.
     * @return {@code DsdataConnection}
     */
    @JsonProperty("data_connection")
    public DsdataConnection getDataConnection() { return this.dataConnection; }

    /**
     * Set the {@code data_connection} property (displayed as {@code Data Connection}) of the object.
     * @param dataConnection the value to set
     */
    @JsonProperty("data_connection")
    public void setDataConnection(DsdataConnection dataConnection) { this.dataConnection = dataConnection; }

    /**
     * Retrieve the {@code data_schema} property (displayed as '{@literal Data Schema}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_schema")
    public String getDataSchema() { return this.dataSchema; }

    /**
     * Set the {@code data_schema} property (displayed as {@code Data Schema}) of the object.
     * @param dataSchema the value to set
     */
    @JsonProperty("data_schema")
    public void setDataSchema(String dataSchema) { this.dataSchema = dataSchema; }

    /**
     * Retrieve the {@code data_source_name} property (displayed as '{@literal Data Source Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_source_name")
    public String getDataSourceName() { return this.dataSourceName; }

    /**
     * Set the {@code data_source_name} property (displayed as {@code Data Source Name}) of the object.
     * @param dataSourceName the value to set
     */
    @JsonProperty("data_source_name")
    public void setDataSourceName(String dataSourceName) { this.dataSourceName = dataSourceName; }

    /**
     * Retrieve the {@code data_source_type} property (displayed as '{@literal Data Source Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_source_type")
    public String getDataSourceType() { return this.dataSourceType; }

    /**
     * Set the {@code data_source_type} property (displayed as {@code Data Source Type}) of the object.
     * @param dataSourceType the value to set
     */
    @JsonProperty("data_source_type")
    public void setDataSourceType(String dataSourceType) { this.dataSourceType = dataSourceType; }

    /**
     * Retrieve the {@code data_store} property (displayed as '{@literal Data Store}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_store")
    public String getDataStore() { return this.dataStore; }

    /**
     * Set the {@code data_store} property (displayed as {@code Data Store}) of the object.
     * @param dataStore the value to set
     */
    @JsonProperty("data_store")
    public void setDataStore(String dataStore) { this.dataStore = dataStore; }

    /**
     * Retrieve the {@code foreign_keys} property (displayed as '{@literal Foreign Keys}') of the object.
     * @return {@code ItemList<ForeignKeyDefinition>}
     */
    @JsonProperty("foreign_keys")
    public ItemList<ForeignKeyDefinition> getForeignKeys() { return this.foreignKeys; }

    /**
     * Set the {@code foreign_keys} property (displayed as {@code Foreign Keys}) of the object.
     * @param foreignKeys the value to set
     */
    @JsonProperty("foreign_keys")
    public void setForeignKeys(ItemList<ForeignKeyDefinition> foreignKeys) { this.foreignKeys = foreignKeys; }

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
     * Retrieve the {@code included_by_stages} property (displayed as '{@literal Included by Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("included_by_stages")
    public ItemList<Stage> getIncludedByStages() { return this.includedByStages; }

    /**
     * Set the {@code included_by_stages} property (displayed as {@code Included by Stages}) of the object.
     * @param includedByStages the value to set
     */
    @JsonProperty("included_by_stages")
    public void setIncludedByStages(ItemList<Stage> includedByStages) { this.includedByStages = includedByStages; }

    /**
     * Retrieve the {@code machine_or_platform_type} property (displayed as '{@literal Machine or Platform Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("machine_or_platform_type")
    public String getMachineOrPlatformType() { return this.machineOrPlatformType; }

    /**
     * Set the {@code machine_or_platform_type} property (displayed as {@code Machine or Platform Type}) of the object.
     * @param machineOrPlatformType the value to set
     */
    @JsonProperty("machine_or_platform_type")
    public void setMachineOrPlatformType(String machineOrPlatformType) { this.machineOrPlatformType = machineOrPlatformType; }

    /**
     * Retrieve the {@code mainframe_access_type} property (displayed as '{@literal Mainframe Access Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("mainframe_access_type")
    public String getMainframeAccessType() { return this.mainframeAccessType; }

    /**
     * Set the {@code mainframe_access_type} property (displayed as {@code Mainframe Access Type}) of the object.
     * @param mainframeAccessType the value to set
     */
    @JsonProperty("mainframe_access_type")
    public void setMainframeAccessType(String mainframeAccessType) { this.mainframeAccessType = mainframeAccessType; }

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
     * Retrieve the {@code referenced_by_data_sources} property (displayed as '{@literal Referenced by Data Sources}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("referenced_by_data_sources")
    public Datagroup getReferencedByDataSources() { return this.referencedByDataSources; }

    /**
     * Set the {@code referenced_by_data_sources} property (displayed as {@code Referenced by Data Sources}) of the object.
     * @param referencedByDataSources the value to set
     */
    @JsonProperty("referenced_by_data_sources")
    public void setReferencedByDataSources(Datagroup referencedByDataSources) { this.referencedByDataSources = referencedByDataSources; }

    /**
     * Retrieve the {@code referenced_by_foreign_keys} property (displayed as '{@literal Referenced by Foreign Keys}') of the object.
     * @return {@code ItemList<ForeignKeyDefinition>}
     */
    @JsonProperty("referenced_by_foreign_keys")
    public ItemList<ForeignKeyDefinition> getReferencedByForeignKeys() { return this.referencedByForeignKeys; }

    /**
     * Set the {@code referenced_by_foreign_keys} property (displayed as {@code Referenced by Foreign Keys}) of the object.
     * @param referencedByForeignKeys the value to set
     */
    @JsonProperty("referenced_by_foreign_keys")
    public void setReferencedByForeignKeys(ItemList<ForeignKeyDefinition> referencedByForeignKeys) { this.referencedByForeignKeys = referencedByForeignKeys; }

    /**
     * Retrieve the {@code software_product} property (displayed as '{@literal Software Product}') of the object.
     * @return {@code String}
     */
    @JsonProperty("software_product")
    public String getSoftwareProduct() { return this.softwareProduct; }

    /**
     * Set the {@code software_product} property (displayed as {@code Software Product}) of the object.
     * @param softwareProduct the value to set
     */
    @JsonProperty("software_product")
    public void setSoftwareProduct(String softwareProduct) { this.softwareProduct = softwareProduct; }

    /**
     * Retrieve the {@code table_type} property (displayed as '{@literal Table Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("table_type")
    public String getTableType() { return this.tableType; }

    /**
     * Set the {@code table_type} property (displayed as {@code Table Type}) of the object.
     * @param tableType the value to set
     */
    @JsonProperty("table_type")
    public void setTableType(String tableType) { this.tableType = tableType; }

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
