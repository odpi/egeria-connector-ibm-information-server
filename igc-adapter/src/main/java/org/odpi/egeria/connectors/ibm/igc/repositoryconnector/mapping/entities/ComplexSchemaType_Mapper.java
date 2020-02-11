/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

/**
 * Defines the mapping to the OMRS "ComplexSchemaType" entity.
 */
public class ComplexSchemaType_Mapper extends SchemaType_Mapper {

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
