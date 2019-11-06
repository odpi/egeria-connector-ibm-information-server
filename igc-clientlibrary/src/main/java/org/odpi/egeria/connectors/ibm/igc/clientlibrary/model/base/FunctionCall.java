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

/**
 * POJO for the {@code function_call} asset type in IGC, displayed as '{@literal Function Call}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=FunctionCall.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("function_call")
public class FunctionCall extends Reference {

    /**
     * Valid values are:
     * <ul>
     *   <li>BEFORE (displayed in the UI as 'BEFORE')</li>
     *   <li>AFTER (displayed in the UI as 'AFTER')</li>
     *   <li>DEFAULT (displayed in the UI as 'DEFAULT')</li>
     * </ul>
     */
    @JsonProperty("call_context")
    protected String callContext;

    @JsonProperty("executes_function")
    protected Function executesFunction;

    @JsonProperty("for_transforms")
    protected Boolean forTransforms;

    @JsonProperty("function_name")
    protected String functionName;

    @JsonProperty("stage_trigger")
    protected Boolean stageTrigger;

    @JsonProperty("used_in_filter_constraint")
    protected Filterconstraint usedInFilterConstraint;

    @JsonProperty("used_in_function")
    protected Function usedInFunction;

    /**
     * Retrieve the {@code call_context} property (displayed as '{@literal Call Context}') of the object.
     * @return {@code String}
     */
    @JsonProperty("call_context")
    public String getCallContext() { return this.callContext; }

    /**
     * Set the {@code call_context} property (displayed as {@code Call Context}) of the object.
     * @param callContext the value to set
     */
    @JsonProperty("call_context")
    public void setCallContext(String callContext) { this.callContext = callContext; }

    /**
     * Retrieve the {@code executes_function} property (displayed as '{@literal Executes Function}') of the object.
     * @return {@code Function}
     */
    @JsonProperty("executes_function")
    public Function getExecutesFunction() { return this.executesFunction; }

    /**
     * Set the {@code executes_function} property (displayed as {@code Executes Function}) of the object.
     * @param executesFunction the value to set
     */
    @JsonProperty("executes_function")
    public void setExecutesFunction(Function executesFunction) { this.executesFunction = executesFunction; }

    /**
     * Retrieve the {@code for_transforms} property (displayed as '{@literal For Transforms}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("for_transforms")
    public Boolean getForTransforms() { return this.forTransforms; }

    /**
     * Set the {@code for_transforms} property (displayed as {@code For Transforms}) of the object.
     * @param forTransforms the value to set
     */
    @JsonProperty("for_transforms")
    public void setForTransforms(Boolean forTransforms) { this.forTransforms = forTransforms; }

    /**
     * Retrieve the {@code function_name} property (displayed as '{@literal Function Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("function_name")
    public String getFunctionName() { return this.functionName; }

    /**
     * Set the {@code function_name} property (displayed as {@code Function Name}) of the object.
     * @param functionName the value to set
     */
    @JsonProperty("function_name")
    public void setFunctionName(String functionName) { this.functionName = functionName; }

    /**
     * Retrieve the {@code stage_trigger} property (displayed as '{@literal Stage Trigger}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("stage_trigger")
    public Boolean getStageTrigger() { return this.stageTrigger; }

    /**
     * Set the {@code stage_trigger} property (displayed as {@code Stage Trigger}) of the object.
     * @param stageTrigger the value to set
     */
    @JsonProperty("stage_trigger")
    public void setStageTrigger(Boolean stageTrigger) { this.stageTrigger = stageTrigger; }

    /**
     * Retrieve the {@code used_in_filter_constraint} property (displayed as '{@literal Used In Filter Constraint}') of the object.
     * @return {@code Filterconstraint}
     */
    @JsonProperty("used_in_filter_constraint")
    public Filterconstraint getUsedInFilterConstraint() { return this.usedInFilterConstraint; }

    /**
     * Set the {@code used_in_filter_constraint} property (displayed as {@code Used In Filter Constraint}) of the object.
     * @param usedInFilterConstraint the value to set
     */
    @JsonProperty("used_in_filter_constraint")
    public void setUsedInFilterConstraint(Filterconstraint usedInFilterConstraint) { this.usedInFilterConstraint = usedInFilterConstraint; }

    /**
     * Retrieve the {@code used_in_function} property (displayed as '{@literal Used In Function}') of the object.
     * @return {@code Function}
     */
    @JsonProperty("used_in_function")
    public Function getUsedInFunction() { return this.usedInFunction; }

    /**
     * Set the {@code used_in_function} property (displayed as {@code Used In Function}) of the object.
     * @param usedInFunction the value to set
     */
    @JsonProperty("used_in_function")
    public void setUsedInFunction(Function usedInFunction) { this.usedInFunction = usedInFunction; }

}
