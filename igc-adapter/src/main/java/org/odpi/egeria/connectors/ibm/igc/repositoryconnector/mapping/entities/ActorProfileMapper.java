/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the mapping to the OMRS "ActorProfile" entity.
 */
public class ActorProfileMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final ActorProfileMapper INSTANCE = new ActorProfileMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "ActorProfile"
        );
    }
    public static ActorProfileMapper getInstance(IGCVersionEnum version) {
        return ActorProfileMapper.Singleton.INSTANCE;
    }

    protected ActorProfileMapper(String igcAssetTypeName,
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

        addLiteralPropertyMapping("description", null);

    }

}
