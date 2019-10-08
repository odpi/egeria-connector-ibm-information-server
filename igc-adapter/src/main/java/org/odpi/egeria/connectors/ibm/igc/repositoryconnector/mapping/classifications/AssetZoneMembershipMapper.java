/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EntityDetail;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.EntityNotKnownException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Singleton to map the OMRS "AssetZoneMembership" classification.
 * @see AssetZoneMembershipMapper_Database
 * @see AssetZoneMembershipMapper_DeployedDatabaseSchema
 * @see AssetZoneMembershipMapper_FileFolder
 * @see AssetZoneMembershipMapper_DataFile
 */
public class AssetZoneMembershipMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(AssetZoneMembershipMapper.class);

    private static class Singleton {
        private static final AssetZoneMembershipMapper INSTANCE = new AssetZoneMembershipMapper();
    }
    public static AssetZoneMembershipMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private AssetZoneMembershipMapper() {
        super(
                "",
                "",
                "Asset",
                "AssetZoneMembership"
        );
        addMappedOmrsProperty("zoneMembership");
        addSubType(AssetZoneMembershipMapper_Database.getInstance(null));
        addSubType(AssetZoneMembershipMapper_DeployedDatabaseSchema.getInstance(null));
        addSubType(AssetZoneMembershipMapper_FileFolder.getInstance(null));
        addSubType(AssetZoneMembershipMapper_DataFile.getInstance(null));
    }

    protected AssetZoneMembershipMapper(String igcAssetType,
                                        String igcRelationshipProperty,
                                        String omrsEntityType,
                                        String omrsClassificationType) {
        super(
                igcAssetType,
                igcRelationshipProperty,
                omrsEntityType,
                omrsClassificationType
        );
    }

    /**
     * Implements the AssetZoneMembership classification by setting up a default set of zones based on the repository
     * connector configuration. This could be overridden to implement whatever other logic may be necessary to come
     * up with more granular zones, including on a per-IGC-asset-type basis (by overriding within the class subtypes).
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
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        List<String> defaultZones = igcomrsRepositoryConnector.getDefaultZones();
        if (defaultZones != null && !defaultZones.isEmpty()) {
            InstanceProperties classificationProperties = repositoryHelper.addStringArrayPropertyToInstance(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    null,
                    "zoneMembership",
                    defaultZones,
                    "addMappedOMRSClassifications"
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
                log.error("Unable to setup default zone membership to: {}", defaultZones, e);
            }
        }
    }

    /**
     * Implement this method to define how to add an OMRS classification to an existing IGC asset. (Since IGC has no
     * actual concept of classification, this is left as a method to-be-implemented depending on how the implementation
     * desires the classification to be represented within IGC.)
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository via OMRS connector
     * @param igcEntity the IGC object to which to add the OMRS classification
     * @param entityGUID the GUID of the OMRS entity (ie. including any prefix)
     * @param initialProperties the set of classification-specific properties to add to the classification
     * @param userId the user requesting the classification to be added (currently unused)
     * @return EntityDetail the updated entity with the OMRS classification added
     * @throws RepositoryErrorException
     */
    @Override
    public EntityDetail addClassificationToIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    Reference igcEntity,
                                                    String entityGUID,
                                                    InstanceProperties initialProperties,
                                                    String userId) throws RepositoryErrorException, EntityNotKnownException {

        final String methodName = "addClassificationToIGCAsset";

        Map<String, InstancePropertyValue> classificationProperties = null;
        if (initialProperties != null) {
            classificationProperties = initialProperties.getInstanceProperties();
        }

        if (classificationProperties != null || !classificationProperties.isEmpty()) {

            log.error("Classification properties are immutable in IGC.");
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

        IGCOMRSMetadataCollection collection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
        return collection.getIgcRepositoryHelper().getEntityDetail(userId, entityGUID, igcEntity);

    }

}
