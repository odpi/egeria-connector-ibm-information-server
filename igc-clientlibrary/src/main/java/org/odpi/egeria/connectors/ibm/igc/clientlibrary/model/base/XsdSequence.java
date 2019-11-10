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
 * POJO for the {@code xsd_sequence} asset type in IGC, displayed as '{@literal XSD Sequence}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdSequence.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_sequence")
public class XsdSequence extends InformationAsset {

    @JsonProperty("contains_attributes")
    protected ItemList<XsdAttribute> containsAttributes;

    @JsonProperty("contains_elements")
    protected ItemList<MainObject> containsElements;

    @JsonProperty("context")
    protected ItemList<MainObject> context;

    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("default_value")
    protected String defaultValue;

    @JsonProperty("enumeration_value")
    protected List<String> enumerationValue;

    @JsonProperty("extends_xsd_complex_type")
    protected XsdComplexType extendsXsdComplexType;

    @JsonProperty("fixed_value")
    protected String fixedValue;

    @JsonProperty("foreign_keys")
    protected ItemList<XsdForeignKey> foreignKeys;

    @JsonProperty("fraction_digits")
    protected Number fractionDigits;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("is_abstract")
    protected Boolean isAbstract;

    @JsonProperty("is_maximum_range_inclusive")
    protected Boolean isMaximumRangeInclusive;

    /**
     * @deprecated No longer applicable from 11.5.0.1ru5 onwards.
     * @see #isMaximumRangeInclusive
     */
    @Deprecated
    @JsonProperty("is_maximum_range_inclusive_string")
    protected List<String> isMaximumRangeInclusiveString;

    @JsonProperty("is_minimum_range_inclusive")
    protected Boolean isMinimumRangeInclusive;

    /**
     * @deprecated No longer applicable from 11.5.0.1ru5 onwards.
     * @see #isMinimumRangeInclusive
     */
    @Deprecated
    @JsonProperty("is_minimum_range_inclusive_string")
    protected List<String> isMinimumRangeInclusiveString;

    @JsonProperty("is_nullable")
    protected Boolean isNullable;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("max_length")
    protected Number maxLength;

    @JsonProperty("max_occurs")
    protected Number maxOccurs;

    @JsonProperty("maximum_range")
    protected List<String> maximumRange;

    @JsonProperty("min_length")
    protected Number minLength;

    @JsonProperty("min_occurs")
    protected Number minOccurs;

    @JsonProperty("minimum_range")
    protected List<String> minimumRange;

    @JsonProperty("name_form")
    protected String nameForm;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("pattern_expression")
    protected List<String> patternExpression;

    @JsonProperty("primary_keys")
    protected ItemList<XsdPrimaryKey> primaryKeys;

    @JsonProperty("referenced_by_xsd_complex_types")
    protected ItemList<XsdElementReference> referencedByXsdComplexTypes;

    @JsonProperty("referenced_by_xsd_element_groups")
    protected ItemList<XsdElementReference> referencedByXsdElementGroups;

    @JsonProperty("referenced_by_xsd_elements")
    protected ItemList<XsdElementReference> referencedByXsdElements;

    @JsonProperty("references_xsd_attribute_groups")
    protected ItemList<XsdAttributeGroup> referencesXsdAttributeGroups;

    @JsonProperty("references_xsd_attributes")
    protected ItemList<XsdAttributeReference> referencesXsdAttributes;

    @JsonProperty("references_xsd_element_groups")
    protected ItemList<XsdElementGroupReference> referencesXsdElementGroups;

    @JsonProperty("references_xsd_elements")
    protected ItemList<XsdElementReference> referencesXsdElements;

    @JsonProperty("restricts_xsd_complex_type")
    protected XsdComplexType restrictsXsdComplexType;

    @JsonProperty("restricts_xsd_simple_type")
    protected XsdSimpleType restrictsXsdSimpleType;

    @JsonProperty("timezone")
    protected String timezone;

    @JsonProperty("total_digits")
    protected Number totalDigits;

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

    @JsonProperty("unique_keys")
    protected ItemList<XsdUniqueKey> uniqueKeys;

    @JsonProperty("white_space")
    protected String whiteSpace;

    @JsonProperty("xsd_complex_type_definition")
    protected XsdComplexType xsdComplexTypeDefinition;

    @JsonProperty("xsd_simple_type_definition")
    protected XsdSimpleType xsdSimpleTypeDefinition;

    /**
     * Retrieve the {@code contains_attributes} property (displayed as '{@literal Contains XSD Attributes}') of the object.
     * @return {@code ItemList<XsdAttribute>}
     */
    @JsonProperty("contains_attributes")
    public ItemList<XsdAttribute> getContainsAttributes() { return this.containsAttributes; }

    /**
     * Set the {@code contains_attributes} property (displayed as {@code Contains XSD Attributes}) of the object.
     * @param containsAttributes the value to set
     */
    @JsonProperty("contains_attributes")
    public void setContainsAttributes(ItemList<XsdAttribute> containsAttributes) { this.containsAttributes = containsAttributes; }

    /**
     * Retrieve the {@code contains_elements} property (displayed as '{@literal Contains XSD Elements}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("contains_elements")
    public ItemList<MainObject> getContainsElements() { return this.containsElements; }

    /**
     * Set the {@code contains_elements} property (displayed as {@code Contains XSD Elements}) of the object.
     * @param containsElements the value to set
     */
    @JsonProperty("contains_elements")
    public void setContainsElements(ItemList<MainObject> containsElements) { this.containsElements = containsElements; }

    /**
     * Retrieve the {@code context} property (displayed as '{@literal Parent XSD Object}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("context")
    public ItemList<MainObject> getTheContext() { return this.context; }

    /**
     * Set the {@code context} property (displayed as {@code Parent XSD Object}) of the object.
     * @param context the value to set
     */
    @JsonProperty("context")
    public void setTheContext(ItemList<MainObject> context) { this.context = context; }

    /**
     * Retrieve the {@code data_type} property (displayed as '{@literal Data Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("data_type")
    public String getDataType() { return this.dataType; }

    /**
     * Set the {@code data_type} property (displayed as {@code Data Type}) of the object.
     * @param dataType the value to set
     */
    @JsonProperty("data_type")
    public void setDataType(String dataType) { this.dataType = dataType; }

    /**
     * Retrieve the {@code default_value} property (displayed as '{@literal Default Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("default_value")
    public String getDefaultValue() { return this.defaultValue; }

    /**
     * Set the {@code default_value} property (displayed as {@code Default Value}) of the object.
     * @param defaultValue the value to set
     */
    @JsonProperty("default_value")
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }

    /**
     * Retrieve the {@code enumeration_value} property (displayed as '{@literal Enumeration Values}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("enumeration_value")
    public List<String> getEnumerationValue() { return this.enumerationValue; }

    /**
     * Set the {@code enumeration_value} property (displayed as {@code Enumeration Values}) of the object.
     * @param enumerationValue the value to set
     */
    @JsonProperty("enumeration_value")
    public void setEnumerationValue(List<String> enumerationValue) { this.enumerationValue = enumerationValue; }

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
     * Retrieve the {@code fixed_value} property (displayed as '{@literal Fixed Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("fixed_value")
    public String getFixedValue() { return this.fixedValue; }

    /**
     * Set the {@code fixed_value} property (displayed as {@code Fixed Value}) of the object.
     * @param fixedValue the value to set
     */
    @JsonProperty("fixed_value")
    public void setFixedValue(String fixedValue) { this.fixedValue = fixedValue; }

    /**
     * Retrieve the {@code foreign_keys} property (displayed as '{@literal Foreign Keys}') of the object.
     * @return {@code ItemList<XsdForeignKey>}
     */
    @JsonProperty("foreign_keys")
    public ItemList<XsdForeignKey> getForeignKeys() { return this.foreignKeys; }

    /**
     * Set the {@code foreign_keys} property (displayed as {@code Foreign Keys}) of the object.
     * @param foreignKeys the value to set
     */
    @JsonProperty("foreign_keys")
    public void setForeignKeys(ItemList<XsdForeignKey> foreignKeys) { this.foreignKeys = foreignKeys; }

    /**
     * Retrieve the {@code fraction_digits} property (displayed as '{@literal Fraction Digits}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("fraction_digits")
    public Number getFractionDigits() { return this.fractionDigits; }

    /**
     * Set the {@code fraction_digits} property (displayed as {@code Fraction Digits}) of the object.
     * @param fractionDigits the value to set
     */
    @JsonProperty("fraction_digits")
    public void setFractionDigits(Number fractionDigits) { this.fractionDigits = fractionDigits; }

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
     * Retrieve the {@code is_abstract} property (displayed as '{@literal Abstract}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_abstract")
    public Boolean getIsAbstract() { return this.isAbstract; }

    /**
     * Set the {@code is_abstract} property (displayed as {@code Abstract}) of the object.
     * @param isAbstract the value to set
     */
    @JsonProperty("is_abstract")
    public void setIsAbstract(Boolean isAbstract) { this.isAbstract = isAbstract; }

    /**
     * Retrieve the {@code is_maximum_range_inclusive} property (displayed as '{@literal Maximum Range Inclusive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_maximum_range_inclusive")
    public Boolean getIsMaximumRangeInclusive() { return this.isMaximumRangeInclusive; }

    /**
     * Set the {@code is_maximum_range_inclusive} property (displayed as {@code Maximum Range Inclusive}) of the object.
     * @param isMaximumRangeInclusive the value to set
     */
    @JsonProperty("is_maximum_range_inclusive")
    public void setIsMaximumRangeInclusive(Boolean isMaximumRangeInclusive) { this.isMaximumRangeInclusive = isMaximumRangeInclusive; }

    /**
     * Retrieve the {@code is_maximum_range_inclusive_string} property (displayed as '{@literal Maximum Range Inclusive}') of the object.
     * @deprecated No longer applicable from 11.5.0.1ru5 onwards.
     * @return {@code List<String>}
     * @see #isMaximumRangeInclusive
     */
    @Deprecated
    @JsonProperty("is_maximum_range_inclusive_string")
    public List<String> getIsMaximumRangeInclusiveString() { return this.isMaximumRangeInclusiveString; }

    /**
     * Set the {@code is_maximum_range_inclusive_string} property (displayed as {@code Maximum Range Inclusive}) of the object.
     * @deprecated No longer applicable from 11.5.0.1ru5 onwards.
     * @param isMaximumRangeInclusiveString the value to set
     * @see #setIsMaximumRangeInclusive(Boolean)
     */
    @Deprecated
    @JsonProperty("is_maximum_range_inclusive_string")
    public void setIsMaximumRangeInclusiveString(List<String> isMaximumRangeInclusiveString) { this.isMaximumRangeInclusiveString = isMaximumRangeInclusiveString; }

    /**
     * Retrieve the {@code is_minimum_range_inclusive} property (displayed as '{@literal Minimum Range Inclusive}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_minimum_range_inclusive")
    public Boolean getIsMinimumRangeInclusive() { return this.isMinimumRangeInclusive; }

    /**
     * Set the {@code is_minimum_range_inclusive} property (displayed as {@code Minimum Range Inclusive}) of the object.
     * @param isMinimumRangeInclusive the value to set
     */
    @JsonProperty("is_minimum_range_inclusive")
    public void setIsMinimumRangeInclusive(Boolean isMinimumRangeInclusive) { this.isMinimumRangeInclusive = isMinimumRangeInclusive; }

    /**
     * Retrieve the {@code is_minimum_range_inclusive_string} property (displayed as '{@literal Minimum Range Inclusive}') of the object.
     * @deprecated No longer applicable from 11.5.0.1ru5 onwards.
     * @return {@code List<String>}
     * @see #isMinimumRangeInclusive
     */
    @Deprecated
    @JsonProperty("is_minimum_range_inclusive_string")
    public List<String> getIsMinimumRangeInclusiveString() { return this.isMinimumRangeInclusiveString; }

    /**
     * Set the {@code is_minimum_range_inclusive_string} property (displayed as {@code Minimum Range Inclusive}) of the object.
     * @deprecated No longer applicable from 11.5.0.1ru5 onwards.
     * @param isMinimumRangeInclusiveString the value to set
     * @see #setIsMinimumRangeInclusive(Boolean)
     */
    @Deprecated
    @JsonProperty("is_minimum_range_inclusive_string")
    public void setIsMinimumRangeInclusiveString(List<String> isMinimumRangeInclusiveString) { this.isMinimumRangeInclusiveString = isMinimumRangeInclusiveString; }

    /**
     * Retrieve the {@code is_nullable} property (displayed as '{@literal Allow Null Values}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_nullable")
    public Boolean getIsNullable() { return this.isNullable; }

    /**
     * Set the {@code is_nullable} property (displayed as {@code Allow Null Values}) of the object.
     * @param isNullable the value to set
     */
    @JsonProperty("is_nullable")
    public void setIsNullable(Boolean isNullable) { this.isNullable = isNullable; }

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
     * Retrieve the {@code maximum_range} property (displayed as '{@literal Maximum Range}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("maximum_range")
    public List<String> getMaximumRange() { return this.maximumRange; }

    /**
     * Set the {@code maximum_range} property (displayed as {@code Maximum Range}) of the object.
     * @param maximumRange the value to set
     */
    @JsonProperty("maximum_range")
    public void setMaximumRange(List<String> maximumRange) { this.maximumRange = maximumRange; }

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
     * Retrieve the {@code minimum_range} property (displayed as '{@literal Minimum Range}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("minimum_range")
    public List<String> getMinimumRange() { return this.minimumRange; }

    /**
     * Set the {@code minimum_range} property (displayed as {@code Minimum Range}) of the object.
     * @param minimumRange the value to set
     */
    @JsonProperty("minimum_range")
    public void setMinimumRange(List<String> minimumRange) { this.minimumRange = minimumRange; }

    /**
     * Retrieve the {@code name_form} property (displayed as '{@literal Form}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name_form")
    public String getNameForm() { return this.nameForm; }

    /**
     * Set the {@code name_form} property (displayed as {@code Form}) of the object.
     * @param nameForm the value to set
     */
    @JsonProperty("name_form")
    public void setNameForm(String nameForm) { this.nameForm = nameForm; }

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
     * Retrieve the {@code pattern_expression} property (displayed as '{@literal Pattern}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("pattern_expression")
    public List<String> getPatternExpression() { return this.patternExpression; }

    /**
     * Set the {@code pattern_expression} property (displayed as {@code Pattern}) of the object.
     * @param patternExpression the value to set
     */
    @JsonProperty("pattern_expression")
    public void setPatternExpression(List<String> patternExpression) { this.patternExpression = patternExpression; }

    /**
     * Retrieve the {@code primary_keys} property (displayed as '{@literal Primary Keys}') of the object.
     * @return {@code ItemList<XsdPrimaryKey>}
     */
    @JsonProperty("primary_keys")
    public ItemList<XsdPrimaryKey> getPrimaryKeys() { return this.primaryKeys; }

    /**
     * Set the {@code primary_keys} property (displayed as {@code Primary Keys}) of the object.
     * @param primaryKeys the value to set
     */
    @JsonProperty("primary_keys")
    public void setPrimaryKeys(ItemList<XsdPrimaryKey> primaryKeys) { this.primaryKeys = primaryKeys; }

    /**
     * Retrieve the {@code referenced_by_xsd_complex_types} property (displayed as '{@literal Referenced by XSD Complex Types}') of the object.
     * @return {@code ItemList<XsdElementReference>}
     */
    @JsonProperty("referenced_by_xsd_complex_types")
    public ItemList<XsdElementReference> getReferencedByXsdComplexTypes() { return this.referencedByXsdComplexTypes; }

    /**
     * Set the {@code referenced_by_xsd_complex_types} property (displayed as {@code Referenced by XSD Complex Types}) of the object.
     * @param referencedByXsdComplexTypes the value to set
     */
    @JsonProperty("referenced_by_xsd_complex_types")
    public void setReferencedByXsdComplexTypes(ItemList<XsdElementReference> referencedByXsdComplexTypes) { this.referencedByXsdComplexTypes = referencedByXsdComplexTypes; }

    /**
     * Retrieve the {@code referenced_by_xsd_element_groups} property (displayed as '{@literal Referenced by XSD Element Groups}') of the object.
     * @return {@code ItemList<XsdElementReference>}
     */
    @JsonProperty("referenced_by_xsd_element_groups")
    public ItemList<XsdElementReference> getReferencedByXsdElementGroups() { return this.referencedByXsdElementGroups; }

    /**
     * Set the {@code referenced_by_xsd_element_groups} property (displayed as {@code Referenced by XSD Element Groups}) of the object.
     * @param referencedByXsdElementGroups the value to set
     */
    @JsonProperty("referenced_by_xsd_element_groups")
    public void setReferencedByXsdElementGroups(ItemList<XsdElementReference> referencedByXsdElementGroups) { this.referencedByXsdElementGroups = referencedByXsdElementGroups; }

    /**
     * Retrieve the {@code referenced_by_xsd_elements} property (displayed as '{@literal Referenced by XSD Elements}') of the object.
     * @return {@code ItemList<XsdElementReference>}
     */
    @JsonProperty("referenced_by_xsd_elements")
    public ItemList<XsdElementReference> getReferencedByXsdElements() { return this.referencedByXsdElements; }

    /**
     * Set the {@code referenced_by_xsd_elements} property (displayed as {@code Referenced by XSD Elements}) of the object.
     * @param referencedByXsdElements the value to set
     */
    @JsonProperty("referenced_by_xsd_elements")
    public void setReferencedByXsdElements(ItemList<XsdElementReference> referencedByXsdElements) { this.referencedByXsdElements = referencedByXsdElements; }

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
     * Retrieve the {@code unique_keys} property (displayed as '{@literal Unique Keys}') of the object.
     * @return {@code ItemList<XsdUniqueKey>}
     */
    @JsonProperty("unique_keys")
    public ItemList<XsdUniqueKey> getUniqueKeys() { return this.uniqueKeys; }

    /**
     * Set the {@code unique_keys} property (displayed as {@code Unique Keys}) of the object.
     * @param uniqueKeys the value to set
     */
    @JsonProperty("unique_keys")
    public void setUniqueKeys(ItemList<XsdUniqueKey> uniqueKeys) { this.uniqueKeys = uniqueKeys; }

    /**
     * Retrieve the {@code white_space} property (displayed as '{@literal Total Whitespace}') of the object.
     * @return {@code String}
     */
    @JsonProperty("white_space")
    public String getWhiteSpace() { return this.whiteSpace; }

    /**
     * Set the {@code white_space} property (displayed as {@code Total Whitespace}) of the object.
     * @param whiteSpace the value to set
     */
    @JsonProperty("white_space")
    public void setWhiteSpace(String whiteSpace) { this.whiteSpace = whiteSpace; }

    /**
     * Retrieve the {@code xsd_complex_type_definition} property (displayed as '{@literal XSD Complex Type Definition}') of the object.
     * @return {@code XsdComplexType}
     */
    @JsonProperty("xsd_complex_type_definition")
    public XsdComplexType getXsdComplexTypeDefinition() { return this.xsdComplexTypeDefinition; }

    /**
     * Set the {@code xsd_complex_type_definition} property (displayed as {@code XSD Complex Type Definition}) of the object.
     * @param xsdComplexTypeDefinition the value to set
     */
    @JsonProperty("xsd_complex_type_definition")
    public void setXsdComplexTypeDefinition(XsdComplexType xsdComplexTypeDefinition) { this.xsdComplexTypeDefinition = xsdComplexTypeDefinition; }

    /**
     * Retrieve the {@code xsd_simple_type_definition} property (displayed as '{@literal XSD Simple Type Definition}') of the object.
     * @return {@code XsdSimpleType}
     */
    @JsonProperty("xsd_simple_type_definition")
    public XsdSimpleType getXsdSimpleTypeDefinition() { return this.xsdSimpleTypeDefinition; }

    /**
     * Set the {@code xsd_simple_type_definition} property (displayed as {@code XSD Simple Type Definition}) of the object.
     * @param xsdSimpleTypeDefinition the value to set
     */
    @JsonProperty("xsd_simple_type_definition")
    public void setXsdSimpleTypeDefinition(XsdSimpleType xsdSimpleTypeDefinition) { this.xsdSimpleTypeDefinition = xsdSimpleTypeDefinition; }

}
