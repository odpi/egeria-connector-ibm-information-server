/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.eventmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.model.*;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCRelationshipGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.openmetadata.frameworks.connectors.Connector;
import org.odpi.openmetadata.frameworks.connectors.VirtualConnectorExtension;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditCode;
import org.odpi.openmetadata.repositoryservices.connectors.openmetadatatopic.OpenMetadataTopicConnector;
import org.odpi.openmetadata.repositoryservices.connectors.openmetadatatopic.OpenMetadataTopicListener;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EntityDetail;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryeventmapper.OMRSRepositoryEventMapperBase;
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
        implements VirtualConnectorExtension, OpenMetadataTopicListener {

    private List<Connector> embeddedConnectors = null;
    private List<OpenMetadataTopicConnector> eventBusConnectors = new ArrayList<>();

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
     * Pass additional information to the connector needed to process events.
     *
     * @param repositoryEventMapperName repository event mapper name used for the source of the OMRS events.
     * @param repositoryConnector ths is the connector to the local repository that the event mapper is processing
     *                            events from.  The repository connector is used to retrieve additional information
     *                            necessary to fill out the OMRS Events.
     */
    @Override
    public void initialize(String                  repositoryEventMapperName,
                           OMRSRepositoryConnector repositoryConnector) {

        super.initialize(repositoryEventMapperName, repositoryConnector);
        log.info("IGC Event Mapper initializing...");

        // Setup IGC OMRS Repository connectivity
        this.igcomrsRepositoryConnector = (IGCOMRSRepositoryConnector) this.repositoryConnector;
        this.igcVersion = igcomrsRepositoryConnector.getIGCVersion();
        this.igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // TODO: Pick the best topic available based on the version of IGC (for now always default to InfosphereEvents)
        this.igcKafkaTopic = "InfosphereEvents";

        // Retrieve connection details to configure Kafka connectivity
        String igcKafkaBootstrap = this.connectionBean.getEndpoint().getAddress();
        igcKafkaProperties = new Properties();
        igcKafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, igcKafkaBootstrap);
        igcKafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "IGCOMRSRepositoryEventMapper_consumer");
        igcKafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        igcKafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Setup ObjectMapper for (de-)serialisation of events
        this.mapper = new ObjectMapper();

    }


    /**
     * Indicates that the connector is completely configured and can begin processing.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    @Override
    public void start() throws ConnectorCheckedException {

        super.start();
        log.info("IGC Event Mapper starting...");
        this.igcKafkaConsumer = new IGCKafkaConsumerThread();
        this.igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
        this.igcRepositoryHelper = igcomrsMetadataCollection.getIgcRepositoryHelper();
        this.metadataCollectionId = igcomrsRepositoryConnector.getMetadataCollectionId();
        this.originatorServerName = igcomrsRepositoryConnector.getServerName();
        this.originatorServerType = igcomrsRepositoryConnector.getServerType();
        log.info("Starting consumption from IGC Kafka bus.");
        igcKafkaConsumer.start();

    }


    /**
     * Class to support multi-threaded consumption of IGC Kafka events.
     */
    private class IGCKafkaConsumerThread implements Runnable {

        private Thread worker;
        private final AtomicBoolean running = new AtomicBoolean(false);

        public void start() {
            worker = new Thread(this);
            worker.start();
        }

        public void stop() {
            running.set(false);
        }

        /**
         * Read IGC Infosphere topic Kafka events.
         */
        @Override
        public void run() {

            running.set(true);
            log.info("Starting IGC Event Mapper consumer thread.");
            final Consumer<Long, String> consumer = new KafkaConsumer<>(igcKafkaProperties);
            consumer.subscribe(Collections.singletonList(igcKafkaTopic));

            // TODO: Likely need to tweak these settings to give further processing time for large events
            //  like IMAM shares -- or even switch to manual offset management rather than auto-commits
            //  (see: https://kafka.apache.org/0110/javadoc/org/apache/kafka/clients/consumer/KafkaConsumer.html)
            while (running.get()) {
                try {
                    ConsumerRecords<Long, String> events = consumer.poll(pollDuration);
                    for (ConsumerRecord<Long, String> event : events) {
                        processEvent(event.value());
                    }
                } catch (Exception e) {
                    log.error("Failed trying to consume IGC events from Kafka.", e);
                }
            }
        }

    }


    /**
     * Registers itself as a listener of any OpenMetadataTopicConnectors that are passed as
     * embedded connectors.
     *
     * @param embeddedConnectors  list of connectors
     */
    @Override
    public void initializeEmbeddedConnectors(List<Connector> embeddedConnectors) {
        this.embeddedConnectors = embeddedConnectors;
    }


    /**
     * Method to pass an event received on topic.
     *
     * @param event inbound event
     */
    @Override
    public void processEvent(String event) {
        if (log.isDebugEnabled()) { log.debug("Processing event: {}", event); }
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            processEventV117(event);
        } else {
            processEventV115(event);
        }
    }

    /**
     * Method to process events from v11.5 of Information Server.
     * Basically this method will simply route between processing IMAM events and normal asset events.
     *
     * @param event inbound event
     */
    private void processEventV115(String event) {

        try {
            InfosphereEvents eventObj = this.mapper.readValue(event, InfosphereEvents.class);
            switch(eventObj.getEventType()) {
                case "IMAM_SHARE_EVENT":
                    processIMAMShareEventV115((InfosphereEventsIMAMEvent)eventObj);
                    break;
                case "DC_CREATE_EVENT":
                case "DC_MERGED_EVENT":
                    processDataConnectionEventV115((InfosphereEventsDCEvent)eventObj);
                    break;
                case "IA_COLUMN_CLASSIFIED_EVENT":
                case "IA_COLUMN_ANALYZED_EVENT":
                case "IA_TABLE_RESULTS_PUBLISHED":
                case "IA_COLUMN_FAILED_EVENT":
                    processIAEventV115((InfosphereEventsIAEvent)eventObj);
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
                case "IA_COLUMN_ANALYSIS_STARTED_EVENT":
                case "IA_COLUMN_ANALYSIS_FINISHED_EVENT":
                case "IA_COLUMN_ANALYSIS_FAILED_EVENT":
                case "IA_PROFILE_BATCH_COMPLETED_EVENT":
                case "IA_DATAQUALITY_ANALYSIS_SUBMITTED":
                case "IA_DATAQUALITY_ANALYSIS_STARTED_EVENT":
                case "IA_DATAQUALITY_ANALYSIS_FINISHED_EVENT":
                case "IA_DATAQUALITY_ANALYSIS_FAILED_EVENT":
                case "DISCOVER_IMPORT_COMPLETE":
                    log.info("Found Information Analyzer event that cannot be processed via APIs, skipping.");
                    break;
                case "IGC_ETLGROUP_EVENT":
                    log.info("Found DataStage event that should be processed via data engine proxy, skipping.");
                    break;
                default:
                    processAssetEventV115((InfosphereEventsAssetEvent)eventObj);
                    break;
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to translate event {} into object.", event, e); }
        }

    }

    /**
     * Processes IMAM_SHARE_EVENT events from v11.5 of Information Server.
     *
     * @param event inbound event
     */
    private void processIMAMShareEventV115(InfosphereEventsIMAMEvent event) {

        Map<String, String> createdRIDs = getRIDsAndTypesFromEventString(event.getCreatedRIDs());
        Map<String, String> updatedRIDs = getRIDsAndTypesFromEventString(event.getMergedRIDs());
        Map<String, String> deletedRIDs = getRIDsAndTypesFromEventString(event.getDeletedRIDs());

        // Start by creating any entities needed by the new RIDs
        for (Map.Entry<String, String> entry : createdRIDs.entrySet()) {
            processAsset(entry.getKey(), entry.getValue(), null);
        }

        // Then iterate through any updated entities
        for (Map.Entry<String, String> entry : updatedRIDs.entrySet()) {
            processAsset(entry.getKey(), entry.getValue(), null);
        }

        // Then iterate through any deleted entities
        for (Map.Entry<String, String> entry : deletedRIDs.entrySet()) {
            sendPurgedEntity(entry.getValue(), entry.getKey());
        }

    }

    /**
     * Processes Data Connection events from v11.5 of Information Server.
     * @param event the data connection event to process
     */
    private void processDataConnectionEventV115(InfosphereEventsDCEvent event) {

        String action = event.getEventType();

        switch(action) {
            case InfosphereEventsDCEvent.ACTION_CREATE:
                processAsset(event.getCreatedRID(), "data_connection", null);
                break;
            case InfosphereEventsDCEvent.ACTION_MODIFY:
                processAsset(event.getMergedRID(), "data_connection", null);
                break;
            default:
                if (log.isWarnEnabled()) { log.warn("Found unhandled action type '{}' for data connection on event: {}", action, event); }
                break;
        }

    }

    /**
     * Processes all asset-specific events from v11.5 of Information Server.
     *
     * @param event inbound event
     */
    private void processAssetEventV115(InfosphereEventsAssetEvent event) {

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
                    processAsset(assetRid, igcAssetType, null);
                }
                break;
            case InfosphereEventsAssetEvent.ACTION_ASSIGNED_RELATIONSHIP:
                if (log.isDebugEnabled()) { log.debug("Ignoring ASSIGNED_RELATIONSHIP event -- should be handled already by an earlier CREATE or MODIFY event: {}", event); }
                break;
            default:
                if (log.isWarnEnabled()) { log.warn("Action '{}' is not yet implemented: {}", action, event); }
                break;
        }

    }

    /**
     * Processes all process-able Information Analyzer events from v11.5 of Information Server.
     *
     * @param event inbound event
     */
    private void processIAEventV115(InfosphereEventsIAEvent event) {

        String action = event.getEventType();

        switch(action) {
            case InfosphereEventsIAEvent.COL_ANALYZED:
            case InfosphereEventsIAEvent.COL_CLASSIFIED:
                // TODO: could potentially retrieve more from these via IA REST API...
                if (log.isInfoEnabled()) { log.info("Column / field analyzed or classified, but not yet published -- skipping: {}", action); }
                break;
            case InfosphereEventsIAEvent.TBL_PUBLISHED:
                // This is the only event we can really do something with, as IGC API can only see
                // published information
                String containerRid = event.getDataCollectionRid();
                // We must do this initial retrieval as insufficient detail in event payload to know whether it is a
                // database table or a file record that was published
                Reference containerAsset = igcRestClient.getAssetRefById(containerRid);
                String searchProperty = null;
                String searchAssetType = null;
                switch(containerAsset.getType()) {
                    case "database_table":
                        searchProperty = "database_table_or_view";
                        searchAssetType = "database_column";
                        break;
                    case "data_file_record":
                        searchProperty = "data_file_record";
                        searchAssetType = "data_file_field";
                        break;
                    default:
                        if (log.isWarnEnabled()) { log.warn("Unimplemented asset type '{}' for IA publishing: {}", containerAsset.getType(), event); }
                        break;
                }
                // First process the containing asset itself
                processAsset(containerRid, containerAsset.getType(), null);
                // We should also check the columns / file fields within the table / file for changes to be processed,
                // as the relationship itself between column and table may not change but there may be
                // new classifications on the columns / fields from the publication
                IGCSearchCondition igcSearchCondition = new IGCSearchCondition(searchProperty, "=", containerAsset.getId());
                IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
                IGCSearch igcSearch = new IGCSearch(searchAssetType, new String[]{ searchProperty }, igcSearchConditionSet);
                ReferenceList subAssets = igcRestClient.search(igcSearch);
                if (subAssets != null) {
                    subAssets.getAllPages(igcRestClient);
                    if (log.isDebugEnabled()) { log.debug("Processing {} child assets from IA publication: {}", subAssets.getPaging().getNumTotal(), containerRid); }
                    for (Reference child : subAssets.getItems()) {
                        processAsset(child.getId(), child.getType(), null);
                    }
                } else {
                    if (log.isWarnEnabled()) { log.warn("Unable to find any sub-assets for IA published container '{}': {}", containerRid, event); }
                }
                break;
            default:
                if (log.isInfoEnabled()) { log.info("Action '{}' is not yet implemented for IA: {}", action, event); }
                break;
        }

    }

    /**
     * Attempt to retrieve the EntityDetail object for the provided asset, and handle any errors if unable to do so.
     *
     * @param asset the IGC asset for which to retrieve an EntityDetail object
     * @return EntityDetail
     */
    private EntityDetail getEntityDetailForAsset(Reference asset) {
        IGCEntityGuid igcEntityGuid = igcRepositoryHelper.getEntityGuid(asset.getType(), null, asset.getId());
        return getEntityDetailForAssetWithGUID(asset, igcEntityGuid);
    }

    /**
     * Attempt to retrieve the EntityDetail object for the provided asset, using the provided Repository ID (RID).
     * Useful for when the RID indicates there is some generated entity that does not actually exist on its own in
     * IGC. Will handle any errors if unable to retrieve the asset, and the EntityDetail will simply be null.
     *
     * @param asset the IGC asset for which to retrieve an EntityDetail object
     * @param guid the IGC GUID to use for the asset
     * @return EntityDetail
     */
    private EntityDetail getEntityDetailForAssetWithGUID(Reference asset, IGCEntityGuid guid) {

        EntityDetail detail = null;
        try {
            detail = igcRepositoryHelper.getEntityDetailFromFullAsset(localServerUserId, guid, asset);
        } catch (EntityNotKnownException e) {
            if (log.isErrorEnabled()) { log.error("Unable to find EntityDetail for GUID: {}", guid, e); }
        } catch (RepositoryErrorException e) {
            if (log.isErrorEnabled()) { log.error("Unexpected error in retrieving EntityDetail for GUID: {}", guid, e); }
        }
        return detail;

    }

    /**
     * Attempt to retrieve the EntityDetail object for the provided OMRS stub, and handle any errors if unable
     * to do so.
     *
     * @param stub the OMRS stub for which to retrieve an EntityDetail object
     * @return EntityDetail
     */
    private EntityDetail getEntityDetailForStub(OMRSStub stub) {
        return getEntityDetailForStubWithGUID(stub, null);
    }

    /**
     * Attempt to retrieve the EntityDetail object for the provided OMRS stub, using the provided Repository ID (RID).
     * Useful for when the RID indicates there is some generated entity that does not actually exist on its own in
     * IGC. Will handle any errors if unable to retrieve the asset, and the EntityDetail will simply be null.
     *
     * @param stub the OMRS stub for which to retrieve an EntityDetail object
     * @param guid the IGC GUID to use for the asset
     * @return EntityDetail
     */
    private EntityDetail getEntityDetailForStubWithGUID(OMRSStub stub, IGCEntityGuid guid) {

        EntityDetail detail = null;
        if (log.isDebugEnabled()) { log.debug("Retrieving EntityDetail for stub: {}", stub); }
        Reference asset = getIgcAssetFromStubPayload(stub);
        if (asset != null) {
            // If no RID was provided, take it from the asset we retrieved
            if (guid == null) {
                guid = igcRepositoryHelper.getEntityGuid(asset.getType(), null, asset.getId());
            }
            if (log.isDebugEnabled()) { log.debug(" ... retrieved asset from stub: {}", asset); }
            try {
                detail = igcRepositoryHelper.getEntityDetailFromFullAsset(localServerUserId, guid, asset);
            } catch (EntityNotKnownException e) {
                if (log.isErrorEnabled()) { log.error("Unable to find EntityDetail for stub with GUID: {}", guid, e); }
            } catch (RepositoryErrorException e) {
                if (log.isErrorEnabled()) { log.error("Unexpected error in retrieving EntityDetail for stub: {}", stub.getId()); }
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
        Reference asset = null;
        if (stub != null) {
            if (log.isDebugEnabled()) { log.debug("Retrieving IGC Reference for stub payload: {}", stub.getPayload()); }
            asset = igcomrsRepositoryConnector.getIGCRestClient().readJSONIntoPOJO(stub.getPayload());
            asset.setFullyRetrieved();
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
        if (payload != null && !payload.equals("")) {
            for (String asset : payload.split(",")) {
                asset = asset.trim();
                String[] assetTokens = asset.split(":");
                if (assetTokens.length == 2) {
                    String type = assetTokens[0];
                    String rid = assetTokens[1];
                    dict.put(rid, IGCRestConstants.getImamTypeToIgcType().getOrDefault(type, null));
                } else {
                    if (log.isWarnEnabled()) { log.warn("Unexpected number of tokens ({}) from event payload: {}", assetTokens.length, payload); }
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
     * @param rid the Repository ID (RID) of the asset in question
     * @param assetType the type of asset (ie. if provided in the event payload)
     * @param relationshipGUID the relationship GUID that triggered this asset to be processed (or null if not triggered
     *                         by relationship being processed)
     */
    private void processAsset(String rid, String assetType, IGCRelationshipGuid relationshipGUID) {
        processAsset(rid, assetType, relationshipGUID, null);
    }

    /**
     * Processes the provided asset according to what we determine about its status (eg. deleted, new, or updated).
     * Will also call into processRelationship as-needed if a relationship is detected as changed.
     *
     * If 'limitToPrefix' is specified, this will only process events for generated IGC assets whose prefix matches
     * the provided value.
     *
     * @param rid the Repository ID (RID) of the asset in question
     * @param assetType the type of asset (ie. if provided in the event payload)
     * @param relationshipGUID the relationship GUID that triggered this asset to be processed (or null if not triggered
     *                         by relationship being processed)
     * @param limitToPrefix if specified, limit the relationships to only those where the asset is prefixed by this prefix
     */
    private void processAsset(String rid, String assetType, IGCRelationshipGuid relationshipGUID, String limitToPrefix) {

        // TODO: make use of 'limitToPrefix' to limit processing to only generated entities

        if (log.isDebugEnabled()) { log.debug("processAsset called with rid {} and type {}", rid, assetType); }

        Reference latestVersion = igcRepositoryHelper.getFullAssetDetails(rid, assetType);

        if (latestVersion == null) {
            // If we can't retrieve the asset by RID, it no longer exists -- so send a delete event
            // TODO: currently only possible if we also know the assetType
            if (assetType != null) {
                sendPurgedEntity(assetType, rid);
                // Find any mapper(s) for this type that use a prefix and send a purge for the prefixed entity as well
                List<EntityMapping> referenceableMappers = igcRepositoryHelper.getMappers(assetType, localServerUserId);
                for (EntityMapping referenceableMapper : referenceableMappers) {
                    List<RelationshipMapping> relationshipMappings = referenceableMapper.getRelationshipMappers();
                    for (RelationshipMapping relationshipMapping : relationshipMappings) {
                        String prefixOne = relationshipMapping.getProxyOneMapping().getIgcRidPrefix();
                        String prefixTwo = relationshipMapping.getProxyTwoMapping().getIgcRidPrefix();
                        if (prefixTwo != null) {
                            sendPurgedEntity(assetType, prefixTwo + rid);
                        }
                        if (prefixOne != null) {
                            sendPurgedEntity(assetType, prefixOne + rid);
                        }
                    }
                }
            } else {
                if (log.isWarnEnabled()) { log.warn("No asset type was provided for purged RID {} -- cannot generate purgeEntity event.", rid); }
            }
        } else {

            // Otherwise see if there's a stub...
            OMRSStub stub = igcRepositoryHelper.getOMRSStubForAsset(latestVersion);

            // Calculate the delta between the latest version and the previous saved stub
            ChangeSet changeSet = new ChangeSet(igcRestClient, latestVersion, stub);
            Set<String> changedProperties = changeSet.getChangedProperties();

            // Output any entities first
            if (stub == null) {
                // If there is no stub, we need to treat this as a new entity
                if (log.isDebugEnabled()) { log.debug("Creating a new entity and stub for: {}", latestVersion.getId()); }
                sendNewEntity(latestVersion);
            } else if (!changedProperties.isEmpty()) {
                // Otherwise, it should be treated as an updated entity, but only if there was some change
                if (log.isDebugEnabled()) {
                    log.debug("Detected changes on entity {}", latestVersion.getId());
                    log.debug(" ... properties that changed: {}", changedProperties);
                    for (String propertyName : changedProperties) {
                        log.debug(" ...... details for property '{}'", propertyName);
                        log.debug(" .......... {}", changeSet.getChangesForProperty(propertyName).toString());
                    }
                    log.debug(" ... before: {}", stub.getPayload());
                    log.debug(" ... now:    {}", igcRestClient.getValueAsJSON(latestVersion));
                }
                sendUpdatedEntity(latestVersion, stub);
            } else {
                if (log.isInfoEnabled()) { log.info("Skipping asset - no changes detected: {}", latestVersion.getId()); }
            }

            // Retrieve the mapping from IGC property name to OMRS relationship type
            Map<String, List<RelationshipMapping>> relationshipMap = igcRepositoryHelper.getIgcPropertiesToRelationshipMappings(
                    latestVersion.getType(),
                    localServerUserId
            );
            if (log.isDebugEnabled()) { log.debug(" ... found mappings: {}", relationshipMap); }

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
                    if (log.isDebugEnabled()) { log.debug(" ... checking for any relationship on: {}", igcProperty); }
                    if (relationshipMap.containsKey(igcProperty)) {
                        List<ChangeSet.Change> changesForProperty = changeSet.getChangesForProperty(igcProperty);
                        if (log.isDebugEnabled()) { log.debug(" ...... found differences for property: {}", changesForProperty); }
                        for (RelationshipMapping relationshipMapping : relationshipMap.get(igcProperty)) {
                            processRelationships(
                                    relationshipMapping,
                                    latestVersion,
                                    changesForProperty,
                                    relationshipGUID
                            );
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
     * @param latestVersion the latest version of the IGC asset from which to get the relationship(s)
     * @param changesForProperty the list of changes for the IGC relationship property being processed
     * @param relationshipTriggerGUID the GUID of the relationship that triggered this processing (or null if not
     *                                triggered initially by the processing of another relationship)
     */
    private void processRelationships(RelationshipMapping relationshipMapping,
                                      Reference latestVersion,
                                      List<ChangeSet.Change> changesForProperty,
                                      IGCRelationshipGuid relationshipTriggerGUID) {

        if (log.isDebugEnabled()) { log.debug("processRelationships called with relationshipMapping {}, reference {} and changes {}", relationshipMapping, latestVersion, changesForProperty); }

        for (ChangeSet.Change change : changesForProperty) {

            String assetType = latestVersion.getType();
            Class pojo = igcRestClient.getPOJOForType(assetType);
            if (pojo != null) {
                List<String> referenceListProperties = igcRestClient.getPagedRelationalPropertiesFromPOJO(assetType);

                Object relatedValue = change.getNewValue(referenceListProperties);
                if (log.isDebugEnabled()) { log.debug(" ... found value: {}", relatedValue); }
                if (relatedValue != null) {
                    if (Reference.isReferenceList(relatedValue)) {

                        if (log.isDebugEnabled()) { log.debug(" ... found ReferenceList, processing each item"); }
                        ReferenceList related = (ReferenceList) relatedValue;
                        for (Reference relatedAsset : related.getItems()) {
                            processOneOrMoreRelationships(
                                    relationshipMapping,
                                    latestVersion,
                                    relatedAsset,
                                    referenceListProperties,
                                    change,
                                    relationshipTriggerGUID
                            );
                        }

                    } else if (Reference.isReference(relatedValue)) {
                        if (log.isDebugEnabled()) { log.debug(" ... found single Reference, processing it"); }
                        Reference relatedAsset = (Reference) relatedValue;
                        processOneOrMoreRelationships(
                                relationshipMapping,
                                latestVersion,
                                relatedAsset,
                                referenceListProperties,
                                change,
                                relationshipTriggerGUID
                        );
                    } else if (change.getIgcPropertyPath().endsWith("_id") && Reference.isSimpleType(relatedValue)) {
                        // In cases where a single object has been replaced, the JSON Patch may only show each property
                        // as needing replacement rather than the object as a whole -- this _should_ already be handled
                        // automatically by the change processing, so if we arrive here we missed something in the
                        // ChangeSet class's processing
                        if (log.isErrorEnabled()) { log.error(" ... change consolidation in ChangeSet did not work: {}", change); }
                    } else if (!change.getIgcPropertyPath().endsWith("_name") && !change.getIgcPropertyPath().endsWith("_url")) {
                        // if the path ends with '_name' or '_url' then we should already be handling it by the '_id' clause above
                        if (log.isWarnEnabled()) { log.warn("Expected relationship for path '{}' for guid {} but found neither Reference nor ReferenceList: {}", change.getIgcPropertyPath(), latestVersion.getId(), relatedValue); }
                    }
                } else {
                    if (log.isWarnEnabled()) { log.warn("Expected relationship for path '{}' for guid {} but found nothing.", change.getIgcPropertyPath(), latestVersion.getId()); }
                }
            } else {
                if (log.isWarnEnabled()) { log.warn("No registered POJO to translate asset type '{}' for guid {} -- skipping its relationships.", assetType, latestVersion.getId()); }
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
     * @param referenceListProperties the list of IGC property names that contain reference lists
     * @param change the JSON Patch entry indicating a specific change (always from the perspective of latestVersion)
     * @param relationshipTriggerGUID passthrough of GUID for relationship that triggered this process (if not triggered
     *                                directly from an event), null if not triggered by another relationship
     */
    private void processOneOrMoreRelationships(RelationshipMapping relationshipMapping,
                                               Reference latestVersion,
                                               Reference relatedAsset,
                                               List<String> referenceListProperties,
                                               ChangeSet.Change change,
                                               IGCRelationshipGuid relationshipTriggerGUID) {

        String omrsRelationshipType = relationshipMapping.getOmrsRelationshipType();
        String latestVersionRID = latestVersion.getId();

        String relatedRID = relatedAsset.getId();

        // Only proceed if there is actually some related RID (ie. it wasn't just an empty list of relationships)
        if (relatedRID != null && !relatedRID.equals("null")) {

            if (log.isDebugEnabled()) { log.debug("processOneOrMoreRelationships processing between {} and {} for type: {}", latestVersionRID, relatedRID, omrsRelationshipType); }

            RelationshipMapping.ProxyMapping pmOne = relationshipMapping.getProxyOneMapping();
            RelationshipMapping.ProxyMapping pmTwo = relationshipMapping.getProxyTwoMapping();

            // Translate the provided assets into the actual assets needed for the relationship
            String latestVersionType = latestVersion.getType();
            String relatedAssetType = relatedAsset.getType();
            List<Reference> proxyOnes = new ArrayList<>();
            List<Reference> proxyTwos = new ArrayList<>();
            if (pmOne.matchesAssetType(latestVersionType) && pmTwo.matchesAssetType(relatedAssetType)) {
                proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(latestVersion, igcRestClient);
                proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(relatedAsset, igcRestClient);
            } else if (pmTwo.matchesAssetType(latestVersionType) && pmOne.matchesAssetType(relatedAssetType)) {
                proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(relatedAsset, igcRestClient);
                proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(latestVersion, igcRestClient);
            } else if (relationshipMapping.hasLinkingAsset()) {
                String linkingType = relationshipMapping.getLinkingAssetType();
                if ( (latestVersionType.equals(linkingType) && pmTwo.matchesAssetType(relatedAssetType))
                        || (relatedAssetType.equals(linkingType) && pmOne.matchesAssetType(latestVersionType)) ) {
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(latestVersion, igcRestClient);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(relatedAsset, igcRestClient);
                } else if ( (relatedAssetType.equals(linkingType) && pmTwo.matchesAssetType(latestVersionType))
                        || (latestVersionType.equals(linkingType) && pmOne.matchesAssetType(relatedAssetType)) ) {
                    proxyOnes = relationshipMapping.getProxyOneAssetFromAsset(relatedAsset, igcRestClient);
                    proxyTwos = relationshipMapping.getProxyTwoAssetFromAsset(latestVersion, igcRestClient);
                } else {
                    if (log.isWarnEnabled()) { log.warn("Unable to match assets to proxies through linking asset '{}' for guids {} and {} through relationship type {} -- skipped, but this is likely indicative of a problem with the mapping.", linkingType, latestVersion.getId(), relatedAsset.getId(), omrsRelationshipType); }
                }
            } else {
                if (log.isWarnEnabled()) { log.warn("Unable to match assets {} and {} to proxies for any asset translation for relationship type {} -- skipped, but this is likely indicative of a problem with the mapping.", latestVersion.getId(), relatedAsset.getId(), omrsRelationshipType); }
                proxyOnes.add(latestVersion);
                proxyTwos.add(relatedAsset);
            }

            for (Reference proxyOne : proxyOnes) {
                for (Reference proxyTwo : proxyTwos) {
                    processSingleRelationship(
                            relationshipMapping,
                            proxyOne,
                            proxyTwo,
                            referenceListProperties,
                            change,
                            relationshipTriggerGUID
                    );
                }
            }

        } else {
            if (log.isInfoEnabled()) { log.info("Related RID for guid {} and relationship {} was null -- skipped.", latestVersion.getId(), omrsRelationshipType); }
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
     * @param referenceListProperties the list of IGC property names that contain reference lists
     * @param change the JSON Patch entry indicating a specific change
     * @param relationshipTriggerGUID passthrough of GUID for relationship that triggered this process (if not triggered
     *                                directly from an event), null if not triggered by another relationship
     */
    private void processSingleRelationship(RelationshipMapping relationshipMapping,
                                           Reference proxyOne,
                                           Reference proxyTwo,
                                           List<String> referenceListProperties,
                                           ChangeSet.Change change,
                                           IGCRelationshipGuid relationshipTriggerGUID) {

        String omrsRelationshipType = relationshipMapping.getOmrsRelationshipType();
        String latestVersionRID = proxyOne.getId();

        String relatedRID = proxyTwo.getId();

        // Only proceed if there is actually some related RID (ie. it wasn't just an empty list of relationships)
        if (relatedRID != null && !relatedRID.equals("null")) {

            if (log.isDebugEnabled()) { log.debug("processSingleRelationship processing between {} and {} for type: {}", latestVersionRID, relatedRID, omrsRelationshipType); }

            RelationshipMapping.ProxyMapping pmOne = relationshipMapping.getProxyOneMapping();
            RelationshipMapping.ProxyMapping pmTwo = relationshipMapping.getProxyTwoMapping();

            IGCRelationshipGuid igcRelationshipGuid = RelationshipMapping.getRelationshipGUID(
                    igcRepositoryHelper,
                    relationshipMapping,
                    proxyOne,
                    proxyTwo,
                    change.getIgcPropertyName(),
                    null
            );

            if (log.isDebugEnabled()) { log.debug(" ... calculated relationship GUID: {}", igcRelationshipGuid); }

            // Only continue if this relationship is different from the one that triggered the processing in the first place
            if (relationshipTriggerGUID == null || !relationshipTriggerGUID.equals(igcRelationshipGuid)) {
                String changeType = change.getOp();
                if (log.isDebugEnabled()) { log.debug(" ... change action: {}", changeType); }

                if (changeType.equals("remove")) {
                    try {
                        RelationshipDef relationshipDef = (RelationshipDef) igcomrsMetadataCollection.getTypeDefByName(
                                localServerUserId,
                                omrsRelationshipType
                        );
                        sendPurgedRelationship(
                                relationshipMapping,
                                relationshipDef,
                                igcRelationshipGuid,
                                change.getIgcPropertyName(),
                                proxyOne,
                                proxyTwo
                        );
                        // After purging the relationship, process any other updates
                        // on the assets at each end of the relationship
                        IGCEntityGuid igcEntityGuid1 = RelationshipMapping.getProxyOneGuidFromRelationship(
                                igcRepositoryHelper,
                                igcRelationshipGuid);
                        IGCEntityGuid igcEntityGuid2 = RelationshipMapping.getProxyTwoGuidFromRelationship(
                                igcRepositoryHelper,
                                igcRelationshipGuid);
                        processAsset(igcEntityGuid1.getRid(),
                                pmOne.getIgcAssetType(),
                                igcRelationshipGuid,
                                igcEntityGuid1.getGeneratedPrefix());
                        processAsset(igcEntityGuid2.getRid(),
                                pmTwo.getIgcAssetType(),
                                igcRelationshipGuid,
                                igcEntityGuid2.getGeneratedPrefix());
                    } catch (InvalidParameterException | RepositoryErrorException | TypeDefNotKnownException e) {
                        if (log.isErrorEnabled()) { log.error("Unable to retrieve relationship type definition: {}", omrsRelationshipType, e); }
                    }
                } else {
                    try {

                        Relationship relationship = igcomrsMetadataCollection.getRelationship(localServerUserId, igcRelationshipGuid.asGuid());
                        if (log.isDebugEnabled()) { log.debug(" ... retrieved relationship: {}", relationship); }

                        // Recursively call processAsset(rid, null) on any non-deletion events
                        // (do this first: so relationship comes after on unwinding from recursion)
                        String proxyOneType = proxyOne.getType();
                        String proxyTwoType = proxyTwo.getType();

                        if (!IGCRestConstants.getRelationshipLevelTypes().contains(proxyTwoType)) {
                            processAsset(relatedRID, proxyTwo.getType(), igcRelationshipGuid, igcRelationshipGuid.getGeneratedPrefix2());
                        } else {
                            if (log.isDebugEnabled()) { log.debug(" ... proxy two was a relationship-level type, not processing it as an asset: {}", proxyTwoType); }
                        }
                        if (!IGCRestConstants.getRelationshipLevelTypes().contains(proxyOneType)) {
                            processAsset(latestVersionRID, proxyOne.getType(), igcRelationshipGuid, igcRelationshipGuid.getGeneratedPrefix1());
                        } else {
                            if (log.isDebugEnabled()) { log.debug(" ... proxy one was a relationship-level type, not processing it as an asset: {}", proxyOneType); }
                        }

                        // Send the appropriate patch-defined action
                        switch (changeType) {
                            case "add":
                                sendNewRelationship(relationship);
                                break;
                            case "replace":
                                sendReplacedRelationship(
                                        relationshipMapping,
                                        relationship,
                                        proxyOne,
                                        proxyTwo,
                                        referenceListProperties,
                                        change
                                );
                                break;
                            default:
                                if (log.isWarnEnabled()) { log.warn("Unknown action '{}' for relationship {}", changeType, igcRelationshipGuid); }
                                break;
                        }

                    } catch (RelationshipNotKnownException e) {
                        if (log.isErrorEnabled()) { log.error("Unable to find relationship with GUID: {}", igcRelationshipGuid); }
                    } catch (InvalidParameterException | RepositoryErrorException e) {
                        if (log.isErrorEnabled()) { log.error("Unknown error occurred trying to retrieve relationship: {}", igcRelationshipGuid, e); }
                    }
                }
            } else {
                if (log.isInfoEnabled()) { log.info("Relationship was same as one that triggered this processing -- skipping: {}", relationshipTriggerGUID); }
            }

        } else {
            if (log.isWarnEnabled()) { log.warn("Related RID for relationship type {} and guid {} was null.", omrsRelationshipType, latestVersionRID); }
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

        if (log.isDebugEnabled()) { log.debug("processSelfReferencingRelationship processing for {} and type: {}", latestVersionRID, omrsRelationshipType); }

        IGCRelationshipGuid igcRelationshipGuid = RelationshipMapping.getRelationshipGUID(
                igcRepositoryHelper,
                relationshipMapping,
                latestVersion,
                latestVersion,
                RelationshipMapping.SELF_REFERENCE_SENTINEL,
                null
        );
        if (log.isDebugEnabled()) { log.debug(" ... calculated relationship GUID: {}", igcRelationshipGuid); }

        // Only continue if this relationship is different from the one that triggered the processing in the first place
        if (relationshipTriggerGUID == null || !relationshipTriggerGUID.equals(igcRelationshipGuid)) {
            try {
                Relationship relationship = igcomrsMetadataCollection.getRelationship(localServerUserId, igcRelationshipGuid.asGuid());
                if (log.isDebugEnabled()) { log.debug(" ... retrieved relationship: {}", relationship); }
                // If there was no stub, this is a new entity
                if (stub == null) {
                    sendNewRelationship(relationship);
                } else {
                    sendUpdatedRelationship(relationship);
                }
                // Note: no need to call back to processAsset with prefixed GUID as it will have been handled already
                // by the base asset being processed
            } catch (RelationshipNotKnownException e) {
                if (log.isErrorEnabled()) { log.error("Unable to find relationship with GUID: {}", igcRelationshipGuid); }
            } catch (InvalidParameterException | RepositoryErrorException e) {
                if (log.isErrorEnabled()) { log.error("Unknown error occurred trying to retrieve relationship: {}", igcRelationshipGuid, e); }
            }
        } else {
            if (log.isInfoEnabled()) { log.info("Relationship was same as the one that triggered this processing -- skipping: {}", relationshipTriggerGUID); }
        }

    }

    /**
     * Send an event out on OMRS topic for a new relationship.
     *
     * @param relationship the new relationship to publish
     */
    private void sendNewRelationship(Relationship relationship) {
        if (relationship != null) {
            repositoryEventProcessor.processNewRelationshipEvent(
                    sourceName,
                    metadataCollectionId,
                    originatorServerName,
                    originatorServerType,
                    null,
                    relationship
            );
        }
    }

    /**
     * Send an event out on OMRS topic for an updated relationship.
     *
     * @param relationship the updated relationship to publish
     */
    private void sendUpdatedRelationship(Relationship relationship) {
        if (relationship != null) {
            repositoryEventProcessor.processUpdatedRelationshipEvent(
                    sourceName,
                    metadataCollectionId,
                    originatorServerName,
                    originatorServerType,
                    null,
                    null,
                    relationship
            );
        }
    }

    /**
     * Send out the events needed for a relationship that has been replaced: basically a DeletePurge followed by a
     * New.
     *
     * @param relationshipMapping the mapping to use for translating this relationship
     * @param relationship the new relationship
     * @param proxyOne the IGC asset used as proxyOne of the new relationship
     * @param proxyTwo the IGC asset used as proxyTwo of the new relationship
     * @param referenceListProperties the list of IGC property names that contain reference lists
     * @param change the change that indicated this relationship replacement
     */
    private void sendReplacedRelationship(RelationshipMapping relationshipMapping,
                                          Relationship relationship,
                                          Reference proxyOne,
                                          Reference proxyTwo,
                                          List<String> referenceListProperties,
                                          ChangeSet.Change change) {

        String newRelationshipGUID = relationship.getGUID();

        String igcPropertyName = change.getIgcPropertyName();
        Object ora = change.getOldValue(referenceListProperties);
        if (ora != null) {
            Reference oldRelatedAsset = (Reference) ora;
            if (oldRelatedAsset.getId() != null) {
                Reference newRelatedAsset = (Reference) change.getNewValue(referenceListProperties);
                if (log.isDebugEnabled()) {
                    log.debug("Processing relationship replacement for: {}", oldRelatedAsset);
                }
                String newRelatedAssetRID = newRelatedAsset.getId();
                try {
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
                        if (log.isDebugEnabled()) {
                            log.debug(" ... calculated old relationship GUID: {}", oldRelationshipGUID);
                        }
                        sendPurgedRelationship(
                                relationshipMapping,
                                relationshipDef,
                                oldRelationshipGUID,
                                igcPropertyName,
                                oldProxyOne,
                                oldProxyTwo
                        );
                    } else {
                        if (log.isWarnEnabled()) {
                            log.warn("Unable to find previous version for relationship replacement -- sending only new: {}", newRelationshipGUID);
                        }
                    }
                } catch (InvalidParameterException | RepositoryErrorException | TypeDefNotKnownException e) {
                    if (log.isErrorEnabled()) {
                        log.error("Unable to find relationship type definition '{}' / not supported for guid: {}", relationshipMapping.getOmrsRelationshipType(), newRelationshipGUID);
                    }
                }
            } else {
                if (log.isWarnEnabled()) { log.warn("Unable to find any previous version for the relationship replacement (no ID) -- sending only new: {}", newRelationshipGUID); }
            }
        } else {
            if (log.isWarnEnabled()) { log.warn("Unable to find any previous version for the relationship replacement (null) -- sending only new: {}", newRelationshipGUID); }
        }
        sendNewRelationship(relationship);

    }

    /**
     * Send an event out on OMRS topic for a purged relationship.
     *
     * @param relationshipMapping the relationship mapping to use to determine what to delete and purge
     * @param relationshipDef the OMRS relationship definition
     * @param relationshipGUID the IGC GUID of the relationship to be deleted and purged
     * @param igcPropertyName the name of the IGC property holding the relationship
     * @param proxyOne IGC asset for end one of the relationship
     * @param proxyTwo IGC asset for end two of the relationship
     */
    private void sendPurgedRelationship(RelationshipMapping relationshipMapping,
                                        RelationshipDef relationshipDef,
                                        IGCRelationshipGuid relationshipGUID,
                                        String igcPropertyName,
                                        Reference proxyOne,
                                        Reference proxyTwo) {

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
            } catch (RepositoryErrorException e) {
                if (log.isErrorEnabled()) { log.error("Unable to retrieve relationship details for: {}", relationshipGUID); }
            }
        } else {
            if (log.isWarnEnabled()) { log.warn("Unable to produce DeletePurgedRelationshipEvent for relationship: {}", relationshipGUID); }
        }

    }

    /**
     * Send an event out on OMRS topic for a new entity.
     *
     * @param asset the IGC asset for which we should send a new entity event
     */
    private void sendNewEntity(Reference asset) {

        EntityDetail detail = getEntityDetailForAsset(asset);

        if (detail != null) {

            // Send an event for the entity itself
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
                    sendNewClassification(detail);
                }
            }

            // See if there are any generated entities to send an event for (ie. *Type)
            List<EntityMapping> referenceableMappers = igcRepositoryHelper.getMappers(asset.getType(), localServerUserId);
            for (EntityMapping referenceableMapper : referenceableMappers) {
                // Generated entities MUST have a prefix, so if there is no prefix ignore that mapper
                String ridPrefix = referenceableMapper.getIgcRidPrefix();
                if (ridPrefix != null) {
                    IGCEntityGuid igcEntityGuid = igcRepositoryHelper.getEntityGuid(asset.getType(), ridPrefix, asset.getId());
                    EntityDetail genDetail = getEntityDetailForAssetWithGUID(asset, igcEntityGuid);
                    if (genDetail != null) {
                        repositoryEventProcessor.processNewEntityEvent(
                                sourceName,
                                metadataCollectionId,
                                originatorServerName,
                                originatorServerType,
                                null,
                                genDetail
                        );
                        // TODO: for now this sends the same set of classifications every time, known design issue with how
                        //  classifications are currently handled (to be changed once classifications are reworked)
                        classifications = genDetail.getClassifications();
                        if (classifications != null) {
                            for (Classification classification : classifications) {
                                sendNewClassification(genDetail);
                            }
                        }
                    } else {
                        if (log.isWarnEnabled()) { log.warn("Unable to generate new entity for asset type {} with prefix {} and RID: {}", asset.getType(), ridPrefix, asset.getId()); }
                    }
                } else {
                    if (log.isDebugEnabled()) { log.debug("No prefix found in mapper {}, skipping for generated new entity.", referenceableMapper.getClass().getCanonicalName()); }
                }
            }

            // Finally, update the stub with the latest version of the asset
            // (if any of the above fail, this will also be missed, so we will simply have more updates on the next event)
            igcRepositoryHelper.upsertOMRSStubForAsset(asset);

        } else {
            if (log.isErrorEnabled()) { log.error("EntityDetail could not be retrieved for RID: {}", asset.getId()); }
        }
    }

    /**
     * Send an event out on OMRS topic for an updated entity.
     *
     * @param latestVersion the IGC asset for which we should send an updated entity event, in its current state
     * @param stub the OMRS stub for the asset, containing the last version for which we successfully sent an event
     */
    private void sendUpdatedEntity(Reference latestVersion, OMRSStub stub) {

        EntityDetail latest = getEntityDetailForAsset(latestVersion);

        if (latest != null) {

            // Send an event for the entity itself
            EntityDetail last = getEntityDetailForStub(stub);
            repositoryEventProcessor.processUpdatedEntityEvent(
                    sourceName,
                    metadataCollectionId,
                    originatorServerName,
                    originatorServerType,
                    null,
                    last,
                    latest
            );

            processClassifications(latest, latest.getClassifications(), last == null ? new ArrayList<>() : last.getClassifications());

            // See if there are any generated entities to send an event for (ie. *Type)
            List<EntityMapping> referenceableMappers = igcRepositoryHelper.getMappers(latestVersion.getType(), localServerUserId);
            for (EntityMapping referenceableMapper : referenceableMappers) {
                // Generated entities MUST have a prefix, so if there is no prefix ignore that mapper
                String ridPrefix = referenceableMapper.getIgcRidPrefix();
                if (ridPrefix != null) {
                    IGCEntityGuid igcEntityGuid = igcRepositoryHelper.getEntityGuid(latestVersion.getType(), ridPrefix, latestVersion.getId());
                    EntityDetail genDetail = getEntityDetailForAssetWithGUID(latestVersion, igcEntityGuid);
                    if (genDetail != null) {
                        EntityDetail genLast = getEntityDetailForStubWithGUID(stub, igcEntityGuid);
                        repositoryEventProcessor.processUpdatedEntityEvent(
                                sourceName,
                                metadataCollectionId,
                                originatorServerName,
                                originatorServerType,
                                null,
                                genLast,
                                genDetail
                        );
                        processClassifications(genDetail, genDetail.getClassifications(), genLast == null ? new ArrayList<>() : genLast.getClassifications());
                    } else {
                        if (log.isWarnEnabled()) { log.warn("Unable to generate updated entity for asset type {} with prefix {} and RID: {}", latestVersion.getType(), ridPrefix, latestVersion.getId()); }
                    }
                } else {
                    if (log.isDebugEnabled()) { log.debug("No prefix found in mapper {}, skipping for generated update entity.", referenceableMapper.getClass().getCanonicalName()); }
                }
            }

            // Finally, update the stub with the latest version of the asset
            // (if any of the above fail, this will also be missed, so we will simply have more updates on the next event)
            igcRepositoryHelper.upsertOMRSStubForAsset(latestVersion);

        } else {
            if (log.isErrorEnabled()) { log.error("Latest EntityDetail could not be retrieved for RID: {}", latestVersion.getId()); }
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
                sendNewClassification(detail);
            } else {
                matchingGUIDs.add(guid);
            }
        }
        // Then iterate through the last version, to find any removed classifications
        for (String guid : lastClassificationByGUID.keySet()) {
            if (!latestClassificationByGUID.containsKey(guid)) {
                sendRemovedClassification(detail);
            }
        }
        // Finally, iterate through the classifications that are the same type and see if there are any changes
        for (String matchingGUID : matchingGUIDs) {
            Classification latest = latestClassificationByGUID.get(matchingGUID);
            Classification last = lastClassificationByGUID.get(matchingGUID);
            if (!latest.equals(last)) {
                sendChangedClassification(detail);
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
                    if (log.isWarnEnabled()) { log.warn("Found multiple classifications of type {} -- clobbering!", typeGUID); }
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
     */
    private void sendNewClassification(EntityDetail detail) {
        repositoryEventProcessor.processClassifiedEntityEvent(
                sourceName,
                metadataCollectionId,
                originatorServerName,
                originatorServerType,
                null,
                detail
        );
    }

    /**
     * Send an event for an existing classification being changed on an entity's detail.
     *
     * @param detail the entity for which to send a changed classification event
     */
    private void sendChangedClassification(EntityDetail detail) {
        repositoryEventProcessor.processReclassifiedEntityEvent(
                sourceName,
                metadataCollectionId,
                originatorServerName,
                originatorServerType,
                null,
                detail
        );
    }

    /**
     * Send an event for a classification having been removed from an entity's detail.
     *
     * @param detail the entity for which to send a removed classification event
     */
    private void sendRemovedClassification(EntityDetail detail) {
        repositoryEventProcessor.processDeclassifiedEntityEvent(
                sourceName,
                metadataCollectionId,
                originatorServerName,
                originatorServerType,
                null,
                detail
        );
    }

    /**
     * Send an event out on OMRS topic for a purged entity.
     *
     * @param igcAssetType the IGC asset type (ie. translated from the ASSET_TYPE from the event)
     * @param rid the IGC Repository ID (RID) of the asset
     */
    private void sendPurgedEntity(String igcAssetType, String rid) {

        OMRSStub stub = igcRepositoryHelper.getOMRSStubForAsset(rid, igcAssetType);

        // Purge entities by getting all mappers used for that entity (ie. *Type generated entities
        // as well as non-generated entities)
        List<EntityMapping> referenceableMappers = igcRepositoryHelper.getMappers(igcAssetType, localServerUserId);
        for (EntityMapping referenceableMapper : referenceableMappers) {
            String ridPrefix = referenceableMapper.getIgcRidPrefix();
            IGCEntityGuid igcEntityGuid = igcRepositoryHelper.getEntityGuid(igcAssetType, ridPrefix, rid);
            EntityDetail detail = getEntityDetailForStubWithGUID(stub, igcEntityGuid);
            if (detail != null) {
                repositoryEventProcessor.processDeletePurgedEntityEvent(
                        sourceName,
                        metadataCollectionId,
                        originatorServerName,
                        originatorServerType,
                        null,
                        detail
                );
            } else {
                if (log.isWarnEnabled()) { log.warn("No stub information exists for purged RID {} -- cannot generated purgeEntity event.", rid); }
            }
        }

        // Finally, remove the stub (so that if such an asset is created in the future it is recognised as new
        // rather than an update)
        igcRepositoryHelper.deleteOMRSStubForAsset(rid, igcAssetType);

    }

    /**
     * Method to process events from v11.7 of Information Server.
     *
     * @param event inbound event
     */
    private void processEventV117(String event) {
        // TODO: implement processEventV117
        if (log.isDebugEnabled()) { log.debug("Not yet implemented as v11.7-specific -- backing to v11.5 processing: {}", event); }
        processEventV115(event);
    }

    /**
     * Free up any resources held since the connector is no longer needed.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    @Override
    public void disconnect() throws ConnectorCheckedException {
        super.disconnect();
        igcKafkaConsumer.stop();
    }

}
