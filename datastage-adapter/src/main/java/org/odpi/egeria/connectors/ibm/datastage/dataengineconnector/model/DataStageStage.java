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

import java.util.Map;

/**
 * Utility class for interacting with 'ds_stage' information via IGC's REST API.
 */
class DataStageStage {

    /**
     * Check whether the provided stage is an input stage (true) or not (false).
     *
     * @param stage the stage to check
     * @return boolean
     */
    static boolean isInputStage(Stage stage) {
        ItemList<Link> inputLinks = stage.getInputLinks();
        return inputLinks != null && inputLinks.getPaging().getNumTotal() == 0;
    }

    /**
     * Check whether the provided stage is an output stage (true) or not (false).
     *
     * @param stage the stage to check
     * @return boolean
     */
    static boolean isOutputStage(Stage stage) {
        ItemList<Link> outputLinks = stage.getOutputLinks();
        return outputLinks != null && outputLinks.getPaging().getNumTotal() == 0;
    }

}
