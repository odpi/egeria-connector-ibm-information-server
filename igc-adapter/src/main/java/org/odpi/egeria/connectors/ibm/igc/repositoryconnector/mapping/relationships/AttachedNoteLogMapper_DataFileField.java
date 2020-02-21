/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "data_file_field" assets.
 */
public class AttachedNoteLogMapper_DataFileField extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DataFileField INSTANCE = new AttachedNoteLogMapper_DataFileField();
    }
    public static AttachedNoteLogMapper_DataFileField getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DataFileField.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DataFileField() {
        super("data_file_field");
    }

}
