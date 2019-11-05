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
 * POJO for the {@code logical_data_model} asset type in IGC, displayed as '{@literal Logical Data Model}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("logical_data_model")
public class LogicalDataModel extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("author")
    protected String author;

    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("contains_logical_data_models")
    protected ItemList<LogicalDataModel> containsLogicalDataModels;

    @JsonProperty("design_tool")
    protected String designTool;

    @JsonProperty("implemented_by_database_schemas")
    protected ItemList<DatabaseSchema> implementedByDatabaseSchemas;

    @JsonProperty("implemented_by_physical_data_models")
    protected ItemList<PhysicalDataModel> implementedByPhysicalDataModels;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("logical_data_model")
    protected LogicalDataModel logicalDataModel;

    @JsonProperty("logical_domains")
    protected ItemList<LogicalDomain> logicalDomains;

    @JsonProperty("logical_entities")
    protected ItemList<LogicalEntity> logicalEntities;

    @JsonProperty("methodology")
    protected String methodology;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("package")
    protected String zpackage;

    @JsonProperty("subject_areas")
    protected ItemList<SubjectArea> subjectAreas;

    @JsonProperty("version")
    protected String version;

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
     * Retrieve the {@code author} property (displayed as '{@literal Author}') of the object.
     * @return {@code String}
     */
    @JsonProperty("author")
    public String getAuthor() { return this.author; }

    /**
     * Set the {@code author} property (displayed as {@code Author}) of the object.
     * @param author the value to set
     */
    @JsonProperty("author")
    public void setAuthor(String author) { this.author = author; }

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
     * Retrieve the {@code contains_logical_data_models} property (displayed as '{@literal Contains Logical Data Models}') of the object.
     * @return {@code ItemList<LogicalDataModel>}
     */
    @JsonProperty("contains_logical_data_models")
    public ItemList<LogicalDataModel> getContainsLogicalDataModels() { return this.containsLogicalDataModels; }

    /**
     * Set the {@code contains_logical_data_models} property (displayed as {@code Contains Logical Data Models}) of the object.
     * @param containsLogicalDataModels the value to set
     */
    @JsonProperty("contains_logical_data_models")
    public void setContainsLogicalDataModels(ItemList<LogicalDataModel> containsLogicalDataModels) { this.containsLogicalDataModels = containsLogicalDataModels; }

    /**
     * Retrieve the {@code design_tool} property (displayed as '{@literal Design Tool}') of the object.
     * @return {@code String}
     */
    @JsonProperty("design_tool")
    public String getDesignTool() { return this.designTool; }

    /**
     * Set the {@code design_tool} property (displayed as {@code Design Tool}) of the object.
     * @param designTool the value to set
     */
    @JsonProperty("design_tool")
    public void setDesignTool(String designTool) { this.designTool = designTool; }

    /**
     * Retrieve the {@code implemented_by_database_schemas} property (displayed as '{@literal Implemented by Database Schemas}') of the object.
     * @return {@code ItemList<DatabaseSchema>}
     */
    @JsonProperty("implemented_by_database_schemas")
    public ItemList<DatabaseSchema> getImplementedByDatabaseSchemas() { return this.implementedByDatabaseSchemas; }

    /**
     * Set the {@code implemented_by_database_schemas} property (displayed as {@code Implemented by Database Schemas}) of the object.
     * @param implementedByDatabaseSchemas the value to set
     */
    @JsonProperty("implemented_by_database_schemas")
    public void setImplementedByDatabaseSchemas(ItemList<DatabaseSchema> implementedByDatabaseSchemas) { this.implementedByDatabaseSchemas = implementedByDatabaseSchemas; }

    /**
     * Retrieve the {@code implemented_by_physical_data_models} property (displayed as '{@literal Implemented by Physical Data Models}') of the object.
     * @return {@code ItemList<PhysicalDataModel>}
     */
    @JsonProperty("implemented_by_physical_data_models")
    public ItemList<PhysicalDataModel> getImplementedByPhysicalDataModels() { return this.implementedByPhysicalDataModels; }

    /**
     * Set the {@code implemented_by_physical_data_models} property (displayed as {@code Implemented by Physical Data Models}) of the object.
     * @param implementedByPhysicalDataModels the value to set
     */
    @JsonProperty("implemented_by_physical_data_models")
    public void setImplementedByPhysicalDataModels(ItemList<PhysicalDataModel> implementedByPhysicalDataModels) { this.implementedByPhysicalDataModels = implementedByPhysicalDataModels; }

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
     * Retrieve the {@code logical_data_model} property (displayed as '{@literal Logical Data Model}') of the object.
     * @return {@code LogicalDataModel}
     */
    @JsonProperty("logical_data_model")
    public LogicalDataModel getLogicalDataModel() { return this.logicalDataModel; }

    /**
     * Set the {@code logical_data_model} property (displayed as {@code Logical Data Model}) of the object.
     * @param logicalDataModel the value to set
     */
    @JsonProperty("logical_data_model")
    public void setLogicalDataModel(LogicalDataModel logicalDataModel) { this.logicalDataModel = logicalDataModel; }

    /**
     * Retrieve the {@code logical_domains} property (displayed as '{@literal Logical Domains}') of the object.
     * @return {@code ItemList<LogicalDomain>}
     */
    @JsonProperty("logical_domains")
    public ItemList<LogicalDomain> getLogicalDomains() { return this.logicalDomains; }

    /**
     * Set the {@code logical_domains} property (displayed as {@code Logical Domains}) of the object.
     * @param logicalDomains the value to set
     */
    @JsonProperty("logical_domains")
    public void setLogicalDomains(ItemList<LogicalDomain> logicalDomains) { this.logicalDomains = logicalDomains; }

    /**
     * Retrieve the {@code logical_entities} property (displayed as '{@literal Logical Entities}') of the object.
     * @return {@code ItemList<LogicalEntity>}
     */
    @JsonProperty("logical_entities")
    public ItemList<LogicalEntity> getLogicalEntities() { return this.logicalEntities; }

    /**
     * Set the {@code logical_entities} property (displayed as {@code Logical Entities}) of the object.
     * @param logicalEntities the value to set
     */
    @JsonProperty("logical_entities")
    public void setLogicalEntities(ItemList<LogicalEntity> logicalEntities) { this.logicalEntities = logicalEntities; }

    /**
     * Retrieve the {@code methodology} property (displayed as '{@literal Methodology}') of the object.
     * @return {@code String}
     */
    @JsonProperty("methodology")
    public String getMethodology() { return this.methodology; }

    /**
     * Set the {@code methodology} property (displayed as {@code Methodology}) of the object.
     * @param methodology the value to set
     */
    @JsonProperty("methodology")
    public void setMethodology(String methodology) { this.methodology = methodology; }

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

    /**
     * Retrieve the {@code package} property (displayed as '{@literal Package}') of the object.
     * @return {@code String}
     */
    @JsonProperty("package")
    public String getPackage() { return this.zpackage; }

    /**
     * Set the {@code package} property (displayed as {@code Package}) of the object.
     * @param zpackage the value to set
     */
    @JsonProperty("package")
    public void setPackage(String zpackage) { this.zpackage = zpackage; }

    /**
     * Retrieve the {@code subject_areas} property (displayed as '{@literal Subject Areas}') of the object.
     * @return {@code ItemList<SubjectArea>}
     */
    @JsonProperty("subject_areas")
    public ItemList<SubjectArea> getSubjectAreas() { return this.subjectAreas; }

    /**
     * Set the {@code subject_areas} property (displayed as {@code Subject Areas}) of the object.
     * @param subjectAreas the value to set
     */
    @JsonProperty("subject_areas")
    public void setSubjectAreas(ItemList<SubjectArea> subjectAreas) { this.subjectAreas = subjectAreas; }

    /**
     * Retrieve the {@code version} property (displayed as '{@literal Version}') of the object.
     * @return {@code String}
     */
    @JsonProperty("version")
    public String getVersion() { return this.version; }

    /**
     * Set the {@code version} property (displayed as {@code Version}) of the object.
     * @param version the value to set
     */
    @JsonProperty("version")
    public void setVersion(String version) { this.version = version; }

}
