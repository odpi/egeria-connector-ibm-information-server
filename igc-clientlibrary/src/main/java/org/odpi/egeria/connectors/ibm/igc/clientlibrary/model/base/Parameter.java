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
 * POJO for the {@code parameter} asset type in IGC, displayed as '{@literal Parameter}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Parameter.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("parameter")
public class Parameter extends DataItem {

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

    @JsonProperty("defines_parameter_val")
    protected ItemList<Parameterval> definesParameterVal;

    @JsonProperty("is_connection_property")
    protected Boolean isConnectionProperty;

    @JsonProperty("is_parameter_set")
    protected ParameterSet2 isParameterSet;

    @JsonProperty("is_return_of_function")
    protected Function isReturnOfFunction;

    @JsonProperty("of_container_def")
    protected SharedContainer ofContainerDef;

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
     * Retrieve the {@code of_container_def} property (displayed as '{@literal Of Container Def}') of the object.
     * @return {@code SharedContainer}
     */
    @JsonProperty("of_container_def")
    public SharedContainer getOfContainerDef() { return this.ofContainerDef; }

    /**
     * Set the {@code of_container_def} property (displayed as {@code Of Container Def}) of the object.
     * @param ofContainerDef the value to set
     */
    @JsonProperty("of_container_def")
    public void setOfContainerDef(SharedContainer ofContainerDef) { this.ofContainerDef = ofContainerDef; }

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
