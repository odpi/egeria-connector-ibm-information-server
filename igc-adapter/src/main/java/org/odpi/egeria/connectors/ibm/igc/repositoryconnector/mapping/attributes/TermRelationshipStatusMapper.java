/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map between OMRS "TermRelationshipStatus" enum and corresponding IGC property values.
 */
public class TermRelationshipStatusMapper extends EnumMapping {

    private static class Singleton {
        private static final TermRelationshipStatusMapper INSTANCE = new TermRelationshipStatusMapper();
    }
    public static TermRelationshipStatusMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TermRelationshipStatusMapper() {
        super(
                "TermRelationshipStatus"
        );
        addDefaultEnumMapping(99, "Other", "Another term relationship status.");
        addEnumMapping("Draft", 0, "Draft", "The term relationship is in development.");
        addEnumMapping("Active", 1, "Active", "The term relationship is approved and in use.");
        addEnumMapping("Deprecated", 2, "Deprecated", "The term relationship should no longer be used.");
        addEnumMapping("Obsolete", 3, "Obsolete", "The term relationship must no longer be used.");
    }

}
