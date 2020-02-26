/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.GovernancePolicyLinkMapper;

/**
 * Defines the mapping to the OMRS "GlossaryPolicy" entity.
 */
public class GovernancePolicyMapper extends GovernanceDefinition_Mapper {

    private static class Singleton {
        private static final GovernancePolicyMapper INSTANCE = new GovernancePolicyMapper();
    }
    public static GovernancePolicyMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected GovernancePolicyMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "information_governance_policy",
                "Information Governance Policy",
                "GovernancePolicy"
        );

        // The list of relationships that should be mapped
        addRelationshipMapper(GovernancePolicyLinkMapper.getInstance(null));

    }

}
