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
 * POJO for the {@code model_eval_metric} asset type in IGC, displayed as '{@literal Model Eval Metric}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ModelEvalMetric.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("model_eval_metric")
public class ModelEvalMetric extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("eval_date")
    protected Date evalDate;

    @JsonProperty("metric_type")
    protected String metricType;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("of_model")
    protected AnalyticsModel ofModel;

    @JsonProperty("value")
    protected String value;

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
     * Retrieve the {@code eval_date} property (displayed as '{@literal Eval Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("eval_date")
    public Date getEvalDate() { return this.evalDate; }

    /**
     * Set the {@code eval_date} property (displayed as {@code Eval Date}) of the object.
     * @param evalDate the value to set
     */
    @JsonProperty("eval_date")
    public void setEvalDate(Date evalDate) { this.evalDate = evalDate; }

    /**
     * Retrieve the {@code metric_type} property (displayed as '{@literal Metric Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("metric_type")
    public String getMetricType() { return this.metricType; }

    /**
     * Set the {@code metric_type} property (displayed as {@code Metric Type}) of the object.
     * @param metricType the value to set
     */
    @JsonProperty("metric_type")
    public void setMetricType(String metricType) { this.metricType = metricType; }

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
     * Retrieve the {@code of_model} property (displayed as '{@literal Of Model}') of the object.
     * @return {@code AnalyticsModel}
     */
    @JsonProperty("of_model")
    public AnalyticsModel getOfModel() { return this.ofModel; }

    /**
     * Set the {@code of_model} property (displayed as {@code Of Model}) of the object.
     * @param ofModel the value to set
     */
    @JsonProperty("of_model")
    public void setOfModel(AnalyticsModel ofModel) { this.ofModel = ofModel; }

    /**
     * Retrieve the {@code value} property (displayed as '{@literal Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("value")
    public String getValue() { return this.value; }

    /**
     * Set the {@code value} property (displayed as {@code Value}) of the object.
     * @param value the value to set
     */
    @JsonProperty("value")
    public void setValue(String value) { this.value = value; }

}
