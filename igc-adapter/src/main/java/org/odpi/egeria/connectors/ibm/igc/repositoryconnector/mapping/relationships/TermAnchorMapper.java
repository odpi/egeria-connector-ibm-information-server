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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
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

    protected TermAnchorMapper() {
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
        setContainedType(ContainedType.TWO);
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
                Reference root = new Reference(rootIdentity.getName(), rootIdentity.getAssetType(), rootIdentity.getRid());
                root.setContext(Collections.emptyList());
                asList.add(root);
            } else {
                log.error("Term has no identity: {}", term);
            }
        } else {
            log.warn("Not a term asset, just returning as-is: {}", term);
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
     * @param toIgcObject the category from which to traverse downwards / term from which to traverse upwards
     *                    (or null if not known)
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user requesting the relationships
     */
    @Override
    public void addMappedOMRSRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                           List<Relationship> relationships,
                                           Reference fromIgcObject,
                                           Reference toIgcObject,
                                           int fromRelationshipElement,
                                           SequencingOrder sequencingOrder,
                                           int pageSize,
                                           String userId) {

        String assetType = IGCRestConstants.getAssetTypeForSearch(fromIgcObject.getType());
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        RelationshipDef relationshipDef = (RelationshipDef) repositoryHelper.getTypeDefByName(
                igcomrsRepositoryConnector.getRepositoryName(),
                "TermAnchor");

        if (GlossaryMapper.isGlossary(igcRestClient, fromIgcObject)) {

            IGCSearchConditionSet conditionSet = new IGCSearchConditionSet();
            if (toIgcObject == null) {
                // If we have a glossary-level category, create term anchors for all of its child terms
                log.debug("Looking for all offspring terms from: {} of type {}", fromIgcObject.getName(), fromIgcObject.getType());
                // We are already at the glossary-level category, so we need to get all the children terms
                IGCSearchCondition byCatPath = new IGCSearchCondition("parent_category.category_path", "=", fromIgcObject.getId());
                IGCSearchCondition byParent = new IGCSearchCondition("parent_category", "=", fromIgcObject.getId());
                conditionSet.addCondition(byCatPath);
                conditionSet.addCondition(byParent);
                conditionSet.setMatchAnyCondition(true);
            } else {
                // If we have a glossary-level category and a single other term object, create term anchors for just
                // that single term
                log.debug("Looking for single terms for: {} of type {}", toIgcObject.getName(), toIgcObject.getType());
                IGCSearchCondition byCatPath = new IGCSearchCondition("parent_category.category_path", "=", fromIgcObject.getId());
                IGCSearchCondition byParent = new IGCSearchCondition("parent_category", "=", fromIgcObject.getId());
                IGCSearchCondition byTerm = new IGCSearchCondition("_id", "=", toIgcObject.getId());
                IGCSearchConditionSet inGlossary = new IGCSearchConditionSet(byCatPath);
                inGlossary.addCondition(byParent);
                inGlossary.setMatchAnyCondition(true);
                IGCSearchConditionSet byTermCS = new IGCSearchConditionSet(byTerm);
                conditionSet.addNestedConditionSet(byTermCS);
                conditionSet.addNestedConditionSet(inGlossary);
                conditionSet.setMatchAnyCondition(false);
            }

            IGCSearch igcSearch = new IGCSearch("term",
                    IGCRestConstants.getModificationProperties(),
                    conditionSet);
            IGCSearchSorting sorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
            if (sorting != null) {
                igcSearch.addSortingCriteria(sorting);
            }
            if (pageSize > 0) {
                igcSearch.setPageSize(fromRelationshipElement + pageSize);
            }
            ItemList<Term> terms = igcRestClient.search(igcSearch);
            if (terms != null) {
                if (pageSize == 0) {
                    List<Term> allPages = igcRestClient.getAllPages(null, terms);
                    terms.setAllPages(allPages);
                }
                log.debug(" ... found a total of {} offspring terms.", terms.getItems().size());
                for (Term term : terms.getItems()) {
                    try {
                        Relationship relationship = getMappedRelationship(
                                igcomrsRepositoryConnector,
                                TermAnchorMapper.getInstance(null),
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
                    IGCRestConstants.getModificationProperties());
            if (root != null) {
                log.debug("Mapping ultimate parent category from: {} of type {}", fromIgcObject.getName(), fromIgcObject.getType());
                try {
                    Relationship relationship = getMappedRelationship(
                            igcomrsRepositoryConnector,
                            TermAnchorMapper.getInstance(null),
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
            log.warn("Found unexpected asset type during relationship mapping: {}", fromIgcObject);
        }

    }

    /**
     * Avoid creating an anchor relationship between the glossary and itself (as a category).
     *
     * @param igcomrsRepositoryConnector connection to the IGC environment
     * @param oneObject the IGC object to consider for inclusion on one end of the relationship
     * @param otherObject the IGC object to consider for inclusion on the other end of the relationship
     * @return boolean
     */
    @Override
    public boolean includeRelationshipForIgcObjects(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    Reference oneObject,
                                                    Reference otherObject) {
        log.debug("Considering inclusion of objects: {} ({}) and {} ({})", oneObject.getName(), oneObject.getType(), otherObject.getName(), otherObject.getType());
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        return (GlossaryMapper.isGlossary(igcRestClient, oneObject) && otherObject.getType().equals("term"))
                || (oneObject.getType().equals("term") && GlossaryMapper.isGlossary(igcRestClient, otherObject));
    }

}
