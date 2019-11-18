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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code job_output_pin} asset type in IGC, displayed as '{@literal Job Output Pin}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=JobOutputPin.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_output_pin")
public class JobOutputPin extends Reference {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("has_ds_argument_map")
    protected ItemList<Dsargumentmap> hasDsArgumentMap;

    @JsonProperty("has_ds_meta_bag")
    protected Dsmetabag hasDsMetaBag;

    @JsonProperty("internal_id")
    protected String internalId;

    @JsonProperty("is_source_of_link")
    protected MainObject isSourceOfLink;

    @JsonProperty("left_text_pos")
    protected Number leftTextPos;

    @JsonProperty("of_job_component")
    protected MainObject ofJobComponent;

    @JsonProperty("partner")
    protected String partner;

    @JsonProperty("pin_type")
    protected String pinType;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("top_text_pos")
    protected Number topTextPos;

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
     * Retrieve the {@code has_ds_argument_map} property (displayed as '{@literal Has DS Argument Map}') of the object.
     * @return {@code ItemList<Dsargumentmap>}
     */
    @JsonProperty("has_ds_argument_map")
    public ItemList<Dsargumentmap> getHasDsArgumentMap() { return this.hasDsArgumentMap; }

    /**
     * Set the {@code has_ds_argument_map} property (displayed as {@code Has DS Argument Map}) of the object.
     * @param hasDsArgumentMap the value to set
     */
    @JsonProperty("has_ds_argument_map")
    public void setHasDsArgumentMap(ItemList<Dsargumentmap> hasDsArgumentMap) { this.hasDsArgumentMap = hasDsArgumentMap; }

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
     * Retrieve the {@code is_source_of_link} property (displayed as '{@literal Is Source Of Link}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("is_source_of_link")
    public MainObject getIsSourceOfLink() { return this.isSourceOfLink; }

    /**
     * Set the {@code is_source_of_link} property (displayed as {@code Is Source Of Link}) of the object.
     * @param isSourceOfLink the value to set
     */
    @JsonProperty("is_source_of_link")
    public void setIsSourceOfLink(MainObject isSourceOfLink) { this.isSourceOfLink = isSourceOfLink; }

    /**
     * Retrieve the {@code left_text_pos} property (displayed as '{@literal Left Text Pos}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("left_text_pos")
    public Number getLeftTextPos() { return this.leftTextPos; }

    /**
     * Set the {@code left_text_pos} property (displayed as {@code Left Text Pos}) of the object.
     * @param leftTextPos the value to set
     */
    @JsonProperty("left_text_pos")
    public void setLeftTextPos(Number leftTextPos) { this.leftTextPos = leftTextPos; }

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
     * Retrieve the {@code top_text_pos} property (displayed as '{@literal Top Text Pos}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("top_text_pos")
    public Number getTopTextPos() { return this.topTextPos; }

    /**
     * Set the {@code top_text_pos} property (displayed as {@code Top Text Pos}) of the object.
     * @param topTextPos the value to set
     */
    @JsonProperty("top_text_pos")
    public void setTopTextPos(Number topTextPos) { this.topTextPos = topTextPos; }

}
