/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;

/**
 * Singleton to map the OMRS "AttachedTag" relationship for IGC "label" assets.
 */
public class NestedSchemaAttributeMapper extends RelationshipMapping {

    private static class Singleton {
        private static final NestedSchemaAttributeMapper INSTANCE = new NestedSchemaAttributeMapper();
    }
    public static NestedSchemaAttributeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private NestedSchemaAttributeMapper() {
        super(
                "database_table",
                "database_column",
                "database_columns",
                "database_table_or_view",
                "NestedSchemaAttribute",
                "parentAttribute",
                "nestedAttributes"
        );
        setContainedType(ContainedType.TWO);
    }

}
