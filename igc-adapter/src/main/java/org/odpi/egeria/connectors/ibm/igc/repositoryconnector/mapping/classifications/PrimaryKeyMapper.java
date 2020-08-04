/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.CandidateKey;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DatabaseColumn;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.KeyPatternMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Singleton defining the mapping to the OMRS "PrimaryKey" classification.
 */
public class PrimaryKeyMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(ConfidentialityMapper.class);

    private static class Singleton {
        private static final PrimaryKeyMapper INSTANCE = new PrimaryKeyMapper();
    }
    public static PrimaryKeyMapper getInstance(IGCVersionEnum version) {
        return PrimaryKeyMapper.Singleton.INSTANCE;
    }

    protected PrimaryKeyMapper() {
        super(
                "database_column",
                "defined_primary_key",
                "RelationalColumn",
                "PrimaryKey"
        );
        addIgcRelationshipProperty("selected_primary_key");
        addMappedOmrsProperty("name");
        addLiteralPropertyMapping("keyPattern", KeyPatternMapper.getInstance(null).getEnumMappingByIgcValue(""));
    }

    /**
     * Implements the "PrimaryKey" OMRS classification for IGC database_column assets.
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

        final String methodName = "addMappedOMRSClassifications";
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Retrieve all assigned_to_terms relationships from this IGC object
        if (fromIgcObject instanceof DatabaseColumn) {
            DatabaseColumn dbColumn = (DatabaseColumn) fromIgcObject;
            Boolean bSelectedPK = dbColumn.getSelectedPrimaryKey();
            ItemList<CandidateKey> definedPK = dbColumn.getDefinedPrimaryKey();

            // If there are no defined PKs, setup a classification only if the user has selected
            // this column as a primary key
            if (definedPK.getItems().isEmpty()) {
                if (bSelectedPK) {
                    try {
                        InstanceProperties classificationProperties = repositoryHelper.addStringPropertyToInstance(
                                repositoryName,
                                null,
                                "name",
                                fromIgcObject.getName(),
                                methodName
                        );
                        Classification classification = getMappedClassification(
                                igcomrsRepositoryConnector,
                                classificationProperties,
                                fromIgcObject,
                                userId
                        );
                        classifications.add(classification);
                    } catch (RepositoryErrorException e) {
                        log.error("Unable to create classification.", e);
                    }
                }
            } else {

                // Otherwise setup primary key classifications for each defined candidate key
                List<CandidateKey> allCandidateKeys = igcRestClient.getAllPages("defined_primary_key", definedPK);
                for (CandidateKey candidateKey : allCandidateKeys) {

                    try {
                        InstanceProperties classificationProperties = repositoryHelper.addStringPropertyToInstance(
                                repositoryName,
                                null,
                                "name",
                                candidateKey.getName(),
                                methodName
                        );
                        Classification classification = getMappedClassification(
                                igcomrsRepositoryConnector,
                                classificationProperties,
                                fromIgcObject,
                                userId
                        );
                        classifications.add(classification);
                    } catch (RepositoryErrorException e) {
                        log.error("Unable to create classification.", e);
                    }

                }

            }
        }

    }

    /**
     * Search for PrimaryKey by looking for either a defined or selected primary key in IGC.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param matchProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     * @throws FunctionNotSupportedException when a regular expression is used for the search which is not supported
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                      String repositoryName,
                                                      SearchProperties matchProperties) throws FunctionNotSupportedException {

        final String methodName = "getIGCSearchCriteria";

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "selected_primary_key",
                "=",
                "true"
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);

        if (matchProperties != null) {
            IGCSearchConditionSet byName = getConditionsForProperties(matchProperties, repositoryHelper, repositoryName, methodName);
            if (byName.size() > 0) {
                igcSearchConditionSet.addNestedConditionSet(byName);
                igcSearchConditionSet.setMatchAnyCondition(true);
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
                if (propertyName.equals("name")) {
                    if (operator.equals(PropertyComparisonOperator.LIKE) || operator.equals(PropertyComparisonOperator.EQ)) {
                        IGCSearchCondition byName = IGCRepositoryHelper.getRegexSearchCondition(
                                repositoryHelper,
                                repositoryName,
                                methodName,
                                "defined_primary_key.name",
                                condition.getValue().valueAsString()
                        );
                        set.addCondition(byName);
                    }
                } else if (propertyName.equals("keyPattern")) {
                    InstancePropertyValue value = condition.getValue();
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
                } else {
                    // There are no other valid properties, so in case any others are requested force no results
                    set.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                }
            }
        }
        IGCRepositoryHelper.setConditionsFromMatchCriteria(set, matchProperties.getMatchCriteria());

        return set;

    }

}
