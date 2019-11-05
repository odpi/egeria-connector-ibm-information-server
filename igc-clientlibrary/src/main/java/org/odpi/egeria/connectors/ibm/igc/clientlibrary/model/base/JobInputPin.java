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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code job_input_pin} asset type in IGC, displayed as '{@literal Job Input Pin}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_input_pin")
public class JobInputPin extends Reference {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("condition_not_met")
    protected String conditionNotMet;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("enable_tx_group")
    protected Number enableTxGroup;

    @JsonProperty("has_ds_meta_bag")
    protected Dsmetabag hasDsMetaBag;

    @JsonProperty("has_dsmf_column_info")
    protected ItemList<Dsmfcolumninfo> hasDsmfColumnInfo;

    @JsonProperty("internal_id")
    protected String internalId;

    @JsonProperty("is_target_of_link")
    protected MainObject isTargetOfLink;

    @JsonProperty("link_type")
    protected Number linkType;

    @JsonProperty("lookup_fail")
    protected String lookupFail;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("of_job_component")
    protected MainObject ofJobComponent;

    @JsonProperty("partner")
    protected String partner;

    @JsonProperty("pin_type")
    protected String pinType;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("transaction_size")
    protected Number transactionSize;

    @JsonProperty("txn_behaviour")
    protected Number txnBehaviour;

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
     * Retrieve the {@code condition_not_met} property (displayed as '{@literal Condition Not Met}') of the object.
     * @return {@code String}
     */
    @JsonProperty("condition_not_met")
    public String getConditionNotMet() { return this.conditionNotMet; }

    /**
     * Set the {@code condition_not_met} property (displayed as {@code Condition Not Met}) of the object.
     * @param conditionNotMet the value to set
     */
    @JsonProperty("condition_not_met")
    public void setConditionNotMet(String conditionNotMet) { this.conditionNotMet = conditionNotMet; }

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
     * Retrieve the {@code enable_tx_group} property (displayed as '{@literal Enable Tx Group}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("enable_tx_group")
    public Number getEnableTxGroup() { return this.enableTxGroup; }

    /**
     * Set the {@code enable_tx_group} property (displayed as {@code Enable Tx Group}) of the object.
     * @param enableTxGroup the value to set
     */
    @JsonProperty("enable_tx_group")
    public void setEnableTxGroup(Number enableTxGroup) { this.enableTxGroup = enableTxGroup; }

    /**
     * Retrieve the {@code has_ds_meta_bag} property (displayed as '{@literal Has DS Meta Bag}') of the object.
     * @return {@code Dsmetabag}
     */
    @JsonProperty("has_ds_meta_bag")
    public Dsmetabag getHasDsMetaBag() { return this.hasDsMetaBag; }

    /**
     * Set the {@code has_ds_meta_bag} property (displayed as {@code Has DS Meta Bag}) of the object.
     * @param hasDsMetaBag the value to set
     */
    @JsonProperty("has_ds_meta_bag")
    public void setHasDsMetaBag(Dsmetabag hasDsMetaBag) { this.hasDsMetaBag = hasDsMetaBag; }

    /**
     * Retrieve the {@code has_dsmf_column_info} property (displayed as '{@literal Has DSMF Column Info}') of the object.
     * @return {@code ItemList<Dsmfcolumninfo>}
     */
    @JsonProperty("has_dsmf_column_info")
    public ItemList<Dsmfcolumninfo> getHasDsmfColumnInfo() { return this.hasDsmfColumnInfo; }

    /**
     * Set the {@code has_dsmf_column_info} property (displayed as {@code Has DSMF Column Info}) of the object.
     * @param hasDsmfColumnInfo the value to set
     */
    @JsonProperty("has_dsmf_column_info")
    public void setHasDsmfColumnInfo(ItemList<Dsmfcolumninfo> hasDsmfColumnInfo) { this.hasDsmfColumnInfo = hasDsmfColumnInfo; }

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
     * Retrieve the {@code is_target_of_link} property (displayed as '{@literal Is Target Of Link}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("is_target_of_link")
    public MainObject getIsTargetOfLink() { return this.isTargetOfLink; }

    /**
     * Set the {@code is_target_of_link} property (displayed as {@code Is Target Of Link}) of the object.
     * @param isTargetOfLink the value to set
     */
    @JsonProperty("is_target_of_link")
    public void setIsTargetOfLink(MainObject isTargetOfLink) { this.isTargetOfLink = isTargetOfLink; }

    /**
     * Retrieve the {@code link_type} property (displayed as '{@literal Link Type}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("link_type")
    public Number getLinkType() { return this.linkType; }

    /**
     * Set the {@code link_type} property (displayed as {@code Link Type}) of the object.
     * @param linkType the value to set
     */
    @JsonProperty("link_type")
    public void setLinkType(Number linkType) { this.linkType = linkType; }

    /**
     * Retrieve the {@code lookup_fail} property (displayed as '{@literal Lookup Fail}') of the object.
     * @return {@code String}
     */
    @JsonProperty("lookup_fail")
    public String getLookupFail() { return this.lookupFail; }

    /**
     * Set the {@code lookup_fail} property (displayed as {@code Lookup Fail}) of the object.
     * @param lookupFail the value to set
     */
    @JsonProperty("lookup_fail")
    public void setLookupFail(String lookupFail) { this.lookupFail = lookupFail; }

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
     * Retrieve the {@code of_job_component} property (displayed as '{@literal Of Job Component}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("of_job_component")
    public MainObject getOfJobComponent() { return this.ofJobComponent; }

    /**
     * Set the {@code of_job_component} property (displayed as {@code Of Job Component}) of the object.
     * @param ofJobComponent the value to set
     */
    @JsonProperty("of_job_component")
    public void setOfJobComponent(MainObject ofJobComponent) { this.ofJobComponent = ofJobComponent; }

    /**
     * Retrieve the {@code partner} property (displayed as '{@literal Partner}') of the object.
     * @return {@code String}
     */
    @JsonProperty("partner")
    public String getPartner() { return this.partner; }

    /**
     * Set the {@code partner} property (displayed as {@code Partner}) of the object.
     * @param partner the value to set
     */
    @JsonProperty("partner")
    public void setPartner(String partner) { this.partner = partner; }

    /**
     * Retrieve the {@code pin_type} property (displayed as '{@literal Pin Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("pin_type")
    public String getPinType() { return this.pinType; }

    /**
     * Set the {@code pin_type} property (displayed as {@code Pin Type}) of the object.
     * @param pinType the value to set
     */
    @JsonProperty("pin_type")
    public void setPinType(String pinType) { this.pinType = pinType; }

    /**
     * Retrieve the {@code sequence} property (displayed as '{@literal Sequence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sequence")
    public Number getSequence() { return this.sequence; }

    /**
     * Set the {@code sequence} property (displayed as {@code Sequence}) of the object.
     * @param sequence the value to set
     */
    @JsonProperty("sequence")
    public void setSequence(Number sequence) { this.sequence = sequence; }

    /**
     * Retrieve the {@code transaction_size} property (displayed as '{@literal Transaction Size}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("transaction_size")
    public Number getTransactionSize() { return this.transactionSize; }

    /**
     * Set the {@code transaction_size} property (displayed as {@code Transaction Size}) of the object.
     * @param transactionSize the value to set
     */
    @JsonProperty("transaction_size")
    public void setTransactionSize(Number transactionSize) { this.transactionSize = transactionSize; }

    /**
     * Retrieve the {@code txn_behaviour} property (displayed as '{@literal TXN Behaviour}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("txn_behaviour")
    public Number getTxnBehaviour() { return this.txnBehaviour; }

    /**
     * Set the {@code txn_behaviour} property (displayed as {@code TXN Behaviour}) of the object.
     * @param txnBehaviour the value to set
     */
    @JsonProperty("txn_behaviour")
    public void setTxnBehaviour(Number txnBehaviour) { this.txnBehaviour = txnBehaviour; }

}
