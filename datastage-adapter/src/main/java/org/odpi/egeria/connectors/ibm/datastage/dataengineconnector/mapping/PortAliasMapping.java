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

import java.util.ArrayList;
import java.util.List;

/**
 * Mappings for creating a set of PortAliases.
 */
class PortAliasMapping extends BaseMapping {

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
     * @param portType the type of port to map (input or output)
     */
    PortAliasMapping(DataStageJob job, List<Stage> stages, PortType portType) {

        this(job.getIgcRestClient());

        for (Stage stage : stages) {
            if (portType.equals(PortType.INPUT_PORT)) {
                addInputPortAliases(job, stage);
            } else if (portType.equals(PortType.OUTPUT_PORT)) {
                addOutputPortAliases(job, stage);
            }
        }

    }

    /**
     * Retrieve the PortAliases that were setup.
     *
     * @return {@code List<PortAlias>}
     */
    List<PortAlias> getPortAliases() { return portAliases; }

    private void addInputPortAliases(DataStageJob job, Stage stage) {
        addPortAliases(job, stage, stage.getReadsFromDesign(), PortType.INPUT_PORT);
    }

    private void addOutputPortAliases(DataStageJob job, Stage stage) {
        addPortAliases(job, stage, stage.getWritesToDesign(), PortType.OUTPUT_PORT);
    }

    private void addPortAliases(DataStageJob job, Stage stage, ItemList<InformationAsset> relations, PortType portType) {
        relations.getAllPages(igcRestClient);
        for (InformationAsset relation : relations.getItems()) {
            String fullyQualifiedStoreName = job.getQualifiedNameFromStoreRid(relation.getId());
            String fullyQualifiedStageName = getFullyQualifiedName(stage);
            PortAlias portAlias = new PortAlias();
            portAlias.setQualifiedName(fullyQualifiedStageName);
            portAlias.setDisplayName(stage.getName());
            portAlias.setPortType(portType);
            portAlias.setDelegatesTo(fullyQualifiedStoreName + fullyQualifiedStageName);
            portAliases.add(portAlias);
        }
    }

}
