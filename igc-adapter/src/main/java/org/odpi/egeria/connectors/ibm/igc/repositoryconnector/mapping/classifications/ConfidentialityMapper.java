/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
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
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
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

    protected ConfidentialityMapper() {
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

        final String methodName = "addMappedOMRSClassification";

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Retrieve all assigned_to_terms relationships from this IGC object
        if (fromIgcObject instanceof MainObject) {
            ItemList<Term> assignedToTerms = ((MainObject) fromIgcObject).getAssignedToTerms();
            if (assignedToTerms != null) {
                List<Term> allAssignedTerms = igcRestClient.getAllPages("assigned_to_terms", assignedToTerms);

                // For each such relationship:
                for (Term assignedTerm : allAssignedTerms) {

                    // Retrieve the identity characteristics (ie. the parent category) of the related term
                    Identity termIdentity = assignedTerm.getIdentity(igcRestClient, cache);
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
     * 'level' classification property is used from matchProperties, as no other properties are implemented in IGC.)
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param matchProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     * @throws FunctionNotSupportedException when an invalid enumeration is requested
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                      String repositoryName,
                                                      SearchProperties matchProperties) throws FunctionNotSupportedException {

        final String methodName = "getIGCSearchCriteria";

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "assigned_to_terms.parent_category.name",
                "=",
                "Confidentiality"
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);

        if (matchProperties != null) {
            IGCSearchConditionSet byProperties = getConditionsForProperties(matchProperties, repositoryHelper, repositoryName, methodName);
            if (byProperties.size() > 0) {
                igcSearchConditionSet.addNestedConditionSet(byProperties);
                igcSearchConditionSet.setMatchAnyCondition(false);
            }
        }

        return igcSearchConditionSet;

    }

    private IGCSearchConditionSet getConditionsForProperties(SearchProperties matchProperties,
                                                             OMRSRepositoryHelper repositoryHelper,
                                                             String repositoryName,
                                                             String methodName) throws FunctionNotSupportedException {

        IGCSearchConditionSet set = new IGCSearchConditionSet();

        List<PropertyCondition> propertyConditions = matchProperties.getConditions();
        for (PropertyCondition condition : propertyConditions) {
            SearchProperties nestedProperties = condition.getNestedConditions();
            if (nestedProperties != null) {
                IGCSearchConditionSet nestedSet = getConditionsForProperties(nestedProperties, repositoryHelper, repositoryName, methodName);
                IGCRepositoryHelper.setConditionsFromMatchCriteria(nestedSet, nestedProperties.getMatchCriteria());
                set.addNestedConditionSet(nestedSet);
            } else {
                String propertyName = condition.getProperty();
                PropertyComparisonOperator operator = condition.getOperator();
                InstancePropertyValue value = condition.getValue();
                switch (propertyName) {
                    case "status":
                        if (value instanceof EnumPropertyValue) {
                            EnumPropertyValue enumValue = (EnumPropertyValue) value;
                            // Only enumeration mapped is 99, anything else should result in no results
                            if (enumValue.getOrdinal() != 99) {
                                set.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                            }
                        } else if (value == null) {
                            // Only valid combination for which we should return results in this case is asking for a
                            // non-null keyPattern
                            if (!operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                set.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                            }
                        } else {
                            throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_ENUMERATION.getMessageDefinition(propertyName),
                                    this.getClass().getName(),
                                    methodName);
                        }
                        break;
                    case "confidence":
                        if (value instanceof PrimitivePropertyValue) {
                            PrimitivePropertyValue levelValue = (PrimitivePropertyValue) value;
                            if (levelValue.getPrimitiveDefCategory().equals(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT)) {
                                // Confidence can only ever match 100, so any mismatch in operators or values should give no results
                                Integer level = (Integer) levelValue.getPrimitiveValue();
                                boolean includeResults = (level == null && operator.equals(PropertyComparisonOperator.NOT_NULL))
                                        || (level != null
                                            && ((level == 100 && (operator.equals(PropertyComparisonOperator.EQ) || operator.equals(PropertyComparisonOperator.GTE) || operator.equals(PropertyComparisonOperator.LTE)))
                                                || (level < 100 && (operator.equals(PropertyComparisonOperator.GT) || operator.equals(PropertyComparisonOperator.GTE)))
                                                || (level > 100 && (operator.equals(PropertyComparisonOperator.LT) || operator.equals(PropertyComparisonOperator.LTE)))));
                                if (!includeResults) {
                                    set.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                }
                            } else {
                                throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition(propertyName, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT.getName()),
                                        this.getClass().getName(),
                                        methodName);
                            }
                        } else {
                            throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition(propertyName, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT.getName()),
                                    this.getClass().getName(),
                                    methodName);
                        }
                        break;
                    case "steward":
                    case "source":
                    case "notes":
                        // These can only be null, if anything else is requested then no results should be returned
                        if (!operator.equals(PropertyComparisonOperator.IS_NULL)) {
                            set.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                        }
                        break;
                    case "level":
                        if (value instanceof PrimitivePropertyValue) {
                            PrimitivePropertyValue levelValue = (PrimitivePropertyValue) value;
                            if (levelValue.getPrimitiveDefCategory().equals(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT)) {
                                Integer level = (Integer) levelValue.getPrimitiveValue();
                                String levelAsString = level.toString() + " ";
                                IGCSearchCondition propertyCondition = new IGCSearchCondition(
                                        "assigned_to_terms.name",
                                        "like {0}%",
                                        levelAsString
                                );
                                set.addCondition(propertyCondition);
                            } else {
                                throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition(propertyName, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT.getName()),
                                        this.getClass().getName(),
                                        methodName);
                            }
                        } else {
                            throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition(propertyName, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT.getName()),
                                    this.getClass().getName(),
                                    methodName);
                        }
                        break;
                    default:
                        // There are no other valid properties, so in case any others are requested force no results
                        set.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                        break;
                }
            }
        }
        IGCRepositoryHelper.setConditionsFromMatchCriteria(set, matchProperties.getMatchCriteria());

        return set;

    }

}
