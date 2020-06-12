/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceAuditHeader;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;

import java.util.*;

/**
 * Provides the base class for all metadata instance mappings.
 */
public abstract class InstanceMapping {

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
    }

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
                    filter = getAllNoneOrSome(repositoryConnector, nestedConditions);
                    if ( (matchCriteria.equals(MatchCriteria.NONE) && !filter.equals(SearchFilter.NONE))
                            || (matchCriteria.equals(MatchCriteria.ALL) && !filter.equals(SearchFilter.ALL)) ) {
                        // If some match but we have been requested to match nothing, or if we have not matched
                        // everything but have been requested to match everything, we can short-circuit as we should
                        // return no results
                        filter = SearchFilter.NONE;
                        break;
                    } else if (matchCriteria.equals(MatchCriteria.ANY) && !filter.equals(SearchFilter.NONE)) {
                        // Otherwise, if we simply need to match anything and we have any matches, we should return all
                        // results
                        filter = SearchFilter.ALL;
                        break;
                    }
                    // (and in any other case we should continue looping to determine the situation...)
                } else {
                    String omrsPropertyName = condition.getProperty();
                    if (getHeaderProperties().contains(omrsPropertyName) && !isKnownInstanceHeaderProperty(repositoryConnector, condition)) {
                        filter = SearchFilter.NONE;
                        break;
                    }
                    // If there is any property we are being asked to search but it has no mapping,
                    // ensure we will get no results
                    if (!mappedOmrsProperties.contains(omrsPropertyName)) {
                        filter = SearchFilter.NONE;
                        break;
                    }
                    if (isOmrsPropertyLiteralMapped(omrsPropertyName)) {
                        Object literalValue = getOmrsPropertyLiteralValue(omrsPropertyName);
                        boolean valuesAreEqual = IGCRepositoryHelper.equivalentValues(literalValue, condition.getOperator(), condition.getValue());
                        if (valuesAreEqual && !matchCriteria.equals(MatchCriteria.ALL)) {
                            // If the values are equal, we can immediately short-circuit: when we should match
                            // none this means we should have no results, and when we should match any this means
                            // all results will match
                            if (matchCriteria.equals(MatchCriteria.NONE)) {
                                filter = SearchFilter.NONE;
                            } else {
                                filter = SearchFilter.ALL;
                            }
                            allValuesAreUnequal = false;
                            break;
                        } else if (!valuesAreEqual && matchCriteria.equals(MatchCriteria.ALL)) {
                            // We can also immediately short-circuit if we have been asked that all values match and
                            // we have found one that does not
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
                    known = actualMCID.equals(value.valueAsString());
                } else if (operator.equals(PropertyComparisonOperator.LIKE)) {
                    known = actualMCID.matches(value.valueAsString());
                } else if (operator.equals(PropertyComparisonOperator.NEQ)) {
                    known = !actualMCID.equals(value.valueAsString());
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
                // Also handles 'type', 'instanceProvenanceType', 'guid' and 'instanceURL'
                // (that is, we will not support searching on any of these properties)
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

}
