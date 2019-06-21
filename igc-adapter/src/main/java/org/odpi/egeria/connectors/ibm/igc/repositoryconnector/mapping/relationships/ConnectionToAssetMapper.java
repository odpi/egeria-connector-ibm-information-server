/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "ConnectionToAsset" relationship.
 * @see ConnectionToAssetMapper_Database
 * @see ConnectionToAssetMapper_FileFolder
 */
public class ConnectionToAssetMapper extends RelationshipMapping {

    private static class Singleton {
        private static final ConnectionToAssetMapper INSTANCE = new ConnectionToAssetMapper();
    }
    public static ConnectionToAssetMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ConnectionToAssetMapper() {
        super(
                "",
                "",
                "",
                "",
                "ConnectionToAsset",
                "connections",
                "asset"
        );
        addSubType(ConnectionToAssetMapper_Database.getInstance(null));
        addSubType(ConnectionToAssetMapper_FileFolder.getInstance(null));
        addLiteralPropertyMapping("assetSummary", null);
    }

}
