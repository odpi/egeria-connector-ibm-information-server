/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ConnectionEndpointMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
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
        addRelationshipMapper(ConnectionEndpointMapper.getInstance());

        addComplexIgcProperty("name");
        addComplexOmrsProperty("networkAddress");

    }

    /**
     * Retrieve the base host asset expected for the mapper from a host_(engine) asset.
     *
     * @param otherAsset the host_(engine) asset to translate into a host asset
     * @param igcomrsRepositoryConnector connectivity to IGC repository
     * @return Reference - the host asset
     */
    @Override
    public Reference getBaseIgcAssetFromAlternative(Reference otherAsset,
                                                    IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        String otherAssetType = otherAsset.getType();
        if (otherAssetType.equals("host_(engine)")) {
            IGCSearchCondition igcSearchCondition = new IGCSearchCondition("_id", "=", otherAsset.getId());
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
            IGCSearch igcSearch = new IGCSearch("host", igcSearchConditionSet);
            igcSearch.setPageSize(2);
            ReferenceList hosts = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);
            if (!hosts.getItems().isEmpty()) {
                // As long as there is at least one result, return the first
                return hosts.getItems().get(0);
            } else {
                log.warn("Unable to translate host_(engine) to host, returning host_(engine): {}", otherAsset);
                return otherAsset;
            }
        } else {
            log.debug("Not a host_(engine) asset, just returning as-is: {}", otherAsset);
            return otherAsset;
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

        // Map from name to networkAddress, without clobbering the simple name mapping
        String networkAddress = (String) igcEntity.getPropertyByName("name");
        instanceProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                igcomrsRepositoryConnector.getRepositoryName(),
                instanceProperties,
                "networkAddress",
                networkAddress,
                methodName
        );

        return instanceProperties;

    }

}
