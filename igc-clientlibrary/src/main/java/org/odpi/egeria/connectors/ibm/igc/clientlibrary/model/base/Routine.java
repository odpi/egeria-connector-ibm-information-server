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
 * POJO for the {@code routine} asset type in IGC, displayed as '{@literal Routine}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Routine.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("routine")
public class Routine extends InformationAsset {

    @JsonProperty("arguments")
    protected ItemList<RoutineArgument> arguments;

    @JsonProperty("author")
    protected String author;

    @JsonProperty("copyright")
    protected String copyright;

    @JsonProperty("source_code")
    protected String sourceCode;

    @JsonProperty("transformation_project")
    protected TransformationProject transformationProject;

    @JsonProperty("vendor")
    protected String vendor;

    @JsonProperty("version")
    protected String version;

    /**
     * Retrieve the {@code arguments} property (displayed as '{@literal Arguments}') of the object.
     * @return {@code ItemList<RoutineArgument>}
     */
    @JsonProperty("arguments")
    public ItemList<RoutineArgument> getArguments() { return this.arguments; }

    /**
     * Set the {@code arguments} property (displayed as {@code Arguments}) of the object.
     * @param arguments the value to set
     */
    @JsonProperty("arguments")
    public void setArguments(ItemList<RoutineArgument> arguments) { this.arguments = arguments; }

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
     * Retrieve the {@code copyright} property (displayed as '{@literal Copyright}') of the object.
     * @return {@code String}
     */
    @JsonProperty("copyright")
    public String getCopyright() { return this.copyright; }

    /**
     * Set the {@code copyright} property (displayed as {@code Copyright}) of the object.
     * @param copyright the value to set
     */
    @JsonProperty("copyright")
    public void setCopyright(String copyright) { this.copyright = copyright; }

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
     * Retrieve the {@code transformation_project} property (displayed as '{@literal Transformation Project}') of the object.
     * @return {@code TransformationProject}
     */
    @JsonProperty("transformation_project")
    public TransformationProject getTransformationProject() { return this.transformationProject; }

    /**
     * Set the {@code transformation_project} property (displayed as {@code Transformation Project}) of the object.
     * @param transformationProject the value to set
     */
    @JsonProperty("transformation_project")
    public void setTransformationProject(TransformationProject transformationProject) { this.transformationProject = transformationProject; }

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
