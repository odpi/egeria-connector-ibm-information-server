/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the common mappings to the OMRS "Asset" entity.
 */
public class Asset_Mapper extends ReferenceableMapper {

    private static class Singleton {
        private static final Asset_Mapper INSTANCE = new Asset_Mapper();
    }
    public static Asset_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private Asset_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "Asset"
        );

    }

    protected Asset_Mapper(String igcAssetTypeName,
                           String igcAssetTypeDisplayName,
                           String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");

        // Deprecated / moved attributes will be null'd
        addLiteralPropertyMapping("owner", null);
        addLiteralPropertyMapping("ownerType", null);
        addLiteralPropertyMapping("zoneMembership", null);
        addLiteralPropertyMapping("latestChange", null);

    }

}
