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
 * POJO for the {@code notebook} asset type in IGC, displayed as '{@literal Notebook}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Notebook.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("notebook")
public class Notebook extends InformationAsset {

    @JsonProperty("analytics_project")
    protected AnalyticsProject analyticsProject;

    @JsonProperty("environment")
    protected String environment;

    @JsonProperty("first_published_date")
    protected Date firstPublishedDate;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("input_training_notebook_inv")
    protected ItemList<AnalyticsModel> inputTrainingNotebookInv;

    @JsonProperty("logical_name")
    protected String logicalName;

    @JsonProperty("model_input_training_column")
    protected ItemList<DataField> modelInputTrainingColumn;

    @JsonProperty("package_name")
    protected String packageName;

    @JsonProperty("system_equiv_id")
    protected String systemEquivId;

    @JsonProperty("tool")
    protected String tool;

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
     * Retrieve the {@code environment} property (displayed as '{@literal Environment}') of the object.
     * @return {@code String}
     */
    @JsonProperty("environment")
    public String getEnvironment() { return this.environment; }

    /**
     * Set the {@code environment} property (displayed as {@code Environment}) of the object.
     * @param environment the value to set
     */
    @JsonProperty("environment")
    public void setEnvironment(String environment) { this.environment = environment; }

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
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

    /**
     * Retrieve the {@code input_training_notebook_inv} property (displayed as '{@literal Data Science Models}') of the object.
     * @return {@code ItemList<AnalyticsModel>}
     */
    @JsonProperty("input_training_notebook_inv")
    public ItemList<AnalyticsModel> getInputTrainingNotebookInv() { return this.inputTrainingNotebookInv; }

    /**
     * Set the {@code input_training_notebook_inv} property (displayed as {@code Data Science Models}) of the object.
     * @param inputTrainingNotebookInv the value to set
     */
    @JsonProperty("input_training_notebook_inv")
    public void setInputTrainingNotebookInv(ItemList<AnalyticsModel> inputTrainingNotebookInv) { this.inputTrainingNotebookInv = inputTrainingNotebookInv; }

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
     * Retrieve the {@code model_input_training_column} property (displayed as '{@literal Data Assets}') of the object.
     * @return {@code ItemList<DataField>}
     */
    @JsonProperty("model_input_training_column")
    public ItemList<DataField> getModelInputTrainingColumn() { return this.modelInputTrainingColumn; }

    /**
     * Set the {@code model_input_training_column} property (displayed as {@code Data Assets}) of the object.
     * @param modelInputTrainingColumn the value to set
     */
    @JsonProperty("model_input_training_column")
    public void setModelInputTrainingColumn(ItemList<DataField> modelInputTrainingColumn) { this.modelInputTrainingColumn = modelInputTrainingColumn; }

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
     * Retrieve the {@code tool} property (displayed as '{@literal Tool}') of the object.
     * @return {@code String}
     */
    @JsonProperty("tool")
    public String getTool() { return this.tool; }

    /**
     * Set the {@code tool} property (displayed as {@code Tool}) of the object.
     * @param tool the value to set
     */
    @JsonProperty("tool")
    public void setTool(String tool) { this.tool = tool; }

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
