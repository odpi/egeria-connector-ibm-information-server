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
import java.util.Date;

/**
 * POJO for the {@code published_data_rule_definition} asset type in IGC, displayed as '{@literal Data Rule Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=PublishedDataRuleDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("published_data_rule_definition")
public class PublishedDataRuleDefinition extends DataRuleDefinition {

    @JsonProperty("copied_into_data_rule_stages")
    protected ItemList<Stage> copiedIntoDataRuleStages;

    @JsonProperty("is_valid")
    protected Boolean isValid;

    @JsonProperty("root_folder")
    protected String rootFolder;

    /**
     * Retrieve the {@code copied_into_data_rule_stages} property (displayed as '{@literal Copied Into Data Rule Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("copied_into_data_rule_stages")
    public ItemList<Stage> getCopiedIntoDataRuleStages() { return this.copiedIntoDataRuleStages; }

    /**
     * Set the {@code copied_into_data_rule_stages} property (displayed as {@code Copied Into Data Rule Stages}) of the object.
     * @param copiedIntoDataRuleStages the value to set
     */
    @JsonProperty("copied_into_data_rule_stages")
    public void setCopiedIntoDataRuleStages(ItemList<Stage> copiedIntoDataRuleStages) { this.copiedIntoDataRuleStages = copiedIntoDataRuleStages; }

    /**
     * Retrieve the {@code is_valid} property (displayed as '{@literal Is Valid}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_valid")
    public Boolean getIsValid() { return this.isValid; }

    /**
     * Set the {@code is_valid} property (displayed as {@code Is Valid}) of the object.
     * @param isValid the value to set
     */
    @JsonProperty("is_valid")
    public void setIsValid(Boolean isValid) { this.isValid = isValid; }

    /**
     * Retrieve the {@code root_folder} property (displayed as '{@literal Root Folder}') of the object.
     * @return {@code String}
     */
    @JsonProperty("root_folder")
    public String getRootFolder() { return this.rootFolder; }

    /**
     * Set the {@code root_folder} property (displayed as {@code Root Folder}) of the object.
     * @param rootFolder the value to set
     */
    @JsonProperty("root_folder")
    public void setRootFolder(String rootFolder) { this.rootFolder = rootFolder; }

}
