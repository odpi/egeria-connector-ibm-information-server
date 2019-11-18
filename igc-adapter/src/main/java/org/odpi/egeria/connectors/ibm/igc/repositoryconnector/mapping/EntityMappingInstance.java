/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
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

    private String igcEntityType;
    private String igcEntityRid;
    private List<String> igcPropertiesToRetrieve;

    private boolean alreadyRetrieved;
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
     * @param igcEntityType the type of IGC object for which to carry out a mapping
     * @param igcEntityRid the RID of the IGC object for which to carry out a mapping
     * @param userId the user through which to do the mapping
     */
    public EntityMappingInstance(EntityMapping mapping,
                                 IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                 String igcEntityType,
                                 String igcEntityRid,
                                 String userId) {

        this.mapping = mapping;
        this.igcomrsRepositoryConnector = igcomrsRepositoryConnector;
        this.igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
        this.igcEntityType = igcEntityType;
        this.igcEntityRid = igcEntityRid;
        this.userId = userId;
        this.omrsRelationships = new ArrayList<>();
        this.omrsClassifications = new ArrayList<>();
        this.alreadyRetrieved = false;

        // Add modification details by default, if available, to the mapping
        if (igcomrsRepositoryConnector.getIGCRestClient().hasModificationDetails(igcEntityType)) {
            for (String property : IGCRestConstants.getModificationProperties()) {
                mapping.addComplexIgcProperty(property);
            }
        }

        // Translate the provided asset to a base asset type for the mapper, if needed
        // (if not needed the 'getBaseIgcAssetFromAlternative' is effectively a NOOP and gives back same object)
        // We need to make use of mapping.getBaseIgcAssetFromAlternative() here before we attempt to retrieve the
        // entity itself, as we may (very rarely) need to retrieve a different entity type than what we've been given
        Reference simple = mapping.getBaseIgcAssetFromAlternative(igcEntityType, igcEntityRid, igcomrsRepositoryConnector);
        if (simple != null) {
            this.igcEntityType = simple.getType();
            this.igcEntityRid = simple.getId();
        }

    }

    /**
     * Creates a new mapping specific to the provided metadata object.
     *
     * @param mapping the definition of the mapping to carry out
     * @param igcomrsRepositoryConnector connectivity to an IGC repository
     * @param igcEntity the already-retrieved IGC object for which to carry out a mapping
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
        this.igcEntityType = igcEntity.getType();
        this.igcEntityRid = igcEntity.getId();
        this.userId = userId;
        this.omrsRelationships = new ArrayList<>();
        this.omrsClassifications = new ArrayList<>();
        this.alreadyRetrieved = true;

        // Translate the provided asset to a base asset type for the mapper, if needed
        // (if not needed the 'getBaseIgcAssetFromAlternative' is effectively a NOOP and gives back same object)
        // We need to make use of mapping.getBaseIgcAssetFromAlternative() here before we attempt to retrieve the
        // entity itself, as we may (very rarely) need to retrieve a different entity type than what we've been given
        Reference simple = mapping.getBaseIgcAssetFromAlternative(igcEntityType, igcEntityRid, igcomrsRepositoryConnector);
        if (simple != null && !simple.getId().equals(igcEntityRid)) {
            // If the base asset has changed, record the new details and reset the status as not yet retrieved
            this.igcEntityType = simple.getType();
            this.igcEntityRid = simple.getId();
            this.alreadyRetrieved = false;
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
     * Retrieve the IGC entity type for which this mapping exists.
     *
     * @return String
     */
    public final String getIgcEntityType() { return igcEntityType; }

    /**
     * Retrieve the IGC entity Repository ID (RID) for which this mapping exists.
     *
     * @return String
     */
    public final String getIgcEntityRid() { return igcEntityRid; }

    /**
     * Indicates whether the IGC entity has already been retrieved (true) or not (false).
     *
     * @return boolean
     */
    public final boolean isIgcEntityAlreadyRetrieved() { return alreadyRetrieved; }

    /**
     * Retrieve the IGC entity itself, which will only be populated after one of 'initializeEntitySummary' or
     * 'initializeEntityDetail' has been called.
     *
     * @return Reference
     * @see #initializeEntitySummary()
     * @see #initializeEntityDetail()
     */
    public final Reference getIgcEntity() { return igcEntity; }

    /**
     * Set the properties that should be included when retrieving the IGC entity.
     *
     * @param properties the list of properties to include in entity retrieval
     */
    public final void setPropertiesToRetrieveForIgcEntity(List<String> properties) {
        igcPropertiesToRetrieve = properties;
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
    public final EntitySummary getOmrsSummary() {
        if (mapping.isOmrsType(igcomrsRepositoryConnector.getIGCRestClient(), igcEntity)) {
            return omrsSummary;
        } else {
            return null;
        }
    }

    /**
     * Retrieve the OMRS EntityDetail for which this mapping exists.
     *
     * @return EntityDetail
     */
    public final EntityDetail getOmrsDetail() {
        if (mapping.isOmrsType(igcomrsRepositoryConnector.getIGCRestClient(), igcEntity)) {
            return omrsDetail;
        } else {
            return null;
        }
    }

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
     * Utility function to initialize an EntitySummary object based on the IGC entity for this mapping.
     */
    public final void initializeEntitySummary() {
        if (omrsSummary == null) {
            omrsSummary = new EntitySummary();
            IGCEntityGuid igcEntityGuid = igcomrsMetadataCollection.getIgcRepositoryHelper().getEntityGuid(
                    igcEntityType,
                    mapping.getIgcRidPrefix(),
                    igcEntityRid);
            omrsSummary.setGUID(igcEntityGuid.asGuid());
            if (!alreadyRetrieved && (igcEntity == null || !igcEntity.isFullyRetrieved())) {
                igcEntity = igcomrsRepositoryConnector.getIGCRestClient().getAssetWithSubsetOfProperties(
                        igcEntityRid,
                        igcEntityType,
                        igcPropertiesToRetrieve
                );
            }
            omrsSummary.setInstanceURL(igcEntity.getUrl());
        }
    }

    /**
     * Utility function to initialize an EntityDetail object based on the IGC entity for this mapping.
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
                IGCEntityGuid igcEntityGuid = igcomrsMetadataCollection.getIgcRepositoryHelper().getEntityGuid(
                        igcEntityType,
                        mapping.getIgcRidPrefix(),
                        igcEntityRid);
                omrsDetail.setGUID(igcEntityGuid.asGuid());
                if (!alreadyRetrieved && (igcEntity == null || !igcEntity.isFullyRetrieved())) {
                    igcEntity = igcomrsRepositoryConnector.getIGCRestClient().getAssetWithSubsetOfProperties(
                            igcEntityRid,
                            igcEntityType,
                            igcPropertiesToRetrieve
                    );
                }
                omrsDetail.setInstanceURL(igcEntity.getUrl());
            } catch (TypeErrorException e) {
                log.error("Unable to get skeleton detail entity, defaulting to basic summary.", e);
            }
        }
    }

    /**
     * Utility function to initialize the IGC entity for this mapping with necessary information to handle relationship
     * mappings.
     *
     * @param sequencingOrder any sequencing order for the relationship properties
     * @param pageSize any page size limitation for the relationship properties
     */
    public final void initializeWithRelationships(SequencingOrder sequencingOrder,
                                                  int pageSize) {
        if (igcEntity == null || !igcEntity.isFullyRetrieved()) {
            ArrayList<String> allProperties = new ArrayList<>();
            List<RelationshipMapping> relationshipMappers = mapping.getRelationshipMappers();
            for (RelationshipMapping relationshipMapping : relationshipMappers) {
                if (log.isDebugEnabled()) { log.debug("Adding properties from mapping: {}", relationshipMapping); }
                allProperties.addAll(relationshipMapping.getIgcRelationshipPropertiesForType(igcEntityType));
            }
            allProperties.addAll(IGCRestConstants.getModificationProperties());
            IGCSearchSorting sort = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
            igcEntity = igcomrsRepositoryConnector.getIGCRestClient().getAssetWithSubsetOfProperties(
                    igcEntityRid,
                    igcEntityType,
                    allProperties,
                    pageSize,
                    sort
            );
        }
    }

}
