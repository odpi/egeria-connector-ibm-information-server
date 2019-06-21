/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.RelationalTableTypeMapper;

/**
 * Singleton to map the OMRS "SchemaAttributeType" relationship for IGC "database_table" assets.
 */
public class SchemaAttributeTypeMapper_DatabaseTable extends RelationshipMapping {

    private static class Singleton {
        private static final SchemaAttributeTypeMapper_DatabaseTable INSTANCE = new SchemaAttributeTypeMapper_DatabaseTable();
    }
    public static SchemaAttributeTypeMapper_DatabaseTable getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private SchemaAttributeTypeMapper_DatabaseTable() {
        super(
                "database_table",
                "database_table",
                SELF_REFERENCE_SENTINEL,
                SELF_REFERENCE_SENTINEL,
                "SchemaAttributeType",
                "usedInSchemas",
                "type",
                null,
                RelationalTableTypeMapper.IGC_RID_PREFIX
        );
    }

}
