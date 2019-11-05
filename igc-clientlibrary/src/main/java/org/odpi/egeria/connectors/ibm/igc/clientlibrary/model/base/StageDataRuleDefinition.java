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
 * POJO for the {@code stage_data_rule_definition} asset type in IGC, displayed as '{@literal Stage Data Rule Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("stage_data_rule_definition")
public class StageDataRuleDefinition extends Reference {

    @JsonProperty("based_on_rule")
    protected PublishedDataRuleDefinition basedOnRule;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("stage")
    protected Stage stage;

    @JsonProperty("stage_logic")
    protected String stageLogic;

    /**
     * Retrieve the {@code based_on_rule} property (displayed as '{@literal Based on Rule}') of the object.
     * @return {@code PublishedDataRuleDefinition}
     */
    @JsonProperty("based_on_rule")
    public PublishedDataRuleDefinition getBasedOnRule() { return this.basedOnRule; }

    /**
     * Set the {@code based_on_rule} property (displayed as {@code Based on Rule}) of the object.
     * @param basedOnRule the value to set
     */
    @JsonProperty("based_on_rule")
    public void setBasedOnRule(PublishedDataRuleDefinition basedOnRule) { this.basedOnRule = basedOnRule; }

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
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code stage} property (displayed as '{@literal Stage}') of the object.
     * @return {@code Stage}
     */
    @JsonProperty("stage")
    public Stage getStage() { return this.stage; }

    /**
     * Set the {@code stage} property (displayed as {@code Stage}) of the object.
     * @param stage the value to set
     */
    @JsonProperty("stage")
    public void setStage(Stage stage) { this.stage = stage; }

    /**
     * Retrieve the {@code stage_logic} property (displayed as '{@literal Stage Logic}') of the object.
     * @return {@code String}
     */
    @JsonProperty("stage_logic")
    public String getStageLogic() { return this.stageLogic; }

    /**
     * Set the {@code stage_logic} property (displayed as {@code Stage Logic}) of the object.
     * @param stageLogic the value to set
     */
    @JsonProperty("stage_logic")
    public void setStageLogic(String stageLogic) { this.stageLogic = stageLogic; }

}
