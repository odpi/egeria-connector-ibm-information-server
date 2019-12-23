/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UpdateMethodType implements IAEnum {

    APPEND("Append"),
    OVERWRITE("Overwrite");

    @JsonValue
    private String value;
    UpdateMethodType(String value) { this.value = value; }
    public String getValue() { return value; }

}
