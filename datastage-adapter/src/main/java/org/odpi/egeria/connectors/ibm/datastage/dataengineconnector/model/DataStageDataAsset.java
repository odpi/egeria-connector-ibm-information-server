/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
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
        log.debug("Retrieving data field details for {}: {}", storeType, storeRid);

        List<Classificationenabledgroup> fields = null;
        if (!store.isVirtualAsset()) {
            // For non-virtual assets the most efficient way of retrieving this information is via a search (by RID)
            IGCSearch igcSearch = new IGCSearch();
            IGCSearchCondition byParentId = null;
            if (storeType.equals("database_table") || storeType.equals("view")) {
                igcSearch.addType("database_column");
                igcSearch.addProperties(DataStageConstants.getDataFieldSearchProperties());
                byParentId = new IGCSearchCondition("database_table_or_view", "=", storeRid);
            } else if (storeType.equals("data_file_record")) {
                igcSearch.addType("data_file_field");
                igcSearch.addProperties(DataStageConstants.getDataFieldSearchProperties());
                byParentId = new IGCSearchCondition("data_file_record", "=", storeRid);
            } else {
                log.warn("Unknown source / target type -- skipping: {}", store);
            }

            if (byParentId != null) {
                IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(byParentId);
                igcSearch.addConditions(conditionSet);
                ItemList<Classificationenabledgroup> ilFields = igcRestClient.search(igcSearch);
                ilFields.getAllPages(igcRestClient);
                fields = ilFields.getItems();
            }
        } else {
            // For virtual assets, we must retrieve the full object and page through its fields (search by RID is not possible)
            fields = new ArrayList<>();
            Reference virtualStore = igcRestClient.getAssetById(storeRid);
            if (virtualStore instanceof DatabaseTable) {
                DatabaseTable virtualTable = (DatabaseTable) virtualStore;
                fields.addAll(getDataFieldsFromVirtualList(igcRestClient, virtualTable.getDatabaseColumns()));
            } else if (virtualStore instanceof View) {
                View virtualView = (View) virtualStore;
                fields.addAll(getDataFieldsFromVirtualList(igcRestClient, virtualView.getDatabaseColumns()));
            } else if (virtualStore instanceof DataFileRecord) {
                DataFileRecord virtualRecord = (DataFileRecord) virtualStore;
                fields.addAll(getDataFieldsFromVirtualList(igcRestClient, virtualRecord.getDataFileFields()));
            } else {
                log.warn("Unhandled case for type: {}", virtualStore.getType());
            }
        }

        return fields;

    }

    /**
     * Retrieve the full list of virtual fields contained within the provided list of virtual fields.  Note that this
     * should ONLY be used for virtual assets, as it is a very expensive operation.
     *
     * @param igcRestClient connectivity to the IGC environment
     * @param virtualFields the paged list of virtual fields that has been retrieved
     * @param <T> the type of field
     * @return {@code List<Classificationenabledgroup>} containing the full list of fully-detailed virtual fields
     */
    static <T extends Classificationenabledgroup> List<Classificationenabledgroup> getDataFieldsFromVirtualList(
            IGCRestClient igcRestClient,
            ItemList<T> virtualFields) {
        List<Classificationenabledgroup> fullFields = null;
        if (virtualFields != null) {
            fullFields = new ArrayList<>();
            virtualFields.getAllPages(igcRestClient);
            for (Classificationenabledgroup virtualField : virtualFields.getItems()) {
                Classificationenabledgroup fullField = (Classificationenabledgroup) igcRestClient.getAssetById(virtualField.getId());
                fullFields.add(fullField);
            }
        }
        return fullFields == null ? Collections.emptyList() : fullFields;
    }

}
