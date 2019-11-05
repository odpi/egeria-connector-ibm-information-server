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
 * POJO for the {@code dsjcltemplate} asset type in IGC, displayed as '{@literal DSJCLTemplate}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsjcltemplate")
public class Dsjcltemplate extends MainObject {

    @JsonProperty("category")
    protected String category;

    @JsonProperty("code_template")
    protected String codeTemplate;

    @JsonProperty("ds_name_space")
    protected String dsNameSpace;

    @JsonProperty("platform_type")
    protected String platformType;

    @JsonProperty("template_type")
    protected String templateType;

    /**
     * Retrieve the {@code category} property (displayed as '{@literal Category}') of the object.
     * @return {@code String}
     */
    @JsonProperty("category")
    public String getCategory() { return this.category; }

    /**
     * Set the {@code category} property (displayed as {@code Category}) of the object.
     * @param category the value to set
     */
    @JsonProperty("category")
    public void setCategory(String category) { this.category = category; }

    /**
     * Retrieve the {@code code_template} property (displayed as '{@literal Code Template}') of the object.
     * @return {@code String}
     */
    @JsonProperty("code_template")
    public String getCodeTemplate() { return this.codeTemplate; }

    /**
     * Set the {@code code_template} property (displayed as {@code Code Template}) of the object.
     * @param codeTemplate the value to set
     */
    @JsonProperty("code_template")
    public void setCodeTemplate(String codeTemplate) { this.codeTemplate = codeTemplate; }

    /**
     * Retrieve the {@code ds_name_space} property (displayed as '{@literal DS Name Space}') of the object.
     * @return {@code String}
     */
    @JsonProperty("ds_name_space")
    public String getDsNameSpace() { return this.dsNameSpace; }

    /**
     * Set the {@code ds_name_space} property (displayed as {@code DS Name Space}) of the object.
     * @param dsNameSpace the value to set
     */
    @JsonProperty("ds_name_space")
    public void setDsNameSpace(String dsNameSpace) { this.dsNameSpace = dsNameSpace; }

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
     * Retrieve the {@code template_type} property (displayed as '{@literal Template Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("template_type")
    public String getTemplateType() { return this.templateType; }

    /**
     * Set the {@code template_type} property (displayed as {@code Template Type}) of the object.
     * @param templateType the value to set
     */
    @JsonProperty("template_type")
    public void setTemplateType(String templateType) { this.templateType = templateType; }

}
