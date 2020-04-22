/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_FileRecord;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_RecordField;

/**
 * Defines the mapping to the OMRS "TabularSchemaType" entity.
 */
public class TabularSchemaTypeMapper extends ComplexSchemaTypeMapper {

    private static class Singleton {
        private static final TabularSchemaTypeMapper INSTANCE = new TabularSchemaTypeMapper();
    }
    public static TabularSchemaTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TabularSchemaTypeMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file_record",
                "Data File Record",
                "TabularSchemaType",
                null
        );

        // The list of relationships that should be mapped
        addRelationshipMapper(AssetSchemaTypeMapper_FileRecord.getInstance(null));
        addRelationshipMapper(AttributeForSchemaMapper_RecordField.getInstance(null));

    }

}
