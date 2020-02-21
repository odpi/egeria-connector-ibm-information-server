/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "database_table" assets.
 */
public class AttachedNoteLogMapper_DatabaseTable extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DatabaseTable INSTANCE = new AttachedNoteLogMapper_DatabaseTable();
    }
    public static AttachedNoteLogMapper_DatabaseTable getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DatabaseTable.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DatabaseTable() {
        super("database_table");
    }

}
