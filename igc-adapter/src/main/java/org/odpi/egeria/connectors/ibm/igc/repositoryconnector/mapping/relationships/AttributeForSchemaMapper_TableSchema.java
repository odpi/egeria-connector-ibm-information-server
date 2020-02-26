/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.RelationalDBSchemaTypeMapper;

/**
 * Singleton to map the OMRS "AttributeForSchema" relationship between IGC "database_schema" and "database_table" assets.
 */
public class AttributeForSchemaMapper_TableSchema extends RelationshipMapping {

    private static class Singleton {
        private static final AttributeForSchemaMapper_TableSchema INSTANCE = new AttributeForSchemaMapper_TableSchema();
    }
    public static AttributeForSchemaMapper_TableSchema getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected AttributeForSchemaMapper_TableSchema() {
        super(
                "database_schema",
                "database_table",
                "database_tables",
                "database_schema",
                "AttributeForSchema",
                "parentSchemas",
                "attributes",
                RelationalDBSchemaTypeMapper.IGC_RID_PREFIX,
                null
        );
        setContainedType(ContainedType.TWO);
    }

}
