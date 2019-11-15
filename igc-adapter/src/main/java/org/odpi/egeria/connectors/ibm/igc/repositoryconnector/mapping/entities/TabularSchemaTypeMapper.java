/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DataFile;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_FileRecord;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_RecordField;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;

/**
 * Defines the mapping to the OMRS "TabularSchemaType" entity.
 */
public class TabularSchemaTypeMapper extends ComplexSchemaType_Mapper {

    private static class Singleton {
        private static final TabularSchemaTypeMapper INSTANCE = new TabularSchemaTypeMapper();
    }
    public static TabularSchemaTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TabularSchemaTypeMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file_record",
                "Data File Record",
                "TabularSchemaType",
                null
        );

        // The list of relationships that should be mapped
        addRelationshipMapper(AssetSchemaTypeMapper_FileRecord.getInstance(null));
        addRelationshipMapper(AttributeForSchemaMapper_RecordField.getInstance(null));

    }

    /**
     * Handle the search for 'namespace' by searching against the 'name' of the parent data_file in IGC.
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
        if (omrsPropertyName.equals("namespace") && value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
            String name = value.valueAsString();
            String searchableName = repositoryHelper.getUnqualifiedLiteralString(name);
            if (repositoryHelper.isExactMatchRegex(name)) {
                IGCSearchCondition exact = new IGCSearchCondition("data_file.name", "=", searchableName);
                igcSearchConditionSet.addCondition(exact);
            } else if (repositoryHelper.isEndsWithRegex(name)) {
                IGCSearchCondition endsWith = new IGCSearchCondition("data_file.name", "like %{0}", searchableName);
                igcSearchConditionSet.addCondition(endsWith);
            } else if (repositoryHelper.isStartsWithRegex(name)) {
                IGCSearchCondition startsWith = new IGCSearchCondition("data_file.name", "like {0}%", searchableName);
                igcSearchConditionSet.addCondition(startsWith);
            } else if (repositoryHelper.isContainsRegex(name)) {
                IGCSearchCondition contains = new IGCSearchCondition("data_file.name", "like %{0}%", searchableName);
                igcSearchConditionSet.addCondition(contains);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        repositoryName,
                        name);
                throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }
        }

    }

}
