/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.CategoryAnchorMapper;
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
        addRelationshipMapper(CategoryAnchorMapper.getInstance(null));
        addRelationshipMapper(CategoryHierarchyLinkMapper.getInstance(null));
        addRelationshipMapper(TermCategorizationMapper.getInstance(null));

        // The classes to use for mapping any classifications
        addClassificationMapper(SubjectAreaMapper.getInstance(null));

    }

    /**
     * {@inheritDoc}
     */
    public boolean isOmrsType(IGCRestClient igcRestClient, Reference igcObject) {
        String assetType = IGCRestConstants.getAssetTypeForSearch(igcObject.getType());
        if (assetType.equals("category")) {
            Identity catIdentity = igcObject.getIdentity(igcRestClient);
            Identity parentIdentity = catIdentity.getParentIdentity();
            if (parentIdentity != null) {
                Identity ultimate = catIdentity.getUltimateParentIdentity();
                return !ultimate.getName().equals("Classifications");
            }
        }
        return false;
    }

    /**
     * Search for GlossaryCategories by looking for a category with a parent category.
     *
     * @return IGCSearchConditionSet - the IGC search criteria to find GlossaryCategory entities
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria() {
        IGCSearchCondition notRootLevel = new IGCSearchCondition("parent_category", "isNull", true);
        IGCSearchCondition notClassification = new IGCSearchCondition("category_path.name", "=", "Classifications", true);
        IGCSearchConditionSet conditions = new IGCSearchConditionSet(notRootLevel);
        conditions.addCondition(notClassification);
        conditions.setMatchAnyCondition(false);
        return conditions;
    }

}
