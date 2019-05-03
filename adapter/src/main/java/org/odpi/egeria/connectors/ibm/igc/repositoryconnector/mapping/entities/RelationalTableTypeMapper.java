/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_TableColumn;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SchemaAttributeTypeMapper_DatabaseTable;

/**
 * Defines the mapping to the OMRS "RelationalTableType" entity.
 */
public class RelationalTableTypeMapper extends ReferenceableMapper {

    public static final String IGC_RID_PREFIX = IGCOMRSMetadataCollection.generateTypePrefix("RTT");

    private static class Singleton {
        private static final RelationalTableTypeMapper INSTANCE = new RelationalTableTypeMapper();
    }
    public static RelationalTableTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private RelationalTableTypeMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database_table",
                "Database Table",
                "RelationalTableType",
                IGC_RID_PREFIX
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "displayName");

        // The list of relationships that should be mapped
        addRelationshipMapper(SchemaAttributeTypeMapper_DatabaseTable.getInstance());
        addRelationshipMapper(AttributeForSchemaMapper_TableColumn.getInstance());

    }

}
