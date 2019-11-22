/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SemanticAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedTagMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Defines the default mapping to an OMRS entity (the OMRS "Referenceable" entity), along with the base methods
 * for all other entity mappings.
 */
public class ReferenceableMapper extends EntityMapping {

    private static final Logger log = LoggerFactory.getLogger(ReferenceableMapper.class);

    private static class Singleton {
        private static final ReferenceableMapper INSTANCE = new ReferenceableMapper();
    }
    public static ReferenceableMapper getInstance(IGCVersionEnum version) { return Singleton.INSTANCE; }

    // By default (if no IGC type or OMRS type defined), map between 'main_object' (IGC) and Referenceable (OMRS)
    private ReferenceableMapper() {
        this(
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                IGCRepositoryHelper.DEFAULT_IGC_TYPE_DISPLAY_NAME,
                "Referenceable"
        );
    }

    protected ReferenceableMapper(String igcAssetTypeName,
                                  String igcAssetTypeDisplayName,
                                  String omrsEntityTypeName,
                                  String igcRidPrefix) {
        this(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                igcRidPrefix,
                true
        );
    }

    protected ReferenceableMapper(String igcAssetTypeName,
                                  String igcAssetTypeDisplayName,
                                  String omrsEntityTypeName) {
        this(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                null,
                true
        );
    }

    protected ReferenceableMapper(String igcAssetTypeName,
                                  String igcAssetTypeDisplayName,
                                  String omrsEntityTypeName,
                                  String igcRidPrefix,
                                  boolean includeDefaultRelationships) {

        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                igcRidPrefix
        );

        if (includeDefaultRelationships) {
            // common set of relationships that could apply to all IGC objects (and all OMRS Referenceables)
            addRelationshipMapper(SemanticAssignmentMapper.getInstance(null));
            addRelationshipMapper(AttachedTagMapper.getInstance(null));
        }

        // common set of properties that apply to all Referenceable objects
        addComplexOmrsProperty("qualifiedName");
        addComplexOmrsProperty("additionalProperties");

        // common set of classifications that apply to all IGC objects (and all OMRS Referenceables) [none]

    }

    /**
     * Configures the 'qualifiedName' and 'additionalProperties' properties of all OMRS entities that extend
     * from Referenceable.
     *
     * @param entityMap instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     */
    @Override
    protected InstanceProperties complexPropertyMappings(EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        final String methodName = "complexPropertyMappings";

        Reference igcEntity = entityMap.getIgcEntity();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        EntityMapping mapping = entityMap.getMapping();
        OMRSRepositoryHelper omrsRepositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // Map IGC's identity characteristics to create a unique 'qualifiedName'
        String qualifiedName = igcEntity.getIdentity(igcomrsRepositoryConnector.getIGCRestClient()).toString();

        if (mapping.igcRidNeedsPrefix()) {
            qualifiedName = IGCRepositoryHelper.getQualifiedNameForGeneratedEntity(mapping.getIgcRidPrefix(), qualifiedName);
        }

        instanceProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                igcomrsRepositoryConnector.getRepositoryName(),
                instanceProperties,
                "qualifiedName",
                qualifiedName,
                methodName
        );

        // And map any other simple (non-relationship) properties that are not otherwise mapped into 'additionalProperties'
        Map<String, String> additionalProperties = new HashMap<>();

        List<String> nonRelationshipProperties = igcRestClient.getNonRelationshipPropertiesForType(igcEntity.getType());
        Set<String> alreadyMapped = mapping.getAllMappedIgcProperties();
        for (ClassificationMapping classificationMapping : mapping.getClassificationMappers()) {
            alreadyMapped.addAll(classificationMapping.getMappedIgcPropertyNames());
        }
        if (nonRelationshipProperties != null) {

            // Remove all of the already-mapped properties from our list of non-relationship properties
            Set<String> nonRelationshipsSet = new HashSet<>(nonRelationshipProperties);
            nonRelationshipsSet.removeAll(alreadyMapped);
            nonRelationshipsSet.removeAll(IGCRestConstants.getModificationProperties());

            // Iterate through the remaining property names, and add them to a map
            // Note that because 'additionalProperties' is a string-to-string map, we will just convert everything
            // to strings (even arrays of values, we'll concatenate into a single string)
            for (String propertyName : nonRelationshipsSet) {
                Object propertyValue = igcRestClient.getPropertyByName(igcEntity, propertyName);
                String value = null;
                if (propertyValue instanceof ArrayList) {
                    StringBuilder sb = new StringBuilder();
                    List<Object> list = (List<Object>) propertyValue;
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size() - 1; i++) {
                            sb.append(list.get(i).toString() + ", ");
                        }
                        sb.append(list.get(list.size() - 1));
                    }
                    value = sb.toString();
                } else if (propertyValue != null) {
                    value = propertyValue.toString();
                }
                // Leave out any properties that are null or empty strings, as these are unset in IGC
                if (value != null && !value.equals("")) {
                    additionalProperties.put(propertyName, value);
                }
            }

            // and finally setup the 'additionalProperties' attribute using this map
            instanceProperties = omrsRepositoryHelper.addStringMapPropertyToInstance(
                    repositoryName,
                    instanceProperties,
                    "additionalProperties",
                    additionalProperties,
                    methodName
            );

        }

        return instanceProperties;

    }

    /**
     * Handle the search for 'qualifiedName' and 'additionalProperties' by reverse-engineering them to IGC-specific
     * properties and values.
     *
     * @param repositoryHelper helper for the OMRS repository
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     */
    @Override
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {

        final String methodName = "addComplexPropertySearchCriteria";

        if (omrsPropertyName.equals("qualifiedName")) {

            if (log.isDebugEnabled()) { log.debug("Adding complex search criteria for: qualifiedName"); }

            String qualifiedName = ((PrimitivePropertyValue) value).getPrimitiveValue().toString();
            String unqualifiedName = repositoryHelper.getUnqualifiedLiteralString(qualifiedName);

            // Check if the qualifiedName has a generated prefix -- need to remove prior to next steps, if so...
            unqualifiedName = IGCRepositoryHelper.getSearchableQualifiedName(unqualifiedName);
            if (log.isDebugEnabled()) { log.debug("Looking up identity: {}", unqualifiedName); }
            Identity identity = Identity.getFromString(unqualifiedName, igcRestClient);
            boolean skip = false;

            if (repositoryHelper.isContainsRegex(qualifiedName)
                    || repositoryHelper.isStartsWithRegex(qualifiedName)
                    || repositoryHelper.isEndsWithRegex(qualifiedName)) {
                // TODO: identity must be translate-able (to some degree?) but type being searched does not need to match
                if (identity != null) {
                    if (log.isDebugEnabled()) { log.debug(". . .found identity: {}", identity.toString()); }
                    IGCSearchConditionSet nested = identity.getSearchCriteria();
                    igcSearchConditionSet.addNestedConditionSet(nested);
                } else {
                    if (log.isInfoEnabled()) { log.info("Unable to find identity '{}' -- skipping.", qualifiedName); }
                    skip = true;
                }
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        repositoryName,
                        qualifiedName);
                throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                        IGCOMRSMetadataCollection.class.getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            } else if (repositoryHelper.isExactMatchRegex(qualifiedName)) {
                // Identity must be translate-able and match the type being searched, if it is to be an exact match
                if (identity != null) {
                    if (log.isDebugEnabled()) { log.debug(". . .found identity: {}", identity.toString()); }
                    String igcType = IGCRestConstants.getAssetTypeForSearch(identity.getAssetType());
                    if (igcType.equals(getIgcAssetType()) || getOtherIGCAssetTypes().contains(igcType)) {
                        IGCSearchConditionSet nested = identity.getSearchCriteria();
                        igcSearchConditionSet.addNestedConditionSet(nested);
                    } else {
                        if (log.isInfoEnabled()) { log.info("Search type did not match identity type -- skipping."); }
                        skip = true;
                    }
                } else {
                    if (log.isInfoEnabled()) { log.info("Unable to find identity '{}' -- skipping.", qualifiedName); }
                    skip = true;
                }
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        repositoryName,
                        qualifiedName);
                throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                        IGCOMRSMetadataCollection.class.getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }

            if (skip) {
                if (log.isDebugEnabled()) { log.debug("Adding search condition to ensure no results."); }
                IGCSearchConditionSet byName = new IGCSearchConditionSet(IGCRestConstants.getConditionToForceNoSearchResults());
                igcSearchConditionSet.addNestedConditionSet(byName);
            }

        } else if (omrsPropertyName.equals("additionalProperties")) {

            if (log.isDebugEnabled()) { log.debug("Adding complex search criteria for: additionalProperties"); }

            Map<String, InstancePropertyValue> mapValues = ((MapPropertyValue) value).getMapValues().getInstanceProperties();
            for (Map.Entry<String, InstancePropertyValue> nextEntry : mapValues.entrySet()) {
                IGCRepositoryHelper.addIGCSearchConditionFromValue(
                        repositoryHelper,
                        repositoryName,
                        igcSearchConditionSet,
                        nextEntry.getKey(),
                        nextEntry.getValue()
                );
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

        super.addComplexStringSearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, searchCriteria);

        final String methodName = "addComplexStringSearchCriteria";

        List<String> stringPropertiesForType = igcRestClient.getAllStringPropertiesForType(IGCRestConstants.getAssetTypeForSearch(getIgcAssetType()));

        // By default, add a condition for every complex-mapped string property EXCEPT for the modification details
        for (String propertyName : getComplexMappedIgcProperties()) {
            if (stringPropertiesForType.contains(propertyName) && !propertyName.equals("modified_by") && !propertyName.equals("created_by")) {
                IGCSearchCondition condition = IGCRepositoryHelper.getRegexSearchCondition(
                        repositoryHelper,
                        repositoryName,
                        methodName,
                        propertyName,
                        searchCriteria
                );
                igcSearchConditionSet.addCondition(condition);
            }
        }

    }

}
