/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "AssetZoneMembership" classification.
 */
public class AssetZoneMembershipMapper_FileFolder extends AssetZoneMembershipMapper {

    private static class Singleton {
        private static final AssetZoneMembershipMapper_FileFolder INSTANCE = new AssetZoneMembershipMapper_FileFolder();
    }
    public static AssetZoneMembershipMapper_FileFolder getInstance(IGCVersionEnum version) { return Singleton.INSTANCE; }

    protected AssetZoneMembershipMapper_FileFolder() {
        super(
                "data_file_folder",
                null,
                "FileFolder",
                "AssetZoneMembership"
        );
    }

}
