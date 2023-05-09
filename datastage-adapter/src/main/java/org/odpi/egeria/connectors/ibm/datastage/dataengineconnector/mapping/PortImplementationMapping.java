/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

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
    public static final String LINK_PREFIX = "::(link)=";
    /**
     * Default constructor to pass in the cache for re-use.
     *
     * @param cache used by this mapping
     */
    PortImplementationMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Creates a PortImplementation for the provided job and link information.
     *
     * @param link the link containing details for the PortImplementation
     * @param job the job for which to create the PortImplementation
     * @param portType the port type to use
     * @param fullyQualifiedStageName to ensure each schema is unique
     * @return PortImplementation
     */
    PortImplementation getForLink(Link link, DataStageJob job, PortType portType, String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForLink";
        PortImplementation portImplementation = null;
        if (link != null) {
            portImplementation = new PortImplementation();

            String linkQN = getFullyQualifiedName(link, fullyQualifiedStageName);
            if (linkQN != null) {
                log.debug("Constructing PortImplementation for: {}", linkQN);
                portImplementation.setQualifiedName(linkQN + LINK_PREFIX + link.getName());
                portImplementation.setDisplayName(link.getName());
                portImplementation.setPortType(portType);
                SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache);
                portImplementation.setSchemaType(schemaTypeMapping.getForLink(link, job, fullyQualifiedStageName));
            } else {
                log.error("Unable to determine identity for link -- not including: {}", link);
            }
        }
        return portImplementation;
    }

    /**
     * Creates a PortImplementation for the provided data store field information, for the given stage.
     *
     * @param fields the data store fields from which to create the PortImplementation's schema
     * @param stage the stage for which to create the PortImplementation
     * @param portType the port type to use
     * @param fullyQualifiedStageName the qualified name of the stage to ensure each schema is unique
     * @return PortImplementation
     */
    PortImplementation getForDataStoreFields(List<Classificationenabledgroup> fields, Stage stage,
                                             PortType portType, String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForDataStoreFields";
        PortImplementation portImplementation = null;
        if (stage != null && fields != null && !fields.isEmpty()) {
            portImplementation = new PortImplementation();

            Identity storeIdentity = getParentIdentity(fields.get(0));
            String storeName = getParentDisplayName(fields.get(0));
            portImplementation.setQualifiedName(getFullyQualifiedName(storeIdentity, fullyQualifiedStageName) + LINK_PREFIX + storeName);
            portImplementation.setDisplayName(storeName);
            portImplementation.setPortType(portType);
            SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache);
            portImplementation.setSchemaType(schemaTypeMapping.getForDataStoreFields(fields, storeIdentity, stage, fullyQualifiedStageName));

        }
        return portImplementation;
    }

    /**
     * Creates a PortImplementation for the provided stage variable information, for the given stage.
     *
     * @param stageVariables the stage variables to include in the PortImplementation
     * @param job the job for which to create the Attributes
     * @param stage the stage for which to create the PortImplementation
     * @param fullyQualifiedStageName the qualified name of the stage to ensure the schema is unique
     * @return PortImplementation
     */
    PortImplementation getForStageVariables(List<StageVariable> stageVariables, DataStageJob job, Stage stage, String fullyQualifiedStageName) throws IGCException {
        PortImplementation portImplementation = null;
        if (stage != null) {
            portImplementation = new PortImplementation();
            portImplementation.setQualifiedName(fullyQualifiedStageName + LINK_PREFIX + stage.getName());
            portImplementation.setDisplayName(stage.getName());
            portImplementation.setPortType(PortType.OTHER);
            SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(cache);
            portImplementation.setSchemaType(schemaTypeMapping.getForStageVariables(stageVariables, job, stage, fullyQualifiedStageName));
        }
        return portImplementation;
    }

}
