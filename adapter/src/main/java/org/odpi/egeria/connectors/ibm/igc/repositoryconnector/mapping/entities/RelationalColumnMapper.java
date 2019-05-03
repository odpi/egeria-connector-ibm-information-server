/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.PrimaryKeyMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_TableColumn;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ForeignKeyMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SchemaAttributeTypeMapper_DatabaseColumn;

/**
 * Defines the mapping to the OMRS "RelationalColumn" entity.
 */
public class RelationalColumnMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final RelationalColumnMapper INSTANCE = new RelationalColumnMapper();
    }
    public static RelationalColumnMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private RelationalColumnMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database_column",
                "Database Column",
                "RelationalColumn"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("position", "position");
        addSimplePropertyMapping("minimum_length", "minimumLength");
        addSimplePropertyMapping("length", "length");
        addSimplePropertyMapping("fraction", "fraction");
        addSimplePropertyMapping("allows_null_values", "isNullable");
        addSimplePropertyMapping("unique", "isUnique");

        // The list of relationships that should be mapped
        addRelationshipMapper(AttributeForSchemaMapper_TableColumn.getInstance());
        addRelationshipMapper(SchemaAttributeTypeMapper_DatabaseColumn.getInstance());
        addRelationshipMapper(ForeignKeyMapper.getInstance());
        addRelationshipMapper(DataClassAssignmentMapper.getInstance());

        // The list of classifications that should be mapped
        addClassificationMapper(PrimaryKeyMapper.getInstance());

    }

}
