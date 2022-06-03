/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SemanticAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.OMRSRuntimeException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the mapping to the OMRS "SchemaElement" entity.
 */
public class SchemaElementMapper extends ReferenceableMapper {

    private static final Logger log = LoggerFactory.getLogger(SchemaElementMapper.class);

    private static class Singleton {
        private static final SchemaElementMapper INSTANCE = new SchemaElementMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "SchemaElement",
                null
        );
    }
    public static SchemaElementMapper getInstance(IGCVersionEnum version) {
        return SchemaElementMapper.Singleton.INSTANCE;
    }

    protected SchemaElementMapper(String igcAssetTypeName,
                                  String igcAssetTypeDisplayName,
                                  String omrsEntityTypeName,
                                  String prefix) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                prefix
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "displayName");
        addSimplePropertyMapping("short_description", "description");
        addLiteralPropertyMapping("isDeprecated", false);

        // Term relationship can be added to all subtypes
        addRelationshipMapper(SemanticAssignmentMapper.getInstance(null));

    }

    /**
     * Retrieve the property name for this IGC object type that refers to its parent object.
     * @return String
     */
    protected String getParentPropertyName() {
        String parentPropertyName = null;
        switch (getIgcAssetType()) {
            case "database_column":
                parentPropertyName = "database_table_or_view";
                break;
            case "database_schema":
                parentPropertyName = "database";
                break;
            case "database_table":
                parentPropertyName = "database_schema";
                break;
            case "data_file_field":
                parentPropertyName = "data_file_record";
                break;
            case "data_file_record":
                parentPropertyName = "data_file";
                break;
        }
        return parentPropertyName;
    }

}
