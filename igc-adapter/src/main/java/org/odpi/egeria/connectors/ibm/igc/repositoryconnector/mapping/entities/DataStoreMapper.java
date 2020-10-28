/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the common mappings to the OMRS "DataStore" entity.
 */
public class DataStoreMapper extends AssetMapper {

    private static class Singleton {
        private static final DataStoreMapper INSTANCE = new DataStoreMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "DataStore"
        );
    }
    public static DataStoreMapper getInstance(IGCVersionEnum version) {
        return DataStoreMapper.Singleton.INSTANCE;
    }

    protected DataStoreMapper(String igcAssetTypeName,
                              String igcAssetTypeDisplayName,
                              String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("created_on", "storeCreateTime");
        addSimplePropertyMapping("modified_on", "storeUpdateTime");

        // Add literal mappings for deprecated properties (so we still pass type verification even during a
        // patching process)
        addLiteralPropertyMapping("createTime", null);
        addLiteralPropertyMapping("modifiedTime", null);

    }

}
