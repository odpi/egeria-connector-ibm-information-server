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
 * POJO for the {@code xsd_attribute_group} asset type in IGC, displayed as '{@literal XSD Attribute Group}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdAttributeGroup.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_attribute_group")
public class XsdAttributeGroup extends InformationAsset {

    @JsonProperty("contains_xsd_attributes")
    protected ItemList<XsdAttribute> containsXsdAttributes;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("referenced_by_xsd_attribute_groups")
    protected ItemList<XsdAttributeGroup> referencedByXsdAttributeGroups;

    @JsonProperty("referenced_by_xsd_complex_types")
    protected ItemList<XsdComplexType> referencedByXsdComplexTypes;

    @JsonProperty("referenced_by_xsd_elements")
    protected ItemList<MainObject> referencedByXsdElements;

    @JsonProperty("references_xsd_attribute_groups")
    protected ItemList<XsdAttributeGroup> referencesXsdAttributeGroups;

    @JsonProperty("references_xsd_attributes")
    protected ItemList<XsdAttributeReference> referencesXsdAttributes;

    @JsonProperty("xsd_schema")
    protected XmlSchemaDefinition xsdSchema;

    /**
     * Retrieve the {@code contains_xsd_attributes} property (displayed as '{@literal Contains XSD Attributes}') of the object.
     * @return {@code ItemList<XsdAttribute>}
     */
    @JsonProperty("contains_xsd_attributes")
    public ItemList<XsdAttribute> getContainsXsdAttributes() { return this.containsXsdAttributes; }

    /**
     * Set the {@code contains_xsd_attributes} property (displayed as {@code Contains XSD Attributes}) of the object.
     * @param containsXsdAttributes the value to set
     */
    @JsonProperty("contains_xsd_attributes")
    public void setContainsXsdAttributes(ItemList<XsdAttribute> containsXsdAttributes) { this.containsXsdAttributes = containsXsdAttributes; }

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
     * Retrieve the {@code referenced_by_xsd_attribute_groups} property (displayed as '{@literal Referenced by XSD Attribute Groups}') of the object.
     * @return {@code ItemList<XsdAttributeGroup>}
     */
    @JsonProperty("referenced_by_xsd_attribute_groups")
    public ItemList<XsdAttributeGroup> getReferencedByXsdAttributeGroups() { return this.referencedByXsdAttributeGroups; }

    /**
     * Set the {@code referenced_by_xsd_attribute_groups} property (displayed as {@code Referenced by XSD Attribute Groups}) of the object.
     * @param referencedByXsdAttributeGroups the value to set
     */
    @JsonProperty("referenced_by_xsd_attribute_groups")
    public void setReferencedByXsdAttributeGroups(ItemList<XsdAttributeGroup> referencedByXsdAttributeGroups) { this.referencedByXsdAttributeGroups = referencedByXsdAttributeGroups; }

    /**
     * Retrieve the {@code referenced_by_xsd_complex_types} property (displayed as '{@literal Referenced by XSD Complex Types}') of the object.
     * @return {@code ItemList<XsdComplexType>}
     */
    @JsonProperty("referenced_by_xsd_complex_types")
    public ItemList<XsdComplexType> getReferencedByXsdComplexTypes() { return this.referencedByXsdComplexTypes; }

    /**
     * Set the {@code referenced_by_xsd_complex_types} property (displayed as {@code Referenced by XSD Complex Types}) of the object.
     * @param referencedByXsdComplexTypes the value to set
     */
    @JsonProperty("referenced_by_xsd_complex_types")
    public void setReferencedByXsdComplexTypes(ItemList<XsdComplexType> referencedByXsdComplexTypes) { this.referencedByXsdComplexTypes = referencedByXsdComplexTypes; }

    /**
     * Retrieve the {@code referenced_by_xsd_elements} property (displayed as '{@literal Referenced by XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("referenced_by_xsd_elements")
    public ItemList<MainObject> getReferencedByXsdElements() { return this.referencedByXsdElements; }

    /**
     * Set the {@code referenced_by_xsd_elements} property (displayed as {@code Referenced by XSD Elements}) of the object.
     * @param referencedByXsdElements the value to set
     */
    @JsonProperty("referenced_by_xsd_elements")
    public void setReferencedByXsdElements(ItemList<MainObject> referencedByXsdElements) { this.referencedByXsdElements = referencedByXsdElements; }

    /**
     * Retrieve the {@code references_xsd_attribute_groups} property (displayed as '{@literal References XSD Attribute Groups}') of the object.
     * @return {@code ItemList<XsdAttributeGroup>}
     */
    @JsonProperty("references_xsd_attribute_groups")
    public ItemList<XsdAttributeGroup> getReferencesXsdAttributeGroups() { return this.referencesXsdAttributeGroups; }

    /**
     * Set the {@code references_xsd_attribute_groups} property (displayed as {@code References XSD Attribute Groups}) of the object.
     * @param referencesXsdAttributeGroups the value to set
     */
    @JsonProperty("references_xsd_attribute_groups")
    public void setReferencesXsdAttributeGroups(ItemList<XsdAttributeGroup> referencesXsdAttributeGroups) { this.referencesXsdAttributeGroups = referencesXsdAttributeGroups; }

    /**
     * Retrieve the {@code references_xsd_attributes} property (displayed as '{@literal References XSD Attributes}') of the object.
     * @return {@code ItemList<XsdAttributeReference>}
     */
    @JsonProperty("references_xsd_attributes")
    public ItemList<XsdAttributeReference> getReferencesXsdAttributes() { return this.referencesXsdAttributes; }

    /**
     * Set the {@code references_xsd_attributes} property (displayed as {@code References XSD Attributes}) of the object.
     * @param referencesXsdAttributes the value to set
     */
    @JsonProperty("references_xsd_attributes")
    public void setReferencesXsdAttributes(ItemList<XsdAttributeReference> referencesXsdAttributes) { this.referencesXsdAttributes = referencesXsdAttributes; }

    /**
     * Retrieve the {@code xsd_schema} property (displayed as '{@literal XSD Schema}') of the object.
     * @return {@code XmlSchemaDefinition}
     */
    @JsonProperty("xsd_schema")
    public XmlSchemaDefinition getXsdSchema() { return this.xsdSchema; }

    /**
     * Set the {@code xsd_schema} property (displayed as {@code XSD Schema}) of the object.
     * @param xsdSchema the value to set
     */
    @JsonProperty("xsd_schema")
    public void setXsdSchema(XmlSchemaDefinition xsdSchema) { this.xsdSchema = xsdSchema; }

}
