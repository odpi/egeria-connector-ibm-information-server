/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionConnectorTypeMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionEndpointMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionToAssetMapper_Database;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionToAssetMapper_FileFolder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.MapPropertyValue;

/**
 * Defines the mapping to the OMRS "Connection" entity.
 */
public class ConnectionMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final ConnectionMapper INSTANCE = new ConnectionMapper();
    }
    public static ConnectionMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ConnectionMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_connection",
                "Data Connection",
                "Connection",
                null,
                false
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "displayName");
        addSimplePropertyMapping("short_description", "description");

        MapPropertyValue emptyMap = new MapPropertyValue();
        addLiteralPropertyMapping("securedProperties", emptyMap);
        addLiteralPropertyMapping("configurationProperties", emptyMap);
        addLiteralPropertyMapping("userId", null);
        addLiteralPropertyMapping("clearPassword", null);
        addLiteralPropertyMapping("encryptedPassword", null);

        // The list of relationships that should be mapped
        addRelationshipMapper(ConnectionToAssetMapper_Database.getInstance());
        addRelationshipMapper(ConnectionToAssetMapper_FileFolder.getInstance());
        addRelationshipMapper(ConnectionConnectorTypeMapper.getInstance());
        addRelationshipMapper(ConnectionEndpointMapper.getInstance());

    }

}
