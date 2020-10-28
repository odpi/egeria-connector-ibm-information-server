/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AssetZoneMembershipMapper_Database;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionToAssetMapper_Database;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataContentForDataSetMapper;

/**
 * Define sthe mapping to the OMRS "Database" entity.
 */
public class DatabaseMapper extends DataStoreMapper {

    private static class Singleton {
        private static final DatabaseMapper INSTANCE = new DatabaseMapper();
    }
    public static DatabaseMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected DatabaseMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database",
                "Database",
                "Database"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("dbms", "deployedImplementationType");
        addSimplePropertyMapping("dbms_version", "databaseVersion");
        addSimplePropertyMapping("dbms_server_instance", "instance");
        addSimplePropertyMapping("imported_from", "importedFrom");

        // Add literal mappings for deprecated properties (so we still pass type verification even during a
        // patching process)
        addLiteralPropertyMapping("type", null);
        addLiteralPropertyMapping("version", null);

        // The list of relationships that should be mapped
        addRelationshipMapper(DataContentForDataSetMapper.getInstance(null));
        addRelationshipMapper(ConnectionToAssetMapper_Database.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(AssetZoneMembershipMapper_Database.getInstance(null));

    }

}
