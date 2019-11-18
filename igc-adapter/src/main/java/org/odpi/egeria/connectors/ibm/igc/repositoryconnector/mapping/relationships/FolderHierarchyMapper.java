/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "FolderHierarchy" relationship for IGC "data_file_folder" assets.
 */
public class FolderHierarchyMapper extends RelationshipMapping {

    private static class Singleton {
        private static final FolderHierarchyMapper INSTANCE = new FolderHierarchyMapper();
    }
    public static FolderHierarchyMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private FolderHierarchyMapper() {
        super(
                "data_file_folder",
                "data_file_folder",
                "data_file_folders",
                "parent_folder",
                "FolderHierarchy",
                "parentFolder",
                "nestedFolder"
        );
        setContainedType(ContainedType.TWO);
    }

}
