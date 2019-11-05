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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code xsd_element_group_reference} asset type in IGC, displayed as '{@literal XSD Element Group Reference}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_element_group_reference")
public class XsdElementGroupReference extends Reference {

    @JsonProperty("max_occurs")
    protected Number maxOccurs;

    @JsonProperty("min_occurs")
    protected Number minOccurs;

    @JsonProperty("references_xsd_element_group")
    protected XsdElementGroup referencesXsdElementGroup;

    @JsonProperty("xsd_complex_type")
    protected ItemList<XsdComplexType> xsdComplexType;

    @JsonProperty("xsd_element")
    protected ItemList<MainObject> xsdElement;

    @JsonProperty("xsd_element_group")
    protected ItemList<XsdElementGroup> xsdElementGroup;

    /**
     * Retrieve the {@code max_occurs} property (displayed as '{@literal Maximum Occurrence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("max_occurs")
    public Number getMaxOccurs() { return this.maxOccurs; }

    /**
     * Set the {@code max_occurs} property (displayed as {@code Maximum Occurrence}) of the object.
     * @param maxOccurs the value to set
     */
    @JsonProperty("max_occurs")
    public void setMaxOccurs(Number maxOccurs) { this.maxOccurs = maxOccurs; }

    /**
     * Retrieve the {@code min_occurs} property (displayed as '{@literal Minimum Occurrence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("min_occurs")
    public Number getMinOccurs() { return this.minOccurs; }

    /**
     * Set the {@code min_occurs} property (displayed as {@code Minimum Occurrence}) of the object.
     * @param minOccurs the value to set
     */
    @JsonProperty("min_occurs")
    public void setMinOccurs(Number minOccurs) { this.minOccurs = minOccurs; }

    /**
     * Retrieve the {@code references_xsd_element_group} property (displayed as '{@literal XSD Element Group}') of the object.
     * @return {@code XsdElementGroup}
     */
    @JsonProperty("references_xsd_element_group")
    public XsdElementGroup getReferencesXsdElementGroup() { return this.referencesXsdElementGroup; }

    /**
     * Set the {@code references_xsd_element_group} property (displayed as {@code XSD Element Group}) of the object.
     * @param referencesXsdElementGroup the value to set
     */
    @JsonProperty("references_xsd_element_group")
    public void setReferencesXsdElementGroup(XsdElementGroup referencesXsdElementGroup) { this.referencesXsdElementGroup = referencesXsdElementGroup; }

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

    /**
     * Retrieve the {@code xsd_element_group} property (displayed as '{@literal XSD Element Group}') of the object.
     * @return {@code ItemList<XsdElementGroup>}
     */
    @JsonProperty("xsd_element_group")
    public ItemList<XsdElementGroup> getXsdElementGroup() { return this.xsdElementGroup; }

    /**
     * Set the {@code xsd_element_group} property (displayed as {@code XSD Element Group}) of the object.
     * @param xsdElementGroup the value to set
     */
    @JsonProperty("xsd_element_group")
    public void setXsdElementGroup(ItemList<XsdElementGroup> xsdElementGroup) { this.xsdElementGroup = xsdElementGroup; }

}
