/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_DatabaseSchema;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_TableSchema;

/**
 * Defines the mapping to the OMRS "RelationalDBSchemaType" entity.
 */
public class RelationalDBSchemaTypeMapper extends ComplexSchemaTypeMapper {

    public static final String IGC_RID_PREFIX = "RDBST";

    private static class Singleton {
        private static final RelationalDBSchemaTypeMapper INSTANCE = new RelationalDBSchemaTypeMapper();
    }
    public static RelationalDBSchemaTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected RelationalDBSchemaTypeMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database_schema",
                "Database Schema",
                "RelationalDBSchemaType",
                IGC_RID_PREFIX
        );

        // The list of relationships that should be mapped
        addRelationshipMapper(AssetSchemaTypeMapper_DatabaseSchema.getInstance(null));
        addRelationshipMapper(AttributeForSchemaMapper_TableSchema.getInstance(null));

    }

}
