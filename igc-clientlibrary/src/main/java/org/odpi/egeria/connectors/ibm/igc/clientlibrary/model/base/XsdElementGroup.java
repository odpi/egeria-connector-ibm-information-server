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
 * POJO for the {@code xsd_element_group} asset type in IGC, displayed as '{@literal XSD Element Group}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdElementGroup.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_element_group")
public class XsdElementGroup extends InformationAsset {

    @JsonProperty("contains_xsd_elements")
    protected ItemList<MainObject> containsXsdElements;

    @JsonProperty("referenced_by_xsd_element_groups")
    protected ItemList<XsdElementGroup> referencedByXsdElementGroups;

    @JsonProperty("references_by_xsd_elements")
    protected ItemList<MainObject> referencesByXsdElements;

    @JsonProperty("references_xsd_element_groups")
    protected ItemList<XsdElementGroupReference> referencesXsdElementGroups;

    @JsonProperty("references_xsd_elements")
    protected ItemList<XsdElementReference> referencesXsdElements;

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
     * Retrieve the {@code referenced_by_xsd_element_groups} property (displayed as '{@literal Referenced by XSD Element Groups}') of the object.
     * @return {@code ItemList<XsdElementGroup>}
     */
    @JsonProperty("referenced_by_xsd_element_groups")
    public ItemList<XsdElementGroup> getReferencedByXsdElementGroups() { return this.referencedByXsdElementGroups; }

    /**
     * Set the {@code referenced_by_xsd_element_groups} property (displayed as {@code Referenced by XSD Element Groups}) of the object.
     * @param referencedByXsdElementGroups the value to set
     */
    @JsonProperty("referenced_by_xsd_element_groups")
    public void setReferencedByXsdElementGroups(ItemList<XsdElementGroup> referencedByXsdElementGroups) { this.referencedByXsdElementGroups = referencedByXsdElementGroups; }

    /**
     * Retrieve the {@code references_by_xsd_elements} property (displayed as '{@literal Referenced by XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("references_by_xsd_elements")
    public ItemList<MainObject> getReferencesByXsdElements() { return this.referencesByXsdElements; }

    /**
     * Set the {@code references_by_xsd_elements} property (displayed as {@code Referenced by XSD Elements}) of the object.
     * @param referencesByXsdElements the value to set
     */
    @JsonProperty("references_by_xsd_elements")
    public void setReferencesByXsdElements(ItemList<MainObject> referencesByXsdElements) { this.referencesByXsdElements = referencesByXsdElements; }

    /**
     * Retrieve the {@code references_xsd_element_groups} property (displayed as '{@literal References XSD Element Groups}') of the object.
     * @return {@code ItemList<XsdElementGroupReference>}
     */
    @JsonProperty("references_xsd_element_groups")
    public ItemList<XsdElementGroupReference> getReferencesXsdElementGroups() { return this.referencesXsdElementGroups; }

    /**
     * Set the {@code references_xsd_element_groups} property (displayed as {@code References XSD Element Groups}) of the object.
     * @param referencesXsdElementGroups the value to set
     */
    @JsonProperty("references_xsd_element_groups")
    public void setReferencesXsdElementGroups(ItemList<XsdElementGroupReference> referencesXsdElementGroups) { this.referencesXsdElementGroups = referencesXsdElementGroups; }

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
