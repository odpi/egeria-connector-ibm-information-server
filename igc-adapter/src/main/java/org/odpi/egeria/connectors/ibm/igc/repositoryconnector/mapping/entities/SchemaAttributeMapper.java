/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassAssignmentMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.PrimitivePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

/**
 * Defines the common mappings to the OMRS "SchemaAttribute" entity.
 */
public class SchemaAttributeMapper extends SchemaElementMapper {

    private static class Singleton {
        private static final SchemaAttributeMapper INSTANCE = new SchemaAttributeMapper(
                SUPERTYPE_SENTINEL,
                SUPERTYPE_SENTINEL,
                "SchemaAttribute"
        );
    }
    public static SchemaAttributeMapper getInstance(IGCVersionEnum version) {
        return SchemaAttributeMapper.Singleton.INSTANCE;
    }

    protected SchemaAttributeMapper(String igcAssetTypeName,
                                    String igcAssetTypeDisplayName,
                                    String omrsEntityTypeName) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                null
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("position", "position");
        addSimplePropertyMapping("type", "nativeClass");
        addSimplePropertyMapping("default_value", "defaultValueOverride");
        addSimplePropertyMapping("minimum_length", "minimumLength");
        addSimplePropertyMapping("length", "length");
        addSimplePropertyMapping("fraction", "significantDigits");
        addSimplePropertyMapping("allows_null_values", "isNullable");
        addLiteralPropertyMapping("maxCardinality", 1);
        addLiteralPropertyMapping("aliases", null);
        addLiteralPropertyMapping("sortOrder", null);
        addLiteralPropertyMapping("orderedValues", null);

        addComplexIgcProperty("allows_null_values");
        addComplexOmrsProperty("minCardinality");
        addComplexIgcProperty("unique");
        addComplexOmrsProperty("allowsDuplicateValues");

        // Deprecated / moved properties will be null'd
        addLiteralPropertyMapping("name", null);
        addLiteralPropertyMapping("cardinality", null);

        // The list of relationships that should be mapped
        addRelationshipMapper(DataClassAssignmentMapper.getInstance(null));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected InstanceProperties complexPropertyMappings(ObjectCache cache,
                                                         EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        instanceProperties = super.complexPropertyMappings(cache, entityMap, instanceProperties);

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

        // setup the OMRS 'allowsDuplicateValues' property
        Boolean isUnique = (Boolean) igcRestClient.getPropertyByName(igcEntity, "unique");
        if (isUnique != null) {
            instanceProperties = repositoryHelper.addBooleanPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "allowsDuplicateValues",
                    !isUnique,
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
     * @param operator the comparison operator to use
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
                                                 PropertyComparisonOperator operator,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, operator, value);

        final String methodName = "addComplexPropertySearchCriteria";

        if (omrsPropertyName.equals("minCardinality")) {

            Object minObj = ((PrimitivePropertyValue) value).getPrimitiveValue();
            int minCardinality = (minObj == null) ? -1 : (Integer) minObj;
            boolean optional = minCardinality <= 0;

            IGCRepositoryHelper.validateNumericOperator(operator, value.getTypeName(), methodName);
            IGCSearchCondition condition = null;
            switch (operator) {
                case IS_NULL:
                    condition = new IGCSearchCondition(
                            "allows_null_values",
                            "isNull",
                            false
                    );
                    break;
                case NOT_NULL:
                    condition = new IGCSearchCondition(
                            "allows_null_values",
                            "isNull",
                            true
                    );
                    break;
                case EQ:
                case NEQ:
                    condition = new IGCSearchCondition(
                            "allows_null_values",
                            "=",
                            optional ? "true" : "false"
                    );
                    break;
                case GTE:
                    condition = new IGCSearchCondition(
                            "allows_null_values",
                            "=",
                            (minCardinality >= 1) ? "false" : "true"
                    );
                    break;
                case GT:
                    condition = new IGCSearchCondition(
                            "allows_null_values",
                            "=",
                            (minCardinality > 0) ? "false" : "true"
                    );
                    break;
                case LTE:
                    condition = new IGCSearchCondition(
                            "allows_null_values",
                            "=",
                            (minCardinality <= 0) ? "true" : "false"
                    );
                    break;
                case LT:
                    condition = new IGCSearchCondition(
                            "allows_null_values",
                            "=",
                            (minCardinality < 1) ? "true" : "false"
                    );
                    break;
                default:
                    // Do nothing...
                    break;
            }
            if (condition != null) {
                igcSearchConditionSet.addCondition(condition);
            }

        } else if (omrsPropertyName.equals("allowsDuplicateValues")) {

            boolean allowsDuplicates = Boolean.parseBoolean(value.valueAsString());

            IGCRepositoryHelper.validateBooleanOperator(operator, methodName);
            IGCSearchCondition condition = null;
            switch (operator) {
                case IS_NULL:
                    condition = new IGCSearchCondition(
                            "unique",
                            "isNull",
                            false
                    );
                    break;
                case NOT_NULL:
                    condition = new IGCSearchCondition(
                            "unique",
                            "isNull",
                            true
                    );
                    break;
                case EQ:
                    condition = new IGCSearchCondition(
                            "unique",
                            "=",
                            allowsDuplicates ? "false" : "true"
                    );
                    break;
                case NEQ:
                    condition = new IGCSearchCondition(
                            "unique",
                            "=",
                            allowsDuplicates ? "true" : "false"
                    );
                    break;
                default:
                    // Do nothing...
                    break;
            }
            if (condition != null) {
                igcSearchConditionSet.addCondition(condition);
            }

        }

    }

}
