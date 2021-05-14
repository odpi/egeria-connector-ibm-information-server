/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The object that contains instances of notes that can exist against many objects in IGC.
 *
 * (Note that this does not in reality extend Reference given that it has no '_type' attribute that is present
 * on ALL other types in IGC, and hence we have some workarounds in place in this class to have it behave as if it
 * does.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="_type", visible=true, defaultImpl=Note.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("note")
public class Note extends Reference {

    /**
     * The 'note' property of a Note contains the text of the note that was left on an object.
     */
    protected String note;

    /**
     * The 'belonging_to' property indicates the IGC asset that this note is attached to.
     */
    protected Reference belonging_to;

    /**
     * The 'subject' property defines the subject of the note that was left on an object.
     */
    protected String subject;

    /**
     * The 'type' property defines the type of note this is (eg. "Informational", "Action", or "Other").
     */
    protected String type;

    /**
     * The 'status' property defines the status of the note (eg. "Open", "Pending", or "Closed").
     */
    protected String status;

    /**
     * Retrieve the text of the IGC note.
     *
     * @return String
     * @see #note
     */
    @JsonProperty("note")
    public String getNote() { return note; }

    /**
     * Set the text of the IGC note.
     *
     * @param note text of the IGC note
     * @see #note
     */
    @JsonProperty("note")
    public void setNote(String note) { this.note = note; }

    /**
     * Retrieve the asset to which this IGC note belongs.
     *
     * @return Reference
     * @see #belonging_to
     */
    @JsonProperty("belonging_to")
    public Reference getBelongingTo() { return belonging_to; }

    /**
     * Set the asset to which this IGC note belongs.
     *
     * @param belonging_to the asset to which this note belongs
     * @see #belonging_to
     */
    @JsonProperty("belonging_to")
    public void setBelongingTo(Reference belonging_to) { this.belonging_to = belonging_to; }

    /**
     * Retrieve the subject of the note.
     *
     * @return String
     * @see #subject
     */
    @JsonProperty("subject")
    public String getSubject() { return subject; }

    /**
     * Set the subject of the note.
     *
     * @param subject of the note
     * @see #subject
     */
    @JsonProperty("subject")
    public void setSubject(String subject) { this.subject = subject; }

    /**
     * Retrieve the type of the IGC note.
     *
     * @return String
     * @see #type
     */
    @JsonProperty("type")
    public String getTheType() { return type; }

    /**
     * Set the type of the IGC note.
     *
     * @param type of the IGC note
     * @see #type
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

    /**
     * Retrieve the status of the note.
     *
     * @return String
     * @see #status
     */
    @JsonProperty("status")
    public String getStatus() { return status; }

    /**
     * Set the status of the note.
     *
     * @param status of the note
     * @see #status
     */
    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

    /**
     * Hard-codes the type of a Note to {@literal note}, as there is no '_type' property on a an IGC note object.
     *
     * @return String
     */
    @Override
    public String getType() { return "note"; }

    /**
     * Sets the name of the note to its RID, as Note objects do not have any name.
     *
     * @return String
     */
    @Override
    public String getName() { return getId(); }

}
