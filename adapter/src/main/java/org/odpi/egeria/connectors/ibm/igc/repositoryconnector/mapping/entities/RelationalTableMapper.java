/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_TableSchema;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SchemaAttributeTypeMapper_DatabaseTable;

/**
 * Defines the mapping to the OMRS "RelationalTable" entity.
 */
public class RelationalTableMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final RelationalTableMapper INSTANCE = new RelationalTableMapper();
    }
    public static RelationalTableMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private RelationalTableMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database_table",
                "Database Table",
                "RelationalTable"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");

        // The list of relationships that should be mapped
        addRelationshipMapper(AttributeForSchemaMapper_TableSchema.getInstance());
        addRelationshipMapper(SchemaAttributeTypeMapper_DatabaseTable.getInstance());

    }

}
