/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.CandidateKey;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DatabaseColumn;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.KeyPatternMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
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

    private PrimaryKeyMapper() {
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
     * @param fromIgcObject the IGC object for which the classification should exist
     * @param userId the user requesting the mapped classifications
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
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
                definedPK.getAllPages(igcRestClient);
                for (CandidateKey candidateKey : definedPK.getItems()) {

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
     * @param matchClassificationProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(InstanceProperties matchClassificationProperties) {

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "selected_primary_key",
                "=",
                "true"
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);

        // We can only search by name, so we will ignore all other properties
        if (matchClassificationProperties != null) {
            Map<String, InstancePropertyValue> properties = matchClassificationProperties.getInstanceProperties();
            if (properties.containsKey("name")) {
                PrimitivePropertyValue name = (PrimitivePropertyValue) properties.get("name");
                String keyName = (String) name.getPrimitiveValue();
                IGCSearchCondition propertyCondition = new IGCSearchCondition(
                        "defined_primary_key.name",
                        "=",
                        keyName
                );
                igcSearchConditionSet.addCondition(propertyCondition);
                igcSearchConditionSet.setMatchAnyCondition(true);
            }
        }

        return igcSearchConditionSet;

    }

}
