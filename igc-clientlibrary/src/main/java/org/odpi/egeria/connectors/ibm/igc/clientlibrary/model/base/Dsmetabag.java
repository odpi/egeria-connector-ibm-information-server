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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import java.util.Date;

/**
 * POJO for the {@code dsmetabag} asset type in IGC, displayed as '{@literal DSMetaBag}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsmetabag")
public class Dsmetabag extends Reference {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("conditions")
    protected String conditions;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("names")
    protected String names;

    @JsonProperty("of_ds_data_connection")
    protected DsdataConnection ofDsDataConnection;

    @JsonProperty("of_ds_data_element")
    protected DataElement ofDsDataElement;

    @JsonProperty("of_ds_data_quality_spec")
    protected StandardizationObject ofDsDataQualitySpec;

    @JsonProperty("of_ds_design_view")
    protected DsdesignView ofDsDesignView;

    @JsonProperty("of_ds_input_pin")
    protected JobInputPin ofDsInputPin;

    @JsonProperty("of_ds_job_def")
    protected Dsjob ofDsJobDef;

    @JsonProperty("of_ds_output_pin")
    protected JobOutputPin ofDsOutputPin;

    @JsonProperty("of_ds_routine")
    protected Routine ofDsRoutine;

    @JsonProperty("of_ds_stage")
    protected Stage ofDsStage;

    @JsonProperty("of_ds_stage_type")
    protected DsstageType ofDsStageType;

    @JsonProperty("of_ds_table_definition")
    protected TableDefinition ofDsTableDefinition;

    @JsonProperty("of_ds_transform")
    protected TransformsFunction ofDsTransform;

    @JsonProperty("owners")
    protected String owners;

    @JsonProperty("values")
    protected String values;

    /**
     * Retrieve the {@code a_xmeta_locking_root} property (displayed as '{@literal A XMeta Locking Root}') of the object.
     * @return {@code String}
     */
    @JsonProperty("a_xmeta_locking_root")
    public String getAXmetaLockingRoot() { return this.aXmetaLockingRoot; }

    /**
     * Set the {@code a_xmeta_locking_root} property (displayed as {@code A XMeta Locking Root}) of the object.
     * @param aXmetaLockingRoot the value to set
     */
    @JsonProperty("a_xmeta_locking_root")
    public void setAXmetaLockingRoot(String aXmetaLockingRoot) { this.aXmetaLockingRoot = aXmetaLockingRoot; }

    /**
     * Retrieve the {@code conditions} property (displayed as '{@literal Conditions}') of the object.
     * @return {@code String}
     */
    @JsonProperty("conditions")
    public String getConditions() { return this.conditions; }

    /**
     * Set the {@code conditions} property (displayed as {@code Conditions}) of the object.
     * @param conditions the value to set
     */
    @JsonProperty("conditions")
    public void setConditions(String conditions) { this.conditions = conditions; }

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

    /**
     * Retrieve the {@code names} property (displayed as '{@literal Names}') of the object.
     * @return {@code String}
     */
    @JsonProperty("names")
    public String getNames() { return this.names; }

    /**
     * Set the {@code names} property (displayed as {@code Names}) of the object.
     * @param names the value to set
     */
    @JsonProperty("names")
    public void setNames(String names) { this.names = names; }

    /**
     * Retrieve the {@code of_ds_data_connection} property (displayed as '{@literal Of DS Data Connection}') of the object.
     * @return {@code DsdataConnection}
     */
    @JsonProperty("of_ds_data_connection")
    public DsdataConnection getOfDsDataConnection() { return this.ofDsDataConnection; }

    /**
     * Set the {@code of_ds_data_connection} property (displayed as {@code Of DS Data Connection}) of the object.
     * @param ofDsDataConnection the value to set
     */
    @JsonProperty("of_ds_data_connection")
    public void setOfDsDataConnection(DsdataConnection ofDsDataConnection) { this.ofDsDataConnection = ofDsDataConnection; }

    /**
     * Retrieve the {@code of_ds_data_element} property (displayed as '{@literal Of DS Data Element}') of the object.
     * @return {@code DataElement}
     */
    @JsonProperty("of_ds_data_element")
    public DataElement getOfDsDataElement() { return this.ofDsDataElement; }

    /**
     * Set the {@code of_ds_data_element} property (displayed as {@code Of DS Data Element}) of the object.
     * @param ofDsDataElement the value to set
     */
    @JsonProperty("of_ds_data_element")
    public void setOfDsDataElement(DataElement ofDsDataElement) { this.ofDsDataElement = ofDsDataElement; }

    /**
     * Retrieve the {@code of_ds_data_quality_spec} property (displayed as '{@literal Of DS Data Quality Spec}') of the object.
     * @return {@code StandardizationObject}
     */
    @JsonProperty("of_ds_data_quality_spec")
    public StandardizationObject getOfDsDataQualitySpec() { return this.ofDsDataQualitySpec; }

    /**
     * Set the {@code of_ds_data_quality_spec} property (displayed as {@code Of DS Data Quality Spec}) of the object.
     * @param ofDsDataQualitySpec the value to set
     */
    @JsonProperty("of_ds_data_quality_spec")
    public void setOfDsDataQualitySpec(StandardizationObject ofDsDataQualitySpec) { this.ofDsDataQualitySpec = ofDsDataQualitySpec; }

    /**
     * Retrieve the {@code of_ds_design_view} property (displayed as '{@literal Of DS Design View}') of the object.
     * @return {@code DsdesignView}
     */
    @JsonProperty("of_ds_design_view")
    public DsdesignView getOfDsDesignView() { return this.ofDsDesignView; }

    /**
     * Set the {@code of_ds_design_view} property (displayed as {@code Of DS Design View}) of the object.
     * @param ofDsDesignView the value to set
     */
    @JsonProperty("of_ds_design_view")
    public void setOfDsDesignView(DsdesignView ofDsDesignView) { this.ofDsDesignView = ofDsDesignView; }

    /**
     * Retrieve the {@code of_ds_input_pin} property (displayed as '{@literal Of DS Input Pin}') of the object.
     * @return {@code JobInputPin}
     */
    @JsonProperty("of_ds_input_pin")
    public JobInputPin getOfDsInputPin() { return this.ofDsInputPin; }

    /**
     * Set the {@code of_ds_input_pin} property (displayed as {@code Of DS Input Pin}) of the object.
     * @param ofDsInputPin the value to set
     */
    @JsonProperty("of_ds_input_pin")
    public void setOfDsInputPin(JobInputPin ofDsInputPin) { this.ofDsInputPin = ofDsInputPin; }

    /**
     * Retrieve the {@code of_ds_job_def} property (displayed as '{@literal Of DS Job Def}') of the object.
     * @return {@code Dsjob}
     */
    @JsonProperty("of_ds_job_def")
    public Dsjob getOfDsJobDef() { return this.ofDsJobDef; }

    /**
     * Set the {@code of_ds_job_def} property (displayed as {@code Of DS Job Def}) of the object.
     * @param ofDsJobDef the value to set
     */
    @JsonProperty("of_ds_job_def")
    public void setOfDsJobDef(Dsjob ofDsJobDef) { this.ofDsJobDef = ofDsJobDef; }

    /**
     * Retrieve the {@code of_ds_output_pin} property (displayed as '{@literal Of DS Output Pin}') of the object.
     * @return {@code JobOutputPin}
     */
    @JsonProperty("of_ds_output_pin")
    public JobOutputPin getOfDsOutputPin() { return this.ofDsOutputPin; }

    /**
     * Set the {@code of_ds_output_pin} property (displayed as {@code Of DS Output Pin}) of the object.
     * @param ofDsOutputPin the value to set
     */
    @JsonProperty("of_ds_output_pin")
    public void setOfDsOutputPin(JobOutputPin ofDsOutputPin) { this.ofDsOutputPin = ofDsOutputPin; }

    /**
     * Retrieve the {@code of_ds_routine} property (displayed as '{@literal Of DS Routine}') of the object.
     * @return {@code Routine}
     */
    @JsonProperty("of_ds_routine")
    public Routine getOfDsRoutine() { return this.ofDsRoutine; }

    /**
     * Set the {@code of_ds_routine} property (displayed as {@code Of DS Routine}) of the object.
     * @param ofDsRoutine the value to set
     */
    @JsonProperty("of_ds_routine")
    public void setOfDsRoutine(Routine ofDsRoutine) { this.ofDsRoutine = ofDsRoutine; }

    /**
     * Retrieve the {@code of_ds_stage} property (displayed as '{@literal Of DS Stage}') of the object.
     * @return {@code Stage}
     */
    @JsonProperty("of_ds_stage")
    public Stage getOfDsStage() { return this.ofDsStage; }

    /**
     * Set the {@code of_ds_stage} property (displayed as {@code Of DS Stage}) of the object.
     * @param ofDsStage the value to set
     */
    @JsonProperty("of_ds_stage")
    public void setOfDsStage(Stage ofDsStage) { this.ofDsStage = ofDsStage; }

    /**
     * Retrieve the {@code of_ds_stage_type} property (displayed as '{@literal Of DS Stage Type}') of the object.
     * @return {@code DsstageType}
     */
    @JsonProperty("of_ds_stage_type")
    public DsstageType getOfDsStageType() { return this.ofDsStageType; }

    /**
     * Set the {@code of_ds_stage_type} property (displayed as {@code Of DS Stage Type}) of the object.
     * @param ofDsStageType the value to set
     */
    @JsonProperty("of_ds_stage_type")
    public void setOfDsStageType(DsstageType ofDsStageType) { this.ofDsStageType = ofDsStageType; }

    /**
     * Retrieve the {@code of_ds_table_definition} property (displayed as '{@literal Of DS Table Definition}') of the object.
     * @return {@code TableDefinition}
     */
    @JsonProperty("of_ds_table_definition")
    public TableDefinition getOfDsTableDefinition() { return this.ofDsTableDefinition; }

    /**
     * Set the {@code of_ds_table_definition} property (displayed as {@code Of DS Table Definition}) of the object.
     * @param ofDsTableDefinition the value to set
     */
    @JsonProperty("of_ds_table_definition")
    public void setOfDsTableDefinition(TableDefinition ofDsTableDefinition) { this.ofDsTableDefinition = ofDsTableDefinition; }

    /**
     * Retrieve the {@code of_ds_transform} property (displayed as '{@literal Of DS Transform}') of the object.
     * @return {@code TransformsFunction}
     */
    @JsonProperty("of_ds_transform")
    public TransformsFunction getOfDsTransform() { return this.ofDsTransform; }

    /**
     * Set the {@code of_ds_transform} property (displayed as {@code Of DS Transform}) of the object.
     * @param ofDsTransform the value to set
     */
    @JsonProperty("of_ds_transform")
    public void setOfDsTransform(TransformsFunction ofDsTransform) { this.ofDsTransform = ofDsTransform; }

    /**
     * Retrieve the {@code owners} property (displayed as '{@literal Owners}') of the object.
     * @return {@code String}
     */
    @JsonProperty("owners")
    public String getOwners() { return this.owners; }

    /**
     * Set the {@code owners} property (displayed as {@code Owners}) of the object.
     * @param owners the value to set
     */
    @JsonProperty("owners")
    public void setOwners(String owners) { this.owners = owners; }

    /**
     * Retrieve the {@code values} property (displayed as '{@literal Values}') of the object.
     * @return {@code String}
     */
    @JsonProperty("values")
    public String getValues() { return this.values; }

    /**
     * Set the {@code values} property (displayed as {@code Values}) of the object.
     * @param values the value to set
     */
    @JsonProperty("values")
    public void setValues(String values) { this.values = values; }

}
