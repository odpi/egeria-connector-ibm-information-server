/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;

/**
 * Singleton to map the OMRS "RelatedTerm" relationship for IGC "term" assets.
 */
public class RelatedTermMapper extends RelationshipMapping {

    private static class Singleton {
        private static final RelatedTermMapper INSTANCE = new RelatedTermMapper();
    }
    public static RelatedTermMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected RelatedTermMapper() {
        super(
                "term",
                "term",
                "related_terms",
                "related_terms",
                "RelatedTerm",
                "seeAlso",
                "seeAlso"
        );
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("expression", null);
        addLiteralPropertyMapping("status", TermRelationshipStatusMapper.getInstance(null).getEnumMappingByIgcValue("Active"));
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
    }

}
