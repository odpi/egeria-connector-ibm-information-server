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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code analytics_script} asset type in IGC, displayed as '{@literal Data Science Script}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AnalyticsScript.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("analytics_script")
public class AnalyticsScript extends MainObject {

    @JsonProperty("analytics_project")
    protected AnalyticsProject analyticsProject;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("data_file")
    protected MainObject dataFile;

    @JsonProperty("first_published_date")
    protected Date firstPublishedDate;

    @JsonProperty("logical_name")
    protected String logicalName;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("package_name")
    protected String packageName;

    @JsonProperty("script_type")
    protected String scriptType;

    @JsonProperty("system_equiv_id")
    protected String systemEquivId;

    @JsonProperty("url")
    protected String url;

    /**
     * Retrieve the {@code analytics_project} property (displayed as '{@literal Data Science Project}') of the object.
     * @return {@code AnalyticsProject}
     */
    @JsonProperty("analytics_project")
    public AnalyticsProject getAnalyticsProject() { return this.analyticsProject; }

    /**
     * Set the {@code analytics_project} property (displayed as {@code Data Science Project}) of the object.
     * @param analyticsProject the value to set
     */
    @JsonProperty("analytics_project")
    public void setAnalyticsProject(AnalyticsProject analyticsProject) { this.analyticsProject = analyticsProject; }

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code data_file} property (displayed as '{@literal Data File}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("data_file")
    public MainObject getDataFile() { return this.dataFile; }

    /**
     * Set the {@code data_file} property (displayed as {@code Data File}) of the object.
     * @param dataFile the value to set
     */
    @JsonProperty("data_file")
    public void setDataFile(MainObject dataFile) { this.dataFile = dataFile; }

    /**
     * Retrieve the {@code first_published_date} property (displayed as '{@literal First Published Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("first_published_date")
    public Date getFirstPublishedDate() { return this.firstPublishedDate; }

    /**
     * Set the {@code first_published_date} property (displayed as {@code First Published Date}) of the object.
     * @param firstPublishedDate the value to set
     */
    @JsonProperty("first_published_date")
    public void setFirstPublishedDate(Date firstPublishedDate) { this.firstPublishedDate = firstPublishedDate; }

    /**
     * Retrieve the {@code logical_name} property (displayed as '{@literal Logical Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("logical_name")
    public String getLogicalName() { return this.logicalName; }

    /**
     * Set the {@code logical_name} property (displayed as {@code Logical Name}) of the object.
     * @param logicalName the value to set
     */
    @JsonProperty("logical_name")
    public void setLogicalName(String logicalName) { this.logicalName = logicalName; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

    /**
     * Retrieve the {@code native_id} property (displayed as '{@literal Native ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_id")
    public String getNativeId() { return this.nativeId; }

    /**
     * Set the {@code native_id} property (displayed as {@code Native ID}) of the object.
     * @param nativeId the value to set
     */
    @JsonProperty("native_id")
    public void setNativeId(String nativeId) { this.nativeId = nativeId; }

    /**
     * Retrieve the {@code package_name} property (displayed as '{@literal Package Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("package_name")
    public String getPackageName() { return this.packageName; }

    /**
     * Set the {@code package_name} property (displayed as {@code Package Name}) of the object.
     * @param packageName the value to set
     */
    @JsonProperty("package_name")
    public void setPackageName(String packageName) { this.packageName = packageName; }

    /**
     * Retrieve the {@code script_type} property (displayed as '{@literal Script Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("script_type")
    public String getScriptType() { return this.scriptType; }

    /**
     * Set the {@code script_type} property (displayed as {@code Script Type}) of the object.
     * @param scriptType the value to set
     */
    @JsonProperty("script_type")
    public void setScriptType(String scriptType) { this.scriptType = scriptType; }

    /**
     * Retrieve the {@code system_equiv_id} property (displayed as '{@literal System Equiv Id}') of the object.
     * @return {@code String}
     */
    @JsonProperty("system_equiv_id")
    public String getSystemEquivId() { return this.systemEquivId; }

    /**
     * Set the {@code system_equiv_id} property (displayed as {@code System Equiv Id}) of the object.
     * @param systemEquivId the value to set
     */
    @JsonProperty("system_equiv_id")
    public void setSystemEquivId(String systemEquivId) { this.systemEquivId = systemEquivId; }

    /**
     * Retrieve the {@code url} property (displayed as '{@literal URL}') of the object.
     * @return {@code String}
     */
    @JsonProperty("url")
    public String getTheUrl() { return this.url; }

    /**
     * Set the {@code url} property (displayed as {@code URL}) of the object.
     * @param url the value to set
     */
    @JsonProperty("url")
    public void setTheUrl(String url) { this.url = url; }

}
