/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "data_file_record" assets.
 */
public class AttachedNoteLogMapper_DataFileRecord extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DataFileRecord INSTANCE = new AttachedNoteLogMapper_DataFileRecord();
    }
    public static AttachedNoteLogMapper_DataFileRecord getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DataFileRecord.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DataFileRecord() {
        super("data_file_record");
    }

}
