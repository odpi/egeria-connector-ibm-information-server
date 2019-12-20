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
 * POJO for the {@code mdm_model} asset type in IGC, displayed as '{@literal MDM Model}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MdmModel.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("mdm_model")
public class MdmModel extends InformationAsset {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("contains_attributes_types")
    protected ItemList<AttributeType> containsAttributesTypes;

    @JsonProperty("contains_member_types")
    protected ItemList<MemberType> containsMemberTypes;

    @JsonProperty("contains_physical_objects")
    protected ItemList<PhysicalObject> containsPhysicalObjects;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("version")
    protected String version;

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
     * Retrieve the {@code contains_attributes_types} property (displayed as '{@literal Contains Attributes Types}') of the object.
     * @return {@code ItemList<AttributeType>}
     */
    @JsonProperty("contains_attributes_types")
    public ItemList<AttributeType> getContainsAttributesTypes() { return this.containsAttributesTypes; }

    /**
     * Set the {@code contains_attributes_types} property (displayed as {@code Contains Attributes Types}) of the object.
     * @param containsAttributesTypes the value to set
     */
    @JsonProperty("contains_attributes_types")
    public void setContainsAttributesTypes(ItemList<AttributeType> containsAttributesTypes) { this.containsAttributesTypes = containsAttributesTypes; }

    /**
     * Retrieve the {@code contains_member_types} property (displayed as '{@literal Contains Member Types}') of the object.
     * @return {@code ItemList<MemberType>}
     */
    @JsonProperty("contains_member_types")
    public ItemList<MemberType> getContainsMemberTypes() { return this.containsMemberTypes; }

    /**
     * Set the {@code contains_member_types} property (displayed as {@code Contains Member Types}) of the object.
     * @param containsMemberTypes the value to set
     */
    @JsonProperty("contains_member_types")
    public void setContainsMemberTypes(ItemList<MemberType> containsMemberTypes) { this.containsMemberTypes = containsMemberTypes; }

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
     * Retrieve the {@code namespace} property (displayed as '{@literal Namespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("namespace")
    public String getNamespace() { return this.namespace; }

    /**
     * Set the {@code namespace} property (displayed as {@code Namespace}) of the object.
     * @param namespace the value to set
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) { this.namespace = namespace; }

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
