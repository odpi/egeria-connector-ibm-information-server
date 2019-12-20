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
import java.util.List;

/**
 * POJO for the {@code idoc_segment_type} asset type in IGC, displayed as '{@literal IDoc Segment Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=IdocSegmentType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("idoc_segment_type")
public class IdocSegmentType extends InformationAsset {

    /**
     * Valid values are:
     * <ul>
     *   <li>INT8 (displayed in the UI as 'INT8')</li>
     *   <li>INT16 (displayed in the UI as 'INT16')</li>
     *   <li>INT32 (displayed in the UI as 'INT32')</li>
     *   <li>INT64 (displayed in the UI as 'INT64')</li>
     *   <li>SFLOAT (displayed in the UI as 'SFLOAT')</li>
     *   <li>DFLOAT (displayed in the UI as 'DFLOAT')</li>
     *   <li>QFLOAT (displayed in the UI as 'QFLOAT')</li>
     *   <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *   <li>STRING (displayed in the UI as 'STRING')</li>
     *   <li>BINARY (displayed in the UI as 'BINARY')</li>
     *   <li>BOOLEAN (displayed in the UI as 'BOOLEAN')</li>
     *   <li>DATE (displayed in the UI as 'DATE')</li>
     *   <li>TIME (displayed in the UI as 'TIME')</li>
     *   <li>DATETIME (displayed in the UI as 'DATETIME')</li>
     *   <li>DURATION (displayed in the UI as 'DURATION')</li>
     *   <li>CHOICE (displayed in the UI as 'CHOICE')</li>
     *   <li>ORDERED_GROUP (displayed in the UI as 'ORDERED_GROUP')</li>
     *   <li>UNORDERED_GROUP (displayed in the UI as 'UNORDERED_GROUP')</li>
     *   <li>GUID (displayed in the UI as 'GUID')</li>
     *   <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     *   <li>JSON (displayed in the UI as 'JSON')</li>
     *   <li>XML (displayed in the UI as 'XML')</li>
     * </ul>
     */
    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("idoc_segment_type")
    protected ItemList<MainObject> idocSegmentType;

    @JsonProperty("idoc_type")
    protected IdocType idocType;

    @JsonProperty("level")
    protected Number level;

    @JsonProperty("min_/_max")
    protected List<String> minMax;

    @JsonProperty("of_idoc_segment_type")
    protected DataItemDefinition ofIdocSegmentType;

    @JsonProperty("of_idoc_type")
    protected ItemList<IdocType> ofIdocType;

    @JsonProperty("segment_definition")
    protected String segmentDefinition;

    @JsonProperty("used_by_analytics_objects")
    protected ItemList<AnalyticsObject> usedByAnalyticsObjects;

    /**
     * Retrieve the {@code data_type} property (displayed as '{@literal Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_type")
    public String getDataType() { return this.dataType; }

    /**
     * Set the {@code data_type} property (displayed as {@code Data Type}) of the object.
     * @param dataType the value to set
     */
    @JsonProperty("data_type")
    public void setDataType(String dataType) { this.dataType = dataType; }

    /**
     * Retrieve the {@code idoc_segment_type} property (displayed as '{@literal IDoc Segment Type}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("idoc_segment_type")
    public ItemList<MainObject> getIdocSegmentType() { return this.idocSegmentType; }

    /**
     * Set the {@code idoc_segment_type} property (displayed as {@code IDoc Segment Type}) of the object.
     * @param idocSegmentType the value to set
     */
    @JsonProperty("idoc_segment_type")
    public void setIdocSegmentType(ItemList<MainObject> idocSegmentType) { this.idocSegmentType = idocSegmentType; }

    /**
     * Retrieve the {@code idoc_type} property (displayed as '{@literal IDoc Type}') of the object.
     * @return {@code IdocType}
     */
    @JsonProperty("idoc_type")
    public IdocType getIdocType() { return this.idocType; }

    /**
     * Set the {@code idoc_type} property (displayed as {@code IDoc Type}) of the object.
     * @param idocType the value to set
     */
    @JsonProperty("idoc_type")
    public void setIdocType(IdocType idocType) { this.idocType = idocType; }

    /**
     * Retrieve the {@code level} property (displayed as '{@literal Level}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("level")
    public Number getLevel() { return this.level; }

    /**
     * Set the {@code level} property (displayed as {@code Level}) of the object.
     * @param level the value to set
     */
    @JsonProperty("level")
    public void setLevel(Number level) { this.level = level; }

    /**
     * Retrieve the {@code min_/_max} property (displayed as '{@literal Min / Max}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("min_/_max")
    public List<String> getMinMax() { return this.minMax; }

    /**
     * Set the {@code min_/_max} property (displayed as {@code Min / Max}) of the object.
     * @param minMax the value to set
     */
    @JsonProperty("min_/_max")
    public void setMinMax(List<String> minMax) { this.minMax = minMax; }

    /**
     * Retrieve the {@code of_idoc_segment_type} property (displayed as '{@literal IDoc Segment Type}') of the object.
     * @return {@code DataItemDefinition}
     */
    @JsonProperty("of_idoc_segment_type")
    public DataItemDefinition getOfIdocSegmentType() { return this.ofIdocSegmentType; }

    /**
     * Set the {@code of_idoc_segment_type} property (displayed as {@code IDoc Segment Type}) of the object.
     * @param ofIdocSegmentType the value to set
     */
    @JsonProperty("of_idoc_segment_type")
    public void setOfIdocSegmentType(DataItemDefinition ofIdocSegmentType) { this.ofIdocSegmentType = ofIdocSegmentType; }

    /**
     * Retrieve the {@code of_idoc_type} property (displayed as '{@literal IDoc Type}') of the object.
     * @return {@code ItemList<IdocType>}
     */
    @JsonProperty("of_idoc_type")
    public ItemList<IdocType> getOfIdocType() { return this.ofIdocType; }

    /**
     * Set the {@code of_idoc_type} property (displayed as {@code IDoc Type}) of the object.
     * @param ofIdocType the value to set
     */
    @JsonProperty("of_idoc_type")
    public void setOfIdocType(ItemList<IdocType> ofIdocType) { this.ofIdocType = ofIdocType; }

    /**
     * Retrieve the {@code segment_definition} property (displayed as '{@literal Segment Definition}') of the object.
     * @return {@code String}
     */
    @JsonProperty("segment_definition")
    public String getSegmentDefinition() { return this.segmentDefinition; }

    /**
     * Set the {@code segment_definition} property (displayed as {@code Segment Definition}) of the object.
     * @param segmentDefinition the value to set
     */
    @JsonProperty("segment_definition")
    public void setSegmentDefinition(String segmentDefinition) { this.segmentDefinition = segmentDefinition; }

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
