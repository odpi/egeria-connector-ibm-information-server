/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.auditlog;

import org.odpi.openmetadata.frameworks.auditlog.messagesets.ExceptionMessageDefinition;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.OMRSRuntimeException;

/**
 * General OMRSRuntimeException for anything that occurs in IGC which cannot be handled, but for which no checked
 * exception can be thrown due to interface restrictions.
 */
public class IGCOMRSRuntimeException extends OMRSRuntimeException {

    private static final long    serialVersionUID = 1L;

    /**
     * This is the constructor used for creating an exception when an unexpected error has been caught.
     * The properties allow additional information to be associated with the exception.
     *
     * @param messageDefinition  content of the message
     * @param className   name of class reporting error
     * @param actionDescription   description of function it was performing when error detected
     * @param caughtError   previous error causing this exception
     */
    public IGCOMRSRuntimeException(ExceptionMessageDefinition messageDefinition,
                                   String                     className,
                                   String                     actionDescription,
                                   Throwable                  caughtError) {
        super(messageDefinition, className, actionDescription, caughtError);
    }

}
