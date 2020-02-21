/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "data_class" assets.
 */
public class AttachedNoteLogMapper_DataClass extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DataClass INSTANCE = new AttachedNoteLogMapper_DataClass();
    }
    public static AttachedNoteLogMapper_DataClass getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DataClass.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DataClass() {
        super("data_class");
    }

}
