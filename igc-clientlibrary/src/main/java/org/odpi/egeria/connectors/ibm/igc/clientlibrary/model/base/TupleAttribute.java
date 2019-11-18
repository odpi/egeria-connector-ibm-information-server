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
 * POJO for the {@code tuple_attribute} asset type in IGC, displayed as '{@literal Tuple Attribute}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=TupleAttribute.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("tuple_attribute")
public class TupleAttribute extends InformationAsset {

    /**
     * Valid values are:
     * <ul>
     *   <li>boolean (displayed in the UI as 'boolean')</li>
     *   <li>enum (displayed in the UI as 'enum')</li>
     *   <li>int8 (displayed in the UI as 'int8')</li>
     *   <li>int16 (displayed in the UI as 'int16')</li>
     *   <li>int32 (displayed in the UI as 'int32')</li>
     *   <li>int64 (displayed in the UI as 'int64')</li>
     *   <li>uint8 (displayed in the UI as 'uint8')</li>
     *   <li>uint16 (displayed in the UI as 'uint16')</li>
     *   <li>uint32 (displayed in the UI as 'uint32')</li>
     *   <li>uint64 (displayed in the UI as 'uint64')</li>
     *   <li>float32 (displayed in the UI as 'float32')</li>
     *   <li>float64 (displayed in the UI as 'float64')</li>
     *   <li>decimal32 (displayed in the UI as 'decimal32')</li>
     *   <li>decimal64 (displayed in the UI as 'decimal64')</li>
     *   <li>decimal128 (displayed in the UI as 'decimal128')</li>
     *   <li>complex32 (displayed in the UI as 'complex32')</li>
     *   <li>complex64 (displayed in the UI as 'complex64')</li>
     *   <li>timestamp (displayed in the UI as 'timestamp')</li>
     *   <li>rstring (displayed in the UI as 'rstring')</li>
     *   <li>ustring (displayed in the UI as 'ustring')</li>
     *   <li>blob (displayed in the UI as 'blob')</li>
     *   <li>xml (displayed in the UI as 'xml')</li>
     *   <li>tuple (displayed in the UI as 'tuple')</li>
     *   <li>list (displayed in the UI as 'list')</li>
     *   <li>set (displayed in the UI as 'set')</li>
     *   <li>map (displayed in the UI as 'map')</li>
     * </ul>
     */
    @JsonProperty("collection_type")
    protected String collectionType;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("read_by_(design)")
    protected ItemList<InformationAsset> readByDesign;

    @JsonProperty("read_by_(operational)")
    protected ItemList<InformationAsset> readByOperational;

    @JsonProperty("read_by_(static)")
    protected ItemList<InformationAsset> readByStatic;

    @JsonProperty("read_by_(user_defined)")
    protected ItemList<InformationAsset> readByUserDefined;

    @JsonProperty("tuple")
    protected RootTuple tuple;

    @JsonProperty("tuple_attribute")
    protected ItemList<TupleAttribute> tupleAttribute;

    @JsonProperty("tuple_or_tuple_attribute")
    protected ItemList<MainObject> tupleOrTupleAttribute;

    /**
     * Valid values are:
     * <ul>
     *   <li>boolean (displayed in the UI as 'boolean')</li>
     *   <li>enum (displayed in the UI as 'enum')</li>
     *   <li>int8 (displayed in the UI as 'int8')</li>
     *   <li>int16 (displayed in the UI as 'int16')</li>
     *   <li>int32 (displayed in the UI as 'int32')</li>
     *   <li>int64 (displayed in the UI as 'int64')</li>
     *   <li>uint8 (displayed in the UI as 'uint8')</li>
     *   <li>uint16 (displayed in the UI as 'uint16')</li>
     *   <li>uint32 (displayed in the UI as 'uint32')</li>
     *   <li>uint64 (displayed in the UI as 'uint64')</li>
     *   <li>float32 (displayed in the UI as 'float32')</li>
     *   <li>float64 (displayed in the UI as 'float64')</li>
     *   <li>decimal32 (displayed in the UI as 'decimal32')</li>
     *   <li>decimal64 (displayed in the UI as 'decimal64')</li>
     *   <li>decimal128 (displayed in the UI as 'decimal128')</li>
     *   <li>complex32 (displayed in the UI as 'complex32')</li>
     *   <li>complex64 (displayed in the UI as 'complex64')</li>
     *   <li>timestamp (displayed in the UI as 'timestamp')</li>
     *   <li>rstring (displayed in the UI as 'rstring')</li>
     *   <li>ustring (displayed in the UI as 'ustring')</li>
     *   <li>blob (displayed in the UI as 'blob')</li>
     *   <li>xml (displayed in the UI as 'xml')</li>
     *   <li>tuple (displayed in the UI as 'tuple')</li>
     *   <li>list (displayed in the UI as 'list')</li>
     *   <li>set (displayed in the UI as 'set')</li>
     *   <li>map (displayed in the UI as 'map')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    @JsonProperty("written_by_(design)")
    protected ItemList<InformationAsset> writtenByDesign;

    @JsonProperty("written_by_(operational)")
    protected ItemList<InformationAsset> writtenByOperational;

    @JsonProperty("written_by_(static)")
    protected ItemList<InformationAsset> writtenByStatic;

    @JsonProperty("written_by_(user_defined)")
    protected ItemList<InformationAsset> writtenByUserDefined;

    /**
     * Retrieve the {@code collection_type} property (displayed as '{@literal Collection Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("collection_type")
    public String getCollectionType() { return this.collectionType; }

    /**
     * Set the {@code collection_type} property (displayed as {@code Collection Type}) of the object.
     * @param collectionType the value to set
     */
    @JsonProperty("collection_type")
    public void setCollectionType(String collectionType) { this.collectionType = collectionType; }

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
     * Retrieve the {@code length} property (displayed as '{@literal Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("length")
    public Number getLength() { return this.length; }

    /**
     * Set the {@code length} property (displayed as {@code Length}) of the object.
     * @param length the value to set
     */
    @JsonProperty("length")
    public void setLength(Number length) { this.length = length; }

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
     * Retrieve the {@code read_by_(design)} property (displayed as '{@literal Read by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(design)")
    public ItemList<InformationAsset> getReadByDesign() { return this.readByDesign; }

    /**
     * Set the {@code read_by_(design)} property (displayed as {@code Read by (Design)}) of the object.
     * @param readByDesign the value to set
     */
    @JsonProperty("read_by_(design)")
    public void setReadByDesign(ItemList<InformationAsset> readByDesign) { this.readByDesign = readByDesign; }

    /**
     * Retrieve the {@code read_by_(operational)} property (displayed as '{@literal Read by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(operational)")
    public ItemList<InformationAsset> getReadByOperational() { return this.readByOperational; }

    /**
     * Set the {@code read_by_(operational)} property (displayed as {@code Read by (Operational)}) of the object.
     * @param readByOperational the value to set
     */
    @JsonProperty("read_by_(operational)")
    public void setReadByOperational(ItemList<InformationAsset> readByOperational) { this.readByOperational = readByOperational; }

    /**
     * Retrieve the {@code read_by_(static)} property (displayed as '{@literal Read by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(static)")
    public ItemList<InformationAsset> getReadByStatic() { return this.readByStatic; }

    /**
     * Set the {@code read_by_(static)} property (displayed as {@code Read by (Static)}) of the object.
     * @param readByStatic the value to set
     */
    @JsonProperty("read_by_(static)")
    public void setReadByStatic(ItemList<InformationAsset> readByStatic) { this.readByStatic = readByStatic; }

    /**
     * Retrieve the {@code read_by_(user_defined)} property (displayed as '{@literal Read by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("read_by_(user_defined)")
    public ItemList<InformationAsset> getReadByUserDefined() { return this.readByUserDefined; }

    /**
     * Set the {@code read_by_(user_defined)} property (displayed as {@code Read by (User-Defined)}) of the object.
     * @param readByUserDefined the value to set
     */
    @JsonProperty("read_by_(user_defined)")
    public void setReadByUserDefined(ItemList<InformationAsset> readByUserDefined) { this.readByUserDefined = readByUserDefined; }

    /**
     * Retrieve the {@code tuple} property (displayed as '{@literal Tuple}') of the object.
     * @return {@code RootTuple}
     */
    @JsonProperty("tuple")
    public RootTuple getTuple() { return this.tuple; }

    /**
     * Set the {@code tuple} property (displayed as {@code Tuple}) of the object.
     * @param tuple the value to set
     */
    @JsonProperty("tuple")
    public void setTuple(RootTuple tuple) { this.tuple = tuple; }

    /**
     * Retrieve the {@code tuple_attribute} property (displayed as '{@literal Tuple Attribute}') of the object.
     * @return {@code ItemList<TupleAttribute>}
     */
    @JsonProperty("tuple_attribute")
    public ItemList<TupleAttribute> getTupleAttribute() { return this.tupleAttribute; }

    /**
     * Set the {@code tuple_attribute} property (displayed as {@code Tuple Attribute}) of the object.
     * @param tupleAttribute the value to set
     */
    @JsonProperty("tuple_attribute")
    public void setTupleAttribute(ItemList<TupleAttribute> tupleAttribute) { this.tupleAttribute = tupleAttribute; }

    /**
     * Retrieve the {@code tuple_or_tuple_attribute} property (displayed as '{@literal Tuple or Tuple Attribute}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("tuple_or_tuple_attribute")
    public ItemList<MainObject> getTupleOrTupleAttribute() { return this.tupleOrTupleAttribute; }

    /**
     * Set the {@code tuple_or_tuple_attribute} property (displayed as {@code Tuple or Tuple Attribute}) of the object.
     * @param tupleOrTupleAttribute the value to set
     */
    @JsonProperty("tuple_or_tuple_attribute")
    public void setTupleOrTupleAttribute(ItemList<MainObject> tupleOrTupleAttribute) { this.tupleOrTupleAttribute = tupleOrTupleAttribute; }

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
     * Retrieve the {@code written_by_(design)} property (displayed as '{@literal Written by (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(design)")
    public ItemList<InformationAsset> getWrittenByDesign() { return this.writtenByDesign; }

    /**
     * Set the {@code written_by_(design)} property (displayed as {@code Written by (Design)}) of the object.
     * @param writtenByDesign the value to set
     */
    @JsonProperty("written_by_(design)")
    public void setWrittenByDesign(ItemList<InformationAsset> writtenByDesign) { this.writtenByDesign = writtenByDesign; }

    /**
     * Retrieve the {@code written_by_(operational)} property (displayed as '{@literal Written by (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(operational)")
    public ItemList<InformationAsset> getWrittenByOperational() { return this.writtenByOperational; }

    /**
     * Set the {@code written_by_(operational)} property (displayed as {@code Written by (Operational)}) of the object.
     * @param writtenByOperational the value to set
     */
    @JsonProperty("written_by_(operational)")
    public void setWrittenByOperational(ItemList<InformationAsset> writtenByOperational) { this.writtenByOperational = writtenByOperational; }

    /**
     * Retrieve the {@code written_by_(static)} property (displayed as '{@literal Written by (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(static)")
    public ItemList<InformationAsset> getWrittenByStatic() { return this.writtenByStatic; }

    /**
     * Set the {@code written_by_(static)} property (displayed as {@code Written by (Static)}) of the object.
     * @param writtenByStatic the value to set
     */
    @JsonProperty("written_by_(static)")
    public void setWrittenByStatic(ItemList<InformationAsset> writtenByStatic) { this.writtenByStatic = writtenByStatic; }

    /**
     * Retrieve the {@code written_by_(user_defined)} property (displayed as '{@literal Written by (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("written_by_(user_defined)")
    public ItemList<InformationAsset> getWrittenByUserDefined() { return this.writtenByUserDefined; }

    /**
     * Set the {@code written_by_(user_defined)} property (displayed as {@code Written by (User-Defined)}) of the object.
     * @param writtenByUserDefined the value to set
     */
    @JsonProperty("written_by_(user_defined)")
    public void setWrittenByUserDefined(ItemList<InformationAsset> writtenByUserDefined) { this.writtenByUserDefined = writtenByUserDefined; }

}
