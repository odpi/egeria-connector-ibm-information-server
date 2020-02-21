/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "database_schema" assets.
 */
public class AttachedNoteLogMapper_DatabaseSchema extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_DatabaseSchema INSTANCE = new AttachedNoteLogMapper_DatabaseSchema();
    }
    public static AttachedNoteLogMapper_DatabaseSchema getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_DatabaseSchema.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_DatabaseSchema() {
        super("database_schema");
    }

}
