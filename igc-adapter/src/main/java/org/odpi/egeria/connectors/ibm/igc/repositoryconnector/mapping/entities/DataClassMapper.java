/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DataClass;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Filter;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassHierarchyMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines the mapping to the OMRS "DataClass" entity.
 */
public class DataClassMapper extends ReferenceableMapper {

    private static final Logger log = LoggerFactory.getLogger(DataClassMapper.class);

    private static final String VALUE_DELIMITER = "__,__";

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

    protected DataClassMapper(IGCVersionEnum version) {

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
        } else {
            addLiteralPropertyMapping("userDefined", null);
        }

        addLiteralPropertyMapping("namespace", null);

        // The list of relationships that should be mapped
        addRelationshipMapper(DataClassHierarchyMapper.getInstance(version));
        addRelationshipMapper(DataClassAssignmentMapper.getInstance(version));

    }

    /**
     * Retrieve the base data_class asset expected for the mapper from a classification asset.
     *
     * @param igcAssetType the type of the classification asset to translate into a data_class asset
     * @param igcRid the RID of the classification asset to translate into a data_class asset
     * @param igcomrsRepositoryConnector connectivity to IGC repository
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return Reference - the data_class asset
     */
    @Override
    public Reference getBaseIgcAssetFromAlternative(String igcAssetType,
                                                    String igcRid,
                                                    IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    ObjectCache cache) {
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        if (igcAssetType.equals("classification")) {
            // In some versions it is not possible to search for 'classification' assets, so generally will be safer
            // to retrieve the entire object by ID (they are small objects anyway so hopefully no significant negative
            // performance impact of doing so)
            return DataClassAssignmentMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()).getProxyTwoAssetFromAsset(
                    igcRestClient.getAssetById(igcRid, cache), igcRestClient, cache).get(0);
        } else {
            return igcRestClient.getAssetWithSubsetOfProperties(igcRid, igcAssetType, igcRestClient.getAllPropertiesForType(igcAssetType));
        }
    }

    /**
     * Implement any complex property mappings that cannot be simply mapped one-to-one.
     *
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     */
    @Override
    protected InstanceProperties complexPropertyMappings(ObjectCache cache,
                                                         EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        instanceProperties = super.complexPropertyMappings(cache, entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        Reference igcEntity = entityMap.getIgcEntity();

        if (igcEntity instanceof DataClass) {

            DataClass dataClass = (DataClass) igcEntity;
            OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
            String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

            /*
             * setup the OMRS 'dataType' property
             */
            // There can be multiple data types defined on an IGC data class...
            List<String> dataTypes = dataClass.getDataTypeFilterElementsEnum();
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
            String dataClassType = dataClass.getDataClassTypeSingle();
            instanceProperties = repositoryHelper.addStringPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "specification",
                    dataClassType,
                    methodName
            );

            /*
             * setup the OMRS 'specification' property
             */
            // There are many different flavours of IGC data classes, so the expression used can vary widely...
            String dataClassDetails = "";
            switch (dataClassType) {
                case "Regex":
                    dataClassDetails = dataClass.getRegularExpressionSingle();
                    break;
                case "ValidValues":
                    List<String> validValues = dataClass.getValidValueStrings();
                    if (validValues == null || validValues.isEmpty()) {
                        dataClassDetails = dataClass.getValidvaluereferencefile();
                    } else {
                        dataClassDetails = String.join(VALUE_DELIMITER, validValues);
                    }
                    break;
                case "Script":
                    dataClassDetails = dataClass.getScript();
                    break;
                case "ColumnSimilarity":
                    dataClassDetails = dataClass.getExpression();
                    break;
                case "UnstructuredFilter":
                    ItemList<Filter> filters = dataClass.getFilters();
                    if (!filters.getItems().isEmpty()) {
                        List<Filter> allFilters = igcomrsRepositoryConnector.getIGCRestClient().getAllPages("filters", filters);
                        ArrayList<String> filterNames = new ArrayList<>();
                        for (Filter filter : allFilters) {
                            filterNames.add(filter.getName());
                        }
                        dataClassDetails = String.join(VALUE_DELIMITER, filterNames);
                    }
                    break;
                default:
                    dataClassDetails = dataClass.getJavaClassNameSingle();
                    break;
            }
            instanceProperties = repositoryHelper.addStringPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "specificationDetails",
                    dataClassDetails,
                    methodName
            );

            /*
             * setup the OMRS 'userDefined' property
             * Provider = 'IBM' is only present in v11.7+ to be able to make this determination
             */
            IGCVersionEnum igcVersion = igcomrsRepositoryConnector.getIGCVersion();
            if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
                String provider = dataClass.getProvider();
                instanceProperties = repositoryHelper.addBooleanPropertyToInstance(
                        repositoryName,
                        instanceProperties,
                        "userDefined",
                        (provider == null || !provider.equals("IBM")),
                        methodName
                );
            }

        }
        return instanceProperties;

    }

    /**
     * Handle the search for 'networkAddress' by searching against 'name' of the endpoint in IGC.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param operator the comparison operator to use
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is provided for the search that is not supported
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

        if (getComplexMappedOmrsProperties().contains(omrsPropertyName)) {

            log.debug("Adding complex search criteria for: {}", omrsPropertyName);

            IGCVersionEnum igcVersion = igcRestClient.getIgcVersion();
            String omrsValue = value.valueAsString();

            // Will be used to handle any simple string properties further below -- the more complicated properties
            // are handled directly in the switch statement
            String igcPropertyToSearch = null;
            switch (omrsPropertyName) {
                case "dataType":
                    igcPropertyToSearch = "data_type_filter_elements_enum";
                    break;
                case "specificationDetails":
                    IGCSearchConditionSet complexCriteria = new IGCSearchConditionSet();

                    IGCSearchConditionSet asRegex = new IGCSearchConditionSet();
                    IGCSearchCondition byRegex = new IGCSearchCondition("data_class_type_single", "=", "Regex");
                    IGCSearchCondition withRegex = IGCRepositoryHelper.getRegexSearchCondition(
                            repositoryHelper,
                            repositoryName,
                            methodName,
                            "regular_expression_single",
                            omrsValue
                    );
                    asRegex.addCondition(byRegex);
                    asRegex.addCondition(withRegex);
                    asRegex.setMatchAnyCondition(false);
                    if (operator.equals(PropertyComparisonOperator.NEQ)) {
                        asRegex.setNegateAll(true);
                    }

                    IGCSearchConditionSet asValidValues = new IGCSearchConditionSet();
                    IGCSearchCondition byValidValues = new IGCSearchCondition("data_class_type_single", "=", "ValidValues");
                    IGCSearchCondition withValidValues;
                    if (omrsValue.contains(VALUE_DELIMITER)) {
                        // This may not be all that accurate since we strip the regular expression, do an 'in' search
                        // and also assume that there are at least 2 valid values in any given data class for this
                        // value delimiter to be present
                        String unqualifiedValue = repositoryHelper.getUnqualifiedLiteralString(omrsValue);
                        String[] valueList = unqualifiedValue.split(VALUE_DELIMITER);
                        withValidValues = new IGCSearchCondition("valid_value_strings", Arrays.asList(valueList));
                    } else {
                        withValidValues = IGCRepositoryHelper.getRegexSearchCondition(
                                repositoryHelper,
                                repositoryName,
                                methodName,
                                "validValueReferenceFile",
                                omrsValue
                        );
                    }
                    asValidValues.addCondition(byValidValues);
                    asValidValues.addCondition(withValidValues);
                    asValidValues.setMatchAnyCondition(false);
                    if (operator.equals(PropertyComparisonOperator.NEQ)) {
                        asValidValues.setNegateAll(true);
                    }

                    if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
                        IGCSearchConditionSet asScript = new IGCSearchConditionSet();
                        IGCSearchCondition byScript = new IGCSearchCondition("data_class_type_single", "=", "Script");
                        IGCSearchCondition withScript = IGCRepositoryHelper.getRegexSearchCondition(
                                repositoryHelper,
                                repositoryName,
                                methodName,
                                "script",
                                omrsValue
                        );
                        asScript.addCondition(byScript);
                        asScript.addCondition(withScript);
                        asScript.setMatchAnyCondition(false);
                        if (operator.equals(PropertyComparisonOperator.NEQ)) {
                            asScript.setNegateAll(true);
                        }

                        IGCSearchConditionSet asColumnSimilarity = new IGCSearchConditionSet();
                        IGCSearchCondition byColumnSimilarity = new IGCSearchCondition("data_class_type_single", "=", "ColumnSimilarity");
                        IGCSearchCondition withColumnSimilarity = IGCRepositoryHelper.getRegexSearchCondition(
                                repositoryHelper,
                                repositoryName,
                                methodName,
                                "expression",
                                omrsValue
                        );
                        asColumnSimilarity.addCondition(byColumnSimilarity);
                        asColumnSimilarity.addCondition(withColumnSimilarity);
                        asColumnSimilarity.setMatchAnyCondition(false);
                        if (operator.equals(PropertyComparisonOperator.NEQ)) {
                            asColumnSimilarity.setNegateAll(true);
                        }

                        IGCSearchConditionSet asUnstructuredFilter = new IGCSearchConditionSet();
                        IGCSearchCondition byUnstructuredFilter = new IGCSearchCondition("data_class_type_single", "=", "UnstructuredFilter");
                        // This may not be all that accurate since we strip the regular expression, do an 'in' search
                        // and also assume that there are at least 2 valid values in any given data class for this
                        // value delimiter to be present
                        String unqualifiedValue = repositoryHelper.getUnqualifiedLiteralString(omrsValue);
                        String[] filterList = unqualifiedValue.split(VALUE_DELIMITER);
                        IGCSearchCondition withUnstructuredFilter = new IGCSearchCondition("filters.name", Arrays.asList(filterList));
                        asUnstructuredFilter.addCondition(byUnstructuredFilter);
                        asUnstructuredFilter.addCondition(withUnstructuredFilter);
                        asUnstructuredFilter.setMatchAnyCondition(false);
                        if (operator.equals(PropertyComparisonOperator.NEQ)) {
                            asUnstructuredFilter.setNegateAll(true);
                        }

                        complexCriteria.addNestedConditionSet(asScript);
                        complexCriteria.addNestedConditionSet(asColumnSimilarity);
                        complexCriteria.addNestedConditionSet(asUnstructuredFilter);
                    }

                    IGCSearchConditionSet asJavaClass = new IGCSearchConditionSet();
                    IGCSearchCondition withJavaClass = IGCRepositoryHelper.getRegexSearchCondition(
                            repositoryHelper,
                            repositoryName,
                            methodName,
                            "java_class_name_single",
                            omrsValue
                    );
                    asJavaClass.addCondition(withJavaClass);
                    asJavaClass.setMatchAnyCondition(false);
                    if (operator.equals(PropertyComparisonOperator.NEQ)) {
                        asJavaClass.setNegateAll(true);
                    }

                    complexCriteria.addNestedConditionSet(asRegex);
                    complexCriteria.addNestedConditionSet(asValidValues);
                    complexCriteria.addNestedConditionSet(asJavaClass);
                    complexCriteria.setMatchAnyCondition(true);

                    igcSearchConditionSet.addNestedConditionSet(complexCriteria);
                    break;
                case "specification":
                    igcPropertyToSearch = "data_class_type_single";
                    break;
                case "userDefined":
                    if (igcVersion.isEqualTo(IGCVersionEnum.V11702) || igcVersion.isHigherThan(IGCVersionEnum.V11702)) {
                        boolean isUserDefined = Boolean.parseBoolean(omrsValue);
                        IGCRepositoryHelper.validateBooleanOperator(operator, methodName);
                        IGCSearchCondition igcSearchCondition = null;
                        switch (operator) {
                            case IS_NULL:
                                igcSearchCondition = new IGCSearchCondition(
                                        "provider",
                                        "isNull",
                                        false
                                );
                                break;
                            case NOT_NULL:
                                igcSearchCondition = new IGCSearchCondition(
                                        "provider",
                                        "isNull",
                                        true
                                );
                                break;
                            case EQ:
                                igcSearchCondition = new IGCSearchCondition(
                                        "provider",
                                        isUserDefined ? "<>" : "=",
                                        "IBM"
                                );
                                break;
                            case NEQ:
                                igcSearchCondition = new IGCSearchCondition(
                                        "provider",
                                        isUserDefined ? "=" : "<>",
                                        "IBM"
                                );
                                break;
                            default:
                                // Do nothing...
                                break;
                        }
                        if (igcSearchCondition != null) {
                            igcSearchConditionSet.addCondition(igcSearchCondition);
                        }
                    }
                    break;
            }

            // Handle any simple string properties
            if (igcPropertyToSearch != null) {
                igcSearchConditionSet.addCondition(
                        IGCRepositoryHelper.getRegexSearchCondition(
                                repositoryHelper,
                                repositoryName,
                                methodName,
                                igcPropertyToSearch,
                                operator,
                                omrsValue
                        )
                );
            }

        }
        // If it is a property that is not complex-mapped, it's inclusion (or not) should already be handled by other
        // methods

    }

}
