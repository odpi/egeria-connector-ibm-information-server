/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
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

    private CategoryAnchorMapper() {
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
    }

    /**
     * Retrieve the root-level category (Glossary) from an arbitrary sub-level category.
     *
     * @param category the category to traverse upwards from to the root-level ancestor
     * @param igcRestClient REST connectivity to the IGC environment
     * @return Reference - the host asset
     */
    @Override
    public List<Reference> getProxyOneAssetFromAsset(Reference category, IGCRestClient igcRestClient) {
        String assetType = category.getType();
        ArrayList<Reference> asList = new ArrayList<>();
        if (assetType.equals("category")) {
            Identity catIdentity = category.getIdentity(igcRestClient);
            if (catIdentity != null) {
                Identity rootIdentity = catIdentity.getUltimateParentIdentity();
                Reference root = igcRestClient.getAssetRefById(rootIdentity.getRid());
                if (root != null) {
                    asList.add(root);
                } else {
                    if (log.isErrorEnabled()) { log.error("Unable to find root-level category with identity: {}", rootIdentity); }
                }
            } else {
                if (log.isDebugEnabled()) { log.debug("Already at a root-level category, returning as-is: {}", category); }
                asList.add(category);
            }
        } else {
            if (log.isWarnEnabled()) { log.warn("Not a category asset, just returning as-is: {}", category); }
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
     * @param fromIgcObject the category from which to traverse upwards / downwards
     * @param userId the user requesting the relationships
     */
    @Override
    public void addMappedOMRSRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                           List<Relationship> relationships,
                                           Reference fromIgcObject,
                                           String userId) {

        String assetType = IGCRestConstants.getAssetTypeForSearch(fromIgcObject.getType());

        if (assetType.equals("category")) {

            IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
            OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
            RelationshipDef relationshipDef = (RelationshipDef) repositoryHelper.getTypeDefByName(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    "CategoryAnchor");

            if (GlossaryMapper.isGlossary(igcRestClient, fromIgcObject)) {
                if (log.isDebugEnabled()) { log.debug("Looking for all offspring categories from: {}", fromIgcObject); }
                // We are already at the glossary-level category, so we need to get all the children categories
                IGCSearchCondition byCatPath = new IGCSearchCondition("category_path", "=", fromIgcObject.getId());
                IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(byCatPath);
                IGCSearch igcSearch = new IGCSearch("category",
                        IGCRestConstants.getModificationProperties().toArray(new String[0]),
                        conditionSet);
                ItemList<Category> children = igcRestClient.search(igcSearch);
                if (children != null) {
                    children.getAllPages(igcRestClient);
                    if (log.isDebugEnabled()) { log.debug(" ... found a total of {} offspring categories.", children.getItems().size()); }
                    for (Category child : children.getItems()) {
                        try {
                            Relationship relationship = getMappedRelationship(
                                    igcomrsRepositoryConnector,
                                    CategoryAnchorMapper.getInstance(null),
                                    relationshipDef,
                                    fromIgcObject,
                                    child,
                                    "subcategories",
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
            } else {
                // We are at a child category, so we need to get the ultimate root-level category
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
            }
        } else {
            if (log.isWarnEnabled()) { log.warn("Found unexpected asset type during relationship mapping: {}", fromIgcObject); }
        }

    }

}
