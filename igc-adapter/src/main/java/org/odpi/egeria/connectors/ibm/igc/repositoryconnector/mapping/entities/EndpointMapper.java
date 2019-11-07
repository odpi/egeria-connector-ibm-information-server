/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Host;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionEndpointMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.PrimitivePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
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

    private EndpointMapper() {

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
     * @return Reference - the host asset
     */
    @Override
    public Reference getBaseIgcAssetFromAlternative(String otherAssetType,
                                                    String otherAssetRid,
                                                    IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        if (otherAssetType.equals("host_(engine)")) {
            IGCSearchCondition igcSearchCondition = new IGCSearchCondition("_id", "=", otherAssetRid);
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
            IGCSearch igcSearch = new IGCSearch("host", igcSearchConditionSet);
            igcSearch.setPageSize(2);
            ItemList<Host> hosts = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);
            if (!hosts.getItems().isEmpty()) {
                // As long as there is at least one result, return the first
                return hosts.getItems().get(0);
            } else {
                if (log.isWarnEnabled()) { log.warn("Unable to translate host_(engine) to host, returning host_(engine)."); }
                return super.getBaseIgcAssetFromAlternative(otherAssetType, otherAssetRid, igcomrsRepositoryConnector);
            }
        } else {
            if (log.isDebugEnabled()) { log.debug("Not a host_(engine) asset, just returning as-is: {}", otherAssetType); }
            return super.getBaseIgcAssetFromAlternative(otherAssetType, otherAssetRid, igcomrsRepositoryConnector);
        }
    }

    /**
     * Implement any complex property mappings that cannot be simply mapped one-to-one.
     *
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     */
    @Override
    protected InstanceProperties complexPropertyMappings(EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        instanceProperties = super.complexPropertyMappings(entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        Reference igcEntity = entityMap.getIgcEntity();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Map from name to networkAddress, without clobbering the simple name mapping
        String networkAddress = (String) igcRestClient.getPropertyByName(igcEntity, "name");
        instanceProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                igcomrsRepositoryConnector.getRepositoryName(),
                instanceProperties,
                "networkAddress",
                networkAddress,
                methodName
        );

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
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, value);

        final String methodName = "addComplexPropertySearchCriteria";

        if (omrsPropertyName.equals("networkAddress")) {

            IGCSearchCondition igcSearchCondition;
            String networkAddress = ((PrimitivePropertyValue) value).getPrimitiveValue().toString();
            String unqualifiedValue = repositoryHelper.getUnqualifiedLiteralString(networkAddress);
            if (repositoryHelper.isContainsRegex(networkAddress)) {
                igcSearchCondition = new IGCSearchCondition(
                        "name",
                        "like %{0}%",
                        unqualifiedValue
                );
                igcSearchConditionSet.addCondition(igcSearchCondition);
            } else if (repositoryHelper.isStartsWithRegex(networkAddress)) {
                igcSearchCondition = new IGCSearchCondition(
                        "name",
                        "like {0}%",
                        unqualifiedValue
                );
                igcSearchConditionSet.addCondition(igcSearchCondition);
            } else if (repositoryHelper.isEndsWithRegex(networkAddress)) {
                igcSearchCondition = new IGCSearchCondition(
                        "name",
                        "like %{0}",
                        unqualifiedValue
                );
                igcSearchConditionSet.addCondition(igcSearchCondition);
            } else if (repositoryHelper.isExactMatchRegex(networkAddress)) {
                igcSearchCondition = new IGCSearchCondition(
                        "name",
                        "=",
                        unqualifiedValue
                );
                igcSearchConditionSet.addCondition(igcSearchCondition);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        repositoryName,
                        networkAddress);
                throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                        EndpointMapper.class.getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }

        }

    }

}
