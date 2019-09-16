/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "AssetZoneMembership" classification.
 */
public class AssetZoneMembershipMapper_Database extends AssetZoneMembershipMapper {

    private static class Singleton {
        private static final AssetZoneMembershipMapper_Database INSTANCE = new AssetZoneMembershipMapper_Database();
    }
    public static AssetZoneMembershipMapper_Database getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private AssetZoneMembershipMapper_Database() {
        super(
                "database",
                null,
                "Database",
                "AssetZoneMembership"
        );
    }

}
