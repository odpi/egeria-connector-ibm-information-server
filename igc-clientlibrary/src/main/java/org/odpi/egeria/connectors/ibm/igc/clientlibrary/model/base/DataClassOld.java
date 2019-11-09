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

/**
 * POJO for the {@code data_class_old} asset type in IGC, displayed as '{@literal Data Class}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataClassOld.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_class_old")
public class DataClassOld extends MainObject {

    @JsonProperty("class_code")
    protected String classCode;

    @JsonProperty("classifies_data_field")
    protected ItemList<DataItem> classifiesDataField;

    @JsonProperty("has_sub_data_class")
    protected ItemList<DataClassOld> hasSubDataClass;

    @JsonProperty("inferred_by_df_analysis_summary")
    protected ItemList<ColumnAnalysisSummary> inferredByDfAnalysisSummary;

    @JsonProperty("is_sub_of_data_class")
    protected DataClassOld isSubOfDataClass;

    @JsonProperty("is_user_defined")
    protected Boolean isUserDefined;

    @JsonProperty("native_id")
    protected String nativeId;

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
     * Retrieve the {@code classifies_data_field} property (displayed as '{@literal Classifies Data Field}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("classifies_data_field")
    public ItemList<DataItem> getClassifiesDataField() { return this.classifiesDataField; }

    /**
     * Set the {@code classifies_data_field} property (displayed as {@code Classifies Data Field}) of the object.
     * @param classifiesDataField the value to set
     */
    @JsonProperty("classifies_data_field")
    public void setClassifiesDataField(ItemList<DataItem> classifiesDataField) { this.classifiesDataField = classifiesDataField; }

    /**
     * Retrieve the {@code has_sub_data_class} property (displayed as '{@literal Has Sub Data Class}') of the object.
     * @return {@code ItemList<DataClassOld>}
     */
    @JsonProperty("has_sub_data_class")
    public ItemList<DataClassOld> getHasSubDataClass() { return this.hasSubDataClass; }

    /**
     * Set the {@code has_sub_data_class} property (displayed as {@code Has Sub Data Class}) of the object.
     * @param hasSubDataClass the value to set
     */
    @JsonProperty("has_sub_data_class")
    public void setHasSubDataClass(ItemList<DataClassOld> hasSubDataClass) { this.hasSubDataClass = hasSubDataClass; }

    /**
     * Retrieve the {@code inferred_by_df_analysis_summary} property (displayed as '{@literal Inferred By DF Analysis Summary}') of the object.
     * @return {@code ItemList<ColumnAnalysisSummary>}
     */
    @JsonProperty("inferred_by_df_analysis_summary")
    public ItemList<ColumnAnalysisSummary> getInferredByDfAnalysisSummary() { return this.inferredByDfAnalysisSummary; }

    /**
     * Set the {@code inferred_by_df_analysis_summary} property (displayed as {@code Inferred By DF Analysis Summary}) of the object.
     * @param inferredByDfAnalysisSummary the value to set
     */
    @JsonProperty("inferred_by_df_analysis_summary")
    public void setInferredByDfAnalysisSummary(ItemList<ColumnAnalysisSummary> inferredByDfAnalysisSummary) { this.inferredByDfAnalysisSummary = inferredByDfAnalysisSummary; }

    /**
     * Retrieve the {@code is_sub_of_data_class} property (displayed as '{@literal Is Sub Of Data Class}') of the object.
     * @return {@code DataClassOld}
     */
    @JsonProperty("is_sub_of_data_class")
    public DataClassOld getIsSubOfDataClass() { return this.isSubOfDataClass; }

    /**
     * Set the {@code is_sub_of_data_class} property (displayed as {@code Is Sub Of Data Class}) of the object.
     * @param isSubOfDataClass the value to set
     */
    @JsonProperty("is_sub_of_data_class")
    public void setIsSubOfDataClass(DataClassOld isSubOfDataClass) { this.isSubOfDataClass = isSubOfDataClass; }

    /**
     * Retrieve the {@code is_user_defined} property (displayed as '{@literal Is User Defined}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_user_defined")
    public Boolean getIsUserDefined() { return this.isUserDefined; }

    /**
     * Set the {@code is_user_defined} property (displayed as {@code Is User Defined}) of the object.
     * @param isUserDefined the value to set
     */
    @JsonProperty("is_user_defined")
    public void setIsUserDefined(Boolean isUserDefined) { this.isUserDefined = isUserDefined; }

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

}
