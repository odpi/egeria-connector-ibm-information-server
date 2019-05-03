/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionToAssetMapper_Database;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataContentForDataSetMapper;

/**
 * Define sthe mapping to the OMRS "Database" entity.
 */
public class DatabaseMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final DatabaseMapper INSTANCE = new DatabaseMapper();
    }
    public static DatabaseMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private DatabaseMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "database",
                "Database",
                "Database"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");
        addSimplePropertyMapping("dbms", "type");
        addSimplePropertyMapping("dbms_version", "version");
        addSimplePropertyMapping("dbms_server_instance", "instance");
        addSimplePropertyMapping("imported_from", "importedFrom");
        addSimplePropertyMapping("created_on", "createTime");
        addSimplePropertyMapping("modified_on", "modifiedTime");

        // The list of relationships that should be mapped
        addRelationshipMapper(DataContentForDataSetMapper.getInstance());
        addRelationshipMapper(ConnectionToAssetMapper_Database.getInstance());

    }

}
