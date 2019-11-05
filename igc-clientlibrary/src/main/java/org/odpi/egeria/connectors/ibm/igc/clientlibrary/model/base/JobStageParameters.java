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
 * POJO for the {@code job_stage_parameters} asset type in IGC, displayed as '{@literal Job Stage Parameters}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_stage_parameters")
public class JobStageParameters extends Reference {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("conv_type")
    protected Number convType;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("display_style")
    protected Number displayStyle;

    @JsonProperty("for_input")
    protected Number forInput;

    @JsonProperty("for_output")
    protected Number forOutput;

    @JsonProperty("for_stage")
    protected Number forStage;

    @JsonProperty("hidden")
    protected Boolean hidden;

    @JsonProperty("locked")
    protected Boolean locked;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("of_ds_parameter_def")
    protected Parameter ofDsParameterDef;

    @JsonProperty("quote_string")
    protected Number quoteString;

    @JsonProperty("transaction_groupable")
    protected Number transactionGroupable;

    @JsonProperty("view_data")
    protected Number viewData;

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
     * Retrieve the {@code conv_type} property (displayed as '{@literal Conv Type}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("conv_type")
    public Number getConvType() { return this.convType; }

    /**
     * Set the {@code conv_type} property (displayed as {@code Conv Type}) of the object.
     * @param convType the value to set
     */
    @JsonProperty("conv_type")
    public void setConvType(Number convType) { this.convType = convType; }

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
     * Retrieve the {@code display_style} property (displayed as '{@literal Display Style}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("display_style")
    public Number getDisplayStyle() { return this.displayStyle; }

    /**
     * Set the {@code display_style} property (displayed as {@code Display Style}) of the object.
     * @param displayStyle the value to set
     */
    @JsonProperty("display_style")
    public void setDisplayStyle(Number displayStyle) { this.displayStyle = displayStyle; }

    /**
     * Retrieve the {@code for_input} property (displayed as '{@literal For Input}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("for_input")
    public Number getForInput() { return this.forInput; }

    /**
     * Set the {@code for_input} property (displayed as {@code For Input}) of the object.
     * @param forInput the value to set
     */
    @JsonProperty("for_input")
    public void setForInput(Number forInput) { this.forInput = forInput; }

    /**
     * Retrieve the {@code for_output} property (displayed as '{@literal For Output}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("for_output")
    public Number getForOutput() { return this.forOutput; }

    /**
     * Set the {@code for_output} property (displayed as {@code For Output}) of the object.
     * @param forOutput the value to set
     */
    @JsonProperty("for_output")
    public void setForOutput(Number forOutput) { this.forOutput = forOutput; }

    /**
     * Retrieve the {@code for_stage} property (displayed as '{@literal For Stage}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("for_stage")
    public Number getForStage() { return this.forStage; }

    /**
     * Set the {@code for_stage} property (displayed as {@code For Stage}) of the object.
     * @param forStage the value to set
     */
    @JsonProperty("for_stage")
    public void setForStage(Number forStage) { this.forStage = forStage; }

    /**
     * Retrieve the {@code hidden} property (displayed as '{@literal Hidden}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("hidden")
    public Boolean getHidden() { return this.hidden; }

    /**
     * Set the {@code hidden} property (displayed as {@code Hidden}) of the object.
     * @param hidden the value to set
     */
    @JsonProperty("hidden")
    public void setHidden(Boolean hidden) { this.hidden = hidden; }

    /**
     * Retrieve the {@code locked} property (displayed as '{@literal Locked}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("locked")
    public Boolean getLocked() { return this.locked; }

    /**
     * Set the {@code locked} property (displayed as {@code Locked}) of the object.
     * @param locked the value to set
     */
    @JsonProperty("locked")
    public void setLocked(Boolean locked) { this.locked = locked; }

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
     * Retrieve the {@code of_ds_parameter_def} property (displayed as '{@literal Of DS Parameter Def}') of the object.
     * @return {@code Parameter}
     */
    @JsonProperty("of_ds_parameter_def")
    public Parameter getOfDsParameterDef() { return this.ofDsParameterDef; }

    /**
     * Set the {@code of_ds_parameter_def} property (displayed as {@code Of DS Parameter Def}) of the object.
     * @param ofDsParameterDef the value to set
     */
    @JsonProperty("of_ds_parameter_def")
    public void setOfDsParameterDef(Parameter ofDsParameterDef) { this.ofDsParameterDef = ofDsParameterDef; }

    /**
     * Retrieve the {@code quote_string} property (displayed as '{@literal Quote String}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("quote_string")
    public Number getQuoteString() { return this.quoteString; }

    /**
     * Set the {@code quote_string} property (displayed as {@code Quote String}) of the object.
     * @param quoteString the value to set
     */
    @JsonProperty("quote_string")
    public void setQuoteString(Number quoteString) { this.quoteString = quoteString; }

    /**
     * Retrieve the {@code transaction_groupable} property (displayed as '{@literal Transaction Groupable}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("transaction_groupable")
    public Number getTransactionGroupable() { return this.transactionGroupable; }

    /**
     * Set the {@code transaction_groupable} property (displayed as {@code Transaction Groupable}) of the object.
     * @param transactionGroupable the value to set
     */
    @JsonProperty("transaction_groupable")
    public void setTransactionGroupable(Number transactionGroupable) { this.transactionGroupable = transactionGroupable; }

    /**
     * Retrieve the {@code view_data} property (displayed as '{@literal View Data}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("view_data")
    public Number getViewData() { return this.viewData; }

    /**
     * Set the {@code view_data} property (displayed as {@code View Data}) of the object.
     * @param viewData the value to set
     */
    @JsonProperty("view_data")
    public void setViewData(Number viewData) { this.viewData = viewData; }

}
