/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

/**
 * Defines the mapping to the OMRS "ActorProfile" entity.
 */
public class ActorProfile_Mapper extends ReferenceableMapper {

    protected ActorProfile_Mapper(String igcAssetTypeName,
                                  String igcAssetTypeDisplayName,
                                  String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                null,
                false
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("principal_id", "name");

    }

}
