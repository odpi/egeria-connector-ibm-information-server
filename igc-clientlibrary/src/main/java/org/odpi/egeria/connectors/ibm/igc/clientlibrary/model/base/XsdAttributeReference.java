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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code xsd_attribute_reference} asset type in IGC, displayed as '{@literal XSD Attribute Reference}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdAttributeReference.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_attribute_reference")
public class XsdAttributeReference extends Reference {

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("references_xsd_attribute")
    protected XsdAttribute referencesXsdAttribute;

    @JsonProperty("usage")
    protected String usage;

    @JsonProperty("xsd_attribute_group")
    protected ItemList<XsdAttributeGroup> xsdAttributeGroup;

    @JsonProperty("xsd_complex_type")
    protected ItemList<XsdComplexType> xsdComplexType;

    @JsonProperty("xsd_element")
    protected ItemList<MainObject> xsdElement;

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
     * Retrieve the {@code references_xsd_attribute} property (displayed as '{@literal XSD Attribute}') of the object.
     * @return {@code XsdAttribute}
     */
    @JsonProperty("references_xsd_attribute")
    public XsdAttribute getReferencesXsdAttribute() { return this.referencesXsdAttribute; }

    /**
     * Set the {@code references_xsd_attribute} property (displayed as {@code XSD Attribute}) of the object.
     * @param referencesXsdAttribute the value to set
     */
    @JsonProperty("references_xsd_attribute")
    public void setReferencesXsdAttribute(XsdAttribute referencesXsdAttribute) { this.referencesXsdAttribute = referencesXsdAttribute; }

    /**
     * Retrieve the {@code usage} property (displayed as '{@literal Usage}') of the object.
     * @return {@code String}
     */
    @JsonProperty("usage")
    public String getUsage() { return this.usage; }

    /**
     * Set the {@code usage} property (displayed as {@code Usage}) of the object.
     * @param usage the value to set
     */
    @JsonProperty("usage")
    public void setUsage(String usage) { this.usage = usage; }

    /**
     * Retrieve the {@code xsd_attribute_group} property (displayed as '{@literal Attribute Group}') of the object.
     * @return {@code ItemList<XsdAttributeGroup>}
     */
    @JsonProperty("xsd_attribute_group")
    public ItemList<XsdAttributeGroup> getXsdAttributeGroup() { return this.xsdAttributeGroup; }

    /**
     * Set the {@code xsd_attribute_group} property (displayed as {@code Attribute Group}) of the object.
     * @param xsdAttributeGroup the value to set
     */
    @JsonProperty("xsd_attribute_group")
    public void setXsdAttributeGroup(ItemList<XsdAttributeGroup> xsdAttributeGroup) { this.xsdAttributeGroup = xsdAttributeGroup; }

    /**
     * Retrieve the {@code xsd_complex_type} property (displayed as '{@literal XSD Complex Type}') of the object.
     * @return {@code ItemList<XsdComplexType>}
     */
    @JsonProperty("xsd_complex_type")
    public ItemList<XsdComplexType> getXsdComplexType() { return this.xsdComplexType; }

    /**
     * Set the {@code xsd_complex_type} property (displayed as {@code XSD Complex Type}) of the object.
     * @param xsdComplexType the value to set
     */
    @JsonProperty("xsd_complex_type")
    public void setXsdComplexType(ItemList<XsdComplexType> xsdComplexType) { this.xsdComplexType = xsdComplexType; }

    /**
     * Retrieve the {@code xsd_element} property (displayed as '{@literal XSD Element}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("xsd_element")
    public ItemList<MainObject> getXsdElement() { return this.xsdElement; }

    /**
     * Set the {@code xsd_element} property (displayed as {@code XSD Element}) of the object.
     * @param xsdElement the value to set
     */
    @JsonProperty("xsd_element")
    public void setXsdElement(ItemList<MainObject> xsdElement) { this.xsdElement = xsdElement; }

}
