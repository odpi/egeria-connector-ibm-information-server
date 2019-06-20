/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

/**
 * Singleton to map between OMRS "TermRelationshipStatus" enum and corresponding IGC property values.
 */
public class TermAssignmentStatusMapper extends EnumMapping {

    private static class Singleton {
        private static final TermAssignmentStatusMapper INSTANCE = new TermAssignmentStatusMapper();
    }
    public static TermAssignmentStatusMapper getInstance() {
        return Singleton.INSTANCE;
    }

    private TermAssignmentStatusMapper() {
        super(
                "TermAssignmentStatus"
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
