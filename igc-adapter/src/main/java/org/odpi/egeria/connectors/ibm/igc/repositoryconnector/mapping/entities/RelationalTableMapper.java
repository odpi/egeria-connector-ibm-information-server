/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.TypeEmbeddedAttributeMapper_RelationalTable;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_TableSchema;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.NestedSchemaAttributeMapper;

/**
 * Defines the mapping to the OMRS "RelationalTable" entity.
 */
public class RelationalTableMapper extends SchemaElement_Mapper {

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
                "RelationalTable",
                null
        );

        // The list of properties that should be mapped
        addLiteralPropertyMapping("position", null);
        addLiteralPropertyMapping("minCardinality", 1);
        addLiteralPropertyMapping("maxCardinality", 1);
        addLiteralPropertyMapping("allowsDuplicateValues", null);
        addLiteralPropertyMapping("orderedValues", null);
        addLiteralPropertyMapping("defaultValueOverride", null);
        addLiteralPropertyMapping("nativeClass", null);

        // Deprecated properties will be null'd
        addLiteralPropertyMapping("name", null);

        // The list of relationships that should be mapped
        addRelationshipMapper(AttributeForSchemaMapper_TableSchema.getInstance(null));
        addRelationshipMapper(NestedSchemaAttributeMapper.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(TypeEmbeddedAttributeMapper_RelationalTable.getInstance(null));

    }

}
