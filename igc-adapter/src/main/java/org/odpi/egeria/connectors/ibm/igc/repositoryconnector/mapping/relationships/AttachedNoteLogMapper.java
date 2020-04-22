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
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
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

    protected AttachedNoteLogMapper() {
        super(
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                SELF_REFERENCE_SENTINEL,
                SELF_REFERENCE_SENTINEL,
                "AttachedNoteLog",
                "describes",
                "noteLogs",
                null,
                NoteLogMapper.IGC_RID_PREFIX
        );

        addLiteralPropertyMapping("isPublic", true);

        // We will explicitly exclude the following types from possessing notes, as they are not able to hold notes
        // in IGC
        ProxyMapping pmOne = getProxyOneMapping();
        pmOne.addExcludedIgcAssetType("connector");
        pmOne.addExcludedIgcAssetType("data_connection");
        pmOne.addExcludedIgcAssetType("group");
        pmOne.addExcludedIgcAssetType("label");

        ProxyMapping pmTwo = getProxyTwoMapping();
        pmTwo.addExcludedIgcAssetType("connector");
        pmTwo.addExcludedIgcAssetType("data_connection");
        pmTwo.addExcludedIgcAssetType("group");
        pmTwo.addExcludedIgcAssetType("label");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGCSearch> getComplexIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                       String repositoryName,
                                                       IGCRestClient igcRestClient,
                                                       SearchProperties matchProperties) {
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
