/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the mapping to the OMRS "TabularColumn" entity.
 */
public class TabularColumnMapper extends SchemaAttributeMapper {

    private static class Singleton {
        private static final TabularColumnMapper INSTANCE = new TabularColumnMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "TabularColumn"
        );
    }
    public static TabularColumnMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TabularColumnMapper(String igcAssetTypeName,
                                  String igcAssetTypeDisplayName,
                                  String omrsEntityTypeName) {

        // Pass-through to the superclass's constructor to initialise the Mapper
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName
        );

    }

}
