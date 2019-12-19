/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;

import java.util.Date;
import java.util.List;

/**
 * The ultimate parent object for all IGC assets, it contains only the most basic information common to every single
 * asset in IGC, and present in every single reference to an IGC asset (whether via relationship, search result,
 * etc):<br>
 * <ul>
 *     <li>_name</li>
 *     <li>_type</li>
 *     <li>_id</li>
 *     <li>_url</li>
 *     <li><i>_context</i> -- present for <i>almost</i> all assets</li>
 * </ul><br>
 *  POJOs to represent user-defined objects (OpenIGC) should not extend this class directly, but the MainObject class.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Reference.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Acl.class, name = "acl"),
        @JsonSubTypes.Type(value = Aclentry.class, name = "aclentry"),
        @JsonSubTypes.Type(value = Actiondescriptor.class, name = "actiondescriptor"),
        @JsonSubTypes.Type(value = AnalysisProject.class, name = "analysis_project"),
        @JsonSubTypes.Type(value = AnalyticsObject.class, name = "analytics_object"),
        @JsonSubTypes.Type(value = Applicationfunction.class, name = "applicationfunction"),
        @JsonSubTypes.Type(value = Applicationsetting.class, name = "applicationsetting"),
        @JsonSubTypes.Type(value = Archivedtask.class, name = "archivedtask"),
        @JsonSubTypes.Type(value = AsclSteward.class, name = "ascl_steward"),
        @JsonSubTypes.Type(value = Associativeobject.class, name = "associativeobject"),
        @JsonSubTypes.Type(value = BiFilter.class, name = "bi_filter"),
        @JsonSubTypes.Type(value = BiFolder.class, name = "bi_folder"),
        @JsonSubTypes.Type(value = BiHierarchyMember.class, name = "bi_hierarchy_member"),
        @JsonSubTypes.Type(value = BiOlapLevelElement.class, name = "bi_olap_level_element"),
        @JsonSubTypes.Type(value = BiReportDataItemSource.class, name = "bi_report_data_item_source"),
        @JsonSubTypes.Type(value = BiReportSection.class, name = "bi_report_section"),
        @JsonSubTypes.Type(value = BiRootFolder.class, name = "bi_root_folder"),
        @JsonSubTypes.Type(value = BinaryInfosetOperation.class, name = "binary_infoset_operation"),
        @JsonSubTypes.Type(value = BlueprintElementLink.class, name = "blueprint_element_link"),
        @JsonSubTypes.Type(value = CandidateKey.class, name = "candidate_key"),
        @JsonSubTypes.Type(value = CdcMapping.class, name = "cdc_mapping"),
        @JsonSubTypes.Type(value = ChangedProperties.class, name = "changed_properties"),
        @JsonSubTypes.Type(value = Classdescriptor.class, name = "classdescriptor"),
        @JsonSubTypes.Type(value = Classification.class, name = "classification"),
        @JsonSubTypes.Type(value = ClassificationContribution.class, name = "classification_contribution"),
        @JsonSubTypes.Type(value = Classificationtask.class, name = "classificationtask"),
        @JsonSubTypes.Type(value = Collection.class, name = "collection"),
        @JsonSubTypes.Type(value = ColumnAnalysis.class, name = "column_analysis"),
        @JsonSubTypes.Type(value = ColumnAnalysisSummary.class, name = "column_analysis_summary"),
        @JsonSubTypes.Type(value = Connector.class, name = "connector"),
        @JsonSubTypes.Type(value = Credential.class, name = "credential"),
        @JsonSubTypes.Type(value = Credentials.class, name = "credentials"),
        @JsonSubTypes.Type(value = CustomAttribute.class, name = "custom_attribute"),
        @JsonSubTypes.Type(value = Customattributeval.class, name = "customattributeval"),
        @JsonSubTypes.Type(value = DataConnection.class, name = "data_connection"),
        @JsonSubTypes.Type(value = DataConnectionMapping.class, name = "data_connection_mapping"),
        @JsonSubTypes.Type(value = DataItemProperties.class, name = "data_item_properties"),
        @JsonSubTypes.Type(value = DataItemValue.class, name = "data_item_value"),
        @JsonSubTypes.Type(value = DataMapFilterOperation.class, name = "data_map_filter_operation"),
        @JsonSubTypes.Type(value = DataRuleResults.class, name = "data_rule_results"),
        @JsonSubTypes.Type(value = DatabaseDomain.class, name = "database_domain"),
        @JsonSubTypes.Type(value = DatabaseIndex.class, name = "database_index"),
        @JsonSubTypes.Type(value = Datasourcealiasgroup.class, name = "datasourcealiasgroup"),
        @JsonSubTypes.Type(value = DatastageItem.class, name = "datastage_item"),
        @JsonSubTypes.Type(value = Derivation.class, name = "derivation"),
        @JsonSubTypes.Type(value = DesignForeignKey.class, name = "design_foreign_key"),
        @JsonSubTypes.Type(value = DesignKey.class, name = "design_key"),
        @JsonSubTypes.Type(value = DevelopmentLog.class, name = "development_log"),
        @JsonSubTypes.Type(value = Directoryproviderconfiguration.class, name = "directoryproviderconfiguration"),
        @JsonSubTypes.Type(value = Directoryproviderproperty.class, name = "directoryproviderproperty"),
        @JsonSubTypes.Type(value = Dsargumentmap.class, name = "dsargumentmap"),
        @JsonSubTypes.Type(value = DsdataConnection.class, name = "dsdata_connection"),
        @JsonSubTypes.Type(value = Dsexternaldependency.class, name = "dsexternaldependency"),
        @JsonSubTypes.Type(value = Dsfolder.class, name = "dsfolder"),
        @JsonSubTypes.Type(value = Dsmetabag.class, name = "dsmetabag"),
        @JsonSubTypes.Type(value = Dsmfcolumninfo.class, name = "dsmfcolumninfo"),
        @JsonSubTypes.Type(value = Dsparameter.class, name = "dsparameter"),
        @JsonSubTypes.Type(value = DsparameterJob.class, name = "dsparameter_job"),
        @JsonSubTypes.Type(value = DsparameterSet.class, name = "dsparameter_set"),
        @JsonSubTypes.Type(value = DuplicatesOperation.class, name = "duplicates_operation"),
        @JsonSubTypes.Type(value = ExceptionFilterOperation.class, name = "exception_filter_operation"),
        @JsonSubTypes.Type(value = ExtensionMapping.class, name = "extension_mapping"),
        @JsonSubTypes.Type(value = FieldAnalysis.class, name = "field_analysis"),
        @JsonSubTypes.Type(value = FileRecordAnalysis.class, name = "file_record_analysis"),
        @JsonSubTypes.Type(value = FilterOperation.class, name = "filter_operation"),
        @JsonSubTypes.Type(value = Folder.class, name = "folder"),
        @JsonSubTypes.Type(value = ForeignKey.class, name = "foreign_key"),
        @JsonSubTypes.Type(value = ForeignKeyDefinition.class, name = "foreign_key_definition"),
        @JsonSubTypes.Type(value = FunctionCall.class, name = "function_call"),
        @JsonSubTypes.Type(value = FunctionCall2.class, name = "functioncall"),
        @JsonSubTypes.Type(value = Group.class, name = "group"),
        @JsonSubTypes.Type(value = IndexMember.class, name = "index_member"),
        @JsonSubTypes.Type(value = InferredForeignKey.class, name = "inferred_foreign_key"),
        @JsonSubTypes.Type(value = InferredKey.class, name = "inferred_key"),
        @JsonSubTypes.Type(value = InformationServerReport.class, name = "information_server_report"),
        @JsonSubTypes.Type(value = InformationServerReportAnalysisProject.class, name = "information_server_report_(analysis_project)"),
        @JsonSubTypes.Type(value = InformationServerReportDatabase.class, name = "information_server_report_(database)"),
        @JsonSubTypes.Type(value = InformationServerReportJob.class, name = "information_server_report_(job)"),
        @JsonSubTypes.Type(value = InformationServerReportMappingProject.class, name = "information_server_report_(mapping_project)"),
        @JsonSubTypes.Type(value = InformationServerReportMappingSpecification.class, name = "information_server_report_(mapping_specification)"),
        @JsonSubTypes.Type(value = InformationServerReportSteward.class, name = "information_server_report_(steward)"),
        @JsonSubTypes.Type(value = InformationServerReportParamJob.class, name = "information_server_report_param_(job)"),
        @JsonSubTypes.Type(value = InformationServerReportParameters.class, name = "information_server_report_parameters"),
        @JsonSubTypes.Type(value = InformationServicesArgument.class, name = "information_services_argument"),
        @JsonSubTypes.Type(value = InfosetOperation.class, name = "infoset_operation"),
        @JsonSubTypes.Type(value = Inputpin.class, name = "inputpin"),
        @JsonSubTypes.Type(value = InvDataRule.class, name = "inv_data_rule"),
        @JsonSubTypes.Type(value = InvDataRuleDefinition.class, name = "inv_data_rule_definition"),
        @JsonSubTypes.Type(value = InvDataRuleSet.class, name = "inv_data_rule_set"),
        @JsonSubTypes.Type(value = InvDataRuleSetDefinition.class, name = "inv_data_rule_set_definition"),
        @JsonSubTypes.Type(value = JobConstraint.class, name = "job_constraint"),
        @JsonSubTypes.Type(value = JobFailEvent.class, name = "job_fail_event"),
        @JsonSubTypes.Type(value = JobInputPin.class, name = "job_input_pin"),
        @JsonSubTypes.Type(value = JobOutputPin.class, name = "job_output_pin"),
        @JsonSubTypes.Type(value = JobParameter.class, name = "job_parameter"),
        @JsonSubTypes.Type(value = JobReadEvent.class, name = "job_read_event"),
        @JsonSubTypes.Type(value = JobRun.class, name = "job_run"),
        @JsonSubTypes.Type(value = JobRunActivity.class, name = "job_run_activity"),
        @JsonSubTypes.Type(value = JobStageParameters.class, name = "job_stage_parameters"),
        @JsonSubTypes.Type(value = JobWriteEvent.class, name = "job_write_event"),
        @JsonSubTypes.Type(value = Keycomponent.class, name = "keycomponent"),
        @JsonSubTypes.Type(value = Label.class, name = "label"),
        @JsonSubTypes.Type(value = LineageReportTemplate.class, name = "lineage_report_template"),
        @JsonSubTypes.Type(value = Lineagefilter.class, name = "lineagefilter"),
        @JsonSubTypes.Type(value = Link.class, name = "link"),
        @JsonSubTypes.Type(value = Linktype.class, name = "linktype"),
        @JsonSubTypes.Type(value = LocalContainer.class, name = "local_container"),
        @JsonSubTypes.Type(value = LogicalDomain.class, name = "logical_domain"),
        @JsonSubTypes.Type(value = LogicalForeignKey.class, name = "logical_foreign_key"),
        @JsonSubTypes.Type(value = LogicalInversionKey.class, name = "logical_inversion_key"),
        @JsonSubTypes.Type(value = LogicalKey.class, name = "logical_key"),
        @JsonSubTypes.Type(value = LogicalValidationList.class, name = "logical_validation_list"),
        @JsonSubTypes.Type(value = LogicalValidationRange.class, name = "logical_validation_range"),
        @JsonSubTypes.Type(value = LogicalValidationRule.class, name = "logical_validation_rule"),
        @JsonSubTypes.Type(value = LogicalVariable.class, name = "logical_variable"),
        @JsonSubTypes.Type(value = MainObject.class, name = "main_object"),
        @JsonSubTypes.Type(value = Mapping.class, name = "mapping"),
        @JsonSubTypes.Type(value = MappingComponent.class, name = "mapping_component"),
        @JsonSubTypes.Type(value = MappingFilter.class, name = "mapping_filter"),
        @JsonSubTypes.Type(value = MappingJoin.class, name = "mapping_join"),
        @JsonSubTypes.Type(value = ModelEvalMetric.class, name = "model_eval_metric"),
        @JsonSubTypes.Type(value = ModelInput.class, name = "model_input"),
        @JsonSubTypes.Type(value = ModelLabel.class, name = "model_label"),
        @JsonSubTypes.Type(value = MwbDatabaseAlias.class, name = "mwb_database_alias"),
        @JsonSubTypes.Type(value = Navigationdescriptor.class, name = "navigationdescriptor"),
        @JsonSubTypes.Type(value = NodeOperation.class, name = "node_operation"),
        @JsonSubTypes.Type(value = NonStewardUser.class, name = "non_steward_user"),
        @JsonSubTypes.Type(value = OlapJoin.class, name = "olap_join"),
        @JsonSubTypes.Type(value = OlapMemberSource.class, name = "olap_member_source"),
        @JsonSubTypes.Type(value = Olapjoinref.class, name = "olapjoinref"),
        @JsonSubTypes.Type(value = OslcLink.class, name = "oslc_link"),
        @JsonSubTypes.Type(value = Outputpin.class, name = "outputpin"),
        @JsonSubTypes.Type(value = ParameterSetDefinition.class, name = "parameter_set_definition"),
        @JsonSubTypes.Type(value = Parameterval.class, name = "parameterval"),
        @JsonSubTypes.Type(value = PhysicalDomain.class, name = "physical_domain"),
        @JsonSubTypes.Type(value = Primarycategory.class, name = "primarycategory"),
        @JsonSubTypes.Type(value = Propdescriptor.class, name = "propdescriptor"),
        @JsonSubTypes.Type(value = Providerpropertyinfo.class, name = "providerpropertyinfo"),
        @JsonSubTypes.Type(value = Providerpropertyinfoextended.class, name = "providerpropertyinfoextended"),
        @JsonSubTypes.Type(value = Providerpropertytype.class, name = "providerpropertytype"),
        @JsonSubTypes.Type(value = QualityProblemType.class, name = "quality_Problem_Type"),
        @JsonSubTypes.Type(value = QualityProblem.class, name = "quality_problem"),
        @JsonSubTypes.Type(value = QualityProblemTypeDqr.class, name = "quality_problem_type_DQR"),
        @JsonSubTypes.Type(value = ReferenceKey.class, name = "reference_key"),
        @JsonSubTypes.Type(value = ReferencedContainer.class, name = "referenced_container"),
        @JsonSubTypes.Type(value = RoleAssignment.class, name = "role_assignment"),
        @JsonSubTypes.Type(value = RoleContext.class, name = "role_context"),
        @JsonSubTypes.Type(value = RootTuple.class, name = "root_tuple"),
        @JsonSubTypes.Type(value = SapConnection.class, name = "sap_connection"),
        @JsonSubTypes.Type(value = SessionConfiguration.class, name = "session_configuration"),
        @JsonSubTypes.Type(value = SetOperation.class, name = "set_operation"),
        @JsonSubTypes.Type(value = StageDataRuleDefinition.class, name = "stage_data_rule_definition"),
        @JsonSubTypes.Type(value = StandardizationObject.class, name = "standardization_object"),
        @JsonSubTypes.Type(value = StandardizationRule.class, name = "standardization_rule"),
        @JsonSubTypes.Type(value = Steward.class, name = "steward"),
        @JsonSubTypes.Type(value = StewardGroup.class, name = "steward_group"),
        @JsonSubTypes.Type(value = StewardUser.class, name = "steward_user"),
        @JsonSubTypes.Type(value = TableAnalysis.class, name = "table_analysis"),
        @JsonSubTypes.Type(value = TableAnalysisSummary.class, name = "table_analysis_summary"),
        @JsonSubTypes.Type(value = TableDefinitionProperties.class, name = "table_definition_properties"),
        @JsonSubTypes.Type(value = TermAssignment.class, name = "term_assignment"),
        @JsonSubTypes.Type(value = TermHistory.class, name = "term_history"),
        @JsonSubTypes.Type(value = TransformsFunction.class, name = "transforms_function"),
        @JsonSubTypes.Type(value = Tuple.class, name = "tuple"),
        @JsonSubTypes.Type(value = User.class, name = "user"),
        @JsonSubTypes.Type(value = UserGroup.class, name = "user_group"),
        @JsonSubTypes.Type(value = UserRole.class, name = "user_role"),
        @JsonSubTypes.Type(value = ValidValue.class, name = "valid_value"),
        @JsonSubTypes.Type(value = ValidValueList.class, name = "valid_value_list"),
        @JsonSubTypes.Type(value = ValidValueRange.class, name = "valid_value_range"),
        @JsonSubTypes.Type(value = ValidityTable.class, name = "validity_table"),
        @JsonSubTypes.Type(value = ValidValueList2.class, name = "validvaluelist"),
        @JsonSubTypes.Type(value = ValidValueRange2.class, name = "validvaluerange"),
        @JsonSubTypes.Type(value = Validvaluerule.class, name = "validvaluerule"),
        @JsonSubTypes.Type(value = Validvalues.class, name = "validvalues"),
        @JsonSubTypes.Type(value = VolumeContribution.class, name = "volume_contribution"),
        @JsonSubTypes.Type(value = WarehouseMapping.class, name = "warehouse_mapping"),
        @JsonSubTypes.Type(value = XsdAttributeReference.class, name = "xsd_attribute_reference"),
        @JsonSubTypes.Type(value = XsdElementGroupReference.class, name = "xsd_element_group_reference"),
        @JsonSubTypes.Type(value = XsdElementReference.class, name = "xsd_element_reference"),
        @JsonSubTypes.Type(value = XsdForeignKey.class, name = "xsd_foreign_key"),
        @JsonSubTypes.Type(value = XsdPrimaryKey.class, name = "xsd_primary_key"),
        @JsonSubTypes.Type(value = XsdUniqueKey.class, name = "xsd_unique_key"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Reference extends ObjectPrinter {

    /**
     * Used to uniquely identify the object without relying on its ID (RID) remaining static.
     */
    @JsonIgnore
    private Identity identity = null;

    /**
     * Used to indicate whether this asset has been fully retrieved already (true) or not (false).
     */
    @JsonIgnore
    private boolean fullyRetrieved = false;

    /**
     * Provides the context to the unique identity of this asset. Note that while this will exist on
     * almost all IGC assets, it is not present on absolutely all of them -- also be aware that without
     * v11.7.0.2+ and an optional parameter it uses, this will always be 'null' in a ReferenceList
     */
    protected List<Reference> _context;

    /**
     * The '_name' property of a Reference is equivalent to its 'name' property, but will always be
     * populated on a reference ('name' may not yet be populated, depending on whether you have only a reference
     * to the asset, or the full asset itself).
     */
    protected String _name;

    /**
     * The '_type' property defines the type of asset this Reference represents. To allow a Reference to
     * be automatically translated into a Java object, you must first register the Java class that should
     * interpret this data type using {@link IGCRestClient#registerPOJO(Class)}.
     */
    protected String _type;

    /**
     * The '_id' property defines the unique Repository ID (RID) of the asset. This ID should be unique within
     * an instance (environment) of IGC.
     */
    protected String _id;

    /**
     * The '_url' property provides a navigable link directly to the full details of asset this Reference represents,
     * within a given IGC environment.
     */
    protected String _url;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    /**
     * Default constructor
     */
    public Reference() {}

    /**
     * Construct a simple stub representing an object instance in IGC.
     *
     * @param name name of the object instance
     * @param type type of the object instance
     */
    public Reference(String name, String type) {
        this(name, type, null);
    }

    /**
     * Construct a simple stub representing an object instance in IGC.
     *
     * @param name name of the object instance
     * @param type type of the object instance
     * @param id repository ID (RID) of the object instance
     */
    public Reference(String name, String type, String id) {
        this._name = name;
        this._type = type;
        this._id = id;
    }

    /**
     * Retrieve the context of the IGC object instance.
     *
     * @return {@code List<Reference>}
     * @see #_context
     */
    @JsonProperty("_context")
    public List<Reference> getContext() { return _context; }

    /**
     * Set the context of the IGC object instance.
     *
     * @param _context of the IGC object instance
     * @see #_context
     */
    @JsonProperty("_context")
    public void setContext(List<Reference> _context) { this._context = _context; }

    /**
     * Retrieve the name of the IGC object instance.
     *
     * @return String
     * @see #_name
     */
    @JsonProperty("_name")
    public String getName() { return _name; }

    /**
     * Set the name of the IGC object instance.
     *
     * @param _name of the IGC object instance
     * @see #_name
     */
    @JsonProperty("_name")
    public void setName(String _name) { this._name = _name; }

    /**
     * Retrieve the type of the IGC object instance.
     *
     * @return String
     * @see #_type
     */
    @JsonProperty("_type")
    public String getType() { return _type; }

    /**
     * Set the type of the IGC object instance.
     *
     * @param _type of the IGC object instance
     * @see #_type
     */
    @JsonProperty("_type")
    public void setType(String _type) { this._type = _type; }

    /**
     * Retrieve the Repository ID (RID) of the object instance.
     *
     * @return String
     * @see #_id
     */
    @JsonProperty("_id")
    public String getId() { return _id; }

    /**
     * Set the Repository ID (RID) of the object instance.
     *
     * @param _id of the IGC object instance
     * @see #_id
     */
    @JsonProperty("_id")
    public void setId(String _id) { this._id = _id; }

    /**
     * Retrieve the IGC REST API URL to the object instance's details.
     *
     * @return String
     * @see #_url
     */
    @JsonProperty("_url")
    public String getUrl() { return _url; }

    /**
     * Set the IGC REST API URL of the object instance's details.
     *
     * @param _url of the IGC object instance
     * @see #_url
     */
    @JsonProperty("_url")
    public void setUrl(String _url) { this._url = _url; }

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * Note: not all objects in IGC have this information, but for re-usability purposes these are held at top-level.
     *
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

    /**
     * Determine whether this object instance is fully retrieved (true) or only partially (false).
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isFullyRetrieved() { return fullyRetrieved; }

    /**
     * Mark this object instance as fully-retrieved from IGC.
     */
    @JsonIgnore
    public void setFullyRetrieved() { fullyRetrieved = true; }

    /**
     * Returns true iff the provided object is a simple type (String, Number, Boolean, Date, etc).
     *
     * @param obj the object to check
     * @return Boolean
     */
    @JsonIgnore
    public static boolean isSimpleType(Object obj) {
        return (!(obj instanceof Reference) && !(obj instanceof ItemList));
    }

    /**
     * Indicates whether the item's modification details (if any) have already been populated (true) or not (false).
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean areModificationDetailsPopulated() {
        return createdBy != null;
    }

    /**
     * Indicates whether this item's identity has already been populated (true) or not (false).
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isIdentityPopulated() {
        return identity != null;
    }

    /**
     * Retrieves the semantic identity of the asset.
     *
     * @param igcrest a REST API connection to use in confirming the identity of the asset
     * @return Identity
     */
    @JsonIgnore
    public Identity getIdentity(IGCRestClient igcrest) {
        if (!isIdentityPopulated()) {
            boolean hasModificationDetails = igcrest.hasModificationDetails(IGCRestConstants.getAssetTypeForSearch(getType()));
            if (_context != null && _context.size() > 0
                    && (!hasModificationDetails
                        || (igcrest.hasModificationDetails(IGCRestConstants.getAssetTypeForSearch(getType()))
                            && createdBy != null && createdOn != null && modifiedBy != null && modifiedOn != null))) {
                // If we have all of the necessary information to populate the identity, do so directly
                identity = new Identity(getContext(), getType(), getName(), getId());
            } else {
                // Only if we do not, go ahead with another search to retrieve the necessary details
                Reference assetWithCtx = igcrest.getModificationDetails(this);
                setContext(assetWithCtx.getContext());
                setCreatedOn(assetWithCtx.getCreatedOn());
                setCreatedBy(assetWithCtx.getCreatedBy());
                setModifiedOn(assetWithCtx.getModifiedOn());
                setModifiedBy(assetWithCtx.getModifiedBy());
                identity = new Identity(getContext(), getType(), getName(), getId());
            }
        }
        return identity;
    }

    // TODO: eventually handle the '_expand' that exists for data classifications

}
