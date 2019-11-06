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
 * POJO for the {@code function} asset type in IGC, displayed as '{@literal Function}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Function.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("function")
public class Function extends MainObject {

    @JsonProperty("author")
    protected String author;

    @JsonProperty("executed_by_function_call")
    protected ItemList<FunctionCall2> executedByFunctionCall;

    @JsonProperty("external_name")
    protected String externalName;

    @JsonProperty("has_function_call")
    protected ItemList<FunctionCall2> hasFunctionCall;

    @JsonProperty("has_parameter_def")
    protected ItemList<Parameter> hasParameterDef;

    @JsonProperty("is_inline")
    protected Boolean isInline;

    @JsonProperty("language")
    protected String language;

    @JsonProperty("module_name")
    protected String moduleName;

    @JsonProperty("module_path")
    protected String modulePath;

    @JsonProperty("platform_type")
    protected String platformType;

    @JsonProperty("returns_parameter_def")
    protected Parameter returnsParameterDef;

    @JsonProperty("source_code")
    protected String sourceCode;

    @JsonProperty("vendor")
    protected String vendor;

    @JsonProperty("version")
    protected String version;

    /**
     * Retrieve the {@code author} property (displayed as '{@literal Author}') of the object.
     * @return {@code String}
     */
    @JsonProperty("author")
    public String getAuthor() { return this.author; }

    /**
     * Set the {@code author} property (displayed as {@code Author}) of the object.
     * @param author the value to set
     */
    @JsonProperty("author")
    public void setAuthor(String author) { this.author = author; }

    /**
     * Retrieve the {@code executed_by_function_call} property (displayed as '{@literal Executed By Function Call}') of the object.
     * @return {@code ItemList<FunctionCall2>}
     */
    @JsonProperty("executed_by_function_call")
    public ItemList<FunctionCall2> getExecutedByFunctionCall() { return this.executedByFunctionCall; }

    /**
     * Set the {@code executed_by_function_call} property (displayed as {@code Executed By Function Call}) of the object.
     * @param executedByFunctionCall the value to set
     */
    @JsonProperty("executed_by_function_call")
    public void setExecutedByFunctionCall(ItemList<FunctionCall2> executedByFunctionCall) { this.executedByFunctionCall = executedByFunctionCall; }

    /**
     * Retrieve the {@code external_name} property (displayed as '{@literal External Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("external_name")
    public String getExternalName() { return this.externalName; }

    /**
     * Set the {@code external_name} property (displayed as {@code External Name}) of the object.
     * @param externalName the value to set
     */
    @JsonProperty("external_name")
    public void setExternalName(String externalName) { this.externalName = externalName; }

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
     * Retrieve the {@code is_inline} property (displayed as '{@literal Is Inline}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_inline")
    public Boolean getIsInline() { return this.isInline; }

    /**
     * Set the {@code is_inline} property (displayed as {@code Is Inline}) of the object.
     * @param isInline the value to set
     */
    @JsonProperty("is_inline")
    public void setIsInline(Boolean isInline) { this.isInline = isInline; }

    /**
     * Retrieve the {@code language} property (displayed as '{@literal Language}') of the object.
     * @return {@code String}
     */
    @JsonProperty("language")
    public String getLanguage() { return this.language; }

    /**
     * Set the {@code language} property (displayed as {@code Language}) of the object.
     * @param language the value to set
     */
    @JsonProperty("language")
    public void setLanguage(String language) { this.language = language; }

    /**
     * Retrieve the {@code module_name} property (displayed as '{@literal Module Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("module_name")
    public String getModuleName() { return this.moduleName; }

    /**
     * Set the {@code module_name} property (displayed as {@code Module Name}) of the object.
     * @param moduleName the value to set
     */
    @JsonProperty("module_name")
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    /**
     * Retrieve the {@code module_path} property (displayed as '{@literal Module Path}') of the object.
     * @return {@code String}
     */
    @JsonProperty("module_path")
    public String getModulePath() { return this.modulePath; }

    /**
     * Set the {@code module_path} property (displayed as {@code Module Path}) of the object.
     * @param modulePath the value to set
     */
    @JsonProperty("module_path")
    public void setModulePath(String modulePath) { this.modulePath = modulePath; }

    /**
     * Retrieve the {@code platform_type} property (displayed as '{@literal Platform Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("platform_type")
    public String getPlatformType() { return this.platformType; }

    /**
     * Set the {@code platform_type} property (displayed as {@code Platform Type}) of the object.
     * @param platformType the value to set
     */
    @JsonProperty("platform_type")
    public void setPlatformType(String platformType) { this.platformType = platformType; }

    /**
     * Retrieve the {@code returns_parameter_def} property (displayed as '{@literal Returns Parameter Def}') of the object.
     * @return {@code Parameter}
     */
    @JsonProperty("returns_parameter_def")
    public Parameter getReturnsParameterDef() { return this.returnsParameterDef; }

    /**
     * Set the {@code returns_parameter_def} property (displayed as {@code Returns Parameter Def}) of the object.
     * @param returnsParameterDef the value to set
     */
    @JsonProperty("returns_parameter_def")
    public void setReturnsParameterDef(Parameter returnsParameterDef) { this.returnsParameterDef = returnsParameterDef; }

    /**
     * Retrieve the {@code source_code} property (displayed as '{@literal Source Code}') of the object.
     * @return {@code String}
     */
    @JsonProperty("source_code")
    public String getSourceCode() { return this.sourceCode; }

    /**
     * Set the {@code source_code} property (displayed as {@code Source Code}) of the object.
     * @param sourceCode the value to set
     */
    @JsonProperty("source_code")
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    /**
     * Retrieve the {@code vendor} property (displayed as '{@literal Vendor}') of the object.
     * @return {@code String}
     */
    @JsonProperty("vendor")
    public String getVendor() { return this.vendor; }

    /**
     * Set the {@code vendor} property (displayed as {@code Vendor}) of the object.
     * @param vendor the value to set
     */
    @JsonProperty("vendor")
    public void setVendor(String vendor) { this.vendor = vendor; }

    /**
     * Retrieve the {@code version} property (displayed as '{@literal Version}') of the object.
     * @return {@code String}
     */
    @JsonProperty("version")
    public String getVersion() { return this.version; }

    /**
     * Set the {@code version} property (displayed as {@code Version}) of the object.
     * @param version the value to set
     */
    @JsonProperty("version")
    public void setVersion(String version) { this.version = version; }

}
