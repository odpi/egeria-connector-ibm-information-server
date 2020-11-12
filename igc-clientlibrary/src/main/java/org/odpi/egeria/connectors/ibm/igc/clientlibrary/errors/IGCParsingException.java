/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors;

/**
 * IGCParsingException is used for any problems parsing JSON information by the client library.
 */
public class IGCParsingException extends IGCException {

    /**
     * Constructor used for creating an IGCParsingException when an unexpected error has been caught based on some
     * other underlying error.
     * @param errorMessage description of error
     * @param details details of the error, used only for logging purposes
     * @param caughtError previous error causing this exception
     */
    public IGCParsingException(String errorMessage, String details, Throwable caughtError) {
        super(errorMessage, details, caughtError);
    }

    /**
     * Constructor used for creating an IGCParsingException with some additional details, when not caused by some
     * other underlying error
     * @param errorMessage description of error
     * @param details details about the description of the error
     */
    public IGCParsingException(String errorMessage, String details) {
        super(errorMessage, details);
    }

}
