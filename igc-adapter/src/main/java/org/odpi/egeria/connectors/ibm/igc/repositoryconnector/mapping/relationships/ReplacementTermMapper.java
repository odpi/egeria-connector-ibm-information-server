/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;

/**
 * Singleton to map the OMRS "ReplacementTerm" relationship for IGC "term" assets.
 */
public class ReplacementTermMapper extends RelationshipMapping {

    private static class Singleton {
        private static final ReplacementTermMapper INSTANCE = new ReplacementTermMapper();
    }
    public static ReplacementTermMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected ReplacementTermMapper() {
        super(
                "term",
                "term",
                "replaced_by",
                "replaces",
                "ReplacementTerm",
                "replacedTerms",
                "replacementTerms"
        );
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("expression", null);
        addLiteralPropertyMapping("status", TermRelationshipStatusMapper.getInstance(null).getEnumMappingByIgcValue("Active"));
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
    }

}
