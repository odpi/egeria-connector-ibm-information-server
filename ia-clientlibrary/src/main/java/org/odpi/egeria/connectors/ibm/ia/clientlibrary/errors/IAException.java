/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.errors;

public class IAException extends RuntimeException {

    /**
     * Default constructor used for creating an IAException when an unexpected error has been caught.
     * @param errorMessage description of the error
     */
    public IAException(String errorMessage) { super(errorMessage); }

    /**
     * Constructor used for creating an IAException when an unexpected error has been caught that was caused by
     * some other error.
     * @param errorMessage description of error
     * @param caughtError previous error causing this exception
     */
    public IAException(String errorMessage, Throwable caughtError) {
        super(errorMessage, caughtError);
    }

}
