/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AssetZoneMembershipMapper_DeployedDatabaseSchema;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_DatabaseSchema;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataContentForDataSetMapper;

/**
 * Defines the mapping to the OMRS "DeployedDatabaseSchema" entity.
 */
public class DeployedDatabaseSchemaMapper extends DataSet_Mapper {

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

        // The list of relationships that should be mapped
        addRelationshipMapper(AssetSchemaTypeMapper_DatabaseSchema.getInstance(null));
        addRelationshipMapper(DataContentForDataSetMapper.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(AssetZoneMembershipMapper_DeployedDatabaseSchema.getInstance(null));

    }

}
