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
 * POJO for the {@code analytics_model} asset type in IGC, displayed as '{@literal Data Science Model}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=AnalyticsModel.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("analytics_model")
public class AnalyticsModel extends InformationAsset {

    @JsonProperty("analytics_project")
    protected AnalyticsProject analyticsProject;

    @JsonProperty("eval_metric")
    protected ItemList<ModelEvalMetric> evalMetric;

    @JsonProperty("first_published_date")
    protected Date firstPublishedDate;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("input_training_column")
    protected ItemList<DataField> inputTrainingColumn;

    @JsonProperty("input_training_notebook")
    protected ItemList<Notebook> inputTrainingNotebook;

    @JsonProperty("input_training_script")
    protected ItemList<AnalyticsScript> inputTrainingScript;

    @JsonProperty("label_training_column")
    protected ItemList<DataField> labelTrainingColumn;

    @JsonProperty("logical_name")
    protected String logicalName;

    @JsonProperty("model_quality")
    protected String modelQuality;

    @JsonProperty("model_type")
    protected String modelType;

    @JsonProperty("package_name")
    protected String packageName;

    @JsonProperty("system_equiv_id")
    protected String systemEquivId;

    @JsonProperty("url")
    protected String url;

    @JsonProperty("version")
    protected Number version;

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
     * Retrieve the {@code eval_metric} property (displayed as '{@literal Eval Metric}') of the object.
     * @return {@code ItemList<ModelEvalMetric>}
     */
    @JsonProperty("eval_metric")
    public ItemList<ModelEvalMetric> getEvalMetric() { return this.evalMetric; }

    /**
     * Set the {@code eval_metric} property (displayed as {@code Eval Metric}) of the object.
     * @param evalMetric the value to set
     */
    @JsonProperty("eval_metric")
    public void setEvalMetric(ItemList<ModelEvalMetric> evalMetric) { this.evalMetric = evalMetric; }

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
     * Retrieve the {@code input_training_column} property (displayed as '{@literal Input Data Assets}') of the object.
     * @return {@code ItemList<DataField>}
     */
    @JsonProperty("input_training_column")
    public ItemList<DataField> getInputTrainingColumn() { return this.inputTrainingColumn; }

    /**
     * Set the {@code input_training_column} property (displayed as {@code Input Data Assets}) of the object.
     * @param inputTrainingColumn the value to set
     */
    @JsonProperty("input_training_column")
    public void setInputTrainingColumn(ItemList<DataField> inputTrainingColumn) { this.inputTrainingColumn = inputTrainingColumn; }

    /**
     * Retrieve the {@code input_training_notebook} property (displayed as '{@literal Notebooks}') of the object.
     * @return {@code ItemList<Notebook>}
     */
    @JsonProperty("input_training_notebook")
    public ItemList<Notebook> getInputTrainingNotebook() { return this.inputTrainingNotebook; }

    /**
     * Set the {@code input_training_notebook} property (displayed as {@code Notebooks}) of the object.
     * @param inputTrainingNotebook the value to set
     */
    @JsonProperty("input_training_notebook")
    public void setInputTrainingNotebook(ItemList<Notebook> inputTrainingNotebook) { this.inputTrainingNotebook = inputTrainingNotebook; }

    /**
     * Retrieve the {@code input_training_script} property (displayed as '{@literal Scripts}') of the object.
     * @return {@code ItemList<AnalyticsScript>}
     */
    @JsonProperty("input_training_script")
    public ItemList<AnalyticsScript> getInputTrainingScript() { return this.inputTrainingScript; }

    /**
     * Set the {@code input_training_script} property (displayed as {@code Scripts}) of the object.
     * @param inputTrainingScript the value to set
     */
    @JsonProperty("input_training_script")
    public void setInputTrainingScript(ItemList<AnalyticsScript> inputTrainingScript) { this.inputTrainingScript = inputTrainingScript; }

    /**
     * Retrieve the {@code label_training_column} property (displayed as '{@literal Output Data Assets}') of the object.
     * @return {@code ItemList<DataField>}
     */
    @JsonProperty("label_training_column")
    public ItemList<DataField> getLabelTrainingColumn() { return this.labelTrainingColumn; }

    /**
     * Set the {@code label_training_column} property (displayed as {@code Output Data Assets}) of the object.
     * @param labelTrainingColumn the value to set
     */
    @JsonProperty("label_training_column")
    public void setLabelTrainingColumn(ItemList<DataField> labelTrainingColumn) { this.labelTrainingColumn = labelTrainingColumn; }

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
     * Retrieve the {@code model_quality} property (displayed as '{@literal Quality}') of the object.
     * @return {@code String}
     */
    @JsonProperty("model_quality")
    public String getModelQuality() { return this.modelQuality; }

    /**
     * Set the {@code model_quality} property (displayed as {@code Quality}) of the object.
     * @param modelQuality the value to set
     */
    @JsonProperty("model_quality")
    public void setModelQuality(String modelQuality) { this.modelQuality = modelQuality; }

    /**
     * Retrieve the {@code model_type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("model_type")
    public String getModelType() { return this.modelType; }

    /**
     * Set the {@code model_type} property (displayed as {@code Type}) of the object.
     * @param modelType the value to set
     */
    @JsonProperty("model_type")
    public void setModelType(String modelType) { this.modelType = modelType; }

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

    /**
     * Retrieve the {@code version} property (displayed as '{@literal Version}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("version")
    public Number getVersion() { return this.version; }

    /**
     * Set the {@code version} property (displayed as {@code Version}) of the object.
     * @param version the value to set
     */
    @JsonProperty("version")
    public void setVersion(Number version) { this.version = version; }

}
