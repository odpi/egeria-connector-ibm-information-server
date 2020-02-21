/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.NoteLogMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

import java.util.Collections;
import java.util.List;

/**
 * Singleton to map the OMRS "AttachedNoteLog" relationship for IGC assets.
 */
public class AttachedNoteLogMapper extends RelationshipMapping {

    private static class Singleton {
        private static final AttachedNoteLogMapper INSTANCE = new AttachedNoteLogMapper();
    }
    public static AttachedNoteLogMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    /**
     * Create a new sub-type of the AttachedNoteLog for the provided IGC asset type.
     *
     * @param igcAssetType the IGC asset type for which to create an AttachedNoteLog relationship
     */
    protected AttachedNoteLogMapper(String igcAssetType) {
        super(
                igcAssetType,
                igcAssetType,
                SELF_REFERENCE_SENTINEL,
                SELF_REFERENCE_SENTINEL,
                "AttachedNoteLog",
                "describes",
                "noteLogs",
                null,
                NoteLogMapper.IGC_RID_PREFIX
        );
        setCommonCharacteristics();
    }

    private AttachedNoteLogMapper() {
        super(
                "",
                "",
                SELF_REFERENCE_SENTINEL,
                SELF_REFERENCE_SENTINEL,
                "AttachedNoteLog",
                "describes",
                "noteLogs",
                null,
                NoteLogMapper.IGC_RID_PREFIX
        );
        setCommonCharacteristics();
        addSubType(AttachedNoteLogMapper_Category.getInstance(null));
        addSubType(AttachedNoteLogMapper_Database.getInstance(null));
        addSubType(AttachedNoteLogMapper_DatabaseColumn.getInstance(null));
        addSubType(AttachedNoteLogMapper_DatabaseSchema.getInstance(null));
        addSubType(AttachedNoteLogMapper_DatabaseTable.getInstance(null));
        addSubType(AttachedNoteLogMapper_DataClass.getInstance(null));
        addSubType(AttachedNoteLogMapper_DataFile.getInstance(null));
        addSubType(AttachedNoteLogMapper_DataFileField.getInstance(null));
        addSubType(AttachedNoteLogMapper_DataFileFolder.getInstance(null));
        addSubType(AttachedNoteLogMapper_DataFileRecord.getInstance(null));
        addSubType(AttachedNoteLogMapper_Host.getInstance(null));
        addSubType(AttachedNoteLogMapper_InformationGovernancePolicy.getInstance(null));
        addSubType(AttachedNoteLogMapper_Term.getInstance(null));
        addSubType(AttachedNoteLogMapper_User.getInstance(null));
    }

    private void setCommonCharacteristics() {
        setOptimalStart(OptimalStart.ONE);
        addLiteralPropertyMapping("isPublic", true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGCSearch> getComplexIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                       String repositoryName,
                                                       IGCRestClient igcRestClient,
                                                       InstanceProperties matchProperties,
                                                       MatchCriteria matchCriteria) {
        // As with NoteLog entities themselves, do not search on AttachedNoteLogs
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGCSearch> getComplexIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                       String repositoryName,
                                                       IGCRestClient igcRestClient,
                                                       String searchCriteria) throws FunctionNotSupportedException {
        // As with NoteLog entities themselves, do not search on AttachedNoteLogs
        return Collections.emptyList();
    }

}
