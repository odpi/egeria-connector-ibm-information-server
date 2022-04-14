/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AnchorsMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.CategoryAnchorMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SemanticAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.TermCategorizationMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.SubjectAreaMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.CategoryHierarchyLinkMapper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;

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

    protected GlossaryCategoryMapper() {

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
        addRelationshipMapper(SemanticAssignmentMapper.getInstance(null));
        addRelationshipMapper(CategoryAnchorMapper.getInstance(null));
        addRelationshipMapper(CategoryHierarchyLinkMapper.getInstance(null));
        addRelationshipMapper(TermCategorizationMapper.getInstance(null));

        // The classes to use for mapping any classifications
        addClassificationMapper(SubjectAreaMapper.getInstance(null));
        addClassificationMapper(AnchorsMapper.getInstance(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOmrsType(IGCRestClient igcRestClient, ObjectCache cache, Reference igcObject) throws RepositoryErrorException {
        final String methodName = "isOmrsType";
        String assetType = IGCRestConstants.getAssetTypeForSearch(igcObject.getType());
        if (assetType.equals("category")) {
            try {
                Identity catIdentity = igcObject.getIdentity(igcRestClient, cache);
                Identity parentIdentity = catIdentity.getParentIdentity();
                if (parentIdentity != null) {
                    Identity ultimate = catIdentity.getUltimateParentIdentity();
                    return !ultimate.getName().equals("Classifications");
                }
            } catch (IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
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
