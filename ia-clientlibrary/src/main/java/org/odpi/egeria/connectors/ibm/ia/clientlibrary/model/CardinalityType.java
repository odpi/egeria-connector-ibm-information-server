/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CardinalityType implements IAEnum {

    UNIQUE_AND_CONSTANT("unique_and_constant"),
    NOT_CONSTRAINED("not_constrained"),
    UNIQUE("unique"),
    CONSTANT("constant");

    @JsonValue
    private String value;
    CardinalityType(String value) { this.value = value; }
    public String getValue() { return value; }

}
