/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

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
     * @param fromIgcObject the IGC object for which the classification should exist
     * @param userId the user requesting the mapped classifications
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             Reference fromIgcObject,
                                             String userId) {

        if (fromIgcObject instanceof Term) {
            ItemList<Category> candidates = ((Term) fromIgcObject).getReferencingCategories();

            if (candidates != null) {

                // This is likely to be a NOOP in most circumstances, otherwise it may be faster to do an explicit search
                // with a full set of criteria (referencing category name, and its parent category under Classifications)
                candidates.getAllPages(igcomrsRepositoryConnector.getIGCRestClient());
                boolean foundSpine = false;
                for (Category candidate : candidates.getItems()) {
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
     * category. (There are no properties on the SpineObject/Attribute classification, so no need to even check the
     * provided matchClassificationProperties.)
     *
     * @param matchClassificationProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(InstanceProperties matchClassificationProperties) {

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "referencing_categories.name",
                "=",
                getOmrsClassificationType()
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        igcSearchConditionSet.setMatchAnyCondition(false);
        return igcSearchConditionSet;

    }

}
