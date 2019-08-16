/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CannotRunAutoDiscovery extends Error {

    @JacksonXmlProperty(isAttribute = true) private String tableName;
    @JacksonXmlProperty(localName = "Cause")
    private ErrorList cause;

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public ErrorList getCause() { return cause; }
    public void setCause(ErrorList cause) { this.cause = cause; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"tableName\": \"" + tableName
                + "\", \"Cause\": " + (cause == null ? "{}" : cause.toString())
                + " }";
    }

}
