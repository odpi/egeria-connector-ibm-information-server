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
 * POJO for the {@code job_stage_record} asset type in IGC, displayed as '{@literal Job Stage Record}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=JobStageRecord.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_stage_record")
public class JobStageRecord extends MainObject {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("has_ds_flow_variable")
    protected ItemList<DataItem> hasDsFlowVariable;

    @JsonProperty("internal_id")
    protected String internalId;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("of_ds_stage")
    protected Stage ofDsStage;

    @JsonProperty("other_records_initialization_flag")
    protected Number otherRecordsInitializationFlag;

    @JsonProperty("record_id_name")
    protected String recordIdName;

    @JsonProperty("record_id_name_value_relation")
    protected String recordIdNameValueRelation;

    @JsonProperty("record_id_value")
    protected String recordIdValue;

    @JsonProperty("record_name")
    protected String recordName;

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
     * Retrieve the {@code has_ds_flow_variable} property (displayed as '{@literal Has DS Flow Variable}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("has_ds_flow_variable")
    public ItemList<DataItem> getHasDsFlowVariable() { return this.hasDsFlowVariable; }

    /**
     * Set the {@code has_ds_flow_variable} property (displayed as {@code Has DS Flow Variable}) of the object.
     * @param hasDsFlowVariable the value to set
     */
    @JsonProperty("has_ds_flow_variable")
    public void setHasDsFlowVariable(ItemList<DataItem> hasDsFlowVariable) { this.hasDsFlowVariable = hasDsFlowVariable; }

    /**
     * Retrieve the {@code internal_id} property (displayed as '{@literal Internal ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("internal_id")
    public String getInternalId() { return this.internalId; }

    /**
     * Set the {@code internal_id} property (displayed as {@code Internal ID}) of the object.
     * @param internalId the value to set
     */
    @JsonProperty("internal_id")
    public void setInternalId(String internalId) { this.internalId = internalId; }

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
     * Retrieve the {@code other_records_initialization_flag} property (displayed as '{@literal Other Records Initialization Flag}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("other_records_initialization_flag")
    public Number getOtherRecordsInitializationFlag() { return this.otherRecordsInitializationFlag; }

    /**
     * Set the {@code other_records_initialization_flag} property (displayed as {@code Other Records Initialization Flag}) of the object.
     * @param otherRecordsInitializationFlag the value to set
     */
    @JsonProperty("other_records_initialization_flag")
    public void setOtherRecordsInitializationFlag(Number otherRecordsInitializationFlag) { this.otherRecordsInitializationFlag = otherRecordsInitializationFlag; }

    /**
     * Retrieve the {@code record_id_name} property (displayed as '{@literal Record ID Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("record_id_name")
    public String getRecordIdName() { return this.recordIdName; }

    /**
     * Set the {@code record_id_name} property (displayed as {@code Record ID Name}) of the object.
     * @param recordIdName the value to set
     */
    @JsonProperty("record_id_name")
    public void setRecordIdName(String recordIdName) { this.recordIdName = recordIdName; }

    /**
     * Retrieve the {@code record_id_name_value_relation} property (displayed as '{@literal Record ID Name Value Relation}') of the object.
     * @return {@code String}
     */
    @JsonProperty("record_id_name_value_relation")
    public String getRecordIdNameValueRelation() { return this.recordIdNameValueRelation; }

    /**
     * Set the {@code record_id_name_value_relation} property (displayed as {@code Record ID Name Value Relation}) of the object.
     * @param recordIdNameValueRelation the value to set
     */
    @JsonProperty("record_id_name_value_relation")
    public void setRecordIdNameValueRelation(String recordIdNameValueRelation) { this.recordIdNameValueRelation = recordIdNameValueRelation; }

    /**
     * Retrieve the {@code record_id_value} property (displayed as '{@literal Record ID Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("record_id_value")
    public String getRecordIdValue() { return this.recordIdValue; }

    /**
     * Set the {@code record_id_value} property (displayed as {@code Record ID Value}) of the object.
     * @param recordIdValue the value to set
     */
    @JsonProperty("record_id_value")
    public void setRecordIdValue(String recordIdValue) { this.recordIdValue = recordIdValue; }

    /**
     * Retrieve the {@code record_name} property (displayed as '{@literal Record Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("record_name")
    public String getRecordName() { return this.recordName; }

    /**
     * Set the {@code record_name} property (displayed as {@code Record Name}) of the object.
     * @param recordName the value to set
     */
    @JsonProperty("record_name")
    public void setRecordName(String recordName) { this.recordName = recordName; }

}
