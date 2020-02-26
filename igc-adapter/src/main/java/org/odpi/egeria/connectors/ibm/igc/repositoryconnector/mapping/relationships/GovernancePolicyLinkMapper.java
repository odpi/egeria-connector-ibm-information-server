/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "GovernancePolicyLink" relationship for IGC "information_governance_policy" assets.
 */
public class GovernancePolicyLinkMapper extends RelationshipMapping {

    private static class Singleton {
        private static final GovernancePolicyLinkMapper INSTANCE = new GovernancePolicyLinkMapper();
    }
    public static GovernancePolicyLinkMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected GovernancePolicyLinkMapper() {
        super(
                "information_governance_policy",
                "information_governance_policy",
                "subpolicies",
                "parent_policy",
                "GovernancePolicyLink",
                "linkingPolicies",
                "linkedPolicies"
        );
        addLiteralPropertyMapping("description", null);
        setContainedType(ContainedType.TWO);
    }

}
