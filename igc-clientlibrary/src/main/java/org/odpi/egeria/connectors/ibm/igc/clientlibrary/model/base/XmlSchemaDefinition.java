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
 * POJO for the {@code xml_schema_definition} asset type in IGC, displayed as '{@literal XML Schema Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XmlSchemaDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xml_schema_definition")
public class XmlSchemaDefinition extends InformationAsset {

    @JsonProperty("contains_xsd_attribute_groups")
    protected ItemList<XsdAttributeGroup> containsXsdAttributeGroups;

    @JsonProperty("contains_xsd_attributes")
    protected ItemList<XsdAttribute> containsXsdAttributes;

    @JsonProperty("contains_xsd_complex_types")
    protected ItemList<XsdComplexType> containsXsdComplexTypes;

    @JsonProperty("contains_xsd_element_groups")
    protected ItemList<XsdElementGroup> containsXsdElementGroups;

    @JsonProperty("contains_xsd_elements")
    protected ItemList<MainObject> containsXsdElements;

    @JsonProperty("contains_xsd_simple_types")
    protected ItemList<XsdSimpleType> containsXsdSimpleTypes;

    @JsonProperty("default_namespace")
    protected String defaultNamespace;

    @JsonProperty("id")
    protected String id;

    @JsonProperty("imports_xsd_schemas")
    protected ItemList<MainObject> importsXsdSchemas;

    @JsonProperty("includes_xsd_schemas")
    protected ItemList<MainObject> includesXsdSchemas;

    @JsonProperty("language")
    protected String language;

    @JsonProperty("overrides_xsd_schemas")
    protected ItemList<MainObject> overridesXsdSchemas;

    @JsonProperty("redefines_xsd_schemas")
    protected ItemList<MainObject> redefinesXsdSchemas;

    @JsonProperty("target_namespace")
    protected String targetNamespace;

    @JsonProperty("version")
    protected String version;

    @JsonProperty("xml_schema_library")
    protected ItemList<XmlSchemaLibrary> xmlSchemaLibrary;

    @JsonProperty("xpath")
    protected String xpath;

    /**
     * Retrieve the {@code contains_xsd_attribute_groups} property (displayed as '{@literal XSD Attribute Groups}') of the object.
     * @return {@code ItemList<XsdAttributeGroup>}
     */
    @JsonProperty("contains_xsd_attribute_groups")
    public ItemList<XsdAttributeGroup> getContainsXsdAttributeGroups() { return this.containsXsdAttributeGroups; }

    /**
     * Set the {@code contains_xsd_attribute_groups} property (displayed as {@code XSD Attribute Groups}) of the object.
     * @param containsXsdAttributeGroups the value to set
     */
    @JsonProperty("contains_xsd_attribute_groups")
    public void setContainsXsdAttributeGroups(ItemList<XsdAttributeGroup> containsXsdAttributeGroups) { this.containsXsdAttributeGroups = containsXsdAttributeGroups; }

    /**
     * Retrieve the {@code contains_xsd_attributes} property (displayed as '{@literal XSD Attributes}') of the object.
     * @return {@code ItemList<XsdAttribute>}
     */
    @JsonProperty("contains_xsd_attributes")
    public ItemList<XsdAttribute> getContainsXsdAttributes() { return this.containsXsdAttributes; }

    /**
     * Set the {@code contains_xsd_attributes} property (displayed as {@code XSD Attributes}) of the object.
     * @param containsXsdAttributes the value to set
     */
    @JsonProperty("contains_xsd_attributes")
    public void setContainsXsdAttributes(ItemList<XsdAttribute> containsXsdAttributes) { this.containsXsdAttributes = containsXsdAttributes; }

    /**
     * Retrieve the {@code contains_xsd_complex_types} property (displayed as '{@literal XSD Complex Types}') of the object.
     * @return {@code ItemList<XsdComplexType>}
     */
    @JsonProperty("contains_xsd_complex_types")
    public ItemList<XsdComplexType> getContainsXsdComplexTypes() { return this.containsXsdComplexTypes; }

    /**
     * Set the {@code contains_xsd_complex_types} property (displayed as {@code XSD Complex Types}) of the object.
     * @param containsXsdComplexTypes the value to set
     */
    @JsonProperty("contains_xsd_complex_types")
    public void setContainsXsdComplexTypes(ItemList<XsdComplexType> containsXsdComplexTypes) { this.containsXsdComplexTypes = containsXsdComplexTypes; }

    /**
     * Retrieve the {@code contains_xsd_element_groups} property (displayed as '{@literal XSD Element Groups}') of the object.
     * @return {@code ItemList<XsdElementGroup>}
     */
    @JsonProperty("contains_xsd_element_groups")
    public ItemList<XsdElementGroup> getContainsXsdElementGroups() { return this.containsXsdElementGroups; }

    /**
     * Set the {@code contains_xsd_element_groups} property (displayed as {@code XSD Element Groups}) of the object.
     * @param containsXsdElementGroups the value to set
     */
    @JsonProperty("contains_xsd_element_groups")
    public void setContainsXsdElementGroups(ItemList<XsdElementGroup> containsXsdElementGroups) { this.containsXsdElementGroups = containsXsdElementGroups; }

    /**
     * Retrieve the {@code contains_xsd_elements} property (displayed as '{@literal XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("contains_xsd_elements")
    public ItemList<MainObject> getContainsXsdElements() { return this.containsXsdElements; }

    /**
     * Set the {@code contains_xsd_elements} property (displayed as {@code XSD Elements}) of the object.
     * @param containsXsdElements the value to set
     */
    @JsonProperty("contains_xsd_elements")
    public void setContainsXsdElements(ItemList<MainObject> containsXsdElements) { this.containsXsdElements = containsXsdElements; }

    /**
     * Retrieve the {@code contains_xsd_simple_types} property (displayed as '{@literal XSD Simple Types}') of the object.
     * @return {@code ItemList<XsdSimpleType>}
     */
    @JsonProperty("contains_xsd_simple_types")
    public ItemList<XsdSimpleType> getContainsXsdSimpleTypes() { return this.containsXsdSimpleTypes; }

    /**
     * Set the {@code contains_xsd_simple_types} property (displayed as {@code XSD Simple Types}) of the object.
     * @param containsXsdSimpleTypes the value to set
     */
    @JsonProperty("contains_xsd_simple_types")
    public void setContainsXsdSimpleTypes(ItemList<XsdSimpleType> containsXsdSimpleTypes) { this.containsXsdSimpleTypes = containsXsdSimpleTypes; }

    /**
     * Retrieve the {@code default_namespace} property (displayed as '{@literal Default Namespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("default_namespace")
    public String getDefaultNamespace() { return this.defaultNamespace; }

    /**
     * Set the {@code default_namespace} property (displayed as {@code Default Namespace}) of the object.
     * @param defaultNamespace the value to set
     */
    @JsonProperty("default_namespace")
    public void setDefaultNamespace(String defaultNamespace) { this.defaultNamespace = defaultNamespace; }

    /**
     * Retrieve the {@code id} property (displayed as '{@literal Id}') of the object.
     * @return {@code String}
     */
    @JsonProperty("id")
    public String getTheId() { return this.id; }

    /**
     * Set the {@code id} property (displayed as {@code Id}) of the object.
     * @param id the value to set
     */
    @JsonProperty("id")
    public void setTheId(String id) { this.id = id; }

    /**
     * Retrieve the {@code imports_xsd_schemas} property (displayed as '{@literal Imports XML Schema Definitions}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("imports_xsd_schemas")
    public ItemList<MainObject> getImportsXsdSchemas() { return this.importsXsdSchemas; }

    /**
     * Set the {@code imports_xsd_schemas} property (displayed as {@code Imports XML Schema Definitions}) of the object.
     * @param importsXsdSchemas the value to set
     */
    @JsonProperty("imports_xsd_schemas")
    public void setImportsXsdSchemas(ItemList<MainObject> importsXsdSchemas) { this.importsXsdSchemas = importsXsdSchemas; }

    /**
     * Retrieve the {@code includes_xsd_schemas} property (displayed as '{@literal Includes XML Schema Definitions}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("includes_xsd_schemas")
    public ItemList<MainObject> getIncludesXsdSchemas() { return this.includesXsdSchemas; }

    /**
     * Set the {@code includes_xsd_schemas} property (displayed as {@code Includes XML Schema Definitions}) of the object.
     * @param includesXsdSchemas the value to set
     */
    @JsonProperty("includes_xsd_schemas")
    public void setIncludesXsdSchemas(ItemList<MainObject> includesXsdSchemas) { this.includesXsdSchemas = includesXsdSchemas; }

    /**
     * Retrieve the {@code language} property (displayed as '{@literal Language}') of the object.
     * @return {@code String}
     */
    @JsonProperty("language")
    public String getLanguage() { return this.language; }

    /**
     * Set the {@code language} property (displayed as {@code Language}) of the object.
     * @param language the value to set
     */
    @JsonProperty("language")
    public void setLanguage(String language) { this.language = language; }

    /**
     * Retrieve the {@code overrides_xsd_schemas} property (displayed as '{@literal Overrides XML Schema Definitions}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("overrides_xsd_schemas")
    public ItemList<MainObject> getOverridesXsdSchemas() { return this.overridesXsdSchemas; }

    /**
     * Set the {@code overrides_xsd_schemas} property (displayed as {@code Overrides XML Schema Definitions}) of the object.
     * @param overridesXsdSchemas the value to set
     */
    @JsonProperty("overrides_xsd_schemas")
    public void setOverridesXsdSchemas(ItemList<MainObject> overridesXsdSchemas) { this.overridesXsdSchemas = overridesXsdSchemas; }

    /**
     * Retrieve the {@code redefines_xsd_schemas} property (displayed as '{@literal Redefines XML Schema Definitions}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("redefines_xsd_schemas")
    public ItemList<MainObject> getRedefinesXsdSchemas() { return this.redefinesXsdSchemas; }

    /**
     * Set the {@code redefines_xsd_schemas} property (displayed as {@code Redefines XML Schema Definitions}) of the object.
     * @param redefinesXsdSchemas the value to set
     */
    @JsonProperty("redefines_xsd_schemas")
    public void setRedefinesXsdSchemas(ItemList<MainObject> redefinesXsdSchemas) { this.redefinesXsdSchemas = redefinesXsdSchemas; }

    /**
     * Retrieve the {@code target_namespace} property (displayed as '{@literal Namespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("target_namespace")
    public String getTargetNamespace() { return this.targetNamespace; }

    /**
     * Set the {@code target_namespace} property (displayed as {@code Namespace}) of the object.
     * @param targetNamespace the value to set
     */
    @JsonProperty("target_namespace")
    public void setTargetNamespace(String targetNamespace) { this.targetNamespace = targetNamespace; }

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

    /**
     * Retrieve the {@code xml_schema_library} property (displayed as '{@literal XML Schema Library}') of the object.
     * @return {@code ItemList<XmlSchemaLibrary>}
     */
    @JsonProperty("xml_schema_library")
    public ItemList<XmlSchemaLibrary> getXmlSchemaLibrary() { return this.xmlSchemaLibrary; }

    /**
     * Set the {@code xml_schema_library} property (displayed as {@code XML Schema Library}) of the object.
     * @param xmlSchemaLibrary the value to set
     */
    @JsonProperty("xml_schema_library")
    public void setXmlSchemaLibrary(ItemList<XmlSchemaLibrary> xmlSchemaLibrary) { this.xmlSchemaLibrary = xmlSchemaLibrary; }

    /**
     * Retrieve the {@code xpath} property (displayed as '{@literal Path}') of the object.
     * @return {@code String}
     */
    @JsonProperty("xpath")
    public String getXpath() { return this.xpath; }

    /**
     * Set the {@code xpath} property (displayed as {@code Path}) of the object.
     * @param xpath the value to set
     */
    @JsonProperty("xpath")
    public void setXpath(String xpath) { this.xpath = xpath; }

}
