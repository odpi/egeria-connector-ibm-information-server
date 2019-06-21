/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AssetSchemaType" relationship.
 * @see AssetSchemaTypeMapper_DatabaseSchema
 * @see AssetSchemaTypeMapper_FileRecord
 */
public class AssetSchemaTypeMapper extends RelationshipMapping {

    private static class Singleton {
        private static final AssetSchemaTypeMapper INSTANCE = new AssetSchemaTypeMapper();
    }
    public static AssetSchemaTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private AssetSchemaTypeMapper() {
        super(
                "",
                "",
                "",
                "",
                "AssetSchemaType",
                "describesAssets",
                "schema"
        );
        addSubType(AssetSchemaTypeMapper_DatabaseSchema.getInstance(null));
        addSubType(AssetSchemaTypeMapper_FileRecord.getInstance(null));
    }

}
