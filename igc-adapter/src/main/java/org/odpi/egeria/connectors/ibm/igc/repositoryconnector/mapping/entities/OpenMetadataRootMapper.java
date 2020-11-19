/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Defines the common mappings to the OMRS "OpenMetadataRoot" entity.
 */
public class OpenMetadataRootMapper extends EntityMapping {

    private static class Singleton {
        private static final OpenMetadataRootMapper INSTANCE = new OpenMetadataRootMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "OpenMetadataRoot",
                null
        );
    }
    public static OpenMetadataRootMapper getInstance(IGCVersionEnum version) {
        return OpenMetadataRootMapper.Singleton.INSTANCE;
    }

    protected OpenMetadataRootMapper(String igcAssetTypeName,
                                     String igcAssetTypeDisplayName,
                                     String omrsEntityTypeName,
                                     String igcRidPrefix) {
        this(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                igcRidPrefix,
                true
        );
    }

    protected OpenMetadataRootMapper(String igcAssetTypeName,
                                     String igcAssetTypeDisplayName,
                                     String omrsEntityTypeName,
                                     String igcRidPrefix,
                                     boolean searchable) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                igcRidPrefix,
                searchable
        );

    }

}
