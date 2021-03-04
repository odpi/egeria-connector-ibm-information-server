/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AssetZoneMembershipMapper_DataFile;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_FileRecord;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.NestedFileMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;

/**
 * Defines the mapping to the OMRS "DataFile" entity.
 */
public class DataFileMapper extends DataStoreMapper {

    private static class Singleton {
        private static final DataFileMapper INSTANCE = new DataFileMapper();
    }
    public static DataFileMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected DataFileMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file",
                "Data File",
                "DataFile"
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("path", "pathName");
        addComplexOmrsProperty("fileType");

        // The list of relationships that should be mapped
        addRelationshipMapper(AssetSchemaTypeMapper_FileRecord.getInstance(null));
        addRelationshipMapper(NestedFileMapper.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(AssetZoneMembershipMapper_DataFile.getInstance(null));

    }

    /**
     * Implement any complex property mappings that cannot be simply mapped one-to-one.
     *
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    protected InstanceProperties complexPropertyMappings(ObjectCache cache,
                                                         EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) throws RepositoryErrorException {

        instanceProperties = super.complexPropertyMappings(cache, entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();
        Reference igcEntity = entityMap.getIgcEntity();

        String fileName = igcEntity.getName();
        String extension = "";
        if (fileName.contains(".")) {
            extension = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        }

        instanceProperties = repositoryHelper.addStringPropertyToInstance(
                repositoryName,
                instanceProperties,
                "fileType",
                extension,
                methodName
        );

        return instanceProperties;

    }

    /**
     * Handle the search for 'fileType' by searching against the extension of the file in IGC.
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
     * @throws RepositoryErrorException on any other error
     */
    @Override
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 PropertyComparisonOperator operator,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException, RepositoryErrorException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, operator, value);

        final String methodName = "addComplexPropertySearchCriteria";

        // Only need to add a condition of we are after the 'fileType' and have been provided a String
        if (omrsPropertyName.equals("fileType") && value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
            String extension = value.valueAsString();
            String searchableExtension = repositoryHelper.getUnqualifiedLiteralString(extension);
            IGCRepositoryHelper.validateStringOperator(operator, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_STRING.getName(), methodName);
            IGCSearchCondition condition = null;
            switch (operator) {
                case IS_NULL:
                    // Assume that any file that has no . in it has no extension, and therefore the fileType is null
                    condition = new IGCSearchCondition("name", "like %{0}%", ".", true);
                    break;
                case NOT_NULL:
                    // Assume that any file that has a . in it has an extension, and therefore has a fileType
                    condition = new IGCSearchCondition("name", "like %{0}%", ".");
                    break;
                case EQ:
                    condition = new IGCSearchCondition("name", "like %{0}", searchableExtension);
                    break;
                case NEQ:
                    condition = new IGCSearchCondition("name", "like %{0}", searchableExtension, true);
                    break;
                case LIKE:
                    if (repositoryHelper.isExactMatchRegex(extension) || repositoryHelper.isEndsWithRegex(extension)) {
                        condition = new IGCSearchCondition("name", "like %{0}", searchableExtension);
                    } else if (repositoryHelper.isStartsWithRegex(extension) || repositoryHelper.isContainsRegex(extension)) {
                        condition = new IGCSearchCondition("name", "like %{0}%", searchableExtension);
                    } else {
                        throw new FunctionNotSupportedException(IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED.getMessageDefinition(repositoryName, extension),
                                this.getClass().getName(),
                                methodName);
                    }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComplexStringSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                               String repositoryName,
                                               IGCRestClient igcRestClient,
                                               IGCSearchConditionSet igcSearchConditionSet,
                                               String searchCriteria) throws FunctionNotSupportedException {

        // Note that we will not call the superclass as we do not want the default behavior
        final String methodName = "addComplexStringSearchCriteria";

        String searchableExtension = repositoryHelper.getUnqualifiedLiteralString(searchCriteria);
        if (repositoryHelper.isExactMatchRegex(searchCriteria) || repositoryHelper.isEndsWithRegex(searchCriteria)) {
            IGCSearchCondition endsWith = new IGCSearchCondition("name", "like %{0}", searchableExtension);
            igcSearchConditionSet.addCondition(endsWith);
        } else if (repositoryHelper.isStartsWithRegex(searchCriteria) || repositoryHelper.isContainsRegex(searchCriteria)) {
            IGCSearchCondition contains = new IGCSearchCondition("name", "like %{0}%", searchableExtension);
            igcSearchConditionSet.addCondition(contains);
        } else {
            throw new FunctionNotSupportedException(IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED.getMessageDefinition(repositoryName, searchCriteria),
                    this.getClass().getName(),
                    methodName);
        }

    }

}
