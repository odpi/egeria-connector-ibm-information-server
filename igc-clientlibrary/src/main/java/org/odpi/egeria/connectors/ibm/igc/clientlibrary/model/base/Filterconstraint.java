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
 * POJO for the {@code filterconstraint} asset type in IGC, displayed as '{@literal FilterConstraint}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Filterconstraint.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("filterconstraint")
public class Filterconstraint extends MainObject {

    @JsonProperty("filter_expression")
    protected String filterExpression;

    @JsonProperty("has_function_call")
    protected ItemList<FunctionCall2> hasFunctionCall;

    @JsonProperty("of_link")
    protected MainObject ofLink;

    /**
     * Valid values are:
     * <ul>
     *   <li>IN (displayed in the UI as 'IN')</li>
     *   <li>OUT (displayed in the UI as 'OUT')</li>
     *   <li>INOUT (displayed in the UI as 'INOUT')</li>
     *   <li>LINK (displayed in the UI as 'LINK')</li>
     * </ul>
     */
    @JsonProperty("usage")
    protected String usage;

    @JsonProperty("uses_flow_variable")
    protected ItemList<DataItem> usesFlowVariable;

    /**
     * Retrieve the {@code filter_expression} property (displayed as '{@literal Filter Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("filter_expression")
    public String getFilterExpression() { return this.filterExpression; }

    /**
     * Set the {@code filter_expression} property (displayed as {@code Filter Expression}) of the object.
     * @param filterExpression the value to set
     */
    @JsonProperty("filter_expression")
    public void setFilterExpression(String filterExpression) { this.filterExpression = filterExpression; }

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
     * Retrieve the {@code of_link} property (displayed as '{@literal Of Link}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("of_link")
    public MainObject getOfLink() { return this.ofLink; }

    /**
     * Set the {@code of_link} property (displayed as {@code Of Link}) of the object.
     * @param ofLink the value to set
     */
    @JsonProperty("of_link")
    public void setOfLink(MainObject ofLink) { this.ofLink = ofLink; }

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
     * Retrieve the {@code uses_flow_variable} property (displayed as '{@literal Uses Flow Variable}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("uses_flow_variable")
    public ItemList<DataItem> getUsesFlowVariable() { return this.usesFlowVariable; }

    /**
     * Set the {@code uses_flow_variable} property (displayed as {@code Uses Flow Variable}) of the object.
     * @param usesFlowVariable the value to set
     */
    @JsonProperty("uses_flow_variable")
    public void setUsesFlowVariable(ItemList<DataItem> usesFlowVariable) { this.usesFlowVariable = usesFlowVariable; }

}
