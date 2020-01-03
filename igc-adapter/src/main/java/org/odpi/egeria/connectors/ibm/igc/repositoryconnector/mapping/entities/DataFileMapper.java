/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AssetZoneMembershipMapper_DataFile;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_FileRecord;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.NestedFileMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

/**
 * Defines the mapping to the OMRS "DataFile" entity.
 */
public class DataFileMapper extends DataStore_Mapper {

    private static class Singleton {
        private static final DataFileMapper INSTANCE = new DataFileMapper();
    }
    public static DataFileMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private DataFileMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file",
                "Data File",
                "DataFile"
        );

        // The list of properties that should be mapped
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
        OMRSRepositoryHelper repositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();
        Reference igcEntity = entityMap.getIgcEntity();

        String fileName = igcEntity.getName();
        String extension = "";
        if (fileName.contains(".")) {
            extension = fileName.substring(fileName.lastIndexOf("."));
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
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, value);

        final String methodName = "addComplexPropertySearchCriteria";

        // Only need to add a condition of we are after the 'fileType' and have been provided a String
        if (omrsPropertyName.equals("fileType") && value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
            String extension = value.valueAsString();
            String searchableExtension = repositoryHelper.getUnqualifiedLiteralString(extension);
            if (repositoryHelper.isExactMatchRegex(extension) || repositoryHelper.isEndsWithRegex(extension)) {
                IGCSearchCondition endsWith = new IGCSearchCondition("name", "like %{0}", searchableExtension);
                igcSearchConditionSet.addCondition(endsWith);
            } else if (repositoryHelper.isStartsWithRegex(extension) || repositoryHelper.isContainsRegex(extension)) {
                IGCSearchCondition contains = new IGCSearchCondition("name", "like %{0}%", searchableExtension);
                igcSearchConditionSet.addCondition(contains);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        repositoryName,
                        extension);
                throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
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
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    repositoryName,
                    searchCriteria);
            throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

    }

}
