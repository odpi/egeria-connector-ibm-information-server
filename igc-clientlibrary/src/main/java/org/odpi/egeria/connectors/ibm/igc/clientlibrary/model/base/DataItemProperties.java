/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

/**
 * POJO for the {@code data_item_properties} asset type in IGC, displayed as '{@literal Data Item Properties}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_item_properties")
public class DataItemProperties extends Reference {

    @JsonProperty("apt_field_prop")
    protected String aptFieldProp;

    @JsonProperty("association")
    protected String association;

    @JsonProperty("belonging_to_parameter_definition")
    protected Parameter belongingToParameterDefinition;

    @JsonProperty("column_definition")
    protected ColumnDefinition columnDefinition;

    @JsonProperty("date_mask")
    protected String dateMask;

    @JsonProperty("depend_field")
    protected String dependField;

    @JsonProperty("field_type")
    protected String fieldType;

    @JsonProperty("filler_parents")
    protected String fillerParents;

    @JsonProperty("flow_variable")
    protected DataItem flowVariable;

    @JsonProperty("has_sign_indicator")
    protected Boolean hasSignIndicator;

    @JsonProperty("is_u_string")
    protected Boolean isUString;

    @JsonProperty("nls_map")
    protected String nlsMap;

    @JsonProperty("pad_char")
    protected String padChar;

    @JsonProperty("redefined_field")
    protected String redefinedField;

    @JsonProperty("scale")
    protected Number scale;

    @JsonProperty("scd_purpose")
    protected Number scdPurpose;

    @JsonProperty("sign_option")
    protected Number signOption;

    @JsonProperty("sync_indicator")
    protected Boolean syncIndicator;

    @JsonProperty("usage")
    protected String usage;

    /**
     * Retrieve the {@code apt_field_prop} property (displayed as '{@literal APT Field Prop}') of the object.
     * @return {@code String}
     */
    @JsonProperty("apt_field_prop")
    public String getAptFieldProp() { return this.aptFieldProp; }

    /**
     * Set the {@code apt_field_prop} property (displayed as {@code APT Field Prop}) of the object.
     * @param aptFieldProp the value to set
     */
    @JsonProperty("apt_field_prop")
    public void setAptFieldProp(String aptFieldProp) { this.aptFieldProp = aptFieldProp; }

    /**
     * Retrieve the {@code association} property (displayed as '{@literal Association}') of the object.
     * @return {@code String}
     */
    @JsonProperty("association")
    public String getAssociation() { return this.association; }

    /**
     * Set the {@code association} property (displayed as {@code Association}) of the object.
     * @param association the value to set
     */
    @JsonProperty("association")
    public void setAssociation(String association) { this.association = association; }

    /**
     * Retrieve the {@code belonging_to_parameter_definition} property (displayed as '{@literal Belonging to Parameter Definition}') of the object.
     * @return {@code Parameter}
     */
    @JsonProperty("belonging_to_parameter_definition")
    public Parameter getBelongingToParameterDefinition() { return this.belongingToParameterDefinition; }

    /**
     * Set the {@code belonging_to_parameter_definition} property (displayed as {@code Belonging to Parameter Definition}) of the object.
     * @param belongingToParameterDefinition the value to set
     */
    @JsonProperty("belonging_to_parameter_definition")
    public void setBelongingToParameterDefinition(Parameter belongingToParameterDefinition) { this.belongingToParameterDefinition = belongingToParameterDefinition; }

    /**
     * Retrieve the {@code column_definition} property (displayed as '{@literal Column Definition}') of the object.
     * @return {@code ColumnDefinition}
     */
    @JsonProperty("column_definition")
    public ColumnDefinition getColumnDefinition() { return this.columnDefinition; }

    /**
     * Set the {@code column_definition} property (displayed as {@code Column Definition}) of the object.
     * @param columnDefinition the value to set
     */
    @JsonProperty("column_definition")
    public void setColumnDefinition(ColumnDefinition columnDefinition) { this.columnDefinition = columnDefinition; }

    /**
     * Retrieve the {@code date_mask} property (displayed as '{@literal Date Mask}') of the object.
     * @return {@code String}
     */
    @JsonProperty("date_mask")
    public String getDateMask() { return this.dateMask; }

    /**
     * Set the {@code date_mask} property (displayed as {@code Date Mask}) of the object.
     * @param dateMask the value to set
     */
    @JsonProperty("date_mask")
    public void setDateMask(String dateMask) { this.dateMask = dateMask; }

    /**
     * Retrieve the {@code depend_field} property (displayed as '{@literal Depend Field}') of the object.
     * @return {@code String}
     */
    @JsonProperty("depend_field")
    public String getDependField() { return this.dependField; }

    /**
     * Set the {@code depend_field} property (displayed as {@code Depend Field}) of the object.
     * @param dependField the value to set
     */
    @JsonProperty("depend_field")
    public void setDependField(String dependField) { this.dependField = dependField; }

    /**
     * Retrieve the {@code field_type} property (displayed as '{@literal Field Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("field_type")
    public String getFieldType() { return this.fieldType; }

    /**
     * Set the {@code field_type} property (displayed as {@code Field Type}) of the object.
     * @param fieldType the value to set
     */
    @JsonProperty("field_type")
    public void setFieldType(String fieldType) { this.fieldType = fieldType; }

    /**
     * Retrieve the {@code filler_parents} property (displayed as '{@literal Filler Parents}') of the object.
     * @return {@code String}
     */
    @JsonProperty("filler_parents")
    public String getFillerParents() { return this.fillerParents; }

    /**
     * Set the {@code filler_parents} property (displayed as {@code Filler Parents}) of the object.
     * @param fillerParents the value to set
     */
    @JsonProperty("filler_parents")
    public void setFillerParents(String fillerParents) { this.fillerParents = fillerParents; }

    /**
     * Retrieve the {@code flow_variable} property (displayed as '{@literal Flow Variable}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("flow_variable")
    public DataItem getFlowVariable() { return this.flowVariable; }

    /**
     * Set the {@code flow_variable} property (displayed as {@code Flow Variable}) of the object.
     * @param flowVariable the value to set
     */
    @JsonProperty("flow_variable")
    public void setFlowVariable(DataItem flowVariable) { this.flowVariable = flowVariable; }

    /**
     * Retrieve the {@code has_sign_indicator} property (displayed as '{@literal Has Sign Indicator}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("has_sign_indicator")
    public Boolean getHasSignIndicator() { return this.hasSignIndicator; }

    /**
     * Set the {@code has_sign_indicator} property (displayed as {@code Has Sign Indicator}) of the object.
     * @param hasSignIndicator the value to set
     */
    @JsonProperty("has_sign_indicator")
    public void setHasSignIndicator(Boolean hasSignIndicator) { this.hasSignIndicator = hasSignIndicator; }

    /**
     * Retrieve the {@code is_u_string} property (displayed as '{@literal Is U String}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_u_string")
    public Boolean getIsUString() { return this.isUString; }

    /**
     * Set the {@code is_u_string} property (displayed as {@code Is U String}) of the object.
     * @param isUString the value to set
     */
    @JsonProperty("is_u_string")
    public void setIsUString(Boolean isUString) { this.isUString = isUString; }

    /**
     * Retrieve the {@code nls_map} property (displayed as '{@literal NLS Map}') of the object.
     * @return {@code String}
     */
    @JsonProperty("nls_map")
    public String getNlsMap() { return this.nlsMap; }

    /**
     * Set the {@code nls_map} property (displayed as {@code NLS Map}) of the object.
     * @param nlsMap the value to set
     */
    @JsonProperty("nls_map")
    public void setNlsMap(String nlsMap) { this.nlsMap = nlsMap; }

    /**
     * Retrieve the {@code pad_char} property (displayed as '{@literal Pad Char}') of the object.
     * @return {@code String}
     */
    @JsonProperty("pad_char")
    public String getPadChar() { return this.padChar; }

    /**
     * Set the {@code pad_char} property (displayed as {@code Pad Char}) of the object.
     * @param padChar the value to set
     */
    @JsonProperty("pad_char")
    public void setPadChar(String padChar) { this.padChar = padChar; }

    /**
     * Retrieve the {@code redefined_field} property (displayed as '{@literal Redefined Field}') of the object.
     * @return {@code String}
     */
    @JsonProperty("redefined_field")
    public String getRedefinedField() { return this.redefinedField; }

    /**
     * Set the {@code redefined_field} property (displayed as {@code Redefined Field}) of the object.
     * @param redefinedField the value to set
     */
    @JsonProperty("redefined_field")
    public void setRedefinedField(String redefinedField) { this.redefinedField = redefinedField; }

    /**
     * Retrieve the {@code scale} property (displayed as '{@literal Scale}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("scale")
    public Number getScale() { return this.scale; }

    /**
     * Set the {@code scale} property (displayed as {@code Scale}) of the object.
     * @param scale the value to set
     */
    @JsonProperty("scale")
    public void setScale(Number scale) { this.scale = scale; }

    /**
     * Retrieve the {@code scd_purpose} property (displayed as '{@literal SCD Purpose}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("scd_purpose")
    public Number getScdPurpose() { return this.scdPurpose; }

    /**
     * Set the {@code scd_purpose} property (displayed as {@code SCD Purpose}) of the object.
     * @param scdPurpose the value to set
     */
    @JsonProperty("scd_purpose")
    public void setScdPurpose(Number scdPurpose) { this.scdPurpose = scdPurpose; }

    /**
     * Retrieve the {@code sign_option} property (displayed as '{@literal Sign Option}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sign_option")
    public Number getSignOption() { return this.signOption; }

    /**
     * Set the {@code sign_option} property (displayed as {@code Sign Option}) of the object.
     * @param signOption the value to set
     */
    @JsonProperty("sign_option")
    public void setSignOption(Number signOption) { this.signOption = signOption; }

    /**
     * Retrieve the {@code sync_indicator} property (displayed as '{@literal Sync Indicator}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("sync_indicator")
    public Boolean getSyncIndicator() { return this.syncIndicator; }

    /**
     * Set the {@code sync_indicator} property (displayed as {@code Sync Indicator}) of the object.
     * @param syncIndicator the value to set
     */
    @JsonProperty("sync_indicator")
    public void setSyncIndicator(Boolean syncIndicator) { this.syncIndicator = syncIndicator; }

    /**
     * Retrieve the {@code usage} property (displayed as '{@literal Usage}') of the object.
     * @return {@code String}
     */
    @JsonProperty("usage")
    public String getUsage() { return this.usage; }

    /**
     * Set the {@code usage} property (displayed as {@code Usage}) of the object.
     * @param usage the value to set
     */
    @JsonProperty("usage")
    public void setUsage(String usage) { this.usage = usage; }

}
