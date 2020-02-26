/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;

/**
 * Singleton to map the OMRS "TermHASARelationship" relationship for IGC "term" assets.
 */
public class TermHASARelationshipMapper extends RelationshipMapping {

    private static class Singleton {
        private static final TermHASARelationshipMapper INSTANCE = new TermHASARelationshipMapper();
    }
    public static TermHASARelationshipMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    // TODO: has_a_term is necessary for search, but may not reveal inherited locations
    //  (only possible via has_a, which is not searchable)
    //  - if we need inherited locations this will likely need to become a custom mapping

    protected TermHASARelationshipMapper() {
        super(
                "term",
                "term",
                "has_a_term",
                "is_of",
                "TermHASARelationship",
                "objects",
                "attributes"
        );
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("status", TermRelationshipStatusMapper.getInstance(null).getEnumMappingByIgcValue("Active"));
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
    }

}
