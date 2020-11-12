/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;

/**
 * Defines the common mappings to the OMRS "GovernanceDefinition" entity.
 */
public class GovernanceDefinitionMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final GovernanceDefinitionMapper INSTANCE = new GovernanceDefinitionMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "GovernanceDefinition"
        );
    }
    public static GovernanceDefinitionMapper getInstance(IGCVersionEnum version) {
        return GovernanceDefinitionMapper.Singleton.INSTANCE;
    }

    protected GovernanceDefinitionMapper(String igcAssetTypeName,
                                         String igcAssetTypeDisplayName,
                                         String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "title");
        addSimplePropertyMapping("short_description", "summary");
        addSimplePropertyMapping("long_description", "description");
        addComplexIgcProperty("parent_policy");
        addComplexOmrsProperty("domain");
        addLiteralPropertyMapping("scope", null);
        addLiteralPropertyMapping("priority", null);
        addLiteralPropertyMapping("implications", null);
        addLiteralPropertyMapping("outcomes", null);
        addLiteralPropertyMapping("results", null);
        addLiteralPropertyMapping("domainIdentifier", null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected InstanceProperties complexPropertyMappings(ObjectCache cache,
                                                         EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) throws RepositoryErrorException {

        instanceProperties = super.complexPropertyMappings(cache, entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        Reference igcEntity = entityMap.getIgcEntity();

        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // setup the OMRS 'domain' property
        try {
            Reference parentPolicy = (Reference) igcRestClient.getPropertyByName(igcEntity, "parent_policy");
            if (parentPolicy != null) {
                instanceProperties = repositoryHelper.addStringPropertyToInstance(
                        repositoryName,
                        instanceProperties,
                        "domain",
                        parentPolicy.getName(),
                        methodName
                );
            }
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

        return instanceProperties;

    }

    /**
     * Handle the search for 'domain' by searching against 'parent_policy' of the object in IGC.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param operator the comparison operator to use
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search which is not supported
     * @throws RepositoryErrorException on any other error
     */
    @Override
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 PropertyComparisonOperator operator,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException, RepositoryErrorException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, operator, value);

        final String methodName = "addComplexPropertySearchCriteria";

        // Only need to add a condition of we are after the 'domain' and have been provided a String
        if (omrsPropertyName.equals("domain") && value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
            String domain = value.valueAsString();
            IGCSearchCondition condition = IGCRepositoryHelper.getRegexSearchCondition(
                    repositoryHelper,
                    repositoryName,
                    methodName,
                    "parent_policy.name",
                    operator,
                    domain
            );
            igcSearchConditionSet.addCondition(condition);
        }

    }

}
