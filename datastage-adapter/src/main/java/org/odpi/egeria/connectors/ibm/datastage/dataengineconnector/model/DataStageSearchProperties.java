/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to capture all the properties that should be included when searching for various lineage-relevant
 * types via the IGC REST API.
 */
class DataStageSearchProperties {

    private static final List<String> STAGE_COLUMN_SEARCH_PROPERTIES = createStageColumnSearchProperties();
    private static final List<String> LINK_SEARCH_PROPERTIES = createLinkSearchProperties();
    private static final List<String> STAGE_SEARCH_PROPERTIES = createStageSearchProperties();
    private static final List<String> JOB_SEARCH_PROPERTIES = createJobSearchProperties();
    private static final List<String> DATA_FILE_FIELD_SEARCH_PROPERTIES = createDataFileFieldSearchProperties();
    private static final List<String> DATABASE_COLUMN_SEARCH_PROPERTIES = createDatabaseColumnSearchProperties();

    private static List<String> createJobSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add("short_description");
        searchProperties.add("long_description");
        searchProperties.add("references_local_or_shared_containers");
        searchProperties.add("type");
        searchProperties.add("reads_from_(design)");
        searchProperties.add("writes_to_(design)");
        searchProperties.add("created_by");
        searchProperties.add("created_on");
        searchProperties.add("modified_by");
        searchProperties.add("modified_on");
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createStageSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add("short_description");
        searchProperties.add("long_description");
        searchProperties.add("shared_or_local_container");
        searchProperties.add("type");
        searchProperties.add("type_definition");
        searchProperties.add("references_container");
        searchProperties.add("runs_sequences_jobs");
        searchProperties.add("input_links");
        searchProperties.add("output_links");
        searchProperties.add("previous_stages");
        searchProperties.add("next_stages");
        searchProperties.add("reads_from_(design)");
        searchProperties.add("writes_to_(design)");
        searchProperties.add("created_by");
        searchProperties.add("created_on");
        searchProperties.add("modified_by");
        searchProperties.add("modified_on");
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createLinkSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add("stage_columns");
        searchProperties.add("input_stages");
        searchProperties.add("output_stages");
        searchProperties.add("created_by");
        searchProperties.add("created_on");
        searchProperties.add("modified_by");
        searchProperties.add("modified_on");
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createStageColumnSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
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

    private static List<String> createDataFileFieldSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
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

    private static List<String> createDatabaseColumnSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
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
     * Retrieve a list of the search properties to use when querying stage columns via the IGC REST API.
     * @return {@code List<String>}
     */
    static List<String> getStageColumnSearchProperties() { return STAGE_COLUMN_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying links via the IGC REST API.
     * @return {@code List<String>}
     */
    static List<String> getLinkSearchProperties() { return LINK_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying stages via the IGC REST API.
     * @return {@code List<String>}
     */
    static List<String> getStageSearchProperties() { return STAGE_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying jobs via the IGC REST API.
     * @return {@code List<String>}
     */
    static List<String> getJobSearchProperties() { return JOB_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying data file fields via the IGC REST API.
     * @return {@code List<String>}
     */
    static List<String> getDataFileFieldSearchProperties() { return DATA_FILE_FIELD_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying database columns via the IGC REST API.
     * @return {@code List<String>}
     */
    static List<String> getDatabaseColumnSearchProperties() { return DATABASE_COLUMN_SEARCH_PROPERTIES; }

}
