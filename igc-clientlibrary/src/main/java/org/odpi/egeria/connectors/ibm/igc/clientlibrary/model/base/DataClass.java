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
import java.util.List;

/**
 * POJO for the {@code data_class} asset type in IGC, displayed as '{@literal Data Class}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataClass.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_class")
public class DataClass extends InformationAsset {

    @JsonProperty("class_code")
    protected String classCode;

    @JsonProperty("classifications_selected")
    protected ItemList<Classificationenabledgroup> classificationsSelected;

    @JsonProperty("classified_assets_detected")
    protected ItemList<Classification> classifiedAssetsDetected;

    @JsonProperty("contains_data_classes")
    protected ItemList<DataClass> containsDataClasses;

    /**
     * Valid values are:
     * <ul>
     *   <li>Undefined (displayed in the UI as 'Unspecified')</li>
     *   <li>Regex (displayed in the UI as 'Regex')</li>
     *   <li>ValidValues (displayed in the UI as 'Valid Values')</li>
     * </ul>
     */
    @JsonProperty("data_class_type_single")
    protected String dataClassTypeSingle;

    /**
     * Valid values are:
     * <ul>
     *   <li>numeric (displayed in the UI as 'Numeric')</li>
     *   <li>string (displayed in the UI as 'String')</li>
     *   <li>date (displayed in the UI as 'Date')</li>
     *   <li>time (displayed in the UI as 'Time')</li>
     *   <li>timestamp (displayed in the UI as 'Timestamp')</li>
     * </ul>
     */
    @JsonProperty("data_type_filter_elements_enum")
    protected List<String> dataTypeFilterElementsEnum;

    @JsonProperty("default_threshold")
    protected Number defaultThreshold;

    @JsonProperty("enabled")
    protected Boolean enabled;

    @JsonProperty("example")
    protected String example;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("java_class_name_single")
    protected String javaClassNameSingle;

    @JsonProperty("length_filter_max")
    protected Number lengthFilterMax;

    @JsonProperty("length_filter_min")
    protected Number lengthFilterMin;

    @JsonProperty("parent_data_class")
    protected DataClass parentDataClass;

    @JsonProperty("regular_expression_single")
    protected String regularExpressionSingle;

    @JsonProperty("valid_value_strings")
    protected List<String> validValueStrings;

    @JsonProperty("valid_values_case_sensitive")
    protected Boolean validValuesCaseSensitive;

    /**
     * Retrieve the {@code class_code} property (displayed as '{@literal Class Code}') of the object.
     * @return {@code String}
     */
    @JsonProperty("class_code")
    public String getClassCode() { return this.classCode; }

    /**
     * Set the {@code class_code} property (displayed as {@code Class Code}) of the object.
     * @param classCode the value to set
     */
    @JsonProperty("class_code")
    public void setClassCode(String classCode) { this.classCode = classCode; }

    /**
     * Retrieve the {@code classifications_selected} property (displayed as '{@literal Selected Data Classifications}') of the object.
     * @return {@code ItemList<Classificationenabledgroup>}
     */
    @JsonProperty("classifications_selected")
    public ItemList<Classificationenabledgroup> getClassificationsSelected() { return this.classificationsSelected; }

    /**
     * Set the {@code classifications_selected} property (displayed as {@code Selected Data Classifications}) of the object.
     * @param classificationsSelected the value to set
     */
    @JsonProperty("classifications_selected")
    public void setClassificationsSelected(ItemList<Classificationenabledgroup> classificationsSelected) { this.classificationsSelected = classificationsSelected; }

    /**
     * Retrieve the {@code classified_assets_detected} property (displayed as '{@literal Detected Data Classifications}') of the object.
     * @return {@code ItemList<Classification>}
     */
    @JsonProperty("classified_assets_detected")
    public ItemList<Classification> getClassifiedAssetsDetected() { return this.classifiedAssetsDetected; }

    /**
     * Set the {@code classified_assets_detected} property (displayed as {@code Detected Data Classifications}) of the object.
     * @param classifiedAssetsDetected the value to set
     */
    @JsonProperty("classified_assets_detected")
    public void setClassifiedAssetsDetected(ItemList<Classification> classifiedAssetsDetected) { this.classifiedAssetsDetected = classifiedAssetsDetected; }

    /**
     * Retrieve the {@code contains_data_classes} property (displayed as '{@literal Contains Data Classes}') of the object.
     * @return {@code ItemList<DataClass>}
     */
    @JsonProperty("contains_data_classes")
    public ItemList<DataClass> getContainsDataClasses() { return this.containsDataClasses; }

    /**
     * Set the {@code contains_data_classes} property (displayed as {@code Contains Data Classes}) of the object.
     * @param containsDataClasses the value to set
     */
    @JsonProperty("contains_data_classes")
    public void setContainsDataClasses(ItemList<DataClass> containsDataClasses) { this.containsDataClasses = containsDataClasses; }

    /**
     * Retrieve the {@code data_class_type_single} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_class_type_single")
    public String getDataClassTypeSingle() { return this.dataClassTypeSingle; }

    /**
     * Set the {@code data_class_type_single} property (displayed as {@code Type}) of the object.
     * @param dataClassTypeSingle the value to set
     */
    @JsonProperty("data_class_type_single")
    public void setDataClassTypeSingle(String dataClassTypeSingle) { this.dataClassTypeSingle = dataClassTypeSingle; }

    /**
     * Retrieve the {@code data_type_filter_elements_enum} property (displayed as '{@literal Data Type}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("data_type_filter_elements_enum")
    public List<String> getDataTypeFilterElementsEnum() { return this.dataTypeFilterElementsEnum; }

    /**
     * Set the {@code data_type_filter_elements_enum} property (displayed as {@code Data Type}) of the object.
     * @param dataTypeFilterElementsEnum the value to set
     */
    @JsonProperty("data_type_filter_elements_enum")
    public void setDataTypeFilterElementsEnum(List<String> dataTypeFilterElementsEnum) { this.dataTypeFilterElementsEnum = dataTypeFilterElementsEnum; }

    /**
     * Retrieve the {@code default_threshold} property (displayed as '{@literal Threshold (Percent)}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("default_threshold")
    public Number getDefaultThreshold() { return this.defaultThreshold; }

    /**
     * Set the {@code default_threshold} property (displayed as {@code Threshold (Percent)}) of the object.
     * @param defaultThreshold the value to set
     */
    @JsonProperty("default_threshold")
    public void setDefaultThreshold(Number defaultThreshold) { this.defaultThreshold = defaultThreshold; }

    /**
     * Retrieve the {@code enabled} property (displayed as '{@literal Enabled}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("enabled")
    public Boolean getEnabled() { return this.enabled; }

    /**
     * Set the {@code enabled} property (displayed as {@code Enabled}) of the object.
     * @param enabled the value to set
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    /**
     * Retrieve the {@code example} property (displayed as '{@literal Example}') of the object.
     * @return {@code String}
     */
    @JsonProperty("example")
    public String getExample() { return this.example; }

    /**
     * Set the {@code example} property (displayed as {@code Example}) of the object.
     * @param example the value to set
     */
    @JsonProperty("example")
    public void setExample(String example) { this.example = example; }

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
     * Retrieve the {@code java_class_name_single} property (displayed as '{@literal JAVA Class}') of the object.
     * @return {@code String}
     */
    @JsonProperty("java_class_name_single")
    public String getJavaClassNameSingle() { return this.javaClassNameSingle; }

    /**
     * Set the {@code java_class_name_single} property (displayed as {@code JAVA Class}) of the object.
     * @param javaClassNameSingle the value to set
     */
    @JsonProperty("java_class_name_single")
    public void setJavaClassNameSingle(String javaClassNameSingle) { this.javaClassNameSingle = javaClassNameSingle; }

    /**
     * Retrieve the {@code length_filter_max} property (displayed as '{@literal Maximum Data Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("length_filter_max")
    public Number getLengthFilterMax() { return this.lengthFilterMax; }

    /**
     * Set the {@code length_filter_max} property (displayed as {@code Maximum Data Length}) of the object.
     * @param lengthFilterMax the value to set
     */
    @JsonProperty("length_filter_max")
    public void setLengthFilterMax(Number lengthFilterMax) { this.lengthFilterMax = lengthFilterMax; }

    /**
     * Retrieve the {@code length_filter_min} property (displayed as '{@literal Minimum Data Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("length_filter_min")
    public Number getLengthFilterMin() { return this.lengthFilterMin; }

    /**
     * Set the {@code length_filter_min} property (displayed as {@code Minimum Data Length}) of the object.
     * @param lengthFilterMin the value to set
     */
    @JsonProperty("length_filter_min")
    public void setLengthFilterMin(Number lengthFilterMin) { this.lengthFilterMin = lengthFilterMin; }

    /**
     * Retrieve the {@code parent_data_class} property (displayed as '{@literal Parent Data Class}') of the object.
     * @return {@code DataClass}
     */
    @JsonProperty("parent_data_class")
    public DataClass getParentDataClass() { return this.parentDataClass; }

    /**
     * Set the {@code parent_data_class} property (displayed as {@code Parent Data Class}) of the object.
     * @param parentDataClass the value to set
     */
    @JsonProperty("parent_data_class")
    public void setParentDataClass(DataClass parentDataClass) { this.parentDataClass = parentDataClass; }

    /**
     * Retrieve the {@code regular_expression_single} property (displayed as '{@literal Regular Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("regular_expression_single")
    public String getRegularExpressionSingle() { return this.regularExpressionSingle; }

    /**
     * Set the {@code regular_expression_single} property (displayed as {@code Regular Expression}) of the object.
     * @param regularExpressionSingle the value to set
     */
    @JsonProperty("regular_expression_single")
    public void setRegularExpressionSingle(String regularExpressionSingle) { this.regularExpressionSingle = regularExpressionSingle; }

    /**
     * Retrieve the {@code valid_value_strings} property (displayed as '{@literal Valid Values}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("valid_value_strings")
    public List<String> getValidValueStrings() { return this.validValueStrings; }

    /**
     * Set the {@code valid_value_strings} property (displayed as {@code Valid Values}) of the object.
     * @param validValueStrings the value to set
     */
    @JsonProperty("valid_value_strings")
    public void setValidValueStrings(List<String> validValueStrings) { this.validValueStrings = validValueStrings; }

    /**
     * Retrieve the {@code valid_values_case_sensitive} property (displayed as '{@literal Case Sensitive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("valid_values_case_sensitive")
    public Boolean getValidValuesCaseSensitive() { return this.validValuesCaseSensitive; }

    /**
     * Set the {@code valid_values_case_sensitive} property (displayed as {@code Case Sensitive}) of the object.
     * @param validValuesCaseSensitive the value to set
     */
    @JsonProperty("valid_values_case_sensitive")
    public void setValidValuesCaseSensitive(Boolean validValuesCaseSensitive) { this.validValuesCaseSensitive = validValuesCaseSensitive; }

}
