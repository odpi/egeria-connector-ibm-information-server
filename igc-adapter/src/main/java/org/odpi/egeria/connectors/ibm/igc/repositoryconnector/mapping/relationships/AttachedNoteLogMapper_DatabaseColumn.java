/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "database_column" assets.
 */
public class AttachedNoteLogMapper_DatabaseColumn extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DatabaseColumn INSTANCE = new AttachedNoteLogMapper_DatabaseColumn();
    }
    public static AttachedNoteLogMapper_DatabaseColumn getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DatabaseColumn.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DatabaseColumn() {
        super("database_column");
    }

}
