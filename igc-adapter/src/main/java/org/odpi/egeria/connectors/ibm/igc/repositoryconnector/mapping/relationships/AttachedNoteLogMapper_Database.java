/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "database" assets.
 */
public class AttachedNoteLogMapper_Database extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_Database INSTANCE = new AttachedNoteLogMapper_Database();
    }
    public static AttachedNoteLogMapper_Database getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_Database.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_Database() {
        super("database");
    }

}
