/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OutputLinkType implements IAEnum {

    PASSING_RECORDS("PassingRecords"),
    FAILING_RECORDS("FailingRecords"),
    VIOLATED_RULES("ViolatedRules"),
    UNASSIGNED("Unassigned");

    @JsonValue
    private String value;
    OutputLinkType(String value) { this.value = value; }
    public String getValue() { return value; }

}
