/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import java.util.Date;

/**
 * POJO for the {@code archivedtask} asset type in IGC, displayed as '{@literal ArchivedTask}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Archivedtask.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("archivedtask")
public class Archivedtask extends Reference {

    @JsonProperty("completion_date")
    protected Date completionDate;

    @JsonProperty("message")
    protected String message;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("requested_on")
    protected Date requestedOn;

    @JsonProperty("status")
    protected String status;

    /**
     * Retrieve the {@code completion_date} property (displayed as '{@literal Date Completed}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("completion_date")
    public Date getCompletionDate() { return this.completionDate; }

    /**
     * Set the {@code completion_date} property (displayed as {@code Date Completed}) of the object.
     * @param completionDate the value to set
     */
    @JsonProperty("completion_date")
    public void setCompletionDate(Date completionDate) { this.completionDate = completionDate; }

    /**
     * Retrieve the {@code message} property (displayed as '{@literal Status Message}') of the object.
     * @return {@code String}
     */
    @JsonProperty("message")
    public String getMessage() { return this.message; }

    /**
     * Set the {@code message} property (displayed as {@code Status Message}) of the object.
     * @param message the value to set
     */
    @JsonProperty("message")
    public void setMessage(String message) { this.message = message; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Asset Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Asset Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code requested_on} property (displayed as '{@literal Date Requested}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("requested_on")
    public Date getRequestedOn() { return this.requestedOn; }

    /**
     * Set the {@code requested_on} property (displayed as {@code Date Requested}) of the object.
     * @param requestedOn the value to set
     */
    @JsonProperty("requested_on")
    public void setRequestedOn(Date requestedOn) { this.requestedOn = requestedOn; }

    /**
     * Retrieve the {@code status} property (displayed as '{@literal Status}') of the object.
     * @return {@code String}
     */
    @JsonProperty("status")
    public String getStatus() { return this.status; }

    /**
     * Set the {@code status} property (displayed as {@code Status}) of the object.
     * @param status the value to set
     */
    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

}
