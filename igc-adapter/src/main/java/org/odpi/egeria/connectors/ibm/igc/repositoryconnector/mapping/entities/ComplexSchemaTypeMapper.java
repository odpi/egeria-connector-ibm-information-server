/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the mapping to the OMRS "ComplexSchemaType" entity.
 */
public class ComplexSchemaTypeMapper extends SchemaTypeMapper {

    private static class Singleton {
        private static final ComplexSchemaTypeMapper INSTANCE = new ComplexSchemaTypeMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "ComplexSchemaType",
                null
        );
    }
    public static ComplexSchemaTypeMapper getInstance(IGCVersionEnum version) {
        return ComplexSchemaTypeMapper.Singleton.INSTANCE;
    }

    protected ComplexSchemaTypeMapper(String igcAssetTypeName,
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
