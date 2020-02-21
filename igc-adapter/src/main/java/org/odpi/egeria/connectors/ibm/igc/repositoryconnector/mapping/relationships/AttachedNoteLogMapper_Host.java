/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "host" assets.
 */
public class AttachedNoteLogMapper_Host extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_Host INSTANCE = new AttachedNoteLogMapper_Host();
    }
    public static AttachedNoteLogMapper_Host getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_Host.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_Host() {
        super("host");
    }

}
