/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SampleType implements IAEnum {

    RANDOM("random"),
    SEQUENTIAL("sequential"),
    EVERY_NTH("every_nth");

    @JsonValue
    private String value;
    SampleType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
