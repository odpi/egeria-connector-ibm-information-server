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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code parameterval} asset type in IGC, displayed as '{@literal ParameterVal}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("parameterval")
public class Parameterval extends Reference {

    @JsonProperty("binds_parameter_def")
    protected Parameter bindsParameterDef;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("for_data_connection")
    protected DataConnection forDataConnection;

    @JsonProperty("for_function_call")
    protected FunctionCall2 forFunctionCall;

    @JsonProperty("for_job_object")
    protected MainObject forJobObject;

    @JsonProperty("has_function_call")
    protected ItemList<FunctionCall2> hasFunctionCall;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("parameter_name")
    protected String parameterName;

    @JsonProperty("sequence")
    protected Number sequence;

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

    @JsonProperty("value_expression")
    protected String valueExpression;

    /**
     * Retrieve the {@code binds_parameter_def} property (displayed as '{@literal Binds Parameter Def}') of the object.
     * @return {@code Parameter}
     */
    @JsonProperty("binds_parameter_def")
    public Parameter getBindsParameterDef() { return this.bindsParameterDef; }

    /**
     * Set the {@code binds_parameter_def} property (displayed as {@code Binds Parameter Def}) of the object.
     * @param bindsParameterDef the value to set
     */
    @JsonProperty("binds_parameter_def")
    public void setBindsParameterDef(Parameter bindsParameterDef) { this.bindsParameterDef = bindsParameterDef; }

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
     * Retrieve the {@code for_data_connection} property (displayed as '{@literal For Data Connection}') of the object.
     * @return {@code DataConnection}
     */
    @JsonProperty("for_data_connection")
    public DataConnection getForDataConnection() { return this.forDataConnection; }

    /**
     * Set the {@code for_data_connection} property (displayed as {@code For Data Connection}) of the object.
     * @param forDataConnection the value to set
     */
    @JsonProperty("for_data_connection")
    public void setForDataConnection(DataConnection forDataConnection) { this.forDataConnection = forDataConnection; }

    /**
     * Retrieve the {@code for_function_call} property (displayed as '{@literal For Function Call}') of the object.
     * @return {@code FunctionCall2}
     */
    @JsonProperty("for_function_call")
    public FunctionCall2 getForFunctionCall() { return this.forFunctionCall; }

    /**
     * Set the {@code for_function_call} property (displayed as {@code For Function Call}) of the object.
     * @param forFunctionCall the value to set
     */
    @JsonProperty("for_function_call")
    public void setForFunctionCall(FunctionCall2 forFunctionCall) { this.forFunctionCall = forFunctionCall; }

    /**
     * Retrieve the {@code for_job_object} property (displayed as '{@literal For Job Object}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("for_job_object")
    public MainObject getForJobObject() { return this.forJobObject; }

    /**
     * Set the {@code for_job_object} property (displayed as {@code For Job Object}) of the object.
     * @param forJobObject the value to set
     */
    @JsonProperty("for_job_object")
    public void setForJobObject(MainObject forJobObject) { this.forJobObject = forJobObject; }

    /**
     * Retrieve the {@code has_function_call} property (displayed as '{@literal Has Function Call}') of the object.
     * @return {@code ItemList<FunctionCall2>}
     */
    @JsonProperty("has_function_call")
    public ItemList<FunctionCall2> getHasFunctionCall() { return this.hasFunctionCall; }

    /**
     * Set the {@code has_function_call} property (displayed as {@code Has Function Call}) of the object.
     * @param hasFunctionCall the value to set
     */
    @JsonProperty("has_function_call")
    public void setHasFunctionCall(ItemList<FunctionCall2> hasFunctionCall) { this.hasFunctionCall = hasFunctionCall; }

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
     * Retrieve the {@code parameter_name} property (displayed as '{@literal Parameter Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("parameter_name")
    public String getParameterName() { return this.parameterName; }

    /**
     * Set the {@code parameter_name} property (displayed as {@code Parameter Name}) of the object.
     * @param parameterName the value to set
     */
    @JsonProperty("parameter_name")
    public void setParameterName(String parameterName) { this.parameterName = parameterName; }

    /**
     * Retrieve the {@code sequence} property (displayed as '{@literal Sequence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sequence")
    public Number getSequence() { return this.sequence; }

    /**
     * Set the {@code sequence} property (displayed as {@code Sequence}) of the object.
     * @param sequence the value to set
     */
    @JsonProperty("sequence")
    public void setSequence(Number sequence) { this.sequence = sequence; }

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
     * Retrieve the {@code value_expression} property (displayed as '{@literal Value Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("value_expression")
    public String getValueExpression() { return this.valueExpression; }

    /**
     * Set the {@code value_expression} property (displayed as {@code Value Expression}) of the object.
     * @param valueExpression the value to set
     */
    @JsonProperty("value_expression")
    public void setValueExpression(String valueExpression) { this.valueExpression = valueExpression; }

}
