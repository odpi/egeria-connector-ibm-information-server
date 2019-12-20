/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code data_item} asset type in IGC, displayed as '{@literal Data Item}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataItem.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataField.class, name = "data_field"),
        @JsonSubTypes.Type(value = DataItemDefinition.class, name = "data_item_definition"),
        @JsonSubTypes.Type(value = Parameter.class, name = "parameter"),
        @JsonSubTypes.Type(value = RoutineArgument.class, name = "routine_argument"),
        @JsonSubTypes.Type(value = StageColumn.class, name = "stage_column"),
        @JsonSubTypes.Type(value = StageTypeDetail.class, name = "stage_type_detail"),
        @JsonSubTypes.Type(value = TransformArgument.class, name = "transform_argument"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_item")
public class DataItem extends InformationAsset {

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

    @JsonProperty("display_size")
    protected Number displaySize;

    @JsonProperty("fraction")
    protected Number fraction;

    @JsonProperty("has_dimension")
    protected ItemList<Array> hasDimension;

    @JsonProperty("is_computed")
    protected Boolean isComputed;

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

    @JsonProperty("native_id")
    protected String nativeId;

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

    @JsonProperty("position")
    protected Number position;

    @JsonProperty("type")
    protected String type;

    @JsonProperty("unique")
    protected Boolean unique;

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
     * Retrieve the {@code based_on} property (displayed as '{@literal Based On}') of the object.
     * @return {@code String}
     */
    @JsonProperty("based_on")
    public String getBasedOn() { return this.basedOn; }

    /**
     * Set the {@code based_on} property (displayed as {@code Based On}) of the object.
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
