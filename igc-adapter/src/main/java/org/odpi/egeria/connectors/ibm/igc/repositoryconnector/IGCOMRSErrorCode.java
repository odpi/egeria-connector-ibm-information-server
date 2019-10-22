/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import java.text.MessageFormat;

/**
 * The IGCOMRSErrorCode is used to define first failure data capture (FFDC) for errors that occur when working with
 * IGC as an OMRS Metadata Repository.  It is used in conjunction with both Checked and Runtime (unchecked) exceptions.
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
public enum IGCOMRSErrorCode {

    REST_CLIENT_FAILURE(500, "OMRS-IGC-REPOSITORY-500-001 ",
            "The IGC REST API client was not successfully initialized to \"{0}\"",
            "The system was unable to login to or access the IBM IGC environment via REST API.",
            "Check your authorization details are accurate, the IGC environment started, and is network-accessible."),
    OMRS_BUNDLE_FAILURE(500, "OMRS-IGC-REPOSITORY-500-002 ",
            "Unable to {0} the required OMRS OpenIGC bundle",
            "The system was unable to either generate or upload the OMRS OpenIGC bundle needed to handle open metadata.",
            "Check the system logs and diagnose or report the problem."),
    CLASSIFICATION_ERROR_UNKNOWN(500, "OMRS-IGC-REPOSITORY-500-003 ",
            "Unable to apply one or more classifications \"{0}\" to entity \"{1}\"",
            "The system was unable to apply the classification(s) to the entity.",
            "Check the system logs and diagnose or report the problem."),
    UPDATE_ERROR_UNKNOWN(500, "OMRS-IGC-REPOSITORY-500-004 ",
            "Unable to apply one or more updates \"{0}\" to entity \"{1}\"",
            "The system was unable to apply the update(s) to the entity.",
            "Check the system logs and diagnose or report the problem."),
    DELETE_ERROR_UNKNOWN(500, "OMRS-IGC-REPOSITORY-500-005 ",
            "Unable to delete the entity \"{0}\" with RID \"{1}\"",
            "The system was unable to delete the specified entity.",
            "Check the system logs and diagnose or report the problem."),
    DELETE_RELATIONSHIP_ERROR_UNKNOWN(500, "OMRS-IGC-REPOSITORY-500-006 ",
            "Unable to delete the relationship \"{0}\" from asset with RID \"{1}\"",
            "The system was unable to delete the specified relationship.",
            "Check the system logs and diagnose or report the problem."),
    REGEX_NOT_IMPLEMENTED(501, "OMRS-IGC-REPOSITORY-501-001 ",
            "Repository {0} is not able to support the regular expression \"{1}\"",
            "This repository has a fixed subset of regular expressions it can support.",
            "No action required, this is a limitation of the technology. To search using such regular expressions, the metadata of interest" +
                    " must be synchronized to a cohort repository that can support such regular expressions."),
    NO_HISTORY(501, "OMRS-IGC-REPOSITORY-501-002 ",
            "Repository {0} is not able to service historical queries",
            "This repository does not retain historical metadata, so cannot support historical queries.",
            "No action required, this is a limitation of the technology. To search such history, the metadata of interest" +
                    " must be synchronized to a cohort repository that can support history."),
    NO_RELATIONSHIP_PROPERTIES(501, "OMRS-IGC-REPOSITORY-501-003 ",
            "Repository {0} does not support properties on relationships",
            "This repository does not store properties on relationships, so they cannot be updated or searched.",
            "No action required, this is a limitation of the technology. To store or search properties on relationships, the metadata of" +
                    " interest must be mastered (homed) in a cohort repository that can support relationship properties."),
    UNSUPPORTED_OBJECT_TYPE(501, "OMRS-IGC-REPOSITORY-501-004 ",
            "Requested object \"{0}\" is of type \"{1}\" that is not supported by repository {2}",
            "This repository cannot support retrieving details for or searching for certain object types.",
            "No action required, this is a limitation of the technology. To store or search such objects, the metadata of interest" +
                    " must be mastered (homed) in a cohort repository that can support it."),
    UNSUPPORTED_STATUS(501, "OMRS-IGC-REPOSITORY-501-005 ",
            "Requested status \"{0}\" for type \"{1}\" is not supported by repository {2}",
            "This repository cannot support the requested status on the provided type.",
            "No action required, this is a limitation of the technology. To store or search based on such a status, the metadata of" +
                    " interest must be mastered (homed) in a cohort repository that can support it."),
    CREATION_NOT_SUPPORTED(501, "OMRS-IGC-REPOSITORY-501-006 ",
            "Requested type \"{0}\" (IGC type \"{1}\") cannot be created by repository {2}",
            "This repository cannot support creating instances of the type requested.",
            "No action required, this is a limitation of the technology. To create new instances of this type, the metadata of interest" +
                    " must be mastered (homed) in a cohort repository that can support it."),
    PROPERTY_CANNOT_BE_TRANSLATED(501, "OMRS-IGC-REPOSITORY-501-007 ",
            "The value \"{0}\" of type \"{1}\" cannot be translated to IGC",
            "This repository cannot store properties of the type provided.",
            "No action required, this is a limitation of the technology. To store such properties, the metadata of interest" +
                    " must be mastered (homed) in a cohort repository that can support them."),
    CLASSIFICATION_INSUFFICIENT_PROPERTIES(400, "OMRS-IGC-REPOSITORY-400-001 ",
            "The properties provided for classification \"{0}\" on entity \"{1}\" are insufficient",
            "The system is unable to proceed classifying an entity because insufficient detail has been provided.",
            "Check the system logs and diagnose or report the problem."),
    CLASSIFICATION_EXCEEDS_REPOSITORY(400, "OMRS-IGC-REPOSITORY-400-002 ",
            "The properties provided for classification \"{0}\" on entity \"{1}\" are excessive",
            "The system is unable to proceed classifying an entity because more details have been provided than the repository is capable of handling.",
            "Check the system logs and diagnose or report the problem."),
    CLASSIFICATION_NOT_FOUND(400, "OMRS-IGC-REPOSITORY-400-003 ",
            "The classification \"{0}\" on entity \"{1}\" was not found",
            "The system cannot proceed classifying an entity because it was unable to find the classification by the provided details.",
            "Check the system logs and diagnose or report the problem."),
    CLASSIFICATION_NOT_EDITABLE(400, "OMRS-IGC-REPOSITORY-400-004 ",
            "The classification \"{0}\" on entity \"{1}\" is not editable",
            "The system cannot proceed classifying an entity because the classification type is not editable through IGC's REST API.",
            "Raise an enhancement request with IBM support."),
    CLASSIFICATION_NOT_APPLICABLE(400, "OMRS-IGC-REPOSITORY-400-005 ",
            "The classification \"{0}\" cannot be applied to IGC entity \"{1}\"",
            "The system does not support the listed classification on the IGC entity type listed.",
            "Raise an enhancement request with IBM support."),
    TYPEDEF_NOT_MAPPED(404, "OMRS-IGC-REPOSITORY-404-001 ",
            "The TypeDef \"{0}\" has not been mapped to a pre-existing type in repository {1}",
            "The system does not support the provided TypeDef, or it has not been mapped to a pre-existing type.",
            "Raise an issue on the Egeria GitHub repository to see if it is feasible to map this TypeDef."),
    ATTRIBUTE_TYPEDEF_NOT_MAPPED(404, "OMRS-IGC-REPOSITORY-404-002 ",
            "The AttributeTypeDef \"{0}\" has not been mapped to a pre-existing type in repository {1}",
            "The system does not support the provided AttributeTypeDef, or it has not been mapped to a pre-existing type.",
            "Raise an issue on the Egeria GitHub repository to see if it is feasible to map this AttributeTypeDef."),
    ENTITY_NOT_KNOWN(404, "OMRS-IGC-REPOSITORY-404-003 ",
            "The entity instance with GUID \"{0}\" and RID \"{1}\" is not known to repository {2}",
            "The system was unable to find an object with the provided RID.",
            "Ensure your IGC repository actually contains such an object, and if so raise an issue on the Egeria GitHub with details."),
    RELATIONSHIP_NOT_KNOWN(404, "OMRS-IGC-REPOSITORY-404-004 ",
            "The relationship instance with GUID \"{0}\" is not known to repository {1}",
            "The system was unable to find a relationship based on the provided GUID.",
            "Ensure your IGC repository actually contains such a relationship, and if so raise an issue on the Egeria GitHub with details."),
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
    IGCOMRSErrorCode(int newHTTPErrorCode, String newErrorMessageId, String newErrorMessage, String newSystemAction, String newUserAction) {
        this.httpErrorCode  = newHTTPErrorCode;
        this.errorMessageId = newErrorMessageId;
        this.errorMessage   = newErrorMessage;
        this.systemAction   = newSystemAction;
        this.userAction     = newUserAction;
    }


    /**
     * Returns the numeric code that can be used in REST responses.
     *
     * @return int
     */
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
