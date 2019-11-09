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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code classification} asset type in IGC, displayed as '{@literal Classification}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Classification.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("classification")
public class Classification extends Reference {

    @JsonProperty("classifies_asset")
    protected MainObject classifiesAsset;

    @JsonProperty("column_analysis")
    protected ItemList<MainObject> columnAnalysis;

    @JsonProperty("confidencePercent")
    protected Number confidencepercent;

    @JsonProperty("data_class")
    protected DataClass dataClass;

    @JsonProperty("date")
    protected Date date;

    @JsonProperty("detected")
    protected Boolean detected;

    /**
     * Valid values are:
     * <ul>
     *   <li>Candidate (displayed in the UI as 'Candidate')</li>
     *   <li>Inferred (displayed in the UI as 'Inferred')</li>
     * </ul>
     */
    @JsonProperty("detectedState")
    protected String detectedstate;

    @JsonProperty("selected")
    protected Boolean selected;

    @JsonProperty("threshold")
    protected Number threshold;

    @JsonProperty("value_frequency")
    protected Number valueFrequency;

    /**
     * Retrieve the {@code classifies_asset} property (displayed as '{@literal Asset}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("classifies_asset")
    public MainObject getClassifiesAsset() { return this.classifiesAsset; }

    /**
     * Set the {@code classifies_asset} property (displayed as {@code Asset}) of the object.
     * @param classifiesAsset the value to set
     */
    @JsonProperty("classifies_asset")
    public void setClassifiesAsset(MainObject classifiesAsset) { this.classifiesAsset = classifiesAsset; }

    /**
     * Retrieve the {@code column_analysis} property (displayed as '{@literal Detected from Column Analysis}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("column_analysis")
    public ItemList<MainObject> getColumnAnalysis() { return this.columnAnalysis; }

    /**
     * Set the {@code column_analysis} property (displayed as {@code Detected from Column Analysis}) of the object.
     * @param columnAnalysis the value to set
     */
    @JsonProperty("column_analysis")
    public void setColumnAnalysis(ItemList<MainObject> columnAnalysis) { this.columnAnalysis = columnAnalysis; }

    /**
     * Retrieve the {@code confidencePercent} property (displayed as '{@literal Confidence (Percent)}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("confidencePercent")
    public Number getConfidencepercent() { return this.confidencepercent; }

    /**
     * Set the {@code confidencePercent} property (displayed as {@code Confidence (Percent)}) of the object.
     * @param confidencepercent the value to set
     */
    @JsonProperty("confidencePercent")
    public void setConfidencepercent(Number confidencepercent) { this.confidencepercent = confidencepercent; }

    /**
     * Retrieve the {@code data_class} property (displayed as '{@literal Data Class}') of the object.
     * @return {@code DataClass}
     */
    @JsonProperty("data_class")
    public DataClass getDataClass() { return this.dataClass; }

    /**
     * Set the {@code data_class} property (displayed as {@code Data Class}) of the object.
     * @param dataClass the value to set
     */
    @JsonProperty("data_class")
    public void setDataClass(DataClass dataClass) { this.dataClass = dataClass; }

    /**
     * Retrieve the {@code date} property (displayed as '{@literal Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("date")
    public Date getDate() { return this.date; }

    /**
     * Set the {@code date} property (displayed as {@code Date}) of the object.
     * @param date the value to set
     */
    @JsonProperty("date")
    public void setDate(Date date) { this.date = date; }

    /**
     * Retrieve the {@code detected} property (displayed as '{@literal Detected}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("detected")
    public Boolean getDetected() { return this.detected; }

    /**
     * Set the {@code detected} property (displayed as {@code Detected}) of the object.
     * @param detected the value to set
     */
    @JsonProperty("detected")
    public void setDetected(Boolean detected) { this.detected = detected; }

    /**
     * Retrieve the {@code detectedState} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("detectedState")
    public String getDetectedstate() { return this.detectedstate; }

    /**
     * Set the {@code detectedState} property (displayed as {@code Type}) of the object.
     * @param detectedstate the value to set
     */
    @JsonProperty("detectedState")
    public void setDetectedstate(String detectedstate) { this.detectedstate = detectedstate; }

    /**
     * Retrieve the {@code selected} property (displayed as '{@literal Selected}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("selected")
    public Boolean getSelected() { return this.selected; }

    /**
     * Set the {@code selected} property (displayed as {@code Selected}) of the object.
     * @param selected the value to set
     */
    @JsonProperty("selected")
    public void setSelected(Boolean selected) { this.selected = selected; }

    /**
     * Retrieve the {@code threshold} property (displayed as '{@literal Threshold}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("threshold")
    public Number getThreshold() { return this.threshold; }

    /**
     * Set the {@code threshold} property (displayed as {@code Threshold}) of the object.
     * @param threshold the value to set
     */
    @JsonProperty("threshold")
    public void setThreshold(Number threshold) { this.threshold = threshold; }

    /**
     * Retrieve the {@code value_frequency} property (displayed as '{@literal Value Frequency}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("value_frequency")
    public Number getValueFrequency() { return this.valueFrequency; }

    /**
     * Set the {@code value_frequency} property (displayed as {@code Value Frequency}) of the object.
     * @param valueFrequency the value to set
     */
    @JsonProperty("value_frequency")
    public void setValueFrequency(Number valueFrequency) { this.valueFrequency = valueFrequency; }

}
