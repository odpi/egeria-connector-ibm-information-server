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
 * POJO for the {@code datastage_item} asset type in IGC, displayed as '{@literal DataStage Item}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DatastageItem.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("datastage_item")
public class DatastageItem extends Reference {

    @JsonProperty("class_name")
    protected String className;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("reference_item")
    protected Boolean referenceItem;

    @JsonProperty("repository_id")
    protected String repositoryId;

    /**
     * Retrieve the {@code class_name} property (displayed as '{@literal Class Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("class_name")
    public String getClassName() { return this.className; }

    /**
     * Set the {@code class_name} property (displayed as {@code Class Name}) of the object.
     * @param className the value to set
     */
    @JsonProperty("class_name")
    public void setClassName(String className) { this.className = className; }

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
     * Retrieve the {@code reference_item} property (displayed as '{@literal Reference Item}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("reference_item")
    public Boolean getReferenceItem() { return this.referenceItem; }

    /**
     * Set the {@code reference_item} property (displayed as {@code Reference Item}) of the object.
     * @param referenceItem the value to set
     */
    @JsonProperty("reference_item")
    public void setReferenceItem(Boolean referenceItem) { this.referenceItem = referenceItem; }

    /**
     * Retrieve the {@code repository_id} property (displayed as '{@literal Repository ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("repository_id")
    public String getRepositoryId() { return this.repositoryId; }

    /**
     * Set the {@code repository_id} property (displayed as {@code Repository ID}) of the object.
     * @param repositoryId the value to set
     */
    @JsonProperty("repository_id")
    public void setRepositoryId(String repositoryId) { this.repositoryId = repositoryId; }

}
