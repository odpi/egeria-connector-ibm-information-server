/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "AssetZoneMembership" classification.
 */
public class AssetZoneMembershipMapper_DeployedDatabaseSchema extends AssetZoneMembershipMapper {

    private static class Singleton {
        private static final AssetZoneMembershipMapper_DeployedDatabaseSchema INSTANCE = new AssetZoneMembershipMapper_DeployedDatabaseSchema();
    }
    public static AssetZoneMembershipMapper_DeployedDatabaseSchema getInstance(IGCVersionEnum version) { return Singleton.INSTANCE; }

    private AssetZoneMembershipMapper_DeployedDatabaseSchema() {
        super(
                "database_schema",
                null,
                "Asset",
                "AssetZoneMembership"
        );
    }

}
