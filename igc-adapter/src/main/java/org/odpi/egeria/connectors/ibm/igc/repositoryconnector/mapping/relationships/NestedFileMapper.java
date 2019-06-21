/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "NestedFile" relationship between IGC "data_file_folder" and "data_file" assets.
 */
public class NestedFileMapper extends RelationshipMapping {

    private static class Singleton {
        private static final NestedFileMapper INSTANCE = new NestedFileMapper();
    }
    public static NestedFileMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private NestedFileMapper() {
        super(
                "data_file_folder",
                "data_file",
                "data_files",
                "parent_folder",
                "NestedFile",
                "homeFolder",
                "nestedFiles"
        );
    }

}
