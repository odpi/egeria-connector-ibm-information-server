/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "data_file" assets.
 */
public class AttachedNoteLogMapper_DataFile extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DataFile INSTANCE = new AttachedNoteLogMapper_DataFile();
    }
    public static AttachedNoteLogMapper_DataFile getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DataFile.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DataFile() {
        super("data_file");
    }

}
