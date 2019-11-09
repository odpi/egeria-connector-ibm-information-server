/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for interacting with 'ds_stage' information via IGC's REST API.
 */
public class DSStage {

    private static final List<String> SEARCH_PROPERTIES = createSearchProperties();

    private static List<String> createSearchProperties() {
        ArrayList<String> searchProperties = new ArrayList<>();
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

    /**
     * Retrieve a list of the search properties to use when querying via the IGC REST API.
     *
     * @return {@code List<String>}
     */
    public static List<String> getSearchProperties() { return SEARCH_PROPERTIES; }

    /**
     * Check whether the provided stage is an input stage (true) or not (false).
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param stage the stage to check
     * @return boolean
     */
    static boolean isInputStage(IGCRestClient igcRestClient, Reference stage) {
        ItemList<Reference> inputLinks = getLinkDetails(igcRestClient, stage, "input_links");
        return inputLinks != null && inputLinks.getPaging().getNumTotal() == 0;
    }

    /**
     * Check whether the provided stage is an output stage (true) or not (false).
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param stage the stage to check
     * @return boolean
     */
    static boolean isOutputStage(IGCRestClient igcRestClient, Reference stage) {
        ItemList<Reference> outputLinks = getLinkDetails(igcRestClient, stage, "output_links");
        return outputLinks != null && outputLinks.getPaging().getNumTotal() == 0;
    }

    /**
     * Retrieve the requested link details, including looking them up against the IGC environment if they are not
     * already populated on the object.
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param stage the stage for which to retrieve link details
     * @param propertyName the property name of the link relationships to retrieve
     * @return {@code ItemList<Reference>}
     */
    private static ItemList<Reference> getLinkDetails(IGCRestClient igcRestClient, Reference stage, String propertyName) {
        ItemList<Reference> links = (ItemList<Reference>) igcRestClient.getPropertyByName(stage, propertyName);
        if (links == null) {
            IGCSearch igcSearch = new IGCSearch("stage");
            igcSearch.addProperties(DSStage.getSearchProperties());
            IGCSearchCondition condition = new IGCSearchCondition("_id", "=", stage.getId());
            IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
            igcSearch.addConditions(conditionSet);
            ItemList<Reference> stages = igcRestClient.search(igcSearch);
            stage = stages.getItems().get(0);
            links = (ItemList<Reference>) igcRestClient.getPropertyByName(stage, propertyName);
        }
        return links;
    }

}
