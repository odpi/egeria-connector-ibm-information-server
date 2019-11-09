/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events;

import com.fasterxml.jackson.annotation.*;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The payload contents of all Information Analyzer-specific events on the InfosphereEvents topic.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfosphereEventsIAEvent extends InfosphereEvents {

    @JsonIgnore
    public static final String COL_CLASSIFIED = "IA_COLUMN_CLASSIFIED_EVENT";

    @JsonIgnore
    public static final String COL_ANALYZED = "IA_COLUMN_ANALYZED_EVENT";

    @JsonIgnore
    public static final String TBL_PUBLISHED = "IA_TABLE_RESULTS_PUBLISHED";

    protected String projectRid;
    protected String projectName;
    protected String user;
    protected String tamRid;
    protected String camRid;
    protected String dataCollectionRid;
    protected String dataFieldRid;

    /**
     * Retrieve the Repository ID (RID) of the Information Analyzer project.
     * @return String
     */
    @JsonProperty("projectRid")
    public String getProjectRid() { return this.projectRid; }

    /**
     * Set the Repository ID (RID) of the Information Analyzer project.
     * @param projectRid of the Information Analyzer project
     */
    @JsonProperty("projectRid")
    public void setProjectRid(String projectRid) { this.projectRid = projectRid; }

    /**
     * Retrieve the name of the Information Analyzer project. This will only be present on IA_PROJECT_* eventTypes.
     * @return String
     */
    @JsonProperty("projectName")
    public String getProjectName() { return this.projectName; }

    /**
     * Set the name of the Information Analyzer project. This should only be present on IA_PROJECT_* eventTypes.
     * @param projectName of the Information Analyzer project
     */
    @JsonProperty("projectName")
    public void setProjectName(String projectName) { this.projectName = projectName; }

    /**
     * Retrieve the user who took the action on the Information Analyzer project. This will only be present on
     * IA_PROJECT_* eventTypes.
     * @return String
     */
    @JsonProperty("user")
    public String getUser() { return this.user; }

    /**
     * Set the user who took the action on the Information Analyzer project. This should only be present on
     * IA_PROJECT_* eventTypes.
     * @param user who took the action on the Information Analyzer project
     */
    @JsonProperty("user")
    public void setUser(String user) { this.user = user; }

    /**
     * Retrieve the Repository ID (RID) of the Table Analysis Master object. Unfortunately these objects are currently
     * not queryable through IGC's REST API.
     * @return String
     */
    @JsonProperty("tamRid")
    public String getTamRid() { return this.tamRid; }

    /**
     * Set the Repository ID (RID) of the Table Analysis Master object. Unfortunately these objects are currently not
     * queryable through IGC's REST API.
     * @param tamRid of the Table Analysis Master object
     */
    @JsonProperty("tamRid")
    public void setTamRid(String tamRid) { this.tamRid = tamRid; }

    /**
     * Retrieve the Repository ID (RID) of the Column Analysis Master object. Unfortunately these objects are currently
     * not queryable through IGC's REST API.
     * @return String
     */
    @JsonProperty("camRid")
    public String getCamRid() { return this.camRid; }

    /**
     * Set the Repository ID (RID) of the Column Analysis Master object. Unfortunately these objects are currently not
     * queryable through IGC's REST API.
     * @param camRid of the Column Analysis Master object
     */
    @JsonProperty("camRid")
    public void setCamRid(String camRid) { this.camRid = camRid; }

    /**
     * Retrieve the Repository ID (RID) of the table (database_table or data_file_record) that has been published as
     * part of the event.
     * @return String
     */
    @JsonProperty("dataCollectionRid")
    public String getDataCollectionRid() { return this.dataCollectionRid; }

    /**
     * Set the Repository ID (RID) of the table (database_table or data_file_record) that has been published as part
     * of the event.
     * @param dataCollectionRid of the table
     */
    @JsonProperty("dataCollectionRid")
    public void setDataCollectionRid(String dataCollectionRid) { this.dataCollectionRid = dataCollectionRid; }

    /**
     * Retrieve the Repository ID (RID) of the field (database_column or data_file_field) that has been analyzed or
     * classified as part of the event.
     * @return String
     */
    @JsonProperty("dataFieldRid")
    public String getDataFieldRid() { return this.dataFieldRid; }

    /**
     * Set the Repository ID (RID) of the field (database_column or data_file_field) that has been analyzed or
     * classified as part of the event.
     * @param dataFieldRid of the field
     */
    @JsonProperty("dataFieldRid")
    public void setDataFieldRid(String dataFieldRid) { this.dataFieldRid = dataFieldRid; }

}
