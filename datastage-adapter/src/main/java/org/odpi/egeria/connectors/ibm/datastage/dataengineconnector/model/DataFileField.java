/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for interacting with 'data_file_field' information via IGC's REST API.
 */
public class DataFileField {

    private static final List<String> SEARCH_PROPERTIES = createSearchProperties();

    private static List<String> createSearchProperties() {
        ArrayList<String> searchProperties = new ArrayList<>();
        searchProperties.add("short_description");
        searchProperties.add("long_description");
        searchProperties.add("data_type");
        searchProperties.add("default_value");
        searchProperties.add("length");
        searchProperties.add("minimum_length");
        searchProperties.add("fraction");
        searchProperties.add("position");
        searchProperties.add("allows_null_values");
        searchProperties.add("unique");
        searchProperties.add("read_by_(design)");
        searchProperties.add("written_by_(design)");
        searchProperties.add("created_by");
        searchProperties.add("created_on");
        searchProperties.add("modified_by");
        searchProperties.add("modified_on");
        return Collections.unmodifiableList(searchProperties);
    }

    /**
     * Retrieve a list of the search properties to use when querying via the IGC REST API.
     *
     * @return {@code List<String>}
     */
    public static final List<String> getSearchProperties() { return SEARCH_PROPERTIES; }

}
