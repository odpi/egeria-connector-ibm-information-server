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
 * POJO for the {@code physical_object} asset type in IGC, displayed as '{@literal Physical Object}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=PhysicalObject.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("physical_object")
public class PhysicalObject extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    /**
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    protected ItemList<BlueprintElementLink> blueprintElements;

    @JsonProperty("contains_physical_object_attributes")
    protected ItemList<PhysicalObjectAttribute> containsPhysicalObjectAttributes;

    @JsonProperty("contains_physical_objects")
    protected ItemList<PhysicalObject> containsPhysicalObjects;

    @JsonProperty("context")
    protected ItemList<MainObject> context;

    @JsonProperty("impacted_by")
    protected ItemList<InformationAsset> impactedBy;

    @JsonProperty("impacts_on")
    protected ItemList<InformationAsset> impactsOn;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("maps_database_tables_or_views")
    protected ItemList<Datagroup> mapsDatabaseTablesOrViews;

    @JsonProperty("mdm_model")
    protected MdmModel mdmModel;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("physical_object")
    protected ItemList<PhysicalObject> physicalObject;

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
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @return {@code ItemList<BlueprintElementLink>}
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public ItemList<BlueprintElementLink> getBlueprintElements() { return this.blueprintElements; }

    /**
     * Set the {@code blueprint_elements} property (displayed as {@code Blueprint Elements}) of the object.
     * @deprecated No longer applicable from 11.7.0.0 onwards.
     * @param blueprintElements the value to set
     */
    @Deprecated
    @JsonProperty("blueprint_elements")
    public void setBlueprintElements(ItemList<BlueprintElementLink> blueprintElements) { this.blueprintElements = blueprintElements; }

    /**
     * Retrieve the {@code contains_physical_object_attributes} property (displayed as '{@literal Contains Physical Object Attributes}') of the object.
     * @return {@code ItemList<PhysicalObjectAttribute>}
     */
    @JsonProperty("contains_physical_object_attributes")
    public ItemList<PhysicalObjectAttribute> getContainsPhysicalObjectAttributes() { return this.containsPhysicalObjectAttributes; }

    /**
     * Set the {@code contains_physical_object_attributes} property (displayed as {@code Contains Physical Object Attributes}) of the object.
     * @param containsPhysicalObjectAttributes the value to set
     */
    @JsonProperty("contains_physical_object_attributes")
    public void setContainsPhysicalObjectAttributes(ItemList<PhysicalObjectAttribute> containsPhysicalObjectAttributes) { this.containsPhysicalObjectAttributes = containsPhysicalObjectAttributes; }

    /**
     * Retrieve the {@code contains_physical_objects} property (displayed as '{@literal Contains Physical Objects}') of the object.
     * @return {@code ItemList<PhysicalObject>}
     */
    @JsonProperty("contains_physical_objects")
    public ItemList<PhysicalObject> getContainsPhysicalObjects() { return this.containsPhysicalObjects; }

    /**
     * Set the {@code contains_physical_objects} property (displayed as {@code Contains Physical Objects}) of the object.
     * @param containsPhysicalObjects the value to set
     */
    @JsonProperty("contains_physical_objects")
    public void setContainsPhysicalObjects(ItemList<PhysicalObject> containsPhysicalObjects) { this.containsPhysicalObjects = containsPhysicalObjects; }

    /**
     * Retrieve the {@code context} property (displayed as '{@literal Context}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("context")
    public ItemList<MainObject> getTheContext() { return this.context; }

    /**
     * Set the {@code context} property (displayed as {@code Context}) of the object.
     * @param context the value to set
     */
    @JsonProperty("context")
    public void setTheContext(ItemList<MainObject> context) { this.context = context; }

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
     * Retrieve the {@code maps_database_tables_or_views} property (displayed as '{@literal Maps Database Tables or Views}') of the object.
     * @return {@code ItemList<Datagroup>}
     */
    @JsonProperty("maps_database_tables_or_views")
    public ItemList<Datagroup> getMapsDatabaseTablesOrViews() { return this.mapsDatabaseTablesOrViews; }

    /**
     * Set the {@code maps_database_tables_or_views} property (displayed as {@code Maps Database Tables or Views}) of the object.
     * @param mapsDatabaseTablesOrViews the value to set
     */
    @JsonProperty("maps_database_tables_or_views")
    public void setMapsDatabaseTablesOrViews(ItemList<Datagroup> mapsDatabaseTablesOrViews) { this.mapsDatabaseTablesOrViews = mapsDatabaseTablesOrViews; }

    /**
     * Retrieve the {@code mdm_model} property (displayed as '{@literal MDM Model}') of the object.
     * @return {@code MdmModel}
     */
    @JsonProperty("mdm_model")
    public MdmModel getMdmModel() { return this.mdmModel; }

    /**
     * Set the {@code mdm_model} property (displayed as {@code MDM Model}) of the object.
     * @param mdmModel the value to set
     */
    @JsonProperty("mdm_model")
    public void setMdmModel(MdmModel mdmModel) { this.mdmModel = mdmModel; }

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
     * Retrieve the {@code physical_object} property (displayed as '{@literal Physical Object}') of the object.
     * @return {@code ItemList<PhysicalObject>}
     */
    @JsonProperty("physical_object")
    public ItemList<PhysicalObject> getPhysicalObject() { return this.physicalObject; }

    /**
     * Set the {@code physical_object} property (displayed as {@code Physical Object}) of the object.
     * @param physicalObject the value to set
     */
    @JsonProperty("physical_object")
    public void setPhysicalObject(ItemList<PhysicalObject> physicalObject) { this.physicalObject = physicalObject; }

}
