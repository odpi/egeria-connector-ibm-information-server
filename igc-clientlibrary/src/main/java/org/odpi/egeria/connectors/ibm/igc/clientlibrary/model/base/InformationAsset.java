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
import java.util.Date;

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
        @JsonSubTypes.Type(value = Application.class, name = "application"),
        @JsonSubTypes.Type(value = Attribute.class, name = "attribute"),
        @JsonSubTypes.Type(value = AttributeType.class, name = "attribute_type"),
        @JsonSubTypes.Type(value = AttributeTypeField.class, name = "attribute_type_field"),
        @JsonSubTypes.Type(value = BiCollection.class, name = "bi_collection"),
        @JsonSubTypes.Type(value = BiCollectionMember.class, name = "bi_collection_member"),
        @JsonSubTypes.Type(value = BiCube.class, name = "bi_cube"),
        @JsonSubTypes.Type(value = BiModel.class, name = "bi_model"),
        @JsonSubTypes.Type(value = BiReport.class, name = "bi_report"),
        @JsonSubTypes.Type(value = BiReportQuery.class, name = "bi_report_query"),
        @JsonSubTypes.Type(value = BiReportQueryDetailFilter.class, name = "bi_report_query_detail_filter"),
        @JsonSubTypes.Type(value = BiReportQuerySummaryFilter.class, name = "bi_report_query_summary_filter"),
        @JsonSubTypes.Type(value = BiReportQueryUsage.class, name = "bi_report_query_usage"),
        @JsonSubTypes.Type(value = BiServer.class, name = "bi_server"),
        @JsonSubTypes.Type(value = Blueprint.class, name = "blueprint"),
        @JsonSubTypes.Type(value = Category.class, name = "category"),
        @JsonSubTypes.Type(value = CdcMappingDocument.class, name = "cdc_mapping_document"),
        @JsonSubTypes.Type(value = CompositeView.class, name = "composite_view"),
        @JsonSubTypes.Type(value = DataClass.class, name = "data_class"),
        @JsonSubTypes.Type(value = DataFile.class, name = "data_file"),
        @JsonSubTypes.Type(value = DataFileDefinition.class, name = "data_file_definition"),
        @JsonSubTypes.Type(value = DataFileDefinitionField.class, name = "data_file_definition_field"),
        @JsonSubTypes.Type(value = DataFileDefinitionRecord.class, name = "data_file_definition_record"),
        @JsonSubTypes.Type(value = DataFileFolder.class, name = "data_file_folder"),
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
        @JsonSubTypes.Type(value = DsstageType.class, name = "dsstage_type"),
        @JsonSubTypes.Type(value = Endpoint.class, name = "endpoint"),
        @JsonSubTypes.Type(value = EntityAttribute.class, name = "entity_attribute"),
        @JsonSubTypes.Type(value = EntityType.class, name = "entity_type"),
        @JsonSubTypes.Type(value = ExtensionMappingDocument.class, name = "extension_mapping_document"),
        @JsonSubTypes.Type(value = File.class, name = "file"),
        @JsonSubTypes.Type(value = Host.class, name = "host"),
        @JsonSubTypes.Type(value = IdocField.class, name = "idoc_field"),
        @JsonSubTypes.Type(value = IdocSegmentType.class, name = "idoc_segment_type"),
        @JsonSubTypes.Type(value = IdocType.class, name = "idoc_type"),
        @JsonSubTypes.Type(value = InParameter.class, name = "in_parameter"),
        @JsonSubTypes.Type(value = InformationGovernancePolicy.class, name = "information_governance_policy"),
        @JsonSubTypes.Type(value = InformationGovernanceRule.class, name = "information_governance_rule"),
        @JsonSubTypes.Type(value = InformationServicesApplication.class, name = "information_services_application"),
        @JsonSubTypes.Type(value = InformationServicesProject.class, name = "information_services_project"),
        @JsonSubTypes.Type(value = InoutParameter.class, name = "inout_parameter"),
        @JsonSubTypes.Type(value = InputParameter.class, name = "input_parameter"),
        @JsonSubTypes.Type(value = LogicalDataModel.class, name = "logical_data_model"),
        @JsonSubTypes.Type(value = LogicalEntity.class, name = "logical_entity"),
        @JsonSubTypes.Type(value = MachineProfile.class, name = "machine_profile"),
        @JsonSubTypes.Type(value = MappingProject.class, name = "mapping_project"),
        @JsonSubTypes.Type(value = MappingSpecification.class, name = "mapping_specification"),
        @JsonSubTypes.Type(value = MatchSpecification.class, name = "match_specification"),
        @JsonSubTypes.Type(value = MdmModel.class, name = "mdm_model"),
        @JsonSubTypes.Type(value = MemberType.class, name = "member_type"),
        @JsonSubTypes.Type(value = Method.class, name = "method"),
        @JsonSubTypes.Type(value = Metric.class, name = "metric"),
        @JsonSubTypes.Type(value = NonPublishedDataRuleDefinition.class, name = "non_published_data_rule_definition"),
        @JsonSubTypes.Type(value = NonPublishedDataRuleSet.class, name = "non_published_data_rule_set"),
        @JsonSubTypes.Type(value = ObjectType.class, name = "object_type"),
        @JsonSubTypes.Type(value = OutParameter.class, name = "out_parameter"),
        @JsonSubTypes.Type(value = OutputValue.class, name = "output_value"),
        @JsonSubTypes.Type(value = ParameterSet.class, name = "parameter_set"),
        @JsonSubTypes.Type(value = PhysicalDataModel.class, name = "physical_data_model"),
        @JsonSubTypes.Type(value = PhysicalObject.class, name = "physical_object"),
        @JsonSubTypes.Type(value = PhysicalObjectAttribute.class, name = "physical_object_attribute"),
        @JsonSubTypes.Type(value = PublishedDataRuleDefinition.class, name = "published_data_rule_definition"),
        @JsonSubTypes.Type(value = PublishedDataRuleSet.class, name = "published_data_rule_set"),
        @JsonSubTypes.Type(value = Reportobject.class, name = "reportobject"),
        @JsonSubTypes.Type(value = ResultColumn.class, name = "result_column"),
        @JsonSubTypes.Type(value = Routine.class, name = "routine"),
        @JsonSubTypes.Type(value = SequenceJob.class, name = "sequence_job"),
        @JsonSubTypes.Type(value = SharedContainer.class, name = "shared_container"),
        @JsonSubTypes.Type(value = Stage.class, name = "stage"),
        @JsonSubTypes.Type(value = StageColumn.class, name = "stage_column"),
        @JsonSubTypes.Type(value = StageVariable.class, name = "stage_variable"),
        @JsonSubTypes.Type(value = StandardizationRuleSet.class, name = "standardization_rule_set"),
        @JsonSubTypes.Type(value = StoredProcedureDefinition.class, name = "stored_procedure_definition"),
        @JsonSubTypes.Type(value = StoredProcedureParameter.class, name = "stored_procedure_parameter"),
        @JsonSubTypes.Type(value = SubjectArea.class, name = "subject_area"),
        @JsonSubTypes.Type(value = Term.class, name = "term"),
        @JsonSubTypes.Type(value = TransformationProject.class, name = "transformation_project"),
        @JsonSubTypes.Type(value = TupleAttribute.class, name = "tuple_attribute"),
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

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

}
