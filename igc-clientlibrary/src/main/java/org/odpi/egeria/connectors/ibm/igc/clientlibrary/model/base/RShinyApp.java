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
 * POJO for the {@code r_shiny_app} asset type in IGC, displayed as '{@literal RShiny App}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=RShinyApp.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("r_shiny_app")
public class RShinyApp extends InformationAsset {

    @JsonProperty("analytics_project")
    protected AnalyticsProject analyticsProject;

    @JsonProperty("contains_r_shiny_app_object")
    protected ItemList<RShinyAppObject> containsRShinyAppObject;

    @JsonProperty("first_published_date")
    protected Date firstPublishedDate;

    @JsonProperty("logical_name")
    protected String logicalName;

    @JsonProperty("package_name")
    protected String packageName;

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
     * Retrieve the {@code contains_r_shiny_app_object} property (displayed as '{@literal RShiny App Objects}') of the object.
     * @return {@code ItemList<RShinyAppObject>}
     */
    @JsonProperty("contains_r_shiny_app_object")
    public ItemList<RShinyAppObject> getContainsRShinyAppObject() { return this.containsRShinyAppObject; }

    /**
     * Set the {@code contains_r_shiny_app_object} property (displayed as {@code RShiny App Objects}) of the object.
     * @param containsRShinyAppObject the value to set
     */
    @JsonProperty("contains_r_shiny_app_object")
    public void setContainsRShinyAppObject(ItemList<RShinyAppObject> containsRShinyAppObject) { this.containsRShinyAppObject = containsRShinyAppObject; }

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
