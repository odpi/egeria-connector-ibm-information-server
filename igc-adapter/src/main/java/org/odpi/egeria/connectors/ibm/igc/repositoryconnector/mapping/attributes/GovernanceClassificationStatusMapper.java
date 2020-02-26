/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map between OMRS "GovernanceClassificationStatus" enum and corresponding IGC property values.
 */
public class GovernanceClassificationStatusMapper extends EnumMapping {

    private static class Singleton {
        private static final GovernanceClassificationStatusMapper INSTANCE = new GovernanceClassificationStatusMapper();
    }
    public static GovernanceClassificationStatusMapper getInstance(IGCVersionEnum version) { return Singleton.INSTANCE; }

    protected GovernanceClassificationStatusMapper() {
        super(
                "GovernanceClassificationStatus"
        );
        addDefaultEnumMapping(99, "Other", "Another classification assignment status.");
        addEnumMapping("Discovered", 0, "Discovered", "The classification assignment was discovered by an automated process.");
        addEnumMapping("Proposed", 1, "Proposed", "The classification assignment was proposed by a subject matter expert.");
        addEnumMapping("Imported", 2, "Imported", "The classification assignment was imported from another metadata system.");
        addEnumMapping("Validated", 3, "Validated", "The classification assignment has been validated and approved by a subject matter expert.");
        addEnumMapping("Deprecated", 4, "Deprecated", "The classification assignment should no longer be used.");
        addEnumMapping("Obsolete", 5, "Obsolete", "The classification assignment must no longer be used.");
    }

}
