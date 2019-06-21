/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;

/**
 * Singleton to map the OMRS "TermISATypeOFRelationship" relationship for IGC "term" assets.
 */
public class TermISATypeOFRelationshipMapper extends RelationshipMapping {

    private static class Singleton {
        private static final TermISATypeOFRelationshipMapper INSTANCE = new TermISATypeOFRelationshipMapper();
    }
    public static TermISATypeOFRelationshipMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TermISATypeOFRelationshipMapper() {
        super(
                "term",
                "term",
                "has_types",
                "is_a_type_of",
                "TermISATypeOFRelationship",
                "supertypes",
                "subtypes"
        );
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("status", TermRelationshipStatusMapper.getInstance(null).getEnumMappingByIgcValue("Active"));
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
    }

}
