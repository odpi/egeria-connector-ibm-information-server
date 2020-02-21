/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC "information_governance_policy" assets.
 */
public class AttachedNoteLogMapper_InformationGovernancePolicy extends AttachedNoteLogMapper {

    private static class Singleton {
        private static final AttachedNoteLogMapper_InformationGovernancePolicy INSTANCE = new AttachedNoteLogMapper_InformationGovernancePolicy();
    }
    public static AttachedNoteLogMapper_InformationGovernancePolicy getInstance(IGCVersionEnum version) {
        return AttachedNoteLogMapper_InformationGovernancePolicy.Singleton.INSTANCE;
    }

    private AttachedNoteLogMapper_InformationGovernancePolicy() {
        super("information_governance_policy");
    }

}
