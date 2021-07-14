/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.EntityNotKnownException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Provides the base class for all metadata instance mappings.
 */
public abstract class InstanceMapping {

    private static final Logger log = LoggerFactory.getLogger(InstanceMapping.class);

    private boolean searchable;
    private Map<String, Object> literalOmrsPropertyMapping;

    public enum SearchFilter { ALL, NONE, SOME }

    private static final Set<String> HEADER_PROPERTIES = createHeaderProperties();
    private static Set<String> createHeaderProperties() {
        Set<String> set = new HashSet<>();
        set.add("type");
        set.add("instanceProvenanceType");
        set.add("metadataCollectionId");
        set.add("metadataCollectionName");
        set.add("replicatedBy");
        set.add("instanceLicense");
        set.add("createdBy");
        set.add("updatedBy");
        set.add("maintainedBy");
        set.add("createTime");
        set.add("updateTime");
        set.add("version");
        set.add("mappingProperties");
        set.add("guid");
        set.add("instanceURL");
        return Collections.unmodifiableSet(set);
    }

    /**
     * Retrieve the set of header properties.
     *
     * @return {@code Set<String>}
     */
    public static Set<String> getHeaderProperties() { return HEADER_PROPERTIES; }

    public InstanceMapping() {
        this.literalOmrsPropertyMapping = new HashMap<>();
        this.searchable = true;
    }

    /**
     * Setup an instance mapping with searchability defined by the provided parameter.
     *
     * @param searchable true if the mapping can be used in a search, false if it cannot
     */
    public InstanceMapping(boolean searchable) {
        this();
        this.searchable = searchable;
    }

    /**
     * Indicates whether this mapping can be used in a search (true) or not (false).
     *
     * @return boolean
     */
    public boolean isSearchable() { return searchable; }

    /**
     * Add a mapping between an OMRS property and a fixed (literal) value.
     *
     * @param omrsPropertyName the OMRS property name to be mapped
     * @param value the fixed (literal) value to be mapped
     */
    public void addLiteralPropertyMapping(String omrsPropertyName, Object value) {
        literalOmrsPropertyMapping.put(omrsPropertyName, value);
    }

    /**
     * Remove a mapping between an OMRS property and a fixed (literal) value.
     *
     * @param omrsPropertyName the OMRS property name to remove
     */
    public void removeLiteralPropertyMapping(String omrsPropertyName) {
        if (literalOmrsPropertyMapping.containsKey(omrsPropertyName)) {
            literalOmrsPropertyMapping.remove(omrsPropertyName);
        }
    }

    /**
     * Retrieve the subset of OMRS properties that have fixed (literal) mappings.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getLiteralPropertyMappings() {
        return literalOmrsPropertyMapping.keySet();
    }

    /**
     * Retrieves the fixed (literal) value that is always mapped to the provided OMRS property name.
     *
     * @param omrsPropertyName the OMRS property name for which to get the fixed (literal) value
     * @return Object
     */
    public Object getOmrsPropertyLiteralValue(String omrsPropertyName) {
        Object value = null;
        if (literalOmrsPropertyMapping.containsKey(omrsPropertyName)) {
            value = literalOmrsPropertyMapping.get(omrsPropertyName);
        }
        return value;
    }

    /**
     * Determines whether the provided OMRS property name has a fixed (literal) mapping to a value (true) rather than
     * an IGC property (false).
     *
     * @param omrsPropertyName the OMRS property name to check for a fixed (literal) mapping
     * @return boolean
     */
    public boolean isOmrsPropertyLiteralMapped(String omrsPropertyName) {
        return literalOmrsPropertyMapping.containsKey(omrsPropertyName);
    }

    /**
     * Returns the set of OMRS property names that are mapped: either to IGC properties, or as fixed (literal) values.
     *
     * @return {@code Set<String>}
     */
    public abstract Set<String> getMappedOmrsPropertyNames();

    /**
     * Determine whether to retrieve all instances, no instances, or some other combination based on the provided
     * search options.
     *
     * @param repositoryConnector connector to the OMRS repository
     * @param matchProperties the properties requested for matching
     * @return SearchFilter indicating whether to retrieve all, none or some of the instances via the search
     */
    public SearchFilter getAllNoneOrSome(IGCOMRSRepositoryConnector repositoryConnector,
                                         SearchProperties matchProperties) {

        SearchFilter filter = SearchFilter.SOME;

        if (matchProperties != null) {

            Set<String> mappedOmrsProperties = getMappedOmrsPropertyNames();
            MatchCriteria matchCriteria = matchProperties.getMatchCriteria();
            List<PropertyCondition> conditions = matchProperties.getConditions();
            if (conditions == null) {
                conditions = new ArrayList<>();
            }

            boolean allValuesAreUnequal = true;
            for (PropertyCondition condition : conditions) {
                SearchProperties nestedConditions = condition.getNestedConditions();
                if (nestedConditions != null) {
                    // Recurse on any nested conditions
                    SearchFilter nestedFilter = getAllNoneOrSome(repositoryConnector, nestedConditions);
                    switch (matchCriteria) {
                        case ALL:
                            log.debug("Requested search with ALL criteria, will pass-through nested filter as-is: {}", nestedFilter);
                            filter = nestedFilter;
                            break;
                        case NONE:
                            if (nestedFilter == SearchFilter.ALL) {
                                log.debug("Requested search with NONE criteria but nested filter would return all results -- should therefore force no results: {}", nestedConditions);
                                filter = SearchFilter.NONE;
                            } else {
                                log.debug("Requested search with NONE criteria and non-ALL nested filter, passing through: {}", nestedFilter);
                                filter = nestedFilter;
                            }
                            break;
                        case ANY:
                            if (nestedFilter == SearchFilter.ALL) {
                                log.debug("Requested search with ANY criteria and nested filter would return all results -- should therefore include all results: {}", nestedConditions);
                                filter = nestedFilter;
                            } else {
                                log.debug("Requested search with ANY criteria and nested filter would return some or no results -- may therefore be some results overall: {}", nestedConditions);
                                filter = SearchFilter.SOME;
                            }
                            break;
                    }
                } else {
                    String omrsPropertyName = condition.getProperty();
                    // If we are being asked to search for an instance header property, ensure it is one we support
                    // searching against (otherwise set the filter to NONE)
                    if (getHeaderProperties().contains(omrsPropertyName)) {
                        if (!isKnownInstanceHeaderProperty(repositoryConnector, condition)) {
                            log.debug("Requested search for header property against which search is not supported: {}", condition);
                            filter = SearchFilter.NONE;
                            break;
                        }
                    } else if (!mappedOmrsProperties.contains(omrsPropertyName)) {
                        // If there is any property we are being asked to search but it has no mapping,
                        // ensure we will get no results
                        log.debug("Requested search against a property with no mapping: {}", condition);
                        filter = SearchFilter.NONE;
                        break;
                    } else if (isOmrsPropertyLiteralMapped(omrsPropertyName)) {
                        Object literalValue = getOmrsPropertyLiteralValue(omrsPropertyName);
                        boolean valuesAreEqual = IGCRepositoryHelper.equivalentValues(literalValue, condition.getOperator(), condition.getValue());
                        if (valuesAreEqual && !matchCriteria.equals(MatchCriteria.ALL)) {
                            // If the values are equal, we can immediately short-circuit: when we should match
                            // NONE this means we should have no results, and when we should match ANY this means
                            // all results will match
                            if (matchCriteria.equals(MatchCriteria.NONE)) {
                                log.debug("Requested search with NONE criteria, but found a literal-mapped property that matches: {}", condition);
                                filter = SearchFilter.NONE;
                            } else {
                                filter = SearchFilter.ALL;
                            }
                            allValuesAreUnequal = false;
                            break;
                        } else if (!valuesAreEqual && matchCriteria.equals(MatchCriteria.ALL)) {
                            // We can also immediately short-circuit if we have been asked that all values match and
                            // we have found one that does not
                            log.debug("Requested search with ALL criteria, but found a literal-mapped property that does not match: {}", condition);
                            filter = SearchFilter.NONE;
                            break;
                        }
                    }
                }
            }
            if (allValuesAreUnequal && matchCriteria.equals(MatchCriteria.NONE)) {
                filter = SearchFilter.ALL;
            }

        }

        return filter;

    }

    /**
     * Determine whether the provided condition includes a known instance header property.
     * @param repositoryConnector connectivity to the IGC environment
     * @param condition the condition to check
     * @return boolean - true if the property is known and matches this repository, in all other cases false
     */
    private boolean isKnownInstanceHeaderProperty(IGCOMRSRepositoryConnector repositoryConnector,
                                                  PropertyCondition condition) {

        boolean known;

        String omrsPropertyName = condition.getProperty();
        PropertyComparisonOperator operator = condition.getOperator();
        InstancePropertyValue value = condition.getValue();

        switch (omrsPropertyName) {
            case "metadataCollectionId":
                String actualMCID = repositoryConnector.getMetadataCollectionId();
                if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                    known = true;
                } else if (operator.equals(PropertyComparisonOperator.EQ)) {
                    known = value != null && actualMCID.equals(value.valueAsString());
                } else if (operator.equals(PropertyComparisonOperator.LIKE)) {
                    known = value != null && actualMCID.matches(value.valueAsString());
                } else if (operator.equals(PropertyComparisonOperator.NEQ)) {
                    known = value != null && !actualMCID.equals(value.valueAsString());
                } else {
                    known = false;
                }
                break;
            case "metadataCollectionName":
                String actualMCName = repositoryConnector.getMetadataCollectionName();
                if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                    known = (actualMCName != null);
                } else if (operator.equals(PropertyComparisonOperator.EQ)) {
                    if (value == null) {
                        known = (actualMCName == null);
                    } else {
                        known = Objects.equals(actualMCName, value.valueAsString());
                    }
                } else if (operator.equals(PropertyComparisonOperator.LIKE)) {
                    if (value == null) {
                        known = (actualMCName == null);
                    } else if (actualMCName == null) {
                        known = false;
                    } else {
                        known = actualMCName.matches(value.valueAsString());
                    }
                } else if (operator.equals(PropertyComparisonOperator.NEQ)) {
                    if (value == null) {
                        known = (actualMCName != null);
                    } else {
                        known = !Objects.equals(actualMCName, value.valueAsString());
                    }
                } else {
                    known = false;
                }
                break;
            case "replicatedBy":
            case "instanceLicense":
            case "mappingProperties":
                // We will only support searching against these properties if they are empty
                known = (value == null);
                break;
            case "createdBy":
            case "updatedBy":
            case "maintainedBy":
            case "createTime":
            case "updateTime":
            case "version":
                known = true;
                break;
            default:
                // Also handles 'instanceProvenanceType', 'guid', 'instanceURL' and 'type'
                // (that is, we will not support searching on any of these properties: note
                // that 'type' is already covered by the find methods themselves)
                known = false;
                break;
        }

        return known;

    }

    /**
     * Set the instance header modification details on the instance object.
     *
     * @param header the instance object on which to set the header details
     * @param createdBy the user that created the instance
     * @param createTime the date and time that the instance was created
     * @param updatedBy the user that last updated the instance
     * @param updateTime the date and time that the instance was last updated
     */
    public static void setupInstanceModDetails(InstanceAuditHeader header,
                                               String createdBy,
                                               Date createTime,
                                               String updatedBy,
                                               Date updateTime) {

        Set<String> maintainers = new TreeSet<>();
        if (createdBy != null) {
            header.setCreatedBy(createdBy);
            header.setCreateTime(createTime);
            maintainers.add(createdBy);
        }
        if (updatedBy != null) {
            header.setUpdatedBy(updatedBy);
            header.setUpdateTime(updateTime);
            maintainers.add(updatedBy);
        }
        if (header.getUpdateTime() != null) {
            header.setVersion(header.getUpdateTime().getTime());
        }
        if (!maintainers.isEmpty()) {
            header.setMaintainedBy(new ArrayList<>(maintainers));
        }

    }

    /**
     * Add search conditions for any header properties provided.
     *
     * @param igcSearchConditionSet the set of conditions to which to append
     * @param omrsPropertyName the OMRS property name to search
     * @param operator the operation to use for the search
     * @param value the value to search
     * @param mapping the mapping between IGC and OMRS
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @throws FunctionNotSupportedException if a requested regular expression is not supported
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    public static void addHeaderPropertySearchCriteria(IGCSearchConditionSet igcSearchConditionSet,
                                                       String omrsPropertyName,
                                                       PropertyComparisonOperator operator,
                                                       InstancePropertyValue value,
                                                       EntityMapping mapping,
                                                       IGCOMRSRepositoryConnector igcomrsRepositoryConnector)
            throws FunctionNotSupportedException, RepositoryErrorException {

        final String methodName = "addHeaderPropertySearchCriteria";

        String igcType = mapping.getIgcAssetType();
        boolean hasModDetails = false;
        try {
            hasModDetails = igcomrsRepositoryConnector.getIGCRestClient().hasModificationDetails(igcType);
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }
        OMRSRepositoryHelper helper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        switch (omrsPropertyName) {
            case "createdBy":
                if (hasModDetails) {
                    IGCRepositoryHelper.addIGCSearchCondition(helper,
                            repositoryName,
                            igcSearchConditionSet,
                            "created_by",
                            operator,
                            value);
                } else {
                    raiseUnsupportedPropertyException(methodName, omrsPropertyName, igcType);
                }
                break;
            case "updatedBy":
                if (hasModDetails) {
                    IGCRepositoryHelper.addIGCSearchCondition(helper,
                            repositoryName,
                            igcSearchConditionSet,
                            "modified_by",
                            operator,
                            value);
                } else {
                    raiseUnsupportedPropertyException(methodName, omrsPropertyName, igcType);
                }
                break;
            case "createTime":
                if (hasModDetails) {
                    IGCRepositoryHelper.addIGCSearchCondition(helper,
                            repositoryName,
                            igcSearchConditionSet,
                            "created_on",
                            operator,
                            value);
                } else {
                    raiseUnsupportedPropertyException(methodName, omrsPropertyName, igcType);
                }
                break;
            case "updateTime":
                if (hasModDetails) {
                    IGCRepositoryHelper.addIGCSearchCondition(helper,
                            repositoryName,
                            igcSearchConditionSet,
                            "modified_on",
                            operator,
                            value);
                } else {
                    raiseUnsupportedPropertyException(methodName, omrsPropertyName, igcType);
                }
                break;
            case "maintainedBy":
                if (hasModDetails) {
                    IGCSearchConditionSet nested = new IGCSearchConditionSet();
                    nested.setMatchAnyCondition(true);
                    IGCRepositoryHelper.addIGCSearchCondition(helper,
                            repositoryName,
                            nested,
                            "created_by",
                            operator,
                            value);
                    IGCRepositoryHelper.addIGCSearchCondition(helper,
                            repositoryName,
                            nested,
                            "modified_by",
                            operator,
                            value);
                    igcSearchConditionSet.addNestedConditionSet(nested);
                } else {
                    raiseUnsupportedPropertyException(methodName, omrsPropertyName, igcType);
                }
                break;
            case "version":
                if (hasModDetails) {
                    IGCRepositoryHelper.addIGCSearchCondition(helper,
                            repositoryName,
                            igcSearchConditionSet,
                            "modified_on",
                            operator,
                            value);
                }
                break;
            default:
                // Nothing to do for any other properties, as they should already have caused exclusion
                // or inclusion based on the filter (getAllNoneOrSome)
                break;
        }

    }

    /**
     * Retrieve the value of the provided header property name as an InstancePropertyValue from the
     * provided EntityDetail object.
     *
     * @param ed EntityDetail from which to retrieve the header property's value
     * @param propertyName name of the header property whose value we should retrieve
     * @return InstancePropertyValue
     */
    public static InstancePropertyValue getHeaderPropertyValue(EntityDetail ed,
                                                               String propertyName) {

        InstancePropertyValue ipv;

        switch (propertyName) {
            case "createdBy":
                ipv = convertValueToIPV(ed.getCreatedBy());
                break;
            case "updatedBy":
                ipv = convertValueToIPV(ed.getUpdatedBy());
                break;
            case "createTime":
                ipv = convertValueToIPV(ed.getCreateTime());
                break;
            case "updateTime":
                ipv = convertValueToIPV(ed.getUpdateTime());
                break;
            case "maintainedBy":
                ipv = convertValueToIPV(ed.getMaintainedBy());
                break;
            case "version":
                ipv = convertValueToIPV(ed.getVersion());
                break;
            case "metadataCollectionId":
                ipv = convertValueToIPV(ed.getMetadataCollectionId());
                break;
            case "metadataCollectionName":
                ipv = convertValueToIPV(ed.getMetadataCollectionName());
                break;
            case "replicatedBy":
                ipv = convertValueToIPV(ed.getReplicatedBy());
                break;
            case "instanceLicense":
                ipv = convertValueToIPV(ed.getInstanceLicense());
                break;
            case "mappingProperties":
                ipv = convertValueToIPV(ed.getMappingProperties());
                break;
            case "instanceProvenanceType":
                ipv = convertValueToIPV(ed.getInstanceProvenanceType());
                break;
            case "guid":
                ipv = convertValueToIPV(ed.getGUID());
                break;
            case "instanceURL":
                ipv = convertValueToIPV(ed.getInstanceURL());
                break;
            default:
                // Currently no other header properties (besides 'type', which we'll exclude)
                ipv = null;
                break;
        }

        return ipv;

    }

    /**
     * Convert a basic Java value into an InstancePropertyValue representation.
     *
     * @param value to convert
     * @return InstancePropertyValue
     */
    @SuppressWarnings("unchecked")
    private static InstancePropertyValue convertValueToIPV(Object value) {
        InstancePropertyValue ipv = null;
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            PrimitivePropertyValue ppv = new PrimitivePropertyValue();
            ppv.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_STRING);
            ppv.setPrimitiveValue(value);
            ipv = ppv;
        } else if (value instanceof Date) {
            PrimitivePropertyValue ppv = new PrimitivePropertyValue();
            ppv.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_DATE);
            ppv.setPrimitiveValue(((Date)value).getTime());
            ipv = ppv;
        } else if (value instanceof Long) {
            PrimitivePropertyValue ppv = new PrimitivePropertyValue();
            ppv.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_LONG);
            ppv.setPrimitiveValue(value);
            ipv = ppv;
        } else if (value instanceof List) {
            List<?> asList = (List<?>) value;
            ArrayPropertyValue apv = new ArrayPropertyValue();
            apv.setArrayCount(asList.size());
            int index = 0;
            for (Object arrayValue : asList) {
                InstancePropertyValue innerValue = convertValueToIPV(arrayValue);
                apv.setArrayValue(index, innerValue);
                index++;
            }
            ipv = apv;
        } else if (value instanceof Map) {
            Map<String, ?> asMap = (Map<String, ?>) value;
            MapPropertyValue mpv = new MapPropertyValue();
            for (Map.Entry<String, ?> entry : asMap.entrySet()) {
                Object innerValue = entry.getValue();
                mpv.setMapValue(entry.getKey(), convertValueToIPV(innerValue));
            }
            ipv = mpv;
        } else if (value instanceof InstanceProvenanceType) {
            InstanceProvenanceType asIPT = (InstanceProvenanceType) value;
            EnumPropertyValue epv = new EnumPropertyValue();
            epv.setOrdinal(asIPT.getOrdinal());
            epv.setSymbolicName(asIPT.getName());
            epv.setDescription(asIPT.getDescription());
            ipv = epv;
        }
        return ipv;
    }

    /**
     * Throw a new FunctionNotSupportedException using the provided details.
     * @param methodName of the caller
     * @param type of the OMRS type we are attempting to search
     * @param propertyName of the property that cannot be searched
     * @throws FunctionNotSupportedException always
     */
    private static void raiseUnsupportedPropertyException(String methodName, String type, String propertyName) throws FunctionNotSupportedException {
        throw new FunctionNotSupportedException(IGCOMRSErrorCode.UNSUPPORTED_PROPERTY_FOR_TYPE.getMessageDefinition(propertyName, type),
                InstanceMapping.class.getName(),
                methodName);
    }

    /**
     * Throw a new RepositoryErrorException using the provided details.
     * @param errorCode the error
     * @param methodName of the caller
     * @param cause of the underlying problem
     * @param params any additional details for the error
     * @throws RepositoryErrorException always
     */
    protected static void raiseRepositoryErrorException(IGCOMRSErrorCode errorCode, String methodName, Exception cause, String ...params) throws RepositoryErrorException {
        if (cause == null) {
            throw new RepositoryErrorException(errorCode.getMessageDefinition(params),
                    InstanceMapping.class.getName(),
                    methodName);
        } else {
            throw new RepositoryErrorException(errorCode.getMessageDefinition(params),
                    InstanceMapping.class.getName(),
                    methodName,
                    cause);
        }
    }

    /**
     * Throw a new EntityNotKnownException using the provided details.
     * @param errorCode the error
     * @param methodName of the caller
     * @param cause of the underlying problem
     * @param params any additional details for the error
     * @throws EntityNotKnownException always
     */
    protected static void raiseEntityNotKnownException(IGCOMRSErrorCode errorCode, String methodName, Exception cause, String ...params) throws EntityNotKnownException {
        if (cause == null) {
            throw new EntityNotKnownException(errorCode.getMessageDefinition(params),
                    InstanceMapping.class.getName(),
                    methodName);
        } else {
            throw new EntityNotKnownException(errorCode.getMessageDefinition(params),
                    InstanceMapping.class.getName(),
                    methodName,
                    cause);
        }
    }

}
