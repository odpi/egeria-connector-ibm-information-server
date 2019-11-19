/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Stage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.openmetadata.accessservices.dataengine.model.PortAlias;
import org.odpi.openmetadata.accessservices.dataengine.model.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Mappings for creating a set of PortAliases.
 */
class PortAliasMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(PortAliasMapping.class);

    private List<PortAlias> portAliases;

    private PortAliasMapping(IGCRestClient igcRestClient) {
        super(igcRestClient);
        portAliases = new ArrayList<>();
    }

    /**
     * Create a list of PortAliases from the provided job and stage information.
     *
     * @param job the job for which to create PortAliases
     * @param stages the stages from which to create PortAliases
     * @param relationshipProperty the relationship property on each stage from which to draw PortAlias details
     */
    PortAliasMapping(DataStageJob job, List<Stage> stages, String relationshipProperty) {

        this(job.getIgcRestClient());

        for (Stage stage : stages) {
            ItemList<InformationAsset> relations = (ItemList<InformationAsset>) igcRestClient.getPropertyByName(stage, relationshipProperty);
            relations.getAllPages(igcRestClient);
            for (InformationAsset relation : relations.getItems()) {
                String fullyQualifiedStoreName = job.getQualifiedNameFromStoreRid(relation.getId());
                String fullyQualifiedStageName = getFullyQualifiedName(stage);
                PortAlias portAlias = new PortAlias();
                portAlias.setQualifiedName(fullyQualifiedStageName);
                portAlias.setDisplayName(stage.getName());
                if (relationshipProperty.startsWith("reads")) {
                    portAlias.setPortType(PortType.INPUT_PORT);
                } else if (relationshipProperty.startsWith("writes")) {
                    portAlias.setPortType(PortType.OUTPUT_PORT);
                } else {
                    log.warn("Unknown port type: {}", relationshipProperty);
                }
                portAlias.setDelegatesTo(fullyQualifiedStoreName + fullyQualifiedStageName);
                portAliases.add(portAlias);
            }
        }

    }

    /**
     * Retrieve the PortAliases that were setup.
     *
     * @return {@code List<PortAlias>}
     */
    List<PortAlias> getPortAliases() { return portAliases; }

}
