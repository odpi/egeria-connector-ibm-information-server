/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "category" assets.
 */
public class AttachedNoteLogMapper_Category extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_Category INSTANCE = new AttachedNoteLogMapper_Category();
    }
    public static AttachedNoteLogMapper_Category getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_Category.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_Category() {
        super("category");
    }

}
