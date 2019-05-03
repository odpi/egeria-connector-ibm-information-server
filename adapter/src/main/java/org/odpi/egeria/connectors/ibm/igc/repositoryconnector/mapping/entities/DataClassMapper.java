/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassHierarchyMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Defines the mapping to the OMRS "DataClass" entity.
 */
public class DataClassMapper extends ReferenceableMapper {

    private static final Logger log = LoggerFactory.getLogger(DataClassMapper.class);

    private static class SingletonOld {
        private static final DataClassMapper INSTANCE = new DataClassMapper(IGCVersionEnum.V11501);
    }
    private static class SingletonNew {
        private static final DataClassMapper INSTANCE = new DataClassMapper(IGCVersionEnum.V11702);
    }
    public static DataClassMapper getInstance(IGCVersionEnum version) {
        if (version.isEqualTo(IGCVersionEnum.V11702) || version.isHigherThan(IGCVersionEnum.V11702)) {
            return SingletonNew.INSTANCE;
        } else {
            return SingletonOld.INSTANCE;
        }
    }

    private DataClassMapper(IGCVersionEnum version) {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_class",
                "Data Class",
                "DataClass"
        );

        // IGC 'data_class' is one of the few objects with a relationship-specific asset type associated,
        // so we need to ensure that is also added to the assets to be handled by this mapper
        addOtherIGCAssetType("classification");

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");
        addSimplePropertyMapping("class_code", "classCode");
        addSimplePropertyMapping("default_threshold", "defaultThreshold");
        addSimplePropertyMapping("example", "example");

        // These properties need complex mappings, handled by 'complexPropertyMappings' method below
        addComplexIgcProperty("data_type_filter_elements_enum");
        addComplexIgcProperty("data_class_type_single");
        addComplexIgcProperty("java_class_name_single");
        addComplexIgcProperty("regular_expression_single");
        addComplexIgcProperty("valid_value_strings");
        addComplexIgcProperty("validValueReferenceFile");
        addComplexOmrsProperty("dataType");
        addComplexOmrsProperty("specificationDetails");
        addComplexOmrsProperty("specification");

        // Further expand the complex properties if we're on v11.7.0.2+ (and these are then available)
        if (version.isEqualTo(IGCVersionEnum.V11702) || version.isHigherThan(IGCVersionEnum.V11702)) {
            addComplexIgcProperty("expression");
            addComplexIgcProperty("script");
            addComplexIgcProperty("provider");
            addComplexIgcProperty("filters");
            addComplexOmrsProperty("userDefined");
        }

        // The list of relationships that should be mapped
        addRelationshipMapper(DataClassHierarchyMapper.getInstance());
        addRelationshipMapper(DataClassAssignmentMapper.getInstance());

    }

    /**
     * Retrieve the base data_class asset expected for the mapper from a classification asset.
     *
     * @param otherAsset the classification asset to translate into a data_class asset
     * @param igcomrsRepositoryConnector connectivity to IGC repository
     * @return Reference - the data_class asset
     */
    @Override
    public Reference getBaseIgcAssetFromAlternative(Reference otherAsset,
                                                    IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        return DataClassAssignmentMapper.getInstance().getProxyTwoAssetFromAsset(
                otherAsset, igcomrsRepositoryConnector.getIGCRestClient()).get(0);
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

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        Reference igcEntity = entityMap.getIgcEntity();

        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        /*
         * setup the OMRS 'dataType' property
         */
        // There can be multiple data types defined on an IGC data class...
        ArrayList<String> dataTypes = (ArrayList<String>) igcEntity.getPropertyByName("data_type_filter_elements_enum");
        String dataType = null;
        for (String type : dataTypes) {
            // We'll take the first dataType we find to start with...
            if (dataType == null) {
                dataType = type;
            } else if (type.equals("string") || !type.equals(dataType)) {
                // But if we find any others, or we find string, we can safely set to "string"
                // as a catch-all and then short-circuit
                dataType = "string";
                break;
            }
        }

        instanceProperties = repositoryHelper.addStringPropertyToInstance(
                repositoryName,
                instanceProperties,
                "dataType",
                dataType,
                methodName
        );

        /*
         * setup the OMRS 'specificationDetails' property
         */
        String dataClassType = (String) igcEntity.getPropertyByName("data_class_type_single");
        instanceProperties = repositoryHelper.addStringPropertyToInstance(
                repositoryName,
                instanceProperties,
                "specificationDetails",
                dataClassType,
                methodName
        );

        /*
         * setup the OMRS 'specification' property
         */
        // There are many different flavours of IGC data classes, so the expression used can vary widely...
        String dataClassDetails = "";
        switch(dataClassType) {
            case "Regex":
                dataClassDetails = (String) igcEntity.getPropertyByName("regular_expression_single");
                break;
            case "ValidValues":
                ArrayList<String> validValues = (ArrayList<String>) igcEntity.getPropertyByName("valid_value_strings");
                if (validValues == null || validValues.isEmpty()) {
                    dataClassDetails = (String) igcEntity.getPropertyByName("validValueReferenceFile");
                } else {
                    dataClassDetails = String.join(", ", validValues);
                }
                break;
            case "Script":
                dataClassDetails = (String) igcEntity.getPropertyByName("script");
                break;
            case "ColumnSimilarity":
                dataClassDetails = (String) igcEntity.getPropertyByName("expression");
                break;
            case "UnstructuredFilter":
                ReferenceList filters = (ReferenceList) igcEntity.getPropertyByName("filters");
                if (!filters.getItems().isEmpty()) {
                    filters.getAllPages(igcomrsRepositoryConnector.getIGCRestClient());
                    ArrayList<String> filterNames = new ArrayList<>();
                    for (Reference filter : filters.getItems()) {
                        filterNames.add(filter.getName());
                    }
                    dataClassDetails = String.join(", ", filterNames);
                }
                break;
            default:
                dataClassDetails = (String) igcEntity.getPropertyByName("java_class_name_single");
                break;
        }
        instanceProperties = repositoryHelper.addStringPropertyToInstance(
                repositoryName,
                instanceProperties,
                "specification",
                dataClassDetails,
                methodName
        );

        /*
         * setup the OMRS 'userDefined' property
         * Provider = 'IBM' is only present in v11.7+ to be able to make this determination
         */
        IGCVersionEnum igcVersion = igcomrsRepositoryConnector.getIGCVersion();
        if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
            String provider = (String) igcEntity.getPropertyByName("provider");
            instanceProperties = repositoryHelper.addBooleanPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "userDefined",
                    (provider == null || !provider.equals("IBM")),
                    methodName
            );
        }

        return instanceProperties;

    }

}
