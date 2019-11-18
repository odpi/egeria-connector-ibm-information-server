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
 * POJO for the {@code xsd_foreign_key} asset type in IGC, displayed as '{@literal XSD Foreign Key}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdForeignKey.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_foreign_key")
public class XsdForeignKey extends Reference {

    @JsonProperty("name")
    protected String name;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("referenced_xsd_keys")
    protected XsdUniqueKey referencedXsdKeys;

    @JsonProperty("selector")
    protected String selector;

    @JsonProperty("xsd_element")
    protected MainObject xsdElement;

    @JsonProperty("xsd_elements_or_attributes")
    protected ItemList<MainObject> xsdElementsOrAttributes;

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

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
     * Retrieve the {@code referenced_xsd_keys} property (displayed as '{@literal References XSD Keys}') of the object.
     * @return {@code XsdUniqueKey}
     */
    @JsonProperty("referenced_xsd_keys")
    public XsdUniqueKey getReferencedXsdKeys() { return this.referencedXsdKeys; }

    /**
     * Set the {@code referenced_xsd_keys} property (displayed as {@code References XSD Keys}) of the object.
     * @param referencedXsdKeys the value to set
     */
    @JsonProperty("referenced_xsd_keys")
    public void setReferencedXsdKeys(XsdUniqueKey referencedXsdKeys) { this.referencedXsdKeys = referencedXsdKeys; }

    /**
     * Retrieve the {@code selector} property (displayed as '{@literal Selector}') of the object.
     * @return {@code String}
     */
    @JsonProperty("selector")
    public String getSelector() { return this.selector; }

    /**
     * Set the {@code selector} property (displayed as {@code Selector}) of the object.
     * @param selector the value to set
     */
    @JsonProperty("selector")
    public void setSelector(String selector) { this.selector = selector; }

    /**
     * Retrieve the {@code xsd_element} property (displayed as '{@literal XSD Element}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("xsd_element")
    public MainObject getXsdElement() { return this.xsdElement; }

    /**
     * Set the {@code xsd_element} property (displayed as {@code XSD Element}) of the object.
     * @param xsdElement the value to set
     */
    @JsonProperty("xsd_element")
    public void setXsdElement(MainObject xsdElement) { this.xsdElement = xsdElement; }

    /**
     * Retrieve the {@code xsd_elements_or_attributes} property (displayed as '{@literal XSD Elements or Attributes}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("xsd_elements_or_attributes")
    public ItemList<MainObject> getXsdElementsOrAttributes() { return this.xsdElementsOrAttributes; }

    /**
     * Set the {@code xsd_elements_or_attributes} property (displayed as {@code XSD Elements or Attributes}) of the object.
     * @param xsdElementsOrAttributes the value to set
     */
    @JsonProperty("xsd_elements_or_attributes")
    public void setXsdElementsOrAttributes(ItemList<MainObject> xsdElementsOrAttributes) { this.xsdElementsOrAttributes = xsdElementsOrAttributes; }

}
