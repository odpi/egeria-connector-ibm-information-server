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
import java.util.List;

/**
 * POJO for the {@code mapping_filter} asset type in IGC, displayed as '{@literal Mapping Filter}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MappingFilter.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("mapping_filter")
public class MappingFilter extends Reference {

    @JsonProperty("description")
    protected List<String> description;

    @JsonProperty("name")
    protected List<String> name;

    @JsonProperty("rule_expression")
    protected String ruleExpression;

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("description")
    public List<String> getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(List<String> description) { this.description = description; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("name")
    public List<String> getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(List<String> name) { this.name = name; }

    /**
     * Retrieve the {@code rule_expression} property (displayed as '{@literal Rule Expression}') of the object.
     * @return {@code String}
     */
    @JsonProperty("rule_expression")
    public String getRuleExpression() { return this.ruleExpression; }

    /**
     * Set the {@code rule_expression} property (displayed as {@code Rule Expression}) of the object.
     * @param ruleExpression the value to set
     */
    @JsonProperty("rule_expression")
    public void setRuleExpression(String ruleExpression) { this.ruleExpression = ruleExpression; }

}
