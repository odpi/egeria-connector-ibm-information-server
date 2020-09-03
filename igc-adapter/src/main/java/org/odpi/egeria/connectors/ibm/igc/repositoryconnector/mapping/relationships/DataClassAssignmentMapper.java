/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classification;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.DataClassAssignmentStatusMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Singleton to map the OMRS "DataClassAssignment" relationship for IGC "data_class" assets, including both
 * detected and selected classifications, and the additional details on IGC "classification" assets.
 */
public class DataClassAssignmentMapper extends RelationshipMapping {

    private static final Logger log = LoggerFactory.getLogger(DataClassAssignmentMapper.class);

    private static final String R_DATA_CLASS_ASSIGNMENT = "DataClassAssignment";
    private static final String P_THRESHOLD = "threshold";

    private static class Singleton {
        private static final DataClassAssignmentMapper INSTANCE = new DataClassAssignmentMapper();
    }
    public static DataClassAssignmentMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected DataClassAssignmentMapper() {
        super(
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                "data_class",
                "detected_classifications",
                "classified_assets_detected",
                "DataClassAssignment",
                "elementsAssignedToDataClass",
                "dataClassesAssignedToElement"
        );
        setOptimalStart(OptimalStart.CUSTOM);
        addAlternativePropertyFromOne("selected_classification");
        addAlternativePropertyFromTwo("classifications_selected");
        setRelationshipLevelIgcAsset("classification", "classifies_asset", "data_class");
        addMappedOmrsProperty("confidence");
        addMappedOmrsProperty(P_THRESHOLD);
        addMappedOmrsProperty("partialMatch");
        addMappedOmrsProperty("valueFrequency");
        addMappedOmrsProperty("status");
        addLiteralPropertyMapping("method", null);
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);
    }

    /**
     * Retrieve the classificationenabledgroup asset expected from a classification asset.
     *
     * @param relationshipAsset the classification asset to translate into a main_object asset
     * @param igcRestClient REST connectivity to the IGC environment
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return Reference - the classificationenabledgroup asset
     */
    @Override
    public List<Reference> getProxyOneAssetFromAsset(Reference relationshipAsset, IGCRestClient igcRestClient, ObjectCache cache) {
        String otherAssetType = relationshipAsset.getType();
        ArrayList<Reference> asList = new ArrayList<>();
        if (otherAssetType.equals("classification")) {
            Reference classifiedObj;
            Object co = igcRestClient.getPropertyByName(relationshipAsset, "classifies_asset");
            if (co == null || co.equals("") || co.equals("null")) {
                Reference classification = igcRestClient.getAssetById(relationshipAsset.getId(), cache);
                classifiedObj = (Reference) igcRestClient.getPropertyByName(classification, "classifies_asset");
            } else {
                classifiedObj = (Reference) co;
            }
            asList.add(classifiedObj);
        } else {
            log.debug("Not a classification asset, just returning as-is: {} of type {}", relationshipAsset.getName(), relationshipAsset.getType());
            asList.add(relationshipAsset);
        }
        return asList;
    }

    /**
     * Retrieve the data_class asset expected from a classification asset.
     *
     * @param relationshipAsset the classification asset to translate into a data_class asset
     * @param igcRestClient REST connectivity to the IGC environment
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return Reference - the data_class asset
     */
    @Override
    public List<Reference> getProxyTwoAssetFromAsset(Reference relationshipAsset, IGCRestClient igcRestClient, ObjectCache cache) {
        String otherAssetType = relationshipAsset.getType();
        ArrayList<Reference> asList = new ArrayList<>();
        if (otherAssetType.equals("classification")) {
            Reference dataClass;
            Object dc = igcRestClient.getPropertyByName(relationshipAsset,"data_class");
            if (dc == null || dc.equals("") || dc.equals("null")) {
                Reference classification = igcRestClient.getAssetById(relationshipAsset.getId(), cache);
                dataClass = (Reference) igcRestClient.getPropertyByName(classification, "data_class");
            } else {
                dataClass = (Reference) dc;
            }
            asList.add(dataClass);
        } else {
            log.debug("Not a classification asset, just returning as-is: {} of type {}", relationshipAsset.getName(), relationshipAsset.getType());
            asList.add(relationshipAsset);
        }
        return asList;
    }

    /**
     * Custom implementation of the relationship between an a DataClass (data_class) and a Referenceable (classificationenabledgroup).
     * This is one of the few relationships in IGC that has relationship-specific properties handled by a separate
     * 'classification' object, so it must be handled using custom logic.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the list of relationships to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the IGC entity from which the relationship exists
     * @param toIgcObject the other entity endpoint for the relationship (or null if unknown)
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     *                                (This will be ignored for this relationship as there is no way to effectively use
     *                                it due to IGC search limitations.)
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size. (This will be ignored for this relationship as there is no way
     *                 to effectively use it due to IGC search limitations.)
     * @param userId the user ID requesting the mapped relationships
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
                                           String userId) {

        if (fromIgcObject instanceof DataClass) {
            mapDetectedClassifications_fromDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    (DataClass) fromIgcObject,
                    toIgcObject instanceof Classificationenabledgroup ? (Classificationenabledgroup) toIgcObject : null,
                    sequencingOrder,
                    userId
            );
            mapSelectedClassifications_fromDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    (DataClass) fromIgcObject,
                    userId
            );
        } else if (fromIgcObject instanceof Classificationenabledgroup) {
            mapDetectedClassifications_toDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    (Classificationenabledgroup) fromIgcObject,
                    toIgcObject instanceof DataClass ? (DataClass) toIgcObject : null,
                    sequencingOrder,
                    userId
            );
            mapSelectedClassifications_toDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    cache,
                    (Classificationenabledgroup) fromIgcObject,
                    userId
            );
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGCSearch> getComplexIGCSearchCriteria(IGCOMRSRepositoryConnector repositoryConnector,
                                                       SearchProperties matchProperties) throws FunctionNotSupportedException {

        IGCRestClient igcRestClient = repositoryConnector.getIGCRestClient();

        // If no search properties were provided, we can short-circuit and just return all such assignments via the
        // simple search criteria
        if (matchProperties == null) {
            return getSimpleIGCSearchCriteria();
        }
        List<PropertyCondition> conditions = matchProperties.getConditions();
        if (conditions == null) {
            return getSimpleIGCSearchCriteria();
        }

        List<IGCSearch> searches = new ArrayList<>();
        IGCSearch searchForDataClass = new IGCSearch("data_class");
        searchForDataClass.addProperties(getProxyTwoMapping().getRealIgcRelationshipProperties());
        IGCSearchConditionSet conditionsForDataClass = new IGCSearchConditionSet();

        IGCSearch searchForClassification = new IGCSearch("classification");
        searchForClassification.addProperties(igcRestClient.getAllPropertiesForType("classification"));
        IGCSearchConditionSet conditionsForClassification = new IGCSearchConditionSet();

        addAllConditions(conditionsForDataClass, conditionsForClassification, matchProperties);

        if (conditionsForDataClass.size() > 0) {
            searchForDataClass.addConditions(conditionsForDataClass);
            searches.add(searchForDataClass);
        }
        if (conditionsForClassification.size() > 0) {
            searchForClassification.addConditions(conditionsForClassification);
            searches.add(searchForClassification);
        }

        return searches;

    }

    private void addAllConditions(IGCSearchConditionSet dataClassConditions,
                                  IGCSearchConditionSet classificationConditions,
                                  SearchProperties matchProperties) throws FunctionNotSupportedException {
        final String methodName = "addAllConditions";
        List<PropertyCondition> conditionsToMatch = matchProperties.getConditions();
        if (conditionsToMatch != null) {
            for (PropertyCondition condition : conditionsToMatch) {
                SearchProperties nestedConditions = condition.getNestedConditions();
                if (nestedConditions != null) {
                    IGCSearchConditionSet nestedDataClassConditions = new IGCSearchConditionSet();
                    IGCSearchConditionSet nestedClassificationConditions = new IGCSearchConditionSet();
                    addAllConditions(nestedDataClassConditions, nestedClassificationConditions, nestedConditions);
                    if (nestedDataClassConditions.size() > 0) {
                        dataClassConditions.addNestedConditionSet(nestedDataClassConditions);
                    }
                    if (nestedClassificationConditions.size() > 0) {
                        classificationConditions.addNestedConditionSet(nestedClassificationConditions);
                    }
                } else {
                    String propertyName = condition.getProperty();
                    PropertyComparisonOperator operator = condition.getOperator();
                    InstancePropertyValue value = condition.getValue();
                    switch (propertyName) {
                        case "status":
                            if (value instanceof EnumPropertyValue) {
                                EnumPropertyValue statusToFind = (EnumPropertyValue) value;
                                IGCRepositoryHelper.validateEnumOperator(operator, methodName);
                                switch (operator) {
                                    case EQ:
                                        switch (statusToFind.getSymbolicName()) {
                                            case "Discovered":
                                                dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                                includeAllDetected(classificationConditions);
                                                break;
                                            case "Proposed":
                                                includeAllSelected(dataClassConditions);
                                                classificationConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                                break;
                                            default:
                                                includeNoResults(dataClassConditions, classificationConditions);
                                                break;
                                        }
                                        break;
                                    case NEQ:
                                        switch (statusToFind.getSymbolicName()) {
                                            case "Discovered":
                                                includeAllSelected(dataClassConditions);
                                                classificationConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                                break;
                                            case "Proposed":
                                                dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                                includeAllDetected(classificationConditions);
                                                break;
                                            default:
                                                includeAllResults(dataClassConditions, classificationConditions);
                                                break;
                                        }
                                        break;
                                    case IS_NULL:
                                        includeNoResults(dataClassConditions, classificationConditions);
                                        break;
                                    case NOT_NULL:
                                        includeAllResults(dataClassConditions, classificationConditions);
                                        break;
                                    default:
                                        throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_SEARCH_COMPARISON.getMessageDefinition(operator.getName(), InstancePropertyCategory.ENUM.getName()),
                                                this.getClass().getName(),
                                                methodName);
                                }
                            } else if (value == null) {
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    // should not include any detected or selected data classes
                                    includeNoResults(dataClassConditions, classificationConditions);
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    // should include all detected and selected data classes
                                    includeAllResults(dataClassConditions, classificationConditions);
                                } else {
                                    // otherwise there is an error
                                    throw new FunctionNotSupportedException(IGCOMRSErrorCode.NO_VALUE_FOR_SEARCH.getMessageDefinition("status", operator.getName()),
                                            this.getClass().getName(),
                                            methodName);
                                }
                            } else {
                                throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_ENUMERATION.getMessageDefinition("status"),
                                        this.getClass().getName(),
                                        methodName);
                            }
                            break;
                        case P_THRESHOLD:
                            if (value instanceof PrimitivePropertyValue) {
                                PrimitivePropertyValue ppv = (PrimitivePropertyValue) value;
                                IGCRepositoryHelper.validateNumericOperator(operator, ppv.getTypeName(), methodName);
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    includeAllSelected(dataClassConditions);
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                }
                                String igcOperator = IGCRepositoryHelper.getIgcOperator(operator);
                                IGCSearchCondition byThreshold = new IGCSearchCondition(P_THRESHOLD, igcOperator, value.valueAsString());
                                classificationConditions.addCondition(byThreshold);
                            } else if (value == null) {
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    includeAllSelected(dataClassConditions);
                                    IGCSearchCondition byThreshold = new IGCSearchCondition(P_THRESHOLD, "isNull", false);
                                    classificationConditions.addCondition(byThreshold);
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                    IGCSearchCondition byThreshold = new IGCSearchCondition(P_THRESHOLD, "isNull", true);
                                    classificationConditions.addCondition(byThreshold);
                                } else {
                                    // otherwise there is an error
                                    throw new FunctionNotSupportedException(IGCOMRSErrorCode.NO_VALUE_FOR_SEARCH.getMessageDefinition(P_THRESHOLD, operator.getName()),
                                            this.getClass().getName(),
                                            methodName);
                                }
                            } else {
                                throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition(P_THRESHOLD, value.getTypeName()),
                                        this.getClass().getName(),
                                        methodName);
                            }
                            break;
                        case "partialMatch":
                            if (value instanceof PrimitivePropertyValue) {
                                PrimitivePropertyValue ppv = (PrimitivePropertyValue) value;
                                IGCRepositoryHelper.validateBooleanOperator(operator, methodName);
                                boolean isPartialMatch = Boolean.parseBoolean(value.valueAsString());
                                IGCSearchCondition byPartialMatch;
                                switch (operator) {
                                    case EQ:
                                        dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                        byPartialMatch = new IGCSearchCondition("confidencePercent", isPartialMatch ? "<" : "=", "100");
                                        classificationConditions.addCondition(byPartialMatch);
                                        break;
                                    case NEQ:
                                        dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                        byPartialMatch = new IGCSearchCondition("confidencePercent", isPartialMatch ? "=" : "<", "100");
                                        classificationConditions.addCondition(byPartialMatch);
                                        break;
                                    case IS_NULL:
                                        includeAllSelected(dataClassConditions);
                                        classificationConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                        break;
                                    case NOT_NULL:
                                        dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                        includeAllDetected(classificationConditions);
                                        break;
                                    default:
                                        // do nothing, not a valid boolean operation
                                        log.error("Invalid boolean operation for search: {}", operator);
                                        break;
                                }
                            } else if (value == null) {
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    includeAllSelected(dataClassConditions);
                                    classificationConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                    includeAllDetected(classificationConditions);
                                } else {
                                    // otherwise there is an error
                                    throw new FunctionNotSupportedException(IGCOMRSErrorCode.NO_VALUE_FOR_SEARCH.getMessageDefinition("partialMatch", operator.getName()),
                                            this.getClass().getName(),
                                            methodName);
                                }
                            } else {
                                throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition("partialMatch", value.getTypeName()),
                                        this.getClass().getName(),
                                        methodName);
                            }
                            break;
                        case "valueFrequency":
                            if (value instanceof PrimitivePropertyValue) {
                                PrimitivePropertyValue ppv = (PrimitivePropertyValue) value;
                                IGCRepositoryHelper.validateNumericOperator(operator, ppv.getTypeName(), methodName);
                                long valueFreq = Long.parseLong(value.valueAsString());
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    includeAllSelected(dataClassConditions);
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                }
                                String igcOperator = IGCRepositoryHelper.getIgcOperator(operator);
                                IGCSearchCondition byValueFrequency = new IGCSearchCondition("value_frequency", igcOperator, "" + valueFreq);
                                classificationConditions.addCondition(byValueFrequency);
                            } else if (value == null) {
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    includeAllSelected(dataClassConditions);
                                    IGCSearchCondition byValueFrequency = new IGCSearchCondition("value_frequency", "isNull", false);
                                    classificationConditions.addCondition(byValueFrequency);
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                    IGCSearchCondition byValueFrequency = new IGCSearchCondition("value_frequency", "isNull", true);
                                    classificationConditions.addCondition(byValueFrequency);
                                } else {
                                    // otherwise there is an error
                                    throw new FunctionNotSupportedException(IGCOMRSErrorCode.NO_VALUE_FOR_SEARCH.getMessageDefinition("valueFrequency", operator.getName()),
                                            this.getClass().getName(),
                                            methodName);
                                }
                            } else {
                                throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition("valueFrequency", value.getTypeName()),
                                        this.getClass().getName(),
                                        methodName);
                            }
                            break;
                        case "confidence":
                            if (value instanceof PrimitivePropertyValue) {
                                PrimitivePropertyValue ppv = (PrimitivePropertyValue) value;
                                IGCRepositoryHelper.validateNumericOperator(operator, ppv.getTypeName(), methodName);
                                int confidence = Integer.parseInt(value.valueAsString());
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    includeAllSelected(dataClassConditions);
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                }
                                String igcOperator = IGCRepositoryHelper.getIgcOperator(operator);
                                IGCSearchCondition byValueFrequency = new IGCSearchCondition("confidencePercent", igcOperator, "" + confidence);
                                classificationConditions.addCondition(byValueFrequency);
                            } else if (value == null) {
                                if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                    includeAllSelected(dataClassConditions);
                                    IGCSearchCondition byConfidence = new IGCSearchCondition("confidencePercent", "isNull", false);
                                    classificationConditions.addCondition(byConfidence);
                                } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
                                    dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                                    IGCSearchCondition byConfidence = new IGCSearchCondition("confidencePercent", "isNull", true);
                                    classificationConditions.addCondition(byConfidence);
                                } else {
                                    // otherwise there is an error
                                    throw new FunctionNotSupportedException(IGCOMRSErrorCode.NO_VALUE_FOR_SEARCH.getMessageDefinition("confidence", operator.getName()),
                                            this.getClass().getName(),
                                            methodName);
                                }
                            } else {
                                throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_PRIMITIVE.getMessageDefinition("confidence", value.getTypeName()),
                                        this.getClass().getName(),
                                        methodName);
                            }
                            break;
                        default:
                            // Any other property is not mapped, so only include results if the request is for null value
                            if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
                                includeAllResults(dataClassConditions, classificationConditions);
                            } else {
                                includeNoResults(dataClassConditions, classificationConditions);
                            }
                            break;
                    }
                }
            }
            MatchCriteria matchCriteria = matchProperties.getMatchCriteria();
            if (dataClassConditions.size() > 0) {
                IGCRepositoryHelper.setConditionsFromMatchCriteria(dataClassConditions, matchCriteria);
            }
            if (classificationConditions.size() > 0) {
                IGCRepositoryHelper.setConditionsFromMatchCriteria(classificationConditions, matchCriteria);
            }
        }
    }

    private void includeAllResults(IGCSearchConditionSet dataClassConditions,
                                   IGCSearchConditionSet classificationConditions) {
        includeAllDetected(classificationConditions);
        includeAllSelected(dataClassConditions);
    }

    private void includeAllDetected(IGCSearchConditionSet classificationConditions) {
        IGCSearchCondition cc = new IGCSearchCondition("classifies_asset", "isNull", true);
        classificationConditions.addCondition(cc);
    }

    private void includeAllSelected(IGCSearchConditionSet dataClassConditions) {
        IGCSearchCondition dcc = new IGCSearchCondition("classifications_selected", "isNull", true);
        dataClassConditions.addCondition(dcc);
    }

    private void includeNoResults(IGCSearchConditionSet dataClassConditions,
                                  IGCSearchConditionSet classificationConditions) {
        dataClassConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
        classificationConditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
    }

    /**
     * Map the detected classifications for objects classified by the provided data_class object.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the list of relationships to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param dataClass the data_class object
     * @param toIgcObject the classificationenabledgroup that is classified (if known, null otherwise)
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param userId the user requesting the mapped relationships
     */
    private void mapDetectedClassifications_fromDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                          List<Relationship> relationships,
                                                          ObjectCache cache,
                                                          DataClass dataClass,
                                                          Classificationenabledgroup toIgcObject,
                                                          SequencingOrder sequencingOrder,
                                                          String userId) {

        final String methodName = "mapDetectedClassifications_fromDataClass";
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // One of the few relationships in IGC that actually has properties of its own!
        // So we need to retrieve this relationship linking object (IGC type 'classification')
        IGCSearchCondition byDataClass = new IGCSearchCondition("data_class", "=", dataClass.getId());
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(byDataClass);
        if (toIgcObject != null) {
            IGCSearchCondition byAsset = new IGCSearchCondition("classifies_asset", "=", toIgcObject.getId());
            igcSearchConditionSet.addCondition(byAsset);
            igcSearchConditionSet.setMatchAnyCondition(false);
        }
        String[] classificationProperties = new String[]{
                "classifies_asset",
                "confidencePercent",
                P_THRESHOLD
        };

        ItemList<Classification> detectedClassifications = getDetectedClassifications(igcomrsRepositoryConnector,
                classificationProperties,
                igcSearchConditionSet,
                sequencingOrder);

        // For each of the detected classifications, create a new DataClassAssignment relationship
        for (Classification detectedClassification : detectedClassifications.getItems()) {

            Reference classifiedObj = detectedClassification.getClassifiesAsset();

            /* Only proceed with the classified object if it is not a 'main_object' asset
             * (in this scenario, 'main_object' represents ColumnAnalysisMaster objects that are not accessible
             *  and will throw bad request (400) REST API errors) */
            if (classifiedObj != null && !classifiedObj.getType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
                try {

                    // Use 'classification' object to put RID of classification on the 'detected classification' relationships
                    Relationship relationship = getMappedRelationship(
                            igcomrsRepositoryConnector,
                            DataClassAssignmentMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()),
                            (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                                    igcomrsRepositoryConnector.getRepositoryName(),
                                    R_DATA_CLASS_ASSIGNMENT),
                            cache,
                            classifiedObj,
                            dataClass,
                            "detected_classifications",
                            userId,
                            detectedClassification.getId()
                    );

                    /* Before adding to the overall set of relationships, setup the relationship properties
                     * we have in IGC from the 'classification' object. */
                    setDetectedRelationshipProperties(detectedClassification,
                            relationship,
                            repositoryHelper,
                            repositoryName,
                            methodName,
                            igcomrsRepositoryConnector.getIGCVersion());

                    log.debug("mapDetectedClassifications_fromDataClass - adding relationship: {}", relationship.getGUID());
                    relationships.add(relationship);

                } catch (RepositoryErrorException e) {
                    log.error("Unable to map relationship.", e);
                }
            }
        }

    }

    /**
     * Map the selected classifications for objects classified by the provided data_class object.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the list of relationships to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param dataClass the data_class object
     * @param userId the user requesting the mapped relationships
     */
    private void mapSelectedClassifications_fromDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                          List<Relationship> relationships,
                                                          ObjectCache cache,
                                                          DataClass dataClass,
                                                          String userId) {

        // (Note that in IGC these can only be retrieved by looking up all assets for which this data_class is selected,
        // they cannot be looked up as a relationship from the data_class object...  Therefore, start by searching
        // for any assets that list this data_class as their selected_classification
        IGCSearchCondition igcSearchCondition = new IGCSearchCondition("selected_classification", "=", dataClass.getId());
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        IGCSearch igcSearch = new IGCSearch("amazon_s3_data_file_field", igcSearchConditionSet);
        igcSearch.addType("data_file_field");
        igcSearch.addType("database_column");
        igcSearch.addProperties(IGCRestConstants.getModificationProperties());
        igcSearch.addProperty("selected_classification");
        ItemList<InformationAsset> assetsWithSelected = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);

        List<InformationAsset> allAssetsWithSelected = igcomrsRepositoryConnector.getIGCRestClient().getAllPages(null, assetsWithSelected);

        for (InformationAsset assetWithSelected : allAssetsWithSelected) {

            try {

                // Use 'data_class' object to put RID of data_class itself on the 'selected classification' relationships
                Relationship relationship = getMappedRelationship(
                        igcomrsRepositoryConnector,
                        DataClassAssignmentMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()),
                        (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                                igcomrsRepositoryConnector.getRepositoryName(),
                                R_DATA_CLASS_ASSIGNMENT),
                        cache,
                        assetWithSelected,
                        dataClass,
                        "selected_classification",
                        userId
                );

                setSelectedRelationshipProperties(relationship, igcomrsRepositoryConnector.getIGCVersion());

                log.debug("mapSelectedClassifications_fromDataClass - adding relationship: {}", relationship.getGUID());
                relationships.add(relationship);

            } catch (RepositoryErrorException e) {
                log.error("Unable to map relationship.", e);
            }

        }

    }

    /**
     * Map the provided main_object object to its detected data classes.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the list of relationships to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the main_object object
     * @param dataClass the data_class object (if known, or null otherwise)
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param userId the user requesting the mapped relationships
     */
    private void mapDetectedClassifications_toDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        List<Relationship> relationships,
                                                        ObjectCache cache,
                                                        Classificationenabledgroup fromIgcObject,
                                                        DataClass dataClass,
                                                        SequencingOrder sequencingOrder,
                                                        String userId) {

        final String methodName = "mapDetectedClassifications_toDataClass";

        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // One of the few relationships in IGC that actually has properties of its own!
        // So we need to retrieve this relationship linking object (IGC type 'classification')
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();
        if (fromIgcObject != null) {
            IGCSearchCondition byAsset = new IGCSearchCondition("classifies_asset", "=", fromIgcObject.getId());
            igcSearchConditionSet.addCondition(byAsset);
        }
        if (dataClass != null) {
            IGCSearchCondition byDataClass = new IGCSearchCondition("data_class", "=", dataClass.getId());
            igcSearchConditionSet.addCondition(byDataClass);
            igcSearchConditionSet.setMatchAnyCondition(false);
        }
        String[] classificationProperties = new String[]{
                "confidencePercent",
                "data_class",
                P_THRESHOLD
        };

        ItemList<Classification> detectedClassifications = getDetectedClassifications(igcomrsRepositoryConnector,
                classificationProperties,
                igcSearchConditionSet,
                sequencingOrder);

        // For each of the detected classifications, create a new DataClassAssignment relationship
        for (Classification detectedClassification : detectedClassifications.getItems()) {

            DataClass dataClassObj = detectedClassification.getDataClass();

            /* Only proceed with the classified object if it is not a 'main_object' asset
             * (in this scenario, 'main_object' represents ColumnAnalysisMaster objects that are not accessible
             *  and will throw bad request (400) REST API errors) */
            if (dataClassObj != null && dataClassObj.getType() != null && !dataClassObj.getType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
                try {

                    // Use 'classification' object to put RID of classification on the 'detected classification' relationships
                    Relationship relationship = getMappedRelationship(
                            igcomrsRepositoryConnector,
                            DataClassAssignmentMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()),
                            (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                                    igcomrsRepositoryConnector.getRepositoryName(),
                                    R_DATA_CLASS_ASSIGNMENT),
                            cache,
                            fromIgcObject,
                            dataClassObj,
                            "detected_classifications",
                            userId,
                            detectedClassification.getId()
                    );

                    /* Before adding to the overall set of relationships, setup the relationship properties
                     * we have in IGC from the 'classification' object. */
                    setDetectedRelationshipProperties(detectedClassification,
                            relationship,
                            repositoryHelper,
                            repositoryName,
                            methodName,
                            igcomrsRepositoryConnector.getIGCVersion());

                    log.debug("mapDetectedClassifications_toDataClass - adding relationship: {}", relationship.getGUID());
                    relationships.add(relationship);

                } catch (RepositoryErrorException e) {
                    log.error("Unable to map relationship.", e);
                }
            }
        }

    }

    /**
     * Map the provided main_object object to its selected classification.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the list of relationships to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the classificationenabledgroup object
     * @param userId the user requesting the mapped relationships
     */
    private void mapSelectedClassifications_toDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        List<Relationship> relationships,
                                                        ObjectCache cache,
                                                        Classificationenabledgroup fromIgcObject,
                                                        String userId) {

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        DataClass selectedClassification = fromIgcObject.getSelectedClassification();
        if (selectedClassification == null) {
            Classificationenabledgroup withSelectedClassification = igcRestClient.getAssetWithSubsetOfProperties(
                    fromIgcObject.getId(),
                    fromIgcObject.getType(),
                    new String[]{"selected_classification"});
            selectedClassification = withSelectedClassification.getSelectedClassification();
        }

        // If the reference itself (or its type) are null the relationship does not exist
        if (selectedClassification != null && selectedClassification.getType() != null) {
            try {

                Relationship relationship = getMappedRelationship(
                        igcomrsRepositoryConnector,
                        DataClassAssignmentMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()),
                        (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                                igcomrsRepositoryConnector.getRepositoryName(),
                                R_DATA_CLASS_ASSIGNMENT),
                        cache,
                        fromIgcObject,
                        selectedClassification,
                        "selected_classification",
                        userId
                );

                setSelectedRelationshipProperties(relationship, igcomrsRepositoryConnector.getIGCVersion());

                log.debug("mapSelectedClassifications_toDataClass - adding relationship: {}", relationship.getGUID());
                relationships.add(relationship);

            } catch (RepositoryErrorException e) {
                log.error("Unable to map relationship.", e);
            }
        } else {
            log.debug("No selected_classification set for asset -- skipping.");
        }

    }

    /**
     * Retrieve the listing of detected classifications.
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param classificationProperties the properties of the classification to retrieve
     * @param igcSearchConditionSet the conditions to use for searching for the classifications
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @return {@code ItemList<Classification>}
     */
    private ItemList<Classification> getDetectedClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                                String[] classificationProperties,
                                                                IGCSearchConditionSet igcSearchConditionSet,
                                                                SequencingOrder sequencingOrder) {

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        ItemList<Classification> detectedClassifications;
        IGCSearch igcSearch = new IGCSearch("classification", classificationProperties, igcSearchConditionSet);
        IGCVersionEnum igcVersion = igcomrsRepositoryConnector.getIGCVersion();
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            igcSearch.addProperty("value_frequency");
        }

        IGCSearchSorting sorting = null;
        if (sequencingOrder != null && sequencingOrder.equals(SequencingOrder.GUID)) {
            // TODO: for now only allowing sorting on the ID, since there is no modification detail on the relationship
            //  (in future we may want to allow sorting on property as well?)
            sorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
        }
        if (sorting != null) {
            igcSearch.addSortingCriteria(sorting);
        }

        if (igcSearchConditionSet.size() > 0) {
            detectedClassifications = igcRestClient.search(igcSearch);
            // Unfortunately there is no way to avoid the inclusion of column analysis master objects in the results, so
            // the only way we can ensure we are sorting across all valid results is to retrieve all of them
            List<Classification> allPages = igcRestClient.getAllPages(null, detectedClassifications);
            detectedClassifications.setAllPages(allPages);
        } else {
            igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
            detectedClassifications = igcRestClient.search(igcSearch);
        }

        return detectedClassifications;

    }

    /**
     * Setup the relationship-level properties for the detected classification.
     * @param detectedClassification the detected Classification
     * @param relationship the OMRS relationship on which to set the properties
     * @param repositoryHelper the helper through which to set properties
     * @param repositoryName the name of the repository
     * @param methodName the name of the method setting the properties
     * @param igcVersion the version of IGC
     */
    private void setDetectedRelationshipProperties(Classification detectedClassification,
                                                   Relationship relationship,
                                                   OMRSRepositoryHelper repositoryHelper,
                                                   String repositoryName,
                                                   String methodName,
                                                   IGCVersionEnum igcVersion) {

        InstanceProperties relationshipProperties = relationship.getProperties();
        if (relationshipProperties == null) {
            relationshipProperties = new InstanceProperties();
        }

        Number confidence = detectedClassification.getConfidencepercent();
        if (confidence != null) {
            int confidenceVal = confidence.intValue();
            relationshipProperties = repositoryHelper.addIntPropertyToInstance(
                    repositoryName,
                    relationshipProperties,
                    "confidence",
                    confidenceVal,
                    methodName
            );
            relationshipProperties = repositoryHelper.addBooleanPropertyToInstance(
                    repositoryName,
                    relationshipProperties,
                    "partialMatch",
                    (confidenceVal < 100),
                    methodName
            );
        }
        Number threshold = detectedClassification.getThreshold();
        if (threshold != null) {
            relationshipProperties = repositoryHelper.addFloatPropertyToInstance(
                    repositoryName,
                    relationshipProperties,
                    P_THRESHOLD,
                    threshold.floatValue(),
                    methodName
            );
        }
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            Number valFreq = detectedClassification.getValueFrequency();
            if (valFreq != null) {
                relationshipProperties = repositoryHelper.addLongPropertyToInstance(
                        repositoryName,
                        relationshipProperties,
                        "valueFrequency",
                        valFreq.longValue(),
                        methodName
                );
            }
        }
        EnumPropertyValue status = DataClassAssignmentStatusMapper.getInstance(igcVersion).getEnumMappingByIgcValue("discovered");
        relationshipProperties.setProperty(
                "status",
                status
        );

        relationship.setProperties(relationshipProperties);

    }

    /**
     * Setup the relationship-level properties for the selected classification.
     * @param relationship the OMRS relationship against which to set the properties
     * @param igcVersion the version of IGC
     */
    private void setSelectedRelationshipProperties(Relationship relationship,
                                                   IGCVersionEnum igcVersion) {

        InstanceProperties relationshipProperties = relationship.getProperties();
        if (relationshipProperties == null) {
            relationshipProperties = new InstanceProperties();
        }

        EnumPropertyValue status = DataClassAssignmentStatusMapper.getInstance(igcVersion).getEnumMappingByIgcValue("selected");
        relationshipProperties.setProperty(
                "status",
                status
        );

        relationship.setProperties(relationshipProperties);

    }

}
