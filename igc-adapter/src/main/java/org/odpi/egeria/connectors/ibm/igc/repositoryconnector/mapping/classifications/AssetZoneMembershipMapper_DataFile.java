/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "AssetZoneMembership" classification.
 */
public class AssetZoneMembershipMapper_DataFile extends AssetZoneMembershipMapper {

    private static class Singleton {
        private static final AssetZoneMembershipMapper_DataFile INSTANCE = new AssetZoneMembershipMapper_DataFile();
    }
    public static AssetZoneMembershipMapper_DataFile getInstance(IGCVersionEnum version) { return Singleton.INSTANCE; }

    private AssetZoneMembershipMapper_DataFile() {
        super(
                "data_file",
                null,
                "Asset",
                "AssetZoneMembership"
        );
    }

}
