/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    protected AssetZoneMembershipMapper() {
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
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param classifications the list of classifications to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the IGC object against which the classification should exist
     * @param userId the user requesting the mapped classifications
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             ObjectCache cache,
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

}
