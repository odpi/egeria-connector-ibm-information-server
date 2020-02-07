/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IGCConnectivityException is used for any unexpected connectivity issues hit by the IGC client library.
 */
public class IAConnectivityException extends IAException {

    private static final Logger log = LoggerFactory.getLogger(IAConnectivityException.class);

    /**
     * Constructor used for creating an IAConnectivityException when an unexpected error has been caught that was
     * caused by some other error.
     * @param errorMessage description of error
     * @param caughtError previous error causing this exception
     */
    public IAConnectivityException(String errorMessage, Throwable caughtError) {
        super(errorMessage, caughtError);
    }

    /**
     * Constructor used for creating an IAConnectivityException with some additional details, when not caused by some
     * other underlying error
     * @param errorMessage description of error
     * @param details details about the description of the error
     */
    public IAConnectivityException(String errorMessage, String details) {
        super(errorMessage);
        log.error("Details for connectivity issue '{}': {}", errorMessage, details);
    }

}