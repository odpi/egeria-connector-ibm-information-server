/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for interacting with 'ds_link' information via IGC's REST API.
 */
public class DSLink {

    private static final List<String> SEARCH_PROPERTIES = createSearchProperties();

    private static List<String> createSearchProperties() {
        ArrayList<String> searchProperties = new ArrayList<>();
        searchProperties.add("stage_columns");
        searchProperties.add("input_stages");
        searchProperties.add("output_stages");
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
