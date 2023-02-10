/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.SchemaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Mappings for creating a SchemaType.
 */
public class SchemaTypeMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(SchemaTypeMapping.class);

    /**
     * Default constructor to pass in the cache for re-use.
     *
     * @param cache used by this mapping
     */
    public SchemaTypeMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Creates a SchemaType for the provided data store and field information.
     *
     * @param storeIdentity the store identity for which to create the schema type
     * @return SchemaType
     */
    public SchemaType getForDataStore(Identity storeIdentity) throws IGCException {
        SchemaType schemaType = null;
        if (storeIdentity != null) {
            schemaType = new SchemaType();
            String schemaTypeQN = getFullyQualifiedName(storeIdentity, null);
            if (schemaTypeQN != null) {
                log.debug("Constructing SchemaType for data store & fields: {}", schemaTypeQN);
                schemaType.setQualifiedName(schemaTypeQN);
                schemaType.setDisplayName(storeIdentity.getName());
                InformationAsset store = new InformationAsset();
                store.setId(storeIdentity.getRid());
                store.setType(storeIdentity.getAssetType());
                store.setName(storeIdentity.getName());
                List<Classificationenabledgroup> fields = cache.getFieldsForStore(store);
                AttributeMapping attributeMapping = new AttributeMapping(cache);
                schemaType.setAttributeList(attributeMapping.getForDataStoreFields(fields));
            } else {
                log.error("Unable to determine identity of store: {}", storeIdentity);
            }
        }
        return schemaType;
    }

    /**
     * Creates a SchemaType for the provided job and link information.
     *
     * @param link the link from which to retrieve stage columns for the SchemaType's attributes
     * @param job the job for which to create the SchemaType
     * @param fullyQualifiedStageName to ensure each attribute is unique
     * @return SchemaType
     */
    SchemaType getForLink(Link link, DataStageJob job, String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForLink";
        SchemaType schemaType = null;
        if (link != null) {
            schemaType = new SchemaType();

            String schemaTypeQN = getFullyQualifiedName(link, fullyQualifiedStageName);
            if (schemaTypeQN != null) {
                log.debug("Constructing SchemaType for job & link: {}", schemaTypeQN);
                schemaType.setQualifiedName(schemaTypeQN + "::(schema)=" + link.getId());
                schemaType.setDisplayName(link.getId());
                schemaType.setAuthor(link.getModifiedBy());
                AttributeMapping attributeMapping = new AttributeMapping(cache);
                schemaType.setAttributeList(attributeMapping.getForLink(link, job, fullyQualifiedStageName));
            } else {
                log.error("Unable to determine identity of link: {}", link);
            }

        }
        return schemaType;
    }

    /**
     * Creates a SchemaType for the provided data store field information, for the provided stage.
     *
     * @param fields the fields from the data store to use in creating the SchemaType
     * @param storeIdentity the store identity for which to create the SchemaType
     * @param stage the stage for which to create the SchemaType
     * @param fullyQualifiedStageName the fully-qualified name of the stage
     * @return SchemaType
     */
    SchemaType getForDataStoreFields(List<Classificationenabledgroup> fields, Identity storeIdentity, Stage stage, String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForDataStoreFields";
        SchemaType schemaType = null;
        if (stage != null) {
            schemaType = new SchemaType();
            String schemaTypeQN = getFullyQualifiedName(storeIdentity, fullyQualifiedStageName);
            if (schemaTypeQN != null) {
                log.debug("Constructing SchemaType for store & fields, within stage: {}", schemaTypeQN);
                schemaType.setQualifiedName(schemaTypeQN);
                schemaType.setDisplayName(storeIdentity.getName());
                schemaType.setAuthor((String) igcRestClient.getPropertyByName(stage, "modified_by"));
                AttributeMapping attributeMapping = new AttributeMapping(cache);
                schemaType.setAttributeList(attributeMapping.getForDataStoreFields(fields, fullyQualifiedStageName));
            } else {
                log.error("Unable to determine identity of store: {}", storeIdentity);
            }
        }
        return schemaType;
    }

    /**
     * Creates a SchemaType for the provided list of stage variables, for the provided stage.
     *
     * @param stageVariables the stage variables to include as attributes in the SchemaType
     * @param job the job for which to create the Attributes
     * @param stage the stage for which to create the SchemaType
     * @param fullyQualifiedStageName the fully-qualified name of the stage
     * @return SchemaType
     */
    SchemaType getForStageVariables(List<StageVariable> stageVariables, DataStageJob job, Stage stage, String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForStageVariables";
        SchemaType schemaType = null;
        if (stage != null) {
            schemaType = new SchemaType();
            log.debug("Constructing SchemaType for stage variables of: {}", fullyQualifiedStageName);
            schemaType.setQualifiedName(fullyQualifiedStageName);
            schemaType.setDisplayName(stage.getName());

            schemaType.setAuthor((String) igcRestClient.getPropertyByName(stage, "modified_by"));
            AttributeMapping attributeMapping = new AttributeMapping(cache);
            schemaType.setAttributeList(attributeMapping.getForStageVariables(stageVariables, job, fullyQualifiedStageName));

        }
        return schemaType;
    }

}
