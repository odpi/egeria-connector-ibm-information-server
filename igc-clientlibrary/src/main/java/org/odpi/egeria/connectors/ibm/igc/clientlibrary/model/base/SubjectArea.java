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
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code subject_area} asset type in IGC, displayed as '{@literal Subject Area}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("subject_area")
public class SubjectArea extends InformationAsset {

    @JsonProperty("author")
    protected String author;

    @JsonProperty("comments")
    protected List<String> comments;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("logical_data_model")
    protected LogicalDataModel logicalDataModel;

    @JsonProperty("logical_entities")
    protected ItemList<LogicalEntity> logicalEntities;

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
     * Retrieve the {@code comments} property (displayed as '{@literal Comments}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("comments")
    public List<String> getComments() { return this.comments; }

    /**
     * Set the {@code comments} property (displayed as {@code Comments}) of the object.
     * @param comments the value to set
     */
    @JsonProperty("comments")
    public void setComments(List<String> comments) { this.comments = comments; }

    /**
     * Retrieve the {@code imported_from} property (displayed as '{@literal Imported From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("imported_from")
    public String getImportedFrom() { return this.importedFrom; }

    /**
     * Set the {@code imported_from} property (displayed as {@code Imported From}) of the object.
     * @param importedFrom the value to set
     */
    @JsonProperty("imported_from")
    public void setImportedFrom(String importedFrom) { this.importedFrom = importedFrom; }

    /**
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

    /**
     * Retrieve the {@code logical_data_model} property (displayed as '{@literal Logical Data Model}') of the object.
     * @return {@code LogicalDataModel}
     */
    @JsonProperty("logical_data_model")
    public LogicalDataModel getLogicalDataModel() { return this.logicalDataModel; }

    /**
     * Set the {@code logical_data_model} property (displayed as {@code Logical Data Model}) of the object.
     * @param logicalDataModel the value to set
     */
    @JsonProperty("logical_data_model")
    public void setLogicalDataModel(LogicalDataModel logicalDataModel) { this.logicalDataModel = logicalDataModel; }

    /**
     * Retrieve the {@code logical_entities} property (displayed as '{@literal Logical Entities}') of the object.
     * @return {@code ItemList<LogicalEntity>}
     */
    @JsonProperty("logical_entities")
    public ItemList<LogicalEntity> getLogicalEntities() { return this.logicalEntities; }

    /**
     * Set the {@code logical_entities} property (displayed as {@code Logical Entities}) of the object.
     * @param logicalEntities the value to set
     */
    @JsonProperty("logical_entities")
    public void setLogicalEntities(ItemList<LogicalEntity> logicalEntities) { this.logicalEntities = logicalEntities; }

}
