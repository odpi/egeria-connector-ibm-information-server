/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the common mappings to the OMRS "DataStore" entity.
 */
public class DataStore_Mapper extends Asset_Mapper {

    private static class Singleton {
        private static final DataStore_Mapper INSTANCE = new DataStore_Mapper();
    }
    public static DataStore_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private DataStore_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "DataStore"
        );

    }

    protected DataStore_Mapper(String igcAssetTypeName,
                               String igcAssetTypeDisplayName,
                               String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("created_on", "createTime");
        addSimplePropertyMapping("modified_on", "modifiedTime");

    }

}
