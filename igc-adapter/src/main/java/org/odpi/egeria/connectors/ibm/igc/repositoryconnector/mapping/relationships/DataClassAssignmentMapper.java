/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classification;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.DataClassAssignmentStatusMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
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

    private DataClassAssignmentMapper() {
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
     * @return Reference - the classificationenabledgroup asset
     */
    @Override
    public List<Reference> getProxyOneAssetFromAsset(Reference relationshipAsset, IGCRestClient igcRestClient) {
        String otherAssetType = relationshipAsset.getType();
        ArrayList<Reference> asList = new ArrayList<>();
        if (otherAssetType.equals("classification")) {
            Reference classifiedObj;
            Object co = igcRestClient.getPropertyByName(relationshipAsset, "classifies_asset");
            if (co == null || co.equals("") || co.equals("null")) {
                Reference classification = igcRestClient.getAssetById(relationshipAsset.getId());
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
     * @return Reference - the data_class asset
     */
    @Override
    public List<Reference> getProxyTwoAssetFromAsset(Reference relationshipAsset, IGCRestClient igcRestClient) {
        String otherAssetType = relationshipAsset.getType();
        ArrayList<Reference> asList = new ArrayList<>();
        if (otherAssetType.equals("classification")) {
            Reference dataClass;
            Object dc = igcRestClient.getPropertyByName(relationshipAsset,"data_class");
            if (dc == null || dc.equals("") || dc.equals("null")) {
                Reference classification = igcRestClient.getAssetById(relationshipAsset.getId());
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
     * @param fromIgcObject the IGC entity from which the relationship exists
     * @param toIgcObject the other entity endpoint for the relationship (or null if unknown)
     * @param userId the user ID requesting the mapped relationships
     */
    @Override
    public void addMappedOMRSRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                           List<Relationship> relationships,
                                           Reference fromIgcObject,
                                           Reference toIgcObject,
                                           String userId) {

        if (fromIgcObject instanceof DataClass) {
            mapDetectedClassifications_fromDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    (DataClass) fromIgcObject,
                    toIgcObject instanceof Classificationenabledgroup ? (Classificationenabledgroup) toIgcObject : null,
                    userId
            );
            mapSelectedClassifications_fromDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    (DataClass) fromIgcObject,
                    userId
            );
        } else if (fromIgcObject instanceof Classificationenabledgroup) {
            mapDetectedClassifications_toDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    (Classificationenabledgroup) fromIgcObject,
                    toIgcObject instanceof DataClass ? (DataClass) toIgcObject : null,
                    userId
            );
            mapSelectedClassifications_toDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    (Classificationenabledgroup) fromIgcObject,
                    userId
            );
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGCSearch> getComplexIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                       String repositoryName,
                                                       IGCRestClient igcRestClient,
                                                       InstanceProperties matchProperties,
                                                       MatchCriteria matchCriteria) {

        // If no search properties were provided, we can short-circuit and just return all such assignments via the
        // simple search criteria
        if (matchProperties == null) {
            return getSimpleIGCSearchCriteria();
        }
        Map<String, InstancePropertyValue> mapOfValues = matchProperties.getInstanceProperties();
        if (mapOfValues == null) {
            return getSimpleIGCSearchCriteria();
        }

        List<IGCSearch> searches = new ArrayList<>();
        IGCSearch searchForDataClass = new IGCSearch("data_class");
        searchForDataClass.addProperties(getProxyTwoMapping().getRealIgcRelationshipProperties());
        IGCSearchConditionSet conditionsForDataClass = new IGCSearchConditionSet();

        IGCSearch searchForClassification = new IGCSearch("classification");
        searchForClassification.addProperties(igcRestClient.getAllPropertiesForType("classification"));
        IGCSearchConditionSet conditionsForClassification = new IGCSearchConditionSet();

        // First see if we are searching by status, as this changes the other properties we can search
        // - Proposed status in IGC will have no other properties (and we can search against data_class only as there is no classification object)
        // - Discovered status in IGC will be the link to a 'classification' object with other properties
        boolean considerOtherProperties = true;
        InstancePropertyValue status = mapOfValues.getOrDefault("status", null);
        String statusName;
        if (status != null && status.getInstancePropertyCategory().equals(InstancePropertyCategory.ENUM)) {
            EnumPropertyValue statusEnum = (EnumPropertyValue) status;
            statusName = statusEnum.getSymbolicName();
            if (statusName != null) {
                if (statusName.equals("Discovered")) {
                    conditionsForDataClass.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                } else if (statusName.equals("Proposed")) {
                    IGCSearchCondition proposed = new IGCSearchCondition("classifications_selected", "isNull", true);
                    conditionsForDataClass.addCondition(proposed);
                    considerOtherProperties = false;
                } else {
                    // If the status is something else, there will be no results from IGC
                    conditionsForDataClass.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                    conditionsForClassification.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                    considerOtherProperties = false;
                }
            }
        }

        // Only need to consider other properties if we are looking for a 'Discovered' status, or not restricting based
        // on status at all...
        if (considerOtherProperties) {
            Set<String> searchProperties = mapOfValues.keySet();
            Set<String> mappedOmrsProperties = getMappedOmrsPropertyNames();
            if (!mappedOmrsProperties.containsAll(searchProperties)) {
                // If any of the properties included in the search is one we do not map in IGC, return no results
                conditionsForDataClass.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                conditionsForClassification.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
            } else {
                // Otherwise we can only search against the 'classification' object, as it is the only one with any
                // additional properties
                // TODO: what about literal-mapped properties for Proposed classifications?
                for (Map.Entry<String, InstancePropertyValue> entry : mapOfValues.entrySet()) {
                    String omrsPropertyName = entry.getKey();
                    InstancePropertyValue value = entry.getValue();
                    switch (omrsPropertyName) {
                        case "confidence":
                            IGCSearchCondition byConfidence = new IGCSearchCondition("confidencePercent", "=", value.valueAsString());
                            conditionsForClassification.addCondition(byConfidence);
                            break;
                        case P_THRESHOLD:
                            IGCSearchCondition byThreshold = new IGCSearchCondition("threshold", "=", value.valueAsString());
                            conditionsForClassification.addCondition(byThreshold);
                            break;
                        case "partialMatch":
                            boolean isPartialMatch = Boolean.getBoolean(value.valueAsString());
                            if (isPartialMatch) {
                                IGCSearchCondition byPartialMatch = new IGCSearchCondition("confidencePercent", "<", "100");
                                conditionsForClassification.addCondition(byPartialMatch);
                            } else {
                                IGCSearchCondition byPartialMatch = new IGCSearchCondition("confidencePercent", "=", "100");
                                conditionsForClassification.addCondition(byPartialMatch);
                            }
                            break;
                        case "valueFrequency":
                            IGCSearchCondition byValueFrequency = new IGCSearchCondition("value_frequency", "=", value.valueAsString());
                            conditionsForClassification.addCondition(byValueFrequency);
                            break;
                    }
                }
            }
        }

        IGCRepositoryHelper.setConditionsFromMatchCriteria(conditionsForDataClass, matchCriteria);
        IGCRepositoryHelper.setConditionsFromMatchCriteria(conditionsForClassification, matchCriteria);

        searchForDataClass.addConditions(conditionsForDataClass);
        searchForClassification.addConditions(conditionsForClassification);

        searches.add(searchForDataClass);
        searches.add(searchForClassification);

        return searches;

    }

    /**
     * Map the detected classifications for objects classified by the provided data_class object.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the list of relationships to which to add
     * @param dataClass the data_class object
     * @param toIgcObject the classificationenabledgroup that is classified (if known, null otherwise)
     * @param userId the user requesting the mapped relationships
     */
    private void mapDetectedClassifications_fromDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                          List<Relationship> relationships,
                                                          DataClass dataClass,
                                                          Classificationenabledgroup toIgcObject,
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

        ItemList<Classification> detectedClassifications = getDetectedClassifications(igcomrsRepositoryConnector, classificationProperties, igcSearchConditionSet);

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
     * @param dataClass the data_class object
     * @param userId the user requesting the mapped relationships
     */
    private void mapSelectedClassifications_fromDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                          List<Relationship> relationships,
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
        igcSearch.addProperty("selected_classification");
        igcSearch.addProperties(IGCRestConstants.getModificationProperties());
        ItemList<InformationAsset> assetsWithSelected = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);

        assetsWithSelected.getAllPages(igcomrsRepositoryConnector.getIGCRestClient());

        for (Reference assetWithSelected : assetsWithSelected.getItems()) {

            try {

                // Use 'data_class' object to put RID of data_class itself on the 'selected classification' relationships
                Relationship relationship = getMappedRelationship(
                        igcomrsRepositoryConnector,
                        DataClassAssignmentMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()),
                        (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                                igcomrsRepositoryConnector.getRepositoryName(),
                                R_DATA_CLASS_ASSIGNMENT),
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
     * @param fromIgcObject the main_object object
     * @param dataClass the data_class object (if known, or null otherwise)
     * @param userId the user requesting the mapped relationships
     */
    private void mapDetectedClassifications_toDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        List<Relationship> relationships,
                                                        Classificationenabledgroup fromIgcObject,
                                                        DataClass dataClass,
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
                "data_class",
                "confidencePercent",
                P_THRESHOLD
        };

        ItemList<Classification> detectedClassifications = getDetectedClassifications(igcomrsRepositoryConnector, classificationProperties, igcSearchConditionSet);

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
     * @param fromIgcObject the classificationenabledgroup object
     * @param userId the user requesting the mapped relationships
     */
    private void mapSelectedClassifications_toDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        List<Relationship> relationships,
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
     * @return {@code ItemList<Classification>}
     */
    private ItemList<Classification> getDetectedClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                                String[] classificationProperties,
                                                                IGCSearchConditionSet igcSearchConditionSet) {

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        ItemList<Classification> detectedClassifications;
        IGCSearch igcSearch = new IGCSearch("classification", classificationProperties, igcSearchConditionSet);
        IGCVersionEnum igcVersion = igcomrsRepositoryConnector.getIGCVersion();
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            igcSearch.addProperty("value_frequency");
        }

        if (igcSearchConditionSet.size() > 0) {
            detectedClassifications = igcRestClient.search(igcSearch);
            detectedClassifications.getAllPages(igcRestClient);
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
