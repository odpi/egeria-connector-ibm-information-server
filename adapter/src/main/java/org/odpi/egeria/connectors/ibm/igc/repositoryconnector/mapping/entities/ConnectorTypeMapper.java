/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionConnectorTypeMapper;

/**
 * Defines the mapping to the OMRS "ConnectorType" entity.
 */
public class ConnectorTypeMapper extends ReferenceableMapper {

    public ConnectorTypeMapper(IGCOMRSRepositoryConnector igcomrsRepositoryConnector, String userId) {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                igcomrsRepositoryConnector,
                "connector",
                "Connector",
                "ConnectorType",
                userId
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");
        addSimplePropertyMapping("type", "connectorProviderClassName");

        // The list of relationships that should be mapped
        addRelationshipMapper(ConnectionConnectorTypeMapper.getInstance());

    }

}
