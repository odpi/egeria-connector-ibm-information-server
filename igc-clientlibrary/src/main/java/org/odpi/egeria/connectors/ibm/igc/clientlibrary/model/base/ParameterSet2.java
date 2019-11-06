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
 * POJO for the {@code parameterset} asset type in IGC, displayed as '{@literal ParameterSet}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=ParameterSet2.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("parameterset")
public class ParameterSet2 extends MainObject {

    @JsonProperty("has_parameter_def")
    protected ItemList<Parameter> hasParameterDef;

    @JsonProperty("used_as_parameter_def")
    protected ItemList<Parameter> usedAsParameterDef;

    /**
     * Retrieve the {@code has_parameter_def} property (displayed as '{@literal Has Parameter Def}') of the object.
     * @return {@code ItemList<Parameter>}
     */
    @JsonProperty("has_parameter_def")
    public ItemList<Parameter> getHasParameterDef() { return this.hasParameterDef; }

    /**
     * Set the {@code has_parameter_def} property (displayed as {@code Has Parameter Def}) of the object.
     * @param hasParameterDef the value to set
     */
    @JsonProperty("has_parameter_def")
    public void setHasParameterDef(ItemList<Parameter> hasParameterDef) { this.hasParameterDef = hasParameterDef; }

    /**
     * Retrieve the {@code used_as_parameter_def} property (displayed as '{@literal Used As Parameter Def}') of the object.
     * @return {@code ItemList<Parameter>}
     */
    @JsonProperty("used_as_parameter_def")
    public ItemList<Parameter> getUsedAsParameterDef() { return this.usedAsParameterDef; }

    /**
     * Set the {@code used_as_parameter_def} property (displayed as {@code Used As Parameter Def}) of the object.
     * @param usedAsParameterDef the value to set
     */
    @JsonProperty("used_as_parameter_def")
    public void setUsedAsParameterDef(ItemList<Parameter> usedAsParameterDef) { this.usedAsParameterDef = usedAsParameterDef; }

}
