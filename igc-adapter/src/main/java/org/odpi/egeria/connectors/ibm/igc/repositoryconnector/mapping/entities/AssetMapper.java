/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the common mappings to the OMRS "Asset" entity.
 */
public class AssetMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final AssetMapper INSTANCE = new AssetMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "Asset"
        );
    }
    public static AssetMapper getInstance(IGCVersionEnum version) {
        return AssetMapper.Singleton.INSTANCE;
    }

    protected AssetMapper(String igcAssetTypeName,
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
