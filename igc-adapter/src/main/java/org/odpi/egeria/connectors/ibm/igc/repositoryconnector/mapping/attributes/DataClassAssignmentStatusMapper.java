/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map between OMRS "DataClassAssignmentStatus" enum and corresponding IGC property values.
 */
public class DataClassAssignmentStatusMapper extends EnumMapping {

    private static class Singleton {
        private static final DataClassAssignmentStatusMapper INSTANCE = new DataClassAssignmentStatusMapper();
    }
    public static DataClassAssignmentStatusMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected DataClassAssignmentStatusMapper() {
        super(
                "DataClassAssignmentStatus"
        );
        addDefaultEnumMapping(99, "Other", "Another data class assignment status.");
        addEnumMapping("discovered", 0, "Discovered", "The data class assignment was discovered by an automated process.");
        addEnumMapping("selected", 1, "Proposed", "The data class assignment was proposed by a subject matter expert.");
    }

}
