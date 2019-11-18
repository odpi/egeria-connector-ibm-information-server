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
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EnumPropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
            if (log.isDebugEnabled()) { log.debug("Not a classification asset, just returning as-is: {}", relationshipAsset); }
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
            if (log.isDebugEnabled()) { log.debug("Not a classification asset, just returning as-is: {}", relationshipAsset); }
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
        for (Reference detectedClassification : detectedClassifications.getItems()) {

            Reference classifiedObj = (Reference) igcRestClient.getPropertyByName(detectedClassification, "classifies_asset");

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

                    Object confidence = igcRestClient.getPropertyByName(detectedClassification, "confidencePercent");
                    InstanceProperties relationshipProperties = relationship.getProperties();
                    if (relationshipProperties == null) {
                        relationshipProperties = new InstanceProperties();
                    }

                    /* Before adding to the overall set of relationships, setup the relationship properties
                     * we have in IGC from the 'classification' object. */
                    if (confidence != null) {
                        int confidenceVal = ((Number)confidence).intValue();
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
                    Object threshold = igcRestClient.getPropertyByName(detectedClassification, P_THRESHOLD);
                    if (threshold != null) {
                        relationshipProperties = repositoryHelper.addFloatPropertyToInstance(
                                repositoryName,
                                relationshipProperties,
                                P_THRESHOLD,
                                (Float) threshold,
                                methodName
                        );
                    }
                    if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
                        Object valFreq = igcRestClient.getPropertyByName(detectedClassification, "value_frequency");
                        if (valFreq != null) {
                            relationshipProperties = repositoryHelper.addLongPropertyToInstance(
                                    repositoryName,
                                    relationshipProperties,
                                    "valueFrequency",
                                    ((Double) valFreq).longValue(),
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
                    if (log.isDebugEnabled()) { log.debug("mapDetectedClassifications_fromDataClass - adding relationship: {}", relationship); }
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
                if (log.isDebugEnabled()) { log.debug("mapSelectedClassifications_fromDataClass - adding relationship: {}", relationship); }
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
        for (Reference detectedClassification : detectedClassifications.getItems()) {

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

                    Object confidence = igcRestClient.getPropertyByName(detectedClassification, "confidencePercent");
                    InstanceProperties relationshipProperties = relationship.getProperties();
                    if (relationshipProperties == null) {
                        relationshipProperties = new InstanceProperties();
                    }

                    /* Before adding to the overall set of relationships, setup the relationship properties
                     * we have in IGC from the 'classification' object. */
                    if (confidence != null) {
                        int confidenceVal = ((Number)confidence).intValue();
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
                    Object threshold = igcRestClient.getPropertyByName(detectedClassification, P_THRESHOLD);
                    if (threshold != null) {
                        relationshipProperties = repositoryHelper.addFloatPropertyToInstance(
                                repositoryName,
                                relationshipProperties,
                                P_THRESHOLD,
                                (Float) threshold,
                                methodName
                        );
                    }
                    if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
                        Object valFreq = igcRestClient.getPropertyByName(detectedClassification, "value_frequency");
                        if (valFreq != null) {
                            relationshipProperties = repositoryHelper.addLongPropertyToInstance(
                                    repositoryName,
                                    relationshipProperties,
                                    "valueFrequency",
                                    ((Double) valFreq).longValue(),
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
                    if (log.isDebugEnabled()) { log.debug("mapDetectedClassifications_toDataClass - adding relationship: {}", relationship); }
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
                    log.debug("mapSelectedClassifications_toDataClass - adding relationship: {}", relationship);
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
