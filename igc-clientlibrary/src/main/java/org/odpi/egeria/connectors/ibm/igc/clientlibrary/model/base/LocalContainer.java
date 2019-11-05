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
import java.util.List;

/**
 * POJO for the {@code local_container} asset type in IGC, displayed as '{@literal Local Container}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("local_container")
public class LocalContainer extends Reference {

    @JsonProperty("annotations")
    protected List<String> annotations;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("job_or_container")
    protected ItemList<MainObject> jobOrContainer;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("referenced_by_containers")
    protected ItemList<ReferencedContainer> referencedByContainers;

    @JsonProperty("referenced_by_stages")
    protected ItemList<Stage> referencedByStages;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("stages")
    protected ItemList<Stage> stages;

    /**
     * Valid values are:
     * <ul>
     *   <li>SERVER (displayed in the UI as 'SERVER')</li>
     *   <li>MAINFRAME (displayed in the UI as 'MAINFRAME')</li>
     *   <li>SEQUENCE (displayed in the UI as 'SEQUENCE')</li>
     *   <li>PARALLEL (displayed in the UI as 'PARALLEL')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    @JsonProperty("version")
    protected String version;

    /**
     * Retrieve the {@code annotations} property (displayed as '{@literal Annotations}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("annotations")
    public List<String> getAnnotations() { return this.annotations; }

    /**
     * Set the {@code annotations} property (displayed as {@code Annotations}) of the object.
     * @param annotations the value to set
     */
    @JsonProperty("annotations")
    public void setAnnotations(List<String> annotations) { this.annotations = annotations; }

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
     * Retrieve the {@code job_or_container} property (displayed as '{@literal Job or Container}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("job_or_container")
    public ItemList<MainObject> getJobOrContainer() { return this.jobOrContainer; }

    /**
     * Set the {@code job_or_container} property (displayed as {@code Job or Container}) of the object.
     * @param jobOrContainer the value to set
     */
    @JsonProperty("job_or_container")
    public void setJobOrContainer(ItemList<MainObject> jobOrContainer) { this.jobOrContainer = jobOrContainer; }

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
     * Retrieve the {@code referenced_by_containers} property (displayed as '{@literal Referenced by Containers}') of the object.
     * @return {@code ItemList<ReferencedContainer>}
     */
    @JsonProperty("referenced_by_containers")
    public ItemList<ReferencedContainer> getReferencedByContainers() { return this.referencedByContainers; }

    /**
     * Set the {@code referenced_by_containers} property (displayed as {@code Referenced by Containers}) of the object.
     * @param referencedByContainers the value to set
     */
    @JsonProperty("referenced_by_containers")
    public void setReferencedByContainers(ItemList<ReferencedContainer> referencedByContainers) { this.referencedByContainers = referencedByContainers; }

    /**
     * Retrieve the {@code referenced_by_stages} property (displayed as '{@literal Referenced by Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("referenced_by_stages")
    public ItemList<Stage> getReferencedByStages() { return this.referencedByStages; }

    /**
     * Set the {@code referenced_by_stages} property (displayed as {@code Referenced by Stages}) of the object.
     * @param referencedByStages the value to set
     */
    @JsonProperty("referenced_by_stages")
    public void setReferencedByStages(ItemList<Stage> referencedByStages) { this.referencedByStages = referencedByStages; }

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
     * Retrieve the {@code stages} property (displayed as '{@literal Stages}') of the object.
     * @return {@code ItemList<Stage>}
     */
    @JsonProperty("stages")
    public ItemList<Stage> getStages() { return this.stages; }

    /**
     * Set the {@code stages} property (displayed as {@code Stages}) of the object.
     * @param stages the value to set
     */
    @JsonProperty("stages")
    public void setStages(ItemList<Stage> stages) { this.stages = stages; }

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
