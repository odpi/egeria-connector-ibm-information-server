/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "AttributeForSchemaMapper" relationship.
 * @see AttributeForSchemaMapper_RecordField
 * @see AttributeForSchemaMapper_TableSchema
 */
public class AttributeForSchemaMapper extends RelationshipMapping {

    private static class Singleton {
        private static final AttributeForSchemaMapper INSTANCE = new AttributeForSchemaMapper();
    }
    public static AttributeForSchemaMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private AttributeForSchemaMapper() {
        super(
                "",
                "",
                "",
                "",
                "AttributeForSchema",
                "parentSchemas",
                "attributes"
        );
        addSubType(AttributeForSchemaMapper_TableSchema.getInstance(null));
        addSubType(AttributeForSchemaMapper_RecordField.getInstance(null));
    }

}
