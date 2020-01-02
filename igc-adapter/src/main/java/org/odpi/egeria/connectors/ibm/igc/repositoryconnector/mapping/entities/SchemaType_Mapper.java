/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

/**
 * Defines the mapping to the OMRS "SchemaType" entity.
 */
public class SchemaType_Mapper extends SchemaElement_Mapper {

    private static class Singleton {
        private static final SchemaType_Mapper INSTANCE = new SchemaType_Mapper();
    }
    public static SchemaType_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private SchemaType_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "SchemaType",
                null
        );

    }

    protected SchemaType_Mapper(String igcAssetTypeName,
                                String igcAssetTypeDisplayName,
                                String omrsEntityTypeName,
                                String prefix) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                prefix
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("long_description", "usage");
        addSimplePropertyMapping("modified_by", "author");
        addComplexOmrsProperty("namespace");
        addLiteralPropertyMapping("versionNumber", null);
        addLiteralPropertyMapping("encodingStandard", null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected InstanceProperties complexPropertyMappings(EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        instanceProperties = super.complexPropertyMappings(entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        Reference igcEntity = entityMap.getIgcEntity();

        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // setup the OMRS 'namespace' property
        Identity identity = igcEntity.getIdentity(igcRestClient);
        Identity parent = identity.getParentIdentity();
        if (parent != null) {
            instanceProperties = repositoryHelper.addStringPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "namespace",
                    parent.getName(),
                    methodName
            );
        }

        return instanceProperties;

    }

    /**
     * Handle the search for 'namespace' by searching against the name of the parent of the object in IGC.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search which is not supported
     */
    @Override
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, value);

        final String methodName = "addComplexPropertySearchCriteria";

        // Only need to add a condition of we are after the 'namespace' and have been provided a String
        if (omrsPropertyName.equals("namespace") && value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
            String name = value.valueAsString();
            addNamespaceCriteria(
                    repositoryHelper,
                    repositoryName,
                    methodName,
                    name,
                    igcSearchConditionSet
            );
        }

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

        super.addComplexStringSearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, searchCriteria);

        final String methodName = "addComplexStringSearchCriteria";
        addNamespaceCriteria(
                repositoryHelper,
                repositoryName,
                methodName,
                searchCriteria,
                igcSearchConditionSet
        );

    }

    private void addNamespaceCriteria(OMRSRepositoryHelper repositoryHelper,
                                      String repositoryName,
                                      String methodName,
                                      String toSearch,
                                      IGCSearchConditionSet igcSearchConditionSet) throws FunctionNotSupportedException {
        String parentPropertyName = getParentPropertyName() + ".name";
        IGCSearchCondition regex = IGCRepositoryHelper.getRegexSearchCondition(
                repositoryHelper,
                repositoryName,
                methodName,
                parentPropertyName,
                toSearch
        );
        igcSearchConditionSet.addCondition(regex);
    }

}
