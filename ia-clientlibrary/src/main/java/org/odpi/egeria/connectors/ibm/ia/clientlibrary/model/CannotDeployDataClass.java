/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CannotDeployDataClass extends Error {

    @JacksonXmlProperty(isAttribute = true) private String dataClassId;
    @JacksonXmlProperty(localName = "Cause")
    private ErrorList cause;

    public String getDataClassId() { return dataClassId; }
    public void setDataClassId(String dataClassId) { this.dataClassId = dataClassId; }

    public ErrorList getCause() { return cause; }
    public void setCause(ErrorList cause) { this.cause = cause; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"dataClassId\": \"" + dataClassId
                + "\", \"Cause\": " + (cause == null ? "{}" : cause.toString())
                + " }";
    }

}
