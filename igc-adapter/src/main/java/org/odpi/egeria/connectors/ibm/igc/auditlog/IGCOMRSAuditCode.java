/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.auditlog;

import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLogRecordSeverity;

import java.text.MessageFormat;

/**
 * The IGCOMRSAuditCode is used to define the message content for the OMRS Audit Log.
 *
 * The 5 fields in the enum are:
 * <ul>
 *     <li>Log Message Id - to uniquely identify the message</li>
 *     <li>Severity - is this an event, decision, action, error or exception</li>
 *     <li>Log Message Text - includes placeholder to allow additional values to be captured</li>
 *     <li>Additional Information - further parameters and data relating to the audit message (optional)</li>
 *     <li>SystemAction - describes the result of the situation</li>
 *     <li>UserAction - describes how a user should correct the situation</li>
 * </ul>
 */
public enum IGCOMRSAuditCode {

    REPOSITORY_SERVICE_STARTING("OMRS-IGC-REPOSITORY-0001",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog proxy is starting a new server instance",
            "The local server has started up a new instance of the IBM Information Governance Catalog proxy.",
            "No action is required.  This is part of the normal operation of the service."),
    REPOSITORY_SERVICE_STARTED("OMRS-IGC-REPOSITORY-0004",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog proxy has started a new instance for server {0}, detected version {1}",
            "The local server has completed startup of a new instance.",
            "No action is required.  This is part of the normal operation of the service."),
    REPOSITORY_SERVICE_SHUTDOWN("OMRS-IGC-REPOSITORY-0005",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog proxy has shutdown its instance for server {0}",
            "The local server has requested shut down of a IBM Information Governance Catalog proxy instance.",
            "No action is required.  This is part of the normal operation of the service."),
    EVENT_MAPPER_INITIALIZING("OMRS-IGC-REPOSITORY-0006",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog event mapper is initializing",
            "The local server has started up a new instance of the IBM Information Governance Catalog event mapper.",
            "No action is required.  This is part of the normal operation of the service."),
    EVENT_MAPPER_INITIALIZED("OMRS-IGC-REPOSITORY-0007",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog event mapper has initialized for server {0}",
            "The local server has completed initialization of a new instance.",
            "No action is required.  This is part of the normal operation of the service."),
    EVENT_MAPPER_SHUTDOWN("OMRS-IGC-REPOSITORY-0008",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog event mapper has shutdown its instance for server {0}",
            "The local server has requested shut down of an IBM Information Governance Catalog event mapper instance.",
            "No action is required.  This is part of the normal operation of the service."),
    EVENT_MAPPER_STARTING("OMRS-IGC-REPOSITORY-0009",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog event mapper consumer thread is starting up",
            "The local server has requested startup of an IBM Information Governance Catalog event mapper consumer.",
            "No action is required.  This is part of the normal operation of the service."),
    EVENT_MAPPER_RUNNING("OMRS-IGC-REPOSITORY-0010",
            OMRSAuditLogRecordSeverity.INFO,
            "The IBM Information Governance Catalog event mapper is running",
            "The local server is now running a consumer thread for IBM Information Governance Catalog.",
            "No action is required.  This is part of the normal operation of the service."),
    EVENT_MAPPER_CONSUMER_FAILURE("OMRS-IGC-REPOSITORY-0011",
            OMRSAuditLogRecordSeverity.EXCEPTION,
            "The IBM Information Governance Catalog event mapper failed to consume an event",
            "The local server failed to consume an IBM Information Governance Catalog event.",
            "Investigate the logs for additional information and raise a GitHub issue with the details."),
    ;

    private String logMessageId;
    private OMRSAuditLogRecordSeverity severity;
    private String logMessage;
    private String systemAction;
    private String userAction;


    /**
     * The constructor for OMRSAuditCode expects to be passed one of the enumeration rows defined in
     * OMRSAuditCode above.   For example:
     * <p>
     * OMRSAuditCode   auditCode = OMRSAuditCode.SERVER_NOT_AVAILABLE;
     * <p>
     * This will expand out to the 4 parameters shown below.
     *
     * @param messageId    - unique Id for the message
     * @param severity     - the severity of the message
     * @param message      - text for the message
     * @param systemAction - description of the action taken by the system when the condition happened
     * @param userAction   - instructions for resolving the situation, if any
     */
    IGCOMRSAuditCode(String messageId, OMRSAuditLogRecordSeverity severity, String message,
                     String systemAction, String userAction) {
        this.logMessageId = messageId;
        this.severity = severity;
        this.logMessage = message;
        this.systemAction = systemAction;
        this.userAction = userAction;
    }

    /**
     * Returns the unique identifier for the error message.
     *
     * @return logMessageId
     */
    public String getLogMessageId() {
        return logMessageId;
    }

    /**
     * Return the severity object for the log
     * @return severity
     */
    public OMRSAuditLogRecordSeverity getSeverity() {
        return severity;
    }

    /**
     * Returns the log message with the placeholders filled out with the supplied parameters.
     *
     * @param params - strings that plug into the placeholders in the logMessage
     * @return logMessage (formatted with supplied parameters)
     */
    public String getFormattedLogMessage(String... params) {
        MessageFormat mf = new MessageFormat(logMessage);
        return mf.format(params);
    }


    /**
     * Returns a description of the action taken by the system when the condition that caused this exception was
     * detected.
     *
     * @return systemAction String
     */
    public String getSystemAction() {
        return systemAction;
    }


    /**
     * Returns instructions of how to resolve the issue reported in this exception.
     *
     * @return userAction String
     */
    public String getUserAction() {
        return userAction;
    }

}
