/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.PropertyErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The base class for all mappings between OMRS AttributeTypeDefs and IGC properties.
 */
public abstract class AttributeMapping {

    private static final Logger log = LoggerFactory.getLogger(AttributeMapping.class);

    private IGCPropertyType igcPropertyType;
    private String omrsAttributeTypeDefName;
    private String igcAssetType;
    private String igcPropertyName;

    public enum IGCPropertyType {
        STRING, BOOLEAN, DATETIME, NUMBER, ENUM,
        STRING_ARRAY, NUMBER_ARRAY, DATETIME_ARRAY,
        REFERENCE, REFERENCE_LIST
    }

    public AttributeMapping(IGCPropertyType igcPropertyType,
                            String omrsAttributeTypeDefName) {
        this(null, null, igcPropertyType, omrsAttributeTypeDefName);
    }

    public AttributeMapping(String igcAssetType,
                            String igcPropertyName,
                            IGCPropertyType igcPropertyType,
                            String omrsAttributeTypeDefName) {
        this.igcAssetType = igcAssetType;
        this.igcPropertyName = igcPropertyName;
        this.igcPropertyType = igcPropertyType;
        this.omrsAttributeTypeDefName = omrsAttributeTypeDefName;
    }

    /**
     * Indicates whether this mapping is for a general attribute (true) or a specific attribute (false).
     *
     * @return boolean
     */
    public boolean isForGeneralAttribute() { return this.igcAssetType == null; }

    /**
     * Indicates whether this mapping is for a specific attribute (true) or a general attribute (false).
     *
     * @return boolean
     */
    public boolean isForSpecificAttribute() { return !isForGeneralAttribute(); }

    /**
     * Retrieve the IGC asset type to which this attribute mapping applies. Note that this will only return a non-null
     * value when the attribute mapping is for a specific attribute.
     *
     * @return String
     * @see #isForSpecificAttribute()
     */
    public String getIgcAssetType() { return this.igcAssetType; }

    /**
     * Retrieve the OMRS AttributeTypeDef name for this attribute mapping applies.
     *
     * @return String
     */
    public String getOmrsAttributeTypeDefName() { return this.omrsAttributeTypeDefName; }

    /**
     * Retrieve the IGC property type for this attribute mapping.
     *
     * @return IGCPropertyType
     */
    public IGCPropertyType getIgcPropertyType() { return this.igcPropertyType; }

    /**
     * Indicates whether this attribute mapping can be applied to the provided IGC property (for the provided IGC asset
     * type) (true) or not (false).
     *
     * @param igcAssetType name of the IGC asset type
     * @param igcPropertyName name of the IGC property on that asset type
     * @return boolean
     */
    public boolean matchesProperty(String igcAssetType, String igcPropertyName) {
        // TODO...
        return false;
    }

    /**
     * Add the supplied property to an instance properties object.  If the instance property object
     * supplied is null, a new instance properties object is created.
     *
     * @param omrsRepositoryHelper the OMRS repository helper
     * @param sourceName  name of caller
     * @param properties  properties object to add property to may be null.
     * @param property  the property
     * @param propertyValue  value of property
     * @param methodName  calling method name
     * @return instance properties object.
     */
    public static InstanceProperties addPrimitivePropertyToInstance(OMRSRepositoryHelper omrsRepositoryHelper,
                                                                    String sourceName,
                                                                    InstanceProperties properties,
                                                                    TypeDefAttribute property,
                                                                    Object propertyValue,
                                                                    String methodName) {

        InstanceProperties  resultingProperties = properties;

        if (propertyValue != null) {
            String propertyName = property.getAttributeName();

            if (property.getAttributeType().getCategory() == AttributeTypeDefCategory.PRIMITIVE) {
                try {
                    // First check if our single primitive value is wrapped in a list (happens occasionally in IGC),
                    // and if so take only its initial object value
                    if (propertyValue instanceof List) {
                        List<?> list = (List<?>)propertyValue;
                        if (!list.isEmpty()) {
                            propertyValue = list.get(0);
                        }
                    }
                    PrimitiveDef primitiveDef = (PrimitiveDef) property.getAttributeType();
                    switch (primitiveDef.getPrimitiveDefCategory()) {
                        case OM_PRIMITIVE_TYPE_BOOLEAN:
                            boolean booleanValue;
                            if (propertyValue instanceof Boolean) {
                                booleanValue = (Boolean) propertyValue;
                            } else {
                                booleanValue = Boolean.valueOf(propertyValue.toString());
                            }
                            resultingProperties = omrsRepositoryHelper.addBooleanPropertyToInstance(
                                    sourceName,
                                    properties,
                                    propertyName,
                                    booleanValue,
                                    methodName
                            );
                            break;
                        case OM_PRIMITIVE_TYPE_INT:
                            Integer intValue = null;
                            if (propertyValue instanceof Integer) {
                                intValue = (Integer) propertyValue;
                            } else if (propertyValue instanceof Number) {
                                intValue = ((Number) propertyValue).intValue();
                            } else {
                                String propertyVal = propertyValue.toString();
                                if (!propertyVal.equals("")) {
                                    intValue = Integer.valueOf(propertyVal);
                                }
                            }
                            if (intValue != null) {
                                resultingProperties = omrsRepositoryHelper.addIntPropertyToInstance(
                                        sourceName,
                                        properties,
                                        propertyName,
                                        intValue,
                                        methodName
                                );
                            }
                            break;
                        case OM_PRIMITIVE_TYPE_LONG:
                            Long longValue = null;
                            if (propertyValue instanceof Long) {
                                longValue = (Long) propertyValue;
                            } else if (propertyValue instanceof Number) {
                                longValue = ((Number) propertyValue).longValue();
                            } else {
                                String propertyVal = propertyValue.toString();
                                if (!propertyVal.equals("")) {
                                    longValue = Long.valueOf(propertyVal);
                                }
                            }
                            if (longValue != null) {
                                resultingProperties = omrsRepositoryHelper.addLongPropertyToInstance(
                                        sourceName,
                                        properties,
                                        propertyName,
                                        longValue,
                                        methodName
                                );
                            }
                            break;
                        case OM_PRIMITIVE_TYPE_FLOAT:
                            Float floatValue = null;
                            if (propertyValue instanceof Float) {
                                floatValue = (Float) propertyValue;
                            } else if (propertyValue instanceof Number) {
                                floatValue = ((Number) propertyValue).floatValue();
                            } else {
                                String propertyVal = propertyValue.toString();
                                if (!propertyVal.equals("")) {
                                    floatValue = Float.valueOf(propertyVal);
                                }
                            }
                            if (floatValue != null) {
                                resultingProperties = omrsRepositoryHelper.addFloatPropertyToInstance(
                                        sourceName,
                                        properties,
                                        propertyName,
                                        floatValue,
                                        methodName
                                );
                            }
                            break;
                        case OM_PRIMITIVE_TYPE_STRING:
                            String stringValue;
                            if (propertyValue instanceof String) {
                                stringValue = (String) propertyValue;
                            } else {
                                stringValue = propertyValue.toString();
                            }
                            // IGC will respond with empty strings when there is no value set, so only bother
                            // adding a string value to the mapped property if it is non-empty
                            if (stringValue != null && !stringValue.equals("")) {
                                resultingProperties = omrsRepositoryHelper.addStringPropertyToInstance(
                                        sourceName,
                                        properties,
                                        propertyName,
                                        stringValue,
                                        methodName
                                );
                            }
                            break;
                        case OM_PRIMITIVE_TYPE_DATE:
                            if (propertyValue instanceof Date) {
                                resultingProperties = omrsRepositoryHelper.addDatePropertyToInstance(
                                        sourceName,
                                        properties,
                                        propertyName,
                                        (Date) propertyValue,
                                        methodName
                                );
                            } else {
                                log.warn("Unable to parse date automatically -- must be first converted before passing in: {}", propertyValue);
                            }
                            break;
                        default:
                            log.error("Unhandled primitive type {} for {}", primitiveDef.getPrimitiveDefCategory(), propertyName);
                    }
                } catch (ClassCastException e) {
                    log.error("Unable to cast {} to {} for {}", propertyValue, property.getAttributeType(), propertyName, e);
                } catch (NumberFormatException e) {
                    log.warn("Unable to convert {} to {} for {}", propertyValue, property.getAttributeType(), propertyName, e);
                }
            } else {
                log.error("Cannot translate non-primitive property {} this way.", propertyName);
            }
        }

        return resultingProperties;

    }

    /**
     * Retrieve the provided OMRS value as a mapped IGC value.
     *
     * @param value the value to translate from an OMRS instance property value to an IGC value
     * @return String
     * @throws PropertyErrorException if the value cannot be translated to an IGC value
     */
    public static String getIgcValueFromPropertyValue(InstancePropertyValue value) throws PropertyErrorException {

        final String methodName = "getIgcValueFromPropertyValue";
        String igcValue = null;
        InstancePropertyCategory category = value.getInstancePropertyCategory();
        switch (category) {
            case PRIMITIVE:
                PrimitivePropertyValue actualValue = (PrimitivePropertyValue) value;
                PrimitiveDefCategory primitiveType = actualValue.getPrimitiveDefCategory();
                switch (primitiveType) {
                    case OM_PRIMITIVE_TYPE_DATE:
                        Long epoch = (Long) actualValue.getPrimitiveValue();
                        igcValue = "" + epoch;
                        break;
                    case OM_PRIMITIVE_TYPE_BOOLEAN:
                    case OM_PRIMITIVE_TYPE_BYTE:
                    case OM_PRIMITIVE_TYPE_CHAR:
                    case OM_PRIMITIVE_TYPE_SHORT:
                    case OM_PRIMITIVE_TYPE_INT:
                    case OM_PRIMITIVE_TYPE_LONG:
                    case OM_PRIMITIVE_TYPE_FLOAT:
                    case OM_PRIMITIVE_TYPE_DOUBLE:
                    case OM_PRIMITIVE_TYPE_BIGINTEGER:
                    case OM_PRIMITIVE_TYPE_BIGDECIMAL:
                    case OM_PRIMITIVE_TYPE_STRING:
                    default:
                        igcValue = actualValue.getPrimitiveValue().toString();
                        break;
                }
                break;
            case ENUM:
                igcValue = ((EnumPropertyValue) value).getSymbolicName();
                break;
            case ARRAY:
                igcValue = "[ ";
                Map<String, InstancePropertyValue> arrayValues = ((ArrayPropertyValue) value).getArrayValues().getInstanceProperties();
                for (Map.Entry<String, InstancePropertyValue> nextEntry : arrayValues.entrySet()) {
                    igcValue += "\"" + getIgcValueFromPropertyValue(nextEntry.getValue()) + "\",";
                }
                if (igcValue.endsWith(",")) {
                    igcValue = igcValue.substring(0, igcValue.length() - 1);
                }
                igcValue += " ]";
                break;
            case STRUCT:
            case MAP:
            default:
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.PROPERTY_CANNOT_BE_TRANSLATED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        value.toString(),
                        category.getName());
                throw new PropertyErrorException(errorCode.getHTTPErrorCode(),
                        AttributeMapping.class.getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
        }

        return igcValue;

    }

}
