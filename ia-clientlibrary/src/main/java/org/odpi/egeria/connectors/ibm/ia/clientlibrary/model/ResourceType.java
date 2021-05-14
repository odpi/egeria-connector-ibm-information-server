/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceType implements IAEnum {

    RULE_DEFINITION("ruleDefinition"),
    RULESET_DEFINITION("rulesetDefinition"),
    EXECUTABLE_RULE("executableRule"),
    GLOBAL_VARIABLE("globalVariable"),
    RULE_VARIABLE("ruleVariable"),
    COLUMN("column"),
    PROJECT("project"),
    TABLE("table"),
    METRIC("metric"),
    TASK_SEQUENCE("taskSequence"),
    EXECUTION_HISTORY("executionHistory"),
    PRIMARY_KEY("primaryKey"),
    FILE("file");

    @JsonValue
    private String value;
    ResourceType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
