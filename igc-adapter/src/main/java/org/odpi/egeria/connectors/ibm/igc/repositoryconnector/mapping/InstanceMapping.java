/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;

import java.util.*;

/**
 * Provides the base class for all metadata instance mappings.
 */
public abstract class InstanceMapping {

    private Map<String, Object> literalOmrsPropertyMapping;

    public enum SearchFilter { ALL, NONE, SOME }

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
     * @param matchProperties the properties requested for matching
     * @return SearchFilter indicating whether to retrieve all, none or some of the instances via the search
     */
    public SearchFilter getAllNoneOrSome(SearchProperties matchProperties) {

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
                    // Recuse on any nested conditions
                    filter = getAllNoneOrSome(nestedConditions);
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

}
