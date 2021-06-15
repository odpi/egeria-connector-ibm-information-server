/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCParsingException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedNoteLogMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SemanticAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedTagMapper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Defines the default mapping to an OMRS entity (the OMRS "Referenceable" entity), along with the base methods
 * for all other entity mappings.
 */
public class ReferenceableMapper extends OpenMetadataRootMapper {

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
        init(igcRidPrefix, includeDefaultRelationships);

    }

    protected ReferenceableMapper(String igcAssetTypeName,
                                  String igcAssetTypeDisplayName,
                                  String omrsEntityTypeName,
                                  String igcRidPrefix,
                                  boolean includeDefaultRelationships,
                                  boolean searchable) {
        super(
                igcAssetTypeName,
                igcAssetTypeDisplayName,
                omrsEntityTypeName,
                igcRidPrefix,
                searchable
        );
        init(igcRidPrefix, includeDefaultRelationships);

    }

    private void init(String igcRidPrefix, boolean includeDefaultRelationships) {
        if (includeDefaultRelationships) {
            // common set of relationships that could apply to all IGC objects (and all OMRS Referenceables)
            addRelationshipMapper(AttachedTagMapper.getInstance(null));
            if (igcRidPrefix == null) {
                // Only include NoteLogs for non-generated entities
                addRelationshipMapper(AttachedNoteLogMapper.getInstance(null));
            }
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
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param entityMap instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    protected InstanceProperties complexPropertyMappings(ObjectCache cache,
                                                         EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) throws RepositoryErrorException {

        final String methodName = "complexPropertyMappings";

        Reference igcEntity = entityMap.getIgcEntity();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        EntityMapping mapping = entityMap.getMapping();
        OMRSRepositoryHelper omrsRepositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        // Map IGC's identity characteristics to create a unique 'qualifiedName'
        String qualifiedName = null;
        try {
            qualifiedName = igcEntity.getIdentity(igcomrsRepositoryConnector.getIGCRestClient(), cache).toString();
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

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

        List<String> nonRelationshipProperties = Collections.emptyList();
        try {
            nonRelationshipProperties = igcRestClient.getNonRelationshipPropertiesForType(igcEntity.getType());
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }
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
            try {
                for (String propertyName : nonRelationshipsSet) {
                    Object propertyValue = igcRestClient.getPropertyByName(igcEntity, propertyName);
                    String value = null;
                    if (propertyValue instanceof List) {
                        StringBuilder sb = new StringBuilder();
                        List<?> list = (List<?>) propertyValue;
                        if (!list.isEmpty()) {
                            for (int i = 0; i < list.size() - 1; i++) {
                                sb.append(list.get(i).toString());
                                sb.append(", ");
                            }
                            sb.append(list.get(list.size() - 1));
                        }
                        value = sb.toString();
                    } else if (propertyValue != null) {
                        value = propertyValue.toString();
                    }
                    // Leave out any properties that are null or empty strings, as these are unset in IGC
                    if (value != null && value.length() != 0) {
                        additionalProperties.put(propertyName, value);
                    }
                }
            } catch (IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
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
     * @param operator the comparison operator to use
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
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

        final String methodName = "addComplexPropertySearchCriteria";

        if (omrsPropertyName.equals("qualifiedName")) {

            log.debug("Adding complex search criteria for: qualifiedName");
            IGCRepositoryHelper.validateStringOperator(operator, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_STRING.getName(), methodName);

            String qualifiedName = value.valueAsString();
            String unqualifiedName;
            Identity.StringType stringType = Identity.StringType.EXACT;
            if (operator.equals(PropertyComparisonOperator.LIKE)) {
                unqualifiedName = repositoryHelper.getUnqualifiedLiteralString(qualifiedName);
                if (repositoryHelper.isEndsWithRegex(qualifiedName)) {
                    stringType = Identity.StringType.ENDS_WITH;
                } else if (repositoryHelper.isStartsWithRegex(qualifiedName)) {
                    stringType = Identity.StringType.STARTS_WITH;
                } else if (repositoryHelper.isContainsRegex(qualifiedName)) {
                    stringType = Identity.StringType.CONTAINS;
                } else if (!repositoryHelper.isExactMatchRegex(qualifiedName)) {
                    throw new FunctionNotSupportedException(IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED.getMessageDefinition(repositoryName, qualifiedName),
                            this.getClass().getName(),
                            methodName);
                }
            } else {
                unqualifiedName = qualifiedName;
            }

            // Check if the qualifiedName has a generated prefix -- need to remove prior to next steps, if so...
            unqualifiedName = IGCRepositoryHelper.getSearchableQualifiedName(unqualifiedName);
            log.debug("Looking up identity: {}", unqualifiedName);

            Identity identity = null;
            try {
                identity = Identity.getFromString(unqualifiedName, igcRestClient, stringType);
            } catch (IGCParsingException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.INVALID_QUALIFIED_NAME, methodName, null, unqualifiedName);
            }
            boolean skip = false;

            if (stringType.equals(Identity.StringType.STARTS_WITH)) {
                // for a startsWith, we only know the upper portions of the context, so the best we can do is search
                // for everything and trim the results after they've been returned
                log.debug(". . . running expensive startsWith query on: {}", qualifiedName);
            } else if (stringType.equals(Identity.StringType.ENDS_WITH)) {
                if (identity != null) {
                    // for an endsWith, we only know the lower portions of the context, which we should be able to
                    // therefore search directly
                    log.debug(". . .found identity: {}", identity);
                    IGCSearchConditionSet nested = identity.getSearchCriteria();
                    igcSearchConditionSet.addNestedConditionSet(nested);
                } else {
                    log.debug(". . . running expensive endsWith query on: {}", qualifiedName);
                }
            } else if (stringType.equals(Identity.StringType.CONTAINS)) {
                // for a contains, we only know some middle portion of the context, so the best we can do is search
                // for everything and trim the results after they've been returned
                log.debug(". . . running expensive contains query on: {}", qualifiedName);
            } else {
                // Identity must be translate-able and match the type being searched, if it is to be an exact match
                if (identity != null) {
                    log.debug(". . .found identity: {}", identity);
                    String igcType = IGCRestConstants.getAssetTypeForSearch(identity.getAssetType());
                    if (igcType.equals(getIgcAssetType()) || getOtherIGCAssetTypes().contains(igcType)) {
                        IGCSearchConditionSet nested = identity.getSearchCriteria();
                        if (operator == PropertyComparisonOperator.NEQ) {
                            nested.setNegateAll(true);
                        }
                        igcSearchConditionSet.addNestedConditionSet(nested);
                    } else {
                        log.info("Search type did not match identity type -- skipping.");
                        skip = true;
                    }
                } else if (operator != PropertyComparisonOperator.NEQ) {
                    log.info("Unable to find identity '{}' -- skipping.", qualifiedName);
                    skip = true;
                }
            }

            if (skip) {
                log.debug("Adding search condition to ensure no results.");
                IGCSearchConditionSet byName = new IGCSearchConditionSet(IGCRestConstants.getConditionToForceNoSearchResults());
                igcSearchConditionSet.addNestedConditionSet(byName);
            }

        } else if (omrsPropertyName.equals("additionalProperties")) {

            log.debug("Adding complex search criteria for: additionalProperties");

            // TODO: not entirely sure this is the semantic we want for a map-based search?
            Map<String, InstancePropertyValue> mapValues = ((MapPropertyValue) value).getMapValues().getInstanceProperties();
            for (Map.Entry<String, InstancePropertyValue> nextEntry : mapValues.entrySet()) {
                IGCRepositoryHelper.addIGCSearchCondition(
                        repositoryHelper,
                        repositoryName,
                        igcSearchConditionSet,
                        nextEntry.getKey(),
                        operator,
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
                                               String searchCriteria) throws FunctionNotSupportedException, RepositoryErrorException {

        super.addComplexStringSearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, searchCriteria);

        final String methodName = "addComplexStringSearchCriteria";

        try {
            List<String> stringPropertiesForType = igcRestClient.getAllStringPropertiesForType(IGCRestConstants.getAssetTypeForSearch(getIgcAssetType()));

            // By default, add a condition for every complex-mapped string property EXCEPT for the modification details
            // TODO: we actually should include the modification details, and also match against the metadata collection ID and name as well?
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
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

    }

}
