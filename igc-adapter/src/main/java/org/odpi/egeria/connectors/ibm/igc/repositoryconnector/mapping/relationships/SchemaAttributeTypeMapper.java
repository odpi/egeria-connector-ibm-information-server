/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "SchemaAttributeType" relationship.
 * @see SchemaAttributeTypeMapper_DatabaseColumn
 * @see SchemaAttributeTypeMapper_DatabaseTable
 * @see SchemaAttributeTypeMapper_FileField
 */
public class SchemaAttributeTypeMapper extends RelationshipMapping {

    private static class Singleton {
        private static final SchemaAttributeTypeMapper INSTANCE = new SchemaAttributeTypeMapper();
    }
    public static SchemaAttributeTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private SchemaAttributeTypeMapper() {
        super(
                "",
                "",
                "",
                "",
                "SchemaAttributeType",
                "usedInSchemas",
                "type"
        );
        addSubType(SchemaAttributeTypeMapper_DatabaseColumn.getInstance(null));
        addSubType(SchemaAttributeTypeMapper_DatabaseTable.getInstance(null));
        addSubType(SchemaAttributeTypeMapper_FileField.getInstance(null));
    }

}
