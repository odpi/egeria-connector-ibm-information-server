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
import java.util.List;

/**
 * POJO for the {@code metric} asset type in IGC, displayed as '{@literal Metric}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Metric.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("metric")
public class Metric extends InformationAsset {

    @JsonProperty("benchmark")
    protected List<String> benchmark;

    @JsonProperty("data_policies")
    protected ItemList<DataPolicy> dataPolicies;

    @JsonProperty("execution_history")
    protected ItemList<DataRuleResults> executionHistory;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("measure")
    protected String measure;

    @JsonProperty("project")
    protected String project;

    @JsonProperty("references_data_rules_or_data_rule_sets")
    protected ItemList<MainObject> referencesDataRulesOrDataRuleSets;

    /**
     * Valid values are:
     * <ul>
     *   <li>CANDIDATE (displayed in the UI as 'CANDIDATE')</li>
     *   <li>ACCEPTED (displayed in the UI as 'ACCEPTED')</li>
     *   <li>STANDARD (displayed in the UI as 'STANDARD')</li>
     *   <li>DEPRECATED (displayed in the UI as 'DEPRECATED')</li>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     *   <li>IN_PROCESS (displayed in the UI as 'IN_PROCESS')</li>
     *   <li>REJECTED (displayed in the UI as 'REJECTED')</li>
     *   <li>ERROR (displayed in the UI as 'ERROR')</li>
     * </ul>
     */
    @JsonProperty("status")
    protected String status;

    /**
     * Retrieve the {@code benchmark} property (displayed as '{@literal Benchmark}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("benchmark")
    public List<String> getBenchmark() { return this.benchmark; }

    /**
     * Set the {@code benchmark} property (displayed as {@code Benchmark}) of the object.
     * @param benchmark the value to set
     */
    @JsonProperty("benchmark")
    public void setBenchmark(List<String> benchmark) { this.benchmark = benchmark; }

    /**
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @return {@code ItemList<DataPolicy>}
     */
    @JsonProperty("data_policies")
    public ItemList<DataPolicy> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<DataPolicy> dataPolicies) { this.dataPolicies = dataPolicies; }

    /**
     * Retrieve the {@code execution_history} property (displayed as '{@literal Execution History}') of the object.
     * @return {@code ItemList<DataRuleResults>}
     */
    @JsonProperty("execution_history")
    public ItemList<DataRuleResults> getExecutionHistory() { return this.executionHistory; }

    /**
     * Set the {@code execution_history} property (displayed as {@code Execution History}) of the object.
     * @param executionHistory the value to set
     */
    @JsonProperty("execution_history")
    public void setExecutionHistory(ItemList<DataRuleResults> executionHistory) { this.executionHistory = executionHistory; }

    /**
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

    /**
     * Retrieve the {@code measure} property (displayed as '{@literal Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("measure")
    public String getMeasure() { return this.measure; }

    /**
     * Set the {@code measure} property (displayed as {@code Expression}) of the object.
     * @param measure the value to set
     */
    @JsonProperty("measure")
    public void setMeasure(String measure) { this.measure = measure; }

    /**
     * Retrieve the {@code project} property (displayed as '{@literal Project}') of the object.
     * @return {@code String}
     */
    @JsonProperty("project")
    public String getProject() { return this.project; }

    /**
     * Set the {@code project} property (displayed as {@code Project}) of the object.
     * @param project the value to set
     */
    @JsonProperty("project")
    public void setProject(String project) { this.project = project; }

    /**
     * Retrieve the {@code references_data_rules_or_data_rule_sets} property (displayed as '{@literal References Data Rules or Data Rule Sets}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("references_data_rules_or_data_rule_sets")
    public ItemList<MainObject> getReferencesDataRulesOrDataRuleSets() { return this.referencesDataRulesOrDataRuleSets; }

    /**
     * Set the {@code references_data_rules_or_data_rule_sets} property (displayed as {@code References Data Rules or Data Rule Sets}) of the object.
     * @param referencesDataRulesOrDataRuleSets the value to set
     */
    @JsonProperty("references_data_rules_or_data_rule_sets")
    public void setReferencesDataRulesOrDataRuleSets(ItemList<MainObject> referencesDataRulesOrDataRuleSets) { this.referencesDataRulesOrDataRuleSets = referencesDataRulesOrDataRuleSets; }

    /**
     * Retrieve the {@code status} property (displayed as '{@literal Status}') of the object.
     * @return {@code String}
     */
    @JsonProperty("status")
    public String getStatus() { return this.status; }

    /**
     * Set the {@code status} property (displayed as {@code Status}) of the object.
     * @param status the value to set
     */
    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

}
