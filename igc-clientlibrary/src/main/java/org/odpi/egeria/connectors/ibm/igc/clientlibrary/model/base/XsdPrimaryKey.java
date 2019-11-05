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
import java.util.Date;

/**
 * POJO for the {@code xsd_primary_key} asset type in IGC, displayed as '{@literal XSD Primary Key}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_primary_key")
public class XsdPrimaryKey extends Reference {

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("referenced_by_xsd_keys")
    protected ItemList<XsdForeignKey> referencedByXsdKeys;

    @JsonProperty("selector")
    protected String selector;

    @JsonProperty("xsd_element")
    protected MainObject xsdElement;

    @JsonProperty("xsd_elements_or_attributes")
    protected ItemList<MainObject> xsdElementsOrAttributes;

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

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
     * Retrieve the {@code referenced_by_xsd_keys} property (displayed as '{@literal Referenced by XSD Keys}') of the object.
     * @return {@code ItemList<XsdForeignKey>}
     */
    @JsonProperty("referenced_by_xsd_keys")
    public ItemList<XsdForeignKey> getReferencedByXsdKeys() { return this.referencedByXsdKeys; }

    /**
     * Set the {@code referenced_by_xsd_keys} property (displayed as {@code Referenced by XSD Keys}) of the object.
     * @param referencedByXsdKeys the value to set
     */
    @JsonProperty("referenced_by_xsd_keys")
    public void setReferencedByXsdKeys(ItemList<XsdForeignKey> referencedByXsdKeys) { this.referencedByXsdKeys = referencedByXsdKeys; }

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
