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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
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

}
