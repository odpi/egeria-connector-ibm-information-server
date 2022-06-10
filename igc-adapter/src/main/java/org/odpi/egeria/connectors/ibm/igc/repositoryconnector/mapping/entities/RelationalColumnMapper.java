/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AnchorsMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.PrimaryKeyMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.TypeEmbeddedAttributeMapper_RelationalColumn;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.*;

/**
 * Defines the mapping to the OMRS "RelationalColumn" entity.
 */
public class RelationalColumnMapper extends TabularColumnMapper {

    private static class Singleton {
        private static final RelationalColumnMapper INSTANCE = new RelationalColumnMapper();
    }
    public static RelationalColumnMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected RelationalColumnMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database_column",
                "Database Column",
                "RelationalColumn"
        );

        // Map these now-deprecated properties to null
        addLiteralPropertyMapping("fraction", null);
        addLiteralPropertyMapping("isUnique", null);

        // The list of relationships that should be mapped
        addRelationshipMapper(NestedSchemaAttributeMapper.getInstance(null));
        addRelationshipMapper(ForeignKeyMapper.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(PrimaryKeyMapper.getInstance(null));
        addClassificationMapper(TypeEmbeddedAttributeMapper_RelationalColumn.getInstance(null));
        addClassificationMapper(AnchorsMapper.getInstance(null));
    }

}
