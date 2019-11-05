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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code parameter} asset type in IGC, displayed as '{@literal Parameter}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("parameter")
public class Parameter extends MainObject {

    @JsonProperty("allow_null_values")
    protected String allowNullValues;

    @JsonProperty("allows_empty_value")
    protected Boolean allowsEmptyValue;

    @JsonProperty("allows_null_values")
    protected Boolean allowsNullValues;

    @JsonProperty("based_on")
    protected String basedOn;

    @JsonProperty("calendar")
    protected String calendar;

    /**
     * Valid values are:
     * <ul>
     *   <li>CONNECTIONSTRING (displayed in the UI as 'CONNECTIONSTRING')</li>
     *   <li>USERNAME (displayed in the UI as 'USERNAME')</li>
     *   <li>PASSWORD (displayed in the UI as 'PASSWORD')</li>
     *   <li>PRIVATE (displayed in the UI as 'PRIVATE')</li>
     * </ul>
     */
    @JsonProperty("connection_property")
    protected String connectionProperty;

    @JsonProperty("data_item_definition")
    protected DataItemDefinition dataItemDefinition;

    /**
     * Valid values are:
     * <ul>
     *   <li>INT8 (displayed in the UI as 'INT8')</li>
     *   <li>INT16 (displayed in the UI as 'INT16')</li>
     *   <li>INT32 (displayed in the UI as 'INT32')</li>
     *   <li>INT64 (displayed in the UI as 'INT64')</li>
     *   <li>SFLOAT (displayed in the UI as 'SFLOAT')</li>
     *   <li>DFLOAT (displayed in the UI as 'DFLOAT')</li>
     *   <li>QFLOAT (displayed in the UI as 'QFLOAT')</li>
     *   <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *   <li>STRING (displayed in the UI as 'STRING')</li>
     *   <li>BINARY (displayed in the UI as 'BINARY')</li>
     *   <li>BOOLEAN (displayed in the UI as 'BOOLEAN')</li>
     *   <li>DATE (displayed in the UI as 'DATE')</li>
     *   <li>TIME (displayed in the UI as 'TIME')</li>
     *   <li>DATETIME (displayed in the UI as 'DATETIME')</li>
     *   <li>DURATION (displayed in the UI as 'DURATION')</li>
     *   <li>CHOICE (displayed in the UI as 'CHOICE')</li>
     *   <li>ORDERED_GROUP (displayed in the UI as 'ORDERED_GROUP')</li>
     *   <li>UNORDERED_GROUP (displayed in the UI as 'UNORDERED_GROUP')</li>
     *   <li>GUID (displayed in the UI as 'GUID')</li>
     *   <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     *   <li>JSON (displayed in the UI as 'JSON')</li>
     *   <li>XML (displayed in the UI as 'XML')</li>
     * </ul>
     */
    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("default_value")
    protected String defaultValue;

    @JsonProperty("defines_parameter_val")
    protected ItemList<Parameterval> definesParameterVal;

    @JsonProperty("display_size")
    protected Number displaySize;

    @JsonProperty("fraction")
    protected Number fraction;

    @JsonProperty("has_dimension")
    protected ItemList<Array> hasDimension;

    @JsonProperty("is_computed")
    protected Boolean isComputed;

    @JsonProperty("is_connection_property")
    protected Boolean isConnectionProperty;

    @JsonProperty("is_parameter_set")
    protected ParameterSet2 isParameterSet;

    @JsonProperty("is_return_of_function")
    protected Function isReturnOfFunction;

    @JsonProperty("is_signed")
    protected Boolean isSigned;

    @JsonProperty("item_kind")
    protected String itemKind;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("level")
    protected Number level;

    @JsonProperty("minimum_length")
    protected Number minimumLength;

    /**
     * Valid values are:
     * <ul>
     *   <li>CHAR (displayed in the UI as 'CHAR')</li>
     *   <li>VARCHAR (displayed in the UI as 'VARCHAR')</li>
     *   <li>LONGVARCHAR (displayed in the UI as 'LONGVARCHAR')</li>
     *   <li>WCHAR (displayed in the UI as 'WCHAR')</li>
     *   <li>WVARCHAR (displayed in the UI as 'WVARCHAR')</li>
     *   <li>WLONGVARCHAR (displayed in the UI as 'WLONGVARCHAR')</li>
     *   <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *   <li>NUMERIC (displayed in the UI as 'NUMERIC')</li>
     *   <li>SMALLINT (displayed in the UI as 'SMALLINT')</li>
     *   <li>INTEGER (displayed in the UI as 'INTEGER')</li>
     *   <li>REAL (displayed in the UI as 'REAL')</li>
     *   <li>FLOAT (displayed in the UI as 'FLOAT')</li>
     *   <li>DOUBLE (displayed in the UI as 'DOUBLE')</li>
     *   <li>BIT (displayed in the UI as 'BIT')</li>
     *   <li>TINYINT (displayed in the UI as 'TINYINT')</li>
     *   <li>BIGINT (displayed in the UI as 'BIGINT')</li>
     *   <li>BINARY (displayed in the UI as 'BINARY')</li>
     *   <li>VARBINARY (displayed in the UI as 'VARBINARY')</li>
     *   <li>LONGVARBINARY (displayed in the UI as 'LONGVARBINARY')</li>
     *   <li>DATE (displayed in the UI as 'DATE')</li>
     *   <li>TIME (displayed in the UI as 'TIME')</li>
     *   <li>TIMESTAMP (displayed in the UI as 'TIMESTAMP')</li>
     *   <li>GUID (displayed in the UI as 'GUID')</li>
     *   <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     * </ul>
     */
    @JsonProperty("odbc_type")
    protected String odbcType;

    @JsonProperty("of_container_def")
    protected ReferencedContainer ofContainerDef;

    @JsonProperty("of_function")
    protected Function ofFunction;

    @JsonProperty("of_job_def")
    protected Jobdef ofJobDef;

    @JsonProperty("of_parameter_set")
    protected ParameterSet2 ofParameterSet;

    @JsonProperty("of_stage_type")
    protected StageType ofStageType;

    @JsonProperty("of_stored_procedure")
    protected Datagroup ofStoredProcedure;

    @JsonProperty("position")
    protected Number position;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("unique")
    protected Boolean unique;

    /**
     * Valid values are:
     * <ul>
     *   <li>DEFAULT (displayed in the UI as 'DEFAULT')</li>
     *   <li>IN (displayed in the UI as 'IN')</li>
     *   <li>OUT (displayed in the UI as 'OUT')</li>
     *   <li>INOUT (displayed in the UI as 'INOUT')</li>
     *   <li>RETURN (displayed in the UI as 'RETURN')</li>
     * </ul>
     */
    @JsonProperty("usage")
    protected String usage;

    @JsonProperty("validated_by_data_values")
    protected ItemList<DataItemValue> validatedByDataValues;

    /**
     * Retrieve the {@code allow_null_values} property (displayed as '{@literal Null Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("allow_null_values")
    public String getAllowNullValues() { return this.allowNullValues; }

    /**
     * Set the {@code allow_null_values} property (displayed as {@code Null Value}) of the object.
     * @param allowNullValues the value to set
     */
    @JsonProperty("allow_null_values")
    public void setAllowNullValues(String allowNullValues) { this.allowNullValues = allowNullValues; }

    /**
     * Retrieve the {@code allows_empty_value} property (displayed as '{@literal Allows Empty Value}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("allows_empty_value")
    public Boolean getAllowsEmptyValue() { return this.allowsEmptyValue; }

    /**
     * Set the {@code allows_empty_value} property (displayed as {@code Allows Empty Value}) of the object.
     * @param allowsEmptyValue the value to set
     */
    @JsonProperty("allows_empty_value")
    public void setAllowsEmptyValue(Boolean allowsEmptyValue) { this.allowsEmptyValue = allowsEmptyValue; }

    /**
     * Retrieve the {@code allows_null_values} property (displayed as '{@literal Allow Null Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("allows_null_values")
    public Boolean getAllowsNullValues() { return this.allowsNullValues; }

    /**
     * Set the {@code allows_null_values} property (displayed as {@code Allow Null Values}) of the object.
     * @param allowsNullValues the value to set
     */
    @JsonProperty("allows_null_values")
    public void setAllowsNullValues(Boolean allowsNullValues) { this.allowsNullValues = allowsNullValues; }

    /**
     * Retrieve the {@code based_on} property (displayed as '{@literal Based On }') of the object.
     * @return {@code String}
     */
    @JsonProperty("based_on")
    public String getBasedOn() { return this.basedOn; }

    /**
     * Set the {@code based_on} property (displayed as {@code Based On }) of the object.
     * @param basedOn the value to set
     */
    @JsonProperty("based_on")
    public void setBasedOn(String basedOn) { this.basedOn = basedOn; }

    /**
     * Retrieve the {@code calendar} property (displayed as '{@literal Calendar}') of the object.
     * @return {@code String}
     */
    @JsonProperty("calendar")
    public String getCalendar() { return this.calendar; }

    /**
     * Set the {@code calendar} property (displayed as {@code Calendar}) of the object.
     * @param calendar the value to set
     */
    @JsonProperty("calendar")
    public void setCalendar(String calendar) { this.calendar = calendar; }

    /**
     * Retrieve the {@code connection_property} property (displayed as '{@literal Connection Property}') of the object.
     * @return {@code String}
     */
    @JsonProperty("connection_property")
    public String getConnectionProperty() { return this.connectionProperty; }

    /**
     * Set the {@code connection_property} property (displayed as {@code Connection Property}) of the object.
     * @param connectionProperty the value to set
     */
    @JsonProperty("connection_property")
    public void setConnectionProperty(String connectionProperty) { this.connectionProperty = connectionProperty; }

    /**
     * Retrieve the {@code data_item_definition} property (displayed as '{@literal Data Item Definition}') of the object.
     * @return {@code DataItemDefinition}
     */
    @JsonProperty("data_item_definition")
    public DataItemDefinition getDataItemDefinition() { return this.dataItemDefinition; }

    /**
     * Set the {@code data_item_definition} property (displayed as {@code Data Item Definition}) of the object.
     * @param dataItemDefinition the value to set
     */
    @JsonProperty("data_item_definition")
    public void setDataItemDefinition(DataItemDefinition dataItemDefinition) { this.dataItemDefinition = dataItemDefinition; }

    /**
     * Retrieve the {@code data_type} property (displayed as '{@literal Type Code}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_type")
    public String getDataType() { return this.dataType; }

    /**
     * Set the {@code data_type} property (displayed as {@code Type Code}) of the object.
     * @param dataType the value to set
     */
    @JsonProperty("data_type")
    public void setDataType(String dataType) { this.dataType = dataType; }

    /**
     * Retrieve the {@code default_value} property (displayed as '{@literal Default Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("default_value")
    public String getDefaultValue() { return this.defaultValue; }

    /**
     * Set the {@code default_value} property (displayed as {@code Default Value}) of the object.
     * @param defaultValue the value to set
     */
    @JsonProperty("default_value")
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }

    /**
     * Retrieve the {@code defines_parameter_val} property (displayed as '{@literal Defines Parameter Val}') of the object.
     * @return {@code ItemList<Parameterval>}
     */
    @JsonProperty("defines_parameter_val")
    public ItemList<Parameterval> getDefinesParameterVal() { return this.definesParameterVal; }

    /**
     * Set the {@code defines_parameter_val} property (displayed as {@code Defines Parameter Val}) of the object.
     * @param definesParameterVal the value to set
     */
    @JsonProperty("defines_parameter_val")
    public void setDefinesParameterVal(ItemList<Parameterval> definesParameterVal) { this.definesParameterVal = definesParameterVal; }

    /**
     * Retrieve the {@code display_size} property (displayed as '{@literal Display Size}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("display_size")
    public Number getDisplaySize() { return this.displaySize; }

    /**
     * Set the {@code display_size} property (displayed as {@code Display Size}) of the object.
     * @param displaySize the value to set
     */
    @JsonProperty("display_size")
    public void setDisplaySize(Number displaySize) { this.displaySize = displaySize; }

    /**
     * Retrieve the {@code fraction} property (displayed as '{@literal Scale}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("fraction")
    public Number getFraction() { return this.fraction; }

    /**
     * Set the {@code fraction} property (displayed as {@code Scale}) of the object.
     * @param fraction the value to set
     */
    @JsonProperty("fraction")
    public void setFraction(Number fraction) { this.fraction = fraction; }

    /**
     * Retrieve the {@code has_dimension} property (displayed as '{@literal Has Dimension}') of the object.
     * @return {@code ItemList<Array>}
     */
    @JsonProperty("has_dimension")
    public ItemList<Array> getHasDimension() { return this.hasDimension; }

    /**
     * Set the {@code has_dimension} property (displayed as {@code Has Dimension}) of the object.
     * @param hasDimension the value to set
     */
    @JsonProperty("has_dimension")
    public void setHasDimension(ItemList<Array> hasDimension) { this.hasDimension = hasDimension; }

    /**
     * Retrieve the {@code is_computed} property (displayed as '{@literal Is Computed}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_computed")
    public Boolean getIsComputed() { return this.isComputed; }

    /**
     * Set the {@code is_computed} property (displayed as {@code Is Computed}) of the object.
     * @param isComputed the value to set
     */
    @JsonProperty("is_computed")
    public void setIsComputed(Boolean isComputed) { this.isComputed = isComputed; }

    /**
     * Retrieve the {@code is_connection_property} property (displayed as '{@literal Is Connection Property}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_connection_property")
    public Boolean getIsConnectionProperty() { return this.isConnectionProperty; }

    /**
     * Set the {@code is_connection_property} property (displayed as {@code Is Connection Property}) of the object.
     * @param isConnectionProperty the value to set
     */
    @JsonProperty("is_connection_property")
    public void setIsConnectionProperty(Boolean isConnectionProperty) { this.isConnectionProperty = isConnectionProperty; }

    /**
     * Retrieve the {@code is_parameter_set} property (displayed as '{@literal Is Parameter Set}') of the object.
     * @return {@code ParameterSet2}
     */
    @JsonProperty("is_parameter_set")
    public ParameterSet2 getIsParameterSet() { return this.isParameterSet; }

    /**
     * Set the {@code is_parameter_set} property (displayed as {@code Is Parameter Set}) of the object.
     * @param isParameterSet the value to set
     */
    @JsonProperty("is_parameter_set")
    public void setIsParameterSet(ParameterSet2 isParameterSet) { this.isParameterSet = isParameterSet; }

    /**
     * Retrieve the {@code is_return_of_function} property (displayed as '{@literal Is Return Of Function}') of the object.
     * @return {@code Function}
     */
    @JsonProperty("is_return_of_function")
    public Function getIsReturnOfFunction() { return this.isReturnOfFunction; }

    /**
     * Set the {@code is_return_of_function} property (displayed as {@code Is Return Of Function}) of the object.
     * @param isReturnOfFunction the value to set
     */
    @JsonProperty("is_return_of_function")
    public void setIsReturnOfFunction(Function isReturnOfFunction) { this.isReturnOfFunction = isReturnOfFunction; }

    /**
     * Retrieve the {@code is_signed} property (displayed as '{@literal Is Signed}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_signed")
    public Boolean getIsSigned() { return this.isSigned; }

    /**
     * Set the {@code is_signed} property (displayed as {@code Is Signed}) of the object.
     * @param isSigned the value to set
     */
    @JsonProperty("is_signed")
    public void setIsSigned(Boolean isSigned) { this.isSigned = isSigned; }

    /**
     * Retrieve the {@code item_kind} property (displayed as '{@literal Item Kind}') of the object.
     * @return {@code String}
     */
    @JsonProperty("item_kind")
    public String getItemKind() { return this.itemKind; }

    /**
     * Set the {@code item_kind} property (displayed as {@code Item Kind}) of the object.
     * @param itemKind the value to set
     */
    @JsonProperty("item_kind")
    public void setItemKind(String itemKind) { this.itemKind = itemKind; }

    /**
     * Retrieve the {@code length} property (displayed as '{@literal Maximum Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("length")
    public Number getLength() { return this.length; }

    /**
     * Set the {@code length} property (displayed as {@code Maximum Length}) of the object.
     * @param length the value to set
     */
    @JsonProperty("length")
    public void setLength(Number length) { this.length = length; }

    /**
     * Retrieve the {@code level} property (displayed as '{@literal Level Number}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("level")
    public Number getLevel() { return this.level; }

    /**
     * Set the {@code level} property (displayed as {@code Level Number}) of the object.
     * @param level the value to set
     */
    @JsonProperty("level")
    public void setLevel(Number level) { this.level = level; }

    /**
     * Retrieve the {@code minimum_length} property (displayed as '{@literal Minimum Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("minimum_length")
    public Number getMinimumLength() { return this.minimumLength; }

    /**
     * Set the {@code minimum_length} property (displayed as {@code Minimum Length}) of the object.
     * @param minimumLength the value to set
     */
    @JsonProperty("minimum_length")
    public void setMinimumLength(Number minimumLength) { this.minimumLength = minimumLength; }

    /**
     * Retrieve the {@code odbc_type} property (displayed as '{@literal ODBC Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("odbc_type")
    public String getOdbcType() { return this.odbcType; }

    /**
     * Set the {@code odbc_type} property (displayed as {@code ODBC Type}) of the object.
     * @param odbcType the value to set
     */
    @JsonProperty("odbc_type")
    public void setOdbcType(String odbcType) { this.odbcType = odbcType; }

    /**
     * Retrieve the {@code of_container_def} property (displayed as '{@literal Of Container Def}') of the object.
     * @return {@code ReferencedContainer}
     */
    @JsonProperty("of_container_def")
    public ReferencedContainer getOfContainerDef() { return this.ofContainerDef; }

    /**
     * Set the {@code of_container_def} property (displayed as {@code Of Container Def}) of the object.
     * @param ofContainerDef the value to set
     */
    @JsonProperty("of_container_def")
    public void setOfContainerDef(ReferencedContainer ofContainerDef) { this.ofContainerDef = ofContainerDef; }

    /**
     * Retrieve the {@code of_function} property (displayed as '{@literal Of Function}') of the object.
     * @return {@code Function}
     */
    @JsonProperty("of_function")
    public Function getOfFunction() { return this.ofFunction; }

    /**
     * Set the {@code of_function} property (displayed as {@code Of Function}) of the object.
     * @param ofFunction the value to set
     */
    @JsonProperty("of_function")
    public void setOfFunction(Function ofFunction) { this.ofFunction = ofFunction; }

    /**
     * Retrieve the {@code of_job_def} property (displayed as '{@literal Of Job Def}') of the object.
     * @return {@code Jobdef}
     */
    @JsonProperty("of_job_def")
    public Jobdef getOfJobDef() { return this.ofJobDef; }

    /**
     * Set the {@code of_job_def} property (displayed as {@code Of Job Def}) of the object.
     * @param ofJobDef the value to set
     */
    @JsonProperty("of_job_def")
    public void setOfJobDef(Jobdef ofJobDef) { this.ofJobDef = ofJobDef; }

    /**
     * Retrieve the {@code of_parameter_set} property (displayed as '{@literal Of Parameter Set}') of the object.
     * @return {@code ParameterSet2}
     */
    @JsonProperty("of_parameter_set")
    public ParameterSet2 getOfParameterSet() { return this.ofParameterSet; }

    /**
     * Set the {@code of_parameter_set} property (displayed as {@code Of Parameter Set}) of the object.
     * @param ofParameterSet the value to set
     */
    @JsonProperty("of_parameter_set")
    public void setOfParameterSet(ParameterSet2 ofParameterSet) { this.ofParameterSet = ofParameterSet; }

    /**
     * Retrieve the {@code of_stage_type} property (displayed as '{@literal Of Stage Type}') of the object.
     * @return {@code StageType}
     */
    @JsonProperty("of_stage_type")
    public StageType getOfStageType() { return this.ofStageType; }

    /**
     * Set the {@code of_stage_type} property (displayed as {@code Of Stage Type}) of the object.
     * @param ofStageType the value to set
     */
    @JsonProperty("of_stage_type")
    public void setOfStageType(StageType ofStageType) { this.ofStageType = ofStageType; }

    /**
     * Retrieve the {@code of_stored_procedure} property (displayed as '{@literal Of Stored Procedure}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("of_stored_procedure")
    public Datagroup getOfStoredProcedure() { return this.ofStoredProcedure; }

    /**
     * Set the {@code of_stored_procedure} property (displayed as {@code Of Stored Procedure}) of the object.
     * @param ofStoredProcedure the value to set
     */
    @JsonProperty("of_stored_procedure")
    public void setOfStoredProcedure(Datagroup ofStoredProcedure) { this.ofStoredProcedure = ofStoredProcedure; }

    /**
     * Retrieve the {@code position} property (displayed as '{@literal Position}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("position")
    public Number getPosition() { return this.position; }

    /**
     * Set the {@code position} property (displayed as {@code Position}) of the object.
     * @param position the value to set
     */
    @JsonProperty("position")
    public void setPosition(Number position) { this.position = position; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Native Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Native Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

    /**
     * Retrieve the {@code unique} property (displayed as '{@literal Unique}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("unique")
    public Boolean getUnique() { return this.unique; }

    /**
     * Set the {@code unique} property (displayed as {@code Unique}) of the object.
     * @param unique the value to set
     */
    @JsonProperty("unique")
    public void setUnique(Boolean unique) { this.unique = unique; }

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

    /**
     * Retrieve the {@code validated_by_data_values} property (displayed as '{@literal Validated By Data Values}') of the object.
     * @return {@code ItemList<DataItemValue>}
     */
    @JsonProperty("validated_by_data_values")
    public ItemList<DataItemValue> getValidatedByDataValues() { return this.validatedByDataValues; }

    /**
     * Set the {@code validated_by_data_values} property (displayed as {@code Validated By Data Values}) of the object.
     * @param validatedByDataValues the value to set
     */
    @JsonProperty("validated_by_data_values")
    public void setValidatedByDataValues(ItemList<DataItemValue> validatedByDataValues) { this.validatedByDataValues = validatedByDataValues; }

}
