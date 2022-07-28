/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the common mappings to the OMRS "DataSet" entity.
 */
public class DataSetMapper extends AssetMapper {

    private static class Singleton {
        private static final DataSetMapper INSTANCE = new DataSetMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "DataSet"
        );
    }
    public static DataSetMapper getInstance(IGCVersionEnum version) {
        return DataSetMapper.Singleton.INSTANCE;
    }

    protected DataSetMapper(String igcAssetTypeName,
                            String igcAssetTypeDisplayName,
                            String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName
        );

        // Mapping to default
        addLiteralPropertyMapping("formula", null);

    }

}
