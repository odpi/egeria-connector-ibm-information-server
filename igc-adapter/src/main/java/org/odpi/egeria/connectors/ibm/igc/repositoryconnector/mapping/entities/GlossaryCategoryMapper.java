/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.TermCategorizationMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.SubjectAreaMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.CategoryHierarchyLinkMapper;

/**
 * Defines the mapping to the OMRS "GlossaryCategory" entity.
 */
public class GlossaryCategoryMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final GlossaryCategoryMapper INSTANCE = new GlossaryCategoryMapper();
    }
    public static GlossaryCategoryMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private GlossaryCategoryMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "category",
                "Category",
                "GlossaryCategory"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "displayName");
        addSimplePropertyMapping("short_description", "description");

        // The classes to use for mapping any relationships
        addRelationshipMapper(CategoryHierarchyLinkMapper.getInstance(null));
        addRelationshipMapper(TermCategorizationMapper.getInstance(null));

        // The classes to use for mapping any classifications
        addClassificationMapper(SubjectAreaMapper.getInstance(null));

    }

}
