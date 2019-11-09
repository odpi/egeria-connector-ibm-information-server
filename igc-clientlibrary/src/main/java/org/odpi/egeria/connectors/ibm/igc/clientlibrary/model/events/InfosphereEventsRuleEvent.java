/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The payload contents of all Rule-specific events on the InfosphereEvents topic.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfosphereEventsRuleEvent extends InfosphereEvents {

    protected String projectRid;
    protected String tamRid;
    protected String commonRuleRid;
    protected String ruleRid;

    /**
     * Retrieve the Repository ID (RID) of the Information Analyzer project.
     * @return String
     */
    @JsonProperty("projectRid")
    public String getProjectRid() { return this.projectRid; }

    /**
     * Set the Repository ID (RID) of the Information Analyzer project.
     * @param projectRid of the Information Analyzer project
     */
    @JsonProperty("projectRid")
    public void setProjectRid(String projectRid) { this.projectRid = projectRid; }

    /**
     * Retrieve the Repository ID (RID) of the Table Analysis Master object.
     * Unfortunately these objects are currently not queryable through IGC's REST API.
     * @return String
     */
    @JsonProperty("tamRid")
    public String getTamRid() { return this.tamRid; }

    /**
     * Set the Repository ID (RID) of the Table Analysis Master object.
     * Unfortunately these objects are currently not queryable through IGC's REST API.
     * @param tamRid of the Table Analysis Master object
     */
    @JsonProperty("tamRid")
    public void setTamRid(String tamRid) { this.tamRid = tamRid; }

    /**
     * Retrieve the Repository ID (RID) of a data rule or set (data_rule or rule_set) that is fully-bound to be
     * executable.
     * @return String
     */
    @JsonProperty("commonRuleRid")
    public String getCommonRuleRid() { return this.commonRuleRid; }

    /**
     * Set the Repository ID (RID) of a data rule or set (data_rule or rule_set) that is fully-bound to be
     * executable.
     * @param commonRuleRid of a data_rule or rule_set with bindings
     */
    @JsonProperty("commonRuleRid")
    public void setCommonRuleRid(String commonRuleRid) { this.commonRuleRid = commonRuleRid; }

    /**
     * Retrieve the Repository ID (RID) the data rule or set (data_rule or rule_set) representation, without bindings.
     * @return String
     */
    @JsonProperty("ruleRid")
    public String getRuleRid() { return this.ruleRid; }

    /**
     * Set the Repository ID (RID) for a data rule or set (data_rule or rule_set) representation, without bindings.
     * @param ruleRid of the data_rule or rule_set without bindings
     */
    @JsonProperty("ruleRid")
    public void setRuleRid(String ruleRid) { this.ruleRid = ruleRid; }

}
