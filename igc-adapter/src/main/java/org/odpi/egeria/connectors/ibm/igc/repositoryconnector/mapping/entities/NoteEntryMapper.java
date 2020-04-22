/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedNoteLogEntryMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

/**
 * Defines the mapping to the OMRS "NoteEntry" entity.
 */
public class NoteEntryMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final NoteEntryMapper INSTANCE = new NoteEntryMapper();
    }
    public static NoteEntryMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected NoteEntryMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "note",
                "Note",
                "NoteEntry",
                null,
                false
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("subject", "title");
        addSimplePropertyMapping("note", "text");
        addLiteralPropertyMapping("isPublic", true);

        // The list of relationships that should be mapped
        addRelationshipMapper(AttachedNoteLogEntryMapper.getInstance(null));

    }

    /**
     * Defines the mapping of qualifiedName to ensure uniqueness for a given note.
     *
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     */
    @Override
    protected InstanceProperties complexPropertyMappings(EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        instanceProperties = super.complexPropertyMappings(entityMap, instanceProperties);

        // Above call will already setup additionalProperties for us, we just need to override qualifiedName
        // And qualifiedName itself actually cannot be completely unique without the RID of the note, so we will
        // just use the RID of the note as the qualifiedName

        final String methodName = "complexPropertyMappings";

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        String qualifiedName = entityMap.getIgcEntityRid();
        instanceProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                igcomrsRepositoryConnector.getRepositoryName(),
                instanceProperties,
                "qualifiedName",
                qualifiedName,
                methodName
        );

        return instanceProperties;

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
                                                 PropertyComparisonOperator operator,
                                                 InstancePropertyValue value) {
        // We have no real way of supporting a search against NoteEntry, so we will simply always ensure that no results
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
        // We have no real way of supporting a search against NoteEntry, so we will simply always ensure that no results
        // are returned
        igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchFilter getAllNoneOrSome(SearchProperties matchProperties) {
        // We have no real way of supporting a search against NoteEntry, so we will simply always ensure that no results
        // are returned
        return SearchFilter.NONE;
    }

}
