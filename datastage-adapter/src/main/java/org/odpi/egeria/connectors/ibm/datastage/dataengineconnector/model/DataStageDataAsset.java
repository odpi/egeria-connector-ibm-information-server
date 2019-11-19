/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classificationenabledgroup;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class to represent and interact with any data assets used within DataStage jobs.
 */
public class DataStageDataAsset {

    private static final Logger log = LoggerFactory.getLogger(DataStageDataAsset.class);

    /**
     * Retrieve a listing of all of the fields contained within the provided data store.
     *
     * @param igcRestClient connectivity to the IGC environment
     * @param store the IGC object for the data store (database_table, view, or data_file_record)
     * @return {@code List<Classificationenabledgroup>}
     */
    static List<Classificationenabledgroup> getDataFieldsForStore(IGCRestClient igcRestClient, InformationAsset store) {

        String storeRid = store.getId();
        String storeType = store.getType();
        if (log.isDebugEnabled()) { log.debug("Retrieving data field details for {}: {}", storeType, storeRid); }

        IGCSearch igcSearch = new IGCSearch();
        IGCSearchCondition byParentId = null;
        if (storeType.equals("database_table") || storeType.equals("view")) {
            igcSearch.addType("database_column");
            igcSearch.addProperties(DataStageSearchProperties.getDatabaseColumnSearchProperties());
            byParentId = new IGCSearchCondition("database_table_or_view", "=", storeRid);
        } else if (storeType.equals("data_file_record")) {
            igcSearch.addType("data_file_field");
            igcSearch.addProperties(DataStageSearchProperties.getDataFileFieldSearchProperties());
            byParentId = new IGCSearchCondition("data_file_record", "=", storeRid);
        } else {
            if (log.isWarnEnabled()) { log.warn("Unknown source / target type -- skipping: {}", store); }
        }

        List<Classificationenabledgroup> fields = null;
        if (byParentId != null) {
            IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(byParentId);
            igcSearch.addConditions(conditionSet);
            ItemList<Classificationenabledgroup> ilFields = igcRestClient.search(igcSearch);
            ilFields.getAllPages(igcRestClient);
            fields = ilFields.getItems();
        }
        return fields;

    }

    /**
     * Indicates whether the provided Repository ID (RID) represents a virtual asset (true) or a full asset (false).
     *
     * @param rid the Repository ID (RID) of the asset to check
     * @return boolean
     */
    public static boolean isVirtualAsset(String rid) {
        return rid.startsWith("extern:");
    }

}
