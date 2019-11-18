/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassAssignmentMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.PrimitivePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

import java.util.List;

/**
 * Defines the common mappings to the OMRS "SchemaAttribute" entity.
 */
public class SchemaAttribute_Mapper extends SchemaElement_Mapper {

    private static class Singleton {
        private static final SchemaAttribute_Mapper INSTANCE = new SchemaAttribute_Mapper();
    }
    public static SchemaAttribute_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private SchemaAttribute_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "SchemaAttribute",
                null
        );

    }

    protected SchemaAttribute_Mapper(String igcAssetTypeName,
                                     String igcAssetTypeDisplayName,
                                     String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                null
        );

        // The list of properties that should be mapped
        addLiteralPropertyMapping("maxCardinality", 1);
        addComplexIgcProperty("allows_null_values");
        addComplexOmrsProperty("minCardinality");

        // Deprecated / moved properties will be null'd
        addLiteralPropertyMapping("name", null);

        // The list of relationships that should be mapped
        addRelationshipMapper(DataClassAssignmentMapper.getInstance(null));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected InstanceProperties complexPropertyMappings(EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        instanceProperties = super.complexPropertyMappings(entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        Reference igcEntity = entityMap.getIgcEntity();

        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // setup the OMRS 'minCardinality' property
        Boolean allowsNulls = (Boolean) igcRestClient.getPropertyByName(igcEntity, "allows_null_values");
        if (allowsNulls != null) {
            instanceProperties = repositoryHelper.addIntPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "minCardinality",
                    allowsNulls ? 0 : 1,
                    methodName
            );
        }

        return instanceProperties;

    }

    /**
     * Handle the search for 'minCardinality' by searching against 'allows_null_values' of the object in IGC.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search which is not supported
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

        if (omrsPropertyName.equals("minCardinality")) {

            Object minCardinality = ((PrimitivePropertyValue) value).getPrimitiveValue();
            boolean optional = (minCardinality == null || ((Integer) minCardinality) <= 0);

            IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                    "allows_null_values",
                    "=",
                    optional ? "true" : "false"
            );
            igcSearchConditionSet.addCondition(igcSearchCondition);

        }

    }

}
