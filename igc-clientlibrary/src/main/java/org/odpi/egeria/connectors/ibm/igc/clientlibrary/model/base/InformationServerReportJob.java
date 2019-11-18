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
 * POJO for the {@code information_server_report_(job)} asset type in IGC, displayed as '{@literal Information Server Report (Job)}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=InformationServerReportJob.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("information_server_report_(job)")
public class InformationServerReportJob extends Reference {

    @JsonProperty("creator")
    protected String creator;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("host_(engine)")
    protected String hostEngine;

    @JsonProperty("job")
    protected List<String> job;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("product")
    protected String product;

    @JsonProperty("transformation_project")
    protected String transformationProject;

    /**
     * Retrieve the {@code creator} property (displayed as '{@literal Creator}') of the object.
     * @return {@code String}
     */
    @JsonProperty("creator")
    public String getCreator() { return this.creator; }

    /**
     * Set the {@code creator} property (displayed as {@code Creator}) of the object.
     * @param creator the value to set
     */
    @JsonProperty("creator")
    public void setCreator(String creator) { this.creator = creator; }

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("description")
    public String getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(String description) { this.description = description; }

    /**
     * Retrieve the {@code host_(engine)} property (displayed as '{@literal Host (Engine)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("host_(engine)")
    public String getHostEngine() { return this.hostEngine; }

    /**
     * Set the {@code host_(engine)} property (displayed as {@code Host (Engine)}) of the object.
     * @param hostEngine the value to set
     */
    @JsonProperty("host_(engine)")
    public void setHostEngine(String hostEngine) { this.hostEngine = hostEngine; }

    /**
     * Retrieve the {@code job} property (displayed as '{@literal Job}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("job")
    public List<String> getJob() { return this.job; }

    /**
     * Set the {@code job} property (displayed as {@code Job}) of the object.
     * @param job the value to set
     */
    @JsonProperty("job")
    public void setJob(List<String> job) { this.job = job; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

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
     * Retrieve the {@code product} property (displayed as '{@literal Product}') of the object.
     * @return {@code String}
     */
    @JsonProperty("product")
    public String getProduct() { return this.product; }

    /**
     * Set the {@code product} property (displayed as {@code Product}) of the object.
     * @param product the value to set
     */
    @JsonProperty("product")
    public void setProduct(String product) { this.product = product; }

    /**
     * Retrieve the {@code transformation_project} property (displayed as '{@literal Transformation Project}') of the object.
     * @return {@code String}
     */
    @JsonProperty("transformation_project")
    public String getTransformationProject() { return this.transformationProject; }

    /**
     * Set the {@code transformation_project} property (displayed as {@code Transformation Project}) of the object.
     * @param transformationProject the value to set
     */
    @JsonProperty("transformation_project")
    public void setTransformationProject(String transformationProject) { this.transformationProject = transformationProject; }

}
