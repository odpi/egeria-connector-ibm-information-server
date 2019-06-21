/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;

/**
 * Singleton to map the OMRS "Synonym" relationship for IGC "term" assets.
 */
public class SynonymMapper extends RelationshipMapping {

    private static class Singleton {
        private static final SynonymMapper INSTANCE = new SynonymMapper();
    }
    public static SynonymMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private static final String P_SYNONYMS = "synonyms";

    private SynonymMapper() {
        super(
                "term",
                "term",
                P_SYNONYMS,
                P_SYNONYMS,
                "Synonym",
                P_SYNONYMS,
                P_SYNONYMS
        );
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("expression", null);
        addLiteralPropertyMapping("status", TermRelationshipStatusMapper.getInstance(null).getEnumMappingByIgcValue("Active"));
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
    }

}
