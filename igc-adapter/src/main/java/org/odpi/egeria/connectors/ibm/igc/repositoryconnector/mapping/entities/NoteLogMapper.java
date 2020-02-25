/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedNoteLogEntryMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedNoteLogMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

/**
 * Defines the mapping to the OMRS "NoteLog" entity.  These are entirely generated as one-to-one instances to the
 * instances of IGC assets that are capable of possessing notes.  They are not searchable, and can only be retrieved
 * from the entity instances that have notes themselves.
 */
public class NoteLogMapper extends ReferenceableMapper {

    public static final String IGC_RID_PREFIX = "NL";

    private static class Singleton {
        private static final NoteLogMapper INSTANCE = new NoteLogMapper();
    }
    public static NoteLogMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private NoteLogMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "category",
                "Category",
                "NoteLog",
                IGC_RID_PREFIX,
                false
        );
        addOtherIGCAssetType("data_class");
        addOtherIGCAssetType("data_file");
        addOtherIGCAssetType("data_file_field");
        addOtherIGCAssetType("data_file_folder");
        addOtherIGCAssetType("data_file_record");
        addOtherIGCAssetType("database");
        addOtherIGCAssetType("database_column");
        addOtherIGCAssetType("database_schema");
        addOtherIGCAssetType("database_table");
        addOtherIGCAssetType("host");
        addOtherIGCAssetType("information_governance_policy");
        addOtherIGCAssetType("term");
        addOtherIGCAssetType("user");

        // The list of properties that should be mapped
        addLiteralPropertyMapping("name", null);
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("isPublic", true);

        // The list of relationships that should be mapped
        addRelationshipMapper(AttachedNoteLogMapper.getInstance(null));
        addRelationshipMapper(AttachedNoteLogEntryMapper.getInstance(null));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 InstancePropertyValue value) {
        // We have no real way of supporting a search against NoteLogs, so we will simply always ensure that no results
        // are returned
        igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComplexStringSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                               String repositoryName,
                                               IGCRestClient igcRestClient,
                                               IGCSearchConditionSet igcSearchConditionSet,
                                               String searchCriteria) throws FunctionNotSupportedException {
        // We have no real way of supporting a search against NoteLogs, so we will simply always ensure that no results
        // are returned
        igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchFilter getAllNoneOrSome(InstanceProperties matchProperties, MatchCriteria matchCriteria) {
        // We have no real way of supporting a search against NoteLogs, so we will simply always ensure that no results
        // are returned
        return SearchFilter.NONE;
    }

}
