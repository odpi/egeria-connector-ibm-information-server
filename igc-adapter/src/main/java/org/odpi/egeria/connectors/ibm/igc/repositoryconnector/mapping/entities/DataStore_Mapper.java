/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

/**
 * Defines the common mappings to the OMRS "DataStore" entity.
 */
public class DataStore_Mapper extends Asset_Mapper {

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
