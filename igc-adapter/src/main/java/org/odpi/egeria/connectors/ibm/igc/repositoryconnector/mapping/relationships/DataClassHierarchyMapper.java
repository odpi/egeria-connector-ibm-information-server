/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "DataClassHierarchy" relationship for IGC "data_class" assets.
 */
public class DataClassHierarchyMapper extends RelationshipMapping {

    private static class Singleton {
        private static final DataClassHierarchyMapper INSTANCE = new DataClassHierarchyMapper();
    }
    public static DataClassHierarchyMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected DataClassHierarchyMapper() {
        super(
                "data_class",
                "data_class",
                "contains_data_classes",
                "parent_data_class",
                "DataClassHierarchy",
                "superDataClass",
                "subDataClasses"
        );
        setContainedType(ContainedType.TWO);
    }

}
