/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "user" assets.
 */
public class AttachedNoteLogMapper_User extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_User INSTANCE = new AttachedNoteLogMapper_User();
    }
    public static AttachedNoteLogMapper_User getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_User.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_User() {
        super("user");
    }

}
