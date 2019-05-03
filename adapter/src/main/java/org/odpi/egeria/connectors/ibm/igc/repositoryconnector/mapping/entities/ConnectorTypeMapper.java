/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionConnectorTypeMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.ArrayPropertyValue;

/**
 * Defines the mapping to the OMRS "ConnectorType" entity.
 */
public class ConnectorTypeMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final ConnectorTypeMapper INSTANCE = new ConnectorTypeMapper();
    }
    public static ConnectorTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ConnectorTypeMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "connector",
                "Connector",
                "ConnectorType"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "displayName");
        addSimplePropertyMapping("short_description", "description");
        addSimplePropertyMapping("type", "connectorProviderClassName");

        ArrayPropertyValue emptyArray = new ArrayPropertyValue();
        addLiteralPropertyMapping("recognizedAdditionalProperties", emptyArray);
        addLiteralPropertyMapping("recognizedSecuredProperties", emptyArray);
        addLiteralPropertyMapping("recognizedConfigurationProperties", emptyArray);

        // The list of relationships that should be mapped
        addRelationshipMapper(ConnectionConnectorTypeMapper.getInstance());

    }

}
