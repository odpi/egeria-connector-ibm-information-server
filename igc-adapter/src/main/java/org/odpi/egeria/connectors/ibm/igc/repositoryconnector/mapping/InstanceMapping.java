/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
     * @param mapping the mapping to use for the instances
     * @param matchProperties the properties requested for matching
     * @param matchCriteria the criteria requested for matching
     * @return SearchFilter indicating whether to retrieve all, none or some of the instances via the search
     */
    public static SearchFilter getAllNoneOrSome(InstanceMapping mapping, InstanceProperties matchProperties, MatchCriteria matchCriteria) {

        SearchFilter filter = SearchFilter.SOME;

        if (matchProperties != null) {

            Set<String> mappedOmrsProperties = mapping.getMappedOmrsPropertyNames();
            Map<String, InstancePropertyValue> propertiesToMatch = matchProperties.getInstanceProperties();
            if (propertiesToMatch == null) {
                propertiesToMatch = new HashMap<>();
            }

            if (!mappedOmrsProperties.containsAll(propertiesToMatch.keySet()) && matchCriteria.equals(MatchCriteria.ALL)) {
                // If there is some property we are being asked to search but it has no mapping,
                // ensure we will get no results
                filter = SearchFilter.NONE;
            } else {

                // Otherwise go through the properties themselves and figure out what results we should filter
                boolean allValuesAreUnequal = true;
                for (Map.Entry<String, InstancePropertyValue> entry : propertiesToMatch.entrySet()) {
                    String omrsPropertyName = entry.getKey();
                    if (mapping.isOmrsPropertyLiteralMapped(omrsPropertyName)) {
                        Object literalValue = mapping.getOmrsPropertyLiteralValue(omrsPropertyName);
                        boolean valuesAreEqual = IGCRepositoryHelper.equivalentValues(literalValue, entry.getValue());
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
                if (allValuesAreUnequal && matchCriteria.equals(MatchCriteria.NONE)) {
                    filter = SearchFilter.ALL;
                }

            }

        }

        return filter;

    }

}
