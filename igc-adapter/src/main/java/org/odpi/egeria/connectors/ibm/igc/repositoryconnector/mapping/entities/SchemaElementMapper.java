/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
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
        addComplexOmrsProperty("anchorGUID");

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
        IGCRepositoryHelper igcRepositoryHelper;
        try {
            igcRepositoryHelper = ((IGCOMRSMetadataCollection)igcomrsRepositoryConnector.getMetadataCollection()).getIgcRepositoryHelper();
        } catch (RepositoryErrorException e) {
            throw new OMRSRuntimeException(IGCOMRSErrorCode.REST_CLIENT_FAILURE.getMessageDefinition(igcomrsRepositoryConnector.getServerName()),
                    this.getClass().getName(),
                    methodName,
                    e);
        }
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // setup the OMRS 'anchorGUID' property
        instanceProperties = addAnchorGUIDProperty(repositoryHelper,
                repositoryName,
                igcRepositoryHelper,
                cache,
                igcEntity,
                igcRestClient,
                instanceProperties);

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

        // Only need to add a condition of we are after the 'anchorGUID', have been provided a String, and it is an
        // exact match that was requested
        if (omrsPropertyName.equals("anchorGUID") && value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
            String guidString = value.valueAsString();
            IGCSearchConditionSet toAdd = getParentAssetSearchCriteria(repositoryHelper, repositoryName, methodName, guidString, operator, true);
            if (toAdd.size() > 0) {
                igcSearchConditionSet.addNestedConditionSet(toAdd);
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

        // Only attempt to extend with criterion for anchorGUID if we have an exact match against something we can
        // reverse into a GUID, otherwise skip the criteria
        IGCSearchConditionSet toAdd = getParentAssetSearchCriteria(repositoryHelper, repositoryName, methodName, searchCriteria, PropertyComparisonOperator.EQ, false);
        if (toAdd.size() > 0) {
            igcSearchConditionSet.addNestedConditionSet(toAdd);
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

    /**
     * Retrieve the search criteria for the 'anchorGUID' of this IGC object.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName the name of the repository
     * @param methodName the name of the method retrieving the search criteria
     * @param regex the regular expression to use in looking up the anchorGUID
     * @param operator the comparison operator to use
     * @param failOnInexactRegex if true, throws the FunctionNotSupportedException if the regex provided is not an exact match regex
     * @return IGCSearchConditionSet
     * @throws FunctionNotSupportedException if the provided regex is not an exact match and failOnInexactRegex is true
     */
    protected IGCSearchConditionSet getParentAssetSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                                 String repositoryName,
                                                                 String methodName,
                                                                 String regex,
                                                                 PropertyComparisonOperator operator,
                                                                 boolean failOnInexactRegex) throws FunctionNotSupportedException {

        IGCSearchConditionSet conditions = new IGCSearchConditionSet();
        IGCEntityGuid guid = null;
        if (operator.equals(PropertyComparisonOperator.EQ)) {
            guid = IGCEntityGuid.fromGuid(regex);
        } else if (repositoryHelper.isExactMatchRegex(regex)) {
            guid = IGCEntityGuid.fromGuid(repositoryHelper.getUnqualifiedLiteralString(regex));
        }
        if (guid != null) {
            IGCSearchCondition condition;
            switch (getIgcAssetType()) {
                case "database_column":
                    IGCSearchConditionSet nested = new IGCSearchConditionSet();
                    IGCSearchCondition view = new IGCSearchCondition("view.database_schema", "=", guid.getRid());
                    IGCSearchCondition table = new IGCSearchCondition("database_table.database_schema", "=", guid.getRid());
                    nested.addCondition(view);
                    nested.addCondition(table);
                    nested.setMatchAnyCondition(true);
                    conditions.addNestedConditionSet(nested);
                    break;
                case "database_table":
                    condition = new IGCSearchCondition("database_schema", "=", guid.getRid());
                    conditions.addCondition(condition);
                    break;
                case "database_schema":
                    // At the RelationalDBSchemaType (database_schema) level, we should be mapping to the
                    // DeployedDatabaseSchema (as an Asset), but since these are generated from each other the RID
                    // will be identical -- so we should be able to narrow specifically to that RID
                    condition = new IGCSearchCondition("_id", "=", guid.getRid());
                    conditions.addCondition(condition);
                    break;
                case "data_file_field":
                    condition = new IGCSearchCondition("data_file_record.data_file", "=", guid.getRid());
                    conditions.addCondition(condition);
                    break;
                case "data_file_record":
                    condition = new IGCSearchCondition("data_file", "=", guid.getRid());
                    conditions.addCondition(condition);
                    break;
                default:
                    log.warn("Unable to add criteria for anchorGUID, type not known: {}", getIgcAssetType());
                    break;
            }
        } else if (failOnInexactRegex) {
            throw new FunctionNotSupportedException(IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED.getMessageDefinition(repositoryName, regex),
                    this.getClass().getName(),
                    methodName);
        }

        return conditions;

    }

    /**
     * Populate the 'anchorGUID' property on the instance properties of the SchemaElement.
     *
     * @param repositoryHelper the OMRS repository helper
     * @param repositoryName the repository name
     * @param igcRepositoryHelper the IGC repository helper
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param igcEntity the IGC object from which to determine the anchorGUID
     * @param igcRestClient connectivity to the IGC environment
     * @param instanceProperties the instance properties into which to populate the anchorGUID
     * @return InstanceProperties
     */
    protected InstanceProperties addAnchorGUIDProperty(OMRSRepositoryHelper repositoryHelper,
                                                       String repositoryName,
                                                       IGCRepositoryHelper igcRepositoryHelper,
                                                       ObjectCache cache,
                                                       Reference igcEntity,
                                                       IGCRestClient igcRestClient,
                                                       InstanceProperties instanceProperties) {

        final String methodName = "addAnchorGUIDProperty";

        Identity identity = igcEntity.getIdentity(igcRestClient, cache);
        Identity asset = getParentAssetIdentity(identity);
        if (asset != null) {
            // We should be safe with no prefix here as the assets DeployedDatabaseSchema and DataFile have no prefix
            IGCEntityGuid assetGuid = igcRepositoryHelper.getEntityGuid(asset.getAssetType(), null, asset.getRid());
            instanceProperties = repositoryHelper.addStringPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "anchorGUID",
                    assetGuid.toString(),
                    methodName
            );
        }

        return instanceProperties;

    }

    /**
     * Retrieve the Asset-level object's identity from the provided identity.
     *
     * @param identity the identity from which to retrieve the asset level identity
     * @return Identity or null, if there is no asset-level identity
     */
    private Identity getParentAssetIdentity(Identity identity) {
        Identity parent = identity.getParentIdentity();
        if (parent != null) {
            String type = parent.getAssetType();
            // Once we reach database_schema or data_file level we have the asset, so return this identity
            if (type.equals("database_schema") || type.equals("data_file")) {
                return parent;
            } else {
                // Otherwise continue to recurse
                return getParentAssetIdentity(parent);
            }
        } else {
            // If we get to a point where there is no parent, there is no asset, so return null
            return null;
        }
    }

}
