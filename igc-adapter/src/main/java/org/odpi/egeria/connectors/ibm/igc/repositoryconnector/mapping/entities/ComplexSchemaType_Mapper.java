/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the mapping to the OMRS "ComplexSchemaType" entity.
 */
public class ComplexSchemaType_Mapper extends SchemaType_Mapper {

    private static class Singleton {
        private static final ComplexSchemaType_Mapper INSTANCE = new ComplexSchemaType_Mapper();
    }
    public static ComplexSchemaType_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ComplexSchemaType_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "ComplexSchemaType",
                null
        );

    }

    protected ComplexSchemaType_Mapper(String igcAssetTypeName,
                                       String igcAssetTypeDisplayName,
                                       String omrsEntityTypeName,
                                       String prefix) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                prefix
        );

    }

}
