/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton to map the OMRS "TermAnchor" relationship between IGC "category" and "term" assets.
 */
public class TermAnchorMapper extends RelationshipMapping {

    private static final Logger log = LoggerFactory.getLogger(TermAnchorMapper.class);

    private static class Singleton {
        private static final TermAnchorMapper INSTANCE = new TermAnchorMapper();
    }
    public static TermAnchorMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TermAnchorMapper() {
        super(
                "category",
                "term",
                "terms",
                "parent_category",
                "TermAnchor",
                "anchor",
                "terms",
                GlossaryMapper.IGC_RID_PREFIX,
                null
        );
        setOptimalStart(OptimalStart.CUSTOM);
    }

    /**
     * Retrieve the root-level category (Glossary) from an arbitrary sub-level parent_category.
     *
     * @param term the term to traverse upwards from to the root-level ancestor
     * @param igcRestClient REST connectivity to the IGC environment
     * @return Reference - the host asset
     */
    @Override
    public List<Reference> getProxyOneAssetFromAsset(Reference term, IGCRestClient igcRestClient) {
        String assetType = term.getType();
        ArrayList<Reference> asList = new ArrayList<>();
        if (assetType.equals("term")) {
            Identity termIdentity = term.getIdentity(igcRestClient);
            if (termIdentity != null) {
                Identity rootIdentity = termIdentity.getUltimateParentIdentity();
                Reference root = igcRestClient.getAssetRefById(rootIdentity.getRid());
                if (root != null) {
                    asList.add(root);
                } else {
                    if (log.isErrorEnabled()) { log.error("Unable to find root-level category with identity: {}", rootIdentity); }
                }
            } else {
                if (log.isErrorEnabled()) { log.error("Term has no identity: {}", term); }
            }
        } else {
            if (log.isWarnEnabled()) { log.warn("Not a term asset, just returning as-is: {}", term); }
            asList.add(term);
        }
        return asList;
    }

    // TODO: need to implement getFromProxyTwo ?

    /**
     * Custom implementation of the relationship between a GlossaryTerm (term) and a Glossary (category).
     * The relationship itself in IGC is complicated, since 'category' is used for both Glossary and GlossaryCategory
     * types, and we therefore potentially need to make multiple hops upwards from the GlossaryTerm to determine the
     * root-level Glossary.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the relationships to which to add
     * @param fromIgcObject the term from which to traverse upwards / category from which to traverse downwards
     * @param userId the user requesting the relationships
     */
    @Override
    public void addMappedOMRSRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                           List<Relationship> relationships,
                                           Reference fromIgcObject,
                                           String userId) {

        String assetType = IGCRestConstants.getAssetTypeForSearch(fromIgcObject.getType());
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        RelationshipDef relationshipDef = (RelationshipDef) repositoryHelper.getTypeDefByName(
                igcomrsRepositoryConnector.getRepositoryName(),
                "TermAnchor");

        if (GlossaryMapper.isGlossary(igcRestClient, fromIgcObject)) {

            // If we have a glossary-level category, create term anchors for all of its child terms
            if (log.isDebugEnabled()) { log.debug("Looking for all offspring terms from: {}", fromIgcObject); }
            // We are already at the glossary-level category, so we need to get all the children terms
            IGCSearchCondition byCatPath = new IGCSearchCondition("parent_category.category_path", "=", fromIgcObject.getId());
            IGCSearchCondition byParent = new IGCSearchCondition("parent_category", "=", fromIgcObject.getId());
            IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(byCatPath);
            conditionSet.addCondition(byParent);
            conditionSet.setMatchAnyCondition(true);
            IGCSearch igcSearch = new IGCSearch("term",
                    IGCRestConstants.getModificationProperties().toArray(new String[0]),
                    conditionSet);
            ItemList<Term> terms = igcRestClient.search(igcSearch);
            if (terms != null) {
                terms.getAllPages(igcRestClient);
                if (log.isDebugEnabled()) { log.debug(" ... found a total of {} offspring terms.", terms.getItems().size()); }
                for (Term term : terms.getItems()) {
                    try {
                        Relationship relationship = getMappedRelationship(
                                igcomrsRepositoryConnector,
                                CategoryAnchorMapper.getInstance(null),
                                relationshipDef,
                                fromIgcObject,
                                term,
                                "terms",
                                userId,
                                null,
                                true
                        );
                        relationships.add(relationship);
                    } catch (RepositoryErrorException e) {
                        log.error("Unable to map downward relationship.", e);
                    }
                }
            }
        } else if (assetType.equals("term")) {
            // We are at a child term, so we need to get the ultimate root-level category
            Identity catIdentity = fromIgcObject.getIdentity(igcRestClient);
            Identity rootIdentity = catIdentity.getUltimateParentIdentity();
            Reference root = igcRestClient.getAssetWithSubsetOfProperties(
                    rootIdentity.getRid(),
                    rootIdentity.getAssetType(),
                    IGCRestConstants.getModificationProperties().toArray(new String[0]));
            if (root != null) {
                if (log.isDebugEnabled()) { log.debug("Mapping ultimate parent category from: {}", fromIgcObject); }
                try {
                    Relationship relationship = getMappedRelationship(
                            igcomrsRepositoryConnector,
                            CategoryAnchorMapper.getInstance(null),
                            relationshipDef,
                            root,
                            fromIgcObject,
                            "parent_category",
                            userId,
                            null,
                            true
                    );
                    relationships.add(relationship);
                } catch (RepositoryErrorException e) {
                    log.error("Unable to map upward relationship.", e);
                }
            } else {
                log.error("Unable to find root-level category with identity: {}", rootIdentity);
            }
        } else {
            if (log.isWarnEnabled()) { log.warn("Found unexpected asset type during relationship mapping: {}", fromIgcObject); }
        }

    }

}
