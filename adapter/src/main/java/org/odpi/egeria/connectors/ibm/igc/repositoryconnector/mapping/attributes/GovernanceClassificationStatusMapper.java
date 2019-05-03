/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

/**
 * Singleton to map between OMRS "GovernanceClassificationStatus" enum and corresponding IGC property values.
 */
public class GovernanceClassificationStatusMapper extends EnumMapping {

    private static class Singleton {
        private static final GovernanceClassificationStatusMapper INSTANCE = new GovernanceClassificationStatusMapper();
    }
    public static GovernanceClassificationStatusMapper getInstance() {
        return Singleton.INSTANCE;
    }

    private GovernanceClassificationStatusMapper() {
        super(
                "GovernanceClassificationStatus"
        );
        addDefaultEnumMapping(99, "Other");
        addEnumMapping("Discovered", 0, "Discovered");
        addEnumMapping("Proposed", 1, "Proposed");
        addEnumMapping("Imported", 2, "Imported");
        addEnumMapping("Validated", 3, "Validated");
        addEnumMapping("Deprecated", 4, "Deprecated");
        addEnumMapping("Obsolete", 5, "Obsolete");
    }

}
