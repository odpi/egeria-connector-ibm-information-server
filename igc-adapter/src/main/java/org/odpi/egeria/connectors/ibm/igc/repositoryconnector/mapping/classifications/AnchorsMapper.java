/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.OMRSRuntimeException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Singleton to map the OMRS "Anchors" classification.
 */
public class AnchorsMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(AnchorsMapper.class);
    private static final String ADD_ANCHOR_GUID_PROPERTY_TO_ELEMENT =
            "Add anchorGUID property with value '{}' to classification {} of element {}";

    private static final String ANCHOR_GUID = "anchorGUID";
    private static final String REFERENCEABLE = "Referenceable";
    private static final String ANCHORS = "Anchors";

    private static AnchorsMapper ANCHORS_MAPPER;

    public static ClassificationMapping getInstance(IGCVersionEnum version) {
        if (ANCHORS_MAPPER == null) {
            ANCHORS_MAPPER = new AnchorsMapper();
        }
        return ANCHORS_MAPPER;
    }

    private AnchorsMapper() {
        super("", "", REFERENCEABLE, ANCHORS);
        addMappedOmrsProperty(ANCHOR_GUID);
    }

    /**
     * Implements the "Anchors" classification for IGC objects
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param classifications            the list of classifications to which to add
     * @param cache                      a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject              the IGC object for which the classification should exist
     * @param userId                     the user requesting the mapped classifications
     *
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             ObjectCache cache,
                                             Reference fromIgcObject,
                                             String userId) throws RepositoryErrorException {

        final String methodName = "addMappedOMRSClassification";

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        IGCRepositoryHelper igcRepositoryHelper;
        try {
            igcRepositoryHelper = ((IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection()).getIgcRepositoryHelper();
        } catch (RepositoryErrorException e) {
            throw new OMRSRuntimeException(IGCOMRSErrorCode.REST_CLIENT_FAILURE.getMessageDefinition(igcomrsRepositoryConnector.getServerName()),
                    this.getClass().getName(), methodName, e);
        }

        try {
            Identity identity = fromIgcObject.getIdentity(igcRestClient, cache);
            Identity assetIdentity = getParentAssetIdentity(identity);
            if (assetIdentity == null) {
                return;
            }
            IGCEntityGuid assetGuid = igcRepositoryHelper.getEntityGuid(assetIdentity.getAssetType(), null, assetIdentity.getRid());

            Classification classification = getAnchorsClassification(igcomrsRepositoryConnector, fromIgcObject, userId, methodName, assetGuid);
            classifications.add(classification);

            log.debug(ADD_ANCHOR_GUID_PROPERTY_TO_ELEMENT, assetGuid, classification.getName(), identity.getName());
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

    }

    /**
     * Builds the Anchors classification
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param fromIgcObject              the IGC object for which the classification should exist
     * @param userId                     the user requesting the mapped classifications
     * @param methodName                 the calling method name
     * @param assetGuid                  the asset guid that will be set as property to the classification
     *
     * @return the Anchors classification
     *
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    private Classification getAnchorsClassification(IGCOMRSRepositoryConnector igcomrsRepositoryConnector, Reference fromIgcObject, String userId,
                                                    String methodName, IGCEntityGuid assetGuid) throws RepositoryErrorException {
        InstanceProperties classificationProperties = new InstanceProperties();
        classificationProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                igcomrsRepositoryConnector.getRepositoryName(), classificationProperties, ANCHOR_GUID, assetGuid.toString(), methodName);

        return getMappedClassification(igcomrsRepositoryConnector, classificationProperties, fromIgcObject, userId);
    }

    /**
     * Retrieve the Asset-level object's identity from the provided identity.
     *
     * @param identity the identity from which to retrieve the asset level identity
     *
     * @return Identity or null, if there is no asset-level identity
     */
    private Identity getParentAssetIdentity(Identity identity) {
        Identity parent = identity.getParentIdentity();
        if (parent != null) {
            String type = parent.getAssetType();
            // Once we reach database_schema/data_file/category level we have the asset, so return this identity
            if (type.equals("database_schema") || type.equals("data_file") || type.equals("category")) {
                return parent;
            } else {
                // Otherwise continue to recurse
                return getParentAssetIdentity(parent);
            }
        } else {
            // If we get to a point where there is no parent, there is no asset, so return null
            return null;
        }
    }
}
