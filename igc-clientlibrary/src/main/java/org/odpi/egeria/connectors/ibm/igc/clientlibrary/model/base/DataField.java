/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code data_field} asset type in IGC, displayed as '{@literal Data Item}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataField.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Classificationenabledgroup.class, name = "classificationenabledgroup"),
        @JsonSubTypes.Type(value = ColumnDefinition.class, name = "column_definition"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_field")
public class DataField extends DataItem {

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("used_by_analytics_objects")
    protected ItemList<AnalyticsObject> usedByAnalyticsObjects;

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
     * Retrieve the {@code used_by_analytics_objects} property (displayed as '{@literal Used By Data Science}') of the object.
     * @return {@code ItemList<AnalyticsObject>}
     */
    @JsonProperty("used_by_analytics_objects")
    public ItemList<AnalyticsObject> getUsedByAnalyticsObjects() { return this.usedByAnalyticsObjects; }

    /**
     * Set the {@code used_by_analytics_objects} property (displayed as {@code Used By Data Science}) of the object.
     * @param usedByAnalyticsObjects the value to set
     */
    @JsonProperty("used_by_analytics_objects")
    public void setUsedByAnalyticsObjects(ItemList<AnalyticsObject> usedByAnalyticsObjects) { this.usedByAnalyticsObjects = usedByAnalyticsObjects; }

}
