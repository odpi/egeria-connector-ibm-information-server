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
import java.util.List;

/**
 * POJO for the {@code xsd_simple_type} asset type in IGC, displayed as '{@literal XSD Simple Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdSimpleType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_simple_type")
public class XsdSimpleType extends InformationAsset {

    @JsonProperty("base_type")
    protected String baseType;

    @JsonProperty("defines_xsd_attributes")
    protected ItemList<XsdAttribute> definesXsdAttributes;

    @JsonProperty("defines_xsd_elements")
    protected ItemList<MainObject> definesXsdElements;

    @JsonProperty("extended_by_xsd_complex_type")
    protected ItemList<XsdComplexType> extendedByXsdComplexType;

    @JsonProperty("extended_by_xsd_simple_type")
    protected ItemList<XsdSimpleType> extendedByXsdSimpleType;

    @JsonProperty("extends_xsd_element")
    protected ItemList<MainObject> extendsXsdElement;

    @JsonProperty("extends_xsd_simple_type")
    protected XsdSimpleType extendsXsdSimpleType;

    @JsonProperty("final")
    protected String zfinal;

    @JsonProperty("fraction")
    protected Number fraction;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("max_length")
    protected Number maxLength;

    @JsonProperty("min_length")
    protected Number minLength;

    @JsonProperty("restricted_by_xsd_simple_type")
    protected ItemList<XsdSimpleType> restrictedByXsdSimpleType;

    @JsonProperty("restriction_enumeration_values")
    protected List<String> restrictionEnumerationValues;

    @JsonProperty("restriction_maximum_inclusive")
    protected Boolean restrictionMaximumInclusive;

    /**
     * No longer applicable from 11.5.0.1ru5 onwards.
     * @see #restrictionMaximumInclusive
     */
    @Deprecated
    @JsonProperty("restriction_maximum_inclusive_string")
    protected List<String> restrictionMaximumInclusiveString;

    @JsonProperty("restriction_maximum_value")
    protected String restrictionMaximumValue;

    @JsonProperty("restriction_minimum_inclusive")
    protected Boolean restrictionMinimumInclusive;

    /**
     * No longer applicable from 11.5.0.1ru5 onwards.
     * @see #restrictionMinimumInclusive
     */
    @Deprecated
    @JsonProperty("restriction_minimum_inclusive_string")
    protected List<String> restrictionMinimumInclusiveString;

    @JsonProperty("restriction_minimum_value")
    protected String restrictionMinimumValue;

    @JsonProperty("restriction_pattern")
    protected String restrictionPattern;

    @JsonProperty("restricts_xsd_element")
    protected ItemList<MainObject> restrictsXsdElement;

    @JsonProperty("restricts_xsd_simple_type")
    protected XsdSimpleType restrictsXsdSimpleType;

    @JsonProperty("timezone")
    protected String timezone;

    @JsonProperty("total_digits")
    protected Number totalDigits;

    @JsonProperty("whitespace")
    protected String whitespace;

    @JsonProperty("xsd_schema")
    protected XmlSchemaDefinition xsdSchema;

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
     * Retrieve the {@code defines_xsd_attributes} property (displayed as '{@literal Defines XSD Attributes}') of the object.
     * @return {@code ItemList<XsdAttribute>}
     */
    @JsonProperty("defines_xsd_attributes")
    public ItemList<XsdAttribute> getDefinesXsdAttributes() { return this.definesXsdAttributes; }

    /**
     * Set the {@code defines_xsd_attributes} property (displayed as {@code Defines XSD Attributes}) of the object.
     * @param definesXsdAttributes the value to set
     */
    @JsonProperty("defines_xsd_attributes")
    public void setDefinesXsdAttributes(ItemList<XsdAttribute> definesXsdAttributes) { this.definesXsdAttributes = definesXsdAttributes; }

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
     * Retrieve the {@code extended_by_xsd_simple_type} property (displayed as '{@literal Extended by XSD Simple Types}') of the object.
     * @return {@code ItemList<XsdSimpleType>}
     */
    @JsonProperty("extended_by_xsd_simple_type")
    public ItemList<XsdSimpleType> getExtendedByXsdSimpleType() { return this.extendedByXsdSimpleType; }

    /**
     * Set the {@code extended_by_xsd_simple_type} property (displayed as {@code Extended by XSD Simple Types}) of the object.
     * @param extendedByXsdSimpleType the value to set
     */
    @JsonProperty("extended_by_xsd_simple_type")
    public void setExtendedByXsdSimpleType(ItemList<XsdSimpleType> extendedByXsdSimpleType) { this.extendedByXsdSimpleType = extendedByXsdSimpleType; }

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
     * Retrieve the {@code final} property (displayed as '{@literal Final}') of the object.
     * @return {@code String}
     */
    @JsonProperty("final")
    public String getFinal() { return this.zfinal; }

    /**
     * Set the {@code final} property (displayed as {@code Final}) of the object.
     * @param zfinal the value to set
     */
    @JsonProperty("final")
    public void setFinal(String zfinal) { this.zfinal = zfinal; }

    /**
     * Retrieve the {@code fraction} property (displayed as '{@literal Fraction}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("fraction")
    public Number getFraction() { return this.fraction; }

    /**
     * Set the {@code fraction} property (displayed as {@code Fraction}) of the object.
     * @param fraction the value to set
     */
    @JsonProperty("fraction")
    public void setFraction(Number fraction) { this.fraction = fraction; }

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
     * Retrieve the {@code length} property (displayed as '{@literal Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("length")
    public Number getLength() { return this.length; }

    /**
     * Set the {@code length} property (displayed as {@code Length}) of the object.
     * @param length the value to set
     */
    @JsonProperty("length")
    public void setLength(Number length) { this.length = length; }

    /**
     * Retrieve the {@code max_length} property (displayed as '{@literal Maximum Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("max_length")
    public Number getMaxLength() { return this.maxLength; }

    /**
     * Set the {@code max_length} property (displayed as {@code Maximum Length}) of the object.
     * @param maxLength the value to set
     */
    @JsonProperty("max_length")
    public void setMaxLength(Number maxLength) { this.maxLength = maxLength; }

    /**
     * Retrieve the {@code min_length} property (displayed as '{@literal Minimum Length}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("min_length")
    public Number getMinLength() { return this.minLength; }

    /**
     * Set the {@code min_length} property (displayed as {@code Minimum Length}) of the object.
     * @param minLength the value to set
     */
    @JsonProperty("min_length")
    public void setMinLength(Number minLength) { this.minLength = minLength; }

    /**
     * Retrieve the {@code restricted_by_xsd_simple_type} property (displayed as '{@literal Restricted by XSD Simple Types}') of the object.
     * @return {@code ItemList<XsdSimpleType>}
     */
    @JsonProperty("restricted_by_xsd_simple_type")
    public ItemList<XsdSimpleType> getRestrictedByXsdSimpleType() { return this.restrictedByXsdSimpleType; }

    /**
     * Set the {@code restricted_by_xsd_simple_type} property (displayed as {@code Restricted by XSD Simple Types}) of the object.
     * @param restrictedByXsdSimpleType the value to set
     */
    @JsonProperty("restricted_by_xsd_simple_type")
    public void setRestrictedByXsdSimpleType(ItemList<XsdSimpleType> restrictedByXsdSimpleType) { this.restrictedByXsdSimpleType = restrictedByXsdSimpleType; }

    /**
     * Retrieve the {@code restriction_enumeration_values} property (displayed as '{@literal Enumeration Values}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("restriction_enumeration_values")
    public List<String> getRestrictionEnumerationValues() { return this.restrictionEnumerationValues; }

    /**
     * Set the {@code restriction_enumeration_values} property (displayed as {@code Enumeration Values}) of the object.
     * @param restrictionEnumerationValues the value to set
     */
    @JsonProperty("restriction_enumeration_values")
    public void setRestrictionEnumerationValues(List<String> restrictionEnumerationValues) { this.restrictionEnumerationValues = restrictionEnumerationValues; }

    /**
     * Retrieve the {@code restriction_maximum_inclusive} property (displayed as '{@literal Maximum Range Inclusive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("restriction_maximum_inclusive")
    public Boolean getRestrictionMaximumInclusive() { return this.restrictionMaximumInclusive; }

    /**
     * Set the {@code restriction_maximum_inclusive} property (displayed as {@code Maximum Range Inclusive}) of the object.
     * @param restrictionMaximumInclusive the value to set
     */
    @JsonProperty("restriction_maximum_inclusive")
    public void setRestrictionMaximumInclusive(Boolean restrictionMaximumInclusive) { this.restrictionMaximumInclusive = restrictionMaximumInclusive; }

    /**
     * Retrieve the {@code restriction_maximum_inclusive_string} property (displayed as '{@literal Maximum Range Inclusive}') of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @return {@code List<String>}
     * @see #getRestrictionMaximumInclusive()
     */
    @Deprecated
    @JsonProperty("restriction_maximum_inclusive_string")
    public List<String> getRestrictionMaximumInclusiveString() { return this.restrictionMaximumInclusiveString; }

    /**
     * Set the {@code restriction_maximum_inclusive_string} property (displayed as {@code Maximum Range Inclusive}) of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @param restrictionMaximumInclusiveString the value to set
     * @see #setRestrictionMaximumInclusive(Boolean)
     */
    @Deprecated
    @JsonProperty("restriction_maximum_inclusive_string")
    public void setRestrictionMaximumInclusiveString(List<String> restrictionMaximumInclusiveString) { this.restrictionMaximumInclusiveString = restrictionMaximumInclusiveString; }


    /**
     * Retrieve the {@code restriction_maximum_value} property (displayed as '{@literal Maximum Range}') of the object.
     * @return {@code String}
     */
    @JsonProperty("restriction_maximum_value")
    public String getRestrictionMaximumValue() { return this.restrictionMaximumValue; }

    /**
     * Set the {@code restriction_maximum_value} property (displayed as {@code Maximum Range}) of the object.
     * @param restrictionMaximumValue the value to set
     */
    @JsonProperty("restriction_maximum_value")
    public void setRestrictionMaximumValue(String restrictionMaximumValue) { this.restrictionMaximumValue = restrictionMaximumValue; }

    /**
     * Retrieve the {@code restriction_minimum_inclusive} property (displayed as '{@literal Minimum Range Inclusive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("restriction_minimum_inclusive")
    public Boolean getRestrictionMinimumInclusive() { return this.restrictionMinimumInclusive; }

    /**
     * Set the {@code restriction_minimum_inclusive} property (displayed as {@code Minimum Range Inclusive}) of the object.
     * @param restrictionMinimumInclusive the value to set
     */
    @JsonProperty("restriction_minimum_inclusive")
    public void setRestrictionMinimumInclusive(Boolean restrictionMinimumInclusive) { this.restrictionMinimumInclusive = restrictionMinimumInclusive; }

    /**
     * Retrieve the {@code restriction_minimum_inclusive_string} property (displayed as '{@literal Minimum Range Inclusive}') of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @return {@code List<String>}
     * @see #getRestrictionMinimumInclusive()
     */
    @Deprecated
    @JsonProperty("restriction_minimum_inclusive_string")
    public List<String> getRestrictionMinimumInclusiveString() { return this.restrictionMinimumInclusiveString; }

    /**
     * Set the {@code restriction_minimum_inclusive_string} property (displayed as {@code Minimum Range Inclusive}) of the object.
     * No longer applicable from 11.5.0.1ru5 onwards.
     *
     * @param restrictionMinimumInclusiveString the value to set
     * @see #setRestrictionMinimumInclusive(Boolean)
     */
    @Deprecated
    @JsonProperty("restriction_minimum_inclusive_string")
    public void setRestrictionMinimumInclusiveString(List<String> restrictionMinimumInclusiveString) { this.restrictionMinimumInclusiveString = restrictionMinimumInclusiveString; }

    /**
     * Retrieve the {@code restriction_minimum_value} property (displayed as '{@literal Minimum Range}') of the object.
     * @return {@code String}
     */
    @JsonProperty("restriction_minimum_value")
    public String getRestrictionMinimumValue() { return this.restrictionMinimumValue; }

    /**
     * Set the {@code restriction_minimum_value} property (displayed as {@code Minimum Range}) of the object.
     * @param restrictionMinimumValue the value to set
     */
    @JsonProperty("restriction_minimum_value")
    public void setRestrictionMinimumValue(String restrictionMinimumValue) { this.restrictionMinimumValue = restrictionMinimumValue; }

    /**
     * Retrieve the {@code restriction_pattern} property (displayed as '{@literal Pattern}') of the object.
     * @return {@code String}
     */
    @JsonProperty("restriction_pattern")
    public String getRestrictionPattern() { return this.restrictionPattern; }

    /**
     * Set the {@code restriction_pattern} property (displayed as {@code Pattern}) of the object.
     * @param restrictionPattern the value to set
     */
    @JsonProperty("restriction_pattern")
    public void setRestrictionPattern(String restrictionPattern) { this.restrictionPattern = restrictionPattern; }

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
     * Retrieve the {@code restricts_xsd_simple_type} property (displayed as '{@literal Restricts XSD Simple Type}') of the object.
     * @return {@code XsdSimpleType}
     */
    @JsonProperty("restricts_xsd_simple_type")
    public XsdSimpleType getRestrictsXsdSimpleType() { return this.restrictsXsdSimpleType; }

    /**
     * Set the {@code restricts_xsd_simple_type} property (displayed as {@code Restricts XSD Simple Type}) of the object.
     * @param restrictsXsdSimpleType the value to set
     */
    @JsonProperty("restricts_xsd_simple_type")
    public void setRestrictsXsdSimpleType(XsdSimpleType restrictsXsdSimpleType) { this.restrictsXsdSimpleType = restrictsXsdSimpleType; }

    /**
     * Retrieve the {@code timezone} property (displayed as '{@literal Timezone}') of the object.
     * @return {@code String}
     */
    @JsonProperty("timezone")
    public String getTimezone() { return this.timezone; }

    /**
     * Set the {@code timezone} property (displayed as {@code Timezone}) of the object.
     * @param timezone the value to set
     */
    @JsonProperty("timezone")
    public void setTimezone(String timezone) { this.timezone = timezone; }

    /**
     * Retrieve the {@code total_digits} property (displayed as '{@literal Total Digits}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("total_digits")
    public Number getTotalDigits() { return this.totalDigits; }

    /**
     * Set the {@code total_digits} property (displayed as {@code Total Digits}) of the object.
     * @param totalDigits the value to set
     */
    @JsonProperty("total_digits")
    public void setTotalDigits(Number totalDigits) { this.totalDigits = totalDigits; }

    /**
     * Retrieve the {@code whitespace} property (displayed as '{@literal Whitespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("whitespace")
    public String getWhitespace() { return this.whitespace; }

    /**
     * Set the {@code whitespace} property (displayed as {@code Whitespace}) of the object.
     * @param whitespace the value to set
     */
    @JsonProperty("whitespace")
    public void setWhitespace(String whitespace) { this.whitespace = whitespace; }

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
