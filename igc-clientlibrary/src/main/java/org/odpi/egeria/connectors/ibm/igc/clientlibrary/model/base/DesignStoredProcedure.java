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
import java.util.Date;

/**
 * POJO for the {@code design_stored_procedure} asset type in IGC, displayed as '{@literal Design Stored Procedure}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DesignStoredProcedure.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("design_stored_procedure")
public class DesignStoredProcedure extends Datagroup {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    /**
     * No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("design_stored_procedure_parameters")
    protected ItemList<DesignStoredProcedureParameter> designStoredProcedureParameters;

    @JsonProperty("error_code")
    protected String errorCode;

    @JsonProperty("implemented_by_stored_procedures")
    protected ItemList<StoredProcedure> implementedByStoredProcedures;

    @JsonProperty("imported_from")
    protected String importedFrom;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("physical_data_model")
    protected PhysicalDataModel physicalDataModel;

    @JsonProperty("source_code")
    protected String sourceCode;

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @param aliasBusinessName the value to set
     */
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code blueprint_elements} property (displayed as '{@literal Blueprint Elements}') of the object.
     * No longer applicable from 11.7.0.0 onwards.
     *
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * No longer applicable from 11.7.0.0 onwards.
     *
     * @param blueprintElements the value to set
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code design_stored_procedure_parameters} property (displayed as '{@literal Design Stored Procedure Parameters}') of the object.
     * @return {@code ItemList<DesignStoredProcedureParameter>}
     */
    @JsonProperty("design_stored_procedure_parameters")
    public ItemList<DesignStoredProcedureParameter> getDesignStoredProcedureParameters() { return this.designStoredProcedureParameters; }

    /**
     * Set the {@code design_stored_procedure_parameters} property (displayed as {@code Design Stored Procedure Parameters}) of the object.
     * @param designStoredProcedureParameters the value to set
     */
    @JsonProperty("design_stored_procedure_parameters")
    public void setDesignStoredProcedureParameters(ItemList<DesignStoredProcedureParameter> designStoredProcedureParameters) { this.designStoredProcedureParameters = designStoredProcedureParameters; }

    /**
     * Retrieve the {@code error_code} property (displayed as '{@literal Error Code}') of the object.
     * @return {@code String}
     */
    @JsonProperty("error_code")
    public String getErrorCode() { return this.errorCode; }

    /**
     * Set the {@code error_code} property (displayed as {@code Error Code}) of the object.
     * @param errorCode the value to set
     */
    @JsonProperty("error_code")
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    /**
     * Retrieve the {@code implemented_by_stored_procedures} property (displayed as '{@literal Implemented by Stored Procedures}') of the object.
     * @return {@code ItemList<StoredProcedure>}
     */
    @JsonProperty("implemented_by_stored_procedures")
    public ItemList<StoredProcedure> getImplementedByStoredProcedures() { return this.implementedByStoredProcedures; }

    /**
     * Set the {@code implemented_by_stored_procedures} property (displayed as {@code Implemented by Stored Procedures}) of the object.
     * @param implementedByStoredProcedures the value to set
     */
    @JsonProperty("implemented_by_stored_procedures")
    public void setImplementedByStoredProcedures(ItemList<StoredProcedure> implementedByStoredProcedures) { this.implementedByStoredProcedures = implementedByStoredProcedures; }

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
     * Retrieve the {@code physical_data_model} property (displayed as '{@literal Physical Data Model}') of the object.
     * @return {@code PhysicalDataModel}
     */
    @JsonProperty("physical_data_model")
    public PhysicalDataModel getPhysicalDataModel() { return this.physicalDataModel; }

    /**
     * Set the {@code physical_data_model} property (displayed as {@code Physical Data Model}) of the object.
     * @param physicalDataModel the value to set
     */
    @JsonProperty("physical_data_model")
    public void setPhysicalDataModel(PhysicalDataModel physicalDataModel) { this.physicalDataModel = physicalDataModel; }

    /**
     * Retrieve the {@code source_code} property (displayed as '{@literal Source Code}') of the object.
     * @return {@code String}
     */
    @JsonProperty("source_code")
    public String getSourceCode() { return this.sourceCode; }

    /**
     * Set the {@code source_code} property (displayed as {@code Source Code}) of the object.
     * @param sourceCode the value to set
     */
    @JsonProperty("source_code")
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

}
