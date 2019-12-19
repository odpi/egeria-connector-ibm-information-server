/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.MainObject;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.GovernanceClassificationStatusMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Singleton defining the mapping to the OMRS "Confidentiality" classification.
 */
public class ConfidentialityMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(ConfidentialityMapper.class);

    private static class Singleton {
        private static final ConfidentialityMapper INSTANCE = new ConfidentialityMapper();
    }
    public static ConfidentialityMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ConfidentialityMapper() {
        super(
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                "assigned_to_terms",
                "Referenceable",
                "Confidentiality"
        );
        addLiteralPropertyMapping("status", GovernanceClassificationStatusMapper.getInstance(null).getEnumMappingByIgcValue(""));
        addLiteralPropertyMapping("confidence", 100);
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
        addLiteralPropertyMapping("notes", null);
        addMappedOmrsProperty("level");
        // Exclude IGC types that do not have 'assigned_to_terms'
        addExcludedIgcAssetType("connector");
        addExcludedIgcAssetType("data_connection");
        addExcludedIgcAssetType("group");
        addExcludedIgcAssetType("information_governance_policy");
        addExcludedIgcAssetType("label");
        addExcludedIgcAssetType("user");
    }

    /**
     * Implements the "Confidentiality" classification for IGC objects (by default we only apply to terms, but could
     * apply to any IGC asset type). We use the 'assigned_to_term' relationship from one term to any term
     * within a "Confidentiality" parent category to represent the "Confidentiality" classification in OMRS.
     * Therefore, any 'assigned_to_term' relationship on a term, where the assigned term is within a "Confidentiality"
     * parent category in IGC, will be mapped to a "Confidentiality" classification in OMRS.
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

        final String methodName = "addMappedOMRSClassification";

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Retrieve all assigned_to_terms relationships from this IGC object
        if (fromIgcObject instanceof MainObject) {
            ItemList<Term> assignedToTerms = ((MainObject) fromIgcObject).getAssignedToTerms();
            if (assignedToTerms != null) {
                assignedToTerms.getAllPages(igcRestClient);

                // For each such relationship:
                for (Term assignedTerm : assignedToTerms.getItems()) {

                    // Retrieve the identity characteristics (ie. the parent category) of the related term
                    Identity termIdentity = assignedTerm.getIdentity(igcRestClient);
                    Identity catIdentity = termIdentity.getParentIdentity();

                    // Only do something with the assigned term if its immediate parent category is named
                    // "Confidentiality"
                    if (catIdentity.toString().endsWith("Confidentiality")) {

                        InstanceProperties classificationProperties = new InstanceProperties();

                        String confidentialityName = assignedTerm.getName();
                        int spaceIndex = confidentialityName.indexOf(" ");
                        if (spaceIndex > 0) {

                            String level = confidentialityName.substring(0, spaceIndex);
                            try {
                                int parsedLevel = Integer.parseInt(level);
                                classificationProperties = igcomrsRepositoryConnector.getRepositoryHelper().addIntPropertyToInstance(
                                        igcomrsRepositoryConnector.getRepositoryName(),
                                        classificationProperties,
                                        "level",
                                        parsedLevel,
                                        methodName
                                );
                            } catch (NumberFormatException e) {
                                log.error("Unable to detect a level in the Confidentiality classification: {}", confidentialityName, e);
                            }
                            try {
                                Classification classification = getMappedClassification(
                                        igcomrsRepositoryConnector,
                                        classificationProperties,
                                        fromIgcObject,
                                        userId
                                );
                                classifications.add(classification);
                            } catch (RepositoryErrorException e) {
                                log.error("Unable to map Confidentiality classification.", e);
                            }

                        } else {
                            log.error("Unable to detect a level in the Confidentiality classification: {}", confidentialityName);
                        }

                    }

                }
            }
        }

    }

    /**
     * Search for Confidentiality by looking for a term assignment where the assigned term both sits under a
     * Confidentiality parent category and has a name matching the confidentiality level. (Note that only the
     * 'level' classification property is used from matchClassificationProperties, as no other properties are
     * implemented in IGC.)
     *
     * @param matchClassificationProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(InstanceProperties matchClassificationProperties) {

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "assigned_to_terms.parent_category.name",
                "=",
                "Confidentiality"
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);

        // We can only search by level, so we will ignore all other properties
        if (matchClassificationProperties != null) {
            Map<String, InstancePropertyValue> properties = matchClassificationProperties.getInstanceProperties();
            if (properties.containsKey("level")) {
                PrimitivePropertyValue levelValue = (PrimitivePropertyValue) properties.get("level");
                Integer level = (Integer) levelValue.getPrimitiveValue();
                String levelAsString = level.toString() + " ";
                IGCSearchCondition propertyCondition = new IGCSearchCondition(
                        "assigned_to_terms.name",
                        "like {0}%",
                        levelAsString
                );
                igcSearchConditionSet.addCondition(propertyCondition);
                igcSearchConditionSet.setMatchAnyCondition(false);
            }
        }

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

        if (classificationProperties == null || classificationProperties.isEmpty()) {

            log.error("Confidentiality classification requires the 'level' property for IGC.");
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

        } else if (classificationProperties.size() == 1 && classificationProperties.containsKey("level")) {

            // Goldilocks scenario - we have a 'level' and only a 'level'
            PrimitivePropertyValue levelValue = (PrimitivePropertyValue) classificationProperties.get("level");
            Integer level = (Integer) levelValue.getPrimitiveValue();
            String levelAsString = level.toString() + " ";

            IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

            // TODO: do we first need to find any other Confidentiality classification and remove that?
            //  (ie. they should probably be mutually-exclusive?)

            IGCSearchCondition findTerm = new IGCSearchCondition(
                    "name",
                    "like {0}%",
                    levelAsString
            );
            IGCSearchCondition inConfidentiality = new IGCSearchCondition(
                    "parent_category.name",
                    "=",
                    "Confidentiality"
            );
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(findTerm);
            igcSearchConditionSet.addCondition(inConfidentiality);
            igcSearchConditionSet.setMatchAnyCondition(false);

            IGCSearch igcSearch = new IGCSearch("term", igcSearchConditionSet);
            ItemList<Term> results = igcRestClient.search(igcSearch);
            if (results == null || results.getPaging().getNumTotal() < 1) {
                if (log.isErrorEnabled()) { log.error("No Confidentiality found with level: {}", levelAsString); }
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
                if (log.isWarnEnabled()) { log.warn("Found multiple Confidentiality terms matching {}, taking the first.", levelAsString); }
            }
            String confidentialityTermRid = results.getItems().get(0).getId();
            IGCUpdate igcUpdate = new IGCUpdate(igcEntity.getId());
            igcUpdate.addRelationship("assigned_to_terms", confidentialityTermRid);
            igcUpdate.setRelationshipUpdateMode(IGCUpdate.UpdateMode.APPEND);
            if (!igcRestClient.update(igcUpdate)) {
                if (log.isErrorEnabled()) { log.error("Unable to update entity {} to add classification {}.", entityGUID, getOmrsClassificationType()); }
            }

        } else {

            log.error("More than only a 'level' property was provided for the Confidentiality classification.");
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

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Retrieve all of the 'assigned_to_terms' relationships for this particular term
        IGCSearchCondition thisTerm = new IGCSearchCondition(
                "_id",
                "=",
                igcAsset.getId()
        );
        IGCSearchCondition relatedToConfidentiality = new IGCSearchCondition(
                "assigned_to_terms.parent_category.name",
                "=",
                "Confidentiality"
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(thisTerm);
        igcSearchConditionSet.addCondition(relatedToConfidentiality);
        igcSearchConditionSet.setMatchAnyCondition(false);

        IGCSearch igcSearch = new IGCSearch("term", igcSearchConditionSet);
        igcSearch.addProperty("assigned_to_terms");

        ItemList<Term> thisTermResults = igcRestClient.search(igcSearch);

        // Only continue if the term has some Confidentiality assigned
        if (thisTermResults != null && thisTermResults.getPaging().getNumTotal() > 0) {

            // search for all Confidentiality terms
            IGCSearchCondition inConfidentiality = new IGCSearchCondition(
                    "parent_category.name",
                    "=",
                    "Confidentiality"
            );
            igcSearchConditionSet = new IGCSearchConditionSet(inConfidentiality);
            igcSearch = new IGCSearch("term", igcSearchConditionSet);
            ItemList<Term> confidentialityTerms = igcRestClient.search(igcSearch);

            Set<String> confidentialityRids = new HashSet<>();
            if (confidentialityTerms != null) {
                confidentialityTerms.getAllPages(igcRestClient);
                for (Term confidentialityTerm : confidentialityTerms.getItems()) {
                    confidentialityRids.add(confidentialityTerm.getId());
                }
            }

            // cull the list of referencing categories to remove any under Confidentiality
            IGCUpdate igcUpdate = new IGCUpdate(igcAsset.getId());

            ItemList<Term> assignedToTerms = thisTermResults.getItems().get(0).getAssignedToTerms();
            assignedToTerms.getAllPages(igcRestClient);
            boolean bRemovedOne = false;
            for (Term assignedToTerm : assignedToTerms.getItems()) {
                String assignedRid = assignedToTerm.getId();
                if (confidentialityRids.contains(assignedRid)) {
                    // Drop any confidentiality relationships
                    bRemovedOne = true;
                    if (assignedToTerms.getPaging().getNumTotal() == 1) {
                        // If there is only this one relationship, we should explicitly null it
                        igcUpdate.addRelationship("assigned_to_terms", null);
                    }
                } else {
                    // Retain all other relationships
                    igcUpdate.addRelationship("assigned_to_terms", assignedRid);
                }
            }

            // if any change, update the referencing categories with a 'replace' semantic
            if (bRemovedOne) {
                igcUpdate.setRelationshipUpdateMode(IGCUpdate.UpdateMode.REPLACE);
                if (!igcRestClient.update(igcUpdate)) {
                    if (log.isErrorEnabled()) { log.error("Unable to update entity {} to remove classification {}.", entityGUID, getOmrsClassificationType()); }
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_ERROR_UNKNOWN;
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

        }

    }

}
