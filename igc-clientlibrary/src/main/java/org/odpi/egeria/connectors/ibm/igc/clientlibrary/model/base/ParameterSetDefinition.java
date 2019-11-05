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
 * POJO for the {@code parameter_set_definition} asset type in IGC, displayed as '{@literal Parameter Set Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("parameter_set_definition")
public class ParameterSetDefinition extends Reference {

    @JsonProperty("context")
    protected TransformationProject context;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("default_value")
    protected String defaultValue;

    @JsonProperty("display_caption")
    protected String displayCaption;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("parameters")
    protected ItemList<DsparameterSet> parameters;

    @JsonProperty("referenced_by_jobs")
    protected ItemList<Jobdef> referencedByJobs;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Valid values are:
     * <ul>
     *   <li>UNUSED (displayed in the UI as 'UNUSED')</li>
     *   <li>ENCRYPTED (displayed in the UI as 'ENCRYPTED')</li>
     *   <li>PATHNAME (displayed in the UI as 'PATHNAME')</li>
     *   <li>STRINGLIST (displayed in the UI as 'STRINGLIST')</li>
     *   <li>INPUTCOL (displayed in the UI as 'INPUTCOL')</li>
     *   <li>OUTPUTCOL (displayed in the UI as 'OUTPUTCOL')</li>
     *   <li>NCHAR (displayed in the UI as 'NCHAR')</li>
     *   <li>PARAMETERSET (displayed in the UI as 'PARAMETERSET')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code context} property (displayed as '{@literal Context}') of the object.
     * @return {@code TransformationProject}
     */
    @JsonProperty("context")
    public TransformationProject getTheContext() { return this.context; }

    /**
     * Set the {@code context} property (displayed as {@code Context}) of the object.
     * @param context the value to set
     */
    @JsonProperty("context")
    public void setTheContext(TransformationProject context) { this.context = context; }

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
     * Retrieve the {@code default_value} property (displayed as '{@literal Default Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("default_value")
    public String getDefaultValue() { return this.defaultValue; }

    /**
     * Set the {@code default_value} property (displayed as {@code Default Value}) of the object.
     * @param defaultValue the value to set
     */
    @JsonProperty("default_value")
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }

    /**
     * Retrieve the {@code display_caption} property (displayed as '{@literal Display Caption}') of the object.
     * @return {@code String}
     */
    @JsonProperty("display_caption")
    public String getDisplayCaption() { return this.displayCaption; }

    /**
     * Set the {@code display_caption} property (displayed as {@code Display Caption}) of the object.
     * @param displayCaption the value to set
     */
    @JsonProperty("display_caption")
    public void setDisplayCaption(String displayCaption) { this.displayCaption = displayCaption; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

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
     * Retrieve the {@code parameters} property (displayed as '{@literal Parameters}') of the object.
     * @return {@code ItemList<DsparameterSet>}
     */
    @JsonProperty("parameters")
    public ItemList<DsparameterSet> getParameters() { return this.parameters; }

    /**
     * Set the {@code parameters} property (displayed as {@code Parameters}) of the object.
     * @param parameters the value to set
     */
    @JsonProperty("parameters")
    public void setParameters(ItemList<DsparameterSet> parameters) { this.parameters = parameters; }

    /**
     * Retrieve the {@code referenced_by_jobs} property (displayed as '{@literal Referenced by Jobs}') of the object.
     * @return {@code ItemList<Jobdef>}
     */
    @JsonProperty("referenced_by_jobs")
    public ItemList<Jobdef> getReferencedByJobs() { return this.referencedByJobs; }

    /**
     * Set the {@code referenced_by_jobs} property (displayed as {@code Referenced by Jobs}) of the object.
     * @param referencedByJobs the value to set
     */
    @JsonProperty("referenced_by_jobs")
    public void setReferencedByJobs(ItemList<Jobdef> referencedByJobs) { this.referencedByJobs = referencedByJobs; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

}
