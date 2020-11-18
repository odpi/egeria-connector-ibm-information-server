/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.KeyPatternMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EnumPropertyValue;

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

        // The list of properties that should be mapped
        addSimplePropertyMapping("_id", "identifier");
        EnumPropertyValue keyPattern = KeyPatternMapper.getInstance().getEnumMappingByIgcValue("LocalKey");
        addLiteralPropertyMapping("keyPattern", keyPattern);

    }

}
