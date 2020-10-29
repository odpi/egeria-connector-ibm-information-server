/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog;

import org.odpi.openmetadata.frameworks.auditlog.messagesets.ExceptionMessageDefinition;
import org.odpi.openmetadata.frameworks.auditlog.messagesets.ExceptionMessageSet;

import java.text.MessageFormat;

/**
 * The DataStageErrorCode is used to define first failure data capture (FFDC) for errors that occur when working with
 * DataStage as a Data Engine.  It is used in conjunction with both Checked and Runtime (unchecked) exceptions.
 * <br><br>
 * The 5 fields in the enum are:
 * <ul>
 *   <li>HTTP Error Code - for translating between REST and JAVA - Typically the numbers used are:</li>
 *   <li><ul>
 *     <li>500 - internal error</li>
 *     <li>400 - invalid parameters</li>
 *     <li>404 - not found</li>
 *   </ul></li>
 *   <li>Error Message Id - to uniquely identify the message</li>
 *   <li>Error Message Text - includes placeholder to allow additional values to be captured</li>
 *   <li>SystemAction - describes the result of the error</li>
 *   <li>UserAction - describes how a AssetConsumerInterface should correct the error</li>
 * </ul>
 */
public enum DataStageErrorCode implements ExceptionMessageSet {

    SYNC_TIME_UPDATE_FAILURE(500, "DATA-ENGINE-IBM-DATASTAGE-500-001 ",
            "Unable to update sync time for IBM DataStage",
            "The system was unable to update the date and time at which synchronization was last successful.",
            "Check the system logs and diagnose or report the problem."),
    CONNECTION_FAILURE(500, "DATA-ENGINE-IBM-DATASTAGE-500-002 ",
            "Unable to initialize DataStage connectivity to: {0}",
            "The system was unable to initialize connectivity to IBM DataStage on the provided address.",
            "Check the inter-host network resolution, credentials and system logs to diagnose or report the problem."),
    UNKNOWN_RUNTIME_ERROR(500, "DATA-ENGINE-IBM-DATASTAGE-500-003 ",
            "An unknown runtime error occurred, and the system is unable to proceed with its processing",
            "The system was unable to complete processing of a given action due to a connectivity-related problem.",
            "Check the system logs and diagnose or report the problem."),
    ;

    private ExceptionMessageDefinition messageDefinition;

    /**
     * The constructor for DataStageErrorCode expects to be passed one of the enumeration rows defined in
     * DataStageErrorCode above.   For example:
     *
     *     DataStageErrorCode   errorCode = DataStageErrorCode.NULL_INSTANCE;
     *
     * This will expand out to the 5 parameters shown below.
     *
     * @param newHTTPErrorCode - error code to use over REST calls
     * @param newErrorMessageId - unique Id for the message
     * @param newErrorMessage - text for the message
     * @param newSystemAction - description of the action taken by the system when the error condition happened
     * @param newUserAction - instructions for resolving the error
     */
    DataStageErrorCode(int newHTTPErrorCode, String newErrorMessageId, String newErrorMessage, String newSystemAction, String newUserAction) {
        this.messageDefinition = new ExceptionMessageDefinition(newHTTPErrorCode,
                newErrorMessageId,
                newErrorMessage,
                newSystemAction,
                newUserAction);
    }

    /**
     * Retrieve a message definition object for an exception.  This method is used when there are no message inserts.
     *
     * @return message definition object.
     */
    @Override
    public ExceptionMessageDefinition getMessageDefinition() {
        return messageDefinition;
    }


    /**
     * Retrieve a message definition object for an exception.  This method is used when there are values to be inserted into the message.
     *
     * @param params array of parameters (all strings).  They are inserted into the message according to the numbering in the message text.
     * @return message definition object.
     */
    @Override
    public ExceptionMessageDefinition getMessageDefinition(String... params) {
        messageDefinition.setMessageParameters(params);
        return messageDefinition;
    }

    /**
     * toString() JSON-style
     *
     * @return string description
     */
    @Override
    public String toString() {
        return "DataStageErrorCode{" +
                "messageDefinition=" + messageDefinition +
                '}';
    }

}
