/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the mapping to the OMRS "ActorProfile" entity.
 */
public class ActorProfile_Mapper extends ReferenceableMapper {

    private static class Singleton {
        private static final ActorProfile_Mapper INSTANCE = new ActorProfile_Mapper();
    }
    public static ActorProfile_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ActorProfile_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "ActorProfile"
        );

    }

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
