/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Link;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Stage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;

/**
 * Utility class for interacting with 'ds_stage' information via IGC's REST API.
 */
class DataStageStage {

    /**
     * Check whether the provided stage is an input stage (true) or not (false).
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param stage the stage to check
     * @return boolean
     */
    static boolean isInputStage(IGCRestClient igcRestClient, Stage stage) {
        ItemList<Link> inputLinks = stage.getInputLinks();
        if (inputLinks == null) {
            populateWithLinkDetails(igcRestClient, stage);
            inputLinks = stage.getInputLinks();
        }
        return inputLinks != null && inputLinks.getPaging().getNumTotal() == 0;
    }

    /**
     * Check whether the provided stage is an output stage (true) or not (false).
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param stage the stage to check
     * @return boolean
     */
    static boolean isOutputStage(IGCRestClient igcRestClient, Stage stage) {
        ItemList<Link> outputLinks = stage.getOutputLinks();
        if (outputLinks == null) {
            populateWithLinkDetails(igcRestClient, stage);
            outputLinks = stage.getOutputLinks();
        }
        return outputLinks != null && outputLinks.getPaging().getNumTotal() == 0;
    }

    /**
     * Populate the link details on the provided stage.
     *
     * @param igcRestClient connectivity to the IGC environment
     * @param stage the stage for which to populate link details
     */
    private static void populateWithLinkDetails(IGCRestClient igcRestClient, Stage stage) {
        IGCSearch igcSearch = new IGCSearch("stage");
        igcSearch.addProperties(DataStageConstants.getLinkSearchProperties());
        IGCSearchCondition byId = new IGCSearchCondition("_id", "=", stage.getId());
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(byId);
        igcSearch.addConditions(conditionSet);
        ItemList<Stage> foundList = igcRestClient.search(igcSearch);
        Stage found = null;
        if (foundList != null && !foundList.getItems().isEmpty()) {
            found = foundList.getItems().get(0);
        }
        if (found != null) {
            ItemList<Link> inputs = found.getInputLinks();
            inputs.getAllPages(igcRestClient);
            ItemList<Link> outputs = found.getOutputLinks();
            outputs.getAllPages(igcRestClient);
            stage.setInputLinks(inputs);
            stage.setOutputLinks(outputs);
        } else {
            stage.setInputLinks(new ItemList<>());
            stage.setOutputLinks(new ItemList<>());
        }
    }

}
