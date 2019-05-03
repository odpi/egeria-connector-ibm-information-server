/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

/**
 * Singleton to map between OMRS "TermRelationshipStatus" enum and corresponding IGC property values.
 */
public class TermRelationshipStatusMapper extends EnumMapping {

    private static class Singleton {
        private static final TermRelationshipStatusMapper INSTANCE = new TermRelationshipStatusMapper();
    }
    public static TermRelationshipStatusMapper getInstance() {
        return Singleton.INSTANCE;
    }

    private TermRelationshipStatusMapper() {
        super(
                "TermRelationshipStatus"
        );
        addDefaultEnumMapping(99, "Other");
        addEnumMapping("Draft", 0, "Draft");
        addEnumMapping("Active", 1, "Active");
        addEnumMapping("Deprecated", 2, "Deprecated");
        addEnumMapping("Obsolete", 3, "Obsolete");
    }

}
