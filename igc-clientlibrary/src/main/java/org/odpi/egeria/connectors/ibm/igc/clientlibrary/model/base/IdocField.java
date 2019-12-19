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
 * POJO for the {@code idoc_field} asset type in IGC, displayed as '{@literal IDoc Field}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=IdocField.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("idoc_field")
public class IdocField extends InformationAsset {

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
    protected ItemList<IdocSegmentType> idocSegmentType;

    @JsonProperty("idoc_type")
    protected ItemList<IdocType> idocType;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("native_id")
    protected String nativeId;

    /**
     * Valid values are:
     * <ul>
     *   <li>CHAR (displayed in the UI as 'CHAR')</li>
     *   <li>VARCHAR (displayed in the UI as 'VARCHAR')</li>
     *   <li>LONGVARCHAR (displayed in the UI as 'LONGVARCHAR')</li>
     *   <li>WCHAR (displayed in the UI as 'WCHAR')</li>
     *   <li>WVARCHAR (displayed in the UI as 'WVARCHAR')</li>
     *   <li>WLONGVARCHAR (displayed in the UI as 'WLONGVARCHAR')</li>
     *   <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *   <li>NUMERIC (displayed in the UI as 'NUMERIC')</li>
     *   <li>SMALLINT (displayed in the UI as 'SMALLINT')</li>
     *   <li>INTEGER (displayed in the UI as 'INTEGER')</li>
     *   <li>REAL (displayed in the UI as 'REAL')</li>
     *   <li>FLOAT (displayed in the UI as 'FLOAT')</li>
     *   <li>DOUBLE (displayed in the UI as 'DOUBLE')</li>
     *   <li>BIT (displayed in the UI as 'BIT')</li>
     *   <li>TINYINT (displayed in the UI as 'TINYINT')</li>
     *   <li>BIGINT (displayed in the UI as 'BIGINT')</li>
     *   <li>BINARY (displayed in the UI as 'BINARY')</li>
     *   <li>VARBINARY (displayed in the UI as 'VARBINARY')</li>
     *   <li>LONGVARBINARY (displayed in the UI as 'LONGVARBINARY')</li>
     *   <li>DATE (displayed in the UI as 'DATE')</li>
     *   <li>TIME (displayed in the UI as 'TIME')</li>
     *   <li>TIMESTAMP (displayed in the UI as 'TIMESTAMP')</li>
     *   <li>GUID (displayed in the UI as 'GUID')</li>
     *   <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     * </ul>
     */
    @JsonProperty("odbc_type")
    protected String odbcType;

    @JsonProperty("type")
    protected String type;

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
     * @return {@code ItemList<IdocSegmentType>}
     */
    @JsonProperty("idoc_segment_type")
    public ItemList<IdocSegmentType> getIdocSegmentType() { return this.idocSegmentType; }

    /**
     * Set the {@code idoc_segment_type} property (displayed as {@code IDoc Segment Type}) of the object.
     * @param idocSegmentType the value to set
     */
    @JsonProperty("idoc_segment_type")
    public void setIdocSegmentType(ItemList<IdocSegmentType> idocSegmentType) { this.idocSegmentType = idocSegmentType; }

    /**
     * Retrieve the {@code idoc_type} property (displayed as '{@literal IDoc Type}') of the object.
     * @return {@code ItemList<IdocType>}
     */
    @JsonProperty("idoc_type")
    public ItemList<IdocType> getIdocType() { return this.idocType; }

    /**
     * Set the {@code idoc_type} property (displayed as {@code IDoc Type}) of the object.
     * @param idocType the value to set
     */
    @JsonProperty("idoc_type")
    public void setIdocType(ItemList<IdocType> idocType) { this.idocType = idocType; }

    /**
     * Retrieve the {@code impacted_by} property (displayed as '{@literal Impacted by}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacted_by")
    public ItemList<InformationAsset> getImpactedBy() { return this.impactedBy; }

    /**
     * Set the {@code impacted_by} property (displayed as {@code Impacted by}) of the object.
     * @param impactedBy the value to set
     */
    @JsonProperty("impacted_by")
    public void setImpactedBy(ItemList<InformationAsset> impactedBy) { this.impactedBy = impactedBy; }

    /**
     * Retrieve the {@code impacts_on} property (displayed as '{@literal Impacts on}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("impacts_on")
    public ItemList<InformationAsset> getImpactsOn() { return this.impactsOn; }

    /**
     * Set the {@code impacts_on} property (displayed as {@code Impacts on}) of the object.
     * @param impactsOn the value to set
     */
    @JsonProperty("impacts_on")
    public void setImpactsOn(ItemList<InformationAsset> impactsOn) { this.impactsOn = impactsOn; }

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
     * Retrieve the {@code odbc_type} property (displayed as '{@literal ODBC Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("odbc_type")
    public String getOdbcType() { return this.odbcType; }

    /**
     * Set the {@code odbc_type} property (displayed as {@code ODBC Type}) of the object.
     * @param odbcType the value to set
     */
    @JsonProperty("odbc_type")
    public void setOdbcType(String odbcType) { this.odbcType = odbcType; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Native Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Native Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

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
