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

/**
 * POJO for the {@code link} asset type in IGC, displayed as '{@literal Link}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Link.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("link")
public class Link extends Reference {

    @JsonProperty("input_stages")
    protected Stage inputStages;

    @JsonProperty("job_or_container")
    protected ItemList<MainObject> jobOrContainer;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("output_stages")
    protected Stage outputStages;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("stage_columns")
    protected ItemList<DataItem> stageColumns;

    /**
     * Retrieve the {@code input_stages} property (displayed as '{@literal Input Stages}') of the object.
     * @return {@code Stage}
     */
    @JsonProperty("input_stages")
    public Stage getInputStages() { return this.inputStages; }

    /**
     * Set the {@code input_stages} property (displayed as {@code Input Stages}) of the object.
     * @param inputStages the value to set
     */
    @JsonProperty("input_stages")
    public void setInputStages(Stage inputStages) { this.inputStages = inputStages; }

    /**
     * Retrieve the {@code job_or_container} property (displayed as '{@literal Job or Container}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("job_or_container")
    public ItemList<MainObject> getJobOrContainer() { return this.jobOrContainer; }

    /**
     * Set the {@code job_or_container} property (displayed as {@code Job or Container}) of the object.
     * @param jobOrContainer the value to set
     */
    @JsonProperty("job_or_container")
    public void setJobOrContainer(ItemList<MainObject> jobOrContainer) { this.jobOrContainer = jobOrContainer; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

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
     * Retrieve the {@code output_stages} property (displayed as '{@literal Output Stages}') of the object.
     * @return {@code Stage}
     */
    @JsonProperty("output_stages")
    public Stage getOutputStages() { return this.outputStages; }

    /**
     * Set the {@code output_stages} property (displayed as {@code Output Stages}) of the object.
     * @param outputStages the value to set
     */
    @JsonProperty("output_stages")
    public void setOutputStages(Stage outputStages) { this.outputStages = outputStages; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    /**
     * Retrieve the {@code stage_columns} property (displayed as '{@literal Stage Columns}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("stage_columns")
    public ItemList<DataItem> getStageColumns() { return this.stageColumns; }

    /**
     * Set the {@code stage_columns} property (displayed as {@code Stage Columns}) of the object.
     * @param stageColumns the value to set
     */
    @JsonProperty("stage_columns")
    public void setStageColumns(ItemList<DataItem> stageColumns) { this.stageColumns = stageColumns; }

}
