/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Note;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.NoteLogMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Singleton to map the OMRS "AttachedNoteLogEntry" relationship for IGC assets.
 */
public class AttachedNoteLogEntryMapper extends RelationshipMapping {

    private static final Logger log = LoggerFactory.getLogger(AttachedNoteLogEntryMapper.class);

    private static class Singleton {
        private static final AttachedNoteLogEntryMapper INSTANCE = new AttachedNoteLogEntryMapper();
    }
    public static AttachedNoteLogEntryMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected AttachedNoteLogEntryMapper() {
        super(
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                "note",
                "notes",
                "belonging_to",
                "AttachedNoteLogEntry",
                "logs",
                "entries",
                NoteLogMapper.IGC_RID_PREFIX,
                null
        );
        setContainedType(ContainedType.TWO);
        setOptimalStart(OptimalStart.CUSTOM); // Notes cannot be searched, can only be retrieved from the owning asset

        // We will explicitly exclude the following types from possessing notes, as they are not able to hold notes
        // in IGC
        ProxyMapping pmOne = getProxyOneMapping();
        pmOne.addExcludedIgcAssetType("connector");
        pmOne.addExcludedIgcAssetType("data_connection");
        pmOne.addExcludedIgcAssetType("group");
        pmOne.addExcludedIgcAssetType("label");

    }

    private Reference getAssetFromNote(Note note, IGCRestClient igcRestClient) {
        Reference asset = note.getBelongingTo();
        if (asset == null) {
            Note withAsset = igcRestClient.getAssetWithSubsetOfProperties(note.getId(), note.getType(), new String[]{"belonging_to"});
            asset = withAsset.getBelongingTo();
        }
        return asset;
    }

    private List<Note> getNotesFromAsset(Reference asset, IGCRestClient igcRestClient) {
        ItemList<Note> notes = asset.getNotes();
        if (notes != null) {
            return igcRestClient.getAllPages("notes", notes);
        } else {
            String type = asset.getType();
            if (igcRestClient.getPagedRelationshipPropertiesForType(type).contains("notes")) {
                Reference withNotes = igcRestClient.getAssetWithSubsetOfProperties(asset.getId(), asset.getType(), new String[]{"notes"});
                notes = withNotes.getNotes();
                return igcRestClient.getAllPages("notes", notes);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Custom implementation of the relationship between a NoteLog (generated) and a NoteEntry (note).
     * The relationship itself in IGC can only be accessed in one direction, and is dependent on the underlying IGC
     * type that was used to generate the NoteLog in the first place.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param relationships the relationships to which to add
     * @param fromIgcObject the IGC asset that may have notes
     * @param toIgcObject the note (or null if all should be retrieved)
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user requesting the relationships
     */
    @Override
    public void addMappedOMRSRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                           List<Relationship> relationships,
                                           Reference fromIgcObject,
                                           Reference toIgcObject,
                                           int fromRelationshipElement,
                                           SequencingOrder sequencingOrder,
                                           int pageSize,
                                           String userId) {

        log.debug("Adding mapped relationships from {} to {}.", fromIgcObject == null ? "<null>" : fromIgcObject.getType(), toIgcObject == null ? "<null>" : toIgcObject.getType());

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        RelationshipDef relationshipDef = (RelationshipDef) repositoryHelper.getTypeDefByName(
                igcomrsRepositoryConnector.getRepositoryName(),
                "AttachedNoteLogEntry");

        ProxyMapping one = getProxyOneMapping();

        if (fromIgcObject instanceof Note) {

            if (toIgcObject != null && one.matchesAssetType(toIgcObject.getType())) {
                // Just add this single relationship
                addRelationshipSafe(igcomrsRepositoryConnector, relationshipDef, toIgcObject, fromIgcObject, userId, relationships);
            } else if (toIgcObject == null) {
                // Otherwise retrieve all of the relationships and add all of them
                Reference asset = getAssetFromNote((Note) fromIgcObject, igcRestClient);
                if (asset != null) {
                    addRelationshipSafe(igcomrsRepositoryConnector, relationshipDef, asset, fromIgcObject, userId, relationships);
                } else {
                    log.warn("Unable to find the owning asset for note: {}", fromIgcObject);
                }
            } else {
                log.warn("Skipping asset relationship, not a type that contains notes: {}", fromIgcObject);
            }

        } else if (fromIgcObject != null && one.matchesAssetType(fromIgcObject.getType())) {

            if (toIgcObject instanceof Note) {
                // Just add this single relationship
                addRelationshipSafe(igcomrsRepositoryConnector, relationshipDef, fromIgcObject, toIgcObject, userId, relationships);
            } else {
                // Otherwise retrieve all the relationships and add them all
                List<Note> notes = getNotesFromAsset(fromIgcObject, igcRestClient);
                for (Note note : notes) {
                    addRelationshipSafe(igcomrsRepositoryConnector, relationshipDef, fromIgcObject, note, userId, relationships);
                }
            }

        } else {
            log.warn("Neither end of the relationship was an IGC note, unable to determine relationship.");
        }

    }

    private void addRelationshipSafe(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                     RelationshipDef relationshipDef,
                                     Reference asset,
                                     Reference note,
                                     String userId,
                                     List<Relationship> relationships) {
        try {
            Relationship relationship = getMappedRelationship(
                    igcomrsRepositoryConnector,
                    AttachedNoteLogEntryMapper.getInstance(null),
                    relationshipDef,
                    asset,
                    note,
                    "notes",
                    userId,
                    null,
                    true
            );
            relationships.add(relationship);
        } catch (RepositoryErrorException e) {
            log.error("Unable to map note-containing asset relationship.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGCSearch> getComplexIGCSearchCriteria(IGCOMRSRepositoryConnector repositoryConnector,
                                                       SearchProperties matchProperties) {
        // As with NoteEntry entities themselves, do not search on AttachedNoteLogEntry
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGCSearch> getComplexIGCSearchCriteria(IGCOMRSRepositoryConnector repositoryConnector,
                                                       String searchCriteria) throws FunctionNotSupportedException {
        // As with NoteEntry entities themselves, do not search on AttachedNoteLogEntry
        return Collections.emptyList();
    }

}
