/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Singleton defining the mapping to the OMRS "SpineObject/Attribute" classification.
 */
public class Spine_Mapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(Spine_Mapper.class);

    protected Spine_Mapper(String omrsClassificationType) {
        super(
                "term",
                "referencing_categories",
                "GlossaryTerm",
                omrsClassificationType
        );
    }

    /**
     * Implements the SpineObject/Attribute classification for IGC 'term' assets. Any term with a "SpineObject/Attribute"
     * referencing category will be considered to be a Spine Object / Attribute (and therefore be given a
     * SpineObject/Attribute classification).
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param classifications the list of classifications to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the IGC object for which the classification should exist
     * @param userId the user requesting the mapped classifications
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             ObjectCache cache,
                                             Reference fromIgcObject,
                                             String userId) {

        if (fromIgcObject instanceof Term) {
            ItemList<Category> candidates = ((Term) fromIgcObject).getReferencingCategories();

            if (candidates != null) {

                // This is likely to be a NOOP in most circumstances, otherwise it may be faster to do an explicit search
                // with a full set of criteria (referencing category name, and its parent category under Classifications)
                List<Category> allCandidates = igcomrsRepositoryConnector.getIGCRestClient().getAllPages("referencing_categories", candidates);
                boolean foundSpine = false;
                for (Category candidate : allCandidates) {
                    if (candidate.getName().equals(getOmrsClassificationType())) {
                        foundSpine = true;
                        break;
                    }
                }

                if (foundSpine) {
                    try {
                        Classification classification = getMappedClassification(
                                igcomrsRepositoryConnector,
                                null,
                                fromIgcObject,
                                userId
                        );
                        classifications.add(classification);
                    } catch (RepositoryErrorException e) {
                        log.error("Unable to map {} classification.", getOmrsClassificationType(), e);
                    }
                }

            }
        }

    }

    /**
     * Search for SpineObject/Attribute by looking at referencing category of the term being "SpineObject/Attribute"
     * category. (There are no properties on the SpineObject/Attribute classification, so if any are specified and
     * the matchCriteria within them is anything other than NONE, we should return no results.)
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param matchProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                      String repositoryName,
                                                      SearchProperties matchProperties) {

        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();
        if (matchProperties == null || matchProperties.getMatchCriteria().equals(MatchCriteria.NONE)) {
            IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                    "referencing_categories.name",
                    "=",
                    getOmrsClassificationType()
            );
            igcSearchConditionSet.addCondition(igcSearchCondition);
        } else {
            igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
        }
        igcSearchConditionSet.setMatchAnyCondition(false);
        return igcSearchConditionSet;

    }

}
