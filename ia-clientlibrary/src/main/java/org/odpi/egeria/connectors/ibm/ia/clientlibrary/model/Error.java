/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    @JacksonXmlProperty(localName = "Message")
    private String message;
    @JacksonXmlProperty(localName = "StackTrace")
    private String stackTrace;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }

    @Override
    public String toString() {
        return "{ \"Message\": \"" + message
                + "\", \"StackTrace\": \"" + stackTrace
                + "\" }";
    }

}
