/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code information_asset} asset type in IGC, displayed as '{@literal Information Asset}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=InformationAsset.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RuleExecutionResult.class, name = "Rule_Execution_Result"),
        @JsonSubTypes.Type(value = AmazonS3Bucket.class, name = "amazon_s3_bucket"),
        @JsonSubTypes.Type(value = AmazonS3DataFile.class, name = "amazon_s3_data_file"),
        @JsonSubTypes.Type(value = AmazonS3DataFileField.class, name = "amazon_s3_data_file_field"),
        @JsonSubTypes.Type(value = AmazonS3DataFileFolder.class, name = "amazon_s3_data_file_folder"),
        @JsonSubTypes.Type(value = AmazonS3DataFileRecord.class, name = "amazon_s3_data_file_record"),
        @JsonSubTypes.Type(value = AnalyticsModel.class, name = "analytics_model"),
        @JsonSubTypes.Type(value = AnalyticsProject.class, name = "analytics_project"),
        @JsonSubTypes.Type(value = Application.class, name = "application"),
        @JsonSubTypes.Type(value = Attribute.class, name = "attribute"),
        @JsonSubTypes.Type(value = AttributeType.class, name = "attribute_type"),
        @JsonSubTypes.Type(value = AttributeTypeField.class, name = "attribute_type_field"),
        @JsonSubTypes.Type(value = AutomationRule.class, name = "automation_rule"),
        @JsonSubTypes.Type(value = BiCollection.class, name = "bi_collection"),
        @JsonSubTypes.Type(value = BiCollectionMember.class, name = "bi_collection_member"),
        @JsonSubTypes.Type(value = BiCube.class, name = "bi_cube"),
        @JsonSubTypes.Type(value = BiModel.class, name = "bi_model"),
        @JsonSubTypes.Type(value = BiReport.class, name = "bi_report"),
        @JsonSubTypes.Type(value = BiReportNocontext.class, name = "bi_report_nocontext"),
        @JsonSubTypes.Type(value = BiReportNofolder.class, name = "bi_report_nofolder"),
        @JsonSubTypes.Type(value = BiReportQuery.class, name = "bi_report_query"),
        @JsonSubTypes.Type(value = BiReportQueryDetailFilter.class, name = "bi_report_query_detail_filter"),
        @JsonSubTypes.Type(value = BiReportQuerySummaryFilter.class, name = "bi_report_query_summary_filter"),
        @JsonSubTypes.Type(value = BiReportQueryUsage.class, name = "bi_report_query_usage"),
        @JsonSubTypes.Type(value = BiServer.class, name = "bi_server"),
        @JsonSubTypes.Type(value = Blueprint.class, name = "blueprint"),
        @JsonSubTypes.Type(value = Category.class, name = "category"),
        @JsonSubTypes.Type(value = CdcMappingDocument.class, name = "cdc_mapping_document"),
        @JsonSubTypes.Type(value = CompositeView.class, name = "composite_view"),
        @JsonSubTypes.Type(value = Customattributedef.class, name = "customattributedef"),
        @JsonSubTypes.Type(value = DataClass.class, name = "data_class"),
        @JsonSubTypes.Type(value = DataFile.class, name = "data_file"),
        @JsonSubTypes.Type(value = DataFileDefinition.class, name = "data_file_definition"),
        @JsonSubTypes.Type(value = DataFileDefinitionField.class, name = "data_file_definition_field"),
        @JsonSubTypes.Type(value = DataFileDefinitionRecord.class, name = "data_file_definition_record"),
        @JsonSubTypes.Type(value = DataFileFolder.class, name = "data_file_folder"),
        @JsonSubTypes.Type(value = DataFileFolderNobucket.class, name = "data_file_folder_nobucket"),
        @JsonSubTypes.Type(value = DataItem.class, name = "data_item"),
        @JsonSubTypes.Type(value = DataRule.class, name = "data_rule"),
        @JsonSubTypes.Type(value = DataRuleDefinition.class, name = "data_rule_definition"),
        @JsonSubTypes.Type(value = DataRuleSet.class, name = "data_rule_set"),
        @JsonSubTypes.Type(value = DataRuleSetDefinition.class, name = "data_rule_set_definition"),
        @JsonSubTypes.Type(value = Database.class, name = "database"),
        @JsonSubTypes.Type(value = DatabaseSchema.class, name = "database_schema"),
        @JsonSubTypes.Type(value = Datagroup.class, name = "datagroup"),
        @JsonSubTypes.Type(value = DesignColumn.class, name = "design_column"),
        @JsonSubTypes.Type(value = DesignStoredProcedureParameter.class, name = "design_stored_procedure_parameter"),
        @JsonSubTypes.Type(value = Dsjob.class, name = "dsjob"),
        @JsonSubTypes.Type(value = Endpoint.class, name = "endpoint"),
        @JsonSubTypes.Type(value = EntityAttribute.class, name = "entity_attribute"),
        @JsonSubTypes.Type(value = EntityType.class, name = "entity_type"),
        @JsonSubTypes.Type(value = ExtensionMappingDocument.class, name = "extension_mapping_document"),
        @JsonSubTypes.Type(value = File.class, name = "file"),
        @JsonSubTypes.Type(value = Filter.class, name = "filter"),
        @JsonSubTypes.Type(value = HbaseNamespace.class, name = "hbase_namespace"),
        @JsonSubTypes.Type(value = Host.class, name = "host"),
        @JsonSubTypes.Type(value = IdocField.class, name = "idoc_field"),
        @JsonSubTypes.Type(value = IdocSegmentType.class, name = "idoc_segment_type"),
        @JsonSubTypes.Type(value = IdocType.class, name = "idoc_type"),
        @JsonSubTypes.Type(value = InParameter.class, name = "in_parameter"),
        @JsonSubTypes.Type(value = InformationGovernancePolicy.class, name = "information_governance_policy"),
        @JsonSubTypes.Type(value = InformationGovernanceRule.class, name = "information_governance_rule"),
        @JsonSubTypes.Type(value = InformationServicesApplication.class, name = "information_services_application"),
        @JsonSubTypes.Type(value = InformationServicesProject.class, name = "information_services_project"),
        @JsonSubTypes.Type(value = Infoset.class, name = "infoset"),
        @JsonSubTypes.Type(value = InoutParameter.class, name = "inout_parameter"),
        @JsonSubTypes.Type(value = InputParameter.class, name = "input_parameter"),
        @JsonSubTypes.Type(value = Instance.class, name = "instance"),
        @JsonSubTypes.Type(value = LineageContainer.class, name = "lineage_container"),
        @JsonSubTypes.Type(value = LogicalDataModel.class, name = "logical_data_model"),
        @JsonSubTypes.Type(value = LogicalEntity.class, name = "logical_entity"),
        @JsonSubTypes.Type(value = MachineProfile.class, name = "machine_profile"),
        @JsonSubTypes.Type(value = MappingProject.class, name = "mapping_project"),
        @JsonSubTypes.Type(value = MappingSpecification.class, name = "mapping_specification"),
        @JsonSubTypes.Type(value = MaskingRule.class, name = "masking_rule"),
        @JsonSubTypes.Type(value = MatchSpecification.class, name = "match_specification"),
        @JsonSubTypes.Type(value = MdmModel.class, name = "mdm_model"),
        @JsonSubTypes.Type(value = MemberType.class, name = "member_type"),
        @JsonSubTypes.Type(value = Method.class, name = "method"),
        @JsonSubTypes.Type(value = Metric.class, name = "metric"),
        @JsonSubTypes.Type(value = NonPublishedDataRuleDefinition.class, name = "non_published_data_rule_definition"),
        @JsonSubTypes.Type(value = NonPublishedDataRuleSet.class, name = "non_published_data_rule_set"),
        @JsonSubTypes.Type(value = Notebook.class, name = "notebook"),
        @JsonSubTypes.Type(value = ObjectType.class, name = "object_type"),
        @JsonSubTypes.Type(value = OutParameter.class, name = "out_parameter"),
        @JsonSubTypes.Type(value = OutputValue.class, name = "output_value"),
        @JsonSubTypes.Type(value = ParameterSet.class, name = "parameter_set"),
        @JsonSubTypes.Type(value = PhysicalDataModel.class, name = "physical_data_model"),
        @JsonSubTypes.Type(value = PhysicalObject.class, name = "physical_object"),
        @JsonSubTypes.Type(value = PhysicalObjectAttribute.class, name = "physical_object_attribute"),
        @JsonSubTypes.Type(value = PublishedDataRuleDefinition.class, name = "published_data_rule_definition"),
        @JsonSubTypes.Type(value = PublishedDataRuleSet.class, name = "published_data_rule_set"),
        @JsonSubTypes.Type(value = RShinyApp.class, name = "r_shiny_app"),
        @JsonSubTypes.Type(value = RShinyAppObject.class, name = "r_shiny_app_object"),
        @JsonSubTypes.Type(value = Reportobject.class, name = "reportobject"),
        @JsonSubTypes.Type(value = ResultColumn.class, name = "result_column"),
        @JsonSubTypes.Type(value = Routine.class, name = "routine"),
        @JsonSubTypes.Type(value = SharedContainer.class, name = "shared_container"),
        @JsonSubTypes.Type(value = Stage.class, name = "stage"),
        @JsonSubTypes.Type(value = StageColumn.class, name = "stage_column"),
        @JsonSubTypes.Type(value = StageType.class, name = "stage_type"),
        @JsonSubTypes.Type(value = StageVariable.class, name = "stage_variable"),
        @JsonSubTypes.Type(value = StandardizationRuleSet.class, name = "standardization_rule_set"),
        @JsonSubTypes.Type(value = StoredProcedureDefinition.class, name = "stored_procedure_definition"),
        @JsonSubTypes.Type(value = StoredProcedureParameter.class, name = "stored_procedure_parameter"),
        @JsonSubTypes.Type(value = SubjectArea.class, name = "subject_area"),
        @JsonSubTypes.Type(value = Term.class, name = "term"),
        @JsonSubTypes.Type(value = TransformationProject.class, name = "transformation_project"),
        @JsonSubTypes.Type(value = TupleAttribute.class, name = "tuple_attribute"),
        @JsonSubTypes.Type(value = Volume.class, name = "volume"),
        @JsonSubTypes.Type(value = WarehouseMappingDocument.class, name = "warehouse_mapping_document"),
        @JsonSubTypes.Type(value = XmlSchemaDefinition.class, name = "xml_schema_definition"),
        @JsonSubTypes.Type(value = XmlSchemaLibrary.class, name = "xml_schema_library"),
        @JsonSubTypes.Type(value = XsdAttribute.class, name = "xsd_attribute"),
        @JsonSubTypes.Type(value = XsdAttributeGroup.class, name = "xsd_attribute_group"),
        @JsonSubTypes.Type(value = XsdChoice.class, name = "xsd_choice"),
        @JsonSubTypes.Type(value = XsdComplexType.class, name = "xsd_complex_type"),
        @JsonSubTypes.Type(value = XsdElement.class, name = "xsd_element"),
        @JsonSubTypes.Type(value = XsdElementGroup.class, name = "xsd_element_group"),
        @JsonSubTypes.Type(value = XsdSequence.class, name = "xsd_sequence"),
        @JsonSubTypes.Type(value = XsdSimpleType.class, name = "xsd_simple_type"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("information_asset")
public class InformationAsset extends MainObject {

    @JsonProperty("read_by_(design)")
    protected ItemList<InformationAsset> readByDesign;

    @JsonProperty("read_by_(operational)")
    protected ItemList<InformationAsset> readByOperational;

    @JsonProperty("read_by_(static)")
    protected ItemList<InformationAsset> readByStatic;

    @JsonProperty("read_by_(user_defined)")
    protected ItemList<InformationAsset> readByUserDefined;

    @JsonProperty("written_by_(design)")
    protected ItemList<InformationAsset> writtenByDesign;

    @JsonProperty("written_by_(operational)")
    protected ItemList<InformationAsset> writtenByOperational;

    @JsonProperty("written_by_(static)")
    protected ItemList<InformationAsset> writtenByStatic;

    @JsonProperty("written_by_(user_defined)")
    protected ItemList<InformationAsset> writtenByUserDefined;

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
