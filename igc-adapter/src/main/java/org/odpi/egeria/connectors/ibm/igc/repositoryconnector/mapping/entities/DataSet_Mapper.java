/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the common mappings to the OMRS "DataSet" entity.
 */
public class DataSet_Mapper extends Asset_Mapper {

    private static class Singleton {
        private static final DataSet_Mapper INSTANCE = new DataSet_Mapper();
    }
    public static DataSet_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private DataSet_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "DataSet"
        );

    }

    protected DataSet_Mapper(String igcAssetTypeName,
                             String igcAssetTypeDisplayName,
                             String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName
        );

    }

}
