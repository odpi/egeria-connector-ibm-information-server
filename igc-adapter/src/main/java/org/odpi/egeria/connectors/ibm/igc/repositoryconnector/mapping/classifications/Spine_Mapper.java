/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
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
     * @param igcomrsRepositoryConnector
     * @param classifications
     * @param fromIgcObject
     * @param userId
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             Reference fromIgcObject,
                                             String userId) {

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        ItemList<Reference> candidates = (ItemList<Reference>) igcRestClient.getPropertyByName(fromIgcObject, "referencing_categories");

        if (candidates != null) {

            // This is likely to be a NOOP in most circumstances, otherwise it may be faster to do an explicit search
            // with a full set of criteria (referencing category name, and its parent category under Classifications)
            candidates.getAllPages(igcomrsRepositoryConnector.getIGCRestClient());
            boolean foundSpine = false;
            for (Reference candidate : candidates.getItems()) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void addClassificationToIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                            Reference igcEntity,
                                            String entityGUID,
                                            InstanceProperties initialProperties,
                                            String userId) throws RepositoryErrorException {

        final String methodName = "addClassificationToIGCAsset";

        Map<String, InstancePropertyValue> classificationProperties = null;
        if (initialProperties != null) {
            classificationProperties = initialProperties.getInstanceProperties();
        }

        if (classificationProperties != null && !classificationProperties.isEmpty()) {

            if (log.isErrorEnabled()) { log.error("{} classification has no properties, yet properties were included: {}", getOmrsClassificationType(), initialProperties); }
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_EXCEEDS_REPOSITORY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    getOmrsClassificationType(),
                    getIgcAssetType()
            );
            throw new RepositoryErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );

        } else {

            IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

            IGCSearchCondition findCategory = new IGCSearchCondition("name", "=", getOmrsClassificationType());
            IGCSearchCondition findClassifications = new IGCSearchCondition("parent_category.name", "=", "Classifications");
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(findCategory);
            igcSearchConditionSet.addCondition(findClassifications);
            igcSearchConditionSet.setMatchAnyCondition(false);

            IGCSearch igcSearch = new IGCSearch("category", igcSearchConditionSet);
            ItemList<Category> results = igcRestClient.search(igcSearch);
            if (results == null || results.getPaging().getNumTotal() < 1) {
                log.error("No Classifications/{} category found -- cannot continue.", getOmrsClassificationType());
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_NOT_FOUND;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        getOmrsClassificationType(),
                        entityGUID
                );
                throw new RepositoryErrorException(
                        errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction()
                );
            } else if (results.getPaging().getNumTotal() > 1) {
                log.warn("Found multiple {} categories, taking the first.", getOmrsClassificationType());
            }
            String spineCatRid = results.getItems().get(0).getId();
            IGCUpdate igcUpdate = new IGCUpdate(igcEntity.getId());
            igcUpdate.addRelationship("referencing_categories", spineCatRid);
            igcUpdate.setRelationshipUpdateMode(IGCUpdate.UpdateMode.APPEND);
            if (!igcRestClient.update(igcUpdate)) {
                if (log.isErrorEnabled()) { log.error("Unable to update entity {} to add classification {}.", entityGUID, getOmrsClassificationType()); }
            }

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeClassificationFromIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                 Reference igcAsset,
                                                 String entityGUID,
                                                 String userId)
            throws RepositoryErrorException {
        final String methodName = "removeClassificationFromIGCAsset";
        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_NOT_EDITABLE;
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                getOmrsClassificationType(),
                entityGUID
        );
        throw new RepositoryErrorException(
                errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction()
        );
    }

}
