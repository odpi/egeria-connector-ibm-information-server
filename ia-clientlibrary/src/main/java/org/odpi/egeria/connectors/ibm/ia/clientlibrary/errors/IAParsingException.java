/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IAParsingException is used for any problems parsing JSON information by the client library.
 */
public class IAParsingException extends IAException {

    private static final Logger log = LoggerFactory.getLogger(IAParsingException.class);

    /**
     * Constructor used for creating an IAParsingException when an unexpected error has been caught based on some
     * other underlying error and we are simply re-throwing.
     *
     * @param errorMessage description of error
     * @param caughtError previous error causing this exception
     */
    public IAParsingException(String errorMessage, Throwable caughtError) {
        super(errorMessage, caughtError);
    }

    /**
     * Constructor used for creating an IAParsingException when an unexpected error has been caught based on some
     * other underlying error.
     * @param errorMessage description of error
     * @param details details of the error, used only for logging purposes
     * @param caughtError previous error causing this exception
     */
    public IAParsingException(String errorMessage, String details, Throwable caughtError) {
        super(errorMessage, caughtError);
        log.error("Parsing exception details for '{}': {}", errorMessage, details);
    }

}
