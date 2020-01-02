/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classification;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
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
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        setRelationshipLevelIgcAsset("classification");
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
     * Retrieve the main_object asset expected from a classification asset.
     *
     * @param relationshipAsset the classification asset to translate into a main_object asset
     * @param igcRestClient REST connectivity to the IGC environment
     * @return Reference - the main_object asset
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
            if (log.isDebugEnabled()) { log.debug("Not a classification asset, just returning as-is: {} of type {}", relationshipAsset.getName(), relationshipAsset.getType()); }
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
            if (log.isDebugEnabled()) { log.debug("Not a classification asset, just returning as-is: {} of type {}", relationshipAsset.getName(), relationshipAsset.getType()); }
            asList.add(relationshipAsset);
        }
        return asList;
    }

    /**
     * Custom implementation of the relationship between an a DataClass (data_class) and a Referenceable (main_object).
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

        String assetType = IGCRestConstants.getAssetTypeForSearch(fromIgcObject.getType());

        if (assetType.equals("data_class")) {
            mapDetectedClassifications_fromDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    fromIgcObject,
                    userId
            );
            mapSelectedClassifications_fromDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    fromIgcObject,
                    userId
            );
        } else {
            mapDetectedClassifications_toDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    fromIgcObject,
                    userId
            );
            mapSelectedClassifications_toDataClass(
                    igcomrsRepositoryConnector,
                    relationships,
                    fromIgcObject,
                    userId
            );
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGCSearch getComplexIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 InstanceProperties matchProperties,
                                                 MatchCriteria matchCriteria) throws FunctionNotSupportedException {

        // If no search properties were provided, we can short-circuit and just return all such assignments via the
        // simple search criteria
        if (matchProperties == null) {
            return getSimpleIGCSearchCriteria();
        }

        IGCSearch igcSearch = new IGCSearch();
        IGCSearchConditionSet conditions = new IGCSearchConditionSet();
        Map<String, InstancePropertyValue> mapOfValues = matchProperties.getInstanceProperties();
        if (mapOfValues == null) {
            return getSimpleIGCSearchCriteria();
        }

        // First see if we are searching by status, as this changes the other properties we can search
        // - Proposed status in IGC will have no other properties
        // - Discovered status in IGC will be the link to a 'classification' object with other properties
        InstancePropertyValue status = mapOfValues.getOrDefault("status", null);
        String statusName = null;
        if (status != null && status.getInstancePropertyCategory().equals(InstancePropertyCategory.ENUM)) {
            EnumPropertyValue statusEnum = (EnumPropertyValue) status;
            statusName = statusEnum.getSymbolicName();
            if (statusName != null) {
                if (statusName.equals("Discovered")) {
                    IGCSearchCondition discovered = new IGCSearchCondition("classified_assets_detected", "isNull", true);
                    conditions.addCondition(discovered);
                } else if (statusName.equals("Proposed")) {
                    IGCSearchCondition proposed = new IGCSearchCondition("classifications_selected", "isNull", true);
                    conditions.addCondition(proposed);
                } else {
                    // If the status is something else, there will be no results from IGC
                    conditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                }
            }
        }

        Set<String> searchProperties = mapOfValues.keySet();
        Set<String> mappedOmrsProperties = getMappedOmrsPropertyNames();
        if (!mappedOmrsProperties.containsAll(searchProperties)) {
            // If any of the properties included in the search is one we do not map in IGC, return no results
            conditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
        } else if (statusName == null || statusName.equals("Discovered")) {
            // Otherwise only add further conditions if we are searching across all statuses, or the Discovered status
            String rootOfProperties = "classified_assets_detected.";
            for (Map.Entry<String, InstancePropertyValue> entry : mapOfValues.entrySet()) {
                String omrsPropertyName = entry.getKey();
                InstancePropertyValue value = entry.getValue();
                switch (omrsPropertyName) {
                    case "confidence":
                        IGCSearchCondition byConfidence = new IGCSearchCondition(rootOfProperties + "confidencePercent", "=", value.valueAsString());
                        conditions.addCondition(byConfidence);
                        break;
                    case P_THRESHOLD:
                        IGCSearchCondition byThreshold = new IGCSearchCondition(rootOfProperties + "threshold", "=", value.valueAsString());
                        conditions.addCondition(byThreshold);
                        break;
                    case "partialMatch":
                        IGCSearchCondition byPartialMatch = new IGCSearchCondition(rootOfProperties + "confidencePercent", "<", "100");
                        conditions.addCondition(byPartialMatch);
                        break;
                    case "valueFrequency":
                        IGCSearchCondition byValueFrequency = new IGCSearchCondition(rootOfProperties + "value_frequency", "=", value.valueAsString());
                        conditions.addCondition(byValueFrequency);
                        break;
                }
            }
        } else {
            conditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
        }

        IGCRepositoryHelper.setConditionsFromMatchCriteria(conditions, matchCriteria);

        igcSearch.addConditions(conditions);

        return igcSearch;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGCSearch getComplexIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 String searchCriteria) throws FunctionNotSupportedException {

        // If no search criteria was provided, we can short-circuit and just return all such assignments via the
        // simple search criteria
        if (searchCriteria == null || searchCriteria.equals("")) {
            return getSimpleIGCSearchCriteria();
        }

        IGCSearch igcSearch = new IGCSearch();
        igcSearch.addType("data_class");

        IGCSearchConditionSet conditions = new IGCSearchConditionSet();
        boolean matchesSomething = false;

        // The only string property we can attempt to match against is the status, and the only ones that are mapped
        // are Discovered and Proposed -- if anything else, we should ensure an empty list is returned
        Pattern pattern = Pattern.compile(searchCriteria);
        Matcher discovered = pattern.matcher("Discovered");
        Matcher proposed = pattern.matcher("Proposed");
        if (discovered.matches()) {
            matchesSomething = true;
            IGCSearchCondition nonNull = new IGCSearchCondition("classified_assets_detected", "isNull", true);
            conditions.addCondition(nonNull);
            igcSearch.addProperty("classified_assets_detected");
        }
        if (proposed.matches()) {
            matchesSomething = true;
            IGCSearchCondition nonNull = new IGCSearchCondition("classifications_selected", "isNull", true);
            conditions.addCondition(nonNull);
            igcSearch.addProperty("classifications_selected");
        }

        // If we are trying to match to something that will give no results, create a spurious search that should
        // give no results
        if (!matchesSomething) {
            conditions.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
        }
        igcSearch.addConditions(conditions);

        return igcSearch;

    }

    /**
     * Map the detected classifications for objects classified by the provided data_class object.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the list of relationships to which to add
     * @param fromIgcObject the data_class object
     * @param userId the user requesting the mapped relationships
     */
    private void mapDetectedClassifications_fromDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                          List<Relationship> relationships,
                                                          Reference fromIgcObject,
                                                          String userId) {

        final String methodName = "mapDetectedClassifications_fromDataClass";
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // One of the few relationships in IGC that actually has properties of its own!
        // So we need to retrieve this relationship linking object (IGC type 'classification')
        IGCSearchCondition igcSearchCondition = new IGCSearchCondition("data_class", "=", fromIgcObject.getId());
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        String[] classificationProperties = new String[]{
                "classifies_asset",
                "confidencePercent",
                P_THRESHOLD
        };
        IGCSearch igcSearch = new IGCSearch("classification", classificationProperties, igcSearchConditionSet);
        IGCVersionEnum igcVersion = igcomrsRepositoryConnector.getIGCVersion();
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            igcSearch.addProperty("value_frequency");
        }
        ItemList<Classification> detectedClassifications = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);

        detectedClassifications.getAllPages(igcomrsRepositoryConnector.getIGCRestClient());

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
                            fromIgcObject,
                            "detected_classifications",
                            userId,
                            detectedClassification.getId()
                    );

                    InstanceProperties relationshipProperties = relationship.getProperties();
                    if (relationshipProperties == null) {
                        relationshipProperties = new InstanceProperties();
                    }

                    /* Before adding to the overall set of relationships, setup the relationship properties
                     * we have in IGC from the 'classification' object. */
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
                    EnumPropertyValue status = DataClassAssignmentStatusMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()).getEnumMappingByIgcValue("discovered");
                    relationshipProperties.setProperty(
                            "status",
                            status
                    );

                    relationship.setProperties(relationshipProperties);
                    if (log.isDebugEnabled()) { log.debug("mapDetectedClassifications_fromDataClass - adding relationship: {}", relationship.getGUID()); }
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
     * @param fromIgcObject the data_class object
     * @param userId the user requesting the mapped relationships
     */
    private void mapSelectedClassifications_fromDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                          List<Relationship> relationships,
                                                          Reference fromIgcObject,
                                                          String userId) {

        // (Note that in IGC these can only be retrieved by looking up all assets for which this data_class is selected,
        // they cannot be looked up as a relationship from the data_class object...  Therefore, start by searching
        // for any assets that list this data_class as their selected_classification
        IGCSearchCondition igcSearchCondition = new IGCSearchCondition("selected_classification", "=", fromIgcObject.getId());
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
                        fromIgcObject,
                        "selected_classification",
                        userId
                );

                InstanceProperties relationshipProperties = relationship.getProperties();
                if (relationshipProperties == null) {
                    relationshipProperties = new InstanceProperties();
                }

                EnumPropertyValue status = DataClassAssignmentStatusMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()).getEnumMappingByIgcValue("selected");
                relationshipProperties.setProperty(
                        "status",
                        status
                );

                relationship.setProperties(relationshipProperties);
                if (log.isDebugEnabled()) { log.debug("mapSelectedClassifications_fromDataClass - adding relationship: {}", relationship.getGUID()); }
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
     * @param userId the user requesting the mapped relationships
     */
    private void mapDetectedClassifications_toDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        List<Relationship> relationships,
                                                        Reference fromIgcObject,
                                                        String userId) {

        final String methodName = "mapDetectedClassifications_toDataClass";
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // One of the few relationships in IGC that actually has properties of its own!
        // So we need to retrieve this relationship linking object (IGC type 'classification')
        IGCSearchCondition igcSearchCondition = new IGCSearchCondition("classifies_asset", "=", fromIgcObject.getId());
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        String[] classificationProperties = new String[]{
                "data_class",
                "confidencePercent",
                P_THRESHOLD
        };
        IGCSearch igcSearch = new IGCSearch("classification", classificationProperties, igcSearchConditionSet);
        IGCVersionEnum igcVersion = igcomrsRepositoryConnector.getIGCVersion();
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            igcSearch.addProperty("value_frequency");
        }
        ItemList<Classification> detectedClassifications = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);

        detectedClassifications.getAllPages(igcomrsRepositoryConnector.getIGCRestClient());

        // For each of the detected classifications, create a new DataClassAssignment relationship
        for (Classification detectedClassification : detectedClassifications.getItems()) {

            Reference dataClassObj = (Reference) igcRestClient.getPropertyByName(detectedClassification, "data_class");

            /* Only proceed with the classified object if it is not a 'main_object' asset
             * (in this scenario, 'main_object' represents ColumnAnalysisMaster objects that are not accessible
             *  and will throw bad request (400) REST API errors) */
            if (dataClassObj != null && !dataClassObj.getType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
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

                    InstanceProperties relationshipProperties = relationship.getProperties();
                    if (relationshipProperties == null) {
                        relationshipProperties = new InstanceProperties();
                    }

                    /* Before adding to the overall set of relationships, setup the relationship properties
                     * we have in IGC from the 'classification' object. */
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
                    EnumPropertyValue status = DataClassAssignmentStatusMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()).getEnumMappingByIgcValue("discovered");
                    relationshipProperties.setProperty(
                            "status",
                            status
                    );

                    relationship.setProperties(relationshipProperties);
                    if (log.isDebugEnabled()) { log.debug("mapDetectedClassifications_toDataClass - adding relationship: {}", relationship.getGUID()); }
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
     * @param fromIgcObject the main_object object
     * @param userId the user requesting the mapped relationships
     */
    private void mapSelectedClassifications_toDataClass(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        List<Relationship> relationships,
                                                        Reference fromIgcObject,
                                                        String userId) {

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        Reference withSelectedClassification = igcRestClient.getAssetWithSubsetOfProperties(
                fromIgcObject.getId(),
                fromIgcObject.getType(),
                new String[]{"selected_classification"});

        Reference selectedClassification = (Reference) igcRestClient.getPropertyByName(withSelectedClassification, "selected_classification");

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
                        (Reference) igcRestClient.getPropertyByName(withSelectedClassification, "selected_classification"),
                        "selected_classification",
                        userId
                );

                InstanceProperties relationshipProperties = relationship.getProperties();
                if (relationshipProperties == null) {
                    relationshipProperties = new InstanceProperties();
                }

                EnumPropertyValue status = DataClassAssignmentStatusMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()).getEnumMappingByIgcValue("selected");
                relationshipProperties.setProperty(
                        "status",
                        status
                );

                relationship.setProperties(relationshipProperties);
                if (log.isDebugEnabled()) {
                    log.debug("mapSelectedClassifications_toDataClass - adding relationship: {}", relationship.getGUID());
                }
                relationships.add(relationship);

            } catch (RepositoryErrorException e) {
                log.error("Unable to map relationship.", e);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("No selected_classification set for asset -- skipping.");
            }
        }

    }

}
