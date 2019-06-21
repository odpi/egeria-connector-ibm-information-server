/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "ForeignKey" relationship for IGC "database_column" assets.
 */
public class ForeignKeyMapper extends RelationshipMapping {

    private static class Singleton {
        private static final ForeignKeyMapper INSTANCE = new ForeignKeyMapper();
    }
    public static ForeignKeyMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ForeignKeyMapper() {
        super(
                "database_column",
                "database_column",
                "defined_foreign_key_referenced",
                "defined_foreign_key_references",
                "ForeignKey",
                "primaryKey",
                "foreignKey"
        );
        addAlternativePropertyFromOne("selected_foreign_key_referenced");
        addAlternativePropertyFromTwo("selected_foreign_key_references");
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("confidence", 100);
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
        addLiteralPropertyMapping("name", null);
    }

}
