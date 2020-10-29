/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Host;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionEndpointMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.PrimitivePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the mapping to the OMRS "Endpoint" entity.
 */
public class EndpointMapper extends ReferenceableMapper {

    private static final Logger log = LoggerFactory.getLogger(EndpointMapper.class);

    private static class Singleton {
        private static final EndpointMapper INSTANCE = new EndpointMapper();
    }
    public static EndpointMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected EndpointMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "host",
                "Host",
                "Endpoint"
        );

        // IGC 'host_(engine)' is equivalent, so we need to ensure that it is also added to the assets to be
        // handled by this mapper
        addOtherIGCAssetType("host_(engine)");

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");

        addLiteralPropertyMapping("protocol", null);
        addLiteralPropertyMapping("encryptionMethod", null);

        // This relationship can only be retrieved inverted
        // (relationship in IGC is cannot be traversed in other direction)
        addRelationshipMapper(ConnectionEndpointMapper.getInstance(null));

        addComplexIgcProperty("name");
        addComplexOmrsProperty("networkAddress");

    }

    /**
     * Retrieve the base host asset expected for the mapper from a host_(engine) asset.
     *
     * @param otherAssetType the type of the host_(engine) asset to translate into a host asset
     * @param otherAssetRid the RID of the host_(engine) asset to translate into a host asset
     * @param igcomrsRepositoryConnector connectivity to IGC repository
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return Reference - the host asset
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    public Reference getBaseIgcAssetFromAlternative(String otherAssetType,
                                                    String otherAssetRid,
                                                    IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    ObjectCache cache) throws RepositoryErrorException {
        final String methodName = "getBaseIgcAssetFromAlternative";
        if (otherAssetType.equals("host_(engine)")) {
            IGCSearchCondition igcSearchCondition = new IGCSearchCondition("_id", "=", otherAssetRid);
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
            IGCSearch igcSearch = new IGCSearch("host", igcSearchConditionSet);
            igcSearch.setPageSize(2);
            try {
                ItemList<Host> hosts = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);
                if (!hosts.getItems().isEmpty()) {
                    // As long as there is at least one result, return the first
                    return hosts.getItems().get(0);
                } else {
                    log.warn("Unable to translate host_(engine) to host, returning host_(engine).");
                    return super.getBaseIgcAssetFromAlternative(otherAssetType, otherAssetRid, igcomrsRepositoryConnector, cache);
                }
            } catch (IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
            return null;
        } else {
            log.debug("Not a host_(engine) asset, just returning as-is: {}", otherAssetType);
            return super.getBaseIgcAssetFromAlternative(otherAssetType, otherAssetRid, igcomrsRepositoryConnector, cache);
        }
    }

    /**
     * Implement any complex property mappings that cannot be simply mapped one-to-one.
     *
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    protected InstanceProperties complexPropertyMappings(ObjectCache cache,
                                                         EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) throws RepositoryErrorException {

        instanceProperties = super.complexPropertyMappings(cache, entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        Reference igcEntity = entityMap.getIgcEntity();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Map from name to networkAddress, without clobbering the simple name mapping
        try {
            String networkAddress = (String) igcRestClient.getPropertyByName(igcEntity, "name");
            instanceProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    instanceProperties,
                    "networkAddress",
                    networkAddress,
                    methodName
            );
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

        return instanceProperties;

    }

    /**
     * Handle the search for 'networkAddress' by searching against 'name' of the endpoint in IGC.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param operator the comparison operator to use
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     */
    @Override
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 PropertyComparisonOperator operator,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, operator, value);
        final String methodName = "addComplexPropertySearchCriteria";
        if (omrsPropertyName.equals("networkAddress")) {
            log.debug("Adding complex search criteria for: networkAddress");
            String networkAddress = value.valueAsString();
            IGCSearchCondition condition = IGCRepositoryHelper.getRegexSearchCondition(
                    repositoryHelper,
                    repositoryName,
                    methodName,
                    "name",
                    operator,
                    networkAddress
            );
            igcSearchConditionSet.addCondition(condition);
        }

    }

}
