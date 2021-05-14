/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.eventmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSAuditCode;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSRuntimeException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.model.ChangeSet;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.model.PurgeMarker;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCRelationshipGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.repositoryservices.connectors.openmetadatatopic.OpenMetadataTopicListener;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EntityDetail;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.AttributeTypeDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDefPatch;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryeventmapper.OMRSRepositoryEventMapperBase;
import org.odpi.openmetadata.repositoryservices.ffdc.OMRSErrorCode;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IGCOMRSRepositoryEventMapper supports the event mapper function for the IBM Information Server suite
 * (IBM InfoSphere Information Governance Catalog, Metadata Asset Manager, and Information Analyzer)
 * when used as an open metadata repository.
 */
public class IGCOMRSRepositoryEventMapper extends OMRSRepositoryEventMapperBase
        implements OpenMetadataTopicListener {

    private static final Logger log = LoggerFactory.getLogger(IGCOMRSRepositoryEventMapper.class);
    private static final Duration pollDuration = Duration.ofMillis(100);

    private String sourceName;
    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;
    private IGCOMRSMetadataCollection igcomrsMetadataCollection;
    private IGCRepositoryHelper igcRepositoryHelper;
    private IGCRestClient igcRestClient;
    private String metadataCollectionId;
    private String originatorServerName;
    private String originatorServerType;

    private IGCVersionEnum igcVersion;
    private Properties igcKafkaProperties;
    private String igcKafkaTopic;

    private IGCKafkaConsumerThread igcKafkaConsumer;
    private ObjectMapper mapper;

    /**
     * Default constructor
     */
    public IGCOMRSRepositoryEventMapper() {
        super();
        this.sourceName = "IGCOMRSRepositoryEventMapper";
    }

    /**
     * Indicates that the connector is completely configured and can begin processing.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    @Override
    public void start() throws ConnectorCheckedException {

        super.start();

        final String methodName = "start";

        auditLog.logMessage(methodName, IGCOMRSAuditCode.EVENT_MAPPER_STARTING.getMessageDefinition());

        // Setup IGC OMRS Repository connectivity
        if ( !(repositoryConnector instanceof IGCOMRSRepositoryConnector) ) {
            raiseConnectorCheckedException(IGCOMRSErrorCode.EVENT_MAPPER_IMPROPERLY_INITIALIZED, methodName, null, repositoryConnector.getServerName());
        }
        this.igcomrsRepositoryConnector = (IGCOMRSRepositoryConnector) this.repositoryConnector;
        this.igcVersion = igcomrsRepositoryConnector.getIGCVersion();
        this.igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // TODO: Pick the best topic available based on the version of IGC (for now always default to InfosphereEvents)
        this.igcKafkaTopic = "InfosphereEvents";

        // Retrieve connection details to configure Kafka connectivity
        String igcKafkaBootstrap = this.connectionBean.getEndpoint().getAddress();
        igcKafkaProperties = new Properties();
        igcKafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, igcKafkaBootstrap);
        igcKafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "IGC_EM_" + igcomrsRepositoryConnector.getMetadataCollectionId());
        // TODO: May need to tweak these settings to give further processing time for large events
        //  like IMAM shares -- or even switch to manual offset management rather than auto-commits
        //  (see: https://kafka.apache.org/0110/javadoc/org/apache/kafka/clients/consumer/KafkaConsumer.html)
        igcKafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        igcKafkaProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "300000");
        igcKafkaProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        igcKafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        igcKafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Setup ObjectMapper for (de-)serialisation of events
        this.mapper = new ObjectMapper();

        try {
            this.igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
            this.igcRepositoryHelper = igcomrsMetadataCollection.getIgcRepositoryHelper();
            igcomrsMetadataCollection.setEventMapper(this);
        } catch (RepositoryErrorException e) {
            raiseConnectorCheckedException(IGCOMRSErrorCode.REST_CLIENT_FAILURE, methodName, e, igcomrsRepositoryConnector.getServerName());
        }
        this.metadataCollectionId = igcomrsRepositoryConnector.getMetadataCollectionId();
        this.originatorServerName = igcomrsRepositoryConnector.getServerName();
        this.originatorServerType = igcomrsRepositoryConnector.getServerType();

        boolean success = false;
        try {
            success = igcomrsRepositoryConnector.upsertOMRSBundleZip();
            this.igcRestClient.registerPOJO(OMRSStub.class);
        } catch (RepositoryErrorException | IGCException e) {
            raiseConnectorCheckedException(IGCOMRSErrorCode.OMRS_BUNDLE_FAILURE, methodName, e, "upload");
        }
        if (!success) {
            raiseConnectorCheckedException(IGCOMRSErrorCode.OMRS_BUNDLE_FAILURE, methodName, null, "upload");
        } else {
            this.igcKafkaConsumer = new IGCKafkaConsumerThread();
            igcKafkaConsumer.start();
        }

    }


    /**
     * Class to support multi-threaded consumption of IGC Kafka events.
     */
    private class IGCKafkaConsumerThread implements Runnable {

        private final AtomicBoolean running = new AtomicBoolean(false);

        void start() {
            Thread worker = new Thread(this);
            worker.start();
        }

        void stop() {
            running.set(false);
        }

        /**
         * Read IGC Infosphere topic Kafka events.
         */
        @Override
        public void run() {

            final String methodName = "run";

            running.set(true);
            try (final Consumer<Long, String> consumer = new KafkaConsumer<>(igcKafkaProperties)) {
                consumer.subscribe(Collections.singletonList(igcKafkaTopic));
                auditLog.logMessage(methodName, IGCOMRSAuditCode.EVENT_MAPPER_RUNNING.getMessageDefinition(igcomrsRepositoryConnector.getServerName()));
                while (running.get()) {
                    try {
                        // Per settings on the consumer properties, the poll should only ever return a single event at
                        // a time (meaning despite the for-loop below, the wrapped try-catch should ensure that only one
                        // event ever fails)
                        ConsumerRecords<Long, String> events = consumer.poll(pollDuration);
                        for (ConsumerRecord<Long, String> event : events) {
                            processEvent(event.value());
                        }
                        consumer.commitSync();
                    } catch (Exception e) {
                        auditLog.logException(methodName, IGCOMRSAuditCode.EVENT_MAPPER_CONSUMER_FAILURE.getMessageDefinition(), e);
                    }
                }
            }
        }

    }


    /**
     * Method to pass an event received on topic.
     *
     * @param event inbound event
     */
    @Override
    public void processEvent(String event) {
        log.debug("Processing event: {}", event);
        ObjectCache cache = new ObjectCache();
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            processEventV117(event, cache);
        } else {
            processEventV115(event, cache);
        }
    }

    /**
     * Method to process events from v11.5 of Information Server.
     * Basically this method will simply route between processing IMAM events and normal asset events.
     *
     * @param event inbound event
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void processEventV115(String event, ObjectCache cache) {

        try {
            InfosphereEvents eventObj = this.mapper.readValue(event, InfosphereEvents.class);
            if (eventObj != null) {
                switch (eventObj.getEventType()) {
                    case "IMAM_SHARE_EVENT":
                        processIMAMShareEventV115((InfosphereEventsIMAMEvent) eventObj, cache);
                        break;
                    case "DC_CREATE_EVENT":
                    case "DC_MERGED_EVENT":
                        processDataConnectionEventV115((InfosphereEventsDCEvent) eventObj, cache);
                        break;
                    case "IA_COLUMN_CLASSIFIED_EVENT":
                    case "IA_COLUMN_ANALYZED_EVENT":
                    case "IA_TABLE_RESULTS_PUBLISHED":
                    case "IA_COLUMN_FAILED_EVENT":
                        processIAEventV115((InfosphereEventsIAEvent) eventObj, cache);
                        break;
                    case "IA_PROJECT_CREATED_EVENT":
                    case "IA_TABLE_ADDED_TO_PROJECT":
                    case "IA_TABLES_ADDED_TO_PROJECT":
                    case "IA_TABLE_REMOVED_FROM_PROJECT":
                    case "IA_TABLES_REMOVED_FROM_PROJECT":
                    case "IA_DATARULE_CREATED_EVENT":
                    case "IA_DATARULE_DELETED_EVENT":
                    case "IA_DATARULESET_CREATED_EVENT":
                    case "IA_DATARULESET_DELETED_EVENT":
                    case "IA_DATARULE_DEFINITION_CREATED_EVENT":
                    case "IA_DATARULE_DEFINITION_DELETED_EVENT":
                    case "IA_DATARULESET_DEFINITION_CREATED_EVENT":
                    case "IA_DATARULESET_DEFINITION_DELETED_EVENT":
                    case "IA_COLUMN_ANALYSIS_SUBMITTED_EVENT":
                    case "IA_COLUMN_ANALYSES_SUBMITTED_EVENT":
                    case "IA_COLUMN_ANALYSIS_STARTED_EVENT":
                    case "IA_COLUMN_ANALYSIS_FINISHED_EVENT":
                    case "IA_COLUMN_ANALYSIS_FAILED_EVENT":
                    case "IA_PROFILE_BATCH_COMPLETED_EVENT":
                    case "IA_DATAQUALITY_ANALYSIS_SUBMITTED":
                    case "IA_DATAQUALITY_ANALYSIS_SUBMITTED_EVENT":
                    case "IA_DATAQUALITY_ANALYSES_SUBMITTED_EVENT":
                    case "IA_DATAQUALITY_ANALYSIS_STARTED_EVENT":
                    case "IA_DATAQUALITY_ANALYSIS_FINISHED_EVENT":
                    case "IA_DATAQUALITY_ANALYSIS_FAILED_EVENT":
                    case "DISCOVER_IMPORT_COMPLETE":
                        log.info("Found Information Analyzer event that cannot be processed via APIs, skipping.");
                        break;
                    case "IGC_ETLGROUP_EVENT":
                        log.info("Found DataStage event that should be processed via data engine proxy, skipping.");
                        break;
                    case "IGC_XT_OMRS__GROUP1_EVENT":
                        log.info("Found OMRS Stub event, skipping.");
                        break;
                    default:
                        processAssetEventV115((InfosphereEventsAssetEvent) eventObj, cache);
                        break;
                }
            }
        } catch (IOException e) {
            log.error("Unable to translate event {} into object.", event, e);
        }

    }

    /**
     * Processes IMAM_SHARE_EVENT events from v11.5 of Information Server.
     *
     * @param event inbound event
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void processIMAMShareEventV115(InfosphereEventsIMAMEvent event, ObjectCache cache) {

        Map<String, String> createdRIDs = getRIDsAndTypesFromEventString(event.getCreatedRIDs());
        Map<String, String> updatedRIDs = getRIDsAndTypesFromEventString(event.getMergedRIDs());
        Map<String, String> deletedRIDs = getRIDsAndTypesFromEventString(event.getDeletedRIDs());

        // Start by creating any entities needed by the new RIDs
        for (Map.Entry<String, String> entry : createdRIDs.entrySet()) {
            processAsset(cache, entry.getKey(), entry.getValue());
        }

        // Then iterate through any updated entities
        for (Map.Entry<String, String> entry : updatedRIDs.entrySet()) {
            processAsset(cache, entry.getKey(), entry.getValue());
        }

        // Then iterate through any deleted entities
        for (Map.Entry<String, String> entry : deletedRIDs.entrySet()) {
            sendPurgedEntity(entry.getValue(), entry.getKey(), cache);
        }

    }

    /**
     * Processes Data Connection events from v11.5 of Information Server.
     * @param event the data connection event to process
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void processDataConnectionEventV115(InfosphereEventsDCEvent event, ObjectCache cache) {

        String action = event.getEventType();

        if (action.equals(InfosphereEventsDCEvent.ACTION_CREATE)) {
            processAsset(cache, event.getCreatedRID(), "data_connection");
        } else if (action.equals(InfosphereEventsDCEvent.ACTION_MODIFY)) {
            processAsset(cache, event.getMergedRID(), "data_connection");
        }

    }

    /**
     * Processes all asset-specific events from v11.5 of Information Server.
     *
     * @param event inbound event
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void processAssetEventV115(InfosphereEventsAssetEvent event, ObjectCache cache) {

        String assetRid = event.getAssetRid();
        String action = event.getAction();

        // And propagate based on the action of the event
        switch (action) {
            case InfosphereEventsAssetEvent.ACTION_CREATE:
            case InfosphereEventsAssetEvent.ACTION_MODIFY:
            case InfosphereEventsAssetEvent.ACTION_DELETE:
                String igcAssetDisplayName = event.getAssetType();
                if (igcAssetDisplayName != null && !igcAssetDisplayName.equals("OMRS Stub")) {
                    String igcAssetType = igcRepositoryHelper.getIgcAssetTypeForAssetName(igcAssetDisplayName);
                    processAsset(cache, assetRid, igcAssetType);
                }
                break;
            case InfosphereEventsAssetEvent.ACTION_ASSIGNED_RELATIONSHIP:
                // TODO: one exception to this is the ADDition of a note, which generates ONLY this type of event (with
                //  the RID of the asset indicated being the RID of the asset to which the note was added, not the RID
                //  of the note itself).  The removal of a note is still listed as a MODIFY against the asset the note
                //  was attached to.
                log.debug("Ignoring ASSIGNED_RELATIONSHIP event -- should be handled already by an earlier CREATE or MODIFY event: {}", event);
                break;
            default:
                log.warn("Action '{}' is not yet implemented: {}", action, event);
                break;
        }

    }

    /**
     * Processes all process-able Information Analyzer events from v11.5 of Information Server.
     *
     * @param event inbound event
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void processIAEventV115(InfosphereEventsIAEvent event, ObjectCache cache) {

        final String methodName = "processIAEventV115";

        String action = event.getEventType();

        switch(action) {
            case InfosphereEventsIAEvent.COL_ANALYZED:
            case InfosphereEventsIAEvent.COL_CLASSIFIED:
                // TODO: could potentially retrieve more from these via IA REST API...
                log.info("Column / field analyzed or classified, but not yet published -- skipping: {}", action);
                break;
            case InfosphereEventsIAEvent.TBL_PUBLISHED:
                // This is the only event we can really do something with, as IGC API can only see
                // published information
                String containerRid = event.getDataCollectionRid();
                try {
                    // We must do this initial retrieval as insufficient detail in event payload to know whether it is a
                    // database table or a file record that was published
                    Reference containerAsset = igcRestClient.getAssetRefById(containerRid);
                    String searchProperty = null;
                    String searchAssetType = null;
                    switch (containerAsset.getType()) {
                        case "database_table":
                            searchProperty = "database_table_or_view";
                            searchAssetType = "database_column";
                            break;
                        case "data_file_record":
                            searchProperty = "data_file_record";
                            searchAssetType = "data_file_field";
                            break;
                        default:
                            log.warn("Unimplemented asset type '{}' for IA publishing: {}", containerAsset.getType(), event);
                            break;
                    }
                    // First process the containing asset itself
                    processAsset(cache, containerRid, containerAsset.getType());
                    // We should also check the columns / file fields within the table / file for changes to be processed,
                    // as the relationship itself between column and table may not change but there may be
                    // new classifications on the columns / fields from the publication
                    IGCSearchCondition igcSearchCondition = new IGCSearchCondition(searchProperty, "=", containerAsset.getId());
                    IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
                    IGCSearch igcSearch = new IGCSearch(searchAssetType, new String[]{searchProperty}, igcSearchConditionSet);
                    ItemList<Reference> subAssets = igcRestClient.search(igcSearch);
                    if (subAssets != null) {
                        List<Reference> allSubAssets = igcRestClient.getAllPages(null, subAssets);
                        log.debug("Processing {} child assets from IA publication: {}", subAssets.getPaging().getNumTotal(), containerRid);
                        for (Reference child : allSubAssets) {
                            processAsset(cache, child.getId(), child.getType());
                        }
                    } else {
                        log.warn("Unable to find any sub-assets for IA published container '{}': {}", containerRid, event);
                    }
                } catch (IGCException e) {
                    raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }
                break;
            default:
                log.info("Action '{}' is not yet implemented for IA: {}", action, event);
                break;
        }

    }

    /**
     * Attempt to retrieve the EntityDetail object for the provided asset, using the provided Repository ID (RID).
     * Useful for when the RID indicates there is some generated entity that does not actually exist on its own in
     * IGC. Will handle any errors if unable to retrieve the asset, and the EntityDetail will simply be null.
     *
     * @param asset the IGC asset for which to retrieve an EntityDetail object
     * @param guid the IGC GUID to use for the asset
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return EntityDetail
     */
    private EntityDetail getEntityDetailForAssetWithGUID(Reference asset, IGCEntityGuid guid, ObjectCache cache) {

        EntityDetail detail = null;
        try {
            detail = igcRepositoryHelper.getEntityDetailFromFullAsset(cache, localServerUserId, guid, asset);
        } catch (EntityNotKnownException e) {
            log.error("Unable to find EntityDetail for GUID: {}", guid, e);
        } catch (RepositoryErrorException e) {
            log.error("Unexpected error in retrieving EntityDetail for GUID: {}", guid, e);
        }
        return detail;

    }

    /**
     * Attempt to retrieve the EntityDetail object for the provided OMRS stub, using the provided Repository ID (RID).
     * Useful for when the RID indicates there is some generated entity that does not actually exist on its own in
     * IGC. Will handle any errors if unable to retrieve the asset, and the EntityDetail will simply be null.
     *
     * @param stub the OMRS stub for which to retrieve an EntityDetail object
     * @param guid the IGC GUID to use for the asset
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return EntityDetail
     */
    private EntityDetail getEntityDetailForStubWithGUID(OMRSStub stub, IGCEntityGuid guid, ObjectCache cache) {

        EntityDetail detail = null;
        log.debug("Retrieving EntityDetail for stub: {}", stub);
        Reference asset = getIgcAssetFromStubPayload(stub);
        if (asset != null) {
            // If no RID was provided, take it from the asset we retrieved
            if (guid == null) {
                guid = igcRepositoryHelper.getEntityGuid(asset.getType(), null, asset.getId());
            }
            log.debug(" ... retrieved asset from stub: {}", asset);
            try {
                detail = igcRepositoryHelper.getEntityDetailFromFullAsset(cache, localServerUserId, guid, asset);
            } catch (EntityNotKnownException e) {
                log.error("Unable to find EntityDetail for stub with GUID: {}", guid, e);
            } catch (RepositoryErrorException e) {
                log.error("Unexpected error in retrieving EntityDetail for stub: {}", stub.getId(), e);
            }
        }
        return detail;

    }

    /**
     * Attempt to instantiate an IGC Reference object from the provided OMRS stub's payload.
     *
     * @param stub the OMRS stub for which to retrieve an IGC Reference object
     * @return Reference
     */
    private Reference getIgcAssetFromStubPayload(OMRSStub stub) {
        final String methodName = "getIgcAssetFromStubPayload";
        Reference asset = null;
        if (stub != null) {
            try {
                log.debug("Retrieving IGC Reference for stub payload: {}", stub.getPayload());
                asset = igcomrsRepositoryConnector.getIGCRestClient().readJSONIntoPOJO(stub.getPayload());
                asset.setFullyRetrieved();
            } catch (IGCException e) {
                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        }
        return asset;
    }

    /**
     * Parses the RID and data type out of the provided payload string.
     *
     * @param payload a string of RIDs and IMAM data types from an IMAM share event
     * @return {@code Map<String, String>} with RID as key and IGC data type as value
     */
    private Map<String, String> getRIDsAndTypesFromEventString(String payload) {

        Map<String, String> dict = new HashMap<>();
        if (payload != null && payload.length() != 0) {
            for (String asset : payload.split(",")) {
                asset = asset.trim();
                String[] assetTokens = asset.split(":");
                if (assetTokens.length == 2) {
                    String type = assetTokens[0];
                    String rid = assetTokens[1];
                    dict.put(rid, IGCRestConstants.getImamTypeToIgcType().getOrDefault(type, null));
                } else {
                    log.warn("Unexpected number of tokens ({}) from event payload: {}", assetTokens.length, payload);
                }
            }
        }
        return dict;

    }

    /**
     * Processes the provided asset according to what we determine about its status (eg. deleted, new, or updated).
     * Will also call into processRelationship as-needed if a relationship is detected as changed.
     *
     * Note that this will process events for all assets (generated and non-generated) based on the provided IGC asset
     * details.
     *
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param rid the Repository ID (RID) of the asset in question
     * @param assetType the type of asset (ie. if provided in the event payload)
     */
    private void processAsset(ObjectCache cache, String rid, String assetType) {
        processAsset(cache, rid, assetType, null, null);
    }

    /**
     * Processes the provided asset according to what we determine about its status (eg. deleted, new, or updated).
     * Will also call into processRelationship as-needed if a relationship is detected as changed.
     *
     * If 'limitToPrefix' is specified, this will only process events for generated IGC assets whose prefix matches
     * the provided value.
     *
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param rid the Repository ID (RID) of the asset in question
     * @param assetType the type of asset (ie. if provided in the event payload)
     * @param relationshipGUID the relationship GUID that triggered this asset to be processed (or null if not triggered
     *                         by relationship being processed)
     * @param limitToPrefix if specified, limit the relationships to only those where the asset is prefixed by this prefix
     */
    private void processAsset(ObjectCache cache,
                              String rid,
                              String assetType,
                              IGCRelationshipGuid relationshipGUID,
                              String limitToPrefix) {

        // TODO: make use of 'limitToPrefix' to limit processing to only generated entities

        final String methodName = "processAsset";
        log.debug("processAsset called with rid {} and type {}", rid, assetType);

        Reference latestVersion = null;
        try {
            latestVersion = igcRepositoryHelper.getFullAssetDetails(rid, assetType);
        } catch (IGCException e) {
            raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

        if (latestVersion == null) {
            // If we can't retrieve the asset by RID, it no longer exists -- so send a delete event
            // TODO: currently only possible if we also know the assetType
            if (assetType != null) {
                // this should also ensure that any generated entities are purged
                sendPurgedEntity(assetType, rid, cache);
            } else {
                log.warn("No asset type was provided for purged RID {} -- cannot generate purgeEntity event.", rid);
            }
        } else {

            // Otherwise see if there's a stub...
            OMRSStub stub = null;
            ChangeSet changeSet = null;
            Set<String> changedProperties = Collections.emptySet();
            try {
                stub = igcRepositoryHelper.getOMRSStubForAsset(latestVersion);
                // Calculate the delta between the latest version and the previous saved stub
                changeSet = new ChangeSet(igcRestClient, latestVersion, stub);
                changedProperties = changeSet.getChangedProperties();
            } catch (IGCException e) {
                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }

            // Output any entities first
            if (stub == null) {
                // If there is no stub, we need to treat this as a new entity
                log.debug("Creating a new entity and stub for: {}", latestVersion.getId());
                sendNewEntity(latestVersion, cache);
            } else if (!changedProperties.isEmpty()) {
                // Otherwise, it should be treated as an updated entity, but only if there was some change
                if (log.isDebugEnabled()) {
                    log.debug("Detected changes on entity {}", latestVersion.getId());
                    log.debug(" ... properties that changed: {}", changedProperties);
                    for (String propertyName : changedProperties) {
                        log.debug(" ...... details for property '{}'", propertyName);
                        log.debug(" .......... {}", changeSet.getChangesForProperty(propertyName));
                    }
                    log.debug(" ... before: {}", stub.getPayload());
                    try {
                        log.debug(" ... now:    {}", igcRestClient.getValueAsJSON(latestVersion));
                    } catch (IGCParsingException e) {
                        log.error(" ... unable to log the 'now' due to a parsing exception.", e);
                    }
                }
                sendUpdatedEntity(latestVersion, stub, cache);
            } else {
                log.info("Skipping asset - no changes detected: {}", latestVersion.getId());
            }

            // Retrieve the mapping from IGC property name to OMRS relationship type
            Map<String, List<RelationshipMapping>> relationshipMap = igcRepositoryHelper.getIgcPropertiesToRelationshipMappings(
                    latestVersion.getType(),
                    localServerUserId
            );
            log.debug(" ... found mappings: {}", relationshipMap);

            // Regardless of whether changedProperties is empty or not, we need to potentially process the
            // relationships for generated types (ie. where the igcProperty = self-reference sentinel), and this
            // self-reference sentinel will never be in the change set since it isn't a real property on the asset!
            // (We should also do this first, in case the code below attempts to update these self-referencing relationships)
            if (relationshipMap.containsKey(RelationshipMapping.SELF_REFERENCE_SENTINEL)) {
                for (RelationshipMapping relationshipMapping : relationshipMap.get(RelationshipMapping.SELF_REFERENCE_SENTINEL)) {
                    processSelfReferencingRelationship(relationshipMapping, latestVersion, stub, relationshipGUID);
                }
            }

            // And then recursively process relationships (which will in turn recursively process further
            // assets), to ensure top-level entities are ultimately output before lower-level entities
            if (!changedProperties.isEmpty()) {
                // Iterate through the properties that differ, looking for any that represent a mapped relationship
                for (String igcProperty : changeSet.getChangedProperties()) {
                    log.debug(" ... checking for any relationship on: {}", igcProperty);
                    if (relationshipMap.containsKey(igcProperty)) {
                        List<ChangeSet.Change> changesForProperty = changeSet.getChangesForProperty(igcProperty);
                        log.debug(" ...... found differences for property: {}", changesForProperty);
                        for (RelationshipMapping relationshipMapping : relationshipMap.get(igcProperty)) {
                            try {
                                processRelationships(
                                        relationshipMapping,
                                        cache,
                                        latestVersion,
                                        changesForProperty,
                                        relationshipGUID
                                );
                            } catch (RepositoryErrorException e) {
                                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                            }
                        }
                    }
                }
            }

        }

    }

    /**
     * Processes the provided relationship mapping to what we determine about its status (ie. new, updated, etc).
     * Will also call into processAsset as-needed before outputting the relationship to ensure that any referenced
     * asset has already been published as an event before the relationship is published.
     *
     * @param relationshipMapping the relationship mapping defining the IGC and OMRS properties and relationship type
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param latestVersion the latest version of the IGC asset from which to get the relationship(s)
     * @param changesForProperty the list of changes for the IGC relationship property being processed
     * @param relationshipTriggerGUID the GUID of the relationship that triggered this processing (or null if not
     *                                triggered initially by the processing of another relationship)
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    private void processRelationships(RelationshipMapping relationshipMapping,
                                      ObjectCache cache,
                                      Reference latestVersion,
                                      List<ChangeSet.Change> changesForProperty,
                                      IGCRelationshipGuid relationshipTriggerGUID) throws RepositoryErrorException {

        final String methodName = "processRelationships";
        log.debug("processRelationships called with relationshipMapping {}, reference {} and changes {}", relationshipMapping, latestVersion, changesForProperty);

        for (ChangeSet.Change change : changesForProperty) {

            String assetType = latestVersion.getType();
            try {
                List<String> referenceListProperties = igcRestClient.getPagedRelationshipPropertiesForType(assetType);
                if (referenceListProperties != null) {

                    Object relatedValue = change.getNewValue(referenceListProperties);
                    log.debug(" ... found value: {}", relatedValue);
                    if (relatedValue != null) {
                        if (relatedValue instanceof ItemList) {

                            log.debug(" ... found ItemList, processing each item");
                            ItemList<?> related = (ItemList<?>) relatedValue;
                            for (Reference relatedAsset : related.getItems()) {
                                processOneOrMoreRelationships(
                                        relationshipMapping,
                                        latestVersion,
                                        relatedAsset,
                                        cache,
                                        referenceListProperties,
                                        change,
                                        relationshipTriggerGUID
                                );
                            }

                        } else if (relatedValue instanceof Reference) {
                            log.debug(" ... found single Reference, processing it");
                            Reference relatedAsset = (Reference) relatedValue;
                            processOneOrMoreRelationships(
                                    relationshipMapping,
                                    latestVersion,
                                    relatedAsset,
                                    cache,
                                    referenceListProperties,
                                    change,
                                    relationshipTriggerGUID
                            );
                        } else if (change.getIgcPropertyPath().endsWith("_id") && Reference.isSimpleType(relatedValue)) {
                            // In cases where a single object has been replaced, the JSON Patch may only show each property
                            // as needing replacement rather than the object as a whole -- this _should_ already be handled
                            // automatically by the change processing, so if we arrive here we missed something in the
                            // ChangeSet class's processing
                            log.error(" ... change consolidation in ChangeSet did not work: {}", change);
                        } else if (!change.getIgcPropertyPath().endsWith("_name") && !change.getIgcPropertyPath().endsWith("_url") && !change.getIgcPropertyPath().endsWith("_type")) {
                            // if the path ends with '_name', '_url' or '_type' then we should already be handling it by the '_id' clause above
                            log.warn("Expected relationship for path '{}' for guid {} but found neither Reference nor ItemList: {}", change.getIgcPropertyPath(), latestVersion.getId(), relatedValue);
                        }
                    } else {
                        log.warn("Expected relationship for path '{}' for guid {} but found nothing.", change.getIgcPropertyPath(), latestVersion.getId());
                    }
                } else {
                    log.warn("No registered POJO to translate asset type '{}' for guid {} -- skipping its relationships.", assetType, latestVersion.getId());
                }
            } catch (IGCException e) {
                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }

        }

    }

    /**
     * Maps the received assets to one or more assets that ultimately represent relationships of this type. In most
     * cases, this will simply be a one-to-one mapping, but this method ensures for the rare cases where the mapping
     * is one-to-many or many-to-many that all such relationships are processed.
     *
     * @param relationshipMapping the relationship mapping through which to translate the relationship
     * @param latestVersion the latest version (non-stub) for one end of the relationship
     * @param relatedAsset the latest version (non-stub) for the other end of the relationship
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param referenceListProperties the list of IGC property names that contain reference lists
     * @param change the JSON Patch entry indicating a specific change (always from the perspective of latestVersion)
     * @param relationshipTriggerGUID passthrough of GUID for relationship that triggered this process (if not triggered
     *                                directly from an event), null if not triggered by another relationship
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    private void processOneOrMoreRelationships(RelationshipMapping relationshipMapping,
                                               Reference latestVersion,
                                               Reference relatedAsset,
                                               ObjectCache cache,
                                               List<String> referenceListProperties,
                                               ChangeSet.Change change,
                                               IGCRelationshipGuid relationshipTriggerGUID) throws RepositoryErrorException {

        final String methodName = "processOneOrMoreRelationships";
        String omrsRelationshipType = relationshipMapping.getOmrsRelationshipType();
        String latestVersionRID = latestVersion.getId();

        String relatedRID = relatedAsset.getId();

        // Only proceed if there is actually some related RID (ie. it wasn't just an empty list of relationships)
        if (relatedRID != null && !relatedRID.equals("null")) {

            log.debug("processOneOrMoreRelationships processing between {} and {} for type: {}", latestVersionRID, relatedRID, omrsRelationshipType);

            RelationshipMapping.ProxyMapping pmOne = relationshipMapping.getProxyOneMapping();
            RelationshipMapping.ProxyMapping pmTwo = relationshipMapping.getProxyTwoMapping();

            // Translate the provided assets into the actual assets needed for the relationship
            String latestVersionType = latestVersion.getType();
            String relatedAssetType = relatedAsset.getType();
            List<Reference> proxyOnes = new ArrayList<>();
            List<Reference> proxyTwos = new ArrayList<>();
            String relationshipLevelRid = null;
            if (relationshipMapping.hasRelationshipLevelAsset()) {
                String relationshipLevelType = relationshipMapping.getRelationshipLevelIgcAsset();
                if (latestVersionType.equals(relationshipLevelType)) {
                    relationshipLevelRid = latestVersionRID;
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(latestVersion, igcRestClient, cache);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(latestVersion, igcRestClient, cache);
                } else if (relatedAssetType.equals(relationshipLevelType)) {
                    relationshipLevelRid = relatedRID;
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(relatedAsset, igcRestClient, cache);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(relatedAsset, igcRestClient, cache);
                }
            } else if (relationshipMapping.sameTypeOnBothEnds() && !relationshipMapping.samePropertiesOnBothEnds()) {
                String igcPropertyName = change.getIgcPropertyName();
                log.debug(" ... relationship is the same on both ends, but property differs: {}", igcPropertyName);
                if (pmOne.getIgcRelationshipProperties().contains(igcPropertyName)) {
                    log.debug(" ... proxy mapping 1 contains the property, setting 1 from '{}' and 2 from '{}'", latestVersion.getName(), relatedAsset.getName());
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(latestVersion, igcRestClient, cache);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(relatedAsset, igcRestClient, cache);
                } else if (pmTwo.getIgcRelationshipProperties().contains(igcPropertyName)) {
                    log.debug(" ... proxy mapping 2 contains the property, setting 1 from '{}' and 2 from '{}'", relatedAsset.getName(), latestVersion.getName());
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(relatedAsset, igcRestClient, cache);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(latestVersion, igcRestClient, cache);
                }
            } else if (relationshipMapping.sameTypeOnBothEnds()
                    || (pmOne.matchesAssetType(latestVersionType) && pmTwo.matchesAssetType(relatedAssetType))) {
                proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(latestVersion, igcRestClient, cache);
                proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(relatedAsset, igcRestClient, cache);
            } else if (pmTwo.matchesAssetType(latestVersionType) && pmOne.matchesAssetType(relatedAssetType)) {
                proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(relatedAsset, igcRestClient, cache);
                proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(latestVersion, igcRestClient, cache);
            } else if (relationshipMapping.hasLinkingAsset()) {
                String linkingType = relationshipMapping.getLinkingAssetType();
                if ( (latestVersionType.equals(linkingType) && pmTwo.matchesAssetType(relatedAssetType))
                        || (relatedAssetType.equals(linkingType) && pmOne.matchesAssetType(latestVersionType)) ) {
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(latestVersion, igcRestClient, cache);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(relatedAsset, igcRestClient, cache);
                } else if ( (relatedAssetType.equals(linkingType) && pmTwo.matchesAssetType(latestVersionType))
                        || (latestVersionType.equals(linkingType) && pmOne.matchesAssetType(relatedAssetType)) ) {
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(relatedAsset, igcRestClient, cache);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(latestVersion, igcRestClient, cache);
                } else {
                    log.warn("Unable to match assets to proxies through linking asset '{}' for guids {} and {} through relationship type {} -- skipped, but this is likely indicative of a problem with the mapping.", linkingType, latestVersion.getId(), relatedAsset.getId(), omrsRelationshipType);
                }
            } else {
                log.warn("Unable to match assets {} and {} to proxies for any asset translation for relationship type {} -- skipped, but this is likely indicative of a problem with the mapping.", latestVersion.getId(), relatedAsset.getId(), omrsRelationshipType);
                proxyOnes.add(latestVersion);
                proxyTwos.add(relatedAsset);
            }

            for (Reference proxyOne : proxyOnes) {
                for (Reference proxyTwo : proxyTwos) {
                    try {
                        processSingleRelationship(
                                relationshipMapping,
                                proxyOne,
                                proxyTwo,
                                cache,
                                referenceListProperties,
                                change,
                                relationshipLevelRid,
                                relationshipTriggerGUID
                        );
                    } catch (RepositoryErrorException e) {
                        raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                    }
                }
            }

        } else {
            log.info("Related RID for guid {} and relationship {} was null -- skipped.", latestVersion.getId(), omrsRelationshipType);
        }

    }

    /**
     * Processes a single relationship based on the provided mapping, IGC objects and JSON Patch entry. As part of the
     * processing, will determine automatically which IGC object provided should be which end of the relationship,
     * based on the provided mapping.
     *
     * @param relationshipMapping the relationship mapping through which to translate the relationship
     * @param proxyOne the latest version (non-stub) for one end of the relationship
     * @param proxyTwo the latest version (non-stub) for the other end of the relationship
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param referenceListProperties the list of IGC property names that contain reference lists
     * @param change the JSON Patch entry indicating a specific change
     * @param relationshipLevelRid the RID of the relationship-level asset (if any, or null if none)
     * @param relationshipTriggerGUID passthrough of GUID for relationship that triggered this process (if not triggered
     *                                directly from an event), null if not triggered by another relationship
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    private void processSingleRelationship(RelationshipMapping relationshipMapping,
                                           Reference proxyOne,
                                           Reference proxyTwo,
                                           ObjectCache cache,
                                           List<String> referenceListProperties,
                                           ChangeSet.Change change,
                                           String relationshipLevelRid,
                                           IGCRelationshipGuid relationshipTriggerGUID) throws RepositoryErrorException {

        String omrsRelationshipType = relationshipMapping.getOmrsRelationshipType();
        String latestVersionRID = proxyOne.getId();

        String relatedRID = proxyTwo.getId();

        // Only proceed if there is actually some related RID (ie. it wasn't just an empty list of relationships),
        // and only if the relationship mapping considers this an actual relationship to include
        if (relatedRID != null
                && !relatedRID.equals("null")
                && relationshipMapping.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, cache, proxyOne, proxyTwo)) {

            log.debug("processSingleRelationship processing between {} and {} for type: {}", latestVersionRID, relatedRID, omrsRelationshipType);

            RelationshipMapping.ProxyMapping pmOne = relationshipMapping.getProxyOneMapping();
            RelationshipMapping.ProxyMapping pmTwo = relationshipMapping.getProxyTwoMapping();

            IGCRelationshipGuid igcRelationshipGuid = RelationshipMapping.getRelationshipGUID(
                    igcRepositoryHelper,
                    relationshipMapping,
                    proxyOne,
                    proxyTwo,
                    change.getIgcPropertyName(),
                    relationshipLevelRid,
                    true
            );

            log.debug(" ... calculated relationship GUID: {}", igcRelationshipGuid);

            // Only continue if this relationship is different from the one that triggered the processing in the first place
            if (relationshipTriggerGUID == null || !relationshipTriggerGUID.equals(igcRelationshipGuid)) {
                String changeType = change.getOp();
                log.debug(" ... change action: {}", changeType);

                if (changeType.equals("remove")) {
                    try {
                        RelationshipDef relationshipDef = (RelationshipDef) igcomrsMetadataCollection.getTypeDefByName(
                                localServerUserId,
                                omrsRelationshipType
                        );
                        sendPurgedRelationship(
                                relationshipMapping,
                                relationshipDef,
                                cache,
                                igcRelationshipGuid,
                                change.getIgcPropertyName(),
                                proxyOne,
                                proxyTwo
                        );

                        // After purging the relationship, process any other updates
                        // on the assets at each end of the relationship
                        String proxyOneType = proxyOne.getType();
                        String proxyTwoType = proxyTwo.getType();

                        if (!IGCRestConstants.getRelationshipLevelTypes().contains(proxyTwoType)) {
                            IGCEntityGuid igcEntityGuid2 = RelationshipMapping.getProxyTwoGuidFromRelationship(
                                    igcRepositoryHelper,
                                    igcRelationshipGuid);
                            processAsset(cache, igcEntityGuid2.getRid(),
                                    proxyTwoType,
                                    igcRelationshipGuid,
                                    igcEntityGuid2.getGeneratedPrefix());
                        } else {
                            log.debug(" ... proxy two was a relationship-level type, not processing it as an asset: {}", proxyTwoType);
                        }
                        if (!IGCRestConstants.getRelationshipLevelTypes().contains(proxyOneType)) {
                            IGCEntityGuid igcEntityGuid1 = RelationshipMapping.getProxyOneGuidFromRelationship(
                                    igcRepositoryHelper,
                                    igcRelationshipGuid);
                            processAsset(cache, igcEntityGuid1.getRid(),
                                    proxyOneType,
                                    igcRelationshipGuid,
                                    igcEntityGuid1.getGeneratedPrefix());
                        } else {
                            log.debug(" ... proxy one was a relationship-level type, not processing it as an asset: {}", proxyOneType);
                        }
                    } catch (InvalidParameterException | RepositoryErrorException | TypeDefNotKnownException e) {
                        log.error("Unable to retrieve relationship type definition: {}", omrsRelationshipType, e);
                    }
                } else {
                    try {

                        Relationship relationship = igcomrsMetadataCollection.getRelationship(localServerUserId, igcRelationshipGuid.toString());
                        log.debug(" ... retrieved relationship: {}", relationship);

                        // Recursively call processAsset(rid, null) on any non-deletion events
                        // (do this first: so relationship comes after on unwinding from recursion)
                        String proxyOneType = proxyOne.getType();
                        String proxyTwoType = proxyTwo.getType();

                        if (!IGCRestConstants.getRelationshipLevelTypes().contains(proxyTwoType)) {
                            processAsset(cache, relatedRID, proxyTwo.getType(), igcRelationshipGuid, igcRelationshipGuid.getGeneratedPrefix2());
                        } else {
                            log.debug(" ... proxy two was a relationship-level type, not processing it as an asset: {}", proxyTwoType);
                        }
                        if (!IGCRestConstants.getRelationshipLevelTypes().contains(proxyOneType)) {
                            processAsset(cache, latestVersionRID, proxyOne.getType(), igcRelationshipGuid, igcRelationshipGuid.getGeneratedPrefix1());
                        } else {
                            log.debug(" ... proxy one was a relationship-level type, not processing it as an asset: {}", proxyOneType);
                        }

                        // Send the appropriate patch-defined action
                        switch (changeType) {
                            case "add":
                                sendNewRelationship(relationship, relationshipLevelRid);
                                break;
                            case "replace":
                                sendReplacedRelationship(
                                        relationshipMapping,
                                        relationship,
                                        cache,
                                        proxyOne,
                                        proxyTwo,
                                        relationshipLevelRid,
                                        referenceListProperties,
                                        change
                                );
                                break;
                            default:
                                log.warn("Unknown action '{}' for relationship {}", changeType, igcRelationshipGuid);
                                break;
                        }

                    } catch (RelationshipNotKnownException e) {
                        log.error("Unable to find relationship with GUID: {}", igcRelationshipGuid, e);
                    } catch (InvalidParameterException e) {
                        log.error("Unknown error occurred trying to retrieve relationship: {}", igcRelationshipGuid, e);
                    }
                }
            } else {
                log.info("Relationship was same as one that triggered this processing -- skipping: {}", relationshipTriggerGUID);
            }

        } else {
            log.warn("Related RID for relationship type {} and guid {} was null, or explicitly excluded.", omrsRelationshipType, latestVersionRID);
        }

    }

    /**
     * Process relationship for a self-referencing IGC entity (ie. one that splits between two OMRS entities).
     *
     * @param relationshipMapping the relationship mapping through which to translate the relationship
     * @param latestVersion the self-referencing IGC asset
     * @param stub the stub for the last version of the self-referencing IGC asset (or null if new)
     * @param relationshipTriggerGUID passthrough of GUID for relationship that triggered this process (if not triggered
     *                                directly from an event), null if not triggered by another relationship
     */
    private void processSelfReferencingRelationship(RelationshipMapping relationshipMapping,
                                                    Reference latestVersion,
                                                    OMRSStub stub,
                                                    IGCRelationshipGuid relationshipTriggerGUID) {

        String omrsRelationshipType = relationshipMapping.getOmrsRelationshipType();
        String latestVersionRID = latestVersion.getId();

        log.debug("processSelfReferencingRelationship processing for {} and type: {}", latestVersionRID, omrsRelationshipType);

        IGCRelationshipGuid igcRelationshipGuid = RelationshipMapping.getRelationshipGUID(
                igcRepositoryHelper,
                relationshipMapping,
                latestVersion,
                latestVersion,
                RelationshipMapping.SELF_REFERENCE_SENTINEL,
                null
        );
        log.debug(" ... calculated relationship GUID: {}", igcRelationshipGuid);

        // Only continue if this relationship is different from the one that triggered the processing in the first place
        if (relationshipTriggerGUID == null || !relationshipTriggerGUID.equals(igcRelationshipGuid)) {
            try {
                Relationship relationship = igcomrsMetadataCollection.getRelationship(localServerUserId, igcRelationshipGuid.toString());
                log.debug(" ... retrieved relationship: {}", relationship);
                // If there was no stub, this is a new entity
                if (stub == null) {
                    sendNewRelationship(relationship, null);
                } else {
                    sendUpdatedRelationship(relationship, stub);
                }
                // Note: no need to call back to processAsset with prefixed GUID as it will have been handled already
                // by the base asset being processed
            } catch (RelationshipNotKnownException e) {
                log.error("Unable to find relationship with GUID: {}", igcRelationshipGuid, e);
            } catch (InvalidParameterException | RepositoryErrorException e) {
                log.error("Unknown error occurred trying to retrieve relationship: {}", igcRelationshipGuid, e);
            }
        } else {
            log.info("Relationship was same as the one that triggered this processing -- skipping: {}", relationshipTriggerGUID);
        }

    }

    /**
     * Send an event out on OMRS topic for a new relationship.
     *
     * @param relationship the new relationship to publish
     * @param relationshipLevelRid the RID of the IGC asset that exists at relationship-level, or null if none
     */
    private void sendNewRelationship(Relationship relationship, String relationshipLevelRid) {
        final String methodName = "sendNewRelationship";
        if (relationship != null) {
            repositoryEventProcessor.processNewRelationshipEvent(
                    sourceName,
                    metadataCollectionId,
                    originatorServerName,
                    originatorServerType,
                    null,
                    relationship
            );
            if (relationshipLevelRid != null) {
                try {
                    Reference relationshipLevelAsset = igcRestClient.getAssetById(relationshipLevelRid);
                    if (relationshipLevelAsset != null) {
                        igcRepositoryHelper.upsertOMRSStubForAsset(relationshipLevelAsset);
                    }
                } catch (IGCException e) {
                    raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }
            }
        }
    }

    /**
     * Send an event out on OMRS topic for an updated relationship (only ever used for self-referencing / generated
     * entities, as all others will send a replaced relationship).
     *
     * @param relationship the updated relationship to publish
     * @param stub the OMRS stub for the asset, containing the last version for which we successfully sent an event
     */
    private void sendUpdatedRelationship(Relationship relationship, OMRSStub stub) {
        if (relationship != null) {
            Relationship old = relationship;
            // If we can, set the old version of the relationship based on the last-shared payload
            Reference igcStubObject = getIgcAssetFromStubPayload(stub);
            if (igcStubObject != null) {
                old.setVersion(igcStubObject.getModifiedOn().getTime());
            }
            repositoryEventProcessor.processUpdatedRelationshipEvent(
                    sourceName,
                    metadataCollectionId,
                    originatorServerName,
                    originatorServerType,
                    null,
                    old,
                    relationship
            );
            // Note that we will never upsert an OMRS stub here as this is only used for self-referencing relationships
        }
    }

    /**
     * Send out the events needed for a relationship that has been replaced: basically a DeletePurge followed by a
     * New.
     *
     * @param relationshipMapping the mapping to use for translating this relationship
     * @param relationship the new relationship
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param proxyOne the IGC asset used as proxyOne of the new relationship
     * @param proxyTwo the IGC asset used as proxyTwo of the new relationship
     * @param relationshipLevelRid the RID of the IGC asset that exists at relationship-level, or null if none
     * @param referenceListProperties the list of IGC property names that contain reference lists
     * @param change the change that indicated this relationship replacement
     */
    private void sendReplacedRelationship(RelationshipMapping relationshipMapping,
                                          Relationship relationship,
                                          ObjectCache cache,
                                          Reference proxyOne,
                                          Reference proxyTwo,
                                          String relationshipLevelRid,
                                          List<String> referenceListProperties,
                                          ChangeSet.Change change) {

        final String methodName = "sendReplacedRelationship";
        String newRelationshipGUID = relationship.getGUID();

        String igcPropertyName = change.getIgcPropertyName();
        Object ora = null;
        try {
            ora = change.getOldValue(referenceListProperties);
        } catch (IGCException e) {
            raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }
        if (ora != null) {
            Reference oldRelatedAsset = (Reference) ora;
            if (oldRelatedAsset.getId() != null) {
                try {
                    Reference newRelatedAsset = (Reference) change.getNewValue(referenceListProperties);
                    log.debug("Processing relationship replacement for: {}", oldRelatedAsset);
                    String newRelatedAssetRID = newRelatedAsset.getId();
                    RelationshipDef relationshipDef = (RelationshipDef) igcomrsMetadataCollection.getTypeDefByName(localServerUserId,
                            relationshipMapping.getOmrsRelationshipType());
                    // Determine which end of the relationship is which (proxyOne vs proxyTwo), and
                    // retrieve the old related asset from the stub itself
                    Reference oldProxyOne = null;
                    Reference oldProxyTwo = null;
                    if (newRelatedAssetRID.equals(proxyOne.getId())) {
                        oldProxyOne = oldRelatedAsset;
                        oldProxyTwo = proxyTwo;
                    } else if (newRelatedAssetRID.equals(proxyTwo.getId())) {
                        oldProxyOne = proxyOne;
                        oldProxyTwo = oldRelatedAsset;
                    }
                    if (oldProxyOne != null && oldProxyTwo != null) {
                        // Re-construct the old relationship GUID from this replaced RID
                        IGCRelationshipGuid oldRelationshipGUID = RelationshipMapping.getRelationshipGUID(
                                igcRepositoryHelper,
                                relationshipMapping,
                                oldProxyOne,
                                oldProxyTwo,
                                igcPropertyName,
                                null,
                                true
                        );
                        log.debug(" ... calculated old relationship GUID: {}", oldRelationshipGUID);
                        sendPurgedRelationship(
                                relationshipMapping,
                                relationshipDef,
                                cache,
                                oldRelationshipGUID,
                                igcPropertyName,
                                oldProxyOne,
                                oldProxyTwo
                        );
                    } else {
                        log.warn("Unable to find previous version for relationship replacement -- sending only new: {}", newRelationshipGUID);
                    }
                } catch (IGCException e) {
                    raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                } catch (InvalidParameterException | RepositoryErrorException | TypeDefNotKnownException e) {
                    log.error("Unable to find relationship type definition '{}' / not supported for guid: {}", relationshipMapping.getOmrsRelationshipType(), newRelationshipGUID, e);
                }
            } else {
                log.warn("Unable to find any previous version for the relationship replacement (no ID) -- sending only new: {}", newRelationshipGUID);
            }
        } else {
            log.warn("Unable to find any previous version for the relationship replacement (null) -- sending only new: {}", newRelationshipGUID);
        }
        sendNewRelationship(relationship, relationshipLevelRid);

    }

    /**
     * Send an event out on OMRS topic for a purged relationship.
     *
     * @param relationshipMapping the relationship mapping to use to determine what to delete and purge
     * @param relationshipDef the OMRS relationship definition
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param relationshipGUID the IGC GUID of the relationship to be deleted and purged
     * @param igcPropertyName the name of the IGC property holding the relationship
     * @param proxyOne IGC asset for end one of the relationship
     * @param proxyTwo IGC asset for end two of the relationship
     */
    private void sendPurgedRelationship(RelationshipMapping relationshipMapping,
                                        RelationshipDef relationshipDef,
                                        ObjectCache cache,
                                        IGCRelationshipGuid relationshipGUID,
                                        String igcPropertyName,
                                        Reference proxyOne,
                                        Reference proxyTwo) {

        final String methodName = "sendPurgedRelationship";
        // Determine if there is a relationship-level asset (RID)
        IGCEntityGuid proxyOneGuid = RelationshipMapping.getProxyOneGuidFromRelationship(
                igcRepositoryHelper,
                relationshipGUID);
        String relationshipLevelRid = null;
        if (relationshipGUID.isRelationshipLevelObject()) {
            relationshipLevelRid = proxyOneGuid.getRid();
        }

        // Assuming proxies have been provided, we can proceed...
        if (proxyOne != null && proxyTwo != null) {
            try {
                // Retrieve OMRS Stubs for the provided proxies, to ensure we will have sufficient details
                // to include as actual EntityProxy instances on the relationship
                OMRSStub stubOne = igcRepositoryHelper.getOMRSStubForAsset(proxyOne);
                OMRSStub stubTwo = igcRepositoryHelper.getOMRSStubForAsset(proxyTwo);
                Relationship relationship = RelationshipMapping.getMappedRelationship(
                        igcomrsRepositoryConnector,
                        relationshipMapping,
                        relationshipDef,
                        cache,
                        getIgcAssetFromStubPayload(stubOne),
                        getIgcAssetFromStubPayload(stubTwo),
                        igcPropertyName,
                        localServerUserId,
                        relationshipLevelRid,
                        true
                );
                repositoryEventProcessor.processDeletePurgedRelationshipEvent(
                        sourceName,
                        metadataCollectionId,
                        originatorServerName,
                        originatorServerType,
                        null,
                        relationship
                );
            } catch (RepositoryErrorException | IGCException e) {
                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        } else {
            log.warn("Unable to produce DeletePurgedRelationshipEvent for relationship: {}", relationshipGUID);
        }

    }

    /**
     * Send an event out on OMRS topic for a new entity.
     *
     * @param asset the IGC asset for which we should send a new entity event
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void sendNewEntity(Reference asset, ObjectCache cache) {

        final String methodName = "sendNewEntity";
        boolean atLeastOneEvent = false;

        // Output an entity for all entities that map to this asset type -- generated and non-generated
        List<EntityMapping> referenceableMappers = igcRepositoryHelper.getMappers(asset.getType(), localServerUserId);
        for (EntityMapping referenceableMapper : referenceableMappers) {
            String ridPrefix = referenceableMapper.getIgcRidPrefix();
            IGCEntityGuid igcEntityGuid = igcRepositoryHelper.getEntityGuid(asset.getType(), ridPrefix, asset.getId());
            EntityDetail detail = getEntityDetailForAssetWithGUID(asset, igcEntityGuid, cache);
            if (detail != null) {
                atLeastOneEvent = true;
                repositoryEventProcessor.processNewEntityEvent(
                        sourceName,
                        metadataCollectionId,
                        originatorServerName,
                        originatorServerType,
                        null,
                        detail
                );
                // TODO: for now this sends the same set of classifications every time, known design issue with how
                //  classifications are currently handled (to be changed once classifications are reworked)
                List<Classification> classifications = detail.getClassifications();
                if (classifications != null) {
                    for (Classification classification : classifications) {
                        sendNewClassification(detail, classification);
                    }
                }
            } else {
                log.warn("Unable to retrieve new entity for asset type {} with prefix {} and RID: {}", asset.getType(), ridPrefix, asset.getId());
            }
        }

        // Finally, update the stub with the latest version of the asset
        // (if any of the above fail, this will also be missed, so we will simply have more updates on the next event)
        if (atLeastOneEvent) {
            try {
                igcRepositoryHelper.upsertOMRSStubForAsset(asset);
            } catch (IGCException e) {
                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        }

    }

    /**
     * Send an event out on OMRS topic for an updated entity.
     *
     * @param latestVersion the IGC asset for which we should send an updated entity event, in its current state
     * @param stub the OMRS stub for the asset, containing the last version for which we successfully sent an event
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void sendUpdatedEntity(Reference latestVersion, OMRSStub stub, ObjectCache cache) {

        final String methodName = "sendUpdatedEntity";
        boolean atLeastOneEvent = false;

        // See if there are any generated entities to send an event for (ie. *Type)
        List<EntityMapping> referenceableMappers = igcRepositoryHelper.getMappers(latestVersion.getType(), localServerUserId);
        for (EntityMapping referenceableMapper : referenceableMappers) {
            // Generated entities MUST have a prefix, so if there is no prefix ignore that mapper
            String ridPrefix = referenceableMapper.getIgcRidPrefix();
            IGCEntityGuid igcEntityGuid = igcRepositoryHelper.getEntityGuid(latestVersion.getType(), ridPrefix, latestVersion.getId());
            EntityDetail detail = getEntityDetailForAssetWithGUID(latestVersion, igcEntityGuid, cache);
            if (detail != null) {
                atLeastOneEvent = true;
                EntityDetail last = getEntityDetailForStubWithGUID(stub, igcEntityGuid, cache);
                repositoryEventProcessor.processUpdatedEntityEvent(
                        sourceName,
                        metadataCollectionId,
                        originatorServerName,
                        originatorServerType,
                        null,
                        last,
                        detail
                );
                processClassifications(detail, detail.getClassifications(), last == null ? new ArrayList<>() : last.getClassifications());
            } else {
                log.warn("Unable to generate updated entity for asset type {} with prefix {} and RID: {}", latestVersion.getType(), ridPrefix, latestVersion.getId());
            }
        }

        // Finally, update the stub with the latest version of the asset
        // (if any of the above fail, this will also be missed, so we will simply have more updates on the next event)
        if (atLeastOneEvent) {
            try {
                igcRepositoryHelper.upsertOMRSStubForAsset(latestVersion);
            } catch (IGCException e) {
                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        }

    }

    /**
     * Determine differences in classifications in order to send appropriate new, changed or remove events.
     *
     * @param detail the latest detail of the entity
     * @param latestClassifications the latest set of classifications for the entity
     * @param lastClassifications the set of classifications for the entity from its previous version
     */
    private void processClassifications(EntityDetail detail,
                                        List<Classification> latestClassifications,
                                        List<Classification> lastClassifications) {

        Map<String, Classification> latestClassificationByGUID = getClassificationMapFromList(latestClassifications);
        Map<String, Classification> lastClassificationByGUID = getClassificationMapFromList(lastClassifications);

        // First iterate through to see if there are any new classifications, keeping track of each classification
        // of the same type between the two lists
        ArrayList<String> matchingGUIDs = new ArrayList<>();
        for (String guid : latestClassificationByGUID.keySet()) {
            if (!lastClassificationByGUID.containsKey(guid)) {
                sendNewClassification(detail, latestClassificationByGUID.get(guid));
            } else {
                matchingGUIDs.add(guid);
            }
        }
        // Then iterate through the last version, to find any removed classifications
        for (String guid : lastClassificationByGUID.keySet()) {
            if (!latestClassificationByGUID.containsKey(guid)) {
                sendRemovedClassification(detail, lastClassificationByGUID.get(guid));
            }
        }
        // Finally, iterate through the classifications that are the same type and see if there are any changes
        for (String matchingGUID : matchingGUIDs) {
            Classification latest = latestClassificationByGUID.get(matchingGUID);
            Classification last = lastClassificationByGUID.get(matchingGUID);
            if (!latest.equals(last)) {
                sendChangedClassification(detail, last, latest);
            }
        }

    }

    /**
     * Translate a list of classifications in to a Map keyed by the classification type's GUID.
     * (Note that this assumes a given classification type will only have one instance in the list!)
     *
     * @param classifications the list of classifications to trasnform
     * @return {@code Map<String, Classification>}
     */
    private Map<String, Classification> getClassificationMapFromList(List<Classification> classifications) {
        HashMap<String, Classification> map = new HashMap<>();
        if (classifications != null) {
            for (Classification classification : classifications) {
                String typeGUID = classification.getType().getTypeDefGUID();
                if (map.containsKey(typeGUID)) {
                    log.warn("Found multiple classifications of type {} -- clobbering!", typeGUID);
                }
                map.put(typeGUID, classification);
            }
        }
        return map;
    }

    /**
     * Send an event for a new classification being added to the entity's detail.
     *
     * @param detail the entity for which to send a new classification event
     * @param classification the new classification
     */
    private void sendNewClassification(EntityDetail detail, Classification classification) {
        repositoryEventProcessor.processClassifiedEntityEvent(
                sourceName,
                metadataCollectionId,
                originatorServerName,
                originatorServerType,
                null,
                detail,
                classification
        );
    }

    /**
     * Send an event for an existing classification being changed on an entity's detail.
     *
     * @param detail the entity for which to send a changed classification event
     * @param original classification
     * @param updated classification
     */
    private void sendChangedClassification(EntityDetail detail,
                                           Classification original,
                                           Classification updated) {
        repositoryEventProcessor.processReclassifiedEntityEvent(
                sourceName,
                metadataCollectionId,
                originatorServerName,
                originatorServerType,
                null,
                detail,
                original,
                updated
        );
    }

    /**
     * Send an event for a classification having been removed from an entity's detail.
     *
     * @param detail the entity for which to send a removed classification event
     * @param classification that was removed
     */
    private void sendRemovedClassification(EntityDetail detail, Classification classification) {
        repositoryEventProcessor.processDeclassifiedEntityEvent(
                sourceName,
                metadataCollectionId,
                originatorServerName,
                originatorServerType,
                null,
                detail,
                classification
        );
    }

    private void sendPurgedEntity(String igcAssetType, String rid, ObjectCache cache) {
        sendPurgedEntity(igcAssetType, rid, new HashSet<>(), cache);
    }

    /**
     * Send an event out on OMRS topic for a purged entity.
     *
     * @param igcAssetType the IGC asset type (ie. translated from the ASSET_TYPE from the event)
     * @param rid the IGC Repository ID (RID) of the asset
     * @param alreadyPurgedRids set of RIDs that have already been purged
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void sendPurgedEntity(String igcAssetType, String rid, Set<String> alreadyPurgedRids, ObjectCache cache) {

        final String methodName = "sendPurgedEntity";

        if (alreadyPurgedRids.contains(rid)) {
            log.debug("Received RID has already been purged -- skipping: {}", rid);
        } else {
            log.debug("Purging entity of type '{}' with RID: {}", igcAssetType, rid);

            OMRSStub stub = null;
            try {
                stub = igcRepositoryHelper.getOMRSStubForAsset(rid, igcAssetType);
            } catch (IGCException e) {
                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
            // If there is no stub, there should not be any information that was sent previously in an event for us
            // to need to purge anything, so we should be able to skip the rest and continue on our way
            if (stub != null) {

                Reference fromObject = getIgcAssetFromStubPayload(stub);

                // Purge entities by getting all mappers used for that entity (ie. *Type generated entities
                // as well as non-generated entities)
                List<EntityMapping> referenceableMappers = igcRepositoryHelper.getMappers(igcAssetType, localServerUserId);
                for (EntityMapping referenceableMapper : referenceableMappers) {

                    log.debug("Checking via: {}", referenceableMapper.getClass().getName());
                    boolean isOmrsType = false;
                    try {
                        isOmrsType = referenceableMapper.isOmrsType(igcRestClient, cache, fromObject);
                    } catch (RepositoryErrorException e) {
                        raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                    }
                    if (isOmrsType) {

                        List<PurgeMarker> purgeMarkers = new ArrayList<>();
                        String ridPrefix = referenceableMapper.getIgcRidPrefix();
                        IGCEntityGuid igcEntityGuid = igcRepositoryHelper.getEntityGuid(igcAssetType, ridPrefix, rid);
                        // First purge any relationships that exist against this entity
                        List<RelationshipMapping> relationshipMappers = referenceableMapper.getRelationshipMappers();
                        for (RelationshipMapping relationshipMapping : relationshipMappers) {

                            log.debug("Checking for relationships via: {}", relationshipMapping.getClass().getName());
                            RelationshipMapping.ProxyMapping pmOne = relationshipMapping.getProxyOneMapping();
                            RelationshipMapping.ProxyMapping pmTwo = relationshipMapping.getProxyTwoMapping();
                            try {
                                RelationshipDef relationshipDef = (RelationshipDef) igcomrsMetadataCollection.getTypeDefByName(
                                        localServerUserId,
                                        relationshipMapping.getOmrsRelationshipType()
                                );
                                RelationshipMapping.ContainedType childEnd = relationshipMapping.getContainedType();
                                // TODO: not quite as simple as this just checking types, as the IGC type could match both ends...
                                if ((childEnd.equals(RelationshipMapping.ContainedType.ONE) && pmTwo.matchesAssetType(igcAssetType))
                                        || (childEnd.equals(RelationshipMapping.ContainedType.TWO) && pmOne.matchesAssetType(igcAssetType))) {
                                    // If the child entities are at one end of the relationship, and we are starting from the
                                    // other, then recursively purge
                                    log.debug(" ... containment detected for type '{}' and relationship {}", igcAssetType, relationshipMapping.getClass().getName());
                                    // Need to ensure all relationships are purged BEFORE recursing on entities, otherwise
                                    // for recursed entities we will inevitably need to try to delete a parent-child relationship
                                    // where one end (eg. the child) has already been purged and therefore a stub for it cannot be
                                    // retrieved
                                    purgeMarkers.add(new PurgeMarker(fromObject, relationshipDef, relationshipMapping));
                                }

                                // Irrespective of containment and the potential need to recurse, remove the relationship
                                List<String> propertyNames = null;
                                List<Reference> endOne = new ArrayList<>();
                                List<Reference> endTwo = new ArrayList<>();
                                boolean iterateOnOne = false;
                                if (pmOne.matchesAssetType(igcAssetType)) {
                                    log.debug(" ... setting 'from' to end1: {}", igcAssetType);
                                    propertyNames = pmOne.getIgcRelationshipProperties();
                                    endOne.addAll(relationshipMapping.getProxyOneAssetFromAsset(fromObject, igcRestClient, cache));
                                    iterateOnOne = true;
                                } else if (pmTwo.matchesAssetType(igcAssetType)) {
                                    log.debug(" ... setting 'from' to end2: {}", igcAssetType);
                                    propertyNames = pmTwo.getIgcRelationshipProperties();
                                    endTwo.addAll(relationshipMapping.getProxyTwoAssetFromAsset(fromObject, igcRestClient, cache));
                                    iterateOnOne = false;
                                } else if (!relationshipMapping.isSelfReferencing()) {
                                    log.warn("Unable to match the purged entity '{}' to either end of relationship: {}", igcAssetType, relationshipDef.getName());
                                }
                                if (propertyNames != null) {
                                    for (String property : propertyNames) {
                                        if (!property.equals(RelationshipMapping.SELF_REFERENCE_SENTINEL)) {
                                            log.debug(" ... checking for relationship on property: {}", property);
                                            try {
                                                Object relatedResult = igcRestClient.getPropertyByName(fromObject, property);
                                                if (relatedResult != null) {
                                                    // TODO: we should also cache up all of the relationship ends that are NOT purged,
                                                    //  as these entities should have their stubs updated (to no longer refer to a
                                                    //  non-existent relationship) -- in fact, that might take care of sending the
                                                    //  correct relationship purges for us?
                                                    if (relatedResult instanceof Reference) {
                                                        Reference relationship = (Reference) relatedResult;
                                                        if (relationship.getType() != null) {
                                                            // In cases of an exclusive relationship, there could be an empty
                                                            // object rather than null, but this semantically still means there
                                                            // is no relationship so treat it as a null relationship (skip it)
                                                            cascadeRelationshipPurge(
                                                                    relationshipMapping,
                                                                    relationshipDef,
                                                                    cache,
                                                                    endOne,
                                                                    endTwo,
                                                                    relationship,
                                                                    property,
                                                                    iterateOnOne
                                                            );
                                                        }
                                                    } else if (relatedResult instanceof ItemList) {
                                                        ItemList<?> relationships = (ItemList<?>) relatedResult;
                                                        for (Reference relationship : relationships.getItems()) {
                                                            cascadeRelationshipPurge(
                                                                    relationshipMapping,
                                                                    relationshipDef,
                                                                    cache,
                                                                    endOne,
                                                                    endTwo,
                                                                    relationship,
                                                                    property,
                                                                    iterateOnOne
                                                            );
                                                        }
                                                    }
                                                }
                                            } catch (IGCException e) {
                                                raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                                            }
                                        } else {
                                            // TODO: probably also need to purge any generated entity and relationship?
                                            log.warn(" ... should also be purging a generated relationship.");
                                        }
                                    }
                                }
                            } catch (RepositoryErrorException | TypeDefNotKnownException | InvalidParameterException e) {
                                log.error("Unable to retrieve the relationship type definition for '{}' -- cannot purge relationship.", relationshipMapping.getOmrsRelationshipType(), e);
                            }

                        }

                        // Recurse on any purges we've marked
                        for (PurgeMarker purgeMarker : purgeMarkers) {
                            recurseOnContainedEntities(purgeMarker, alreadyPurgedRids, cache);
                        }

                        // Then remove the entity itself
                        EntityDetail detail = getEntityDetailForStubWithGUID(stub, igcEntityGuid, cache);
                        if (detail != null) {
                            log.debug(" ... purging entity: {}", igcEntityGuid);
                            repositoryEventProcessor.processDeletePurgedEntityEvent(
                                    sourceName,
                                    metadataCollectionId,
                                    originatorServerName,
                                    originatorServerType,
                                    null,
                                    detail
                            );
                            // and mark it as purged, so that we don't attempt to purge again during the recursion
                            alreadyPurgedRids.add(igcEntityGuid.getRid());
                        } else {
                            log.warn("No stub information exists for purged GUID {} -- cannot generated purgeEntity event.", igcEntityGuid);
                        }

                    } else {
                        log.info("Type ({}) did not match mapper, skipped: {}", igcAssetType, referenceableMapper.getClass().getName());
                    }
                }

                // Finally, remove the stub (so that if such an asset is created in the future it is recognised as new
                // rather than an update)
                log.debug("Deleting stub: {}", rid);
                try {
                    igcRepositoryHelper.deleteOMRSStubForAsset(rid, igcAssetType);
                } catch (IGCException e) {
                    raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }
            } else {
                log.info("No stub information exists for RID {} of type {} -- cannot generated purgeEntity event.", rid, igcAssetType);
            }
        }

    }

    /**
     * Recursively purge any entities that are contained within the marker pointing to what was purged.
     *
     * @param marker the marker indicating what was purged
     * @param alreadyPurgedRids a set of RIDs for entities that have already been purged
     * @param cache a cache of information that may already have been retrieved about the provided object
     */
    private void recurseOnContainedEntities(PurgeMarker marker, Set<String> alreadyPurgedRids, ObjectCache cache) {

        final String methodName = "recurseOnContainedEntities";

        RelationshipMapping relationshipMapping = marker.getMapping();
        Reference parentObject = marker.getTriggerObject();
        RelationshipMapping.ContainedType childEnd = relationshipMapping.getContainedType();
        RelationshipMapping.ProxyMapping parent;

        log.debug("Recursing on mapping: {}", relationshipMapping.getClass().getName());

        String parentRid = parentObject.getId();

        if (childEnd.equals(RelationshipMapping.ContainedType.TWO)) {
            log.debug(" ... setting parent as 1, child as 2");
            parent = relationshipMapping.getProxyOneMapping();
        } else {
            log.debug(" ... setting child as 1, parent as 2");
            parent = relationshipMapping.getProxyTwoMapping();
        }
        List<String> relationshipProperties = parent.getIgcRelationshipProperties();
        log.debug(" ... iterating through parent's properties: {}", relationshipProperties);
        for (String property : relationshipProperties) {
            if (!property.equals(RelationshipMapping.SELF_REFERENCE_SENTINEL)) {
                log.debug(" ... getting child entities from property: {}", property);
                try {
                    Object relatedResult = igcRestClient.getPropertyByName(parentObject, property);
                    if (relatedResult != null) {
                        if (relatedResult instanceof Reference) {
                            Reference relationship = (Reference) relatedResult;
                            if (!relationship.getId().equals(parentRid)) {
                                log.debug(" ... purging child entity: {}", relationship.getId());
                                sendPurgedEntity(relationship.getType(), relationship.getId(), alreadyPurgedRids, cache);
                            }
                        } else if (relatedResult instanceof ItemList) {
                            ItemList<?> relationships = (ItemList<?>) relatedResult;
                            for (Reference relationship : relationships.getItems()) {
                                if (!relationship.getId().equals(parentRid)) {
                                    log.debug(" ... purging child entity: {}", relationship.getId());
                                    sendPurgedEntity(relationship.getType(), relationship.getId(), alreadyPurgedRids, cache);
                                }
                            }
                        }
                    }
                } catch (IGCException e) {
                    raiseIGCRuntimeException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }
            }
        }

    }

    private void cascadeRelationshipPurge(RelationshipMapping relationshipMapping,
                                          RelationshipDef relationshipDef,
                                          ObjectCache cache,
                                          List<Reference> endOne,
                                          List<Reference> endTwo,
                                          Reference relatedObject,
                                          String propertyName,
                                          boolean iterateOnOne) {

        if (iterateOnOne) {
            for (Reference one : endOne) {
                IGCRelationshipGuid relGuid = RelationshipMapping.getRelationshipGUID(
                        igcRepositoryHelper,
                        relationshipMapping,
                        one,
                        relatedObject,
                        propertyName,
                        null,
                        true
                );
                // Purge the relationship
                log.debug(" ... purging relationship for purged entity: {}", relGuid);
                sendPurgedRelationship(
                        relationshipMapping,
                        relationshipDef,
                        cache,
                        relGuid,
                        propertyName,
                        one,
                        relatedObject
                );
            }
        } else {
            for (Reference two : endTwo) {
                IGCRelationshipGuid relGuid = RelationshipMapping.getRelationshipGUID(
                        igcRepositoryHelper,
                        relationshipMapping,
                        relatedObject,
                        two,
                        propertyName,
                        null,
                        true
                );
                // Purge the relationship
                log.debug(" ... purging relationship for purged entity: {}", relGuid);
                sendPurgedRelationship(
                        relationshipMapping,
                        relationshipDef,
                        cache,
                        relGuid,
                        propertyName,
                        relatedObject,
                        two
                );
            }
        }

    }

    /**
     * Method to process events from v11.7 of Information Server.
     *
     * @param event inbound event
     */
    private void processEventV117(String event, ObjectCache cache) {
        // TODO: implement processEventV117
        log.debug("Not yet implemented as v11.7-specific -- backing to v11.5 processing: {}", event);
        processEventV115(event, cache);
    }

    /**
     * Free up any resources held since the connector is no longer needed.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    @Override
    public void disconnect() throws ConnectorCheckedException {
        super.disconnect();
        final String methodName = "disconnect";
        if (igcKafkaConsumer != null) {
            igcKafkaConsumer.stop();
        }
        auditLog.logMessage(methodName, IGCOMRSAuditCode.EVENT_MAPPER_SHUTDOWN.getMessageDefinition(igcomrsRepositoryConnector.getServerName()));
    }

    /**
     * Sends a new TypeDef event.
     *
     * @param newTypeDef the new TypeDef for which to send an event
     */
    public void sendNewTypeDefEvent(TypeDef newTypeDef) {
        repositoryEventProcessor.processNewTypeDefEvent(
                sourceName,
                metadataCollectionId,
                localServerName,
                localServerType,
                localOrganizationName,
                newTypeDef
        );
    }

    /**
     * Sends a new AttributeTypeDef event.
     *
     * @param newAttributeTypeDef the new AttributeTypeDef for which to send an event
     */
    public void sendNewAttributeTypeDefEvent(AttributeTypeDef newAttributeTypeDef) {
        repositoryEventProcessor.processNewAttributeTypeDefEvent(
                sourceName,
                metadataCollectionId,
                localServerName,
                localServerType,
                localOrganizationName,
                newAttributeTypeDef);
    }

    /**
     * Sends an updated TypeDef event.
     *
     * @param typeDefPatch the patch that was applied to achieve the update
     */
    public void sendUpdatedTypeDefEvent(TypeDefPatch typeDefPatch) {
        repositoryEventProcessor.processUpdatedTypeDefEvent(
                sourceName,
                metadataCollectionId,
                localServerName,
                localServerType,
                localOrganizationName,
                typeDefPatch);
    }

    /**
     * Sends a refresh entity request event.
     *
     * @param typeDefGUID unique identifier of requested entity's TypeDef
     * @param typeDefName unique name of requested entity's TypeDef
     * @param entityGUID unique identifier of requested entity
     * @param homeMetadataCollectionId identifier of the metadata collection that is the home to this entity
     */
    public void sendRefreshEntityRequest(String typeDefGUID,
                                         String typeDefName,
                                         String entityGUID,
                                         String homeMetadataCollectionId) {
        repositoryEventProcessor.processRefreshEntityRequested(
                sourceName,
                metadataCollectionId,
                localServerName,
                localServerType,
                localOrganizationName,
                typeDefGUID,
                typeDefName,
                entityGUID,
                homeMetadataCollectionId);
    }

    /**
     * Sends a refresh relationship request event.
     *
     * @param typeDefGUID the guid of the TypeDef for the relationship used to verify the relationship identity
     * @param typeDefName the name of the TypeDef for the relationship used to verify the relationship identity
     * @param relationshipGUID unique identifier of the relationship
     * @param homeMetadataCollectionId unique identifier for the home repository for this relationship
     */
    public void sendRefreshRelationshipRequest(String typeDefGUID,
                                               String typeDefName,
                                               String relationshipGUID,
                                               String homeMetadataCollectionId) {
        repositoryEventProcessor.processRefreshRelationshipRequest(
                sourceName,
                metadataCollectionId,
                localServerName,
                localServerType,
                localOrganizationName,
                typeDefGUID,
                typeDefName,
                relationshipGUID,
                homeMetadataCollectionId);
    }

    /**
     * Throws a ConnectorCheckedException based on the provided parameters.
     *
     * @param errorCode the error code for the exception
     * @param methodName the method name throwing the exception
     * @param cause the underlying cause of the exception (if any, otherwise null)
     * @param params any additional parameters for formatting the error message
     * @throws ConnectorCheckedException always
     */
    private void raiseConnectorCheckedException(IGCOMRSErrorCode errorCode, String methodName, Exception cause, String ...params) throws ConnectorCheckedException {
        if (cause == null) {
            throw new ConnectorCheckedException(errorCode.getMessageDefinition(params),
                    this.getClass().getName(),
                    methodName);
        } else {
            throw new ConnectorCheckedException(errorCode.getMessageDefinition(params),
                    this.getClass().getName(),
                    methodName,
                    cause);
        }
    }

    /**
     * Throws an IGCOMRSRuntimeException based on the provided parameters.
     *
     * @param errorCode the error code for the exception
     * @param methodName the method name throwing the exception
     * @param cause the underlying cause of the exception (if any, otherwise null)
     * @param params any additional parameters for formatting the error message
     * @throws IGCOMRSRuntimeException always
     */
    private void raiseIGCRuntimeException(IGCOMRSErrorCode errorCode, String methodName, Exception cause, String ...params) throws IGCOMRSRuntimeException {
        throw new IGCOMRSRuntimeException(errorCode.getMessageDefinition(params),
                this.getClass().getName(),
                methodName,
                cause);
    }

}
