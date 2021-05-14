/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LandingTableType implements IAEnum {

    SIMPLE("Simple"),
    ADVANCED("Advanced");

    @JsonValue
    private String value;
    LandingTableType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
