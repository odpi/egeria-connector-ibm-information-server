/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.OMRSRuntimeException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the mapping to the OMRS "SchemaElement" entity.
 */
public class SchemaElement_Mapper extends ReferenceableMapper {

    private static final Logger log = LoggerFactory.getLogger(SchemaElement_Mapper.class);

    private static class Singleton {
        private static final SchemaElement_Mapper INSTANCE = new SchemaElement_Mapper();
    }
    public static SchemaElement_Mapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private SchemaElement_Mapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "",
                "",
                "SchemaElement"
        );

    }

    protected SchemaElement_Mapper(String igcAssetTypeName,
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
        addComplexOmrsProperty("anchorGUID");

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
        IGCRepositoryHelper igcRepositoryHelper;
        try {
            igcRepositoryHelper = ((IGCOMRSMetadataCollection)igcomrsRepositoryConnector.getMetadataCollection()).getIgcRepositoryHelper();
        } catch (RepositoryErrorException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REST_CLIENT_FAILURE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(igcomrsRepositoryConnector.getServerName());
            throw new OMRSRuntimeException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction(),
                    e);
        }
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // setup the OMRS 'anchorGUID' property
        Identity identity = igcEntity.getIdentity(igcRestClient);
        Identity parent = identity.getParentIdentity();
        if (parent != null) {
            // TODO: are none of the parents ever generated?
            IGCEntityGuid parentGuid = igcRepositoryHelper.getEntityGuid(parent.getAssetType(), null, parent.getRid());
            instanceProperties = repositoryHelper.addStringPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "anchorGUID",
                    parentGuid.asGuid(),
                    methodName
            );
        }

        return instanceProperties;

    }

    /**
     * Handle the search for 'anchorGUID' by searching against the parent object in IGC.
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

        final String methodName = "addComplexPropertySearchCriteria";

        // Only need to add a condition of we are after the 'anchorGUID', have been provided a String, and it is an
        // exact match that was requested
        if (omrsPropertyName.equals("anchorGUID") && value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
            String guidString = value.valueAsString();
            String searchableGuid = repositoryHelper.getUnqualifiedLiteralString(guidString);
            String parentPropertyName = getParentPropertyName();
            if (parentPropertyName != null) {
                // Only support exact matches for GUIDs
                IGCEntityGuid guid = IGCEntityGuid.fromGuid(searchableGuid);
                if (guid != null) {
                    if (repositoryHelper.isExactMatchRegex(guidString)) {
                        IGCSearchCondition exact = new IGCSearchCondition(parentPropertyName, "=", guid.getRid());
                        igcSearchConditionSet.addCondition(exact);
                    } else {
                        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                                repositoryName,
                                guidString);
                        throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                                this.getClass().getName(),
                                methodName,
                                errorMessage,
                                errorCode.getSystemAction(),
                                errorCode.getUserAction());
                    }
                }
            } else {
                log.warn("Unable to add criteria for anchorGUID, type not known: {}", getIgcAssetType());
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

        // Only attempt to extend with criterion for anchorGUID if we have an exact match against something we can
        // reverse into a GUID, otherwise skip the criteria
        String searchableAnchor = repositoryHelper.getUnqualifiedLiteralString(searchCriteria);
        if (repositoryHelper.isExactMatchRegex(searchCriteria)) {
            IGCEntityGuid guid = IGCEntityGuid.fromGuid(searchableAnchor);
            String parentPropertyName = getParentPropertyName();
            if (guid != null && parentPropertyName != null) {
                IGCSearchCondition byGuid = new IGCSearchCondition(parentPropertyName, "=", guid.getRid());
                igcSearchConditionSet.addCondition(byGuid);
            }
        }

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
