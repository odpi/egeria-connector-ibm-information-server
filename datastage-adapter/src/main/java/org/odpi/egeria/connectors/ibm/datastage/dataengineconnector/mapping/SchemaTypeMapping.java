/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DSJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.openmetadata.accessservices.dataengine.model.SchemaType;

import java.util.List;

/**
 * Mappings for creating a SchemaType.
 */
public class SchemaTypeMapping extends BaseMapping {

    private SchemaType schemaType;

    /**
     * Creates a SchemaType for the provided data store and field information.
     *
     * @param job the job for which to create the SchemaType
     * @param storeIdentity the store identity for which to create the schema type
     * @param fields the fields that should be created for the store
     */
    public SchemaTypeMapping(DSJob job, Identity storeIdentity, List<Reference> fields) {
        super(job.getIgcRestClient());
        schemaType = null;
        if (storeIdentity != null) {
            schemaType = new SchemaType();
            schemaType.setQualifiedName(storeIdentity.toString());
            schemaType.setDisplayName(storeIdentity.getName());
            AttributeMapping attributeMapping = new AttributeMapping(job, fields);
            schemaType.setAttributeList(attributeMapping.getAttributes());
        }
    }

    /**
     * Creates a SchemaType for the provided job and link information.
     *
     * @param job the job for which to create the SchemaType
     * @param link the link from which to retrieve stage columns for the SchemaType's attributes
     * @param stageNameSuffix the unique suffix (based on the stage name) to ensure each attribute is unique
     */
    SchemaTypeMapping(DSJob job, Reference link, String stageNameSuffix) {
        super(job.getIgcRestClient());
        schemaType = null;
        if (link != null) {
            schemaType = new SchemaType();
            schemaType.setQualifiedName(link.getIdentity(igcRestClient).toString() + stageNameSuffix);
            schemaType.setDisplayName(link.getId());
            schemaType.setAuthor((String)igcRestClient.getPropertyByName(link, "modified_by"));
            AttributeMapping attributeMapping = new AttributeMapping(job, link, stageNameSuffix);
            schemaType.setAttributeList(attributeMapping.getAttributes());
        }
    }

    /**
     * Creates a SchemaType for the provided job and data store field information, for the provided stage.
     *
     * @param job the job for which to create the SchemaType
     * @param stage the stage for which to create the SchemaType
     * @param storeName the name of the data store from which the fields come
     * @param storeQualifiedName the fully-qualified name of the data store
     * @param fields the fields from the data store to use in creating the SchemaType
     * @param fullyQualifiedStageName the fully-qualified name of the stage
     */
    SchemaTypeMapping(DSJob job, Reference stage, String storeName, String storeQualifiedName, List<Reference> fields, String fullyQualifiedStageName) {
        super(job.getIgcRestClient());
        schemaType = null;
        if (stage != null) {
            schemaType = new SchemaType();
            schemaType.setQualifiedName(storeQualifiedName + fullyQualifiedStageName);
            schemaType.setDisplayName(storeName);
            schemaType.setAuthor((String)igcRestClient.getPropertyByName(stage, "modified_by"));
            AttributeMapping attributeMapping = new AttributeMapping(job, fields, fullyQualifiedStageName);
            schemaType.setAttributeList(attributeMapping.getAttributes());
        }
    }

    /**
     * Retrieve the SchemaType that was setup.
     *
     * @return SchemaType
     */
    public SchemaType getSchemaType() { return schemaType; }

}
