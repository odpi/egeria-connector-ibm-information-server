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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The base class for all mappings between OMRS AttributeTypeDefs and IGC properties.
 */
public abstract class AttributeMapping {

    private static final Logger log = LoggerFactory.getLogger(AttributeMapping.class);

    private String omrsAttributeTypeDefName;

    /**
     * Construct a new attribute mapping.
     *
     * @param omrsAttributeTypeDefName the name of the open metadata attribute type
     */
    public AttributeMapping(String omrsAttributeTypeDefName) {
        this.omrsAttributeTypeDefName = omrsAttributeTypeDefName;
    }

    /**
     * Retrieve the OMRS AttributeTypeDef name for this attribute mapping applies.
     *
     * @return String
     */
    public String getOmrsAttributeTypeDefName() { return this.omrsAttributeTypeDefName; }

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

        if (value == null) {
            return null;
        }

        final String methodName = "getIgcValueFromPropertyValue";
        String igcValue;
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
                StringBuilder sb = new StringBuilder();
                sb.append("[ ");
                Map<String, InstancePropertyValue> arrayValues = ((ArrayPropertyValue) value).getArrayValues().getInstanceProperties();
                for (Map.Entry<String, InstancePropertyValue> nextEntry : arrayValues.entrySet()) {
                    sb.append("\"");
                    sb.append(getIgcValueFromPropertyValue(nextEntry.getValue()));
                    sb.append("\",");
                }
                igcValue = sb.toString();
                if (igcValue.endsWith(",")) {
                    igcValue = igcValue.substring(0, igcValue.length() - 1);
                }
                igcValue += " ]";
                break;
            case STRUCT:
            case MAP:
            default:
                throw new PropertyErrorException(IGCOMRSErrorCode.PROPERTY_CANNOT_BE_TRANSLATED.getMessageDefinition(value.toString(), category.getName()),
                        AttributeMapping.class.getName(),
                        methodName);
        }

        return igcValue;

    }

    /**
     * Comparator input for sorting based on an InstancePropertyValue. Note that this will assume that both v1 and v2
     * are the same type of property value (eg. both same type of primitive)
     *
     * @param v1 first value to compare
     * @param v2 second value to compare
     * @return int
     */
    public static int compareInstanceProperty(InstancePropertyValue v1, InstancePropertyValue v2) {

        int result = 0;
        if (v1 == v2) {
            result = 0;
        } else if (v1 == null) {
            result = -1;
        } else if (v2 == null) {
            result = 1;
        } else {

            InstancePropertyCategory category = v1.getInstancePropertyCategory();
            if (category.equals(InstancePropertyCategory.PRIMITIVE)) {
                PrimitivePropertyValue pv1 = (PrimitivePropertyValue) v1;
                PrimitivePropertyValue pv2 = (PrimitivePropertyValue) v2;
                PrimitiveDefCategory primitiveCategory = pv1.getPrimitiveDefCategory();
                switch (primitiveCategory) {
                    case OM_PRIMITIVE_TYPE_INT:
                        result = ((Integer) pv1.getPrimitiveValue()).compareTo((Integer) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_BYTE:
                        result = ((Byte) pv1.getPrimitiveValue()).compareTo((Byte) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_CHAR:
                        result = ((Character) pv1.getPrimitiveValue()).compareTo((Character) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_STRING:
                        result = ((String) pv1.getPrimitiveValue()).compareTo((String) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_DATE:
                    case OM_PRIMITIVE_TYPE_LONG:
                        result = ((Long) pv1.getPrimitiveValue()).compareTo((Long) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_FLOAT:
                        result = ((Float) pv1.getPrimitiveValue()).compareTo((Float) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_SHORT:
                        result = ((Short) pv1.getPrimitiveValue()).compareTo((Short) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_DOUBLE:
                        result = ((Double) pv1.getPrimitiveValue()).compareTo((Double) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_BOOLEAN:
                        result = ((Boolean) pv1.getPrimitiveValue()).compareTo((Boolean) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_BIGDECIMAL:
                        result = ((BigDecimal) pv1.getPrimitiveValue()).compareTo((BigDecimal) pv2.getPrimitiveValue());
                        break;
                    case OM_PRIMITIVE_TYPE_BIGINTEGER:
                        result = ((BigInteger) pv1.getPrimitiveValue()).compareTo((BigInteger) pv2.getPrimitiveValue());
                        break;
                    default:
                        result = pv1.getPrimitiveValue().toString().compareTo(pv2.getPrimitiveValue().toString());
                        break;
                }
            } else if (category.equals(InstancePropertyCategory.ENUM)) {
                EnumPropertyValue ev1 = (EnumPropertyValue) v1;
                EnumPropertyValue ev2 = (EnumPropertyValue) v2;
                result = ev1.getOrdinal() - ev2.getOrdinal();
            } else {
                log.warn("Unhandled instance value type for comparison: {}", category);
            }

        }
        return result;
    }

}
