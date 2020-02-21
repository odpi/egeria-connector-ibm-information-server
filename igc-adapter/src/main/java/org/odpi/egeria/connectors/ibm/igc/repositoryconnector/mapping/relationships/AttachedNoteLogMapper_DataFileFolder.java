/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "data_file_folder" assets.
 */
public class AttachedNoteLogMapper_DataFileFolder extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DataFileFolder INSTANCE = new AttachedNoteLogMapper_DataFileFolder();
    }
    public static AttachedNoteLogMapper_DataFileFolder getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DataFileFolder.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DataFileFolder() {
        super("data_file_folder");
    }

}
