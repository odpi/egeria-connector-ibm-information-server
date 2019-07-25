/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import java.text.MessageFormat;

public enum DataStageErrorCode {

    SYNC_TIME_UPDATE_FAILURE(500, "DATA-ENGINE-IBM-DATASTAGE-500-001",
            "Unable to update sync time for IBM DataStage",
            "The system was unable to update the date and time at which synchronization was last successful.",
            "Check the system logs and diagnose or report the problem."),
    ;

    private int    httpErrorCode;
    private String errorMessageId;
    private String errorMessage;
    private String systemAction;
    private String userAction;

    /**
     * The constructor for LocalAtlasOMRSErrorCode expects to be passed one of the enumeration rows defined in
     * LocalAtlasOMRSErrorCode above.   For example:
     *
     *     LocalAtlasOMRSErrorCode   errorCode = LocalAtlasOMRSErrorCode.NULL_INSTANCE;
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
        this.httpErrorCode  = newHTTPErrorCode;
        this.errorMessageId = newErrorMessageId;
        this.errorMessage   = newErrorMessage;
        this.systemAction   = newSystemAction;
        this.userAction     = newUserAction;
    }


    public int getHTTPErrorCode() {
        return httpErrorCode;
    }


    /**
     * Returns the unique identifier for the error message.
     *
     * @return errorMessageId
     */
    public String getErrorMessageId() {
        return errorMessageId;
    }


    /**
     * Returns the error message with placeholders for specific details.
     *
     * @return errorMessage (unformatted)
     */
    public String getUnformattedErrorMessage() {
        return errorMessage;
    }


    /**
     * Returns the error message with the placeholders filled out with the supplied parameters.
     *
     * @param params - strings that plug into the placeholders in the errorMessage
     * @return errorMessage (formatted with supplied parameters)
     */
    public String getFormattedErrorMessage(String... params) {
        MessageFormat mf = new MessageFormat(errorMessage);
        String result = mf.format(params);
        return result;
    }


    /**
     * Returns a description of the action taken by the system when the condition that caused this exception was
     * detected.
     *
     * @return systemAction
     */
    public String getSystemAction() {
        return systemAction;
    }


    /**
     * Returns instructions of how to resolve the issue reported in this exception.
     *
     * @return userAction
     */
    public String getUserAction() {
        return userAction;
    }

}
