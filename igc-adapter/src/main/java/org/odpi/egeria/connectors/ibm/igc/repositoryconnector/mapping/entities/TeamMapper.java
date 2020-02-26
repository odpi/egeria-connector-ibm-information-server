/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ContactThroughMapper_Team;

/**
 * Defines the mapping to the OMRS "Team" entity.
 */
public class TeamMapper extends ActorProfile_Mapper {

    private static class Singleton {
        private static final TeamMapper INSTANCE = new TeamMapper();
    }
    public static TeamMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TeamMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "group",
                "Group",
                "Team"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("principal_id", "name");
        addSimplePropertyMapping("group_name", "description");

        addLiteralPropertyMapping("teamType", null);

        // The classes to use for mapping any relationships
        addRelationshipMapper(ContactThroughMapper_Team.getInstance(null));

    }

}
