/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.FolderHierarchyMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionToAssetMapper_FileFolder;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.NestedFileMapper;

/**
 * Defines the mapping to the OMRS "FileFolder" entity.
 */
public class FileFolderMapper extends ReferenceableMapper {

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

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");

        // The classes to use for mapping any relationships
        addRelationshipMapper(FolderHierarchyMapper.getInstance());
        addRelationshipMapper(NestedFileMapper.getInstance());
        addRelationshipMapper(ConnectionToAssetMapper_FileFolder.getInstance());

    }

}
