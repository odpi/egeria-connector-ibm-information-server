/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors;

/**
 * IGCConnectivityException is used for any unexpected connectivity issues hit by the IGC client library.
 */
public class IGCConnectivityException extends IGCException {

    /**
     * Constructor used for creating an IGCConnectivityException when an unexpected error has been caught that was
     * caused by some other error.
     * @param errorMessage description of error
     * @param caughtError previous error causing this exception
     */
    public IGCConnectivityException(String errorMessage, Throwable caughtError) {
        super(errorMessage, caughtError);
    }

    /**
     * Constructor used for creating an IGCConnectivityException with some additional details, when not caused by some
     * other underlying error
     * @param errorMessage description of error
     * @param details details about the description of the error
     */
    public IGCConnectivityException(String errorMessage, String details) {
        super(errorMessage, details);
    }

}
