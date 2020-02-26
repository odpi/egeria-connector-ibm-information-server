/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map between OMRS "TermRelationshipStatus" enum and corresponding IGC property values.
 */
public class TermAssignmentStatusMapper extends EnumMapping {

    private static class Singleton {
        private static final TermAssignmentStatusMapper INSTANCE = new TermAssignmentStatusMapper();
    }
    public static TermAssignmentStatusMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TermAssignmentStatusMapper() {
        super(
                "TermAssignmentStatus"
        );
        addDefaultEnumMapping(99, "Other", "Another term assignment status.");
        addEnumMapping("Discovered", 0, "Discovered", "The term assignment was discovered by an automated process.");
        addEnumMapping("Proposed", 1, "Proposed", "The term assignment was proposed by a subject matter expert.");
        addEnumMapping("Imported", 2, "Imported", "The term assignment was imported from another metadata system.");
        addEnumMapping("Validated", 3, "Validated", "The term assignment has been validated and approved by a subject matter expert.");
        addEnumMapping("Deprecated", 4, "Deprecated", "The term assignment should no longer be used.");
        addEnumMapping("Obsolete", 5, "Obsolete", "The term assignment must no longer be used.");
    }

}
