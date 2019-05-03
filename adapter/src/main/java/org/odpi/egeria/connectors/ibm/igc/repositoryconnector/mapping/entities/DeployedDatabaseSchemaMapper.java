/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_DatabaseSchema;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataContentForDataSetMapper;

/**
 * Defines the mapping to the OMRS "DeployedDatabaseSchema" entity.
 */
public class DeployedDatabaseSchemaMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final DeployedDatabaseSchemaMapper INSTANCE = new DeployedDatabaseSchemaMapper();
    }
    public static DeployedDatabaseSchemaMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private DeployedDatabaseSchemaMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database_schema",
                "Database Schema",
                "DeployedDatabaseSchema"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");

        // The list of relationships that should be mapped
        addRelationshipMapper(AssetSchemaTypeMapper_DatabaseSchema.getInstance());
        addRelationshipMapper(DataContentForDataSetMapper.getInstance());

    }

}
