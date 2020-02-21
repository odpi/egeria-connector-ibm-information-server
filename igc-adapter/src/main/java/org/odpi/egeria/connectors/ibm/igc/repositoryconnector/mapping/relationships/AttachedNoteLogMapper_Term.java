/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "term" assets.
 */
public class AttachedNoteLogMapper_Term extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_Term INSTANCE = new AttachedNoteLogMapper_Term();
    }
    public static AttachedNoteLogMapper_Term getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_Term.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_Term() {
        super("term");
    }

}
