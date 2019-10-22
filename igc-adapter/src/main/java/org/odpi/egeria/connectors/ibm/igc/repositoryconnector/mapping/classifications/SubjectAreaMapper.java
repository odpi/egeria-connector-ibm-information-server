/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.EntityNotKnownException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Singleton defining the mapping to the OMRS "SubjectArea" classification.
 */
public class SubjectAreaMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(SubjectAreaMapper.class);

    private static class Singleton {
        private static final SubjectAreaMapper INSTANCE = new SubjectAreaMapper();
    }
    public static SubjectAreaMapper getInstance(IGCVersionEnum version) {
        return SubjectAreaMapper.Singleton.INSTANCE;
    }

    private SubjectAreaMapper() {
        super(
                "category",
                "category_path",
                "GlossaryCategory",
                "SubjectArea"
        );
        addMappedOmrsProperty("name");
    }

    /**
     * Implements the SubjectArea classification for IGC 'category' assets. If a category comes
     * under any higher-level 'Subject Areas' category, such a category should be treated as subject area
     * and therefore will be mapped to a "SubjectArea" classification in OMRS.
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

        final String methodName = "addMappedOMRSClassifications";

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Retrieve all ancestral category relationships from this IGC object
        ReferenceList categoryPath = (ReferenceList) igcRestClient.getPropertyByName(fromIgcObject, "category_path");

        // Only need to continue if there are any parent categories in the path
        if (categoryPath != null) {
            categoryPath.getAllPages(igcRestClient);

            boolean isSubjectArea = false;

            // For each such relationship:
            for (Reference category : categoryPath.getItems()) {
                String categoryName = category.getName();
                // As soon as we find one that starts with Subject Area we can short-circuit out
                if (categoryName != null && categoryName.startsWith("Subject Area")) {
                    isSubjectArea = true;
                    break;
                }
            }

            if (isSubjectArea) {

                InstanceProperties classificationProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                        igcomrsRepositoryConnector.getRepositoryName(),
                        null,
                        "name",
                        fromIgcObject.getName(),
                        methodName
                );
                try {
                    Classification classification = getMappedClassification(
                            igcomrsRepositoryConnector,
                            classificationProperties,
                            fromIgcObject,
                            userId
                    );
                    classifications.add(classification);
                } catch (RepositoryErrorException e) {
                    log.error("Unable to map classification.", e);
                }

            }
        }

    }

    /**
     * Search for SubjectArea by looking at ancestral categories of the term being under a "Subject Area" category.
     *
     * @param matchClassificationProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(InstanceProperties matchClassificationProperties) {

        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "parent_category.parent_category.name",
                "=",
                "Subject Area"
        );
        IGCSearchConditionSet subjectAreaAncestor = new IGCSearchConditionSet(igcSearchCondition);
        IGCSearchCondition igcSearchCondition2 = new IGCSearchCondition(
                "parent_category.parent_category.parent_category.name",
                "=",
                "Subject Area"
        );
        subjectAreaAncestor.addCondition(igcSearchCondition2);
        subjectAreaAncestor.setMatchAnyCondition(true);

        IGCSearchConditionSet byName = new IGCSearchConditionSet();
        // We can only search by name, so we will ignore all other properties
        if (matchClassificationProperties != null) {
            Map<String, InstancePropertyValue> properties = matchClassificationProperties.getInstanceProperties();
            if (properties.containsKey("name")) {
                PrimitivePropertyValue name = (PrimitivePropertyValue) properties.get("name");
                String subjectAreaName = (String) name.getPrimitiveValue();
                IGCSearchCondition propertyCondition = new IGCSearchCondition(
                        "parent_category.name",
                        "=",
                        subjectAreaName
                );
                byName.addCondition(propertyCondition);
                IGCSearchCondition propertyCondition2 = new IGCSearchCondition(
                        "parent_category.parent_category.name",
                        "=",
                        subjectAreaName
                );
                byName.addCondition(propertyCondition2);
                byName.setMatchAnyCondition(true);
            }
        }
        if (byName.size() > 0) {
            igcSearchConditionSet.addNestedConditionSet(subjectAreaAncestor);
            igcSearchConditionSet.addNestedConditionSet(byName);
            igcSearchConditionSet.setMatchAnyCondition(false);
            return igcSearchConditionSet;
        } else {
            return subjectAreaAncestor;
        }

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

        if (classificationProperties == null || classificationProperties.isEmpty()) {

            log.error("SubjectArea classification requires the 'name' property for IGC.");
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_INSUFFICIENT_PROPERTIES;
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

        } else if (classificationProperties.size() == 1 && classificationProperties.containsKey("name")) {

            // Goldilocks scenario - we have a 'name' and only a 'name'
            String subjectAreaName = (String) ((PrimitivePropertyValue) classificationProperties.get("name")).getPrimitiveValue();

            IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

            // TODO: do we first need to find any other SubjectArea classification and remove that?
            //  (eg. should they be mutually-exclusive?)

            IGCSearchCondition findCategory = new IGCSearchCondition(
                    "name",
                    "=",
                    subjectAreaName
            );
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(findCategory);
            IGCSearchCondition inSubjectArea = new IGCSearchCondition(
                    "parent_category.name",
                    "=",
                    "Subject Areas"
            );
            IGCSearchCondition inSubjectArea2 = new IGCSearchCondition(
                    "parent_category.parent_category.name",
                    "=",
                    "Subject Areas"
            );
            IGCSearchConditionSet inSubjectAreas = new IGCSearchConditionSet(inSubjectArea);
            inSubjectAreas.addCondition(inSubjectArea2);
            inSubjectAreas.setMatchAnyCondition(true);
            igcSearchConditionSet.addNestedConditionSet(inSubjectAreas);
            igcSearchConditionSet.setMatchAnyCondition(false);

            IGCSearch igcSearch = new IGCSearch("category", igcSearchConditionSet);
            ReferenceList results = igcRestClient.search(igcSearch);
            if (results == null || results.getPaging().getNumTotal() < 1) {
                if (log.isErrorEnabled()) { log.error("No SubjectArea found with name: {}", subjectAreaName); }
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
                if (log.isWarnEnabled()) { log.warn("Found multiple SubjectArea categories matching {}, taking the first.", subjectAreaName); }
            }
            String subjectAreaCatRid = results.getItems().get(0).getId();
            IGCUpdate igcUpdate = new IGCUpdate(igcEntity.getId());
            igcUpdate.addRelationship("referencing_categories", subjectAreaCatRid);
            igcUpdate.setRelationshipUpdateMode(IGCUpdate.UpdateMode.APPEND);
            if (!igcRestClient.update(igcUpdate)) {
                if (log.isErrorEnabled()) { log.error("Unable to update entity {} to add classification {}.", entityGUID, getOmrsClassificationType()); }
            }

        } else {

            log.error("More than only a 'name' property was provided for the SubjectArea classification.");
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
