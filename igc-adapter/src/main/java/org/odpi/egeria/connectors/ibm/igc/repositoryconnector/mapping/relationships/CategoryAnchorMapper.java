/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
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
 * Singleton to map the OMRS "CategoryAnchor" relationship between IGC "category" and "category" assets.
 */
public class CategoryAnchorMapper extends RelationshipMapping {

    private static final Logger log = LoggerFactory.getLogger(CategoryAnchorMapper.class);

    private static class Singleton {
        private static final CategoryAnchorMapper INSTANCE = new CategoryAnchorMapper();
    }
    public static CategoryAnchorMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected CategoryAnchorMapper() {
        super(
                "category",
                "category",
                "subcategories",
                "parent_category",
                "CategoryAnchor",
                "anchor",
                "categories",
                GlossaryMapper.IGC_RID_PREFIX,
                null
        );
        setOptimalStart(OptimalStart.CUSTOM);
        setContainedType(ContainedType.TWO);
    }

    /**
     * Retrieve the root-level category (Glossary) from an arbitrary sub-level category.
     *
     * @param category the category to traverse upwards from to the root-level ancestor
     * @param igcRestClient REST connectivity to the IGC environment
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return Reference - the host asset
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    public List<Reference> getProxyOneAssetFromAsset(Reference category, IGCRestClient igcRestClient, ObjectCache cache) throws RepositoryErrorException {
        final String methodName = "getProxyOneAssetFromAsset";
        String assetType = category.getType();
        ArrayList<Reference> asList = new ArrayList<>();
        if (assetType.equals("category")) {
            try {
                Identity catIdentity = category.getIdentity(igcRestClient, cache);
                if (catIdentity != null) {
                    Identity rootIdentity = catIdentity.getUltimateParentIdentity();
                    Reference root = new Reference(rootIdentity.getName(), rootIdentity.getAssetType(), rootIdentity.getRid());
                    root.setContext(Collections.emptyList());
                    asList.add(root);
                } else {
                    log.debug("Already at a root-level category, returning as-is: {} of type {}", category.getName(), category.getType());
                    asList.add(category);
                }
            } catch (IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        } else {
            log.warn("Not a category asset, just returning as-is: {} of type {}", category.getName(), category.getType());
            asList.add(category);
        }
        return asList;
    }

    // TODO: need to implement getFromProxyTwo ?

    /**
     * Custom implementation of the relationship between a GlossaryCategory (category) and a Glossary (category).
     * The relationship itself in IGC is complicated, since 'category' is used for both types, and we therefore
     * potentially need to make multiple hops upwards from an arbitrary GlossaryCategory to determine the root-level
     * Glossary.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the relationships to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the category from which to traverse upwards / downwards
     * @param toIgcObject the category from which to traverse downwards / upwards (or null if not known)
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user requesting the relationships
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    public void addMappedOMRSRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                           List<Relationship> relationships,
                                           ObjectCache cache,
                                           Reference fromIgcObject,
                                           Reference toIgcObject,
                                           int fromRelationshipElement,
                                           SequencingOrder sequencingOrder,
                                           int pageSize,
                                           String userId) throws RepositoryErrorException {

        final String methodName = "addMappedOMRSRelationships";
        String assetType = IGCRestConstants.getAssetTypeForSearch(fromIgcObject.getType());

        if (assetType.equals("category")) {

            IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
            OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
            RelationshipDef relationshipDef = (RelationshipDef) repositoryHelper.getTypeDefByName(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    "CategoryAnchor");

            if (GlossaryMapper.isGlossary(igcRestClient, cache, fromIgcObject)) {

                IGCSearchConditionSet conditionSet = new IGCSearchConditionSet();
                if (toIgcObject == null) {
                    log.debug("Looking for all offspring categories from: {} of type {}", fromIgcObject.getName(), fromIgcObject.getType());
                    // We are already at the glossary-level category, so we need to get all the children categories
                    IGCSearchCondition byCatPath = new IGCSearchCondition("category_path", "=", fromIgcObject.getId());
                    conditionSet.addCondition(byCatPath);
                } else {
                    // We are already at the glossary-level category, and have a single other category object, create
                    // category anchor for just that single category
                    log.debug("Looking for single category for: {} of type {}", toIgcObject.getName(), toIgcObject.getType());
                    IGCSearchCondition byCatPath = new IGCSearchCondition("category_path", "=", fromIgcObject.getId());
                    IGCSearchCondition byCategory = new IGCSearchCondition("_id", "=", toIgcObject.getId());
                    conditionSet.addCondition(byCatPath);
                    conditionSet.addCondition(byCategory);
                    conditionSet.setMatchAnyCondition(false);
                }
                IGCSearch igcSearch = new IGCSearch("category",
                        IGCRestConstants.getModificationProperties(),
                        conditionSet);
                IGCSearchSorting sorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
                if (sorting != null) {
                    igcSearch.addSortingCriteria(sorting);
                }
                if (pageSize > 0) {
                    igcSearch.setPageSize(fromRelationshipElement + pageSize);
                }
                try {
                    ItemList<Category> children = igcRestClient.search(igcSearch);
                    if (children != null) {
                        if (pageSize == 0) {
                            List<Category> allPages = igcRestClient.getAllPages(null, children);
                            children.setAllPages(allPages);
                        }
                        log.debug(" ... found a total of {} offspring categories.", children.getItems().size());
                        for (Category child : children.getItems()) {
                            Relationship relationship = getMappedRelationship(
                                    igcomrsRepositoryConnector,
                                    CategoryAnchorMapper.getInstance(null),
                                    relationshipDef,
                                    cache,
                                    fromIgcObject,
                                    child,
                                    "subcategories",
                                    userId,
                                    null,
                                    true
                            );
                            relationships.add(relationship);
                        }
                    }
                } catch (IGCException e) {
                    raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }
            } else {
                // We are at a child category, so we need to get the ultimate root-level category
                try {
                    Identity catIdentity = fromIgcObject.getIdentity(igcRestClient, cache);
                    Identity rootIdentity = catIdentity.getUltimateParentIdentity();
                    Reference root = igcRestClient.getAssetWithSubsetOfProperties(
                            rootIdentity.getRid(),
                            rootIdentity.getAssetType(),
                            IGCRestConstants.getModificationProperties());
                    if (root != null) {
                        log.debug("Mapping ultimate parent category from: {} of type {}", fromIgcObject.getName(), fromIgcObject.getType());
                        Relationship relationship = getMappedRelationship(
                                igcomrsRepositoryConnector,
                                CategoryAnchorMapper.getInstance(null),
                                relationshipDef,
                                cache,
                                root,
                                fromIgcObject,
                                "parent_category",
                                userId,
                                null,
                                true
                        );
                        relationships.add(relationship);
                    } else {
                        log.error("Unable to find root-level category with identity: {}", rootIdentity);
                    }
                } catch (IGCException e) {
                    raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }
            }
        } else {
            log.warn("Found unexpected asset type during relationship mapping: {}", fromIgcObject);
        }

    }

    /**
     * Avoid creating an anchor relationship between the glossary and itself (as a category).
     *
     * @param igcomrsRepositoryConnector connection to the IGC environment
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param oneObject the IGC object to consider for inclusion on one end of the relationship
     * @param otherObject the IGC object to consider for inclusion on the other end of the relationship
     * @return boolean
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    public boolean includeRelationshipForIgcObjects(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    ObjectCache cache,
                                                    Reference oneObject,
                                                    Reference otherObject) throws RepositoryErrorException {
        log.debug("Considering inclusion of objects: {} ({}) and {} ({})", oneObject.getName(), oneObject.getType(), otherObject.getName(), otherObject.getType());
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        return (GlossaryMapper.isGlossary(igcRestClient, cache, oneObject) && otherObject.getType().equals("category") && !GlossaryMapper.isGlossary(igcRestClient, cache, otherObject))
                || (!GlossaryMapper.isGlossary(igcRestClient, cache, oneObject) && oneObject.getType().equals("category") && GlossaryMapper.isGlossary(igcRestClient, cache, otherObject));
    }

}
