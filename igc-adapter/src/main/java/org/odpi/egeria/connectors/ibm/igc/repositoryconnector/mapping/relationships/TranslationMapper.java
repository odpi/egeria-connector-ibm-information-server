/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;

/**
 * Singleton to map the OMRS "Translation" relationship for IGC "term" assets.
 */
public class TranslationMapper extends RelationshipMapping {

    private static class Singleton {
        private static final TranslationMapper INSTANCE = new TranslationMapper();
    }
    public static TranslationMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private static final String P_TRANSLATIONS = "translations";

    private TranslationMapper() {
        super(
                "term",
                "term",
                P_TRANSLATIONS,
                P_TRANSLATIONS,
                "Translation",
                P_TRANSLATIONS,
                P_TRANSLATIONS
        );
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("expression", null);
        addLiteralPropertyMapping("status", TermRelationshipStatusMapper.getInstance(null).getEnumMappingByIgcValue("Active"));
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
    }

}
