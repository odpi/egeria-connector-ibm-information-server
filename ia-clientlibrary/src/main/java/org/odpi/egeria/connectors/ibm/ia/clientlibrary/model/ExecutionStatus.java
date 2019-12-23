/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExecutionStatus implements IAEnum {

    NOT_STARTED("not_stated"),
    RUNNING("running"),
    FAILED("failed"),
    SUCCESSFUL("successful"),
    CANCELLED("cancelled"),
    UNKNOWN("unknown");

    @JsonValue
    private String value;
    ExecutionStatus(String value) { this.value = value; }
    public String getValue() { return value; }

}
