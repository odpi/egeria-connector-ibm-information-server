/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Provides the base class for all metadata instance mappings.
 */
public abstract class InstanceMapping {

    private static final Logger log = LoggerFactory.getLogger(InstanceMapping.class);

    private Map<String, Object> literalOmrsPropertyMapping;

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

}
