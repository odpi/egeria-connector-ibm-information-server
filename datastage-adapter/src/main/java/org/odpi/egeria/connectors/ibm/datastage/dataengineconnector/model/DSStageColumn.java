/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for interacting with 'ds_stage_column' information via IGC's REST API.
 */
public class DSStageColumn {

    private static final List<String> SEARCH_PROPERTIES = createSearchProperties();

    private static List<String> createSearchProperties() {
        ArrayList<String> searchProperties = new ArrayList<>();
        searchProperties.add("short_description");
        searchProperties.add("long_description");
        searchProperties.add("allows_null_values");
        searchProperties.add("length");
        searchProperties.add("minimum_length");
        searchProperties.add("odbc_type");
        searchProperties.add("key");
        searchProperties.add("expression");
        searchProperties.add("previous_stage_columns");
        searchProperties.add("next_stage_columns");
        searchProperties.add("reads_from_(design)");
        searchProperties.add("writes_to_(design)");
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
    public static List<String> getSearchProperties() { return SEARCH_PROPERTIES; }

}
