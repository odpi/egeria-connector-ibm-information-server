/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnector;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classificationenabledgroup;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Link;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Stage;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.StageVariable;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.PortImplementation;
import org.odpi.openmetadata.accessservices.dataengine.model.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Mappings for creating a PortImplementation.
 */
class PortImplementationMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(PortImplementationMapping.class);
    private PortImplementation portImplementation;

    /**
     * Creates a PortImplementation for the provided job and link information.
     *
     * @param cache used by this mapping
     * @param job the job for which to create the PortImplementation
     * @param link the link containing details for the PortImplementation
     * @param portType the port type to use
     * @param fullyQualifiedStageName to ensure each schema is unique
     */
    PortImplementationMapping(DataStageCache cache, DataStageJob job, Link link, PortType portType, String fullyQualifiedStageName) {
        super(cache);
        final String methodName = "PortImplementationMapping";
        portImplementation = null;
        if (link != null) {
            portImplementation = new PortImplementation();
            try {
                String linkQN = getFullyQualifiedName(link, fullyQualifiedStageName);
                if (linkQN != null) {
                    log.debug("Constructing PortImplementation for: {}", linkQN);
                    portImplementation.setQualifiedName(linkQN);
                    portImplementation.setDisplayName(link.getName());
                    portImplementation.setPortType(portType);
                    SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache, job, link, fullyQualifiedStageName);
                    portImplementation.setSchemaType(schemaTypeMapping.getSchemaType());
                } else {
                    log.error("Unable to determine identity for link -- not including: {}", link);
                }
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
    }

    /**
     * Creates a PortImplementation for the provided data store field information, for the given stage.
     *
     * @param cache used by this mapping
     * @param stage the stage for which to create the PortImplementation
     * @param portType the port type to use
     * @param fields the data store fields from which to create the PortImplementation's schema
     * @param fullyQualifiedStageName the qualified name of the stage to ensure each schema is unique
     */
    PortImplementationMapping(DataStageCache cache, Stage stage, PortType portType, List<Classificationenabledgroup> fields, String fullyQualifiedStageName) {
        super(cache);
        final String methodName = "PortImplementationMapping";
        portImplementation = null;
        if (stage != null && fields != null && !fields.isEmpty()) {
            portImplementation = new PortImplementation();
            try {
                Identity storeIdentity = getParentIdentity(fields.get(0));
                String storeName = getParentDisplayName(fields.get(0));
                portImplementation.setQualifiedName(getFullyQualifiedName(storeIdentity, fullyQualifiedStageName));
                portImplementation.setDisplayName(storeName);
                portImplementation.setPortType(portType);
                SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache, stage, storeIdentity, fields, fullyQualifiedStageName);
                portImplementation.setSchemaType(schemaTypeMapping.getSchemaType());
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
    }

    /**
     * Creates a PortImplementation for the provided stage variable information, for the given stage.
     *
     * @param cache used by this mapping
     * @param stage the stage for which to create the PortImplementation
     * @param stageVariables the stage variables to include in the PortImplementation
     * @param fullyQualifiedStageName the qualified name of the stage to ensure the schema is unique
     */
    PortImplementationMapping(DataStageCache cache, Stage stage, List<StageVariable> stageVariables, String fullyQualifiedStageName) {
        super(cache);
        final String methodName = "PortImplementationMapping";
        portImplementation = null;
        if (stage != null) {
            portImplementation = new PortImplementation();
            portImplementation.setQualifiedName(fullyQualifiedStageName);
            portImplementation.setDisplayName(stage.getName());
            portImplementation.setPortType(PortType.OTHER);
            SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache, stage, stageVariables, fullyQualifiedStageName);
            portImplementation.setSchemaType(schemaTypeMapping.getSchemaType());
        }
    }

    /**
     * Retrieve the PortImplementation that was setup.
     *
     * @return PortImplementation
     */
    PortImplementation getPortImplementation() { return portImplementation; }

}
