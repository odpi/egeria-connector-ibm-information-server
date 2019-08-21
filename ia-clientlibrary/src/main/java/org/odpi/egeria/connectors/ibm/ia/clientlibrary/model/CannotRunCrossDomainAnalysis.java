/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CannotRunCrossDomainAnalysis extends Error {

    @JacksonXmlProperty(isAttribute = true) private String baseTable;
    @JacksonXmlProperty(isAttribute = true) private String pairTable;
    @JacksonXmlProperty(localName = "Cause")
    private ErrorList cause;

    public String getBaseTable() { return baseTable; }
    public void setBaseTable(String baseTable) { this.baseTable = baseTable; }

    public String getPairTable() { return pairTable; }
    public void setPairTable(String pairTable) { this.pairTable = pairTable; }

    public ErrorList getCause() { return cause; }
    public void setCause(ErrorList cause) { this.cause = cause; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"baseTable\": \"" + baseTable
                + "\", \"pairTable\": \"" + pairTable
                + "\", \"Cause\": " + (cause == null ? "{}" : cause.toString())
                + " }";
    }

}
