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
 * POJO for the {@code xsd_attribute} asset type in IGC, displayed as '{@literal XSD Attribute}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=XsdAttribute.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("xsd_attribute")
public class XsdAttribute extends InformationAsset {

    @JsonProperty("context")
    protected ItemList<MainObject> context;

    @JsonProperty("data_type")
    protected String dataType;

    @JsonProperty("default_value")
    protected String defaultValue;

    @JsonProperty("enumeration_value")
    protected List<String> enumerationValue;

    @JsonProperty("fixed_value")
    protected String fixedValue;

    @JsonProperty("fraction_digits")
    protected Number fractionDigits;

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("inheritable")
    protected Boolean inheritable;

    @JsonProperty("is_maximum_range_inclusive_string")
    protected List<String> isMaximumRangeInclusiveString;

    @JsonProperty("is_minimum_range_inclusive_string")
    protected List<String> isMinimumRangeInclusiveString;

    @JsonProperty("length")
    protected Number length;

    @JsonProperty("max_length")
    protected Number maxLength;

    @JsonProperty("maximum_range")
    protected List<String> maximumRange;

    @JsonProperty("min_length")
    protected Number minLength;

    @JsonProperty("minimum_range")
    protected List<String> minimumRange;

    @JsonProperty("name_form")
    protected String nameForm;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("pattern_expression")
    protected List<String> patternExpression;

    @JsonProperty("referenced_by_xsd_attribute_groups")
    protected ItemList<XsdAttributeGroup> referencedByXsdAttributeGroups;

    @JsonProperty("referenced_by_xsd_complex_type")
    protected ItemList<XsdComplexType> referencedByXsdComplexType;

    @JsonProperty("referenced_by_xsd_elements")
    protected ItemList<MainObject> referencedByXsdElements;

    @JsonProperty("timezone")
    protected String timezone;

    @JsonProperty("total_digits")
    protected Number totalDigits;

    @JsonProperty("usage")
    protected String usage;

    @JsonProperty("white_space")
    protected String whiteSpace;

    @JsonProperty("xsd_simple_type_definition")
    protected XsdSimpleType xsdSimpleTypeDefinition;

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
     * Retrieve the {@code fraction_digits} property (displayed as '{@literal Fraction}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("fraction_digits")
    public Number getFractionDigits() { return this.fractionDigits; }

    /**
     * Set the {@code fraction_digits} property (displayed as {@code Fraction}) of the object.
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
     * Retrieve the {@code inheritable} property (displayed as '{@literal Inheritable}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("inheritable")
    public Boolean getInheritable() { return this.inheritable; }

    /**
     * Set the {@code inheritable} property (displayed as {@code Inheritable}) of the object.
     * @param inheritable the value to set
     */
    @JsonProperty("inheritable")
    public void setInheritable(Boolean inheritable) { this.inheritable = inheritable; }

    /**
     * Retrieve the {@code is_maximum_range_inclusive_string} property (displayed as '{@literal Maximum Range Inclusive}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("is_maximum_range_inclusive_string")
    public List<String> getIsMaximumRangeInclusiveString() { return this.isMaximumRangeInclusiveString; }

    /**
     * Set the {@code is_maximum_range_inclusive_string} property (displayed as {@code Maximum Range Inclusive}) of the object.
     * @param isMaximumRangeInclusiveString the value to set
     */
    @JsonProperty("is_maximum_range_inclusive_string")
    public void setIsMaximumRangeInclusiveString(List<String> isMaximumRangeInclusiveString) { this.isMaximumRangeInclusiveString = isMaximumRangeInclusiveString; }

    /**
     * Retrieve the {@code is_minimum_range_inclusive_string} property (displayed as '{@literal Minimum Range Inclusive}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("is_minimum_range_inclusive_string")
    public List<String> getIsMinimumRangeInclusiveString() { return this.isMinimumRangeInclusiveString; }

    /**
     * Set the {@code is_minimum_range_inclusive_string} property (displayed as {@code Minimum Range Inclusive}) of the object.
     * @param isMinimumRangeInclusiveString the value to set
     */
    @JsonProperty("is_minimum_range_inclusive_string")
    public void setIsMinimumRangeInclusiveString(List<String> isMinimumRangeInclusiveString) { this.isMinimumRangeInclusiveString = isMinimumRangeInclusiveString; }

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
     * Retrieve the {@code referenced_by_xsd_complex_type} property (displayed as '{@literal Referenced by XSD Complex Types}') of the object.
     * @return {@code ItemList<XsdComplexType>}
     */
    @JsonProperty("referenced_by_xsd_complex_type")
    public ItemList<XsdComplexType> getReferencedByXsdComplexType() { return this.referencedByXsdComplexType; }

    /**
     * Set the {@code referenced_by_xsd_complex_type} property (displayed as {@code Referenced by XSD Complex Types}) of the object.
     * @param referencedByXsdComplexType the value to set
     */
    @JsonProperty("referenced_by_xsd_complex_type")
    public void setReferencedByXsdComplexType(ItemList<XsdComplexType> referencedByXsdComplexType) { this.referencedByXsdComplexType = referencedByXsdComplexType; }

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
