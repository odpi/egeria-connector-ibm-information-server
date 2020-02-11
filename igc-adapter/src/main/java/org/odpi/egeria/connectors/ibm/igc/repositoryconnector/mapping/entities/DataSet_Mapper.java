/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

/**
 * Defines the common mappings to the OMRS "DataSet" entity.
 */
public class DataSet_Mapper extends Asset_Mapper {

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
