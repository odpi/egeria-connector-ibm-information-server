/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.*;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ConfidentialityMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.SpineObjectMapper;

/**
 * Defines the mapping to the OMRS "GlossaryTerm" entity.
 */
public class GlossaryTermMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final GlossaryTermMapper INSTANCE = new GlossaryTermMapper();
    }
    public static GlossaryTermMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private GlossaryTermMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "term",
                "Term",
                "GlossaryTerm"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "displayName");
        addSimplePropertyMapping("short_description", "summary");
        addSimplePropertyMapping("long_description", "description");
        addSimplePropertyMapping("example", "examples");
        addSimplePropertyMapping("abbreviation", "abbreviation");
        addSimplePropertyMapping("usage", "usage");

        // The classes to use for mapping any relationships
        addRelationshipMapper(TermCategorizationMapper.getInstance());
        addRelationshipMapper(SynonymMapper.getInstance());
        addRelationshipMapper(RelatedTermMapper.getInstance());
        addRelationshipMapper(ReplacementTermMapper.getInstance());
        addRelationshipMapper(TranslationMapper.getInstance());
        addRelationshipMapper(TermHASARelationshipMapper.getInstance());
        addRelationshipMapper(TermISATypeOFRelationshipMapper.getInstance());

        // The classes to use for mapping any classifications
        addClassificationMapper(ConfidentialityMapper.getInstance());
        addClassificationMapper(SpineObjectMapper.getInstance());

    }

}
