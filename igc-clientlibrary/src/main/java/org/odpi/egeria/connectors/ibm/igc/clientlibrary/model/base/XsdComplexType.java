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
 * POJO for the {@code xsd_complex_type} asset type in IGC, displayed as '{@literal XSD Complex Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdComplexType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_complex_type")
public class XsdComplexType extends InformationAsset {

    @JsonProperty("abstract")
    protected Boolean zabstract;

    @JsonProperty("base_type")
    protected String baseType;

    @JsonProperty("block")
    protected String block;

    @JsonProperty("contains_xsd_attributes")
    protected ItemList<XsdAttribute> containsXsdAttributes;

    @JsonProperty("contains_xsd_elements")
    protected ItemList<MainObject> containsXsdElements;

    @JsonProperty("default")
    protected Boolean zdefault;

    @JsonProperty("defines_xsd_elements")
    protected ItemList<MainObject> definesXsdElements;

    @JsonProperty("extended_by_xsd_complex_type")
    protected ItemList<XsdComplexType> extendedByXsdComplexType;

    @JsonProperty("extends_xsd_complex_type")
    protected XsdComplexType extendsXsdComplexType;

    @JsonProperty("extends_xsd_element")
    protected ItemList<MainObject> extendsXsdElement;

    @JsonProperty("extends_xsd_simple_type")
    protected XsdSimpleType extendsXsdSimpleType;

    @JsonProperty("mixed")
    protected Boolean mixed;

    @JsonProperty("references_xsd_attribute_groups")
    protected ItemList<XsdAttributeGroup> referencesXsdAttributeGroups;

    @JsonProperty("references_xsd_attributes")
    protected ItemList<XsdAttributeReference> referencesXsdAttributes;

    @JsonProperty("references_xsd_element_group")
    protected ItemList<XsdElementGroupReference> referencesXsdElementGroup;

    @JsonProperty("references_xsd_elements")
    protected ItemList<XsdElementReference> referencesXsdElements;

    @JsonProperty("restricted_by_xsd_complex_type")
    protected ItemList<XsdComplexType> restrictedByXsdComplexType;

    @JsonProperty("restricts_xsd_complex_type")
    protected XsdComplexType restrictsXsdComplexType;

    @JsonProperty("restricts_xsd_element")
    protected ItemList<MainObject> restrictsXsdElement;

    /**
     * Valid values are:
     * <ul>
     *   <li>All (displayed in the UI as 'All')</li>
     *   <li>Choice (displayed in the UI as 'Choice')</li>
     *   <li>Sequence (displayed in the UI as 'Sequence')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    @JsonProperty("xsd_schema")
    protected XmlSchemaDefinition xsdSchema;

    /**
     * Retrieve the {@code abstract} property (displayed as '{@literal Abstract}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("abstract")
    public Boolean getAbstract() { return this.zabstract; }

    /**
     * Set the {@code abstract} property (displayed as {@code Abstract}) of the object.
     * @param zabstract the value to set
     */
    @JsonProperty("abstract")
    public void setAbstract(Boolean zabstract) { this.zabstract = zabstract; }

    /**
     * Retrieve the {@code base_type} property (displayed as '{@literal Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("base_type")
    public String getBaseType() { return this.baseType; }

    /**
     * Set the {@code base_type} property (displayed as {@code Data Type}) of the object.
     * @param baseType the value to set
     */
    @JsonProperty("base_type")
    public void setBaseType(String baseType) { this.baseType = baseType; }

    /**
     * Retrieve the {@code block} property (displayed as '{@literal Block}') of the object.
     * @return {@code String}
     */
    @JsonProperty("block")
    public String getBlock() { return this.block; }

    /**
     * Set the {@code block} property (displayed as {@code Block}) of the object.
     * @param block the value to set
     */
    @JsonProperty("block")
    public void setBlock(String block) { this.block = block; }

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
     * Retrieve the {@code contains_xsd_elements} property (displayed as '{@literal Contains XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("contains_xsd_elements")
    public ItemList<MainObject> getContainsXsdElements() { return this.containsXsdElements; }

    /**
     * Set the {@code contains_xsd_elements} property (displayed as {@code Contains XSD Elements}) of the object.
     * @param containsXsdElements the value to set
     */
    @JsonProperty("contains_xsd_elements")
    public void setContainsXsdElements(ItemList<MainObject> containsXsdElements) { this.containsXsdElements = containsXsdElements; }

    /**
     * Retrieve the {@code default} property (displayed as '{@literal Default}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("default")
    public Boolean getDefault() { return this.zdefault; }

    /**
     * Set the {@code default} property (displayed as {@code Default}) of the object.
     * @param zdefault the value to set
     */
    @JsonProperty("default")
    public void setDefault(Boolean zdefault) { this.zdefault = zdefault; }

    /**
     * Retrieve the {@code defines_xsd_elements} property (displayed as '{@literal Defines XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("defines_xsd_elements")
    public ItemList<MainObject> getDefinesXsdElements() { return this.definesXsdElements; }

    /**
     * Set the {@code defines_xsd_elements} property (displayed as {@code Defines XSD Elements}) of the object.
     * @param definesXsdElements the value to set
     */
    @JsonProperty("defines_xsd_elements")
    public void setDefinesXsdElements(ItemList<MainObject> definesXsdElements) { this.definesXsdElements = definesXsdElements; }

    /**
     * Retrieve the {@code extended_by_xsd_complex_type} property (displayed as '{@literal Extended by XSD Complex Types}') of the object.
     * @return {@code ItemList<XsdComplexType>}
     */
    @JsonProperty("extended_by_xsd_complex_type")
    public ItemList<XsdComplexType> getExtendedByXsdComplexType() { return this.extendedByXsdComplexType; }

    /**
     * Set the {@code extended_by_xsd_complex_type} property (displayed as {@code Extended by XSD Complex Types}) of the object.
     * @param extendedByXsdComplexType the value to set
     */
    @JsonProperty("extended_by_xsd_complex_type")
    public void setExtendedByXsdComplexType(ItemList<XsdComplexType> extendedByXsdComplexType) { this.extendedByXsdComplexType = extendedByXsdComplexType; }

    /**
     * Retrieve the {@code extends_xsd_complex_type} property (displayed as '{@literal Extends XSD Complex Type}') of the object.
     * @return {@code XsdComplexType}
     */
    @JsonProperty("extends_xsd_complex_type")
    public XsdComplexType getExtendsXsdComplexType() { return this.extendsXsdComplexType; }

    /**
     * Set the {@code extends_xsd_complex_type} property (displayed as {@code Extends XSD Complex Type}) of the object.
     * @param extendsXsdComplexType the value to set
     */
    @JsonProperty("extends_xsd_complex_type")
    public void setExtendsXsdComplexType(XsdComplexType extendsXsdComplexType) { this.extendsXsdComplexType = extendsXsdComplexType; }

    /**
     * Retrieve the {@code extends_xsd_element} property (displayed as '{@literal Extends XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("extends_xsd_element")
    public ItemList<MainObject> getExtendsXsdElement() { return this.extendsXsdElement; }

    /**
     * Set the {@code extends_xsd_element} property (displayed as {@code Extends XSD Elements}) of the object.
     * @param extendsXsdElement the value to set
     */
    @JsonProperty("extends_xsd_element")
    public void setExtendsXsdElement(ItemList<MainObject> extendsXsdElement) { this.extendsXsdElement = extendsXsdElement; }

    /**
     * Retrieve the {@code extends_xsd_simple_type} property (displayed as '{@literal Extends XSD Simple Type}') of the object.
     * @return {@code XsdSimpleType}
     */
    @JsonProperty("extends_xsd_simple_type")
    public XsdSimpleType getExtendsXsdSimpleType() { return this.extendsXsdSimpleType; }

    /**
     * Set the {@code extends_xsd_simple_type} property (displayed as {@code Extends XSD Simple Type}) of the object.
     * @param extendsXsdSimpleType the value to set
     */
    @JsonProperty("extends_xsd_simple_type")
    public void setExtendsXsdSimpleType(XsdSimpleType extendsXsdSimpleType) { this.extendsXsdSimpleType = extendsXsdSimpleType; }

    /**
     * Retrieve the {@code mixed} property (displayed as '{@literal Mixed}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("mixed")
    public Boolean getMixed() { return this.mixed; }

    /**
     * Set the {@code mixed} property (displayed as {@code Mixed}) of the object.
     * @param mixed the value to set
     */
    @JsonProperty("mixed")
    public void setMixed(Boolean mixed) { this.mixed = mixed; }

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
     * Retrieve the {@code references_xsd_element_group} property (displayed as '{@literal References XSD Element Groups}') of the object.
     * @return {@code ItemList<XsdElementGroupReference>}
     */
    @JsonProperty("references_xsd_element_group")
    public ItemList<XsdElementGroupReference> getReferencesXsdElementGroup() { return this.referencesXsdElementGroup; }

    /**
     * Set the {@code references_xsd_element_group} property (displayed as {@code References XSD Element Groups}) of the object.
     * @param referencesXsdElementGroup the value to set
     */
    @JsonProperty("references_xsd_element_group")
    public void setReferencesXsdElementGroup(ItemList<XsdElementGroupReference> referencesXsdElementGroup) { this.referencesXsdElementGroup = referencesXsdElementGroup; }

    /**
     * Retrieve the {@code references_xsd_elements} property (displayed as '{@literal References XSD Elements}') of the object.
     * @return {@code ItemList<XsdElementReference>}
     */
    @JsonProperty("references_xsd_elements")
    public ItemList<XsdElementReference> getReferencesXsdElements() { return this.referencesXsdElements; }

    /**
     * Set the {@code references_xsd_elements} property (displayed as {@code References XSD Elements}) of the object.
     * @param referencesXsdElements the value to set
     */
    @JsonProperty("references_xsd_elements")
    public void setReferencesXsdElements(ItemList<XsdElementReference> referencesXsdElements) { this.referencesXsdElements = referencesXsdElements; }

    /**
     * Retrieve the {@code restricted_by_xsd_complex_type} property (displayed as '{@literal Restricted by XSD Complex Types}') of the object.
     * @return {@code ItemList<XsdComplexType>}
     */
    @JsonProperty("restricted_by_xsd_complex_type")
    public ItemList<XsdComplexType> getRestrictedByXsdComplexType() { return this.restrictedByXsdComplexType; }

    /**
     * Set the {@code restricted_by_xsd_complex_type} property (displayed as {@code Restricted by XSD Complex Types}) of the object.
     * @param restrictedByXsdComplexType the value to set
     */
    @JsonProperty("restricted_by_xsd_complex_type")
    public void setRestrictedByXsdComplexType(ItemList<XsdComplexType> restrictedByXsdComplexType) { this.restrictedByXsdComplexType = restrictedByXsdComplexType; }

    /**
     * Retrieve the {@code restricts_xsd_complex_type} property (displayed as '{@literal Restricts XSD Complex Type}') of the object.
     * @return {@code XsdComplexType}
     */
    @JsonProperty("restricts_xsd_complex_type")
    public XsdComplexType getRestrictsXsdComplexType() { return this.restrictsXsdComplexType; }

    /**
     * Set the {@code restricts_xsd_complex_type} property (displayed as {@code Restricts XSD Complex Type}) of the object.
     * @param restrictsXsdComplexType the value to set
     */
    @JsonProperty("restricts_xsd_complex_type")
    public void setRestrictsXsdComplexType(XsdComplexType restrictsXsdComplexType) { this.restrictsXsdComplexType = restrictsXsdComplexType; }

    /**
     * Retrieve the {@code restricts_xsd_element} property (displayed as '{@literal Restricts XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("restricts_xsd_element")
    public ItemList<MainObject> getRestrictsXsdElement() { return this.restrictsXsdElement; }

    /**
     * Set the {@code restricts_xsd_element} property (displayed as {@code Restricts XSD Elements}) of the object.
     * @param restrictsXsdElement the value to set
     */
    @JsonProperty("restricts_xsd_element")
    public void setRestrictsXsdElement(ItemList<MainObject> restrictsXsdElement) { this.restrictsXsdElement = restrictsXsdElement; }

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
