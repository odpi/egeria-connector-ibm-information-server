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

/**
 * POJO for the {@code stored_procedure_parameter} asset type in IGC, displayed as '{@literal Stored Procedure Parameter}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("stored_procedure_parameter")
public class StoredProcedureParameter extends InformationAsset {

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

    @JsonProperty("fraction")
    protected Number fraction;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("minimum_length")
    protected Number minimumLength;

    @JsonProperty("reads_from_(design)")
    protected ItemList<InformationAsset> readsFromDesign;

    @JsonProperty("reads_from_(operational)")
    protected ItemList<InformationAsset> readsFromOperational;

    @JsonProperty("reads_from_(static)")
    protected ItemList<InformationAsset> readsFromStatic;

    @JsonProperty("reads_from_(user_defined)")
    protected ItemList<InformationAsset> readsFromUserDefined;

    @JsonProperty("stored_procedure")
    protected StoredProcedure storedProcedure;

    @JsonProperty("writes_to_(design)")
    protected ItemList<InformationAsset> writesToDesign;

    @JsonProperty("writes_to_(operational)")
    protected ItemList<InformationAsset> writesToOperational;

    @JsonProperty("writes_to_(static)")
    protected ItemList<InformationAsset> writesToStatic;

    @JsonProperty("writes_to_(user_defined)")
    protected ItemList<InformationAsset> writesToUserDefined;

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
     * Retrieve the {@code fraction} property (displayed as '{@literal Fraction}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("fraction")
    public Number getFraction() { return this.fraction; }

    /**
     * Set the {@code fraction} property (displayed as {@code Fraction}) of the object.
     * @param fraction the value to set
     */
    @JsonProperty("fraction")
    public void setFraction(Number fraction) { this.fraction = fraction; }

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
     * Retrieve the {@code minimum_length} property (displayed as '{@literal Minimum Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("minimum_length")
    public Number getMinimumLength() { return this.minimumLength; }

    /**
     * Set the {@code minimum_length} property (displayed as {@code Minimum Length}) of the object.
     * @param minimumLength the value to set
     */
    @JsonProperty("minimum_length")
    public void setMinimumLength(Number minimumLength) { this.minimumLength = minimumLength; }

    /**
     * Retrieve the {@code reads_from_(design)} property (displayed as '{@literal Reads from (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(design)")
    public ItemList<InformationAsset> getReadsFromDesign() { return this.readsFromDesign; }

    /**
     * Set the {@code reads_from_(design)} property (displayed as {@code Reads from (Design)}) of the object.
     * @param readsFromDesign the value to set
     */
    @JsonProperty("reads_from_(design)")
    public void setReadsFromDesign(ItemList<InformationAsset> readsFromDesign) { this.readsFromDesign = readsFromDesign; }

    /**
     * Retrieve the {@code reads_from_(operational)} property (displayed as '{@literal Reads from (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(operational)")
    public ItemList<InformationAsset> getReadsFromOperational() { return this.readsFromOperational; }

    /**
     * Set the {@code reads_from_(operational)} property (displayed as {@code Reads from (Operational)}) of the object.
     * @param readsFromOperational the value to set
     */
    @JsonProperty("reads_from_(operational)")
    public void setReadsFromOperational(ItemList<InformationAsset> readsFromOperational) { this.readsFromOperational = readsFromOperational; }

    /**
     * Retrieve the {@code reads_from_(static)} property (displayed as '{@literal Reads from (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(static)")
    public ItemList<InformationAsset> getReadsFromStatic() { return this.readsFromStatic; }

    /**
     * Set the {@code reads_from_(static)} property (displayed as {@code Reads from (Static)}) of the object.
     * @param readsFromStatic the value to set
     */
    @JsonProperty("reads_from_(static)")
    public void setReadsFromStatic(ItemList<InformationAsset> readsFromStatic) { this.readsFromStatic = readsFromStatic; }

    /**
     * Retrieve the {@code reads_from_(user_defined)} property (displayed as '{@literal Reads from (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("reads_from_(user_defined)")
    public ItemList<InformationAsset> getReadsFromUserDefined() { return this.readsFromUserDefined; }

    /**
     * Set the {@code reads_from_(user_defined)} property (displayed as {@code Reads from (User-Defined)}) of the object.
     * @param readsFromUserDefined the value to set
     */
    @JsonProperty("reads_from_(user_defined)")
    public void setReadsFromUserDefined(ItemList<InformationAsset> readsFromUserDefined) { this.readsFromUserDefined = readsFromUserDefined; }

    /**
     * Retrieve the {@code stored_procedure} property (displayed as '{@literal Stored Procedure}') of the object.
     * @return {@code StoredProcedure}
     */
    @JsonProperty("stored_procedure")
    public StoredProcedure getStoredProcedure() { return this.storedProcedure; }

    /**
     * Set the {@code stored_procedure} property (displayed as {@code Stored Procedure}) of the object.
     * @param storedProcedure the value to set
     */
    @JsonProperty("stored_procedure")
    public void setStoredProcedure(StoredProcedure storedProcedure) { this.storedProcedure = storedProcedure; }

    /**
     * Retrieve the {@code writes_to_(design)} property (displayed as '{@literal Writes to (Design)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(design)")
    public ItemList<InformationAsset> getWritesToDesign() { return this.writesToDesign; }

    /**
     * Set the {@code writes_to_(design)} property (displayed as {@code Writes to (Design)}) of the object.
     * @param writesToDesign the value to set
     */
    @JsonProperty("writes_to_(design)")
    public void setWritesToDesign(ItemList<InformationAsset> writesToDesign) { this.writesToDesign = writesToDesign; }

    /**
     * Retrieve the {@code writes_to_(operational)} property (displayed as '{@literal Writes to (Operational)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(operational)")
    public ItemList<InformationAsset> getWritesToOperational() { return this.writesToOperational; }

    /**
     * Set the {@code writes_to_(operational)} property (displayed as {@code Writes to (Operational)}) of the object.
     * @param writesToOperational the value to set
     */
    @JsonProperty("writes_to_(operational)")
    public void setWritesToOperational(ItemList<InformationAsset> writesToOperational) { this.writesToOperational = writesToOperational; }

    /**
     * Retrieve the {@code writes_to_(static)} property (displayed as '{@literal Writes to (Static)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(static)")
    public ItemList<InformationAsset> getWritesToStatic() { return this.writesToStatic; }

    /**
     * Set the {@code writes_to_(static)} property (displayed as {@code Writes to (Static)}) of the object.
     * @param writesToStatic the value to set
     */
    @JsonProperty("writes_to_(static)")
    public void setWritesToStatic(ItemList<InformationAsset> writesToStatic) { this.writesToStatic = writesToStatic; }

    /**
     * Retrieve the {@code writes_to_(user_defined)} property (displayed as '{@literal Writes to (User-Defined)}') of the object.
     * @return {@code ItemList<InformationAsset>}
     */
    @JsonProperty("writes_to_(user_defined)")
    public ItemList<InformationAsset> getWritesToUserDefined() { return this.writesToUserDefined; }

    /**
     * Set the {@code writes_to_(user_defined)} property (displayed as {@code Writes to (User-Defined)}) of the object.
     * @param writesToUserDefined the value to set
     */
    @JsonProperty("writes_to_(user_defined)")
    public void setWritesToUserDefined(ItemList<InformationAsset> writesToUserDefined) { this.writesToUserDefined = writesToUserDefined; }

}
