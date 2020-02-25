/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AssetZoneMembershipMapper_FileFolder;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.FolderHierarchyMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionToAssetMapper_FileFolder;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.NestedFileMapper;

/**
 * Defines the mapping to the OMRS "FileFolder" entity.
 */
public class FileFolderMapper extends DataStore_Mapper {

    private static class Singleton {
        private static final FileFolderMapper INSTANCE = new FileFolderMapper();
    }
    public static FileFolderMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private FileFolderMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file_folder",
                "Data File Folder",
                "FileFolder"
        );

        // The classes to use for mapping any relationships
        addRelationshipMapper(FolderHierarchyMapper.getInstance(null));
        addRelationshipMapper(NestedFileMapper.getInstance(null));
        addRelationshipMapper(ConnectionToAssetMapper_FileFolder.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(AssetZoneMembershipMapper_FileFolder.getInstance(null));

    }

}
