/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors;

/**
 * IGCException is used as the foundation for all IGC client library exceptions.
 */
public class IGCException extends Exception {

    /**
     * Default constructor used for creating an IGCException when an unexpected error has been caught.
     * @param errorMessage description of the error
     * @param details details about the description of the error
     */
    public IGCException(String errorMessage, String details) {
        super(errorMessage + (details == null ? "" : ": " + details));
    }

    /**
     * Constructor used for creating an IGCException when an unexpected error has been caught that was caused by
     * some other error.
     * @param errorMessage description of error
     * @param caughtError previous error causing this exception
     */
    public IGCException(String errorMessage, Throwable caughtError) {
        super(errorMessage, caughtError);
    }

    /**
     * Constructor used for creating an IGCException when an unexpected error has been caught that was caused by
     * some other error.
     * @param errorMessage description of error
     * @param details details about the description of the error
     * @param caughtError previous error causing this exception
     */
    public IGCException(String errorMessage, String details, Throwable caughtError) {
        super(errorMessage + (details == null ? "" : ": " + details), caughtError);
    }

}
