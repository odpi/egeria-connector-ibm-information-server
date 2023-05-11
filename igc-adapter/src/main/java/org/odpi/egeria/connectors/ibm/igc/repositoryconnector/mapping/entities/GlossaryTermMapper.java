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
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.PrimaryCategoryMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.SpineAttributeMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.*;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ConfidentialityMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.SpineObjectMapper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;

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

    protected GlossaryTermMapper() {

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
        // default value mappings
        addLiteralPropertyMapping("publishVersionIdentifier", null);

        // Mapping to default
        addLiteralPropertyMapping("publishVersionIdentifier", null);

        // The classes to use for mapping any relationships
        addRelationshipMapper(SemanticAssignmentMapper.getInstance(null));
        addRelationshipMapper(TermAnchorMapper.getInstance(null));
        addRelationshipMapper(TermCategorizationMapper.getInstance(null));
        addRelationshipMapper(SynonymMapper.getInstance(null));
        addRelationshipMapper(RelatedTermMapper.getInstance(null));
        addRelationshipMapper(ReplacementTermMapper.getInstance(null));
        addRelationshipMapper(TranslationMapper.getInstance(null));
        addRelationshipMapper(TermHASARelationshipMapper.getInstance(null));
        addRelationshipMapper(TermISATypeOFRelationshipMapper.getInstance(null));

        // The classes to use for mapping any classifications
        addClassificationMapper(ConfidentialityMapper.getInstance(null));
        addClassificationMapper(SpineObjectMapper.getInstance(null));
        addClassificationMapper(SpineAttributeMapper.getInstance(null));
        addClassificationMapper(PrimaryCategoryMapper.getInstance(null));
        addClassificationMapper(AnchorsMapper.getInstance(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOmrsType(IGCRestClient igcRestClient, ObjectCache cache, Reference igcObject) throws RepositoryErrorException {
        final String methodName = "isOmrsType";
        String assetType = IGCRestConstants.getAssetTypeForSearch(igcObject.getType());
        if (assetType.equals("term")) {
            try {
                Identity termIdentity = igcObject.getIdentity(igcRestClient, cache);
                Identity ultimateParentIdentity = termIdentity.getUltimateParentIdentity();
                return ultimateParentIdentity != null && !ultimateParentIdentity.getName().equals("Classifications");
            } catch (IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        }
        return false;
    }

    /**
     * Search for GlossaryTerms by looking for a term not contained under the Classifications category.
     *
     * @return IGCSearchConditionSet - the IGC search criteria to find GlossaryTerm entities
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria() {
        IGCSearchCondition notClassification = new IGCSearchCondition("category_path.name", "=", "Classifications", true);
        IGCSearchConditionSet conditions = new IGCSearchConditionSet(notClassification);
        conditions.setMatchAnyCondition(false);
        return conditions;
    }

}
