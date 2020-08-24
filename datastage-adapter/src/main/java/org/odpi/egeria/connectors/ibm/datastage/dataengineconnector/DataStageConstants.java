/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to capture all the properties that should be included when searching for various lineage-relevant
 * types via the IGC REST API.
 */
public class DataStageConstants {

    private static final List<String> STAGE_VARIABLE_SEARCH_PROPERTIES = createStageVariableSearchProperties();
    private static final List<String> STAGE_COLUMN_SEARCH_PROPERTIES = createStageColumnSearchProperties();
    private static final List<String> LINK_SEARCH_PROPERTIES = createLinkSearchProperties();
    private static final List<String> STAGE_SEARCH_PROPERTIES = createStageSearchProperties();
    private static final List<String> JOB_SEARCH_PROPERTIES = createJobSearchProperties();
    private static final List<String> DATA_FIELD_SEARCH_PROPERTIES = createDataFieldSearchProperties();

    public static final String NAME = "name";
    public static final String SHORT_DESCRIPTION = "short_description";
    private static final String LONG_DESCRIPTION = "long_description";
    private static final String TYPE = "type";
    private static final String LENGTH = "length";
    private static final String MINIMUM_LENGTH = "minimum_length";
    private static final String ALLOWS_NULL_VALUES = "allows_null_values";
    private static final String READS_FROM_DESIGN = "reads_from_(design)";
    private static final String WRITES_TO_DESIGN = "writes_to_(design)";
    private static final String READ_BY_DESIGN = "read_by_(design)";
    private static final String WRITTEN_BY_DESIGN = "written_by_(design)";

    private DataStageConstants() {
        // Do nothing...
    }

    private static List<String> createJobSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add(SHORT_DESCRIPTION);
        searchProperties.add(LONG_DESCRIPTION);
        searchProperties.add("references_local_or_shared_containers");
        searchProperties.add("sequenced_by_jobs");
        searchProperties.add(TYPE);
        searchProperties.add(READS_FROM_DESIGN);
        searchProperties.add(WRITES_TO_DESIGN);
        searchProperties.add(IGCRestConstants.MOD_CREATED_BY);
        searchProperties.add(IGCRestConstants.MOD_CREATED_ON);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_BY);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_ON);
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createStageSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add(SHORT_DESCRIPTION);
        searchProperties.add(LONG_DESCRIPTION);
        searchProperties.add("shared_or_local_container");
        searchProperties.add(TYPE);
        searchProperties.add("type_definition");
        searchProperties.add("references_container");
        searchProperties.add("runs_sequences_jobs");
        searchProperties.add("input_links");
        searchProperties.add("output_links");
        searchProperties.add("previous_stages");
        searchProperties.add("next_stages");
        searchProperties.add(READS_FROM_DESIGN);
        searchProperties.add(WRITES_TO_DESIGN);
        searchProperties.add(IGCRestConstants.MOD_CREATED_BY);
        searchProperties.add(IGCRestConstants.MOD_CREATED_ON);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_BY);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_ON);
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createLinkSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add("stage_columns");
        searchProperties.add("input_stages");
        searchProperties.add("output_stages");
        searchProperties.add(IGCRestConstants.MOD_CREATED_BY);
        searchProperties.add(IGCRestConstants.MOD_CREATED_ON);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_BY);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_ON);
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createStageVariableSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add(SHORT_DESCRIPTION);
        searchProperties.add(LONG_DESCRIPTION);
        searchProperties.add(LENGTH);
        searchProperties.add(MINIMUM_LENGTH);
        searchProperties.add("odbc_type");
        searchProperties.add("expression");
        searchProperties.add("previous_stage_columns");
        searchProperties.add("next_stage_columns");
        searchProperties.add(IGCRestConstants.MOD_CREATED_BY);
        searchProperties.add(IGCRestConstants.MOD_CREATED_ON);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_BY);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_ON);
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createStageColumnSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add(SHORT_DESCRIPTION);
        searchProperties.add(LONG_DESCRIPTION);
        searchProperties.add(ALLOWS_NULL_VALUES);
        searchProperties.add(LENGTH);
        searchProperties.add(MINIMUM_LENGTH);
        searchProperties.add("odbc_type");
        searchProperties.add("key");
        searchProperties.add("expression");
        searchProperties.add("previous_stage_columns");
        searchProperties.add("next_stage_columns");
        searchProperties.add(READS_FROM_DESIGN);
        searchProperties.add(WRITES_TO_DESIGN);
        searchProperties.add(IGCRestConstants.MOD_CREATED_BY);
        searchProperties.add(IGCRestConstants.MOD_CREATED_ON);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_BY);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_ON);
        return Collections.unmodifiableList(searchProperties);
    }

    private static List<String> createDataFieldSearchProperties() {
        List<String> searchProperties = new ArrayList<>();
        searchProperties.add(SHORT_DESCRIPTION);
        searchProperties.add(LONG_DESCRIPTION);
        searchProperties.add("data_type");
        searchProperties.add("default_value");
        searchProperties.add(LENGTH);
        searchProperties.add(MINIMUM_LENGTH);
        searchProperties.add("fraction");
        searchProperties.add("position");
        searchProperties.add(ALLOWS_NULL_VALUES);
        searchProperties.add("unique");
        searchProperties.add(READ_BY_DESIGN);
        searchProperties.add(WRITTEN_BY_DESIGN);
        searchProperties.add(IGCRestConstants.MOD_CREATED_BY);
        searchProperties.add(IGCRestConstants.MOD_CREATED_ON);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_BY);
        searchProperties.add(IGCRestConstants.MOD_MODIFIED_ON);
        return Collections.unmodifiableList(searchProperties);
    }

    /**
     * Retrieve a list of the search properties to use when querying stage variables via the IGC REST API.
     * @return {@code List<String>}
     */
    public static List<String> getStageVariableSearchProperties() { return STAGE_VARIABLE_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying stage columns via the IGC REST API.
     * @return {@code List<String>}
     */
    public static List<String> getStageColumnSearchProperties() { return STAGE_COLUMN_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying links via the IGC REST API.
     * @return {@code List<String>}
     */
    public static List<String> getLinkSearchProperties() { return LINK_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying stages via the IGC REST API.
     * @return {@code List<String>}
     */
    public static List<String> getStageSearchProperties() { return STAGE_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying jobs via the IGC REST API.
     * @return {@code List<String>}
     */
    public static List<String> getJobSearchProperties() { return JOB_SEARCH_PROPERTIES; }

    /**
     * Retrieve a list of the search properties to use when querying data fields (file fields or database columns)
     * via the IGC REST API.
     * @return {@code List<String>}
     */
    public static List<String> getDataFieldSearchProperties() { return DATA_FIELD_SEARCH_PROPERTIES; }

}
