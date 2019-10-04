/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.TypeErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Captures the instantiation of a mapping for a specific metadata object.
 */
public class EntityMappingInstance {

    private static final Logger log = LoggerFactory.getLogger(EntityMappingInstance.class);

    private EntityMapping mapping;
    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;
    private IGCOMRSMetadataCollection igcomrsMetadataCollection;
    private String userId;

    private Reference igcEntity;
    private EntitySummary omrsSummary;
    private EntityDetail omrsDetail;

    private List<Classification> omrsClassifications;
    private List<Relationship> omrsRelationships;

    /**
     * Creates a new mapping specific to the provided metadata object.
     *
     * @param mapping the definition of the mapping to carry out
     * @param igcomrsRepositoryConnector connectivity to an IGC repository
     * @param igcEntity the IGC object for which to carry out a mapping
     * @param userId the user through which to do the mapping
     */
    public EntityMappingInstance(EntityMapping mapping,
                                 IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                 Reference igcEntity,
                                 String userId) {

        this.mapping = mapping;
        this.igcomrsRepositoryConnector = igcomrsRepositoryConnector;
        this.igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
        this.igcEntity = igcEntity;
        this.userId = userId;
        this.omrsRelationships = new ArrayList<>();
        this.omrsClassifications = new ArrayList<>();

        // Add modification details by default, if available, to the mapping
        if (igcEntity != null && igcomrsRepositoryConnector.getIGCRestClient().hasModificationDetails(igcEntity.getType())) {
            for (String property : IGCRestConstants.getModificationProperties()) {
                mapping.addComplexIgcProperty(property);
            }
        }

    }

    /**
     * Retrieve the mapping definition.
     *
     * @return EntityMapping
     */
    public final EntityMapping getMapping() { return mapping; }

    /**
     * Retrieve the repository connection.
     *
     * @return IGCOMRSRepositoryConnector
     */
    public final IGCOMRSRepositoryConnector getRepositoryConnector() { return igcomrsRepositoryConnector; }

    /**
     * Retrieve the metadata collection.
     *
     * @return IGCOMRSMetadataCollection
     */
    public final IGCOMRSMetadataCollection getMetadataCollection() { return igcomrsMetadataCollection; }

    /**
     * Retrieve the user through which the mapping will be done.
     *
     * @return String
     */
    public final String getUserId() { return userId; }

    /**
     * Retrieve the IGC object for which this mapping exists.
     *
     * @return Reference
     */
    public final Reference getIgcEntity() { return igcEntity; }

    /**
     * Update the IGC object for which this mapping exists.
     *
     * @param properties the list of IGC properties to further retrieve for the asset
     */
    public final void updateIgcEntityWithProperties(List<String> properties) {
        if (!igcEntity.isFullyRetrieved()) {
            igcEntity = igcEntity.getAssetWithSubsetOfProperties(
                    igcomrsRepositoryConnector.getIGCRestClient(),
                    properties.toArray(new String[0])
            );
        }
    }

    /**
     * Update the OMRS EntityDetail with the provided InstanceProperties.
     *
     * @param instanceProperties the properties (and values) with which to update the EntityDetail
     */
    public final void updateOmrsDetailWithProperties(InstanceProperties instanceProperties) {
        omrsDetail.setProperties(instanceProperties);
    }

    /**
     * Retrieve the OMRS EntitySummary for which this mapping exists.
     *
     * @return EntitySummary
     */
    public final EntitySummary getOmrsSummary() { return omrsSummary; }

    /**
     * Retrieve the OMRS EntityDetail for which this mapping exists.
     *
     * @return EntityDetail
     */
    public final EntityDetail getOmrsDetail() { return omrsDetail; }

    /**
     * Retrieve the OMRS Classifications mapped to this entity.
     *
     * @return {@code List<Classification>}
     */
    public List<Classification> getOmrsClassifications() { return omrsClassifications; }

    /**
     * Retrieve the OMRS Relationships mapped to this entity.
     *
     * @return {@code List<Relationship>}
     */
    public List<Relationship> getOmrsRelationships() { return omrsRelationships; }

    /**
     * Utility function to initalize an EntitySummary object based on the IGC entity for this mapping.
     */
    public final void initializeEntitySummary() {
        if (omrsSummary == null) {
            omrsSummary = new EntitySummary();
            String rid = igcEntity.getId();
            String igcRidPrefix = mapping.getIgcRidPrefix();
            if (igcRidPrefix != null) {
                rid = igcRidPrefix + rid;
            }
            omrsSummary.setGUID(igcomrsMetadataCollection.getGuidForRid(rid));
            omrsSummary.setInstanceURL(igcEntity.getUrl());
        }
    }

    /**
     * Utility function to initalize an EntityDetail object based on the IGC entity for this mapping.
     */
    public final void initializeEntityDetail() {
        if (omrsDetail == null) {
            try {
                omrsDetail = igcomrsRepositoryConnector.getRepositoryHelper().getSkeletonEntity(
                        igcomrsRepositoryConnector.getRepositoryName(),
                        igcomrsRepositoryConnector.getMetadataCollectionId(),
                        InstanceProvenanceType.LOCAL_COHORT,
                        userId,
                        mapping.getOmrsTypeDefName()
                );
                omrsDetail.setStatus(InstanceStatus.ACTIVE);
                String rid = igcEntity.getId();
                String igcRidPrefix = mapping.getIgcRidPrefix();
                if (igcRidPrefix != null) {
                    rid = igcRidPrefix + rid;
                }
                omrsDetail.setGUID(igcomrsMetadataCollection.getGuidForRid(rid));
                omrsDetail.setInstanceURL(igcEntity.getUrl());
            } catch (TypeErrorException e) {
                log.error("Unable to get skeleton detail entity, defaulting to basic summary.", e);
            }
        }
    }

}
