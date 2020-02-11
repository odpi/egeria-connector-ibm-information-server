/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

/**
 * Defines the common mappings to the OMRS "Asset" entity.
 */
public class Asset_Mapper extends ReferenceableMapper {

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
