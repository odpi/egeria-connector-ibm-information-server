/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JoinType implements IAEnum {

    INNER("inner"),
    LEFT_OUTER("leftOuter"),
    RIGHT_OUTER("rightOuter"),
    FULL_OUTER("fullOuter");

    @JsonValue
    private String value;
    JoinType(String value) { this.value = value; }
    public String getValue() { return value; }

}
